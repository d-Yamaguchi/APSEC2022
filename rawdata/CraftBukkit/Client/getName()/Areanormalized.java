/**
 * Gets the message associated with a player flag.
 *
 * @param flag
 * 		The flag to retrieve the message for.
 * @param parse
 * 		True if you wish to populate instances of {AreaType}, {Owner},
 * 		and {World} and translate color codes
 * @return The message associated with the flag.
 */
// API
@java.lang.SuppressWarnings("WeakerAccess")
public java.lang.String getMessage(Flag flag, boolean parse) {
    org.apache.commons.lang.Validate.notNull(flag);
    if (message == null) {
        message = new Default(getWorld()).getMessage(flag);
    }
     _CVAR0 = Flags.getDataStore();
    io.github.alshain01.flags.area.Area _CVAR1 = this;
    io.github.alshain01.flags.area.Flag _CVAR2 = flag;
    java.lang.String message = _CVAR0.readMessage(_CVAR1, _CVAR2);
    if (parse) {
        org.bukkit.World _CVAR5 = getWorld();
        java.lang.String _CVAR3 = message;
        java.lang.String _CVAR4 = "{World}";
        java.lang.String _CVAR6 = _CVAR5.getName();
        java.lang.String _CVAR7 = _CVAR3.replace(_CVAR4, _CVAR6);
        java.lang.String _CVAR8 = "{AreaName}";
        java.lang.String _CVAR9 = getName();
        io.github.alshain01.flags.area.CuboidType _CVAR12 = getCuboidType();
         _CVAR13 = _CVAR12.getCuboidName();
        java.lang.String _CVAR10 = _CVAR7.replace(_CVAR8, _CVAR9);
        java.lang.String _CVAR11 = "{AreaType}";
         _CVAR14 = _CVAR13.toLowerCase();
        java.lang.Object _CVAR17 = getOwnerNames().toArray()[0];
        java.lang.String _CVAR15 = _CVAR10.replace(_CVAR11, _CVAR14);
        java.lang.String _CVAR16 = "{Owner}";
        java.lang.String _CVAR18 = _CVAR17.toString();
        java.lang.String _CVAR19 = _CVAR15.replace(_CVAR16, _CVAR18);
        message = _CVAR19;
        message = org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
    return message;
}