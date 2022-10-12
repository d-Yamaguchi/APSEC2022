package com.teozcommunity.teozfrank.ranklist.util;

import com.teozcommunity.teozfrank.ranklist.main.RankList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Origional Author: teozfrank
 * Date: 25/09/13
 * Time: 16:56
 * Project: RankList
 * -----------------------------
 * Removing this header is in breach of the license agreement,
 * please do not remove, move or edit it in any way.
 * -----------------------------
 */
public class Util {

    private RankList plugin;
    private static final String LINE_BREAK = ChatColor.LIGHT_PURPLE + "=====================================================";

    public Util(RankList plugin){
        this.plugin = plugin;
    }

    public void listRanks(CommandSender sender){

        List<String> ranks = plugin.getConfig().getStringList("ranklist.ranks");
        List<String> description = plugin.getConfig().getStringList("ranklist.description");
        Util.sendEmptyMsg(sender, LINE_BREAK);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("ranklist.servername")));
        Util.sendEmptyMsg(sender, LINE_BREAK);
        for(String desc: description){
           sender.sendMessage(ChatColor.translateAlternateColorCodes('&',desc));
        }
        Util.sendEmptyMsg(sender, LINE_BREAK);
        for(String rank: ranks){
           sender.sendMessage(ChatColor.GOLD + "- " +ChatColor.translateAlternateColorCodes('&', rank));
        }
        Util.sendEmptyMsg(sender, LINE_BREAK);
        sender.sendMessage(ChatColor.GOLD+"           RankList "+plugin.version+" for 1.7.5 available on Bukkit Dev   ");
        sender.sendMessage(ChatColor.GOLD+"           http://dev.bukkit.org/server-mods/rank-list/    ");
        Util.sendEmptyMsg(sender, LINE_BREAK);
    }

    public static void sendMsg(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.GOLD + "[RankList] " + message);
    }

    public static void sendEmptyMsg(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

}
