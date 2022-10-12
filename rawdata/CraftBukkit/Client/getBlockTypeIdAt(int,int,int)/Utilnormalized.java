private static org.bukkit.Material findPortalMaterialSingle(org.bukkit.World world, int x, int y, int z) {
    org.bukkit.World _CVAR0 = world;
    int _CVAR1 = x;
    int _CVAR2 = y;
    int _CVAR3 = z;
    int typeId = _CVAR0.getBlockTypeIdAt(_CVAR1, _CVAR2, _CVAR3);
    if (typeId == com.bergerkiller.bukkit.mw.Util.STATW_ID) {
        if (com.bergerkiller.bukkit.mw.Util.isWaterPortal(world.getBlockAt(x, y, z))) {
            return org.bukkit.Material.STATIONARY_WATER;
        }
    } else if (typeId == org.bukkit.Material.PORTAL.getId()) {
        if (com.bergerkiller.bukkit.mw.Util.isNetherPortal(world.getBlockAt(x, y, z))) {
            return org.bukkit.Material.PORTAL;
        }
    } else if (typeId == org.bukkit.Material.ENDER_PORTAL.getId()) {
        if (com.bergerkiller.bukkit.mw.Util.isEndPortal(world.getBlockAt(x, y, z))) {
            return org.bukkit.Material.ENDER_PORTAL;
        }
    }
    return null;
}