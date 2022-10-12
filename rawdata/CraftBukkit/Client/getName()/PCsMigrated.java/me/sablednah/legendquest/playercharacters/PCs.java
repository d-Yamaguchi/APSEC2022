package me.sablednah.legendquest.playercharacters;
import me.sablednah.legendquest.Main;
public class PCs {
    public class DelayedWrite implements java.lang.Runnable {
        public java.lang.String name;

        public DelayedWrite(final java.lang.String n) {
            this.name = n;
        }

        @java.lang.Override
        public void run() {
            savePlayer(name);
        }
    }

    public me.sablednah.legendquest.Main lq;

    public java.util.Map<java.lang.String, me.sablednah.legendquest.playercharacters.PC> activePlayers = new java.util.HashMap<java.lang.String, me.sablednah.legendquest.playercharacters.PC>();

    public PCs(final me.sablednah.legendquest.Main p) {
        this.lq = p;
        for (final org.bukkit.entity.Player player : lq.getServer().getOnlinePlayers()) {
            java.lang.String pName = store.loadAchievements(mPlayer);
            final me.sablednah.legendquest.playercharacters.PC pc = getPC(player);
            addPlayer(pName, pc);
        }
    }

    public void addPlayer(final java.lang.String pName, final me.sablednah.legendquest.playercharacters.PC pc) {
        activePlayers.put(pName, pc);
    }

    public me.sablednah.legendquest.playercharacters.PC getPC(final org.bukkit.OfflinePlayer p) {
        if (p != null) {
            return getPC(p.getName());
        }
        return null;
    }

    public me.sablednah.legendquest.playercharacters.PC getPC(final org.bukkit.entity.Player p) {
        if (p != null) {
            return getPC(p.getName());
        }
        return null;
    }

    public me.sablednah.legendquest.playercharacters.PC getPC(final java.lang.String name) {
        me.sablednah.legendquest.playercharacters.PC pc = null;
        if (name != null) {
            pc = activePlayers.get(name);
            if (pc == null) {
                pc = loadPlayer(name);
            }
        }
        return pc;
    }

    private me.sablednah.legendquest.playercharacters.PC loadPlayer(final java.lang.String pName) {
        // Load player from disk. if not found make new blank
        me.sablednah.legendquest.playercharacters.PC pc = null;
        pc = lq.datasync.getData(pName);
        if (pc == null) {
            pc = new me.sablednah.legendquest.playercharacters.PC(lq, pName);
        }
        return pc;
    }

    public void removePlayer(final java.lang.String pName) {
        savePlayer(pName);
        activePlayers.remove(pName);
    }

    public void savePlayer(final me.sablednah.legendquest.playercharacters.PC pc) {
        lq.datasync.addWrite(pc);
    }

    public void savePlayer(final java.lang.String name) {
        savePlayer(getPC(name));
    }

    public void scheduleUpdate(final java.lang.String pName) {
        org.bukkit.Bukkit.getServer().getScheduler().runTaskLater(lq, new me.sablednah.legendquest.playercharacters.PCs.DelayedWrite(pName), 4L);
    }
}