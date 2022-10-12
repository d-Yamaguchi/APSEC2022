public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
    if (cmd.getName().equalsIgnoreCase("papermail")) {
        if ((!(sender instanceof org.bukkit.entity.Player)) && (args.length > 0)) {
             _CVAR1 = PaperMail.instance.getDescription();
             _CVAR2 = _CVAR1.getVersion();
            org.bukkit.command.CommandSender _CVAR0 = sender;
             _CVAR3 = "Current Version of ItemMail is " + _CVAR2;
            _CVAR0.sendMessage(_CVAR3);
            return true;
        } else {
            if (args.length == 0) {
                 _CVAR5 = PaperMail.instance.getDescription();
                 _CVAR6 = _CVAR5.getVersion();
                org.bukkit.command.CommandSender _CVAR4 = sender;
                 _CVAR7 = "Current Version of ItemMail is " + _CVAR6;
                _CVAR4.sendMessage(_CVAR7);
                return true;
            }
            if ((!args[0].toLowerCase().equals("sendtext")) && (!args[0].toLowerCase().equals("createbox"))) {
                org.bukkit.ChatColor _CVAR9 = org.bukkit.ChatColor.RESET;
                org.bukkit.entity.Player _CVAR8 = player;
                java.lang.String _CVAR10 = (((org.bukkit.ChatColor.DARK_RED + "invalid argument \"") + args[0]) + "\"") + _CVAR9;
                _CVAR8.sendMessage(_CVAR10);
                return true;
            }
            if ((Settings.EnableTextMail && args[0].toLowerCase().equals("sendtext")) && player.hasPermission(Permissions.SEND_TEXT_PERM)) {
                if (args.length < 3) {
                    if (args.length < 2) {
                        org.bukkit.ChatColor _CVAR12 = org.bukkit.ChatColor.RESET;
                        org.bukkit.entity.Player _CVAR11 = player;
                        java.lang.String _CVAR13 = (org.bukkit.ChatColor.DARK_RED + "Missing arguments for textmail!") + _CVAR12;
                        _CVAR11.sendMessage(_CVAR13);
                        return true;
                    }
                    org.bukkit.ChatColor _CVAR15 = org.bukkit.ChatColor.RESET;
                    org.bukkit.entity.Player _CVAR14 = player;
                    java.lang.String _CVAR16 = (org.bukkit.ChatColor.DARK_RED + "Missing text of textmail!") + _CVAR15;
                    _CVAR14.sendMessage(_CVAR16);
                    return true;
                }
                // if player isn't cost exempt and costs is enabled and price is set, try to send textmail
                if (((Settings.EnableMailCosts == true) && (Settings.Price != 0)) && (!player.hasPermission(Permissions.COSTS_EXEMPT))) {
                    // check if player has the correct amount of currency
                    if (com.github.derwisch.paperMail.PaperMailEconomy.hasMoney(Settings.Price, player) == true) {
                        sendText(player, args);
                        com.github.derwisch.paperMail.PaperMailEconomy.takeMoney(Cost, player);
                        return true;
                        // if player doesn't have enough money don't send textmail
                    } else {
                        java.lang.String _CVAR18 = "Not Enough Money to send your mail!";
                        org.bukkit.entity.Player _CVAR17 = player;
                        java.lang.String _CVAR19 = org.bukkit.ChatColor.RED + _CVAR18;
                        _CVAR17.sendMessage(_CVAR19);
                        return true;
                    }
                }
                // if player is cost exempt or price is zero or mailcosts is off send textmail
                if (((Settings.EnableMailCosts == false) || (player.hasPermission(Permissions.COSTS_EXEMPT) && (Settings.EnableMailCosts == true))) || (((Settings.EnableMailCosts == true) && (Settings.Price == 0)) && (!player.hasPermission(Permissions.COSTS_EXEMPT)))) {
                    sendText(player, args);
                    return true;
                }
            }
            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
            // create inbox chest
            if (args[0].toLowerCase().equals("createbox")) {
                com.github.derwisch.paperMail.Inbox inbox = null;
                if ((args.length == 1) && (player.hasPermission(Permissions.CREATE_CHEST_SELF_PERM) || player.hasPermission(Permissions.CREATE_CHEST_ALL_PERM))) {
                    try {
                        inbox = com.github.derwisch.paperMail.Inbox.GetInbox(player.getName());
                    } catch (java.io.IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (org.bukkit.configuration.InvalidConfigurationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if ((args.length == 2) && player.hasPermission(Permissions.CREATE_CHEST_ALL_PERM)) {
                    try {
                        inbox = com.github.derwisch.paperMail.Inbox.GetInbox(args[1]);
                    } catch (java.io.IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (org.bukkit.configuration.InvalidConfigurationException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    org.bukkit.ChatColor _CVAR21 = org.bukkit.ChatColor.RESET;
                    org.bukkit.entity.Player _CVAR20 = player;
                    java.lang.String _CVAR22 = (org.bukkit.ChatColor.DARK_RED + "Too many arguments!") + _CVAR21;
                    _CVAR20.sendMessage(_CVAR22);
                    return true;
                }
                org.bukkit.block.Block block = player.getTargetBlock(null, 10);
                if ((block != null) && (block.getType() == org.bukkit.Material.CHEST)) {
                    org.bukkit.block.Chest chest = ((org.bukkit.block.Chest) (block.getState()));
                    inbox.SetChest(chest);
                    org.bukkit.ChatColor _CVAR24 = org.bukkit.ChatColor.RESET;
                    org.bukkit.entity.Player _CVAR23 = player;
                    java.lang.String _CVAR25 = (org.bukkit.ChatColor.DARK_GREEN + "Inbox created!") + _CVAR24;
                    _CVAR23.sendMessage(_CVAR25);
                    return true;
                } else {
                    org.bukkit.ChatColor _CVAR27 = org.bukkit.ChatColor.RESET;
                    org.bukkit.entity.Player _CVAR26 = player;
                    java.lang.String _CVAR28 = (org.bukkit.ChatColor.DARK_RED + "You must focus a chest") + _CVAR27;
                    _CVAR26.sendMessage(_CVAR28);
                    return true;
                }
            }
        }
        return true;
    }
    return false;
}