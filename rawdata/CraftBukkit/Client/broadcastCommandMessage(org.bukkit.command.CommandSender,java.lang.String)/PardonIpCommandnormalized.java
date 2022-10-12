@java.lang.Override
public boolean execute(org.bukkit.command.CommandSender sender, java.lang.String currentAlias, java.lang.String[] args) {
    if (!testPermission(sender)) {
        return true;
    }
    if (args.length != 1) {
        sender.sendMessage((org.bukkit.ChatColor.RED + "Usage: ") + usageMessage);
        return false;
    }
    org.bukkit.Bukkit.unbanIP(args[0]);
    java.lang.String _CVAR1 = args[0];
    org.bukkit.command.CommandSender _CVAR0 = sender;
    java.lang.String _CVAR2 = "Pardoning ip " + _CVAR1;
    org.bukkit.command.Command.broadcastCommandMessage(_CVAR0, _CVAR2);
    return true;
}