@java.lang.Override
public void run() {
    if (uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        return;
    }
    for (org.bukkit.entity.Player player : plugin.getServer().getOnlinePlayers()) {
        if (player.getName().toLowerCase().equals(name)) {
            if (uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().hasLimboPlayer(name)) {
                org.bukkit.entity.Player _CVAR0 = player;
                uk.org.whoami.authme.cache.limbo.LimboPlayer _CVAR4 = inv;
                org.bukkit.inventory.PlayerInventory _CVAR1 = _CVAR0.getInventory();
                 _CVAR5 = _CVAR4.getArmour();
                _CVAR1.setArmorContents(_CVAR5);
                org.bukkit.entity.Player _CVAR6 = player;
                 _CVAR2 = uk.org.whoami.authme.cache.limbo.LimboCache.getInstance();
                java.lang.String _CVAR3 = name;
                 _CVAR8 = _CVAR2;
                java.lang.String _CVAR9 = _CVAR3;
                uk.org.whoami.authme.cache.limbo.LimboPlayer inv = _CVAR8.getLimboPlayer(_CVAR9);
                uk.org.whoami.authme.cache.limbo.LimboPlayer _CVAR10 = inv;
                org.bukkit.inventory.PlayerInventory _CVAR7 = _CVAR6.getInventory();
                 _CVAR11 = _CVAR10.getInventory();
                _CVAR7.setContents(_CVAR11);
                utils.addNormal(player, inv.getGroup());
                player.setOp(inv.getOperator());
                // System.out.println("debug timout group reset "+inv.getGroup());
                uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().deleteLimboPlayer(name);
                if (playerCache.doesCacheExist(name)) {
                    playerCache.removeCache(name);
                }
            }
            player.kickPlayer(m._("timeout"));
            break;
        }
    }
}