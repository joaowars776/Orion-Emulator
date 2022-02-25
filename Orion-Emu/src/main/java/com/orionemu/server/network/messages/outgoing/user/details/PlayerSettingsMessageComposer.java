package com.orionemu.server.network.messages.outgoing.user.details;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.types.PlayerSettings;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class PlayerSettingsMessageComposer extends MessageComposer {
    private final PlayerSettings playerSettings;

    public PlayerSettingsMessageComposer(final PlayerSettings playerSettings) {
        this.playerSettings = playerSettings;
    }

    @Override
    public short getId() {
        return Composers.SoundSettingsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerSettings.getVolumes().getSystemVolume());
        msg.writeInt(this.playerSettings.getVolumes().getFurniVolume());
        msg.writeInt(this.playerSettings.getVolumes().getTraxVolume());
        msg.writeBoolean(this.playerSettings.isUseOldChat()); // old chat enabled?
    }
}
