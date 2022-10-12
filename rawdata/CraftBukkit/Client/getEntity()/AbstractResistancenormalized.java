@java.lang.Override
public boolean trigger(org.bukkit.event.Event event) {
    if (!(event instanceof org.bukkit.event.entity.EntityDamageEvent)) {
        return false;
    }
    org.bukkit.event.entity.EntityDamageEvent Eevent = ((org.bukkit.event.entity.EntityDamageEvent) (event));
    org.bukkit.event.entity.EntityDamageEvent _CVAR0 = Eevent;
    org.bukkit.entity.Entity entity = _CVAR0.getEntity();
    if (!(entity instanceof org.bukkit.entity.Player)) {
        return false;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (entity));
    if (de.tobiyas.racesandclasses.datacontainer.traitholdercontainer.TraitHolderCombinder.checkContainer(player.getName(), this)) {
        if (getResistanceTypes().contains(Eevent.getCause())) {
            // If there is damage * 0, cancel the Event to show no damage effect.
            if (instantCancle()) {
                CompatibilityModifier.EntityDamage.safeSetDamage(0, Eevent);
                Eevent.setCancelled(true);
                return true;
            }
            double oldDmg = CompatibilityModifier.EntityDamage.safeGetDamage(Eevent);
            double newDmg = de.tobiyas.racesandclasses.util.traitutil.TraitStringUtils.getNewValue(oldDmg, operation, value);
            CompatibilityModifier.EntityDamage.safeSetDamage(newDmg, Eevent);
            return true;
        }
    }
    return false;
}