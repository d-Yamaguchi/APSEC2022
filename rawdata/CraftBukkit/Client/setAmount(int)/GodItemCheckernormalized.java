public void checkOverstack(org.bukkit.inventory.ItemStack itemStack, org.bukkit.inventory.Inventory inventory, org.bukkit.Location location, java.lang.String name) {
    int amount = itemStack.getAmount();
    org.bukkit.inventory.ItemStack _CVAR1 = itemStack;
    org.bukkit.inventory.ItemStack _CVAR7 = _CVAR1;
    org.bukkit.Material _CVAR2 = _CVAR7.getType();
    org.bukkit.Material _CVAR8 = _CVAR2;
    int maxAmount = _CVAR8.getMaxStackSize();
    if (amount > maxAmount) {
        int numStacks = amount / maxAmount;
        plugin.getLogger().log(java.util.logging.Level.INFO, "Unstacked item {0} of size {1} to size {2} with {3} extra stacks in inventory of {4} size", new java.lang.Object[]{ itemStack.getType().name(), amount, left, numStacks, name });
        int _CVAR3 = maxAmount;
        int left = amount % _CVAR3;
        org.bukkit.inventory.ItemStack _CVAR0 = itemStack;
        int _CVAR4 = left;
        _CVAR0.setAmount(_CVAR4);
        for (int i = 0; i < numStacks; i++) {
            org.bukkit.inventory.ItemStack _CVAR5 = itemStack;
            org.bukkit.inventory.ItemStack newStack = _CVAR5.clone();
            org.bukkit.inventory.ItemStack _CVAR6 = newStack;
            int _CVAR9 = maxAmount;
            _CVAR6.setAmount(_CVAR9);
            int slot = inventory.firstEmpty();
            if (slot < 0) {
                location.getWorld().dropItemNaturally(location, newStack);
            } else {
                inventory.setItem(slot, newStack);
            }
        }
    }
}