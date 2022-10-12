private void handleEvent(org.bukkit.event.Cancellable event, org.bukkit.entity.Player requester, org.bukkit.entity.Player requested, me.josvth.trade.request.RequestMethod method) {
    org.bukkit.entity.Player _CVAR1 = requester;
    me.josvth.trade.request.RequestManager _CVAR0 = requestManager;
    java.lang.String _CVAR2 = _CVAR1.getName();
    org.bukkit.entity.Player _CVAR3 = requested;
    org.bukkit.entity.Player _CVAR5 = _CVAR3;
    java.lang.String _CVAR4 = _CVAR5.getName();
    // First we check if the requester his answering a request
    me.josvth.trade.request.Request request = _CVAR0.getRequest(_CVAR2, _CVAR4);
    if (request != null) {
        if (requestManager.mayUseMethod(requester, method)) {
            event.setCancelled(true);
            me.josvth.trade.request.RequestRestriction restriction = requestManager.accept(request);
            if (restriction == RequestRestriction.ALLOW) {
                org.bukkit.entity.Player _CVAR11 = requested;
                me.josvth.bukkitformatlibrary.FormattedMessage _CVAR8 = message;
                org.bukkit.entity.Player _CVAR9 = requester;
                java.lang.String _CVAR10 = "%player%";
                java.lang.String _CVAR12 = _CVAR11.getName();
                _CVAR8.send(_CVAR9, _CVAR10, _CVAR12);
                me.josvth.bukkitformatlibrary.managers.FormatManager _CVAR6 = formatManager;
                java.lang.String _CVAR7 = "trading.started";
                me.josvth.bukkitformatlibrary.managers.FormatManager _CVAR13 = _CVAR6;
                java.lang.String _CVAR14 = _CVAR7;
                me.josvth.bukkitformatlibrary.FormattedMessage message = _CVAR13.getMessage(_CVAR14);
                org.bukkit.entity.Player _CVAR18 = requester;
                me.josvth.bukkitformatlibrary.FormattedMessage _CVAR15 = message;
                org.bukkit.entity.Player _CVAR16 = requested;
                java.lang.String _CVAR17 = "%player%";
                java.lang.String _CVAR19 = _CVAR18.getName();
                _CVAR15.send(_CVAR16, _CVAR17, _CVAR19);
            } else {
                org.bukkit.entity.Player _CVAR25 = requested;
                me.josvth.bukkitformatlibrary.FormattedMessage _CVAR22 = message;
                org.bukkit.entity.Player _CVAR23 = requester;
                java.lang.String _CVAR24 = "%player%";
                java.lang.String _CVAR26 = _CVAR25.getName();
                _CVAR22.send(_CVAR23, _CVAR24, _CVAR26);
                me.josvth.bukkitformatlibrary.managers.FormatManager _CVAR20 = formatManager;
                 _CVAR21 = restriction.tradeMessagePath;
                me.josvth.bukkitformatlibrary.managers.FormatManager _CVAR27 = _CVAR20;
                 _CVAR28 = _CVAR21;
                me.josvth.bukkitformatlibrary.FormattedMessage message = _CVAR27.getMessage(_CVAR28);
                org.bukkit.entity.Player _CVAR32 = requester;
                me.josvth.bukkitformatlibrary.FormattedMessage _CVAR29 = message;
                org.bukkit.entity.Player _CVAR30 = requested;
                java.lang.String _CVAR31 = "%player%";
                java.lang.String _CVAR33 = _CVAR32.getName();
                _CVAR29.send(_CVAR30, _CVAR31, _CVAR33);
            }
        }
    } else {
        org.bukkit.entity.Player _CVAR34 = requester;
        java.lang.String _CVAR35 = _CVAR34.getName();
        org.bukkit.entity.Player _CVAR36 = requested;
        org.bukkit.entity.Player _CVAR40 = _CVAR36;
        java.lang.String _CVAR37 = _CVAR40.getName();
        me.josvth.trade.request.RequestMethod _CVAR38 = method;
        me.josvth.trade.request.Request _CVAR39 = new me.josvth.trade.request.Request(_CVAR35, _CVAR37, _CVAR38);
        request = _CVAR39;
        me.josvth.trade.request.RequestRestriction restriction = requestManager.checkRequest(request);
        if (restriction == RequestRestriction.ALLOW) {
            requestManager.submit(request);
            event.setCancelled(true);
        }
        if ((restriction != RequestRestriction.METHOD) && (restriction != RequestRestriction.PERMISSION)) {
            me.josvth.bukkitformatlibrary.managers.FormatManager _CVAR41 = formatManager;
             _CVAR42 = restriction.requestMessagePath;
            me.josvth.bukkitformatlibrary.FormattedMessage message = _CVAR41.getMessage(_CVAR42);
            org.bukkit.entity.Player _CVAR46 = requested;
            me.josvth.bukkitformatlibrary.FormattedMessage _CVAR43 = message;
            org.bukkit.entity.Player _CVAR44 = requester;
            java.lang.String _CVAR45 = "%player%";
            java.lang.String _CVAR47 = _CVAR46.getName();
            _CVAR43.send(_CVAR44, _CVAR45, _CVAR47);
        }
    }
}