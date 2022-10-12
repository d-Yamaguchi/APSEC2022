public static boolean isWorldEnabled(org.bukkit.entity.Player p) {
    if (p == null) {
        return false;
    }
    if (p.getWorld() == null) {
        return false;
    }
    if ((com.ancientshores.AncientRPG.Experience.AncientRPGExperience.worlds.length == 0) || ((com.ancientshores.AncientRPG.Experience.AncientRPGExperience.worlds.length >= 1) && ((com.ancientshores.AncientRPG.Experience.AncientRPGExperience.worlds[0] == null) || com.ancientshores.AncientRPG.Experience.AncientRPGExperience.worlds[0].equals("")))) {
        return true;
    }
    for (java.lang.String s : com.ancientshores.AncientRPG.Experience.AncientRPGExperience.worlds) {
        if (s == null) {
            continue;
        }
        org.bukkit.entity.Player _CVAR0 = p;
        org.bukkit.World _CVAR1 = _CVAR0.getWorld();
        java.lang.String _CVAR2 = _CVAR1.getName();
        java.lang.String _CVAR3 = s;
        boolean _CVAR4 = _CVAR2.equalsIgnoreCase(_CVAR3);
        boolean _CVAR5 = _CVAR4 || s.equalsIgnoreCase("all");
        if () {
            return true;
        }
    }
    return false;
}