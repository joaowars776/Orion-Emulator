package com.orionemu.server.storage.queries.player;

import com.orionemu.server.boot.Orion;
import com.orionemu.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PlayerAccessDao {

    public static void saveAccess(int playerId, String hardwareId, String ipAddress) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT into player_access (player_id, hardware_id, ip_address, timestamp) VALUES(?, ?, ?, ?);", sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, hardwareId);
            preparedStatement.setString(3, ipAddress);
            preparedStatement.setInt(4, (int) Orion.getTime());

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

}
