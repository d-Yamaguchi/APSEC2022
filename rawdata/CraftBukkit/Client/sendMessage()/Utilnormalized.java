public void listRanks(org.bukkit.entity.Player sender) {
    java.util.List<java.lang.String> ranks = plugin.getConfig().getStringList("ranklist.ranks");
    java.util.List<java.lang.String> description = plugin.getConfig().getStringList("ranklist.description");
    java.lang.String _CVAR1 = "=====================================================";
    org.bukkit.entity.Player _CVAR0 = sender;
    java.lang.String _CVAR2 = org.bukkit.ChatColor.LIGHT_PURPLE + _CVAR1;
    _CVAR0.sendMessage(_CVAR2);
    com.teozcommunity.teozfrank.ranklist.main.RankList _CVAR4 = plugin;
     _CVAR5 = _CVAR4.getConfig();
    java.lang.String _CVAR6 = "ranklist.servername";
     _CVAR7 = _CVAR5.getString(_CVAR6);
    org.bukkit.entity.Player _CVAR3 = sender;
     _CVAR8 = com.teozcommunity.teozfrank.ranklist.util.ChatColorHelper.replaceColorCodes(_CVAR7);
    _CVAR3.sendMessage(_CVAR8);
    java.lang.String _CVAR10 = "=====================================================";
    org.bukkit.entity.Player _CVAR9 = sender;
    java.lang.String _CVAR11 = org.bukkit.ChatColor.LIGHT_PURPLE + _CVAR10;
    _CVAR9.sendMessage(_CVAR11);
    for (java.lang.String desc : description) {
        java.lang.String _CVAR13 = desc;
        org.bukkit.entity.Player _CVAR12 = sender;
         _CVAR14 = com.teozcommunity.teozfrank.ranklist.util.ChatColorHelper.replaceColorCodes(_CVAR13);
        _CVAR12.sendMessage(_CVAR14);
    }
    java.lang.String _CVAR16 = "=====================================================";
    org.bukkit.entity.Player _CVAR15 = sender;
    java.lang.String _CVAR17 = org.bukkit.ChatColor.LIGHT_PURPLE + _CVAR16;
    _CVAR15.sendMessage(_CVAR17);
    for (java.lang.String rank : ranks) {
        java.lang.String _CVAR19 = rank;
         _CVAR20 = com.teozcommunity.teozfrank.ranklist.util.ChatColorHelper.replaceColorCodes(_CVAR19);
        org.bukkit.entity.Player _CVAR18 = sender;
         _CVAR21 = (org.bukkit.ChatColor.GOLD + "- ") + _CVAR20;
        _CVAR18.sendMessage(_CVAR21);
    }
    java.lang.String _CVAR23 = "=====================================================";
    org.bukkit.entity.Player _CVAR22 = sender;
    java.lang.String _CVAR24 = org.bukkit.ChatColor.LIGHT_PURPLE + _CVAR23;
    _CVAR22.sendMessage(_CVAR24);
    java.lang.String _CVAR26 = " for 1.7.2 available on Bukkit Dev   ";
    org.bukkit.entity.Player _CVAR25 = sender;
     _CVAR27 = ((org.bukkit.ChatColor.GOLD + "           RankList ") + plugin.version) + _CVAR26;
    _CVAR25.sendMessage(_CVAR27);
    java.lang.String _CVAR29 = "           http://dev.bukkit.org/server-mods/rank-list/    ";
    org.bukkit.entity.Player _CVAR28 = sender;
    java.lang.String _CVAR30 = org.bukkit.ChatColor.GOLD + _CVAR29;
    _CVAR28.sendMessage(_CVAR30);
    java.lang.String _CVAR32 = "=====================================================";
    org.bukkit.entity.Player _CVAR31 = sender;
    java.lang.String _CVAR33 = org.bukkit.ChatColor.LIGHT_PURPLE + _CVAR32;
    _CVAR31.sendMessage(_CVAR33);
}