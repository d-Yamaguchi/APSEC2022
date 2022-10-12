package tc.oc.adminchat;
import tc.oc.adminchat.events.AdminChatEvent;
public final class AdminChat extends org.bukkit.plugin.java.JavaPlugin {
    static java.lang.String PERM_NODE = "chat.admin.";

    static java.lang.String PERM_SEND = tc.oc.adminchat.AdminChat.PERM_NODE + "send";

    static java.lang.String PERM_RECEIVE = tc.oc.adminchat.AdminChat.PERM_NODE + "receive";

    static java.lang.String PREFIX = ((("[" + org.bukkit.ChatColor.GOLD) + "A") + org.bukkit.ChatColor.WHITE) + "] ";

    @java.lang.Override
    public void onEnable() {
        ChannelsPlugin.get().getPluginManager().registerEvents(__SmPLUnsupported__(0), this);
    }

    @java.lang.Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.lang.String[] args) {
        if (!sender.hasPermission(tc.oc.adminchat.AdminChat.PERM_SEND)) {
            sender.sendMessage(org.bukkit.ChatColor.RED + "No permission");
        } else if (args.length == 0) {
            sender.sendMessage(org.bukkit.ChatColor.RED + "Usage: /a <msg>");
        } else {
            tc.oc.adminchat.events.AdminChatEvent event = new tc.oc.adminchat.events.AdminChatEvent(sender.getName(), org.apache.commons.lang.StringUtils.join(args, " "), tc.oc.adminchat.AdminChat.PREFIX);
            org.bukkit.Bukkit.getPluginManager().callEvent(event);
        }
        return true;
    }
}