@java.lang.Override
public boolean triggerButHasUplink(org.bukkit.event.Event event) {
    if (event instanceof org.bukkit.event.player.PlayerInteractEvent) {
        org.bukkit.event.player.PlayerInteractEvent playerInteractEvent = ((org.bukkit.event.player.PlayerInteractEvent) (event));
        org.bukkit.event.player.PlayerInteractEvent _CVAR0 = playerInteractEvent;
        org.bukkit.event.block.Action action = _CVAR0.getAction();
        org.bukkit.entity.Player player = playerInteractEvent.getPlayer();
        if ((action == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) || (action == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            changeMagicSpell(player);
            return true;
        }
        if ((action == org.bukkit.event.block.Action.LEFT_CLICK_AIR) || (action == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
            boolean playerHasWandInHand = checkWandInHand(player);
            return playerHasWandInHand;
        }
    }
    return false;
}