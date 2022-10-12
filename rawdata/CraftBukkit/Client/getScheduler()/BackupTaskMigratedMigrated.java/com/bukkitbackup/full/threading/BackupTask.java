package com.bukkitbackup.full.threading;
import com.bukkitbackup.full.BackupFull;
import com.bukkitbackup.full.config.Settings;
import com.bukkitbackup.full.config.Strings;
import com.bukkitbackup.full.ftp.FTPUploader;
import com.bukkitbackup.full.threading.tasks.BackupEverything;
import com.bukkitbackup.full.threading.tasks.BackupPlugins;
import com.bukkitbackup.full.threading.tasks.BackupWorlds;
import com.bukkitbackup.full.utils.FileUtils;
import com.bukkitbackup.full.utils.LogUtils;
import static com.bukkitbackup.full.utils.FileUtils.FILE_SEPARATOR;
/**
 * This is the main backup thread. It handles backing everything up.
 *
 * @author Domenic Horner
 */
public class BackupTask implements java.lang.Runnable {
    private org.bukkit.plugin.Plugin plugin;

    private org.bukkit.Server pluginServer;

    private com.bukkitbackup.full.config.Settings settings;

    private com.bukkitbackup.full.config.Strings strings;

    private boolean backupEverything;

    private boolean splitBackup;

    private boolean shouldZIP;

    private boolean useTemp;

    private java.lang.String dateFormat;

    private final java.lang.String worldContainer;

    private java.lang.String backupPath;

    private java.lang.String tempDestination;

    private java.lang.String thisBackupName;

    // Threads.
    private com.bukkitbackup.full.threading.tasks.BackupWorlds worldBackupTask;

    private final com.bukkitbackup.full.threading.tasks.BackupPlugins pluginBackupTask;

    private final com.bukkitbackup.full.threading.tasks.BackupEverything everythingBackupTask;

    private com.bukkitbackup.full.threading.SyncSaveAll syncSaveAllUtil;

    public BackupTask(org.bukkit.plugin.Plugin plugin, com.bukkitbackup.full.config.Settings settings, com.bukkitbackup.full.config.Strings strings) {
        // Retrieve parameters.
        this.plugin = plugin;
        this.pluginServer = plugin.getServer();
        this.settings = settings;
        this.strings = strings;
        // The worlds container, if any.
        worldContainer = pluginServer.getWorldContainer().getName();
        // Load settings.
        backupPath = settings.getStringProperty("backuppath", "backups");
        backupEverything = settings.getBooleanProperty("backupeverything", false);
        splitBackup = settings.getBooleanProperty("splitbackup", false);
        shouldZIP = settings.getBooleanProperty("zipbackup", true);
        useTemp = settings.getBooleanProperty("usetemp", true);
        dateFormat = settings.getStringProperty("dateformat", "%1$tY-%1$tm-%1$td-%1$tH-%1$tM-%1$tS");
        // Import backup tasks.
        everythingBackupTask = com.bukkitbackup.full.BackupFull.backupEverything;
        worldBackupTask = com.bukkitbackup.full.BackupFull.backupWorlds;
        pluginBackupTask = com.bukkitbackup.full.BackupFull.backupPlugins;
        // Generate the worldStore.
        if (useTemp) {
            java.lang.String tempFolder = settings.getStringProperty("tempfoldername", "");
            if (!tempFolder.equals("")) {
                // Absolute.
                tempDestination = tempFolder.concat(FileUtils.FILE_SEPARATOR);
            } else {
                // Relative.
                tempDestination = backupPath.concat(FileUtils.FILE_SEPARATOR).concat("temp").concat(FileUtils.FILE_SEPARATOR);
            }
        } else {
            // No temp folder.
            tempDestination = backupPath.concat(FileUtils.FILE_SEPARATOR);
        }
    }

