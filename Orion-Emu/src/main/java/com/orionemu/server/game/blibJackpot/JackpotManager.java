package com.orionemu.server.game.blibJackpot;

import com.orionemu.server.utilities.Initialisable;

import java.util.*;

/**
 * Created by Spreed on 2017-07-22.
 */
public class JackpotManager implements Initialisable {
    private static JackpotManager instance;
    private Map<BlibJackpot, Integer> blibJackpotList;
    private Timer timer = new Timer();

    private JackpotManager() {

    }

    @Override
    public void initialize(){
        blibJackpotList = new LinkedHashMap<>();
    }

    public Map<BlibJackpot, Integer> getJackpotList(){
        return blibJackpotList;
    }

    public static JackpotManager getInstance() {
        if(instance == null)
            instance = new JackpotManager();
        return instance;
    }

    public Timer getTimer() { return this.timer; }

    public <E> E getWeightedRandom(Map<E, Integer> weights, Random random) {
        E result = null;
        double bestValue = Double.MAX_VALUE;

        for (E element : weights.keySet()) {
            double value = -Math.log(random.nextDouble()) / weights.get(element);

            if (value < bestValue) {
                bestValue = value;
                result = element;
            }
        }

        return result;
    }
}
