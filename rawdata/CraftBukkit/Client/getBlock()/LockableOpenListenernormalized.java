@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    org.bukkit.event.block.BlockPlaceEvent _CVAR0 = event;
    org.bukkit.block.Block block = _CVAR0.getBlock();
    org.bukkit.World world = block.getWorld();
    int x = block.getX();
    int y = block.getY();
    int z = block.getZ();
    for (int dx = -1; dx <= 1; ++dx) {
        for (int dy = -2; dy <= 2; ++dy) {
            for (int dz = -1; dz <= 1; ++dz) {
                org.bukkit.block.Block locked = world.getBlockAt(x + dx, y + dy, z + dz);
                if (plugin.lockableBlocks.contains(locked.getType()) && (!plugin.locker.playerCanAccess(locked, player))) {
                    event.setCancelled(true);
                    player.sendMessage(plugin.formatMessage(org.bukkit.ChatColor.RED + "You cannot place blocks this close to a locked door"));
                    return;
                }
            }
        }
    }
}