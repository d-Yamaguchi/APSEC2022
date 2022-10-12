private void readConfig() {
    placeFiles();
    java.io.File _CVAR0 = me.repeat.ruFix.ruFix.directory;
    java.lang.String _CVAR1 = "config.yml";
    java.io.File configFile = new java.io.File(_CVAR0, _CVAR1);
    java.io.File _CVAR2 = configFile;
    org.bukkit.configuration.file.YamlConfiguration _CVAR3 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR2);
    config = _CVAR3;
    saveConfig();
    me.repeat.ruFix.ruFix.ruFixDebug = config.getBoolean("Debug", false);
    me.repeat.ruFix.ruFix.parseConsole = config.getBoolean("ParseConsole", true);
    me.repeat.ruFix.ruFix.parseLogFile = config.getBoolean("ParseLogFile", true);
    me.repeat.ruFix.ruFix.ruFixConsole = config.getString("Console", "UTF-8");
    me.repeat.ruFix.ruFix.ruFixLogFile = config.getString("LogFile", "UTF-8");
}