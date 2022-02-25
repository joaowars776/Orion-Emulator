package com.orionemu.server.network.messages.incoming.talenttrack;

import com.orionemu.server.game.talents.TalentTrackLevel;
import com.orionemu.server.game.talents.TalentTrackManager;
import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.talenttrack.TalentTrackComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;

import java.util.Collection;

/**
 * Created by admin on 2017-06-29.
 */
public class GetTalentTrackEvnet implements Event{

    public void handle(Session session, MessageEvent msg){
        String type = msg.readString();
        Collection<TalentTrackLevel> levels = TalentTrackManager.getInstance().getCitizenshipLevels();

        session.send(new TalentTrackComposer(session));
    }
}
