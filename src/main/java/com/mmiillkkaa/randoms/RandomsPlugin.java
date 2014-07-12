package com.mmiillkkaa.randoms;

import com.mmiillkkaa.randoms.events.EventManager;
import com.mmiillkkaa.randoms.listener.EntityListener;
import com.mmiillkkaa.randoms.listener.InventoryListener;
import com.mmiillkkaa.randoms.listener.WorldListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class RandomsPlugin extends JavaPlugin {
    private static RandomsPlugin instance;
    private EventManager eventManager;
    /**
     * Inventory containing 2 options: set events per hour, or setup creeper item.
     */
    private Inventory setupCommandInventory;
    /**
     * Inventory which allows the user to click which item to drop.
     */
    public Inventory zombieCommandInventory;

    @Override
    public void onEnable() {
        /*
         * Schedule events
         */
        int eventsPerHour = getConfig().getInt("EventsPerHour", 1);
        int delay = (20 * 60 * 60)/eventsPerHour; // Ticks Per Second * Seconds per Minute * Minutes per Hour all
                                                  // Divided by the number of events in an hour gives us
                                                  // The delay between each event.
        eventManager = new EventManager();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, eventManager, 0, delay);

        /*
         * Register listeners
         */
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        instance = this;

        /*
         * Initial setup
         */
        if(getConfig().getItemStack("DerpyZombie.DropItemStack") == null) {
            getConfig().set("DerpyZombie.DropItemStack", new ItemStack(Material.APPLE, 1));
        }

        /*
         * Setup the setup command's inventory UI.
         */
        setupCommandInventory = getServer().createInventory(null, 27, "Setup - mmRandoms");
        ItemStack choiceSetEventsPerHour = new ItemStack(Material.REDSTONE_BLOCK, 1);
        ItemStack choiceSetDerpyZombieDropItem = new ItemStack(Material.LAPIS_BLOCK, 1);
        ItemStack choiceSetCakeInterval = new ItemStack(Material.CAKE, 1);
        setItemName(choiceSetEventsPerHour, "Set number of events to occur each hour.");
        setItemName(choiceSetDerpyZombieDropItem, "Set drop of the derpy zombie.");
        setItemName(choiceSetCakeInterval, "Set percentage chance of cake in the world.");
        for(int i = 0; i < 27; i++) {
            int x = i % 9;
            if(x < 3) {
                setupCommandInventory.setItem(i, choiceSetEventsPerHour);
            } else if (x > 3 && x < 5) {
                setupCommandInventory.setItem(i, choiceSetCakeInterval);
            } else {
                setupCommandInventory.setItem(i, choiceSetDerpyZombieDropItem);
            }
        }

        zombieCommandInventory = getServer().createInventory(null, 9, "Derpy Zombie Drop");
        ItemStack tipPlaceItem = new ItemStack(Material.EMERALD, 1);
        setItemName(tipPlaceItem, "Click an item.");
        setStackLore(tipPlaceItem, new String[] {"The stack you click", "will be the stack", "the zombie drops."});
        zombieCommandInventory.setItem(4, tipPlaceItem);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    /**
     * @return The running instance.
     */
    public static RandomsPlugin getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(command.getName().equalsIgnoreCase("triggerrandom")) {
            if(!(sender instanceof Player)) { // Unless Console is a floating thing in the sky, this isn't happening.
                sender.sendMessage("This command can only be used by in-game players.");
                return true;
            }

            if(!sender.hasPermission("mmrandoms.triggerevent.self")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to trigger events on yourself.");
            }

            if(args.length == 0) {
                String[] messages = new String[] {"Events:",
                                                  "  0 = Derpy Zombie",
                                                  "  1 = Stalker Derpy Pig",
                                                  "  2 = Chicken Attack"};
                sender.sendMessage(messages);
                return true;
            }

            Player p = (Player) sender;
            int eventNumber;
            try {
                eventNumber = Integer.parseInt(args[0]);
                if(eventNumber > 2) {
                    sender.sendMessage(ChatColor.RED + "" + eventNumber + " is not a valid event. Use /triggerrandom to get a list of" +
                            " events.");
                    return true;
                }
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "That is not a number, and example of an acceptable number" +
                        " would be 5 or 12345");
                return true;
            }

            if(!eventManager.triggerEvent(eventNumber, p)) {
                p.sendMessage(ChatColor.RED + "Could not run the event in your environment.");
            }
            return true;
        } else if(command.getName().equalsIgnoreCase("randomssetup")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("This can only be run be in-game players.");
                return true;
            }
            Player p = (Player) sender;
            p.openInventory(setupCommandInventory);
            return true;
        }
        return false;
    }

    /**
     * Sets the ItemStack's name.
     * @param stack ItemStack to rename.
     * @param name New name.
     */
    private void setItemName(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
    }

    /**
     * Sets the ItemStack's lore
     * @param stack ItemStack to edit
     * @param lines New lore
     */
    private void setStackLore(ItemStack stack, String lines[]) {
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(Arrays.asList(lines));
        stack.setItemMeta(meta);
    }
}
