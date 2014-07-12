package com.mmiillkkaa.randoms.listener;

import com.mmiillkkaa.randoms.RandomsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

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
            ((Player)ocelot.getOwner()).sendMessage(ChatColor.BLUE + "Congratulations, you have a cat now!");
        } else if(entity instanceof Zombie && entity.getEquipment().getHelmet().getType() == Material.DIRT) {
            ItemStack stack = RandomsPlugin.getInstance().getConfig().getItemStack("DerpyZombie.DropItemStack");
            entity.getWorld().dropItemNaturally(entity.getLocation(), stack);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLightningStrikeEntities(LightningStrikeEvent event) {
        if(event.isCancelled()) {
            return;
        }
        List<Entity> entities = event.getLightning().getNearbyEntities(1, 0, 1);
        for(Entity entity : entities) {
            if(entity instanceof Zombie) { //On zombie lightning strike, transform into Giant.
                ((Zombie) entity).setHealth(1D);
                Giant giant = (Giant) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.GIANT);
                giant.getEquipment().setHelmet(new ItemStack(Material.DIRT, 1));
            }
        }
    }
}
