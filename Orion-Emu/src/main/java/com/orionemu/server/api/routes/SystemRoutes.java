package com.orionemu.server.api.routes;

import com.orionemu.api.networking.sessions.BaseSession;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.boot.utils.ShutdownProcess;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.commands.CommandManager;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.landing.LandingManager;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.catalog.CatalogPublishMessageComposer;
import com.orionemu.server.network.messages.outgoing.moderation.ModToolMessageComposer;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class SystemRoutes {
    public static Object status(Request req, Response res) {
        Map<String, Object> result = new HashMap<>();
        res.type("application/json");

        result.put("status", Orion.getStats());

        return result;
    }

    public static Object shutdown(Request req, Response res) {
        Map<String, Object> result = new HashMap<>();
        res.type("application/json");

        try {
            result.put("success", true);

            return result;
        } finally {
            ShutdownProcess.shutdown(true);
        }
    }

    public static Object reload(Request req, Response res) {
        Map<String, Object> result = new HashMap<>();
        res.type("application/json");

        String type = req.params("type");

        if (type == null) {
            result.put("error", "Invalid type");
            return result;
        }

        switch (type) {
            case "bans":
                BanManager.getInstance().loadBans();
                break;

            case "catalog":
                CatalogManager.getInstance().loadItemsAndPages();
                CatalogManager.getInstance().loadGiftBoxes();

                NetworkManager.getInstance().getSessions().broadcast(new CatalogPublishMessageComposer(true));
                break;

            case "navigator":
                NavigatorManager.getInstance().loadPublicRooms();
                break;

            case "permissions":
                PermissionsManager.getInstance().loadRankPermissions();
                PermissionsManager.getInstance().loadPerks();
                PermissionsManager.getInstance().loadCommands();
                PermissionsManager.getInstance().loadOverrideCommands();
                break;

            case "config":
                OrionSettings.initialize();
                break;

            case "news":
                LandingManager.getInstance().loadArticles();
                break;

            case "items":
                ItemManager.getInstance().loadItemDefinitions();
                break;

            case "filter":
                RoomManager.getInstance().getFilter().loadFilter();
                break;

            case "locale":
                Locale.reload();
                CommandManager.getInstance().reloadAllCommands();
                break;

            case "modpresets":
                ModerationManager.getInstance().loadPresets();

                for (BaseSession session : NetworkManager.getInstance().getSessions().getByPlayerPermission("mod_tool")) {
                    session.send(new ModToolMessageComposer());
                }

                break;
        }

        result.put("success", true);
        return result;
    }

}
