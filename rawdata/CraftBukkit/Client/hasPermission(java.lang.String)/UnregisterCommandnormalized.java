@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return true;
    }
    boolean _CVAR0 = !sender.hasPermission("authme." + label.toLowerCase());
    if () {
        sender.sendMessage(m._("no_perm"));
        return true;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    java.lang.String name = player.getName().toLowerCase();
    if (!uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        player.sendMessage(m._("not_logged_in"));
        return true;
    }
    if (args.length != 1) {
        player.sendMessage(m._("usage_unreg"));
        return true;
    }
    try {
        if (uk.org.whoami.authme.security.PasswordSecurity.comparePasswordWithHash(args[0], uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().getAuth(name).getHash(), name)) {
            if (!database.removeAuth(name)) {
                player.sendMessage("error");
                return true;
            }
            if (uk.org.whoami.authme.settings.Settings.isForcedRegistrationEnabled) {
                player.getInventory().setArmorContents(new org.bukkit.inventory.ItemStack[4]);
                player.getInventory().setContents(new org.bukkit.inventory.ItemStack[36]);
                player.saveData();
                uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().removePlayer(player.getName().toLowerCase());
                uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().addLimboPlayer(player);
                int delay = uk.org.whoami.authme.settings.Settings.getRegistrationTimeout * 20;
                int interval = uk.org.whoami.authme.settings.Settings.getWarnMessageInterval;
                org.bukkit.scheduler.BukkitScheduler sched = sender.getServer().getScheduler();
                if (delay != 0) {
                    org.bukkit.scheduler.BukkitTask id = sched.runTaskLater(plugin, new uk.org.whoami.authme.task.TimeoutTask(plugin, name), delay);
                    uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setTimeoutTaskId(id.getTaskId());
                }
                sched.runTask(plugin, new uk.org.whoami.authme.task.MessageTask(plugin, name, m._("reg_msg"), interval));
                if (!Settings.unRegisteredGroup.isEmpty()) {
                    uk.org.whoami.authme.Utils.getInstance().setGroup(player, Utils.groupType.UNREGISTERED);
                }
                player.sendMessage("unregistered");
                uk.org.whoami.authme.ConsoleLogger.info(player.getDisplayName() + " unregistered himself");
                if (plugin.notifications != null) {
                    plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " unregistered himself!"));
                }
                return true;
            }
            if (!Settings.unRegisteredGroup.isEmpty()) {
                uk.org.whoami.authme.Utils.getInstance().setGroup(player, Utils.groupType.UNREGISTERED);
            }
            uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().removePlayer(player.getName().toLowerCase());
            // check if Player cache File Exist and delete it, preventing duplication of items
            if (playerCache.doesCacheExist(name)) {
                playerCache.removeCache(name);
            }
            if (PlayersLogs.players.contains(player.getName())) {
                PlayersLogs.players.remove(player.getName());
                pllog.save();
            }
            player.sendMessage("unregistered");
            uk.org.whoami.authme.ConsoleLogger.info(player.getDisplayName() + " unregistered himself");
            if (plugin.notifications != null) {
                plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " unregistered himself!"));
            }
            if (uk.org.whoami.authme.settings.Settings.isTeleportToSpawnEnabled) {
                org.bukkit.Location spawn = player.getWorld().getSpawnLocation();
                if (plugin.mv != null) {
                    try {
                        spawn = plugin.mv.getMVWorldManager().getMVWorld(player.getWorld()).getSpawnLocation();
                    } catch (java.lang.NullPointerException npe) {
                    } catch (java.lang.ClassCastException cce) {
                    } catch (java.lang.NoClassDefFoundError ncdfe) {
                    }
                }
                if (uk.org.whoami.authme.settings.Spawn.getInstance().getLocation() != null) {
                    spawn = uk.org.whoami.authme.settings.Spawn.getInstance().getLocation();
                }
                uk.org.whoami.authme.events.SpawnTeleportEvent tpEvent = new uk.org.whoami.authme.events.SpawnTeleportEvent(player, player.getLocation(), spawn, false);
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
            player.sendMessage(m._("wrong_pwd"));
        }
    } catch (java.security.NoSuchAlgorithmException ex) {
        uk.org.whoami.authme.ConsoleLogger.showError(ex.getMessage());
        sender.sendMessage("Internal Error please read the server log");
    }
    return true;
}