/* Another example of a event handler. This one will give you the name of
the entity you interact with, if it is a Creature it will give you the
creature Id.
 */
@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEntityEvent event) {
    org.bukkit.event.player.PlayerInteractEntityEvent _CVAR2 = event;
    final org.bukkit.entity.Entity entity = _CVAR2.getRightClicked();
    if (entity instanceof org.bukkit.entity.Creature) {
        java.lang.String _CVAR0 = "You interacted with a ";
        java.lang.StringBuilder sb = new java.lang.StringBuilder(_CVAR0);
        org.bukkit.entity.Creature creature = ((org.bukkit.entity.Creature) (entity));
        org.bukkit.entity.Creature _CVAR3 = creature;
        java.lang.Class<? extends org.bukkit.entity.Creature> _CVAR4 = _CVAR3.getClass();
        java.lang.StringBuilder _CVAR1 = sb;
        java.lang.String _CVAR5 = _CVAR4.getSimpleName();
        _CVAR1.append(_CVAR5);
        short s = creatureIdFromCreature(creature);
        if (s > 0) {
            sb.append(" it has a id of ");
            sb.append(s);
        }
    }
}