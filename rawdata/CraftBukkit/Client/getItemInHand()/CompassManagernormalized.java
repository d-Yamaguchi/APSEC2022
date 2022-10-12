@java.lang.Override
public void onPlayerInteractEntity(final org.bukkit.event.player.PlayerInteractEntityEvent event) {
    org.bukkit.event.player.PlayerInteractEntityEvent _CVAR0 = event;
    org.bukkit.entity.Player _CVAR1 = _CVAR0.getPlayer();
    org.bukkit.inventory.ItemStack _CVAR2 = _CVAR1.getItemInHand();
    org.bukkit.Material _CVAR3 = _CVAR2.getType();
     _CVAR4 = _CVAR3 != za.dats.bukkit.memorystone.Config.getTeleportItem();
    if () {
        return;
    }
    // check permissions!
    if (!event.getPlayer().hasPermission("memorystone.useonothers")) {
        return;
    }
    if (!(event.getRightClicked() instanceof org.bukkit.entity.HumanEntity)) {
        return;
    }
    za.dats.bukkit.memorystone.CompassManager.Teleport teleport = getTeleport(event.getPlayer());
    long now = new java.util.Date().getTime();
    if ((now - teleport.lastEventTime) < 100) {
        return;
    }
    teleport.lastEventTime = now;
    if (isInNoTeleportZone(event.getPlayer())) {
        return;
    }
    if (!(event.getPlayer().hasPermission("memorystone.useanywhere") || (za.dats.bukkit.memorystone.Config.getMinProximityToStoneForTeleport() == 0))) {
        if (!withinDistanceOfAnyStone(event.getPlayer(), za.dats.bukkit.memorystone.Config.getMinProximityToStoneForTeleport())) {
            event.getPlayer().sendMessage(za.dats.bukkit.memorystone.Config.getColorLang("outsideproximity"));
            return;
        }
    }
    if (plugin.isSpoutEnabled()) {
        if (((org.getspout.spoutapi.player.SpoutPlayer) (event.getPlayer())).isSpoutCraftEnabled()) {
            java.lang.String name = ((org.bukkit.entity.HumanEntity) (event.getRightClicked())).getName();
            plugin.getSpoutLocationPopupManager().showPopup(event.getPlayer(), getPlayerLocations(event.getPlayer().getWorld().getName(), event.getPlayer()), za.dats.bukkit.memorystone.Config.getColorLang("selectotherlocation", "name", name), new za.dats.bukkit.memorystone.ui.LocationPopupListener() {
                public void selected(za.dats.bukkit.memorystone.MemoryStone stone) {
                    tryTeleportOther(event, event.getPlayer(), stone.getName());
                }
            });
        } else {
            java.lang.String name = selected.get(event.getPlayer().getName());
            tryTeleportOther(event, event.getPlayer(), name);
        }
    } else {
        java.lang.String name = selected.get(event.getPlayer().getName());
        tryTeleportOther(event, event.getPlayer(), name);
    }
}