package mc.alk.arena.controllers;
import mc.alk.arena.BattleArena;
import mc.alk.arena.objects.arenas.Arena;
import mc.alk.arena.objects.options.SpawnOptions;
import mc.alk.arena.objects.spawns.BlockSpawn;
import mc.alk.arena.objects.spawns.ChestSpawn;
import mc.alk.arena.objects.spawns.TimedSpawn;
import mc.alk.arena.serializers.ArenaSerializer;
import mc.alk.arena.serializers.SpawnSerializer;
import mc.alk.arena.util.MessageUtil;
public class ArenaEditor implements org.bukkit.event.Listener {
    int nListening = 0;

    java.lang.Integer timerID;

    java.util.HashMap<java.lang.String, mc.alk.arena.controllers.ArenaEditor.CurrentSelection> selections = new java.util.HashMap<java.lang.String, mc.alk.arena.controllers.ArenaEditor.CurrentSelection>();

    public class CurrentSelection {
        public long lastUsed;

        public mc.alk.arena.objects.arenas.Arena arena;

        public java.lang.Long listeningIndex;

        public mc.alk.arena.objects.options.SpawnOptions listeningOptions;

        CurrentSelection(long used, mc.alk.arena.objects.arenas.Arena arena) {
            this.lastUsed = used;
            this.arena = arena;
        }

        public void updateCurrentSelection() {
            lastUsed = java.lang.System.currentTimeMillis();
        }

        public long getLastUsed() {
            return lastUsed;
        }

        public void setLastUsed(long lastUsed) {
            this.lastUsed = lastUsed;
        }

        public mc.alk.arena.objects.arenas.Arena getArena() {
            return arena;
        }

        public void setArena(mc.alk.arena.objects.arenas.Arena arena) {
            this.arena = arena;
        }

        public void setStartListening(java.lang.Long index, mc.alk.arena.objects.options.SpawnOptions po) {
            startListening();
            listeningIndex = index;
            listeningOptions = po;
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        if () {
            return;
        }
        mc.alk.arena.controllers.ArenaEditor.CurrentSelection cs = selections.get(store.loadAchievements(mPlayer));
        if ((cs.listeningIndex == null) || (cs.getArena() == null))
            return;

        if ((event.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE) && (event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK))
            event.setCancelled(true);

        mc.alk.arena.objects.arenas.Arena a = cs.getArena();
        mc.alk.arena.objects.spawns.BlockSpawn bs;
        if (event.getClickedBlock().getState() instanceof org.bukkit.block.Chest) {
            bs = new mc.alk.arena.objects.spawns.ChestSpawn(event.getClickedBlock(), true);
        } else {
            bs = new mc.alk.arena.objects.spawns.BlockSpawn(event.getClickedBlock(), true);
        }
        mc.alk.arena.objects.spawns.TimedSpawn ts = mc.alk.arena.serializers.SpawnSerializer.createTimedSpawn(bs, cs.listeningOptions);
        a.addTimedSpawn(cs.listeningIndex, ts);
        mc.alk.arena.BattleArena.getBAController().updateArena(a);
        mc.alk.arena.serializers.ArenaSerializer.saveArenas(a.getArenaType().getPlugin());
        mc.alk.arena.util.MessageUtil.sendMessage(event.getPlayer(), (("&2Added block spawn &6" + ts) + "&2 to index=&5") + cs.listeningIndex);
        cs.listeningIndex = null;
        cs.listeningOptions = null;
    }

    void startListening() {
        final mc.alk.arena.controllers.ArenaEditor self = this;
        if (nListening == 0) {
            org.bukkit.Bukkit.getPluginManager().registerEvents(this, mc.alk.arena.BattleArena.getSelf());
        }
        nListening++;
        if (timerID != null) {
            mc.alk.arena.controllers.Scheduler.cancelTask(timerID);
        }
        timerID = /* 30 seconds */
        mc.alk.arena.controllers.Scheduler.scheduleSynchronousTask(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                org.bukkit.event.HandlerList.unregisterAll(self);
                nListening = 0;
            }
        }, 20 * 30L);
    }

    public void setCurrentArena(org.bukkit.command.CommandSender p, mc.alk.arena.objects.arenas.Arena arena) {
        if (selections.containsKey(p.getName())) {
            mc.alk.arena.controllers.ArenaEditor.CurrentSelection cs = selections.get(p.getName());
            cs.setLastUsed(java.lang.System.currentTimeMillis());
            cs.setArena(arena);
        } else {
            mc.alk.arena.controllers.ArenaEditor.CurrentSelection cs = new mc.alk.arena.controllers.ArenaEditor.CurrentSelection(java.lang.System.currentTimeMillis(), arena);
            selections.put(p.getName(), cs);
        }
    }

    public mc.alk.arena.objects.arenas.Arena getArena(org.bukkit.command.CommandSender p) {
        mc.alk.arena.controllers.ArenaEditor.CurrentSelection cs = selections.get(p.getName());
        if (cs == null)
            return null;

        return cs.arena;
    }

    public mc.alk.arena.controllers.ArenaEditor.CurrentSelection getCurrentSelection(org.bukkit.command.CommandSender sender) {
        return selections.get(sender.getName());
    }
}