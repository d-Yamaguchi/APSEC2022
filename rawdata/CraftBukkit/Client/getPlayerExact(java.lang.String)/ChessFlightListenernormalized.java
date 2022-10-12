/**
 * Globally enable or disable chessboard flight for all players.
 */
public void setEnabled(boolean enabled) {
    if (enabled == this.enabled) {
        return;
    }
    if (enabled) {
        for (org.bukkit.entity.Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
            if (bvm.getFlightRegion(player.getLocation()) != null) {
                controller.changeFlight(player, true);
            }
            // setFlightAllowed(player, bvm.getFlightRegion(player.getLocation()) != null);
        }
    } else {
        for (java.lang.String playerName : allowedToFly.keySet()) {
            java.lang.String _CVAR0 = playerName;
            org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayerExact(_CVAR0);
            if (player != null) {
                controller.yieldControl(player, gameModeAllowsFlight(player));
                // player.setAllowFlight(gameModeAllowsFlight(player));
                // restore previous flight/walk speed
                allowedToFly.get(playerName).restoreSpeeds();
                me.desht.dhutils.MiscUtil.alertMessage(player, me.desht.chesscraft.Messages.getString("Flight.flightDisabledByAdmin"));
            }
        }
        allowedToFly.clear();
    }
    this.enabled = enabled;
}