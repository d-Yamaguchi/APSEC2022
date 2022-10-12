public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
    if (commandLabel.equalsIgnoreCase("serubans")) {
        if ((sender.hasPermission(SeruBans.DEBUGPERM) || sender.isOp()) || (!(sender instanceof org.bukkit.entity.Player))) {
            if (args.length == 0) {
                boolean banPerm = false;
                 _CVAR1 = net.serubin.serubans.SeruBans.getVersion();
                org.bukkit.command.CommandSender _CVAR0 = sender;
                 _CVAR2 = (((org.bukkit.ChatColor.GOLD + "Serubans ") + org.bukkit.ChatColor.YELLOW) + " version ") + _CVAR1;
                _CVAR0.sendMessage(_CVAR2);
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.BANPERM)) {
                    // Ban help
                    banPerm = true;
                    java.lang.String _CVAR4 = "/ban [-options] <player> <reason>";
                    org.bukkit.command.CommandSender _CVAR3 = sender;
                    java.lang.String _CVAR5 = org.bukkit.ChatColor.GOLD + _CVAR4;
                    _CVAR3.sendMessage(_CVAR5);
                    java.lang.String _CVAR7 = "Used to ban players permanently from the server.";
                    org.bukkit.command.CommandSender _CVAR6 = sender;
                    java.lang.String _CVAR8 = ((org.bukkit.ChatColor.GOLD + "    Usage: ") + org.bukkit.ChatColor.YELLOW) + _CVAR7;
                    _CVAR6.sendMessage(_CVAR8);
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.TEMPBANPERM)) {
                    // Tempban help
                    banPerm = true;
                    java.lang.String _CVAR10 = "/tempban [-options] <player> <time> <unit> <reason>";
                    org.bukkit.command.CommandSender _CVAR9 = sender;
                    java.lang.String _CVAR11 = org.bukkit.ChatColor.GOLD + _CVAR10;
                    _CVAR9.sendMessage(_CVAR11);
                    java.lang.String _CVAR13 = "Used to ban players temporarily, for a defined amount of time, from the server.";
                    org.bukkit.command.CommandSender _CVAR12 = sender;
                    java.lang.String _CVAR14 = ((org.bukkit.ChatColor.GOLD + "    Usage: ") + org.bukkit.ChatColor.YELLOW) + _CVAR13;
                    _CVAR12.sendMessage(_CVAR14);
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.KICKPERM)) {
                    // Kick help
                    banPerm = true;
                    java.lang.String _CVAR16 = "/kick [-options] <player> <reason>";
                    org.bukkit.command.CommandSender _CVAR15 = sender;
                    java.lang.String _CVAR17 = org.bukkit.ChatColor.GOLD + _CVAR16;
                    _CVAR15.sendMessage(_CVAR17);
                    java.lang.String _CVAR19 = "Used to kick players from the server.";
                    org.bukkit.command.CommandSender _CVAR18 = sender;
                    java.lang.String _CVAR20 = ((org.bukkit.ChatColor.GOLD + "    Usage: ") + org.bukkit.ChatColor.YELLOW) + _CVAR19;
                    _CVAR18.sendMessage(_CVAR20);
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.WARNPERM)) {
                    // Warn help
                    banPerm = true;
                    java.lang.String _CVAR22 = "/warn [-options] <player> <reason>";
                    org.bukkit.command.CommandSender _CVAR21 = sender;
                    java.lang.String _CVAR23 = org.bukkit.ChatColor.GOLD + _CVAR22;
                    _CVAR21.sendMessage(_CVAR23);
                    java.lang.String _CVAR25 = "Used to warn a player.";
                    org.bukkit.command.CommandSender _CVAR24 = sender;
                    java.lang.String _CVAR26 = ((org.bukkit.ChatColor.GOLD + "    Usage: ") + org.bukkit.ChatColor.YELLOW) + _CVAR25;
                    _CVAR24.sendMessage(_CVAR26);
                    java.lang.String _CVAR28 = "When warning an offline player, they will be notified when they next login.";
                    org.bukkit.command.CommandSender _CVAR27 = sender;
                    java.lang.String _CVAR29 = ((org.bukkit.ChatColor.GOLD + "    Note: ") + org.bukkit.ChatColor.YELLOW) + _CVAR28;
                    _CVAR27.sendMessage(_CVAR29);
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.UNBANPERM)) {
                    // Unban perm
                    banPerm = true;
                    java.lang.String _CVAR31 = "/unban [-options] <player>";
                    org.bukkit.command.CommandSender _CVAR30 = sender;
                    java.lang.String _CVAR32 = org.bukkit.ChatColor.GOLD + _CVAR31;
                    _CVAR30.sendMessage(_CVAR32);
                    java.lang.String _CVAR34 = "Used to unban a player.";
                    org.bukkit.command.CommandSender _CVAR33 = sender;
                    java.lang.String _CVAR35 = ((org.bukkit.ChatColor.GOLD + "    Usage: ") + org.bukkit.ChatColor.YELLOW) + _CVAR34;
                    _CVAR33.sendMessage(_CVAR35);
                    java.lang.String _CVAR37 = "Spelling must be exact to the player's name. Also, -h option does not work with this command";
                    org.bukkit.command.CommandSender _CVAR36 = sender;
                    java.lang.String _CVAR38 = ((org.bukkit.ChatColor.GOLD + "    Note: ") + org.bukkit.ChatColor.YELLOW) + _CVAR37;
                    _CVAR36.sendMessage(_CVAR38);
                }
                if (banPerm) {
                    java.lang.String _CVAR40 = "Options: ";
                    org.bukkit.command.CommandSender _CVAR39 = sender;
                    java.lang.String _CVAR41 = org.bukkit.ChatColor.GOLD + _CVAR40;
                    _CVAR39.sendMessage(_CVAR41);
                    java.lang.String _CVAR43 = "Hides the ban messages from the displaying to players.";
                    org.bukkit.command.CommandSender _CVAR42 = sender;
                    java.lang.String _CVAR44 = ((org.bukkit.ChatColor.GOLD + "-s    ") + org.bukkit.ChatColor.YELLOW) + _CVAR43;
                    _CVAR42.sendMessage(_CVAR44);
                    java.lang.String _CVAR46 = "Hides the ban from the ban list.";
                    org.bukkit.command.CommandSender _CVAR45 = sender;
                    java.lang.String _CVAR47 = ((org.bukkit.ChatColor.GOLD + "-h    ") + org.bukkit.ChatColor.YELLOW) + _CVAR46;
                    _CVAR45.sendMessage(_CVAR47);
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.CHECKBANPERM)) {
                    java.lang.String _CVAR49 = "/checkban <player>";
                    org.bukkit.command.CommandSender _CVAR48 = sender;
                    java.lang.String _CVAR50 = org.bukkit.ChatColor.GOLD + _CVAR49;
                    _CVAR48.sendMessage(_CVAR50);
                    java.lang.String _CVAR52 = "Used to determine if a player is banned or not";
                    org.bukkit.command.CommandSender _CVAR51 = sender;
                    java.lang.String _CVAR53 = ((org.bukkit.ChatColor.GOLD + "    Usage: ") + org.bukkit.ChatColor.YELLOW) + _CVAR52;
                    _CVAR51.sendMessage(_CVAR53);
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.UPDATEPERM)) {
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.SEARCHPERM)) {
                }
                if (net.serubin.serubans.SeruBans.hasPermission(((org.bukkit.entity.Player) (sender)), SeruBans.DEBUGPERM)) {
                }
                java.lang.String _CVAR55 = "for more debug options";
                org.bukkit.command.CommandSender _CVAR54 = sender;
                java.lang.String _CVAR56 = ((((org.bukkit.ChatColor.YELLOW + "Type ") + org.bukkit.ChatColor.GOLD) + "'/serubans debug' ") + org.bukkit.ChatColor.YELLOW) + _CVAR55;
                _CVAR54.sendMessage(_CVAR56);
                return true;
            }
            if (args[0].equalsIgnoreCase("debug")) {
                java.lang.String _CVAR58 = "for debug functionality.";
                org.bukkit.command.CommandSender _CVAR57 = sender;
                java.lang.String _CVAR59 = ((((org.bukkit.ChatColor.YELLOW + "Use ") + org.bukkit.ChatColor.GOLD) + "'/serubans -option' ") + org.bukkit.ChatColor.YELLOW) + _CVAR58;
                _CVAR57.sendMessage(_CVAR59);
                java.lang.String _CVAR61 = "Options:";
                org.bukkit.command.CommandSender _CVAR60 = sender;
                java.lang.String _CVAR62 = org.bukkit.ChatColor.YELLOW + _CVAR61;
                _CVAR60.sendMessage(_CVAR62);
                java.lang.String _CVAR64 = "-a    prints full hashmaps lists";
                org.bukkit.command.CommandSender _CVAR63 = sender;
                java.lang.String _CVAR65 = org.bukkit.ChatColor.YELLOW + _CVAR64;
                _CVAR63.sendMessage(_CVAR65);
                java.lang.String _CVAR67 = "-p    prints player hashmaps lists";
                org.bukkit.command.CommandSender _CVAR66 = sender;
                java.lang.String _CVAR68 = org.bukkit.ChatColor.YELLOW + _CVAR67;
                _CVAR66.sendMessage(_CVAR68);
                java.lang.String _CVAR70 = "-i     prints id  hashmaps lists";
                org.bukkit.command.CommandSender _CVAR69 = sender;
                java.lang.String _CVAR71 = org.bukkit.ChatColor.YELLOW + _CVAR70;
                _CVAR69.sendMessage(_CVAR71);
                java.lang.String _CVAR73 = "-b    prints banned player hashmaps lists";
                org.bukkit.command.CommandSender _CVAR72 = sender;
                java.lang.String _CVAR74 = org.bukkit.ChatColor.YELLOW + _CVAR73;
                _CVAR72.sendMessage(_CVAR74);
                java.lang.String _CVAR76 = "-w    prints warns hashmaps lists";
                org.bukkit.command.CommandSender _CVAR75 = sender;
                java.lang.String _CVAR77 = org.bukkit.ChatColor.YELLOW + _CVAR76;
                _CVAR75.sendMessage(_CVAR77);
                java.lang.String _CVAR79 = "-e    export bans to minecraft bans files";
                org.bukkit.command.CommandSender _CVAR78 = sender;
                java.lang.String _CVAR80 = org.bukkit.ChatColor.YELLOW + _CVAR79;
                _CVAR78.sendMessage(_CVAR80);
                return true;
            }
            if (args[0].startsWith("-")) {
                if (args[0].contains("a") && (!args[0].contains("api"))) {
                     _CVAR82 = net.serubin.serubans.util.HashMaps.getFullPlayerList();
                    org.bukkit.command.CommandSender _CVAR81 = sender;
                     _CVAR83 = "Players: " + _CVAR82;
                    _CVAR81.sendMessage(_CVAR83);
                     _CVAR85 = net.serubin.serubans.util.HashMaps.getFullBannedPlayers();
                    org.bukkit.command.CommandSender _CVAR84 = sender;
                     _CVAR86 = "Banned Players: " + _CVAR85;
                    _CVAR84.sendMessage(_CVAR86);
                     _CVAR88 = net.serubin.serubans.util.HashMaps.getFullTempBannedTime();
                    org.bukkit.command.CommandSender _CVAR87 = sender;
                     _CVAR89 = "TempBan: " + _CVAR88;
                    _CVAR87.sendMessage(_CVAR89);
                     _CVAR91 = net.serubin.serubans.util.HashMaps.getFullIds();
                    org.bukkit.command.CommandSender _CVAR90 = sender;
                     _CVAR92 = "Ids: " + _CVAR91;
                    _CVAR90.sendMessage(_CVAR92);
                    return true;
                }
                if (args[0].contains("p") && (!args[0].contains("api"))) {
                     _CVAR94 = net.serubin.serubans.util.HashMaps.getFullPlayerList();
                    org.bukkit.command.CommandSender _CVAR93 = sender;
                     _CVAR95 = "Players: " + _CVAR94;
                    _CVAR93.sendMessage(_CVAR95);
                }
                if (args[0].contains("i") && (!args[0].contains("api"))) {
                     _CVAR97 = net.serubin.serubans.util.HashMaps.getFullIds();
                    org.bukkit.command.CommandSender _CVAR96 = sender;
                     _CVAR98 = "Ids: " + _CVAR97;
                    _CVAR96.sendMessage(_CVAR98);
                }
                if (args[0].contains("b")) {
                     _CVAR100 = net.serubin.serubans.util.HashMaps.getFullBannedPlayers();
                    org.bukkit.command.CommandSender _CVAR99 = sender;
                     _CVAR101 = "Banned Players: " + _CVAR100;
                    _CVAR99.sendMessage(_CVAR101);
                }
                if (args[0].contains("t")) {
                     _CVAR103 = net.serubin.serubans.util.HashMaps.getFullTempBannedTime();
                    org.bukkit.command.CommandSender _CVAR102 = sender;
                     _CVAR104 = "TempBan: " + _CVAR103;
                    _CVAR102.sendMessage(_CVAR104);
                }
                if (args[0].contains("w")) {
                     _CVAR106 = net.serubin.serubans.util.HashMaps.getFullWarnList();
                    org.bukkit.command.CommandSender _CVAR105 = sender;
                     _CVAR107 = "Warns: " + _CVAR106;
                    _CVAR105.sendMessage(_CVAR107);
                }
                if (args[0].contains("e")) {
                    java.util.List<java.lang.String> ban = net.serubin.serubans.util.HashMaps.getBannedForFile();
                    java.util.Iterator<java.lang.String> iterator = ban.iterator();
                    try {
                        java.io.BufferedWriter banlist = new java.io.BufferedWriter(new java.io.FileWriter("banned-players.txt", true));
                        while (iterator.hasNext()) {
                            java.lang.String player = iterator.next();
                            banlist.write(player);
                            banlist.newLine();
                        } 
                        banlist.close();
                    } catch (java.io.IOException e) {
                        plugin.log.severe("File Could not be writen!");
                    }
                }
                return true;
            }
            return false;
        } else {
            java.lang.String _CVAR109 = "You do not have permission!";
            org.bukkit.command.CommandSender _CVAR108 = sender;
            java.lang.String _CVAR110 = org.bukkit.ChatColor.RED + _CVAR109;
            _CVAR108.sendMessage(_CVAR110);
            return true;
        }
    }
    return false;
}