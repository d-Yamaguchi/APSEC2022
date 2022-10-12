@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent e) {
    org.bukkit.Material _CVAR0 = org.bukkit.Material.BOOK_AND_QUILL;
    boolean _CVAR1 = holding.getType() != _CVAR0;
    org.bukkit.Material _CVAR3 = org.bukkit.Material.WRITTEN_BOOK;
    boolean _CVAR4 = holding.getType() != _CVAR3;
    boolean _CVAR2 = _CVAR1 && _CVAR4;
    org.bukkit.event.player.PlayerInteractEvent _CVAR5 = e;
    org.bukkit.entity.Player p = _CVAR5.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR7 = _CVAR5;
    org.bukkit.entity.Player p = _CVAR7.getPlayer();
    org.bukkit.entity.Player _CVAR8 = p;
    org.bukkit.inventory.ItemStack holding = _CVAR8.getItemInHand();
    if () {
        org.bukkit.inventory.ItemStack _CVAR9 = holding;
        org.bukkit.Material _CVAR10 = _CVAR9.getType();
        java.lang.String _CVAR11 = _CVAR10.toString();
        org.bukkit.entity.Player _CVAR6 = p;
        java.lang.String _CVAR12 = "MATERIAL IS " + _CVAR11;
        _CVAR6.sendMessage(_CVAR12);
        return;
    }
    p.sendMessage("Clicky click " + e.getAction().toString());
    com.basicer.parchment.craftbukkit.Book b = com.basicer.parchment.craftbukkit.Book.createFromBukkitItemStack(holding);
    if ((e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR) || (e.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
        if ((e.getClickedBlock() != null) && (e.getClickedBlock().getType() == org.bukkit.Material.BOOKSHELF)) {
            b.unlock();
            e.setCancelled(true);
            return;
        }
        p.sendMessage(b.getFullText());
        java.lang.String action = b.getFullText();
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
            p.sendMessage("Preformed " + action.substring(5));
            p.performCommand(action.substring(5));
        } else if (action.startsWith("heal")) {
            com.basicer.parchment.Context ctx = new com.basicer.parchment.Context();
            ctx.setTarget(com.basicer.parchment.parameters.Parameter.from(p));
            ctx.setCaster(com.basicer.parchment.parameters.Parameter.from(p));
            com.basicer.parchment.spells.Heal h = new com.basicer.parchment.spells.Heal();
            try {
                h.cast(ctx);
            } catch (com.basicer.parchment.Spell.FizzleException fizzle) {
                p.sendMessage("The spell fizzles");
            }
        } else {
            p.sendMessage("Couldnt do " + action);
        }
        // e.getClickedBlock().breakNaturally(e.getPlayer().getItemInHand());
    }
}