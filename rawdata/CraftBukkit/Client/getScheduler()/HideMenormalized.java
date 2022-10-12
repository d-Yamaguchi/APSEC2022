public void onEnable() {
    this.server = this.getServer();
    this.cserver = ((org.bukkit.craftbukkit.CraftServer) (this.server));
    this.mojangServer = this.cserver.getHandle();
    this.pm = this.server.getPluginManager();
    this.config = this.getConfiguration();
    this.dataFolder = this.getDataFolder();
    org.bukkit.Server _CVAR0 = this.server;
    org.bukkit.scheduler.BukkitScheduler _CVAR1 = _CVAR0.getScheduler();
    this.scheduler = _CVAR1;
    this.hider = new de.codeinfection.quickwango.HideMe.Hider(this, mojangServer, cserver);
    this.dataFolder.mkdirs();
    // Create default config if it doesn't exist.
    if (!new java.io.File(this.dataFolder, "config.yml").exists()) {
        this.defaultConfig();
    }
    this.loadConfig();
    com.nijikokun.bukkit.Permissions.Permissions permissions = ((com.nijikokun.bukkit.Permissions.Permissions) (this.pm.getPlugin("Permissions")));
    if (permissions != null) {
        de.codeinfection.quickwango.HideMe.HideMe.permissionHandler = permissions.getHandler();
    }
    this.getCommand("hide").setExecutor(new de.codeinfection.quickwango.HideMe.commands.HideCommand(this));
    this.getCommand("unhide").setExecutor(new de.codeinfection.quickwango.HideMe.commands.UnhideCommand(this));
    this.getCommand("seehiddens").setExecutor(new de.codeinfection.quickwango.HideMe.commands.SeehiddensCommand(this));
    this.getCommand("listhiddens").setExecutor(new de.codeinfection.quickwango.HideMe.commands.ListhiddensCommand(this));
    de.codeinfection.quickwango.HideMe.HideMePlayerListener playerListener = new de.codeinfection.quickwango.HideMe.HideMePlayerListener(this);
    this.pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Highest, this);
    this.pm.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Highest, this);
    this.pm.registerEvent(Type.PLAYER_PICKUP_ITEM, playerListener, Priority.Highest, this);
    this.pm.registerEvent(Type.PLAYER_CHAT, playerListener, Priority.Lowest, this);
    this.activateHider();
    java.lang.System.out.println(((this.getDescription().getName() + " (v") + this.getDescription().getVersion()) + ") enabled");
}