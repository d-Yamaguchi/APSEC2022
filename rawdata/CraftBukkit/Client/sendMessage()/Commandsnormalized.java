public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
    if ((args.length < 1) && (sender instanceof org.bukkit.entity.Player)) {
        org.bukkit.entity.Player p = ((org.bukkit.entity.Player) (sender));
        plugin.util.listRanks(p);
        return true;
    }
    if ((args.length == 1) && args[0].equals("reload")) {
        if (sender.hasPermission("ranklist.reload")) {
            plugin.reloadConfig();
            java.lang.String _CVAR1 = "[Ranklist] Config Reloaded from Disk.";
            org.bukkit.command.CommandSender _CVAR0 = sender;
            java.lang.String _CVAR2 = org.bukkit.ChatColor.GREEN + _CVAR1;
            _CVAR0.sendMessage(_CVAR2);
            return true;
        } else {
            java.lang.String _CVAR4 = "You do not have permission to do this!";
            org.bukkit.command.CommandSender _CVAR3 = sender;
            java.lang.String _CVAR5 = ((org.bukkit.ChatColor.GREEN + "[Ranklist] ") + org.bukkit.ChatColor.RED) + _CVAR4;
            _CVAR3.sendMessage(_CVAR5);
            return true;
        }
    } else {
        java.lang.String _CVAR7 = "Unknown Command!";
        org.bukkit.command.CommandSender _CVAR6 = sender;
        java.lang.String _CVAR8 = ((org.bukkit.ChatColor.GREEN + "[Ranklist] ") + org.bukkit.ChatColor.RED) + _CVAR7;
        _CVAR6.sendMessage(_CVAR8);
        return true;
    }
}