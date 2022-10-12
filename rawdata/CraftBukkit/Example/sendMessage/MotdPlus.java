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

import org.bukkit.plugin.RegisteredServiceProvider;
import net.milkbowl.vault.permission.Permission;

public class MotdPlus extends JavaPlugin implements Listener {

    public static Permission perms = null;
    protected LanguageWrapper t;

    public void sendMotd(CommandSender sender) {
        if (perms != null && perms.has(sender, "MOTDPlus.motd") == true) {
            sender.sendMessage(
                    t.get(sender, "command.motd", ChatColor.GREEN + "MOTD: {0}", getMessage(sender)));
        }
        else
            getLogger().warning("No permissions found, not sending motd.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        getLogger().info("Sending motd.");
        sendMotd(e.getPlayer());
    }

    public void onEnable() {
        t = new LanguageWrapper(this, "eng");

        getConfig().options().copyDefaults(true);
        getConfig().addDefault("motd.default", "Welcome");

        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();

        if (perms == null) {
            getLogger().warning("Could not find vault permissions, disabling plugin.");
            setEnabled(false);
        }
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(
                t.get(sender, "command.setmotd.error.arg", ChatColor.RED + "Usage: " + ChatColor.YELLOW + "/<command> <language code> <message>"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("motd")) {
            if (perms != null && perms.has(sender, "MOTDPlus.motd") == false) {
                sender.sendMessage(
                        t.get(sender, "command.motd.deny", ChatColor.RED + "You are not permitted to do this"));
                return true;
            }
            sendMotd(sender);
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("setmotd")) {
            if (perms == null || perms.has(sender, "MOTDPlus.setmotd") == false) {
                sender.sendMessage(
                        t.get(sender, "command.setmotd.deny", ChatColor.RED + "You are not permitted to do this!"));
                return true;
            }
            if (args.length < 2) {
                sendUsage(sender);
            }

            StringBuilder str = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                str.append(args[i]).append(" ");
            }
            String motd = str.toString();
            getConfig().set("motd." + args[0], motd);
            saveConfig();
            
            getLogger().info(sender.getName() + " Set the motd of language "+args[0]+" to "+motd);

            sender.sendMessage(
                    t.get(sender, "command.setmotd", ChatColor.GREEN + "MOTD set to: {0}", motd));
            return true;
        }
        return true;
    }

    public String getMessage(CommandSender sender) {
        ConfigurationSection motd = getConfig().getConfigurationSection("motd");

        if (motd != null) {
            String locale = t.getLocale(sender);
            if (motd.contains(locale)) {
                return motd.getString(locale);
            }

            return motd.getString("default");
        }

        return "";
    }
}