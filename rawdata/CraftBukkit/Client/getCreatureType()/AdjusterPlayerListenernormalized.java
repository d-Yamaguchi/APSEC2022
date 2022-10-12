@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if ((event.getClickedBlock() == null) || (!SpawnerAdjuster.usePlayerListener)) {
        return;
    }
    if (event.getClickedBlock().getType() == org.bukkit.Material.MOB_SPAWNER) {
        if (com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(event.getPlayer(), "SpawnerAdjuster.ChangeSpawnType")) {
            if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) || (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR)) {
                org.bukkit.block.CreatureSpawner _CVAR2 = spawner;
                org.bukkit.entity.CreatureType _CVAR3 = _CVAR2.getCreatureType();
                java.lang.String name = _CVAR3.getName();
                if (SpawnerAdjuster.opsChangeSpawnTypeOnly) {
                    if (event.getPlayer().isOp()) {
                        com.sadmean.mc.SpawnerAdjuster.AdjusterPlayerListener.setSpawnType(spawner, event.getPlayer());
                    }
                } else {
                    com.sadmean.mc.SpawnerAdjuster.AdjusterPlayerListener.setSpawnType(spawner, event.getPlayer());
                }
                org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
                org.bukkit.event.player.PlayerInteractEvent _CVAR4 = _CVAR0;
                org.bukkit.block.Block _CVAR1 = _CVAR4.getClickedBlock();
                org.bukkit.block.Block _CVAR5 = _CVAR1;
                org.bukkit.block.CreatureSpawner spawner = ((org.bukkit.block.CreatureSpawner) (_CVAR5.getState()));
                org.bukkit.block.CreatureSpawner _CVAR6 = spawner;
                org.bukkit.entity.CreatureType _CVAR7 = _CVAR6.getCreatureType();
                java.lang.String newName = _CVAR7.getName();
                event.getPlayer().sendMessage(((((((SpawnerAdjuster.chatPrefix + "Spawner was: ") + org.bukkit.ChatColor.GREEN) + name) + org.bukkit.ChatColor.GRAY) + " is now: ") + org.bukkit.ChatColor.GREEN) + newName);
                event.setCancelled(true);// maybe prevent block placement?

                spawner.setDelay(200);
            }
        }
    }
}