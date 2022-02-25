package com.orionemu.server.game.items.rares;

public class LimitedEditionItemData implements com.orionemu.api.game.furniture.types.LimitedEditionItem {
    public static final LimitedEditionItemData NONE = new LimitedEditionItemData(0, 0, 0);

    private long itemId;
    private int limitedRare;
    private int limitedRareTotal;

    public LimitedEditionItemData(long itemId, int limitedRare, int limitedRareTotal) {
        this.itemId = itemId;
        this.limitedRare = limitedRare;
        this.limitedRareTotal = limitedRareTotal;
    }

    public long getItemId() {
        return itemId;
    }

    public int getLimitedRare() {
        return limitedRare;
    }

    public int getLimitedRareTotal() {
        return limitedRareTotal;
    }
}
