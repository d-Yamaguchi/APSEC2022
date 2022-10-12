package com.rylinaux.who;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoCommands implements CommandExecutor {

    private final Who plugin;

    public WhoCommands(Who plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            if (sender.hasPermission("who.who")) {
                sender.sendMessage(plugin.getUtils().who());
            } else {
                sender.sendMessage(Who.PREFIX + Who.NO_PERMS);
            }
        } else if (args.length == 1) {
            String s = args[0];
            if (isPlayer(s)) {
                if (sender.hasPermission("who.player")) {
                    Player player = plugin.getServer().getPlayer(s);
                    if (player != null) {
                        plugin.getUtils().playerInfo(sender, player);
                    } else {
                        OfflinePlayer oplayer = plugin.getServer().getOfflinePlayer(s);
                        if (oplayer.hasPlayedBefore()) {
                            plugin.getUtils().playerInfo(sender, oplayer);
                        } else {
                            sender.sendMessage(Who.PREFIX + ChatColor.RED + "Player not found!");
                        }
                    }
                } else {
                    sender.sendMessage(Who.PREFIX + Who.NO_PERMS);
                }
            } else if (isWorld(s)) {
                if (sender.hasPermission("who.world")) {
                    World world = plugin.getServer().getWorld(s);
                    if (world != null) {
                        sender.sendMessage(plugin.getUtils().who(world));
                    } else {
                        sender.sendMessage(Who.PREFIX + ChatColor.RED + "World not found!");
                    }
                } else {
                    sender.sendMessage(Who.PREFIX + Who.NO_PERMS);
                }
            } else {
                sender.sendMessage(Who.PREFIX + ChatColor.RED + "Player or world not found!");
            }
        } else {
            sender.sendMessage(Who.PREFIX + ChatColor.RED + "Invalid arguments!");
        }
        return true;
    }

    private boolean isPlayer(String s) {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            if (p.getName().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWorld(String s) {
        for (World w : plugin.getServer().getWorlds()) {
            if (w.getName().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}