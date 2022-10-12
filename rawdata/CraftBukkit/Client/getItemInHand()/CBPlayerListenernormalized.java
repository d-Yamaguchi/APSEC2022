@org.bukkit.event.EventHandler(ignoreCancelled = true, priority = org.bukkit.event.EventPriority.MONITOR)
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    org.bukkit.event.block.Action action = event.getAction();
    if (action.equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
        org.bukkit.block.Block block = event.getClickedBlock();
        org.bukkit.entity.Player player = event.getPlayer();
         _CVAR1 = ((org.bukkit.craftbukkit.inventory.CraftItemStack) (player.getItemInHand())).getHandle().tag;
        java.lang.String _CVAR2 = "title";
        java.io.PrintStream _CVAR0 = java.lang.System.out;
         _CVAR3 = _CVAR1.getString(_CVAR2);
        // Test code :P crapy but works to get and set title/pages/autor
        _CVAR0.println(_CVAR3);
         _CVAR4 = ((org.bukkit.craftbukkit.inventory.CraftItemStack) (player.getItemInHand())).getHandle().tag;
        java.lang.String _CVAR5 = "title";
        java.lang.String _CVAR6 = "FUCKYOU";
        _CVAR4.setString(_CVAR5, _CVAR6);
         _CVAR8 = ((org.bukkit.craftbukkit.inventory.CraftItemStack) (player.getItemInHand())).getHandle().tag;
        java.lang.String _CVAR9 = "title";
        java.io.PrintStream _CVAR7 = java.lang.System.out;
         _CVAR10 = _CVAR8.getString(_CVAR9);
        _CVAR7.println(_CVAR10);
         _CVAR12 = ((org.bukkit.craftbukkit.inventory.CraftItemStack) (player.getItemInHand())).getHandle().tag;
        java.lang.String _CVAR13 = "pages";
         _CVAR14 = _CVAR12.getList(_CVAR13);
        java.io.PrintStream _CVAR11 = java.lang.System.out;
         _CVAR15 = _CVAR14.toString();
        _CVAR11.println(_CVAR15);
        d.add(new net.minecraft.server.NBTTagString("asdf", "dfxcv"));
        d.add(new net.minecraft.server.NBTTagString("ddd", "kdfnk"));
        java.lang.String _CVAR18 = "pages";
        net.minecraft.server.NBTTagList d = new net.minecraft.server.NBTTagList(_CVAR18);
         _CVAR16 = ((org.bukkit.craftbukkit.inventory.CraftItemStack) (player.getItemInHand())).getHandle().tag;
        java.lang.String _CVAR17 = "pages";
        net.minecraft.server.NBTTagList _CVAR19 = d;
        _CVAR16.set(_CVAR17, _CVAR19);
         _CVAR21 = ((org.bukkit.craftbukkit.inventory.CraftItemStack) (player.getItemInHand())).getHandle().tag;
        java.lang.String _CVAR22 = "pages";
        java.io.PrintStream _CVAR20 = java.lang.System.out;
         _CVAR23 = _CVAR21.getList(_CVAR22);
        _CVAR20.println(_CVAR23);
        if (block == null) {
            return;
        }
        if (block.getState() instanceof org.bukkit.block.Sign) {
            java.lang.String[] lines = ((org.bukkit.block.Sign) (block.getState())).getLines();
            if (((!lines[0].equalsIgnoreCase("[CopyBooks]")) || lines[1].equals("")) || lines[2].equals("")) {
                return;
            }
            int id = 0;
            try {
                id = java.lang.Integer.parseInt(lines[2]);
            } catch (java.lang.NumberFormatException ex) {
                player.sendMessage("Invalid format!");
                return;
            }
            // give book by id
        }
    }
}