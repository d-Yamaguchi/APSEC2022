// Click drop (&drag?) into slot
@org.bukkit.event.EventHandler
public void onPlayerHoldItemClick(org.bukkit.event.inventory.InventoryClickEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.event.inventory.InventoryClickEvent _CVAR0 = event;
    org.bukkit.inventory.Inventory inventory = _CVAR0.getInventory();
    if (inventory.getType() != org.bukkit.event.inventory.InventoryType.CRAFTING) {
        return;
    }
    if (event.getSlotType() != org.bukkit.event.inventory.InventoryType.SlotType.QUICKBAR) {
        return;
    }
    org.bukkit.inventory.ItemStack toCheck = event.getCursor();
    if (isAir(toCheck)) {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
    if (player == null) {
        return;
    }
    if (event.getSlot() != player.getPlayer().getInventory().getHeldItemSlot()) {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
    net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
    if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
        event.setCancelled(true);
        player.notifyPlayer(action);
    }
}