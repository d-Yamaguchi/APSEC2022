@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if (event.getPlayer().getItemInHand().getType() != org.bukkit.Material.COMPASS) {
        return;
    }
    if (event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
        if (event.getClickedBlock().getState() instanceof org.bukkit.block.Sign) {
            org.bukkit.block.Sign state = ((org.bukkit.block.Sign) (event.getClickedBlock().getState()));
            za.dats.bukkit.memorystone.MemoryStoneManager.memorized = getMemoryStructureBehind(state);
            if ((za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) && (za.dats.bukkit.memorystone.MemoryStoneManager.memorized.getSign() != null)) {
                event.getPlayer().sendMessage("Memorized " + state.getLine(1));
            }
            return;
        }
    }
    if (event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) || event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_AIR)) {
        if (za.dats.bukkit.memorystone.MemoryStoneManager.memorized != null) {
            if (sign == null) {
                return;
            }
            event.getPlayer().sendMessage("Recalling");
            org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
            za.dats.bukkit.memorystone.MemoryStone _CVAR2 = za.dats.bukkit.memorystone.MemoryStoneManager.memorized;
            org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (_CVAR2.getSign()));
            org.bukkit.block.Sign _CVAR3 = sign;
            za.dats.bukkit.memorystone.MemoryStone _CVAR5 = _CVAR2;
            org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (_CVAR5.getSign()));
            org.bukkit.block.Sign _CVAR6 = sign;
            org.bukkit.material.Sign aSign = ((org.bukkit.material.Sign) (_CVAR6.getData()));
            org.bukkit.material.Sign _CVAR7 = aSign;
            org.bukkit.block.Block _CVAR4 = _CVAR3.getBlock();
            org.bukkit.block.BlockFace _CVAR8 = _CVAR7.getFacing();
            org.bukkit.block.Block infront = _CVAR4.getRelative(_CVAR8);
            org.bukkit.block.Block _CVAR9 = infront;
            org.bukkit.entity.Player _CVAR1 = _CVAR0.getPlayer();
            org.bukkit.Location _CVAR10 = _CVAR9.getLocation();
            // new Location(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ());
            _CVAR1.teleport(_CVAR10);
        } else {
            event.getPlayer().sendMessage("No Memorized recalling");
        }
    }
    super.onPlayerInteract(event);
}