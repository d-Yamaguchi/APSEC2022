@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent event) {
    if (plugin.getConfigManager().getGeneralConfig().isConfig_channels_enable()) {
        de.tobiyas.racesandclasses.RacesAndClasses _CVAR0 = plugin;
        org.bukkit.event.player.PlayerQuitEvent _CVAR2 = event;
        org.bukkit.entity.Player player = _CVAR2.getPlayer();
        org.bukkit.entity.Player _CVAR3 = player;
         _CVAR1 = _CVAR0.getChannelManager();
        java.util.UUID _CVAR4 = _CVAR3.getUniqueId();
        _CVAR1.playerQuit(_CVAR4);
    }
}