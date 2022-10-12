public static void broadcastMsg(org.bukkit.entity.Player player, com.dre.managerxl.broadcaster.BroadcasterMsg msg) {
    org.bukkit.entity.Player _CVAR0 = player;
    java.lang.String _CVAR1 = _CVAR0.getName();
    com.dre.managerxl.MPlayer mPlayer = com.dre.managerxl.MPlayer.getOrCreate(_CVAR1);
    com.dre.managerxl.broadcaster.BroadcasterPlayerMsg bMsg = mPlayer.getBMsg(msg.getId());
    bMsg.setSendCount(bMsg.getSendCount() + 1);
    bMsg.setLastSend(java.lang.System.currentTimeMillis());
    int newPlayerLevel = (bMsg.getSendCount() / com.dre.managerxl.broadcaster.Broadcaster.sendsPerLevel) + 1;
    if (newPlayerLevel > com.dre.managerxl.broadcaster.Broadcaster.maxLevel) {
        newPlayerLevel = com.dre.managerxl.broadcaster.Broadcaster.maxLevel;
    }
    bMsg.setPlayerLevel(newPlayerLevel);
    if (msg.getType().equalsIgnoreCase("Broadcast")) {
        player.sendMessage(msg.getMsgAsBroadcast());
    } else if (msg.getType().equalsIgnoreCase("News")) {
        player.sendMessage(msg.getMsgAsNews());
    } else if (msg.getType().equalsIgnoreCase("Date")) {
        player.sendMessage(msg.getMsgAsDate());
    }
}