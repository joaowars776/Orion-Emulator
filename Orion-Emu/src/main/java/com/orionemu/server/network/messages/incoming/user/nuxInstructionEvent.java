package com.orionemu.server.network.messages.incoming.user;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;


public class nuxInstructionEvent implements Event {
    Connection sqlConnection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    public static String[] NuxInstruct = new String[]
    {
        "helpBubble/add/BOTTOM_BAR_INVENTORY/nux.bot.info.inventory.1",
                "helpBubble/add/BOTTOM_BAR_NAVIGATOR/nux.bot.info.navigator.1",
                "helpBubble/add/chat_input/nux.bot.info.chat.1",
                "nux/lobbyoffer/show"
    };

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {

        if(client.getPlayer() == null || client.getPlayer().getData() == null){
            return;
        }

        if (client.getPlayer().getData().getUsername() == "Spreed"){

            try {
                sqlConnection = SqlHelper.getConnection();

                SqlHelper.prepare("UPDATE `players` SET `nux` = 'false' WHERE username = 'Spreed' LIMIT 1", sqlConnection);
                preparedStatement.executeQuery();
            } catch (SQLException e){
                //TODO: SqlException
            }
        }

    }
}
