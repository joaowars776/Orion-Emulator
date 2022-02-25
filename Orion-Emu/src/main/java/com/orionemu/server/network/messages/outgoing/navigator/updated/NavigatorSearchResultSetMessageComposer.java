package com.orionemu.server.network.messages.outgoing.navigator.updated;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.navigator.types.Category;
import com.orionemu.server.game.navigator.types.categories.NavigatorSearchAllowance;
import com.orionemu.server.game.navigator.types.categories.NavigatorViewMode;
import com.orionemu.server.game.navigator.types.search.NavigatorSearchService;
import com.orionemu.server.game.players.types.Player;
import com.orionemu.server.game.rooms.RoomManager;
import com.orionemu.server.game.rooms.types.RoomData;
import com.orionemu.server.game.rooms.types.RoomWriter;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.List;

public class NavigatorSearchResultSetMessageComposer extends MessageComposer {

    private final String category;
    private final String data;
    private final List<Category> categories;
    private final Player player;

    public NavigatorSearchResultSetMessageComposer(String category, String data, List<Category> categories, Player player) {
        this.category = category;
        this.data = data;
        this.categories = categories;
        this.player = player;
    }

    @Override
    public short getId() {
        return Composers.NavigatorSearchResultSetMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.category);
        msg.writeString(this.data);

        if(this.categories == null) {
            msg.writeInt(1);
            msg.writeString("query");
            msg.writeString("");

            msg.writeInt(NavigatorSearchAllowance.getIntValue(NavigatorSearchAllowance.NOTHING));
            msg.writeBoolean(false);
            msg.writeInt(0);

            List<RoomData> rooms = NavigatorSearchService.order(RoomManager.getInstance().getRoomsByQuery(this.data), 50);

            msg.writeInt(rooms.size());

            for (RoomData roomData : rooms) {
                RoomWriter.write(roomData, msg);
            }

            rooms.clear();
        } else {
            msg.writeInt(this.categories.size());

            for (Category category : this.categories) {
                msg.writeString(category.getCategoryId());
                msg.writeString(category.getPublicName());

                msg.writeInt(NavigatorSearchAllowance.getIntValue(category.getSearchAllowance()));
                msg.writeBoolean(false);//is minimised
                msg.writeInt(category.getViewMode() == NavigatorViewMode.REGULAR ? 0 : category.getViewMode() == NavigatorViewMode.THUMBNAIL ? 1 : 0);

                List<RoomData> rooms = NavigatorSearchService.getInstance().search(category, this.player, this.categories.size() == 1);

                msg.writeInt(rooms.size());// size of rooms found.

                for (RoomData roomData : rooms) {
                    RoomWriter.write(roomData, msg);
                }

                rooms.clear();
            }
        }
    }

    @Override
    public void dispose() {
        if (this.categories != null)
            this.categories.clear();
    }
}
