package com.teozcommunity.teozfrank.ranklist.util;

import com.teozcommunity.teozfrank.ranklist.main.RankList;
import org.bukkit.ChatColor;
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

    public Util(RankList plugin){
        this.plugin = plugin;
    }

    public void listRanks(Player sender){

        List<String> ranks = plugin.getConfig().getStringList("ranklist.ranks");
        List<String> description = plugin.getConfig().getStringList("ranklist.description");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=====================================================");
        sender.sendMessage(ChatColorHelper.replaceColorCodes(plugin.getConfig().getString("ranklist.servername")));
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=====================================================");
        for(String desc: description){
           sender.sendMessage(ChatColorHelper.replaceColorCodes(desc));
        }
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=====================================================");
        for(String rank: ranks){
           sender.sendMessage(ChatColor.GOLD + "- " +ChatColorHelper.replaceColorCodes(rank));
        }
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=====================================================");
        sender.sendMessage(ChatColor.GOLD+"           RankList "+plugin.version+" for 1.7.2 available on Bukkit Dev   ");
        sender.sendMessage(ChatColor.GOLD+"           http://dev.bukkit.org/server-mods/rank-list/    ");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "=====================================================");
    }

}
