public void setOwner(org.bukkit.entity.AnimalTamer tamer) {
    if (tamer != null) {
        setTamed(true);
        getHandle().setPathEntity(null);
        org.bukkit.entity.AnimalTamer _CVAR0 = tamer;
        java.lang.String _CVAR1 = _CVAR0.getName();
        setOwnerName(_CVAR1);
    } else {
        setTamed(false);
        setOwnerName("");
    }
}