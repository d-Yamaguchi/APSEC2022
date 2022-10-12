public org.bukkit.OfflinePlayer getOfflinePlayer(java.lang.String name, boolean search) {
    org.apache.commons.lang.Validate.notNull(name, "Name cannot be null");
    java.lang.String _CVAR0 = name;
    org.bukkit.OfflinePlayer result = getPlayerExact(_CVAR0);
    java.lang.String lname = name.toLowerCase();
    if (result == null) {
        result = offlinePlayers.get(lname);
        if (result == null) {
            if (search) {
                net.minecraft.server.WorldNBTStorage storage = ((net.minecraft.server.WorldNBTStorage) (console.worlds.get(0).getDataManager()));
                for (java.lang.String dat : storage.getPlayerDir().list(new org.bukkit.craftbukkit.util.DatFileFilter())) {
                    java.lang.String datName = dat.substring(0, dat.length() - 4);
                    if (datName.equalsIgnoreCase(name)) {
                        name = datName;
                        break;
                    }
                }
            }
            result = new org.bukkit.craftbukkit.CraftOfflinePlayer(this, name);
            offlinePlayers.put(lname, result);
        }
    } else {
        offlinePlayers.remove(lname);
    }
    return result;
}