package me.desht.testbookevents;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBookEditEvent;
import org.bukkit.event.player.PlayerBookSignEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class testbookevents extends JavaPlugin implements Listener {

	@Override
	public void onEnable() { 
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("btrepl") && sender instanceof Player) {
			if (args.length >= 2) {
				final int slot = Integer.parseInt(args[0]);
				final int delay = Integer.parseInt(args[1]);
				final Player p = (Player) sender;
				Bukkit.getScheduler().runTaskLater(this, new Runnable() {
					@Override
					public void run() {
						p.getInventory().setItem(slot, new ItemStack(Material.DIRT, 1));
					}
				}, delay * 20L);
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void bookSigned(PlayerBookSignEvent event) {
		getLogger().info("PlayerBookSignEvent: player = " + event.getPlayer().getName() + ", inv slot = " + event.getSlot());
		if (!event.getPlayer().hasPermission("books.sign")) {
			// test event cancellation
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to sign books!");
		} else {
			BookMeta bm = event.getNewBookMeta();
			if (bm.getTitle().contains("dirt")) {
				// test replacing the book with some other item
				event.getPlayer().setItemInHand(new ItemStack(Material.DIRT, 1));
			} else if (bm.getTitle().equals("replacemeta")) {
				// test event.setBookMeta() to replace meta 
				bm.setTitle("replaced!");
				event.setNewBookMeta(bm);
			} else {
				// test setting title in event's new book meta
				// this should have no effect!  we haven't used event.setNewBookMeta()
				bm.setTitle(bm.getTitle().replace("rudeword", "****"));
			}
		}
	}

	@EventHandler
	public void bookEdited(PlayerBookEditEvent event) {
		getLogger().info("PlayerBookEditEvent: player = " + event.getPlayer().getName() + ", inv slot = " + event.getSlot());
		if (!event.getPlayer().hasPermission("books.edit")) {
			// test event cancellation
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "You are not allowed to edit books!");
		} else {
			// test setting page text in event's new book meta
			BookMeta bm = event.getNewBookMeta();
			List<String> pages = bm.getPages();
			List<String> newPages = new ArrayList<String>(pages.size());
			for (String page : pages) {
				newPages.add(page.replace("rudeword", "****"));
			}
			bm.setPages(newPages);
			event.setNewBookMeta(bm);
		}
	}
}
