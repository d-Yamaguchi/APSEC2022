@java.lang.Override
public java.lang.String getPrefix(org.bukkit.OfflinePlayer player) {
    org.bukkit.OfflinePlayer _CVAR0 = player;
    java.lang.String _CVAR1 = _CVAR0.getName();
    java.lang.String name = _CVAR1.toLowerCase();
    if (users.containsKey(name)) {
        java.lang.String prefix = users.get(name).prefix;
        if (prefix.length() > 0) {
            return prefix;
        }
    }
    if (groups.containsKey(getUserGroup(player).toLowerCase())) {
        return groups.get(getUserGroup(player).toLowerCase()).prefix;
    }
    return "";
}