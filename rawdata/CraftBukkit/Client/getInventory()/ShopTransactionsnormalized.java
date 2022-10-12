public org.bukkit.inventory.ItemStack SellItem(org.bukkit.inventory.ItemStack item_stack) {
    regalowl.hyperconomy.hyperobject.HyperObject ho = hyperAPI.getHyperObject(item_stack, hyperAPI.getShop(shopname).getEconomy(), hyperAPI.getShop(shopname));
    // HyperObject ho = hyperAPI.getHyperObject(item_name, hyperAPI.getShop(shopname).getEconomy(), hyperAPI.getShop(shopname));
    if (ho == null) {
        return null;
    }
    int amount = item_stack.getAmount();
    java.lang.String item_name = ho.getDisplayName().toLowerCase();
    if (shopmenu.getShopStock().display_names.contains(item_name) && hyperAPI.getShop(shopname).isTradeable(ho)) {
        org.bukkit.entity.Player _CVAR0 = player;
        org.bukkit.inventory.PlayerInventory _CVAR1 = _CVAR0.getInventory();
        org.bukkit.inventory.ItemStack _CVAR2 = item_stack;
        _CVAR1.addItem(_CVAR2);
        regalowl.hyperconomy.transaction.TransactionResponse response = hyperAPI.sell(player, ho, amount, hyperAPI.getShop(shopname));
        response.sendMessages();
        return new org.bukkit.inventory.ItemStack(org.bukkit.Material.AIR);
    }
    return new org.bukkit.inventory.ItemStack(item_stack);
}