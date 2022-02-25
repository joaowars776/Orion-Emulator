package com.orionemu.server.network.messages.outgoing.talenttrack;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.config.Locale;
import com.orionemu.server.game.players.data.PlayerData;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.sessions.Session;
import com.orionemu.server.network.messages.protocol.headers.Composers;
import com.orionemu.server.storage.queries.player.PlayerDao;
import com.orionemu.server.storage.queries.player.inventory.InventoryDao;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static com.orionemu.server.game.commands.ChatCommand.sendNotif;

/**
 * Created by admin on 2017-06-30.
 */
public class TalentTrackComposer extends MessageComposer {
    public List<PlayerData> players;
    private int changed = 1;
    private int respect = 1;
    private int rooms = 1;
    private int behabbo = 1;
    private int created2 = 0;
    private int online = 1;
    private int online2 = 0;
    private int rooms2 = 1;
    private int friends = 1;
    private int online3 = 1;
    private int behabbo2 = 1;
    private int way = 2;
    private int cit = 0;
    private int full = 0;
    private int begin = 0;

    public TalentTrackComposer(Session session) {
        final String username = session.getPlayer().getData().getUsername();
        final String badge = "EXH";

        int playerId = PlayerDao.getIdByUsername(username);
        int regDate = StringUtils.isNumeric(session.getPlayer().getData().getRegDate()) ? Integer.parseInt(session.getPlayer().getData().getRegDate()) : session.getPlayer().getData().getRegTimestamp();
        int daysSinceRegistration = (int) Math.floor((((int) Orion.getTime()) - regDate) / 86400);


        //Step two
        if(!session.getPlayer().getData().getFigure().equals("hr-115-42_hd-195-19_ch-3030-82_lg-275-1408_fa-1201_ca-1804-64")) { changed = 2; }
        if(session.getPlayer().getStats().getRespectPoints() > 1 ) { respect = 2; }
        if(session.getPlayer().getRooms().size() > 1) { rooms = 2; }
        if(session.getPlayer().getData().getActivityPoints() > 799) { online = 2; }

        //Next level locked or not
        if(changed == 2 && respect == 2 && rooms == 2 && online == 2) {
            begin = 1;
        }

        //Step three
        if(session.getPlayer().getData().getActivityPoints() > 1099) { online2 = 2; }
        if(session.getPlayer().getRooms().size() > 4) { rooms2 = 2; }
        if(daysSinceRegistration > 0.5) { behabbo = 2; }

        //Step three missions available or not
        if(begin != 1) {
            behabbo = 0;
            rooms2 = 0;
            online2 = 0;
        }

        //Next level locked or not
        if(rooms == 2 && respect == 2 && changed == 2 && online == 2 && rooms2 == 2 && behabbo == 2 && online2 == 2) {
            friends = 1;
            online3 = 1;
            behabbo2 = 1;
            cit = 1;
        }

        //Step four
        if(session.getPlayer().getData().getActivityPoints() > 1399) { online3 = 2; }
        if(session.getPlayer().getMessenger().getFriends().size() != 0) { friends = 2; }
        if(daysSinceRegistration > 3) { behabbo2 = 2; }

        //Step four missions locked or not
        if(cit != 1) {
            friends = 0;
            online3 = 0;
            behabbo2 = 0;
            way = 0;
        }

        //Step 5 (finished or not)
        if(rooms == 2 && respect == 2 && changed == 2 && online == 2 && rooms2 == 2 && behabbo == 2 && online2 == 2 && friends == 2 && online3 == 2 && behabbo2 == 2) {
            full = 1;
            InventoryDao.addBadge(badge, playerId);
            sendNotif(Locale.get("command.givebadge.success").replace("%username%", username).replace("%badge%", badge), session);
        }
    }


    @Override
    public short getId(){
        return Composers.TalentTrackMessageComposer;
    }

