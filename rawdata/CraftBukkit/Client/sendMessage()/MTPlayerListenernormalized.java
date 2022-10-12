@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.event.block.Action act = event.getAction();
    org.bukkit.Material mat = block.getType();
    org.bukkit.Material item = org.bukkit.Material.AIR;
    try {
        item = event.getItem().getType();
    } catch (java.lang.NullPointerException npe) {
        // Do nothing because it means we have nothing in our hands, and we've
        // already defined it as air.
    }
    boolean rst = mat.equals(org.bukkit.Material.REDSTONE_TORCH_ON) || mat.equals(org.bukkit.Material.REDSTONE_TORCH_OFF);
    if (rst) {
        if (pl.mt.isInEditMode(player)) {
            // has to have perm_create, or wouldn't be in edit mode.
            if (act.equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
                if (!pl.mt.isSetTransmitter(player, block)) {
                    if (pl.mt.setTransmitter(player, block)) {
                        java.lang.String _CVAR2 = "Selected transmitter torch.";
                        org.bukkit.entity.Player _CVAR1 = player;
                         _CVAR3 = pl.g + _CVAR2;
                        _CVAR1.sendMessage(_CVAR3);
                    } else {
                         _CVAR6 = pl.mt.message;
                        org.bukkit.entity.Player _CVAR5 = player;
                         _CVAR7 = pl.r + _CVAR6;
                        _CVAR5.sendMessage(_CVAR7);
                    }
                }
                event.setCancelled(true);
            } else if (act.equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                if (pl.mt.setReceiver(player, block)) {
                     _CVAR10 = pl.mt.message;
                    org.bukkit.entity.Player _CVAR9 = player;
                     _CVAR11 = pl.g + _CVAR10;
                    _CVAR9.sendMessage(_CVAR11);
                } else {
                     _CVAR14 = pl.mt.message;
                    org.bukkit.entity.Player _CVAR13 = player;
                     _CVAR15 = pl.r + _CVAR14;
                    _CVAR13.sendMessage(_CVAR15);
                }
            }
        }
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.event.player.PlayerInteractEvent _CVAR4 = _CVAR0;
    org.bukkit.event.player.PlayerInteractEvent _CVAR8 = _CVAR4;
    org.bukkit.event.player.PlayerInteractEvent _CVAR12 = _CVAR8;
    org.bukkit.event.player.PlayerInteractEvent _CVAR16 = _CVAR12;
    org.bukkit.event.player.PlayerInteractEvent _CVAR19 = _CVAR16;
    org.bukkit.event.player.PlayerInteractEvent _CVAR25 = _CVAR19;
    org.bukkit.event.player.PlayerInteractEvent _CVAR31 = _CVAR25;
    org.bukkit.entity.Player player = _CVAR31.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR21 = event;
    org.bukkit.event.player.PlayerInteractEvent _CVAR27 = _CVAR21;
    org.bukkit.event.player.PlayerInteractEvent _CVAR33 = _CVAR27;
    org.bukkit.block.Block block = _CVAR33.getClickedBlock();
    // Lets check for a switch in the hand (which indicates info request).
    if (rst || mat.equals(org.bukkit.Material.TORCH)) {
        sorklin.magictorches.MagicTorches.log(java.util.logging.Level.FINEST, "at rst || torch");
        if (act.equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && item.equals(org.bukkit.Material.LEVER)) {
            sorklin.magictorches.MagicTorches.log(java.util.logging.Level.FINEST, "at lever click");
            if (sorklin.magictorches.MagicTorches.canCreate(player)) {
                // i have create, admin or op perms
                sorklin.magictorches.MagicTorches.log(java.util.logging.Level.FINEST, "canCreate");
                sorklin.magictorches.MagicTorches.listMessage(player, pl.mt.getInfo(block, player.getName(), sorklin.magictorches.MagicTorches.isAdmin(player), true));
            }
        } else if (act.equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && item.equals(org.bukkit.Material.REDSTONE)) {
            org.bukkit.entity.Player _CVAR17 = player;
            java.lang.String _CVAR18 = "Info:";
            _CVAR17.sendMessage(_CVAR18);
            org.bukkit.block.Block _CVAR22 = block;
            int _CVAR23 = _CVAR22.getBlockPower();
            org.bukkit.entity.Player _CVAR20 = player;
            java.lang.String _CVAR24 = "getBlockPower(): " + _CVAR23;
            _CVAR20.sendMessage(_CVAR24);
            org.bukkit.block.Block _CVAR28 = block;
            boolean _CVAR29 = _CVAR28.isBlockIndirectlyPowered();
            org.bukkit.entity.Player _CVAR26 = player;
            java.lang.String _CVAR30 = "isBlockIndirectlyPowered(): " + _CVAR29;
            _CVAR26.sendMessage(_CVAR30);
            org.bukkit.block.Block _CVAR34 = block;
            boolean _CVAR35 = _CVAR34.isBlockPowered();
            org.bukkit.entity.Player _CVAR32 = player;
            java.lang.String _CVAR36 = "isBlockPowered(): " + _CVAR35;
            _CVAR32.sendMessage(_CVAR36);
        }
    }
}