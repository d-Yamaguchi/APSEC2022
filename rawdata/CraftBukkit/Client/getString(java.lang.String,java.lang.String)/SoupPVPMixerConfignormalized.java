public void reloadConfig() {
    // フォルダやファイルがない場合は、作成したりする
    java.io.File dir = com.github.ucchyocean.spm.SoupPVPMixer.getConfigFolder();
    if (!dir.exists()) {
        dir.mkdirs();
    }
    java.io.File file = new java.io.File(dir, "config.yml");
    if (!file.exists()) {
        com.github.ucchyocean.spm.Utility.copyFileFromJar(com.github.ucchyocean.spm.SoupPVPMixer.getPluginJarFile(), file, "config.yml", false);
    }
    // 再読み込み処理
    SoupPVPMixer.instance.reloadConfig();
    matchingRandomRange = config.getInt("matchingRandomRange", 30);
    org.bukkit.configuration.file.FileConfiguration _CVAR1 = config;
    java.lang.String _CVAR2 = "kit.items";
    java.lang.String _CVAR3 = "";
    com.github.ucchyocean.spm.KitHandler _CVAR0 = handler;
    java.lang.String _CVAR4 = _CVAR1.getString(_CVAR2, _CVAR3);
    java.util.ArrayList<org.bukkit.inventory.ItemStack> _CVAR5 = _CVAR0.convertToItemStack(_CVAR4);
    kitItems = _CVAR5;
    // 各コンフィグの取得
    com.github.ucchyocean.spm.KitHandler handler = new com.github.ucchyocean.spm.KitHandler();
    org.bukkit.configuration.file.FileConfiguration _CVAR7 = config;
    java.lang.String _CVAR8 = "kit.armor";
    java.lang.String _CVAR9 = "";
    com.github.ucchyocean.spm.KitHandler _CVAR6 = handler;
    java.lang.String _CVAR10 = _CVAR7.getString(_CVAR8, _CVAR9);
    java.util.ArrayList<org.bukkit.inventory.ItemStack> _CVAR11 = _CVAR6.convertToItemStack(_CVAR10);
    kitArmor = _CVAR11;
    org.bukkit.configuration.file.FileConfiguration config = SoupPVPMixer.instance.getConfig();
    org.bukkit.configuration.file.FileConfiguration _CVAR12 = config;
    java.lang.String _CVAR13 = "teleportWorld";
    java.lang.String _CVAR14 = "world";
    java.lang.String teleWorld = _CVAR12.getString(_CVAR13, _CVAR14);
    teleportWorld = org.bukkit.Bukkit.getWorld(teleWorld);
    if (teleportWorld == null) {
        teleportWorld = org.bukkit.Bukkit.getWorld("world");
    }
    org.bukkit.configuration.ConfigurationSection section = config.getConfigurationSection("teleport");
    teleport = new java.util.HashMap<java.lang.String, org.bukkit.Location>();
    if (section != null) {
        for (java.lang.String key : section.getKeys(false)) {
            teleport.put(key, getLocation(teleportWorld, section.getString(key)));
        }
    }
    winnerTeleportToSpectator = config.getBoolean("winnerTeleportToSpectator", true);
    loserRespawnToSpectator = config.getBoolean("loserRespawnToSpectator", true);
    loseByLogout = config.getBoolean("loseByLogout", true);
}