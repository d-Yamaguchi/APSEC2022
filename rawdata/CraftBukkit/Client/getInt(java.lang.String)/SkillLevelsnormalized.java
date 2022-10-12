public void loadConfig() {
    if (!new java.io.File(getDataFolder().getPath(), "config.yml").exists()) {
        saveDefaultConfig();
    }
    reloadConfig();
    config = getConfig();
    de.craftlancer.skilllevels.LevelLanguage.loadStrings(config);
    for (java.lang.String key : config.getConfigurationSection("systems").getKeys(false)) {
        java.lang.String name = config.getString(("systems." + key) + ".name");
        java.lang.String formula = config.getString(("systems." + key) + ".formula");
        java.lang.String _CVAR1 = ".pointsperlevel";
        org.bukkit.configuration.file.FileConfiguration _CVAR0 = config;
        java.lang.String _CVAR2 = ("systems." + key) + _CVAR1;
        int ppl = _CVAR0.getInt(_CVAR2);
        java.lang.String _CVAR4 = ".maxlevel";
        org.bukkit.configuration.file.FileConfiguration _CVAR3 = config;
        java.lang.String _CVAR5 = ("systems." + key) + _CVAR4;
        int maxlevel = _CVAR3.getInt(_CVAR5);
        java.lang.String levelKey = config.getString(("systems." + key) + ".levelKey");
        java.lang.String expKey = config.getString(("systems." + key) + ".expKey");
        java.lang.String pointKey = config.getString(("systems." + key) + ".pointKey");
        java.lang.String levelName = config.getString(("systems." + key) + ".levelName");
        java.lang.String expName = config.getString(("systems." + key) + ".expName");
        java.lang.String pointName = config.getString(("systems." + key) + ".pointName");
        java.util.Map<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>> helpMap = new java.util.HashMap<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>>();
        for (java.lang.String action : config.getConfigurationSection(("systems." + key) + ".actions").getKeys(false)) {
            java.util.Map<java.lang.String, java.lang.Integer> xpMap = new java.util.HashMap<java.lang.String, java.lang.Integer>();
            for (java.lang.String value : config.getConfigurationSection((("systems." + key) + ".actions.") + action).getKeys(false)) {
                java.lang.String _CVAR7 = value;
                java.lang.String _CVAR10 = value;
                org.bukkit.configuration.file.FileConfiguration _CVAR9 = config;
                java.lang.String _CVAR11 = (((("systems." + key) + ".actions.") + action) + ".") + _CVAR10;
                java.util.Map<java.lang.String, java.lang.Integer> _CVAR6 = xpMap;
                java.lang.String _CVAR8 = _CVAR7.toUpperCase();
                int _CVAR12 = _CVAR9.getInt(_CVAR11);
                _CVAR6.put(_CVAR8, _CVAR12);
            }
            helpMap.put(de.craftlancer.skilllevels.LevelAction.valueOf(action), xpMap);
        }
        levelMap.put(key, new de.craftlancer.skilllevels.LevelSystem(key, name, ppl, maxlevel, formula, helpMap, levelName, levelKey, pointName, pointKey, expName, expKey));
    }
}