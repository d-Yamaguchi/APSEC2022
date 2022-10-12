package org.monstercraft.party.plugin.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.monstercraft.party.plugin.PartyAPI;
import org.monstercraft.party.plugin.command.GameCommand;
import org.monstercraft.party.plugin.wrappers.Party;

public class Teleport extends GameCommand {

	@Override
	public boolean canExecute(CommandSender sender, String[] split) {
		return split.length == 3
				&& split[0].equalsIgnoreCase("party")
				&& (split[1].equalsIgnoreCase("teleport") || split[1]
						.equalsIgnoreCase("tp"));
	}

	@Override
	public boolean execute(CommandSender sender, String[] split) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to teleport!");
		}
		Player player = (Player) sender;
		Party p;
		if (!PartyAPI.inParty(player)) {
			player.sendMessage(ChatColor.RED
					+ "You must be in a party to teleport to another player!");
			return true;
		}
		if ((p = PartyAPI.getParty(player)) != null) {
			Player to = Bukkit.getPlayer(split[2]);
			if (to != null) {
				if (p.containsMember(to)) {
					player.teleport(to);
					player.sendMessage(ChatColor.GREEN
							+ "You have teleported to: " + to.getDisplayName());
					return true;
				}
			}
		}
		player.sendMessage(ChatColor.RED + "Player not found!");
		return true;
	}

	@Override
	public String[] getPermission() {
		return new String[] { "monsterparty.teleport" };
	}

}
