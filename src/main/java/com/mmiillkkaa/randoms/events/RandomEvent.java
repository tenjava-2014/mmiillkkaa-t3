package com.mmiillkkaa.randoms.events;

import org.bukkit.entity.Player;

public abstract class RandomEvent {
    protected Player affected;

    public RandomEvent(Player affected) {
        this.affected = affected;
    }

    /**
     * Check if the environment won't ruin the event
     *
     * @return If the event can be executed
     */
    public abstract boolean safe();

    /**
     * Executes the event on the player.
     */
    public abstract void execute();
}
