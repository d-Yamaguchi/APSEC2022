/* To change this template, choose Tools | Templates
and open the template in the editor.
 */
package com.cnaude.mutemanager;
/**
 *
 *
 * @author cnaude
 */
public class MuteManager extends org.bukkit.plugin.java.JavaPlugin {
    // Mute list is stored as playername and milliseconds
    public java.util.HashMap<java.lang.String, java.lang.Long> mList = new java.util.HashMap<java.lang.String, java.lang.Long>();

    public java.util.HashMap<java.lang.String, java.lang.String> mReason = new java.util.HashMap<java.lang.String, java.lang.String>();

    private final com.cnaude.mutemanager.MMListeners mmListeners = new com.cnaude.mutemanager.MMListeners(this);

    public boolean configLoaded = false;

    public static com.cnaude.mutemanager.MMConfig config;

    public static final java.lang.String PLUGIN_NAME = "MuteManager";

    public static final java.lang.String LOG_HEADER = ("[" + com.cnaude.mutemanager.MuteManager.PLUGIN_NAME) + "]";

    static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("Minecraft");

    private final com.cnaude.mutemanager.MMFile mFile = new com.cnaude.mutemanager.MMFile(this);

    private final java.lang.String muteBroadcastPermNode = "mutemanager.mutenotify";

    private final java.lang.String unMuteBroadcastPermNode = "mutemanager.unmutenotify";

    com.cnaude.mutemanager.MMLoop mmLoop;

    @java.lang.Override
    public void onEnable() {
        loadConfig();
        mFile.loadMuteList();
        mFile.loadMuteReasonList();
        getCommand("mute").setExecutor(new com.cnaude.mutemanager.MMCommandMute(this));
        getCommand("unmute").setExecutor(new com.cnaude.mutemanager.MMCommandUnMute(this));
        getCommand("mutelist").setExecutor(new com.cnaude.mutemanager.MMCommandMuteList(this));
        getServer().getPluginManager().registerEvents(mmListeners, this);
        mmLoop = new com.cnaude.mutemanager.MMLoop(this);
    }

    @java.lang.Override
    public void onDisable() {
        mmLoop.end();
        mFile.saveMuteList();
        mFile.saveMuteReasonList();
        mList.clear();
        mReason.clear();
    }

    void loadConfig() {
        if (!this.configLoaded) {
            getConfig().options().copyDefaults(true);
            saveConfig();
            logInfo("Configuration loaded.");
            com.cnaude.mutemanager.MuteManager.config = new com.cnaude.mutemanager.MMConfig(this);
        } else {
            reloadConfig();
            getConfig().options().copyDefaults(false);
            com.cnaude.mutemanager.MuteManager.config = new com.cnaude.mutemanager.MMConfig(this);
            logInfo("Configuration reloaded.");
        }
        configLoaded = true;
    }

    public void logInfo(java.lang.String _message) {
        com.cnaude.mutemanager.MuteManager.log.log(java.util.logging.Level.INFO, java.lang.String.format("%s %s", com.cnaude.mutemanager.MuteManager.LOG_HEADER, _message));
    }

    public void logError(java.lang.String _message) {
        com.cnaude.mutemanager.MuteManager.log.log(java.util.logging.Level.SEVERE, java.lang.String.format("%s %s", com.cnaude.mutemanager.MuteManager.LOG_HEADER, _message));
    }

    public void logDebug(java.lang.String _message) {
        if (com.cnaude.mutemanager.MuteManager.config.debugEnabled()) {
            com.cnaude.mutemanager.MuteManager.log.log(java.util.logging.Level.INFO, java.lang.String.format("%s [DEBUG] %s", com.cnaude.mutemanager.MuteManager.LOG_HEADER, _message));
        }
    }

    public com.cnaude.mutemanager.MMConfig getMConfig() {
        return com.cnaude.mutemanager.MuteManager.config;
    }

    public void mutePlayer(org.bukkit.entity.Player player, java.lang.Long muteTime, org.bukkit.command.CommandSender sender, java.lang.String reason) {
        long curTime = java.lang.System.currentTimeMillis();
        long expTime = curTime + ((muteTime * 60) * 1000);
        java.lang.String pName = store.loadAchievements(mPlayer);
        mList.put(pName, expTime);
        mReason.put(pName, reason);
        java.lang.String senderMessage = com.cnaude.mutemanager.MuteManager.config.msgPlayerNowMuted().replace("%AUTHOR%", sender.getName()).replace("%PLAYER%", pName).replace("%DURATION%", expireTime(pName));
        if (!reason.isEmpty()) {
            senderMessage = (((((senderMessage + org.bukkit.ChatColor.YELLOW) + ". ") + com.cnaude.mutemanager.MuteManager.config.msgReason()) + ": ") + org.bukkit.ChatColor.RED) + reason;
        }
        if (com.cnaude.mutemanager.MuteManager.config.shouldNotify()) {
            getServer().broadcast(senderMessage, muteBroadcastPermNode);
        } else {
            sender.sendMessage(senderMessage);
            if (!com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().isEmpty()) {
                player.sendMessage(com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().replace("%DURATION%", expireTime(pName)).replace("%REASON%", reason));
            }
        }
    }

