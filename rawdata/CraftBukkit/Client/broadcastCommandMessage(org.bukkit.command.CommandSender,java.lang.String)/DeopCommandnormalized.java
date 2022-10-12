@java.lang.Override
public boolean execute(org.bukkit.command.CommandSender sender, java.lang.String currentAlias, java.lang.String[] args) {
    if (!testPermission(sender)) {
        return true;
    }
    if (args.length != 1) {
        sender.sendMessage((org.bukkit.ChatColor.RED + "Usage: ") + usageMessage);
        return false;
    }
    java.lang.String _CVAR1 = args[0];
    org.bukkit.command.CommandSender _CVAR0 = sender;
    java.lang.String _CVAR2 = "De-opping " + _CVAR1;
    org.bukkit.command.Command.broadcastCommandMessage(_CVAR0, _CVAR2);
    org.bukkit.OfflinePlayer player = org.bukkit.Bukkit.getOfflinePlayer(args[0]);
    player.setOp(false);
    if (player instanceof org.bukkit.entity.Player) {
        ((org.bukkit.entity.Player) (player)).sendMessage(org.bukkit.ChatColor.YELLOW + "You are no longer op!");
    }
    return true;
}