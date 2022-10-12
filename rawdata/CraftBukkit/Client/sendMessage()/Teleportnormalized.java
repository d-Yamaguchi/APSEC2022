@java.lang.Override
public boolean execute(org.bukkit.command.CommandSender sender, java.lang.String[] split) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        org.bukkit.command.CommandSender _CVAR0 = sender;
        java.lang.String _CVAR1 = "You must be a player to teleport!";
        _CVAR0.sendMessage(_CVAR1);
    }
    org.monstercraft.party.plugin.wrappers.Party p;
    if (!org.monstercraft.party.plugin.PartyAPI.inParty(player)) {
        java.lang.String _CVAR3 = "You must be in a party to teleport to another player!";
        org.bukkit.entity.Player _CVAR2 = player;
        java.lang.String _CVAR4 = org.bukkit.ChatColor.RED + _CVAR3;
        _CVAR2.sendMessage(_CVAR4);
        return true;
    }
    if ((p = org.monstercraft.party.plugin.PartyAPI.getParty(player)) != null) {
        java.lang.String _CVAR6 = split[2];
        org.bukkit.entity.Player to = org.bukkit.Bukkit.getPlayer(_CVAR6);
        if (to != null) {
            if (p.containsMember(to)) {
                player.teleport(to);
                org.bukkit.entity.Player _CVAR7 = to;
                java.lang.String _CVAR8 = _CVAR7.getDisplayName();
                org.bukkit.entity.Player _CVAR5 = player;
                java.lang.String _CVAR9 = (org.bukkit.ChatColor.GREEN + "You have teleported to: ") + _CVAR8;
                _CVAR5.sendMessage(_CVAR9);
                return true;
            }
        }
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    java.lang.String _CVAR11 = "Player not found!";
    org.bukkit.entity.Player _CVAR10 = player;
    java.lang.String _CVAR12 = org.bukkit.ChatColor.RED + _CVAR11;
    _CVAR10.sendMessage(_CVAR12);
    return true;
}