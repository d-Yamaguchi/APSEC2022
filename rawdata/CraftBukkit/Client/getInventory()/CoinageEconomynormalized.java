@java.lang.Override
public double getBalance(java.lang.String playerName) {
    me.xhawk87.Coinage.Coinage _CVAR1 = plugin;
     _CVAR2 = _CVAR1.getServer();
    java.lang.String _CVAR3 = playerName;
    org.bukkit.entity.Player player = _CVAR2.getPlayerExact(_CVAR3);
    if (player != null) {
        org.bukkit.entity.Player _CVAR4 = player;
        me.xhawk87.Coinage.Currency _CVAR0 = currency;
        org.bukkit.inventory.PlayerInventory _CVAR5 = _CVAR4.getInventory();
        double _CVAR6 = _CVAR0.getCoinCount(_CVAR5);
        return _CVAR6;
    }
    return 0.0;
}