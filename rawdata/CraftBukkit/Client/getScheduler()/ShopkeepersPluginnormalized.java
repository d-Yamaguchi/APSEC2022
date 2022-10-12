@java.lang.Override
public void onEnable() {
    com.nisovin.shopkeepers.ShopkeepersPlugin.plugin = this;
    // try to load suitable NMS code
    com.nisovin.shopkeepers.compat.NMSManager.load(this);
    if (com.nisovin.shopkeepers.compat.NMSManager.getProvider() == null) {
        com.nisovin.shopkeepers.ShopkeepersPlugin.plugin.getLogger().severe("Incompatible server version: Shopkeepers cannot be enabled.");
        this.setEnabled(false);
        return;
    }
    // get config
    java.io.File file = new java.io.File(getDataFolder(), "config.yml");
    if (!file.exists()) {
        saveDefaultConfig();
    }
    reloadConfig();
    org.bukkit.configuration.Configuration config = getConfig();
    if (Settings.loadConfiguration(config)) {
        // if values were missing -> add those to the file and save it
        saveConfig();
    }
    debug = config.getBoolean("debug", debug);
    // get lang config
    java.lang.String lang = config.getString("language", "en");
    java.io.File langFile = new java.io.File(getDataFolder(), ("language-" + lang) + ".yml");
    if ((!langFile.exists()) && (this.getResource(("language-" + lang) + ".yml") != null)) {
        saveResource(("language-" + lang) + ".yml", false);
    }
    if (langFile.exists()) {
        try {
            org.bukkit.configuration.file.YamlConfiguration langConfig = new org.bukkit.configuration.file.YamlConfiguration();
            langConfig.load(langFile);
            Settings.loadLanguageConfiguration(langConfig);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }
    // register force-creature-spawn event:
    org.bukkit.plugin.PluginManager pm = getServer().getPluginManager();
    if (Settings.bypassSpawnBlocking) {
        creatureForceSpawnListener = new CreatureForceSpawnListener();
        pm.registerEvents(creatureForceSpawnListener, this);
    }
    // load shopkeeper saved data
    load();
    // spawn villagers in loaded chunks
    for (org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
        for (org.bukkit.Chunk chunk : world.getLoadedChunks()) {
            loadShopkeepersInChunk(chunk);
        }
    }
    // process additional perms
    java.lang.String[] perms = Settings.maxShopsPermOptions.replace(" ", "").split(",");
    for (java.lang.String perm : perms) {
        if (org.bukkit.Bukkit.getPluginManager().getPermission("shopkeeper.maxshops." + perm) == null) {
            org.bukkit.Bukkit.getPluginManager().addPermission(new org.bukkit.permissions.Permission("shopkeeper.maxshops." + perm, org.bukkit.permissions.PermissionDefault.FALSE));
        }
    }
    // register events
    pm.registerEvents(new PlayerJoinListener(), this);
    pm.registerEvents(new ShopListener(this), this);
    pm.registerEvents(new CreateListener(this), this);
    if (Settings.enableVillagerShops) {
        pm.registerEvents(new VillagerListener(this), this);
    }
    if (Settings.enableSignShops) {
        pm.registerEvents(new BlockListener(this), this);
    }
    if (Settings.enableWitchShops) {
        pm.registerEvents(new WitchListener(this), this);
    }
    if (Settings.enableCreeperShops) {
        pm.registerEvents(new CreeperListener(this), this);
    }
    if (Settings.blockVillagerSpawns) {
        pm.registerEvents(new BlockSpawnListener(), this);
    }
    if (Settings.protectChests) {
        pm.registerEvents(new ChestProtectListener(this), this);
    }
    if (Settings.deleteShopkeeperOnBreakChest) {
        pm.registerEvents(new ChestBreakListener(this), this);
    }
    // let's update the shopkeepers for all online players:
    for (org.bukkit.entity.Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
        this.updateShopkeepersForPlayer(player);
    }
    org.bukkit.scheduler.BukkitScheduler _CVAR0 = org.bukkit.Bukkit.getScheduler();
    com.nisovin.shopkeepers.ShopkeepersPlugin _CVAR1 = this;
    java.lang.Runnable _CVAR2 = new java.lang.Runnable() {
        public void run() {
            java.util.List<Shopkeeper> readd = new java.util.ArrayList<Shopkeeper>();
            java.util.Iterator<java.util.Map.Entry<java.lang.String, Shopkeeper>> iter = activeShopkeepers.entrySet().iterator();
            while (iter.hasNext()) {
                Shopkeeper shopkeeper = iter.next().getValue();
                boolean update = shopkeeper.teleport();
                if (update) {
                    readd.add(shopkeeper);
                    iter.remove();
                }
            } 
            for (Shopkeeper shopkeeper : readd) {
                if (shopkeeper.isActive()) {
                    activeShopkeepers.put(shopkeeper.getId(), shopkeeper);
                }
            }
        }
    };
    int _CVAR3 = 200;
    int _CVAR4 = 200;
    // start teleporter
    _CVAR0.scheduleSyncRepeatingTask(_CVAR1, _CVAR2, _CVAR3, _CVAR4);
    // start verifier
    if (Settings.enableSpawnVerifier) {
        org.bukkit.scheduler.BukkitScheduler _CVAR5 = org.bukkit.Bukkit.getScheduler();
        com.nisovin.shopkeepers.ShopkeepersPlugin _CVAR6 = this;
        java.lang.Runnable _CVAR7 = new java.lang.Runnable() {
            public void run() {
                int count = 0;
                for (java.lang.String chunkStr : allShopkeepersByChunk.keySet()) {
                    if (isChunkLoaded(chunkStr)) {
                        java.util.List<Shopkeeper> shopkeepers = allShopkeepersByChunk.get(chunkStr);
                        for (Shopkeeper shopkeeper : shopkeepers) {
                            if (!shopkeeper.isActive()) {
                                boolean spawned = shopkeeper.spawn();
                                if (spawned) {
                                    activeShopkeepers.put(shopkeeper.getId(), shopkeeper);
                                    count++;
                                } else {
                                    com.nisovin.shopkeepers.ShopkeepersPlugin.debug("Failed to spawn shopkeeper at " + shopkeeper.getPositionString());
                                }
                            }
                        }
                    }
                }
                if (count > 0) {
                    com.nisovin.shopkeepers.ShopkeepersPlugin.debug(("Spawn verifier: " + count) + " shopkeepers respawned");
                }
            }
        };
        int _CVAR8 = 600;
        int _CVAR9 = 1200;
        _CVAR5.scheduleSyncRepeatingTask(_CVAR6, _CVAR7, _CVAR8, _CVAR9);
    }
    // start saver
    if (!Settings.saveInstantly) {
        org.bukkit.scheduler.BukkitScheduler _CVAR10 = org.bukkit.Bukkit.getScheduler();
        com.nisovin.shopkeepers.ShopkeepersPlugin _CVAR11 = this;
        java.lang.Runnable _CVAR12 = new java.lang.Runnable() {
            public void run() {
                if (dirty) {
                    saveReal();
                    dirty = false;
                }
            }
        };
        int _CVAR13 = 6000;
        int _CVAR14 = 6000;
        _CVAR10.scheduleSyncRepeatingTask(_CVAR11, _CVAR12, _CVAR13, _CVAR14);
    }
}