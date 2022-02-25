package com.orionemu.server.network.messages.outgoing.user.details;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class UserObjectMessageComposer extends MessageComposer {

    private final Player player;

    public UserObjectMessageComposer(final Player player) {
        this.player = player;
    }

    @Override
    public short getId() {
        return Composers.UserObjectMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(player.getId());
        msg.writeString(player.getData().getUsername());
        msg.writeString(player.getData().getFigure());
        msg.writeString(player.getData().getGender().toUpperCase());
        msg.writeString(player.getData().getMotto());
        msg.writeString(player.getData().getUsername().toLowerCase());
        msg.writeBoolean(true);

        msg.writeInt(8); // ??? (8)

        msg.writeInt(player.getStats().getDailyRespects()); // daily respects!
        msg.writeInt(player.getStats().getScratches()); // (3) pet respects I guess

        msg.writeBoolean(true);
        msg.writeString(getDate(player.getData().getLastVisit()));

        msg.writeBoolean(player.getData().getChangingName()); // can change username
        msg.writeBoolean(false);
    }

    public static String getDate(int timestamp) {
        Date d = new Date(timestamp * 1000L);
        SimpleDateFormat df = new SimpleDateFormat(Locale.getOrDefault("date.format", "MMM d yyyy 'at' HH:mm"));
        df.setTimeZone(TimeZone.getTimeZone("GMT"));

        return df.format(d);
    }
}
