@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return true;
    }
    if (!plugin.authmePermissible(sender, "authme." + label.toLowerCase())) {
        fr.xephi.authme.settings.Messages _CVAR1 = m;
        java.lang.String _CVAR2 = "no_perm";
        org.bukkit.command.CommandSender _CVAR0 = sender;
         _CVAR3 = _CVAR1._(_CVAR2);
        _CVAR0.sendMessage(_CVAR3);
        return true;
    }
    final java.lang.String name = player.getName().toLowerCase();
    java.lang.String ipA = player.getAddress().getAddress().getHostAddress();
    if (fr.xephi.authme.settings.Settings.bungee) {
        if (plugin.realIp.containsKey(name)) {
            ipA = plugin.realIp.get(name);
        }
    }
    final java.lang.String ip = ipA;
    if (fr.xephi.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        fr.xephi.authme.settings.Messages _CVAR5 = m;
        java.lang.String _CVAR6 = "logged_in";
        org.bukkit.entity.Player _CVAR4 = player;
         _CVAR7 = _CVAR5._(_CVAR6);
        _CVAR4.sendMessage(_CVAR7);
        return true;
    }
    if (!fr.xephi.authme.settings.Settings.isRegistrationEnabled) {
        fr.xephi.authme.settings.Messages _CVAR9 = m;
        java.lang.String _CVAR10 = "reg_disabled";
        org.bukkit.entity.Player _CVAR8 = player;
         _CVAR11 = _CVAR9._(_CVAR10);
        _CVAR8.sendMessage(_CVAR11);
        return true;
    }
    if (database.isAuthAvailable(player.getName().toLowerCase())) {
        fr.xephi.authme.settings.Messages _CVAR13 = m;
        java.lang.String _CVAR14 = "user_regged";
        org.bukkit.entity.Player _CVAR12 = player;
         _CVAR15 = _CVAR13._(_CVAR14);
        _CVAR12.sendMessage(_CVAR15);
        if (pllog.getStringList("players").contains(player.getName())) {
            pllog.getStringList("players").remove(player.getName());
        }
        return true;
    }
    if (fr.xephi.authme.settings.Settings.getmaxRegPerIp > 0) {
        if ((!plugin.authmePermissible(sender, "authme.allow2accounts")) && (database.getAllAuthsByIp(ipA).size() >= fr.xephi.authme.settings.Settings.getmaxRegPerIp)) {
            fr.xephi.authme.settings.Messages _CVAR17 = m;
            java.lang.String _CVAR18 = "max_reg";
            org.bukkit.entity.Player _CVAR16 = player;
             _CVAR19 = _CVAR17._(_CVAR18);
            _CVAR16.sendMessage(_CVAR19);
            return true;
        }
    }
    if (fr.xephi.authme.settings.Settings.emailRegistration && (!Settings.getmailAccount.isEmpty())) {
        if (!args[0].contains("@")) {
            fr.xephi.authme.settings.Messages _CVAR21 = m;
            java.lang.String _CVAR22 = "usage_reg";
            org.bukkit.entity.Player _CVAR20 = player;
             _CVAR23 = _CVAR21._(_CVAR22);
            _CVAR20.sendMessage(_CVAR23);
            return true;
        }
        if (fr.xephi.authme.settings.Settings.doubleEmailCheck) {
            if (args.length < 2) {
                fr.xephi.authme.settings.Messages _CVAR25 = m;
                java.lang.String _CVAR26 = "usage_reg";
                org.bukkit.entity.Player _CVAR24 = player;
                 _CVAR27 = _CVAR25._(_CVAR26);
                _CVAR24.sendMessage(_CVAR27);
                return true;
            }
            if (!args[0].equals(args[1])) {
                fr.xephi.authme.settings.Messages _CVAR29 = m;
                java.lang.String _CVAR30 = "usage_reg";
                org.bukkit.entity.Player _CVAR28 = player;
                 _CVAR31 = _CVAR29._(_CVAR30);
                _CVAR28.sendMessage(_CVAR31);
                return true;
            }
        }
        final java.lang.String email = args[0];
        if (fr.xephi.authme.settings.Settings.getmaxRegPerEmail > 0) {
            if ((!plugin.authmePermissible(sender, "authme.allow2accounts")) && (database.getAllAuthsByEmail(email).size() >= fr.xephi.authme.settings.Settings.getmaxRegPerEmail)) {
                fr.xephi.authme.settings.Messages _CVAR33 = m;
                java.lang.String _CVAR34 = "max_reg";
                org.bukkit.entity.Player _CVAR32 = player;
                 _CVAR35 = _CVAR33._(_CVAR34);
                _CVAR32.sendMessage(_CVAR35);
                return true;
            }
        }
        fr.xephi.authme.security.RandomString rand = new fr.xephi.authme.security.RandomString(fr.xephi.authme.settings.Settings.getRecoveryPassLength);
        final java.lang.String thePass = rand.nextString();
        if (!thePass.isEmpty()) {
            org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(plugin, new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    if (PasswordSecurity.userSalt.containsKey(name)) {
                        try {
                            final java.lang.String hashnew = fr.xephi.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, thePass, name);
                            final fr.xephi.authme.cache.auth.PlayerAuth fAuth = new fr.xephi.authme.cache.auth.PlayerAuth(name, hashnew, PasswordSecurity.userSalt.get(name), ip, new java.util.Date().getTime(), ((int) (player.getLocation().getX())), ((int) (player.getLocation().getY())), ((int) (player.getLocation().getZ())), player.getLocation().getWorld().getName(), email, player.getName());
                            database.saveAuth(fAuth);
                            database.updateEmail(fAuth);
                            database.updateSession(fAuth);
                            plugin.mail.main(fAuth, thePass);
                        } catch (java.security.NoSuchAlgorithmException e) {
                            fr.xephi.authme.ConsoleLogger.showError(e.getMessage());
                        }
                    } else {
                        try {
                            final java.lang.String hashnew = fr.xephi.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, thePass, name);
                            final fr.xephi.authme.cache.auth.PlayerAuth fAuth = new fr.xephi.authme.cache.auth.PlayerAuth(name, hashnew, ip, new java.util.Date().getTime(), ((int) (player.getLocation().getX())), ((int) (player.getLocation().getY())), ((int) (player.getLocation().getZ())), player.getLocation().getWorld().getName(), email, player.getName());
                            database.saveAuth(fAuth);
                            database.updateEmail(fAuth);
                            database.updateSession(fAuth);
                            plugin.mail.main(fAuth, thePass);
                        } catch (java.security.NoSuchAlgorithmException e) {
                            fr.xephi.authme.ConsoleLogger.showError(e.getMessage());
                        }
                    }
                }
            });
            if (!Settings.getRegisteredGroup.isEmpty()) {
                fr.xephi.authme.Utils.getInstance().setGroup(player, Utils.groupType.REGISTERED);
            }
            fr.xephi.authme.settings.Messages _CVAR37 = m;
            java.lang.String _CVAR38 = "vb_nonActiv";
            org.bukkit.entity.Player _CVAR36 = player;
             _CVAR39 = _CVAR37._(_CVAR38);
            _CVAR36.sendMessage(_CVAR39);
            java.lang.String msg = m._("login_msg");
            int time = fr.xephi.authme.settings.Settings.getRegistrationTimeout * 20;
            int msgInterval = fr.xephi.authme.settings.Settings.getWarnMessageInterval;
            if (time != 0) {
                org.bukkit.Bukkit.getScheduler().cancelTask(fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).getTimeoutTaskId());
                org.bukkit.scheduler.BukkitTask id = org.bukkit.Bukkit.getScheduler().runTaskLater(plugin, new fr.xephi.authme.task.TimeoutTask(plugin, name), time);
                fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setTimeoutTaskId(id.getTaskId());
            }
            org.bukkit.Bukkit.getScheduler().cancelTask(fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).getMessageTaskId());
            org.bukkit.scheduler.BukkitTask nwMsg = org.bukkit.Bukkit.getScheduler().runTask(plugin, new fr.xephi.authme.task.MessageTask(plugin, name, msg, msgInterval));
            fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setMessageTaskId(nwMsg.getTaskId());
            fr.xephi.authme.cache.limbo.LimboCache.getInstance().deleteLimboPlayer(name);
            if (fr.xephi.authme.settings.Settings.isTeleportToSpawnEnabled) {
                org.bukkit.World world = player.getWorld();
                org.bukkit.Location loca = plugin.getSpawnLocation(world);
                fr.xephi.authme.events.RegisterTeleportEvent tpEvent = new fr.xephi.authme.events.RegisterTeleportEvent(player, loca);
                plugin.getServer().getPluginManager().callEvent(tpEvent);
                if (!tpEvent.isCancelled()) {
                    if (!tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).isLoaded()) {
                        tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).load();
                    }
                    player.teleport(tpEvent.getTo());
                }
            }
            this.isFirstTimeJoin = true;
            if ((player.getGameMode() != org.bukkit.GameMode.CREATIVE) && (!fr.xephi.authme.settings.Settings.isMovementAllowed)) {
                player.setAllowFlight(false);
                player.setFlying(false);
            }
            player.saveData();
            if (!fr.xephi.authme.settings.Settings.noConsoleSpam) {
                fr.xephi.authme.ConsoleLogger.info((player.getName() + " registered ") + player.getAddress().getAddress().getHostAddress());
            }
            if (plugin.notifications != null) {
                plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " has registered!"));
            }
            return true;
        }
    }
    if ((args.length == 0) || (fr.xephi.authme.settings.Settings.getEnablePasswordVerifier && (args.length < 2))) {
        fr.xephi.authme.settings.Messages _CVAR41 = m;
        java.lang.String _CVAR42 = "usage_reg";
        org.bukkit.entity.Player _CVAR40 = player;
         _CVAR43 = _CVAR41._(_CVAR42);
        _CVAR40.sendMessage(_CVAR43);
        return true;
    }
    if ((args[0].length() < fr.xephi.authme.settings.Settings.getPasswordMinLen) || (args[0].length() > fr.xephi.authme.settings.Settings.passwordMaxLength)) {
        fr.xephi.authme.settings.Messages _CVAR45 = m;
        java.lang.String _CVAR46 = "pass_len";
        org.bukkit.entity.Player _CVAR44 = player;
         _CVAR47 = _CVAR45._(_CVAR46);
        _CVAR44.sendMessage(_CVAR47);
        return true;
    }
    final org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    try {
        java.lang.String hash;
        if (fr.xephi.authme.settings.Settings.getEnablePasswordVerifier) {
            if (args[0].equals(args[1])) {
                hash = fr.xephi.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, args[0], name);
            } else {
                fr.xephi.authme.settings.Messages _CVAR49 = m;
                java.lang.String _CVAR50 = "password_error";
                org.bukkit.entity.Player _CVAR48 = player;
                 _CVAR51 = _CVAR49._(_CVAR50);
                _CVAR48.sendMessage(_CVAR51);
                return true;
            }
        } else {
            hash = fr.xephi.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, args[0], name);
        }
        if (Settings.getMySQLColumnSalt.isEmpty()) {
            auth = new fr.xephi.authme.cache.auth.PlayerAuth(name, hash, ip, new java.util.Date().getTime(), "your@email.com", player.getName());
        } else {
            auth = new fr.xephi.authme.cache.auth.PlayerAuth(name, hash, PasswordSecurity.userSalt.get(name), ip, new java.util.Date().getTime(), player.getName());
        }
        if (!database.saveAuth(auth)) {
            fr.xephi.authme.settings.Messages _CVAR53 = m;
            java.lang.String _CVAR54 = "error";
            org.bukkit.entity.Player _CVAR52 = player;
             _CVAR55 = _CVAR53._(_CVAR54);
            _CVAR52.sendMessage(_CVAR55);
            return true;
        }
        fr.xephi.authme.cache.auth.PlayerCache.getInstance().addPlayer(auth);
        fr.xephi.authme.cache.limbo.LimboPlayer limbo = fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name);
        if (limbo != null) {
            player.setGameMode(org.bukkit.GameMode.getByValue(limbo.getGameMode()));
            if (fr.xephi.authme.settings.Settings.isTeleportToSpawnEnabled) {
                org.bukkit.World world = player.getWorld();
                org.bukkit.Location loca = plugin.getSpawnLocation(world);
                fr.xephi.authme.events.RegisterTeleportEvent tpEvent = new fr.xephi.authme.events.RegisterTeleportEvent(player, loca);
                plugin.getServer().getPluginManager().callEvent(tpEvent);
                if (!tpEvent.isCancelled()) {
                    if (!tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).isLoaded()) {
                        tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).load();
                    }
                    player.teleport(tpEvent.getTo());
                }
            }
            sender.getServer().getScheduler().cancelTask(limbo.getTimeoutTaskId());
            sender.getServer().getScheduler().cancelTask(limbo.getMessageTaskId());
            fr.xephi.authme.cache.limbo.LimboCache.getInstance().deleteLimboPlayer(name);
        }
        if (!Settings.getRegisteredGroup.isEmpty()) {
            fr.xephi.authme.Utils.getInstance().setGroup(player, Utils.groupType.REGISTERED);
        }
        fr.xephi.authme.settings.Messages _CVAR57 = m;
        java.lang.String _CVAR58 = "registered";
        org.bukkit.entity.Player _CVAR56 = player;
         _CVAR59 = _CVAR57._(_CVAR58);
        _CVAR56.sendMessage(_CVAR59);
        if (!Settings.getmailAccount.isEmpty()) {
            fr.xephi.authme.settings.Messages _CVAR61 = m;
            java.lang.String _CVAR62 = "add_email";
            org.bukkit.entity.Player _CVAR60 = player;
             _CVAR63 = _CVAR61._(_CVAR62);
            _CVAR60.sendMessage(_CVAR63);
        }
        this.isFirstTimeJoin = true;
        if ((player.getGameMode() != org.bukkit.GameMode.CREATIVE) && (!fr.xephi.authme.settings.Settings.isMovementAllowed)) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
        player.saveData();
        if (!fr.xephi.authme.settings.Settings.noConsoleSpam) {
            fr.xephi.authme.ConsoleLogger.info((player.getName() + " registered ") + player.getAddress().getAddress().getHostAddress());
        }
        if (plugin.notifications != null) {
            plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " has registered!"));
        }
    } catch (java.security.NoSuchAlgorithmException ex) {
        fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        fr.xephi.authme.settings.Messages _CVAR65 = m;
        java.lang.String _CVAR66 = "error";
        org.bukkit.command.CommandSender _CVAR64 = sender;
         _CVAR67 = _CVAR65._(_CVAR66);
        _CVAR64.sendMessage(_CVAR67);
    }
    return true;
}