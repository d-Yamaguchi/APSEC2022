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
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.doncarnage.minecraft.Babble.Patterns.Transformable;

/**
 * The main class for this bukkit plugin. It handles the command interception
 * as well as dealing with the events. This is going to be separated later on.
 * 
 * @author Viciouss
 *
 */
public class Babble extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getLogger().info("Loaded Babble successfully");
		File dataFolder = getDataFolder();
		if (!dataFolder.exists() && !dataFolder.mkdir()) {
			getLogger().warning("Could not create directory to save data!");
		}

		getServer().getPluginManager().registerEvents(this, this);
	}

	HashMap<String, Transformable> babblers = new HashMap<String, Transformable>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (command.getName().equalsIgnoreCase("babble")) {

			if (args.length > 0) {

				if ("add".equals(args[0])) {
					if(args.length!=3 && args.length!=2) return false;
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
		if(babblers.size()==0) {
			sender.sendMessage("[Babble] No people are babbling right now!");
			return;
		}
		sender.sendMessage("[Babble] People on the babbling list right now:");
		ArrayList<String> babblerList = new ArrayList<String>();
		for(String name : babblers.keySet()) {
			String onTheList = name + "(" + babblers.get(name).getPatternName() + ")";
			babblerList.add(onTheList);
		}
		sender.sendMessage("[Babble] " + StringUtils.join(babblerList, ", "));
	}

	private void clearList(CommandSender sender) {
		babblers.clear();
		sender.sendMessage("[Babble] Babblers successfully resetted.");
	}

	private void addPlayerToList(CommandSender sender, String[] args) {
		Player toMute = Bukkit.getPlayer(args[1]);
		if (toMute != null) {

			BabbleMode modeToAdd = null;
			if (args.length == 3) {
				modeToAdd = BabbleMode.getPatternByName(args[2].toLowerCase());
			} else {
				BabbleMode[] modes = BabbleMode.values();
				if (modes.length > 0) {
					modeToAdd = modes[0];
				}
			}

			if (modeToAdd != null) {
				babblers.put(toMute.getName(), modeToAdd.getPattern());
				sender.sendMessage("[Babble] Pattern "
						+ modeToAdd.name().toLowerCase()
						+ " has been applied to " + toMute.getName() + ".");
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
		Transformable pattern = babblers.remove(args[1]);
		if (pattern != null) {
			sender.sendMessage("[Babble] Player " + args[1]
					+ " has been removed.");
		} else {
			sender.sendMessage("[Babble] Player " + args[1]
					+ " could not be found on the list.");
		}
	} 

	private void sendAvailablePatternsToPlayer(CommandSender sender) {
		sender.sendMessage("[Babble] You can use one of these patterns:");
		String mode = StringUtils.join(BabbleMode.values(), ", ").toLowerCase();
		sender.sendMessage("[Babble] " + mode);
	}

	@EventHandler
	public void onPlayerTalking(AsyncPlayerChatEvent event) {
		String playerName = event.getPlayer().getName();
		if (babblers.containsKey(playerName)) {
			try {
				String oldMessage = event.getMessage();
				Transformable pattern = babblers.get(playerName);
				String newMessage = pattern.transformString(oldMessage);
				if(newMessage!=null) {
					event.setMessage(pattern.transformString(
							oldMessage));

					if(!pattern.isTrivial()) {
						Player[] onlinePlayers = getServer().getOnlinePlayers();
						for (Player p : onlinePlayers) {
							if (p.isOp()
									&& !p.getName().equals(event.getPlayer().getName())) {
								p.sendMessage("[Babble]<" + event.getPlayer().getName()
										+ "> " + oldMessage);
							}
						}
					}
				}
				else {
					event.setCancelled(true);
				}
			} catch (BabbleException e) {
				getLogger().warning(
						"Could not generate message for pattern "
								+ babblers.get(playerName).getPatternName());
				getLogger().warning(e.getMessage());
			}
		}
	}
}
