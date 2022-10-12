public static java.lang.String getPlayerName(java.lang.String searchPlayer) {
    // get the base player information
    org.bukkit.entity.Player player = org.bukkit.Bukkit.getServer().getPlayer(searchPlayer);
    if (player != null) {
        return player.getName();
    } else {
        // check to see if they are off-line
        org.bukkit.OfflinePlayer offlinePlayer = org.bukkit.Bukkit.getServer().getOfflinePlayer(searchPlayer);
        if (offlinePlayer != null) {
            int _CVAR0 = 0;
            boolean _CVAR1 = offlinePlayer.getLastPlayed() == _CVAR0;
            // if they've never logged in, then we're not using them
            if () {
                return null;
            }
            // they've logged in before, we're good
            return offlinePlayer.getName();
        }
    }
    return null;
}