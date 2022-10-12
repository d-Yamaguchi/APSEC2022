public void pixlArt(org.bukkit.block.Block b, org.bukkit.entity.Player p) {
    // Very hackish detection
    org.bukkit.event.block.BlockBreakEvent event1 = new org.bukkit.event.block.BlockBreakEvent(b, p);
    plugin.getServer().getPluginManager().callEvent(event1);
    if (event1.isCancelled()) {
        return;
    } else {
        // switch statement later on?
        org.bukkit.block.Block previousBlock = b;
        if ((a.Type(b) == org.bukkit.Material.LOG) || (a.Type(b) == org.bukkit.Material.LEAVES)) {
            int _CVAR0 = ((byte) (2));
            boolean _CVAR1 = b.getData() < _CVAR0;
            if () {
                int _CVAR3 = 1;
                org.bukkit.block.Block _CVAR2 = b;
                int _CVAR4 = ((byte) (b.getData() + _CVAR3));
                _CVAR2.setData(_CVAR4);
            }// reset it
             else // add one
            {
                b.setData(((byte) (0)));
            }// reset it

        } else if (a.Type(b) == org.bukkit.Material.WOOL) {
            if (plugin.isSet(p) == null) {
                int _CVAR5 = ((byte) (15));
                boolean _CVAR6 = b.getData() < _CVAR5;
                if () {
                    int _CVAR8 = 1;
                    org.bukkit.block.Block _CVAR7 = b;
                    int _CVAR9 = ((byte) (b.getData() + _CVAR8));
                    _CVAR7.setData(_CVAR9);
                }// reset it
                 else // add one
                {
                    b.setData(((byte) (0)));
                }// reset it

            }// should set the byte value...
             else {
                b.setData(plugin.isSet(p).byteValue());
            }// should set the byte value...

        } else if ((a.Type(b) == org.bukkit.Material.STEP) || (a.Type(b) == org.bukkit.Material.DOUBLE_STEP)) {
            int _CVAR10 = ((byte) (5));
            boolean _CVAR11 = b.getData() < _CVAR10;
            if () {
                int _CVAR13 = 1;
                org.bukkit.block.Block _CVAR12 = b;
                int _CVAR14 = ((byte) (b.getData() + _CVAR13));
                _CVAR12.setData(_CVAR14);
            }// reset it
             else // add one
            {
                b.setData(((byte) (0)));
            }// reset it

        } else if (((((a.Type(b) == org.bukkit.Material.WOOD_STAIRS) || (a.Type(b) == org.bukkit.Material.COBBLESTONE_STAIRS)) || (a.Type(b) == org.bukkit.Material.BRICK_STAIRS)) || (a.Type(b) == org.bukkit.Material.SMOOTH_STAIRS)) || (a.ID(b) == 114)) {
            int _CVAR15 = ((byte) (0));
            boolean _CVAR16 = b.getData() == _CVAR15;
            if () {
                // Ascending south
                b.setData(((byte) (2)));// Ascending west

            } else {
                int _CVAR17 = ((byte) (1));
                boolean _CVAR18 = b.getData() == _CVAR17;
                if () {
                    // Ascending north
                    b.setData(((byte) (3)));// Ascending east

                } else {
                    int _CVAR19 = ((byte) (2));
                    boolean _CVAR20 = b.getData() == _CVAR19;
                    if () {
                        // Ascending west
                        b.setData(((byte) (1)));// Ascending north

                    } else {
                        int _CVAR21 = ((byte) (3));
                        boolean _CVAR22 = b.getData() == _CVAR21;
                        if () {
                            // Ascending east
                            b.setData(((byte) (0)));// Ascending south

                        }
                    }
                }
            }
        } else if (a.Type(b) == org.bukkit.Material.SMOOTH_BRICK) {
            int _CVAR23 = ((byte) (2));
            boolean _CVAR24 = b.getData() < _CVAR23;
            if () {
                int _CVAR26 = 1;
                org.bukkit.block.Block _CVAR25 = b;
                int _CVAR27 = ((byte) (b.getData() + _CVAR26));
                _CVAR25.setData(_CVAR27);
            }// reset it
             else // add one
            {
                b.setData(((byte) (0)));
            }// reset it

        } else if ((a.Type(b) == org.bukkit.Material.SPONGE) || (a.Type(b) == org.bukkit.Material.FENCE)) {
            // Turn Sponges into fences and back again!
            if (a.Type(b) == org.bukkit.Material.SPONGE) {
                b.setType(org.bukkit.Material.FENCE);
            } else if (a.Type(b) == org.bukkit.Material.FENCE) {
                b.setType(org.bukkit.Material.SPONGE);
            }
        } else if ((a.Type(b) == org.bukkit.Material.COBBLESTONE) || (a.Type(b) == org.bukkit.Material.MOSSY_COBBLESTONE)) {
            // Turn Cobble into mossy and back again!
            if (a.Type(b) == org.bukkit.Material.COBBLESTONE) {
                b.setType(org.bukkit.Material.MOSSY_COBBLESTONE);
            } else if (a.Type(b) == org.bukkit.Material.MOSSY_COBBLESTONE) {
                b.setType(org.bukkit.Material.COBBLESTONE);
            }
        } else if ((a.Type(b) == org.bukkit.Material.LONG_GRASS) || (a.Type(b) == org.bukkit.Material.SAPLING)) {
            int _CVAR28 = ((byte) (2));
            boolean _CVAR29 = b.getData() < _CVAR28;
            if () {
                int _CVAR31 = 1;
                org.bukkit.block.Block _CVAR30 = b;
                int _CVAR32 = ((byte) (b.getData() + _CVAR31));
                _CVAR30.setData(_CVAR32);
            }// reset it
             else // add one
            {
                b.setData(((byte) (0)));
            }// reset it

        }
        plugin.logBlockPlace(b, previousBlock.getState(), previousBlock, p.getItemInHand(), p, true);
    }
}