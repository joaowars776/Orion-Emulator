package com.orionemu.server.network.messages.incoming.user.camera;

import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.api.game.players.data.components.inventory.PlayerItem;
import com.orionemu.server.game.players.components.types.inventory.InventoryItem;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.catalog.UnseenItemsMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.items.ItemDao;
import com.google.common.collect.Sets;

public class TakePhotoMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String code = msg.readString();
        final String itemExtraData = "{\"t\":" + System.currentTimeMillis() + ",\"u\":\"" + code + "\",\"n\":\"" + client.getPlayer().getData().getUsername() + "\",\"m\":\"\",\"s\":" + client.getPlayer().getId() + ",\"w\":\"" + OrionSettings.cameraPhotoUrl.replace("%photoId%", code) + "\"}";

        long itemId = ItemDao.createItem(client.getPlayer().getId(), OrionSettings.cameraPhotoItemId, itemExtraData);
        final PlayerItem playerItem = new InventoryItem(itemId, OrionSettings.cameraPhotoItemId, itemExtraData);

        client.getPlayer().getInventory().addItem(playerItem);

        client.send(new NotificationMessageComposer("generic", Locale.getOrDefault("camera.photoTaken", "You successfully took a photo!")));
        client.send(new UpdateInventoryMessageComposer());

        client.send(new UnseenItemsMessageComposer(Sets.newHashSet(playerItem)));

        client.getPlayer().getAchievements().progressAchievement(AchievementType.CAMERA_PHOTO, 1);
    }
}
