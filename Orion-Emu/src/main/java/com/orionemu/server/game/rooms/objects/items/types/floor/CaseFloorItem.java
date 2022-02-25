package com.orionemu.server.game.rooms.objects.items.types.floor;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.crackables.ExplodeCrackable;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.tasks.OrionTask;
import com.orionemu.server.tasks.OrionThreadManager;

import java.sql.SQLException;


public class CaseFloorItem extends RoomItemFloor {

    public CaseFloorItem(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) throws Exception {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }

    @Override
    public boolean onInteract(RoomEntity entity, int state, boolean isWiredTrigger) {

        if (this.getExtraData().length() == 0)
            this.setExtraData("0");
        this.setExtraData(Integer.valueOf(this.getExtraData()) + 1 + "");
        this.sendUpdate();
        this.saveData();

        PlayerEntity entity1 = (PlayerEntity) entity;

        if (Integer.valueOf(this.getExtraData()) == (ItemManager.getInstance().getCrackableCount(this.getItemId()))) {
            OrionThreadManager.getInstance().executeOnceWithDelay(this.explodeCrackable(entity.getRoom(), this, entity1.getPlayer().getSession()), 1500);
        }

        return true;
    }

    private OrionTask explodeCrackable(Room room, RoomItemFloor floor, Session session) {
        try {
            return new ExplodeCrackable(room, floor, session);
        } catch (SQLException ex) {
            Orion.getServer().getLogger().info(ex);
        }
        return null;
    }

    @Override
    public void onPlaced() {

        this.setExtraData("0");
    }

    public void serializeCaseFloorItem(IComposer msg) {
        if (this.getExtraData().length() == 0)
            this.setExtraData("0");

        msg.writeInt(0);
        msg.writeInt(7);
        msg.writeString(ItemManager.getInstance().calculateCrackState(Integer.valueOf(this.getExtraData()), ItemManager.getInstance().getCrackableCount(this.getItemId())) + "");
        msg.writeInt(Integer.valueOf(this.getExtraData()));
        msg.writeInt(ItemManager.getInstance().getCrackableCount(this.getItemId()));
    }
}
