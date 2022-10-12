@me.neatmonster.spacemodule.api.Action(aliases = { "throwEgg", "egg" }, schedulable = false)
public static boolean throwEgg(final java.lang.String playerName) {
    java.lang.String _CVAR0 = playerName;
    final org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayer(_CVAR0);
    if (player != null) {
        org.bukkit.entity.Player _CVAR1 = player;
        _CVAR1.throwEgg();
        return true;
    }
    return false;
}