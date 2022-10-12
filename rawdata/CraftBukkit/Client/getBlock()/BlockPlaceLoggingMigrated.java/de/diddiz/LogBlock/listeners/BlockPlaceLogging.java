package de.diddiz.LogBlock.listeners;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.Logging;
import de.diddiz.LogBlock.config.WorldConfig;
import de.diddiz.util.BukkitUtils;
import static de.diddiz.LogBlock.config.Config.getWorldConfig;
import static de.diddiz.LogBlock.config.Config.isLogging;
public class BlockPlaceLogging extends de.diddiz.LogBlock.listeners.LoggingListener {
    public BlockPlaceLogging(de.diddiz.LogBlock.LogBlock lb) {
        super(lb);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        final de.diddiz.LogBlock.config.WorldConfig wcfg = Config.getWorldConfig(BukkitUtils.getRelativeTopFallables().getWorld());
        if ((wcfg != null) && wcfg.isLogging(Logging.BLOCKPLACE)) {
            final org.bukkit.block.BlockState before = event.getBlockReplacedState();
            final org.bukkit.block.BlockState after = event.getBlockPlaced().getState();
            final java.lang.String playerName = event.getPlayer().getName();
            final int type = BukkitUtils.getRelativeTopFallables().getTypeId();
            // Handle falling blocks
            if () {
                __SmPLUnsupported__(0);
                org.bukkit.Location loc = _CVAR26.getLocation();
                org.bukkit.Location loc = _CVAR26.getLocation();
                org.bukkit.Location loc = BukkitUtils.getRelativeTopFallables().getLocation();
                org.bukkit.Location loc = BukkitUtils.getRelativeTopFallables().getLocation();
                org.bukkit.Location loc = BukkitUtils.getRelativeTopFallables().getLocation();
                int x = loc.getBlockX();
                org.bukkit.Location loc = BukkitUtils.getRelativeTopFallables().getLocation();
                int y = loc.getBlockY();
                org.bukkit.Location loc = BukkitUtils.getRelativeTopFallables().getLocation();
                int z = loc.getBlockZ();
                // If y is 0 then the sand block fell out of the world :(
                if (y != 0) {
                    org.bukkit.Location finalLoc = new org.bukkit.Location(loc.getWorld(), x, y, z);
                    org.bukkit.Location finalLoc = new org.bukkit.Location(loc.getWorld(), x, y, z);
                    org.bukkit.Location finalLoc = new org.bukkit.Location(loc.getWorld(), x, y, z);
                    if (finalLoc.getBlock().getType() == org.bukkit.Material.AIR) {
                        consumer.queueBlockPlace(playerName, finalLoc, type, BukkitUtils.getRelativeTopFallables().getData());
                    } else {
                        consumer.queueBlockReplace(playerName, finalLoc, BukkitUtils.getRelativeTopFallables().getTypeId(), BukkitUtils.getRelativeTopFallables().getData(), type, BukkitUtils.getRelativeTopFallables().getData());
                    }
                }
                return;
            }
            // Sign logging is handled elsewhere
            if (wcfg.isLogging(Logging.SIGNTEXT) && ((type == 63) || (type == 68)))
                return;

            // Delay queuing by one tick to allow data to be updated
            de.diddiz.LogBlock.LogBlock.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(de.diddiz.LogBlock.LogBlock.getInstance(), __SmPLUnsupported__(1), 1L);
        }
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerBucketEmpty(org.bukkit.event.player.PlayerBucketEmptyEvent event) {
        if (Config.isLogging(event.getPlayer().getWorld(), Logging.BLOCKPLACE))
            consumer.queueBlockPlace(event.getPlayer().getName(), event.getBlockClicked().getRelative(event.getBlockFace()).getLocation(), event.getBucket() == org.bukkit.Material.WATER_BUCKET ? 9 : 11, ((byte) (0)));

    }
}