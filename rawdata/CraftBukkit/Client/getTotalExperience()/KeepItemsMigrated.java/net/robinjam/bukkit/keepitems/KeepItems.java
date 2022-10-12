package net.robinjam.bukkit.keepitems;
/**
 * The main plugin class.
 *
 * @author robinjam
 */
public class KeepItems extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    private java.util.Map<org.bukkit.entity.Player, net.robinjam.bukkit.keepitems.Death> deaths = new java.util.HashMap<org.bukkit.entity.Player, net.robinjam.bukkit.keepitems.Death>();

    @java.lang.Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @java.lang.Override
    public void onDisable() {
        // When the plugin is disabled, drop all managed items and clear the list
        for (net.robinjam.bukkit.keepitems.Death death : deaths.values()) {
            death.drop();
        }
        deaths.clear();
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGH)
    public void onEntityDeath(final org.bukkit.event.entity.EntityDeathEvent event) {
        // Skip if the entity that died is not a player
        if (!(event.getEntity() instanceof org.bukkit.entity.Player))
            return;

        // If the player already has a death on record, drop the items
        net.robinjam.bukkit.keepitems.Death death = deaths.get(player);
        if (death != null)
            death.drop();

        org.bukkit.inventory.ItemStack[] inventoryContents = __SmPLUnsupported__(0);
        org.bukkit.inventory.ItemStack[] armorContents = __SmPLUnsupported__(1);
        int experience = 0;
        if (player.hasPermission("keep-items.items")) {
            inventoryContents = player.getInventory().getContents();
            armorContents = player.getInventory().getArmorContents();
            // Don't drop any items at the death location
            event.getDrops().clear();
        }
        org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (event.getEntity()));
        if (player.hasPermission("keep-items.experience")) {
            if (player.hasPermission("keep-items.progress")) {
                int playerLevel = player.getLevel();
                experience = java.lang.Math.max(_CVAR1Level, 0);
            } else
                experience = calcExperience(player.getLevel());

            // Don't drop any experience at the death location
            event.setDroppedExp(0);
        }
        // Register the death event
        deaths.put(player, new net.robinjam.bukkit.keepitems.Death(this, player.getLocation(), inventoryContents, armorContents, experience));
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGH)
    public void onPlayerRespawn(final org.bukkit.event.player.PlayerRespawnEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        // If the player has a death on record, drop the items and experience at their respawn location
        net.robinjam.bukkit.keepitems.Death death = deaths.remove(player);
        if (death != null)
            death.give(player);

    }

    /**
     * Calculates the total amount of experience required to reach the given level from level 0.
     *
     * @param level
     * 		The level for which to calculate the required experience
     * @return The amount of experience required
     */
    private int calcExperience(int level) {
        // Calculate the amount of experience required to reach this level from the previous one
        int xp = 7 + ((int) (java.lang.Math.floor((level - 1) * 3.5)));
        // Recursively repeat until we reach level 1
        return level > 1 ? xp + calcExperience(level - 1) : xp;
    }
}