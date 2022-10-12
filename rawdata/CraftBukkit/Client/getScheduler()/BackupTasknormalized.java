@java.lang.Override
public void run() {
    // Should we enable auto-save again?
    if (settings.getBooleanProperty("enableautosave", true)) {
        syncSaveAllUtil = new com.bukkitbackup.full.threading.SyncSaveAll(pluginServer, 2);
        org.bukkit.Server _CVAR0 = pluginServer;
        org.bukkit.scheduler.BukkitScheduler _CVAR1 = _CVAR0.getScheduler();
        org.bukkit.plugin.Plugin _CVAR2 = plugin;
        com.bukkitbackup.full.threading.SyncSaveAll _CVAR3 = syncSaveAllUtil;
        _CVAR1.scheduleSyncDelayedTask(_CVAR2, _CVAR3);
    }
    // Delete the temp directory.
    if (useTemp) {
        com.bukkitbackup.full.utils.FileUtils.deleteDir(new java.io.File(tempDestination));
    }
    // Notify that it has completed.
    notifyCompleted();
}