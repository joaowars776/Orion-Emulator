package com.orionemu.games.snowwar.items;

import com.orionemu.api.networking.messages.IComposer;

/**
 * Created by SpreedBlood on 2017-12-22.
 */
public abstract class ExtraDataBase {

    public void setExtraData(Object extraData) { }

    public String getWallLegacyString() {
        return "";
    }

    public abstract byte[] data();

    public abstract int getType();

    public abstract void serializeComposer(IComposer writer);

}
