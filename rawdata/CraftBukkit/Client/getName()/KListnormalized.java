@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String commandLabel, java.lang.String[] args) {
    java.lang.String prefix = org.bukkit.ChatColor.GREEN + "[Kustom Warn]";
    org.bukkit.Server _CVAR44 = _CVAR42;
    org.bukkit.command.ConsoleCommandSender consoleSender = _CVAR44.getConsoleSender();
    org.bukkit.Server _CVAR12 = _CVAR10;
    org.bukkit.Server _CVAR33 = _CVAR12;
    org.bukkit.Server _CVAR56 = _CVAR33;
    org.bukkit.command.ConsoleCommandSender consoleSender = _CVAR56.getConsoleSender();
    org.bukkit.command.CommandSender _CVAR0 = sender;
    org.bukkit.command.CommandSender _CVAR9 = _CVAR0;
    org.bukkit.command.CommandSender _CVAR24 = _CVAR9;
    org.bukkit.command.CommandSender _CVAR41 = _CVAR24;
    org.bukkit.command.CommandSender _CVAR71 = _CVAR41;
    org.bukkit.Server _CVAR1 = _CVAR71.getServer();
    org.bukkit.Server _CVAR10 = _CVAR1;
    org.bukkit.Server _CVAR25 = _CVAR10;
    org.bukkit.Server _CVAR42 = _CVAR25;
    org.bukkit.Server _CVAR72 = _CVAR42;
    org.bukkit.command.ConsoleCommandSender consoleSender = _CVAR72.getConsoleSender();
    if (commandLabel.equalsIgnoreCase("klist")) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            if (args.length == 0) {
                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Not enough arguments!");
                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Usage: /klist [player]");
                return true;
            } else if (args.length == 1) {
                org.bukkit.command.ConsoleCommandSender _CVAR2 = consoleSender;
                org.bukkit.command.ConsoleCommandSender _CVAR13 = _CVAR2;
                org.bukkit.command.ConsoleCommandSender _CVAR26 = _CVAR13;
                org.bukkit.command.ConsoleCommandSender _CVAR34 = _CVAR26;
                org.bukkit.command.ConsoleCommandSender _CVAR45 = _CVAR34;
                org.bukkit.command.ConsoleCommandSender _CVAR57 = _CVAR45;
                org.bukkit.Server _CVAR3 = _CVAR57.getServer();
                org.bukkit.Server _CVAR14 = _CVAR3;
                org.bukkit.Server _CVAR27 = _CVAR14;
                org.bukkit.Server _CVAR35 = _CVAR27;
                org.bukkit.Server _CVAR46 = _CVAR35;
                java.lang.String _CVAR4 = args[0];
                java.lang.String _CVAR15 = _CVAR4;
                java.lang.String _CVAR28 = _CVAR15;
                java.lang.String _CVAR36 = _CVAR28;
                java.lang.String _CVAR47 = _CVAR36;
                org.bukkit.Server _CVAR58 = _CVAR46;
                java.lang.String _CVAR59 = _CVAR47;
                org.bukkit.entity.Player targetPlayer = _CVAR58.getPlayer(_CVAR59);
                if (targetPlayer.isOnline()) {
                    org.bukkit.entity.Player _CVAR5 = targetPlayer;
                    java.lang.String _CVAR6 = _CVAR5.getName();
                     _CVAR7 = plugin.warnedPlayers.getWarningTotal(_CVAR6);
                     _CVAR8 = _CVAR7 == 0;
                    if () {
                        org.bukkit.entity.Player _CVAR16 = targetPlayer;
                        java.lang.String _CVAR17 = _CVAR16.getName();
                        java.lang.String _CVAR18 = (prefix + org.bukkit.ChatColor.YELLOW) + _CVAR17;
                        org.bukkit.command.ConsoleCommandSender _CVAR11 = consoleSender;
                        java.lang.String _CVAR19 = _CVAR18 + " has not received any warnings";
                        _CVAR11.sendMessage(_CVAR19);
                        return true;
                    } else {
                        me.kustomkraft.kustomwarn.KustomWarn _CVAR20 = plugin;
                        java.lang.String _CVAR22 = ".warnings";
                         _CVAR21 = _CVAR20.getCustomConfiguration();
                        java.lang.String _CVAR23 = targetPlayer.getName() + _CVAR22;
                        java.util.List warningsList = _CVAR21.getStringList(_CVAR23);
                        org.bukkit.entity.Player _CVAR29 = targetPlayer;
                        java.lang.String _CVAR30 = _CVAR29.getName();
                         _CVAR31 = plugin.warnedPlayers.getWarningTotal(_CVAR30);
                         _CVAR32 = _CVAR31 != 0;
                        if () {
                            for (java.lang.Object s : warningsList) {
                                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + s);
                            }
                        }
                    }
                } else if (targetPlayer.hasPlayedBefore()) {
                    org.bukkit.entity.Player _CVAR37 = targetPlayer;
                    java.lang.String _CVAR38 = _CVAR37.getName();
                     _CVAR39 = plugin.warnedPlayers.getWarningTotal(_CVAR38);
                     _CVAR40 = _CVAR39 == 0;
                    if () {
                        org.bukkit.entity.Player _CVAR48 = targetPlayer;
                        java.lang.String _CVAR49 = _CVAR48.getName();
                        java.lang.String _CVAR50 = (prefix + org.bukkit.ChatColor.YELLOW) + _CVAR49;
                        org.bukkit.command.ConsoleCommandSender _CVAR43 = consoleSender;
                        java.lang.String _CVAR51 = _CVAR50 + " has not received any warnings";
                        _CVAR43.sendMessage(_CVAR51);
                        return true;
                    } else {
                        me.kustomkraft.kustomwarn.KustomWarn _CVAR52 = plugin;
                        java.lang.String _CVAR54 = ".warnings";
                         _CVAR53 = _CVAR52.getCustomConfiguration();
                        java.lang.String _CVAR55 = targetPlayer.getName() + _CVAR54;
                        java.util.List warningsList = _CVAR53.getStringList(_CVAR55);
                        org.bukkit.entity.Player _CVAR60 = targetPlayer;
                        java.lang.String _CVAR61 = _CVAR60.getName();
                         _CVAR62 = plugin.warnedPlayers.getWarningTotal(_CVAR61);
                         _CVAR63 = _CVAR62 != 0;
                        if () {
                            for (java.lang.Object s : warningsList) {
                                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + s);
                            }
                        }
                    }
                } else {
                    consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + "This player has not played on this server!");
                    return true;
                }
            }
        } else {
            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
            if (args.length == 0) {
                player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Not enough arguments!");
                player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "Usage: /klist [player]");
                return true;
            } else if (args.length == 1) {
                org.bukkit.entity.Player _CVAR64 = player;
                org.bukkit.entity.Player _CVAR74 = _CVAR64;
                org.bukkit.entity.Player _CVAR85 = _CVAR74;
                org.bukkit.entity.Player _CVAR92 = _CVAR85;
                org.bukkit.entity.Player _CVAR100 = _CVAR92;
                org.bukkit.entity.Player _CVAR111 = _CVAR100;
                org.bukkit.Server _CVAR65 = _CVAR111.getServer();
                org.bukkit.Server _CVAR75 = _CVAR65;
                org.bukkit.Server _CVAR86 = _CVAR75;
                org.bukkit.Server _CVAR93 = _CVAR86;
                org.bukkit.Server _CVAR101 = _CVAR93;
                java.lang.String _CVAR66 = args[0];
                java.lang.String _CVAR76 = _CVAR66;
                java.lang.String _CVAR87 = _CVAR76;
                java.lang.String _CVAR94 = _CVAR87;
                java.lang.String _CVAR102 = _CVAR94;
                org.bukkit.Server _CVAR112 = _CVAR101;
                java.lang.String _CVAR113 = _CVAR102;
                org.bukkit.entity.Player targetPlayer = _CVAR112.getPlayer(_CVAR113);
                if (targetPlayer.isOnline()) {
                    org.bukkit.entity.Player _CVAR67 = targetPlayer;
                    java.lang.String _CVAR68 = _CVAR67.getName();
                     _CVAR69 = plugin.warnedPlayers.getWarningTotal(_CVAR68);
                     _CVAR70 = _CVAR69 == 0;
                    if () {
                        org.bukkit.entity.Player _CVAR77 = targetPlayer;
                        java.lang.String _CVAR78 = _CVAR77.getName();
                        java.lang.String _CVAR79 = (prefix + org.bukkit.ChatColor.YELLOW) + _CVAR78;
                        org.bukkit.command.ConsoleCommandSender _CVAR73 = consoleSender;
                        java.lang.String _CVAR80 = _CVAR79 + " has not received any warnings";
                        _CVAR73.sendMessage(_CVAR80);
                        return true;
                    } else {
                        me.kustomkraft.kustomwarn.KustomWarn _CVAR81 = plugin;
                        java.lang.String _CVAR83 = ".warnings";
                         _CVAR82 = _CVAR81.getCustomConfiguration();
                        java.lang.String _CVAR84 = targetPlayer.getName() + _CVAR83;
                        java.util.List warningsList = _CVAR82.getStringList(_CVAR84);
                        org.bukkit.entity.Player _CVAR88 = targetPlayer;
                        java.lang.String _CVAR89 = _CVAR88.getName();
                         _CVAR90 = plugin.warnedPlayers.getWarningTotal(_CVAR89);
                         _CVAR91 = _CVAR90 != 0;
                        if () {
                            for (java.lang.Object s : warningsList) {
                                consoleSender.sendMessage((prefix + org.bukkit.ChatColor.RED) + s);
                            }
                        }
                    }
                } else if (targetPlayer.hasPlayedBefore()) {
                    org.bukkit.entity.Player _CVAR95 = targetPlayer;
                    java.lang.String _CVAR96 = _CVAR95.getName();
                     _CVAR97 = plugin.warnedPlayers.getWarningTotal(_CVAR96);
                     _CVAR98 = _CVAR97 == 0;
                    if () {
                        org.bukkit.entity.Player _CVAR103 = targetPlayer;
                        java.lang.String _CVAR104 = _CVAR103.getName();
                        java.lang.String _CVAR105 = (prefix + org.bukkit.ChatColor.YELLOW) + _CVAR104;
                        org.bukkit.entity.Player _CVAR99 = player;
                        java.lang.String _CVAR106 = _CVAR105 + " has not received any warnings";
                        _CVAR99.sendMessage(_CVAR106);
                        return true;
                    } else {
                        me.kustomkraft.kustomwarn.KustomWarn _CVAR107 = plugin;
                        java.lang.String _CVAR109 = ".warnings";
                         _CVAR108 = _CVAR107.getCustomConfiguration();
                        java.lang.String _CVAR110 = targetPlayer.getName() + _CVAR109;
                        java.util.List warningsList = _CVAR108.getStringList(_CVAR110);
                        org.bukkit.entity.Player _CVAR114 = targetPlayer;
                        java.lang.String _CVAR115 = _CVAR114.getName();
                         _CVAR116 = plugin.warnedPlayers.getWarningTotal(_CVAR115);
                         _CVAR117 = _CVAR116 != 0;
                        if () {
                            for (java.lang.Object s : warningsList) {
                                player.sendMessage((prefix + org.bukkit.ChatColor.RED) + s);
                            }
                        }
                    }
                } else {
                    player.sendMessage((prefix + org.bukkit.ChatColor.RED) + "This player has not played on this server!");
                    return true;
                }
            }
        }
    }
    return true;
}