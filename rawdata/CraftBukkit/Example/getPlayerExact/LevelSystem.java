package de.craftlancer.skilllevels;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.craftlancer.skilllevels.event.SkillExpChangeEvent;
import de.craftlancer.skilllevels.event.SkillLevelUpEvent;

public class LevelSystem
{
    private final String systemKey;
    private final String systemName;
    private final String expKey;
    private final String pointKey;
    private final String levelKey;
    private final String expName;
    private final String pointName;
    private final String levelName;
    
    private final String formula;
    private final int maxlevel;
    private Map<UUID, LevelUser> userMap = new HashMap<UUID, LevelUser>();
    
    private final int pointsperlevel;
    
    private int[] preCalc = null;
    private Map<Integer, Integer> preCalcOpen = null;
    private final Map<LevelAction, Map<String, Integer>> xpperaction;
    
    public LevelSystem(String systemKey, String systemName, int ppl, int maxlevel, String formula, Map<LevelAction, Map<String, Integer>> xpperaction, String levelName, String levelKey, String pointName, String pointKey, String expName, String expKey)
    {
        this.systemKey = systemKey;
        this.systemName = systemName;
        
        this.expKey = expKey;
        this.pointKey = pointKey;
        this.levelKey = levelKey;
        this.expName = expName;
        this.pointName = pointName;
        this.levelName = levelName;
        
        this.formula = formula;
        
        this.maxlevel = maxlevel;
        pointsperlevel = ppl;
        this.xpperaction = xpperaction;
        
        if (maxlevel > 0)
            preCalcLevels();
        else
            preCalcOpen = new HashMap<Integer, Integer>(60);
    }
    
    public boolean hasUser(Player player)
    {
        return hasUser(player.getUniqueId());
    }
    
    public boolean hasUser(Levelable player)
    {
        return hasUser(player.getUniqueId());
    }
    
    public boolean hasUser(UUID uuid)
    {
        return userMap.containsKey(uuid);
    }
    
    public void addUser(Player player, int exp, int usedPoints)
    {
        addUser(player.getUniqueId(), exp, usedPoints);
    }
    
    public void addUser(Levelable player, int exp, int usedPoints)
    {
        addUser(player.getUniqueId(), exp, usedPoints);
    }
    
    public void addUser(UUID uuid, int exp, int usedpoints)
    {
        if (!hasUser(uuid))
            userMap.put(uuid, new LevelUser(exp, usedpoints, uuid, this));
    }
    
    public LevelUser getUser(Player p)
    {
        return getUser(p.getUniqueId());
    }
    
    public LevelUser getUser(Levelable p)
    {
        return getUser(p.getUniqueId());
    }
    
    public LevelUser getUser(UUID uuid)
    {
        if (!hasUser(uuid))
            addUser(uuid, 0, 0);
        
        return userMap.get(uuid);
    }
    
    public int getExpAtLevel(int level)
    {
        if (level < 0)
            return -1;
        if (maxlevel == 0)
            return preCalcOpen.containsKey(level) ? preCalcOpen.get(level) : calcExpAtLevel(level);
        else if (level <= maxlevel)
            return preCalc != null ? preCalc[level] : calcExpAtLevel(level);
        else
            return -1;
    }
    
    public int getLevel(int exp)
    {
        for (int i = 0; i <= maxlevel; i++)
            if (getExpAtLevel(i) > exp)
                return i - 1;
        
        return -1;
    }
    
    @Deprecated
    public Map<UUID, LevelUser> getPlayers()
    {
        return userMap;
    }
    
    public String getFormula()
    {
        return formula;
    }
    
    public int getPointsPerLevel()
    {
        return pointsperlevel;
    }
    
    public String getSystemKey()
    {
        return systemKey;
    }
    
    public String getSystemName()
    {
        return systemName;
    }
    
    public String getExpKey()
    {
        return expKey;
    }
    
    public String getExpName()
    {
        return expName;
    }
    
    public String getPointKey()
    {
        return pointKey;
    }
    
    public String getPointName()
    {
        return pointName;
    }
    
    public String getLevelKey()
    {
        return levelKey;
    }
    
    public String getLevelName()
    {
        return levelName;
    }
    
