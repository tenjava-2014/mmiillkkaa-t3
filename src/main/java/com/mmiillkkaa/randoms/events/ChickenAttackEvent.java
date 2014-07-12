package com.mmiillkkaa.randoms.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChickenAttackEvent extends RandomEvent {
    public ChickenAttackEvent(Player affected) {
        super(affected);
    }

    @Override
    public boolean safe() {
        Location playerLocation = affected.getEyeLocation();

        /*
         * Search the area a small radius around the player
         * To be sure the chickens won't suffocate, and can move.
         */
        for(int x = (int) playerLocation.getX() - 2; x < (int) playerLocation.getX() + 2; x++) {
            for(int y = (int) playerLocation.getY() + 1; y < (int) playerLocation.getY() + 3; y++) {
                for(int z = (int) playerLocation.getZ() - 2; z < (int) playerLocation.getZ() + 2; z++) {
                    if(affected.getWorld().getBlockAt(x, y, z).getType() != Material.AIR) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void execute() {
        affected.sendMessage("Affected!");
    }
}
