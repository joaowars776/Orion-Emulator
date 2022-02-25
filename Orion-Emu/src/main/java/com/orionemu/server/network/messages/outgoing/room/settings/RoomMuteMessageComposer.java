package com.orionemu.server.network.messages.outgoing.room.settings;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class RoomMuteMessageComposer extends MessageComposer {

    private final boolean roomHasMute;

    public RoomMuteMessageComposer(boolean roomHasMute) {
        this.roomHasMute = roomHasMute;
    }

    @Override
    public short getId() {
        return Composers.RoomMuteSettingsMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.roomHasMute);
    }
}
