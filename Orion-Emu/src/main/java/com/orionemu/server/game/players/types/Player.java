package com.orionemu.server.game.players.types;

import com.orionemu.api.game.players.BasePlayer;
import com.orionemu.api.game.players.data.components.PlayerInventory;
import com.orionemu.api.networking.sessions.BaseSession;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.catalog.types.CatalogItem;
import com.orionemu.server.game.guides.GuideManager;
import com.orionemu.server.game.guides.types.HelpRequest;
import com.orionemu.server.game.guides.types.HelperSession;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.components.*;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.game.quests.QuestManager;
import com.orionemu.server.game.quests.types.Quest;
import com.orionemu.server.game.quests.types.QuestType;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.game.rooms.types.components.types.ChatMessageColour;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.AdvancedAlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.MotdNotificationMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.messages.outgoing.quests.QuestStartedMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.UpdateInfoMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.HotelViewMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.purse.CurrenciesMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.purse.SendCreditsMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.catalog.CatalogDao;
import com.orionemu.server.storage.queries.groups.GroupDao;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queue.types.PlayerDataStorageQueue;
import com.orionemu.server.utilities.collections.ConcurrentHashSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Player implements BasePlayer {

    private int id;

    private PlayerSettings settings;
    private PlayerData data;
    private PlayerStatistics stats;

    private PlayerEntity entity;
    private Session session;

    private HelperSession helperSession;

    private final PermissionComponent permissions;
    private final InventoryComponent inventory;
    private final SubscriptionComponent subscription;
    private final MessengerComponent messenger;
    private final RelationshipComponent relationships;
    private final InventoryBotComponent bots;
    private final PetComponent pets;
    private final QuestComponent quests;
    private final AchievementComponent achievements;
    private final NavigatorComponent navigator;

    private List<Integer> rooms = new ArrayList<>();
    private List<Integer> enteredRooms = new ArrayList<>();

    private List<Integer> groups = new ArrayList<>();

    private List<Integer> ignoredPlayers = new ArrayList<>();
    private long roomLastMessageTime = 0;
    private double roomFloodTime = 0;
    private int lastForumPost = 0;

    private long lastRoomRequest = 0;
    private long lastBadgeUpdate = 0;
    private int lastFigureUpdate = 0;

    private int roomFloodFlag = 0;
    private long messengerLastMessageTime = 0;

    private double messengerFloodTime = 0;
    private int messengerFloodFlag = 0;

    private boolean usernameConfirmed = false;

    private long teleportId = 0;
    private int teleportRoomId = 0;
    private String lastMessage = "";

    private int lastVoucherRedeemAttempt = 0;
    private int voucherRedeemAttempts = 0;

    private int notifCooldown = 0;
    private int lastRoomId;

    private int lastGift = 0;

    private int lastRoomCreated = 0;
    public boolean cancelPageOpen = false;

    private boolean isDeletingGroup = false;

    private long deletingGroupAttempt = 0;

    private boolean bypassRoomAuth;

    public boolean isDisposed = false;

    private long lastReward = 0;

    private boolean invisible = false;

    private int lastTradePlayer = 0;
    private long lastTradeTime = 0;
    private int lastTradeFlag = 0;

    private int lastTradeFlood = 0;

    private long lastPhotoTaken = 0;

    private String ssoTicket;

    public int lastBannedListRequest = 0;

    private Set<CatalogItem> recentPurchases;

    private Set<String> eventLogCategories = Sets.newConcurrentHashSet();

    private ChatMessageColour chatMessageColour = null;

    private HelpRequest helpRequest = null;

    public Player(ResultSet data, boolean isFallback) throws SQLException {
        this.id = data.getInt("playerId");

        if (isFallback) {
            this.settings = PlayerDao.getSettingsById(this.id);
            this.stats = PlayerDao.getStatisticsById(this.id);
        } else {
            this.settings = new PlayerSettings(data, true);
            this.stats = new PlayerStatistics(data, true);
        }

        if (PlayerDataStorageQueue.getInstance().isPlayerSaving(this.id)) {
            this.data = PlayerDataStorageQueue.getInstance().getPlayerData(this.id);
        } else {
            this.data = new PlayerData(data);
        }

        this.permissions = new PermissionComponent(this);
        this.inventory = new InventoryComponent(this);
        this.messenger = new MessengerComponent(this);
        this.subscription = new SubscriptionComponent(this);
        this.relationships = new RelationshipComponent(this);
        this.bots = new InventoryBotComponent(this);
        this.pets = new PetComponent(this);
        this.quests = new QuestComponent(this);
        this.achievements = new AchievementComponent(this);
        this.navigator = new NavigatorComponent(this);

        this.groups = GroupDao.getIdsByPlayerId(this.id);

        this.entity = null;
        this.lastReward = Orion.getTime();
    }

    @Override
    public void dispose() {
        if (this.getEntity() != null) {
            try {
                this.getEntity().leaveRoom(true, false, false);
            } catch (Exception e) {
                // Player failed to leave room
                this.getSession().getLogger().error("Error while disposing entity when player disconnects", e);
            }
        }

        if (this.helperSession != null) {
            GuideManager.getInstance().finishPlayerDuty(this.helperSession);
            this.helperSession = null;
        }

        if (PlayerDataStorageQueue.getInstance().isQueued(this.getData())) {
            this.data.saveNow();
            PlayerDataStorageQueue.getInstance().unqueue(this.getData());
        }

        this.getPets().dispose();
        this.getBots().dispose();
        this.getInventory().dispose();
        this.getMessenger().dispose();
        this.getRelationships().dispose();
        this.getQuests().dispose();
        this.getNavigator().dispose();

        PlayerManager.getInstance().getSsoTicketToPlayerId().remove(this.ssoTicket);

        this.session.getLogger().debug(this.getData().getUsername() + " logged out");

        PlayerDao.updatePlayerStatus(this, false, false);

        this.rooms.clear();
        this.rooms = null;

        this.groups.clear();
        this.groups = null;

        this.ignoredPlayers.clear();
        this.ignoredPlayers = null;

        this.enteredRooms.clear();
        this.enteredRooms = null;

        this.eventLogCategories.clear();
        this.eventLogCategories = null;

        if (this.recentPurchases != null) {
            this.recentPurchases.clear();
            this.recentPurchases = null;
        }

        this.settings = null;
        this.data = null;

        this.isDisposed = true;
    }

    @Override
    public void sendBalance() {
        session.send(composeCurrenciesBalance());
        session.send(composeCreditBalance());
    }

    @Override
    public void sendNotif(String title, String message) {
        session.send(new AdvancedAlertMessageComposer(title, message));
    }

    @Override
    public void sendMotd(String message) {
        session.send(new MotdNotificationMessageComposer(message));
    }

    @Override
    public MessageComposer composeCreditBalance() {
        return new SendCreditsMessageComposer(OrionSettings.playerInfiniteBalance ? com.orionemu.server.game.players.types.Player.INFINITE_BALANCE : session.getPlayer().getData().getCredits());
    }

    @Override
    public MessageComposer composeCurrenciesBalance() {
        Map<Integer, Integer> currencies = new HashMap<>();

        currencies.put(0, OrionSettings.playerInfiniteBalance ? com.orionemu.server.game.players.types.Player.INFINITE_BALANCE : getData().getActivityPoints());
        currencies.put(105, getData().getVipPoints());
        currencies.put(5, getData().getVipPoints());

        return new CurrenciesMessageComposer(currencies);
    }

    @Override
    public void loadRoom(int id, String password) {

        if (entity != null && entity.getRoom() != null) {
            entity.leaveRoom(true, false, false);
            setEntity(null);
        }

        Room room = RoomManager.getInstance().get(id);

        if (room == null) {
            session.send(new HotelViewMessageComposer());
            return;
        }

        if (room.getEntities() == null) {
            return;
        }

        if (room.getEntities().getEntityByPlayerId(this.id) != null) {
            room.getEntities().getEntityByPlayerId(this.id).leaveRoom(true, false, false);
        }

        PlayerEntity playerEntity = room.getEntities().createEntity(this);
        setEntity(playerEntity);

        if (!playerEntity.joinRoom(room, password)) {
            setEntity(null);
        }

        if (this.getData().getQuestId() != 0) {
            Quest quest = QuestManager.getInstance().getById(this.getData().getQuestId());

            if (quest != null && this.getQuests().hasStartedQuest(quest.getId()) && !this.getQuests().hasCompletedQuest(quest.getId())) {
                this.getSession().send(new QuestStartedMessageComposer(quest, this));

                if (quest.getType() == QuestType.SOCIAL_VISIT) {
                    this.getQuests().progressQuest(QuestType.SOCIAL_VISIT);
                }
            }
        }

        if (!this.enteredRooms.contains(id) && !this.rooms.contains(id)) {
            this.enteredRooms.add(id);
        }
    }

    @Override
    public void poof() {
        this.getSession().send(new UpdateInfoMessageComposer(-1, this.getData().getFigure(), this.getData().getGender(), this.getData().getMotto(), this.getData().getAchievementPoints()));

        if (this.getEntity() != null && this.getEntity().getRoom() != null && this.getEntity().getRoom().getEntities() != null) {
            this.getEntity().unIdle();
            this.getEntity().getRoom().getEntities().broadcastMessage(new UpdateInfoMessageComposer(this.getEntity()));
        }
    }

    @Override
    public void ignorePlayer(int playerId) {
        if (this.ignoredPlayers == null) {
            this.ignoredPlayers = new ArrayList<>();
        }

        this.ignoredPlayers.add(playerId);
    }

    @Override
    public void unignorePlayer(int playerId) {
        this.ignoredPlayers.remove((Integer) playerId);
    }

    @Override
    public boolean ignores(int playerId) {
        return this.ignoredPlayers != null && this.ignoredPlayers.contains(playerId);
    }

    @Override
    public List<Integer> getRooms() {
        return rooms;
    }

    @Override
    public void setRooms(List<Integer> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void setSession(BaseSession client) {
        this.session = ((Session) client);
    }

    //    @Override
    public void setEntity(PlayerEntity avatar) {
        this.entity = avatar;
    }

    //    @Override
    public PlayerEntity getEntity() {
        return this.entity;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    @Override
    public PlayerData getData() {
        return this.data;
    }

    @Override
    public PlayerStatistics getStats() {
        return this.stats;
    }

    @Override
    public PermissionComponent getPermissions() {
        return this.permissions;
    }

    //    @Override
    public MessengerComponent getMessenger() {
        return this.messenger;
    }

    //    @Override
    public PlayerInventory getInventory() {
        return this.inventory;
    }

    //    @Override
    public SubscriptionComponent getSubscription() {
        return this.subscription;
    }

    //    @Override
    public RelationshipComponent getRelationships() {
        return this.relationships;
    }

    //    @Override
    public InventoryBotComponent getBots() {
        return this.bots;
    }

    //    @Override
    public PetComponent getPets() {
        return this.pets;
    }

    //    @Override
    public QuestComponent getQuests() {
        return quests;
    }

    public AchievementComponent getAchievements() {
        return achievements;
    }

    @Override
    public PlayerSettings getSettings() {
        return this.settings;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean isTeleporting() {
        return this.teleportId != 0;
    }

    @Override
    public long getTeleportId() {
        return this.teleportId;
    }

    @Override
    public void setTeleportId(long teleportId) {
        this.teleportId = teleportId;
    }

    @Override
    public long getRoomLastMessageTime() {
        return roomLastMessageTime;
    }

    @Override
    public void setRoomLastMessageTime(long roomLastMessageTime) {
        this.roomLastMessageTime = roomLastMessageTime;
    }

    @Override
    public double getRoomFloodTime() {
        return roomFloodTime;
    }

    @Override
    public void setRoomFloodTime(double roomFloodTime) {
        this.roomFloodTime = roomFloodTime;
    }

    @Override
    public int getRoomFloodFlag() {
        return roomFloodFlag;
    }

    @Override
    public void setRoomFloodFlag(int roomFloodFlag) {
        this.roomFloodFlag = roomFloodFlag;
    }

    @Override
    public String getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public List<Integer> getGroups() {
        return groups == null ? Lists.newArrayList() : groups;
    }

    @Override
    public int getNotifCooldown() {
        return this.notifCooldown;
    }

    @Override
    public void setNotifCooldown(int notifCooldown) {
        this.notifCooldown = notifCooldown;
    }

    @Override
    public int getLastRoomId() {
        return lastRoomId;
    }

    @Override
    public void setLastRoomId(int lastRoomId) {
        this.lastRoomId = lastRoomId;
    }

    @Override
    public int getLastGift() {
        return lastGift;
    }

    @Override
    public void setLastGift(int lastGift) {
        this.lastGift = lastGift;
    }

    @Override
    public long getMessengerLastMessageTime() {
        return messengerLastMessageTime;
    }

    @Override
    public void setMessengerLastMessageTime(long messengerLastMessageTime) {
        this.messengerLastMessageTime = messengerLastMessageTime;
    }

    @Override
    public double getMessengerFloodTime() {
        return messengerFloodTime;
    }

    @Override
    public void setMessengerFloodTime(double messengerFloodTime) {
        this.messengerFloodTime = messengerFloodTime;
    }

    @Override
    public int getMessengerFloodFlag() {
        return messengerFloodFlag;
    }

    @Override
    public void setMessengerFloodFlag(int messengerFloodFlag) {
        this.messengerFloodFlag = messengerFloodFlag;
    }

    @Override
    public boolean isDeletingGroup() {
        return isDeletingGroup;
    }

    @Override
    public void setDeletingGroup(boolean isDeletingGroup) {
        this.isDeletingGroup = isDeletingGroup;
    }

    @Override
    public long getDeletingGroupAttempt() {
        return deletingGroupAttempt;
    }

    @Override
    public void setDeletingGroupAttempt(long deletingGroupAttempt) {
        this.deletingGroupAttempt = deletingGroupAttempt;
    }

    @Override
    public void bypassRoomAuth(final boolean bypassRoomAuth) {
        this.bypassRoomAuth = bypassRoomAuth;
    }

    @Override
    public boolean isBypassingRoomAuth() {
        return bypassRoomAuth;
    }

    @Override
    public int getLastFigureUpdate() {
        return lastFigureUpdate;
    }

    @Override
    public void setLastFigureUpdate(int lastFigureUpdate) {
        this.lastFigureUpdate = lastFigureUpdate;
    }

    public int getTeleportRoomId() {
        return teleportRoomId;
    }

    public void setTeleportRoomId(int teleportRoomId) {
        this.teleportRoomId = teleportRoomId;
    }

    @Override
    public long getLastReward() {
        return lastReward;
    }

    @Override
    public void setLastReward(long lastReward) {
        this.lastReward = lastReward;
    }

    public int getLastForumPost() {
        return lastForumPost;
    }

    public void setLastForumPost(int lastForumPost) {
        this.lastForumPost = lastForumPost;
    }

    private int roomQueueId = 0;

    private int spectatorRoomId = 0;

    public boolean hasQueued(int id) {
        if (roomQueueId == id)
            return true;

        return false;
    }

    public void setRoomQueueId(int id) {
        this.roomQueueId = id;
    }

    public boolean isSpectating(int id) {
        if (this.spectatorRoomId == id) {
            return true;
        }

        return false;
    }

    public void setSpectatorRoomId(int id) {
        this.spectatorRoomId = id;
    }

    public int getLastRoomCreated() {
        return lastRoomCreated;
    }

    public void setLastRoomCreated(int lastRoomCreated) {
        this.lastRoomCreated = lastRoomCreated;
    }

    public long getLastRoomRequest() {
        return lastRoomRequest;
    }

    public void setLastRoomRequest(long lastRoomRequest) {
        this.lastRoomRequest = lastRoomRequest;
    }

    public long getLastBadgeUpdate() {
        return lastBadgeUpdate;
    }

    public void setLastBadgeUpdate(long lastBadgeUpdate) {
        this.lastBadgeUpdate = lastBadgeUpdate;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

    public int getLastTradePlayer() {
        return lastTradePlayer;
    }

    public void setLastTradePlayer(int lastTradePlayer) {
        this.lastTradePlayer = lastTradePlayer;
    }

    public long getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(long lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public int getLastTradeFlag() {
        return lastTradeFlag;
    }

    public void setLastTradeFlag(int lastTradeFlag) {
        this.lastTradeFlag = lastTradeFlag;
    }

    public int getLastTradeFlood() {
        return lastTradeFlood;
    }

    public void setLastTradeFlood(int lastTradeFlood) {
        this.lastTradeFlood = lastTradeFlood;
    }

    public void setSsoTicket(final String ssoTicket) {
        this.ssoTicket = ssoTicket;
    }

    public String getSsoTicket() {
        return this.ssoTicket;
    }

    public long getLastPhotoTaken() {
        return lastPhotoTaken;
    }

    public void setLastPhotoTaken(long lastPhotoTaken) {
        this.lastPhotoTaken = lastPhotoTaken;
    }

    public int getLastVoucherRedeemAttempt() {
        return lastVoucherRedeemAttempt;
    }

    public void setLastVoucherRedeemAttempt(int lastVoucherRedeem) {
        this.lastVoucherRedeemAttempt = lastVoucherRedeem;
    }

    public int getVoucherRedeemAttempts() {
        return voucherRedeemAttempts;
    }

    public void setVoucherRedeemAttempts(int voucherRedeemAttempts) {
        this.voucherRedeemAttempts = voucherRedeemAttempts;
    }

    public boolean isUsernameConfirmed() {
        return usernameConfirmed;
    }

    public void setUsernameConfirmed(boolean usernameConfirmed) {
        this.usernameConfirmed = usernameConfirmed;
    }

    public Set<String> getEventLogCategories() {
        return eventLogCategories;
    }

    public ChatMessageColour getChatMessageColour() {
        return chatMessageColour;
    }

    public void setChatMessageColour(ChatMessageColour chatMessageColour) {
        this.chatMessageColour = chatMessageColour;
    }

    public HelperSession getHelperSession() {
        return helperSession;
    }

    public void setHelperSession(HelperSession helperSession) {
        this.helperSession = helperSession;
    }

    public HelpRequest getHelpRequest() {
        return helpRequest;
    }

    public void setHelpRequest(HelpRequest helpRequest) {
        this.helpRequest = helpRequest;
    }

    public Set<CatalogItem> getRecentPurchases() {
        if (this.recentPurchases == null) {
            this.recentPurchases = new ConcurrentHashSet<>();

            this.recentPurchases.addAll(CatalogDao.findRecentPurchases(30, this.id));
        }

        return this.recentPurchases;
    }

    public NavigatorComponent getNavigator() {
        return navigator;
    }

    public String getFastFoodTicket() {
        return PlayerDao.generateFastFoodTicket(this.id);
    }

    public void sendNotif(String s) {
    }

    public void increaseXP(int xp) {


        int oldLevel;
        oldLevel = this.data.getLevel();

        System.out.println(oldLevel);
        this.data.increaseSnowXp(xp);
        System.out.println(this.data.getLevel());
        PlayerDao.updateSnowXp(this);
        if (this.getData().getLevel() != oldLevel) {
            this.data.increasePoints(this.data.diamondsToGive());
            this.data.save();
            this.sendBalance();
            session.send(new NotificationMessageComposer("generic", "Du levlade precis till level: " + this.data.getLevel() + " och tilldelades: " + this.data.diamondsToGive() + " diamanter!"));
        }
    }
}
