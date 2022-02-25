package com.orionemu.api.events.players;

import com.orionemu.api.events.Event;
import com.orionemu.api.events.players.args.OnPlayerLoginEventArgs;

import java.util.function.Consumer;

public class OnPlayerLoginEvent extends Event<OnPlayerLoginEventArgs> {
    public OnPlayerLoginEvent(Consumer<OnPlayerLoginEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
