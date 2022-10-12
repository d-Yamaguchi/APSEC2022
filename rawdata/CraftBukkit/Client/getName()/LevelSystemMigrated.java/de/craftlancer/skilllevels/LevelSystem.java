package de.craftlancer.skilllevels;
import de.craftlancer.skilllevels.event.SkillExpChangeEvent;
import de.craftlancer.skilllevels.event.SkillLevelUpEvent;
public class LevelSystem {
    private java.lang.String expKey;

    private java.lang.String expName;

    private java.lang.String formula;

    private java.lang.String levelKey;

    private java.lang.String systemKey;

    private java.lang.String systemName;

    private java.lang.String levelName;

    private int maxlevel;

    private java.util.Map<java.lang.String, de.craftlancer.skilllevels.LevelUser> userMap = new java.util.HashMap<java.lang.String, de.craftlancer.skilllevels.LevelUser>();

    private java.lang.String pointKey;

    private java.lang.String pointName;

    private int pointsperlevel;

    private int[] preCalc = null;

    private java.util.Map<java.lang.Integer, java.lang.Integer> preCalcOpen = null;

    private java.util.Map<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>> xpperaction = new java.util.HashMap<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>>();

    public LevelSystem(java.lang.String key, java.lang.String name, int ppl, int maxlevel, java.lang.String form, java.util.Map<de.craftlancer.skilllevels.LevelAction, java.util.Map<java.lang.String, java.lang.Integer>> xpmap, java.lang.String levelName, java.lang.String levelKey, java.lang.String pointName, java.lang.String pointKey, java.lang.String expName, java.lang.String expKey) {
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
            preCalcOpen = new java.util.HashMap<java.lang.Integer, java.lang.Integer>(60);

    }

    public void addExp(int amount, org.bukkit.entity.Player p) {
        addExp(amount, store.loadAchievements(mPlayer));
    }

    public void addExp(int amount, java.lang.String p) {
        getUser(p).addExp(amount);
    }

    public void addLevel(int level, org.bukkit.entity.Player p) {
        addLevel(level, p.getName());
    }

    public void addLevel(int level, java.lang.String p) {
        int init = getLevel(p);
        getUser(p).addExp(getExpAtLevel(init + level) - getExpAtLevel(init));
    }

    public void addUser(java.lang.String name, int exp, int usedpoints) {
        name = name.toLowerCase();
        if (!hasUser(name))
            userMap.put(name, new de.craftlancer.skilllevels.LevelUser(exp, usedpoints, name));

    }

    public void addUsedPoints(int amount, org.bukkit.entity.Player p) {
        addUsedPoints(amount, p.getName());
    }

    public void addUsedPoints(int amount, java.lang.String p) {
        getUser(p).addUsedPoints(amount);
    }

    public int calcExpAtLevel(int x) {
        int value = java.lang.Double.valueOf(getMathResult(formula, x, formula)).intValue();
        if (preCalcOpen != null)
            preCalcOpen.put(x, value);

        return value;
    }

    public int getExp(org.bukkit.entity.Player p) {
        return getExp(p.getName());
    }

    public int getExp(java.lang.String p) {
        return getUser(p).getExp();
    }

    public int getExpAtLevel(int i) {
        if (i < 0)
            return -1;

        if (maxlevel == 0)
            return preCalcOpen.containsKey(i) ? preCalcOpen.get(i) : calcExpAtLevel(i);
        else if (i <= maxlevel)
            return preCalc != null ? preCalc[i] : calcExpAtLevel(i);
        else
            return -1;

    }

    public java.lang.String getExpKey() {
        return expKey;
    }

    public java.lang.String getExpName() {
        return expName;
    }

    public java.lang.String getFormula() {
        return formula;
    }

    public int getLevel(int exp) {
        for (int i = 0; i <= maxlevel; i++)
            if (getExpAtLevel(i) > exp)
                return i - 1;


        return -1;
    }

    public int getLevel(org.bukkit.entity.Player p) {
        return getLevel(p.getName());
    }

    public int getLevel(java.lang.String p) {
        return getLevel(getUser(p).getExp());
    }

    public java.lang.String getLevelKey() {
        return levelKey;
    }

