// TODO: In 1.7.6+ OfflinePlayer lookup should be by UUID and store it like it does the name now
public java.util.UUID getUniqueId() {
    net.minecraft.server.NBTTagCompound data = getData();
    if (data == null) {
        return null;
    }
    if (data.hasKeyOfType("UUIDMost", 4) && data.hasKeyOfType("UUIDLeast", 4)) {
        return new java.util.UUID(data.getLong("UUIDMost"), data.getLong("UUIDLeast"));
    }
    return null;
}