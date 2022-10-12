@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return true;
    }
    if (!plugin.authmePermissible(sender, "authme." + label.toLowerCase())) {
        m._(sender, "no_perm");
        return true;
    }
    if (!fr.xephi.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        m._(player, "not_logged_in");
        return true;
    }
    fr.xephi.authme.cache.auth.PlayerAuth auth = fr.xephi.authme.cache.auth.PlayerCache.getInstance().getAuth(name);
    auth.setIp("198.18.0.1");
    database.updateSession(auth);
    auth.setQuitLocX(player.getLocation().getX());
    auth.setQuitLocY(player.getLocation().getY());
    auth.setQuitLocZ(player.getLocation().getZ());
    auth.setWorld(player.getWorld().getName());
    database.updateQuitLoc(auth);
    fr.xephi.authme.cache.auth.PlayerCache.getInstance().removePlayer(name);
    if (fr.xephi.authme.settings.Settings.isTeleportToSpawnEnabled) {
        org.bukkit.Location spawnLoc = player.getWorld().getSpawnLocation();
        if (plugin.essentialsSpawn != null) {
            spawnLoc = plugin.essentialsSpawn;
        }
        if (fr.xephi.authme.settings.Spawn.getInstance().getLocation() != null) {
            spawnLoc = fr.xephi.authme.settings.Spawn.getInstance().getLocation();
        }
        fr.xephi.authme.events.AuthMeTeleportEvent tpEvent = new fr.xephi.authme.events.AuthMeTeleportEvent(player, spawnLoc);
        plugin.getServer().getPluginManager().callEvent(tpEvent);
        if (!tpEvent.isCancelled()) {
            if (!tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).isLoaded()) {
                tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).load();
            }
            player.teleport(tpEvent.getTo());
        }
    }
    if (fr.xephi.authme.cache.limbo.LimboCache.getInstance().hasLimboPlayer(name)) {
        fr.xephi.authme.cache.limbo.LimboCache.getInstance().deleteLimboPlayer(name);
    }
    fr.xephi.authme.cache.limbo.LimboCache.getInstance().addLimboPlayer(player, utils.removeAll(player));
    fr.xephi.authme.cache.limbo.LimboCache.getInstance().addLimboPlayer(player);
    if (fr.xephi.authme.settings.Settings.protectInventoryBeforeLogInEnabled) {
        player.getInventory().clear();
        // create cache file for handling lost of inventories on unlogged in status
        fr.xephi.authme.cache.backup.DataFileCache playerData = new fr.xephi.authme.cache.backup.DataFileCache(fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).getInventory(), fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).getArmour());
        playerBackup.createCache(name, playerData, fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).getGroup(), fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).getOperator(), fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).isFlying());
    }
    int interval = fr.xephi.authme.settings.Settings.getWarnMessageInterval;
    org.bukkit.command.CommandSender _CVAR0 = sender;
    org.bukkit.Server _CVAR1 = _CVAR0.getServer();
    org.bukkit.scheduler.BukkitScheduler sched = _CVAR1.getScheduler();
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    org.bukkit.entity.Player _CVAR5 = player;
    java.lang.String _CVAR6 = _CVAR5.getName();
    java.lang.String name = _CVAR6.toLowerCase();
    int _CVAR9 = 20;
    int delay = fr.xephi.authme.settings.Settings.getRegistrationTimeout * _CVAR9;
    if (delay != 0) {
        fr.xephi.authme.AuthMe _CVAR4 = plugin;
        java.lang.String _CVAR7 = name;
        org.bukkit.scheduler.BukkitScheduler _CVAR2 = sched;
        fr.xephi.authme.AuthMe _CVAR3 = plugin;
        fr.xephi.authme.task.TimeoutTask _CVAR8 = new fr.xephi.authme.task.TimeoutTask(_CVAR4, _CVAR7);
        int _CVAR10 = delay;
        org.bukkit.scheduler.BukkitTask id = _CVAR2.runTaskLater(_CVAR3, _CVAR8, _CVAR10);
        fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setTimeoutTaskId(id.getTaskId());
    }
    org.bukkit.scheduler.BukkitTask msgT = sched.runTask(plugin, new fr.xephi.authme.task.MessageTask(plugin, name, m._("login_msg"), interval));
    fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setMessageTaskId(msgT.getTaskId());
    try {
        if (PlayersLogs.players.contains(player.getName())) {
            PlayersLogs.players.remove(player.getName());
            pllog.save();
        }
        if (player.isInsideVehicle()) {
            player.getVehicle().eject();
        }
    } catch (java.lang.NullPointerException npe) {
    }
    m._(player, "logout");
    fr.xephi.authme.ConsoleLogger.info(player.getDisplayName() + " logged out");
    if (plugin.notifications != null) {
        plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " logged out!"));
    }
    return true;
}