public void onEnable() {
    org.bukkit.plugin.PluginDescriptionFile _CVAR0 = getDescription();
    java.lang.String _CVAR1 = _CVAR0.getName();
    pluginName = _CVAR1;
    pluginFolder = getDataFolder();
    // create the settings configuration object
    settingsConfig = new com.netprogs.minecraft.plugins.assassins.config.settings.SettingsConfig(getDataFolder() + "/config.json");
    settingsConfig.loadConfig();
    // create the resources configuration object
    resourcesConfig = new com.netprogs.minecraft.plugins.assassins.config.resources.ResourcesConfig(getDataFolder() + "/resources.json");
    resourcesConfig.loadConfig();
    // create the data storage object
    storage = new com.netprogs.minecraft.plugins.assassins.storage.PluginStorage();
    // create the vault integration object
    vault = new com.netprogs.minecraft.plugins.assassins.integration.VaultIntegration(this, "assassin", settingsConfig.isLoggingDebug());
    vault.initialize();
    // check to make sure Vault is installed
    if (!vault.isEnabled()) {
        org.bukkit.plugin.PluginDescriptionFile _CVAR3 = pdfFile;
        java.lang.String _CVAR4 = _CVAR3.getName();
        java.lang.String _CVAR5 = "[" + _CVAR4;
        java.lang.String _CVAR6 = _CVAR5 + "] v";
        java.lang.String _CVAR7 = _CVAR6 + pdfFile.getVersion();
        java.util.logging.Logger _CVAR2 = logger;
        java.lang.String _CVAR8 = _CVAR7 + " has been disabled.";
        _CVAR2.info(_CVAR8);
        return;
    }
    // attach to the "assassin" command
    com.netprogs.minecraft.plugins.assassins.command.PluginDispatcher dispatcher = new com.netprogs.minecraft.plugins.assassins.command.PluginDispatcher(this);
    getCommand("assassin").setExecutor(dispatcher);
    // attach the events to our listeners
    getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.assassins.listener.AutoContractListener(), this);
    getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.assassins.listener.PlayerQuitListener(), this);
    getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.assassins.listener.PlayerDamageListener(), this);
    getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.assassins.listener.PlayerDeathListener(), this);
    // create the command timer instance
    commandTimer = new com.netprogs.minecraft.plugins.assassins.command.util.TimerUtil(this, settingsConfig.isLoggingDebug());
    // start up the metrics engine
    try {
        metrics = new com.netprogs.minecraft.plugins.assassins.Metrics(this);
        metrics.start();
    } catch (java.io.IOException e) {
        logger.log(java.util.logging.Level.WARNING, "Error while enabling Metrics.");
    }
    // report that this plug in is being loaded
    org.bukkit.plugin.PluginDescriptionFile pdfFile = getDescription();
    org.bukkit.plugin.PluginDescriptionFile _CVAR10 = pdfFile;
    java.lang.String _CVAR11 = _CVAR10.getName();
    java.lang.String _CVAR12 = "[" + _CVAR11;
    java.lang.String _CVAR13 = _CVAR12 + "] v";
    java.lang.String _CVAR14 = _CVAR13 + pdfFile.getVersion();
    java.util.logging.Logger _CVAR9 = logger;
    java.lang.String _CVAR15 = _CVAR14 + " has been enabled.";
    // Okay, we're done
    _CVAR9.info(_CVAR15);
}