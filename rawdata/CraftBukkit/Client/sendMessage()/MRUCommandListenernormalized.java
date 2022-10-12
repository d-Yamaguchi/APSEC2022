@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender cs, org.bukkit.command.Command cmnd, java.lang.String string, java.lang.String[] args) {
    // Do not use Console !
    // if(!(cs instanceof Player) || args.length == 0) return false;
    if (!(cs instanceof org.bukkit.entity.Player)) {
        return false;
    } else if (args.length == 0) {
        return Help(cs);
    }
    // Show ? - Checks the players current and next promotional levels and displays them only
    // Rank ? - Checks and attempts to promote the player to the next level in the promotion ladder
    // args[0] = show or rank
    if (args[0].equalsIgnoreCase("rank") || args[0].equalsIgnoreCase("show")) {
        return Check(cs, args[0]);
    }
    // Change to Rank Up on Ability
    if (args[0].equalsIgnoreCase("hab")) {
        if (args.length > 1) {
            return RankOnHability(cs, args[1].toString());
        } else {
            return ListHability(cs);
        }
    }
    if (args[0].equalsIgnoreCase("male")) {
        setGender(cs, "Male");
    }
    if (args[0].equalsIgnoreCase("female")) {
        setGender(cs, "Female");
    }
    // Toggle Displaying The Next Promotional Information in Rank Info and/or Promotion Messaging
    if (args[0].equalsIgnoreCase("pinfo")) {
        org.bukkit.entity.Player _player = ((org.bukkit.entity.Player) (cs));
        if (!plugin.permission.has(_player.getWorld(), _player.getName(), "mru.reload")) {
            return false;
        }
        plugin.displayNextPromo = !plugin.displayNextPromo;
        plugin.getConfig().set("Config.DisplayNextPromo", plugin.displayNextPromo);
        plugin.saveConfig();
         _CVAR1 = (plugin.displayNextPromo) ? "ON" : "OFF";
        org.bukkit.command.CommandSender _CVAR0 = cs;
         _CVAR2 = ((((("" + org.bukkit.ChatColor.YELLOW) + org.bukkit.ChatColor.BOLD) + "Promotional Information Toggled ") + org.bukkit.ChatColor.WHITE) + org.bukkit.ChatColor.BOLD) + _CVAR1;
        _CVAR0.sendMessage(_CVAR2);
    }
    // Its Reload ?
    if (args[0].equalsIgnoreCase("reload")) {
        org.bukkit.entity.Player _player = ((org.bukkit.entity.Player) (cs));
        if (!plugin.permission.has(_player.getWorld(), _player.getName(), "mru.reload")) {
            return false;
        }
        plugin.onReload();
        org.bukkit.command.CommandSender _CVAR3 = cs;
        java.lang.String _CVAR4 = "Reload complete...";
        _CVAR3.sendMessage(_CVAR4);
        return false;
    }
    if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
        return Help(cs);
    }
    return false;
}