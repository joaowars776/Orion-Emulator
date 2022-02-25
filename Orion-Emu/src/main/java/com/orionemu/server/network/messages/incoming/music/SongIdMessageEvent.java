package com.orionemu.server.network.messages.incoming.music;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.items.music.MusicData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.music.SongIdMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;

public class SongIdMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String songName = msg.readString();

        MusicData musicData = ItemManager.getInstance().getMusicDataByName(songName);

        if (musicData != null) {
            client.send(new SongIdMessageComposer(musicData.getName(), musicData.getSongId()));
        }
    }
}