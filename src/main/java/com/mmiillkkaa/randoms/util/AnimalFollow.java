package com.mmiillkkaa.randoms.util;

import net.minecraft.server.v1_7_R3.EntityAnimal;
import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.PathEntity;
import org.bukkit.Bukkit;

/**
 * Allows for animals to "target" players
 */
public class AnimalFollow implements Runnable {
    private EntityAnimal animal;
    private EntityHuman player;
    private int taskId;

    /**
     * @param animal The animal targetting
     * @param player The target player
     */
    public AnimalFollow(EntityAnimal animal, EntityHuman player) {
        this.animal = animal;
        this.player = player;
    }

    /**
     * Sets the scheduler task id so we can stop this later.
     *
     * @param id Task ID
     */
    public void setBukkitTaskId(int id) {
        taskId = id;
    }

    @Override
    public void run() {
        if(!animal.isAlive()) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        PathEntity newPath = animal.getNavigation().a(player); //Path to player
        animal.getNavigation().a(newPath, 1D); //Navigate the path
    }
}
