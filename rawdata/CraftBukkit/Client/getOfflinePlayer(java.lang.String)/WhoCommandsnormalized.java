private boolean isPlayer(final java.lang.String s) {
    java.lang.String _CVAR0 = s;
    org.bukkit.OfflinePlayer _CVAR1 = org.bukkit.Bukkit.getOfflinePlayer(_CVAR0);
    boolean _CVAR2 = _CVAR1.hasPlayedBefore();
    if () {
        return true;
    }
    return false;
}