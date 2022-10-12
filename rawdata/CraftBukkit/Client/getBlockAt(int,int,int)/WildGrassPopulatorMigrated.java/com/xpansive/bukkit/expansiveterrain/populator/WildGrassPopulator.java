package com.xpansive.bukkit.expansiveterrain.populator;
public class WildGrassPopulator extends org.bukkit.generator.BlockPopulator {
    private int minSteps;

    private int maxSteps;

    private int chance;

    public WildGrassPopulator(int minSteps, int maxSteps, int chance) {
        this.minSteps = minSteps;
        this.maxSteps = maxSteps;
        this.chance = chance;
    }

    @java.lang.Override
    public void populate(org.bukkit.World world, java.util.Random random, org.bukkit.Chunk source) {
        if (random.nextInt(100) > chance)
            return;

        // Determine the size/steps
        int numSteps = random.nextInt((maxSteps - minSteps) + 1) + minSteps;
        int x = source.getX() << 4;
        int x = source.getX() << 4;
        int z = source.getZ() << 4;
        int y = world.getHighestBlockYAt(x, z);
        int z = source.getZ() << 4;
        // Random walking
        for (int i = 0; i < numSteps; i++) {
            x += random.nextInt(3) - 1;
            z += random.nextInt(3) - 1;
            y = world.getHighestBlockYAt(x, z);
            DirectWorld world = state.getDirectWorld();
            Block b = world.getHighestBlockY(x, z);
            if ((b.getRelative(0, -1, 0).getType() == org.bukkit.Material.GRASS) && (b.getTypeId() == 0)) {
                b.setType(org.bukkit.Material.LONG_GRASS);
                b.setData(((byte) (1)));// The default wild grass type

            }
        }
    }
}