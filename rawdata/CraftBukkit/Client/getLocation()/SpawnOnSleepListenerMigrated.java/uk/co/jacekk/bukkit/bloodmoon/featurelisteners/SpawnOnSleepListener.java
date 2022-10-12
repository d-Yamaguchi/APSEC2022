package uk.co.jacekk.bukkit.bloodmoon.featurelisteners;
import uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener;
import uk.co.jacekk.bukkit.baseplugin.v5.util.ListUtils;
import uk.co.jacekk.bukkit.bloodmoon.BloodMoon;
import uk.co.jacekk.bukkit.bloodmoon.Config;
public class SpawnOnSleepListener extends uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener<uk.co.jacekk.bukkit.bloodmoon.BloodMoon> {
    public SpawnOnSleepListener(uk.co.jacekk.bukkit.bloodmoon.BloodMoon plugin) {
        super(plugin);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerBedEnter(org.bukkit.event.player.PlayerBedEnterEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        HashSet floorBlocks = new java.util.HashSet<org.bukkit.block.Block>();
        Location location = java.lang.Integer.toString(floorBlocks.size());
        org.bukkit.World world = location.getWorld();
        if (plugin.isActive(world.getName())) {
            java.lang.String mobName = uk.co.jacekk.bukkit.baseplugin.v5.util.ListUtils.getRandom(plugin.config.getStringList(Config.FEATURE_SPAWN_ON_SLEEP_SPAWN));
            org.bukkit.entity.EntityType creatureType = org.bukkit.entity.EntityType.fromName(mobName.toUpperCase());
            if (creatureType != null) {
                world.spawnEntity(location, creatureType);
            }
        }
    }
}