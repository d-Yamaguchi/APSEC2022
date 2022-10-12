/**
 * Check safe block below.
 *
 * @param safeBlock
 * 		the safe block
 * @return true, if successful
 */
private static boolean checkSafeBlockBelow(final org.bukkit.block.Block safeBlock) {
    if (safeBlock != null) {
        org.bukkit.block.Block _CVAR0 = safeBlock;
        org.bukkit.block.BlockFace _CVAR1 = org.bukkit.block.BlockFace.DOWN;
        org.bukkit.block.BlockFace _CVAR2 = _CVAR0.getFace(_CVAR1);
        final int typeId = _CVAR2.getTypeId();
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkNonSafeTypeId(typeId);
    }
    return false;
}