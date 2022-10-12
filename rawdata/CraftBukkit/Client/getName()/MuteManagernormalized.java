public void mutePlayer(org.bukkit.entity.Player player, java.lang.Long muteTime, org.bukkit.command.CommandSender sender, java.lang.String reason) {
    long curTime = java.lang.System.currentTimeMillis();
    long expTime = curTime + ((muteTime * 60) * 1000);
    org.bukkit.entity.Player _CVAR0 = player;
    java.lang.String pName = _CVAR0.getName();
    mList.put(pName, expTime);
    mReason.put(pName, reason);
    java.lang.String senderMessage = com.cnaude.mutemanager.MuteManager.config.msgPlayerNowMuted().replace("%AUTHOR%", sender.getName()).replace("%PLAYER%", pName).replace("%DURATION%", expireTime(pName));
    if (!reason.isEmpty()) {
        senderMessage = (((((senderMessage + org.bukkit.ChatColor.YELLOW) + ". ") + com.cnaude.mutemanager.MuteManager.config.msgReason()) + ": ") + org.bukkit.ChatColor.RED) + reason;
    }
    if (com.cnaude.mutemanager.MuteManager.config.shouldNotify()) {
        getServer().broadcast(senderMessage, muteBroadcastPermNode);
    } else {
        sender.sendMessage(senderMessage);
        if (!com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().isEmpty()) {
            player.sendMessage(com.cnaude.mutemanager.MuteManager.config.msgYouHaveBeenMuted().replace("%DURATION%", expireTime(pName)).replace("%REASON%", reason));
        }
    }
}