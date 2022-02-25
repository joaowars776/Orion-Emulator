package com.orionemu.server.game.rooms.types;

import com.orionemu.api.game.rooms.IRoom;
import com.orionemu.server.game.bots.BotData;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.pets.data.PetData;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.RoomQueue;
import com.orionemu.server.game.rooms.models.CustomFloorMapData;
import com.orionemu.server.game.rooms.models.RoomModel;
import com.orionemu.server.game.rooms.models.types.DynamicRoomModel;
import com.orionemu.server.game.rooms.objects.entities.types.BotEntity;
import com.orionemu.server.game.rooms.objects.entities.types.PetEntity;
import com.orionemu.server.game.rooms.objects.entities.types.data.PlayerBotData;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.items.RoomItemWall;
import com.orionemu.server.game.rooms.objects.items.types.floor.banzai.JackpotTimerFloorItem;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerAtGivenTime;
import com.orionemu.server.game.rooms.types.components.*;
import com.orionemu.server.game.rooms.types.components.games.GameType;
import com.orionemu.server.game.rooms.types.mapping.RoomMapping;
import com.orionemu.server.network.messages.outgoing.room.polls.QuickPollMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.polls.QuickPollResultsMessageComposer;
import com.orionemu.server.storage.cache.CacheManager;
import com.orionemu.server.storage.cache.objects.RoomDataObject;
import com.orionemu.server.storage.cache.objects.items.FloorItemDataObject;
import com.orionemu.server.storage.cache.objects.items.WallItemDataObject;
import com.orionemu.server.utilities.JsonUtil;
import com.orionemu.server.utilities.attributes.Attributable;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Room implements Attributable, IRoom {
    public static final boolean useCycleForItems = false;
    public static final boolean useCycleForEntities = false;

    public final Logger log;

    private final RoomData data;

    private final RoomDataObject cachedData;

    private RoomModel model;
    private RoomMapping mapping;

    private ProcessComponent process;
    private RightsComponent rights;
    private ItemsComponent items;
    private ItemProcessComponent itemProcess;
    private TradeComponent trade;
    private RoomBotComponent bots;
    private PetComponent pets;
    private GameComponent game;
    private EntityComponent entities;
    private Group group;

    private Map<String, Object> attributes;
    private Set<Integer> ratings;

    private String question;
    private Set<Integer> yesVotes;
    private Set<Integer> noVotes;

    private boolean isDisposed = false;
    private int idleTicks = 0;
    private final AtomicInteger wiredTimer = new AtomicInteger(0);

    private boolean isReloading = false;

    public Room(RoomData data) {
        this.data = data;
        this.log = Logger.getLogger("Room \"" + this.getData().getName() + "\"");
        this.cachedData = null;
    }

    public Room(RoomDataObject cachedRoomObject) {
        this(cachedRoomObject.getData());
    }

    public Room load() {
        this.model = RoomManager.getInstance().getModel(this.getData().getModel());

        if (this.getData().getHeightmap() != null) {
            DynamicRoomModel dynamicRoomModel;

            if (this.getData().getHeightmap().startsWith("{")) {
                CustomFloorMapData mapData = JsonUtil.getInstance().fromJson(this.getData().getHeightmap(), CustomFloorMapData.class);

                dynamicRoomModel = DynamicRoomModel.create("dynamic_heightmap", mapData.getModelData(), mapData.getDoorX(), mapData.getDoorY(), mapData.getDoorRotation(), mapData.getWallHeight());
            } else {
                dynamicRoomModel = DynamicRoomModel.create("dynamic_heightmap", this.getData().getHeightmap(), this.getModel().getDoorX(), this.getModel().getDoorY(), this.getModel().getDoorRotation(), -1);
            }

            if (dynamicRoomModel != null) {
                this.model = dynamicRoomModel;
            }
        }

        // Cache the group.
        this.group = GroupManager.getInstance().get(this.getData().getGroupId());

        this.attributes = new HashMap<>();
        this.ratings = new HashSet<>();

        this.mapping = new RoomMapping(this);
        this.itemProcess = new ItemProcessComponent(this);
        this.process = new ProcessComponent(this);
        this.rights = new RightsComponent(this);
        this.items = new ItemsComponent(this);

        this.mapping.init();

        this.trade = new TradeComponent(this);
        this.game = new GameComponent(this);
        this.entities = new EntityComponent(this);
        this.bots = new RoomBotComponent(this);
        this.pets = new PetComponent(this);

        this.setAttribute("loadTime", System.currentTimeMillis());

        if (this.data.getType() == RoomType.PUBLIC) {
            RoomQueue.getInstance().addQueue(this.getId(), 0);
        }

        this.log.debug("Room loaded");
        return this;
    }

    public RoomDataObject getCacheObject() {
        final List<FloorItemDataObject> floorItems = new ArrayList<>();
        final List<WallItemDataObject> wallItems = new ArrayList<>();
        final List<Integer> rights = new ArrayList<>();
        final List<PetData> petData = new ArrayList<>();
        final List<BotData> botData = new ArrayList<>();

        for (RoomItemFloor floorItem : this.getItems().getFloorItems().values()) {
            if (floorItem != null) {
                floorItems.add(new FloorItemDataObject(floorItem.getId(), floorItem.getItemId(), this.getId(), floorItem.getOwner(), floorItem.getOwnerName(), floorItem.getDataObject(), floorItem.getPosition(), floorItem.getRotation(), floorItem.getLimitedEditionItemData()));
            }
        }

        for (RoomItemWall wallItem : this.getItems().getWallItems().values()) {
            if (wallItem != null) {
                wallItems.add(new WallItemDataObject(wallItem.getId(), wallItem.getItemId(), this.getId(), wallItem.getOwner(), wallItem.getOwnerName(), wallItem.getExtraData(), wallItem.getWallPosition(), wallItem.getLimitedEditionItemData()));
            }
        }

        for (Integer roomRightsHolder : this.rights.getAll()) {
            rights.add(roomRightsHolder);
        }

        for (PetEntity petEntity : this.getEntities().getPetEntities()) {
            if (petEntity.getData() != null) {
                petData.add(petEntity.getData());
            }
        }

        for (BotEntity botEntity : this.getEntities().getBotEntities()) {
            if (botEntity.getData() instanceof PlayerBotData) {
                botData.add(botEntity.getData());
            }
        }

        return new RoomDataObject(this.getId(), this.getData(), rights, floorItems, wallItems, petData, botData);
    }

    public boolean isIdle() {
        if (this.idleTicks < 600 && this.getEntities().realPlayerCount() > 0) {
            this.idleTicks = 0;
        } else {
            if (this.idleTicks >= 600) {
                return true;
            } else {
                this.idleTicks += 10;
            }
        }

        return false;
    }

    private boolean forcedUnload = false;

    public void setIdleNow() {
        this.idleTicks = 600;
        this.forcedUnload = true;
    }

    public void reload() {
        this.setIdleNow();
        this.isReloading = true;
    }

    public void dispose() {
        if (this.isDisposed) {
            return;
        }

        long currentTime = System.currentTimeMillis();

        if (CacheManager.getInstance().isEnabled()) {
            CacheManager.getInstance().put("rooms." + this.getId(), this.getCacheObject());
        }

        this.getItems().commit();

        this.isDisposed = true;

        this.process.stop();
        this.itemProcess.stop();
        this.game.stop();
        this.game.stopV75();

        this.game.dispose();
        this.entities.dispose();
        this.items.dispose();
        this.bots.dispose();
        this.mapping.dispose();

        if (this.data.getType() == RoomType.PUBLIC) {
            RoomQueue.getInstance().removeQueue(this.getId());
        }

        if (this.forcedUnload) {
            RoomManager.getInstance().removeData(this.getId());
        }

        if (this.yesVotes != null) {
            this.yesVotes.clear();
        }

        if (this.noVotes != null) {
            this.noVotes.clear();
        }

        long timeTaken = System.currentTimeMillis() - currentTime;

        if (timeTaken >= 250) {
            this.log.warn("Room [" + this.getData().getId() + "][" + this.getData().getName() + "] took " + timeTaken + "ms to dispose");
        }

        this.log.debug("Room has been disposed");
    }

    public void tick() {
        WiredTriggerAtGivenTime.executeTriggers(this, this.wiredTimer.incrementAndGet());

        if (this.rights != null) {
            this.rights.tick();
        }

        if (this.mapping != null) {
            this.mapping.tick();
        }

        if (this.getGame().getV75StringMap().size() > 0) {
            if (!this.getGame().isV75Active()){
                this.getGame().setV75Active(true);
                this.startV75Ticking();
            }
        }

        if (this.getGame().getBetters().size() > 0) {
            if (!this.getGame().getActive()) {
                this.startInteracting();
                this.getGame().setActive(true);
            }
        }
    }

    private void startV75Ticking() {
        String lastTime = "";

        for (RoomItemFloor item : this.getItems().getByClass(JackpotTimerFloorItem.class)) {
            int time = 30;

            item.setExtraData(time + "");
            item.sendUpdate();
            item.saveData();
            if (item.getExtraData().equals("0") && lastTime != null && lastTime.isEmpty()) {
                item.setExtraData(lastTime);
            }

            int gameLength = Integer.parseInt(item.getExtraData());

            lastTime = item.getExtraData();

            if (this.getGame().getV75Instance() == null) {
                this.getGame().createNew(GameType.V75);
                this.getGame().getV75Instance().startTimer(gameLength);
            }
        }
    }

    private void startInteracting() {
        String lastTime = "";

        for (RoomItemFloor item : this.getItems().getByClass(JackpotTimerFloorItem.class)) {
            int time = 60;

            item.setExtraData(time + "");
            item.sendUpdate();
            item.saveData();
            if (item.getExtraData().equals("0") && lastTime != null && lastTime.isEmpty()) {
                item.setExtraData(lastTime);
            }

            int gameLength = Integer.parseInt(item.getExtraData());

            lastTime = item.getExtraData();

            if (this.getGame().getInstance() == null) {
                this.getGame().createNew(GameType.JACKPOT);
                this.getGame().getInstance().startTimer(gameLength);
            }
        }
    }

    public void startQuestion(String question) {
        this.question = question;
        this.yesVotes = Sets.newConcurrentHashSet();
        this.noVotes = Sets.newConcurrentHashSet();

        this.getEntities().broadcastMessage(new QuickPollMessageComposer(question));
    }

    public void endQuestion() {
        this.question = null;

        this.getEntities().broadcastMessage(new QuickPollResultsMessageComposer(this.yesVotes.size(), this.noVotes.size()));

        if (this.yesVotes != null) {
            this.yesVotes.clear();
        }

        if (this.noVotes != null) {
            this.noVotes.clear();
        }
    }

    public int getWiredTimer() {
        return this.wiredTimer.get();
    }

    public void resetWiredTimer() {
        this.wiredTimer.set(0);
    }

    public RoomPromotion getPromotion() {
        return RoomManager.getInstance().getRoomPromotions().get(this.getId());
    }

    @Override
    public void setAttribute(String attributeKey, Object attributeValue) {
        if (this.attributes.containsKey(attributeKey)) {
            this.removeAttribute(attributeKey);
        }

        this.attributes.put(attributeKey, attributeValue);
    }

    @Override
    public Object getAttribute(String attributeKey) {
        return this.attributes.get(attributeKey);
    }

    @Override
    public boolean hasAttribute(String attributeKey) {
        return this.attributes.containsKey(attributeKey);
    }

    @Override
    public void removeAttribute(String attributeKey) {
        this.attributes.remove(attributeKey);
    }

    public int getId() {
        return this.data.getId();
    }

    public RoomData getData() {
        return this.data;
    }

    public RoomModel getModel() {
        return this.model;
    }

    public ProcessComponent getProcess() {
        return this.process;
    }

    public ItemProcessComponent getItemProcess() {
        return this.itemProcess;
    }

    public ItemsComponent getItems() {
        return this.items;
    }

    public TradeComponent getTrade() {
        return this.trade;
    }

    public RightsComponent getRights() {
        return this.rights;
    }

    public RoomBotComponent getBots() {
        return this.bots;
    }

    public PetComponent getPets() {
        return this.pets;
    }

    public GameComponent getGame() {
        return this.game;
    }

    public EntityComponent getEntities() {
        return this.entities;
    }

    public RoomMapping getMapping() {
        return this.mapping;
    }

    public Group getGroup() {
        if (this.group == null || this.group.getData() == null)
            return null;

        return this.group;
    }

    public void setGroup(final Group group) {
        this.group = group;
    }

    public boolean hasRoomMute() {
        return this.attributes.containsKey("room_muted") && (boolean) this.attributes.get("room_muted");
    }

    public void setRoomMute(boolean mute) {
        if (this.attributes.containsKey("room_muted")) {
            this.attributes.replace("room_muted", mute);
        } else {
            this.attributes.put("room_muted", mute);
        }
    }

    public Set<Integer> getRatings() {
        return ratings;
    }

    public Set<Integer> getNoVotes() {
        return noVotes;
    }

    public Set<Integer> getYesVotes() {
        return yesVotes;
    }

    public String getQuestion() {
        return question;
    }

    public RoomDataObject getCachedData() {
        return cachedData;
    }

    public boolean isReloading() {
        return this.isReloading;
    }
}
