package com.orionemu.server.network.messages.types.tasks;

import com.orionemu.server.network.messages.incoming.Event;
import com.orionemu.server.network.messages.outgoing.room.engine.UserNameChangeMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.tasks.OrionTask;
import org.apache.log4j.Logger;

public class MessageEventTask implements OrionTask {
    private static final Logger log = Logger.getLogger(MessageEventTask.class.getName());

    private Event messageEvent;
    private Session session;
    private MessageEvent messageEventData;

    private long start;

    public MessageEventTask(Event messageEvent, Session session, MessageEvent messageEventData) {
        nametags(session);
        this.messageEvent = messageEvent;
        this.session = session;
        this.messageEventData = messageEventData;

        this.start = System.currentTimeMillis();
    }
    public void nametags(Session session) {
        String hex;
        String tag;
        String username = session.getPlayer().getData().getUsername();
        if(session.getPlayer().getData().getRank() >= 2) {
            switch(session.getPlayer().getData().getRank()) {
                case 2:
                    hex = "#42f462";
                    tag = "VIP";
                    break;
                case 3:
                    hex = "#41b2f4";
                    tag = "eXpert";
                    break;
                case 4:
                    hex = "#41f4ac";
                    tag = "Silver-H";
                    break;
                case 5:
                    hex = "#41f4ac";
                    tag = "Guld-H";
                    break;
                case 6:
                    hex = "#41f4ac";
                    tag = "Eventmgr";
                    break;
                case 7:
                    hex = "#41f4ac";
                    tag = "MOD";
                    break;
                case 8:
                    hex = "#41f4ac";
                    tag = "MOD-CHEF";
                    break;
                case 9:
                    hex = "#41f4ac";
                    tag = "The one and only Omega";
                    break;
                default:
                    tag = "";
                    hex = "";
                    return;
            }
            String newTag = "<font color='" + hex + "'>[" + tag + "]</font> " + username;
            session.send(new UserNameChangeMessageComposer(session.getPlayer().getEntity().getRoom().getId(), session.getPlayer().getId(), newTag));
        }
    }

    @Override
    public void run() {
        try {
            log.debug("Started packet process for packet: [" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "]");
            this.messageEvent.handle(this.session, this.messageEventData);

            long timeTakenSinceCreation = ((System.currentTimeMillis() - this.start));

            // If the packet took more than 750ms to be handled, red flag!
            if (timeTakenSinceCreation >= 750) {
                if (session.getPlayer() != null && session.getPlayer().getData() != null)
                    log.trace("[" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "][" + session.getPlayer().getId() + "][" + session.getPlayer().getData().getUsername() + "] Packet took " + timeTakenSinceCreation + "ms to execute");
                else
                    log.trace("[" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "] Packet took " + timeTakenSinceCreation + "ms to execute");
            }
            log.debug("Finished packet process for packet: [" + this.messageEvent.getClass().getSimpleName() + "][" + messageEventData.getId() + "] in " + timeTakenSinceCreation + "ms");

        } catch (Exception e) {
            if (this.session.getLogger() != null)
                session.getLogger().error("Error while handling event: " + this.messageEvent.getClass().getSimpleName(), e);
            else
                log.error("Error while handling event: " + this.messageEvent.getClass().getSimpleName(), e);
        }
    }
}
