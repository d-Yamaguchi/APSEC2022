/**
 * Create a new, unnamed pad
 *
 * @param location
 * 		Location of the center of the pad
 * @param player
 * 		Player who should own this pad
 */
public void createPad(final org.bukkit.Location location, org.bukkit.entity.Player player) {
    update();
    final net.h31ix.travelpad.api.UnnamedPad pad = new net.h31ix.travelpad.api.UnnamedPad(location, player);
    net.h31ix.travelpad.event.TravelPadCreateEvent e = new net.h31ix.travelpad.event.TravelPadCreateEvent(pad);
    plugin.getServer().getPluginManager().callEvent(e);
    if (!e.isCancelled()) {
        config.addUnv(pad);
        final org.bukkit.entity.Player owner = pad.getOwner();
        server.getScheduler().scheduleSyncDelayedTask(plugin, new java.lang.Runnable() {
            public void run() {
                if (config.isUnv(pad)) {
                    net.h31ix.travelpad.event.TravelPadExpireEvent e = new net.h31ix.travelpad.event.TravelPadExpireEvent(pad);
                    plugin.getServer().getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        config.removePad(pad);
                        owner.sendMessage(org.bukkit.ChatColor.RED + l.pad_expire());
                        deleteBlocks(location);
                    }
                }
            }
        }, 600L);
        org.bukkit.block.Block _CVAR1 = block;
        org.bukkit.block.BlockFace _CVAR2 = org.bukkit.block.BlockFace.EAST;
        org.bukkit.block.Block _CVAR3 = _CVAR1.getRelative(_CVAR2);
        org.bukkit.Material _CVAR4 = org.bukkit.Material.STEP;
        _CVAR3.setType(_CVAR4);
        org.bukkit.block.Block _CVAR6 = block;
        org.bukkit.block.BlockFace _CVAR7 = org.bukkit.block.BlockFace.WEST;
        org.bukkit.block.Block _CVAR8 = _CVAR6.getRelative(_CVAR7);
        org.bukkit.Material _CVAR9 = org.bukkit.Material.STEP;
        _CVAR8.setType(_CVAR9);
        org.bukkit.block.Block _CVAR11 = block;
        org.bukkit.block.BlockFace _CVAR12 = org.bukkit.block.BlockFace.NORTH;
        org.bukkit.block.Block _CVAR13 = _CVAR11.getRelative(_CVAR12);
        org.bukkit.Material _CVAR14 = org.bukkit.Material.STEP;
        _CVAR13.setType(_CVAR14);
        org.bukkit.block.Block _CVAR16 = block;
        org.bukkit.block.BlockFace _CVAR17 = org.bukkit.block.BlockFace.SOUTH;
        org.bukkit.block.Block _CVAR18 = _CVAR16.getRelative(_CVAR17);
        org.bukkit.Material _CVAR19 = org.bukkit.Material.STEP;
        _CVAR18.setType(_CVAR19);
        org.bukkit.Location _CVAR0 = location;
        org.bukkit.Location _CVAR5 = _CVAR0;
        org.bukkit.Location _CVAR10 = _CVAR5;
        org.bukkit.Location _CVAR15 = _CVAR10;
        org.bukkit.Location _CVAR20 = _CVAR15;
        final org.bukkit.block.Block block = _CVAR20.getBlock();
        org.bukkit.block.Block _CVAR21 = block;
        org.bukkit.block.BlockFace _CVAR22 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.Block _CVAR23 = _CVAR21.getRelative(_CVAR22);
        org.bukkit.Material _CVAR24 = org.bukkit.Material.WATER;
        _CVAR23.setType(_CVAR24);
        org.bukkit.Server _CVAR25 = server;
        org.bukkit.scheduler.BukkitScheduler _CVAR26 = _CVAR25.getScheduler();
        org.bukkit.plugin.Plugin _CVAR27 = plugin;
        java.lang.Runnable _CVAR28 = new java.lang.Runnable() {
            public void run() {
                block.getRelative(org.bukkit.block.BlockFace.UP).setType(org.bukkit.Material.AIR);
            }
        };
        long _CVAR29 = 10L;
        _CVAR26.scheduleSyncDelayedTask(_CVAR27, _CVAR28, _CVAR29);
        owner.sendMessage(org.bukkit.ChatColor.GREEN + l.create_approve_1());
        owner.sendMessage(org.bukkit.ChatColor.GREEN + l.create_approve_2());
        update();
    }
}