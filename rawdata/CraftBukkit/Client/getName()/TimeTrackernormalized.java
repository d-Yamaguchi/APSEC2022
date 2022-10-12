@java.lang.Override
public void onEnable() {
    org.bukkit.plugin.PluginManager pluginManager = this.getServer().getPluginManager();
    pluginManager.registerEvents(new be.darnell.timetracker.TimeTrackerPlayerListener(this), this);
    saveDefaultConfig();
    saveData();
    players = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Long>();
    this.getCommand("seen").setExecutor(new be.darnell.timetracker.SeenCommand(this));
    this.getCommand("playtime").setExecutor(new be.darnell.timetracker.PlaytimeCommand(this));
    joinMsg = getConfig().getString("JoinMessage");
    for (org.bukkit.entity.Player p : getServer().getOnlinePlayers()) {
        org.bukkit.entity.Player _CVAR0 = p;
        java.lang.String _CVAR1 = _CVAR0.getName();
        addPlayerAsync(_CVAR1);
    }
    colour = org.bukkit.ChatColor.translateAlternateColorCodes('&', getConfig().getString("MessageColour", "&e"));
    alwaysDate = getConfig().getBoolean("AlwaysDate", false);
    daysBeforeDate = getConfig().getInt("DaysBeforeDate", 30);
    this.getLogger().info("All good. Loaded successfully");
}