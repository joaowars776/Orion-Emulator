package com.orionemu.server.network.messages.incoming.user.details;

import com.orionemu.server.game.players.components.types.settings.VolumeData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.utilities.JsonUtil;


public class UpdateAudioSettingsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer() == null) {
            return;
        }

        int systemVolume = msg.readInt();
        int furniVolume = msg.readInt();
        int traxVolume = msg.readInt();

        if (client.getPlayer().getSettings().getVolumes().getSystemVolume() == systemVolume
                && client.getPlayer().getSettings().getVolumes().getFurniVolume() == furniVolume
                && client.getPlayer().getSettings().getVolumes().getTraxVolume() == traxVolume) {
            return;
        }

        PlayerDao.saveVolume(JsonUtil.getInstance().toJson(new VolumeData(systemVolume, furniVolume, traxVolume)), client.getPlayer().getId());
    }
}
