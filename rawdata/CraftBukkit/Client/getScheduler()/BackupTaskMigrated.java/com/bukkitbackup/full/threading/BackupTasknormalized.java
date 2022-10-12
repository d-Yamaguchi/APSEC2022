/**
 * Creates a temporary Runnable that is running on the main thread by the
 * scheduler to prevent thread problems.
 */
private void finishBackup() {
    // Do the FTP upload if required.
    if (shouldZIP && (!splitBackup)) {
        doFTPUpload(backupPath.concat(FileUtils.FILE_SEPARATOR).concat(thisBackupName) + ".zip");
    }
    org.bukkit.Server _CVAR0 = pluginServer;
    // Create new Runnable instance.
    java.lang.Runnable run = new java.lang.Runnable() {
        @java.lang.Override
        public void run() {
            // Should we enable auto-save again?
            if (settings.getBooleanProperty("enableautosave", true)) {
                syncSaveAllUtil = new com.bukkitbackup.full.threading.SyncSaveAll(pluginServer, 2);
                com.bukkitbackup.full.threading.Calendar.getInstance().scheduleSyncDelayedTask(plugin, syncSaveAllUtil);
            }
            // Delete the temp directory.
            if (useTemp) {
                com.bukkitbackup.full.utils.FileUtils.deleteDir(new java.io.File(tempDestination));
            }
            // Notify that it has completed.
            notifyCompleted();
        }

        private void notifyCompleted() {
            java.lang.String completedBackupMessage = strings.getString("backupfinished");
            // Check there is a message.
            if ((completedBackupMessage != null) && (!completedBackupMessage.trim().isEmpty())) {
                // Check if we are using multiple lines.
                if (completedBackupMessage.contains(";;")) {
                    // Convert to array of lines.
                    java.util.List<java.lang.String> messageList = java.util.Arrays.asList(completedBackupMessage.split(";;"));
                    // Loop the lines of this message.
                    for (int i = 0; i < messageList.size(); i++) {
                        // Retrieve this line of the message.
                        java.lang.String thisMessage = messageList.get(i);
                        // Notify all players, regardless of the permission node.
                        if (settings.getBooleanProperty("notifyallplayers", true)) {
                            pluginServer.broadcastMessage(thisMessage);
                        } else {
                            // Get all players.
                            org.bukkit.entity.Player[] players = pluginServer.getOnlinePlayers();
                            // Loop through all online players.
                            for (int pos = 0; pos < players.length; pos++) {
                                org.bukkit.entity.Player currentplayer = players[pos];
                                // If the current player has the right permissions, notify them.
                                if (currentplayer.hasPermission("backup.notify")) {
                                    currentplayer.sendMessage(thisMessage);
                                }
                            }
                        }
                    }
                } else if (settings.getBooleanProperty("notifyallplayers", true)) {
                    pluginServer.broadcastMessage(completedBackupMessage);
                } else {
                    // Get all players.
                    org.bukkit.entity.Player[] players = pluginServer.getOnlinePlayers();
                    // Loop through all online players.
                    for (int pos = 0; pos < players.length; pos++) {
                        org.bukkit.entity.Player currentplayer = players[pos];
                        // If the current player has the right permissions, notify them.
                        if (currentplayer.hasPermission("backup.notify")) {
                            currentplayer.sendMessage(completedBackupMessage);
                        }
                    }
                }
            }
        }
    };
    org.bukkit.scheduler.BukkitScheduler _CVAR1 = _CVAR0.getScheduler();
    org.bukkit.plugin.Plugin _CVAR2 = plugin;
    java.lang.Runnable _CVAR3 = run;
    _CVAR1.scheduleSyncDelayedTask(_CVAR2, _CVAR3);
    PrepareBackup.backupInProgress = false;
}