package de.craftlancer.buyskills;
import de.craftlancer.buyskills.commands.SkillCommandHandler;
import net.milkbowl.vault.permission.Permission;
import org.mcstats.Metrics;
/* TODO extend Events + JavaDocs
TODO switch to UUID instead of playernames -> requires a getUUID(OfflinePlayer) method!
 */
public class BuySkills extends org.bukkit.plugin.java.JavaPlugin {
    private static de.craftlancer.buyskills.BuySkills instance;

    private net.milkbowl.vault.permission.Permission permission;

    private org.bukkit.configuration.file.FileConfiguration config;

    private org.bukkit.configuration.file.FileConfiguration sConfig;

    private org.bukkit.configuration.file.FileConfiguration rentedConfig;

    private java.io.File rentedFile;

    private final java.util.Map<java.lang.String, de.craftlancer.buyskills.Skill> skills = new java.util.HashMap<java.lang.String, de.craftlancer.buyskills.Skill>();

    private final java.util.Map<java.lang.String, de.craftlancer.buyskills.Skill> skillsByKey = new java.util.HashMap<java.lang.String, de.craftlancer.buyskills.Skill>();

    private final java.util.HashMap<java.lang.String, de.craftlancer.buyskills.SkillPlayer> playerMap = new java.util.HashMap<java.lang.String, de.craftlancer.buyskills.SkillPlayer>();

    private final java.util.Set<java.lang.String> categories = new java.util.HashSet<java.lang.String>();

    private int skillcap = 0;

    private long updatetime = 6000L;

    private long saveTime = 12000L;

    private int skillsperpage = 5;

    {
        instance = this;
    }

