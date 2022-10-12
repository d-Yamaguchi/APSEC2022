@org.bukkit.event.EventHandler
public void onEntityDamage(org.bukkit.event.entity.EntityDamageEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.event.entity.EntityDamageEvent _CVAR0 = event;
    org.bukkit.entity.Entity entity = _CVAR0.getEntity();
    if (!(entity instanceof org.bukkit.entity.Player)) {
        return;
    }
    if (uk.org.whoami.authme.Utils.getInstance().isUnrestricted(((org.bukkit.entity.Player) (entity)))) {
        return;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (entity));
    java.lang.String name = player.getName().toLowerCase();
    if (uk.org.whoami.authme.plugin.manager.CombatTagComunicator.isNPC(player)) {
        return;
    }
    if (uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        return;
    }
    if (!data.isAuthAvailable(name)) {
        if (!uk.org.whoami.authme.settings.Settings.isForcedRegistrationEnabled) {
            return;
        }
    }
    player.setFireTicks(0);
    event.setCancelled(true);
}