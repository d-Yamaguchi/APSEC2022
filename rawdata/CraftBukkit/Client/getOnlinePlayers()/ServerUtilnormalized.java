public static org.bukkit.entity.Player findPlayer(java.lang.String name) {
    if (name == null) {
        return null;
    }
    org.bukkit.entity.Player foundPlayer = org.bukkit.Bukkit.getPlayer(name);
    if (foundPlayer != null) {
        return foundPlayer;
    }
    if (mc.alk.arena.Defaults.DEBUG_VIRTUAL) {
        foundPlayer = mc.alk.virtualPlayer.VirtualPlayers.getPlayer(name);
    }
    if (foundPlayer != null) {
        return foundPlayer;
    }
    org.bukkit.entity.Player[] online = org.bukkit.Bukkit.getOnlinePlayers();
    if (mc.alk.arena.Defaults.DEBUG_VIRTUAL) {
        online = mc.alk.virtualPlayer.VirtualPlayers.getOnlinePlayers();
    }
    for (org.bukkit.entity.Player player : online) {
        java.lang.String playerName = player.getName();
        if (playerName.equalsIgnoreCase(name)) {
            foundPlayer = player;
            break;
        }
        if (playerName.toLowerCase().indexOf(name.toLowerCase(), 0) != (-1)) {
            if (foundPlayer != null) {
                return null;
            }
            foundPlayer = player;
        }
    }
    return foundPlayer;
}