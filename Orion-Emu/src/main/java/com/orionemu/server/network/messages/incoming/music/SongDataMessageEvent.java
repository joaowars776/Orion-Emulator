package com.orionemu.server.network.messages.incoming.music;

import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.items.music.MusicData;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.music.SongDataMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.google.common.collect.Lists;

import java.util.List;

public class SongDataMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int size = msg.readInt();

        List<MusicData> musicDataList = Lists.newArrayList();

        for (int i = 0; i < size; i++) {
            int songId = msg.readInt();

            MusicData musicData = ItemManager.getInstance().getMusicData(songId);

            if (musicData != null) {
                musicDataList.add(musicData);
            }
        }

        if (!musicDataList.isEmpty())
            client.send(new SongDataMessageComposer(musicDataList));
    }
}
