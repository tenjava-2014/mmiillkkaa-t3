package com.mmiillkkaa.randoms;

import com.mmiillkkaa.randoms.events.ChickenAttackEvent;
import com.mmiillkkaa.randoms.events.EventSchedule;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomsPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        int eventsPerHour = getConfig().getInt("EventsPerHour", 1);
        int delay = (20 * 60 * 60)/eventsPerHour; // Ticks Per Second * Seconds per Minute * Minutes per Hour all
                                                  // Divided by the number of events in an hour gives us
                                                  // The delay between each event.
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new EventSchedule(), 0, delay);
    }

    @Override
    public void onDisable() {
        saveConfig();
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

            ChickenAttackEvent event = new ChickenAttackEvent((Player) sender);
            if(event.safe()) {
                event.execute();
            } else {
                sender.sendMessage(ChatColor.RED + "Event not possible.");
            }
            return true;
        } else if(command.getName().equalsIgnoreCase("eventsperhour")) {
            if(args.length > 0) {
                if(!sender.hasPermission("mmrandoms.timer.set")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to set the number of events" +
                            " per hour.");
                    return true;
                }

                int newEventsPerHour;

                try {
                    newEventsPerHour = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "That is not a number, and example of an acceptable number" +
                            " would be 5 or 12345");
                    return true;
                }

                getConfig().set("EventsPerHour", newEventsPerHour);
                sender.sendMessage(newEventsPerHour + " events will now occur each hour.");
            } else {
                if(!sender.hasPermission("mmrandoms.timer.get")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to see the number of events" +
                            " per hour. (Maybe the admin wants it to be a surprise!)");
                    return true;
                }
                sender.sendMessage(getConfig().getInt("EventsPerHour", 1) + " events occur each hour.");
            }
            return true;
        }
        return false;
    }
}
