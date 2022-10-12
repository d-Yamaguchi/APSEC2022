public boolean isOp() {
    org.bukkit.craftbukkit.CraftServer _CVAR0 = server;
    java.lang.String _CVAR2 = getName();
     _CVAR1 = _CVAR0.getHandle();
    java.lang.String _CVAR3 = _CVAR2.toLowerCase();
    boolean _CVAR4 = _CVAR1.isOp(_CVAR3);
    return _CVAR4;
}