    public java.lang.String getLevelName() {
        return levelName;
    }

    private java.lang.String getMathResult(java.lang.String form, double x, java.lang.String completForm) {
        if ((x <= 0) || (x > maxlevel))
            return "0";

        form = form.replace("x", java.lang.String.valueOf(x));
        form = form.replaceAll("[-]", "+-");
        while (form.contains("(") && form.contains(")")) {
            int indexFirst = -1;
            int indexLast = -1;
            int countOpen = 0;
            for (int i = 0; i < form.toCharArray().length; i++) {
                if (form.toCharArray()[i] == '(')
                    if (indexFirst == (-1))
                        indexFirst = i;
                    else
                        countOpen++;


                if (form.toCharArray()[i] == ')')
                    if ((indexLast == (-1)) && (countOpen == 0))
                        indexLast = i;
                    else
                        countOpen--;


            }
            if ((indexFirst != (-1)) && (indexLast != (-1)))
                form = form.replace(form.substring(indexFirst, indexLast + 1), getMathResult(form.substring(indexFirst + 1, indexLast), x, completForm));

        } 
        java.lang.String[] array = form.split("[+]");
        for (java.lang.String s : array)
            if (s.contains("*"))
                form = form.replace(s, java.lang.String.valueOf(java.lang.Double.valueOf(getMathResult(s.substring(0, s.indexOf("*")), x, completForm)) * java.lang.Double.valueOf(getMathResult(s.substring(s.indexOf("*") + 1), x, completForm))));
            else if (s.contains("/"))
                form = form.replace(s, java.lang.String.valueOf(java.lang.Double.valueOf(s.substring(0, s.indexOf("/"))) / java.lang.Double.valueOf(getMathResult(s.substring(s.indexOf("/") + 1), x, completForm))));
            else if (s.contains("log"))
                form = form.replace(s, java.lang.String.valueOf(java.lang.Math.log(java.lang.Double.valueOf(getMathResult(s.replace("log", ""), x, completForm)))));
            else if (s.contains("sqrt"))
                form = form.replace(s, java.lang.String.valueOf(java.lang.Math.sqrt(java.lang.Double.valueOf(getMathResult(s.replace("sqrt", ""), x, completForm)))));
            else if (s.contains("recur"))
                form = form.replace(s, java.lang.String.valueOf(getExpAtLevel(java.lang.Double.valueOf(s.replace("recur", "")).intValue())));
            else if (s.contains("^"))
                form = form.replace(s, java.lang.String.valueOf(java.lang.Math.pow(java.lang.Double.valueOf(getMathResult(s.substring(0, s.indexOf("^")), x, completForm)), java.lang.Double.valueOf(getMathResult(s.substring(s.indexOf("^") + 1), x, completForm)))));


        double result = 0;
        for (java.lang.String s : form.split("[+]"))
            try {
                result += java.lang.Double.valueOf(s);
            } catch (java.lang.NumberFormatException e) {
                result += 0;
            }

        return java.lang.String.valueOf(result);
    }

    public de.craftlancer.skilllevels.LevelUser getUser(org.bukkit.entity.Player p) {
        return getUser(p.getName());
    }

    public de.craftlancer.skilllevels.LevelUser getUser(java.lang.String name) {
        if (!hasUser(name))
            addUser(name, 0, 0);

        return userMap.get(name.toLowerCase());
    }

    public java.util.Map<java.lang.String, de.craftlancer.skilllevels.LevelUser> getPlayers() {
        return userMap;
    }

    public java.lang.String getPointKey() {
        return pointKey;
    }

    public java.lang.String getPointName() {
        return pointName;
    }

    public int getPoints(org.bukkit.entity.Player p) {
        return getPoints(p.getName());
    }

    public int getPoints(java.lang.String p) {
        return (getLevel(p) * getPointsPerLevel()) - getUser(p).getUsedPoints();
    }

    public int getPointsPerLevel() {
        return pointsperlevel;
    }

    public java.lang.String getSystemName() {
        return systemName;
    }

    public int getUsedPoints(org.bukkit.entity.Player p) {
        return getUsedPoints(p.getName());
    }

    public int getUsedPoints(java.lang.String p) {
        return getUser(p).getUsedPoints();
    }

