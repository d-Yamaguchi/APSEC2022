@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
public void onPlayerNormalChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
    if (event.isCancelled() || (event.getPlayer() == null)) {
        return;
    }
    final java.lang.String name = player.getName().toLowerCase();
    if (fr.xephi.authme.Utils.getInstance().isUnrestricted(player)) {
        return;
    }
    if (fr.xephi.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        return;
    }
    java.lang.String cmd = event.getMessage().split(" ")[0];
    org.bukkit.event.player.AsyncPlayerChatEvent _CVAR0 = event;
    org.bukkit.event.player.AsyncPlayerChatEvent _CVAR5 = _CVAR0;
    org.bukkit.event.player.AsyncPlayerChatEvent _CVAR10 = _CVAR5;
    final org.bukkit.entity.Player player = _CVAR10.getPlayer();
    if (data.isAuthAvailable(name)) {
        fr.xephi.authme.settings.Messages _CVAR2 = m;
        java.lang.String _CVAR3 = "login_msg";
        org.bukkit.entity.Player _CVAR1 = player;
         _CVAR4 = _CVAR2._(_CVAR3);
        _CVAR1.sendMessage(_CVAR4);
    } else {
        if (!fr.xephi.authme.settings.Settings.isForcedRegistrationEnabled) {
            return;
        }
        if (fr.xephi.authme.settings.Settings.emailRegistration) {
            fr.xephi.authme.settings.Messages _CVAR7 = m;
            java.lang.String _CVAR8 = "reg_email_msg";
            org.bukkit.entity.Player _CVAR6 = player;
             _CVAR9 = _CVAR7._(_CVAR8);
            _CVAR6.sendMessage(_CVAR9);
            return;
        } else {
            fr.xephi.authme.settings.Messages _CVAR12 = m;
            java.lang.String _CVAR13 = "reg_msg";
            org.bukkit.entity.Player _CVAR11 = player;
             _CVAR14 = _CVAR12._(_CVAR13);
            _CVAR11.sendMessage(_CVAR14);
            return;
        }
    }
    if ((!fr.xephi.authme.settings.Settings.isChatAllowed) && (!Settings.allowCommands.contains(cmd))) {
        event.setCancelled(true);
        return;
    }
}