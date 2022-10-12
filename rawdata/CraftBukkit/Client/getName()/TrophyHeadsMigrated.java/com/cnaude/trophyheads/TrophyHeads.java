package com.cnaude.trophyheads;
/**
 *
 *
 * @author cnaude
 */
public class TrophyHeads extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    public static java.lang.String LOG_HEADER;

    static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("Minecraft");

    private static java.util.Random randomGenerator;

    private java.io.File pluginFolder;

    private java.io.File configFile;

    private static final java.util.ArrayList<java.lang.String> deathTypes = new java.util.ArrayList<java.lang.String>();

    private static boolean debugEnabled = false;

    private static boolean renameEnabled = false;

    private static boolean playerSkin = true;

    private static boolean sneakPunchInfo = true;

    private static boolean noBreak = true;

    private static final java.util.EnumMap<org.bukkit.entity.EntityType, java.util.List<java.lang.String>> itemsRequired = new java.util.EnumMap<org.bukkit.entity.EntityType, java.util.List<java.lang.String>>(org.bukkit.entity.EntityType.class);

    private static final java.util.EnumMap<org.bukkit.entity.EntityType, java.lang.Integer> dropChances = new java.util.EnumMap<org.bukkit.entity.EntityType, java.lang.Integer>(org.bukkit.entity.EntityType.class);

    private static final java.util.EnumMap<org.bukkit.entity.EntityType, java.lang.String> customSkins = new java.util.EnumMap<org.bukkit.entity.EntityType, java.lang.String>(org.bukkit.entity.EntityType.class);

    private static final java.util.EnumMap<org.bukkit.entity.EntityType, java.lang.String> skullMessages = new java.util.EnumMap<org.bukkit.entity.EntityType, java.lang.String>(org.bukkit.entity.EntityType.class);

    private static java.util.ArrayList<java.lang.String> infoBlackList = new java.util.ArrayList<java.lang.String>();

    private static org.bukkit.Material renameItem = org.bukkit.Material.PAPER;

    @java.lang.Override
    public void onEnable() {
        com.cnaude.trophyheads.TrophyHeads.LOG_HEADER = ("[" + store.loadAchievements(mPlayer)) + "]";
        com.cnaude.trophyheads.TrophyHeads.randomGenerator = new java.util.Random();
        pluginFolder = getDataFolder();
        configFile = new java.io.File(pluginFolder, "config.yml");
        createConfig();
        this.getConfig().options().copyDefaults(true);
        saveConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("headspawn").setExecutor(new com.cnaude.trophyheads.HeadSpawnCommand());
        getCommand("trophyreload").setExecutor(new com.cnaude.trophyheads.ReloadCommand(this));
        if (com.cnaude.trophyheads.TrophyHeads.renameEnabled) {
            org.bukkit.inventory.ItemStack resultHead = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, ((byte) (3)));
            org.bukkit.inventory.ShapelessRecipe shapelessRecipe = new org.bukkit.inventory.ShapelessRecipe(resultHead);
            shapelessRecipe.addIngredient(1, org.bukkit.Material.SKULL_ITEM, -1);
            shapelessRecipe.addIngredient(1, com.cnaude.trophyheads.TrophyHeads.renameItem, -1);
            getServer().addRecipe(shapelessRecipe);
        }
    }

    public void reloadMainConfig(org.bukkit.command.CommandSender sender) {
        reloadConfig();
        getConfig().options().copyDefaults(false);
        loadConfig();
        sender.sendMessage(((org.bukkit.ChatColor.GOLD + "[TrophyHeads] ") + org.bukkit.ChatColor.WHITE) + "Configuration reloaded.");
    }

    public org.bukkit.entity.EntityType getCustomSkullType(java.lang.String name) {
        for (org.bukkit.entity.EntityType et : com.cnaude.trophyheads.TrophyHeads.customSkins.keySet()) {
            if (com.cnaude.trophyheads.TrophyHeads.customSkins.get(et).equals(name)) {
                return et;
            }
        }
        return org.bukkit.entity.EntityType.UNKNOWN;
    }

    @org.bukkit.event.EventHandler
    public void onPrepareItemCraftEvent(org.bukkit.event.inventory.PrepareItemCraftEvent event) {
        if (!com.cnaude.trophyheads.TrophyHeads.renameEnabled) {
            return;
        }
        if (event.getRecipe() instanceof org.bukkit.inventory.Recipe) {
            org.bukkit.inventory.CraftingInventory ci = event.getInventory();
            org.bukkit.inventory.ItemStack result = ci.getResult();
            if (result == null) {
                return;
            }
            if (result.getType().equals(org.bukkit.Material.SKULL_ITEM)) {
                for (org.bukkit.inventory.ItemStack i : ci.getContents()) {
                    if (i.getType().equals(org.bukkit.Material.SKULL_ITEM)) {
                        if (i.getData().getData() != ((byte) (3))) {
                            ci.setResult(new org.bukkit.inventory.ItemStack(0));
                            return;
                        }
                    }
                }
                for (org.bukkit.inventory.ItemStack i : ci.getContents()) {
                    if (i.hasItemMeta() && i.getType().equals(com.cnaude.trophyheads.TrophyHeads.renameItem)) {
                        org.bukkit.inventory.meta.ItemMeta im = i.getItemMeta();
                        if (im.hasDisplayName()) {
                            org.bukkit.inventory.ItemStack res = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, ((byte) (3)));
                            org.bukkit.inventory.meta.ItemMeta itemMeta = res.getItemMeta();
                            ((org.bukkit.inventory.meta.SkullMeta) (itemMeta)).setOwner(im.getDisplayName());
                            res.setItemMeta(itemMeta);
                            ci.setResult(res);
                            break;
                        }
                    }
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (!com.cnaude.trophyheads.TrophyHeads.sneakPunchInfo) {
            return;
        }
        org.bukkit.entity.Player player = event.getPlayer();
        if (!player.isSneaking()) {
            return;
        }
        if (!player.hasPermission("trophyheads.info")) {
            return;
        }
        if (event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
            org.bukkit.block.Block block = event.getClickedBlock();
            if (block.getType() == org.bukkit.Material.SKULL) {
                org.bukkit.block.BlockState bs = block.getState();
                org.bukkit.block.Skull skull = ((org.bukkit.block.Skull) (bs));
                java.lang.String pName = "Unknown";
                java.lang.String message;
                if (skull.getSkullType().equals(org.bukkit.SkullType.PLAYER)) {
                    if (skull.hasOwner()) {
                        pName = skull.getOwner();
                        if (com.cnaude.trophyheads.TrophyHeads.customSkins.containsValue(pName)) {
                            message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(getCustomSkullType(pName));
                        } else {
                            message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.PLAYER);
                        }
                    } else {
                        message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.PLAYER);
                    }
                } else if (skull.getSkullType().equals(org.bukkit.SkullType.CREEPER)) {
                    message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.CREEPER);
                } else if (skull.getSkullType().equals(org.bukkit.SkullType.SKELETON)) {
                    message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.SKELETON);
                } else if (skull.getSkullType().equals(org.bukkit.SkullType.WITHER)) {
                    message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.WITHER);
                } else if (skull.getSkullType().equals(org.bukkit.SkullType.ZOMBIE)) {
                    message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.ZOMBIE);
                } else {
                    message = com.cnaude.trophyheads.TrophyHeads.skullMessages.get(org.bukkit.entity.EntityType.PLAYER);
                }
                if (com.cnaude.trophyheads.TrophyHeads.infoBlackList.contains(pName.toLowerCase())) {
                    logDebug("Ignoring: " + pName);
                } else {
                    message = message.replaceAll("%%NAME%%", pName);
                    message = org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
                    player.sendMessage(message);
                }
                event.setCancelled(com.cnaude.trophyheads.TrophyHeads.noBreak);
            }
        }
    }

    public boolean isValidItem(org.bukkit.entity.EntityType et, org.bukkit.Material mat) {
        if ((et == null) || (mat == null)) {
            return false;
        }
        if (com.cnaude.trophyheads.TrophyHeads.itemsRequired.containsKey(et)) {
            if (com.cnaude.trophyheads.TrophyHeads.itemsRequired.get(et).contains("ANY")) {
                return true;
            }
            if (com.cnaude.trophyheads.TrophyHeads.itemsRequired.get(et).contains(java.lang.String.valueOf(mat.getId()))) {
                return true;
            } else {
                for (java.lang.String s : com.cnaude.trophyheads.TrophyHeads.itemsRequired.get(et)) {
                    if (s.toUpperCase().equals(mat.toString())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @org.bukkit.event.EventHandler
    public void onPlayerDeathEvent(org.bukkit.event.entity.PlayerDeathEvent event) {
        org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (event.getEntity()));
        if (!player.hasPermission("trophyheads.drop")) {
            return;
        }
        if (com.cnaude.trophyheads.TrophyHeads.randomGenerator.nextInt(100) >= com.cnaude.trophyheads.TrophyHeads.dropChances.get(org.bukkit.entity.EntityType.PLAYER)) {
            return;
        }
        boolean dropOkay = false;
        org.bukkit.event.entity.EntityDamageEvent.DamageCause dc;
        if (player.getLastDamageCause() != null) {
            dc = player.getLastDamageCause().getCause();
            logDebug("DamageCause: " + dc.toString());
        } else {
            logDebug("DamageCause: NULL");
            return;
        }
        if (com.cnaude.trophyheads.TrophyHeads.deathTypes.contains(dc.toString())) {
            dropOkay = true;
        }
        if (com.cnaude.trophyheads.TrophyHeads.deathTypes.contains("ALL")) {
            dropOkay = true;
        }
        if (player.getKiller() instanceof org.bukkit.entity.Player) {
            logDebug(("Player " + player.getName()) + " killed by another player. Checking if PVP is valid death type.");
            if (com.cnaude.trophyheads.TrophyHeads.deathTypes.contains("PVP")) {
                dropOkay = isValidItem(org.bukkit.entity.EntityType.PLAYER, player.getKiller().getItemInHand().getType());
                logDebug("PVP is a valid death type. Killer's item in hand is valid? " + dropOkay);
            } else {
                logDebug("PVP is not a valid death type.");
            }
        }
        if (dropOkay) {
            logDebug("Match: true");
            org.bukkit.Location loc = player.getLocation().clone();
            org.bukkit.World world = loc.getWorld();
            java.lang.String pName = player.getName();
            org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, ((byte) (3)));
            org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
            java.util.ArrayList<java.lang.String> itemDesc = new java.util.ArrayList<java.lang.String>();
            itemMeta.setDisplayName("Head of " + pName);
            itemDesc.add(event.getDeathMessage());
            itemMeta.setLore(itemDesc);
            if (com.cnaude.trophyheads.TrophyHeads.playerSkin) {
                ((org.bukkit.inventory.meta.SkullMeta) (itemMeta)).setOwner(pName);
            }
            item.setItemMeta(itemMeta);
            world.dropItemNaturally(loc, item);
        } else {
            logDebug("Match: false");
        }
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
    public void onBlockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (event.isCancelled()) {
            logDebug("TH: Block break cancel detected.");
            return;
        }
        logDebug("TH: No cancel detected.");
        org.bukkit.block.Block block = event.getBlock();
        if (event.getPlayer() instanceof org.bukkit.entity.Player) {
            if (event.getPlayer().getGameMode().equals(org.bukkit.GameMode.CREATIVE)) {
                return;
            }
        }
        if (block.getType() == org.bukkit.Material.SKULL) {
            org.bukkit.block.Skull skull = ((org.bukkit.block.Skull) (block.getState()));
            if (skull.getSkullType().equals(org.bukkit.SkullType.PLAYER)) {
                if (skull.hasOwner()) {
                    java.lang.String pName = skull.getOwner();
                    if (com.cnaude.trophyheads.TrophyHeads.customSkins.containsValue(pName)) {
                        org.bukkit.Location loc = block.getLocation().clone();
                        event.setCancelled(true);
                        block.setType(org.bukkit.Material.AIR);
                        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, ((byte) (3)));
                        org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                        ((org.bukkit.inventory.meta.SkullMeta) (itemMeta)).setOwner(pName);
                        itemMeta.setDisplayName((org.bukkit.ChatColor.GREEN + getCustomSkullType(pName).getName()) + " Head");
                        item.setItemMeta(itemMeta);
                        org.bukkit.World world = loc.getWorld();
                        world.dropItemNaturally(loc, item);
                    }
                }
            }
        }
    }

    public void setSkullName(org.bukkit.inventory.ItemStack item, java.lang.String name) {
        org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
        ((org.bukkit.inventory.meta.SkullMeta) (itemMeta)).setOwner(name);
        itemMeta.setDisplayName(name + " Head");
        item.setItemMeta(itemMeta);
    }

    @org.bukkit.event.EventHandler
    public void onEntityDeathEvent(org.bukkit.event.entity.EntityDeathEvent event) {
        org.bukkit.entity.EntityType et = event.getEntityType();
        if (et.equals(org.bukkit.entity.EntityType.PLAYER)) {
            return;
        }
        org.bukkit.entity.Entity e = event.getEntity();
        int sti;
        boolean dropOkay;
        org.bukkit.entity.Player player;
        org.bukkit.Material mat = org.bukkit.Material.AIR;
        if (((org.bukkit.entity.LivingEntity) (e)).getKiller() instanceof org.bukkit.entity.Player) {
            player = ((org.bukkit.entity.Player) (((org.bukkit.entity.LivingEntity) (e)).getKiller()));
            mat = player.getItemInHand().getType();
        }
        dropOkay = isValidItem(et, mat);
        if (et.equals(org.bukkit.entity.EntityType.SKELETON)) {
            if (((org.bukkit.entity.Skeleton) (e)).getSkeletonType().equals(org.bukkit.entity.Skeleton.SkeletonType.NORMAL)) {
                if (com.cnaude.trophyheads.TrophyHeads.randomGenerator.nextInt(100) >= com.cnaude.trophyheads.TrophyHeads.dropChances.get(et)) {
                    return;
                }
                sti = 0;
            } else {
                return;
            }
        } else if (et.equals(org.bukkit.entity.EntityType.ZOMBIE)) {
            if (com.cnaude.trophyheads.TrophyHeads.randomGenerator.nextInt(100) >= com.cnaude.trophyheads.TrophyHeads.dropChances.get(et)) {
                return;
            }
            sti = 2;
        } else if (et.equals(org.bukkit.entity.EntityType.CREEPER)) {
            if (com.cnaude.trophyheads.TrophyHeads.randomGenerator.nextInt(100) >= com.cnaude.trophyheads.TrophyHeads.dropChances.get(et)) {
                return;
            }
            sti = 4;
        } else if (com.cnaude.trophyheads.TrophyHeads.dropChances.containsKey(et)) {
            if (com.cnaude.trophyheads.TrophyHeads.randomGenerator.nextInt(100) >= com.cnaude.trophyheads.TrophyHeads.dropChances.get(et)) {
                return;
            }
            sti = 3;
        } else {
            return;
        }
        if (!dropOkay) {
            return;
        }
        org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(org.bukkit.Material.SKULL_ITEM, 1, ((byte) (sti)));
        if ((sti == 3) || com.cnaude.trophyheads.TrophyHeads.customSkins.containsKey(et)) {
            if (com.cnaude.trophyheads.TrophyHeads.customSkins.get(et).equalsIgnoreCase("@default")) {
                logDebug("Dropping default head for " + et.getName());
            } else {
                org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
                ((org.bukkit.inventory.meta.SkullMeta) (itemMeta)).setOwner(com.cnaude.trophyheads.TrophyHeads.customSkins.get(et));
                itemMeta.setDisplayName(et.getName() + " Head");
                item.setItemMeta(itemMeta);
                logDebug((("Dropping " + com.cnaude.trophyheads.TrophyHeads.customSkins.get(et)) + " head for ") + et.getName());
            }
        }
        org.bukkit.Location loc = e.getLocation().clone();
        org.bukkit.World world = loc.getWorld();
        world.dropItemNaturally(loc, item);
    }

    private void createConfig() {
        if (!pluginFolder.exists()) {
            try {
                pluginFolder.mkdir();
            } catch (java.lang.Exception e) {
                logError(e.getMessage());
            }
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (java.io.IOException e) {
                logError(e.getMessage());
            }
        }
    }

    private void loadConfig() {
        com.cnaude.trophyheads.TrophyHeads.debugEnabled = getConfig().getBoolean("debug-enabled");
        logDebug("Debug enabled");
        com.cnaude.trophyheads.TrophyHeads.dropChances.put(org.bukkit.entity.EntityType.PLAYER, getConfig().getInt("drop-chance"));
        logDebug(("Chance to drop head: " + com.cnaude.trophyheads.TrophyHeads.dropChances.get(org.bukkit.entity.EntityType.PLAYER)) + "%");
        com.cnaude.trophyheads.TrophyHeads.playerSkin = getConfig().getBoolean("player-skin");
        logDebug("Player skins: " + com.cnaude.trophyheads.TrophyHeads.playerSkin);
        com.cnaude.trophyheads.TrophyHeads.sneakPunchInfo = getConfig().getBoolean("sneak-punch-info");
        logDebug("Sneak punch info: " + com.cnaude.trophyheads.TrophyHeads.sneakPunchInfo);
        com.cnaude.trophyheads.TrophyHeads.noBreak = getConfig().getBoolean("sneak-punch-no-break");
        logDebug("Sneak punch no break: " + com.cnaude.trophyheads.TrophyHeads.noBreak);
        java.util.List<java.lang.String> pItems = getConfig().getStringList("items-required");
        if (pItems.isEmpty()) {
            pItems.add("ANY");
            pItems.add("276");
        }
        com.cnaude.trophyheads.TrophyHeads.itemsRequired.put(org.bukkit.entity.EntityType.PLAYER, pItems);
        logDebug("Player items required: " + com.cnaude.trophyheads.TrophyHeads.itemsRequired.get(org.bukkit.entity.EntityType.PLAYER));
        for (java.lang.String entityName : getConfig().getConfigurationSection("custom-heads").getKeys(false)) {
            logDebug("Entity Name: " + entityName);
            org.bukkit.entity.EntityType et;
            if (entityName.equalsIgnoreCase("golem")) {
                et = org.bukkit.entity.EntityType.IRON_GOLEM;
            } else if (entityName.equalsIgnoreCase("ocelot")) {
                et = org.bukkit.entity.EntityType.OCELOT;
            } else {
                et = org.bukkit.entity.EntityType.fromName(entityName);
            }
            if (et == null) {
                logError("Invalid entity: " + entityName);
                continue;
            }
            logDebug("  Type: " + et.getName());
            int dropChance = getConfig().getInt(("custom-heads." + entityName) + ".drop-chance", 0);
            java.util.List<java.lang.String> items = getConfig().getStringList(("custom-heads." + entityName) + ".items-required");
            if (items.isEmpty()) {
                items.add("ANY");
                items.add("276");
            }
            java.lang.String skin = getConfig().getString(("custom-heads." + entityName) + ".skin", "MHF_" + entityName);
            java.lang.String message = getConfig().getString(("custom-heads." + entityName) + ".message", ("&eThis head once belonged to a &e" + entityName) + "&e.");
            com.cnaude.trophyheads.TrophyHeads.dropChances.put(et, dropChance);
            logDebug(("  Chance to drop head: " + com.cnaude.trophyheads.TrophyHeads.dropChances.get(et)) + "%");
            com.cnaude.trophyheads.TrophyHeads.itemsRequired.put(et, items);
            logDebug("  Items required: " + com.cnaude.trophyheads.TrophyHeads.itemsRequired.get(et));
            com.cnaude.trophyheads.TrophyHeads.customSkins.put(et, skin);
            logDebug("  Skin: " + com.cnaude.trophyheads.TrophyHeads.customSkins.get(et));
            com.cnaude.trophyheads.TrophyHeads.skullMessages.put(et, message);
            logDebug("  Message: " + com.cnaude.trophyheads.TrophyHeads.skullMessages.get(et));
        }
        com.cnaude.trophyheads.TrophyHeads.skullMessages.put(org.bukkit.entity.EntityType.PLAYER, getConfig().getString("message"));
        com.cnaude.trophyheads.TrophyHeads.renameEnabled = getConfig().getBoolean("rename-enabled");
        if (com.cnaude.trophyheads.TrophyHeads.renameEnabled) {
            try {
                com.cnaude.trophyheads.TrophyHeads.renameItem = org.bukkit.Material.getMaterial(getConfig().getInt("rename-item"));
            } catch (java.lang.Exception e) {
                com.cnaude.trophyheads.TrophyHeads.renameItem = org.bukkit.Material.PAPER;
            }
            logDebug("Rename recipe enabled: head + " + com.cnaude.trophyheads.TrophyHeads.renameItem.toString());
        }
        com.cnaude.trophyheads.TrophyHeads.deathTypes.addAll(getConfig().getStringList("death-types"));
        com.cnaude.trophyheads.TrophyHeads.infoBlackList.clear();
        for (java.lang.String name : getConfig().getStringList("info-blacklist")) {
            com.cnaude.trophyheads.TrophyHeads.infoBlackList.add(name.toLowerCase());
            logDebug("Blacklisting: " + name.toLowerCase());
        }
    }

    public void logInfo(java.lang.String _message) {
        com.cnaude.trophyheads.TrophyHeads.log.log(java.util.logging.Level.INFO, java.lang.String.format("%s %s", com.cnaude.trophyheads.TrophyHeads.LOG_HEADER, _message));
    }

    public void logError(java.lang.String _message) {
        com.cnaude.trophyheads.TrophyHeads.log.log(java.util.logging.Level.SEVERE, java.lang.String.format("%s %s", com.cnaude.trophyheads.TrophyHeads.LOG_HEADER, _message));
    }

    public void logDebug(java.lang.String _message) {
        if (com.cnaude.trophyheads.TrophyHeads.debugEnabled) {
            com.cnaude.trophyheads.TrophyHeads.log.log(java.util.logging.Level.INFO, java.lang.String.format("%s [DEBUG] %s", com.cnaude.trophyheads.TrophyHeads.LOG_HEADER, _message));
        }
    }
}