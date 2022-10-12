/*
 * Copyright (c) 2011 Urs P. Stettler, httpimport java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import li.cryx.minecraft.death.persist.AbstractPersistManager;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.death.persist.db.model.DeathLocation;
import li.cryx.minecraft.death.persist.db.model.Item;
import li.cryx.minecraft.death.persist.db.model.Kills;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.Query;
IMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package li.cryx.minecraft.death.persist.db;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import li.cryx.minecraft.death.persist.AbstractPersistManager;
import li.cryx.minecraft.death.persist.FragsInfo;
import li.cryx.minecraft.death.persist.db.model.DeathLocation;
import li.cryx.minecraft.death.persist.db.model.Item;
import li.cryx.minecraft.death.persist.db.model.Kills;
import li.cryx.minecraft.util.LivingEntityType;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.avaje.ebean.Query;

/**
 * Database implementation of a {@link AbstractPersistManager}. It uses bukkits
 * database mechanisms.
 * 
 * @author cryxli
 */
public class PersistenceDatabase extends AbstractPersistManager {

	private static Logger LOG = Logger.getLogger(PersistenceDatabase.class
			.getName());

	public PersistenceDatabase(final JavaPlugin plugin) {
		super(plugin);
	}

	@Override
	public void deleteItems(final Player player) {
		plugin.getDatabase().delete(getItems(player));
	}

	@Override
	public Location getDeathLocation(final Player player) {
		DeathLocation loc = getDeathLocation(player.getName());
		if (loc == null) {
			return null;
		} else {
			return loc.getLocation(player.getServer());
		}
	}

	/**
	 * Get the death location of the given player
	 * 
	 * @param playerName
	 *            A player by his/her name
	 * @return A {@link DeathLocation}, or, <code>null</code> if none was
	 *         stored.
	 */
	private DeathLocation getDeathLocation(final String playerName) {
		Query<DeathLocation> query = plugin.getDatabase().find(
				DeathLocation.class);
		query.where().eq("player", playerName).setMaxRows(1);
		List<DeathLocation> locs = query.findList();
		if (locs == null || locs.size() == 0) {
			return null;
		} else {
			return locs.get(0);
		}
	}

	@Override
	public FragsInfo getFrags(final Player player) {
		return new FragsInfoDelegator(this, player.getName());
	}

	/**
	 * Get the persisted items of a player.
	 * 
	 * @param player
	 *            The player
	 * @return List of {@link Item}s.
	 */
	private List<Item> getItems(final Player player) {
		Query<Item> query = plugin.getDatabase().createQuery(Item.class);
		query.where().eq("player", player.getName());
		return query.findList();
	}

	/**
	 * Get the kills/death info for a player and opponent type.
	 * 
	 * @param playerName
	 *            The player
	 * @param type
	 *            The opponent
	 * @return The {@link Kills} object counting kills and deaths
	 */
	Kills getSingleKillEntry(final String playerName,
			final LivingEntityType type) {
		Query<Kills> query = plugin.getDatabase().find(Kills.class);
		query.where().eq("player", playerName).eq("entity", type).setMaxRows(1);
		List<Kills> kills = query.findList();
		if (kills == null || kills.size() == 0) {
			Kills k = plugin.getDatabase().createEntityBean(Kills.class);
			k.setPlayer(playerName);
			k.setEntity(type);
			return k;
		} else {
			return kills.get(0);
		}
	}

	@Override
	public boolean hasInventory(final Player player) {
		Query<Item> query = plugin.getDatabase().createQuery(Item.class);
		query.where().eq("player", player.getName());
		return query.findRowCount() > 0;
	}

	@Override
	public void increaseDeaths(final Player player, final LivingEntityType type) {
		Kills kill = getSingleKillEntry(player.getName(), type);
		kill.increaseDeaths();
		plugin.getDatabase().save(kill);
	}

	@Override
	public void increaseKills(final Player player, final LivingEntityType type) {
		Kills kill = getSingleKillEntry(player.getName(), type);
		kill.increaseKills();
		plugin.getDatabase().save(kill);
	}

	@Override
	public boolean persistDeathLocation(final Player player) {
		DeathLocation loc = getDeathLocation(player.getName());
		if (loc == null) {
			loc = plugin.getDatabase().createEntityBean(DeathLocation.class);
		}

		loc.setPlayerEntity(player);
		loc.setLocation(player.getLocation());

		try {
			plugin.getDatabase().save(loc);
			return true;
		} catch (OptimisticLockException e) {
			return false;
		}
	}

	@Override
	public boolean persistItems(final Player player) {
		final List<ItemStack> items = getAllItemsOfPlayer(player);
		boolean success = true;
		for (ItemStack i : items) {
			final Item item = plugin.getDatabase().createEntityBean(Item.class);
			item.setPlayerEntity(player);
			item.setItemStack(i);
			try {
				plugin.getDatabase().save(item);
			} catch (PersistenceException e) {
				LOG.log(Level.SEVERE, "Unable to persist item: " + item, e);
				success = false;
			}
		}
		return success;
	}

	@Override
	public List<ItemStack> restoreItems(final Player player) {
		List<ItemStack> items = new LinkedList<ItemStack>();
		for (Item item : getItems(player)) {
			items.add(item.getItemStack());
		}
		return items;
	}

}
