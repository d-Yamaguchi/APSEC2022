@java.lang.Override
public void deleteItems(final org.bukkit.entity.Player player) {
    java.io.File file = getInventoryFile(player);
    if (!file.delete()) {
        li.cryx.minecraft.death.persist.flat.PersistenceFlatFile _CVAR0 = plugin;
        org.bukkit.entity.Player _CVAR3 = player;
        java.lang.String _CVAR4 = _CVAR3.getName();
         _CVAR1 = _CVAR0.getLogger();
        java.util.logging.Level _CVAR2 = java.util.logging.Level.SEVERE;
        java.lang.String _CVAR5 = "Unable to delete inventory for " + _CVAR4;
        _CVAR1.log(_CVAR2, _CVAR5);
    }
}