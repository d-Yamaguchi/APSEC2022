package sample.example.groupid;
/* This is a sample event listener */
public class SampleListener implements org.bukkit.event.Listener {
    private final sample.example.groupid.SamplePlugin plugin;

    /* This listener needs to know about the plugin which it came from */
    public SampleListener(sample.example.groupid.SamplePlugin plugin) {
        this.plugin = plugin;
    }

    /* Send the sample message to all players that join */
    @org.bukkit.event.EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().sendMessage(this.plugin.getConfig().getString("sample.message"));
    }

    /* Another example of a event handler. This one will give you the name of
    the entity you interact with, if it is a Creature it will give you the
    creature Id.
     */
    @org.bukkit.event.EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        final org.bukkit.entity.Entity entity = event.getRightClicked();
        if (entity instanceof org.bukkit.entity.Creature) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder("You interacted with a ");
            org.bukkit.entity.Creature creature = ((org.bukkit.entity.Creature) (entity));
            sb.append(event.getPlayer().getSimpleName());
            short s = creatureIdFromCreature(creature);
            if (s > 0) {
                sb.append(" it has a id of ");
                sb.append(s);
            }
        }
    }

    /**
     * This is an example of a helper method.
     *
     * @param entity
     * 		you wish to get the creatureType Id of.
     * @return the type id of the creature or -1 if the entity does not have a
    creature id
     */
    private short creatureIdFromCreature(org.bukkit.entity.Creature entity) {
        java.lang.Class<?>[] interfaces = entity.getClass().getInterfaces();
        if (interfaces.length == 1) {
            java.lang.Class<?> clazz = interfaces[0];
            org.bukkit.entity.CreatureType creatureType = org.bukkit.entity.CreatureType.fromName(clazz.getSimpleName());
            if (creatureType != null) {
                return creatureType.getTypeId();
            }
        }
        return -1;
    }
}