package de.craftlancer.skilllevels;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;
import org.mcstats.Metrics.Graph;

import de.craftlancer.currencyhandler.CurrencyHandler;
import de.craftlancer.skilllevels.commands.LevelCommands;
import de.craftlancer.skilllevels.handlers.SkillExpHandler;
import de.craftlancer.skilllevels.handlers.SkillLevelHandler;
import de.craftlancer.skilllevels.handlers.SkillPointHandler;

public class SkillLevels extends JavaPlugin implements Listener
{
    private FileConfiguration config;
    private FileConfiguration pconfig;
    private File pfile;
    private Map<String, LevelSystem> levelMap = new HashMap<String, LevelSystem>();
    private static SkillLevels instance;
    
    @Override
    public void onEnable()
    {
        instance = this;
        loadConfig();
        loadUsers();
        
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
        for (Player p : getServer().getOnlinePlayers())
            savePlayer(p.getName());
    }
    
    public static SkillLevels getInstance()
    {
        return instance;
    }
    
    public int getUserLevel(String system, String user)
    {
        if (!hasLevelSystem(system) || !getLevelSystem(system).hasUser(user))
            return 0;
        
        return getLevelSystem(system).getLevel(user);
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
                ls.handleAction(action, name, amount, player.getName());
    }
    
    public void handleAction(LevelAction action, String name, int amount, String user)
    {
        for (LevelSystem ls : levelMap.values())
            ls.handleAction(action, name, amount, user);
    }
    
    public Map<String, LevelSystem> getLevelSystems()
    {
        return levelMap;
    }
    
    public List<LevelSystem> getUsersSystems(String user)
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
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent e)
    {
        savePlayer(e.getPlayer().getName());
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
    
    public void loadUsers()
    {
        pfile = new File(getDataFolder(), "users.yml");
        pconfig = YamlConfiguration.loadConfiguration(pfile);
        
        for (String key : pconfig.getKeys(false))
            for (String system : pconfig.getConfigurationSection(key).getKeys(false))
                if (levelMap.containsKey(system))
                    levelMap.get(system).addUser(key, pconfig.getInt(key + "." + system + ".exp"), pconfig.getInt(key + "." + system + ".usedskillp"));
    }
    
    public void savePlayer(String p)
    {
        for (Entry<String, LevelSystem> system : levelMap.entrySet())
        {
            LevelSystem ls = system.getValue();
            if (!ls.hasUser(p))
                continue;
            pconfig.set(p + "." + system.getKey() + ".exp", ls.getExp(p));
            pconfig.set(p + "." + system.getKey() + ".usedskillp", ls.getUsedPoints(p));
        }
        
        try
        {
            pconfig.save(pfile);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }
}
