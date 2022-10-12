public boolean onCommand(org.bukkit.entity.Player player, org.bukkit.command.Command command, java.lang.String commandLabel, java.lang.String[] args) {
    java.lang.String[] split = args;
    if (command.getName().equalsIgnoreCase("dev")) {
        if (split.length > 0) {
            if (split[0].equalsIgnoreCase("debug") || split[0].equalsIgnoreCase("d")) {
                if (split.length == 1) {
                    java.lang.String _CVAR1 = "Dev: debug mode toggle.";
                    org.bukkit.entity.Player _CVAR0 = player;
                    java.lang.String _CVAR2 = org.bukkit.ChatColor.RED + _CVAR1;
                    _CVAR0.sendMessage(_CVAR2);
                    debugModeToggle();
                } else if (split.length >= 2) {
                    if (split[1].equalsIgnoreCase("on")) {
                        java.lang.String _CVAR4 = "Dev: debug mode on.";
                        org.bukkit.entity.Player _CVAR3 = player;
                        java.lang.String _CVAR5 = org.bukkit.ChatColor.RED + _CVAR4;
                        _CVAR3.sendMessage(_CVAR5);
                        setDebugMode(true);
                    } else if (split[1].equalsIgnoreCase("off")) {
                        java.lang.String _CVAR7 = "Dev: debug mode off.";
                        org.bukkit.entity.Player _CVAR6 = player;
                        java.lang.String _CVAR8 = org.bukkit.ChatColor.RED + _CVAR7;
                        _CVAR6.sendMessage(_CVAR8);
                        setDebugMode(false);
                    } else if ((split.length > 2) && eventAliases.containsKey(split[1].toLowerCase())) {
                        java.lang.Class<?> eventClass = eventAliases.get(split[1].toLowerCase());
                        boolean priv = false;
                        if ((split.length > 3) && split[3].equalsIgnoreCase("p")) {
                            priv = true;
                        }
                        if (split[2].equalsIgnoreCase("on")) {
                            java.lang.String _CVAR10 = (priv) ? " (private)." : ".";
                            org.bukkit.entity.Player _CVAR9 = player;
                            java.lang.String _CVAR11 = (((org.bukkit.ChatColor.RED + "Dev: event ") + split[1]) + " debug mode on") + _CVAR10;
                            _CVAR9.sendMessage(_CVAR11);
                            setDebugMode(eventClass, true, priv);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            java.lang.String _CVAR13 = (priv) ? " (private)." : ".";
                            org.bukkit.entity.Player _CVAR12 = player;
                            java.lang.String _CVAR14 = (((org.bukkit.ChatColor.RED + "Dev: event ") + split[1]) + " debug mode off") + _CVAR13;
                            _CVAR12.sendMessage(_CVAR14);
                            setDebugMode(eventClass, false, priv);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else if (split[0].equalsIgnoreCase("help")) {
                if ((split.length > 1) && eventAliases.containsKey(split[1].toLowerCase())) {
                    printHelp(player, eventAliases.get(split[1].toLowerCase()));
                } else {
                    printHelp(player);
                }
            } else if (split[0].equalsIgnoreCase("god")) {
                if (split.length == 1) {
                    java.lang.String _CVAR16 = ".";
                    org.bukkit.entity.Player _CVAR15 = player;
                    java.lang.String _CVAR17 = ((org.bukkit.ChatColor.RED + "Dev: god mode ") + (godModeToggle(player) ? "on" : "off")) + _CVAR16;
                    _CVAR15.sendMessage(_CVAR17);
                } else if (split.length == 2) {
                    if (split[1].equalsIgnoreCase("on")) {
                        if (setGodMode(player, true)) {
                            java.lang.String _CVAR19 = "Dev: god mode on.";
                            org.bukkit.entity.Player _CVAR18 = player;
                            java.lang.String _CVAR20 = org.bukkit.ChatColor.RED + _CVAR19;
                            _CVAR18.sendMessage(_CVAR20);
                        }
                    } else if (split[1].equalsIgnoreCase("off")) {
                        if (setGodMode(player, false)) {
                            java.lang.String _CVAR22 = "Dev: god mode off.";
                            org.bukkit.entity.Player _CVAR21 = player;
                            java.lang.String _CVAR23 = org.bukkit.ChatColor.RED + _CVAR22;
                            _CVAR21.sendMessage(_CVAR23);
                        }
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
    return false;
}