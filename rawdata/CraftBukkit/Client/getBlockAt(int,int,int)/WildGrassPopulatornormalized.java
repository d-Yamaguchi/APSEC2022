@java.lang.Override
public void populate(org.bukkit.World world, java.util.Random random, org.bukkit.Chunk source) {
    if (random.nextInt(100) > chance) {
        return;
    }
    // Determine the size/steps
    int numSteps = random.nextInt((maxSteps - minSteps) + 1) + minSteps;
    int _CVAR1 = 4;
    int x = source.getX() << _CVAR1;
    int _CVAR4 = _CVAR1;
    int x = source.getX() << _CVAR4;
    int _CVAR6 = 4;
    int z = source.getZ() << _CVAR6;
    org.bukkit.World _CVAR3 = world;
    int _CVAR5 = x;
    int _CVAR7 = z;
    int y = _CVAR3.getHighestBlockYAt(_CVAR5, _CVAR7);
    int _CVAR9 = _CVAR6;
    int z = source.getZ() << _CVAR9;
    // Random walking
    for (int i = 0; i < numSteps; i++) {
        x += random.nextInt(3) - 1;
        z += random.nextInt(3) - 1;
        y = world.getHighestBlockYAt(x, z);
        org.bukkit.World _CVAR0 = world;
        int _CVAR2 = x;
        int _CVAR8 = y;
        int _CVAR10 = z;
        org.bukkit.block.Block b = _CVAR0.getBlockAt(_CVAR2, _CVAR8, _CVAR10);
        if ((b.getRelative(0, -1, 0).getType() == org.bukkit.Material.GRASS) && (b.getTypeId() == 0)) {
            b.setType(org.bukkit.Material.LONG_GRASS);
            b.setData(((byte) (1)));// The default wild grass type

        }
    }
}