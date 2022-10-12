public void onEnable() {
    org.bukkit.plugin.PluginDescriptionFile _CVAR0 = getDescription();
    java.lang.String _CVAR1 = _CVAR0.getName();
    pluginName = _CVAR1;
    pluginFolder = getDataFolder();
    // load the rank data from the XML file
    loadConfigurations();
    // check to make sure Vault is installed
    com.netprogs.minecraft.plugins.social.integration.VaultIntegration.getInstance().initialize(this);
    if (!com.netprogs.minecraft.plugins.social.integration.VaultIntegration.getInstance().isEnabled()) {
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
    // if WorldGuard is available, attach the listener for it
    if (isPluginAvailable("WorldGuard")) {
        com.netprogs.minecraft.plugins.social.integration.WorldGuardIntegration.getInstance().initialize(this);
        if (com.netprogs.minecraft.plugins.social.integration.WorldGuardIntegration.getInstance().isEnabled()) {
            getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.social.listener.perk.WorldGuardListener(), this);
        }
    } else {
        org.bukkit.plugin.PluginDescriptionFile _CVAR10 = pdfFile;
        java.lang.String _CVAR11 = _CVAR10.getName();
        java.lang.String _CVAR12 = "[" + _CVAR11;
        java.lang.String _CVAR13 = _CVAR12 + "] ";
        java.util.logging.Logger _CVAR9 = logger;
        java.lang.String _CVAR14 = _CVAR13 + "Could not find WorldGuard; features are disabled.";
        _CVAR9.info(_CVAR14);
    }
    // if LWC is available, attach the listener for it
    if (isPluginAvailable("LWC")) {
        com.netprogs.minecraft.plugins.social.integration.LWCIntegration.getInstance().initialize(this);
        if (com.netprogs.minecraft.plugins.social.integration.LWCIntegration.getInstance().isEnabled()) {
            getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.social.listener.perk.LWCListener(), this);
        }
    } else {
        org.bukkit.plugin.PluginDescriptionFile _CVAR16 = pdfFile;
        java.lang.String _CVAR17 = _CVAR16.getName();
        java.lang.String _CVAR18 = "[" + _CVAR17;
        java.lang.String _CVAR19 = _CVAR18 + "] ";
        java.util.logging.Logger _CVAR15 = logger;
        java.lang.String _CVAR20 = _CVAR19 + "Could not find LWC; features are disabled.";
        _CVAR15.info(_CVAR20);
    }
    // attach to the "social" command
    getCommand("social").setExecutor(new com.netprogs.minecraft.plugins.social.command.SocialNetworkDispatcher());
    // attach the events to our listeners
    getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.social.listener.perk.PlayerDamageListener(), this);
    getServer().getPluginManager().registerEvents(new com.netprogs.minecraft.plugins.social.listener.PlayerJoinListener(), this);
    // start up the metrics engine
    try {
        metrics = new com.netprogs.minecraft.plugins.social.Metrics(this);
        metrics.start();
    } catch (java.io.IOException e) {
        logger.log(java.util.logging.Level.WARNING, "Error while enabling Metrics.");
    }
    // report that this plug in is being loaded
    org.bukkit.plugin.PluginDescriptionFile pdfFile = getDescription();
    org.bukkit.plugin.PluginDescriptionFile _CVAR22 = pdfFile;
    java.lang.String _CVAR23 = _CVAR22.getName();
    java.lang.String _CVAR24 = "[" + _CVAR23;
    java.lang.String _CVAR25 = _CVAR24 + "] v";
    java.lang.String _CVAR26 = _CVAR25 + pdfFile.getVersion();
    java.util.logging.Logger _CVAR21 = logger;
    java.lang.String _CVAR27 = _CVAR26 + " has been enabled.";
    // Okay, we're done
    _CVAR21.info(_CVAR27);
}