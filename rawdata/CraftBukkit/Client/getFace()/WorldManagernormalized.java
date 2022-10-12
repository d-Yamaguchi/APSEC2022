/**
 * Check safe block above.
 *
 * @param safeBlock
 * 		the safe block
 * @return true, if successful
 */
private static boolean checkSafeBlockAbove(final org.bukkit.block.Block safeBlock) {
    if (safeBlock != null) {
        org.bukkit.block.Block _CVAR0 = safeBlock;
        org.bukkit.block.BlockFace _CVAR1 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.BlockFace _CVAR2 = _CVAR0.getFace(_CVAR1);
        final int typeId = _CVAR2.getTypeId();
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTypeId(typeId);
    }
    return false;
}