    @java.lang.Override
    public void run() {
        // Get this instances folder name, set variables.
        thisBackupName = getBackupName();
        // Check if backupeverything enabled.
        if (backupEverything) {
            // Start the BackupEverything class.
            try {
                everythingBackupTask.doEverything(thisBackupName);
            } catch (java.lang.Exception e) {
                com.bukkitbackup.full.utils.LogUtils.exceptionLog(e, "Failed to backup worlds: Exception in BackupWorlds.");
            }
        } else {
            // Check if we should be backing up worlds.
            if (settings.getBooleanProperty("backupworlds", true)) {
                // Attempt to backup worlds.
                try {
                    worldBackupTask.doWorlds(thisBackupName);
                } catch (java.lang.Exception e) {
                    com.bukkitbackup.full.utils.LogUtils.exceptionLog(e, "Failed to backup worlds: Exception in BackupWorlds.");
                }
            } else {
                com.bukkitbackup.full.utils.LogUtils.sendLog(strings.getString("skipworlds"));
            }
            // Check if we should be backing up plugins.
            if (settings.getBooleanProperty("backupplugins", true)) {
                // Attempt to backup plugins.
                try {
                    pluginBackupTask.doPlugins(thisBackupName);
                } catch (java.lang.Exception e) {
                    com.bukkitbackup.full.utils.LogUtils.exceptionLog(e, "Failed to backup plugins: Exception in BackupPlugins.");
                }
            } else {
                com.bukkitbackup.full.utils.LogUtils.sendLog(strings.getString("skipplugins"));
            }
            // If this is a non-split backup, we need to ZIP the whole thing.
            if (!splitBackup) {
                com.bukkitbackup.full.utils.FileUtils.doCopyAndZIP(tempDestination.concat(thisBackupName), backupPath.concat(FileUtils.FILE_SEPARATOR).concat(thisBackupName), shouldZIP, useTemp);
            }
        }
        // Perform cleaning on the backup folder.
        try {
            deleteOldBackups();
        } catch (java.lang.Exception e) {
            com.bukkitbackup.full.utils.LogUtils.exceptionLog(e, "Failed to delete old backups.");
        }
        // Perform finalization for this backup.
        finishBackup();
    }

