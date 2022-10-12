// Handle a player interacting with an entity
@org.bukkit.event.EventHandler
public void onPlayerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
    net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
    org.bukkit.event.player.PlayerInteractEntityEvent _CVAR0 = event;
    org.bukkit.entity.Entity clicked = _CVAR0.getRightClicked();
    net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.INTERACT_ENTITY, getEntityName(clicked));
    if (!player.canDoAction(action)) {
        event.setCancelled(true);
        player.notifyPlayer(action);
    }
}