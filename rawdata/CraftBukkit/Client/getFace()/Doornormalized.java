private void flipState() {
    // this is kinda funky, but we only check one position
    // to see if the door is open and/or closable.
    // efficiency choice :/
    org.bukkit.block.Block hinge = null;
    if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Up]")) {
        org.bukkit.block.Block _CVAR0 = proximalBaseCenter;
        org.bukkit.block.BlockFace _CVAR1 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.BlockFace _CVAR2 = _CVAR0.getFace(_CVAR1);
        hinge = _CVAR2;
    } else {
        org.bukkit.block.Block _CVAR3 = proximalBaseCenter;
        org.bukkit.block.BlockFace _CVAR4 = org.bukkit.block.BlockFace.DOWN;
        org.bukkit.block.BlockFace _CVAR5 = _CVAR3.getFace(_CVAR4);
        hinge = _CVAR5;
    }
    // aaand we also only check if it's something we can
    // smosh or not when deciding if we're open or closed.
    // there are no errors reported upon weird blocks like
    // obsidian in the middle of a wooden door, just weird
    // results.
    if (com.sk89q.craftbook.mech.Door.canPassThrough(hinge.getTypeId())) {
        new com.sk89q.craftbook.mech.Door.ToggleRegionClosed().run();
    } else {
        new com.sk89q.craftbook.mech.Door.ToggleRegionOpen().run();
    }
}