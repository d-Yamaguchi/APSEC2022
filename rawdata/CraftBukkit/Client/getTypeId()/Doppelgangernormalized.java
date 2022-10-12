// --------------------------------------------------------------------------
/**
 * Event handler for placing blocks.message
 *
 * Checks that a named item is stacked on a configured shape made of blocks of
 * the requisite material.
 *
 * @param event
 * 		the event.
 */
@org.bukkit.event.EventHandler(ignoreCancelled = true)
public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
    // Ignore named hoes tilling soil by checking if the item is a block.
    if ((!placedItem.hasItemMeta()) || (!placedItem.getType().isBlock())) {
        return;
    }
    org.bukkit.inventory.meta.ItemMeta meta = placedItem.getItemMeta();
    org.bukkit.event.block.BlockPlaceEvent _CVAR4 = event;
    org.bukkit.inventory.ItemStack placedItem = _CVAR4.getItemInHand();
    if (meta.hasDisplayName()) {
        java.lang.String doppelgangerName = meta.getDisplayName();
        java.util.regex.Matcher nameMatcher = _namePattern.matcher(doppelgangerName);
        if ((_configuration.warnOnInvalidName() && (!_configuration.isArbitraryNameAllowed())) && (!nameMatcher.matches())) {
            event.getPlayer().sendMessage(((org.bukkit.ChatColor.DARK_RED + "\"") + doppelgangerName) + "\" is not a valid player name.");
        }// if name is allowed
         else {
            org.bukkit.World world = event.getPlayer().getWorld();
            // Is a specific shape required to spawn a doppelganger of this name?
            java.util.ArrayList<io.github.totemo.doppelganger.CreatureShape> shapes = _creatureFactory.getPlayerShapes(doppelgangerName);
            org.bukkit.event.block.BlockPlaceEvent _CVAR1 = event;
            org.bukkit.block.Block _CVAR2 = _CVAR1.getBlock();
            org.bukkit.Location loc = _CVAR2.getLocation();
            if (shapes != null) {
                // Search the shapes associated with the specific player name.
                io.github.totemo.doppelganger.CreatureShape shape = null;
                for (io.github.totemo.doppelganger.CreatureShape tryShape : shapes) {
                    org.bukkit.inventory.ItemStack _CVAR5 = placedItem;
                    io.github.totemo.doppelganger.CreatureShape _CVAR0 = tryShape;
                    org.bukkit.Location _CVAR3 = loc;
                    int _CVAR6 = _CVAR5.getTypeId();
                    boolean _CVAR7 = _CVAR0.isComplete(_CVAR3, _CVAR6);
                    if () {
                        if (tryShape.hasBorder(loc)) {
                            shape = tryShape;
                        } else {
                            event.getPlayer().sendMessage(org.bukkit.ChatColor.YELLOW + "You need a one block gap horizontally around the shape.");
                        }
                        break;
                    }
                }// for

                if (shape == null) {
                    event.getPlayer().sendMessage(((org.bukkit.ChatColor.YELLOW + "That's not how you summon ") + doppelgangerName) + ".");
                } else {
                    doDoppelganger(doppelgangerName, _creatureFactory.getPlayerCreature(doppelgangerName), shape, event);
                }
            } else {
                // Generic case where the doppelganger name doesn't matter.
                // Check whether there is a complete creature under the trigger block.
                io.github.totemo.doppelganger.CreatureShape shape = _creatureFactory.getCreatureShape(loc, event.getPlayer().getItemInHand());
                if (shape != null) {
                    if (shape.hasBorder(loc)) {
                        java.lang.String creatureType = shape.chooseCreatureType();
                        if (_creatureFactory.isValidCreatureType(creatureType)) {
                            doDoppelganger(doppelgangerName, creatureType, shape, event);
                        } else {
                            getLogger().warning(java.lang.String.format(java.util.Locale.US, "Player %s tried to spawn a doppelganger named %s at (%g,%g,%g) in %s of invalid type %s by building a %s.", event.getPlayer().getName(), doppelgangerName, loc.getX(), loc.getY(), loc.getZ(), world.getName(), creatureType, shape.getName()));
                        }
                    } else {
                        event.getPlayer().sendMessage(org.bukkit.ChatColor.YELLOW + "You need a one block gap horizontally around the shape.");
                    }
                }
            }
        }// if name is allowed

    }
}// onBlockPlace
