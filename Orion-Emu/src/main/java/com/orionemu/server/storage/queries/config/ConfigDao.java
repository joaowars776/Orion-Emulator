package com.orionemu.server.storage.queries.config;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.rooms.filter.FilterMode;
import com.orionemu.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConfigDao {
    public static void getAll() {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet config = null;
        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM server_configuration LIMIT 1", sqlConnection);

            config = preparedStatement.executeQuery();

            while (config.next()) {
                OrionSettings.motdEnabled = config.getBoolean("motd_enabled");
                OrionSettings.motdMessage = config.getString("motd_message");
                OrionSettings.hotelName = config.getString("hotel_name");
                OrionSettings.hotelUrl = config.getString("hotel_url");
                OrionSettings.groupCost = config.getInt("group_cost");
                OrionSettings.onlineRewardEnabled = config.getBoolean("online_reward_enabled");
                OrionSettings.onlineRewardCredits = config.getInt("online_reward_credits");
                OrionSettings.onlineRewardDuckets = config.getInt("online_reward_duckets");
                OrionSettings.onlineRewardInterval = config.getInt("online_reward_interval");
                OrionSettings.aboutImg = config.getString("about_image");
                OrionSettings.aboutShowPlayersOnline = config.getBoolean("about_show_players_online");
                OrionSettings.aboutShowRoomsActive = config.getBoolean("about_show_rooms_active");
                OrionSettings.aboutShowUptime = config.getBoolean("about_show_uptime");
                OrionSettings.floorEditorMaxX = config.getInt("floor_editor_max_x");
                OrionSettings.floorEditorMaxY = config.getInt("floor_editor_max_y");
                OrionSettings.floorEditorMaxTotal = config.getInt("floor_editor_max_total");
                OrionSettings.roomMaxPlayers = config.getInt("room_max_players");
                OrionSettings.roomEncryptPasswords = config.getBoolean("room_encrypt_passwords");
                OrionSettings.roomCanPlaceItemOnEntity = config.getBoolean("room_can_place_item_on_entity");
                OrionSettings.roomMaxBots = config.getInt("room_max_bots");
                OrionSettings.roomMaxPets = config.getInt("room_max_pets");
                OrionSettings.roomWiredRewardMinimumRank = config.getInt("room_wired_reward_minimum_rank");
                OrionSettings.roomIdleMinutes = config.getInt("room_idle_minutes");
                OrionSettings.wordFilterMode = FilterMode.valueOf(config.getString("word_filter_mode").toUpperCase());
                OrionSettings.useDatabaseIp = config.getBoolean("use_database_ip");
                OrionSettings.saveLogins = config.getBoolean("save_logins");
                OrionSettings.playerInfiniteBalance = config.getBoolean("player_infinite_balance");
                OrionSettings.playerGiftCooldown = config.getInt("player_gift_cooldown");
                OrionSettings.playerChangeFigureCooldown = config.getInt("player_change_figure_cooldown");
                OrionSettings.playerFigureValidation = config.getBoolean("player_figure_validation");
                OrionSettings.messengerMaxFriends = config.getInt("messenger_max_friends");
                OrionSettings.messengerLogMessages = config.getBoolean("messenger_log_messages");
//                OrionSettings.storageItemQueueEnabled = config.getBoolean("storage_item_queue_enabled");
//                OrionSettings.storagePlayerQueueEnabled = config.getBoolean("storage_player_queue_enabled");
                OrionSettings.cameraPhotoUrl = config.getString("camera_photo_url");
                OrionSettings.cameraPhotoItemId = config.getInt("camera_photo_itemid");
                OrionSettings.maxConnectionsPerIpAddress = config.getInt("max_connections_per_ip");
                OrionSettings.groupChatEnabled = config.getBoolean("group_chat_enabled");
                OrionSettings.logCatalogPurchases = config.getBoolean("log_catalog_purchases");
                OrionSettings.hallOfFameEnabled = config.getBoolean("hall_of_fame_enabled");
                OrionSettings.hallOfFameCurrency = config.getString("hall_of_fame_currency");
                OrionSettings.hallOfFameRefreshMinutes = config.getInt("hall_of_fame_refresh_minutes");
                OrionSettings.hallOfFameTextsKey = config.getString("hall_of_fame_texts_key");
                final String characters = config.getString("word_filter_strict_chars");

                OrionSettings.strictFilterCharacters.clear();

                for (String charSet : characters.split(",")) {
                    if (!charSet.contains(":")) continue;

                    final String[] chars = charSet.split(":");

                    if (chars.length == 2) {
                        OrionSettings.strictFilterCharacters.put(chars[0], chars[1]);
                    } else {
                        OrionSettings.strictFilterCharacters.put(chars[0], "");
                    }
                }
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(config);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }
}
