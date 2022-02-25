package com.orionemu.server.boot;

import com.orionemu.games.GamesManager;
import com.orionemu.games.snowwar.thread.WorkerTasks;
import com.orionemu.server.api.APIManager;
import com.orionemu.server.boot.utils.gui.OrionGui;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Configuration;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.GameCycle;
import com.orionemu.server.game.achievements.AchievementManager;
import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.commands.CommandManager;
import com.orionemu.server.game.gamecenter.GameDataManager;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.guides.GuideManager;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.landing.LandingManager;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.game.pets.PetManager;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.polls.PollManager;
import com.orionemu.server.game.quests.QuestManager;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.bundles.RoomBundleManager;
import com.orionemu.server.game.utilities.validator.PlayerFigureValidator;
import com.orionemu.server.logging.LogManager;
import com.orionemu.server.modules.ModuleManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.storage.StorageManager;
import com.orionemu.server.storage.queue.types.ItemStorageQueue;
import com.orionemu.server.storage.queue.types.PlayerDataStorageQueue;
import com.orionemu.server.tasks.OrionThreadManager;
import org.apache.log4j.Logger;

import java.util.Map;


public class OrionServer {
    private final Logger log = Logger.getLogger(OrionServer.class.getName());

    public static final String CLIENT_VERSION = "PRODUCTION-201610052203-260805057";

    public OrionServer(Map<String, String> overridenConfig) {
        Configuration.setConfiguration(new Configuration("./config/orion.properties"));

        if (overridenConfig != null) {
            Configuration.currentConfig().override(overridenConfig);
        }
    }

    /**
     * Initialize Orion Server
     */
    public void init() {
        ModuleManager.getInstance().initialize();
        APIManager.getInstance().initialize();
//        WebSocketServer.getInstance().initialize();
        PlayerFigureValidator.loadFigureData();

        OrionThreadManager.getInstance().initialize();
        StorageManager.getInstance().initialize();
        LogManager.getInstance().initialize();

        // Locale & config
        OrionSettings.initialize();
        Locale.initialize();

        // Initialize the game managers
        // TODO: Implement some sort of dependency injection so we don't need any of this crap!!

        PermissionsManager.getInstance().initialize();
        RoomBundleManager.getInstance().initialize();
        ItemManager.getInstance().initialize();
        CatalogManager.getInstance().initialize();
        RoomManager.getInstance().initialize();
        NavigatorManager.getInstance().initialize();
        CommandManager.getInstance().initialize();
        BanManager.getInstance().initialize();
        ModerationManager.getInstance().initialize();
        PetManager.getInstance().initialize();
        LandingManager.getInstance().initialize();
        PlayerManager.getInstance().initialize();
        GroupManager.getInstance().initialize();
        QuestManager.getInstance().initialize();
        AchievementManager.getInstance().initialize();
        PollManager.getInstance().initialize();
        GuideManager.getInstance().initialize();

        PlayerDataStorageQueue.getInstance().initialize();
        ItemStorageQueue.getInstance().initialize();

        String ipAddress = this.getConfig().get("orion.network.host"),
                port = this.getConfig().get("orion.network.port");

        NetworkManager.getInstance().initialize(ipAddress, port);
        GameCycle.getInstance().initialize();
        GameDataManager.getInstance().initialize();
        GamesManager.initManager();
        WorkerTasks.initWorkers();

        if(Orion.showGui) {
            OrionGui gui = new OrionGui();
            gui.setVisible(true);
        }

        if (Orion.isDebugging) {
            log.debug("Orion Server is debugging");
        }
    }

    /**
     * Get the Orion configuration
     *
     * @return Orion configuration
     */
    public Configuration getConfig() {
        return Configuration.currentConfig();
    }

    public Logger getLogger() {
        return log;
    }
}
