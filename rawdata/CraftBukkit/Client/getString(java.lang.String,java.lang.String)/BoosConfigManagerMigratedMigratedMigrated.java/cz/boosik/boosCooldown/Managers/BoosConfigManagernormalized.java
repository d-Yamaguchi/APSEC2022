public static java.lang.String getCannotCreateSignMessage() {
    org.bukkit.configuration.file.YamlConfiguration _CVAR0 = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf;
    java.lang.String _CVAR1 = "options.messages.cannot_create_sign";
    java.lang.String _CVAR2 = "&6You are not allowed to create this kind of signs!&f";
    java.lang.String _CVAR3 = _CVAR0.getString(_CVAR1, _CVAR2);
    return _CVAR3;
}