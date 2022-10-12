public org.bukkit.configuration.file.FileConfiguration loadConfig() {
    try {
        // reloadCustomConfig();
        myconfigfile = new java.io.File(getDataFolder(), Constants.CONFIG_FILE_NAME);
        if (!myconfigfile.exists()) {
            // load the default values into file
            copy(getResource(Constants.CONFIG_FILE_NAME), myconfigfile);
        }
        materialfile = new java.io.File(getDataFolder(), Constants.MAT_FILE_NAME);
        if (!materialfile.exists()) {
            // load the default values into file
            copy(getResource(Constants.MAT_FILE_NAME), materialfile);
        }
        java.io.File _CVAR0 = myconfigfile;
        org.bukkit.configuration.file.YamlConfiguration _CVAR1 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR0);
        config = _CVAR1;
        // read the material values we need and convert them to ENUM
        java.lang.String f_str = config.getString("farmer_material");
        java.lang.String b_str = config.getString("butcher_material");
        java.lang.String l_str = config.getString("librarian_material");
        java.lang.String s_str = config.getString("smith_material");
        java.lang.String p_str = config.getString("priest_material");
        // convert strings to Material ENUMs
        f = org.bukkit.Material.getMaterial(f_str);
        b = org.bukkit.Material.getMaterial(b_str);
        l = org.bukkit.Material.getMaterial(l_str);
        s = org.bukkit.Material.getMaterial(s_str);
        p = org.bukkit.Material.getMaterial(p_str);
        if (config.getString("lang") == null) {
            java.lang.System.out.println("Profession is trying to update the config file...");
            // we need to overwrite the config file and set the values back from above
            copy(getResource(Constants.CONFIG_FILE_NAME), myconfigfile);
            config.set("farmer_material", f_str);
            config.set("butcher_material", b_str);
            config.set("librarian_material", l_str);
            config.set("smith_material", s_str);
            config.set("priest_material", p_str);
            // find the current world name and add it to the config
            current_world = getServer().getWorlds().get(0);
            config.set("worlds." + current_world.getName(), true);
            config.set("lang", "EN");
            config.set("consume", false);
            // save the config
            saveCustomConfig();
            java.lang.System.out.println("Profession is saving the new config and reloading... please check the config file and edit it as necessary using the appropriate commands.");
            java.io.File _CVAR2 = myconfigfile;
            org.bukkit.configuration.file.YamlConfiguration _CVAR3 = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR2);
            // reload the config
            config = _CVAR3;
        }
        // read the language value
        Constants.LANGUAGE = config.getString("lang");
        // read the consume value
        Constants.CONSUME = config.getBoolean("consume");
        nms_hm = Constants.nms();
        java.lang.String nms = ((java.lang.String) (nms_hm.get(Constants.LANGUAGE)));
        Constants.NO_MATS_MESSAGE = ((((((((((((((nms + org.bukkit.ChatColor.YELLOW) + f_str) + " -> FARMER\n") + org.bukkit.ChatColor.RED) + b_str) + " -> BUTCHER\n") + org.bukkit.ChatColor.BLUE) + l_str) + " -> LIBRARIAN\n") + org.bukkit.ChatColor.GRAY) + s_str) + " -> BLACKSMITH\n") + org.bukkit.ChatColor.DARK_RED) + p_str) + " -> PRIEST";
    } catch (java.lang.Exception e) {
        java.lang.System.err.println(Constants.MY_PLUGIN_NAME + " failed to retrieve configuration from directory. Using defaults.");
    }
    return config;
}