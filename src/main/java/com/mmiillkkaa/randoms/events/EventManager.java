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

        RandomEvent event = null;

        /*
         * rand contains a number between 0 and 2.
         * There is a 1/3 chance for a specific event
         * to occur.
         */
        switch (rand) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                event = new ChickenAttackEvent(randomPlayer);
                break;
        }

        if(event != null && event.safe()) {
            event.execute();
        }
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
            case 1:
                event = null;
                break;
            case 2:
                event = new ChickenAttackEvent(affected);
                break;
        }
        return triggerEvent(event, affected);
    }

    public boolean triggerEvent(RandomEvent event, Player affected) {
        if(event.safe()) {
            event.execute();
            return true;
        }
        return false;
    }
}
