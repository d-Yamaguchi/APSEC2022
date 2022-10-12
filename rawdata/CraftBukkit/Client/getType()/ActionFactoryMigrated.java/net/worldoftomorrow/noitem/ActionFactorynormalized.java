// Reference - org/bukkit/craftbukkit/inventory/CraftInventoryFurnace.java#L22
// Cooking also only checks with a second value
@org.bukkit.event.EventHandler
public void onPlayerCook(org.bukkit.event.inventory.InventoryClickEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.event.inventory.InventoryClickEvent _CVAR0 = event;
    org.bukkit.inventory.Inventory _CVAR1 = _CVAR0.getInventory();
    org.bukkit.event.inventory.InventoryType invType = _CVAR1.getType();
    if (invType != org.bukkit.event.inventory.InventoryType.FURNACE) {
        return;
    }
    org.bukkit.inventory.ItemStack itemToCheck;
    org.bukkit.inventory.InventoryView inventoryView = event.getView();
    org.bukkit.inventory.ItemStack onCursor = event.getCursor();
    org.bukkit.inventory.ItemStack currentIng = inventoryView.getItem(0);
    // -- Begin logic to determine what to check -- //
    // If the clicked slot type is fuel and it is empty
    if ((event.getSlotType() == org.bukkit.event.inventory.InventoryType.SlotType.FUEL) && isAir(event.getCurrentItem())) {
        // If the cursor or the ingredient is empty, return.
        if (isAir(onCursor) || isAir(currentIng)) {
            return;
        }
        itemToCheck = currentIng;
    } else {
        int clickedSlot = event.getRawSlot();
        // If click was in the ingredient slot
        if (isSlotTopInventory(clickedSlot, inventoryView) && (clickedSlot == 0)) {
            // If there is not an ingredient and there is a cursor item
            if (isAir(currentIng) && (!isAir(onCursor))) {
                itemToCheck = onCursor;
            } else {
                return;
            }
        } else {
            // Anything not a click on the fuel slot, or ingredient slot.
            // Shift clicking is the last possibility.
            if (!event.isShiftClick()) {
                return;
            }
            org.bukkit.inventory.ItemStack clickedItem = event.getCurrentItem();
            // If the clicked slot is empty, return.
            if (isAir(clickedItem)) {
                return;
            }
            itemToCheck = clickedItem;
        }
    }
    // -- End logic to determine what to check -- //
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
    if (player == null) {
        return;
    }
    short dur = itemToCheck.getDurability();
    net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.COOK, getItemName(itemToCheck), java.lang.String.valueOf(dur));
    if (dur != 0) {
        if (!player.canDoAction(actionWithData)) {
            event.setCancelled(true);
            player.notifyPlayer(actionWithData);
        }
    } else {
        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.COOK, getItemName(itemToCheck));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }
}