public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    boolean _CVAR0 = !pending.containsKey(event.getPlayer().getName());
    if () {
        return;
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR2 = event;
    org.bukkit.entity.Player _CVAR3 = _CVAR2.getPlayer();
    java.util.Map<java.lang.String, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand> _CVAR1 = pending;
    java.lang.String _CVAR4 = _CVAR3.getName();
    net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand _CVAR5 = _CVAR1.get(_CVAR4);
    switch (_CVAR5) {
        case CREATE :
            if ((event.getClickedBlock() != null) && (event.getClickedBlock().getState() instanceof org.bukkit.inventory.InventoryHolder)) {
                event.setCancelled(true);
                create(event.getPlayer(), ((org.bukkit.inventory.InventoryHolder) (event.getClickedBlock().getState())).getInventory());
            }
            break;
        case REMOVE :
            if ((event.getClickedBlock() != null) && (event.getClickedBlock().getState() instanceof org.bukkit.inventory.InventoryHolder)) {
                event.setCancelled(true);
                remove(event.getPlayer(), ((org.bukkit.inventory.InventoryHolder) (event.getClickedBlock().getState())).getInventory());
            }
            break;
        case ADMIN_ON :
            if ((event.getClickedBlock() != null) && (event.getClickedBlock().getState() instanceof org.bukkit.inventory.InventoryHolder)) {
                event.setCancelled(true);
                setAdmin(event.getPlayer(), ((org.bukkit.inventory.InventoryHolder) (event.getClickedBlock().getState())).getInventory(), true);
            }
            break;
        case ADMIN_OFF :
            if ((event.getClickedBlock() != null) && (event.getClickedBlock().getState() instanceof org.bukkit.inventory.InventoryHolder)) {
                event.setCancelled(true);
                setAdmin(event.getPlayer(), ((org.bukkit.inventory.InventoryHolder) (event.getClickedBlock().getState())).getInventory(), false);
            }
            break;
    }
}