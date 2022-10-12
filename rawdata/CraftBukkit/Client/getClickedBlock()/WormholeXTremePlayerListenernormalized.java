/* (non-Javadoc)
@see org.bukkit.event.player.PlayerListener#onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent)
 */
@java.lang.Override
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    <nulltype> _CVAR0 = null;
    boolean _CVAR1 = eventFinal.getClickedBlock() != _CVAR0;
    final org.bukkit.event.player.PlayerInteractEvent eventFinal = event;
    if () {
        org.bukkit.event.player.PlayerInteractEvent _CVAR5 = eventFinal;
        org.bukkit.block.Block _CVAR6 = _CVAR5.getClickedBlock();
        org.bukkit.Material _CVAR7 = _CVAR6.getType();
        java.lang.String _CVAR8 = _CVAR7.toString();
         _CVAR9 = (((((("Caught Player: \"" + eventFinal.getPlayer().getName()) + "\" Event type: \"") + eventFinal.getType().toString()) + "\" Action Type: \"") + eventFinal.getAction().toString()) + "\" Event Block Type: \"") + _CVAR8;
         _CVAR10 = _CVAR9 + "\" Event World: \"";
        org.bukkit.event.player.PlayerInteractEvent _CVAR15 = eventFinal;
        org.bukkit.block.Block _CVAR16 = _CVAR15.getClickedBlock();
        org.bukkit.World _CVAR17 = _CVAR16.getWorld();
        java.lang.String _CVAR18 = _CVAR17.toString();
         _CVAR11 = _CVAR10 + _CVAR18;
         _CVAR12 = _CVAR11 + "\" Event Block: ";
        org.bukkit.event.player.PlayerInteractEvent _CVAR19 = eventFinal;
        org.bukkit.block.Block _CVAR20 = _CVAR19.getClickedBlock();
        java.lang.String _CVAR21 = _CVAR20.toString();
         _CVAR13 = _CVAR12 + _CVAR21;
         _CVAR2 = com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin();
        java.util.logging.Level _CVAR3 = java.util.logging.Level.FINE;
        boolean _CVAR4 = false;
         _CVAR14 = _CVAR13 + "\"";
        _CVAR2.prettyLog(_CVAR3, _CVAR4, _CVAR14);
        if (com.wormhole_xtreme.wormhole.WormholeXTremePlayerListener.handlePlayerInteractEvent(eventFinal)) {
            eventFinal.setCancelled(true);
            org.bukkit.event.player.PlayerInteractEvent _CVAR25 = eventFinal;
            org.bukkit.block.Block _CVAR26 = _CVAR25.getClickedBlock();
            org.bukkit.Material _CVAR27 = _CVAR26.getType();
            java.lang.String _CVAR28 = _CVAR27.toString();
             _CVAR29 = (((((("Cancelled Player: \"" + eventFinal.getPlayer().getName()) + "\" Event type: \"") + eventFinal.getType().toString()) + "\" Action Type: \"") + eventFinal.getAction().toString()) + "\" Event Block Type: \"") + _CVAR28;
             _CVAR30 = _CVAR29 + "\" Event World: \"";
            org.bukkit.event.player.PlayerInteractEvent _CVAR35 = eventFinal;
            org.bukkit.block.Block _CVAR36 = _CVAR35.getClickedBlock();
            org.bukkit.World _CVAR37 = _CVAR36.getWorld();
            java.lang.String _CVAR38 = _CVAR37.toString();
             _CVAR31 = _CVAR30 + _CVAR38;
             _CVAR32 = _CVAR31 + "\" Event Block: ";
            org.bukkit.event.player.PlayerInteractEvent _CVAR39 = eventFinal;
            org.bukkit.block.Block _CVAR40 = _CVAR39.getClickedBlock();
            java.lang.String _CVAR41 = _CVAR40.toString();
             _CVAR33 = _CVAR32 + _CVAR41;
             _CVAR22 = com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin();
            java.util.logging.Level _CVAR23 = java.util.logging.Level.FINE;
            boolean _CVAR24 = false;
             _CVAR34 = _CVAR33 + "\"";
            _CVAR22.prettyLog(_CVAR23, _CVAR24, _CVAR34);
        }
    } else {
        com.wormhole_xtreme.wormhole.WormholeXTreme.getThisPlugin().prettyLog(java.util.logging.Level.FINE, false, ((("Caught and ignored Player: \"" + eventFinal.getPlayer().getName()) + "\" Event type: \"") + eventFinal.getType().toString()) + "\"");
    }
}