@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOW)
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    if ((!canBeShop(event.getClickedBlock())) || (event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
        return;
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.event.player.PlayerInteractEvent _CVAR9 = _CVAR0;
    org.bukkit.event.player.PlayerInteractEvent _CVAR19 = _CVAR9;
    org.bukkit.block.Block _CVAR1 = _CVAR19.getClickedBlock();
    org.bukkit.block.Block _CVAR10 = _CVAR1;
    org.bukkit.block.Block _CVAR20 = _CVAR10;
    org.bukkit.block.BlockState _CVAR2 = ((org.bukkit.inventory.InventoryHolder) (_CVAR20.getState()));
    org.bukkit.block.BlockState _CVAR11 = _CVAR2;
    org.bukkit.block.BlockState _CVAR21 = _CVAR11;
    org.bukkit.inventory.Inventory inv = _CVAR21.getInventory();
    if (event.getPlayer().isSneaking()) {
        if (net.miscjunk.fancyshop.Shop.isShop(inv)) {
            org.bukkit.event.player.PlayerInteractEvent _CVAR4 = event;
            org.bukkit.entity.Player _CVAR5 = _CVAR4.getPlayer();
            org.bukkit.inventory.Inventory _CVAR3 = inv;
            java.lang.String _CVAR6 = _CVAR5.getName();
            net.miscjunk.fancyshop.Shop shop = net.miscjunk.fancyshop.Shop.fromInventory(_CVAR3, _CVAR6);
             _CVAR7 = !shop.getOwner().equals(event.getPlayer().getName());
             _CVAR8 = _CVAR7 && (!event.getPlayer().hasPermission("fancyshop.open"));
            if () {
                event.setCancelled(true);
                net.miscjunk.fancyshop.Chat.e(event.getPlayer(), "You don't have permission to open this shop chest.");
            }
        }
    } else {
        org.bukkit.event.player.PlayerInteractEvent _CVAR13 = event;
        org.bukkit.event.player.PlayerInteractEvent _CVAR16 = _CVAR13;
        org.bukkit.entity.Player p = _CVAR16.getPlayer();
        org.bukkit.event.player.PlayerInteractEvent _CVAR23 = _CVAR16;
        org.bukkit.entity.Player p = _CVAR23.getPlayer();
        if (cmdExecutor.hasPending(p)) {
            // wait for the high-priority handler to catch it
        } else if (net.miscjunk.fancyshop.Shop.isShop(inv)) {
            event.setCancelled(true);
            if (p.hasPermission("fancyshop.use")) {
                org.bukkit.entity.Player _CVAR17 = p;
                org.bukkit.inventory.Inventory _CVAR12 = inv;
                org.bukkit.entity.Player _CVAR14 = p;
                org.bukkit.entity.Player _CVAR24 = _CVAR14;
                java.lang.String _CVAR15 = _CVAR24.getName();
                org.bukkit.inventory.Inventory _CVAR22 = _CVAR12;
                java.lang.String _CVAR25 = _CVAR15;
                net.miscjunk.fancyshop.Shop shop = net.miscjunk.fancyshop.Shop.fromInventory(_CVAR22, _CVAR25);
                net.miscjunk.fancyshop.Shop _CVAR26 = shop;
                java.lang.String _CVAR18 = _CVAR17.getName();
                 _CVAR27 = _CVAR26.getOwner();
                boolean _CVAR28 = _CVAR18.equals(_CVAR27);
                 _CVAR29 = _CVAR28 && (event.getMaterial() != org.bukkit.Material.STICK);
                if () {
                    shop.edit(p);
                } else {
                    shop.open(p);
                }
            } else {
                net.miscjunk.fancyshop.Chat.e(p, "You don't have permission!");
            }
        }
    }
}