package org.mcteam.ancientgates.listeners;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import org.mcteam.ancientgates.Conf;
import org.mcteam.ancientgates.Gate;
import org.mcteam.ancientgates.Gates;
import org.mcteam.ancientgates.Plugin;
import org.mcteam.ancientgates.queue.BungeeQueue;
import org.mcteam.ancientgates.queue.types.BungeeQueueType;
import org.mcteam.ancientgates.util.TeleportUtil;
import org.mcteam.ancientgates.util.types.PluginMessage;
import org.mcteam.ancientgates.util.types.WorldCoord;

public class PluginPlayerListener implements Listener {
	
    public Plugin plugin;
    
    private HashMap<Player, Location> playerLocationAtEvent = new HashMap<Player, Location>();
    
    public PluginPlayerListener(Plugin plugin) {
        this.plugin = plugin;
    }
    
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!Conf.bungeeCordSupport) {
			return;
		}

		final Player player = event.getPlayer();
		String playerName = player.getName();

		// Ok so a player joins the server
		// Find if they're in the BungeeCord in-bound teleport queue
		BungeeQueue queue = Plugin.bungeeCordInQueue.remove(playerName.toLowerCase());
		if (queue != null) {
			// Display custom join message
			String server = queue.getServer();
			event.setJoinMessage(playerName + " came from " + server + " server");
			
			// Display teleport message
			String message = queue.getMessage();
			if (!message.equals("null")) player.sendMessage(message);
			
			// Teleport incoming BungeeCord player
			BungeeQueueType queueType = queue.getQueueType();
			if (queueType == BungeeQueueType.PLAYER) {
				TeleportUtil.teleportPlayer(player, queue.getDestination());
				return;
			// Teleport incoming BungeeCord passenger
			} else if (queueType == BungeeQueueType.PASSENGER) {
				TeleportUtil.teleportVehicle(player, queue.getVehicleTypeId(), queue.getVelocity(), queue.getDestination());
				return;
			}	
		}
		
		// Ensure bungeeServerName is set
		if (Plugin.bungeeServerName == null) {
			// Construct BungeeCord "GetServer" command
			final PluginMessage msg = new PluginMessage("GetServer");

	        new BukkitRunnable() {	 
	            @Override
	            public void run() { player.sendPluginMessage(Plugin.instance, "BungeeCord", msg.toByteArray()); }
	        }.runTaskLater(plugin, 40L); // requires 2s delay after join
		}

	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (!Conf.bungeeCordSupport) {
			return;
		}
		
		String playerName = event.getPlayer().getName();

		// Ok so a player quits the server
		// If it's a BungeeCord teleport, display a custom quit message
		String server = Plugin.bungeeCordOutQueue.remove(playerName.toLowerCase());
		if (server != null) {
			event.setQuitMessage(playerName + " went to " + server + " server");
		}
		
	}
    
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerPortal(PlayerPortalEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		
		// Ok so a player portal event begins
		// Find the nearest gate!
		WorldCoord playerCoord = new WorldCoord(this.playerLocationAtEvent.get(player));
		Gate nearestGate = Gates.gateFromPortal(playerCoord);
		
		if (nearestGate != null) {
			event.setCancelled(true);
			
			// Check teleportation method
			if (!Conf.useVanillaPortals) {
				return;
			}

			// Check player has permission to enter the gate.
			if ((!Plugin.hasPermManage(player, "ancientgates.use."+nearestGate.getId())
					&& !Plugin.hasPermManage(player, "ancientgates.use.*")) && Conf.enforceAccess) {
				player.sendMessage("You lack the permissions to enter this gate.");
				return;
			}
			
			// Handle economy (check player has funds to use gate)
			if (!Plugin.handleEconManage(player, nearestGate.getCost())) {
				player.sendMessage("This gate costs: "+nearestGate.getCost()+". You have insufficient funds.");
				return;
			}
			
			// Handle BungeeCord gates (BungeeCord support disabled)
			if (nearestGate.getBungeeTo() != null && (Conf.bungeeCordSupport == false)) {
				player.sendMessage(String.format("BungeeCord support not enabled."));
				return;
			}
			
			// Teleport the player (Nether method)
			if (nearestGate.getTo() == null && nearestGate.getBungeeTo() == null) {
				player.sendMessage(String.format("This gate does not point anywhere :P"));
			} else if (nearestGate.getBungeeTo() == null)  {
				TeleportUtil.teleportPlayer(player, nearestGate.getTo());
				
				if (nearestGate.getMessage() != null) player.sendMessage(nearestGate.getMessage());
			} else {
				TeleportUtil.teleportPlayer(player, nearestGate.getBungeeTo(), event.getFrom().getBlockY() == event.getTo().getBlockY(), nearestGate.getMessage());
			}
		}
	}
    
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityPortalEnterEvent(EntityPortalEnterEvent event) {
    	if (event.getEntity() instanceof Player) {
    		Player player = (Player)event.getEntity();
    		
    		// Ok so a player enters a portal
    		// Immediately record their location
    	    Location playerLocation = event.getLocation();
    	    this.playerLocationAtEvent.put(player, playerLocation);
    	}
    }
    
}