    @java.lang.Override
    public void onEnable() {
        loadConfigurations();
        getCommand("skill").setExecutor(new de.craftlancer.buyskills.commands.SkillCommandHandler(this));
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            org.bukkit.plugin.RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> permissionProvider = org.bukkit.Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
            if (permissionProvider != null)
                permission = permissionProvider.getProvider();

        }
        new de.craftlancer.buyskills.SkillRentTask(this).runTaskTimer(this, updatetime, updatetime);
        new de.craftlancer.buyskills.SkillSaveTask(this).runTaskTimer(this, saveTime, saveTime);
        try {
            org.mcstats.Metrics metrics = new org.mcstats.Metrics(this);
            metrics.start();
        } catch (java.io.IOException e) {
            getLogger().info("Error while loading Metrics");
        }
    }

    @java.lang.Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        save();
    }

    /**
     * Get a SkillPlayer by his Player object
     *
     * @param player
     * 		the player
     * @return the SkillPlayer
     */
    public de.craftlancer.buyskills.SkillPlayer getSkillPlayer(org.bukkit.entity.Player player) {
        return getSkillPlayer(store.loadAchievements(mPlayer));
    }

    /**
     * Get a SkillPlayer by his name
     *
     * @param name
     * 		the name
     * @return the SkillPlayer
     */
    public de.craftlancer.buyskills.SkillPlayer getSkillPlayer(java.lang.String name) {
        if (!playerMap.containsKey(name))
            loadPlayer(name);

        return playerMap.get(name);
    }

    /**
     * Get the skill with the given name
     *
     * @param name
     * 		the name of the skill
     * @return the Skill object with the given name, null if no skill was found
     */
    public de.craftlancer.buyskills.Skill getSkill(java.lang.String name) {
        return skills.get(name.toLowerCase());
    }

    /**
     * Get the skill with the given key
     *
     * @param key
     * 		the key of the skill
     * @return the Skill object with the given key, null if no skill was found
     */
    public de.craftlancer.buyskills.Skill getSkillByKey(java.lang.String key) {
        return skillsByKey.get(key);
    }

    /**
     * Check if a skill with the given name exists
     *
     * @param name
     * 		the name of the skill
     * @return true when the skill exists, false if not
     */
    public boolean hasSkill(java.lang.String name) {
        return skills.containsKey(name.toLowerCase());
    }

    /**
     * Get the Map of all registered skills
     *
     * @return the Map of all registered Skills with their name as key
     */
    public java.util.Map<java.lang.String, de.craftlancer.buyskills.Skill> getSkillMap() {
        return skills;
    }

    /**
     * Get the Set of all registered Categories
     *
     * @return the Set of all registered Skills
     */
    public java.util.Set<java.lang.String> getCategories() {
        return categories;
    }

    /**
     * Get the maximum number of skills per player, 0 means unlimited skills
     * allowed
     * Note: the value can differ from player to player, depending on their
     * bonuscap
     *
     * @return the maximum number of skills per player
     */
    public int getSkillCap() {
        return skillcap;
    }

    /**
     * Get the number of skills per /list page
     *
     * @return the number of skills per /list page
     */
    public int getSkillsPerPage() {
        return skillsperpage;
    }

    /**
     * Get the plugin's instance
     *
     * @return the instance of the plugin
     */
    public static de.craftlancer.buyskills.BuySkills getInstance() {
        return de.craftlancer.buyskills.BuySkills.instance;
    }

    /**
     * Get a Collection of all active SkillPlayers
     * SkillPlayers are loaded lazy into this collection, meaning they are only
     * loaded if they are actually needed.
     *
     * @return a Collection of SkillPlayers
     */
    public java.util.Collection<de.craftlancer.buyskills.SkillPlayer> getSkillPlayers() {
        return playerMap.values();
    }

    /**
     * (Re)Load the config files
     */
    public void loadConfigurations() {
        if (!new java.io.File(getDataFolder(), "config.yml").exists())
            saveDefaultConfig();

        if (!new java.io.File(getDataFolder(), "skills.yml").exists())
            saveResource("skills.yml", false);

        reloadConfig();
        sConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new java.io.File(getDataFolder(), "skills.yml"));
        config = getConfig();
        loadConfig();
        loadSkills();
        rentedFile = new java.io.File(getDataFolder(), "rented.yml");
        if (!rentedFile.exists())
            try {
                if (!rentedFile.createNewFile())
                    getLogger().info("Failed to load rentedFile");

            } catch (java.io.IOException e) {
                e.printStackTrace();
            }

        rentedConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(rentedFile);
    }

    protected org.bukkit.configuration.file.FileConfiguration getRentedConfig() {
        return rentedConfig;
    }

    protected java.io.File getRentedFile() {
        return rentedFile;
    }

    protected void save() {
        for (de.craftlancer.buyskills.SkillPlayer skillPlayer : getSkillPlayers()) {
            skillPlayer.save();
            skillPlayer.saveRented();
        }
        try {
            getRentedConfig().save(getRentedFile());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    protected net.milkbowl.vault.permission.Permission getPermissions() {
        return permission;
    }

    private void loadSkills() {
        skills.clear();
        for (java.lang.String key : sConfig.getKeys(false)) {
            de.craftlancer.buyskills.Skill skill = new de.craftlancer.buyskills.Skill(key, sConfig);
            skills.put(skill.getName().toLowerCase(), skill);
            skillsByKey.put(key, skill);
            for (java.lang.String cat : skill.getCategories())
                categories.add(cat);

        }
    }

    private void loadConfig() {
        updatetime = java.lang.Math.max(1, config.getLong("general.updatetime", 300)) * 20;
        saveTime = java.lang.Math.max(1, config.getLong("general.updatetime", 600)) * 20;
        skillcap = config.getInt("general.skillcap", 0);
        skillsperpage = java.lang.Math.max(1, config.getInt("general.skillsperpage", 5));
    }

    private void loadPlayer(java.lang.String player) {
        java.io.File file = new java.io.File(getDataFolder(), (("players" + java.io.File.separator) + player) + ".yml");
        org.bukkit.configuration.file.FileConfiguration conf = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        java.util.List<java.lang.String> playerSkills = conf.getStringList("skills");
        int bonuscap = conf.getInt("bonuscap", 0);
        java.util.HashMap<java.lang.String, java.lang.Long> rent = new java.util.HashMap<java.lang.String, java.lang.Long>();
        if (rentedConfig.getConfigurationSection(player) != null)
            for (java.lang.String key : rentedConfig.getConfigurationSection(player).getKeys(false))
                rent.put(key, rentedConfig.getLong((player + ".") + key));


        playerMap.put(player, new de.craftlancer.buyskills.SkillPlayer(this, player, playerSkills, rent, bonuscap));
    }
}