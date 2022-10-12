@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
public void onPlayerBedEnter(org.bukkit.event.player.PlayerBedEnterEvent event) {
    org.bukkit.event.player.PlayerBedEnterEvent _CVAR0 = event;
    org.bukkit.entity.Player player = _CVAR0.getPlayer();
    org.bukkit.entity.Player _CVAR1 = player;
    org.bukkit.Location location = _CVAR1.getLocation();
    org.bukkit.World world = location.getWorld();
    if (plugin.isActive(world.getName())) {
        java.lang.String mobName = uk.co.jacekk.bukkit.baseplugin.v5.util.ListUtils.getRandom(plugin.config.getStringList(Config.FEATURE_SPAWN_ON_SLEEP_SPAWN));
        org.bukkit.entity.EntityType creatureType = org.bukkit.entity.EntityType.fromName(mobName.toUpperCase());
        if (creatureType != null) {
            world.spawnEntity(location, creatureType);
        }
    }
}