@java.lang.Override
public java.lang.String toVariableNameString(final org.bukkit.entity.Entity e) {
    org.bukkit.entity.Entity _CVAR0 = e;
    java.util.UUID _CVAR1 = _CVAR0.getUniqueId();
    java.lang.String _CVAR2 = _CVAR1.toString();
    java.lang.String _CVAR3 = "entity:" + _CVAR2;
    return _CVAR3;
}