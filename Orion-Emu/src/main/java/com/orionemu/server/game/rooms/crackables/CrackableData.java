package com.orionemu.server.game.rooms.crackables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CrackableData {
    public final int itemId;
    public final int count;
    private final Map<Integer, Map.Entry<Integer, Integer>> prizes;
    private int totalChance;

    public CrackableData(ResultSet set) throws SQLException
    {
        this.itemId = set.getInt("item_id");
        this.count = set.getInt("count");

        String[] data = set.getString("prizes").split(";");
        this.prizes = new HashMap<>();

        this.totalChance = 0;
        for(int i = 0; i < data.length; i++) {
            try {
                int itemId;
                int chance = 100;

                if (data[i].contains(":") && data[i].split(":").length == 2) {
                    itemId = Integer.valueOf(data[i].split(":")[0]);
                    chance = Integer.valueOf(data[i].split(":")[1]);
                } else {
                    itemId = Integer.valueOf(data[i].replace(":", ""));
                }

                this.prizes.put(itemId, new AbstractMap.SimpleEntry<>(this.totalChance, this.totalChance + chance));
                this.totalChance += chance;
            } catch (Exception e) { }
        }
    }

    public int getRandomReward()
    {
        Random rand = new Random();
        int random = rand.nextInt(this.totalChance);

        int notFound = 0;
        for (Map.Entry<Integer, Map.Entry<Integer, Integer>> set : this.prizes.entrySet())
        {
            notFound = set.getKey();
            if (random >= set.getValue().getKey() && random < set.getValue().getValue())
            {
                return set.getKey();
            }
        }

        return notFound;
    }
}