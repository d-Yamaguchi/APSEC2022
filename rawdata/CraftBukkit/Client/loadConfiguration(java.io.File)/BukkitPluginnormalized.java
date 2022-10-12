public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
    org.getspout.spoutapi.player.SpoutPlayer splayer = ((org.getspout.spoutapi.player.SpoutPlayer) (sender));
    java.lang.String _CVAR0 = "tutorials.yml";
    java.io.File confFile = new java.io.File(_CVAR0);
    java.io.File _CVAR1 = confFile;
    org.bukkit.configuration.file.FileConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR1);
    if (cmd.getName().equalsIgnoreCase("tut")) {
        if (args[0] != null) {
            if (args[0].equalsIgnoreCase("stop")) {
                org.getspout.spoutapi.SpoutManager.getSoundManager().stopMusic(splayer);
                splayer.sendMessage(org.bukkit.ChatColor.DARK_GREEN + "The tutorial has stopped playing.");
                return true;
            } else if (args[0].equalsIgnoreCase("play")) {
                if ((args[1] != null) && (args[2] != null)) {
                    if (splayer.isSpoutCraftEnabled()) {
                        java.lang.String url = config.getString(((("tutorials.categories." + args[1]) + ".") + args[2]) + ".url");
                        org.getspout.spoutapi.SpoutManager.getSoundManager().playCustomMusic(com.precipicegames.betternpc.BukkitPlugin.plugin, splayer, url, true);
                    } else {
                        splayer.sendMessage(org.bukkit.ChatColor.RED + "You need SpoutCraft to listen to tutorials!");
                    }
                }
            }
        }
    }
    return false;
}