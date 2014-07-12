package com.mmiillkkaa.randoms.world;

import com.mmiillkkaa.randoms.RandomsPlugin;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CakePopulator extends BlockPopulator {
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        if(Math.random() < (RandomsPlugin.getInstance().getConfig().getInt("PercentageChanceOfCake", 10)) / 100) {
            return;
        }
        int worldX = chunk.getX() * 16;
        int worldZ = chunk.getZ() * 16;

        Block top = getTopBlockInWorld(world, worldX + random.nextInt(16), worldZ + random.nextInt(16));
        if(top == null) {
            return;
        }

        Block above = top.getRelative(BlockFace.UP);
        if(above == null || above.getY() > world.getMaxHeight()) {
            return;
        }

        above.setType(Material.CAKE_BLOCK);
        Logger.getLogger("Minecraft").log(Level.INFO, String.format("Cake at x: %d, y: %d, z: %d", above.getX(), above.getY(), above.getZ()));
    }

    /**
     * @return Returns the topmost block that is not air in the given world at the given coordinates.
     */
    public Block getTopBlockInWorld(World world, int x, int z) {
        Block topBlock = null;
        for(int y = 0; y < world.getMaxHeight(); y++) {
            Block block = world.getBlockAt(x, y, z);
            if(block.getType() != Material.AIR) {
                topBlock = block;
            }
        }
        return topBlock;
    }
}
