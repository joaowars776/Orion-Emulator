package com.orionemu.server.game.rooms.types;

import com.orionemu.api.game.rooms.settings.RoomAccessType;
import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.groups.types.Group;
import com.orionemu.server.game.navigator.NavigatorManager;
import com.orionemu.server.game.navigator.types.publics.PublicRoom;
import com.orionemu.server.game.rooms.RoomManager;


public class RoomWriter {
    public static void write(RoomData room, IComposer msg) {
        write(room, msg, false);
    }

    public static void write(RoomData room, IComposer msg, boolean skipAuth) {
        boolean isActive = RoomManager.getInstance().isActive(room.getId());
        PublicRoom publicRoom = NavigatorManager.getInstance().getPublicRoom(room.getId());

        msg.writeInt(room.getId());
        msg.writeString(room.getName());
        msg.writeBoolean(true);
        msg.writeInt(room.getOwnerId());
        msg.writeString(room.getOwner());
        msg.writeInt(RoomWriter.roomAccessToNumber(room.getAccess()));
        msg.writeInt(!isActive ? 0 : RoomManager.getInstance().get(room.getId()).getEntities().playerCount());
        msg.writeInt(room.getMaxUsers());
        msg.writeString(room.getDescription());
        msg.writeInt(0);
        msg.writeInt(room.getTradeState().getState());
        msg.writeInt(room.getScore());
        msg.writeInt(0);
        msg.writeInt(room.getCategory().getId());
        msg.writeInt(0);
        msg.writeString("");
        msg.writeString("");
        msg.writeString("");
        msg.writeInt(room.getTags().length);

        for (String tag : room.getTags()) {
            msg.writeString(tag);
        }

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeInt(0); // has promo
        msg.writeString(""); // promo name
        msg.writeString(""); // promo description
        msg.writeInt(0); // promo minutes left
    }

    public static void writeInfo(RoomData room, IComposer msg) {
        boolean isActive  = RoomManager.getInstance().isActive(room.getId());

        msg.writeInt(room.getId());
        msg.writeString(room.getName());
        msg.writeBoolean(true);
        msg.writeInt(room.getOwnerId());
        msg.writeString(room.getOwner());
        msg.writeInt(RoomWriter.roomAccessToNumber(room.getAccess()));
        msg.writeInt(isActive ? RoomManager.getInstance().get(room.getId()).getEntities().playerCount() : 0);
        msg.writeInt(room.getMaxUsers());
        msg.writeString(room.getDescription());
        msg.writeInt(0);
        msg.writeInt(room.getCategory().canDoActions() ? 2 : 0);
        msg.writeInt(room.getScore());
        msg.writeInt(0);
        msg.writeInt(room.getCategory().getId());
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeString("");
        msg.writeInt(room.getTags().length);

        for (String tag : room.getTags()) {
            msg.writeString(tag);
        }

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(true);
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(0);
    }

    public static void writeData(RoomData room, IComposer msg) {
        boolean isActive = RoomManager.getInstance().isActive(room.getId());

        msg.writeBoolean(true);
        msg.writeInt(room.getId());
        msg.writeString(room.getName());
        msg.writeBoolean(true);
        msg.writeInt(room.getOwnerId());
        msg.writeString(room.getOwner());
        msg.writeInt(RoomWriter.roomAccessToNumber(room.getAccess()));
        msg.writeInt(!isActive ? 0 : RoomManager.getInstance().get(room.getId()).getEntities().playerCount());
        msg.writeInt(room.getMaxUsers());
        msg.writeString(room.getDescription());
        msg.writeInt(room.getTradeState().getState());
        msg.writeInt(0);
        msg.writeInt(room.getScore());
        msg.writeInt(0);
        msg.writeInt(room.getCategory().getId());
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeString("");

        msg.writeInt(room.getTags().length);

        for (String tag : room.getTags()) {
            msg.writeString(tag);
        }

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(true);
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);
        msg.writeBoolean(true);
        msg.writeBoolean(false);

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(2);
        msg.writeInt(0);
        msg.writeInt(14);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(true);

    }

    public static void entryData(RoomData room, IComposer msg) {
        boolean isActive = RoomManager.getInstance().isActive(room.getId());

        msg.writeBoolean(true);
        msg.writeInt(room.getId());
        msg.writeString(room.getName());
        msg.writeBoolean(true);
        msg.writeInt(room.getOwnerId());
        msg.writeString(room.getOwner());
        msg.writeInt(RoomWriter.roomAccessToNumber(room.getAccess()));
        msg.writeInt(!isActive ? 0 : RoomManager.getInstance().get(room.getId()).getEntities().playerCount());
        msg.writeInt(room.getMaxUsers());
        msg.writeString(room.getDescription());
        msg.writeInt(room.getTradeState().getState());
        msg.writeInt(0);
        msg.writeInt(room.getScore());
        msg.writeInt(0);
        msg.writeInt(room.getCategory().getId());
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeString("");

        msg.writeInt(room.getTags().length);

        for (String tag : room.getTags()) {
            msg.writeString(tag);
        }

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(true);
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);
        msg.writeBoolean(true);
        msg.writeBoolean(false);

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(2);
        msg.writeInt(0);
        msg.writeInt(14);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(false);
        msg.writeBoolean(true);
    }

    public static void composeRoomSpecials(IComposer msg, RoomData roomData, RoomPromotion promotion, Group group, RoomType roomType) {
        boolean composeGroup = group != null && group.getData() != null;
        boolean composePromo = promotion != null;

        int specialsType = 0;

        if(group != null)
            specialsType += 2;

        if(promotion != null)
            specialsType += 4;

        if(roomData.isAllowPets()) {
            specialsType += 16;
        }

        PublicRoom publicRoom = NavigatorManager.getInstance().getPublicRoom(roomData.getId());

        if(publicRoom != null)
            specialsType += 1;
        else
            specialsType += 8;

        msg.writeInt(specialsType);

        if(publicRoom != null) {
            msg.writeString(publicRoom.getImageUrl());
        }

        if (composeGroup) {
            composeGroup(group, msg);
        }

        if (composePromo) {
            composePromotion(promotion, msg);
        }
    }

    private static void composePromotion(RoomPromotion promotion, IComposer msg) {
        msg.writeString(promotion.getPromotionName()); // promo name
        msg.writeString(promotion.getPromotionDescription()); // promo description
        msg.writeInt(promotion.minutesLeft()); // promo minutes left
    }

    private static void composeGroup(Group group, IComposer msg) {
        msg.writeInt(group.getId());
        msg.writeString(group.getData().getTitle());
        msg.writeString(group.getData().getBadge());
    }

    public static int roomAccessToNumber(RoomAccessType access) {
        if (access == RoomAccessType.DOORBELL) {
            return 1;
        } else if (access == RoomAccessType.PASSWORD) {
            return 2;
        } else if (access == RoomAccessType.INVISIBLE) {
            // return 3; - TODO: this
            return 1;
        }

        return 0;
    }

    public static RoomAccessType roomAccessToString(int access) {
        if (access == 1) {
            return RoomAccessType.DOORBELL;
        } else if (access == 2) {
            return RoomAccessType.PASSWORD;
        } else if (access == 3) {
            // TODO: this (invisible)
            return RoomAccessType.OPEN;
        }

        return RoomAccessType.OPEN;
    }
}
