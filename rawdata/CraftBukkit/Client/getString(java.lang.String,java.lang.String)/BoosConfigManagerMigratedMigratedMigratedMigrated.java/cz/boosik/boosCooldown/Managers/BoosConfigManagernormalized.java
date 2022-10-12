public static java.lang.String getCannotUseSignMessage() {
    org.bukkit.configuration.file.YamlConfiguration _CVAR0 = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf;
    java.lang.String _CVAR1 = "options.messages.cannot_use_sign";
    java.lang.String _CVAR2 = "&6You are not allowed to use this sign!&f";
    java.lang.String _CVAR3 = _CVAR0.getString(_CVAR1, _CVAR2);
    return _CVAR3;
}