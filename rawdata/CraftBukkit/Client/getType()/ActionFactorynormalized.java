@org.bukkit.event.EventHandler
public void playerOpenInventoryEvent(org.bukkit.event.inventory.InventoryOpenEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.event.inventory.InventoryOpenEvent _CVAR0 = event;
    org.bukkit.inventory.Inventory _CVAR1 = _CVAR0.getInventory();
    org.bukkit.event.inventory.InventoryType invType = _CVAR1.getType();
    // These do not need to be checked for opening
    if ((invType == org.bukkit.event.inventory.InventoryType.PLAYER) || (invType == org.bukkit.event.inventory.InventoryType.CREATIVE)) {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
    // Apparently this might happen sometimes?
    if (player == null) {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.OPEN, invType.toString().replaceAll("_", "").toLowerCase());
    if (!player.canDoAction(action)) {
        event.setCancelled(true);
        player.notifyPlayer(action);
    }
}