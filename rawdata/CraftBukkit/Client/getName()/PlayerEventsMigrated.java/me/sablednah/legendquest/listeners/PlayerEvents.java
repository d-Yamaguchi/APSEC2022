package me.sablednah.legendquest.listeners;
import me.sablednah.legendquest.Main;
import me.sablednah.legendquest.events.LevelUpEvent;
import me.sablednah.legendquest.playercharacters.PC;
import me.sablednah.legendquest.utils.SetExp;
public class PlayerEvents implements org.bukkit.event.Listener {
    public class delayedSpawn implements java.lang.Runnable {
        public int xp;

        public org.bukkit.entity.Player player;

        public delayedSpawn(final int xp, final org.bukkit.entity.Player player) {
            this.xp = xp;
            this.player = player;
        }

        @java.lang.Override
        public void run() {
            java.lang.String pName = store.loadAchievements(mPlayer);
            final me.sablednah.legendquest.playercharacters.PC pc = lq.players.getPC(pName);
            pc.setXP(xp);
            lq.players.savePlayer(pc);
        }
    }

    public me.sablednah.legendquest.Main lq;

    public PlayerEvents(final me.sablednah.legendquest.Main p) {
        this.lq = p;
    }

    // preserve XP on death...
    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOW)
    public void onDeath(final org.bukkit.event.entity.PlayerDeathEvent event) {
        event.setDroppedExp(0);
        event.setKeepLevel(true);
    }

    // set to monitor - we're not gonna change the login, only load our data
    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR, ignoreCancelled = true)
    public void onJoin(final org.bukkit.event.player.PlayerJoinEvent event) {
        final org.bukkit.entity.Player p = event.getPlayer();
        final java.lang.String pName = p.getName();
        final me.sablednah.legendquest.playercharacters.PC pc = lq.players.getPC(pName);
        lq.players.addPlayer(pName, pc);
        p.setTotalExperience(pc.currentXP);
        p.setMaxHealth(pc.maxHP);
        p.setHealth(pc.health);
        pc.healthCheck();
    }

    // set to monitor - we can't change the quit - just want to clean our data up.
    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
    public void onQuit(final org.bukkit.event.player.PlayerQuitEvent event) {
        final java.lang.String pName = event.getPlayer().getName();
        lq.players.removePlayer(pName);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOW)
    public void onRespawn(final org.bukkit.event.player.PlayerRespawnEvent event) {
        final org.bukkit.entity.Player p = event.getPlayer();
        final java.lang.String pName = p.getName();
        final me.sablednah.legendquest.playercharacters.PC pc = lq.players.getPC(pName);
        lq.logWarn("currentXP: " + pc.currentXP);
        final int currentXP = me.sablednah.legendquest.utils.SetExp.getTotalExperience(p);
        lq.logWarn("totxp: " + currentXP);
        final int xpLoss = ((int) (currentXP * (lq.configMain.percentXpLossRespawn / 100)));
        lq.logWarn("xpLoss: " + xpLoss);
        final int newXp = currentXP - xpLoss;
        pc.setXP(newXp);
        lq.players.savePlayer(pc);
        lq.getServer().getScheduler().runTaskLater(lq, new me.sablednah.legendquest.listeners.PlayerEvents.delayedSpawn(newXp, p), 5);
    }

    // track EXP changes - and halve then if dual class
    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOW)
    public void onXPChange(final org.bukkit.event.player.PlayerExpChangeEvent event) {
        int xpAmount = event.getAmount();
        final org.bukkit.entity.Player p = event.getPlayer();
        final java.lang.String pName = p.getName();
        final me.sablednah.legendquest.playercharacters.PC pc = lq.players.getPC(pName);
        // half xp gain for dual class
        if (pc.subClass != null) {
            xpAmount = xpAmount / 2;
        }
        pc.setXP(me.sablednah.legendquest.utils.SetExp.getTotalExperience(p) + xpAmount);
        lq.players.addPlayer(pName, pc);
        lq.players.savePlayer(pc);
        if (xpAmount >= p.getExpToLevel()) {
            pc.scheduleHealthCheck();
            final me.sablednah.legendquest.events.LevelUpEvent e = new me.sablednah.legendquest.events.LevelUpEvent(p, p.getLevel() + 1, pc);
            org.bukkit.Bukkit.getServer().getPluginManager().callEvent(e);
        }
    }
}