package me.dretax.SaveIt;
import me.dretax.SaveIt.metrics.Metrics;
public class Main extends org.bukkit.plugin.java.JavaPlugin {
    protected boolean EnableMsg;

    protected boolean CheckForUpdates;

    protected boolean DisableDefaultWorldSave;

    protected boolean SaveOnLogin;

    protected boolean SaveOnQuit;

    protected boolean SaveOnBlockBreak;

    protected boolean SaveOnBlockPlace;

    protected boolean SelfInventorySave;

    protected boolean SavePlayersFully;

    protected boolean Debug;

    protected boolean PowerSave;

    protected boolean SaveAllWorlds;

    /* @Author: DreTaX */
    protected boolean BroadCastErrorIg;

    protected int Delay;

    protected int Delay2;

    protected int SaveOnBlockBreakcount;

    protected int SaveOnBlockPlacecount;

    protected int SaveOnLoginCount;

    protected int SaveOnQuitCount;

    protected org.bukkit.plugin.PluginManager _pm;

    protected org.bukkit.command.ConsoleCommandSender _cs;

    protected final java.lang.String _prefix = org.bukkit.ChatColor.AQUA + "[SaveIt] ";

    protected java.util.List<java.lang.String> ExWorlds;

    protected java.lang.Boolean isLatest;

    protected java.lang.String latestVersion;

    protected final me.dretax.SaveIt.SaveItExpansions expansions = new me.dretax.SaveIt.SaveItExpansions(this);

    protected org.bukkit.configuration.file.FileConfiguration config;

    public void onDisable() {
        WorldSaveOnStop();
        if (Debug) {
            sendConsoleMessage(org.bukkit.ChatColor.YELLOW + "Saved on Disable!");
        }
        super.onDisable();
    }

