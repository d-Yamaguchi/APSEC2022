@java.lang.Override
public com.norcode.bukkit.buildinabox.BlockProcessResult processBlockUpdate(com.norcode.bukkit.buildinabox.BlockUpdate update) {
    com.norcode.bukkit.schematica.ClipboardBlock bb = update.getBlock();
    org.bukkit.Location wc = getWorldLocationFor(update.getPos());
    if (wc.equals(getBlock().getLocation())) {
        return BlockProcessResult.DISCARD;
    }
    org.bukkit.block.Block _CVAR0 = getBlock();
    org.bukkit.block.Block _CVAR5 = _CVAR0;
    org.bukkit.Location _CVAR1 = _CVAR5.getLocation();
    org.bukkit.Location _CVAR6 = _CVAR1;
    int _CVAR2 = _CVAR6.getBlockY();
    boolean _CVAR3 = wc.getBlockY() >= _CVAR2;
     _CVAR4 = _CVAR3 && (!BuildingPlan.coverableBlocks.contains(wc.getBlock().getType()));
    if () {
        cancelled = true;
        return BlockProcessResult.DISCARD;
    }
    if (checkBuildPermissions) {
        com.norcode.bukkit.buildinabox.FakeBlockPlaceEvent event = new com.norcode.bukkit.buildinabox.FakeBlockPlaceEvent(wc, player);
        plugin.getServer().getPluginManager().callEvent(event);
        if (event.isCancelled() && event.wasCancelled()) {
            cancelled = true;
            return BlockProcessResult.DISCARD;
        }
    }
    player.sendBlockChange(wc, bb.getType(), ((byte) (bb.getData())));
    return BlockProcessResult.PROCESSED;
}