/**
 * handles redstone events (torch, wire, repeater, button)
 *
 * @param event
 * 		
 */
@org.bukkit.event.EventHandler
public void RedstoneEvent(org.bukkit.event.block.BlockRedstoneEvent event) {
    if (block == null) {
        return;
    }
    org.bukkit.event.block.BlockRedstoneEvent _CVAR1 = event;
    org.bukkit.block.Block block = _CVAR1.getBlock();
    org.bukkit.block.Block _CVAR2 = block;
    int _CVAR3 = _CVAR2.getTypeId();
    java.lang.String _CVAR4 = "Redstone event was fired by " + _CVAR3;
    java.lang.String _CVAR5 = _CVAR4 + ":";
    at.pavlov.cannons.Cannons _CVAR0 = plugin;
    java.lang.String _CVAR6 = _CVAR5 + block.getData();
    _CVAR0.logDebug(_CVAR6);
    // ##########  redstone torch fire
    // off because it turn form off to on
    if (block.getType() == org.bukkit.Material.REDSTONE_TORCH_ON) {
        // go one block up and check this is a cannon
        at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(block.getRelative(org.bukkit.block.BlockFace.UP).getLocation(), null);
        if (cannon != null) {
            // there is cannon next to the torch - check if the torch is
            // place right
            plugin.logDebug("redstone torch");
            if (cannon.isRedstoneTorchInterface(block.getLocation())) {
                at.pavlov.cannons.event.CannonRedstoneEvent redEvent = new at.pavlov.cannons.event.CannonRedstoneEvent(cannon);
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(redEvent);
                if (redEvent.isCancelled()) {
                    return;
                }
                at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, null, cannon.getCannonDesign().isAutoreloadRedstone());
                plugin.logDebug("fire cannon returned: " + message.getString());
            }
        }
    }
    // ##########  redstone wire fire
    if (block.getType() == org.bukkit.Material.REDSTONE_WIRE) {
        // block is powered
        if (block.getData() == 0) {
            // check all block next to this if there is a cannon
            for (org.bukkit.block.Block b : at.pavlov.cannons.utils.CannonsUtil.HorizontalSurroundingBlocks(block)) {
                at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(b.getLocation(), null);
                if (cannon != null) {
                    // there is cannon next to the wire - check if the wire
                    // is place right
                    plugin.logDebug("redstone wire ");
                    if (cannon.isRedstoneWireInterface(block.getLocation())) {
                        at.pavlov.cannons.event.CannonRedstoneEvent redEvent = new at.pavlov.cannons.event.CannonRedstoneEvent(cannon);
                        org.bukkit.Bukkit.getServer().getPluginManager().callEvent(redEvent);
                        if (redEvent.isCancelled()) {
                            return;
                        }
                        at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, null, cannon.getCannonDesign().isAutoreloadRedstone());
                        plugin.logDebug("fire cannon returned: " + message.getString());
                    }
                }
            }
        }
    }
    int _CVAR7 = 149;
    boolean _CVAR8 = block.getTypeId() == _CVAR7;
    boolean _CVAR9 = (block.getType() == org.bukkit.Material.DIODE_BLOCK_OFF) || _CVAR8;
    // ##########  redstone repeater and comparator fire
    if () {
        // check all block next to this if there is a cannon
        for (org.bukkit.block.Block b : at.pavlov.cannons.utils.CannonsUtil.HorizontalSurroundingBlocks(block)) {
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(b.getLocation(), null);
            if (cannon != null) {
                // there is cannon next to the wire - check if the wire
                // is place right
                plugin.logDebug("redstone repeater ");
                if (cannon.isRedstoneRepeaterInterface(block.getLocation())) {
                    at.pavlov.cannons.event.CannonRedstoneEvent redEvent = new at.pavlov.cannons.event.CannonRedstoneEvent(cannon);
                    org.bukkit.Bukkit.getServer().getPluginManager().callEvent(redEvent);
                    if (redEvent.isCancelled()) {
                        return;
                    }
                    at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, null, cannon.getCannonDesign().isAutoreloadRedstone());
                    plugin.logDebug("fire cannon returned: " + message.getString());
                }
            }
        }
    }
    // ##########  fire with Button
    at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(event.getBlock().getLocation(), null);
    if (cannon != null) {
        // check if the button is a loading firing interface of the cannon
        if (cannon.isRestoneTrigger(event.getBlock().getLocation())) {
            // get the user of the cannon
            org.bukkit.entity.Player player = null;
            if (cannon.getLastUser() != null) {
                player = org.bukkit.Bukkit.getPlayer(cannon.getLastUser());
            }
            // reset user
            cannon.setLastUser("");
            if (player == null) {
                return;
            }
            plugin.logDebug("Redfire with button by " + player.getName());
            // register event with bukkit
            at.pavlov.cannons.event.CannonUseEvent useEvent = new at.pavlov.cannons.event.CannonUseEvent(cannon, player, at.pavlov.cannons.event.InteractAction.fireButton);
            org.bukkit.Bukkit.getServer().getPluginManager().callEvent(useEvent);
            if (useEvent.isCancelled()) {
                return;
            }
            // execute event
            boolean autoreload = player.isSneaking() && player.hasPermission(cannon.getCannonDesign().getPermissionAutoreload());
            at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, player, autoreload);
            userMessages.displayMessage(player, message, cannon);
        }
    }
}