public boolean play(byte instrument, byte note) {
    org.bukkit.block.Block block = getBlock();
    if (block.getType() == org.bukkit.Material.NOTE_BLOCK) {
        org.bukkit.craftbukkit.CraftWorld _CVAR0 = world;
        org.bukkit.block.Block _CVAR5 = block;
         _CVAR1 = _CVAR0.getHandle();
        int _CVAR2 = getX();
        int _CVAR3 = getY();
        int _CVAR4 = getZ();
        int _CVAR6 = _CVAR5.getTypeId();
        byte _CVAR7 = instrument;
        byte _CVAR8 = note;
        _CVAR1.playNote(_CVAR2, _CVAR3, _CVAR4, _CVAR6, _CVAR7, _CVAR8);
        return true;
    } else {
        return false;
    }
}