package com.orionemu.server.network.messages.outgoing.landing;

import com.orionemu.api.networking.messages.IComposer;
import com.orionemu.server.network.messages.composers.MessageComposer;
import com.orionemu.server.network.messages.protocol.headers.Composers;

public class HotelViewItemMessageComposer extends MessageComposer {

    private final String campaignString;
    private final String campaignName;

    public HotelViewItemMessageComposer(String campaignString, String campaignName) {
        this.campaignString = campaignString;
        this.campaignName = campaignName;
    }

    @Override
    public short getId() {
        return Composers.CampaignMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.campaignString);
        msg.writeString(this.campaignName);
    }
}