    public void mutePlayer(java.lang.String pName, java.lang.Long muteTime, org.bukkit.command.CommandSender sender, java.lang.String reason) {
        org.bukkit.entity.Player player = org.bukkit.Bukkit.getServer().getPlayerExact(pName);
        long curTime = java.lang.System.currentTimeMillis();
        long expTime = curTime + ((muteTime * 60) * 1000);
        mList.put(pName, expTime);
        mReason.put(pName, reason);
        java.lang.String senderMessage = com.cnaude.mutemanager.MuteManager.config.msgPlayerNowMuted().replace("%AUTHOR%", sender.getName()).replace("%PLAYER%", pName).replace("%DURATION%", expireTime(pName));
        if (!reason.isEmpty()) {
            senderMessage = (((((senderMessage + org.bukkit.ChatColor.YELLOW) + ". ") + com.cnaude.mutemanager.MuteManager.config.msgReason()) + ": ") + org.bukkit.ChatColor.RED) + reason;
        }
        if (com.cnaude.mutemanager.MuteManager.config.shouldNotify()) {
            getServer().broadcast(senderMessage, muteBroadcastPermNode);
        } else {
            sender.sendMessage(senderMessage);
            if (!com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().isEmpty()) {
                if (player != null) {
                    player.sendMessage(com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().replace("%DURATION%", expireTime(pName)).replace("%REASON%", reason));
                }
            }
        }
    }

    public void unMutePlayer(java.lang.String pName, org.bukkit.command.CommandSender sender) {
        org.bukkit.entity.Player player = org.bukkit.Bukkit.getServer().getPlayerExact(pName);
        java.lang.String senderMessage = com.cnaude.mutemanager.MuteManager.config.msgSenderUnMuted().replace("%PLAYER%", pName).replace("%AUTHOR%", sender.getName());
        boolean success = unMutePlayer(pName);
        if (success) {
            if (com.cnaude.mutemanager.MuteManager.config.shouldNotify()) {
                getServer().broadcast(senderMessage, unMuteBroadcastPermNode);
            } else {
                logInfo(pName + " has been unmuted!");
                if (!com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().isEmpty()) {
                    if (player != null) {
                        player.sendMessage(com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenUnMuted());
                    }
                }
                sender.sendMessage(senderMessage);
            }
        } else {
            sender.sendMessage(com.cnaude.mutemanager.MuteManager.config.msgUnableToUnMute().replace("%PLAYER%", pName));
        }
    }

    public boolean unMutePlayer(java.lang.String p) {
        logDebug("Unmuting: " + p);
        java.lang.String pName = p;
        for (java.lang.String s : mList.keySet()) {
            if (s.equalsIgnoreCase(pName)) {
                pName = s;
            }
        }
        if (mReason.containsKey(pName)) {
            mReason.remove(pName);
        }
        if (mList.containsKey(pName)) {
            mList.remove(pName);
            return true;
        } else {
            return false;
        }
    }

    public boolean isMuted(org.bukkit.entity.Player player) {
        java.lang.String pName = player.getName();
        if (mList.containsKey(pName)) {
            long curTime = java.lang.System.currentTimeMillis();
            long expTime = mList.get(pName);
            if (expTime > curTime) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public java.lang.String expireTime(org.bukkit.entity.Player player) {
        java.lang.String pName = player.getName();
        return expireTime(pName);
    }

    public boolean isBlockedCmd(java.lang.String cmd) {
        return getMConfig().blockedCmds().contains(cmd);
    }

    public java.lang.String expireTime(java.lang.String pName) {
        java.text.DecimalFormat formatter = new java.text.DecimalFormat("0.00");
        if (mList.containsKey(pName)) {
            long curTime = java.lang.System.currentTimeMillis();
            long expTime = mList.get(pName);
            float diffTime = ((expTime - curTime) / 1000.0F) / 60.0F;
            if (diffTime > 5256000) {
                return com.cnaude.mutemanager.MuteManager.config.msgForever();
            }
            if (diffTime > 525600) {
                return (formatter.format(diffTime / 525600.0F) + " ") + com.cnaude.mutemanager.MuteManager.config.msgYears();
            }
            if (diffTime > 1440) {
                return (formatter.format(diffTime / 1440.0F) + " ") + com.cnaude.mutemanager.MuteManager.config.msgDays();
            }
            if (diffTime > 60) {
                return (formatter.format(diffTime / 60.0F) + " ") + com.cnaude.mutemanager.MuteManager.config.msgHOurs();
            }
            if (diffTime < 1.0F) {
                return (formatter.format(diffTime * 60.0F) + " ") + com.cnaude.mutemanager.MuteManager.config.msgSeconds();
            }
            return (formatter.format(diffTime) + " ") + com.cnaude.mutemanager.MuteManager.config.msgMinutes();
        } else {
            return com.cnaude.mutemanager.MuteManager.config.msgZeroSeconds();
        }
    }

    public org.bukkit.entity.Player lookupPlayer(java.lang.String pName) {
        org.bukkit.entity.Player player;
        if (com.cnaude.mutemanager.MuteManager.config.reqFullName()) {
            player = org.bukkit.Bukkit.getPlayerExact(pName);
        } else {
            // First we attempt to get an exact match before partial match
            player = org.bukkit.Bukkit.getPlayerExact(pName);
            if (player == null) {
                for (org.bukkit.entity.Player pl : org.bukkit.Bukkit.getOnlinePlayers()) {
                    if (pl.getName().toLowerCase().startsWith(pName.toLowerCase())) {
                        player = org.bukkit.Bukkit.getPlayer(pName);
                        break;
                    }
                }
            }
        }
        return player;
    }
}