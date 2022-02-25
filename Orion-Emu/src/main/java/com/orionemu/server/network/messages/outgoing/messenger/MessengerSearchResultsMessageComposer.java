package com.orionemu.server.network.messages.outgoing.messenger;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.players.components.types.messenger.MessengerSearchResult;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;


public class MessengerSearchResultsMessageComposer extends MessageComposer {
    private final List<MessengerSearchResult> currentFriends;
    private final List<MessengerSearchResult> otherPeople;


    public MessengerSearchResultsMessageComposer(final List<MessengerSearchResult> currentFriends, final List<MessengerSearchResult> otherPeople) {
        this.currentFriends = currentFriends;
        this.otherPeople = otherPeople;
    }

    @Override
    public short getId() {
        return Composers.HabboSearchResultMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(currentFriends.size());

        for (MessengerSearchResult result : currentFriends) {
            result.compose(msg);
        }

        msg.writeInt(otherPeople.size());

        for (MessengerSearchResult result : otherPeople) {
            result.compose(msg);
        }
    }

    @Override
    public void dispose() {
        this.currentFriends.clear();
        this.otherPeople.clear();
    }
}
