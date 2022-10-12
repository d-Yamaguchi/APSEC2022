private boolean standingOnSlab(org.bukkit.Location l) {
    org.bukkit.Location _CVAR0 = l;
    org.bukkit.block.Block _CVAR1 = _CVAR0.getBlock();
    int _CVAR2 = _CVAR1.getTypeId();
    boolean _CVAR3 = isSlab(_CVAR2);
    boolean _CVAR4 = _CVAR3 && ((l.getY() % 1) <= 0.51);
    return _CVAR4;
}