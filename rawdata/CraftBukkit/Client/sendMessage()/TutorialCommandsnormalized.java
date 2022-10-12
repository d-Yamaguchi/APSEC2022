@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return false;
    }
    if (cmd.getName().equalsIgnoreCase("tutorial")) {
        if (args.length == 0) {
            if (sender.hasPermission("tutorial.use")) {
                this.plugin.startTutorial(((org.bukkit.entity.Player) (sender)));
            }
        }
        if (args.length == 1) {
            java.lang.String _CVAR1 = "You must Specify TEXT or META!";
            org.bukkit.command.CommandSender _CVAR0 = sender;
            java.lang.String _CVAR2 = org.bukkit.ChatColor.RED + _CVAR1;
            _CVAR0.sendMessage(_CVAR2);
            return true;
        }
        if (args.length == 2) {
            java.lang.String _CVAR4 = "You must supply a message!";
            org.bukkit.command.CommandSender _CVAR3 = sender;
            java.lang.String _CVAR5 = org.bukkit.ChatColor.RED + _CVAR4;
            _CVAR3.sendMessage(_CVAR5);
            return true;
        }
        if ((!"META".equalsIgnoreCase(args[1])) || (!"TEXT".equalsIgnoreCase(args[1]))) {
            java.lang.String _CVAR7 = "Must specify META or TEXT";
            org.bukkit.command.CommandSender _CVAR6 = sender;
            java.lang.String _CVAR8 = org.bukkit.ChatColor.RED + _CVAR7;
            _CVAR6.sendMessage(_CVAR8);
            return true;
        }
        if (args.length > 2) {
            if (args[0].equalsIgnoreCase("create")) {
                if (sender.hasPermission("tutorial.create")) {
                    java.lang.String message = "";
                    boolean skip = true;
                    for (java.lang.String part : args) {
                        if (skip) {
                            skip = false;
                        } else {
                            if (!"".equals(message)) {
                                message += " ";
                            }
                            message += part;
                        }
                    }
                    int viewID = 1;
                    while (this.plugin.getConfig().get("views." + viewID) != null) {
                        viewID++;
                    } 
                    org.bukkit.Location loc = ((org.bukkit.entity.Player) (sender)).getLocation();
                    this.plugin.getTutorialUtils().saveLoc(viewID, loc);
                    this.plugin.getConfig().set(("views." + viewID) + ".message", message);
                    this.plugin.getConfig().set(("views." + viewID) + ".type", args[1].toUpperCase());
                    this.plugin.saveConfig();
                    this.plugin.incrementTotalViews();
                    io.snw.tutorial.TutorialView view = new io.snw.tutorial.TutorialView(viewID, message, loc, io.snw.tutorial.enums.MessageType.META);
                    plugin.addTutorialView(viewID, view);
                    java.lang.String _CVAR10 = " was successfully saved.";
                    org.bukkit.command.CommandSender _CVAR9 = sender;
                    java.lang.String _CVAR11 = ((((org.bukkit.ChatColor.DARK_BLUE + "[Tutorial] ") + org.bukkit.ChatColor.LIGHT_PURPLE) + "View ") + viewID) + _CVAR10;
                    _CVAR9.sendMessage(_CVAR11);
                }
            } else {
                java.lang.String _CVAR13 = "Try /tutorial";
                org.bukkit.command.CommandSender _CVAR12 = sender;
                java.lang.String _CVAR14 = org.bukkit.ChatColor.RED + _CVAR13;
                _CVAR12.sendMessage(_CVAR14);
            }
        }
    }
    return true;
}