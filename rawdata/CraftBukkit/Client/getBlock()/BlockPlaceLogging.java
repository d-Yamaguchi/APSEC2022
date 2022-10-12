package de.diddiz.LogBlock.listeners;

import static de.diddiz.LogBlock.config.Config.getWorldConfig;
import static de.diddiz.LogBlock.config.Config.isLogging;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.Logging;
import de.diddiz.LogBlock.config.WorldConfig;
import de.diddiz.util.BukkitUtils;

public class BlockPlaceLogging extends LoggingListener
{
	public BlockPlaceLogging(LogBlock lb) {
		super(lb);
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		final WorldConfig wcfg = getWorldConfig(event.getBlock().getWorld());
		if (wcfg != null && wcfg.isLogging(Logging.BLOCKPLACE)) {
			final int type = event.getBlock().getTypeId();
			final BlockState before = event.getBlockReplacedState();
			final BlockState after = event.getBlockPlaced().getState();
			final String playerName = event.getPlayer().getName();

			//Handle falling blocks
			if (event.getBlock().getType() == Material.SAND || event.getBlock().getType() == Material.GRAVEL || event.getBlock().getType() == Material.DRAGON_EGG) {
				Location loc = event.getBlock().getLocation();
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				while (y > 0 && BukkitUtils.canFall(loc.getWorld(), x, (y - 1), z)) {
					y--;
				}
				// If y is 0 then the sand block fell out of the world :(
				if (y != 0) {
					Location finalLoc = new Location(loc.getWorld(), x, y, z);
					if (finalLoc.getBlock().getType() == Material.AIR) {
						consumer.queueBlockPlace(playerName, finalLoc, type, event.getBlock().getData());
					} else {
						consumer.queueBlockReplace(playerName, finalLoc, finalLoc.getBlock().getTypeId(), finalLoc.getBlock().getData(), type, event.getBlock().getData());
					}
				}
				return;
			}

			//Sign logging is handled elsewhere
			if (wcfg.isLogging(Logging.SIGNTEXT) && (type == 63 || type == 68))
				return;

			//Delay queuing by one tick to allow data to be updated
			LogBlock.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(LogBlock.getInstance(), new Runnable()
			{
				@Override
				public void run() {
					if (before.getTypeId() == 0)
						consumer.queueBlockPlace(playerName, after);
					else
						consumer.queueBlockReplace(playerName, before, after);
				}
			}, 1L);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
		if (isLogging(event.getPlayer().getWorld(), Logging.BLOCKPLACE))
			consumer.queueBlockPlace(event.getPlayer().getName(), event.getBlockClicked().getRelative(event.getBlockFace()).getLocation(), event.getBucket() == Material.WATER_BUCKET ? 9 : 11, (byte)0);
	}
}
