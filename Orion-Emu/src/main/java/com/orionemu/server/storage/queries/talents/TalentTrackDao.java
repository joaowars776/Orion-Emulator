package com.orionemu.server.storage.queries.talents;

import com.orionemu.server.game.talents.TalentTrackLevel;
import com.orionemu.server.game.talents.TalentTrackSubLevel;
import com.orionemu.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by admin on 2017-06-30.
 */
public class TalentTrackDao {
    public static Map<Integer, TalentTrackSubLevel> getSubLevels(Map<Integer, TalentTrackSubLevel> subLevelMap, int level) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `sub_level`,`badge_code`,`required_progress` FROM `talents_sub_levels` WHERE `talent_level` = ?", sqlConnection);
            preparedStatement.setInt(1, level);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                subLevelMap.put(resultSet.getInt("sub_level"), new TalentTrackSubLevel(resultSet.getInt("sub_level"), resultSet.getString("badge_code"), resultSet.getInt("required_progress")));
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }

    public static Map<Integer, TalentTrackLevel> getLevels(Map<Integer, TalentTrackLevel> subLevelMap) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `type`,`level`,`data_actions`,`data_gifts` FROM `talents`", sqlConnection);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                subLevelMap.put(resultSet.getInt("level"), new TalentTrackLevel(resultSet.getString("type"), resultSet.getInt("level"), resultSet.getString("data_actions"), resultSet.getString("data_gifts")));
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }
}
