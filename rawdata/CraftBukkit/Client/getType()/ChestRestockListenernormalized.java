@org.bukkit.event.EventHandler
public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.block.Block block = event.getBlock();
    org.bukkit.Material _CVAR0 = org.bukkit.Material.CHEST;
    boolean _CVAR1 = block.getType() != _CVAR0;
    if () {
        return;
    }
}