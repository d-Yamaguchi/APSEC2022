@java.lang.Override
public void onPlayerTeleport(org.bukkit.event.player.PlayerTeleportEvent event) {
    if (event.isCancelled()) {
        return;
    }
    net.krinsoft.privileges.Privileges _CVAR0 = plugin;
    org.bukkit.event.player.PlayerTeleportEvent _CVAR2 = event;
    org.bukkit.entity.Player _CVAR3 = _CVAR2.getPlayer();
    org.bukkit.event.player.PlayerTeleportEvent _CVAR5 = event;
    org.bukkit.Location _CVAR6 = _CVAR5.getTo();
    org.bukkit.World _CVAR7 = _CVAR6.getWorld();
     _CVAR1 = _CVAR0.getPermissionManager();
    java.lang.String _CVAR4 = _CVAR3.getName();
    java.lang.String _CVAR8 = _CVAR7.getName();
    _CVAR1.updatePlayerWorld(_CVAR4, _CVAR8);
}