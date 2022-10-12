package com.bergerkiller.bukkit.mw;
import com.bergerkiller.bukkit.common.Common;
import com.bergerkiller.bukkit.common.bases.IntVector3;
import com.bergerkiller.bukkit.common.controller.PlayerDataController;
import com.bergerkiller.bukkit.common.conversion.Conversion;
import com.bergerkiller.bukkit.common.entity.CommonEntity;
import com.bergerkiller.bukkit.common.entity.type.CommonLivingEntity;
import com.bergerkiller.bukkit.common.entity.type.CommonPlayer;
import com.bergerkiller.bukkit.common.nbt.CommonTagCompound;
import com.bergerkiller.bukkit.common.nbt.CommonTagList;
import com.bergerkiller.bukkit.common.protocol.PacketType;
import com.bergerkiller.bukkit.common.reflection.classes.EntityHumanRef;
import com.bergerkiller.bukkit.common.reflection.classes.MobEffectRef;
import com.bergerkiller.bukkit.common.utils.NBTUtil;
import com.bergerkiller.bukkit.common.utils.PacketUtil;
import com.bergerkiller.bukkit.common.utils.PlayerUtil;
import com.bergerkiller.bukkit.common.utils.WorldUtil;
public class MWPlayerDataController extends com.bergerkiller.bukkit.common.controller.PlayerDataController {
    public static final java.lang.String DATA_TAG_LASTPOS = "MyWorlds.playerPos";

    public static final java.lang.String DATA_TAG_LASTROT = "MyWorlds.playerRot";

    /**
     * Gets the Main world save file for the playerName specified
     *
     * @param playerName
     * 		
     * @return Save file
     */
    public static java.io.File getMainFile(java.lang.String playerName) {
        java.io.File playerData = com.bergerkiller.bukkit.mw.WorldConfig.getMain().getPlayerData(playerName);
        playerData.getParentFile().mkdirs();
        return playerData;
    }

    /**
     * Gets the World Configuration of the world player data is saved in.
     *
     * @param player
     * 		to get the save file world for
     * @return save file world
     */
    public static com.bergerkiller.bukkit.mw.WorldConfig getSaveWorld(org.bukkit.entity.HumanEntity player) {
        if (MyWorlds.useWorldInventories) {
            return com.bergerkiller.bukkit.mw.WorldConfig.get(com.bergerkiller.bukkit.mw.WorldConfig.get(player).inventory.getSharedWorldName());
        } else {
            return com.bergerkiller.bukkit.mw.WorldConfig.getMain();
        }
    }

    /**
     * Gets the save file for the player in the current world.
     * World inventories settings are applied here.
     *
     * @param player
     * 		to get the save file for
     * @return save file
     */
    public static java.io.File getSaveFile(org.bukkit.entity.HumanEntity player) {
        java.io.File playerData = com.bergerkiller.bukkit.mw.MWPlayerDataController.getSaveWorld(player).getPlayerData(store.loadAchievements(mPlayer));
        playerData.getParentFile().mkdirs();
        return playerData;
    }

    private static void setLocation(com.bergerkiller.bukkit.common.nbt.CommonTagCompound tagCompound, org.bukkit.Location location) {
        tagCompound.putListValues("Pos", location.getX(), location.getY(), location.getZ());
        tagCompound.putListValues("Rotation", location.getYaw(), location.getPitch());
        final org.bukkit.World world = location.getWorld();
        tagCompound.putValue("World", world.getName());
        tagCompound.putUUID("World", world.getUID());
        tagCompound.putValue("Dimension", com.bergerkiller.bukkit.common.utils.WorldUtil.getDimension(world));
    }

