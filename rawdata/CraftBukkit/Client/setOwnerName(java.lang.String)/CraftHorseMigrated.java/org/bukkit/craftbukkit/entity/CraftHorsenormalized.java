@java.lang.Override
public void setOwner(org.bukkit.entity.AnimalTamer owner) {
    if ((owner != null) && (!"".equals(owner.getName()))) {
        setTamed(true);
        getHandle().setPathEntity(null);
        getHandle().setOwnerUUID("");
    } else {
        setTamed(false);
        java.lang.String _CVAR0 = "";
        setOwnerName(_CVAR0);
    }
}