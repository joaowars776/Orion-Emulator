package com.orionemu.server.network.messages.incoming.room.bots;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.players.components.types.inventory.InventoryBot;
import com.orionemu.server.game.rooms.objects.entities.types.BotEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.mapping.RoomTile;
import com.orionemu.server.game.rooms.types.tiles.RoomTileState;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.AvatarsMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.bots.RoomBotDao;


public class PlaceBotMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        int botId = msg.readInt();
        int x = msg.readInt();
        int y = msg.readInt();

        Room room = client.getPlayer().getEntity().getRoom();
        InventoryBot bot = (InventoryBot) client.getPlayer().getBots().getBot(botId);

        if (room == null || bot == null || (!room.getRights().hasRights(client.getPlayer().getId()) && !client.getPlayer().getPermissions().getRank().roomFullControl())) {
            return;
        }

        if (room.getEntities().getBotEntities().size() >= OrionSettings.roomMaxBots) {
            client.send(new AlertMessageComposer(String.format(Locale.getOrDefault("orion.game.bots.toomany", "You can only have %s bots per room!"), OrionSettings.roomMaxBots)));
            return;
        }

        double height = room.getMapping().getTile(x, y).getWalkHeight();
        final Position position = new Position(x, y, height);

        final RoomTile tile = room.getMapping().getTile(x, y);

        if (tile == null || !room.getMapping().isValidPosition(position) || room.getModel().getSquareState()[x][y] != RoomTileState.VALID) {
            return;
        }

        if (tile.getEntities().size() >= 1) {
            return;
        }

        RoomBotDao.savePosition(x, y, height, botId, room.getId());

        BotEntity botEntity = room.getBots().addBot(bot, x, y, height);
        client.getPlayer().getBots().remove(botId);

        tile.getEntities().add(botEntity);

        room.getEntities().broadcastMessage(new AvatarsMessageComposer(botEntity));
        client.send(new BotInventoryMessageComposer(client.getPlayer().getBots().getBots()));

        for (RoomItemFloor floorItem : room.getItems().getItemsOnSquare(x, y)) {
            floorItem.onEntityStepOn(botEntity);
        }

        botEntity.getAI().onAddedToRoom();
    }
}
