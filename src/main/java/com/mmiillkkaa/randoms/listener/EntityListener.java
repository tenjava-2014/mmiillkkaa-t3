package com.mmiillkkaa.randoms.listener;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity instanceof Chicken && entity.getPassenger() != null
                && entity.getPassenger().getType() == EntityType.SILVERFISH) {
            //When the chicken dies, we want to get rid of the silverfish.
            Silverfish silverfish = (Silverfish) entity.getPassenger();
            silverfish.setHealth(0D);
        }
    }
}
