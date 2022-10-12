@org.bukkit.event.EventHandler(ignoreCancelled = true)
public void onEntityInteract(org.bukkit.event.entity.EntityInteractEvent event) {
    PetEntity pet = getPet(event.getEntity());
    if (pet == null) {
        return;
    }
    org.bukkit.event.entity.EntityInteractEvent _CVAR0 = event;
    org.bukkit.block.Block _CVAR1 = _CVAR0.getBlock();
    org.bukkit.Material _CVAR2 = _CVAR1.getType();
    boolean _CVAR3 = _CVAR2 != org.bukkit.Material.WHEAT;
    org.bukkit.event.entity.EntityInteractEvent _CVAR5 = event;
    org.bukkit.block.Block _CVAR6 = _CVAR5.getBlock();
    org.bukkit.Material _CVAR7 = _CVAR6.getType();
    boolean _CVAR8 = _CVAR7 != org.bukkit.Material.SOIL;
    boolean _CVAR4 = _CVAR3 || _CVAR8;
    if () {
        return;
    }
    event.setCancelled(true);
}