package de.craftlancer.skilllevels;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.craftlancer.skilllevels.event.SkillExpChangeEvent;
import de.craftlancer.skilllevels.event.SkillLevelUpEvent;

public class LevelSystem
{
    private String expKey;
    private String expName;
    private String formula;
    private String levelKey;
    private String systemKey;
    private String systemName;
    
    private String levelName;
    private int maxlevel;
    private Map<String, LevelUser> userMap = new HashMap<String, LevelUser>();
    
    private String pointKey;
    private String pointName;
    private int pointsperlevel;
    
    private int[] preCalc = null;
    private Map<Integer, Integer> preCalcOpen = null;
    private Map<LevelAction, Map<String, Integer>> xpperaction = new HashMap<LevelAction, Map<String, Integer>>();
    
    public LevelSystem(String key, String name, int ppl, int maxlevel, String form, Map<LevelAction, Map<String, Integer>> xpmap, String levelName, String levelKey, String pointName, String pointKey, String expName, String expKey)
    {
        this.maxlevel = maxlevel;
        
        setPointsPerLevel(ppl);
        setFormula(form);
        xpperaction = xpmap;
        
        systemKey = key;
        setSystemName(name);
        setLevelName(levelName);
        setPointName(pointName);
        setExpName(expName);
        setExpKey(expKey);
        setLevelKey(levelKey);
        setPointKey(pointKey);
        
        if (maxlevel > 0)
            preCalcLevels();
        else
            preCalcOpen = new HashMap<Integer, Integer>(60);
    }
    
    public void addExp(int amount, Player p)
    {
        addExp(amount, p.getName());
    }
    
    public void addExp(int amount, String p)
    {
        getUser(p).addExp(amount);
    }
    
    public void addLevel(int level, Player p)
    {
        addLevel(level, p.getName());
    }
    
    public void addLevel(int level, String p)
    {
        int init = getLevel(p);
        getUser(p).addExp(getExpAtLevel(init + level) - getExpAtLevel(init));
    }
    
    public void addUser(String name, int exp, int usedpoints)
    {
        name = name.toLowerCase();
        if (!hasUser(name))
            userMap.put(name, new LevelUser(exp, usedpoints, name));
    }
    
    public void addUsedPoints(int amount, Player p)
    {
        addUsedPoints(amount, p.getName());
    }
    
    public void addUsedPoints(int amount, String p)
    {
        getUser(p).addUsedPoints(amount);
    }
    
    public int calcExpAtLevel(int x)
    {
        int value = Double.valueOf(getMathResult(formula, x, formula)).intValue();
        
        if (preCalcOpen != null)
            preCalcOpen.put(x, value);
        
        return value;
    }
    
    public int getExp(Player p)
    {
        return getExp(p.getName());
    }
    
    public int getExp(String p)
    {
        return getUser(p).getExp();
    }
    
    public int getExpAtLevel(int i)
    {
        if (i < 0)
            return -1;
        if (maxlevel == 0)
            return preCalcOpen.containsKey(i) ? preCalcOpen.get(i) : calcExpAtLevel(i);
        else if (i <= maxlevel)
            return preCalc != null ? preCalc[i] : calcExpAtLevel(i);
        else
            return -1;
    }
    
    public String getExpKey()
    {
        return expKey;
    }
    
    public String getExpName()
    {
        return expName;
    }
    
    public String getFormula()
    {
        return formula;
    }
    
    public int getLevel(int exp)
    {
        for (int i = 0; i <= maxlevel; i++)
            if (getExpAtLevel(i) > exp)
                return i - 1;
        
        return -1;
    }
    
    public int getLevel(Player p)
    {
        return getLevel(p.getName());
    }
    
    public int getLevel(String p)
    {
        return getLevel(getUser(p).getExp());
    }
    
    public String getLevelKey()
    {
        return levelKey;
    }
    
