static java.lang.String getCommandBlockedMessage() {
    org.bukkit.configuration.file.YamlConfiguration _CVAR0 = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf;
    java.lang.String _CVAR1 = "options.messages.limit_achieved";
    java.lang.String _CVAR2 = "&6You cannot use this command anymore!&f";
    java.lang.String _CVAR3 = _CVAR0.getString(_CVAR1, _CVAR2);
    return _CVAR3;
}