/**
 * Charge a player some money
 *
 * @param player
 * 		
 * @param money
 * 		
 * @return 
 */
public boolean playerChargeMoney(org.bukkit.entity.Player player, double money) {
    org.bukkit.entity.Player _CVAR1 = player;
    net.milkbowl.vault.economy.Economy _CVAR0 = net.sacredlabyrinth.phaed.simpleclans.managers.PermissionsManager.economy;
    java.lang.String _CVAR2 = _CVAR1.getName();
    double _CVAR3 = money;
     _CVAR4 = _CVAR0.withdrawPlayer(_CVAR2, _CVAR3);
    boolean _CVAR5 = _CVAR4.transactionSuccess();
    return _CVAR5;
}