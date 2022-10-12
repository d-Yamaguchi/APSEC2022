// This method is to keep track of players that join
@org.bukkit.event.EventHandler
public void registerPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
    org.bukkit.event.player.PlayerJoinEvent _CVAR1 = event;
    org.bukkit.entity.Player _CVAR2 = _CVAR1.getPlayer();
    org.bukkit.event.player.PlayerJoinEvent _CVAR4 = event;
    org.bukkit.entity.Player _CVAR5 = _CVAR4.getPlayer();
    java.util.HashMap<java.lang.String, net.worldoftomorrow.noitem.interfaces.INoItemPlayer> _CVAR0 = players;
    java.lang.String _CVAR3 = _CVAR2.getName();
    net.worldoftomorrow.noitem.NoItemPlayer _CVAR6 = new net.worldoftomorrow.noitem.NoItemPlayer(_CVAR5);
    _CVAR0.put(_CVAR3, _CVAR6);
}