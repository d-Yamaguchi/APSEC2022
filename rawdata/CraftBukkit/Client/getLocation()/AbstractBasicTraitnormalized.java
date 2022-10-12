@java.lang.Override
public boolean checkRestrictions(de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper wrapper) {
    if (player == null) {
        return true;
    }
    java.lang.String playerName = player.getName();
    int playerLevel = de.tobiyas.racesandclasses.traitcontainer.interfaces.AbstractBasicTrait.plugin.getPlayerManager().getPlayerLevelManager(player.getUniqueId()).getCurrentLevel();
    if ((playerLevel < minimumLevel) || (playerLevel > maximumLevel)) {
        return false;
    }
    org.bukkit.entity.Player _CVAR1 = player;
    org.bukkit.Location _CVAR2 = _CVAR1.getLocation();
    org.bukkit.block.Block _CVAR3 = _CVAR2.getBlock();
    org.bukkit.block.Biome currentBiome = _CVAR3.getBiome();
    if (!biomes.contains(currentBiome)) {
        return false;
    }
    // Check if player is in water
    if (onlyInWater) {
        org.bukkit.entity.Player _CVAR5 = player;
        org.bukkit.Location _CVAR6 = _CVAR5.getLocation();
        org.bukkit.block.Block _CVAR7 = _CVAR6.getBlock();
        org.bukkit.block.BlockFace _CVAR8 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.Block feetblock = _CVAR7.getRelative(_CVAR8);
        if ((feetblock.getType() != org.bukkit.Material.WATER) && (feetblock.getType() != org.bukkit.Material.STATIONARY_WATER)) {
            return false;
        }
    }
    // Sneaking
    if (onlyWhileSneaking) {
        if (!player.isSneaking()) {
            return false;
        }
    }
    // Not sneaking
    if (onlyWhileNotSneaking) {
        if (player.isSneaking()) {
            return false;
        }
    }
    // check if player is on land
    if (onlyOnLand) {
        org.bukkit.entity.Player _CVAR10 = player;
        org.bukkit.Location _CVAR11 = _CVAR10.getLocation();
        org.bukkit.block.Block _CVAR12 = _CVAR11.getBlock();
        org.bukkit.block.BlockFace _CVAR13 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.Block feetblock = _CVAR12.getRelative(_CVAR13);
        if ((feetblock.getType() == org.bukkit.Material.WATER) || (feetblock.getType() == org.bukkit.Material.STATIONARY_WATER)) {
            return false;
        }
    }
    // check if player is in lava
    if (onlyInLava) {
        org.bukkit.entity.Player _CVAR15 = player;
        org.bukkit.Location _CVAR16 = _CVAR15.getLocation();
        org.bukkit.block.Block _CVAR17 = _CVAR16.getBlock();
        org.bukkit.block.BlockFace _CVAR18 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.Block feetblock = _CVAR17.getRelative(_CVAR18);
        if ((feetblock.getType() != org.bukkit.Material.LAVA) && (feetblock.getType() != org.bukkit.Material.STATIONARY_LAVA)) {
            return false;
        }
    }
    // check if player is on Snow
    if (onlyOnSnow) {
        org.bukkit.entity.Player _CVAR20 = player;
        org.bukkit.Location _CVAR21 = _CVAR20.getLocation();
        org.bukkit.block.Block feetblock = _CVAR21.getBlock();
        if (!((feetblock.getType() == org.bukkit.Material.SNOW) || (feetblock.getType() == org.bukkit.Material.SNOW_BLOCK))) {
            return false;
        }
    }
    // check if player is in Rain
    if (onlyInRain) {
        if (!player.getWorld().hasStorm()) {
            return false;
        }
        org.bukkit.entity.Player _CVAR23 = player;
        org.bukkit.Location _CVAR24 = _CVAR23.getLocation();
        org.bukkit.block.Block feetblock = _CVAR24.getBlock();
        int ownY = feetblock.getY();
        int highestY = feetblock.getWorld().getHighestBlockYAt(feetblock.getX(), feetblock.getZ());
        // This means having a roof over oneself
        if (ownY != highestY) {
            return false;
        }
    }
    // checking for wearing
    if (!wearing.isEmpty()) {
        boolean found = false;
        for (org.bukkit.Material mat : wearing) {
            found = false;
            for (org.bukkit.inventory.ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null) {
                    continue;
                }
                if (mat == item.getType()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
    }
    // check above elevation
    if (aboveElevation != java.lang.Integer.MIN_VALUE) {
        org.bukkit.entity.Player _CVAR26 = player;
        org.bukkit.Location _CVAR27 = _CVAR26.getLocation();
        org.bukkit.block.Block feetblock = _CVAR27.getBlock();
        if (feetblock.getY() <= aboveElevation) {
            return false;
        }
    }
    // check below elevation
    if (belowElevation != java.lang.Integer.MAX_VALUE) {
        org.bukkit.entity.Player _CVAR29 = player;
        org.bukkit.Location _CVAR30 = _CVAR29.getLocation();
        org.bukkit.block.Block feetblock = _CVAR30.getBlock();
        if (feetblock.getY() >= belowElevation) {
            return false;
        }
    }
    // check onlyAfterDamaged
    if (onlyAfterDamaged > 0) {
        int lastDamage = de.tobiyas.racesandclasses.listeners.generallisteners.PlayerLastDamageListener.getTimePassedSinceLastDamageInSeconds(playerName);
        if (lastDamage > onlyAfterDamaged) {
            return false;
        }
    }
    // check onlyAfterDamaged
    if (onlyAfterNotDamaged > 0) {
        int lastDamage = de.tobiyas.racesandclasses.listeners.generallisteners.PlayerLastDamageListener.getTimePassedSinceLastDamageInSeconds(playerName);
        if (onlyAfterNotDamaged > lastDamage) {
            return false;
        }
    }
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR0 = wrapper;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR4 = _CVAR0;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR9 = _CVAR4;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR14 = _CVAR9;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR19 = _CVAR14;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR22 = _CVAR19;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR25 = _CVAR22;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR28 = _CVAR25;
    de.tobiyas.racesandclasses.eventprocessing.eventresolvage.EventWrapper _CVAR31 = _CVAR28;
    org.bukkit.entity.Player player = _CVAR31.getPlayer();
    // check blocks on
    if (!onlyOnBlocks.isEmpty()) {
        org.bukkit.entity.Player _CVAR32 = player;
        org.bukkit.Location _CVAR33 = _CVAR32.getLocation();
        org.bukkit.block.Block feetblock = _CVAR33.getBlock();
        org.bukkit.block.Block belowFeetBlock = feetblock.getRelative(org.bukkit.block.BlockFace.DOWN);
        if (!onlyOnBlocks.contains(belowFeetBlock.getType())) {
            return false;
        }
    }
    // check cooldown
    java.lang.String cooldownName = "trait." + getDisplayName();
    int playerUplinkTime = de.tobiyas.racesandclasses.traitcontainer.interfaces.AbstractBasicTrait.plugin.getCooldownManager().stillHasCooldown(playerName, cooldownName);
    if (playerUplinkTime > 0) {
        if (!triggerButHasUplink(wrapper)) {
            if (notifyTriggeredUplinkTime(wrapper)) {
                // if notices are disabled, we don't need to do anything here.
                if (disableCooldownNotice) {
                    return false;
                }
                // we still check to not spam players.
                long lastNotified = (uplinkNotifyList.containsKey(playerName)) ? uplinkNotifyList.get(playerName) : 0;
                long maxTime = minUplinkShowTime * 1000;
                if (new java.util.Date().after(new java.util.Date(lastNotified + maxTime))) {
                    de.tobiyas.racesandclasses.APIs.LanguageAPI.sendTranslatedMessage(player, trait_cooldown, "seconds", java.lang.String.valueOf(playerUplinkTime), "name", getDisplayName());
                    uplinkNotifyList.put(playerName, new java.util.Date().getTime());
                }
            }
        }
        return false;
    }
    // Only check if we really need. Otherwise we would use resources we don't need
    if (onlyOnDay || onlyInNight) {
        // Daytime check
        int hour = (((int) (player.getWorld().getTime() / 1000L)) + 8) % 24;
        boolean isNight = (hour > 18) || (hour < 6);
        boolean isDay = (hour > 6) && (hour < 18);
        // Check day
        if ((onlyOnDay && isNight) && (!onlyInNight)) {
            return false;
        }
        // Check night
        if ((onlyInNight && isDay) && (!onlyOnDay)) {
            return false;
        }
    }
    return true;
}