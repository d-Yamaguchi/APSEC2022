/* ### DAMAGE ### */
private boolean doDamage() {
    // If player is creative and shouldn't do damage, then return
    if ((player.getGameMode() == org.bukkit.GameMode.CREATIVE) && (!uk.co.gorbb.qwicktree.config.Config.get().doCreativeDamage())) {
        return true;
    }
    // Work out base damage
    int damageAmt;
    switch (tree.getDamageType()) {
        case NONE :
        default :
            damageAmt = 0;
            break;
        case NORM :
            damageAmt = ((short) (logs.size()));
            break;
        case FIXED :
            damageAmt = ((short) (tree.getDamageAmount()));
            break;
        case MULT :
            damageAmt = ((short) (tree.getDamageAmount() * logs.size()));
    }
    // Work out unbreaking
    damageAmt = calculateUnbreaking(damageAmt);
    org.bukkit.entity.Player _CVAR0 = player;
    // Check we can do this damage
    org.bukkit.inventory.ItemStack item = _CVAR0.getItemInHand();
    short newDurability = ((short) (item.getDurability() + damageAmt));// Figure out the new durability of the item

    if (newDurability > item.getType().getMaxDurability()) {
        return false;
    }// If the item cannot take this much damage, then return

    // Apply damage
    item.setDurability(newDurability);
    return true;
}