private int calculateUnbreaking(int damageAmt) {
    org.bukkit.entity.Player _CVAR0 = player;
    org.bukkit.inventory.ItemStack _CVAR1 = _CVAR0.getItemInHand();
    org.bukkit.enchantments.Enchantment _CVAR2 = org.bukkit.enchantments.Enchantment.DURABILITY;
    int unbreakingLevel = _CVAR1.getEnchantmentLevel(_CVAR2);
    // If the item doesn't have unbreaking, or the damage amount is already nothing (or less?!), then don't do anything
    if ((unbreakingLevel == 0) || (damageAmt <= 0)) {
        return damageAmt;
    }
    int newDamageAmt = 0;
    double chance = 0.5;
    if (unbreakingLevel == 2) {
        chance = 0.6666;
    } else if (unbreakingLevel == 3) {
        chance = 0.75;
    }
    // Since unbreaking is applied for EACH point of damage, I'll do the same.
    for (int point = 0; point < damageAmt; point++) {
        if (rnd.nextDouble() >= chance) {
            newDamageAmt++;
        }
    }
    return newDamageAmt;
}