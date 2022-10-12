@java.lang.Override
public void reload() {
    java.io.File _CVAR0 = dataFolder;
    java.lang.String _CVAR1 = io.github.alshain01.flags.DataStoreYaml.WILDERNESS_FILE;
    java.io.File _CVAR2 = new java.io.File(_CVAR0, _CVAR1);
    org.bukkit.configuration.file.YamlConfiguration _CVAR3 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR2);
    wilderness = _CVAR3;
    java.io.File _CVAR4 = dataFolder;
    java.lang.String _CVAR5 = io.github.alshain01.flags.DataStoreYaml.DEFAULT_FILE;
    java.io.File _CVAR6 = new java.io.File(_CVAR4, _CVAR5);
    org.bukkit.configuration.file.YamlConfiguration _CVAR7 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR6);
    def = _CVAR7;
    java.io.File _CVAR8 = dataFolder;
    java.lang.String _CVAR9 = io.github.alshain01.flags.DataStoreYaml.DATA_FILE;
    java.io.File _CVAR10 = new java.io.File(_CVAR8, _CVAR9);
    org.bukkit.configuration.file.YamlConfiguration _CVAR11 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR10);
    data = _CVAR11;
    if (!bundleFile.exists()) {
        plugin.saveResource(io.github.alshain01.flags.DataStoreYaml.BUNDLE_FILE, false);
    }
    java.io.File _CVAR12 = dataFolder;
    java.lang.String _CVAR13 = io.github.alshain01.flags.DataStoreYaml.BUNDLE_FILE;
    // Check to see if the file exists and if not, write the defaults
    java.io.File bundleFile = new java.io.File(_CVAR12, _CVAR13);
    java.io.File _CVAR14 = bundleFile;
    org.bukkit.configuration.file.YamlConfiguration _CVAR15 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR14);
    bundle = _CVAR15;
    if (!priceFile.exists()) {
        plugin.saveResource(io.github.alshain01.flags.DataStoreYaml.PRICE_FILE, false);
    }
    java.io.File _CVAR16 = dataFolder;
    java.lang.String _CVAR17 = io.github.alshain01.flags.DataStoreYaml.PRICE_FILE;
    // Check to see if the file exists and if not, write the defaults
    java.io.File priceFile = new java.io.File(_CVAR16, _CVAR17);
    java.io.File _CVAR18 = priceFile;
    org.bukkit.configuration.file.YamlConfiguration _CVAR19 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR18);
    price = _CVAR19;
    // Remove old auto-saves
    if (as != null) {
        as.cancel();
        as = null;
    }
    // Set up autosave
    if (saveInterval > 0) {
        as = new io.github.alshain01.flags.DataStoreYaml.AutoSave().runTaskTimer(plugin, saveInterval * 1200, saveInterval * 1200);
    }
}