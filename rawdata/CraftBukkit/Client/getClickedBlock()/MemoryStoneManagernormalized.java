@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if (event.getPlayer().getItemInHand().getType() != org.bukkit.Material.COMPASS) {
        return;
    }
    if (event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
        org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
        org.bukkit.block.Block _CVAR1 = _CVAR0.getClickedBlock();
        org.bukkit.block.BlockState _CVAR2 = _CVAR1.getState();
        boolean _CVAR3 = _CVAR2 instanceof org.bukkit.block.Sign;
        if () {
            org.bukkit.event.player.PlayerInteractEvent _CVAR4 = event;
            org.bukkit.block.Block _CVAR5 = _CVAR4.getClickedBlock();
            org.bukkit.block.Sign state = ((org.bukkit.block.Sign) (_CVAR5.getState()));
            za.dats.bukkit.memorystone.MemoryStoneManager.memorized = getMemoryStructureBehind(state);
            if ((za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) && (za.dats.bukkit.memorystone.MemoryStoneManager.memorized.getSign() != null)) {
                event.getPlayer().sendMessage("Memorized " + state.getLine(1));
            }
            return;
        }
    }
    if (event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) || event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_AIR)) {
        if (za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) {
            org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (za.dats.bukkit.memorystone.MemoryStoneManager.memorized.getSign()));
            if (sign == null) {
                return;
            }
            event.getPlayer().sendMessage("Recalling");
            org.bukkit.material.Sign aSign = ((org.bukkit.material.Sign) (sign.getData()));
            org.bukkit.block.Block infront = sign.getBlock().getRelative(aSign.getFacing());
            // new Location(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ());
            event.getPlayer().teleport(infront.getLocation());
        } else {
            event.getPlayer().sendMessage("No Memorized recalling");
        }
    }
    super.onPlayerInteract(event);
}