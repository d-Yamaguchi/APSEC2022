/**
 * Do dial.
 *
 * @param player
 * 		the player
 * @param args
 * 		the args
 * @return true, if successful
 */
private static boolean doDial(final org.bukkit.entity.Player player, final java.lang.String[] args) {
    final de.luricos.bukkit.WormholeXTreme.Wormhole.model.Stargate start = de.luricos.bukkit.WormholeXTreme.Wormhole.model.StargateManager.removeActivatedStargate(player);
    if (start != null) {
        if (de.luricos.bukkit.WormholeXTreme.Wormhole.permissions.WXPermissions.checkWXPermissions(player, start, PermissionType.DIALER)) {
            final java.lang.String startnetwork = de.luricos.bukkit.WormholeXTreme.Wormhole.bukkit.commands.CommandUtilities.getGateNetwork(start);
            if (!start.getGateName().equals(args[0])) {
                final de.luricos.bukkit.WormholeXTreme.Wormhole.model.Stargate target = de.luricos.bukkit.WormholeXTreme.Wormhole.model.StargateManager.getStargate(args[0]);
                // No target
                if (target == null) {
                    de.luricos.bukkit.WormholeXTreme.Wormhole.bukkit.commands.CommandUtilities.closeGate(start, false);
                    org.bukkit.entity.Player _CVAR0 = player;
                     _CVAR1 = ConfigManager.MessageStrings.targetInvalid.toString();
                    _CVAR0.sendMessage(_CVAR1);
                    return true;
                }
                final java.lang.String targetnetwork = de.luricos.bukkit.WormholeXTreme.Wormhole.bukkit.commands.CommandUtilities.getGateNetwork(target);
                de.luricos.bukkit.WormholeXTreme.Wormhole.utils.WXTLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Dial Target - Gate: \"" + target.getGateName()) + "\" Network: \"") + targetnetwork) + "\"");
                // Not on same network
                if (!startnetwork.equals(targetnetwork)) {
                    de.luricos.bukkit.WormholeXTreme.Wormhole.bukkit.commands.CommandUtilities.closeGate(start, false);
                    java.lang.String _CVAR3 = " Not on same network.";
                    org.bukkit.entity.Player _CVAR2 = player;
                     _CVAR4 = ConfigManager.MessageStrings.targetInvalid.toString() + _CVAR3;
                    _CVAR2.sendMessage(_CVAR4);
                    return true;
                }
                if (start.isGateIrisActive()) {
                    start.toggleIrisActive(false);
                }
                if ((!target.getGateIrisDeactivationCode().equals("")) && target.isGateIrisActive()) {
                    if ((args.length >= 2) && target.getGateIrisDeactivationCode().equals(args[1])) {
                        if (target.isGateIrisActive()) {
                            target.toggleIrisActive(false);
                            java.lang.String _CVAR6 = "IDC accepted. Iris has been deactivated.";
                            org.bukkit.entity.Player _CVAR5 = player;
                             _CVAR7 = ConfigManager.MessageStrings.normalHeader.toString() + _CVAR6;
                            _CVAR5.sendMessage(_CVAR7);
                        }
                    }
                }
                if (start.dialStargate(target, false)) {
                    org.bukkit.entity.Player _CVAR8 = player;
                     _CVAR9 = ConfigManager.MessageStrings.gateConnected.toString();
                    _CVAR8.sendMessage(_CVAR9);
                } else {
                    de.luricos.bukkit.WormholeXTreme.Wormhole.bukkit.commands.CommandUtilities.closeGate(start, false);
                    org.bukkit.entity.Player _CVAR10 = player;
                     _CVAR11 = ConfigManager.MessageStrings.targetIsActive.toString();
                    _CVAR10.sendMessage(_CVAR11);
                }
            } else {
                de.luricos.bukkit.WormholeXTreme.Wormhole.bukkit.commands.CommandUtilities.closeGate(start, false);
                org.bukkit.entity.Player _CVAR12 = player;
                 _CVAR13 = ConfigManager.MessageStrings.targetIsSelf.toString();
                _CVAR12.sendMessage(_CVAR13);
            }
        } else {
            org.bukkit.entity.Player _CVAR14 = player;
             _CVAR15 = ConfigManager.MessageStrings.permissionNo.toString();
            _CVAR14.sendMessage(_CVAR15);
        }
    } else {
        org.bukkit.entity.Player _CVAR16 = player;
         _CVAR17 = ConfigManager.MessageStrings.gateNotActive.toString();
        _CVAR16.sendMessage(_CVAR17);
    }
    return true;
}