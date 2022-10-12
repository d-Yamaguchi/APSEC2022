/**
 * Processes a clan chat command
 *
 * @param msg
 * 		
 */
public void processClanChat(org.bukkit.entity.Player player, java.lang.String msg) {
    ClanPlayer cp = plugin.getClanManager().getClanPlayer(player.getName());
    if (cp == null) {
        return;
    }
    java.lang.String[] split = msg.split(" ");
    if (split.length == 0) {
        return;
    }
    java.lang.String command = split[0];
    if (command.equals(plugin.getLang("on"))) {
        cp.setClanChat(true);
        plugin.getStorageManager().updateClanPlayer(cp);
        ChatBlock.sendMessage(player, org.bukkit.ChatColor.AQUA + "You have enabled clan chat");
    } else if (command.equals(plugin.getLang("off"))) {
        cp.setClanChat(false);
        plugin.getStorageManager().updateClanPlayer(cp);
        ChatBlock.sendMessage(player, org.bukkit.ChatColor.AQUA + "You have disabled clan chat");
    } else if (command.equals(plugin.getLang("join"))) {
        cp.setChannel(ClanPlayer.Channel.CLAN);
        plugin.getStorageManager().updateClanPlayer(cp);
        ChatBlock.sendMessage(player, org.bukkit.ChatColor.AQUA + "You have joined clan chat");
    } else if (command.equals(plugin.getLang("leave"))) {
        cp.setChannel(ClanPlayer.Channel.NONE);
        plugin.getStorageManager().updateClanPlayer(cp);
        ChatBlock.sendMessage(player, org.bukkit.ChatColor.AQUA + "You have left clan chat");
    } else {
        java.lang.String code = ((org.bukkit.ChatColor.RED.toString() + org.bukkit.ChatColor.WHITE) + org.bukkit.ChatColor.RED) + org.bukkit.ChatColor.BLACK;
        java.lang.String tag;
        if ((cp.getRank() != null) && (!cp.getRank().isEmpty())) {
            tag = (((((plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketLeft()) + plugin.getSettingsManager().getClanChatRankColor()) + cp.getRank()) + plugin.getSettingsManager().getClanChatBracketColor()) + plugin.getSettingsManager().getClanChatTagBracketRight()) + " ";
        } else {
            tag = (((((plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketLeft()) + plugin.getSettingsManager().getTagDefaultColor()) + cp.getClan().getColorTag()) + plugin.getSettingsManager().getClanChatBracketColor()) + plugin.getSettingsManager().getClanChatTagBracketRight()) + " ";
        }
        org.bukkit.entity.Player _CVAR0 = player;
        java.lang.String _CVAR1 = _CVAR0.getDisplayName();
         _CVAR2 = (((code + Helper.parseColors(tag)) + plugin.getSettingsManager().getClanChatNameColor()) + plugin.getSettingsManager().getClanChatPlayerBracketLeft()) + _CVAR1;
         _CVAR3 = _CVAR2 + plugin.getSettingsManager().getClanChatPlayerBracketRight();
         _CVAR4 = _CVAR3 + " ";
         _CVAR5 = _CVAR4 + plugin.getSettingsManager().getClanChatMessageColor();
        java.lang.String message = _CVAR5 + msg;
        org.bukkit.entity.Player _CVAR6 = player;
        java.lang.String _CVAR7 = _CVAR6.getDisplayName();
         _CVAR8 = (((((((((code + plugin.getSettingsManager().getClanChatBracketColor()) + plugin.getSettingsManager().getClanChatTagBracketLeft()) + plugin.getSettingsManager().getTagDefaultColor()) + cp.getClan().getColorTag()) + plugin.getSettingsManager().getClanChatBracketColor()) + plugin.getSettingsManager().getClanChatTagBracketRight()) + " ") + plugin.getSettingsManager().getClanChatNameColor()) + plugin.getSettingsManager().getClanChatPlayerBracketLeft()) + _CVAR7;
         _CVAR9 = _CVAR8 + plugin.getSettingsManager().getClanChatPlayerBracketRight();
         _CVAR10 = _CVAR9 + " ";
         _CVAR11 = _CVAR10 + plugin.getSettingsManager().getClanChatMessageColor();
        java.lang.String eyeMessage = _CVAR11 + msg;
        plugin.getServer().getConsoleSender().sendMessage(eyeMessage);
        java.util.List<ClanPlayer> cps = cp.getClan().getMembers();
        for (ClanPlayer cpp : cps) {
            org.bukkit.entity.Player member = plugin.getServer().getPlayer(cpp.getName());
            ChatBlock.sendMessage(member, message);
        }
        sendToAllSeeing(eyeMessage, cps);
    }
}