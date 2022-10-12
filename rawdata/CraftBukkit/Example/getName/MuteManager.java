/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cnaude.mutemanager;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author cnaude
 */
public class MuteManager extends JavaPlugin {

    // Mute list is stored as playername and milliseconds
    public ArrayList<MutedPlayer> mList = new ArrayList<>();
    private final MMListeners mmListeners = new MMListeners(this);
    public boolean configLoaded = false;
    public static MMConfig config;
    public static final String PLUGIN_NAME = "MuteManager";
    public static final String LOG_HEADER = "[" + PLUGIN_NAME + "]";
    static final Logger log = Logger.getLogger("Minecraft");
    private final MMFile mFile = new MMFile(this);
    private final String muteBroadcastPermNode = "mutemanager.mutenotify";
    private final String unMuteBroadcastPermNode = "mutemanager.unmutenotify";
    MMLoop mmLoop;

    @Override
    public void onEnable() {
        loadConfig();
        mFile.loadMuteList();
        mFile.loadMuteReasonList();
        getCommand("mute").setExecutor(new MMCommandMute(this));
        getCommand("unmute").setExecutor(new MMCommandUnMute(this));
        getCommand("mutelist").setExecutor(new MMCommandMuteList(this));
        getServer().getPluginManager().registerEvents(mmListeners, this);
        mmLoop = new MMLoop(this);
    }

    @Override
    public void onDisable() {
        mmLoop.end();
        mFile.saveMuteList();
        mList.clear();
    }

    void loadConfig() {
        if (!this.configLoaded) {
            getConfig().options().copyDefaults(true);
            saveConfig();
            logInfo("Configuration loaded.");
            config = new MMConfig(this);
        } else {
            reloadConfig();
            getConfig().options().copyDefaults(false);
            config = new MMConfig(this);
            logInfo("Configuration reloaded.");
        }
        configLoaded = true;
    }

    public void logInfo(String _message) {
        log.log(Level.INFO, String.format("%s %s", LOG_HEADER, _message));
    }

    public void logError(String _message) {
        log.log(Level.SEVERE, String.format("%s %s", LOG_HEADER, _message));
    }

    public void logDebug(String _message) {
        if (config.debugEnabled()) {
            log.log(Level.INFO, String.format("%s [DEBUG] %s", LOG_HEADER, _message));
        }
    }

    public MMConfig getMConfig() {
        return config;
    }

    public void mutePlayer(Player player, Long muteTime, CommandSender sender, String reason) {
        if (isMuted(player)) {
            sender.sendMessage(config.msgAlreadyMuted().replace("%PLAYER%", player.getName()));                
            return;
        }
        long curTime = System.currentTimeMillis();
        long expTime = curTime + (muteTime * 60 * 1000);
        MutedPlayer mutedPlayer = new MutedPlayer(player, expTime, reason);
        mList.add(mutedPlayer);
        String senderMessage = config.msgPlayerNowMuted()
                .replace("%AUTHOR%", sender.getName())
                .replace("%PLAYER%", mutedPlayer.getPlayerName())
                .replace("%DURATION%", mutedPlayer.getExpiredTime(config));
        if (!reason.isEmpty()) {
            senderMessage = senderMessage + ChatColor.YELLOW + ". " + config.msgReason() + ": " + ChatColor.RED + reason;
        }
        if (config.shouldNotify()) {
            getServer().broadcast(senderMessage, muteBroadcastPermNode);
        } else {
            sender.sendMessage(senderMessage);
            if (!config.msgYouHaveBeenMuted().isEmpty()) {
                player.sendMessage(config.msgYouHaveBeenMuted()
                        .replace("%DURATION%", mutedPlayer.getExpiredTime(config))
                        .replace("%REASON%", mutedPlayer.getReason())
                );
            }
        }
    }

    public void unMutePlayer(String pName, CommandSender sender) {
        String senderMessage = config.msgSenderUnMuted().replace("%PLAYER%", pName)
                .replace("%AUTHOR%", sender.getName());
        boolean success = unMutePlayer(pName);
        if (success) {
            if (config.shouldNotify()) {
                getServer().broadcast(senderMessage, unMuteBroadcastPermNode);
            } else {
                logInfo(pName + " has been unmuted!");
                if (!config.msgYouHaveBeenMuted().isEmpty()) {
                    for (Player player : getServer().getOnlinePlayers()) {
                        if (player.getName().equals(pName)) {
                            player.sendMessage(config.msgYouHaveBeenUnMuted());
                            break;
                        }
                    }
                }
                sender.sendMessage(senderMessage);
            }
        } else {
            sender.sendMessage(config.msgUnableToUnMute().replace("%PLAYER%", pName));
        }
    }

    public boolean unMutePlayer(String p) {
        logDebug("Unmuting: " + p);
        String pName = p;
        int idx = -1;
        for (MutedPlayer mutedPlayer : mList) {
            if (mutedPlayer.getPlayerName().equalsIgnoreCase(pName)) {
                idx = mList.indexOf(mutedPlayer);
                break;
            }
        }
        if (idx >= 0) {
            mList.remove(idx);
            return true;
        }
        return false;
    }

    public boolean unMutePlayer(MutedPlayer mutedPlayer) {
        logDebug("Unmuting: " + mutedPlayer.getPlayerName());
        if (mList.contains(mutedPlayer)) {
            mList.remove(mutedPlayer);
            return true;
        }
        return false;
    }

    public boolean isMuted(Player player) {
        for (MutedPlayer mutedPlayer : mList) {
            if (mutedPlayer.getUUID().equals(player.getUniqueId())) {
                return mutedPlayer.isMuted();
            }
        }
        return false;
    }

    public boolean isBlockedCmd(String cmd) {
        return getMConfig().blockedCmds().contains(cmd);
    }

    public MutedPlayer getMutedPlayer(Player player) {
        MutedPlayer mPlayer = null;
        for (MutedPlayer mutedPlayer : mList) {
            if (mutedPlayer.getPlayerName().equals(player.getDisplayName())) {
                return mutedPlayer;
            }
        }
        return mPlayer;
    }

    public Player lookupPlayer(String pName) {
        for (Player player : getServer().getOnlinePlayers()) {
            if (player.getDisplayName().equals(pName) && config.reqFullName()) {
                return player;
            }
            if (player.getDisplayName().toLowerCase().startsWith(pName)) {
                return player;
            }
        }
        return null;
    }
}
