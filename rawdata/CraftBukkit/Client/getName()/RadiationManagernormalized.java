public void saveRadiation(org.bukkit.entity.Player p) throws java.sql.SQLException {
    db.open();
    if (!p.getMetadata(apocalyptic.getMetadataKey()).isEmpty()) {
        org.bukkit.entity.Player _CVAR1 = p;
        java.lang.String _CVAR2 = _CVAR1.getName();
        java.lang.String _CVAR3 = "SELECT COUNT(*) AS \"exists\" FROM radiationLevels WHERE player=\"" + _CVAR2;
        lib.PatPeter.SQLibrary.Database _CVAR0 = db;
        java.lang.String _CVAR4 = _CVAR3 + "\";";
         _CVAR5 = _CVAR0.query(_CVAR4);
        java.lang.String _CVAR6 = "exists";
         _CVAR7 = _CVAR5.getInt(_CVAR6);
         _CVAR8 = _CVAR7 > 0;
        if () {
            org.bukkit.entity.Player _CVAR10 = p;
            java.lang.String _CVAR11 = _CVAR10.getName();
             _CVAR12 = (("UPDATE radiationLevels SET level=" + p.getMetadata(apocalyptic.getMetadataKey()).get(0).asDouble()) + " WHERE player=\"") + _CVAR11;
            lib.PatPeter.SQLibrary.Database _CVAR9 = db;
             _CVAR13 = _CVAR12 + "\";";
            // $NON-NLS-3$
            _CVAR9.query(_CVAR13);
        } else {
            org.bukkit.entity.Player _CVAR15 = p;
            java.lang.String _CVAR16 = _CVAR15.getName();
            java.lang.String _CVAR17 = "INSERT INTO radiationLevels (player, level) VALUES (\"" + _CVAR16;
            java.lang.String _CVAR18 = _CVAR17 + "\", ";
             _CVAR19 = _CVAR18 + p.getMetadata(apocalyptic.getMetadataKey()).get(0).asDouble();
            lib.PatPeter.SQLibrary.Database _CVAR14 = db;
             _CVAR20 = _CVAR19 + ");";
            _CVAR14.query(_CVAR20);// $NON-NLS-3$

        }
    }
    db.close();
}