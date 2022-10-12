@org.bukkit.event.EventHandler(ignoreCancelled = true)
public void onInventoryMoveItem(org.bukkit.event.inventory.InventoryMoveItemEvent event) {
    if (event.getDestination().getHolder() instanceof org.bukkit.block.Furnace) {
        org.bukkit.block.Furnace f = ((org.bukkit.block.Furnace) (event.getDestination().getHolder()));
        org.bukkit.event.inventory.InventoryMoveItemEvent _CVAR1 = event;
        org.bukkit.inventory.ItemStack _CVAR2 = _CVAR1.getItem();
        java.util.HashMap<org.bukkit.Material, com.norcode.bukkit.salvagesmelter.SmeltRecipe> _CVAR0 = recipeMap;
        org.bukkit.Material _CVAR3 = _CVAR2.getType();
        boolean _CVAR4 = _CVAR0.containsKey(_CVAR3);
        if () {
            if (!enabledInWorld(f.getWorld())) {
                event.setCancelled(true);
            }
        }
    }
}