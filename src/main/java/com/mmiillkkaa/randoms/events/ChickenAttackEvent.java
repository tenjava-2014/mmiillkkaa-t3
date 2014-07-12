package com.mmiillkkaa.randoms.events;

import com.mmiillkkaa.randoms.RandomsPlugin;
import com.mmiillkkaa.randoms.util.AnimalFollow;
import com.mmiillkkaa.randoms.util.Cuboid;
import net.minecraft.server.v1_7_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftChicken;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * The chicken attack events spawns 1-6 chickens on silverfish around the player.
 */
public class ChickenAttackEvent extends RandomEvent {
    private Cuboid area;

    public ChickenAttackEvent(Player affected) {
        super(affected);

        Location playerLocation = affected.getEyeLocation();

        // Clone prevents undoing the addition
        area = new Cuboid(playerLocation.clone().add(-2, 0, -2), playerLocation.add(2, 2, 2));
    }

    @Override
    public boolean safe() {
        /*
         * Search the area a small radius around the player
         * To be sure the chickens won't suffocate, and can move.
         */
        for(Block block : area.getBlocks(affected.getWorld())) {
            if(block.getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
        //This effect will last a long time.
        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false);
        int numberOfChickens = new Random().nextInt(5) + 1;
        World world = affected.getWorld();
        for(int i = 0; i < numberOfChickens; i++) {
            Chicken theChicken = (Chicken) world.spawnEntity(area.getRandomLocation(world), EntityType.CHICKEN); // __THE__ chicken.

            /*
             * See the AnimalFollow class for more information.
             * The AnimalFollow task will run every 10 ticks (0.5 seconds)
             */
            AnimalFollow followTask = new AnimalFollow(((CraftChicken)theChicken).getHandle(), ((CraftPlayer)affected).getHandle());
            followTask.setBukkitTaskId(Bukkit.getScheduler()
                    .scheduleSyncRepeatingTask(RandomsPlugin.getInstance(), followTask, 0, 10));

            Silverfish attackingEntity = (Silverfish) world.spawnEntity(area.getRandomLocation(world),EntityType.SILVERFISH);
            attackingEntity.addPotionEffect(invisibility);
            theChicken.setPassenger(attackingEntity);
            attackingEntity.setTarget(affected);
            attackingEntity.setMaxHealth(2000D);
            attackingEntity.setHealth(2000D); //We don't want the silverfish to die.

            ((CraftPlayer) affected).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityDestroy(attackingEntity.getEntityId()));
        }
    }
}
