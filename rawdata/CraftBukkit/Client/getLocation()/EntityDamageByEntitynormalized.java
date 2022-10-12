/* Used to separate mob damage from player damage.
This is the mob version.
 */
@java.lang.SuppressWarnings("deprecation")
public void mobDamage(org.bukkit.event.entity.EntityDamageByEntityEvent event, org.bukkit.entity.LivingEntity damager, org.bukkit.entity.LivingEntity e, double evtdmg) {
    com.github.jamesnorris.ablockalypse.aspect.entity.ZAMob zam = data.getZAMob(e);
    if (damager instanceof org.bukkit.entity.Fireball) {
        org.bukkit.entity.Fireball f = ((org.bukkit.entity.Fireball) (damager));
        if (com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.instakillids.contains(f.getUniqueId())) {
            event.setDamage(zam.getEntity().getHealth() * 10);
        } else {
            double dmg = 40 - zam.getHitAbsorption();// fireball damage

            if (dmg <= com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.MIN_FIREBALL_DMG) {
                dmg = com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.MIN_FIREBALL_DMG;
            }
            event.setDamage(dmg);
        }
    } else if (damager instanceof org.bukkit.entity.Arrow) {
        org.bukkit.entity.Arrow a = ((org.bukkit.entity.Arrow) (damager));
        if (com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.instakillids.contains(a.getUniqueId())) {
            event.setDamage(zam.getEntity().getHealth() * 10);
        } else {
            double dmg = 50 - zam.getHitAbsorption();// arrow damage

            if (dmg <= com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.MIN_ARROW_DMG) {
                dmg = com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.MIN_ARROW_DMG;
            }
            event.setDamage(dmg);
        }
    } else if (damager instanceof org.bukkit.entity.Player) {
        org.bukkit.entity.Player p = ((org.bukkit.entity.Player) (damager));
        if (data.isZAPlayer(p)) {
            com.github.jamesnorris.ablockalypse.aspect.entity.ZAPlayer zap = data.getZAPlayer(p);
            org.bukkit.entity.LivingEntity _CVAR0 = e;
            org.bukkit.Location _CVAR1 = _CVAR0.getLocation();
            org.bukkit.entity.Player _CVAR2 = p;
            org.bukkit.entity.Player _CVAR7 = _CVAR2;
            org.bukkit.Location _CVAR3 = _CVAR7.getLocation();
             _CVAR4 = com.github.jamesnorris.ablockalypse.utility.Pathfinder.calculate(_CVAR1, _CVAR3);
             _CVAR5 = _CVAR4.getTotalHeuristic();
            org.bukkit.entity.LivingEntity _CVAR8 = e;
            org.bukkit.Location _CVAR9 = _CVAR8.getLocation();
            org.bukkit.entity.Player _CVAR10 = p;
            org.bukkit.entity.Player _CVAR14 = _CVAR10;
            org.bukkit.Location _CVAR11 = _CVAR14.getLocation();
            double _CVAR12 = _CVAR9.distance(_CVAR11);
            double _CVAR13 = _CVAR12 * 3;
             _CVAR6 = _CVAR5 >= _CVAR13;
            if () {
                zam.getTargetter().panic(120);
            }
            if (zap.hasInstaKill()) {
                event.setDamage(zam.getEntity().getHealth() * 5);
            } else {
                double dmg = evtdmg - zam.getHitAbsorption();// regular hit damage

                if (dmg <= com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.MIN_HIT_DMG) {
                    dmg = com.github.jamesnorris.ablockalypse.event.bukkit.EntityDamageByEntity.MIN_HIT_DMG;
                }
                event.setDamage(dmg);
            }
        }
    } else if (data.isZAMob(damager) && (damager instanceof org.bukkit.entity.Wolf)) {
        event.setDamage((evtdmg - zam.getHitAbsorption()) / 2);
    }
}