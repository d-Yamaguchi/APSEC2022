public void setOwner(org.bukkit.entity.AnimalTamer tamer) {
    if (tamer != null) {
        setTamed(true);
        getHandle().setPathEntity(null);
        setOwnerUUID(tamer.getUniqueId());
    } else {
        setTamed(false);
        java.lang.String _CVAR0 = "";
        setOwnerName(_CVAR0);
    }
}