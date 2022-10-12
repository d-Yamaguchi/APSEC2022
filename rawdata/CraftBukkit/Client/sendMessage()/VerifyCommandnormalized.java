@java.lang.Override
public void execute(org.bukkit.entity.Player player, java.lang.String label, java.lang.String[] args) {
    net.sacredlabyrinth.phaed.simpleclans.ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);
    net.sacredlabyrinth.phaed.simpleclans.Clan clan = (cp == null) ? null : cp.getClan();
    boolean isNonVerified = (clan != null) && (!clan.isVerified());
    boolean isBuy = plugin.getSettingsManager().isePurchaseVerification();
    if (plugin.getSettingsManager().isRequireVerification()) {
        if (isNonVerified) {
            if (isBuy) {
                if (!plugin.getClanManager().purchaseVerification(player)) {
                    net.sacredlabyrinth.phaed.simpleclans.SimpleClans _CVAR1 = plugin;
                    java.lang.String _CVAR2 = "not.sufficient.money";
                     _CVAR3 = _CVAR1.getLang(_CVAR2);
                    org.bukkit.entity.Player _CVAR0 = player;
                     _CVAR4 = org.bukkit.ChatColor.AQUA + _CVAR3;
                    _CVAR0.sendMessage(_CVAR4);
                    return;
                }
            }
            clan.verifyClan();
            clan.addBb(player.getName(), org.bukkit.ChatColor.AQUA + java.text.MessageFormat.format(plugin.getLang("clan.0.has.been.verified"), clan.getName()));
            net.sacredlabyrinth.phaed.simpleclans.ChatBlock.sendMessage(player, org.bukkit.ChatColor.AQUA + plugin.getLang("the.clan.has.been.verified"));
        } else {
            net.sacredlabyrinth.phaed.simpleclans.SimpleClans _CVAR6 = plugin;
            java.lang.String _CVAR7 = "your.clan.is.already.verified";
             _CVAR8 = _CVAR6.getLang(_CVAR7);
            org.bukkit.entity.Player _CVAR5 = player;
             _CVAR9 = org.bukkit.ChatColor.GRAY + _CVAR8;
            _CVAR5.sendMessage(_CVAR9);
        }
    } else {
        net.sacredlabyrinth.phaed.simpleclans.SimpleClans _CVAR11 = plugin;
        java.lang.String _CVAR12 = "you.dont.need.to.verify";
         _CVAR13 = _CVAR11.getLang(_CVAR12);
        org.bukkit.entity.Player _CVAR10 = player;
         _CVAR14 = org.bukkit.ChatColor.GRAY + _CVAR13;
        _CVAR10.sendMessage(_CVAR14);
    }
}