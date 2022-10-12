public void sendMotd(org.bukkit.command.CommandSender sender) {
    java.lang.String _CVAR4 = "MOTD: {0}";
    org.bukkit.command.CommandSender _CVAR6 = sender;
    me.parisfuja.plugins.motdplus.LanguageWrapper _CVAR1 = t;
    org.bukkit.command.CommandSender _CVAR2 = sender;
    java.lang.String _CVAR3 = "command.motd";
    java.lang.String _CVAR5 = org.bukkit.ChatColor.GREEN + _CVAR4;
    java.lang.String _CVAR7 = getMessage(_CVAR6);
    org.bukkit.command.CommandSender _CVAR0 = sender;
     _CVAR8 = _CVAR1.get(_CVAR2, _CVAR3, _CVAR5, _CVAR7);
    _CVAR0.sendMessage(_CVAR8);
}