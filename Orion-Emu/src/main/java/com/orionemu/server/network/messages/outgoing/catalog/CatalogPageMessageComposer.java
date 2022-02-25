package com.orionemu.server.network.messages.outgoing.catalog;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.config.OrionSettings;
import com.orionemu.server.game.catalog.types.*;
import com.orionemu.server.game.items.ItemManager;
import com.orionemu.server.game.items.types.ItemDefinition;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class CatalogPageMessageComposer extends MessageComposer {

    private final CatalogPage page;
    private String type;

    public CatalogPageMessageComposer(final CatalogPage catalogPage, String type) {
        this.page = catalogPage;
        this.type = type;
    }

    @Override
    public short getId() {
        return Composers.CatalogPageMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(page.getId());

        msg.writeString("NORMAL");

        if (page.getTemplate().equals("frontpage")) {
            msg.writeString("frontpage4");
            msg.writeInt(3);
            msg.writeString(page.getHeadline());
            msg.writeString(page.getTeaser());
            msg.writeString("");
            msg.writeInt(2);
            msg.writeString(page.getPageText1());
            msg.writeString(page.getPageText2());

        } else if (page.getTemplate().equals("spaces_new")) {
            msg.writeString("spaces_new");
            msg.writeInt(1);
            msg.writeString(page.getHeadline());
            msg.writeInt(1);
            msg.writeString(page.getPageText1());

        } else if (page.getTemplate().equals("trophies")) {
            msg.writeString("trophies");
            msg.writeInt(1);
            msg.writeString(page.getHeadline());
            msg.writeInt(2);
            msg.writeString(page.getPageText1());
            msg.writeString(page.getPageTextDetails());

        } else if (page.getTemplate().equals("pets")) {
            msg.writeString("pets");
            msg.writeInt(2);
            msg.writeString(page.getHeadline());
            msg.writeString(page.getTeaser());
            msg.writeInt(4);
            msg.writeString(page.getPageText1());
            msg.writeString("Give a name:");
            msg.writeString("Pick a colour:");
            msg.writeString("Pick a race:");

        } else if (page.getTemplate().equals("guild_frontpage")) {
            msg.writeString("guild_frontpage");
            msg.writeInt(2);
            msg.writeString("catalog_groups_en");
            msg.writeString("");
            msg.writeInt(3);
            msg.writeString(OrionSettings.hotelName + " Groups are a great way to stay in touch with your friends and share your interests with others. Each Group has a homeroom that can be decorated by other Group members, members can also purchase exclusive Group Furni that can be customised with your Group colours!");
            msg.writeString("* Co-op room decorating for group members\n* Show off your group badge!\n* Get some neat Furni in your group's colors!");
            msg.writeString("What's so great about " + OrionSettings.hotelName + " Groups?");
        } else if (page.getTemplate().equals("club_buy")) {
            // TODO: buy HC
        } else {
            msg.writeString(page.getTemplate());
            msg.writeInt(3);
            msg.writeString(page.getHeadline());
            msg.writeString(page.getTeaser());
            msg.writeString(page.getSpecial());
            msg.writeInt(3);
            msg.writeString(page.getPageText1());
            msg.writeString(page.getPageTextDetails());
            msg.writeString(page.getPageText2());
        }

        if (!page.getTemplate().equals("frontpage") && !page.getTemplate().equals("club_buy")) {
            msg.writeInt(page.getItems().size());

            for (CatalogItem item : page.getItems().values()) {
                msg.writeInt(item.getId());
                msg.writeString(item.getDisplayName());
                msg.writeBoolean(false);
                msg.writeInt(item.getCostCredits());

                if (item.getCostOther() > 0) {
                    msg.writeInt(item.getCostOther());
                    msg.writeInt(105);
                } else {
                    msg.writeInt(0);
                    msg.writeInt(0);
                }

                msg.writeBoolean(false); // Can gift

                if (!item.hasBadge()) {
                    msg.writeInt(item.getItems().size());
                } else {
                    msg.writeInt(item.getItems().size() + 1);
                    msg.writeString("b");
                    msg.writeString(item.getBadgeId());
                }

                for (CatalogBundledItem bundledItem : item.getItems()) {
                    ItemDefinition def = ItemManager.getInstance().getDefinition(bundledItem.getItemId());
                    msg.writeString(def.getType());
                    msg.writeInt(def.getSpriteId());

                    if (item.getDisplayName().contains("wallpaper_single") || item.getDisplayName().contains("floor_single") || item.getDisplayName().contains("landscape_single")) {
                        msg.writeString(item.getDisplayName().split("_")[2]);
                    } else {
                        msg.writeString(item.getPresetData());
                    }

                    msg.writeInt(item.getAmount());

                    if (item.getLimitedTotal() == 0)
                        msg.writeInt(0);
                }

                msg.writeBoolean(item.getLimitedTotal() != 0);

                if (item.getLimitedTotal() > 0) {
                    msg.writeInt(item.getLimitedTotal());
                    msg.writeInt(item.getLimitedTotal() - item.getLimitedSells());
                    msg.writeInt(0);
                }

                msg.writeBoolean(!(item.getLimitedTotal() > 0) && item.allowOffer());
            }

            msg.writeInt(-1);
            msg.writeBoolean(false);
            msg.writeInt(0);
            msg.writeInt(-1);
            msg.writeBoolean(false);
        }
    }
}