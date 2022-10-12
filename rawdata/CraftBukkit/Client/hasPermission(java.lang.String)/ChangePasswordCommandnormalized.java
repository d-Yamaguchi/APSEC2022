@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return true;
    }
    boolean _CVAR0 = !sender.hasPermission("authme." + label.toLowerCase());
    if () {
        sender.sendMessage(m._("no_perm"));
        return true;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    java.lang.String name = player.getName().toLowerCase();
    if (!uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        player.sendMessage(m._("not_logged_in"));
        return true;
    }
    if (args.length != 2) {
        player.sendMessage(m._("usage_changepassword"));
        return true;
    }
    try {
        java.lang.String hashnew = uk.org.whoami.authme.security.PasswordSecurity.getHash(Settings.getPasswordHash, args[1], name);
        if (uk.org.whoami.authme.security.PasswordSecurity.comparePasswordWithHash(args[0], uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().getAuth(name).getHash(), name)) {
            uk.org.whoami.authme.cache.auth.PlayerAuth auth = uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().getAuth(name);
            auth.setHash(hashnew);
            if (!database.updatePassword(auth)) {
                player.sendMessage(m._("error"));
                return true;
            }
            uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().updatePlayer(auth);
            player.sendMessage(m._("pwd_changed"));
            uk.org.whoami.authme.ConsoleLogger.info(player.getName() + " changed his password");
            if (plugin.notifications != null) {
                plugin.notifications.showNotification(new me.muizers.Notifications.Notification(("[AuthMe] " + player.getName()) + " change his password!"));
            }
        } else {
            player.sendMessage(m._("wrong_pwd"));
        }
    } catch (java.security.NoSuchAlgorithmException ex) {
        uk.org.whoami.authme.ConsoleLogger.showError(ex.getMessage());
        sender.sendMessage(m._("error"));
    }
    return true;
}