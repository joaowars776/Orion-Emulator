package com.orionemu.server.game.rooms.crackables;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.items.types.ItemDefinition;
import com.orionemu.server.game.rooms.objects.items.RoomItemFactory;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.mapping.RoomTile;
import com.orionemu.server.network.messages.outgoing.room.engine.UpdateStackMapMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.SendFloorItemMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.items.CrackablesDao;
import com.orionemu.server.tasks.OrionTask;

import java.sql.SQLException;

/**
 * Created by SpreedBlood on 2017-08-08.
 */
public class ExplodeCrackable implements OrionTask {
    private Room room;
    private RoomItemFloor itemFloor;
    private Session session;

    public ExplodeCrackable(Room room, RoomItemFloor itemFloor, Session session) throws SQLException {
        this.room = room;
        this.itemFloor = itemFloor;
        this.session = session;
    }

    @Override
    public void run() {
        if (itemFloor == null) {
            return;
        }
        int x = itemFloor.getPosition().getX();
        int y = itemFloor.getPosition().getY();
        int rot = itemFloor.getRotation();
        int virtualId = itemFloor.getVirtualId();

        ItemDefinition reward = ItemManager.getInstance().getCrackableReward(itemFloor.getItemId());
        double height = reward.getHeight();

        this.room.getItems().removeItem(itemFloor, session, false, true);

        RoomItemFloor newItem = RoomItemFactory.createFloor(itemFloor.getVirtualId(), reward.getId(), room, session.getPlayer().getId(), session.getPlayer().getData().getUsername(), x, y, height, rot, "", null);
        this.room.getItems().addFloorItem(virtualId, reward.getId(), room, session.getPlayer().getId(), session.getPlayer().getData().getUsername(), x, y, rot, reward.getHeight(), "", null);
        CrackablesDao.createItem(this.room.getId(), session.getPlayer().getData().getId(), reward.getId(), "", x, y);
        room.getEntities().broadcastMessage(new SendFloorItemMessageComposer(newItem));
        this.updateFloorStatus(reward, x, y);
    }

    private void updateFloorStatus(ItemDefinition definition, int x, int y) {
        RoomTile tileInstance = this.room.getMapping().getTile(x, y);

        this.room.getEntities().broadcastMessage(new UpdateStackMapMessageComposer(tileInstance));
    }
}
