/**
 * Check safe block.
 *
 * @param safeBlock
 * 		the safe block
 * @return true, if successful
 */
private static boolean checkSafeBlock(final org.bukkit.block.Block safeBlock) {
    if (safeBlock != null) {
        org.bukkit.block.Block _CVAR0 = safeBlock;
        final int typeId = _CVAR0.getTypeId();
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTypeId(typeId);
    }
    return false;
}