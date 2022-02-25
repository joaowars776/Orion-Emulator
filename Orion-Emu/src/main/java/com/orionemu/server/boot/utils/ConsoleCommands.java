package com.orionemu.server.boot.utils;

import com.orionemu.api.stats.OrionStats;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.moderation.BanManager;
import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.game.permissions.PermissionsManager;
import com.orionemu.server.modules.ModuleManager;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.storage.SqlHelper;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ConsoleCommands {
    private static final Logger log = Logger.getLogger("Console Command Handler");

    public static void init() {
        // Console commands
        final Thread cmdThr = new Thread() {
            public void run() {
                while (Orion.isRunning) {
                    if (!Orion.isRunning) {
                        break;
                    }

                    try {
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        String input = br.readLine();

                        if (input != null) {
                            handleCommand(input);
                        }
                    } catch (Exception e) {
                        log.error("Error while parsing console command");
                    }
                }
            }
        };

        cmdThr.start();
    }

    public static void handleCommand(String line) {
        if (line.startsWith("/")) {
            switch (line.split(" ")[0]) {
                default:
                    log.error("Invalid command");
                    break;

                case "/query-log":
                    SqlHelper.queryLogEnabled = !SqlHelper.queryLogEnabled;
                    break;

                case "/":
                case "/help":
                case "/commands":
                    log.info("Commands available: /about, /reload_messages, /gc, /reload_permissions, /changemotd, /reload_catalog, /reload_bans, /reload_locale, /reload_permissions, /queries, /queries");
                    break;

                case "/reload_modules":
                    ModuleManager.getInstance().initialize();
                    log.info("Modules reloaded successfully.");
                    break;

                case "/about":
                    final OrionStats stats = Orion.getStats();

                    log.info("This server is powered by Orion (" + Orion.getBuild() + ")");
                    log.info("    Players online: " + stats.getPlayers());
                    log.info("    Active rooms: " + stats.getRooms());
                    log.info("    Uptime: " + stats.getUptime());
                    log.info("    Process ID: " + stats.getProcessId());
                    log.info("    Memory allocated: " + stats.getAllocatedMemory() + "MB");
                    log.info("    Memory usage: " + stats.getUsedMemory() + "MB");
                    break;

                case "/reload_messages":
                    NetworkManager.getInstance().getMessages().load();
                    log.info("Message handlers were reloaded");
                    break;

                case "/gc":
                    System.gc();
                    log.info("GC was run");
                    break;

                case "/changemotd":
                    String motd = line.replace("/changemotd ", "");
                    OrionSettings.setMotd(motd);
                    log.info("Message of the day was set.");
                    break;

                case "/reload_permissions":
                    PermissionsManager.getInstance().loadCommands();
                    PermissionsManager.getInstance().loadOverrideCommands();
                    PermissionsManager.getInstance().loadPerks();
                    PermissionsManager.getInstance().loadRankPermissions();
                    log.info("Permissions cache was reloaded.");
                    break;

                case "/reload_catalog":
                    CatalogManager.getInstance().loadItemsAndPages();
                    CatalogManager.getInstance().loadGiftBoxes();
                    log.info("Catalog cache was reloaded.");
                    break;

                case "/reload_bans":
                    BanManager.getInstance().loadBans();
                    log.info("Bans were reloaded.");
                    break;

                case "/reload_navigator":
                    NavigatorManager.getInstance().loadPublicRooms();
                    NavigatorManager.getInstance().loadCategories();
                    log.info("Navigator was reloaded.");
                    break;

                case "/reload_locale":
                    Locale.initialize();
                    log.info("Locale configuration was reloaded.");
                    break;

                case "/queries":

                    log.info("Queries");
                    log.info("================================================");

                    for (Map.Entry<String, AtomicInteger> query : SqlHelper.getQueryCounters().entrySet()) {
                        log.info("Query:" + query.getKey());
                        log.info("Count: " + query.getValue().get());
                        log.info("");
                    }

                    break;

                case "/clear_queries":
                    SqlHelper.getQueryCounters().clear();
                    log.info("Query counters have been cleared.");
                    break;
            }
        } else {
            log.error("Invalid command");
        }
    }
}
