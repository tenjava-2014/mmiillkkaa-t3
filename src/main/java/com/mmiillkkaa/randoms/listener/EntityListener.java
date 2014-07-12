package com.mmiillkkaa.randoms.listener;

import com.mmiillkkaa.randoms.RandomsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if(entity instanceof Chicken && entity.getPassenger() != null
                && entity.getPassenger().getType() == EntityType.SILVERFISH) {
            //When the chicken dies, we want to get rid of the silverfish.
            Silverfish silverfish = (Silverfish) entity.getPassenger();
            silverfish.setHealth(0D);
        } else if(entity instanceof Pig && entity.getVehicle() != null
                && entity.getVehicle().getType() == EntityType.OCELOT) {
            Ocelot ocelot = (Ocelot) entity.getVehicle();
            ocelot.setMaxHealth(10D);
            ocelot.setHealth(10D);
            ocelot.removePotionEffect(PotionEffectType.INVISIBILITY);
            ((Player)ocelot.getOwner()).sendMessage("Congratulations, you have a cat now!");
        } else if(entity instanceof Zombie && entity.getEquipment().getHelmet().getType() == Material.DIRT) {
            ItemStack stack = RandomsPlugin.getInstance().getConfig().getItemStack("DerpyZombie.DropItemStack");
            entity.getWorld().dropItemNaturally(entity.getLocation(), stack);
        }
    }
}
