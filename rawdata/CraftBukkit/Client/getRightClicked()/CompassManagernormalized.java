@java.lang.Override
public void onPlayerInteractEntity(org.bukkit.event.player.PlayerInteractEntityEvent event) {
    event.getPlayer().sendMessage("PlayerInteractEntity");
    cancelTeleport(event.getPlayer());
    if (event.getPlayer().getItemInHand().getType() != za.dats.bukkit.memorystone.Config.getTeleportItem()) {
        return;
    }
    // check permissions!
    if (!event.getPlayer().hasPermission("memorystone.use")) {
        return;
    }
    boolean _CVAR0 = !(event.getRightClicked() instanceof org.bukkit.entity.HumanEntity);
    if () {
        return;
    }
    org.bukkit.event.player.PlayerInteractEntityEvent _CVAR10 = event;
    org.bukkit.entity.Player _CVAR11 = _CVAR10.getPlayer();
    java.util.Map<java.lang.String, java.lang.String> _CVAR9 = selected;
    java.lang.String _CVAR12 = _CVAR11.getName();
    java.lang.String name = _CVAR9.get(_CVAR12);
    if (name != null) {
        if (stone == null) {
            event.getPlayer().sendMessage(za.dats.bukkit.memorystone.Config.getColorLang("notexist", "name", name));
            forgetStone(name, false);
            return;
        }
        org.bukkit.event.player.PlayerInteractEntityEvent _CVAR1 = event;
        org.bukkit.event.player.PlayerInteractEntityEvent _CVAR3 = event;
        org.bukkit.entity.Entity _CVAR4 = ((org.bukkit.entity.HumanEntity) (_CVAR3.getRightClicked()));
        java.lang.String _CVAR5 = _CVAR4.getName();
        org.bukkit.entity.Player _CVAR2 = _CVAR1.getPlayer();
        java.lang.String _CVAR6 = "Teleporting " + _CVAR5;
        _CVAR2.sendMessage(_CVAR6);
        za.dats.bukkit.memorystone.MemoryStonePlugin _CVAR7 = plugin;
         _CVAR8 = _CVAR7.getMemoryStoneManager();
        java.lang.String _CVAR13 = name;
        za.dats.bukkit.memorystone.MemoryStone stone = _CVAR8.getNamedMemoryStone(_CVAR13);
        org.bukkit.event.player.PlayerInteractEntityEvent _CVAR16 = event;
        za.dats.bukkit.memorystone.MemoryStone _CVAR14 = stone;
        org.bukkit.event.player.PlayerInteractEntityEvent _CVAR15 = event;
        org.bukkit.entity.Entity _CVAR17 = _CVAR16.getRightClicked();
        startTeleport(_CVAR14, _CVAR15, _CVAR17);
        event.setCancelled(true);
    } else {
        event.getPlayer().sendMessage(za.dats.bukkit.memorystone.Config.getColorLang("notmemorized"));
    }
}