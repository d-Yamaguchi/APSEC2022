private void modifyPoints(org.bukkit.command.CommandSender sender, java.lang.String[] args) {
    int change = java.lang.Integer.parseInt(args[1]);
    java.lang.String message = us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_PLAYER_POINTS_ALTERED);
    java.lang.String playerName = args[0];
    java.lang.String _CVAR0 = playerName;
    org.bukkit.OfflinePlayer player = org.bukkit.Bukkit.getOfflinePlayer(_CVAR0);
    message = message.replace("<Player>", player.getName());
    message = message.replace("<Change>", java.lang.Integer.toString(change));
    us.corenetwork.challenges.Util.Message(message, sender);
    java.lang.String reason = null;
    if (args.length > 2) {
        reason = "";
        for (int i = 2; i < args.length; i++) {
            reason += args[i] + " ";
        }
        reason = reason.trim();
    }
    us.corenetwork.challenges.PlayerPoints.addPoints(player.getUniqueId(), change, reason);
}