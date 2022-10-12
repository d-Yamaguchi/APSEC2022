@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGH)
public void onEntityDeath(final org.bukkit.event.entity.EntityDeathEvent event) {
    // Skip if the entity that died is not a player
    if (!(event.getEntity() instanceof org.bukkit.entity.Player)) {
        return;
    }
    // If the player already has a death on record, drop the items
    net.robinjam.bukkit.keepitems.Death death = deaths.get(player);
    if (death != null) {
        death.drop();
    }
    org.bukkit.inventory.ItemStack[] inventoryContents = new org.bukkit.inventory.ItemStack[0];
    org.bukkit.inventory.ItemStack[] armorContents = new org.bukkit.inventory.ItemStack[0];
    int experience = 0;
    if (player.hasPermission("keep-items.items")) {
        inventoryContents = player.getInventory().getContents();
        armorContents = player.getInventory().getArmorContents();
        // Don't drop any items at the death location
        event.getDrops().clear();
    }
    org.bukkit.event.entity.EntityDeathEvent _CVAR0 = event;
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (_CVAR0.getEntity()));
    if (player.hasPermission("keep-items.experience")) {
        if (player.hasPermission("keep-items.progress")) {
            org.bukkit.entity.Player _CVAR1 = player;
            int _CVAR2 = _CVAR1.getTotalExperience();
            experience = _CVAR2;
        } else {
            experience = calcExperience(player.getLevel());
        }
        // Don't drop any experience at the death location
        event.setDroppedExp(0);
    }
    // Register the death event
    deaths.put(player, new net.robinjam.bukkit.keepitems.Death(this, player.getLocation(), inventoryContents, armorContents, experience));
}