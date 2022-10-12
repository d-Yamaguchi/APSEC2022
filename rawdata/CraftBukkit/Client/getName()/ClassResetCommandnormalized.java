public static void resetCommand(org.bukkit.entity.Player p) {
    org.bukkit.entity.Player _CVAR0 = p;
    java.lang.String _CVAR1 = _CVAR0.getName();
    com.ancientshores.AncientRPG.PlayerData pd = com.ancientshores.AncientRPG.PlayerData.getPlayerData(_CVAR1);
    com.ancientshores.AncientRPG.Classes.AncientRPGClass oldClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
    com.ancientshores.AncientRPG.Classes.Commands.ClassResetCommand.reset(p, oldClass, pd);
    p.sendMessage(com.ancientshores.AncientRPG.AncientRPG.brand2 + "Successfully reset your class");
}