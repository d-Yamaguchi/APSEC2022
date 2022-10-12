public SkyPlayerLocation(org.bukkit.Location location) {
    org.bukkit.Location _CVAR0 = location;
    org.bukkit.Location _CVAR2 = location;
    org.bukkit.Location _CVAR4 = location;
    org.bukkit.Location _CVAR6 = location;
    org.bukkit.World _CVAR7 = _CVAR6.getWorld();
    int _CVAR1 = _CVAR0.getBlockX();
    int _CVAR3 = _CVAR2.getBlockY();
    int _CVAR5 = _CVAR4.getBlockZ();
    java.lang.String _CVAR8 = _CVAR7.getName();
    this(_CVAR1, _CVAR3, _CVAR5, _CVAR8);
}