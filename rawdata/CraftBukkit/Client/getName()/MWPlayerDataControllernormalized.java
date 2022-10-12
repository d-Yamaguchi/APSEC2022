/**
 * Gets the save file for the player in the current world.
 * World inventories settings are applied here.
 *
 * @param player
 * 		to get the save file for
 * @return save file
 */
public static java.io.File getSaveFile(org.bukkit.entity.HumanEntity player) {
    org.bukkit.entity.HumanEntity _CVAR0 = player;
    org.bukkit.entity.HumanEntity _CVAR2 = player;
    com.bergerkiller.bukkit.mw.WorldConfig _CVAR1 = com.bergerkiller.bukkit.mw.MWPlayerDataController.getSaveWorld(_CVAR0);
    java.lang.String _CVAR3 = _CVAR2.getName();
    java.io.File playerData = _CVAR1.getPlayerData(_CVAR3);
    playerData.getParentFile().mkdirs();
    return playerData;
}