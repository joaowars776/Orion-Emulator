package com.orionemu.server.network.sessions;

import com.orionemu.api.networking.messages.IMessageComposer;
import com.orionemu.api.networking.sessions.BaseSession;
import com.orionemu.games.snowwar.data.SnowWarPlayerData;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.moderation.ModerationManager;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.network.messages.outgoing.notification.LogoutMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.AvatarUpdateMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.items.UpdateFloorItemMessageComposer;
import com.orionemu.server.network.messages.protocol.messages.MessageEvent;
import com.orionemu.server.network.messages.protocol.security.exchange.DiffieHellman;
import com.orionemu.server.storage.queries.player.PlayerDao;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.UUID;


public class Session implements BaseSession {
    private Logger logger = Logger.getLogger("Session");
    public static int CLIENT_VERSION = 0;

    private final ChannelHandlerContext channel;
    private final SessionEventHandler eventHandler;

    private boolean isClone = false;
    private String uniqueId = "";

    private final UUID uuid = UUID.randomUUID();

    private Player player;
    public SnowWarPlayerData snowWarPlayerData;
    private boolean disconnectCalled = false;

    private DiffieHellman diffieHellman;
    private long lastPing = Orion.getTime();

    public Session(ChannelHandlerContext channel) {
        this.channel = channel;
        this.channel.attr(SessionManager.SESSION_ATTR).set(this);
        this.eventHandler = new SessionEventHandler(this);
    }

    public void setPlayer(Player player) {
        if (player == null || player.getData() == null) {
            return;
        }

        String username = player.getData().getUsername();

        this.logger = Logger.getLogger("[" + username + "][" + player.getId() + "]");
        this.player = player;
        this.snowWarPlayerData = new SnowWarPlayerData(player);

        int channelId = this.channel.attr(SessionManager.CHANNEL_ID_ATTR).get();

        PlayerManager.getInstance().put(player.getId(), channelId, username, this.getIpAddress());

        if (player.getPermissions().getRank().modTool()) {
            ModerationManager.getInstance().addModerator(player.getSession());
        }
    }

    public void onDisconnect() {
        if (this.disconnectCalled) {
            return;
        }

        this.disconnectCalled = true;

        PlayerManager.getInstance().getPlayerLoadExecutionService().submit(() -> {
            if (player != null && player.getData() != null)
                PlayerManager.getInstance().remove(player.getId(), player.getData().getUsername(), this.channel.attr(SessionManager.CHANNEL_ID_ATTR).get(), this.getIpAddress());

            this.eventHandler.dispose();

            if (this.player != null) {
                if (this.getPlayer().getPermissions().getRank().modTool()) {
                    ModerationManager.getInstance().removeModerator(this);
                }

                try {
                    this.getPlayer().dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            this.setPlayer(null);
        });
    }

    public void disconnect() {
        this.onDisconnect();

        this.getChannel().disconnect();
    }

    public String getIpAddress() {
        String ipAddress = "0.0.0.0";

        if (!OrionSettings.useDatabaseIp) {
            return ((InetSocketAddress) this.getChannel().channel().remoteAddress()).getAddress().getHostAddress();
        } else {
            if (this.getPlayer() != null) {
                ipAddress = PlayerDao.getIpAddress(this.getPlayer().getId());
            }
        }

        return ipAddress;
    }

    public void disconnect(String reason) {
        this.send(new LogoutMessageComposer(reason));
        this.disconnect();
    }

    public void handleMessageEvent(MessageEvent msg) {
        this.eventHandler.handle(msg);
    }

    public Session sendQueue(final IMessageComposer msg) {
        return this.send(msg, true);
    }

    public Session send(IMessageComposer msg) {
        return this.send(msg, false);
    }

    public Session send(IMessageComposer msg, boolean queue) {
        if (msg == null) {
            return this;
        }

        if (msg.getId() == 0) {
            logger.debug("Unknown header ID for message: " + msg.getClass().getSimpleName());
        }

        if (!(msg instanceof AvatarUpdateMessageComposer) && !(msg instanceof UpdateFloorItemMessageComposer))
            logger.debug("Sent message: " + msg.getClass().getSimpleName() + " / " + msg.getId());

        if (!queue) {
            this.channel.writeAndFlush(msg);
        } else {
            this.channel.write(msg);
        }
        return this;
    }

    public void flush() {
        this.channel.flush();
    }

    public Logger getLogger() {
        return this.logger;
    }

    public Player getPlayer() {
        return this.player;
    }

    public ChannelHandlerContext getChannel() {
        return this.channel;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getSessionId() {
        return uuid;
    }

    public DiffieHellman getDiffieHellman() {
        if (this.diffieHellman == null) {
            this.diffieHellman = new DiffieHellman();
        }

        return diffieHellman;
    }

    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }

    public long getLastPing() {
        return lastPing;
    }
}