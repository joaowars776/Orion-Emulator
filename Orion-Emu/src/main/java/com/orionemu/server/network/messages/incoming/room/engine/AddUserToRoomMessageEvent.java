package com.orionemu.server.network.messages.incoming.room.engine;

import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.groups.GroupManager;
import com.orionemu.server.game.groups.types.GroupData;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.polls.PollManager;
import com.orionemu.server.game.polls.types.Poll;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;
import com.orionemu.server.game.rooms.objects.entities.types.PetEntity;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.game.rooms.objects.items.types.floor.wired.triggers.WiredTriggerEnterRoom;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.group.GroupBadgesMessageComposer;
import com.orionemu.server.network.messages.outgoing.notification.NotificationMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.*;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomDataMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.engine.RoomEntryInfoMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.FloorItemsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.WallItemsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.permissions.FloodFilterMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.pets.horse.HorseFigureMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.polls.InitializePollMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.polls.QuickPollMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.polls.QuickPollResultsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.settings.RoomVisualizationSettingsMessageComposer;
import com.orionemu.server.network.messages.outgoing.user.nuxInstructionComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.polls.PollDao;

import java.util.HashMap;
import java.util.Map;


public class AddUserToRoomMessageEvent implements Event {

    private void SendAlert(Session client) {
        if (client.getPlayer().getEntity().getRoom().getData().getCategory().getId() == 30) {
            client.getPlayer().sendNotif("Casino-alert.", "Du har gått in i ett casino, vill du spela så se till att filma. \r\rOm du filmat ökar sannolikheten att det löser sig, annars får du skylla dig själv.");
        }
    }

    private void v75Alert(Session client) {
        if (client.getPlayer().getData().getRank() > 6 && client.getPlayer().getEntity().getRoom().getData().getId() == 1809) {
            client.getPlayer().sendNotif("Vänligen läs.", "Du har gått in i officiella V75 rummet, du kan dekorera rummet, men inte delen med hästburarna och mattorna, det måste ha sin position.");
        }
    }

    public void handle(Session client, MessageEvent msg) {
        PlayerEntity avatar = client.getPlayer().getEntity();

        if (avatar == null) {
            return;
        }

        Room room = avatar.getRoom();

        if (room == null) {

            return;
        }

        if (!room.getProcess().isActive()) {
            room.getProcess().start();
        }

        if (!room.getItemProcess().isActive()) {
            room.getItemProcess().start();
        }

        if (client.getPlayer().getRoomFloodTime() >= 1) {
            client.sendQueue(new FloodFilterMessageComposer(client.getPlayer().getRoomFloodTime()));
        }

        Map<Integer, String> groupsInRoom = new HashMap<>();

        for (PlayerEntity playerEntity : room.getEntities().getPlayerEntities()) {
            if (playerEntity.getPlayer() != null && playerEntity.getPlayer().getData() != null) {
                if (playerEntity.getPlayer().getData().getFavouriteGroup() != 0) {
                    GroupData groupData = GroupManager.getInstance().getData(playerEntity.getPlayer().getData().getFavouriteGroup());
                    groupsInRoom.put(playerEntity.getPlayer().getData().getFavouriteGroup(), groupData.getBadge());
                    if (groupData == null)
                        continue;

                    groupsInRoom.put(playerEntity.getPlayer().getData().getFavouriteGroup(), groupData.getBadge());
                }
            }
        }
        System.out.println(groupsInRoom.size());
        client.sendQueue(new GroupBadgesMessageComposer(groupsInRoom));
        client.sendQueue(new RoomEntryInfoMessageComposer(room.getId(), room.getData().getOwnerId() == client.getPlayer().getId() || client.getPlayer().getPermissions().getRank().roomFullControl()));
        client.sendQueue(new RoomDataMessageComposer(room));
        client.sendQueue(new AvatarsMessageComposer(room));

        if (room.getEntities().getAllEntities().size() > 0)
            client.sendQueue(new AvatarUpdateMessageComposer(room.getEntities().getAllEntities().values()));

        for (RoomEntity av : room.getEntities().getAllEntities().values()) {
            if (av.getCurrentEffect() != null) {
                client.sendQueue(new ApplyEffectMessageComposer(av.getId(), av.getCurrentEffect().getEffectId()));
            }

            if (av.getDanceId() != 0) {
                client.sendQueue(new DanceMessageComposer(av.getId(), av.getDanceId()));
            }

            if (av.getHandItem() != 0) {
                client.sendQueue(new HandItemMessageComposer(av.getId(), av.getHandItem()));
            }

            if (av.isIdle()) {
                client.sendQueue(new IdleStatusMessageComposer(av.getId(), true));
            }

            if (av.getAI() != null) {
                if (av instanceof PetEntity && ((PetEntity) av).getData().getTypeId() == 15) {
                    client.send(new HorseFigureMessageComposer(((PetEntity) av)));
                }

                av.getAI().onPlayerEnter(client.getPlayer().getEntity());
            }
        }
        client.sendQueue(new RoomVisualizationSettingsMessageComposer(room.getData().getHideWalls(), room.getData().getWallThickness(), room.getData().getFloorThickness()));
        client.getPlayer().getMessenger().sendStatus(true, true);

        client.sendQueue(new FloorItemsMessageComposer(room));
        client.sendQueue(new WallItemsMessageComposer(room));

        WiredTriggerEnterRoom.executeTriggers(client.getPlayer().getEntity());

        if (PollManager.getInstance().roomHasPoll(room.getId())) {
            Poll poll = PollManager.getInstance().getPollByRoomId(room.getId());

            if (!poll.getPlayersAnswered().contains(client.getPlayer().getId()) && !PollDao.hasAnswered(client.getPlayer().getId(), poll.getPollId())) {
                client.send(new InitializePollMessageComposer(poll.getPollId(), poll.getPollTitle(), poll.getThanksMessage()));
            }
        }

        if (room.getQuestion() != null) {
            client.send(new QuickPollMessageComposer(room.getQuestion()));

            if (room.getYesVotes().contains(client.getPlayer().getId()) || room.getNoVotes().contains(client.getPlayer().getId())) {
                client.send(new QuickPollResultsMessageComposer(room.getYesVotes().size(), room.getNoVotes().size()));
            }
        }
        client.flush();
        client.getPlayer().getData().setInHotelView(false);
        avatar.markNeedsUpdate();
        SendAlert(client);
        v75Alert(client);
    }
}
