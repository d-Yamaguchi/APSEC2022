package au.com.mineauz.MobHunting.storage.asynch;
import au.com.mineauz.MobHunting.storage.AchievementStore;
import au.com.mineauz.MobHunting.storage.DataStore;
import au.com.mineauz.MobHunting.storage.DataStoreException;
public class AchievementRetrieverTask implements au.com.mineauz.MobHunting.storage.asynch.DataStoreTask<java.util.Set<au.com.mineauz.MobHunting.storage.AchievementStore>> {
    public enum Mode {

        All,
        Completed,
        InProgress;}

    private au.com.mineauz.MobHunting.storage.asynch.AchievementRetrieverTask.Mode mMode;

    private org.bukkit.OfflinePlayer mPlayer;

    public AchievementRetrieverTask(au.com.mineauz.MobHunting.storage.asynch.AchievementRetrieverTask.Mode mode, org.bukkit.OfflinePlayer player) {
        mMode = mode;
        mPlayer = player;
    }

    public java.util.Set<au.com.mineauz.MobHunting.storage.AchievementStore> run(au.com.mineauz.MobHunting.storage.DataStore store) throws au.com.mineauz.MobHunting.storage.DataStoreException {
        java.util.Set<au.com.mineauz.MobHunting.storage.AchievementStore> achievements = store.loadAchievements(store.loadAchievements(mPlayer));
        __SmPLUnsupported__(0);
        return achievements;
    }

    @java.lang.Override
    public boolean readOnly() {
        return true;
    }
}