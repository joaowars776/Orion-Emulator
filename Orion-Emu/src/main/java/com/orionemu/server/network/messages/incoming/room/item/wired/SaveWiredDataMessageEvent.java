package com.orionemu.server.network.messages.incoming.room.item.wired;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.filter.FilterResult;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.WiredFloorItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.actions.WiredActionGiveReward;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.actions.WiredActionMatchToSnapshot;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionMatchSnapshot;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.wired.SaveWiredMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;


public class SaveWiredDataMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int virtualId = msg.readInt();

        if (ItemManager.getInstance().getItemIdByVirtualId(virtualId) == null) return;

        long itemId = ItemManager.getInstance().getItemIdByVirtualId(virtualId);

        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) return;

        Room room = client.getPlayer().getEntity().getRoom();

        boolean isOwner = client.getPlayer().getId() == room.getData().getOwnerId();
        boolean hasRights = room.getRights().hasRights(client.getPlayer().getId());

        if ((!isOwner && !hasRights) && !client.getPlayer().getPermissions().getRank().roomFullControl()) {
            return;
        }

        WiredFloorItem wiredItem = ((WiredFloorItem) room.getItems().getFloorItem(itemId));

        if (wiredItem == null) return;
        
        if (wiredItem instanceof WiredActionGiveReward && OrionSettings.roomWiredRewardMinimumRank > client.getPlayer().getData().getRank()) {
            client.send(new SaveWiredMessageComposer());
            return;
        }

        int paramCount = msg.readInt();

        for (int param = 0; param < paramCount; param++) {
            wiredItem.getWiredData().getParams().put(param, msg.readInt());
        }

        String filteredMessage = msg.readString();

        if (filteredMessage == null) {
            return;
        }

        if (!client.getPlayer().getPermissions().getRank().roomFilterBypass()) {
            FilterResult filterResult = RoomManager.getInstance().getFilter().filter(filteredMessage);

            if (filterResult.isBlocked()) {
                client.send(new AdvancedAlertMessageComposer(Locale.get("game.message.blocked").replace("%s", filterResult.getMessage())));
                return;
            } else if (filterResult.wasModified()) {
                filteredMessage = filterResult.getMessage();
            }
        }

        wiredItem.getWiredData().setText(filteredMessage);

        wiredItem.getWiredData().getSelectedIds().clear();

        int selectedItemCount = msg.readInt();

        for (int i = 0; i < selectedItemCount; i++) {
            long selectedItem = ItemManager.getInstance().getItemIdByVirtualId(msg.readInt());

            final RoomItemFloor floor = room.getItems().getFloorItem(selectedItem);

            if(floor == null) {
                continue;
            }

            floor.getWiredItems().add(wiredItem.getId());
            wiredItem.getWiredData().selectItem(selectedItem);
        }

        if (wiredItem instanceof WiredActionItem) {
            ((WiredActionItem) wiredItem).getWiredData().setDelay(msg.readInt());
        }

        wiredItem.getWiredData().setSelectionType(msg.readInt());
        wiredItem.save();

        if (wiredItem instanceof WiredActionMatchToSnapshot ||
                wiredItem instanceof WiredConditionMatchSnapshot) {
            wiredItem.refreshSnapshots();
        }

        client.send(new SaveWiredMessageComposer());
        wiredItem.onDataRefresh();
        wiredItem.onDataChange();
    }
}
