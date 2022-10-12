public void save(org.bukkit.configuration.file.FileConfiguration config) {
    java.lang.String _CVAR1 = ".world";
    com.lenis0012.bukkit.npc.NPC _CVAR3 = npc;
     _CVAR4 = _CVAR3.getBukkitEntity();
    org.bukkit.Location location = _CVAR4.getLocation();
    org.bukkit.Location _CVAR5 = location;
    org.bukkit.World _CVAR6 = _CVAR5.getWorld();
    org.bukkit.configuration.file.FileConfiguration _CVAR0 = config;
    java.lang.String _CVAR2 = ("bankers." + name) + _CVAR1;
    java.lang.String _CVAR7 = _CVAR6.getName();
    _CVAR0.set(_CVAR2, _CVAR7);
    config.set(("bankers." + name) + ".x", location.getX());
    config.set(("bankers." + name) + ".y", location.getY());
    config.set(("bankers." + name) + ".z", location.getZ());
    config.set(("bankers." + name) + ".yaw", ((double) (location.getYaw())));
}