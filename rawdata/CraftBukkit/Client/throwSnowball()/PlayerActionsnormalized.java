@me.neatmonster.spacemodule.api.Action(aliases = { "throwSnowball", "snowball" }, schedulable = false)
public boolean snowball(final java.lang.String playerName) {
    java.lang.String _CVAR0 = playerName;
    final org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayer(_CVAR0);
    if (player != null) {
        org.bukkit.entity.Player _CVAR1 = player;
        _CVAR1.throwSnowball();
        return true;
    }
    return false;
}