@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
public void dealDamageEffects(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
    if (event.isCancelled()) {
        return;
    }
    if (damager instanceof org.bukkit.entity.Projectile) {
        org.bukkit.entity.Projectile pj = ((org.bukkit.entity.Projectile) (event.getDamager()));
        damager = pj.getShooter();
    }
    org.bukkit.event.entity.EntityDamageByEntityEvent _CVAR0 = event;
    org.bukkit.entity.Entity damager = _CVAR0.getDamager();
    org.bukkit.event.entity.EntityDamageByEntityEvent _CVAR4 = event;
    org.bukkit.entity.Entity ent = _CVAR4.getEntity();
    org.bukkit.event.entity.EntityDamageByEntityEvent _CVAR6 = _CVAR0;
    org.bukkit.entity.Entity damager = _CVAR6.getDamager();
    if (com.github.customentitylibrary.entities.CustomEntityWrapper.instanceOf(ent)) {
        if (damager instanceof org.bukkit.entity.Player) {
            com.github.customentitylibrary.entities.CustomEntityWrapper.getCustomEntity(ent).addAttack(((org.bukkit.entity.Player) (damager)), event.getDamage());
        }
    } else if (ent instanceof org.bukkit.entity.Player) {
        if (com.github.customentitylibrary.entities.CustomEntityWrapper.instanceOf(damager)) {
            org.bukkit.entity.Entity _CVAR1 = damager;
            com.github.customentitylibrary.entities.CustomEntityWrapper customEnt = com.github.customentitylibrary.entities.CustomEntityWrapper.getCustomEntity(_CVAR1);
            com.github.customentitylibrary.entities.CustomEntityWrapper _CVAR2 = customEnt;
            org.bukkit.entity.Entity _CVAR7 = damager;
             _CVAR3 = _CVAR2.getType();
            org.bukkit.entity.Entity _CVAR5 = ((org.bukkit.entity.Player) (ent));
            org.bukkit.Location _CVAR8 = _CVAR7.getLocation();
            _CVAR3.dealEffects(_CVAR5, _CVAR8);
        }
    }
}