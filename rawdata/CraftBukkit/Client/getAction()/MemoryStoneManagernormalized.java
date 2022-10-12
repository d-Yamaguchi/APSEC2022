@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if (event.getPlayer().getItemInHand().getType() != org.bukkit.Material.COMPASS) {
        return;
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.event.block.Action _CVAR1 = _CVAR0.getAction();
    org.bukkit.event.block.Action _CVAR2 = org.bukkit.event.block.Action.LEFT_CLICK_BLOCK;
    boolean _CVAR3 = _CVAR1.equals(_CVAR2);
    if () {
        if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign) {
            org.bukkit.block.Sign state = ((org.bukkit.block.Sign) (event.getClickedBlock().getState()));
            za.dats.bukkit.memorystone.MemoryStoneManager.memorized = getMemoryStructureBehind(state);
            if ((za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) && (za.dats.bukkit.memorystone.MemoryStoneManager.memorized.getSign() != null)) {
                event.getPlayer().sendMessage("Memorized " + state.getLine(1));
            }
            return;
        }
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR4 = event;
    org.bukkit.event.block.Action _CVAR5 = _CVAR4.getAction();
    org.bukkit.event.block.Action _CVAR6 = org.bukkit.event.block.Action.LEFT_CLICK_BLOCK;
    boolean _CVAR7 = _CVAR5.equals(_CVAR6);
    org.bukkit.event.player.PlayerInteractEvent _CVAR9 = event;
    org.bukkit.event.block.Action _CVAR10 = _CVAR9.getAction();
    org.bukkit.event.block.Action _CVAR11 = org.bukkit.event.block.Action.LEFT_CLICK_AIR;
    boolean _CVAR12 = _CVAR10.equals(_CVAR11);
    boolean _CVAR8 = _CVAR7 || _CVAR12;
    if () {
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