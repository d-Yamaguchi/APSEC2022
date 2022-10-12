/**
 * Checks if the block is a chest
 *
 * @param block
 * 		Block to check
 * @return Is this block a chest?
 */
public static boolean isChest(org.bukkit.block.Block block) {
    org.bukkit.Material _CVAR0 = org.bukkit.Material.CHEST;
    boolean _CVAR1 = block.getType() == _CVAR0;
    return _CVAR1;
}