public java.util.List<org.bukkit.block.Block> getBlocks() {
    java.util.List<org.bukkit.block.Block> blocks = new java.util.ArrayList<org.bukkit.block.Block>();
    int topBlockX = (l1.getBlockX() < l2.getBlockX()) ? l2.getBlockX() : l1.getBlockX();
    int bottomBlockX = (l1.getBlockX() > l2.getBlockX()) ? l2.getBlockX() : l1.getBlockX();
    int topBlockY = (l1.getBlockY() < l2.getBlockY()) ? l2.getBlockY() : l1.getBlockY();
    int bottomBlockY = (l1.getBlockY() > l2.getBlockY()) ? l2.getBlockY() : l1.getBlockY();
    int topBlockZ = (l1.getBlockZ() < l2.getBlockZ()) ? l2.getBlockZ() : l1.getBlockZ();
    int bottomBlockZ = (l1.getBlockZ() > l2.getBlockZ()) ? l2.getBlockZ() : l1.getBlockZ();
    for (int x = bottomBlockX; x <= topBlockX; x++) {
        for (int z = bottomBlockZ; z <= topBlockZ; z++) {
            for (int y = bottomBlockY; y <= topBlockY; y++) {
                org.bukkit.block.Block block = l1.getWorld().getBlockAt(x, y, z);
                blocks.add(block);
            }
        }
    }
    return blocks;
}