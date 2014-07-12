package com.mmiillkkaa.randoms.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Runs a random event on a random player on
 * a set interval.
 */
public class EventSchedule implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        int rand = random.nextInt(100);

        Player[] players = Bukkit.getOnlinePlayers();

        if(players.length <= 0) {
            return;
        }
        Player randomPlayer = players[random.nextInt(players.length)];

        RandomEvent event = null;

        /*
         * rand contanis a number between 0 and 99.
         * There is a 1/3 chance for a specific event
         * to occur.
         */
        switch (rand % 3) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                event = new ChickenAttackEvent(randomPlayer);
                break;
        }

        if(event != null && event.safe()){
            event.execute();
            Logger.getLogger("Minecraft").log(Level.INFO, "Ran: " + randomPlayer.getName());
        } else {
            Logger.getLogger("Minecraft").log(Level.INFO, "Unran: " + randomPlayer.getName());
        }
    }
}
