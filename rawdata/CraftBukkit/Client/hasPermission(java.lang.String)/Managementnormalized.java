private void displayOtherAccounts(uk.org.whoami.authme.cache.auth.PlayerAuth auth) {
    if (!uk.org.whoami.authme.settings.Settings.displayOtherAccounts) {
        return;
    }
    if (auth == null) {
        return;
    }
    if (this.database.getAllAuthsByName(auth).isEmpty() || (this.database.getAllAuthsByName(auth) == null)) {
        return;
    }
    if (this.database.getAllAuthsByName(auth).size() == 1) {
        return;
    }
    java.util.List<java.lang.String> accountList = this.database.getAllAuthsByName(auth);
    java.lang.String message = "[AuthMe] ";
    int i = 0;
    for (java.lang.String account : accountList) {
        i++;
        message = message + account;
        if (i != accountList.size()) {
            message = message + ", ";
        } else {
            message = message + ".";
        }
    }
    for (org.bukkit.entity.Player player : uk.org.whoami.authme.AuthMe.getInstance().getServer().getOnlinePlayers()) {
        org.bukkit.entity.Player _CVAR0 = player;
        java.lang.String _CVAR1 = "authme.seeOtherAccounts";
        boolean _CVAR2 = _CVAR0.hasPermission(_CVAR1);
        if () {
            player.sendMessage(((("[AuthMe] The player " + auth.getNickname()) + " has ") + java.lang.String.valueOf(accountList.size())) + " accounts");
            player.sendMessage(message);
        }
    }
}