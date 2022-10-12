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
    if (args.length != 1) {
        m._(player, "usage_unreg");
        return true;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    org.bukkit.entity.Player _CVAR5 = player;
    java.lang.String _CVAR6 = _CVAR5.getName();
    java.lang.String name = _CVAR6.toLowerCase();
    try {
        if (fr.xephi.authme.security.PasswordSecurity.comparePasswordWithHash(args[0], fr.xephi.authme.cache.auth.PlayerCache.getInstance().getAuth(name).getHash(), name)) {
            if (!database.removeAuth(name)) {
                player.sendMessage("error");
                return true;
            }
            if (fr.xephi.authme.settings.Settings.isForcedRegistrationEnabled) {
                player.getInventory().setArmorContents(new org.bukkit.inventory.ItemStack[4]);
                player.getInventory().setContents(new org.bukkit.inventory.ItemStack[36]);
                player.saveData();
                fr.xephi.authme.cache.auth.PlayerCache.getInstance().removePlayer(player.getName().toLowerCase());
                fr.xephi.authme.cache.limbo.LimboCache.getInstance().addLimboPlayer(player);
                int interval = fr.xephi.authme.settings.Settings.getWarnMessageInterval;
                org.bukkit.command.CommandSender _CVAR0 = sender;
                org.bukkit.Server _CVAR1 = _CVAR0.getServer();
                org.bukkit.scheduler.BukkitScheduler sched = _CVAR1.getScheduler();
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
                sched.scheduleSyncDelayedTask(plugin, new fr.xephi.authme.task.MessageTask(plugin, name, m._("reg_msg"), interval));
                if (!Settings.unRegisteredGroup.isEmpty()) {
                    fr.xephi.authme.Utils.getInstance().setGroup(player, Utils.groupType.UNREGISTERED);
                }
                player.sendMessage("unregistered");
                fr.xephi.authme.ConsoleLogger.info(player.getDisplayName() + " unregistered himself");
                if (plugin.notifications != null) {
                    plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " unregistered himself!"));
                }
                return true;
            }
            if (!Settings.unRegisteredGroup.isEmpty()) {
                fr.xephi.authme.Utils.getInstance().setGroup(player, Utils.groupType.UNREGISTERED);
            }
            fr.xephi.authme.cache.auth.PlayerCache.getInstance().removePlayer(player.getName().toLowerCase());
            // check if Player cache File Exist and delete it, preventing duplication of items
            if (playerCache.doesCacheExist(name)) {
                playerCache.removeCache(name);
            }
            if (PlayersLogs.players.contains(player.getName())) {
                PlayersLogs.players.remove(player.getName());
                pllog.save();
            }
            player.sendMessage("unregistered");
            fr.xephi.authme.ConsoleLogger.info(player.getDisplayName() + " unregistered himself");
            if (plugin.notifications != null) {
                plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " unregistered himself!"));
            }
            if (fr.xephi.authme.settings.Settings.isTeleportToSpawnEnabled) {
                org.bukkit.Location spawn = plugin.getSpawnLocation(player.getWorld());
                fr.xephi.authme.events.SpawnTeleportEvent tpEvent = new fr.xephi.authme.events.SpawnTeleportEvent(player, player.getLocation(), spawn, false);
                plugin.getServer().getPluginManager().callEvent(tpEvent);
                if (!tpEvent.isCancelled()) {
                    if (!tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).isLoaded()) {
                        tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).load();
                    }
                    player.teleport(tpEvent.getTo());
                }
            }
            return true;
        } else {
            m._(player, "wrong_pwd");
        }
    } catch (java.security.NoSuchAlgorithmException ex) {
        fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        sender.sendMessage("Internal Error please read the server log");
    }
    return true;
}