    /**
     * Creates new human data information as if the human just joined the server.
     *
     * @param human
     * 		to generate information about
     * @return empty data
     */
    public static com.bergerkiller.bukkit.common.nbt.CommonTagCompound createEmptyData(org.bukkit.entity.HumanEntity human) {
        final org.bukkit.util.Vector velocity = human.getVelocity();
        com.bergerkiller.bukkit.common.nbt.CommonTagCompound empty = new com.bergerkiller.bukkit.common.nbt.CommonTagCompound();
        com.bergerkiller.bukkit.common.entity.type.CommonLivingEntity<?> livingEntity = com.bergerkiller.bukkit.common.entity.CommonEntity.get(human);
        empty.putUUID("", human.getUniqueId());
        empty.putValue("Health", ((short) (livingEntity.getMaxHealth())));
        empty.putValue("HealF", ((float) (livingEntity.getMaxHealth())));// since 1.6.1 health is a float

        empty.putValue("HurtTime", ((short) (0)));
        empty.putValue("DeathTime", ((short) (0)));
        empty.putValue("AttackTime", ((short) (0)));
        empty.putListValues("Motion", velocity.getX(), velocity.getY(), velocity.getZ());
        com.bergerkiller.bukkit.mw.MWPlayerDataController.setLocation(empty, com.bergerkiller.bukkit.mw.WorldManager.getSpawnLocation(com.bergerkiller.bukkit.mw.MyWorlds.getMainWorld()));
        final java.lang.Object humanHandle = livingEntity.getHandle();
        com.bergerkiller.bukkit.common.bases.IntVector3 coord = EntityHumanRef.spawnCoord.get(humanHandle);
        if (coord != null) {
            empty.putValue("SpawnWorld", EntityHumanRef.spawnWorld.get(humanHandle));
            empty.putValue("SpawnX", coord.x);
            empty.putValue("SpawnY", coord.y);
            empty.putValue("SpawnZ", coord.z);
        }
        return empty;
    }

    /**
     * Attempts to read the last known Player position on a specific World
     *
     * @param player
     * 		to get the last position for
     * @param world
     * 		to get the last position for
     * @return Last known Location, or null if not found/stored
     */
    public static org.bukkit.Location readLastLocation(org.bukkit.entity.Player player, org.bukkit.World world) {
        java.io.File posFile = com.bergerkiller.bukkit.mw.WorldConfig.get(world).getPlayerData(player.getName());
        if (!posFile.exists()) {
            return null;
        }
        com.bergerkiller.bukkit.common.nbt.CommonTagCompound data = com.bergerkiller.bukkit.mw.MWPlayerDataController.read(posFile, player);
        com.bergerkiller.bukkit.common.nbt.CommonTagList posInfo = data.getValue(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTPOS, com.bergerkiller.bukkit.common.nbt.CommonTagList.class);
        if ((posInfo != null) && (posInfo.size() == 3)) {
            // Apply position
            org.bukkit.Location location = new org.bukkit.Location(world, posInfo.getValue(0, 0.0), posInfo.getValue(1, 0.0), posInfo.getValue(2, 0.0));
            com.bergerkiller.bukkit.common.nbt.CommonTagList rotInfo = data.getValue(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTROT, com.bergerkiller.bukkit.common.nbt.CommonTagList.class);
            if ((rotInfo != null) && (rotInfo.size() == 2)) {
                location.setYaw(rotInfo.getValue(0, 0.0F));
                location.setPitch(rotInfo.getValue(1, 0.0F));
            }
            return location;
        }
        return null;
    }

    /**
     * Tries to read the saved data from a source file
     *
     * @param sourceFile
     * 		to read from
     * @return the data in the file, or the empty data constant if the file does not exist
     * @throws Exception
     * 		
     */
    public static com.bergerkiller.bukkit.common.nbt.CommonTagCompound read(java.io.File sourceFile, org.bukkit.entity.HumanEntity human) {
        try {
            if (sourceFile.exists()) {
                return com.bergerkiller.bukkit.common.nbt.CommonTagCompound.readFrom(sourceFile);
            }
        } catch (java.util.zip.ZipException ex) {
            org.bukkit.Bukkit.getLogger().warning(("Failed to read player data for " + human.getName()) + " (ZIP-exception: file corrupted)");
        } catch (java.lang.Throwable t) {
            // Return an empty data constant for now
            org.bukkit.Bukkit.getLogger().warning("Failed to read player data for " + human.getName());
            t.printStackTrace();
        }
        return com.bergerkiller.bukkit.mw.MWPlayerDataController.createEmptyData(human);
    }

