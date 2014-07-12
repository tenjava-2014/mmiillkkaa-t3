package com.mmiillkkaa.randoms.listener;

import com.mmiillkkaa.randoms.RandomsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.isCancelled()) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack itemClicked = event.getCurrentItem();
        if(inventory == null || inventory.getName() == null) { //Why does this happen?
            return;
        }
        if (inventory.getName().equalsIgnoreCase("Setup - mmRandoms")) {
            if (itemClicked.getType() == Material.REDSTONE_BLOCK) {
                player.sendMessage(ChatColor.BLUE + "The number of events which occur each hour can be changed in the config.yml of this plugin.");
                player.closeInventory();
            } else if (itemClicked.getType() == Material.LAPIS_BLOCK) {
                player.openInventory(RandomsPlugin.getInstance().zombieCommandInventory);
            } else if (itemClicked.getType() == Material.CAKE) {
                player.sendMessage(ChatColor.BLUE + "The percentage chance of a cake spawning in the world can be changed in the config.yml of this plugin.");
                player.closeInventory();
            }
            event.setCancelled(true);
        } else if (inventory.getName().equalsIgnoreCase("Derpy Zombie Drop")) {
            event.setCancelled(true);
            if(event.getRawSlot() > 8) {
                RandomsPlugin.getInstance().getConfig().set("DerpyZombie.DropItemStack", itemClicked.clone());
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "Derpy zombies now drop: " + ChatColor.GREEN + itemClicked.getAmount() + "x " + itemClicked.getType().name());
            }
        }
    }
}

