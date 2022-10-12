public static java.lang.String getCancelWarmupOnSprintMessage() {
    org.bukkit.configuration.file.YamlConfiguration _CVAR0 = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf;
    java.lang.String _CVAR1 = "options.messages.warmup_cancelled_by_sprint";
    java.lang.String _CVAR2 = "&6Warm-ups have been cancelled due to sprinting.&f";
    java.lang.String _CVAR3 = _CVAR0.getString(_CVAR1, _CVAR2);
    return _CVAR3;
}