package com.teozcommunity.teozfrank.ranklist.commands;

import com.teozcommunity.teozfrank.ranklist.main.RankList;
import com.teozcommunity.teozfrank.ranklist.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Commands implements CommandExecutor {

    private RankList plugin;
    String[] ranks = new String[20];

    public Commands(RankList plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[]) {
        if (args.length < 1 && sender instanceof Player) {
            plugin.util.listRanks(sender);
            return true;
        }

        if (args.length == 1 && args[0].equals("reload")) {
            if (sender.hasPermission("ranklist.reload")) {
                plugin.reloadConfig();
                Util.sendMsg(sender,ChatColor.GREEN + "Config Reloaded from Disk.");
                return true;
            } else {
                Util.sendMsg(sender, ChatColor.RED + "You do not have permission to do this!");
                return true;
            }
        } else {
            Util.sendMsg(sender,ChatColor.RED + "Unknown Command!");
            return true;
        }
    }
}
