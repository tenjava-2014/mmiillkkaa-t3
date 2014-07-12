package com.mmiillkkaa.randoms.events;

import com.mmiillkkaa.randoms.util.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

/**
 * A creeper with dirt on its head. Doesn't explode. Drops a configurable item.
 */
public class DerpyZombieEvent extends RandomEvent {
    private Cuboid area;

    public DerpyZombieEvent(Player affected) {
        super(affected);
        area = new Cuboid(affected.getLocation().clone().add(-3, 0, -3), affected.getLocation().add(3, 1, 3));
    }

    @Override
    public boolean safe() {
        for(Block block : area.getBlocks(affected.getWorld())) {
            if(block.getType() != Material.AIR) {
                if(safeTwoBlocksAbove(block)) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
        World world = affected.getWorld();
        Location creeperSpawn = area.getRandomLocation(world);
        if(creeperSpawn.getBlock().getType() != Material.AIR && safeTwoBlocksAbove(creeperSpawn.getBlock())) {
            creeperSpawn.add(0, 1, 0);
        }
        Zombie zombie = (Zombie) world.spawnEntity(creeperSpawn, EntityType.ZOMBIE);
        zombie.getEquipment().setHelmet(new ItemStack(Material.DIRT, 1));
        zombie.setTarget(affected);
    }

    /**
     * Checks if the two blocks above the given block are air
     * @return true if the blocks are air, else false.
     */
    private boolean safeTwoBlocksAbove(Block block) {
        Block blockAbove = block.getRelative(BlockFace.UP);
        return blockAbove.getType() == Material.AIR && blockAbove.getRelative(BlockFace.UP).getType() == Material.AIR;
    }
}
