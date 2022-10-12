@java.lang.Override
public void addWarp(de.xzise.xwarp.Warp... warps) {
    org.bukkit.configuration.file.YamlConfiguration _CVAR0 = this.config;
    java.lang.String _CVAR1 = de.xzise.xwarp.dataconnections.YmlConnection.WARP_PATH;
    java.util.List<java.lang.Object> rawNodes = _CVAR0.getList(_CVAR1);
    for (de.xzise.xwarp.Warp warp : warps) {
        java.util.Map<java.lang.String, java.lang.Object> warpMap = de.xzise.xwarp.dataconnections.YmlConnection.warpObjectToMap(warp);
        de.xzise.xwarp.dataconnections.YmlConnection.fixedLocToMap(warpMap, warp.getLocation(), false, true);
        warpMap.put("visibility", warp.getVisibility().name);
        warpMap.put("listed", warp.isListed());
        warpMap.put("price", warp.getPrice());
        warpMap.put("cooldown", warp.getCoolDown());
        warpMap.put("warmup", warp.getWarmUp());
        java.lang.String rawMessage = warp.getRawWelcomeMessage();
        if (rawMessage != null) {
            warpMap.put("welcome", rawMessage);
        }
        rawNodes.add(warpMap);
    }
    this.save();
}