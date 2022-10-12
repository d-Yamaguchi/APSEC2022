public void CheckConfig() {
    config = this.getConfig();
    if (!config.contains("ExtraOptions.SaveOnDisable")) {
        config.addDefault("ExtraOptions.SaveOnDisable", true);
        org.bukkit.configuration.file.FileConfiguration _CVAR0 = config;
        org.bukkit.configuration.file.FileConfigurationOptions _CVAR1 = _CVAR0.options();
        boolean _CVAR2 = true;
        _CVAR1.copyDefaults(_CVAR2);
        saveConfig();
        reloadConfig();
    }
    if (!config.contains("SaveAllWorlds")) {
        config.addDefault("SaveAllWorlds", false);
        org.bukkit.configuration.file.FileConfiguration _CVAR3 = config;
        org.bukkit.configuration.file.FileConfigurationOptions _CVAR4 = _CVAR3.options();
        boolean _CVAR5 = true;
        _CVAR4.copyDefaults(_CVAR5);
        saveConfig();
        reloadConfig();
    }
    if (!config.contains("BroadCastWorldErrorIg")) {
        config.addDefault("BroadCastWorldErrorIg", false);
        org.bukkit.configuration.file.FileConfiguration _CVAR6 = config;
        org.bukkit.configuration.file.FileConfigurationOptions _CVAR7 = _CVAR6.options();
        boolean _CVAR8 = true;
        _CVAR7.copyDefaults(_CVAR8);
        saveConfig();
        reloadConfig();
    }
    if (!config.contains("EnableDelay")) {
        config.addDefault("EnableDelay", true);
        org.bukkit.configuration.file.FileConfiguration _CVAR9 = config;
        org.bukkit.configuration.file.FileConfigurationOptions _CVAR10 = _CVAR9.options();
        boolean _CVAR11 = true;
        _CVAR10.copyDefaults(_CVAR11);
        saveConfig();
        reloadConfig();
    }
}