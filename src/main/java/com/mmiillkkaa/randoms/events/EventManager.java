package com.mmiillkkaa.randoms.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Runs a random event on a random player on
 * a set interval.
 */
public class EventManager implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        int rand = random.nextInt(3);

        Player[] players = Bukkit.getOnlinePlayers();

        if(players.length <= 0) {
            return;
        }
        Player randomPlayer = players[random.nextInt(players.length)];

        triggerEvent(rand, randomPlayer);
    }

    /**
     * Triggers a random event.
     * @param eventNumber Number of the event
     * @param affected The player the event will affect
     * @return true if event triggered successfully, else false.
     */
    public boolean triggerEvent(int eventNumber, Player affected) {
        RandomEvent event = null;
        switch (eventNumber) {
            case 0:
                event = new DerpyCreeperEvent(affected);
            case 1:
                event = new StalkerDerpyPigEvent(affected);
                break;
            case 2:
                event = new ChickenAttackEvent(affected);
                break;
        }
        return triggerEvent(event);
    }

    public boolean triggerEvent(RandomEvent event) {
        if(event.safe()) {
            event.execute();
            return true;
        }
        return false;
    }
}
