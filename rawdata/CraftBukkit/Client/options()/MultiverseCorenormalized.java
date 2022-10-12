/**
 * {@inheritDoc }
 */
@java.lang.Override
public void loadConfigs() {
    // Now grab the Configuration Files.
    this.multiverseConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new java.io.File(getDataFolder(), "config.yml"));
    org.bukkit.configuration.Configuration coreDefaults = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(this.getClass().getResourceAsStream("/defaults/config.yml"));
    this.multiverseConfig.setDefaults(coreDefaults);
    org.bukkit.configuration.file.FileConfiguration _CVAR0 = this.multiverseConfig;
    org.bukkit.configuration.file.FileConfigurationOptions _CVAR1 = _CVAR0.options();
    boolean _CVAR2 = true;
    _CVAR1.copyDefaults(_CVAR2);
    this.saveMVConfig();
    this.multiverseConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new java.io.File(getDataFolder(), "config.yml"));
    this.worldManager.loadWorldConfig(new java.io.File(getDataFolder(), "worlds.yml"));
    MultiverseCoreConfiguration wantedConfig = null;
    try {
        wantedConfig = ((MultiverseCoreConfiguration) (multiverseConfig.get("multiverse-configuration")));
    } catch (java.lang.Exception e) {
        // We're just thinking "no risk no fun" and therefore have to catch and forget this exception
    } finally {
        com.onarandombox.MultiverseCore.MultiverseCore.config = (wantedConfig == null) ? new MultiverseCoreConfiguration() : wantedConfig;
    }
    // ... and save it
    multiverseConfig.set("multiverse-configuration", com.onarandombox.MultiverseCore.MultiverseCore.config);
    this.messaging.setCooldown(com.onarandombox.MultiverseCore.MultiverseCore.config.getMessageCooldown());
    // Remove old values.
    this.multiverseConfig.set("enforcegamemodes", null);
    this.multiverseConfig.set("bedrespawn", null);
    this.multiverseConfig.set("opfallback", null);
    this.saveMVConfigs();
}