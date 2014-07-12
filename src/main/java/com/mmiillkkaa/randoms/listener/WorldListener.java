package com.mmiillkkaa.randoms.listener;

import com.mmiillkkaa.randoms.world.CakePopulator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WorldListener implements Listener {
    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        Logger.getLogger("Minecraft").log(Level.INFO, "We were actually called!");
        event.getWorld().getPopulators().add(new CakePopulator());
    }
}
