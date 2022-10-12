@java.lang.Override
public void onEnable() {
    com.nisovin.shopkeepers.ShopkeepersPlugin.plugin = this;
    // register default stuff:
    shopTypesManager.registerAll(DefaultShopTypes.getAll());
    shopObjectTypesManager.registerAll(DefaultShopObjectTypes.getAll());
    uiManager.registerAll(com.nisovin.shopkeepers.ui.defaults.DefaultUIs.getAll());
    // try to load suitable NMS code
    com.nisovin.shopkeepers.compat.NMSManager.load(this);
    if (com.nisovin.shopkeepers.compat.NMSManager.getProvider() == null) {
        com.nisovin.shopkeepers.ShopkeepersPlugin.plugin.getLogger().severe("Incompatible server version: Shopkeepers cannot be enabled.");
        this.setEnabled(false);
        return;
    }
    // get config
    java.io.File file = new java.io.File(this.getDataFolder(), "config.yml");
    if (!file.exists()) {
        this.saveDefaultConfig();
    }
    this.reloadConfig();
    org.bukkit.configuration.Configuration config = this.getConfig();
    if (Settings.loadConfiguration(config)) {
        // if values were missing -> add those to the file and save it
        this.saveConfig();
    }
    Log.setDebug(config.getBoolean("debug", false));
    // get lang config
    java.lang.String lang = config.getString("language", "en");
    java.io.File langFile = new java.io.File(this.getDataFolder(), ("language-" + lang) + ".yml");
    if ((!langFile.exists()) && (this.getResource(("language-" + lang) + ".yml") != null)) {
        this.saveResource(("language-" + lang) + ".yml", false);
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
    // process additional permissions
    java.lang.String[] perms = Settings.maxShopsPermOptions.replace(" ", "").split(",");
    for (java.lang.String perm : perms) {
        if (org.bukkit.Bukkit.getPluginManager().getPermission("shopkeeper.maxshops." + perm) == null) {
            org.bukkit.Bukkit.getPluginManager().addPermission(new org.bukkit.permissions.Permission("shopkeeper.maxshops." + perm, org.bukkit.permissions.PermissionDefault.FALSE));
        }
    }
    // inform ui manager (registers ui event handlers):
    uiManager.onEnable(this);
    // register events
    org.bukkit.plugin.PluginManager pm = org.bukkit.Bukkit.getPluginManager();
    pm.registerEvents(new WorldListener(this), this);
    pm.registerEvents(new PlayerJoinQuitListener(this), this);
    pm.registerEvents(new ShopNamingListener(this), this);
    pm.registerEvents(new ChestListener(this), this);
    pm.registerEvents(new CreateListener(this), this);
    pm.registerEvents(new VillagerInteractionListener(this), this);
    pm.registerEvents(new LivingEntityShopListener(this), this);
    if (Settings.enableSignShops) {
        pm.registerEvents(new BlockShopListener(this), this);
    }
    if (Settings.enableCitizenShops) {
        try {
            if (!CitizensHandler.isEnabled()) {
                Log.warning("Citizens Shops enabled, but Citizens plugin not found or disabled.");
                Settings.enableCitizenShops = false;
            } else {
                this.getLogger().info("Citizens found, enabling NPC shopkeepers");
                CitizensShopkeeperTrait.registerTrait();
            }
        } catch (java.lang.Throwable ex) {
        }
    }
    if (Settings.blockVillagerSpawns) {
        pm.registerEvents(new BlockVillagerSpawnListener(), this);
    }
    if (Settings.protectChests) {
        pm.registerEvents(new ChestProtectListener(this), this);
    }
    if (Settings.deleteShopkeeperOnBreakChest) {
        pm.registerEvents(new RemoveShopOnChestBreakListener(this), this);
    }
    // register force-creature-spawn event:
    if (Settings.bypassSpawnBlocking) {
        creatureForceSpawnListener = new CreatureForceSpawnListener();
        org.bukkit.Bukkit.getPluginManager().registerEvents(creatureForceSpawnListener, this);
    }
    // register command handler:
    CommandManager commandManager = new CommandManager(this);
    this.getCommand("shopkeeper").setExecutor(commandManager);
    // load shopkeeper saved data:
    this.load();
    // spawn villagers in loaded chunks:
    for (org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
        for (org.bukkit.Chunk chunk : world.getLoadedChunks()) {
            this.loadShopkeepersInChunk(chunk);
        }
    }
    // start removing inactive player shops after a short delay:
    org.bukkit.Bukkit.getScheduler().runTaskLater(this, new java.lang.Runnable() {
        @java.lang.Override
        public void run() {
            removeInactivePlayerShops();
        }
    }, 5L);
    // start teleporter task:
    org.bukkit.Bukkit.getScheduler().runTaskTimer(this, new java.lang.Runnable() {
        public void run() {
            java.util.List<Shopkeeper> readd = new java.util.ArrayList<Shopkeeper>();
            java.util.Iterator<java.util.Map.Entry<java.lang.String, Shopkeeper>> iter = activeShopkeepers.entrySet().iterator();
            while (iter.hasNext()) {
                Shopkeeper shopkeeper = iter.next().getValue();
                boolean update = shopkeeper.teleport();
                if (update) {
                    // if the shopkeeper had to be respawned it's shop id changed:
                    // this removes the entry which was stored with the old shop id and later adds back the shopkeeper with it's new id
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
    }, 200, 200);// 10 seconds

    // start verifier task:
    if (Settings.enableSpawnVerifier) {
        org.bukkit.Bukkit.getScheduler().runTaskTimer(this, new java.lang.Runnable() {
            public void run() {
                int count = 0;
                for (java.util.Map.Entry<ChunkData, java.util.List<Shopkeeper>> chunkEntry : shopkeepersByChunk.entrySet()) {
                    ChunkData chunk = chunkEntry.getKey();
                    if (chunk.isChunkLoaded()) {
                        java.util.List<Shopkeeper> shopkeepers = chunkEntry.getValue();
                        for (Shopkeeper shopkeeper : shopkeepers) {
                            if ((!shopkeeper.isActive()) && shopkeeper.needsSpawning()) {
                                boolean spawned = shopkeeper.spawn();
                                if (spawned) {
                                    activeShopkeepers.put(shopkeeper.getId(), shopkeeper);
                                    count++;
                                } else {
                                    Log.debug("Failed to spawn shopkeeper at " + shopkeeper.getPositionString());
                                }
                            }
                        }
                    }
                }
                if (count > 0) {
                    Log.debug(("Spawn verifier: " + count) + " shopkeepers respawned");
                    save();
                }
            }
        }, 600, 1200);// 30,60 seconds

    }
    // start save task:
    if (!Settings.saveInstantly) {
        org.bukkit.Bukkit.getScheduler().runTaskTimer(this, new java.lang.Runnable() {
            public void run() {
                if (dirty) {
                    saveReal();
                }
            }
        }, 6000, 6000);// 5 minutes

    }
    // let's update the shopkeepers for all online players:
    for (org.bukkit.entity.Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
        org.bukkit.entity.Player _CVAR0 = player;
        org.bukkit.entity.Player _CVAR2 = player;
        java.util.UUID _CVAR1 = _CVAR0.getUniqueId();
        java.lang.String _CVAR3 = _CVAR2.getName();
        this.updateShopkeepersForPlayer(_CVAR1, _CVAR3);
    }
}