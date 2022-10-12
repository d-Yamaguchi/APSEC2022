package com.ancientshores.AncientRPG.Classes.Commands;
import com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent;
import com.ancientshores.AncientRPG.AncientRPG;
import com.ancientshores.AncientRPG.Classes.AncientRPGClass;
import com.ancientshores.AncientRPG.Experience.AncientRPGExperience;
import com.ancientshores.AncientRPG.PlayerData;
public class ClassResetCommand {
    public static void resetCommand(org.bukkit.entity.Player p) {
        com.ancientshores.AncientRPG.PlayerData pd = com.ancientshores.AncientRPG.PlayerData.getPlayerData(store.loadAchievements(mPlayer));
        com.ancientshores.AncientRPG.Classes.AncientRPGClass oldClass = AncientRPGClass.classList.get(pd.getClassName().toLowerCase());
        com.ancientshores.AncientRPG.Classes.Commands.ClassResetCommand.reset(p, oldClass, pd);
        p.sendMessage(com.ancientshores.AncientRPG.AncientRPG.brand2 + "Successfully reset your class");
    }

    public static void reset(org.bukkit.entity.Player p, com.ancientshores.AncientRPG.Classes.AncientRPGClass oldClass, com.ancientshores.AncientRPG.PlayerData pd) {
        com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent classevent = new com.ancientshores.AncientRPG.API.AncientRPGClassChangeEvent(p, oldClass, null);
        org.bukkit.Bukkit.getPluginManager().callEvent(classevent);
        if (classevent.isCancelled()) {
            return;
        }
        if (((oldClass != null) && (oldClass.permGroup != null)) && (!oldClass.permGroup.equals(""))) {
            if (com.ancientshores.AncientRPG.AncientRPG.permissionHandler != null) {
                AncientRPG.permissionHandler.playerRemoveGroup(p, oldClass.permGroup);
                for (java.util.Map.Entry<java.lang.String, com.ancientshores.AncientRPG.Classes.AncientRPGClass> entry : oldClass.stances.entrySet()) {
                    AncientRPG.permissionHandler.playerRemoveGroup(p, entry.getValue().permGroup);
                }
            }
            if (com.ancientshores.AncientRPG.Experience.AncientRPGExperience.isEnabled()) {
                pd.getClassLevels().put(oldClass.name, pd.getXpSystem().xp);
            }
        }
        if (com.ancientshores.AncientRPG.Experience.AncientRPGExperience.isEnabled()) {
            if (pd.getClassLevels().get(AncientRPGClass.standardclassName) == null) {
                pd.getClassLevels().put(AncientRPGClass.standardclassName, 0);
            }
            pd.getXpSystem().xp = pd.getClassLevels().get(AncientRPGClass.standardclassName);
            pd.getXpSystem().addXP(0, false);
        }
        pd.setClassName(AncientRPGClass.standardclassName);
        pd.setStance("");
    }
}