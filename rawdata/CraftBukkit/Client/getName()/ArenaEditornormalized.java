@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    boolean _CVAR0 = !selections.containsKey(event.getPlayer().getName());
    boolean _CVAR1 = (event.getClickedBlock() == null) || _CVAR0;
    if () {
        return;
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR3 = event;
    org.bukkit.entity.Player _CVAR4 = _CVAR3.getPlayer();
    java.util.HashMap<java.lang.String, mc.alk.arena.controllers.ArenaEditor.CurrentSelection> _CVAR2 = selections;
    java.lang.String _CVAR5 = _CVAR4.getName();
    mc.alk.arena.controllers.ArenaEditor.CurrentSelection cs = _CVAR2.get(_CVAR5);
    if ((cs.listeningIndex == null) || (cs.getArena() == null)) {
        return;
    }
    if ((event.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE) && (event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
        event.setCancelled(true);
    }
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