    public String getLevelName()
    {
        return levelName;
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
    
    public LevelUser getUser(Player p)
    {
        return getUser(p.getName());
    }
    
    public LevelUser getUser(String name)
    {
        if (!hasUser(name))
            addUser(name, 0, 0);
        
        return userMap.get(name.toLowerCase());
    }
    
    public Map<String, LevelUser> getPlayers()
    {
        return userMap;
    }
    
    public String getPointKey()
    {
        return pointKey;
    }
    
    public String getPointName()
    {
        return pointName;
    }
    
    public int getPoints(Player p)
    {
        return getPoints(p.getName());
    }
    
    public int getPoints(String p)
    {
        return getLevel(p) * getPointsPerLevel() - getUser(p).getUsedPoints();
    }
    
    public int getPointsPerLevel()
    {
        return pointsperlevel;
    }
    
    public String getSystemName()
    {
        return systemName;
    }
    
    public int getUsedPoints(Player p)
    {
        return getUsedPoints(p.getName());
    }
    
    public int getUsedPoints(String p)
    {
        return getUser(p).getUsedPoints();
    }
    
    public void handleAction(LevelAction action, String name, int amount, String user)
    {
        name = name.toUpperCase();
        
        if (!xpperaction.containsKey(action) || !xpperaction.get(action).containsKey(name))
            return;
        
        if (!hasUser(user))
            addUser(user, 0, 0);
        
        int initlevel = getLevel(user);
        addExp(xpperaction.get(action).get(name) * amount, user);
        int newlevel = getLevel(user);
        boolean isPlayer = Bukkit.getPlayerExact(user) != null;
        
        if (newlevel > initlevel)
        {
            if (isPlayer)
            {
                String msg = LevelLanguage.LEVEL_UP;
                msg = msg.replace("%level%", String.valueOf(newlevel));
                msg = msg.replace("%systemname%", getSystemName());
                Bukkit.getPlayerExact(user).sendMessage(msg);
            }
            Bukkit.getPluginManager().callEvent(new SkillLevelUpEvent(initlevel, newlevel, user, isPlayer, this));
        }
        
        Bukkit.getPluginManager().callEvent(new SkillExpChangeEvent(user, isPlayer, amount, action, this));
    }
    
    public boolean hasUser(Player p)
    {
        return hasUser(p.getName());
    }
    
    public boolean hasUser(String user)
    {
        return userMap.containsKey(user.toLowerCase());
    }
    
    private void preCalcLevels()
    {
        preCalc = new int[maxlevel + 1];
        
        for (int i = 0; i <= maxlevel; i++)
            preCalc[i] = calcExpAtLevel(i);
    }
    
    public void revokeExp(int amount, Player p)
    {
        revokeExp(amount, p.getName());
    }
    
    public void revokeExp(int amount, String p)
    {
        getUser(p).revokeExp(amount);
    }
    
    public void revokeLevel(int level, Player p)
    {
        revokeLevel(level, p.getName());
    }
    
    public void revokeLevel(int level, String p)
    {
        int init = getLevel(p);
        getUser(p).revokeExp(getExpAtLevel(init + level) - getExpAtLevel(init));
    }
    
    public void revokeUsedPoints(int amount, Player p)
    {
        revokeUsedPoints(amount, p.getName());
    }
    
    public void revokeUsedPoints(int amount, String p)
    {
        getUser(p).revokeUsedPoints(amount);
    }
    
    public void setExp(int amount, Player p)
    {
        setExp(amount, p.getName());
    }
    
    public void setExp(int amount, String p)
    {
        getUser(p).setExp(amount);
    }
    
    public void setExpKey(String expKey)
    {
        this.expKey = expKey;
    }
    
    public void setExpName(String expName)
    {
        this.expName = expName;
    }
    
    public void setFormula(String formula)
    {
        this.formula = formula;
    }
    
    public void setLevel(int level, Player p)
    {
        setLevel(level, p.getName());
    }
    
    public void setLevel(int level, String p)
    {
        getUser(p).setExp(getExpAtLevel(level));
    }
    
    public void setLevelKey(String levelKey)
    {
        this.levelKey = levelKey;
    }
    
    public void setLevelName(String levelName)
    {
        this.levelName = levelName;
    }
    
    public void setPointKey(String pointKey)
    {
        this.pointKey = pointKey;
    }
    
    public void setPointName(String pointName)
    {
        this.pointName = pointName;
    }
    
    public void setPointsPerLevel(int pointsperlevel)
    {
        this.pointsperlevel = pointsperlevel;
    }
    
    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }
    
    public void setUsedPoints(int amount, Player p)
    {
        setUsedPoints(amount, p.getName());
    }
    
    public void setUsedPoints(int amount, String p)
    {
        getUser(p).setUsedPoints(amount);
    }
    
    public String getSystemKey()
    {
        return systemKey;
    }
}
