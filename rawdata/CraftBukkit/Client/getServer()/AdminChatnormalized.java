@java.lang.Override
public void onEnable() {
    org.bukkit.Server _CVAR0 = this.getServer();
    org.bukkit.plugin.PluginManager _CVAR1 = _CVAR0.getPluginManager();
    org.bukkit.event.Listener _CVAR2 = new org.bukkit.event.Listener() {
        @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.MONITOR)
        public void broadcastAdminChat(final tc.oc.adminchat.events.AdminChatEvent event) {
            org.bukkit.Bukkit.getServer().broadcast((((event.getPrefix() + event.getPlayer()) + org.bukkit.ChatColor.RESET) + ": ") + event.getMessage(), tc.oc.adminchat.AdminChat.PERM_RECEIVE);
        }
    };
    tc.oc.adminchat.AdminChat _CVAR3 = this;
    _CVAR1.registerEvents(_CVAR2, _CVAR3);
}