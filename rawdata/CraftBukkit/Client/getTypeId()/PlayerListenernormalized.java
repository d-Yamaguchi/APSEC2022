/**
 * Create a cannon if the building process is finished Deletes a projectile
 * if loaded Checks for redstone torches if built
 *
 * @param event
 * 		
 */
@org.bukkit.event.EventHandler
public void BlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
    org.bukkit.Location blockLoc = block.getLocation();
    // setup a new cannon
    cannonManager.getCannon(blockLoc, event.getPlayer().getName());
    org.bukkit.event.block.BlockPlaceEvent _CVAR10 = event;
    org.bukkit.block.Block block = _CVAR10.getBlockPlaced();
    org.bukkit.event.block.BlockPlaceEvent _CVAR13 = _CVAR10;
    org.bukkit.block.Block block = _CVAR13.getBlockPlaced();
    // delete placed projectile or gunpowder if clicked against the barrel
    if (event.getBlockAgainst() != null) {
        org.bukkit.event.block.BlockPlaceEvent _CVAR2 = event;
        org.bukkit.block.Block _CVAR3 = _CVAR2.getBlockAgainst();
        org.bukkit.Location barrel = _CVAR3.getLocation();
        org.bukkit.event.block.BlockPlaceEvent _CVAR5 = event;
        org.bukkit.entity.Player _CVAR6 = _CVAR5.getPlayer();
        at.pavlov.cannons.CannonManager _CVAR1 = cannonManager;
        org.bukkit.Location _CVAR4 = barrel;
        java.lang.String _CVAR7 = _CVAR6.getName();
        boolean _CVAR8 = true;
        // check if block is cannonblock
        at.pavlov.cannons.cannon.Cannon cannon = _CVAR1.getCannon(_CVAR4, _CVAR7, _CVAR8);
        if (cannon != null) {
            org.bukkit.block.Block _CVAR11 = block;
            org.bukkit.block.Block _CVAR14 = block;
            at.pavlov.cannons.Cannons _CVAR0 = plugin;
            at.pavlov.cannons.cannon.Cannon _CVAR9 = cannon;
            int _CVAR12 = _CVAR11.getTypeId();
            byte _CVAR15 = _CVAR14.getData();
            // delete projectile
            at.pavlov.cannons.projectile.Projectile projectile = _CVAR0.getProjectile(_CVAR9, _CVAR12, _CVAR15);
            if ((projectile != null) && cannon.getCannonDesign().canLoad(projectile)) {
                // check if the placed block is not part of the cannon
                if (!cannon.isCannonBlock(event.getBlock())) {
                    event.setCancelled(true);
                }
            }
            // delete gunpowder block
            if (cannon.getCannonDesign().getGunpowderType().equalsFuzzy(event.getBlock())) {
                // check if the placed block is not part of the cannon
                if (!cannon.isCannonBlock(event.getBlock())) {
                    event.setCancelled(true);
                }
            }
        }
    }
    // Place redstonetorch under to the cannon
    if ((event.getBlockPlaced().getType() == org.bukkit.Material.REDSTONE_TORCH_ON) || (event.getBlockPlaced().getType() == org.bukkit.Material.REDSTONE_TORCH_OFF)) {
        // check cannon
        org.bukkit.Location loc = event.getBlock().getRelative(org.bukkit.block.BlockFace.UP).getLocation();
        at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(loc, event.getPlayer().getName(), true);
        if (cannon != null) {
            // check permissions
            if (event.getPlayer().hasPermission(cannon.getCannonDesign().getPermissionRedstone()) == false) {
                // check if the placed block is in the redstone torch interface
                if (cannon.isRedstoneTorchInterface(event.getBlock().getLocation())) {
                    userMessages.displayMessage(event.getPlayer(), MessageEnum.PermissionErrorRedstone);
                    event.setCancelled(true);
                }
            }
        }
    }
    // Place redstone wire next to the button
    if (event.getBlockPlaced().getType() == org.bukkit.Material.REDSTONE_WIRE) {
        // check cannon
        for (org.bukkit.block.Block b : at.pavlov.cannons.utils.CannonsUtil.HorizontalSurroundingBlocks(event.getBlock())) {
            org.bukkit.Location loc = b.getLocation();
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(loc, event.getPlayer().getName(), true);
            if (cannon != null) {
                // check permissions
                if (event.getPlayer().hasPermission(cannon.getCannonDesign().getPermissionRedstone()) == false) {
                    // check if the placed block is in the redstone wire interface
                    if (cannon.isRedstoneWireInterface(event.getBlock().getLocation())) {
                        userMessages.displayMessage(event.getPlayer(), MessageEnum.PermissionErrorRedstone);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    // cancel igniting of the cannon
    if (event.getBlock().getType() == org.bukkit.Material.FIRE) {
        // check cannon
        org.bukkit.Location loc = event.getBlockAgainst().getLocation();
        if (cannonManager.getCannon(loc, event.getPlayer().getName(), true) != null) {
            event.setCancelled(true);
        }
    }
}