package de.craftlancer.skilllevels;
import de.craftlancer.currencyhandler.CurrencyHandler;
import de.craftlancer.skilllevels.commands.LevelCommands;
import de.craftlancer.skilllevels.handlers.SkillExpHandler;
import de.craftlancer.skilllevels.handlers.SkillLevelHandler;
import de.craftlancer.skilllevels.handlers.SkillPointHandler;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;
public class SkillLevels extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    private org.bukkit.configuration.file.FileConfiguration config;

    private org.bukkit.configuration.file.FileConfiguration pconfig;

    private java.io.File pfile;

    private java.util.Map<java.lang.String, de.craftlancer.skilllevels.LevelSystem> levelMap = new java.util.HashMap<java.lang.String, de.craftlancer.skilllevels.LevelSystem>();

    private static de.craftlancer.skilllevels.SkillLevels instance;

    @java.lang.Override
    public void onEnable() {
        de.craftlancer.skilllevels.SkillLevels.instance = this;
        loadConfig();
        loadUsers();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new de.craftlancer.skilllevels.LevelListener(this), this);
        getCommand("level").setExecutor(new de.craftlancer.skilllevels.commands.LevelCommands(this));
        if (config.getBoolean("general.useCurrencyHandler", false) && (getServer().getPluginManager().getPlugin("CurrencyHandler") != null))
            for (de.craftlancer.skilllevels.LevelSystem ls : levelMap.values()) {
                de.craftlancer.currencyhandler.CurrencyHandler.registerCurrency(ls.getLevelKey(), new de.craftlancer.skilllevels.handlers.SkillLevelHandler(ls));
                de.craftlancer.currencyhandler.CurrencyHandler.registerCurrency(ls.getPointKey(), new de.craftlancer.skilllevels.handlers.SkillPointHandler(ls));
                de.craftlancer.currencyhandler.CurrencyHandler.registerCurrency(ls.getExpKey(), new de.craftlancer.skilllevels.handlers.SkillExpHandler(ls));
            }

        try {
            org.mcstats.Metrics metrics = new org.mcstats.Metrics(this);
            metrics.start();
            org.mcstats.Metrics.Graph sysCount = metrics.createGraph("Number of Level Systems");
            sysCount.addPlotter(new org.mcstats.Metrics.Plotter(java.lang.String.valueOf(levelMap.size())) {
                @java.lang.Override
                public int getValue() {
                    return 1;
                }
            });
        } catch (java.io.IOException e) {
        }
    }

    @java.lang.Override
    public void onDisable() {
        for (org.bukkit.entity.Player p : getServer().getOnlinePlayers())
            savePlayer(p.getName());

    }

    public static de.craftlancer.skilllevels.SkillLevels getInstance() {
        return de.craftlancer.skilllevels.SkillLevels.instance;
    }

    public int getUserLevel(java.lang.String system, java.lang.String user) {
        if ((!hasLevelSystem(system)) || (!getLevelSystem(system).hasUser(user)))
            return 0;

        return getLevelSystem(system).getLevel(user);
    }

    public boolean hasLevelSystem(java.lang.String system) {
        return getLevelSystems().containsKey(system);
    }

    public void handleAction(de.craftlancer.skilllevels.LevelAction action, java.lang.String name, org.bukkit.entity.Player player) {
        handleAction(action, name, 1, player);
    }

    public void handleAction(de.craftlancer.skilllevels.LevelAction action, java.lang.String name, int amount, org.bukkit.entity.Player player) {
        for (de.craftlancer.skilllevels.LevelSystem ls : levelMap.values())
            if (player.hasPermission("levels.system." + ls.getSystemKey()))
                ls.handleAction(action, name, amount, player.getName());


    }

    public void handleAction(de.craftlancer.skilllevels.LevelAction action, java.lang.String name, int amount, java.lang.String user) {
        for (de.craftlancer.skilllevels.LevelSystem ls : levelMap.values())
            ls.handleAction(action, name, amount, user);

    }

    public java.util.Map<java.lang.String, de.craftlancer.skilllevels.LevelSystem> getLevelSystems() {
        return levelMap;
    }

    public java.util.List<de.craftlancer.skilllevels.LevelSystem> getUsersSystems(java.lang.String user) {
        java.util.List<de.craftlancer.skilllevels.LevelSystem> tmp = new java.util.LinkedList<de.craftlancer.skilllevels.LevelSystem>();
        for (de.craftlancer.skilllevels.LevelSystem ls : levelMap.values())
            if (ls.hasUser(user))
                tmp.add(ls);


        return tmp;
    }

    public de.craftlancer.skilllevels.LevelSystem getLevelSystem(java.lang.String name) {
        return levelMap.get(name);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
    public void onPlayerQuit(org.bukkit.event.player.PlayerQuitEvent e) {
        savePlayer(e.getPlayer().getName());
    }

    public void loadConfig() {
        if (!new java.io.File(getDataFolder().getPath(), "config.yml").exists())
            saveDefaultConfig();

        reloadConfig();
        config = getConfig();
        de.craftlancer.skilllevels.LevelLanguage.loadStrings(config);
        for (java.lang.String key : config.getConfigurationSection("systems").getKeys(false)) {
            java.lang.String name = config.getString(("systems." + key) + ".name");
            java.lang.String formula = config.getString(("systems." + key) + ".formula");
            int ppl = e.getClickedBlock();
            int maxlevel = config.getInt(("systems." + key) + ".maxlevel");
            java.lang.String levelKey = config.getString(("systems." + key) + ".levelKey");
            java.lang.String expKey = config.getString(("systems." + key) + ".expKey");
            java.lang.String pointKey = config.getString(("systems." + key) + ".pointKey");
            java.lang.String levelName = config.getString(("systems." + key) + ".levelName");
            java.lang.String expName = config.getString(("systems." + key) + ".expName");
            java.lang.String pointName = config.getString(("systems." + key) + ".pointName");
            java.util.Map<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>> helpMap = new java.util.HashMap<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>>();
            for (java.lang.String action : config.getConfigurationSection(("systems." + key) + ".actions").getKeys(false)) {
                java.util.Map<java.lang.String, java.lang.Integer> xpMap = new java.util.HashMap<java.lang.String, java.lang.Integer>();
                for (java.lang.String value : config.getConfigurationSection((("systems." + key) + ".actions.") + action).getKeys(false)) {
                    xpMap.put(value.toUpperCase(), config.getInt((((("systems." + key) + ".actions.") + action) + ".") + value));
                }
                helpMap.put(de.craftlancer.skilllevels.LevelAction.valueOf(action), xpMap);
            }
            levelMap.put(key, new de.craftlancer.skilllevels.LevelSystem(key, name, ppl, maxlevel, formula, helpMap, levelName, levelKey, pointName, pointKey, expName, expKey));
        }
    }

    public void loadUsers() {
        pfile = new java.io.File(getDataFolder(), "users.yml");
        pconfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(pfile);
        for (java.lang.String key : pconfig.getKeys(false))
            for (java.lang.String system : pconfig.getConfigurationSection(key).getKeys(false))
                if (levelMap.containsKey(system))
                    levelMap.get(system).addUser(key, pconfig.getInt(((key + ".") + system) + ".exp"), pconfig.getInt(((key + ".") + system) + ".usedskillp"));



    }

    public void savePlayer(java.lang.String p) {
        for (java.util.Map.Entry<java.lang.String, de.craftlancer.skilllevels.LevelSystem> system : levelMap.entrySet()) {
            de.craftlancer.skilllevels.LevelSystem ls = system.getValue();
            if (!ls.hasUser(p))
                continue;

            pconfig.set(((p + ".") + system.getKey()) + ".exp", ls.getExp(p));
            pconfig.set(((p + ".") + system.getKey()) + ".usedskillp", ls.getUsedPoints(p));
        }
        try {
            pconfig.save(pfile);
        } catch (java.io.IOException e1) {
            e1.printStackTrace();
        }
    }
}