package fr.xephi.authme.commands;

import java.security.NoSuchAlgorithmException;

import me.muizers.Notifications.Notification;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.Utils;
import fr.xephi.authme.Utils.groupType;
import fr.xephi.authme.cache.auth.PlayerCache;
import fr.xephi.authme.cache.backup.FileCache;
import fr.xephi.authme.cache.limbo.LimboCache;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.events.SpawnTeleportEvent;
import fr.xephi.authme.security.PasswordSecurity;
import fr.xephi.authme.settings.Messages;
import fr.xephi.authme.settings.PlayersLogs;
import fr.xephi.authme.settings.Settings;
import fr.xephi.authme.task.MessageTask;
import fr.xephi.authme.task.TimeoutTask;


public class UnregisterCommand implements CommandExecutor {

    private Messages m = Messages.getInstance();
    private PlayersLogs pllog = PlayersLogs.getInstance();
    public AuthMe plugin;
    private DataSource database;
    private FileCache playerCache = new FileCache();

    public UnregisterCommand(AuthMe plugin, DataSource database) {
        this.plugin = plugin;
        this.database = database;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmnd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (!plugin.authmePermissible(sender, "authme." + label.toLowerCase())) {
        	m._(sender, "no_perm");
            return true;
        }

        Player player = (Player) sender;
        String name = player.getName().toLowerCase();

        if (!PlayerCache.getInstance().isAuthenticated(name)) {
        	m._(player, "not_logged_in");
            return true;
        }

        if (args.length != 1) {
        	m._(player, "usage_unreg");
            return true;
        }
        try {
            if (PasswordSecurity.comparePasswordWithHash(args[0], PlayerCache.getInstance().getAuth(name).getHash(), name)) {
                if (!database.removeAuth(name)) {
                    player.sendMessage("error");
                    return true;
                }
                if(Settings.isForcedRegistrationEnabled) {
                    player.getInventory().setArmorContents(new ItemStack[4]);
                    player.getInventory().setContents(new ItemStack[36]); 
                    player.saveData();
                    PlayerCache.getInstance().removePlayer(player.getName().toLowerCase());
                    LimboCache.getInstance().addLimboPlayer(player);
                    Utils.getInstance().setGroup(player, groupType.UNREGISTERED);
                    int delay = Settings.getRegistrationTimeout * 20;
                    int interval = Settings.getWarnMessageInterval;
                    BukkitScheduler sched = sender.getServer().getScheduler();
                    if (delay != 0) {
                        int id = sched.scheduleSyncDelayedTask(plugin, new TimeoutTask(plugin, name), delay);
                        LimboCache.getInstance().getLimboPlayer(name).setTimeoutTaskId(id);
                    }
                    sched.scheduleSyncDelayedTask(plugin, new MessageTask(plugin, name, m._("reg_msg"), interval));
                        if(!Settings.unRegisteredGroup.isEmpty()){
                            Utils.getInstance().setGroup(player, Utils.groupType.UNREGISTERED);
                        }
                        player.sendMessage("unregistered");
                        ConsoleLogger.info(player.getDisplayName() + " unregistered himself");
                        if(plugin.notifications != null) {
                        	plugin.notifications.showNotification(new Notification("[AuthMe] " + player.getName() + " unregistered himself!"));
                        }
                    return true;
                }
                if(!Settings.unRegisteredGroup.isEmpty()){
                     Utils.getInstance().setGroup(player, Utils.groupType.UNREGISTERED);
                  }
                 PlayerCache.getInstance().removePlayer(player.getName().toLowerCase());
                // check if Player cache File Exist and delete it, preventing duplication of items
                 if(playerCache.doesCacheExist(name)) {
                        playerCache.removeCache(name);
                 }
                 if (PlayersLogs.players.contains(player.getName())) {
                	 PlayersLogs.players.remove(player.getName());
                	 pllog.save();
                 }
                 player.sendMessage("unregistered");
                 ConsoleLogger.info(player.getDisplayName() + " unregistered himself");
                 if(plugin.notifications != null) {
                 	plugin.notifications.showNotification(new Notification("[AuthMe] " + player.getName() + " unregistered himself!"));
                 }
                 if (Settings.isTeleportToSpawnEnabled) {
                	 Location spawn = plugin.getSpawnLocation(player.getWorld());
                     SpawnTeleportEvent tpEvent = new SpawnTeleportEvent(player, player.getLocation(), spawn, false);
                     plugin.getServer().getPluginManager().callEvent(tpEvent);
                     if(!tpEvent.isCancelled()) {
                     	if (!tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).isLoaded()) {
                   		tpEvent.getTo().getWorld().getChunkAt(tpEvent.getTo()).load();
                   	}
                   	  player.teleport(tpEvent.getTo());
                     }
                 }
                return true;
            } else {
            	m._(player, "wrong_pwd");
            }
        } catch (NoSuchAlgorithmException ex) {
            ConsoleLogger.showError(ex.getMessage());
            sender.sendMessage("Internal Error please read the server log");
        }
        return true;
    }
}
