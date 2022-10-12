/*
 * Copyright (c) 2011 Urs P. Stettler, https://github.com/cryxli
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.minecraft.death.persist.flat;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import li.cryx.minecraft.death.persist.AbstractPersistManager;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.util.DummyPlayer;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Persist plugin data to flat files (YAML format). There is a mechanism to
 * store complete <code>ItemStack</code>s using YAML. (ItemStack = item instance
 * in game world.)
 * 
 * <p>
 * This implementation will create one YML file per dead player. The contents of
 * the file will be kept in memory for 5 minutes before it is unloaded. After
 * that The YML structure has to be rebuild from the persisted file. This is
 * done to prevent memory leaks.
 * </p>
 * 
 * @author cryxli
 */
public class PersistenceFlatFile extends AbstractPersistManager {

	/** How often to save files [ms]. Defaults to every 10s. */
	private static final long SAVE_INTERVAL = 10 * 1000;

	private static final int TIMEOUT_TICKS = (int) Math
			.ceil(5 * 60 * 1000 / SAVE_INTERVAL);

	private static final int DIRTY_TICK = 0;

	private static final int NOT_DIRTY_TICK = 1;

	/**
	 * Trick to have death locations stored like player kills/deaths
	 */
	private static final Player DEATH_LOC_PLAYER = new DummyPlayer(
			"DeathLocationPlayer");

	private boolean autoSave = false;

	/** Folder that contains killing stats. */
	private final File countFolder;

	/** Folder that contains inventories. */
	private final File itemFolder;

	private final Map<Player, YamlConfiguration> kills = new HashMap<Player, YamlConfiguration>();

	private final Map<Player, Integer> timeout = new HashMap<Player, Integer>();

	public PersistenceFlatFile(final JavaPlugin plugin) {
		super(plugin);

		File rootFolder = plugin.getDataFolder();
		countFolder = new File(rootFolder, "stats");
		if (!countFolder.isDirectory() && !countFolder.mkdirs()) {
			plugin.getLogger().log(Level.SEVERE,
					"Failed to create stats folder");
		}
		itemFolder = new File(rootFolder, "inventory");
		if (!itemFolder.isDirectory() && !itemFolder.mkdirs()) {
			plugin.getLogger().log(Level.SEVERE,
					"Failed to create items folder");
		}

		// start storing interval
		Thread intervalStorage = new Thread() {
			@Override
			public void run() {
				while (autoSave) {
					flush();
					try {
						Thread.sleep(SAVE_INTERVAL);
					} catch (InterruptedException e) {
					}
				}
			}
		};
		autoSave = true;
		intervalStorage.setDaemon(true);
		intervalStorage.start();
	}

	@Override
	public void deleteItems(final Player player) {
		File file = getInventoryFile(player);
		if (!file.delete()) {
			plugin.getLogger().log(Level.SEVERE,
					"Unable to delete inventory for " + player.getName());
		}
	}

	public synchronized void flush() {
		List<Player> toUnload = new LinkedList<Player>();
		for (Player player : kills.keySet()) {
			YamlConfiguration data = getKills(player);
			int tick = timeout.get(player);
			if (tick == 0) {
				try {
					data.save(new File(countFolder, player.getName()
							.toLowerCase() + ".yml"));
					timeout.put(player, NOT_DIRTY_TICK);
				} catch (IOException e) {
					plugin.getLogger().log(Level.SEVERE,
							"Unable to persist kills", e);
				}
			} else if (tick == TIMEOUT_TICKS) {
				toUnload.add(player);
			} else {
				timeout.put(player, 1 + tick);
			}
		}
		for (Player player : toUnload) {
			kills.remove(player);
			timeout.remove(player);
			plugin.getLogger().fine("Unloaded " + player.getName());
		}
	}

