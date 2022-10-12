@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    int x = block.getX();
    int y = block.getY();
    int z = block.getZ();
    org.bukkit.event.block.BlockPlaceEvent _CVAR0 = event;
    org.bukkit.block.Block block = _CVAR0.getBlock();
    org.bukkit.block.Block _CVAR1 = block;
    org.bukkit.World world = _CVAR1.getWorld();
    int dx = -1;
    for (int dx = -1; dx <= 1; ++dx) {
        int dy = -2;
        for (int dy = -2; dy <= 2; ++dy) {
            int dz = -1;
            for (int dz = -1; dz <= 1; ++dz) {
                int _CVAR3 = dx;
                int _CVAR5 = dy;
                int _CVAR7 = dz;
                org.bukkit.World _CVAR2 = world;
                int _CVAR4 = x + _CVAR3;
                int _CVAR6 = y + _CVAR5;
                int _CVAR8 = z + _CVAR7;
                org.bukkit.block.Block locked = _CVAR2.getBlockAt(_CVAR4, _CVAR6, _CVAR8);
                if (plugin.lockableBlocks.contains(locked.getType()) && (!plugin.locker.playerCanAccess(locked, player))) {
                    event.setCancelled(true);
                    player.sendMessage(plugin.formatMessage(org.bukkit.ChatColor.RED + "You cannot place blocks this close to a locked door"));
                    return;
                }
            }
        }
    }
}