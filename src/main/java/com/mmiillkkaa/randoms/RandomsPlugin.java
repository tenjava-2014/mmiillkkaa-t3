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
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new EventSchedule(), 0, 20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(command.getName().equalsIgnoreCase("triggerrandom")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by in-game players.");
                return true;
            }

            ChickenAttackEvent event = new ChickenAttackEvent((Player) sender);
            if(event.safe()) {
                event.execute();
            } else {
                sender.sendMessage(ChatColor.RED + "Event not possible.");
            }
            return true;
        }
        return false;
    }
}
