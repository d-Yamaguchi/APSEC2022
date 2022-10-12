/**
 * Changes to the next arrow.
 */
protected void changeArrowType(org.bukkit.entity.Player player) {
    de.tobiyas.racesandclasses.datacontainer.arrow.ArrowManager arrowManager = plugin.getPlayerManager().getArrowManagerOfPlayer(player.getName());
    de.tobiyas.racesandclasses.traitcontainer.traits.arrows.AbstractArrow arrow = arrowManager.getCurrentArrow();
    if ((arrow == null) || (arrow != this)) {
        return;
    }
    de.tobiyas.racesandclasses.traitcontainer.traits.arrows.AbstractArrow newArrow = arrowManager.nextArrow();
    if ((newArrow != null) && (newArrow != arrow)) {
        java.lang.String _CVAR1 = newArrow.getArrowName();
        org.bukkit.entity.Player _CVAR0 = player;
        java.lang.String _CVAR2 = ((org.bukkit.ChatColor.GREEN + "Switched arrows to: ") + org.bukkit.ChatColor.LIGHT_PURPLE) + _CVAR1;
        _CVAR0.sendMessage(_CVAR2);
    }
}