    public void onEnable() {
        this._pm = getServer().getPluginManager();
        _cs = getServer().getConsoleSender();
        /* Metrics */
        try {
            me.dretax.SaveIt.metrics.Metrics metrics = new me.dretax.SaveIt.metrics.Metrics(this);
            metrics.start();
            if (Debug) {
                sendConsoleMessage(org.bukkit.ChatColor.GREEN + "SaveIt Metrics Successfully Enabled!");
            }
        }// Couldn't Connect.
         catch (java.io.IOException localIOException) {
            if (Debug) {
                sendConsoleMessage(org.bukkit.ChatColor.RED + "SaveIt Metrics Failed to boot! Notify DreTaX!");
            }
        }
        /* Configuration and Command Definitions */
        getCommand("saveit").setExecutor(this);
        config = this.getConfig();
        /* Regural Variables */
        EnableMsg = config.getBoolean("EnableSaveMSG");
        CheckForUpdates = config.getBoolean("CheckForUpdates");
        SavePlayersFully = config.getBoolean("SavePlayersEverywhere");
        DisableDefaultWorldSave = config.getBoolean("DisableDefaultWorldSave");
        PowerSave = config.getBoolean("EnablePowerSave");
        SaveAllWorlds = config.getBoolean("SaveAllWorlds");
        BroadCastErrorIg = config.getBoolean("BroadCastWorldErrorIg");
        /* Special Savings */
        SaveOnLogin = config.getBoolean("ExtraOptions.SaveOnLogin");
        SaveOnLoginCount = config.getInt("ExtraOptions.SaveOnLoginCount");
        SaveOnQuit = config.getBoolean("ExtraOptions.SaveOnQuit");
        SaveOnQuitCount = config.getInt("ExtraOptions.SaveOnQuitCount");
        SaveOnBlockBreak = config.getBoolean("ExtraOptions.SaveOnBlockBreak");
        SaveOnBlockPlace = config.getBoolean("ExtraOptions.SaveOnBlockPlace");
        SaveOnBlockBreakcount = config.getInt("ExtraOptions.SaveOnBlockBreakcount");
        SaveOnBlockPlacecount = config.getInt("ExtraOptions.SaveOnBlockPlacecount");
        SelfInventorySave = config.getBoolean("ExtraOptions.EnableSelfInventorySave");
        Debug = config.getBoolean("ExtraOptions.EnableDebugMSGs");
        java.io.File configFile = new java.io.File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            this.saveDefaultConfig();
            if (Debug) {
                sendConsoleMessage(org.bukkit.ChatColor.GREEN + "Saved Default Config");
            }
        } else {
            if (Debug) {
                sendConsoleMessage(org.bukkit.ChatColor.GREEN + "Config Exists");
            }
            CheckConfig();
        }
        /* Delay */
        Delay = config.getInt("DelayInMinutes");
        org.bukkit.Bukkit.getScheduler().runTaskTimer(this, new java.lang.Runnable() {
            public void run() {
                if (PowerSave) {
                    for (org.bukkit.entity.Player p : getServer().getOnlinePlayers()) {
                        if (p == null) {
                            return;
                        } else {
                            WorldSaveDelayed();
                        }
                    }
                } else {
                    WorldSaveDelayed();
                }
            }
        }, 1200L * Delay, 1200L * Delay);
        /* Others */
        if (DisableDefaultWorldSave) {
            for (org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
                world.setAutoSave(false);
            }
        }
        if (CheckForUpdates) {
            me.dretax.SaveIt.SaveItUpdate updateChecker = new me.dretax.SaveIt.SaveItUpdate(this);
            isLatest = updateChecker.isLatest();
            latestVersion = updateChecker.getUpdateVersion();
        }
        _pm.registerEvents(this.expansions, this);
        sendConsoleMessage(org.bukkit.ChatColor.GREEN + "Successfully Enabled!");
    }

    public void CheckConfig() {
        config = this.getConfig();
        if (!config.contains("ExtraOptions.SaveOnDisable")) {
            config.addDefault("ExtraOptions.SaveOnDisable", true);
            config.isSet(_prefix).copyDefaults(true);
            saveConfig();
            reloadConfig();
        }
        if (!config.contains("SaveAllWorlds")) {
            config.addDefault("SaveAllWorlds", false);
            config.isSet(_prefix).copyDefaults(true);
            saveConfig();
            reloadConfig();
        }
        if (!config.contains("BroadCastWorldErrorIg")) {
            config.addDefault("BroadCastWorldErrorIg", false);
            config.isSet(_prefix).copyDefaults(true);
            saveConfig();
            reloadConfig();
        }
        if (!config.contains("EnableDelay")) {
            config.addDefault("EnableDelay", true);
            config.isSet(_prefix).copyDefaults(true);
            saveConfig();
            reloadConfig();
        }
    }

    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.lang.String[] args) {
        ExWorlds = config.getStringList("Worlds");
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("save")) {
                if (sender.hasPermission("saveit.save")) {
                    WorldSaveDelayed();
                } else
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "You Don't Have Permission to do this!");

            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("saveit.reload")) {
                    ConfigReload();
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.GREEN) + "Config Reloaded! Check Console for Errors, If Config doesn't Work");
                } else
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "You Don't Have Permission to do this!");

            }
            if (args[0].equalsIgnoreCase("selfsave")) {
                if (SelfInventorySave) {
                    if (sender.hasPermission("saveit.selfsave")) {
                        if (!(sender instanceof org.bukkit.entity.Player)) {
                            sender.sendMessage((_prefix + org.bukkit.ChatColor.GREEN) + "This command can only be run by a player.");
                        } else {
                            ((org.bukkit.entity.Player) (sender)).saveData();
                            sender.sendMessage((_prefix + org.bukkit.ChatColor.GREEN) + "Your Inventory has been Saved!");
                        }
                    } else
                        sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "You Don't Have Permission to do this!");

                } else
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "This Option isn't Enabled!");

            }
            if (args[0].equalsIgnoreCase("add")) {
                if (sender.hasPermission("saveit.manage")) {
                    ExWorlds = config.getStringList("Worlds");
                    if (args.length == 2) {
                        if (!ExWorlds.contains(args[1])) {
                            ExWorlds.add(args[1]);
                            config.set("Worlds", ExWorlds);
                            saveConfig();
                            ConfigReload();
                            sender.sendMessage(((_prefix + org.bukkit.ChatColor.GREEN) + "Added World: ") + args[1]);
                        } else {
                            sender.sendMessage(((_prefix + org.bukkit.ChatColor.RED) + "World Already Exists in config: ") + args[1]);
                        }
                    } else
                        sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "Specify a World Name!");

                } else
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "You Don't Have Permission to do this!");

            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (sender.hasPermission("saveit.manage")) {
                    ExWorlds = config.getStringList("Worlds");
                    if (args.length == 2) {
                        if (ExWorlds.contains(args[1])) {
                            ExWorlds.remove(args[1]);
                            config.set("Worlds", ExWorlds);
                            saveConfig();
                            ConfigReload();
                            sender.sendMessage(((_prefix + org.bukkit.ChatColor.GREEN) + "Removed World: ") + args[1]);
                        } else {
                            sender.sendMessage(((_prefix + org.bukkit.ChatColor.RED) + "World Doesn't Exist in config: ") + args[1]);
                        }
                    } else
                        sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "Specify a World Name!");

                } else
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "You Don't Have Permission to do this!");

            }
            if (args[0].equalsIgnoreCase("list")) {
                if (sender.hasPermission("saveit.manage")) {
                    if (!SaveAllWorlds) {
                        ExWorlds = config.getStringList("Worlds");
                        sender.sendMessage((_prefix + org.bukkit.ChatColor.GREEN) + ExWorlds);
                    } else {
                        sender.sendMessage((_prefix + org.bukkit.ChatColor.GREEN) + "You are Saving all Existing Worlds.");
                        sender.sendMessage((_prefix + org.bukkit.ChatColor.GREEN) + "You don't need the list.");
                    }
                } else
                    sender.sendMessage((_prefix + org.bukkit.ChatColor.RED) + "You Don't Have Permission to do this!");

            }
        } else {
            sender.sendMessage((((_prefix + org.bukkit.ChatColor.GREEN) + "1.0.3 ") + org.bukkit.ChatColor.AQUA) + "===Commands:===");
            sender.sendMessage(((((org.bukkit.ChatColor.BLUE + "/saveit save") + org.bukkit.ChatColor.GREEN) + " - Saves All the Configured Worlds, and Inventories") + org.bukkit.ChatColor.YELLOW) + "(FULLSAVE)");
            sender.sendMessage(((org.bukkit.ChatColor.BLUE + "/saveit reload") + org.bukkit.ChatColor.GREEN) + " - Reloads Config");
            sender.sendMessage(((org.bukkit.ChatColor.BLUE + "/saveit selfsave") + org.bukkit.ChatColor.GREEN) + " - Saves Your Data Only");
            sender.sendMessage(((((org.bukkit.ChatColor.BLUE + "/saveit add ") + org.bukkit.ChatColor.YELLOW) + "WORLDNAME (Case Sensitive)") + org.bukkit.ChatColor.GREEN) + " - Adds a Given World to Config");
            sender.sendMessage(((((org.bukkit.ChatColor.BLUE + "/saveit remove ") + org.bukkit.ChatColor.YELLOW) + "WORLDNAME (Case Sensitive)") + org.bukkit.ChatColor.GREEN) + " - Removes a Given World from Config");
            sender.sendMessage(((org.bukkit.ChatColor.BLUE + "/saveit list") + org.bukkit.ChatColor.GREEN) + " - Lists Current Worlds in Config");
        }
        return false;
    }

    public void WorldSaveDelayed() {
        // Getting Variables
        config = this.getConfig();
        EnableMsg = config.getBoolean("EnableSaveMSG");
        ExWorlds = config.getStringList("Worlds");
        SavePlayersFully = config.getBoolean("SavePlayersEverywhere");
        SaveAllWorlds = config.getBoolean("SaveAllWorlds");
        BroadCastErrorIg = config.getBoolean("BroadCastWorldErrorIg");
        Delay2 = 1;
        // Checking on "EnableSaveMSG".
        if (EnableMsg) {
            org.bukkit.Bukkit.getServer().broadcastMessage(colorize(config.getString("SaveMSG")));
        }
        /* Full Save On Players, if Enabled
        If not, It will only Save Players in
        The Configured Worlds
         */
        if (SavePlayersFully) {
            org.bukkit.Bukkit.getScheduler().runTaskLater(this, new java.lang.Runnable() {
                public void run() {
                    Delay2 += 1;
                    org.bukkit.Bukkit.savePlayers();
                }
            }, 20L * Delay2);
        }
        // Getting Worlds, and Saving Them.
        for (final org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
            java.lang.String w = world.getName();
            if (!SaveAllWorlds) {
                // Checking if an Existing World is written in the Config
                if (ExWorlds.contains(w)) {
                    for (java.lang.String worldname : ExWorlds) {
                        if (org.bukkit.Bukkit.getWorld(worldname) != null) {
                            org.bukkit.Bukkit.getScheduler().runTaskLater(this, new java.lang.Runnable() {
                                public void run() {
                                    Delay2 += 1;
                                    world.save();
                                    // Getting All The Players, and Saving Them, only in the Configured Worlds.
                                    if (!SavePlayersFully) {
                                        for (org.bukkit.entity.Player player : world.getPlayers()) {
                                            player.saveData();
                                        }
                                    }
                                }
                            }, 20L * Delay2);
                        }
                        if (org.bukkit.Bukkit.getWorld(worldname) == null) {
                            sendConsoleMessage(org.bukkit.ChatColor.RED + "[ERROR] Not Existing World in Config!");
                            sendConsoleMessage(((((org.bukkit.ChatColor.RED + "[ERROR] ") + org.bukkit.ChatColor.BLUE) + worldname) + org.bukkit.ChatColor.RED) + " does not exist! Remove it from the config!");
                            if (BroadCastErrorIg) {
                                org.bukkit.Bukkit.getServer().broadcastMessage((_prefix + org.bukkit.ChatColor.RED) + "[ERROR] Not Existing World In Config!");
                                org.bukkit.Bukkit.getServer().broadcastMessage((((((_prefix + org.bukkit.ChatColor.RED) + "[ERROR] ") + org.bukkit.ChatColor.BLUE) + worldname) + org.bukkit.ChatColor.RED) + " does not exist! Remove it from the config!");
                            }
                        }
                    }
                }
            } else /* If SaveAllWorlds is true
            We will Save all the worlds instead of the configured one
            Also Calling Performance Method here
             */
            {
                org.bukkit.Bukkit.getScheduler().runTaskLater(this, new java.lang.Runnable() {
                    public void run() {
                        Delay2 += 1;
                        world.save();
                        // Getting All The Players, and Saving Them, only in the Configured Worlds.
                        if (!SavePlayersFully) {
                            for (org.bukkit.entity.Player player : world.getPlayers()) {
                                player.saveData();
                            }
                        }
                    }
                }, 20L * Delay2);
            }
        }
        if (EnableMsg) {
            org.bukkit.Bukkit.getServer().broadcastMessage(colorize(config.getString("SaveMSG2")));
        }
    }

    public void WorldSaveOnStop() {
        ExWorlds = config.getStringList("Worlds");
        SavePlayersFully = config.getBoolean("SavePlayersEverywhere");
        if (SavePlayersFully) {
            org.bukkit.Bukkit.savePlayers();
        }
        for (org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
            if (ExWorlds.contains(world.getName())) {
                world.save();
                if (!SavePlayersFully) {
                    for (org.bukkit.entity.Player player : world.getPlayers()) {
                        player.saveData();
                    }
                }
            }
        }
    }

    public void sendConsoleMessage(java.lang.String msg) {
        // My Nice Colored Console Message Prefix.
        _cs.sendMessage((_prefix + org.bukkit.ChatColor.AQUA) + msg);
    }

    public java.lang.String colorize(java.lang.String s) {
        // This little code supports coloring.
        // If String is null it will return null
        if (s == null)
            return null;

        // Extra Stuff, taken from My SimpleNames Plugin
        s = s.replaceAll("&r", org.bukkit.ChatColor.RESET + "");
        s = s.replaceAll("&l", org.bukkit.ChatColor.BOLD + "");
        s = s.replaceAll("&m", org.bukkit.ChatColor.STRIKETHROUGH + "");
        s = s.replaceAll("&o", org.bukkit.ChatColor.ITALIC + "");
        s = s.replaceAll("&n", org.bukkit.ChatColor.UNDERLINE + "");
        // This one Supports all the Default Colors
        return s.replaceAll("&([0-9a-f])", "§$1");
    }

    public void ConfigReload() {
        this.reloadConfig();
        config = this.getConfig();
        // Getting all the values, then reloading them.
        EnableMsg = config.getBoolean("EnableSaveMSG");
        CheckForUpdates = config.getBoolean("CheckForUpdates");
        SavePlayersFully = config.getBoolean("SavePlayersEverywhere");
        DisableDefaultWorldSave = config.getBoolean("DisableDefaultWorldSave");
        SaveOnLogin = config.getBoolean("ExtraOptions.SaveOnLogin");
        SaveOnLoginCount = config.getInt("ExtraOptions.SaveOnLoginCount");
        SaveOnQuit = config.getBoolean("ExtraOptions.SaveOnQuit");
        SaveOnQuitCount = config.getInt("ExtraOptions.SaveOnQuitCount");
        SaveOnBlockBreak = config.getBoolean("ExtraOptions.SaveOnBlockBreak");
        SaveOnBlockPlace = config.getBoolean("ExtraOptions.SaveOnBlockPlace");
        SaveOnBlockBreakcount = config.getInt("ExtraOptions.SaveOnBlockBreakcount");
        SaveOnBlockPlacecount = config.getInt("ExtraOptions.SaveOnBlockPlacecount");
        SelfInventorySave = config.getBoolean("ExtraOptions.EnableSelfInventorySave");
        Debug = config.getBoolean("ExtraOptions.EnableDebugMSGs");
        Delay = config.getInt("DelayInMinutes");
        PowerSave = config.getBoolean("EnablePowerSave");
        SaveAllWorlds = config.getBoolean("SaveAllWorlds");
        BroadCastErrorIg = config.getBoolean("BroadCastWorldErrorIg");
        this.reloadConfig();
        if (Debug) {
            sendConsoleMessage(org.bukkit.ChatColor.GREEN + "Config Reloaded!");
        }
    }
}