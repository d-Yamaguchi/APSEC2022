// Handle a player interacting with an object
@java.lang.SuppressWarnings("deprecation")
@org.bukkit.event.EventHandler
public void onPlayerInteractObjectEvent(org.bukkit.event.player.PlayerInteractEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.event.block.Action _CVAR0 = org.bukkit.event.block.Action.LEFT_CLICK_AIR;
    boolean _CVAR1 = event.getAction() == _CVAR0;
    org.bukkit.event.block.Action _CVAR2 = org.bukkit.event.block.Action.LEFT_CLICK_BLOCK;
    boolean _CVAR3 = event.getAction() == _CVAR2;
    // Shouldn't need to do anything here
    if () {
        return;
    }
    // interacting should only be right click, #amiright?
    if () {
        return;
    }
    org.bukkit.block.Block toCheck = event.getClickedBlock();
    if (isAir(toCheck)) {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
    if (player == null) {
        return;
    }
    // If the item is not on the intractable item list
    // This is to prevent blanket permissions from stopping blocks being broken
    if (!net.worldoftomorrow.noitem.ItemChecks.isInteractable(toCheck)) {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.INTERACT_OBJECT, getBlockName(toCheck));
    net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.INTERACT_OBJECT, getBlockName(toCheck), java.lang.String.valueOf(toCheck.getData()));
    if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
        event.setCancelled(true);
        event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
        event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
        player.notifyPlayer(action);
    }
}