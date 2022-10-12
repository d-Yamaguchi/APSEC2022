public boolean isInGroup(org.bukkit.entity.Player player, java.lang.String group) {
    if (permissionHandler != null) {
        org.bukkit.entity.Player _CVAR1 = player;
        org.bukkit.World _CVAR2 = _CVAR1.getWorld();
        org.bukkit.entity.Player _CVAR4 = player;
        com.nijiko.permissions.PermissionHandler _CVAR0 = permissionHandler;
        java.lang.String _CVAR3 = _CVAR2.getName();
        java.lang.String _CVAR5 = _CVAR4.getName();
        java.lang.String _CVAR6 = group;
        boolean _CVAR7 = _CVAR0.inGroup(_CVAR3, _CVAR5, _CVAR6);
        return _CVAR7;
    }
    return false;
}