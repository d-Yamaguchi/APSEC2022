public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String alias, java.lang.String[] args) {
    // invalid args
    if (args.length > 2) {
        return false;
    }
    // check if player
    java.lang.Boolean isPlayer = true;
    if (!(sender instanceof org.bukkit.entity.Player)) {
        isPlayer = false;
    }
    if (isPlayer) {
        player = ((org.bukkit.entity.Player) (sender));
    }
    // <command>
    if (args.length == 0) {
        // check if player
        if (!isPlayer) {
            return false;
        }
        // check permission
        if (!co.viocode.nexus.Nexus.checkPermission("nexus.level", player)) {
            return true;
        }
        // format level
        int expNext = ((int) (7 + ((int) (player.getLevel() * 3.5))));
        org.bukkit.entity.Player _CVAR0 = player;
        int _CVAR1 = _CVAR0.getTotalExperience();
        int actualNext = (expNext - ((int) (player.getExp() * expNext))) + _CVAR1;
        org.bukkit.entity.Player _CVAR3 = player;
        int _CVAR4 = _CVAR3.getTotalExperience();
        java.lang.String _CVAR5 = (((((((org.bukkit.ChatColor.GREEN + "Level: ") + org.bukkit.ChatColor.WHITE) + player.getLevel()) + "  ") + org.bukkit.ChatColor.GREEN) + "Exp: ") + org.bukkit.ChatColor.WHITE) + _CVAR4;
        java.lang.String _CVAR6 = _CVAR5 + "/";
        org.bukkit.entity.Player _CVAR2 = player;
        java.lang.String _CVAR7 = _CVAR6 + actualNext;
        // display level
        _CVAR2.sendMessage(_CVAR7);
        return true;
    }
    // init vars
    org.bukkit.entity.Player player = null;
    // <command> (level)
    if (args.length == 1) {
        // check if player
        if (!isPlayer) {
            return false;
        }
        // check permission
        if (!co.viocode.nexus.Nexus.checkPermission("nexus.level.set", player)) {
            return true;
        }
        // init vars
        int level;
        try {
            level = java.lang.Integer.parseInt(args[0]);
        } catch (java.lang.Exception e) {
            return false;
        }
        // check max level
        if (level > 50) {
            player.sendMessage(org.bukkit.ChatColor.RED + "Maximum level is 50.");
            return true;
        }
        // set player level
        player.setLevel(0);
        player.setExp(0);
        player.setTotalExperience(0);
        for (int i = 0; i < level; i++) {
            double _CVAR9 = 3.5;
            double _CVAR10 = ((int) (player.getLevel() * _CVAR9));
            int _CVAR11 = ((int) (7 + _CVAR10));
            org.bukkit.entity.Player _CVAR8 = player;
            int _CVAR12 = player.getTotalExperience() + _CVAR11;
            _CVAR8.setTotalExperience(_CVAR12);
            player.setLevel(player.getLevel() + 1);
        }
        return true;
    }
    // <command> (player) (level)
    if (args.length == 2) {
        // check permission
        if (isPlayer) {
            if (!co.viocode.nexus.Nexus.checkPermission("nexus.level.set", player)) {
                return true;
            }
        }
        int level = java.lang.Integer.parseInt(args[1]);
        // check if player is offline
        if (target == null) {
            sender.sendMessage(org.bukkit.ChatColor.RED + "Player is not online.");
            return true;
        }
        // check max level
        if (level > 50) {
            player.sendMessage(org.bukkit.ChatColor.RED + "Maximum level is 50.");
            return true;
        }
        // set player level
        target.setLevel(0);
        target.setExp(0);
        target.setTotalExperience(0);
        java.lang.String _CVAR13 = args[0];
        java.lang.String _CVAR14 = _CVAR13.toLowerCase();
        // init vars
        org.bukkit.entity.Player target = co.viocode.nexus.Nexus.findOnlinePlayer(_CVAR14);
        for (int i = 0; i < level; i++) {
            double _CVAR16 = 3.5;
            double _CVAR17 = ((int) (target.getLevel() * _CVAR16));
            int _CVAR18 = ((int) (7 + _CVAR17));
            org.bukkit.entity.Player _CVAR15 = target;
            int _CVAR19 = target.getTotalExperience() + _CVAR18;
            _CVAR15.setTotalExperience(_CVAR19);
            target.setLevel(target.getLevel() + 1);
        }
        // display
        target.sendMessage((((org.bukkit.ChatColor.GREEN + sender.getName()) + " set your level to ") + level) + ".");
        sender.sendMessage((((org.bukkit.ChatColor.GREEN + target.getName()) + " set to level ") + level) + ".");
        return true;
    }
    // end of command
    return false;
}