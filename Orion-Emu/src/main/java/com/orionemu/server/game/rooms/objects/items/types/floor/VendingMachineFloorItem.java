package com.orionemu.server.game.rooms.objects.items.types.floor;

import com.orionemu.api.networking.sessions.SessionManagerAccessor;
import com.orionemu.server.game.players.PlayerManager;
import com.orionemu.server.game.rooms.objects.entities.RoomEntity;

import com.orionemu.server.game.rooms.objects.entities.RoomEntityStatus;
import com.orionemu.server.game.rooms.objects.items.RoomItemFactory;
import com.orionemu.server.game.rooms.objects.items.RoomItemFloor;
import com.orionemu.server.game.rooms.objects.misc.Position;
import com.orionemu.server.game.rooms.types.Room;
import com.orionemu.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.orionemu.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.utilities.RandomInteger;

import java.util.Timer;
import java.util.TimerTask;


public class VendingMachineFloorItem extends RoomItemFloor {
    private RoomEntity vendingEntity;
    private int state = -1;
    private Timer timer = new Timer();

    public VendingMachineFloorItem(long id, int itemId, Room room, int owner, String ownerName, int x, int y, double z, int rotation, String data) {
        super(id, itemId, room, owner, ownerName, x, y, z, rotation, data);
    }

    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) { //ASSÅ VA?? varsågod ska hjälpa malek nu peacneej for fort fort fort
        if (isWiredTrigger || entity == null) return false;

        Position posInFront = this.getPosition().squareInFront(this.getRotation());
        int playerId = PlayerManager.getInstance().getPlayerIdByUsername(entity.getUsername()); //jag hade i

        Session client = (Session) SessionManagerAccessor.getInstance().getSessionManager().getByPlayerId(playerId);

        if (!this.getPosition().touching(entity)) {
            entity.moveTo(this.getPosition().squareInFront(this.rotation).getX(), this.getPosition().squareBehind(this.rotation).getY());
            client.send(new AlertMessageComposer("Sugen på att vinna mynt?\r\rSkriver du <b> :bet {antal}</b> så har du chansen att vinna mer mynt än du satsade.\r\rMöjlig vinst:\r- Dubbelt (t.ex satsar du 10 mynt kan du vinna 20)\r- Trippelt (t.ex satsar du 10 mynt kan du vinna 30)\r- Tillbaka (du får tillbaka det du satsade)\r- Inget (du förlorar det du satsade)\r\rLycka till, bli inte beroende!"));//
        } else if (!(client.getPlayer().getData().getBetAmount() > 0)) {
            client.send(new WhisperMessageComposer(client.getPlayer().getId(), "Skriv :bet {antal} för att spela!", 30));
            try {
                this.getRoom().getMapping().getTile(posInFront.getX(), posInFront.getY()).scheduleEvent(entity.getId(), (e) -> onInteract(e, requestData, false));
            } catch (Exception e) {
                // this isn't important, if we can't find the tile then we might as well just end it here.
            }
            return false;
        }

        int rotation = Position.calculateRotation(entity.getPosition().getX(), entity.getPosition().getY(), this.getPosition().getX(), this.getPosition().getY(), false);

        if (!entity.hasStatus(RoomEntityStatus.SIT) && !entity.hasStatus(RoomEntityStatus.LAY)) {
            entity.setBodyRotation(rotation);
            entity.setHeadRotation(rotation);

            entity.markNeedsUpdate();
        }

        this.vendingEntity = entity;

        if (this.getPosition().touching(entity)) {
            if (client.getPlayer().getData().getBetAmount() > 0) {
                int credits = client.getPlayer().getData().getCredits();
                int betCredits = client.getPlayer().getData().getBetAmount();
                String username = client.getPlayer().getData().getUsername();

                int random = RandomInteger.getRandom(1, 20);

                if(client.getPlayer().getData().getCredits() < 2000000) {
                    random = RandomInteger.getRandom(2, 20);
                }

                if(client.getPlayer().getData().getBetAmount() > 2990000) {
                    random = RandomInteger.getRandom(1, 16); //de funkar va? självklart lol lär dig. hejdå
                }

                switch (random) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        client.getPlayer().getData().decreaseCredits(betCredits);
                        timer.schedule(new TimerTask() {
                            public void run() {
                                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "" + username + ", förlorade " + betCredits + " mynt!", 34));
                            }
                        }, 1400);
                        break;
                    case 9:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        timer.schedule(new TimerTask() {
                            public void run() {
                                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "" + username + ", vann tillbaka sina " + betCredits + " mynt!", 34));
                            }
                        }, 1400);
                        break;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                        client.getPlayer().getData().increaseCredits(betCredits * 1);
                        timer.schedule(new TimerTask() {
                            public void run() {
                                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "" + username + ", vann och dubblade sin insats. Tar ut " + betCredits * 2 + " mynt!", 34));
                            }
                        }, 1400);
                        break;
                    case 10:
                        client.getPlayer().getData().increaseCredits(betCredits * 3);
                        timer.schedule(new TimerTask() {
                            public void run() {
                                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "" + username + ", vann och 4x sin instats. Tar ut " + betCredits * 4 + " mynt!", 34));
                            }
                        }, 1400);
                        break;
                    case 20:
                        client.getPlayer().getData().increaseCredits(betCredits * 2);
                        timer.schedule(new TimerTask() {
                            public void run() {
                                client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new WhisperMessageComposer(client.getPlayer().getEntity().getId(), "" + username + ", vann och tripplade sin insats. Tar ut " + betCredits * 3 + " mynt!", 34));
                            }
                        }, 1400);
                        break;
                }
                client.getPlayer().getData().setBetAmount(0);
                client.getPlayer().getData().save();
                client.getPlayer().sendBalance();
                this.state = 0;
                this.setTicks(RoomItemFactory.getProcessTime(1));
            }
        }
        return true;
    }

    @Override
    public void onTickComplete() {
        switch (this.state) {
            case 0: {
                this.setExtraData("1");
                this.sendUpdate();

                this.state = 1;
                this.setTicks(RoomItemFactory.getProcessTime(0.5));
                break;
            }

            case 1: { // du e inte dum hahahahhaaha väldigt enkel lösning men det funkar, lär dig av de bästa. Anhväanhdha din hjärna det är det programmering handlar om ;) yea :), ska uppdatera blib nu :)
                if (this.getDefinition().getVendingIds().length != 0) {
                    int vendingId = Integer.parseInt(this.getDefinition().getVendingIds()[RandomInteger.getRandom(0, this.getDefinition().getVendingIds().length - 1)].trim());
                    vendingEntity.carryItem(vendingId);
                }

                this.state = 2;
                this.setTicks(RoomItemFactory.getProcessTime(0.5));
                break;
            }

            case 2: {
                this.setExtraData("0");
                this.sendUpdate();

                this.state = 0;
                this.vendingEntity = null;
                break;
            }
        }
    }

    @Override
    public void onPlaced() {
        this.setExtraData("0");
    }
}
