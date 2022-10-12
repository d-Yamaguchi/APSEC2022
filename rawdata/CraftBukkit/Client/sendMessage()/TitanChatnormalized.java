/**
 * Called when a Player uses a command
 *
 * @param sender
 * 		The sender who sent the command
 * @param cmd
 * 		The Command used
 * @param label
 * 		The exact word the Player used
 * @param args
 * 		The list of words that follows
 * @return True if the Command is executed
 */
@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
    com.titankingdoms.nodinchan.titanchat.TitanChat.db.i("onCommand: " + cmd.getName());
    if (cmd.getName().equals("titanchat")) {
        if (args.length < 1) {
            com.titankingdoms.nodinchan.titanchat.TitanChat.db.i("onCommand: No arguments!");
            com.titankingdoms.nodinchan.titanchat.TitanChat _CVAR1 = this;
            org.bukkit.command.CommandSender _CVAR0 = sender;
            java.lang.String _CVAR2 = (org.bukkit.ChatColor.AQUA + "You are running ") + _CVAR1;
            _CVAR0.sendMessage(_CVAR2);
            if (sender instanceof org.bukkit.entity.Player) {
                sendInfo(((org.bukkit.entity.Player) (sender)), "\"/titanchat commands [page]\" for command list");
            } else {
                log(java.util.logging.Level.INFO, "\"/titanchat commands [page]\" for command list");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("update")) {
            updateLib();
            return true;
        }
        if (!(sender instanceof org.bukkit.entity.Player)) {
            if (args[0].equalsIgnoreCase("reload")) {
                log(java.util.logging.Level.INFO, "Reloading configs...");
                reloadConfig();
                manager.getAddonManager().preReload();
                manager.getChannelManager().preReload();
                manager.getCommandManager().preReload();
                variable.unload();
                format.load();
                manager.getAddonManager().postReload();
                manager.getChannelManager().postReload();
                manager.getCommandManager().postReload();
                log(java.util.logging.Level.INFO, "Configs reloaded");
                return true;
            }
            if (args[0].equalsIgnoreCase("broadcast")) {
                if (!getConfig().getBoolean("broadcast.server.enable")) {
                    log(java.util.logging.Level.WARNING, "Command disabled");
                    return true;
                }
                for (java.lang.String word : args) {
                    if (str.length() > 0) {
                        str.append(" ");
                    }
                    str.append(word);
                }
                java.lang.String[] lines = getFormatHandler().regroup(message, str.toString());
                getServer().broadcastMessage(getFormatHandler().colourise(message.replace("%message", lines[0])));
                for (int line = 1; line < lines.length; line++) {
                    getServer().broadcastMessage(lines[line]);
                }
                java.lang.String console = ((("<" + org.bukkit.ChatColor.RED) + "Server") + org.bukkit.ChatColor.RESET) + "> ";
                org.bukkit.Server _CVAR3 = getServer();
                org.bukkit.configuration.file.FileConfiguration _CVAR5 = getConfig();
                java.lang.String _CVAR6 = "broadcast.server.format";
                java.lang.String message = _CVAR5.getString(_CVAR6);
                org.bukkit.configuration.file.FileConfiguration _CVAR9 = _CVAR5;
                java.lang.String _CVAR10 = _CVAR6;
                java.lang.String message = _CVAR9.getString(_CVAR10);
                java.lang.StringBuilder str = new java.lang.StringBuilder();
                java.lang.StringBuilder _CVAR13 = str;
                java.lang.String _CVAR11 = message;
                java.lang.String _CVAR12 = "%message";
                java.lang.String _CVAR14 = _CVAR13.toString();
                java.lang.String _CVAR7 = message;
                java.lang.String _CVAR8 = "%message";
                java.lang.String _CVAR15 = _CVAR11.replace(_CVAR12, _CVAR14);
                java.lang.String _CVAR16 = _CVAR7.replace(_CVAR8, _CVAR15);
                org.bukkit.command.ConsoleCommandSender _CVAR4 = _CVAR3.getConsoleSender();
                java.lang.String _CVAR17 = console + _CVAR16;
                _CVAR4.sendMessage(_CVAR17);
                return true;
            }
            log(java.util.logging.Level.INFO, "Please use commands in-game");
            return true;
        }
        com.titankingdoms.nodinchan.titanchat.TitanChat.db.i("CommandManager executing command:");
        manager.getCommandManager().execute(((org.bukkit.entity.Player) (sender)), args[0], java.util.Arrays.copyOfRange(args, 1, args.length));
        return true;
    }
    if (cmd.getName().equalsIgnoreCase("broadcast")) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            if (!getConfig().getBoolean("broadcast.server.enable")) {
                log(java.util.logging.Level.WARNING, "Command disabled");
                return true;
            }
            for (java.lang.String word : args) {
                if (str.length() > 0) {
                    str.append(" ");
                }
                str.append(word);
            }
            java.lang.String[] lines = getFormatHandler().regroup(message, str.toString());
            getServer().broadcastMessage(getFormatHandler().colourise(message.replace("%message", lines[0])));
            for (int line = 1; line < lines.length; line++) {
                getServer().broadcastMessage(lines[line]);
            }
            java.lang.String console = ((("<" + org.bukkit.ChatColor.RED) + "Server") + org.bukkit.ChatColor.RESET) + "> ";
            org.bukkit.Server _CVAR18 = getServer();
            org.bukkit.configuration.file.FileConfiguration _CVAR20 = getConfig();
            java.lang.String _CVAR21 = "broadcast.server.format";
            java.lang.String message = _CVAR20.getString(_CVAR21);
            org.bukkit.configuration.file.FileConfiguration _CVAR24 = _CVAR20;
            java.lang.String _CVAR25 = _CVAR21;
            java.lang.String message = _CVAR24.getString(_CVAR25);
            java.lang.StringBuilder str = new java.lang.StringBuilder();
            java.lang.StringBuilder _CVAR28 = str;
            java.lang.String _CVAR26 = message;
            java.lang.String _CVAR27 = "%message";
            java.lang.String _CVAR29 = _CVAR28.toString();
            java.lang.String _CVAR22 = message;
            java.lang.String _CVAR23 = "%message";
            java.lang.String _CVAR30 = _CVAR26.replace(_CVAR27, _CVAR29);
            java.lang.String _CVAR31 = _CVAR22.replace(_CVAR23, _CVAR30);
            org.bukkit.command.ConsoleCommandSender _CVAR19 = _CVAR18.getConsoleSender();
            java.lang.String _CVAR32 = console + _CVAR31;
            _CVAR19.sendMessage(_CVAR32);
            return true;
        }
        if (!getConfig().getBoolean("broadcast.player.enable")) {
            sendWarning(((org.bukkit.entity.Player) (sender)), "Command disabled");
            return true;
        }
        if (permBridge.has(((org.bukkit.entity.Player) (sender)), "TitanChat.broadcast")) {
            try {
                manager.getCommandManager().execute(((org.bukkit.entity.Player) (sender)), "broadcast", args);
            } catch (java.lang.Exception e) {
            }
        } else {
            sendWarning(((org.bukkit.entity.Player) (sender)), "You do not have permission");
        }
        return true;
    }
    if (cmd.getName().equalsIgnoreCase("me")) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            if (!getConfig().getBoolean("emote.server.enable")) {
                log(java.util.logging.Level.WARNING, "Command disabled");
                return true;
            }
            java.lang.String message = getConfig().getString("emote.server.format");
            for (java.lang.String word : args) {
                if (str.length() > 0) {
                    str.append(" ");
                }
                str.append(word);
            }
            java.lang.String[] lines = getFormatHandler().regroup(message, str.toString());
            getServer().broadcastMessage(getFormatHandler().colourise(message.replace("%action", lines[0])));
            for (int line = 1; line < lines.length; line++) {
                getServer().broadcastMessage(getFormatHandler().colourise(lines[line]));
            }
            java.lang.String console = (("* " + org.bukkit.ChatColor.RED) + "Server ") + org.bukkit.ChatColor.RESET;
            org.bukkit.Server _CVAR33 = getServer();
            java.lang.StringBuilder str = new java.lang.StringBuilder();
            java.lang.StringBuilder _CVAR35 = str;
            java.lang.String _CVAR36 = _CVAR35.toString();
            org.bukkit.command.ConsoleCommandSender _CVAR34 = _CVAR33.getConsoleSender();
            java.lang.String _CVAR37 = console + _CVAR36;
            _CVAR34.sendMessage(_CVAR37);
            return true;
        }
        if (!getConfig().getBoolean("emote.player.enable")) {
            sendWarning(((org.bukkit.entity.Player) (sender)), "Command disabled");
            return true;
        }
        if (permBridge.has(((org.bukkit.entity.Player) (sender)), "TitanChat.emote.server")) {
            try {
                manager.getCommandManager().execute(((org.bukkit.entity.Player) (sender)), "me", args);
            } catch (java.lang.Exception e) {
            }
        } else {
            sendWarning(((org.bukkit.entity.Player) (sender)), "You do not have permission");
        }
        return true;
    }
    if (cmd.getName().equalsIgnoreCase("whisper")) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            if (!getConfig().getBoolean("whisper.server.enable")) {
                log(java.util.logging.Level.WARNING, "Command disabled");
                return true;
            }
            if (getPlayer(args[0]) == null) {
                log(java.util.logging.Level.WARNING, "Player not online");
                return true;
            }
            for (java.lang.String word : args) {
                if (str.length() > 0) {
                    str.append(" ");
                }
                str.append(word);
            }
            if (args[0].equalsIgnoreCase("console")) {
                log(java.util.logging.Level.INFO, "You whispered to yourself: " + str.toString());
                log(java.util.logging.Level.INFO, message.replace("%message", str.toString()));
                return true;
            }
            org.bukkit.configuration.file.FileConfiguration _CVAR40 = getConfig();
            java.lang.String _CVAR41 = "whisper.server.format";
            org.bukkit.configuration.file.FileConfiguration _CVAR48 = _CVAR40;
            java.lang.String _CVAR49 = _CVAR41;
            java.lang.String message = _CVAR48.getString(_CVAR49);
            java.lang.StringBuilder str = new java.lang.StringBuilder();
            if (getPlayer(args[0]) != null) {
                java.lang.String[] lines = getFormatHandler().regroup(message, str.toString());
                java.lang.String _CVAR38 = args[0];
                java.lang.String _CVAR42 = message;
                java.lang.String _CVAR43 = "%message";
                java.lang.String _CVAR44 = lines[0];
                org.bukkit.entity.Player _CVAR39 = getPlayer(_CVAR38);
                java.lang.String _CVAR45 = _CVAR42.replace(_CVAR43, _CVAR44);
                _CVAR39.sendMessage(_CVAR45);
                getPlayer(args[0]).sendMessage(java.util.Arrays.copyOfRange(lines, 1, lines.length));
                java.lang.String _CVAR46 = args[0];
                java.lang.StringBuilder _CVAR53 = str;
                com.titankingdoms.nodinchan.titanchat.util.FormatHandler _CVAR52 = getFormatHandler();
                java.lang.String _CVAR54 = _CVAR53.toString();
                java.lang.String _CVAR50 = message;
                java.lang.String _CVAR51 = "%message";
                 _CVAR55 = _CVAR52.colourise(_CVAR54);
                org.bukkit.entity.Player _CVAR47 = getPlayer(_CVAR46);
                java.lang.String _CVAR56 = _CVAR50.replace(_CVAR51, _CVAR55);
                _CVAR47.sendMessage(_CVAR56);
                log(java.util.logging.Level.INFO, (("[Server -> " + getPlayer(args[0]).getName()) + "] ") + str.toString());
            } else {
                log(java.util.logging.Level.WARNING, "Player not online");
            }
        }
        if (!getConfig().getBoolean("whisper.player.enable")) {
            sendWarning(((org.bukkit.entity.Player) (sender)), "Command disabled");
            return true;
        }
        if (permBridge.has(((org.bukkit.entity.Player) (sender)), "TitanChat.whisper")) {
            try {
                manager.getCommandManager().execute(((org.bukkit.entity.Player) (sender)), "whisper", args);
            } catch (java.lang.Exception e) {
            }
        } else {
            sendWarning(((org.bukkit.entity.Player) (sender)), "You do not have permission");
        }
        return true;
    }
    return false;
}