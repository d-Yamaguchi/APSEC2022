@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent e) {
    if ((holding.getType() != org.bukkit.Material.BOOK_AND_QUILL) && (holding.getType() != org.bukkit.Material.WRITTEN_BOOK)) {
        org.bukkit.inventory.ItemStack _CVAR4 = holding;
        org.bukkit.Material _CVAR5 = _CVAR4.getType();
        java.lang.String _CVAR6 = _CVAR5.toString();
        org.bukkit.entity.Player _CVAR1 = p;
        java.lang.String _CVAR7 = "MATERIAL IS " + _CVAR6;
        _CVAR1.sendMessage(_CVAR7);
        return;
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR10 = e;
    org.bukkit.event.block.Action _CVAR11 = _CVAR10.getAction();
    java.lang.String _CVAR12 = _CVAR11.toString();
    org.bukkit.entity.Player _CVAR9 = p;
    java.lang.String _CVAR13 = "Clicky click " + _CVAR12;
    _CVAR9.sendMessage(_CVAR13);
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = e;
    org.bukkit.event.player.PlayerInteractEvent _CVAR8 = _CVAR0;
    org.bukkit.event.player.PlayerInteractEvent _CVAR21 = _CVAR8;
    org.bukkit.entity.Player p = _CVAR21.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR23 = _CVAR21;
    org.bukkit.entity.Player p = _CVAR23.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR2 = _CVAR0;
    org.bukkit.event.player.PlayerInteractEvent _CVAR14 = _CVAR2;
    org.bukkit.event.player.PlayerInteractEvent _CVAR31 = _CVAR14;
    org.bukkit.entity.Player p = _CVAR31.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR16 = _CVAR14;
    org.bukkit.event.player.PlayerInteractEvent _CVAR34 = _CVAR16;
    org.bukkit.entity.Player p = _CVAR34.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR36 = _CVAR34;
    org.bukkit.entity.Player p = _CVAR36.getPlayer();
    org.bukkit.entity.Player _CVAR3 = p;
    org.bukkit.entity.Player _CVAR17 = _CVAR3;
    org.bukkit.entity.Player _CVAR24 = _CVAR17;
    org.bukkit.entity.Player _CVAR37 = _CVAR24;
    org.bukkit.inventory.ItemStack holding = _CVAR37.getItemInHand();
    org.bukkit.inventory.ItemStack _CVAR18 = holding;
    org.bukkit.inventory.ItemStack _CVAR25 = _CVAR18;
    org.bukkit.inventory.ItemStack _CVAR38 = _CVAR25;
    com.basicer.parchment.craftbukkit.Book b = com.basicer.parchment.craftbukkit.Book.createFromBukkitItemStack(_CVAR38);
    if ((e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR) || (e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
        if ((e.getClickedBlock() != null) && (e.getClickedBlock().getType() == org.bukkit.Material.BOOKSHELF)) {
            b.unlock();
            e.setCancelled(true);
            return;
        }
        com.basicer.parchment.craftbukkit.Book _CVAR19 = b;
        org.bukkit.entity.Player _CVAR15 = p;
         _CVAR20 = _CVAR19.getFullText();
        _CVAR15.sendMessage(_CVAR20);
        com.basicer.parchment.craftbukkit.Book _CVAR26 = b;
        com.basicer.parchment.craftbukkit.Book _CVAR39 = _CVAR26;
        java.lang.String action = _CVAR39.getFullText();
        if (action.startsWith("bridge")) {
            for (org.bukkit.block.Block bl : p.getLineOfSight(null, 40)) {
                if (bl.getLocation().distance(p.getLocation()) > 2) {
                    bl.getWorld().getHighestBlockAt(bl.getLocation()).getRelative(0, 0, 0).setTypeId(66);
                }
            }
        } else if (action.startsWith("arrow")) {
            org.bukkit.entity.Arrow a = p.launchProjectile(org.bukkit.entity.Arrow.class);
            a.setVelocity(a.getVelocity().multiply(4));
        } else if (action.startsWith("eval")) {
            java.lang.String _CVAR27 = action;
            int _CVAR28 = 5;
            java.lang.String _CVAR29 = _CVAR27.substring(_CVAR28);
            org.bukkit.entity.Player _CVAR22 = p;
            java.lang.String _CVAR30 = "Preformed " + _CVAR29;
            _CVAR22.sendMessage(_CVAR30);
            p.performCommand(action.substring(5));
        } else if (action.startsWith("heal")) {
            com.basicer.parchment.Context ctx = new com.basicer.parchment.Context();
            ctx.setTarget(com.basicer.parchment.parameters.Parameter.from(p));
            ctx.setCaster(com.basicer.parchment.parameters.Parameter.from(p));
            com.basicer.parchment.spells.Heal h = new com.basicer.parchment.spells.Heal();
            try {
                h.cast(ctx);
            } catch (com.basicer.parchment.Spell.FizzleException fizzle) {
                org.bukkit.entity.Player _CVAR32 = p;
                java.lang.String _CVAR33 = "The spell fizzles";
                _CVAR32.sendMessage(_CVAR33);
            }
        } else {
            java.lang.String _CVAR40 = action;
            org.bukkit.entity.Player _CVAR35 = p;
            java.lang.String _CVAR41 = "Couldnt do " + _CVAR40;
            _CVAR35.sendMessage(_CVAR41);
        }
        // e.getClickedBlock().breakNaturally(e.getPlayer().getItemInHand());
    }
}