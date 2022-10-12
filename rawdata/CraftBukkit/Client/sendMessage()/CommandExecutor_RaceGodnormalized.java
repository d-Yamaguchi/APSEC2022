@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.lang.String[] args) {
    if (!plugin.getPermissionManager().checkPermissions(sender, PermissionNode.god)) {
        return true;
    }
    org.bukkit.entity.Player target = null;
    if (args.length == 1) {
        target = org.bukkit.Bukkit.getPlayer(args[0]);
    }
    if (args.length == 0) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            java.lang.String _CVAR1 = "Only players can use this on themselves!";
            org.bukkit.command.CommandSender _CVAR0 = sender;
            java.lang.String _CVAR2 = org.bukkit.ChatColor.RED + _CVAR1;
            _CVAR0.sendMessage(_CVAR2);
            return true;
        }
        target = ((org.bukkit.entity.Player) (sender));
    }
    if (args.length > 1) {
        java.lang.String _CVAR4 = "Wrong usage. Use: /racegod [playername]";
        org.bukkit.command.CommandSender _CVAR3 = sender;
        java.lang.String _CVAR5 = org.bukkit.ChatColor.RED + _CVAR4;
        _CVAR3.sendMessage(_CVAR5);
        return true;
    }
    if (target == null) {
        java.lang.String _CVAR7 = "Target not found.";
        org.bukkit.command.CommandSender _CVAR6 = sender;
        java.lang.String _CVAR8 = org.bukkit.ChatColor.RED + _CVAR7;
        _CVAR6.sendMessage(_CVAR8);
        return true;
    }
    if (plugin.getPlayerManager().switchGod(target.getName())) {
        java.lang.String _CVAR10 = "Success.";
        org.bukkit.command.CommandSender _CVAR9 = sender;
        java.lang.String _CVAR11 = org.bukkit.ChatColor.GREEN + _CVAR10;
        _CVAR9.sendMessage(_CVAR11);
    } else {
        java.lang.String _CVAR13 = "failed.";
        org.bukkit.command.CommandSender _CVAR12 = sender;
        java.lang.String _CVAR14 = org.bukkit.ChatColor.RED + _CVAR13;
        _CVAR12.sendMessage(_CVAR14);
    }
    return true;
}