package com.orionemu.server.game.catalog.purchase;

import com.orionemu.api.game.players.data.components.inventory.PlayerItem;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.achievements.types.AchievementType;
import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.catalog.types.CatalogBundledItem;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.game.catalog.types.CatalogPage;
import com.orionemu.server.game.catalog.types.CatalogPageType;
import com.orionemu.server.game.catalog.types.gifts.GiftData;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.items.music.MusicData;
import com.orionemu.server.game.items.rares.LimitedEditionItemData;
import com.orionemu.server.game.items.types.ItemDefinition;
import com.orionemu.server.game.pets.data.PetData;
import com.orionemu.server.game.pets.data.StaticPetProperties;
import com.orionemu.server.game.players.components.types.inventory.InventoryBot;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.bundles.RoomBundleManager;
import com.orionemu.server.game.rooms.bundles.types.RoomBundle;
import com.orionemu.server.game.rooms.bundles.types.RoomBundleItem;
import com.orionemu.server.network.NetworkManager;
import com.orionemu.server.network.messages.outgoing.catalog.BoughtItemMessageComposer;
import com.orionemu.server.network.messages.outgoing.catalog.GiftUserNotFoundMessageComposer;
import com.orionemu.server.network.messages.outgoing.catalog.UnseenItemsMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.*;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.settings.EnforceRoomCategoryMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.PetInventoryMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.bots.PlayerBotDao;
import com.orionemu.server.storage.queries.catalog.CatalogDao;
import com.orionemu.server.storage.queries.items.ItemDao;
import com.orionemu.server.storage.queries.items.LimitedEditionDao;
import com.orionemu.server.storage.queries.items.TeleporterDao;
import com.orionemu.server.storage.queries.pets.PetDao;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queries.rooms.RoomItemDao;
import com.orionemu.server.utilities.JsonUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class OldCatalogPurchaseHandler {
    private final Logger log = Logger.getLogger(OldCatalogPurchaseHandler.class.getName());
    private ExecutorService executorService;

    public OldCatalogPurchaseHandler() {
    }

    public void purchaseItem(Session client, int pageId, int itemId, String data, int amount, GiftData giftData) {
        if (OrionSettings.asyncCatalogPurchase) {
            if (this.executorService == null) {
                this.executorService = Executors.newFixedThreadPool(2);
            }

            this.executorService.submit(() -> this.handle(client, pageId, itemId, data, amount, giftData));
        } else {
            this.handle(client, pageId, itemId, data, amount, giftData);
        }
    }

    /**
     * Handle the catalog purchase
     *
     * @param client   The session assigned to the player who's purchasing the item
     * @param pageId   The catalog page the purchased item is on
     * @param itemId   The ID of the item that was purchased
     * @param data     The data sent by the client
     * @param amount   The amount of items we're purchasing
     * @param giftData Gift data (if-any)
     */
    private void handle(Session client, int pageId, int itemId, String data, int amount, GiftData giftData) {
        if (client == null || client.getPlayer() == null) return;

        // TODO: redo all of this, it sucks so bad ;P, maybe add purchase handlers for each item or some crap
        if (amount > 100) {
            client.send(new AlertMessageComposer(Locale.get("catalog.error.toomany")));
            return;
        }

        final int playerIdToDeliver = giftData == null ? -1 : PlayerDao.getIdByUsername(giftData.getReceiver());

        if (giftData != null) {
            if (playerIdToDeliver == 0) {
                client.send(new GiftUserNotFoundMessageComposer());
                return;
            } else {
                client.getPlayer().getAchievements().progressAchievement(AchievementType.GIFT_GIVER, 1);
            }
        }

        Set<PlayerItem> unseenItems = Sets.newHashSet();
        CatalogPage page = CatalogManager.getInstance().getPage(pageId);

        try {
            CatalogItem item;

            try {
                if (page == null) {
                    page = CatalogManager.getInstance().getCatalogPageByCatalogItemId(itemId);

                    if (page.getMinRank() > client.getPlayer().getData().getRank() || !page.getItems().containsKey(itemId)) {
                        //y u do dis.
                        client.disconnect();
                        return;
                    }

                }

                item = page.getItems().get(itemId);
            } catch (Exception e) {
                return;
            }

            if (item == null) {
                return;
            }

            if (giftData != null) {
                try {
                    final ItemDefinition itemDefinition = ItemManager.getInstance().getDefinition(item.getItems().get(0).getItemId());

                    if (itemDefinition == null) {
                        return;
                    }

                    if (itemDefinition != null && !itemDefinition.canGift()) {
                        return;
                    }
                } catch (Exception e) {
                    return;
                }

                if (client.getPlayer().getLastGift() != 0 && !client.getPlayer().getPermissions().getRank().floodBypass()) {
                    if (((int) Orion.getTime() - client.getPlayer().getLastGift()) < OrionSettings.playerGiftCooldown) {
                        client.send(new AdvancedAlertMessageComposer(Locale.get("catalog.error.gifttoofast")));
                        client.send(new BoughtItemMessageComposer(BoughtItemMessageComposer.PurchaseType.BADGE));
                        return;
                    }
                }

                client.getPlayer().setLastGift((int) Orion.getTime());
            }

            if (item.isBadgeOnly()) {

                if (item.hasBadge() && client.getPlayer().getInventory().hasBadge(item.getBadgeId())) {
                    client.send(new PurchaseErrorMessageComposer(1));
                    client.send(new BoughtItemMessageComposer(BoughtItemMessageComposer.PurchaseType.BADGE));
                    return;
                }
            }

            if (amount > 1 && !item.allowOffer()) {
                client.send(new AlertMessageComposer(Locale.get("catalog.error.nooffer")));

                return;
            }

            int totalCostCredits;
            int totalCostPoints;
            int totalCostActivityPoints;

            if (item.getLimitedSells() >= item.getLimitedTotal() && item.getLimitedTotal() != 0) {
//                client.send(new LimitedEditionSoldOutMessageComposer());
                // TODO: Fix this.
                return;
            }

            try {
                if (CatalogManager.getInstance().getPage(item.getPageId()).getMinRank() > client.getPlayer().getData().getRank()) {
                    client.disconnect();
                    return;
                }
            } catch (Exception ignored) {
                // Invalid page id..
                return;
            }

            if (item.allowOffer()) {
                totalCostCredits = amount > 1 ? ((item.getCostCredits() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostCredits())) : item.getCostCredits();
                totalCostPoints = amount > 1 ? ((item.getCostOther() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostOther())) : item.getCostOther();
                totalCostActivityPoints = amount > 1 ? ((item.getCostActivityPoints() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostActivityPoints())) : item.getCostActivityPoints();
            } else {
                totalCostCredits = item.getCostCredits();
                totalCostPoints = item.getCostOther();
                totalCostActivityPoints = item.getCostActivityPoints();
            }

            if ((!OrionSettings.playerInfiniteBalance && (client.getPlayer().getData().getCredits() < totalCostCredits || client.getPlayer().getData().getActivityPoints() < totalCostActivityPoints)) || client.getPlayer().getData().getVipPoints() < totalCostPoints) {
                client.getLogger().warn("Player with ID: " + client.getPlayer().getId() + " tried to purchase item with ID: " + item.getId() + " with the incorrect amount of credits or points.");
                client.send(new AlertMessageComposer(Locale.get("catalog.error.notenough")));
                return;
            }

            if(item.getLimitedTotal() > 0) {
                item.increaseLimitedSells(amount);
                CatalogDao.updateLimitSellsForItem(item.getId(), amount);
            }

            if (!OrionSettings.playerInfiniteBalance) {
                client.getPlayer().getData().decreaseCredits(totalCostCredits);
                client.getPlayer().getData().decreaseActivityPoints(totalCostActivityPoints);
            }

            client.getPlayer().getData().decreasePoints(totalCostPoints);

            client.getPlayer().sendBalance();
            client.getPlayer().getData().save();

            if (page != null) {
                if (page.getType() == CatalogPageType.BUNDLE) {
                    RoomBundle roomBundle = RoomBundleManager.getInstance().getBundle(page.getExtraData());

                    try {

                        int roomId = RoomManager.getInstance().createRoom(roomBundle.getConfig().getRoomName().replace("%username%", client.getPlayer().getData().getUsername()), "", roomBundle.getRoomModelData(), 0, 20, 0, client, roomBundle.getConfig().getThicknessWall(), roomBundle.getConfig().getThicknessFloor(), roomBundle.getConfig().getDecorations(), roomBundle.getConfig().isHideWalls());

                        for (RoomBundleItem roomBundleItem : roomBundle.getRoomBundleData()) {
                            long newItemId = ItemDao.createItem(client.getPlayer().getId(), roomBundleItem.getItemId(), roomBundleItem.getExtraData());

                            if (roomBundleItem.getWallPosition() == null) {
                                RoomItemDao.placeFloorItem(roomId, roomBundleItem.getX(), roomBundleItem.getY(), roomBundleItem.getZ(), roomBundleItem.getRotation(), roomBundleItem.getExtraData(), newItemId);
                            } else {

                                RoomItemDao.placeWallItem(roomId, roomBundleItem.getWallPosition(), roomBundleItem.getExtraData(), newItemId);
                            }
                        }

                        client.send(new RoomForwardMessageComposer(roomId));
                        client.send(new EnforceRoomCategoryMessageComposer());
                        client.send(new BoughtItemMessageComposer(BoughtItemMessageComposer.PurchaseType.BADGE));
                        client.getPlayer().setLastRoomCreated((int) Orion.getTime());

                    } catch (Exception e) {
                        client.send(new MotdNotificationMessageComposer("Invalid room bundle data, please contact an administrator."));
                        client.send(new BoughtItemMessageComposer(BoughtItemMessageComposer.PurchaseType.BADGE));
                        return;
                    }
                    return;
                }
            }

            if (item.isBadgeOnly()) {

                if (item.hasBadge() && !client.getPlayer().getInventory().hasBadge(item.getBadgeId())) {
                    client.getPlayer().getInventory().addBadge(item.getBadgeId(), true);
                }

                client.send(new BoughtItemMessageComposer(BoughtItemMessageComposer.PurchaseType.BADGE));
                return;
            }

            for (CatalogBundledItem bundledItem : item.getItems()) {
                ItemDefinition def = ItemManager.getInstance().getDefinition(bundledItem.getItemId());

                if (def == null) {
                    continue;
                }

                client.send(new BoughtItemMessageComposer(item, def));

                if (def.getItemName().equals("DEAL_HC_1")) {
                    // TODO: HC buying
                    throw new Exception("HC purchasing is not implemented");
                }

                String extraData = "";

                boolean isTeleport = false;

                if (def.getInteraction().equals("trophy")) {
                    extraData +=
                            client.getPlayer().getData().getUsername() + Character.toChars(9)[0] + DateTime.now().getDayOfMonth() + "-" + DateTime.now().getMonthOfYear() + "-" + DateTime.now().getYear() + Character.toChars(9)[0] + data;
                } else if (def.isTeleporter()) {
                    amount = amount * 2;
                    isTeleport = true;
                } else if (item.getDisplayName().startsWith("a0 pet")) {
                    String petRace = item.getDisplayName().replace("a0 pet", "");
                    String[] petData = data.split("\n"); // [0:name, 1:race, 2:colour]

                    if (petData.length != 3) {
                        throw new Exception("Invalid pet data length: " + petData.length);
                    }

                    int petId = PetDao.createPet(client.getPlayer().getId(), petData[0], Integer.parseInt(petRace), Integer.parseInt(petData[1]), petData[2]);

                    client.getPlayer().getAchievements().progressAchievement(AchievementType.PET_LOVER, 1);
                    client.getPlayer().getPets().addPet(new PetData(petId, petData[0], 0, StaticPetProperties.DEFAULT_LEVEL, StaticPetProperties.DEFAULT_HAPPINESS, StaticPetProperties.DEFAULT_EXPERIENCE, StaticPetProperties.DEFAULT_ENERGY, client.getPlayer().getId(), client.getPlayer().getData().getUsername(), petData[2], Integer.parseInt(petData[1]), Integer.parseInt(petRace)));
                    client.send(new PetInventoryMessageComposer(client.getPlayer().getPets().getPets()));

                    client.send(new UnseenItemsMessageComposer(new HashMap<Integer, List<Integer>>() {{
                        put(3, Lists.newArrayList(petId));
                    }}));
                    return;
                } else if (def.getInteraction().equals("postit")) {
                    amount = 20; // we want 20 stickies

                    extraData = "";
                } else if (def.isRoomDecor()) {
                    if (data.isEmpty()) {
                        extraData += "0";
                    } else {
                        extraData += data.replace(",", ".");
                    }
                } else if (def.getInteraction().equals("group_item") || def.getInteraction().equals("group_gate")) {
                    if (data.isEmpty() || !StringUtils.isNumeric(data)) return;

                    if (!client.getPlayer().getGroups().contains(new Integer(data))) {
                        return;
                    }

                    extraData = data;
                } else if (def.getType().equals("r")) {
                    // It's a bot!
                    String botName = "New Bot";
                    String botFigure = item.getPresetData();
                    String botGender = "m";
                    String botMotto = "Beeb beeb boop beep!";
                    String type = "generic";

                    switch (item.getDisplayName()) {
                        case "bot_bartender":
                            type = "waiter";
                            break;

                        case "bot_spy":
                            type = "spy";
                            break;
                    }

                    int botId = PlayerBotDao.createBot(client.getPlayer().getId(), botName, botFigure, botGender, botMotto, type);
                    client.getPlayer().getBots().addBot(new InventoryBot(botId, client.getPlayer().getId(), client.getPlayer().getData().getUsername(), botName, botFigure, botGender, botMotto, type));
                    client.send(new BotInventoryMessageComposer(client.getPlayer().getBots().getBots()));

                    client.send(new UnseenItemsMessageComposer(new HashMap<Integer, List<Integer>>() {{
                        put(5, Lists.newArrayList(botId));
                    }}));
                    return;
                } else if (def.getInteraction().equals("badge_display")) {
                    if (client.getPlayer().getInventory().getBadges().get(data) == null) {
                        return;
                    }

                    extraData = data + "~" + client.getPlayer().getData().getUsername() + "~" + DateTime.now().getDayOfMonth() + "-" + DateTime.now().getMonthOfYear() + "-" + DateTime.now().getYear();
                } else if (def.getInteraction().equals("group_forum")) {
                    if (data.isEmpty() || !StringUtils.isNumeric(data))
                        return;

                    if (!client.getPlayer().getGroups().contains(new Integer(data))) {
                        return;
                    }

                    int groupId = Integer.parseInt(data);
                    Group group = GroupManager.getInstance().get(groupId);

                    if (!group.getData().hasForum() && group.getData().getOwnerId() == client.getPlayer().getId()) {
                        group.getData().setHasForum(true);
                        group.getData().save();

                        group.initializeForum();

                        Map<String, String> notificationParams = Maps.newHashMap();

                        notificationParams.put("groupId", groupId + "");
                        notificationParams.put("groupName", group.getData().getTitle());

                        client.send(new NotificationMessageComposer("forums.delivered", notificationParams));

                    }

                    extraData = "" + groupId;
                } else if (def.isSong()) {
                    final String songName = item.getPresetData();

                    if (songName != null && !songName.isEmpty()) {
                        MusicData musicData = ItemManager.getInstance().getMusicDataByName(songName);

                        if (musicData != null) {
                            extraData = String.format("%s\n%s\n%s\n%s\n%s\n%s",
                                    client.getPlayer().getData().getUsername(),
                                    Calendar.getInstance().get(Calendar.YEAR),
                                    Calendar.getInstance().get(Calendar.MONTH),
                                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                                    musicData.getLengthSeconds(),
                                    musicData.getTitle());
                        }
                    }
                }

                long[] teleportIds = null;

                if (isTeleport) {
                    teleportIds = new long[amount];
                }

                List<CatalogPurchase> purchases = new ArrayList<>();

                if (giftData != null) {
                    giftData.setExtraData(extraData);

                    ItemDefinition itemDefinition = ItemManager.getInstance().getBySpriteId(giftData.getSpriteId());

                    purchases.add(new CatalogPurchase(playerIdToDeliver, itemDefinition == null ? CatalogManager.getInstance().getGiftBoxesOld().get(0) : itemDefinition.getId(), "GIFT::##" + JsonUtil.getInstance().toJson(giftData)));
                } else {
                    for (int purchaseCount = 0; purchaseCount < amount; purchaseCount++) {
                        for (int itemCount = 0; itemCount != bundledItem.getAmount(); itemCount++) {
                            purchases.add(new CatalogPurchase(client.getPlayer().getId(), bundledItem.getItemId(), extraData));
                        }
                    }
                }

                List<Long> newItems = ItemDao.createItems(purchases);

                for (long newItem : newItems) {
                    if (item.getLimitedTotal() > 0) {
                        LimitedEditionDao.save(new LimitedEditionItemData(newItem, item.getLimitedSells(), item.getLimitedTotal()));
                    }

                    if (giftData == null)
                        unseenItems.add(client.getPlayer().getInventory().add(newItem, bundledItem.getItemId(), extraData, giftData, item.getLimitedTotal() > 0 ? new LimitedEditionItemData(bundledItem.getItemId(), item.getLimitedSells(), item.getLimitedTotal()) : null));

                    if (isTeleport)
                        teleportIds[newItems.indexOf(newItem)] = newItem;
                }

                if (isTeleport) {
                    long lastId = 0;

                    for (int i = 0; i < teleportIds.length; i++) {
                        if (lastId == 0) {
                            lastId = teleportIds[i];
                        }

                        if (i % 2 == 0 && lastId != 0) {
                            lastId = teleportIds[i];
                            continue;
                        }

                        TeleporterDao.savePair(teleportIds[i], lastId);
                    }
                }

                if (giftData != null) {
                    this.deliverGift(playerIdToDeliver, giftData, newItems, client.getPlayer().getData().getUsername());
                } else {
                    if (item.hasBadge()) {
                        client.getPlayer().getInventory().addBadge(item.getBadgeId(), true);
                    }

                    client.send(new UnseenItemsMessageComposer(unseenItems));
                    client.send(new UpdateInventoryMessageComposer());

                    if (OrionSettings.logCatalogPurchases) {
                        CatalogDao.saveRecentPurchase(client.getPlayer().getId(), item.getId(), amount, extraData);
                    }

                    client.getPlayer().getRecentPurchases().add(item);
                }
            }
        } catch (Exception e) {
            log.error("Error while buying catalog item", e);
        } finally {
            // Clean up the purchase - even if there was an exception!!
            unseenItems.clear();
        }
    }

    /**
     * Deliver the gift
     *
     * @param playerId The ID of the player to deliver the item to
     * @param giftData The data of the gift
     * @param newItems List of items to deliver
     */
    private void deliverGift(int playerId, GiftData giftData, List<Long> newItems, String senderUsername) {
        Session client = NetworkManager.getInstance().getSessions().getByPlayerId(playerId);

        if (client != null) {
            Set<PlayerItem> unseenItems = Sets.newHashSet();

            if (client.getPlayer() != null) {
                if (client.getPlayer().getInventory() != null) {
                    for (long newItem : newItems) {
                        unseenItems.add(client.getPlayer().getInventory().add(newItem, ItemManager.getInstance().getBySpriteId(giftData.getSpriteId()).getId(), "GIFT::##" + JsonUtil.getInstance().toJson(giftData), giftData, null));
                    }
                }

                if (client.getPlayer().getAchievements() != null) {
                    client.getPlayer().getAchievements().progressAchievement(AchievementType.GIFT_RECEIVER, 1);
                }
            }

            client.send(new UnseenItemsMessageComposer(unseenItems));
            client.send(new UpdateInventoryMessageComposer());
            client.send(new NotificationMessageComposer("gift_received", Locale.getOrDefault("notification.gift_received", "You have just received a gift from %username%!").replace("%username%", senderUsername)));

        }
    }

}