    protected void handleAction(LevelAction action, String name, int amount, UUID uuid)
    {
        name = name.toUpperCase();
        
        if (!xpperaction.containsKey(action) || !xpperaction.get(action).containsKey(name))
            return;
        
        if (!hasUser(uuid))
            addUser(uuid, 0, 0);
        
        LevelUser user = getUser(uuid);
        int initlevel = user.getLevel();
        user.addExp(xpperaction.get(action).get(name) * amount);
        int newlevel = user.getLevel();
        boolean isPlayer = Bukkit.getPlayer(uuid) != null;
        
        if (newlevel > initlevel)
        {
            if (isPlayer)
            {
                String msg = LevelLanguage.LEVEL_UP;
                msg = msg.replace("%level%", String.valueOf(newlevel));
                msg = msg.replace("%systemname%", getSystemName());
                Bukkit.getPlayer(uuid).sendMessage(msg);
            }
            Bukkit.getPluginManager().callEvent(new SkillLevelUpEvent(user, isPlayer, this, initlevel, newlevel));
        }
        
        Bukkit.getPluginManager().callEvent(new SkillExpChangeEvent(getUser(uuid), isPlayer, this, amount, action));
    }
    
    private int calcExpAtLevel(int level)
    {
        int value = Double.valueOf(getMathResult(formula, level, formula)).intValue();
        
        if (preCalcOpen != null)
            preCalcOpen.put(level, value);
        
        return value;
    }
    
    private void preCalcLevels()
    {
        preCalc = new int[maxlevel + 1];
        
        for (int i = 0; i <= maxlevel; i++)
            preCalc[i] = calcExpAtLevel(i);
    }
    
    private String getMathResult(String form, double x, String completForm)
    {
        if (x <= 0 || x > maxlevel)
            return "0";
        
        form = form.replace("x", String.valueOf(x));
        form = form.replaceAll("[-]", "+-");
        
        while (form.contains("(") && form.contains(")"))
        {
            int indexFirst = -1;
            int indexLast = -1;
            int countOpen = 0;
            for (int i = 0; i < form.toCharArray().length; i++)
            {
                if (form.toCharArray()[i] == '(')
                    if (indexFirst == -1)
                        indexFirst = i;
                    else
                        countOpen++;
                
                if (form.toCharArray()[i] == ')')
                    if (indexLast == -1 && countOpen == 0)
                        indexLast = i;
                    else
                        countOpen--;
            }
            
            if (indexFirst != -1 && indexLast != -1)
                form = form.replace(form.substring(indexFirst, indexLast + 1), getMathResult(form.substring(indexFirst + 1, indexLast), x, completForm));
        }
        
        String[] array = form.split("[+]");
        
        for (String s : array)
            if (s.contains("*"))
                form = form.replace(s, String.valueOf(Double.valueOf(getMathResult(s.substring(0, s.indexOf("*")), x, completForm)) * Double.valueOf(getMathResult(s.substring(s.indexOf("*") + 1), x, completForm))));
            else if (s.contains("/"))
                form = form.replace(s, String.valueOf(Double.valueOf(s.substring(0, s.indexOf("/"))) / Double.valueOf(getMathResult(s.substring(s.indexOf("/") + 1), x, completForm))));
            else if (s.contains("log"))
                form = form.replace(s, String.valueOf(Math.log(Double.valueOf(getMathResult(s.replace("log", ""), x, completForm)))));
            else if (s.contains("sqrt"))
                form = form.replace(s, String.valueOf(Math.sqrt(Double.valueOf(getMathResult(s.replace("sqrt", ""), x, completForm)))));
            else if (s.contains("recur"))
                form = form.replace(s, String.valueOf(getExpAtLevel(Double.valueOf(s.replace("recur", "")).intValue())));
            else if (s.contains("^"))
                form = form.replace(s, String.valueOf(Math.pow(Double.valueOf(getMathResult(s.substring(0, s.indexOf("^")), x, completForm)), Double.valueOf(getMathResult(s.substring(s.indexOf("^") + 1), x, completForm)))));
        
        double result = 0;
        for (String s : form.split("[+]"))
            try
            {
                result += Double.valueOf(s);
            }
            catch (NumberFormatException e)
            {
                result += 0;
            }
        
        return String.valueOf(result);
    }
    
    public void save()
    {
        for (LevelUser user : userMap.values())
            user.save();
    }
}
