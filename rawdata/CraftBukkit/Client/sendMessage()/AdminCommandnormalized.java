@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (args.length == 0) {
        org.bukkit.command.CommandSender _CVAR0 = sender;
        java.lang.String _CVAR1 = "Usage: /authme reload - Reload the config";
        _CVAR0.sendMessage(_CVAR1);
        org.bukkit.command.CommandSender _CVAR2 = sender;
        java.lang.String _CVAR3 = "/authme register <playername> <password> - Register a player";
        _CVAR2.sendMessage(_CVAR3);
        org.bukkit.command.CommandSender _CVAR4 = sender;
        java.lang.String _CVAR5 = "/authme changepassword <playername> <password> - Change player password";
        _CVAR4.sendMessage(_CVAR5);
        org.bukkit.command.CommandSender _CVAR6 = sender;
        java.lang.String _CVAR7 = "/authme unregister <playername> - Unregister a player";
        _CVAR6.sendMessage(_CVAR7);
        org.bukkit.command.CommandSender _CVAR8 = sender;
        java.lang.String _CVAR9 = "/authme purge <days> - Purge Database";
        _CVAR8.sendMessage(_CVAR9);
        org.bukkit.command.CommandSender _CVAR10 = sender;
        java.lang.String _CVAR11 = "/authme version - Get AuthMe version infos";
        _CVAR10.sendMessage(_CVAR11);
        org.bukkit.command.CommandSender _CVAR12 = sender;
        java.lang.String _CVAR13 = "/authme lastlogin <playername> - Display Date about the Player's LastLogin";
        _CVAR12.sendMessage(_CVAR13);
        org.bukkit.command.CommandSender _CVAR14 = sender;
        java.lang.String _CVAR15 = "/authme accounts <playername> - Display all player's accounts";
        _CVAR14.sendMessage(_CVAR15);
        org.bukkit.command.CommandSender _CVAR16 = sender;
        java.lang.String _CVAR17 = "/authme setSpawn - Set AuthMe spawn to your current pos";
        _CVAR16.sendMessage(_CVAR17);
        org.bukkit.command.CommandSender _CVAR18 = sender;
        java.lang.String _CVAR19 = "/authme spawn - Teleport you to the AuthMe SpawnPoint";
        _CVAR18.sendMessage(_CVAR19);
        org.bukkit.command.CommandSender _CVAR20 = sender;
        java.lang.String _CVAR21 = "/authme chgemail <playername> <email> - Change player email";
        _CVAR20.sendMessage(_CVAR21);
        org.bukkit.command.CommandSender _CVAR22 = sender;
        java.lang.String _CVAR23 = "/authme getemail <playername> - Get player email";
        _CVAR22.sendMessage(_CVAR23);
        org.bukkit.command.CommandSender _CVAR24 = sender;
        java.lang.String _CVAR25 = "/authme purgelastpos <playername> - Purge last position for a player";
        _CVAR24.sendMessage(_CVAR25);
        org.bukkit.command.CommandSender _CVAR26 = sender;
        java.lang.String _CVAR27 = "/authme switchantibot on/off - Enable/Disable antibot method";
        _CVAR26.sendMessage(_CVAR27);
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
    if (!plugin.authmePermissible(sender, "authme.admin." + args[0].toLowerCase())) {
        fr.xephi.authme.settings.Messages _CVAR29 = m;
        java.lang.String _CVAR30 = "no_perm";
        org.bukkit.command.CommandSender _CVAR28 = sender;
         _CVAR31 = _CVAR29._(_CVAR30);
        _CVAR28.sendMessage(_CVAR31);
        return true;
    }
    if (args[0].equalsIgnoreCase("version")) {
         _CVAR33 = fr.xephi.authme.AuthMe.getInstance();
         _CVAR34 = _CVAR33.getDescription();
         _CVAR35 = _CVAR34.getVersion();
        org.bukkit.command.CommandSender _CVAR32 = sender;
         _CVAR36 = "AuthMe Version: " + _CVAR35;
        _CVAR32.sendMessage(_CVAR36);
        return true;
    }
    if (args[0].equalsIgnoreCase("purge")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR37 = sender;
            java.lang.String _CVAR38 = "Usage: /authme purge <DAYS>";
            _CVAR37.sendMessage(_CVAR38);
            return true;
        }
        try {
            long days = java.lang.Long.parseLong(args[1]) * 86400000;
            long until = new java.util.Date().getTime() - days;
            java.util.List<java.lang.String> purged = database.autoPurgeDatabase(until);
            java.lang.String _CVAR40 = " user accounts";
            org.bukkit.command.CommandSender _CVAR39 = sender;
            java.lang.String _CVAR41 = ("Deleted " + purged.size()) + _CVAR40;
            _CVAR39.sendMessage(_CVAR41);
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
            org.bukkit.command.CommandSender _CVAR42 = sender;
            java.lang.String _CVAR43 = "Usage: /authme purge <DAYS>";
            _CVAR42.sendMessage(_CVAR43);
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
        fr.xephi.authme.settings.Messages _CVAR45 = m;
        java.lang.String _CVAR46 = "reload";
        org.bukkit.command.CommandSender _CVAR44 = sender;
         _CVAR47 = _CVAR45._(_CVAR46);
        _CVAR44.sendMessage(_CVAR47);
    } else if (args[0].equalsIgnoreCase("lastlogin")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR48 = sender;
            java.lang.String _CVAR49 = "Usage: /authme lastlogin <playername>";
            _CVAR48.sendMessage(_CVAR49);
            return true;
        }
        try {
            if (database.getAuth(args[1].toLowerCase()) != null) {
                final long diff = java.lang.System.currentTimeMillis() - lastLogin;
                java.lang.String _CVAR52 = args[1];
                fr.xephi.authme.datasource.DataSource _CVAR51 = database;
                java.lang.String _CVAR53 = _CVAR52.toLowerCase();
                fr.xephi.authme.cache.auth.PlayerAuth player = _CVAR51.getAuth(_CVAR53);
                fr.xephi.authme.cache.auth.PlayerAuth _CVAR54 = player;
                long lastLogin = _CVAR54.getLastLogin();
                long _CVAR55 = lastLogin;
                java.util.Date d = new java.util.Date(_CVAR55);
                java.util.Date _CVAR56 = d;
                java.lang.String _CVAR57 = _CVAR56.toString();
                org.bukkit.command.CommandSender _CVAR50 = sender;
                java.lang.String _CVAR58 = (("[AuthMe] " + args[1].toLowerCase()) + " lastlogin : ") + _CVAR57;
                _CVAR50.sendMessage(_CVAR58);
                java.lang.String _CVAR60 = " secs.";
                final java.lang.String msg = ((((((((int) (diff / 86400000)) + " days ") + ((int) ((diff / 3600000) % 24))) + " hours ") + ((int) ((diff / 60000) % 60))) + " mins ") + ((int) ((diff / 1000) % 60))) + _CVAR60;
                java.lang.String _CVAR61 = msg;
                org.bukkit.command.CommandSender _CVAR59 = sender;
                 _CVAR62 = (("[AuthMe] The player : " + player.getNickname()) + " is unlogged since ") + _CVAR61;
                _CVAR59.sendMessage(_CVAR62);
                fr.xephi.authme.cache.auth.PlayerAuth _CVAR64 = player;
                java.lang.String lastIP = _CVAR64.getIp();
                java.lang.String _CVAR65 = lastIP;
                org.bukkit.command.CommandSender _CVAR63 = sender;
                java.lang.String _CVAR66 = "[AuthMe] LastPlayer IP : " + _CVAR65;
                _CVAR63.sendMessage(_CVAR66);
            } else {
                org.bukkit.command.CommandSender _CVAR67 = sender;
                java.lang.String _CVAR68 = "This player does not exist";
                _CVAR67.sendMessage(_CVAR68);
                return true;
            }
        } catch (java.lang.NullPointerException e) {
            org.bukkit.command.CommandSender _CVAR69 = sender;
            java.lang.String _CVAR70 = "This player does not exist";
            _CVAR69.sendMessage(_CVAR70);
            return true;
        }
    } else if (args[0].equalsIgnoreCase("accounts")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR71 = sender;
            java.lang.String _CVAR72 = "Usage: /authme accounts <playername>";
            _CVAR71.sendMessage(_CVAR72);
            org.bukkit.command.CommandSender _CVAR73 = sender;
            java.lang.String _CVAR74 = "Or: /authme accounts <ip>";
            _CVAR73.sendMessage(_CVAR74);
            return true;
        }
        if (!args[1].contains(".")) {
            final org.bukkit.command.CommandSender fSender = sender;
            final java.lang.String[] arguments = args;
            org.bukkit.scheduler.BukkitScheduler _CVAR75 = org.bukkit.Bukkit.getScheduler();
            fr.xephi.authme.AuthMe _CVAR76 = plugin;
            java.lang.Runnable _CVAR77 = new java.lang.Runnable() {
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
            };
            _CVAR75.scheduleSyncDelayedTask(_CVAR76, _CVAR77);
            return true;
        } else {
            final org.bukkit.command.CommandSender fSender = sender;
            final java.lang.String[] arguments = args;
            org.bukkit.scheduler.BukkitScheduler _CVAR78 = org.bukkit.Bukkit.getScheduler();
            fr.xephi.authme.AuthMe _CVAR79 = plugin;
            java.lang.Runnable _CVAR80 = new java.lang.Runnable() {
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
            };
            _CVAR78.scheduleSyncDelayedTask(_CVAR79, _CVAR80);
            return true;
        }
    } else if (args[0].equalsIgnoreCase("register") || args[0].equalsIgnoreCase("reg")) {
        if (args.length != 3) {
            org.bukkit.command.CommandSender _CVAR81 = sender;
            java.lang.String _CVAR82 = "Usage: /authme register playername password";
            _CVAR81.sendMessage(_CVAR82);
            return true;
        }
        try {
            java.lang.String name = args[1].toLowerCase();
            if (database.isAuthAvailable(name)) {
                fr.xephi.authme.settings.Messages _CVAR84 = m;
                java.lang.String _CVAR85 = "user_regged";
                org.bukkit.command.CommandSender _CVAR83 = sender;
                 _CVAR86 = _CVAR84._(_CVAR85);
                _CVAR83.sendMessage(_CVAR86);
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
                fr.xephi.authme.settings.Messages _CVAR88 = m;
                java.lang.String _CVAR89 = "error";
                org.bukkit.command.CommandSender _CVAR87 = sender;
                 _CVAR90 = _CVAR88._(_CVAR89);
                _CVAR87.sendMessage(_CVAR90);
                return true;
            }
            fr.xephi.authme.settings.Messages _CVAR92 = m;
            java.lang.String _CVAR93 = "registered";
            org.bukkit.command.CommandSender _CVAR91 = sender;
             _CVAR94 = _CVAR92._(_CVAR93);
            _CVAR91.sendMessage(_CVAR94);
            fr.xephi.authme.ConsoleLogger.info(args[1] + " registered");
        } catch (java.security.NoSuchAlgorithmException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
            fr.xephi.authme.settings.Messages _CVAR96 = m;
            java.lang.String _CVAR97 = "error";
            org.bukkit.command.CommandSender _CVAR95 = sender;
             _CVAR98 = _CVAR96._(_CVAR97);
            _CVAR95.sendMessage(_CVAR98);
        }
        return true;
    } else if (args[0].equalsIgnoreCase("convertflattosql")) {
        try {
            fr.xephi.authme.converter.FlatToSql.FlatToSqlConverter();
            if (sender instanceof org.bukkit.entity.Player) {
                org.bukkit.command.CommandSender _CVAR99 = sender;
                java.lang.String _CVAR100 = "[AuthMe] FlatFile converted to authme.sql file";
                _CVAR99.sendMessage(_CVAR100);
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
                org.bukkit.command.CommandSender _CVAR101 = sender;
                java.lang.String _CVAR102 = s;
                _CVAR101.sendMessage(_CVAR102);
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
                org.bukkit.command.CommandSender _CVAR103 = sender;
                java.lang.String _CVAR104 = "[AuthMe] No version of xAuth found or xAuth isn't enable! ";
                _CVAR103.sendMessage(_CVAR104);
            }
        }
        return true;
    } else if (args[0].equalsIgnoreCase("getemail")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR105 = sender;
            java.lang.String _CVAR106 = "Usage: /authme getemail playername";
            _CVAR105.sendMessage(_CVAR106);
            return true;
        }
        if (getAuth == null) {
            org.bukkit.command.CommandSender _CVAR107 = sender;
            java.lang.String _CVAR108 = "This player does not exist";
            _CVAR107.sendMessage(_CVAR108);
            return true;
        }
        java.lang.String _CVAR111 = args[1];
        java.lang.String playername = _CVAR111.toLowerCase();
        fr.xephi.authme.datasource.DataSource _CVAR110 = database;
        java.lang.String _CVAR112 = playername;
        fr.xephi.authme.cache.auth.PlayerAuth getAuth = _CVAR110.getAuth(_CVAR112);
        fr.xephi.authme.cache.auth.PlayerAuth _CVAR113 = getAuth;
         _CVAR114 = _CVAR113.getEmail();
        org.bukkit.command.CommandSender _CVAR109 = sender;
         _CVAR115 = (("[AuthMe] " + args[1]) + " email : ") + _CVAR114;
        _CVAR109.sendMessage(_CVAR115);
        return true;
    } else if (args[0].equalsIgnoreCase("chgemail")) {
        if (args.length != 3) {
            org.bukkit.command.CommandSender _CVAR116 = sender;
            java.lang.String _CVAR117 = "Usage: /authme chgemail playername email";
            _CVAR116.sendMessage(_CVAR117);
            return true;
        }
        java.lang.String playername = args[1].toLowerCase();
        fr.xephi.authme.cache.auth.PlayerAuth getAuth = database.getAuth(playername);
        if (getAuth == null) {
            org.bukkit.command.CommandSender _CVAR118 = sender;
            java.lang.String _CVAR119 = "This player does not exist";
            _CVAR118.sendMessage(_CVAR119);
            return true;
        }
        getAuth.setEmail(args[2]);
        if (!database.updateEmail(getAuth)) {
            fr.xephi.authme.settings.Messages _CVAR121 = m;
            java.lang.String _CVAR122 = "error";
            org.bukkit.command.CommandSender _CVAR120 = sender;
             _CVAR123 = _CVAR121._(_CVAR122);
            _CVAR120.sendMessage(_CVAR123);
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
                org.bukkit.command.CommandSender _CVAR124 = sender;
                java.lang.String _CVAR125 = "[AuthMe] Rakamak database converted to auths.db";
                _CVAR124.sendMessage(_CVAR125);
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
                    org.bukkit.command.CommandSender _CVAR126 = sender;
                    java.lang.String _CVAR127 = "[AuthMe] Correctly define new spawn";
                    _CVAR126.sendMessage(_CVAR127);
                } else {
                    org.bukkit.command.CommandSender _CVAR128 = sender;
                    java.lang.String _CVAR129 = "[AuthMe] SetSpawn fail , please retry";
                    _CVAR128.sendMessage(_CVAR129);
                }
            } else {
                org.bukkit.command.CommandSender _CVAR130 = sender;
                java.lang.String _CVAR131 = "[AuthMe] Please use that command in game";
                _CVAR130.sendMessage(_CVAR131);
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
                if (fr.xephi.authme.settings.Spawn.getInstance().getLocation() != null) {
                    ((org.bukkit.entity.Player) (sender)).teleport(fr.xephi.authme.settings.Spawn.getInstance().getLocation());
                } else {
                    org.bukkit.command.CommandSender _CVAR132 = sender;
                    java.lang.String _CVAR133 = "[AuthMe] Spawn fail , please try to define the spawn";
                    _CVAR132.sendMessage(_CVAR133);
                }
            } else {
                org.bukkit.command.CommandSender _CVAR134 = sender;
                java.lang.String _CVAR135 = "[AuthMe] Please use that command in game";
                _CVAR134.sendMessage(_CVAR135);
            }
        } catch (java.lang.NullPointerException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
        }
        return true;
    } else if (args[0].equalsIgnoreCase("changepassword") || args[0].equalsIgnoreCase("cp")) {
        if (args.length != 3) {
            org.bukkit.command.CommandSender _CVAR136 = sender;
            java.lang.String _CVAR137 = "Usage: /authme changepassword playername newpassword";
            _CVAR136.sendMessage(_CVAR137);
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
            } else {
                fr.xephi.authme.settings.Messages _CVAR139 = m;
                java.lang.String _CVAR140 = "unknown_user";
                org.bukkit.command.CommandSender _CVAR138 = sender;
                 _CVAR141 = _CVAR139._(_CVAR140);
                _CVAR138.sendMessage(_CVAR141);
                return true;
            }
            auth.setHash(hash);
            auth.setSalt(PasswordSecurity.userSalt.get(name));
            if (!database.updatePassword(auth)) {
                fr.xephi.authme.settings.Messages _CVAR143 = m;
                java.lang.String _CVAR144 = "error";
                org.bukkit.command.CommandSender _CVAR142 = sender;
                 _CVAR145 = _CVAR143._(_CVAR144);
                _CVAR142.sendMessage(_CVAR145);
                return true;
            }
            database.updateSalt(auth);
            org.bukkit.command.CommandSender _CVAR146 = sender;
            java.lang.String _CVAR147 = "pwd_changed";
            _CVAR146.sendMessage(_CVAR147);
            fr.xephi.authme.ConsoleLogger.info(args[1] + "'s password changed");
        } catch (java.security.NoSuchAlgorithmException ex) {
            fr.xephi.authme.ConsoleLogger.showError(ex.getMessage());
            fr.xephi.authme.settings.Messages _CVAR149 = m;
            java.lang.String _CVAR150 = "error";
            org.bukkit.command.CommandSender _CVAR148 = sender;
             _CVAR151 = _CVAR149._(_CVAR150);
            _CVAR148.sendMessage(_CVAR151);
        }
        return true;
    } else if ((args[0].equalsIgnoreCase("unregister") || args[0].equalsIgnoreCase("unreg")) || args[0].equalsIgnoreCase("del")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR152 = sender;
            java.lang.String _CVAR153 = "Usage: /authme unregister playername";
            _CVAR152.sendMessage(_CVAR153);
            return true;
        }
        java.lang.String name = args[1].toLowerCase();
        if (!database.removeAuth(name)) {
            fr.xephi.authme.settings.Messages _CVAR155 = m;
            java.lang.String _CVAR156 = "error";
            org.bukkit.command.CommandSender _CVAR154 = sender;
             _CVAR157 = _CVAR155._(_CVAR156);
            _CVAR154.sendMessage(_CVAR157);
            return true;
        }
        fr.xephi.authme.cache.auth.PlayerCache.getInstance().removePlayer(name);
        org.bukkit.command.CommandSender _CVAR158 = sender;
        java.lang.String _CVAR159 = "unregistered";
        _CVAR158.sendMessage(_CVAR159);
        fr.xephi.authme.ConsoleLogger.info(args[1] + " unregistered");
        return true;
    } else if (args[0].equalsIgnoreCase("purgelastpos")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR160 = sender;
            java.lang.String _CVAR161 = "Usage: /authme purgelastpos playername";
            _CVAR160.sendMessage(_CVAR161);
            return true;
        }
        try {
            java.lang.String name = args[1].toLowerCase();
            fr.xephi.authme.cache.auth.PlayerAuth auth = database.getAuth(name);
            if (auth == null) {
                java.lang.String _CVAR163 = " is not registered ";
                org.bukkit.command.CommandSender _CVAR162 = sender;
                java.lang.String _CVAR164 = ("The player " + name) + _CVAR163;
                _CVAR162.sendMessage(_CVAR164);
                return true;
            }
            auth.setQuitLocX(0);
            auth.setQuitLocY(0);
            auth.setQuitLocZ(0);
            auth.setWorld("world");
            database.updateQuitLoc(auth);
            java.lang.String _CVAR166 = " 's last pos location is now reset";
            org.bukkit.command.CommandSender _CVAR165 = sender;
            java.lang.String _CVAR167 = name + _CVAR166;
            _CVAR165.sendMessage(_CVAR167);
        } catch (java.lang.Exception e) {
            fr.xephi.authme.ConsoleLogger.showError("An error occured while trying to reset location or player do not exist, please see below: ");
            fr.xephi.authme.ConsoleLogger.showError(e.getMessage());
            if (sender instanceof org.bukkit.entity.Player) {
                org.bukkit.command.CommandSender _CVAR168 = sender;
                java.lang.String _CVAR169 = "An error occured while trying to reset location or player do not exist, please see logs";
                _CVAR168.sendMessage(_CVAR169);
            }
        }
        return true;
    } else if (args[0].equalsIgnoreCase("switchantibot")) {
        if (args.length != 2) {
            org.bukkit.command.CommandSender _CVAR170 = sender;
            java.lang.String _CVAR171 = "Usage : /authme switchantibot on/off";
            _CVAR170.sendMessage(_CVAR171);
            return true;
        }
        if (args[1].equalsIgnoreCase("on")) {
            plugin.switchAntiBotMod(true);
            org.bukkit.command.CommandSender _CVAR172 = sender;
            java.lang.String _CVAR173 = "[AuthMe] AntiBotMod enabled";
            _CVAR172.sendMessage(_CVAR173);
            return true;
        }
        if (args[1].equalsIgnoreCase("off")) {
            plugin.switchAntiBotMod(false);
            org.bukkit.command.CommandSender _CVAR174 = sender;
            java.lang.String _CVAR175 = "[AuthMe] AntiBotMod disabled";
            _CVAR174.sendMessage(_CVAR175);
            return true;
        }
        org.bukkit.command.CommandSender _CVAR176 = sender;
        java.lang.String _CVAR177 = "Usage : /authme switchantibot on/off";
        _CVAR176.sendMessage(_CVAR177);
        return true;
    } else {
        org.bukkit.command.CommandSender _CVAR178 = sender;
        java.lang.String _CVAR179 = "Usage: /authme reload|register playername password|changepassword playername password|unregister playername";
        _CVAR178.sendMessage(_CVAR179);
    }
    return true;
}