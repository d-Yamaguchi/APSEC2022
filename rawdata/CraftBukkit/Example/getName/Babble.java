/**
 * 
 *  Copyright (c) 2013 Viciouss
 *
 *  This software is licensed under the MIT license.
 *  See LICENSE file for more information.
 *  
 */

package de.doncarnage.minecraft.Babble;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.doncarnage.minecraft.Babble.ErrorHandling.CraftBukkitMessageHandler;
import de.doncarnage.minecraft.Babble.Exceptions.InitializationException;

/**
 * The main class for this bukkit plugin. It handles the command interception
 * as well as dealing with the events. This is going to be separated later on.
 * 
 * @author Viciouss
 *
 */
public class Babble extends JavaPlugin implements Listener {

	public static final String PLUGIN_NAME = "Babble";
	private BabbleService babbleService; 
	private BabbleContext context;
	
	@Override
	public void onEnable() {
		
		getLogger().info("Starting up " + PLUGIN_NAME + " now.");
		
		init();
		
		getLogger().info(PLUGIN_NAME + " successfully started!");
		
	}

	private void init() {
		
		boolean initIsOkay = true;
		
		File dataFolder = getDataFolder();
		
		if (!dataFolder.exists() && !dataFolder.mkdir()) {
			getLogger().warning("Could not create directory to save data!");
			initIsOkay = false;
		}

		context = new BabbleContext();
		context.setPathToConfigFiles(dataFolder);
		context.setMessageHandler(new CraftBukkitMessageHandler(this, context));
		
		try {
			babbleService = new BabbleService(context);
		} catch (InitializationException e) {
			initIsOkay = false;
			getLogger().warning("There have been errors loading Babble: " + e.getMessage());
		}
		
		if(initIsOkay) {
			getServer().getPluginManager().registerEvents(this, this);
		}
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equalsIgnoreCase("babble")) {

			if (args.length > 0) {

				if ("add".equals(args[0])) {
					if(args.length!=3) return false;
					addPlayerToList(sender, args);
					return true;
				} else if ("remove".equals(args[0])) {
					if(args.length!=2) return false;
					removePlayerFromList(sender, args);
					return true;
				} else if ("list".equals(args[0])) {
					if(args.length!=1) return false;
					sendBabblersToPlayer(sender);
					return true;
				} else if ("patterns".equals(args[0])) {
					if(args.length!=1) return false;
					sendAvailablePatternsToPlayer(sender);
					return true;
				} else if ("reset".equals(args[0])) {
					if(args.length!=1) return false;
					clearList(sender);
					return true;
				} else if ("mute".equals(args[0])) {
					if(args.length!=2) return false;
					String[] newArgs = new String[] { "", args[1], args[0] };
					addPlayerToList(sender, newArgs);
					return true;
				}

			} else {
				return false;
			}

		}
		return false;
	}

	private void sendBabblersToPlayer(CommandSender sender) {
		Map<String, String> babblers = babbleService.getBabblingPlayers();
		if(babblers.size()==0) {
			sender.sendMessage("[Babble] No people are babbling right now!");
			return;
		}
		sender.sendMessage("[Babble] People on the babbling list right now:");
		
		ArrayList<String> temporaryList = new ArrayList<String>();
		for(String name : babblers.keySet()) {
			String onTheList = name + "(" + babbleService.getPatternNameForPlayer(name) + ")";
			temporaryList.add(onTheList);
		}
		sender.sendMessage("[Babble] " + StringUtils.join(temporaryList, ", "));
	}

	private void clearList(CommandSender sender) {
		babbleService.removeAllBabblers();
		sender.sendMessage("[Babble] Babblers successfully resetted.");
	}

	private void addPlayerToList(CommandSender sender, String[] args) {
		Player player = Bukkit.getPlayer(args[1]);
		if (player != null) {
			
			if(babbleService.isValidPattern(args[2].toLowerCase())) {
				babbleService.addPlayerToList(args[1], args[2].toLowerCase());
				sender.sendMessage("[Babble] Pattern "
						+ args[2].toLowerCase()
						+ " has been applied to " + player.getName() + ".");
			} else {
				sender.sendMessage("[Babble] Pattern " + args[2]
						+ " could not be found.");
			}
			
		} else {
			sender.sendMessage("[Babble] Player " + args[1]
					+ " could not be found.");
		}
	}

	private void removePlayerFromList(CommandSender sender, String[] args) {
		String patternName = babbleService.removePlayerFromList(args[1]);
		if (patternName != null) {
			sender.sendMessage("[Babble] Player " + args[1]
					+ " has been removed.");
		} else {
			sender.sendMessage("[Babble] Player " + args[1]
					+ " could not be found on the list.");
		}
	} 

	private void sendAvailablePatternsToPlayer(CommandSender sender) {
		sender.sendMessage("[Babble] You can use one of these patterns:");
		sender.sendMessage("[Babble] " + babbleService.getPatternList());
	}

	@EventHandler
	public void onPlayerTalking(AsyncPlayerChatEvent event) {
		
		String playerName = event.getPlayer().getName();
		
		if (babbleService.isPlayerOnList(playerName)) {
			
			String oldMessage = event.getMessage();
			String newMessage = babbleService.getBabbleForPlayer(playerName, oldMessage);
			
			if(newMessage!=null) {
				event.setMessage(newMessage);

				if(babbleService.isPatternTrivial(playerName)) {
					
					Player[] onlinePlayers = getServer().getOnlinePlayers();
					
					for (Player player : onlinePlayers) {
						if (player.isOp() && !player.getName().equals(event.getPlayer().getName())) {
							player.sendMessage("[Babble]<" + event.getPlayer().getName() + "> " + oldMessage);
						}
					}
				}
			}
			else {
				event.setCancelled(true);
			}
		}
	}

}
