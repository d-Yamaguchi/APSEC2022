@java.lang.Override
public void onEnable() {
    java.lang.String _CVAR0 = this.getName();
    java.lang.String _CVAR1 = "[" + _CVAR0;
    java.lang.String _CVAR2 = _CVAR1 + "]";
    com.cnaude.trophyheads.TrophyHeads.LOG_HEADER = _CVAR2;
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