public void moveTo(com.narrowtux.MagnetBlock.BlockPosition pos, int step) {
    org.bukkit.block.BlockState state;
    switch (step) {
        case 0 :
            typeid = world.getBlockTypeIdAt(block.getLocation());
            material = org.bukkit.Material.getMaterial(typeid);
            data = block.getData();
            state = block.getState();
            if (material.equals(org.bukkit.Material.AIR)) {
                com.narrowtux.MagnetBlock.MagnetBlockBlock.plugin.log.log(java.util.logging.Level.SEVERE, "Block has encountered AIR Type!");
                block = block.getWorld().getBlockAt(block.getLocation());
                material = org.bukkit.Material.COBBLESTONE;
                data = 0;
            }
            if (state instanceof org.bukkit.block.Sign) {
                org.bukkit.block.Sign s = ((org.bukkit.block.Sign) (state));
                for (int i = 0; i < s.getLines().length; i++) {
                    signText.add(s.getLine(i));
                }
            }
            if (state instanceof org.bukkit.block.Chest) {
                org.bukkit.block.Chest chest = ((org.bukkit.block.Chest) (state));
                inventory = InventoryCopy(chest.getInventory().getContents());
                chest.getInventory().clear();
            }
            if (state instanceof org.bukkit.block.Dispenser) {
                org.bukkit.block.Dispenser dispenser = ((org.bukkit.block.Dispenser) (state));
                inventory = InventoryCopy(dispenser.getInventory().getContents());
                dispenser.getInventory().clear();
            }
            if (state instanceof org.bukkit.block.Furnace) {
                org.bukkit.block.Furnace furnace = ((org.bukkit.block.Furnace) (state));
                burnTime = furnace.getBurnTime();
                cookTime = furnace.getCookTime();
                inventory = InventoryCopy(furnace.getInventory().getContents());
                furnace.getInventory().clear();
                furnace.setCookTime(((short) (0)));
                furnace.setBurnTime(((short) (0)));
            }
            if (state instanceof org.bukkit.block.CreatureSpawner) {
                org.bukkit.block.CreatureSpawner spawner = ((org.bukkit.block.CreatureSpawner) (state));
                org.bukkit.block.CreatureSpawner _CVAR0 = spawner;
                org.bukkit.entity.CreatureType _CVAR1 = _CVAR0.getCreatureType();
                creature = _CVAR1;
                delay = spawner.getDelay();
            }
            if (state instanceof org.bukkit.block.Jukebox) {
                org.bukkit.block.Jukebox jukebox = ((org.bukkit.block.Jukebox) (state));
                record = jukebox.getPlaying();
            }
            if (state instanceof org.bukkit.block.NoteBlock) {
                org.bukkit.block.NoteBlock noteBlock = ((org.bukkit.block.NoteBlock) (state));
                note = noteBlock.getNote();
            }
            break;
        case 1 :
            block.setType(org.bukkit.Material.AIR);
            if (block.getRelative(org.bukkit.block.BlockFace.DOWN).getType().equals(org.bukkit.Material.WOOD_PLATE)) {
                block.getRelative(org.bukkit.block.BlockFace.DOWN).setData(((byte) (0)));
            }
            break;
        case 2 :
            block = pos.getWorld().getBlockAt(pos.toLocation());
            block.setTypeId(typeid);
            block.setData(data);
            state = block.getState();
            if (state instanceof org.bukkit.block.Sign) {
                org.bukkit.block.Sign s = ((org.bukkit.block.Sign) (block.getState()));
                int i = 0;
                for (java.lang.String line : signText) {
                    s.setLine(i, line);
                    i++;
                }
                signText.clear();
            }
            if (state instanceof org.bukkit.block.Chest) {
                org.bukkit.block.Chest chest = ((org.bukkit.block.Chest) (block.getState()));
                chest.getInventory().setContents(InventoryCopy(inventory));
            }
            if (state instanceof org.bukkit.block.Dispenser) {
                org.bukkit.block.Dispenser dispenser = ((org.bukkit.block.Dispenser) (block.getState()));
                dispenser.getInventory().setContents(InventoryCopy(inventory));
            }
            if (state instanceof org.bukkit.block.Furnace) {
                org.bukkit.block.Furnace furnace = ((org.bukkit.block.Furnace) (block.getState()));
                furnace.setBurnTime(burnTime);
                furnace.setCookTime(cookTime);
                furnace.getInventory().setContents(InventoryCopy(inventory));
            }
            if (state instanceof org.bukkit.block.CreatureSpawner) {
                org.bukkit.block.CreatureSpawner spawner = ((org.bukkit.block.CreatureSpawner) (state));
                spawner.setCreatureType(creature);
                spawner.setDelay(delay);
            }
            if (state instanceof org.bukkit.block.Jukebox) {
                org.bukkit.block.Jukebox jukebox = ((org.bukkit.block.Jukebox) (state));
                jukebox.setPlaying(record);
            }
            if (state instanceof org.bukkit.block.NoteBlock) {
                org.bukkit.block.NoteBlock noteBlock = ((org.bukkit.block.NoteBlock) (state));
                noteBlock.setNote(note);
            }
            break;
    }
}