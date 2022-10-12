package uk.org.whoami.authme;
import me.muizers.Notifications.Notification;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import uk.org.whoami.authme.api.API;
import uk.org.whoami.authme.cache.auth.PlayerAuth;
import uk.org.whoami.authme.cache.auth.PlayerCache;
import uk.org.whoami.authme.cache.backup.FileCache;
import uk.org.whoami.authme.cache.limbo.LimboCache;
import uk.org.whoami.authme.cache.limbo.LimboPlayer;
import uk.org.whoami.authme.datasource.DataSource;
import uk.org.whoami.authme.events.AuthMeTeleportEvent;
import uk.org.whoami.authme.events.LoginEvent;
import uk.org.whoami.authme.events.RestoreInventoryEvent;
import uk.org.whoami.authme.events.SpawnTeleportEvent;
import uk.org.whoami.authme.listener.AuthMePlayerListener;
import uk.org.whoami.authme.security.PasswordSecurity;
import uk.org.whoami.authme.security.RandomString;
import uk.org.whoami.authme.settings.Messages;
import uk.org.whoami.authme.settings.PlayersLogs;
import uk.org.whoami.authme.settings.Settings;
import uk.org.whoami.authme.settings.Spawn;
public class Management {
    private uk.org.whoami.authme.settings.Messages m = uk.org.whoami.authme.settings.Messages.getInstance();

    private uk.org.whoami.authme.settings.PlayersLogs pllog = uk.org.whoami.authme.settings.PlayersLogs.getInstance();

    private uk.org.whoami.authme.Utils utils = uk.org.whoami.authme.Utils.getInstance();

    private uk.org.whoami.authme.cache.backup.FileCache playerCache = new uk.org.whoami.authme.cache.backup.FileCache();

    private uk.org.whoami.authme.datasource.DataSource database;

    public uk.org.whoami.authme.AuthMe plugin;

    private boolean passpartu = false;

    public static uk.org.whoami.authme.security.RandomString rdm = new uk.org.whoami.authme.security.RandomString(uk.org.whoami.authme.settings.Settings.captchaLength);

    public org.bukkit.plugin.PluginManager pm;

    public Management(uk.org.whoami.authme.datasource.DataSource database, uk.org.whoami.authme.AuthMe plugin) {
        this.database = database;
        this.plugin = plugin;
        this.pm = plugin.getServer().getPluginManager();
    }

    public Management(uk.org.whoami.authme.datasource.DataSource database, boolean passpartu, uk.org.whoami.authme.AuthMe plugin) {
        this.database = database;
        this.passpartu = passpartu;
        this.plugin = plugin;
        this.pm = plugin.getServer().getPluginManager();
    }

