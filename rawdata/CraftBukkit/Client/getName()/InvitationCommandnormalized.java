/**
 * Accept Command - Accepts the channel join invitation and joins the channel
 */
@com.titankingdoms.nodinchan.titanchat.command.commands.ChCommand
@com.titankingdoms.nodinchan.titanchat.command.commands.Description("Accepts the channel join invitation and joins the channel")
@com.titankingdoms.nodinchan.titanchat.command.commands.Usage("accept [channel]")
public void accept(org.bukkit.entity.Player player, java.lang.String[] args) {
    try {
        if (cm.exists(args[0])) {
            com.titankingdoms.nodinchan.titanchat.channel.Channel _CVAR2 = channel;
            org.bukkit.entity.Player _CVAR4 = player;
             _CVAR3 = _CVAR2.getInviteList();
            java.lang.String _CVAR5 = _CVAR4.getName();
            boolean _CVAR6 = _CVAR3.contains(_CVAR5);
            com.titankingdoms.nodinchan.titanchat.channel.ChannelManager _CVAR0 = cm;
            java.lang.String _CVAR1 = args[0];
            com.titankingdoms.nodinchan.titanchat.channel.ChannelManager _CVAR7 = _CVAR0;
            java.lang.String _CVAR8 = _CVAR1;
            com.titankingdoms.nodinchan.titanchat.channel.Channel channel = _CVAR7.getChannel(_CVAR8);
            if () {
                com.titankingdoms.nodinchan.titanchat.channel.Channel _CVAR9 = channel;
                org.bukkit.entity.Player _CVAR11 = player;
                 _CVAR10 = _CVAR9.getInviteList();
                java.lang.String _CVAR12 = _CVAR11.getName();
                _CVAR10.remove(_CVAR12);
                cm.onInviteRespond(channel, player, true);
                cm.chSwitch(player, channel);
                plugin.sendInfo(player, "You have accepted the invitation");
            } else {
                plugin.sendWarning(player, "You did not receive any invitations from this channel");
            }
        } else if (args[0].toLowerCase().startsWith("tag:")) {
            if (cm.existsAsTag(args[0].substring(4))) {
                com.titankingdoms.nodinchan.titanchat.channel.Channel _CVAR17 = channel;
                org.bukkit.entity.Player _CVAR19 = player;
                 _CVAR18 = _CVAR17.getInviteList();
                java.lang.String _CVAR20 = _CVAR19.getName();
                boolean _CVAR21 = _CVAR18.contains(_CVAR20);
                com.titankingdoms.nodinchan.titanchat.channel.ChannelManager _CVAR13 = cm;
                java.lang.String _CVAR14 = args[0];
                int _CVAR15 = 4;
                java.lang.String _CVAR23 = _CVAR14;
                int _CVAR24 = _CVAR15;
                java.lang.String _CVAR16 = _CVAR23.substring(_CVAR24);
                com.titankingdoms.nodinchan.titanchat.channel.ChannelManager _CVAR22 = _CVAR13;
                java.lang.String _CVAR25 = _CVAR16;
                com.titankingdoms.nodinchan.titanchat.channel.Channel channel = _CVAR22.getChannelByTag(_CVAR25);
                if () {
                    com.titankingdoms.nodinchan.titanchat.channel.Channel _CVAR26 = channel;
                    org.bukkit.entity.Player _CVAR28 = player;
                     _CVAR27 = _CVAR26.getInviteList();
                    java.lang.String _CVAR29 = _CVAR28.getName();
                    _CVAR27.remove(_CVAR29);
                    cm.onInviteRespond(channel, player, true);
                    cm.chSwitch(player, channel);
                    plugin.sendInfo(player, "You have accepted the invitation");
                } else {
                    plugin.sendWarning(player, "You did not receive any invitations from this channel");
                }
            } else {
                plugin.sendWarning(player, "No such channel");
            }
        } else {
            plugin.sendWarning(player, "No such channel");
        }
    } catch (java.lang.IndexOutOfBoundsException e) {
        invalidArgLength(player, "Accept");
    }
}