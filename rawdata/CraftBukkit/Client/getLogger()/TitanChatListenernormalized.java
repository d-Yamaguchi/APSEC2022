/**
 * Listens to the PlayerChatEvent
 *
 * @param event
 * 		PlayerChatEvent
 */
@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST, ignoreCancelled = true)
public void onPlayerChat(org.bukkit.event.player.PlayerChatEvent event) {
    org.bukkit.event.player.PlayerChatEvent _CVAR12 = event;
    org.bukkit.event.player.PlayerChatEvent _CVAR24 = _CVAR12;
    org.bukkit.entity.Player player = _CVAR24.getPlayer();
    org.bukkit.event.player.PlayerChatEvent _CVAR27 = _CVAR24;
    org.bukkit.entity.Player player = _CVAR27.getPlayer();
    org.bukkit.event.player.PlayerChatEvent _CVAR14 = event;
    org.bukkit.event.player.PlayerChatEvent _CVAR29 = _CVAR14;
    java.lang.String message = _CVAR29.getMessage();
    if (plugin.enableChannels()) {
        event.setCancelled(true);
        com.titankingdoms.nodinchan.titanchat.TitanChat _CVAR5 = plugin;
         _CVAR6 = _CVAR5.getConfig();
        java.lang.String _CVAR7 = "channels.quick-message";
        java.lang.String quickMessage = _CVAR6.getString(_CVAR7);
        if (message.startsWith(quickMessage) && (!message.substring(quickMessage.length()).startsWith(" "))) {
            com.titankingdoms.nodinchan.titanchat.TitanChat _CVAR2 = plugin;
            java.lang.String _CVAR8 = quickMessage;
            java.lang.String _CVAR4 = message.split(" ")[0];
            int _CVAR9 = _CVAR8.length();
             _CVAR3 = _CVAR2.getChannelManager();
            java.lang.String _CVAR10 = _CVAR4.substring(_CVAR9);
            com.titankingdoms.nodinchan.titanchat.channel.Channel channel = _CVAR3.getChannel(_CVAR10);
            if (channel != null) {
                if (!channel.canAccess(player)) {
                    return;
                }
                if (voiceless(player, channel)) {
                    return;
                }
                java.lang.String _CVAR16 = message.split(" ")[0];
                java.lang.String _CVAR15 = message;
                int _CVAR17 = _CVAR16.length();
                com.titankingdoms.nodinchan.titanchat.channel.Channel _CVAR11 = channel;
                org.bukkit.entity.Player _CVAR13 = player;
                java.lang.String _CVAR18 = _CVAR15.substring(_CVAR17);
                java.lang.String log = _CVAR11.sendMessage(_CVAR13, _CVAR18);
                if (!log.equals("")) {
                    java.util.logging.Logger _CVAR0 = org.bukkit.Bukkit.getLogger();
                    java.util.logging.Level _CVAR1 = java.util.logging.Level.INFO;
                    java.lang.String _CVAR19 = log;
                    _CVAR0.log(_CVAR1, _CVAR19);
                }
            } else {
                plugin.sendWarning(player, "No such channel");
            }
            return;
        }
        if (channel == null) {
            plugin.sendWarning(player, "You are not in a channel, please join one to chat");
            return;
        }
        if (voiceless(player, channel)) {
            return;
        }
        com.titankingdoms.nodinchan.titanchat.TitanChat _CVAR22 = plugin;
         _CVAR23 = _CVAR22.getChannelManager();
        org.bukkit.entity.Player _CVAR25 = player;
        com.titankingdoms.nodinchan.titanchat.channel.Channel channel = _CVAR23.getChannel(_CVAR25);
        com.titankingdoms.nodinchan.titanchat.channel.Channel _CVAR26 = channel;
        org.bukkit.entity.Player _CVAR28 = player;
        java.lang.String _CVAR30 = message;
        java.lang.String log = _CVAR26.sendMessage(_CVAR28, _CVAR30);
        if (!log.equals("")) {
            java.util.logging.Logger _CVAR20 = org.bukkit.Bukkit.getLogger();
            java.util.logging.Level _CVAR21 = java.util.logging.Level.INFO;
            java.lang.String _CVAR31 = log;
            _CVAR20.log(_CVAR21, _CVAR31);
        }
    } else {
        event.setFormat(plugin.getFormatHandler().format(player, null, true));
        com.titankingdoms.nodinchan.titanchat.event.MessageSendEvent sendEvent = new com.titankingdoms.nodinchan.titanchat.event.MessageSendEvent(player, null, plugin.getServer().getOnlinePlayers(), message);
        plugin.getServer().getPluginManager().callEvent(sendEvent);
        if (sendEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }
        java.lang.String colour = plugin.getConfig().getString("channels.chat-display-colour");
        event.setMessage(plugin.getFormatHandler().colourise(colour + sendEvent.getMessage()));
    }
}