@java.lang.Override
protected boolean init() {
    org.bukkit.plugin.PluginManager _CVAR0 = org.bukkit.Bukkit.getPluginManager();
    java.lang.String _CVAR1 = "WorldGuard";
    final org.bukkit.plugin.Plugin p = _CVAR0.getPlugin(_CVAR1);
    if ((p != null) && (p instanceof com.sk89q.worldguard.bukkit.WorldGuardPlugin)) {
        ch.njol.skript.hooks.regions.WorldGuardHook.worldGuard = ((com.sk89q.worldguard.bukkit.WorldGuardPlugin) (p));
        return super.init();
    }
    return false;
}