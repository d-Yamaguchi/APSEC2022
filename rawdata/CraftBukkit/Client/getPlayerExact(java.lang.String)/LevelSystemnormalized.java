public void handleAction(de.craftlancer.skilllevels.LevelAction action, java.lang.String name, int amount, java.lang.String user) {
    name = name.toUpperCase();
    if ((!xpperaction.containsKey(action)) || (!xpperaction.get(action).containsKey(name))) {
        return;
    }
    if (!hasUser(user)) {
        addUser(user, 0, 0);
    }
    int initlevel = getLevel(user);
    addExp(xpperaction.get(action).get(name) * amount, user);
    int newlevel = getLevel(user);
    <nulltype> _CVAR0 = null;
    boolean isPlayer = org.bukkit.Bukkit.getPlayerExact(user) != _CVAR0;
    if (newlevel > initlevel) {
        if (isPlayer) {
            msg = msg.replace("%level%", java.lang.String.valueOf(newlevel));
            msg = msg.replace("%systemname%", getSystemName());
            java.lang.String _CVAR1 = user;
            java.lang.String msg = LevelLanguage.LEVEL_UP;
            org.bukkit.entity.Player _CVAR2 = org.bukkit.Bukkit.getPlayerExact(_CVAR1);
            java.lang.String _CVAR3 = msg;
            _CVAR2.sendMessage(_CVAR3);
        }
        org.bukkit.Bukkit.getPluginManager().callEvent(new de.craftlancer.skilllevels.event.SkillLevelUpEvent(initlevel, newlevel, user, isPlayer, this));
    }
    org.bukkit.Bukkit.getPluginManager().callEvent(new de.craftlancer.skilllevels.event.SkillExpChangeEvent(user, isPlayer, amount, action, this));
}