/**
 * Catch commands
 */
@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
    // ignore commands if disabled
    if (game == null) {
        return true;
    }
    java.lang.String command = cmd.getName();
    // only players
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return false;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    // command without any parameters
    if (args.length < 1) {
        return false;
    } else {
        command = args[0];
        // one argument
        if (args.length == 1) {
            // check aliases
            if (command.equalsIgnoreCase("blue")) {
                command = "1";
            }// alias for team 2
             else if (command.equalsIgnoreCase("red")) {
                command = "2";
            }// alias for team 2

            // check actual commands
            if (command.equalsIgnoreCase("join")) {
                // has first player permission on empty game?
                if (game.isEmpty() && (!checkPermission(player, "initialize"))) {
                    return true;
                }
                if (!checkPermission(player, "play")) {
                    return true;
                }// check permission

                game.addPlayer(player, null);// join no-team spleef

            } else if (command.equalsIgnoreCase("announce")) {
                // announce game without actually joining
                if (!checkPermission(player, "announce")) {
                    return true;
                }// check permission

                game.announceNewGame(player);
            } else if (command.equalsIgnoreCase("1")) {
                // has first player permission on empty game?
                if (game.isEmpty() && (!checkPermission(player, "initializeteam"))) {
                    return true;
                }
                if (!checkPermission(player, "team")) {
                    return true;
                }// check permission

                game.addPlayer(player, 1);// add player to team 1

            } else if (command.equalsIgnoreCase("2")) {
                // has first player permission on empty game?
                if (game.isEmpty() && (!checkPermission(player, "initializeteam"))) {
                    return true;
                }
                if (!checkPermission(player, "team")) {
                    return true;
                }// check permission

                game.addPlayer(player, 2);// add player to team 2

            } else if (command.equalsIgnoreCase("leave")) {
                if (!checkPermission(player, "leave")) {
                    return true;
                }// check permission

                game.leavePlayer(player);// player leaves spleef

            } else if (command.equalsIgnoreCase("list")) {
                if (!checkPermission(player, "list")) {
                    return true;
                }// check permission

                game.listSpleefers(player);// print a list of

                // spleefers
            } else if (command.equalsIgnoreCase("start")) {
                if (!checkPermission(player, "start")) {
                    return true;
                }// check permission

                game.startGame(player, false);// start a game

            } else if (command.equalsIgnoreCase("startsingle")) {
                if (!checkPermission(player, "startsingle")) {
                    return true;
                }// check permission

                game.startGame(player, true);// start a game

            } else if (command.equalsIgnoreCase("stop")) {
                if (!checkPermission(player, "stop")) {
                    return true;
                }// check permission

                game.stopGame(player);// stop a game

            } else if (command.equalsIgnoreCase("delete")) {
                if (!checkPermission(player, "delete")) {
                    return true;
                }// check permission

                game.deleteGame(player);// delete a game

            } else if (command.equalsIgnoreCase("spectate")) {
                if (!checkPermission(player, "spectate")) {
                    return true;
                }// check permission

                game.spectateGame(player);// jump to spectation spawn point

            } else if (command.equalsIgnoreCase("back")) {
                if (!checkPermission(player, "back")) {
                    return true;
                }// check permission

                game.teleportPlayerBack(player);// jump back to saved point

            } else if (command.equalsIgnoreCase("reload")) {
                if (!checkPermission(player, "reload")) {
                    return true;
                }// check permission

                reloadConfiguration(player);// reload configuration

            } else {
                return false;
            }
        } else // multiple arguments
        if (command.equalsIgnoreCase("prize")) {
            if (!checkPermission(player, "prize")) {
                return true;
            }// check permission

            // get second argument
            java.lang.String second = args[1];
            if (second.equalsIgnoreCase("money")) {
                // money prize
                if (args.length != 3) {
                    return false;
                }
                try {
                    java.lang.Integer money = java.lang.Integer.parseInt(args[2]);
                    if (money == null) {
                        return false;
                    }
                    game.setMoneyPrize(player, ((int) (money)));
                } catch (java.lang.NumberFormatException e) {
                    return false;
                }
            } else {
                // item prize, possibly
                if (args.length != 2) {
                    return false;
                }
                try {
                    int prizeId = java.lang.Integer.parseInt(second);
                    game.setItemPrize(player, prizeId);
                } catch (java.lang.NumberFormatException e) {
                    return false;
                }
            }
        } else if (command.equalsIgnoreCase("admin")) {
            if (!checkPermission(player, "admin")) {
                return true;
            }// check permission

            // get second argument
            java.lang.String second = args[1];
            if (second.equalsIgnoreCase("setspawn")) {
                try {
                    conf.setProperty("spawn", getExactLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR1 = "Spleef spawn could not be set!";
                    org.bukkit.entity.Player _CVAR0 = player;
                    java.lang.String _CVAR2 = org.bukkit.ChatColor.RED + _CVAR1;
                    _CVAR0.sendMessage(_CVAR2);
                }
                if (conf.save()) {
                    java.lang.String _CVAR4 = "Spleef spawn location set.";
                    org.bukkit.entity.Player _CVAR3 = player;
                    java.lang.String _CVAR5 = org.bukkit.ChatColor.GREEN + _CVAR4;
                    _CVAR3.sendMessage(_CVAR5);
                } else {
                    java.lang.String _CVAR7 = "Spleef spawn could not be saved!";
                    org.bukkit.entity.Player _CVAR6 = player;
                    java.lang.String _CVAR8 = org.bukkit.ChatColor.RED + _CVAR7;
                    _CVAR6.sendMessage(_CVAR8);
                }
            } else if (second.equalsIgnoreCase("setbluespawn")) {
                try {
                    conf.setProperty("bluespawn", getExactLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR10 = "Spleef blue spawn could not be set!";
                    org.bukkit.entity.Player _CVAR9 = player;
                    java.lang.String _CVAR11 = org.bukkit.ChatColor.RED + _CVAR10;
                    _CVAR9.sendMessage(_CVAR11);
                }
                if (conf.save()) {
                    java.lang.String _CVAR13 = "Spleef blue spawn location set.";
                    org.bukkit.entity.Player _CVAR12 = player;
                    java.lang.String _CVAR14 = org.bukkit.ChatColor.GREEN + _CVAR13;
                    _CVAR12.sendMessage(_CVAR14);
                } else {
                    java.lang.String _CVAR16 = "Spleef blue spawn could not be saved!";
                    org.bukkit.entity.Player _CVAR15 = player;
                    java.lang.String _CVAR17 = org.bukkit.ChatColor.RED + _CVAR16;
                    _CVAR15.sendMessage(_CVAR17);
                }
            } else if (second.equalsIgnoreCase("setredspawn")) {
                try {
                    conf.setProperty("redspawn", getExactLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR19 = "Spleef red spawn could not be set!";
                    org.bukkit.entity.Player _CVAR18 = player;
                    java.lang.String _CVAR20 = org.bukkit.ChatColor.RED + _CVAR19;
                    _CVAR18.sendMessage(_CVAR20);
                }
                if (conf.save()) {
                    java.lang.String _CVAR22 = "Spleef red spawn location set.";
                    org.bukkit.entity.Player _CVAR21 = player;
                    java.lang.String _CVAR23 = org.bukkit.ChatColor.GREEN + _CVAR22;
                    _CVAR21.sendMessage(_CVAR23);
                } else {
                    java.lang.String _CVAR25 = "Spleef red spawn could not be saved!";
                    org.bukkit.entity.Player _CVAR24 = player;
                    java.lang.String _CVAR26 = org.bukkit.ChatColor.RED + _CVAR25;
                    _CVAR24.sendMessage(_CVAR26);
                }
            } else if (second.equalsIgnoreCase("a")) {
                try {
                    conf.setProperty("arenaa", getStandingOnLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR28 = "Spleef arena point could not be set!";
                    org.bukkit.entity.Player _CVAR27 = player;
                    java.lang.String _CVAR29 = org.bukkit.ChatColor.RED + _CVAR28;
                    _CVAR27.sendMessage(_CVAR29);
                }
                if (conf.save()) {
                    java.lang.String _CVAR31 = "Spleef arena point set.";
                    org.bukkit.entity.Player _CVAR30 = player;
                    java.lang.String _CVAR32 = org.bukkit.ChatColor.GREEN + _CVAR31;
                    _CVAR30.sendMessage(_CVAR32);
                } else {
                    java.lang.String _CVAR34 = "Spleef arena point could not be saved!";
                    org.bukkit.entity.Player _CVAR33 = player;
                    java.lang.String _CVAR35 = org.bukkit.ChatColor.RED + _CVAR34;
                    _CVAR33.sendMessage(_CVAR35);
                }
            } else if (second.equalsIgnoreCase("b")) {
                try {
                    conf.setProperty("arenab", getStandingOnLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR37 = "Spleef arena point could not be set!";
                    org.bukkit.entity.Player _CVAR36 = player;
                    java.lang.String _CVAR38 = org.bukkit.ChatColor.RED + _CVAR37;
                    _CVAR36.sendMessage(_CVAR38);
                }
                if (conf.save()) {
                    java.lang.String _CVAR40 = "Spleef arena point set.";
                    org.bukkit.entity.Player _CVAR39 = player;
                    java.lang.String _CVAR41 = org.bukkit.ChatColor.GREEN + _CVAR40;
                    _CVAR39.sendMessage(_CVAR41);
                } else {
                    java.lang.String _CVAR43 = "Spleef arena point could not be saved!";
                    org.bukkit.entity.Player _CVAR42 = player;
                    java.lang.String _CVAR44 = org.bukkit.ChatColor.RED + _CVAR43;
                    _CVAR42.sendMessage(_CVAR44);
                }
            } else if (second.equalsIgnoreCase("spectatorspawn")) {
                try {
                    conf.setProperty("spectatorspawn", getExactLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR46 = "Spectator spawn could not be set!";
                    org.bukkit.entity.Player _CVAR45 = player;
                    java.lang.String _CVAR47 = org.bukkit.ChatColor.RED + _CVAR46;
                    _CVAR45.sendMessage(_CVAR47);
                }
                if (conf.save()) {
                    java.lang.String _CVAR49 = "Spectator spawn location set.";
                    org.bukkit.entity.Player _CVAR48 = player;
                    java.lang.String _CVAR50 = org.bukkit.ChatColor.GREEN + _CVAR49;
                    _CVAR48.sendMessage(_CVAR50);
                } else {
                    java.lang.String _CVAR52 = "Spectator spawn could not be saved!";
                    org.bukkit.entity.Player _CVAR51 = player;
                    java.lang.String _CVAR53 = org.bukkit.ChatColor.RED + _CVAR52;
                    _CVAR51.sendMessage(_CVAR53);
                }
            } else if (second.equalsIgnoreCase("loungespawn")) {
                try {
                    conf.setProperty("loungespawn", getExactLocation(player.getLocation()));
                } catch (java.lang.Exception e) {
                    java.lang.String _CVAR55 = "Lounge spawn could not be set!";
                    org.bukkit.entity.Player _CVAR54 = player;
                    java.lang.String _CVAR56 = org.bukkit.ChatColor.RED + _CVAR55;
                    _CVAR54.sendMessage(_CVAR56);
                }
                if (conf.save()) {
                    java.lang.String _CVAR58 = "Lounge spawn location set.";
                    org.bukkit.entity.Player _CVAR57 = player;
                    java.lang.String _CVAR59 = org.bukkit.ChatColor.GREEN + _CVAR58;
                    _CVAR57.sendMessage(_CVAR59);
                } else {
                    java.lang.String _CVAR61 = "Lounge spawn could not be saved!";
                    org.bukkit.entity.Player _CVAR60 = player;
                    java.lang.String _CVAR62 = org.bukkit.ChatColor.RED + _CVAR61;
                    _CVAR60.sendMessage(_CVAR62);
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    return true;
}