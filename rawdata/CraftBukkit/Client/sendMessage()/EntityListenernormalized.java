@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
    org.bukkit.entity.Entity entity = event.getEntity();
    if (entity instanceof org.bukkit.entity.Player) {
        org.bukkit.entity.Player defender = ((org.bukkit.entity.Player) (entity));
        java.lang.String dname = defender.getName();
        java.lang.String aname = this.attackers.get(dname);
        org.bukkit.entity.Player attacker = (aname == null) ? null : org.bukkit.Bukkit.getPlayer(aname);
        if ((attacker != null) && attacker.isOnline()) {
            com.lenis0012.bukkit.pvp.PvpPlayer pp = new com.lenis0012.bukkit.pvp.PvpPlayer(aname);
            com.lenis0012.bukkit.pvp.PvpPlayer dpp = new com.lenis0012.bukkit.pvp.PvpPlayer(dname);
            if (killer.containsKey(aname)) {
                java.lang.String value = killer.get(aname);
                java.lang.String[] data = value.split(";");
                int allowed = plugin.getConfig().getInt("settings.kill-session");
                java.lang.String cname = data[0];
                int current = java.lang.Integer.valueOf(data[1]);
                if (dname.equals(cname)) {
                    if (current >= allowed) {
                        return;
                    } else {
                        killer.put(aname, (dname + ';') + java.lang.String.valueOf(current + 1));
                    }
                } else {
                    killer.put(aname, (dname + ';') + '1');
                }
            } else {
                killer.put(aname, (dname + ';') + '1');
            }
            int kills = pp.get("kills");
            int lvl = pp.get("level");
            kills += 1;
            pp.set("kills", kills);
            dpp.set("deaths", dpp.get("deaths") + 1);
            if (plugin.levelList.contains(kills)) {
                lvl += 1;
                pp.set("level", lvl);
                java.lang.String _CVAR1 = "Level up!";
                org.bukkit.entity.Player _CVAR0 = attacker;
                java.lang.String _CVAR2 = org.bukkit.ChatColor.GREEN + _CVAR1;
                _CVAR0.sendMessage(_CVAR2);
                pp.reward(attacker);
            }
        }
    }
}