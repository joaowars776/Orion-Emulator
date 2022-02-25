package com.orionemu.server.boot.utils.gui;

import com.orionemu.api.stats.OrionStats;
import com.orionemu.server.boot.Orion;
import com.orionemu.server.tasks.OrionThreadManager;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class OrionGui extends JFrame {
    private JPanel mainPanel;
    private JLabel playersOnline;
    private JLabel roomsLoaded;
    private JLabel uptime;

    public OrionGui() {
        super("Orion Server - " + Orion.getBuild());

        this.pack();

        if(mainPanel == null) {
            return;
        }

        this.setContentPane(mainPanel);

        this.setSize(new Dimension(350, 120));
        this.setResizable(false);

        OrionThreadManager.getInstance().executePeriodic(this::update, 0, 500, TimeUnit.MILLISECONDS);
    }

    public void update() {
        final OrionStats stats = Orion.getStats();

        playersOnline.setText("Players online: " + stats.getPlayers());
        roomsLoaded.setText("Rooms loaded: " + stats.getRooms());
        uptime.setText("Uptime: " + stats.getUptime());
    }
}
