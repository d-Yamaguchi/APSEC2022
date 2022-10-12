@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
        org.bukkit.block.Block block = event.getClickedBlock();
        org.bukkit.Material _CVAR0 = org.bukkit.Material.OBSIDIAN;
        boolean _CVAR1 = block.getType() == _CVAR0;
        if () {
            org.bukkit.Material _CVAR2 = org.bukkit.Material.BRICK;
            boolean _CVAR3 = block.getRelative(org.bukkit.block.BlockFace.EAST).getType() == _CVAR2;
            org.bukkit.Material _CVAR7 = org.bukkit.Material.BRICK;
            boolean _CVAR8 = block.getRelative(org.bukkit.block.BlockFace.WEST).getType() == _CVAR7;
            boolean _CVAR4 = _CVAR3 && _CVAR8;
            org.bukkit.Material _CVAR9 = org.bukkit.Material.BRICK;
            boolean _CVAR10 = block.getRelative(org.bukkit.block.BlockFace.NORTH).getType() == _CVAR9;
            boolean _CVAR5 = _CVAR4 && _CVAR10;
            org.bukkit.Material _CVAR11 = org.bukkit.Material.BRICK;
            boolean _CVAR12 = block.getRelative(org.bukkit.block.BlockFace.SOUTH).getType() == _CVAR11;
            boolean _CVAR6 = _CVAR5 && _CVAR12;
            if () {
                org.bukkit.entity.Player player = event.getPlayer();
                if (plugin.canCreate(player)) {
                    plugin.create(block.getLocation(), player);
                }
            }
        }
    }
}