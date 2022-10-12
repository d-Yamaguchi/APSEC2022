package de.craftlancer.skilllevels;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

import de.craftlancer.currencyhandler.CurrencyHandler;
import de.craftlancer.skilllevels.commands.LevelCommands;
import de.craftlancer.skilllevels.handlers.SkillExpHandler;
import de.craftlancer.skilllevels.handlers.SkillLevelHandler;
import de.craftlancer.skilllevels.handlers.SkillPointHandler;

//TODO MySQL Storage option
//TODO UUID Auto-Update (not needed, as nobody else uses this plugin?)
public class SkillLevels extends JavaPlugin implements Listener
{
    private FileConfiguration config;
    private Map<String, LevelSystem> levelMap = new HashMap<String, LevelSystem>();
    private static SkillLevels instance;
    
    @Override
    public void onEnable()
    {
        instance = this;
        loadConfig();
        PlayerDataHandler.getInstance().loadUsers();
        
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new LevelListener(this), this);
        
        getCommand("level").setExecutor(new LevelCommands(this));
        
        if (config.getBoolean("general.useCurrencyHandler", false) && getServer().getPluginManager().getPlugin("CurrencyHandler") != null)
            for (LevelSystem ls : levelMap.values())
            {
                CurrencyHandler.registerCurrency(ls.getLevelKey(), new SkillLevelHandler(ls));
                CurrencyHandler.registerCurrency(ls.getPointKey(), new SkillPointHandler(ls));
                CurrencyHandler.registerCurrency(ls.getExpKey(), new SkillExpHandler(ls));
            }
        
        new LevelSaveTask().runTaskTimer(this, 12000, 12000);
        
        try
        {
            Metrics metrics = new Metrics(this);
            metrics.start();
            
            Graph sysCount = metrics.createGraph("Number of Level Systems");
            sysCount.addPlotter(new Metrics.Plotter(String.valueOf(levelMap.size()))
            {
                @Override
                public int getValue()
                {
                    return 1;
                }
            });
        }
        catch (IOException e)
        {
        }
    }
    
    @Override
    public void onDisable()
    {
        save();
    }
    
    public static SkillLevels getInstance()
    {
        return instance;
    }
    
    public boolean hasLevelSystem(String system)
    {
        return getLevelSystems().containsKey(system);
    }
    
    public void handleAction(LevelAction action, String name, Player player)
    {
        handleAction(action, name, 1, player);
    }
    
    public void handleAction(LevelAction action, String name, int amount, Player player)
    {
        for (LevelSystem ls : levelMap.values())
            if (player.hasPermission("levels.system." + ls.getSystemKey()))
                ls.handleAction(action, name, amount, player.getUniqueId());
    }
    
    public void handleAction(LevelAction action, String name, int amount, UUID user)
    {
        for (LevelSystem ls : levelMap.values())
            ls.handleAction(action, name, amount, user);
    }
    
    public Map<String, LevelSystem> getLevelSystems()
    {
        return levelMap;
    }
    
    public List<LevelSystem> getUsersSystems(UUID user)
    {
        List<LevelSystem> tmp = new LinkedList<LevelSystem>();
        
        for (LevelSystem ls : levelMap.values())
            if (ls.hasUser(user))
                tmp.add(ls);
        
        return tmp;
    }
    
    public LevelSystem getLevelSystem(String name)
    {
        return levelMap.get(name);
    }
    
    public void loadConfig()
    {
        if (!new File(getDataFolder().getPath(), "config.yml").exists())
            saveDefaultConfig();
        
        reloadConfig();
        config = getConfig();
        LevelLanguage.loadStrings(config);
        
        for (String key : config.getConfigurationSection("systems").getKeys(false))
        {
            String name = config.getString("systems." + key + ".name");
            String formula = config.getString("systems." + key + ".formula");
            int ppl = config.getInt("systems." + key + ".pointsperlevel");
            int maxlevel = config.getInt("systems." + key + ".maxlevel");
            
            String levelKey = config.getString("systems." + key + ".levelKey");
            String expKey = config.getString("systems." + key + ".expKey");
            String pointKey = config.getString("systems." + key + ".pointKey");
            
            String levelName = config.getString("systems." + key + ".levelName");
            String expName = config.getString("systems." + key + ".expName");
            String pointName = config.getString("systems." + key + ".pointName");
            
            Map<LevelAction, Map<String, Integer>> helpMap = new HashMap<LevelAction, Map<String, Integer>>();
            
            for (String action : config.getConfigurationSection("systems." + key + ".actions").getKeys(false))
            {
                Map<String, Integer> xpMap = new HashMap<String, Integer>();
                for (String value : config.getConfigurationSection("systems." + key + ".actions." + action).getKeys(false))
                    xpMap.put(value.toUpperCase(), config.getInt("systems." + key + ".actions." + action + "." + value));
                
                helpMap.put(LevelAction.valueOf(action), xpMap);
            }
            
            levelMap.put(key, new LevelSystem(key, name, ppl, maxlevel, formula, helpMap, levelName, levelKey, pointName, pointKey, expName, expKey));
        }
    }
    
    public void save()
    {
        for (LevelSystem system : getLevelSystems().values())
            system.save();
        
        PlayerDataHandler.getInstance().save();
    }
    
    public void reload()
    {
        save();
        
        levelMap.clear();
        loadConfig();
        PlayerDataHandler.getInstance().loadUsers();
    }
}
