package me.parisfuja.plugins.motdplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import org.bukkit.configuration.ConfigurationSection;

public class MotdPlus extends JavaPlugin implements Listener
{

	public String getMessage(CommandSender sender)
	{
		ConfigurationSection motd = getConfig().getConfigurationSection("motd");

		String locale = t.getLocale(sender);
		if (motd.contains(locale))
		{
			return motd.getString(locale);
		}

		return motd.getString("default");
	}
	LanguageWrapper t;

	public void sendMotd(CommandSender sender)
	{
		sender.sendMessage(
				t.get(sender, "command.motd", ChatColor.GREEN + "MOTD: {0}", getMessage(sender)));
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e)
	{
		sendMotd(e.getPlayer());
	}

	public void onEnable()
	{
		t = new LanguageWrapper(this, "eng");

		getConfig().options().copyDefaults(true);
		getConfig().addDefault("motd.default", "Welcome");

		saveConfig();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("motd"))
		{
			sendMotd(sender);
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("setmotd"))
		{
			if (!sender.hasPermission("MOTDPlus.setmotd"))
			{
				sender.sendMessage(
						t.get(sender, "command.setmotd.deny", ChatColor.RED + "You are not permitted to do this!"));
				return true;
			}
			if (args.length == 0)
			{
				sender.sendMessage(
						t.get(sender, "command.setmotd.error.arg", ChatColor.RED + "Please specify a message!"));
				return true;
			}

			StringBuilder str = new StringBuilder();
			for (int i = 0; i < args.length; i++)
			{
				str.append(args[i] + " ");
			}
			String motd = str.toString();
			getConfig().set("message", motd);
			saveConfig();

			sender.sendMessage(
					t.get(sender, "command.setmotd", ChatColor.GREEN + "MOTD set to: {0}", motd));
			return true;
		}
		return true;
	}
}