public static void repairItem(org.bukkit.entity.Player player, int id, short duribility) {
    org.bukkit.inventory.Inventory inv = player.getInventory();
    for (int i = 0; i < inv.getSize(); i++) {
        org.bukkit.inventory.ItemStack is = inv.getItem(i);
        if (is == null) {
            continue;
        }
        int _CVAR0 = id;
        boolean _CVAR1 = is.getTypeId() == _CVAR0;
        if () {
            is.setDurability(duribility);
            inv.setItem(i, is);
        }
    }
}