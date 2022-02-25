package com.orionemu.server.network.messages.incoming.room.bots;

        import com.orionemu.server.game.rooms.objects.entities.types.BotEntity;
        import com.orionemu.server.network.messages.incoming.Event;
        import com.orionemu.server.network.messages.outgoing.room.bots.BotConfigMessageComposer;
        import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
        import com.orionemu.server.network.sessions.Session;


public class BotConfigMessageEvent implements Event {
    public void Bothanditem(Session client, MessageEvent msg) {
        int botId = msg.readInt();
        int skillId = msg.readInt();
        String message = "you are welcome";
        BotEntity bot = client.getPlayer().getEntity().nearestBotEntity();
        if(client.getPlayer().getLastMessage() == "coke") {
            bot.say(message);
        }
        }

    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int botId = msg.readInt();
        int skillId = msg.readInt();

        BotEntity entity = client.getPlayer().getEntity().getRoom().getEntities().getEntityByBotId(botId);

        if (entity == null) {
            return;
        }

        String message = "";

        switch (skillId) {
            case 2:
                for (int i = 0; i < entity.getData().getMessages().length; i++) {
                    message += entity.getData().getMessages()[i] + "\r";
                }

                message += ";#;";
                message += String.valueOf(entity.getData().isAutomaticChat());
                message += ";#;";
                message += String.valueOf(entity.getData().getChatDelay());
                break;

            case 5:
                message = entity.getUsername();
                break;
        }
        Bothanditem(client, msg);
        client.send(new BotConfigMessageComposer(entity.getBotId(), skillId, message));
    }
}
