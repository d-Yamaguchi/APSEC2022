@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (args.length == 0) {
        sender.sendMessage("Usage: /authme reload - Reload the config");
        sender.sendMessage("/authme register <playername> <password> - Register a player");
        sender.sendMessage("/authme changepassword <playername> <password> - Change player password");
        sender.sendMessage("/authme unregister <playername> - Unregister a player");
        sender.sendMessage("/authme purge <days> - Purge Database");
        sender.sendMessage("/authme version - Get AuthMe version infos");
        sender.sendMessage("/authme lastlogin <playername> - Display Date about the Player's LastLogin");
        sender.sendMessage("/authme accounts <playername> - Display all player's accounts");
        sender.sendMessage("/authme setSpawn - Set AuthMe spawn to your current pos");
        sender.sendMessage("/authme spawn - Teleport you to the AuthMe SpawnPoint");
        sender.sendMessage("/authme chgemail <playername> <email> - Change player email");
        sender.sendMessage("/authme getemail <playername> - Get player email");
        sender.sendMessage("/authme purgelastpos <playername> - Purge last position for a player");
        sender.sendMessage("/authme switchantibot on/off - Enable/Disable antibot method");
        return true;
    }
    if (!plugin.authmePermissible(sender, "authme.admin." + args[0].toLowerCase())) {
        m._(sender, "no_perm");
        return true;
    }
    if ((sender instanceof org.bukkit.command.ConsoleCommandSender) && args[0].equalsIgnoreCase("passpartuToken")) {
        if (args.length > 1) {
            java.lang.System.out.println("[AuthMe] command usage: /authme passpartuToken");
            return true;
        }
        if (fr.xephi.authme.Utils.getInstance().obtainToken()) {
            java.lang.System.out.println("[AuthMe] You have 30s for insert this token ingame with /passpartu [token]");
        } else {
            java.lang.System.out.println("[AuthMe] Security error on passpartu token, redo it. ");
        }
        return true;
    }
    if (args[0].equalsIgnoreCase("version")) {
        sender.sendMessage("AuthMe Version: " + fr.xephi.authme.AuthMe.getInstance().getDescription().getVersion());
        return true;
    }
    if (args[0].equalsIgnoreCase("purge")) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /authme purge <DAYS>");
            return true;
        }
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.add(java.util.Calendar.DATE, -java.lang.Integer.parseInt(args[1]));
            long until = calendar.getTimeInMillis();
            java.util.List<java.lang.String> purged = database.autoPurgeDatabase(until);
            sender.sendMessage(("Deleted " + purged.size()) + " user accounts");
            if (fr.xephi.authme.settings.Settings.purgeEssentialsFile && (plugin.ess != null)) {
                plugin.purgeEssentials(purged);
            }
            if (fr.xephi.authme.settings.Settings.purgePlayerDat) {
                plugin.purgeDat(purged);
            }
            if (fr.xephi.authme.settings.Settings.purgeLimitedCreative) {
                plugin.purgeLimitedCreative(purged);
            }
            if (fr.xephi.authme.settings.Settings.purgeAntiXray) {
                plugin.purgeAntiXray(purged);
            }
            return true;
        } catch (java.lang.NumberFormatException e) {
            sender.sendMessage("Usage: /authme purge <DAYS>");
            return true;
        }
    } else if (args[0].equalsIgnoreCase("reload")) {
        database.reload();
        java.io.File newConfigFile = new java.io.File("plugins/AuthMe", "config.yml");
        if (!newConfigFile.exists()) {
            java.io.InputStream fis = getClass().getResourceAsStream("/config.yml");
            java.io.FileOutputStream fos = null;
            try {
                fos = new java.io.FileOutputStream(newConfigFile);
                byte[] buf = new byte[1024];
                int i = 0;
                while ((i = fis.read(buf)) != (-1)) {
                    fos.write(buf, 0, i);
                } 
            } catch (java.lang.Exception e) {
                java.util.logging.Logger.getLogger(org.bukkit.plugin.java.JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Failed to load config from JAR");
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (java.lang.Exception e) {
                }
            }
        }
        org.bukkit.configuration.file.YamlConfiguration newConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(newConfigFile);
        fr.xephi.authme.settings.Settings.reloadConfigOptions(newConfig);
        m.reLoad();
        s.reLoad();
        m._(sender, "reload");
    } else if (args[0].equalsIgnoreCase("lastlogin")) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /authme lastlogin <playername>");
            return true;
        }
        try {
            if (database.getAuth(args[1].toLowerCase()) != null) {
                fr.xephi.authme.cache.auth.PlayerAuth player = database.getAuth(args[1].toLowerCase());
                long lastLogin = player.getLastLogin();
                java.util.Date d = new java.util.Date(lastLogin);
                final long diff = java.lang.System.currentTimeMillis() - lastLogin;
                final java.lang.String msg = ((((((((int) (diff / 86400000)) + " days ") + ((int) ((diff / 3600000) % 24))) + " hours ") + ((int) ((diff / 60000) % 60))) + " mins ") + ((int) ((diff / 1000) % 60))) + " secs.";
                java.lang.String lastIP = player.getIp();
                sender.sendMessage((("[AuthMe] " + args[1].toLowerCase()) + " lastlogin : ") + d.toString());
                sender.sendMessage((("[AuthMe] The player : " + player.getNickname()) + " is unlogged since ") + msg);
                sender.sendMessage("[AuthMe] LastPlayer IP : " + lastIP);
            } else {
                m._(sender, "unknown_user");
                return true;
            }
        } catch (java.lang.NullPointerException e) {
            m._(sender, "unknown_user");
            return true;
        }
    } else if (args[0].equalsIgnoreCase("accounts")) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /authme accounts <playername>");
            sender.sendMessage("Or: /authme accounts <ip>");
            return true;
        }
        if (!args[1].contains(".")) {
            final org.bukkit.command.CommandSender fSender = sender;
            final java.lang.String[] arguments = args;
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    fr.xephi.authme.cache.auth.PlayerAuth pAuth = null;
                    java.lang.String message = "[AuthMe] ";
                    try {
                        pAuth = database.getAuth(arguments[1].toLowerCase());
                    } catch (java.lang.NullPointerException npe) {
                        fSender.sendMessage("[AuthMe] This player is unknown");
                        return;
                    }
                    if (pAuth != null) {
                        java.util.List<java.lang.String> accountList = database.getAllAuthsByName(pAuth);
                        if (accountList.isEmpty() || (accountList == null)) {
                            fSender.sendMessage("[AuthMe] This player is unknown");
                            return;
                        }
                        if (accountList.size() == 1) {
                            fSender.sendMessage(("[AuthMe] " + arguments[1]) + " is a single account player");
                            return;
                        }
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
                        fSender.sendMessage(((("[AuthMe] " + arguments[1]) + " has ") + java.lang.String.valueOf(accountList.size())) + " accounts");
                        fSender.sendMessage(message);
                    } else {
                        fSender.sendMessage("[AuthMe] This player is unknown");
                        return;
                    }
                }
            });
            return true;
        } else {
            final org.bukkit.command.CommandSender fSender = sender;
            final java.lang.String[] arguments = args;
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    java.lang.String message = "[AuthMe] ";
                    if (arguments[1] != null) {
                        java.util.List<java.lang.String> accountList = database.getAllAuthsByIp(arguments[1]);
                        if (accountList.isEmpty() || (accountList == null)) {
                            fSender.sendMessage("[AuthMe] Please put a valid IP");
                            return;
                        }
                        if (accountList.size() == 1) {
                            fSender.sendMessage(("[AuthMe] " + arguments[1]) + " is a single account player");
                            return;
                        }
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
                        fSender.sendMessage(((("[AuthMe] " + arguments[1]) + " has ") + java.lang.String.valueOf(accountList.size())) + " accounts");
                        fSender.sendMessage(message);
                    } else {
                        fSender.sendMessage("[AuthMe] Please put a valid IP");
                        return;
                    }
                }
            });
            return true;
        }
    } else if (args[0].equalsIgnoreCase("register") || args[0].equalsIgnoreCase("reg")) {
        if (args.length != 3) {
            sender.sendMessage("Usage: /authme register playername password");
            return true;
        }
        try {
            java.lang.String name = args[1].toLowerCase();
            if (database.isAuthAvailable(name)) {
                m._(sender, "user_regged");
                return true;
            }
            java.lang.String hash = fr.xephi.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, args[2], name);
            fr.xephi.authme.cache.auth.PlayerAuth auth = new fr.xephi.authme.cache.auth.PlayerAuth(name, hash, "198.18.0.1", 0L, "your@email.com", fr.xephi.authme.api.API.getPlayerRealName(name));
            if (PasswordSecurity.userSalt.containsKey(name) && (PasswordSecurity.userSalt.get(name) != null)) {
                auth.setSalt(PasswordSecurity.userSalt.get(name));
            } else {
                auth.setSalt("");
            }
            if (!database.saveAuth(auth)) {
                m._(sender, "error");
                return true;
            }
            m._(sender, "registered");
            fr.xephi.authme.ConsoleLogger.info(args[1] + " registered");
        } catch (java.security.NoSuchAlgorithmException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
            m._(sender, "error");
        }
        return true;
    } else if (args[0].equalsIgnoreCase("convertflattosql")) {
        try {
            fr.xephi.authme.converter.FlatToSql converter = new fr.xephi.authme.converter.FlatToSql();
            if (sender instanceof org.bukkit.entity.Player) {
                if (converter.convert()) {
                    sender.sendMessage("[AuthMe] FlatFile converted to authme.sql file");
                } else {
                    sender.sendMessage("[AuthMe] Error while converting to authme.sql");
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (java.lang.NullPointerException ex) {
            java.lang.System.out.println(ex.getMessage());
        }
    } else if (args[0].equalsIgnoreCase("flattosqlite")) {
        try {
            java.lang.String s = fr.xephi.authme.converter.FlatToSqlite.convert();
            if (sender instanceof org.bukkit.entity.Player) {
                sender.sendMessage(s);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (java.lang.NullPointerException ex) {
            java.lang.System.out.println(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("xauthimport")) {
        try {
            java.lang.Class.forName("com.cypherx.xauth.xAuth");
            fr.xephi.authme.converter.oldxAuthToFlat converter = new fr.xephi.authme.converter.oldxAuthToFlat(plugin, database, sender);
            converter.run();
        } catch (java.lang.ClassNotFoundException e) {
            try {
                java.lang.Class.forName("de.luricos.bukkit.xAuth.xAuth");
                fr.xephi.authme.converter.newxAuthToFlat converter = new fr.xephi.authme.converter.newxAuthToFlat(plugin, database, sender);
                converter.run();
            } catch (java.lang.ClassNotFoundException ce) {
                sender.sendMessage("[AuthMe] No version of xAuth found or xAuth isn't enable! ");
            }
        }
        return true;
    } else if (args[0].equalsIgnoreCase("getemail")) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /authme getemail playername");
            return true;
        }
        java.lang.String playername = args[1].toLowerCase();
        fr.xephi.authme.cache.auth.PlayerAuth getAuth = database.getAuth(playername);
        if (getAuth == null) {
            m._(sender, "unknown_user");
            return true;
        }
        sender.sendMessage((("[AuthMe] " + args[1]) + " email : ") + getAuth.getEmail());
        return true;
    } else if (args[0].equalsIgnoreCase("chgemail")) {
        if (args.length != 3) {
            sender.sendMessage("Usage: /authme chgemail playername email");
            return true;
        }
        java.lang.String playername = args[1].toLowerCase();
        fr.xephi.authme.cache.auth.PlayerAuth getAuth = database.getAuth(playername);
        if (getAuth == null) {
            m._(sender, "unknown_user");
            return true;
        }
        getAuth.setEmail(args[2]);
        if (!database.updateEmail(getAuth)) {
            m._(sender, "error");
            return true;
        }
        if (fr.xephi.authme.cache.auth.PlayerCache.getInstance().getAuth(playername) != null) {
            fr.xephi.authme.cache.auth.PlayerCache.getInstance().updatePlayer(getAuth);
        }
        return true;
    } else if (args[0].equalsIgnoreCase("convertfromrakamak")) {
        try {
            fr.xephi.authme.converter.RakamakConverter.RakamakConvert();
            if (sender instanceof org.bukkit.entity.Player) {
                sender.sendMessage("[AuthMe] Rakamak database converted to auths.db");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (java.lang.NullPointerException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("setspawn")) {
        try {
            if (sender instanceof org.bukkit.entity.Player) {
                if (fr.xephi.authme.settings.Spawn.getInstance().setSpawn(((org.bukkit.entity.Player) (sender)).getLocation())) {
                    sender.sendMessage("[AuthMe] Correctly define new spawn");
                } else {
                    sender.sendMessage("[AuthMe] SetSpawn fail , please retry");
                }
            } else {
                sender.sendMessage("[AuthMe] Please use that command in game");
            }
        } catch (java.lang.NullPointerException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("setfirstspawn")) {
        try {
            if (sender instanceof org.bukkit.entity.Player) {
                if (fr.xephi.authme.settings.Spawn.getInstance().setFirstSpawn(((org.bukkit.entity.Player) (sender)).getLocation())) {
                    sender.sendMessage("[AuthMe] Correctly define new first spawn");
                } else {
                    sender.sendMessage("[AuthMe] SetFirstSpawn fail , please retry");
                }
            } else {
                sender.sendMessage("[AuthMe] Please use that command in game");
            }
        } catch (java.lang.NullPointerException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("purgebannedplayers")) {
        java.util.List<java.lang.String> bannedPlayers = new java.util.ArrayList<java.lang.String>();
        for (org.bukkit.OfflinePlayer off : plugin.getServer().getBannedPlayers()) {
            bannedPlayers.add(off.getName().toLowerCase());
        }
        final java.util.List<java.lang.String> bP = bannedPlayers;
        if (database instanceof java.lang.Thread) {
            database.purgeBanned(bP);
        } else {
            org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(plugin, new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    database.purgeBanned(bP);
                }
            });
        }
        if (fr.xephi.authme.settings.Settings.purgeEssentialsFile && (plugin.ess != null)) {
            plugin.purgeEssentials(bannedPlayers);
        }
        if (fr.xephi.authme.settings.Settings.purgePlayerDat) {
            plugin.purgeDat(bannedPlayers);
        }
        if (fr.xephi.authme.settings.Settings.purgeLimitedCreative) {
            plugin.purgeLimitedCreative(bannedPlayers);
        }
        if (fr.xephi.authme.settings.Settings.purgeAntiXray) {
            plugin.purgeAntiXray(bannedPlayers);
        }
        return true;
    } else if (args[0].equalsIgnoreCase("spawn")) {
        try {
            if (sender instanceof org.bukkit.entity.Player) {
                if (fr.xephi.authme.settings.Spawn.getInstance().getSpawn() != null) {
                    ((org.bukkit.entity.Player) (sender)).teleport(fr.xephi.authme.settings.Spawn.getInstance().getSpawn());
                } else {
                    sender.sendMessage("[AuthMe] Spawn fail , please try to define the spawn");
                }
            } else {
                sender.sendMessage("[AuthMe] Please use that command in game");
            }
        } catch (java.lang.NullPointerException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("firstspawn")) {
        try {
            if (sender instanceof org.bukkit.entity.Player) {
                if (fr.xephi.authme.settings.Spawn.getInstance().getFirstSpawn() != null) {
                    ((org.bukkit.entity.Player) (sender)).teleport(fr.xephi.authme.settings.Spawn.getInstance().getFirstSpawn());
                } else {
                    sender.sendMessage("[AuthMe] Spawn fail , please try to define the first spawn");
                }
            } else {
                sender.sendMessage("[AuthMe] Please use that command in game");
            }
        } catch (java.lang.NullPointerException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("changepassword") || args[0].equalsIgnoreCase("cp")) {
        if (args.length != 3) {
            sender.sendMessage("Usage: /authme changepassword playername newpassword");
            return true;
        }
        try {
            java.lang.String name = args[1].toLowerCase();
            java.lang.String hash = fr.xephi.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, args[2], name);
            fr.xephi.authme.cache.auth.PlayerAuth auth = null;
            if (fr.xephi.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
                auth = fr.xephi.authme.cache.auth.PlayerCache.getInstance().getAuth(name);
            } else if (database.isAuthAvailable(name)) {
                auth = database.getAuth(name);
            }
            if (auth == null) {
                m._(sender, "unknown_user");
                return true;
            }
            auth.setHash(hash);
            if (PasswordSecurity.userSalt.containsKey(name)) {
                auth.setSalt(PasswordSecurity.userSalt.get(name));
                database.updateSalt(auth);
            }
            if (!database.updatePassword(auth)) {
                m._(sender, "error");
                return true;
            }
            sender.sendMessage("pwd_changed");
            fr.xephi.authme.ConsoleLogger.info(args[1] + "'s password changed");
        } catch (java.security.NoSuchAlgorithmException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
            m._(sender, "error");
        }
        return true;
    } else if ((args[0].equalsIgnoreCase("unregister") || args[0].equalsIgnoreCase("unreg")) || args[0].equalsIgnoreCase("del")) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /authme unregister playername");
            return true;
        }
        java.lang.String name = args[1].toLowerCase();
        if (!database.removeAuth(name)) {
            m._(sender, "error");
            return true;
        }
        org.bukkit.entity.Player target = org.bukkit.Bukkit.getPlayer(name);
        fr.xephi.authme.cache.auth.PlayerCache.getInstance().removePlayer(name);
        fr.xephi.authme.Utils.getInstance().setGroup(name, groupType.UNREGISTERED);
        if (target != null) {
            if (target.isOnline()) {
                if (fr.xephi.authme.settings.Settings.isTeleportToSpawnEnabled) {
                    org.bukkit.Location spawn = plugin.getSpawnLocation(target);
                    fr.xephi.authme.events.SpawnTeleportEvent tpEvent = new fr.xephi.authme.events.SpawnTeleportEvent(target, target.getLocation(), spawn, false);
                    plugin.getServer().getPluginManager().callEvent(tpEvent);
                    if (!tpEvent.isCancelled()) {
                        target.teleport(tpEvent.getTo());
                    }
                }
                fr.xephi.authme.cache.limbo.LimboCache.getInstance().addLimboPlayer(target);
                int delay = fr.xephi.authme.settings.Settings.getRegistrationTimeout * 20;
                int interval = fr.xephi.authme.settings.Settings.getWarnMessageInterval;
                org.bukkit.scheduler.BukkitScheduler sched = sender.getServer().getScheduler();
                if (delay != 0) {
                    int id = sched.scheduleSyncDelayedTask(plugin, new fr.xephi.authme.task.TimeoutTask(plugin, name), delay);
                    fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setTimeoutTaskId(id);
                }
                fr.xephi.authme.cache.limbo.LimboCache.getInstance().getLimboPlayer(name).setMessageTaskId(sched.scheduleSyncDelayedTask(plugin, new fr.xephi.authme.task.MessageTask(plugin, name, m._("reg_msg"), interval)));
                m._(target, "unregistered");
            } else {
                // Player isn't online, do nothing else
            }
        } else {
            // Player does not exist, do nothing else
        }
        m._(sender, "unregistered");
        fr.xephi.authme.ConsoleLogger.info(args[1] + " unregistered");
        return true;
    } else if (args[0].equalsIgnoreCase("purgelastpos")) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /authme purgelastpos playername");
            return true;
        }
        try {
            java.lang.String name = args[1].toLowerCase();
            fr.xephi.authme.cache.auth.PlayerAuth auth = database.getAuth(name);
            if (auth == null) {
                sender.sendMessage(("The player " + name) + " is not registered ");
                return true;
            }
            auth.setQuitLocX(0);
            auth.setQuitLocY(0);
            auth.setQuitLocZ(0);
            auth.setWorld("world");
            database.updateQuitLoc(auth);
            sender.sendMessage(name + " 's last pos location is now reset");
        } catch (java.lang.Exception e) {
            fr.xephi.authme.ConsoleLogger.showError("An error occured while trying to reset location or player do not exist, please see below: ");
            fr.xephi.authme.ConsoleLogger.showError(e.getMessage());
            if (sender instanceof org.bukkit.entity.Player) {
                sender.sendMessage("An error occured while trying to reset location or player do not exist, please see logs");
            }
        }
        return true;
    } else if (args[0].equalsIgnoreCase("switchantibot")) {
        if (args.length != 2) {
            sender.sendMessage("Usage : /authme switchantibot on/off");
            return true;
        }
        if (args[1].equalsIgnoreCase("on")) {
            plugin.switchAntiBotMod(true);
            sender.sendMessage("[AuthMe] AntiBotMod enabled");
            return true;
        }
        if (args[1].equalsIgnoreCase("off")) {
            plugin.switchAntiBotMod(false);
            sender.sendMessage("[AuthMe] AntiBotMod disabled");
            return true;
        }
        sender.sendMessage("Usage : /authme switchantibot on/off");
        return true;
    } else if (args[0].equalsIgnoreCase("royalauth")) {
        new fr.xephi.authme.converter.RoyalAuthConverter(plugin);
        sender.sendMessage("[AuthMe] RoyalAuth database has been imported correctly");
        return true;
    } else if (args[0].equalsIgnoreCase("getip")) {
        if (args.length < 2) {
            sender.sendMessage("Usage : /authme getip onlinePlayerName");
            return true;
        }
        java.lang.String _CVAR0 = args[1];
        org.bukkit.OfflinePlayer _CVAR1 = org.bukkit.Bukkit.getOfflinePlayer(_CVAR0);
        boolean _CVAR2 = _CVAR1.isOnline();
        if () {
            org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayer(args[1]);
            sender.sendMessage((((player.getName() + " actual ip is : ") + player.getAddress().getAddress().getHostAddress()) + ":") + player.getAddress().getPort());
            sender.sendMessage((player.getName() + " real ip is : ") + plugin.getIP(player));
            return true;
        } else {
            sender.sendMessage("This player is not actually online");
            sender.sendMessage("Usage : /authme getip onlinePlayerName");
            return true;
        }
    } else {
        sender.sendMessage("Usage: /authme reload|register playername password|changepassword playername password|unregister playername");
    }
    return true;
}