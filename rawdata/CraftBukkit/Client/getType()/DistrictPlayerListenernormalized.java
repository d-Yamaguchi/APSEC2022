@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    // Event details
    org.bukkit.entity.Player eventPlayer = event.getPlayer();
    if (block == null) {
        return;
    }
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.block.Block block = _CVAR0.getClickedBlock();
    org.bukkit.block.Block _CVAR1 = block;
    // Don't do anything for pressure plates
    org.bukkit.Material type = _CVAR1.getType();
    if ((type == org.bukkit.Material.WOOD_PLATE) || (type == org.bukkit.Material.STONE_PLATE)) {
        return;
    }
    if ((plugin.lwc != null) && (plugin.lwc.findProtection(block) != null)) {
        // Let LWC handle it.
        return;
    }
    org.bukkit.Location eventLocation = block.getLocation();
    // Regions the block is in
    java.util.ArrayList<com.md_5.district.Region> currentRegionSet = com.md_5.district.Util.getRegions(eventLocation);
    java.lang.String regions = "";
    // Check if they are denied from placing in ANY region the block is in
    if (currentRegionSet != null) {
        for (com.md_5.district.Region r : currentRegionSet) {
            if (eventPlayer.hasPermission("district.wand") && (eventPlayer.getItemInHand().getTypeId() == Config.wand)) {
                regions += ((r.getName() + " (") + r.getOwner()) + "), ";
            }
            if (!r.canUse(eventPlayer)) {
                r.sendDeny(eventPlayer);
                event.setCancelled(true);
            }
        }
    }
    if (!regions.equals("")) {
        eventPlayer.sendMessage((org.bukkit.ChatColor.GOLD + "District: Applicable regions: ") + regions.substring(0, regions.length() - 2));
    } else if (eventPlayer.hasPermission("district.wand") && (eventPlayer.getItemInHand().getTypeId() == Config.wand)) {
        eventPlayer.sendMessage(org.bukkit.ChatColor.GOLD + "District: There are no applicable regions here");
    }
    return;
}