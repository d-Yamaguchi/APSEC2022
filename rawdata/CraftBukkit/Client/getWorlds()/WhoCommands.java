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

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("who.who")) {
                sender.sendMessage(Utilities.who());
            } else {
                sender.sendMessage(Who.noPerms);
            }
        } else if (args.length == 1) {
            final String s = args[0];
            if (isPlayer(s)) {
                if (sender.hasPermission("who.player")) {
                    final Player player = Bukkit.getPlayer(s);
                    if (player != null) {
                        Utilities.onlinePlayerInfo(sender, player);
                    } else {
                        final OfflinePlayer oplayer = Bukkit.getPlayer(s);
                        if (oplayer.hasPlayedBefore()) {
                            Utilities.offlinePlayerInfo(sender, oplayer);
                        } else {
                            sender.sendMessage(Who.pre + ChatColor.RED + "Player not found!");
                        }
                    }
                } else {
                    sender.sendMessage(Who.noPerms);
                }
            } else if (isWorld(s)) {
                if (sender.hasPermission("who.world")) {
                    final World world = Bukkit.getWorld(s);
                    if (world != null) {
                        sender.sendMessage(Utilities.who(world));
                    } else {
                        sender.sendMessage(Who.pre + ChatColor.RED + "World not found!");
                    }
                } else {
                    sender.sendMessage(Who.noPerms);
                }
            } else {
                sender.sendMessage(Who.pre + ChatColor.RED + "Player or world not found!");
            }
        } else {
            sender.sendMessage(Who.pre + ChatColor.RED + "Invalid arguments!");
        }
        return true;
    }

    private boolean isPlayer(final String s) {
        if (Bukkit.getOfflinePlayer(s).hasPlayedBefore()) {
            return true;
        }
        return false;
    }

    private boolean isWorld(final String s) {
        if (Bukkit.getWorlds().contains(s)) {
            return true;
        }
        return false;
    }
}