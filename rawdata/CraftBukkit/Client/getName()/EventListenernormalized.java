@org.bukkit.event.EventHandler
public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
    if (!Legacy.timeTracker.containsKey(event.getPlayer())) {
        return;
    }
    java.util.Date now = new java.util.Date();
    org.bukkit.entity.Player _CVAR1 = player;
    java.lang.String _CVAR2 = _CVAR1.getName();
    boolean _CVAR3 = Legacy.config.contains(_CVAR2);
    org.bukkit.event.player.PlayerQuitEvent _CVAR0 = event;
    org.bukkit.event.player.PlayerQuitEvent _CVAR4 = _CVAR0;
    org.bukkit.event.player.PlayerQuitEvent _CVAR10 = _CVAR4;
    org.bukkit.event.player.PlayerQuitEvent _CVAR14 = _CVAR10;
    // initialize variables
    org.bukkit.entity.Player player = _CVAR14.getPlayer();
    int _CVAR7 = 1000;
    int _CVAR17 = _CVAR7;
    long playerSession = (now.getTime() - Legacy.timeTracker.get(player)) / _CVAR17;
    // save in config
    if () {
        org.bukkit.entity.Player _CVAR5 = player;
        long _CVAR8 = playerSession;
        java.lang.String _CVAR6 = _CVAR5.getName();
        org.bukkit.entity.Player _CVAR11 = player;
        java.lang.String _CVAR12 = _CVAR11.getName();
         _CVAR13 = Legacy.config.getLong(_CVAR12);
         _CVAR9 = _CVAR13 + _CVAR8;
        Legacy.config.set(_CVAR6, _CVAR9);
    } else {
        org.bukkit.entity.Player _CVAR15 = player;
        java.lang.String _CVAR16 = _CVAR15.getName();
        long _CVAR18 = playerSession;
        Legacy.config.set(_CVAR16, _CVAR18);
    }
    co.viocode.legacy.Legacy.saveLegacyConfig();
    // remove from map
    Legacy.timeTracker.remove(player);
}