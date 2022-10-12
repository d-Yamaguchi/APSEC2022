@java.lang.Override
public org.bukkit.entity.Player getOwner() {
    if (this.owner == null) {
        return null;
    }
    java.lang.String _CVAR0 = owner;
    org.bukkit.entity.Player _CVAR1 = org.bukkit.Bukkit.getPlayerExact(_CVAR0);
    return _CVAR1;
}