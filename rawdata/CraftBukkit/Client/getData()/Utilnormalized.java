/**
 * Helper methods for making ItemStacks out of strings and ints
 */
public org.bukkit.inventory.ItemStack makeItemStack(java.lang.String name, int amount, java.lang.String data) {
    try {
        byte offset = 0;
        if (material == org.bukkit.Material.INK_SACK) {
            offset = 15;
        }
        org.bukkit.Material material = (name.matches("[0-9]+")) ? org.bukkit.Material.getMaterial(java.lang.Integer.parseInt(name)) : org.bukkit.Material.valueOf(name.toUpperCase());
        org.bukkit.DyeColor dye = (data.matches("[0-9]+")) ? org.bukkit.DyeColor.getByData(((byte) (java.lang.Math.abs(offset - java.lang.Integer.parseInt(data))))) : org.bukkit.DyeColor.valueOf(data.toUpperCase());
        org.bukkit.DyeColor _CVAR2 = dye;
        byte _CVAR3 = _CVAR2.getData();
        int _CVAR4 = offset - _CVAR3;
        org.bukkit.Material _CVAR0 = material;
        int _CVAR1 = amount;
        int _CVAR5 = ((byte) (java.lang.Math.abs(_CVAR4)));
        org.bukkit.inventory.ItemStack _CVAR6 = new org.bukkit.inventory.ItemStack(_CVAR0, _CVAR1, _CVAR5);
        return _CVAR6;
    } catch (java.lang.Exception e) {
        return null;
    }
}