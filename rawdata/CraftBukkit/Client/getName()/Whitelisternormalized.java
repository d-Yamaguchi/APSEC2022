@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
public void onAsyncPlayerPreLoginEvent(final org.bukkit.event.player.AsyncPlayerPreLoginEvent event) {
    java.lang.String _CVAR1 = " is trying to join...";
     _CVAR0 = com.sk89q.commandbook.CommandBook.logger();
    java.lang.String _CVAR2 = event.getName() + _CVAR1;
    _CVAR0.info(_CVAR2);
    if (maintenanceMode) {
         _CVAR3 = com.sk89q.commandbook.CommandBook.server();
         _CVAR4 = _CVAR3.getScheduler();
         _CVAR5 = com.sk89q.commandbook.CommandBook.inst();
        java.util.concurrent.Callable<java.lang.Boolean> _CVAR6 = new java.util.concurrent.Callable<java.lang.Boolean>() {
            @java.lang.Override
            public java.lang.Boolean call() {
                return com.sk89q.commandbook.CommandBook.server().getOfflinePlayer(event.getName()).isOp();
            }
        };
        java.util.concurrent.Future<java.lang.Boolean> isOp = _CVAR4.callSyncMethod(_CVAR5, _CVAR6);
        try {
            if (!isOp.get()) {
                event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, config.maintenanceMessage);
                com.sk89q.commandbook.CommandBook.logger().info("Disallow (maintenance mode)");
            }
        } catch (java.lang.Exception e) {
            org.bukkit.event.player.AsyncPlayerPreLoginEvent _CVAR8 = event;
            java.lang.String _CVAR9 = _CVAR8.getName();
            java.lang.String _CVAR10 = "Error whole checking op-status for " + _CVAR9;
            java.lang.String _CVAR11 = _CVAR10 + ", ";
             _CVAR7 = com.sk89q.commandbook.CommandBook.logger();
            java.lang.String _CVAR12 = _CVAR11 + e;
            _CVAR7.warning(_CVAR12);
            event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_OTHER, config.maintenanceMessage);
            com.sk89q.commandbook.CommandBook.logger().info("Disallow (maintenance mode)");
        }
    } else {
         _CVAR13 = !whitelistManager.isOnWhitelist(event.getName());
        if () {
            event.disallow(org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, config.notOnWhitelistMessage);
            com.sk89q.commandbook.CommandBook.logger().info("Disallow (not on whitelist)");
        }
    }
}