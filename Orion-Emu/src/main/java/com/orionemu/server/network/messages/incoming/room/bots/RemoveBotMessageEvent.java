package com.orionemu.server.network.messages.incoming.room.bots;

import com.orionemu.server.game.players.components.types.inventory.InventoryBot;
import com.orionemu.server.game.rooms.objects.entities.types.BotEntity;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.bots.RoomBotDao;


public class RemoveBotMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        BotEntity entity = client.getPlayer().getEntity().getRoom().getEntities().getEntityByBotId(msg.readInt());

        if (entity == null) {
            return;
        }

        if (client.getPlayer().getId() != entity.getData().getOwnerId() && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        if (entity.getBotId() > 0) {
            InventoryBot bot = new InventoryBot(entity.getBotId(), entity.getData().getOwnerId(), entity.getData().getOwnerName(), entity.getUsername(), entity.getFigure(), entity.getGender(), entity.getMotto(), entity.getData().getBotType().toString());

            client.getPlayer().getBots().addBot(bot);

            RoomBotDao.setRoomId(0, entity.getBotId());
            client.send(new BotInventoryMessageComposer(client.getPlayer().getBots().getBots()));
        }

        entity.leaveRoom();
    }
}
