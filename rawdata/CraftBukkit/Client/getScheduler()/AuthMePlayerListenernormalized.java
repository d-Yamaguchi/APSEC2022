private void checkAntiBotMod(final org.bukkit.event.player.PlayerLoginEvent event) {
    if (plugin.delayedAntiBot || plugin.antibotMod) {
        return;
    }
    if (plugin.authmePermissible(event.getPlayer(), "authme.bypassantibot")) {
        return;
    }
    if (antibot.keySet().size() > fr.xephi.authme.settings.Settings.antiBotSensibility) {
        plugin.switchAntiBotMod(true);
        org.bukkit.Bukkit.broadcastMessage(m._("antibot_auto_enabled"));
        int _CVAR3 = 1200;
        org.bukkit.scheduler.BukkitScheduler _CVAR0 = org.bukkit.Bukkit.getScheduler();
        fr.xephi.authme.AuthMe _CVAR1 = plugin;
        java.lang.Runnable _CVAR2 = new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                if (plugin.antibotMod) {
                    plugin.switchAntiBotMod(false);
                    antibot.clear();
                    org.bukkit.Bukkit.broadcastMessage(m._("antibot_auto_disabled").replace("%m", "" + fr.xephi.authme.settings.Settings.antiBotDuration));
                }
            }
        };
         _CVAR4 = fr.xephi.authme.settings.Settings.antiBotDuration * _CVAR3;
        _CVAR0.scheduleSyncDelayedTask(_CVAR1, _CVAR2, _CVAR4);
        return;
    }
    antibot.put(event.getPlayer().getName().toLowerCase(), event);
    org.bukkit.scheduler.BukkitScheduler _CVAR5 = org.bukkit.Bukkit.getScheduler();
    fr.xephi.authme.AuthMe _CVAR6 = plugin;
    java.lang.Runnable _CVAR7 = new java.lang.Runnable() {
        @java.lang.Override
        public void run() {
            antibot.remove(event.getPlayer().getName().toLowerCase());
        }
    };
    int _CVAR8 = 300;
    _CVAR5.scheduleSyncDelayedTask(_CVAR6, _CVAR7, _CVAR8);
}