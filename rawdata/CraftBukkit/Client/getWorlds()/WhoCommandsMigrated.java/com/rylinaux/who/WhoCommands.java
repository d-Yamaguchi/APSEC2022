package com.rylinaux.who;
public class WhoCommands implements org.bukkit.command.CommandExecutor {
    @java.lang.Override
    public boolean onCommand(final org.bukkit.command.CommandSender sender, final org.bukkit.command.Command cmd, final java.lang.String label, final java.lang.String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("who.who")) {
                sender.sendMessage(com.rylinaux.who.Utilities.who());
            } else {
                sender.sendMessage(Who.noPerms);
            }
        } else if (args.length == 1) {
            final java.lang.String s = args[0];
            if (isPlayer(s)) {
                if (sender.hasPermission("who.player")) {
                    final org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayer(s);
                    if (player != null) {
                        com.rylinaux.who.Utilities.onlinePlayerInfo(sender, player);
                    } else {
                        final org.bukkit.OfflinePlayer oplayer = org.bukkit.Bukkit.getPlayer(s);
                        if (oplayer.hasPlayedBefore()) {
                            com.rylinaux.who.Utilities.offlinePlayerInfo(sender, oplayer);
                        } else {
                            sender.sendMessage((Who.pre + org.bukkit.ChatColor.RED) + "Player not found!");
                        }
                    }
                } else {
                    sender.sendMessage(Who.noPerms);
                }
            } else if (isWorld(s)) {
                if (sender.hasPermission("who.world")) {
                    final org.bukkit.World world = org.bukkit.Bukkit.getWorld(s);
                    if (world != null) {
                        sender.sendMessage(com.rylinaux.who.Utilities.who(world));
                    } else {
                        sender.sendMessage((Who.pre + org.bukkit.ChatColor.RED) + "World not found!");
                    }
                } else {
                    sender.sendMessage(Who.noPerms);
                }
            } else {
                sender.sendMessage((Who.pre + org.bukkit.ChatColor.RED) + "Player or world not found!");
            }
        } else {
            sender.sendMessage((Who.pre + org.bukkit.ChatColor.RED) + "Invalid arguments!");
        }
        return true;
    }

    private boolean isPlayer(final java.lang.String s) {
        if (org.bukkit.Bukkit.getOfflinePlayer(s).hasPlayedBefore()) {
            return true;
        }
        return false;
    }

    private boolean isWorld(final java.lang.String s) {
        if () {
            return true;
        }
        return false;
    }
}