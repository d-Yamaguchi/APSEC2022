@java.lang.Override
public org.bukkit.entity.AnimalTamer getOwner() {
    <nulltype> _CVAR0 = null;
    boolean _CVAR1 = getOwnerName() == _CVAR0;
    java.lang.String _CVAR3 = "";
    java.lang.String _CVAR4 = getOwnerName();
    boolean _CVAR5 = _CVAR3.equals(_CVAR4);
    boolean _CVAR2 = _CVAR1 || _CVAR5;
    if () {
        return null;
    }
    org.bukkit.Server _CVAR6 = getServer();
    java.lang.String _CVAR7 = getOwnerName();
    org.bukkit.OfflinePlayer _CVAR8 = _CVAR6.getOfflinePlayer(_CVAR7);
    return _CVAR8;
}