package com.orionemu.server.game.groups.types.components;

import com.orionemu.server.game.groups.types.Group;

public interface GroupComponent {
    public Group getGroup();

    public void dispose();
}