    public void performLogin(final org.bukkit.entity.Player player, final java.lang.String password) {
        final java.lang.String name = player.getName().toLowerCase();
        org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(plugin, new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                java.lang.String ip = player.getAddress().getAddress().getHostAddress();
                if (uk.org.whoami.authme.settings.Settings.bungee) {
                    try {
                        for (net.md_5.bungee.api.connection.ProxiedPlayer pp : net.md_5.bungee.BungeeCord.getInstance().getPlayers()) {
                            if (pp.getName().toLowerCase() == name) {
                                ip = pp.getAddress().getAddress().getHostAddress();
                                break;
                            }
                        }
                    } catch (java.lang.NoClassDefFoundError ncdfe) {
                    }
                }
                org.bukkit.World world = player.getWorld();
                org.bukkit.Location spawnLoc = world.getSpawnLocation();
                if (plugin.mv != null) {
                    try {
                        spawnLoc = plugin.mv.getMVWorldManager().getMVWorld(world).getSpawnLocation();
                    } catch (java.lang.NullPointerException npe) {
                    } catch (java.lang.ClassCastException cce) {
                    } catch (java.lang.NoClassDefFoundError ncdfe) {
                    }
                }
                if (uk.org.whoami.authme.settings.Spawn.getInstance().getLocation() != null)
                    spawnLoc = uk.org.whoami.authme.settings.Spawn.getInstance().getLocation();

                if (uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
                    player.sendMessage(m._("logged_in"));
                    return;
                }
                if (!database.isAuthAvailable(player.getName().toLowerCase())) {
                    player.sendMessage(m._("user_unknown"));
                    return;
                }
                uk.org.whoami.authme.cache.auth.PlayerAuth pAuth = database.getAuth(name);
                if (pAuth == null) {
                    player.sendMessage(m._("user_unknown"));
                    return;
                }
                if ((!Settings.getMySQLColumnGroup.isEmpty()) && (pAuth.getGroupId() == uk.org.whoami.authme.settings.Settings.getNonActivatedGroup)) {
                    player.sendMessage(m._("vb_nonActiv"));
                    return;
                }
                java.lang.String hash = pAuth.getHash();
                java.lang.String email = pAuth.getEmail();
                try {
                    if (!passpartu) {
                        if (uk.org.whoami.authme.settings.Settings.useCaptcha) {
                            if (!plugin.captcha.containsKey(name)) {
                                plugin.captcha.put(name, 1);
                            } else {
                                int i = plugin.captcha.get(name) + 1;
                                plugin.captcha.remove(name);
                                plugin.captcha.put(name, i);
                            }
                            if (plugin.captcha.containsKey(name) && (plugin.captcha.get(name) > uk.org.whoami.authme.settings.Settings.maxLoginTry)) {
                                player.sendMessage(m._("need_captcha"));
                                plugin.cap.put(name, uk.org.whoami.authme.Management.rdm.nextString());
                                player.sendMessage("Type : /captcha " + plugin.cap.get(name));
                                return;
                            } else if (plugin.captcha.containsKey(name) && (plugin.captcha.get(name) > uk.org.whoami.authme.settings.Settings.maxLoginTry)) {
                                try {
                                    plugin.captcha.remove(name);
                                    plugin.cap.remove(name);
                                } catch (java.lang.NullPointerException npe) {
                                }
                            }
                        }
                        if (uk.org.whoami.authme.security.PasswordSecurity.comparePasswordWithHash(password, hash, name)) {
                            uk.org.whoami.authme.cache.auth.PlayerAuth auth = new uk.org.whoami.authme.cache.auth.PlayerAuth(name, hash, ip, new java.util.Date().getTime(), email);
                            database.updateSession(auth);
                            uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().addPlayer(auth);
                            final uk.org.whoami.authme.cache.limbo.LimboPlayer limbo = uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name);
                            uk.org.whoami.authme.cache.auth.PlayerAuth getAuth = database.getAuth(name);
                            if (limbo != null) {
                                org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        player.setOp(limbo.getOperator());
                                    }
                                });
                                utils.addNormal(player, limbo.getGroup());
                                if (uk.org.whoami.authme.settings.Settings.isTeleportToSpawnEnabled && ((!uk.org.whoami.authme.settings.Settings.isForceSpawnLocOnJoinEnabled) && Settings.getForcedWorlds.contains(player.getWorld().getName()))) {
                                    if (uk.org.whoami.authme.settings.Settings.isSaveQuitLocationEnabled && (getAuth.getQuitLocY() != 0)) {
                                        utils.packCoords(getAuth.getQuitLocX(), getAuth.getQuitLocY(), getAuth.getQuitLocZ(), player);
                                    } else {
                                        org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                            @java.lang.Override
                                            public void run() {
                                                uk.org.whoami.authme.events.AuthMeTeleportEvent tpEvent = new uk.org.whoami.authme.events.AuthMeTeleportEvent(player, limbo.getLoc());
                                                pm.callEvent(tpEvent);
                                                org.bukkit.Location fLoc = tpEvent.getTo();
                                                if (!tpEvent.isCancelled()) {
                                                    if (!fLoc.getChunk().isLoaded()) {
                                                        fLoc.getChunk().load();
                                                    }
                                                    player.teleport(fLoc);
                                                }
                                            }
                                        });
                                    }
                                } else if (uk.org.whoami.authme.settings.Settings.isForceSpawnLocOnJoinEnabled && Settings.getForcedWorlds.contains(player.getWorld().getName())) {
                                    final org.bukkit.Location spawnL = spawnLoc;
                                    org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                        @java.lang.Override
                                        public void run() {
                                            uk.org.whoami.authme.events.SpawnTeleportEvent tpEvent = new uk.org.whoami.authme.events.SpawnTeleportEvent(player, player.getLocation(), spawnL, true);
                                            pm.callEvent(tpEvent);
                                            if (!tpEvent.isCancelled()) {
                                                org.bukkit.Location fLoc = tpEvent.getTo();
                                                if (!fLoc.getChunk().isLoaded()) {
                                                    fLoc.getChunk().load();
                                                }
                                                player.teleport(fLoc);
                                            }
                                        }
                                    });
                                } else if (uk.org.whoami.authme.settings.Settings.isSaveQuitLocationEnabled && (getAuth.getQuitLocY() != 0)) {
                                    utils.packCoords(getAuth.getQuitLocX(), getAuth.getQuitLocY(), getAuth.getQuitLocZ(), player);
                                } else {
                                    org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                        @java.lang.Override
                                        public void run() {
                                            uk.org.whoami.authme.events.AuthMeTeleportEvent tpEvent = new uk.org.whoami.authme.events.AuthMeTeleportEvent(player, limbo.getLoc());
                                            pm.callEvent(tpEvent);
                                            org.bukkit.Location fLoc = tpEvent.getTo();
                                            if (!tpEvent.isCancelled()) {
                                                if (!fLoc.getChunk().isLoaded()) {
                                                    fLoc.getChunk().load();
                                                }
                                                player.teleport(fLoc);
                                            }
                                        }
                                    });
                                }
                                org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        player.setGameMode(org.bukkit.GameMode.getByValue(limbo.getGameMode()));
                                    }
                                });
                                if (uk.org.whoami.authme.settings.Settings.protectInventoryBeforeLogInEnabled && player.hasPlayedBefore()) {
                                    uk.org.whoami.authme.events.RestoreInventoryEvent event = new uk.org.whoami.authme.events.RestoreInventoryEvent(player, limbo.getInventory(), limbo.getArmour());
                                    org.bukkit.Bukkit.getServer().getPluginManager().callEvent(event);
                                    if (!event.isCancelled()) {
                                        uk.org.whoami.authme.api.API.setPlayerInventory(player, limbo.getInventory(), limbo.getArmour());
                                    }
                                }
                                player.getServer().getScheduler().cancelTask(limbo.getTimeoutTaskId());
                                player.getServer().getScheduler().cancelTask(limbo.getMessageTaskId());
                                uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().deleteLimboPlayer(name);
                                if (playerCache.doesCacheExist(name)) {
                                    playerCache.removeCache(name);
                                }
                            }
                            /* Little Work Around under Registration Group Switching for admins that
                             add Registration thru a web Scripts.
                             */
                            if ((uk.org.whoami.authme.settings.Settings.isPermissionCheckEnabled && AuthMe.permission.playerInGroup(player, Settings.unRegisteredGroup)) && (!Settings.unRegisteredGroup.isEmpty())) {
                                AuthMe.permission.playerRemoveGroup(player.getWorld(), player.getName(), Settings.unRegisteredGroup);
                                AuthMe.permission.playerAddGroup(player.getWorld(), player.getName(), Settings.getRegisteredGroup);
                            }
                            try {
                                if (!PlayersLogs.players.contains(player.getName()))
                                    PlayersLogs.players.add(player.getName());

                                pllog.save();
                            } catch (java.lang.NullPointerException ex) {
                            }
                            org.bukkit.Bukkit.getServer().getPluginManager().callEvent(new uk.org.whoami.authme.events.LoginEvent(player, true));
                            if (uk.org.whoami.authme.settings.Settings.useCaptcha) {
                                if (plugin.captcha.containsKey(name)) {
                                    plugin.captcha.remove(name);
                                }
                                if (plugin.cap.containsKey(name)) {
                                    plugin.cap.containsKey(name);
                                }
                            }
                            player.sendMessage(m._("login"));
                            displayOtherAccounts(auth);
                            if (!uk.org.whoami.authme.settings.Settings.noConsoleSpam)
                                uk.org.whoami.authme.ConsoleLogger.info(player.getName() + " logged in!");

                            if (plugin.notifications != null) {
                                plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " logged in!"));
                            }
                            org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                @java.lang.Override
                                public void run() {
                                    player.saveData();
                                }
                            });
                        } else {
                            if (!uk.org.whoami.authme.settings.Settings.noConsoleSpam)
                                uk.org.whoami.authme.ConsoleLogger.info(player.getName() + " used the wrong password");

                            if (uk.org.whoami.authme.settings.Settings.isKickOnWrongPasswordEnabled) {
                                try {
                                    final int gm = AuthMePlayerListener.gameMode.get(name);
                                    org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                        @java.lang.Override
                                        public void run() {
                                            player.setGameMode(org.bukkit.GameMode.getByValue(gm));
                                        }
                                    });
                                } catch (java.lang.NullPointerException npe) {
                                }
                                org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        player.kickPlayer(m._("wrong_pwd"));
                                    }
                                });
                            } else {
                                player.sendMessage(m._("wrong_pwd"));
                                return;
                            }
                        }
                    } else {
                        // need for bypass password check if passpartu command is enabled
                        uk.org.whoami.authme.cache.auth.PlayerAuth auth = new uk.org.whoami.authme.cache.auth.PlayerAuth(name, hash, ip, new java.util.Date().getTime(), email);
                        database.updateSession(auth);
                        uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().addPlayer(auth);
                        final uk.org.whoami.authme.cache.limbo.LimboPlayer limbo = uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name);
                        if (limbo != null) {
                            org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                @java.lang.Override
                                public void run() {
                                    player.setOp(limbo.getOperator());
                                }
                            });
                            utils.addNormal(player, limbo.getGroup());
                            if (uk.org.whoami.authme.settings.Settings.isTeleportToSpawnEnabled && ((!uk.org.whoami.authme.settings.Settings.isForceSpawnLocOnJoinEnabled) && Settings.getForcedWorlds.contains(player.getWorld().getName()))) {
                                if (uk.org.whoami.authme.settings.Settings.isSaveQuitLocationEnabled && (database.getAuth(name).getQuitLocY() != 0)) {
                                    final org.bukkit.Location quitLoc = new org.bukkit.Location(player.getWorld(), database.getAuth(name).getQuitLocX() + 0.5, database.getAuth(name).getQuitLocY() + 0.5, database.getAuth(name).getQuitLocZ() + 0.5);
                                    org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                        @java.lang.Override
                                        public void run() {
                                            uk.org.whoami.authme.events.AuthMeTeleportEvent tpEvent = new uk.org.whoami.authme.events.AuthMeTeleportEvent(player, quitLoc);
                                            pm.callEvent(tpEvent);
                                            org.bukkit.Location fLoc = tpEvent.getTo();
                                            if (!tpEvent.isCancelled()) {
                                                if (!fLoc.getChunk().isLoaded()) {
                                                    fLoc.getChunk().load();
                                                }
                                                player.teleport(fLoc);
                                            }
                                        }
                                    });
                                } else {
                                    org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                        @java.lang.Override
                                        public void run() {
                                            uk.org.whoami.authme.events.AuthMeTeleportEvent tpEvent = new uk.org.whoami.authme.events.AuthMeTeleportEvent(player, limbo.getLoc());
                                            pm.callEvent(tpEvent);
                                            org.bukkit.Location fLoc = tpEvent.getTo();
                                            if (!tpEvent.isCancelled()) {
                                                if (!fLoc.getChunk().isLoaded()) {
                                                    fLoc.getChunk().load();
                                                }
                                                player.teleport(fLoc);
                                            }
                                        }
                                    });
                                }
                            } else if (uk.org.whoami.authme.settings.Settings.isForceSpawnLocOnJoinEnabled && Settings.getForcedWorlds.contains(player.getWorld().getName())) {
                                final org.bukkit.Location spawnL = spawnLoc;
                                org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        uk.org.whoami.authme.events.SpawnTeleportEvent tpEvent = new uk.org.whoami.authme.events.SpawnTeleportEvent(player, player.getLocation(), spawnL, true);
                                        pm.callEvent(tpEvent);
                                        if (!tpEvent.isCancelled()) {
                                            org.bukkit.Location fLoc = tpEvent.getTo();
                                            if (!fLoc.getChunk().isLoaded()) {
                                                fLoc.getChunk().load();
                                            }
                                            player.teleport(fLoc);
                                        }
                                    }
                                });
                            } else if (uk.org.whoami.authme.settings.Settings.isSaveQuitLocationEnabled && (database.getAuth(name).getQuitLocY() != 0)) {
                                final org.bukkit.Location quitLoc = new org.bukkit.Location(player.getWorld(), database.getAuth(name).getQuitLocX() + 0.5, database.getAuth(name).getQuitLocY() + 0.5, database.getAuth(name).getQuitLocZ() + 0.5);
                                org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        uk.org.whoami.authme.events.AuthMeTeleportEvent tpEvent = new uk.org.whoami.authme.events.AuthMeTeleportEvent(player, quitLoc);
                                        pm.callEvent(tpEvent);
                                        org.bukkit.Location fLoc = tpEvent.getTo();
                                        if (!tpEvent.isCancelled()) {
                                            if (!fLoc.getChunk().isLoaded()) {
                                                fLoc.getChunk().load();
                                            }
                                            player.teleport(fLoc);
                                        }
                                    }
                                });
                            } else {
                                org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        uk.org.whoami.authme.events.AuthMeTeleportEvent tpEvent = new uk.org.whoami.authme.events.AuthMeTeleportEvent(player, limbo.getLoc());
                                        pm.callEvent(tpEvent);
                                        org.bukkit.Location fLoc = tpEvent.getTo();
                                        if (!tpEvent.isCancelled()) {
                                            if (!fLoc.getChunk().isLoaded()) {
                                                fLoc.getChunk().load();
                                            }
                                            player.teleport(fLoc);
                                        }
                                    }
                                });
                            }
                            org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                                @java.lang.Override
                                public void run() {
                                    player.setGameMode(org.bukkit.GameMode.getByValue(limbo.getGameMode()));
                                }
                            });
                            if (uk.org.whoami.authme.settings.Settings.protectInventoryBeforeLogInEnabled && player.hasPlayedBefore()) {
                                uk.org.whoami.authme.events.RestoreInventoryEvent event = new uk.org.whoami.authme.events.RestoreInventoryEvent(player, limbo.getInventory(), limbo.getArmour());
                                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(event);
                                if (!event.isCancelled()) {
                                    uk.org.whoami.authme.api.API.setPlayerInventory(player, limbo.getInventory(), limbo.getArmour());
                                }
                            }
                            player.getServer().getScheduler().cancelTask(limbo.getTimeoutTaskId());
                            player.getServer().getScheduler().cancelTask(limbo.getMessageTaskId());
                            uk.org.whoami.authme.cache.limbo.LimboCache.getInstance().deleteLimboPlayer(name);
                            if (playerCache.doesCacheExist(name)) {
                                playerCache.removeCache(name);
                            }
                        }
                        /* Little Work Around under Registration Group Switching for admins that
                         add Registration thru a web Scripts.
                         */
                        if ((uk.org.whoami.authme.settings.Settings.isPermissionCheckEnabled && AuthMe.permission.playerInGroup(player, Settings.unRegisteredGroup)) && (!Settings.unRegisteredGroup.isEmpty())) {
                            AuthMe.permission.playerRemoveGroup(player.getWorld(), player.getName(), Settings.unRegisteredGroup);
                            AuthMe.permission.playerAddGroup(player.getWorld(), player.getName(), Settings.getRegisteredGroup);
                        }
                        try {
                            if (!PlayersLogs.players.contains(player.getName()))
                                PlayersLogs.players.add(player.getName());

                            pllog.save();
                        } catch (java.lang.NullPointerException ex) {
                        }
                        org.bukkit.Bukkit.getServer().getPluginManager().callEvent(new uk.org.whoami.authme.events.LoginEvent(player, true));
                        if (uk.org.whoami.authme.settings.Settings.useCaptcha) {
                            if (plugin.captcha.containsKey(name)) {
                                plugin.captcha.remove(name);
                            }
                            if (plugin.cap.containsKey(name)) {
                                plugin.cap.containsKey(name);
                            }
                        }
                        player.sendMessage(m._("login"));
                        displayOtherAccounts(auth);
                        if (!uk.org.whoami.authme.settings.Settings.noConsoleSpam)
                            uk.org.whoami.authme.ConsoleLogger.info(player.getName() + " logged in!");

                        if (plugin.notifications != null) {
                            plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " logged in!"));
                        }
                        org.bukkit.Bukkit.getScheduler().runTask(plugin, new java.lang.Runnable() {
                            @java.lang.Override
                            public void run() {
                                player.saveData();
                            }
                        });
                        passpartu = false;
                    }
                } catch (java.security.NoSuchAlgorithmException ex) {
                    uk.org.whoami.authme.ConsoleLogger.showError(ex.getMessage());
                    player.sendMessage(m._("error"));
                    return;
                }
                return;
            }
        });
    }

    private void displayOtherAccounts(uk.org.whoami.authme.cache.auth.PlayerAuth auth) {
        if (!uk.org.whoami.authme.settings.Settings.displayOtherAccounts) {
            return;
        }
        if (auth == null) {
            return;
        }
        if (this.database.getAllAuthsByName(auth).isEmpty() || (this.database.getAllAuthsByName(auth) == null)) {
            return;
        }
        if (this.database.getAllAuthsByName(auth).size() == 1) {
            return;
        }
        java.util.List<java.lang.String> accountList = this.database.getAllAuthsByName(auth);
        java.lang.String message = "[AuthMe] ";
        int i = 0;
        for (java.lang.String account : accountList) {
            i++;
            message = message + account;
            if (i != accountList.size()) {
                message = message + ", ";
            } else {
                message = message + ".";
            }
        }
        for (org.bukkit.entity.Player player : uk.org.whoami.authme.AuthMe.getInstance().getServer().getOnlinePlayers()) {
            java.lang.String playerName = player.getName();
            if () {
                player.sendMessage(((("[AuthMe] The player " + auth.getNickname()) + " has ") + java.lang.String.valueOf(accountList.size())) + " accounts");
                player.sendMessage(message);
            }
        }
    }
}