    /**
     * Return a formatted date string, using the option from settings.
     *
     * @return The formatted date, as a string.
     */
    private java.lang.String getBackupName() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.lang.String formattedDate;
        try {
            formattedDate = java.lang.String.format(dateFormat, calendar);
        } catch (java.lang.Exception e) {
            com.bukkitbackup.full.utils.LogUtils.exceptionLog(e, "Exception formatting date.");
            formattedDate = java.lang.String.format("%1$tY-%1$tm-%1$td-%1$tH-%1$tM-%1$tS", calendar);
        }
        return formattedDate;
    }

    /**
     * Check if we need to delete old backups, and perform required operations.
     *
     * @throws Exception
     * 		
     */
    private void deleteOldBackups() throws java.lang.Exception {
        java.io.File backupDir = new java.io.File(backupPath);
        if (splitBackup) {
            // Look inside the folders.
            // Check if we have a different container for worlds.
            if (!worldContainer.equals(".")) {
                // Custom.
                backupDir = new java.io.File(backupPath.concat(FileUtils.FILE_SEPARATOR).concat(worldContainer));
                java.io.File[] worldFoldersToClean = backupDir.listFiles();
                for (int l = 0; l < worldFoldersToClean.length; l++) {
                    // Make sure we are cleaning a directory.
                    if (worldFoldersToClean[l].isDirectory()) {
                        cleanFolder(worldFoldersToClean[l]);
                    }
                }
                backupDir = new java.io.File(backupPath.concat(FileUtils.FILE_SEPARATOR).concat("plugins"));
                java.io.File[] pluginFolderToClean = backupDir.listFiles();
                for (int l = 0; l < pluginFolderToClean.length; l++) {
                    // Make sure we are cleaning a directory.
                    if (pluginFolderToClean[l].isDirectory()) {
                        cleanFolder(pluginFolderToClean[l]);
                    }
                }
            } else {
                java.io.File[] foldersToClean = backupDir.listFiles();
                for (int l = 0; l < foldersToClean.length; l++) {
                    // Make sure we are cleaning a directory.
                    if (foldersToClean[l].isDirectory()) {
                        cleanFolder(foldersToClean[l]);
                    }
                }
            }
        } else {
            // Clean entire directory.
            cleanFolder(backupDir);
        }
    }

    private void cleanFolder(java.io.File folderToClean) throws java.io.IOException {
        try {
            // Get total backup limit.
            int backupLimit = settings.getBackupLimits();
            // List all the files inside this folder.
            java.io.File[] filesList = com.bukkitbackup.full.utils.FileUtils.listFilesInDir(folderToClean);
            // Check we listed the directory.
            if (filesList == null) {
                com.bukkitbackup.full.utils.LogUtils.sendLog(strings.getString("failedlistdir"));
                return;
            }
            // Using size to limit backups.
            if (settings.useMaxSizeBackup) {
                // Get total folder size.
                int totalFolderSize = com.bukkitbackup.full.utils.FileUtils.getTotalFolderSize(folderToClean);
                // If the amount of files exceeds the max backups to keep.
                if (totalFolderSize > backupLimit) {
                    // Create a list for deleted backups.
                    java.util.ArrayList<java.io.File> deletedList = new java.util.ArrayList<java.io.File>(filesList.length);
                    // Inti variables.
                    int maxModifiedIndex;
                    long maxModified;
                    // While the total folder size is bigger than the limit.
                    while (com.bukkitbackup.full.utils.FileUtils.getTotalFolderSize(folderToClean) > backupLimit) {
                        // Create updated list.
                        filesList = com.bukkitbackup.full.utils.FileUtils.listFilesInDir(folderToClean);
                        // List of all the backups.
                        java.util.ArrayList<java.io.File> backupList = new java.util.ArrayList<java.io.File>(filesList.length);
                        backupList.addAll(java.util.Arrays.asList(filesList));
                        // Loop backup list,
                        for (int i = 0; backupList.size() > 1; i++) {
                            maxModifiedIndex = 0;
                            maxModified = backupList.get(0).lastModified();
                            for (int j = 1; j < backupList.size(); ++j) {
                                java.io.File currentFile = backupList.get(j);
                                if (currentFile.lastModified() > maxModified) {
                                    maxModified = currentFile.lastModified();
                                    maxModifiedIndex = j;
                                }
                            }
                            backupList.remove(maxModifiedIndex);
                        }
                        com.bukkitbackup.full.utils.FileUtils.deleteDir(backupList.get(0));
                        deletedList.add(backupList.get(0));
                    } 
                    // Inform the user what backups are being deleted.
                    com.bukkitbackup.full.utils.LogUtils.sendLog(strings.getString("removeoldsize"));
                    com.bukkitbackup.full.utils.LogUtils.sendLog(java.util.Arrays.toString(deletedList.toArray()));
                }
            } else if (filesList.length > backupLimit) {
                java.util.ArrayList<java.io.File> backupList = new java.util.ArrayList<java.io.File>(filesList.length);
                backupList.addAll(java.util.Arrays.asList(filesList));
                int maxModifiedIndex;
                long maxModified;
                // Remove the newst backups from the list.
                for (int i = 0; i < backupLimit; ++i) {
                    maxModifiedIndex = 0;
                    maxModified = backupList.get(0).lastModified();
                    for (int j = 1; j < backupList.size(); ++j) {
                        java.io.File currentFile = backupList.get(j);
                        if (currentFile.lastModified() > maxModified) {
                            maxModified = currentFile.lastModified();
                            maxModifiedIndex = j;
                        }
                    }
                    backupList.remove(maxModifiedIndex);
                }
                // Inform the user what backups are being deleted.
                com.bukkitbackup.full.utils.LogUtils.sendLog(strings.getString("removeoldage"));
                com.bukkitbackup.full.utils.LogUtils.sendLog(java.util.Arrays.toString(backupList.toArray()));
                // Finally delete the backups.
                for (java.io.File backupToDelete : backupList) {
                    com.bukkitbackup.full.utils.FileUtils.deleteDir(backupToDelete);
                }
            }
        } catch (java.lang.SecurityException se) {
            com.bukkitbackup.full.utils.LogUtils.exceptionLog(se, "Failed to clean old backups: Security Exception.");
        }
    }

    /**
     * Creates a temporary Runnable that is running on the main thread by the
     * scheduler to prevent thread problems.
     */
    private void finishBackup() {
        // Do the FTP upload if required.
        if (shouldZIP && (!splitBackup)) {
            doFTPUpload(backupPath.concat(FileUtils.FILE_SEPARATOR).concat(thisBackupName) + ".zip");
        }
        // Create new Runnable instance.
        java.lang.Runnable run = __SmPLUnsupported__(0);
        Utils.getInstance().scheduleSyncDelayedTask(plugin, run);
        PrepareBackup.backupInProgress = false;
    }

    private void doFTPUpload(java.lang.String ZIPFile) {
        // This runs in another thread to ensure it does nto affect server performance.
        if (settings.getBooleanProperty("ftpuploadenable", false)) {
            pluginServer.getScheduler().scheduleAsyncDelayedTask(plugin, new com.bukkitbackup.full.ftp.FTPUploader(settings, strings, ZIPFile));
        }
    }
}