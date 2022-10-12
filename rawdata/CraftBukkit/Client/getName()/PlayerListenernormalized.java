@org.bukkit.event.EventHandler(ignoreCancelled = true)
public void onPlayerChat(org.bukkit.event.player.AsyncPlayerChatEvent event) {
    // WorldEdit CUI call
    if (event.getMessage().startsWith("u00a7")) {
        event.setCancelled(true);
        return;
    }
    com.patrickanker.isay.ChatPlayer cp = com.patrickanker.isay.ISMain.getInstance().getRegisteredPlayer(event.getPlayer());
    if (cp.isMuted()) {
        if (!com.patrickanker.isay.ISMain.getInstance().getConfigData().getString("mute-key-phrase").equals(event.getMessage())) {
            com.patrickanker.isay.MuteServices.muteWarn(cp);
        } else {
            if (!cp.muteTimedOut()) {
                com.patrickanker.isay.MuteServices.muteWarn(cp);
                event.setCancelled(true);
                return;
            }
            cp.setMuted(false);
            cp.setMuteTimeout("");
            com.patrickanker.isay.MuteServices.unmuteAnnounce(cp);
        }
        java.util.Set<org.bukkit.entity.Player> recipients = event.getRecipients();
        recipients.clear();
        event.setCancelled(true);
        return;
    }
    if (event.getPlayer().getItemInHand() != null) {
        java.lang.String prefix = com.patrickanker.isay.ISMain.getInstance().getItemAliasManager().getAliasForItem(event.getPlayer().getItemInHand().getTypeId());
        if (prefix != null) {
            event.getPlayer().chat((prefix + " ") + event.getMessage());
            event.setCancelled(true);
            return;
        }
    }
     _CVAR0 = com.patrickanker.isay.ISMain.getInstance();
    org.bukkit.event.player.AsyncPlayerChatEvent _CVAR2 = event;
    org.bukkit.entity.Player _CVAR3 = _CVAR2.getPlayer();
     _CVAR1 = _CVAR0.getChannelManager();
    java.lang.String _CVAR4 = _CVAR3.getName();
    com.patrickanker.isay.channels.Channel channel = _CVAR1.getFocus(_CVAR4);
    if (channel != null) {
        com.patrickanker.isay.channels.ChatChannel cc = ((com.patrickanker.isay.channels.ChatChannel) (channel));
        cc.dispatch(cp, event.getMessage());
        java.util.Set<org.bukkit.entity.Player> recipients = event.getRecipients();
        for (org.bukkit.entity.Player p : org.bukkit.Bukkit.getOnlinePlayers()) {
             _CVAR5 = !cc.hasFocus(p.getName());
             _CVAR6 = (!cc.isHelpOp()) && _CVAR5;
            if () {
                recipients.remove(p);
            } else {
                 _CVAR7 = !cc.hasListener(p.getName());
                 _CVAR8 = cc.isHelpOp() && _CVAR7;
                if () {
                    recipients.remove(p);
                }
            }
        }
        event.setCancelled(true);
    } else {
        event.getPlayer().sendMessage("§cYou do not have a channel focus.");
        event.setCancelled(true);
        java.util.Set<org.bukkit.entity.Player> recipients = event.getRecipients();
        recipients.clear();
    }
}