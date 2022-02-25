package com.orionemu.server.network.messages.outgoing.landing;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.game.landing.types.PromoArticle;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

import java.util.Map;


public class PromoArticlesMessageComposer extends MessageComposer {
    private final Map<Integer, PromoArticle> articles;

    public PromoArticlesMessageComposer(final Map<Integer, PromoArticle> articles) {
        this.articles = articles;
    }

    @Override
    public short getId() {
        return Composers.PromoArticlesMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(articles.size());

        for (PromoArticle article : articles.values()) {
            msg.writeInt(article.getId());
            msg.writeString(article.getTitle());
            msg.writeString(article.getMessage());
            msg.writeString(article.getButtonText());
            msg.writeInt(0); // Button Type
            msg.writeString(article.getButtonLink());
            msg.writeString(article.getImagePath());
        }
    }
}
