@java.lang.Override
public org.bukkit.entity.Player getPlayer(int id) {
    int _CVAR0 = id;
    java.lang.String _CVAR1 = getLastSeenPlayerName(_CVAR0);
    org.bukkit.entity.Player _CVAR2 = org.bukkit.Bukkit.getPlayerExact(_CVAR1);
    return _CVAR2;
}