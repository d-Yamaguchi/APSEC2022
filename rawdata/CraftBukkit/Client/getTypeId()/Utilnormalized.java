public static boolean isSolid(org.bukkit.block.Block b, org.bukkit.block.BlockFace direction) {
    int maxwidth = 10;
    while ((maxwidth--) >= 0) {
        int id = b.getTypeId();
        if (com.bergerkiller.bukkit.common.utils.MaterialUtil.isType(id, org.bukkit.Material.WATER, org.bukkit.Material.STATIONARY_WATER)) {
            b = b.getRelative(direction);
        } else {
            return id != 0;
        }
    } 
    return false;
}