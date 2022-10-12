private static com.github.zarena.utils.LocationSer getTollableBlock(org.bukkit.Location pos) {
    org.bukkit.block.BlockFace[] verticalFaces = new org.bukkit.block.BlockFace[]{ org.bukkit.block.BlockFace.UP, org.bukkit.block.BlockFace.SELF, org.bukkit.block.BlockFace.DOWN };
    org.bukkit.block.BlockFace[] horizontalFaces = new org.bukkit.block.BlockFace[]{ org.bukkit.block.BlockFace.NORTH, org.bukkit.block.BlockFace.EAST, org.bukkit.block.BlockFace.SOUTH, org.bukkit.block.BlockFace.WEST, org.bukkit.block.BlockFace.SELF };
    for (org.bukkit.block.BlockFace bf : verticalFaces) {
        org.bukkit.block.Block current = pos.getBlock().getRelative(bf);
        for (org.bukkit.block.BlockFace bf2 : horizontalFaces) {
            org.bukkit.Material _CVAR0 = org.bukkit.Material.LEVER;
            boolean _CVAR1 = current.getRelative(bf2).getType() == _CVAR0;
            org.bukkit.Material _CVAR7 = org.bukkit.Material.WOODEN_DOOR;
            boolean _CVAR8 = current.getRelative(bf2).getType() == _CVAR7;
            boolean _CVAR2 = _CVAR1 || _CVAR8;
            org.bukkit.Material _CVAR9 = org.bukkit.Material.TRAP_DOOR;
            boolean _CVAR10 = current.getRelative(bf2).getType() == _CVAR9;
            boolean _CVAR3 = _CVAR2 || _CVAR10;
            org.bukkit.Material _CVAR11 = org.bukkit.Material.WOOD_BUTTON;
            boolean _CVAR12 = current.getRelative(bf2).getType() == _CVAR11;
            boolean _CVAR4 = _CVAR3 || _CVAR12;
            org.bukkit.Material _CVAR13 = org.bukkit.Material.STONE_BUTTON;
            boolean _CVAR14 = current.getRelative(bf2).getType() == _CVAR13;
            boolean _CVAR5 = _CVAR4 || _CVAR14;
            org.bukkit.Material _CVAR15 = org.bukkit.Material.IRON_DOOR_BLOCK;
            boolean _CVAR16 = current.getRelative(bf2).getType() == _CVAR15;
            boolean _CVAR6 = _CVAR5 || _CVAR16;
            if () {
                return com.github.zarena.utils.LocationSer.convertFromBukkitLocation(current.getRelative(bf2).getLocation());
            }
        }
    }
    return null;
}