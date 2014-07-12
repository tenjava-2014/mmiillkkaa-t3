package com.mmiillkkaa.randoms.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * A pig riding a cat walks and runs around with you.
 * <p/>
 * You get a free cat out of it :)
 */
public class StalkerDerpyPigEvent extends RandomEvent {
    public StalkerDerpyPigEvent(Player affected) {
        super(affected);
    }

    @Override
    public boolean safe() {
        return true;
    }

    @Override
    public void execute() {
        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false);
        Ocelot ocelot = (Ocelot) affected.getWorld().spawnEntity(affected.getLocation(), EntityType.OCELOT);
        ocelot.setCatType(Ocelot.Type.values()[new Random().nextInt(4)]); //Random cat type.
        ocelot.setOwner(affected);
        ocelot.addPotionEffect(invisibility);

        Pig derpyPig = (Pig) affected.getWorld().spawnEntity(affected.getLocation(), EntityType.PIG);
        ocelot.setPassenger(derpyPig);
        ocelot.setMaxHealth(2000D); //We don't want the ocelot to die until the owner actually owns it.
        ocelot.setHealth(2000D);
    }
}
