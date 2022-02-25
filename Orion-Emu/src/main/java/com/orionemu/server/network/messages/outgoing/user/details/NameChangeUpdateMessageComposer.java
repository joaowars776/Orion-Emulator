package com.orionemu.server.network.messages.outgoing.user.details;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;

public class NameChangeUpdateMessageComposer extends MessageComposer {
    private String name;
    private int error;
    private List<String> tags;

    public NameChangeUpdateMessageComposer(String name, int error, List<String> tags) {
        this.name = name;
        this.error = error;
        this.tags = tags;
    }

    public NameChangeUpdateMessageComposer(String name, int error) {
        this.name = name;
        this.error = error;
    }

    public NameChangeUpdateMessageComposer(int id, int id1, String newTag) {
    }

    @Override
    public short getId() {
        return Composers.NameChangeUpdateMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {

    }
}