	@Override
	public Location getDeathLocation(final Player player) {
		YamlConfiguration yml = getKills(DEATH_LOC_PLAYER);

		String name = player.getName();
		World world = player.getServer().getWorld(
				yml.getString(name + ".world"));
		if (world == null) {
			return null;
		}
		double x = yml.getDouble(name + ".x", Double.NaN);
		double y = yml.getDouble(name + ".y", Double.NaN);
		double z = yml.getDouble(name + ".z", Double.NaN);
		if (x == Double.NaN || y == Double.NaN || z == Double.NaN) {
			return null;
		} else {
			return new Location(world, x, y, z);
		}
	}

	@Override
	public FragsInfo getFrags(final Player player) {
		return new FragsInfoFlatFile(getKills(player));
	}

	/**
	 * Get the file containing the inventory for the given player.
	 * 
	 * @param player
	 *            Player in question
	 * @return A <code>File</code> object.
	 */
	private File getInventoryFile(final Player player) {
		// return new File(itemFolder, player.getName().toLowerCase() + ".yml");
		return new File(itemFolder, player.getUniqueId().toString() + ".yml");
	}

	private synchronized YamlConfiguration getKills(final Player player) {
		YamlConfiguration kp = kills.get(player);
		if (kp == null) {
			kp = YamlConfiguration.loadConfiguration(new File(countFolder,
					player.getName().toLowerCase() + ".yml"));
			kills.put(player, kp);
			timeout.put(player, NOT_DIRTY_TICK);
		}
		return kp;
	}

	@Override
	public boolean hasInventory(final Player player) {
		return getInventoryFile(player).exists();
	}

	@Override
	public void increaseDeaths(final Player player, final LivingEntityType type) {
		if (type == null) {
			return;
		}
		YamlConfiguration conf = getKills(player);

		String key = "killed." + type.toString();
		conf.set(key, 1 + conf.getInt(key));
		timeout.put(player, DIRTY_TICK);
	}

	@Override
	public void increaseKills(final Player player, final LivingEntityType type) {
		if (type == null) {
			return;
		}
		YamlConfiguration conf = getKills(player);

		String key = "kills." + type.toString();
		conf.set(key, 1 + conf.getInt(key));
		timeout.put(player, DIRTY_TICK);
	}

	private YamlConfiguration loadInventoryFile(final Player player) {
		File file = getInventoryFile(player);
		return YamlConfiguration.loadConfiguration(file);
	}

	@Override
	public boolean persistDeathLocation(final Player player) {
		Location loc = player.getLocation();
		YamlConfiguration yml = getKills(DEATH_LOC_PLAYER);

		String name = player.getName();
		yml.set(name + ".world", player.getWorld().getName());
		yml.set(name + ".x", loc.getX());
		yml.set(name + ".y", loc.getY());
		yml.set(name + ".z", loc.getZ());

		timeout.put(DEATH_LOC_PLAYER, DIRTY_TICK);
		return true;
	}

	@Override
	public boolean persistItems(final Player player) {
		// add inventory
		List<ItemStack> items = getAllItemsOfPlayer(player);

		try {
			YamlConfiguration data = loadInventoryFile(player);
			int counter = 1;
			for (ItemStack item : items) {
				data.set("item" + counter, item);
				counter++;
			}
			data.set("playerName", player.getName());
			data.save(getInventoryFile(player));
			return true;
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE,
					"Cannot store player's inventory", e);
			return false;
		}
	}

	@Override
	public List<ItemStack> restoreItems(final Player player) {
		YamlConfiguration data = loadInventoryFile(player);
		plugin.getLogger().log(Level.INFO,
				"Loading inventory for " + player.getName());
		List<ItemStack> items = new LinkedList<ItemStack>();
		for (int i = 1; i <= 40; i++) {
			if (!data.isItemStack("item" + i)) {
				break;
			}
			items.add(data.getItemStack("item" + i));
		}
		return items;
	}

	@Override
	public void shutdown() {
		autoSave = false;
		flush();
	}
}
