public org.bukkit.entity.AnimalTamer getOwner() {
    java.lang.String _CVAR0 = "";
    java.lang.String _CVAR1 = getOwnerName();
    boolean _CVAR2 = _CVAR0.equals(_CVAR1);
    if () {
        return null;
    }
    org.bukkit.Server _CVAR3 = getServer();
    java.lang.String _CVAR4 = getOwnerName();
    org.bukkit.entity.AnimalTamer owner = _CVAR3.getPlayerExact(_CVAR4);
    if (owner == null) {
        org.bukkit.Server _CVAR5 = getServer();
        java.lang.String _CVAR6 = getOwnerName();
        org.bukkit.OfflinePlayer _CVAR7 = _CVAR5.getOfflinePlayer(_CVAR6);
        owner = _CVAR7;
    }
    return owner;
}