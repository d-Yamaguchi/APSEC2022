public void addScore(int add, org.bukkit.entity.Player player) {
    player.sendMessage(((org.bukkit.ChatColor.GREEN + "+") + add) + " Points");
    org.bukkit.entity.Player _CVAR1 = player;
    org.bukkit.configuration.file.FileConfiguration _CVAR0 = getScoreConfig();
    java.lang.String _CVAR2 = _CVAR1.getName();
    boolean _CVAR3 = _CVAR0.contains(_CVAR2);
    if () {
        org.bukkit.entity.Player _CVAR4 = player;
        java.lang.String _CVAR5 = _CVAR4.getName();
        int _CVAR6 = getScore(_CVAR5);
        int _CVAR7 = _CVAR6 + add;
        add = _CVAR7;
    }
    setScore(add, player);
}