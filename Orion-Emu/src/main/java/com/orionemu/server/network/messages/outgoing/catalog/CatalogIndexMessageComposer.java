package com.orionemu.server.network.messages.outgoing.catalog;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.catalog.CatalogManager;
import com.orionemu.server.game.catalog.types.CatalogPage;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CatalogIndexMessageComposer extends MessageComposer {
    private final int playerRank;

    public CatalogIndexMessageComposer(final int playerRank) {
        this.playerRank = playerRank;
    }

    @Override
    public short getId() {
        return Composers.CatalogIndexMessageComposer;
    }

    @Override
    public void compose(final IComposer msg) {
        final List<CatalogPage> pages = CatalogManager.getInstance().getPagesForRank(this.playerRank);
        final List<CatalogPage> pagesTwo = CatalogManager.getInstance().getPagesForRank(this.playerRank);
        final List<CatalogPage> subPages = CatalogManager.getInstance().getPagesForRank(this.playerRank);

        Collections.sort(subPages, new Comparator<CatalogPage>() {
            @Override
            public int compare(final CatalogPage o1, final CatalogPage o2) {
                return o1.getCaption().compareTo(o2.getCaption());
            }
        });

        msg.writeBoolean(true);
        msg.writeInt(0);
        msg.writeInt(-1);
        msg.writeString("root");
        msg.writeString("");
        msg.writeInt(0);

        msg.writeInt(count(-1, pages));
        for (CatalogPage page : pages) {
            if (page.getParentId() != -1) {
                continue;
            }

            msg.writeBoolean(true);
            //msg.writeInt(page.getColour());
            msg.writeInt(page.getIcon());
            msg.writeInt(page.getId());
            msg.writeString(page.getCaption().toLowerCase().replace(" ", "_"));
            msg.writeString(page.getCaption());
            msg.writeInt(0);

            msg.writeInt(count(page.getId(), pages));

            for (CatalogPage child : pages) {
                if (child.getParentId() != page.getId()) {
                    continue;
                }

                msg.writeBoolean(true);
                //msg.writeInt(child.getColour());
                msg.writeInt(child.getIcon());
                msg.writeInt(child.getId());
                msg.writeString(child.getCaption().toLowerCase().replace(" ", "_"));
                msg.writeString(child.getCaption());
                msg.writeInt(0);
                msg.writeInt(0); //??
            }
        }

        msg.writeBoolean(false);
        msg.writeString("NORMAL");
    }

    private int count(final int index, final List<CatalogPage> pages) {
        int i = 0;

        for (final CatalogPage page : pages) {
            if (page.getParentId() == index) {
                ++i;
            }

        }
        return i;
    }
}
