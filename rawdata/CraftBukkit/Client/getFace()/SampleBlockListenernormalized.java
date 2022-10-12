@java.lang.Override
public void onBlockPhysics(org.bukkit.event.block.BlockPhysicsEvent event) {
    org.bukkit.event.block.BlockPhysicsEvent _CVAR0 = event;
    org.bukkit.block.Block block = _CVAR0.getBlock();
    if ((block.getType() == org.bukkit.Material.SAND) || (block.getType() == org.bukkit.Material.GRAVEL)) {
        org.bukkit.block.Block _CVAR1 = block;
        org.bukkit.block.BlockFace _CVAR2 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.Block above = _CVAR1.getFace(_CVAR2);
        if (above.getType() == org.bukkit.Material.IRON_BLOCK) {
            event.setCancelled(true);
        }
    }
}