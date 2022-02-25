package com.orionemu.server.network.messages.outgoing.navigator;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Map;


public class PopularTagsMessageComposer extends MessageComposer {
    private final Map<String, Integer> popularTags;

    public PopularTagsMessageComposer(final Map<String, Integer> popularTags) {
        this.popularTags = popularTags;
    }

    @Override
    public short getId() {
        return Composers.PopularRoomTagsResultMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
    }

    @Override
    public void dispose() {
        this.popularTags.clear();
    }
}
