package com.orionemu.server.network.sessions;

import com.orionemu.api.networking.messages.IMessageComposer;
import com.orionemu.api.networking.sessions.BaseSession;
import com.orionemu.api.networking.sessions.ISessionManager;
import com.orionemu.api.networking.sessions.SessionManagerAccessor;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.utilities.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public final class SessionManager implements ISessionManager {
    public static final AttributeKey<Session> SESSION_ATTR = AttributeKey.valueOf("Session.attr");
    public static final AttributeKey<Integer> CHANNEL_ID_ATTR = AttributeKey.valueOf("ChannelId.attr");

    private final AtomicInteger idGenerator = new AtomicInteger();
    private final Map<Integer, BaseSession> sessions = new ConcurrentHashMap<>();

    private final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static boolean isLocked = false;

    public SessionManager() {
        SessionManagerAccessor.getInstance().setSessionManager(this);
    }

    public boolean add(ChannelHandlerContext channel) {
        Session session = new Session(channel);

        this.channelGroup.add(channel.channel());
        channel.attr(CHANNEL_ID_ATTR).set(this.idGenerator.incrementAndGet());

        return (this.sessions.putIfAbsent(channel.attr(CHANNEL_ID_ATTR).get(), session) == null);
    }

    public boolean remove(ChannelHandlerContext channel) {
        if (this.sessions.containsKey(channel.attr(CHANNEL_ID_ATTR).get())) {
            this.channelGroup.remove(channel.channel());
            this.sessions.remove(channel.attr(CHANNEL_ID_ATTR).get());

            return true;
        }

        return false;
    }

    public boolean disconnectByPlayerId(int id) {
        if (PlayerManager.getInstance().getSessionIdByPlayerId(id) == -1) {
            return false;
        }

        int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(id);
        Session session = (Session) sessions.get(sessionId);

        if (session != null) {
            session.disconnect();
            return true;
        }

        return false;
    }

    public Session getByPlayerId(int id) {
        if (PlayerManager.getInstance().getSessionIdByPlayerId(id) != -1) {
            int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(id);

            return (Session) sessions.get(sessionId);
        }

        return null;
    }

    public Set<BaseSession> getByPlayerPermission(String permission) {
        // TODO: Optimize this
        Set<BaseSession> sessions = new HashSet<>();

//        int rank = PermissionsManager.getInstance().getPermissions().get(permission).getRank();
//
//        for (Map.Entry<Integer, BaseSession> session : this.sessions.entrySet()) {
//            if (session.getValue().getPlayer() != null) {
//                if (((Session) session.getValue()).getPlayer().getData().getRank() >= rank) {
//                    sessions.add(session.getValue());
//                }
//            }
//        }

        return sessions;
    }

    public Session getByPlayerUsername(String username) {
        int playerId = PlayerManager.getInstance().getPlayerIdByUsername(username);

        if (playerId == -1)
            return null;

        int sessionId = PlayerManager.getInstance().getSessionIdByPlayerId(playerId);

        if (sessionId == -1)
            return null;

        if (this.sessions.containsKey(sessionId))
            return (Session) this.sessions.get(sessionId);

        return null;
    }

    public int getUsersOnlineCount() {
        return PlayerManager.getInstance().size();
    }

    public Map<Integer, BaseSession> getSessions() {
        return this.sessions;
    }

    public void broadcast(IMessageComposer msg) {
        this.getChannelGroup().writeAndFlush(msg);
//
//        for (Session client : sessions.values()) {
//            client.getChannel().write(msg);
//        }
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public void broadcastToModerators(IMessageComposer messageComposer) {
        for (BaseSession session : this.sessions.values()) {
            if (session.getPlayer() != null && session.getPlayer().getPermissions() != null && session.getPlayer().getPermissions().getRank().modTool()) {
                session.send(messageComposer);
            }
        }
    }

    @Override
    public void parseCommand(String[] message, ChannelHandlerContext ctx) {
        String password = message[0];

        if (password.equals("orionServer")) {
            String command = message[1];

            switch (command) {
                default: {
                    ctx.channel().writeAndFlush("response||You're connected!");
                    break;
                }

                case "stats": {
                    ctx.channel().writeAndFlush("response||" + JsonUtil.getInstance().toJson(Orion.getStats()));
                    break;
                }

            }
        } else {
            ctx.disconnect();
        }
    }
}