    @Override
    public void compose(IComposer msg){

        msg.writeString("citizenship");
        msg.writeInt(5);
        {
            {
                msg.writeInt(0);//First level
                msg.writeInt(2);//Progress, 0 = nothing, 1 = started, 2 = done
                msg.writeInt(1);
                {
                    msg.writeInt(125);
                    msg.writeInt(1);
                    msg.writeString("ACH_SafetyQuizGraduate1");
                    msg.writeInt(2);//Progress, 0 = nothing, 1 = started, 2 = done
                    msg.writeInt(0);
                    msg.writeInt(1);
                }

                msg.writeInt(0);//umm??
                {

                }

                msg.writeInt(1);//Gift?
                {
                    msg.writeString("A1 KUMIANKKA");
                    msg.writeInt(0);
                }
            }

            {
                msg.writeInt(1);//Second level
                msg.writeInt(1);
                msg.writeInt(4);//4 loops
                //1
                {
                    msg.writeInt(6);
                    msg.writeInt(1);
                    msg.writeString("ACH_AvatarLooks1");
                    msg.writeInt(changed);
                    msg.writeInt(0);
                    msg.writeInt(1);
                }
                //2
                {
                    msg.writeInt(18);
                    msg.writeInt(1);
                    msg.writeString("ACH_RespectGiven1");
                    msg.writeInt(respect);
                    msg.writeInt(0);
                    msg.writeInt(2);
                }
                //3
                {
                    msg.writeInt(19);
                    msg.writeInt(1);
                    msg.writeString("ACH_AllTimeHotelPresence1");//badge name
                    msg.writeInt(online);//Progress
                    msg.writeInt(50);//Current progress?
                    msg.writeInt(60);//Required progress
                }
                //4
                {
                    msg.writeInt(8);
                    msg.writeInt(1);
                    msg.writeString("ACH_RoomEntry1");
                    msg.writeInt(rooms);
                    msg.writeInt(0);
                    msg.writeInt(2);
                }

                msg.writeInt(0);//count
                {

                }

                msg.writeInt(1);//1 loop
                {
                    msg.writeString("A1 KUMIANKKA");
                    msg.writeInt(0);
                }

                msg.writeInt(2);
                msg.writeInt(begin);
            }

            {
                msg.writeInt(3);//Third
                msg.writeInt(11);
                msg.writeInt(1);
                msg.writeString("ACH_RegistrationDuration1");
                msg.writeInt(behabbo);
                msg.writeInt(0);
                msg.writeInt(1);
                msg.writeInt(19);
                msg.writeInt(2);
                msg.writeString("ACH_AllTimeHotelPresence2");
                msg.writeInt(online2);
                msg.writeInt(1);
                msg.writeInt(60);
                msg.writeInt(8);
                msg.writeInt(2);
                msg.writeString("ACH_RoomEntry2");
                msg.writeInt(rooms2);
                msg.writeInt(0);
                msg.writeInt(5);
                msg.writeInt(0);
                msg.writeInt(1);
                msg.writeString("A1 KUMIANKKA");
                msg.writeInt(0);
                msg.writeInt(3);
                msg.writeInt(cit);
            }

            {
                msg.writeInt(4);//Forth
                msg.writeInt(11);
                msg.writeInt(2);
                msg.writeString("ACH_RegistrationDuration2");
                msg.writeInt(behabbo2);
                msg.writeInt(0);
                msg.writeInt(3);
                msg.writeInt(94);
                msg.writeInt(1);
                msg.writeString("ACH_HabboWayGraduate1");
                msg.writeInt(way);
                msg.writeInt(0);
                msg.writeInt(1);
                msg.writeInt(19);
                msg.writeInt(3);
                msg.writeString("ACH_AllTimeHotelPresence3");
                msg.writeInt(online3);
                msg.writeInt(0);
                msg.writeInt(120);
                msg.writeInt(381);
                msg.writeInt(1);
                msg.writeString("ACH_FriendListSize1");
                msg.writeInt(friends);
                msg.writeInt(0);
                msg.writeInt(2);
                msg.writeInt(1);
                msg.writeString("TRADE");
                msg.writeInt(1);
                msg.writeString("A1 KUMIANKKA");
                msg.writeInt(0);
            }

            {
                msg.writeInt(4);//Final
                msg.writeInt(full);
                msg.writeInt(0);
                msg.writeInt(1);
                msg.writeString("CITIZEN");
                msg.writeInt(2);
                msg.writeString("A1 KUMIANKKA");
                msg.writeInt(0);
                msg.writeString("pixel_walldeco");
                msg.writeInt(1);
            }
        }
    }
}
