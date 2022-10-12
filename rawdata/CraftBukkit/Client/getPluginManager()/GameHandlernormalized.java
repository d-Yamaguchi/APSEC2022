/**
 * Removes a player from the game.
 *
 * @param player
 * 		player to remove
 */
public void removePlayer(org.bukkit.entity.Player player) {
    if (players.contains(player.getName())) {
        PlayerStats stats = getPlayerStats(player);
        if (plugin.getConfiguration().getBoolean(ConfigEnum.SEPERATE_INVENTORY.toString())) {
            clearInventory(player.getInventory());
            org.bukkit.inventory.PlayerInventory pi = player.getInventory();
            org.bukkit.inventory.ItemStack[] contents = stats.getInventoryContents();
            if (contents != null) {
                pi.setContents(contents);
            }
            org.bukkit.inventory.ItemStack[] armorContents = stats.getInventoryArmor();
            if (armorContents != null) {
                pi.setArmorContents(armorContents);
            }
        }
        org.bukkit.plugin.PluginManager _CVAR0 = org.bukkit.Bukkit.getPluginManager();
        java.lang.String _CVAR1 = "Vault";
        org.bukkit.plugin.Plugin _CVAR2 = _CVAR0.getPlugin(_CVAR1);
        boolean _CVAR3 = _CVAR2 != null;
         _CVAR4 = plugin.getConfiguration().getBoolean(ConfigEnum.SEPERATE_MONEY.toString()) && _CVAR3;
        if () {
            plugin.getEconomy().depositPlayer(player.getName(), stats.getOldMoney());
        }
        player.teleport(getPlayersLeaveLocation(player));
        player.setGameMode(stats.getOldGameMode());
        player.setLevel(stats.getOldLevel());
        players.remove(player.getName());
        playerStats.remove(player.getName());
    }
}