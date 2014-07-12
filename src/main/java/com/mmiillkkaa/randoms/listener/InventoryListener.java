package com.mmiillkkaa.randoms.listener;

import com.mmiillkkaa.randoms.RandomsPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
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
        if(inventory == null || inventory.getName() == null) { //Why does this happen?
            return;
        }
        if(event.getAction() == InventoryAction.PLACE_ALL
                || event.getAction() == InventoryAction.PLACE_ONE
                || event.getAction() == InventoryAction.PLACE_SOME) {
            if(inventory.getName().equalsIgnoreCase("Derpy Zombie Drop")) {
                player.sendMessage("Derpy zombie now drop " + event.getCurrentItem().getType().name() + ".");
                RandomsPlugin.getInstance().getConfig().set("DerpyZombie.DropItemStack", event.getCurrentItem());
                event.setCancelled(true);
            }
        } else {
            if (inventory.getName().equalsIgnoreCase("Setup - mmRandoms")) {
                ItemStack clicked = event.getCurrentItem();
                if (clicked.getType() == Material.REDSTONE_BLOCK) {
                    player.sendMessage("The number of events which occur each hour can be changed in the config.yml of this plugin.");
                    player.closeInventory();
                } else if (clicked.getType() == Material.LAPIS_BLOCK) {
                    player.openInventory(RandomsPlugin.getInstance().creeperCommandInventory);
                }
                event.setCancelled(true);
            } else if (inventory.getName().equalsIgnoreCase("Derpy Zombie Drop")
                    && event.getCurrentItem().getItemMeta().getDisplayName().startsWith("Place the")) {
                event.setCancelled(true);
            }
        }
    }
}