    public void handleAction(de.craftlancer.skilllevels.LevelAction action, java.lang.String name, int amount, java.lang.String user) {
        name = name.toUpperCase();
        if ((!xpperaction.containsKey(action)) || (!xpperaction.get(action).containsKey(name)))
            return;

        if (!hasUser(user))
            addUser(user, 0, 0);

        int initlevel = getLevel(user);
        addExp(xpperaction.get(action).get(name) * amount, user);
        int newlevel = getLevel(user);
        boolean isPlayer = org.bukkit.Bukkit.getPlayerExact(user) != null;
        if (newlevel > initlevel) {
            if (isPlayer) {
                java.lang.String msg = LevelLanguage.LEVEL_UP;
                msg = msg.replace("%level%", java.lang.String.valueOf(newlevel));
                msg = msg.replace("%systemname%", getSystemName());
                org.bukkit.Bukkit.getPlayerExact(user).sendMessage(msg);
            }
            org.bukkit.Bukkit.getPluginManager().callEvent(new de.craftlancer.skilllevels.event.SkillLevelUpEvent(initlevel, newlevel, user, isPlayer, this));
        }
        org.bukkit.Bukkit.getPluginManager().callEvent(new de.craftlancer.skilllevels.event.SkillExpChangeEvent(user, isPlayer, amount, action, this));
    }

    public boolean hasUser(org.bukkit.entity.Player p) {
        return hasUser(p.getName());
    }

    public boolean hasUser(java.lang.String user) {
        return userMap.containsKey(user.toLowerCase());
    }

    private void preCalcLevels() {
        preCalc = new int[maxlevel + 1];
        for (int i = 0; i <= maxlevel; i++)
            preCalc[i] = calcExpAtLevel(i);

    }

    public void revokeExp(int amount, org.bukkit.entity.Player p) {
        revokeExp(amount, p.getName());
    }

    public void revokeExp(int amount, java.lang.String p) {
        getUser(p).revokeExp(amount);
    }

    public void revokeLevel(int level, org.bukkit.entity.Player p) {
        revokeLevel(level, p.getName());
    }

    public void revokeLevel(int level, java.lang.String p) {
        int init = getLevel(p);
        getUser(p).revokeExp(getExpAtLevel(init + level) - getExpAtLevel(init));
    }

    public void revokeUsedPoints(int amount, org.bukkit.entity.Player p) {
        revokeUsedPoints(amount, p.getName());
    }

    public void revokeUsedPoints(int amount, java.lang.String p) {
        getUser(p).revokeUsedPoints(amount);
    }

    public void setExp(int amount, org.bukkit.entity.Player p) {
        setExp(amount, p.getName());
    }

    public void setExp(int amount, java.lang.String p) {
        getUser(p).setExp(amount);
    }

    public void setExpKey(java.lang.String expKey) {
        this.expKey = expKey;
    }

    public void setExpName(java.lang.String expName) {
        this.expName = expName;
    }

    public void setFormula(java.lang.String formula) {
        this.formula = formula;
    }

    public void setLevel(int level, org.bukkit.entity.Player p) {
        setLevel(level, p.getName());
    }

    public void setLevel(int level, java.lang.String p) {
        getUser(p).setExp(getExpAtLevel(level));
    }

    public void setLevelKey(java.lang.String levelKey) {
        this.levelKey = levelKey;
    }

    public void setLevelName(java.lang.String levelName) {
        this.levelName = levelName;
    }

    public void setPointKey(java.lang.String pointKey) {
        this.pointKey = pointKey;
    }

    public void setPointName(java.lang.String pointName) {
        this.pointName = pointName;
    }

    public void setPointsPerLevel(int pointsperlevel) {
        this.pointsperlevel = pointsperlevel;
    }

    public void setSystemName(java.lang.String systemName) {
        this.systemName = systemName;
    }

    public void setUsedPoints(int amount, org.bukkit.entity.Player p) {
        setUsedPoints(amount, p.getName());
    }

    public void setUsedPoints(int amount, java.lang.String p) {
        getUser(p).setUsedPoints(amount);
    }

    public java.lang.String getSystemKey() {
        return systemKey;
    }
}