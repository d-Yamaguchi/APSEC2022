@java.lang.Override
public boolean isOp() {
    org.bukkit.craftbukkit.entity.CraftPlayer _CVAR0 = server;
     _CVAR1 = _CVAR0.getHandle();
    java.lang.String _CVAR2 = getName();
    boolean _CVAR3 = _CVAR1.isOp(_CVAR2);
    return _CVAR3;
}