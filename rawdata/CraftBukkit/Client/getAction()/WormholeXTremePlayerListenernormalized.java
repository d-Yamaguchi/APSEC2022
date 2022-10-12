/* (non-Javadoc)
@see org.bukkit.event.player.PlayerListener#onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent)
 */
@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    final org.bukkit.event.player.PlayerInteractEvent eventFinal = event;
    if (eventFinal.getClickedBlock() != null) {
        org.bukkit.event.player.PlayerInteractEvent _CVAR3 = eventFinal;
        org.bukkit.event.block.Action _CVAR4 = _CVAR3.getAction();
        java.lang.String _CVAR5 = _CVAR4.toString();
         _CVAR6 = (((("Caught Player: \"" + eventFinal.getPlayer().getName()) + "\" Event type: \"") + eventFinal.getType().toString()) + "\" Action Type: \"") + _CVAR5;
         _CVAR7 = _CVAR6 + "\" Event Block Type: \"";
         _CVAR8 = _CVAR7 + eventFinal.getClickedBlock().getType().toString();
         _CVAR9 = _CVAR8 + "\" Event World: \"";
         _CVAR10 = _CVAR9 + eventFinal.getClickedBlock().getWorld().toString();
         _CVAR11 = _CVAR10 + "\" Event Block: ";
         _CVAR12 = _CVAR11 + eventFinal.getClickedBlock().toString();
         _CVAR0 = com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin();
        java.util.logging.Level _CVAR1 = java.util.logging.Level.FINE;
        boolean _CVAR2 = false;
         _CVAR13 = _CVAR12 + "\"";
        _CVAR0.prettyLog(_CVAR1, _CVAR2, _CVAR13);
        if (com.wormhole_xtreme.wormhole.WormholeXTremePlayerListener.handlePlayerInteractEvent(eventFinal)) {
            eventFinal.setCancelled(true);
            org.bukkit.event.player.PlayerInteractEvent _CVAR17 = eventFinal;
            org.bukkit.event.block.Action _CVAR18 = _CVAR17.getAction();
            java.lang.String _CVAR19 = _CVAR18.toString();
             _CVAR20 = (((("Cancelled Player: \"" + eventFinal.getPlayer().getName()) + "\" Event type: \"") + eventFinal.getType().toString()) + "\" Action Type: \"") + _CVAR19;
             _CVAR21 = _CVAR20 + "\" Event Block Type: \"";
             _CVAR22 = _CVAR21 + eventFinal.getClickedBlock().getType().toString();
             _CVAR23 = _CVAR22 + "\" Event World: \"";
             _CVAR24 = _CVAR23 + eventFinal.getClickedBlock().getWorld().toString();
             _CVAR25 = _CVAR24 + "\" Event Block: ";
             _CVAR26 = _CVAR25 + eventFinal.getClickedBlock().toString();
             _CVAR14 = com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin();
            java.util.logging.Level _CVAR15 = java.util.logging.Level.FINE;
            boolean _CVAR16 = false;
             _CVAR27 = _CVAR26 + "\"";
            _CVAR14.prettyLog(_CVAR15, _CVAR16, _CVAR27);
        }
    } else {
        com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin().prettyLog(java.util.logging.Level.FINE, false, ((("Caught and ignored Player: \"" + eventFinal.getPlayer().getName()) + "\" Event type: \"") + eventFinal.getType().toString()) + "\"");
    }
}