    private static void clearEffects(org.bukkit.entity.HumanEntity human) {
        // Clear mob effects
        java.util.HashMap<java.lang.Integer, java.lang.Object> effects = EntityHumanRef.mobEffects.get(Conversion.toEntityHandle.convert(human));
        if (human instanceof org.bukkit.entity.Player) {
            // Send mob effect removal messages
            org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (human));
            for (java.lang.Object effect : effects.values()) {
                com.bergerkiller.bukkit.common.utils.PacketUtil.sendPacket(player, PacketType.OUT_ENTITY_EFFECT_REMOVE.newInstance(player.getEntityId(), effect));
            }
        }
        effects.clear();
        // Clear attributes
        com.bergerkiller.bukkit.common.utils.NBTUtil.resetAttributes(human);
    }

    /**
     * Handles post loading of an Entity
     *
     * @param human
     * 		that got loaded
     */
    private static void postLoad(org.bukkit.entity.HumanEntity human) {
        if (com.bergerkiller.bukkit.mw.WorldConfig.get(human.getWorld()).clearInventory) {
            human.getInventory().clear();
            com.bergerkiller.bukkit.mw.MWPlayerDataController.clearEffects(human);
        }
    }

    /**
     * Applies the player states in the world to the player specified
     *
     * @param player
     * 		to set the states for
     */
    public static void refreshState(org.bukkit.entity.Player player) {
        if (!MyWorlds.useWorldInventories) {
            // If not enabled, only do the post-load logic
            com.bergerkiller.bukkit.mw.MWPlayerDataController.postLoad(player);
            return;
        }
        try {
            com.bergerkiller.bukkit.common.entity.type.CommonPlayer commonPlayer = com.bergerkiller.bukkit.common.entity.CommonEntity.get(player);
            java.lang.Object playerHandle = Conversion.toEntityHandle.convert(player);
            java.io.File source = com.bergerkiller.bukkit.mw.MWPlayerDataController.getSaveFile(player);
            com.bergerkiller.bukkit.common.nbt.CommonTagCompound data = com.bergerkiller.bukkit.mw.MWPlayerDataController.read(source, player);
            // First, clear previous player information when loading involves adding new elements
            com.bergerkiller.bukkit.mw.MWPlayerDataController.clearEffects(player);
            // Refresh attributes
            if (data.containsKey("Attributes")) {
                com.bergerkiller.bukkit.common.utils.NBTUtil.loadAttributes(player, data.get("Attributes", com.bergerkiller.bukkit.common.nbt.CommonTagList.class));
            }
            // Load the data
            com.bergerkiller.bukkit.common.utils.NBTUtil.loadInventory(player.getInventory(), data.createList("Inventory"));
            EntityHumanRef.exp.set(playerHandle, data.getValue("XpP", 0.0F));
            EntityHumanRef.expLevel.set(playerHandle, data.getValue("XpLevel", 0));
            EntityHumanRef.expTotal.set(playerHandle, data.getValue("XpTotal", 0));
            if (Common.MC_VERSION.equals("1.5.2")) {
                commonPlayer.setHealth(data.getValue("Health", ((int) (commonPlayer.getMaxHealth()))));
            } else {
                commonPlayer.setHealth(data.getValue("HealF", ((float) (commonPlayer.getMaxHealth()))));
            }
            // Respawn position
            java.lang.String spawnWorld = data.getValue("SpawnWorld", "");
            com.bergerkiller.bukkit.common.bases.IntVector3 spawn = null;
            if (!spawnWorld.isEmpty()) {
                java.lang.Integer x = data.getValue("SpawnX", java.lang.Integer.class);
                java.lang.Integer y = data.getValue("SpawnY", java.lang.Integer.class);
                java.lang.Integer z = data.getValue("SpawnZ", java.lang.Integer.class);
                if (((x != null) && (y != null)) && (z != null)) {
                    spawn = new com.bergerkiller.bukkit.common.bases.IntVector3(x, y, z);
                } else {
                    spawnWorld = "";// reset, invalid coordinates

                }
            }
            EntityHumanRef.spawnCoord.set(playerHandle, spawn);
            EntityHumanRef.spawnWorld.set(playerHandle, spawnWorld);
            EntityHumanRef.spawnForced.set(playerHandle, data.getValue("SpawnForced", false));
            // Other data
            com.bergerkiller.bukkit.common.utils.NBTUtil.loadFoodMetaData(EntityHumanRef.foodData.get(playerHandle), data);
            com.bergerkiller.bukkit.common.utils.NBTUtil.loadInventory(player.getEnderChest(), data.createList("EnderItems"));
            // Load Mob Effects
            java.util.HashMap<java.lang.Integer, java.lang.Object> effects = EntityHumanRef.mobEffects.get(playerHandle);
            if (data.containsKey("ActiveEffects")) {
                com.bergerkiller.bukkit.common.nbt.CommonTagList taglist = data.createList("ActiveEffects");
                for (int i = 0; i < taglist.size(); ++i) {
                    java.lang.Object mobEffect = com.bergerkiller.bukkit.common.utils.NBTUtil.loadMobEffect(((com.bergerkiller.bukkit.common.nbt.CommonTagCompound) (taglist.get(i))));
                    effects.put(MobEffectRef.effectId.get(mobEffect), mobEffect);
                }
            }
            EntityHumanRef.updateEffects.set(playerHandle, true);
            // Send add messages for all (new) effects
            for (java.lang.Object effect : effects.values()) {
                com.bergerkiller.bukkit.common.utils.PacketUtil.sendPacket(player, PacketType.OUT_ENTITY_EFFECT_ADD.newInstance(player.getEntityId(), effect));
            }
            // Perform post loading
            com.bergerkiller.bukkit.mw.MWPlayerDataController.postLoad(player);
        } catch (java.lang.Exception exception) {
            org.bukkit.Bukkit.getLogger().warning("Failed to load player data for " + player.getName());
            exception.printStackTrace();
        }
    }

    @java.lang.Override
    public com.bergerkiller.bukkit.common.nbt.CommonTagCompound onLoad(org.bukkit.entity.HumanEntity human) {
        try {
            java.io.File main;
            com.bergerkiller.bukkit.common.nbt.CommonTagCompound tagcompound;
            boolean hasPlayedBefore = false;
            // Get the source file to use for loading
            main = com.bergerkiller.bukkit.mw.MWPlayerDataController.getMainFile(human.getName());
            hasPlayedBefore = main.exists();
            // Find out where to find the save file
            // No need to check for this if not using world inventories - it is always the main file then
            if ((MyWorlds.useWorldInventories && hasPlayedBefore) && (!MyWorlds.forceMainWorldSpawn)) {
                try {
                    // Allow switching worlds and positions
                    tagcompound = com.bergerkiller.bukkit.common.nbt.CommonTagCompound.readFrom(main);
                    org.bukkit.World world = org.bukkit.Bukkit.getWorld(tagcompound.getUUID("World"));
                    if (world != null) {
                        // Switch to the save file of the loaded world
                        java.lang.String saveWorld = com.bergerkiller.bukkit.mw.WorldConfig.get(world).inventory.getSharedWorldName();
                        main = com.bergerkiller.bukkit.mw.WorldConfig.get(saveWorld).getPlayerData(human.getName());
                    }
                } catch (java.lang.Throwable t) {
                    // Stick with the current world for now.
                }
            }
            tagcompound = com.bergerkiller.bukkit.mw.MWPlayerDataController.read(main, human);
            if ((!hasPlayedBefore) || MyWorlds.forceMainWorldSpawn) {
                // Alter saved data to point to the main world
                com.bergerkiller.bukkit.mw.MWPlayerDataController.setLocation(tagcompound, com.bergerkiller.bukkit.mw.WorldManager.getSpawnLocation(com.bergerkiller.bukkit.mw.MyWorlds.getMainWorld()));
            }
            // Minecraft bugfix here: Clear effects BEFORE loading the data
            // This resolves issues with effects staying behind
            com.bergerkiller.bukkit.mw.MWPlayerDataController.clearEffects(human);
            // Load the save file
            com.bergerkiller.bukkit.common.utils.NBTUtil.loadEntity(human, tagcompound);
            if (human instanceof org.bukkit.entity.Player) {
                // Bukkit bug: entityplayer.e(tag) -> b(tag) -> craft.readExtraData(tag) which instantly sets it
                // Make sure the player is marked as being new
                com.bergerkiller.bukkit.common.utils.PlayerUtil.setHasPlayedBefore(((org.bukkit.entity.Player) (human)), hasPlayedBefore);
                if (hasPlayedBefore) {
                    // As specified in the WorldNBTStorage implementation, set this
                    com.bergerkiller.bukkit.common.utils.PlayerUtil.setFirstPlayed(((org.bukkit.entity.Player) (human)), main.lastModified());
                }
            }
            com.bergerkiller.bukkit.mw.MWPlayerDataController.postLoad(human);
            return tagcompound;
        } catch (java.lang.Exception exception) {
            org.bukkit.Bukkit.getLogger().warning("Failed to load player data for " + human.getName());
            exception.printStackTrace();
            return super.onLoad(human);
        }
    }

    /**
     * Fired when a player respawns and all it's settings will be wiped.
     * The player contains all information right before respawning.
     * All data that would be wiped should be written has being wiped.
     * This involves a manual save.
     *
     * @param player
     * 		that respawned
     * @param respawnLocation
     * 		where the player respawns at
     */
    public void onRespawnSave(org.bukkit.entity.Player player, org.bukkit.Location respawnLocation) {
        try {
            // Generate player saved information - used in favour of accessing NMS fields
            com.bergerkiller.bukkit.common.nbt.CommonTagCompound savedInfo = com.bergerkiller.bukkit.common.utils.NBTUtil.saveEntity(player, null);
            // Generate a new tag compound with information
            com.bergerkiller.bukkit.common.nbt.CommonTagCompound tagcompound = com.bergerkiller.bukkit.mw.MWPlayerDataController.createEmptyData(player);
            // We store this entire Bukkit tag + experience information!
            com.bergerkiller.bukkit.common.nbt.CommonTagCompound bukkitTag = savedInfo.get("bukkit", com.bergerkiller.bukkit.common.nbt.CommonTagCompound.class);
            if (bukkitTag != null) {
                // But, we do need to wipe information as specified
                if (bukkitTag.getValue("keepLevel", false)) {
                    // Preserve experience
                    bukkitTag.putValue("newTotalExp", savedInfo.getValue("XpTotal", 0));
                    bukkitTag.putValue("newLevel", savedInfo.getValue("XpLevel", 0));
                    tagcompound.putValue("XpP", savedInfo.getValue("XpP", 0.0F));
                }
                // Store experience (if not preserved, uses newTotal/newLevel) and the tag
                tagcompound.putValue("XpTotal", bukkitTag.getValue("newTotalExp", 0));
                tagcompound.putValue("XpLevel", bukkitTag.getValue("newLevel", 0));
                tagcompound.put("bukkit", bukkitTag);
            }
            // Ender inventory should not end up wiped!
            com.bergerkiller.bukkit.common.nbt.CommonTagList enderItems = savedInfo.get("EnderItems", com.bergerkiller.bukkit.common.nbt.CommonTagList.class);
            if (enderItems != null) {
                tagcompound.put("EnderItems", enderItems);
            }
            // Now, go ahead and save this data
            java.io.File mainDest = com.bergerkiller.bukkit.mw.MWPlayerDataController.getMainFile(player.getName());
            java.io.File dest = com.bergerkiller.bukkit.mw.MWPlayerDataController.getSaveFile(player);
            tagcompound.writeTo(dest);
            // Finally, we need to update where the player is at right now
            // To do so, we will write a new main world where the player is meant to be
            // This operation is a bit optional at this point, but it avoids possible issues in case of crashes
            // This is only needed if a main player data file doesn't exist
            // (this should in theory never happen either...player is not joining)
            if (mainDest.exists()) {
                tagcompound = com.bergerkiller.bukkit.common.nbt.CommonTagCompound.readFrom(mainDest);
                tagcompound.putUUID("World", respawnLocation.getWorld().getUID());
                tagcompound.writeTo(mainDest);
            }
        } catch (java.lang.Exception exception) {
            org.bukkit.Bukkit.getLogger().warning("Failed to save player respawned data for " + player.getName());
            exception.printStackTrace();
        }
    }

    @java.lang.Override
    public void onSave(org.bukkit.entity.HumanEntity human) {
        try {
            com.bergerkiller.bukkit.common.nbt.CommonTagCompound tagcompound = com.bergerkiller.bukkit.common.utils.NBTUtil.saveEntity(human, null);
            // Request several locations where player data is stored
            // Main file: the Main World folder where only the current World is stored
            // Pos file: the folder of the World the player is on where the position is stored
            // Dest file: the inventory-merged folder where player info is stored
            java.io.File mainFile = com.bergerkiller.bukkit.mw.MWPlayerDataController.getMainFile(human.getName());
            java.io.File posFile = com.bergerkiller.bukkit.mw.WorldConfig.get(human).getPlayerData(human.getName());
            java.io.File destFile = com.bergerkiller.bukkit.mw.MWPlayerDataController.getSaveFile(human);
            org.bukkit.Location loc = human.getLocation();
            if (posFile.equals(destFile)) {
                // Append the Last Pos/Rot to the data
                tagcompound.putListValues(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTPOS, loc.getX(), loc.getY(), loc.getZ());
                tagcompound.putListValues(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTROT, loc.getYaw(), loc.getPitch());
            } else {
                // Append original last position (if available) to the data
                if (destFile.exists()) {
                    com.bergerkiller.bukkit.common.nbt.CommonTagCompound data = com.bergerkiller.bukkit.mw.MWPlayerDataController.read(destFile, human);
                    if (data.containsKey(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTPOS)) {
                        tagcompound.put(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTPOS, data.get(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTPOS));
                    }
                    if (data.containsKey(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTROT)) {
                        tagcompound.put(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTROT, data.get(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTROT));
                    }
                }
                // Write the Last Pos/Rot to the official world file instead
                com.bergerkiller.bukkit.common.nbt.CommonTagCompound data = com.bergerkiller.bukkit.mw.MWPlayerDataController.read(posFile, human);
                data.putListValues(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTPOS, loc.getX(), loc.getY(), loc.getZ());
                data.putListValues(com.bergerkiller.bukkit.mw.MWPlayerDataController.DATA_TAG_LASTROT, loc.getYaw(), loc.getPitch());
                data.writeTo(posFile);
            }
            // Save data to the destination file
            tagcompound.writeTo(destFile);
            // Write the current world name of the player to the save file of the main world
            if (!mainFile.equals(destFile)) {
                // Update the world in the main file
                com.bergerkiller.bukkit.common.nbt.CommonTagCompound maincompound = com.bergerkiller.bukkit.mw.MWPlayerDataController.read(mainFile, human);
                maincompound.put("Pos", tagcompound.get("Pos"));
                maincompound.put("Rotation", tagcompound.get("Rotation"));
                maincompound.putUUID("World", human.getWorld().getUID());
                maincompound.writeTo(mainFile);
            }
        } catch (java.lang.Exception exception) {
            org.bukkit.Bukkit.getLogger().warning("Failed to save player data for " + human.getName());
            exception.printStackTrace();
        }
    }
}