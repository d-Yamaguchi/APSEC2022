// 
// * Sign facing data from block face.
// *
// * @param bf the bf
// * @return the byte
// 
// public static byte signFacingDataFromBlockFace(BlockFace bf)
// {
// switch ( bf )
// {
// case NORTH:
// return (byte)4;
// case SOUTH:
// return (byte)5;
// case EAST:
// return (byte)2;
// case WEST:
// return (byte)3;
// }
// 
// return (byte)0;
// }
// 
// 
// * Lever facing data from block face.
// *
// * @param bf the bf
// * @return the byte
// 
// public static byte leverFacingDataFromBlockFace(BlockFace bf)
// {
// switch ( bf )
// {
// case NORTH:
// return (byte)3;
// case SOUTH:
// return (byte)1;
// case EAST:
// return (byte)0;
// case WEST:
// return (byte)2;
// }
// 
// return (byte)0;
// }
/**
 * Check chunk load.
 *
 * @param b
 * 		the b
 */
public static void checkChunkLoad(org.bukkit.block.Block b) {
    org.bukkit.block.Block _CVAR3 = b;
    org.bukkit.Chunk c = _CVAR3.getChunk();
    org.bukkit.block.Block _CVAR9 = b;
    org.bukkit.World w = _CVAR9.getWorld();
    if (!w.isChunkLoaded(c)) {
        org.bukkit.Chunk _CVAR4 = c;
        java.lang.String _CVAR5 = _CVAR4.toString();
        java.lang.String _CVAR6 = "Loading chunk: " + _CVAR5;
        java.lang.String _CVAR7 = _CVAR6 + " on: ";
         _CVAR0 = com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin();
        java.util.logging.Level _CVAR1 = java.util.logging.Level.FINE;
        boolean _CVAR2 = false;
        org.bukkit.World _CVAR10 = w;
        java.lang.String _CVAR11 = _CVAR10.toString();
        java.lang.String _CVAR8 = _CVAR7 + _CVAR11;
        _CVAR0.prettyLog(_CVAR1, _CVAR2, _CVAR8);
        w.loadChunk(c);
    }
}