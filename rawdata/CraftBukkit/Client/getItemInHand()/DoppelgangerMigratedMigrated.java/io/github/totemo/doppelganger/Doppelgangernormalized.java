// --------------------------------------------------------------------------
/**
 * Vanilla Minecraft doesn't always drop equipment when the drop chance is 1.0
 * (or more). Try to work around that by moving the equipment into the drops
 * if it is not already there.
 *
 * In Bukkit versions prior to 1.7.9, the equipment was not part of the drops
 * list in the EntityDeathEvent. Bukkit fixed that issue with the API, but had
 * to retain vanilla's handling of drop probabilities, which is still faulty.
 *
 * This handler will process any entity death, but naturally spawned monsters
 * probably won't have a (near) 1.0 drop chance for the their equipment, and
 * in any case the relocation of the equipment to drops should be benign.
 */
@org.bukkit.event.EventHandler(ignoreCancelled = true)
public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
    final float NEAR_UNITY = 0.999F;
    boolean forcedDrops = false;
    if (event.getEntity() instanceof org.bukkit.entity.Creature) {
        java.util.List<org.bukkit.inventory.ItemStack> drops = event.getDrops();
        if (equipment.getHelmetDropChance() > NEAR_UNITY) {
            forcedDrops = true;
            org.bukkit.inventory.ItemStack helmet = equipment.getHelmet();
            if ((helmet != null) && (!drops.contains(helmet))) {
                drops.add(helmet);
                equipment.setHelmet(null);
            }
        }
        if (equipment.getChestplateDropChance() > NEAR_UNITY) {
            forcedDrops = true;
            org.bukkit.inventory.ItemStack chestplate = equipment.getChestplate();
            if ((chestplate != null) && (!drops.contains(chestplate))) {
                drops.add(chestplate);
                equipment.setChestplate(null);
            }
        }
        if (equipment.getLeggingsDropChance() > NEAR_UNITY) {
            forcedDrops = true;
            org.bukkit.inventory.ItemStack leggings = equipment.getLeggings();
            if ((leggings != null) && (!drops.contains(leggings))) {
                drops.add(leggings);
                equipment.setLeggings(null);
            }
        }
        if (equipment.getBootsDropChance() > NEAR_UNITY) {
            forcedDrops = true;
            org.bukkit.inventory.ItemStack boots = equipment.getBoots();
            if ((boots != null) && (!drops.contains(boots))) {
                drops.add(boots);
                equipment.setBoots(null);
            }
        }
        org.bukkit.event.entity.EntityDeathEvent _CVAR0 = event;
        org.bukkit.entity.LivingEntity _CVAR1 = _CVAR0.getEntity();
        org.bukkit.inventory.EntityEquipment equipment = _CVAR1.getEquipment();
        if (equipment.getItemInHandDropChance() > NEAR_UNITY) {
            forcedDrops = true;
            org.bukkit.inventory.EntityEquipment _CVAR2 = equipment;
            org.bukkit.inventory.ItemStack itemInHand = _CVAR2.getItemInHand();
            if ((itemInHand != null) && (!drops.contains(itemInHand))) {
                drops.add(itemInHand);
                equipment.setItemInHand(null);
            }
        }
    }
    // If a unity drop chance was specified, it's probably a Doppelganger.
    // Also require a custom name, since 'special' mobs that pick up items
    // will always drop them too.
    // Log the drops for verification purposes.
    if (forcedDrops && (event.getEntity().getCustomName() != null)) {
        org.bukkit.Location loc = event.getEntity().getLocation();
        java.lang.StringBuilder drops = new java.lang.StringBuilder();
        drops.append("At (").append(loc.getBlockX()).append(',');
        drops.append(loc.getBlockY()).append(',');
        drops.append(loc.getBlockZ()).append(") drops:");
        for (org.bukkit.inventory.ItemStack item : event.getDrops()) {
            drops.append(' ');
            drops.append(item);
        }
        getLogger().info(drops.toString());
    }
}// onEntityDeath
