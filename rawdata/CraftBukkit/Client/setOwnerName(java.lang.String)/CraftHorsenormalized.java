@java.lang.Override
public void setOwner(org.bukkit.entity.AnimalTamer owner) {
    if ((owner != null) && (!"".equals(owner.getName()))) {
        setTamed(true);
        getHandle().setPathEntity(null);
        org.bukkit.entity.AnimalTamer _CVAR0 = owner;
        java.lang.String _CVAR1 = _CVAR0.getName();
        setOwnerName(_CVAR1);
    } else {
        setTamed(false);
        java.lang.String _CVAR2 = "";
        setOwnerName(_CVAR2);
    }
}