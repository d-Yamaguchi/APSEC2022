public void run() {
    org.bukkit.entity.Player _CVAR0 = p;
    final java.lang.String name = _CVAR0.getName();
    java.lang.String _CVAR1 = name;
    org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayerExact(_CVAR1);
    if (pl != null) {
        pl.giveExp(exp);
    }
}