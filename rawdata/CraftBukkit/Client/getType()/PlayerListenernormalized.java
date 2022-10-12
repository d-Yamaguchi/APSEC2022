/**
 * Returns if the block is a chest or an other inventory-holder, that can hold items.
 */
private boolean isChest(org.bukkit.block.Block block) {
    org.bukkit.Material _CVAR0 = org.bukkit.Material.ENDER_CHEST;
    boolean _CVAR1 = block.getType() == _CVAR0;
    boolean _CVAR2 = (block.getState() instanceof org.bukkit.inventory.InventoryHolder) || _CVAR1;
    org.bukkit.Material _CVAR4 = org.bukkit.Material.BEACON;
    boolean _CVAR5 = block.getType() == _CVAR4;
    boolean _CVAR3 = _CVAR2 || _CVAR5;
    return _CVAR3;// Workaround, Bukkit not recognize a Enderchests/Beacons

}