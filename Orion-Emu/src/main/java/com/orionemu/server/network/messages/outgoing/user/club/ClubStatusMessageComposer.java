package com.orionemu.server.network.messages.outgoing.user.club;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.players.components.SubscriptionComponent;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;


public class ClubStatusMessageComposer extends MessageComposer {
    private final SubscriptionComponent subscriptionComponent;

    public ClubStatusMessageComposer(final SubscriptionComponent subscriptionComponent) {
        this.subscriptionComponent = subscriptionComponent;
    }

    @Override
    public short getId() {
        return Composers.ScrSendUserInfoMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        int timeLeft = 0;
        int days = 0;
        int months = 0;

        if (subscriptionComponent.isValid()) {
            timeLeft = subscriptionComponent.getExpire() - (int) Orion.getTime();
            days = (int) Math.ceil(timeLeft / 86400);
            months = days / 31;

            if (months >= 1) {
                months--;
            }
        } else {
            if (subscriptionComponent.exists()) {
                subscriptionComponent.delete();
            }
        }

        msg.writeString("habbo_club");

        msg.writeInt(0);
        msg.writeInt(2);
        msg.writeInt(0);
        msg.writeInt(1);
        msg.writeBoolean(subscriptionComponent.isValid());
        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(495);

    }
}
