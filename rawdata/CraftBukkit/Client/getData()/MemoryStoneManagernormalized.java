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
            za.dats.bukkit.memorystone.MemoryStone _CVAR0 = za.dats.bukkit.memorystone.MemoryStoneManager.memorized;
            org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (_CVAR0.getSign()));
            org.bukkit.block.Sign _CVAR1 = sign;
            org.bukkit.material.Sign aSign = ((org.bukkit.material.Sign) (_CVAR1.getData()));
            org.bukkit.block.Block infront = sign.getBlock().getRelative(aSign.getFacing());
            // new Location(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ());
            event.getPlayer().teleport(infront.getLocation());
        } else {
            event.getPlayer().sendMessage("No Memorized recalling");
        }
    }
    super.onPlayerInteract(event);
}