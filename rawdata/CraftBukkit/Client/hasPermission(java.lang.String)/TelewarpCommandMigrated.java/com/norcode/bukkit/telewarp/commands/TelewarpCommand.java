package com.norcode.bukkit.telewarp.commands;
import com.norcode.bukkit.telewarp.Telewarp;
import com.norcode.bukkit.telewarp.commands.BaseCommand;
/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 6/2/13
 * Time: 1:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class TelewarpCommand extends com.norcode.bukkit.telewarp.commands.BaseCommand {
    public TelewarpCommand(com.norcode.bukkit.telewarp.Telewarp plugin) {
        super(plugin, null);
        minArgs = 1;
    }

    @java.lang.Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.util.LinkedList<java.lang.String> args) {
        if (args.peek().toLowerCase().equals("reload")) {
            plugin.reloadAll();
            sender.sendMessage(plugin.getMsg("config-reloaded"));
            return true;
        } else if (args.peek().toLowerCase().equals("save")) {
            plugin.saveConfig();
            plugin.getWarpManager().saveAll();
            sender.sendMessage(plugin.getMsg("config-saved"));
            return true;
        }
        return false;
    }

    @java.lang.Override
    public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String alias, java.util.LinkedList<java.lang.String> args) {
        java.util.List<java.lang.String> results = new java.util.ArrayList<java.lang.String>();
        if (args.peekLast().toLowerCase().startsWith("reload")) {
            if () {
                results.add("reload");
            }
        }
        return results;
    }
}