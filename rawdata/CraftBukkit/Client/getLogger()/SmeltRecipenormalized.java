public void installFurnaceRecipe(org.bukkit.plugin.java.JavaPlugin plugin) {
    org.bukkit.plugin.java.JavaPlugin _CVAR0 = plugin;
    org.bukkit.inventory.FurnaceRecipe _CVAR2 = this.recipe;
    java.util.logging.Logger _CVAR1 = _CVAR0.getLogger();
    java.lang.String _CVAR3 = "Installing furnace recipe: " + _CVAR2;
    _CVAR1.info(_CVAR3);
    plugin.getServer().addRecipe(this.recipe);
}