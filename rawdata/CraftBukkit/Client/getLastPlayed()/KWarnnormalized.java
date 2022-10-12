@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String commandLabel, java.lang.String[] args) {
    org.bukkit.command.ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
    java.lang.String prefix = org.bukkit.ChatColor.GREEN + "[Kustom Warn]";
    java.lang.String reason = " ";
    for (int i = 1; i < args.length; i++) {
        reason += args[i] + " ";
    }
    if (commandLabel.equalsIgnoreCase("kwarn")) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            if (args.length == 0) {
                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Not enough arguments!");
                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Usage: /kwarn [player] (reason)");
                return true;
            } else if (args.length == 1) {
                org.bukkit.entity.Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                if (targetPlayer != null) {
                    java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                    if (plugin.getConfig().getBoolean("Alert Admins")) {
                        org.bukkit.command.Command.broadcastCommandMessage(sender, ((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned by a console user!");
                        targetPlayer.sendMessage((prefix + org.bukkit.ChatColor.RED) + plugin.getConfig().getString("Warning Message"));
                        consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()))) + " time(s)!");
                        dbStore.setOffenderName(targetPlayer.getName());
                        dbStore.setName("Console User");
                        dbStore.setDate(getDate());
                        /* plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), "Console User", getDate());
                        plugin.warnedPlayers.save();
                        if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                        } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                        targetPlayer.setBanned(true);
                        }
                         */
                        return true;
                    } else {
                        consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                        /* plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), "console user", getDate());
                        plugin.warnedPlayers.save();
                        if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                        } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                        targetPlayer.setBanned(true);
                        }
                         */
                    }
                    return true;
                } else {
                    org.bukkit.OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                    java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                    int _CVAR0 = 0;
                    boolean _CVAR1 = offlinePlayer.getLastPlayed() != _CVAR0;
                    if () {
                        if (plugin.getConfig().getBoolean("Alert Admin")) {
                            org.bukkit.command.Command.broadcastCommandMessage(sender, ((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned by a console user");
                            consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                            consoleSender.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                            /* offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                            plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), "console user", getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            offlinePlayer.setBanned(true);
                            }
                             */
                            return true;
                        } else {
                            consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                            consoleSender.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                            /* plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), "console user", getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            offlinePlayer.setBanned(true);
                            }
                             */
                            return true;
                        }
                    } else {
                        consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Player not found!");
                        return true;
                    }
                }
            } else if (args.length >= 2) {
                org.bukkit.entity.Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                if (targetPlayer != null) {
                    java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                    if (plugin.getConfig().getBoolean("Alert Admins")) {
                        org.bukkit.command.Command.broadcastCommandMessage(sender, (((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned by a console user for ") + reason);
                        targetPlayer.sendMessage((((prefix + org.bukkit.ChatColor.RED) + plugin.getConfig().getString("Warning For")) + " ") + reason);
                        consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()))) + " time(s)");
                        /* plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), "console user", reason, getDate());
                        plugin.warnedPlayers.save();
                        if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                        } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                        targetPlayer.setBanned(true);
                        }
                         */
                        return true;
                    } else {
                        consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                        consoleSender.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " is not currently online and will receive their warning when they join!");
                        /* plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), "console user", reason, getDate());
                        plugin.warnedPlayers.save();
                        if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                        } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                        targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                        targetPlayer.setBanned(true);
                        }
                         */
                        return true;
                    }
                } else {
                    org.bukkit.OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                    java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                    int _CVAR2 = 0;
                    boolean _CVAR3 = offlinePlayer.getLastPlayed() != _CVAR2;
                    if () {
                        if (plugin.getConfig().getBoolean("Alert Admin")) {
                            org.bukkit.command.Command.broadcastCommandMessage(sender, (((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned by a console user for ") + reason);
                            consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                            consoleSender.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                            /* offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                            plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), "console user", reason, getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            offlinePlayer.setBanned(true);
                            }
                             */
                            return true;
                        } else {
                            consoleSender.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                            consoleSender.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                            /* plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), "console user", reason, getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            offlinePlayer.setBanned(true);
                            }
                             */
                            return true;
                        }
                    } else {
                        consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Player not found!");
                        return true;
                    }
                }
            }
        } else {
            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
            if (player.hasPermission("kustomwarn.warn") || player.isOp()) {
                if (args.length == 0) {
                    player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Not enough arguments!");
                    player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Usage: /kwarn [player] (reason)");
                    return true;
                } else if (args.length == 1) {
                    org.bukkit.entity.Player targetPlayer = player.getServer().getPlayer(args[0]);
                    if (targetPlayer != null) {
                        java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                        if (plugin.getConfig().getBoolean("Alert Admins")) {
                            org.bukkit.command.Command.broadcastCommandMessage(consoleSender, (((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned by ") + player.getName());
                            targetPlayer.sendMessage((prefix + org.bukkit.ChatColor.RED) + plugin.getConfig().getString("Warning"));
                            player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                            /* plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), player.getName(), getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                            targetPlayer.setBanned(true);
                            }
                             */
                            return true;
                        } else {
                            targetPlayer.sendMessage((((prefix + org.bukkit.ChatColor.RED) + plugin.getConfig().getString("Warning For")) + " ") + reason);
                            player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                            /* plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), player.getName(), getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                            targetPlayer.setBanned(true);
                            }
                             */
                            return true;
                        }
                    } else {
                        org.bukkit.OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                        java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                        int _CVAR4 = 0;
                        boolean _CVAR5 = offlinePlayer.getLastPlayed() != _CVAR4;
                        if () {
                            if (plugin.getConfig().getBoolean("Alert Admin")) {
                                org.bukkit.command.Command.broadcastCommandMessage(consoleSender, (((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned by ") + player.getName());
                                player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                                player.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                                /* plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), player.getName(), getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                offlinePlayer.setBanned(true);
                                }
                                 */
                                return true;
                            } else {
                                player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                                player.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                                /* offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), player.getName(), getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                offlinePlayer.setBanned(true);
                                }
                                 */
                                return true;
                            }
                        } else {
                            player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Player not found!");
                            return true;
                        }
                    }
                } else if (args.length >= 2) {
                    org.bukkit.entity.Player targetPlayer = player.getServer().getPlayer(args[0]);
                    if (targetPlayer != null) {
                        java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                        if (plugin.getConfig().getBoolean("Alert Admins")) {
                            org.bukkit.command.Command.broadcastCommandMessage(consoleSender, (((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned by ") + player.getName()) + " for ") + reason);
                            targetPlayer.sendMessage((((prefix + org.bukkit.ChatColor.RED) + plugin.getConfig().getString("Warning For")) + " ") + reason);
                            player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                            /* plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), player.getName(), reason, getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                            targetPlayer.setBanned(true);
                            }
                             */
                            return true;
                        } else {
                            targetPlayer.sendMessage((((prefix + org.bukkit.ChatColor.RED) + plugin.getConfig().getString("Warning For")) + " ") + reason);
                            player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + targetPlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1)) + " time(s)!");
                            /* plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), player.getName(), reason, getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                            targetPlayer.setBanned(true);
                            }
                             */
                            return true;
                        }
                    } else {
                        org.bukkit.OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                        java.lang.String indexNumber = java.lang.String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                        int _CVAR6 = 0;
                        boolean _CVAR7 = offlinePlayer.getLastPlayed() != _CVAR6;
                        if () {
                            if (plugin.getConfig().getBoolean("Alert Admin")) {
                                org.bukkit.command.Command.broadcastCommandMessage(consoleSender, (((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned by a console user for ") + reason);
                                player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                                player.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                                /* offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), player.getName(), reason, getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                offlinePlayer.setBanned(true);
                                }
                                 */
                                return true;
                            } else {
                                player.sendMessage(((((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " has been warned ") + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1)) + " time(s)!");
                                player.sendMessage(((prefix + org.bukkit.ChatColor.YELLOW) + offlinePlayer.getName()) + " is not currently online and will receive their warning when they join!");
                                /* offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), player.getName(), reason, getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                offlinePlayer.setBanned(true);
                                }
                                 */
                                return true;
                            }
                        } else {
                            player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Player not found");
                            return true;
                        }
                    }
                }
            }
        }
    }
    return true;
}