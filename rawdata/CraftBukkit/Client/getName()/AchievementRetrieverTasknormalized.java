public java.util.Set<au.com.mineauz.MobHunting.storage.AchievementStore> run(au.com.mineauz.MobHunting.storage.DataStore store) throws au.com.mineauz.MobHunting.storage.DataStoreException {
    org.bukkit.OfflinePlayer _CVAR1 = mPlayer;
    au.com.mineauz.MobHunting.storage.DataStore _CVAR0 = store;
    java.lang.String _CVAR2 = _CVAR1.getName();
    java.util.Set<au.com.mineauz.MobHunting.storage.AchievementStore> achievements = _CVAR0.loadAchievements(_CVAR2);
    switch (mMode) {
        case All :
            break;
        case Completed :
            {
                java.util.Iterator<au.com.mineauz.MobHunting.storage.AchievementStore> it = achievements.iterator();
                while (it.hasNext()) {
                    au.com.mineauz.MobHunting.storage.AchievementStore achievement = it.next();
                    if (achievement.progress != (-1)) {
                        it.remove();
                    }
                } 
                break;
            }
        case InProgress :
            {
                java.util.Iterator<au.com.mineauz.MobHunting.storage.AchievementStore> it = achievements.iterator();
                while (it.hasNext()) {
                    au.com.mineauz.MobHunting.storage.AchievementStore achievement = it.next();
                    if (achievement.progress == (-1)) {
                        it.remove();
                    }
                } 
                break;
            }
    }
    return achievements;
}