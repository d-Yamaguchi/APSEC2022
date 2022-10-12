@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmnd, java.lang.String label, java.lang.String[] args) {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        return true;
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    java.lang.String name = player.getName().toLowerCase();
    if (args.length == 0) {
        player.sendMessage(m._("usage_captcha"));
        return true;
    }
    if (uk.org.whoami.authme.cache.auth.PlayerCache.getInstance().isAuthenticated(name)) {
        player.sendMessage(m._("logged_in"));
        return true;
    }
    boolean _CVAR0 = !player.hasPermission("authme." + label.toLowerCase());
    if () {
        player.sendMessage(m._("no_perm"));
        return true;
    }
    if (!uk.org.whoami.authme.settings.Settings.useCaptcha) {
        player.sendMessage(m._("usage_log"));
        return true;
    }
    if (!plugin.cap.containsKey(name)) {
        player.sendMessage(m._("usage_log"));
        return true;
    }
    if (uk.org.whoami.authme.settings.Settings.useCaptcha && (!args[0].equals(plugin.cap.get(name)))) {
        plugin.cap.remove(name);
        plugin.cap.put(name, uk.org.whoami.authme.commands.CaptchaCommand.rdm.nextString());
        player.sendMessage(m._("wrong_captcha").replaceAll("THE_CAPTCHA", plugin.cap.get(name)));
        return true;
    }
    try {
        plugin.captcha.remove(name);
        plugin.cap.remove(name);
    } catch (java.lang.NullPointerException npe) {
    }
    player.sendMessage("Your captcha is correct");
    player.sendMessage(m._("login_msg"));
    return true;
}