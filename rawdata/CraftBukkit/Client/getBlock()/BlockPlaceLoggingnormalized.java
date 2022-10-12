@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR, ignoreCancelled = true)
public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
    org.bukkit.event.block.BlockPlaceEvent _CVAR0 = event;
    org.bukkit.block.Block _CVAR1 = _CVAR0.getBlock();
    org.bukkit.World _CVAR2 = _CVAR1.getWorld();
    final de.diddiz.LogBlock.config.WorldConfig wcfg = Config.getWorldConfig(_CVAR2);
    if ((wcfg != null) && wcfg.isLogging(Logging.BLOCKPLACE)) {
        final org.bukkit.block.BlockState before = event.getBlockReplacedState();
        final org.bukkit.block.BlockState after = event.getBlockPlaced().getState();
        org.bukkit.event.block.BlockPlaceEvent _CVAR5 = event;
        org.bukkit.block.Block _CVAR6 = _CVAR5.getBlock();
        org.bukkit.Material _CVAR7 = _CVAR6.getType();
        boolean _CVAR8 = _CVAR7 == org.bukkit.Material.SAND;
        org.bukkit.event.block.BlockPlaceEvent _CVAR11 = event;
        org.bukkit.block.Block _CVAR12 = _CVAR11.getBlock();
        org.bukkit.Material _CVAR13 = _CVAR12.getType();
        boolean _CVAR14 = _CVAR13 == org.bukkit.Material.GRAVEL;
        boolean _CVAR9 = _CVAR8 || _CVAR14;
        org.bukkit.event.block.BlockPlaceEvent _CVAR15 = event;
        org.bukkit.block.Block _CVAR16 = _CVAR15.getBlock();
        org.bukkit.Material _CVAR17 = _CVAR16.getType();
        boolean _CVAR18 = _CVAR17 == org.bukkit.Material.DRAGON_EGG;
        boolean _CVAR10 = _CVAR9 || _CVAR18;
        org.bukkit.event.block.BlockPlaceEvent _CVAR22 = event;
        org.bukkit.event.block.BlockPlaceEvent _CVAR46 = _CVAR22;
        org.bukkit.entity.Player _CVAR23 = _CVAR46.getPlayer();
        org.bukkit.entity.Player _CVAR47 = _CVAR23;
        final java.lang.String playerName = _CVAR47.getName();
        org.bukkit.event.block.BlockPlaceEvent _CVAR3 = event;
        org.bukkit.event.block.BlockPlaceEvent _CVAR39 = _CVAR3;
        org.bukkit.event.block.BlockPlaceEvent _CVAR77 = _CVAR39;
        org.bukkit.block.Block _CVAR4 = _CVAR77.getBlock();
        org.bukkit.block.Block _CVAR40 = _CVAR4;
        org.bukkit.block.Block _CVAR78 = _CVAR40;
        final int type = _CVAR78.getTypeId();
        // Handle falling blocks
        if () {
            while ((y > 0) && de.diddiz.util.BukkitUtils.canFall(loc.getWorld(), x, y - 1, z)) {
                y--;
            } 
            org.bukkit.block.Block _CVAR29 = _CVAR26;
            org.bukkit.Location loc = _CVAR29.getLocation();
            org.bukkit.block.Block _CVAR32 = _CVAR29;
            org.bukkit.Location loc = _CVAR32.getLocation();
            org.bukkit.block.Block _CVAR35 = _CVAR32;
            org.bukkit.Location loc = _CVAR35.getLocation();
            org.bukkit.event.block.BlockPlaceEvent _CVAR19 = event;
            org.bukkit.event.block.BlockPlaceEvent _CVAR25 = _CVAR19;
            org.bukkit.event.block.BlockPlaceEvent _CVAR49 = _CVAR25;
            org.bukkit.block.Block _CVAR20 = _CVAR49.getBlock();
            org.bukkit.block.Block _CVAR26 = _CVAR20;
            org.bukkit.block.Block _CVAR50 = _CVAR26;
            org.bukkit.Location loc = _CVAR50.getLocation();
            org.bukkit.block.Block _CVAR53 = _CVAR50;
            org.bukkit.Location loc = _CVAR53.getLocation();
            org.bukkit.Location _CVAR30 = loc;
            org.bukkit.Location _CVAR54 = _CVAR30;
            int x = _CVAR54.getBlockX();
            org.bukkit.block.Block _CVAR56 = _CVAR53;
            org.bukkit.Location loc = _CVAR56.getLocation();
            org.bukkit.Location _CVAR33 = loc;
            org.bukkit.Location _CVAR57 = _CVAR33;
            int y = _CVAR57.getBlockY();
            org.bukkit.block.Block _CVAR59 = _CVAR56;
            org.bukkit.Location loc = _CVAR59.getLocation();
            org.bukkit.Location _CVAR36 = loc;
            org.bukkit.Location _CVAR60 = _CVAR36;
            int z = _CVAR60.getBlockZ();
            // If y is 0 then the sand block fell out of the world :(
            if (y != 0) {
                org.bukkit.Location _CVAR27 = loc;
                org.bukkit.Location _CVAR51 = _CVAR27;
                org.bukkit.World _CVAR28 = _CVAR51.getWorld();
                int _CVAR31 = x;
                int _CVAR34 = y;
                int _CVAR37 = z;
                org.bukkit.World _CVAR52 = _CVAR28;
                int _CVAR55 = _CVAR31;
                int _CVAR58 = _CVAR34;
                int _CVAR61 = _CVAR37;
                org.bukkit.Location finalLoc = new org.bukkit.Location(_CVAR52, _CVAR55, _CVAR58, _CVAR61);
                org.bukkit.World _CVAR63 = _CVAR52;
                int _CVAR64 = _CVAR55;
                int _CVAR65 = _CVAR58;
                int _CVAR66 = _CVAR61;
                org.bukkit.Location finalLoc = new org.bukkit.Location(_CVAR63, _CVAR64, _CVAR65, _CVAR66);
                org.bukkit.World _CVAR70 = _CVAR63;
                int _CVAR71 = _CVAR64;
                int _CVAR72 = _CVAR65;
                int _CVAR73 = _CVAR66;
                org.bukkit.Location finalLoc = new org.bukkit.Location(_CVAR70, _CVAR71, _CVAR72, _CVAR73);
                if (finalLoc.getBlock().getType() == org.bukkit.Material.AIR) {
                    org.bukkit.event.block.BlockPlaceEvent _CVAR42 = event;
                    org.bukkit.block.Block _CVAR43 = _CVAR42.getBlock();
                    de.diddiz.LogBlock.listeners.BlockPlaceLogging _CVAR21 = consumer;
                    java.lang.String _CVAR24 = playerName;
                    org.bukkit.Location _CVAR38 = finalLoc;
                    int _CVAR41 = type;
                    byte _CVAR44 = _CVAR43.getData();
                    _CVAR21.queueBlockPlace(_CVAR24, _CVAR38, _CVAR41, _CVAR44);
                } else {
                    org.bukkit.Location _CVAR67 = finalLoc;
                    org.bukkit.block.Block _CVAR68 = _CVAR67.getBlock();
                    org.bukkit.Location _CVAR74 = finalLoc;
                    org.bukkit.block.Block _CVAR75 = _CVAR74.getBlock();
                    org.bukkit.event.block.BlockPlaceEvent _CVAR80 = event;
                    org.bukkit.block.Block _CVAR81 = _CVAR80.getBlock();
                    de.diddiz.LogBlock.listeners.BlockPlaceLogging _CVAR45 = consumer;
                    java.lang.String _CVAR48 = playerName;
                    org.bukkit.Location _CVAR62 = finalLoc;
                    int _CVAR69 = _CVAR68.getTypeId();
                    byte _CVAR76 = _CVAR75.getData();
                    int _CVAR79 = type;
                    byte _CVAR82 = _CVAR81.getData();
                    _CVAR45.queueBlockReplace(_CVAR48, _CVAR62, _CVAR69, _CVAR76, _CVAR79, _CVAR82);
                }
            }
            return;
        }
        // Sign logging is handled elsewhere
        if (wcfg.isLogging(Logging.SIGNTEXT) && ((type == 63) || (type == 68))) {
            return;
        }
        // Delay queuing by one tick to allow data to be updated
        de.diddiz.LogBlock.LogBlock.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(de.diddiz.LogBlock.LogBlock.getInstance(), new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                if (before.getTypeId() == 0) {
                    consumer.queueBlockPlace(playerName, after);
                } else {
                    consumer.queueBlockReplace(playerName, before, after);
                }
            }
        }, 1L);
    }
}