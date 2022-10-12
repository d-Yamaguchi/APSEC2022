@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
public void onPlayerSuffocate(org.bukkit.event.entity.EntityDamageByBlockEvent event) {
    org.bukkit.event.entity.EntityDamageByBlockEvent _CVAR0 = event;
    org.bukkit.entity.Entity e = _CVAR0.getEntity();
    if ((e instanceof org.bukkit.entity.Player) && event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) {
        org.bukkit.entity.Player p = ((org.bukkit.entity.Player) (e));
        org.bukkit.entity.Player _CVAR1 = p;
        java.lang.String name = _CVAR1.getName();
        if (travellers.contains(name)) {
            org.bukkit.Location l = p.getLocation();
            double y = l.getWorld().getHighestBlockYAt(l);
            l.setY(y);
            p.teleport(l);
            travellers.remove(name);
        }
    }
}