@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
public void onEntityDamage(org.bukkit.event.entity.EntityDamageEvent event) {
    java.lang.String worldName = entity.getWorld().getName();
    org.bukkit.event.entity.EntityDamageEvent _CVAR0 = event;
    org.bukkit.entity.Entity entity = _CVAR0.getEntity();
    if (((entity instanceof org.bukkit.entity.Creature) && this.playerCauses.contains(event.getCause())) && plugin.isActive(worldName)) {
        uk.co.jacekk.bukkit.baseplugin.v6.config.PluginConfig worldConfig = plugin.getConfig(worldName);
        org.bukkit.entity.Creature creature = ((org.bukkit.entity.Creature) (entity));
        if (((creature.getTarget() instanceof org.bukkit.entity.Player) && worldConfig.getBoolean(Config.FEATURE_DOUBLE_HEALTH_ENABLED)) && worldConfig.getStringList(Config.FEATURE_DOUBLE_HEALTH_MOBS).contains(entity.getType().name().toUpperCase())) {
            event.setDamage(((int) (java.lang.Math.floor(event.getDamage() / 2.0F))));
            if (creature.getHealth() < 0) {
                org.bukkit.entity.Creature _CVAR1 = creature;
                int _CVAR2 = 0;
                _CVAR1.setHealth(_CVAR2);
            }
        }
    }
}