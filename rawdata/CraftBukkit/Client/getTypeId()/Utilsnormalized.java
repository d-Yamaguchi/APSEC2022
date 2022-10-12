/**
 * tests whether two item stacks are of the same type, i.e. it ignores the amounts.
 *
 * @param is1
 * 		
 * @param is2
 * 		
 * @return 
 */
public static boolean itemStacksEqual(final org.bukkit.inventory.ItemStack is1, final org.bukkit.inventory.ItemStack is2) {
    if ((is1 == null) || (is2 == null)) {
        return is1 == is2;
    }
    org.bukkit.inventory.ItemStack _CVAR0 = is2;
    org.bukkit.inventory.ItemStack _CVAR5 = _CVAR0;
    int _CVAR1 = _CVAR5.getTypeId();
    boolean _CVAR2 = is1.getTypeId() == _CVAR1;
    boolean _CVAR3 = _CVAR2 && (is1.getDurability() == is2.getDurability());
     _CVAR4 = _CVAR3 && (ch.njol.skript.Skript.isRunningMinecraft(1, 4, 5) ? is1.getItemMeta().equals(is2.getItemMeta()) : is1.getEnchantments().equals(is2.getEnchantments()));
    return _CVAR4;
}