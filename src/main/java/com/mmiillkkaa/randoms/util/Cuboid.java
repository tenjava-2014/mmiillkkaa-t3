package com.mmiillkkaa.randoms.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Random;

/**
 * A cuboid selection from (x1, y1, z1) to (x2, y2, z2)
 * Assumes x1 < x2, y1 < y2, and z1 < z2
 */
public class Cuboid {
    int x1, x2, y1, y2, z1, z2;

    /**
     * A cuboid from the given coordinates.
     */
    public Cuboid(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    /**
     * A cuboid from the coordinates of the given locations.
     */
    public Cuboid(Location location1, Location location2) {
        this((int) location1.getX(), (int) location1.getY(), (int) location1.getZ(),
                (int) location2.getX(), (int) location2.getY(), (int) location2.getZ());
    }

    /**
     * @return A random location within the cuboid in the given world.
     */
    public Location getRandomLocation(World world) {
        Random random = new Random();
        Location locationBase = new Location(world, x1, y1, z1);
        int maxAddToX = Math.abs((x2 - x1)) + 1; //Adding one to prevent 0s, which random.nextInt cannot handle
        int maxAddToY = Math.abs((y2 - y1)) + 1;
        int maxAddToZ = Math.abs((z2 - z1)) + 1;
        return locationBase.add(random.nextInt(maxAddToX), random.nextInt(maxAddToY), random.nextInt(maxAddToZ));
    }

    /**
     * @return An array of blocks within the cuboid in the given world.
     */
    public Block[] getBlocks(World world) {
        Block[] blockArray = new Block[getVolume()];
        int i = 0;
        for(int x = x1; x < x2; x++) {
            for(int y = y1; y < y2; y++) {
                for(int z = z1; z < z2; z++) {
                    blockArray[i++] = world.getBlockAt(x, y, z);
                }
            }
        }
        return blockArray;
    }

    /**
     * @return The volume of the cuboid.
     */
    public int getVolume() {
        return Math.abs((x2 - x1)) * Math.abs((y2 - y1)) * Math.abs((z2 - z1));
    }
}
