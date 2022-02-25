package com.orionemu.server.game.commands.vip;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.commands.ChatCommand;
import com.orionemu.server.game.pets.PetManager;
import com.orionemu.server.game.rooms.objects.entities.types.PlayerEntity;
import com.orionemu.server.network.messages.outgoing.room.avatar.AvatarsMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.LeaveRoomMessageComposer;
import com.orionemu.server.network.sessions.Session;


public class TransformCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendWhisper(Locale.getOrDefault("command.transform.none", "Oops! Which pet do you want to be?"), client);
            return;
        }

        if (params[0].toLowerCase().equals("human")) {
            client.getPlayer().getEntity().removeAttribute("transformation");
        } else {
            String data = PetManager.getInstance().getTransformationData(params[0].toLowerCase());

            if(data == null || data.isEmpty()) {
                sendWhisper(Locale.getOrDefault("command.transform.notexists", "Oops! This Pet name does not exists."), client);
                return;
            }

            client.getPlayer().getEntity().setAttribute("transformation", data);
        }

        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(client.getPlayer().getEntity().getId()));
        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new AvatarsMessageComposer(client.getPlayer().getEntity()));
        isExecuted(client);
    }

    @Override
    public String getPermission() {
        return "transform_command";
    }
    
    @Override
    public String getParameter() {
        return "%petname%";
    }

    @Override
    public String getDescription() {
        return Locale.get("command.transform.description");
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    public static void composeTransformation(IComposer msg, String[] transformationData, PlayerEntity entity) {
        // TODO: create global composer for entity types maybe
        msg.writeInt(entity.getPlayerId());
        msg.writeString(entity.getUsername());
        msg.writeString(entity.getMotto());
        msg.writeString(transformationData[0]);
        msg.writeInt(entity.getId());

        msg.writeInt(entity.getPosition().getX());
        msg.writeInt(entity.getPosition().getY());
        msg.writeDouble(entity.getPosition().getZ());

        msg.writeInt(0); // 2 = user 4 = bot 0 = pet ??????
        msg.writeInt(2); // 1 = user 2 = pet 3 = bot ??????n

        msg.writeInt(Integer.parseInt(transformationData[1]));
        msg.writeInt(entity.getPlayerId());
        msg.writeString("Leon"); // TODO: this :P
        msg.writeInt(1);
        msg.writeBoolean(true); // has saddle
        msg.writeBoolean(false); // has rider?

        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeString("");
    }
}
