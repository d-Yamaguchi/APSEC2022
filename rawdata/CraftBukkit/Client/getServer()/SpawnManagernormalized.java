protected static void spawn(com.github.aspect.Game game, org.bukkit.Location l, org.bukkit.entity.EntityType et) {
    org.bukkit.Server _CVAR0 = org.bukkit.Bukkit.getServer();
    org.bukkit.Location _CVAR2 = l;
    org.bukkit.World _CVAR3 = _CVAR2.getWorld();
    org.bukkit.Location _CVAR4 = l;
    org.bukkit.entity.EntityType _CVAR5 = et;
    org.bukkit.entity.Entity e = _CVAR3.spawnEntity(_CVAR4, _CVAR5);
    org.bukkit.entity.EntityType _CVAR8 = et;
    com.github.enumerated.GameEntityType type = com.github.enumerated.GameEntityType.translate(_CVAR8);
    org.bukkit.entity.Entity _CVAR6 = e;
    com.github.aspect.Game _CVAR7 = game;
    com.github.enumerated.GameEntityType _CVAR9 = type;
    com.github.event.GameMobSpawnEvent gmse = new com.github.event.GameMobSpawnEvent(_CVAR6, _CVAR7, _CVAR9);
    org.bukkit.plugin.PluginManager _CVAR1 = _CVAR0.getPluginManager();
    com.github.event.GameMobSpawnEvent _CVAR10 = gmse;
    _CVAR1.callEvent(_CVAR10);
    game.setMobCountSpawnedInThisRound(game.getMobCountSpawnedInThisRound() + 1);
    if (!gmse.isCancelled()) {
        type.instantiate(e, game);
    } else {
        e.remove();
    }
}