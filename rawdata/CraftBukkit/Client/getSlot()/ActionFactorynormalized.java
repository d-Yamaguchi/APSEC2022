// Click drop (&drag?) into slot
@org.bukkit.event.EventHandler
public void onPlayerHoldItemClick(org.bukkit.event.inventory.InventoryClickEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.inventory.Inventory inventory = event.getInventory();
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
    if (player == null) {
        return;
    }
    org.bukkit.event.inventory.InventoryClickEvent _CVAR0 = event;
    org.bukkit.entity.HumanEntity _CVAR1 = _CVAR0.getWhoClicked();
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(_CVAR1);
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer _CVAR2 = player;
     _CVAR3 = _CVAR2.getPlayer();
     _CVAR4 = _CVAR3.getInventory();
     _CVAR5 = _CVAR4.getHeldItemSlot();
     _CVAR6 = event.getSlot() != _CVAR5;
    if () {
        return;
    }
    net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
    net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
    if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
        event.setCancelled(true);
        player.notifyPlayer(action);
    }
}