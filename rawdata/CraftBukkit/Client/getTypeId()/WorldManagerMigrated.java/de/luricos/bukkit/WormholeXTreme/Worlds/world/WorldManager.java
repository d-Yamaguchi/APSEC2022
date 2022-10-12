/* Wormhole X-Treme Worlds Plugin for Bukkit
Copyright (C) 2011 Dean Bailey <https://github.com/alron/Wormhole-X-Treme-Worlds>


This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.luricos.bukkit.WormholeXTreme.Worlds.world;
import de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager;
import de.luricos.bukkit.WormholeXTreme.Worlds.config.XMLConfig;
import de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsAutoloadException;
import de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsDuplicateUUIDException;
import de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger;
/**
 * The Class WorldManager.
 *
 * @author alron
 */
public class WorldManager {
    /**
     * The world list.
     */
    private static final java.util.concurrent.ConcurrentHashMap<java.lang.String, de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld> worldList = new java.util.concurrent.ConcurrentHashMap<java.lang.String, de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld>();

    /**
     * The Constant thisPlugin.
     */
    private static final de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds thisPlugin = de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds.getThisPlugin();

    /**
     * Adds the world.
     *
     * @param wormholeWorld
     * 		the world
     * @return true, if successful
     */
    public static boolean addWorld(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld) {
        if (wormholeWorld != null) {
            try {
                de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.put(wormholeWorld.getWorldName(), wormholeWorld);
                return true;
            } catch (final java.lang.NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Check non safe type id.
     *
     * @param typeId
     * 		the type id
     * @return true, if successful
     */
    private static boolean checkNonSafeTypeId(final int typeId) {
        if (((typeId == 0) || (typeId == 10)) || (typeId == 11)) {
            return false;
        }
        de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, "Did not find blockId to be unsafe material:" + typeId);
        return true;
    }

    /**
     * Check safe block.
     *
     * @param safeBlock
     * 		the safe block
     * @return true, if successful
     */
    private static boolean checkSafeBlock(final org.bukkit.block.Block safeBlock) {
        if (safeBlock != null) {
            int typeId = safeBlock.getType();
            return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTypeId(typeId);
        }
        return false;
    }

    /**
     * Check safe block above.
     *
     * @param safeBlock
     * 		the safe block
     * @return true, if successful
     */
    private static boolean checkSafeBlockAbove(final org.bukkit.block.Block safeBlock) {
        if (safeBlock != null) {
            final int typeId = safeBlock.getFace(org.bukkit.block.BlockFace.UP).getTypeId();
            return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTypeId(typeId);
        }
        return false;
    }

    /**
     * Check safe block below.
     *
     * @param safeBlock
     * 		the safe block
     * @return true, if successful
     */
    private static boolean checkSafeBlockBelow(final org.bukkit.block.Block safeBlock) {
        if (safeBlock != null) {
            final int typeId = safeBlock.getFace(org.bukkit.block.BlockFace.DOWN).getTypeId();
            return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkNonSafeTypeId(typeId);
        }
        return false;
    }

    /**
     * Check safe teleport destination.
     *
     * @param destination
     * 		the destination
     * @return true, if successful
     */
    private static boolean checkSafeTeleportDestination(final org.bukkit.Location destination) {
        if (destination != null) {
            final org.bukkit.block.Block safeBlock = destination.getBlock();
            if ((de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeBlock(safeBlock) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeBlockAbove(safeBlock)) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeBlockBelow(safeBlock)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check safe type id.
     *
     * @param typeId
     * 		the type id
     * @return true, if successful
     */
    private static boolean checkSafeTypeId(final int typeId) {
        if ((((typeId == 0) || (typeId == 8)) || (typeId == 9)) || (typeId == 66)) {
            return true;
        }
        return false;
    }

    /**
     * Check timelock worlds.
     */
    public static void checkTimelockWorlds() {
        if (de.luricos.bukkit.WormholeXTreme.Worlds.config.ConfigManager.getServerOptionTimelock()) {
            for (final java.lang.String key : de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.keySet()) {
                if (key != null) {
                    final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.get(key);
                    if (((wormholeWorld != null) && wormholeWorld.isWorldLoaded()) && wormholeWorld.isWorldTimeLock()) {
                        final long worldRelativeTime = wormholeWorld.getWorld().getTime() % 24000;
                        if ((worldRelativeTime > 11800) && (wormholeWorld.getWorldTimeLockType() == TimeLockType.DAY)) {
                            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Set world: " + key) + " New time: 0") + " Old Time: ") + worldRelativeTime);
                            wormholeWorld.getWorld().setTime(0);
                        } else if (((worldRelativeTime < 13500) || (worldRelativeTime > 21800)) && (wormholeWorld.getWorldTimeLockType() == TimeLockType.NIGHT)) {
                            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Set world: " + key) + " New time: 13700") + " Old Time: ") + worldRelativeTime);
                            wormholeWorld.getWorld().setTime(13700);
                        }
                    }
                }
            }
        }
    }

    /**
     * Clear world creatures.
     *
     * @param wormholeWorld
     * 		the wormhole world
     * @return the int number of creatures cleared
     */
    public static int clearWorldCreatures(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld) {
        int cleared = 0;
        if ((!wormholeWorld.isWorldAllowSpawnHostiles()) || (!wormholeWorld.isWorldAllowSpawnNeutrals())) {
            final java.util.List<org.bukkit.entity.LivingEntity> entityList = wormholeWorld.getWorld().getLivingEntities();
            for (final org.bukkit.entity.LivingEntity entity : entityList) {
                if (((!wormholeWorld.isWorldAllowSpawnHostiles()) && ((entity instanceof org.bukkit.entity.Monster) || (entity instanceof org.bukkit.entity.Flying))) || ((!wormholeWorld.isWorldAllowSpawnNeutrals()) && ((entity instanceof org.bukkit.entity.Animals) || (entity instanceof org.bukkit.entity.WaterMob)))) {
                    de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, "Removed entity: " + entity);
                    entity.remove();
                    cleared++;
                }
            }
        }
        return cleared;
    }

    private static void createDefaultWorld() {
        final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld = new de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld();
        final org.bukkit.World world = de.luricos.bukkit.WormholeXTreme.Worlds.WormholeXTremeWorlds.getThisPlugin().getServer().getWorlds().get(0);
        wormholeWorld.setWorldName(world.getName());
        // set worldType
        switch (world.getEnvironment()) {
            case NORMAL :
                wormholeWorld.setWorldTypeNormal(true);
                break;
            case NETHER :
                wormholeWorld.setWorldTypeNether(true);
                break;
            case SKYLANDS :
                wormholeWorld.setWorldTypeSkylands(true);
                break;
        }
        de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.createWormholeWorld(wormholeWorld);
        de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.INFO, false, "Added default world as wormhole world.");
    }

    /**
     * Creates the world.
     *
     * @param wormholeWorld
     * 		the wormhole world
     * @return true, if successful
     */
    public static boolean createWormholeWorld(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld) {
        if ((wormholeWorld == null) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.isWormholeWorld(wormholeWorld.getWorldName()))
            return false;

        final java.lang.String worldName = wormholeWorld.getWorldName();
        final org.bukkit.World.Environment worldEnvironment = wormholeWorld.getWorldEnvironment();
        if ("".equals(worldName)) {
            return false;
        }
        try {
            if (de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.thisPlugin.getServer().getWorld(worldName) == null) {
                org.bukkit.World world = (wormholeWorld.getWorldSeed() == 0) ? de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.thisPlugin.getServer().createWorld(wormholeWorld.getWorldName(), worldEnvironment) : de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.thisPlugin.getServer().createWorld(wormholeWorld.getWorldName(), worldEnvironment, wormholeWorld.getWorldSeed());
                if (world == null)
                    throw new de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsDuplicateUUIDException(((("World '" + wormholeWorld.getWorldName()) + "' is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from ") + wormholeWorld.getWorldName()) + "'s world directory if you want to be able to load the duplicate world.");

                wormholeWorld.setWorld(world);
                if (wormholeWorld.getWorldSeed() == 0)
                    wormholeWorld.setWorldSeed(wormholeWorld.getWorld().getSeed());

                wormholeWorld.getWorld().save();
            } else {
                wormholeWorld.setWorld(de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.thisPlugin.getServer().getWorld(worldName));
                wormholeWorld.setWorldSeed(wormholeWorld.getWorld().getSeed());
            }
            wormholeWorld.setWorldSpawn(wormholeWorld.isWorldTypeNether() || wormholeWorld.isWorldTypeSkylands() ? de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawn(wormholeWorld.getWorld().getSpawnLocation(), 13, 13) : wormholeWorld.getWorld().getSpawnLocation());
            final int tsX = wormholeWorld.getWorldSpawn().getBlockX();
            final int tsY = wormholeWorld.getWorldSpawn().getBlockY();
            final int tsZ = wormholeWorld.getWorldSpawn().getBlockZ();
            if (!wormholeWorld.getWorldSpawn().equals(wormholeWorld.getWorld().getSpawnLocation())) {
                wormholeWorld.getWorld().setSpawnLocation(tsX, tsY, tsZ);
            }
            final int[] tempSpawn = new int[]{ tsX, tsY, tsZ };
            wormholeWorld.setWorldCustomSpawn(tempSpawn);
            de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.clearWorldCreatures(wormholeWorld);
            de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.setWorldWeather(wormholeWorld);
            de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.setWorldPvP(wormholeWorld);
            wormholeWorld.setWorldLoaded(true);
            return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.addWorld(wormholeWorld);
        } catch (de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsDuplicateUUIDException e) {
            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.SEVERE, true, e.getMessage());
        }
        return false;
    }

    /**
     * Find safe spawn.
     *
     * @param spawnLocation
     * 		the wormhole world
     * @return the location
     */
    private static org.bukkit.Location findSafeSpawn(final org.bukkit.Location spawnLocation, final int yDepth, final int xzDepth) {
        if (spawnLocation != null) {
            final int worldSpawnY = spawnLocation.getBlockY();
            final int worldSpawnX = spawnLocation.getBlockX();
            final int worldSpawnZ = spawnLocation.getBlockZ();
            final org.bukkit.World world = spawnLocation.getWorld();
            final int xzDepthOdd = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.oddNumberMaker(xzDepth);
            final int yDepthOdd = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.oddNumberMaker(yDepth);
            org.bukkit.Location safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX, worldSpawnZ, yDepthOdd, xzDepthOdd);
            if (safeSpawn != null) {
                de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, "Found safe spawn in pass 0/0.");
                return safeSpawn;
            } else {
                safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX + xzDepthOdd, worldSpawnZ, yDepthOdd, xzDepthOdd);
                if (safeSpawn != null) {
                    de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ("Found safe spawn in pass +" + xzDepthOdd) + "/0.");
                    return safeSpawn;
                } else {
                    safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX + xzDepthOdd, worldSpawnZ + xzDepthOdd, yDepthOdd, xzDepthOdd);
                    if (safeSpawn != null) {
                        de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Found safe spawn in pass +" + xzDepthOdd) + "/+") + xzDepthOdd) + ".");
                        return safeSpawn;
                    } else {
                        safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX, worldSpawnZ + xzDepthOdd, yDepthOdd, xzDepthOdd);
                        if (safeSpawn != null) {
                            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ("Found safe spawn in pass 0/+" + xzDepthOdd) + ".");
                            return safeSpawn;
                        } else {
                            safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX - xzDepthOdd, worldSpawnZ + xzDepthOdd, yDepthOdd, xzDepthOdd);
                            if (safeSpawn != null) {
                                de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Found safe spawn in pass -" + xzDepthOdd) + "/+") + xzDepthOdd) + ".");
                                return safeSpawn;
                            } else {
                                safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX - xzDepthOdd, worldSpawnZ, yDepthOdd, xzDepthOdd);
                                if (safeSpawn != null) {
                                    de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ("Found safe spawn in pass -" + xzDepthOdd) + "/0.");
                                    return safeSpawn;
                                } else {
                                    safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX - xzDepthOdd, worldSpawnZ - xzDepthOdd, yDepthOdd, xzDepthOdd);
                                    if (safeSpawn != null) {
                                        de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Found safe spawn in pass -" + xzDepthOdd) + "/-") + xzDepthOdd) + ".");
                                        return safeSpawn;
                                    } else {
                                        safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX, worldSpawnZ - xzDepthOdd, yDepthOdd, xzDepthOdd);
                                        if (safeSpawn != null) {
                                            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ("Found safe spawn in pass 0/-" + xzDepthOdd) + ".");
                                            return safeSpawn;
                                        } else {
                                            safeSpawn = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawnFromYXZ(world, worldSpawnY, worldSpawnX + xzDepthOdd, worldSpawnZ - xzDepthOdd, yDepthOdd, xzDepthOdd);
                                            if (safeSpawn != null) {
                                                de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, ((("Found safe spawn in pass +" + xzDepthOdd) + "/-") + xzDepthOdd) + ".");
                                                return safeSpawn;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, (((xzDepthOdd * xzDepthOdd) * yDepthOdd) * 9) + " blocks later. No safe spawn in sight.");
            return spawnLocation;
        }
        de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINE, false, "Returned null value for safe spawn!");
        return null;
    }

    /**
     * Find safe spawn from yxz.
     *
     * @param world
     * 		the world
     * @param worldSpawnY
     * 		the world spawn y
     * @param worldSpawnX
     * 		the world spawn x
     * @param worldSpawnZ
     * 		the world spawn z
     * @param ySize
     * 		the yAxis depth
     * @param xzSize
     * 		the xzAxis depth
     * @return the location
     */
    private static org.bukkit.Location findSafeSpawnFromYXZ(final org.bukkit.World world, final int worldSpawnY, final int worldSpawnX, final int worldSpawnZ, final int ySize, final int xzSize) {
        final int yAxisDepth = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.oddNumberMaker(ySize);
        final int yAxisSmall = (yAxisDepth - 1) / 2;
        final int yAxisLarge = yAxisSmall + 1;
        final int xzAxisDepth = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.oddNumberMaker(xzSize);
        final int xzAxisSmall = (xzAxisDepth - 1) / 2;
        final int xzAxisLarge = xzAxisSmall + 1;
        final java.util.HashMap<java.lang.Integer, org.bukkit.block.Block[][]> blockYaxisPlane = new java.util.HashMap<java.lang.Integer, org.bukkit.block.Block[][]>(yAxisDepth * 2);
        int iYY = 0;
        if ((worldSpawnY <= (127 - yAxisSmall)) && (worldSpawnY >= yAxisLarge)) {
            for (int iY = worldSpawnY - yAxisSmall; iY < (worldSpawnY + yAxisLarge); iY++) {
                blockYaxisPlane.put(iYY, de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.populateYaxisPlane(world, worldSpawnX, worldSpawnZ, iY, iYY, xzAxisDepth, xzAxisSmall, xzAxisLarge));
                iYY++;
            }
        } else if (worldSpawnY <= yAxisSmall) {
            for (int iY = worldSpawnY; iY < (worldSpawnY + yAxisDepth); iY++) {
                blockYaxisPlane.put(iYY, de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.populateYaxisPlane(world, worldSpawnX, worldSpawnZ, iY, iYY, xzAxisDepth, xzAxisSmall, xzAxisLarge));
                iYY++;
            }
        } else if (worldSpawnY >= (127 - yAxisLarge)) {
            for (int iY = worldSpawnY - yAxisDepth; iY < worldSpawnY; iY++) {
                blockYaxisPlane.put(iYY, de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.populateYaxisPlane(world, worldSpawnX, worldSpawnZ, iY, iYY, xzAxisDepth, xzAxisSmall, xzAxisLarge));
                iYY++;
            }
        }
        for (int y = 0; y < yAxisDepth; y++) {
            final org.bukkit.block.Block[][] tmpBlockArr = blockYaxisPlane.get(y);
            for (int x = 0; x < xzAxisDepth; x++) {
                for (int z = 0; z < xzAxisDepth; z++) {
                    final org.bukkit.block.Block tmpBlock = tmpBlockArr[x][z];
                    if (tmpBlock != null) {
                        final int typeId = tmpBlock.getTypeId();
                        if ((de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTypeId(typeId) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeBlockAbove(tmpBlock)) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeBlockBelow(tmpBlock)) {
                            blockYaxisPlane.clear();
                            return new org.bukkit.Location(tmpBlock.getWorld(), tmpBlock.getX() + 0.5, tmpBlock.getY(), tmpBlock.getZ() + 0.5);
                        }
                    }
                }
            }
        }
        blockYaxisPlane.clear();
        return null;
    }

    /**
     * Gets the all world names.
     *
     * @return the all world names
     */
    public static java.lang.String[] getAllWorldNames() {
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.keySet().toArray(new java.lang.String[de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.size()]);
    }

    /**
     * Gets the all worlds.
     *
     * @return the all worlds
     */
    public static de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld[] getAllWorlds() {
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.values().toArray(new de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld[de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.size()]);
    }

    /**
     * Gets the safe spawn location.
     *
     * @param wormholeWorld
     * 		the wormhole world
     * @param player
     * 		the player
     * @return the safe teleport location
     */
    public static org.bukkit.Location getSafeSpawnLocation(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld, final org.bukkit.entity.Player player) {
        return wormholeWorld.isWorldTypeNether() || wormholeWorld.isWorldTypeSkylands() ? de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTeleportDestination(wormholeWorld.getWorldSpawn()) ? new org.bukkit.Location(wormholeWorld.getWorld(), wormholeWorld.getWorldSpawn().getBlockX() + 0.5, wormholeWorld.getWorldSpawn().getBlockY(), wormholeWorld.getWorldSpawn().getBlockZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch()) : de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.findSafeSpawn(wormholeWorld.getWorldSpawn(), 3, 3) : de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.checkSafeTeleportDestination(wormholeWorld.getWorldSpawn()) ? new org.bukkit.Location(wormholeWorld.getWorld(), wormholeWorld.getWorldSpawn().getBlockX() + 0.5, wormholeWorld.getWorldSpawn().getBlockY(), wormholeWorld.getWorldSpawn().getBlockZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch()) : new org.bukkit.Location(wormholeWorld.getWorld(), wormholeWorld.getWorldSpawn().getBlockX() + 0.5, wormholeWorld.getWorld().getHighestBlockYAt(wormholeWorld.getWorldSpawn()), wormholeWorld.getWorldSpawn().getBlockZ() + 0.5, player.getLocation().getYaw(), player.getLocation().getPitch());
    }

    /**
     * Gets the world from player.
     *
     * @param player
     * 		the player
     * @return the world from player
     */
    public static de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld getWorldFromPlayer(final org.bukkit.entity.Player player) {
        if (player != null) {
            return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.getWormholeWorld(player.getWorld().getName());
        } else {
            return null;
        }
    }

    /**
     * Gets the wormholeWorld.
     *
     * @param worldName
     * 		the world name
     * @return the wormholeWorld
     */
    public static de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld getWormholeWorld(final java.lang.String worldName) {
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.get(worldName);
    }

    /**
     * Gets the wormhole world.
     *
     * @param world
     * 		the world
     * @return the wormhole world
     */
    public static de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld getWormholeWorld(final org.bukkit.World world) {
        return de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.get(world.getName());
    }

    /**
     * Checks if is wormhole world.
     *
     * @param worldName
     * 		the world name
     * @return true, if is wormhole world
     */
    public static boolean isWormholeWorld(final java.lang.String worldName) {
        if ((worldName != null) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.containsKey(worldName)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is wormhole world.
     *
     * @param world
     * 		the world
     * @return true, if is wormhole world
     */
    public static boolean isWormholeWorld(final org.bukkit.World world) {
        if ((world != null) && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.containsKey(world.getName())) {
            return true;
        }
        return false;
    }

    /**
     * Load autoconnect worlds.
     *
     * @return the int number of worlds loaded.
     */
    public static int loadAutoloadWorlds() {
        int loaded = 0;
        if (!(de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.getAllWorlds().length > 0)) {
            de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.createDefaultWorld();
            loaded++;
        }
        for (final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld : de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.getAllWorlds()) {
            if (wormholeWorld.isWorldAutoload() && de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.loadWorld(wormholeWorld))
                loaded++;

        }
        return loaded;
    }

    /**
     * Connect world.
     *
     * @param wormholeWorld
     * 		the wormhole world
     * @return true, if successful
     */
    public static boolean loadWorld(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld) {
        try {
            if ((wormholeWorld.getWorldName() == null) || "".equals(wormholeWorld.getWorldName())) {
                throw new de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsAutoloadException("Worldname was null or empty.");
            }
            if (wormholeWorld.getWorldEnvironment() == null) {
                throw new de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsAutoloadException("Environment was undefined. Please check if at least Environment is set to true.");
            }
            org.bukkit.World world = (wormholeWorld.getWorldSeed() == 0) ? de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.thisPlugin.getServer().createWorld(wormholeWorld.getWorldName(), wormholeWorld.getWorldEnvironment()) : de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.thisPlugin.getServer().createWorld(wormholeWorld.getWorldName(), wormholeWorld.getWorldEnvironment(), wormholeWorld.getWorldSeed());
            if (world == null)
                throw new de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsDuplicateUUIDException(((("World '" + wormholeWorld.getWorldName()) + "' is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from ") + wormholeWorld.getWorldName()) + "'s world directory if you want to be able to load the duplicate world.");

            wormholeWorld.setWorld(world);
            wormholeWorld.getWorld().setSpawnLocation(wormholeWorld.getWorldCustomSpawn()[0], wormholeWorld.getWorldCustomSpawn()[1], wormholeWorld.getWorldCustomSpawn()[2]);
            wormholeWorld.setWorldSpawn(wormholeWorld.getWorld().getSpawnLocation());
            if (wormholeWorld.getWorldSeed() == 0) {
                wormholeWorld.setWorldSeed(wormholeWorld.getWorld().getSeed());
            }
            wormholeWorld.setWorldLoaded(true);
            if (de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.addWorld(wormholeWorld)) {
                final int c = de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.clearWorldCreatures(wormholeWorld);
                if (c > 0) {
                    de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.INFO, false, ((("Cleared \"" + c) + "\" creature entities on world \"") + wormholeWorld.getWorldName()) + "\"");
                }
                de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.setWorldWeather(wormholeWorld);
                de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.setWorldPvP(wormholeWorld);
                return true;
            }
        } catch (de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsAutoloadException e) {
            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.SEVERE, true, "WorldsAutoloadException: " + e.getMessage());
        } catch (de.luricos.bukkit.WormholeXTreme.Worlds.exceptions.WorldsDuplicateUUIDException e) {
            de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.SEVERE, true, "WorldsDuplicateUUIDException: " + e.getMessage());
        }
        return false;
    }

    /**
     * Odd number maker. Takes numbers 3 or larger and makes sure they are odd.
     *
     * @param number
     * 		the number
     * @return the odd int
     */
    private static int oddNumberMaker(final int number) {
        return ((number % 2) == 0) && (number > 3) ? number - 1 : number > 2 ? number : 3;
    }

    /**
     * Populate yaxis plane.
     *
     * @param world
     * 		the world
     * @param worldSpawnX
     * 		the world spawn x
     * @param worldSpawnZ
     * 		the world spawn z
     * @param iY
     * 		the i y
     * @param iYY
     * 		the i yy
     * @param xzAxisDepth
     * 		the xzAxis depth
     * @param xzAxisSmall
     * 		the xzAxis small half depth
     * @param xzAxisLarge
     * 		the xzAxis large half depth
     * @return the block[][]
     */
    private static org.bukkit.block.Block[][] populateYaxisPlane(final org.bukkit.World world, final int worldSpawnX, final int worldSpawnZ, final int iY, final int iYY, final int xzAxisDepth, final int xzAxisSmall, final int xzAxisLarge) {
        final org.bukkit.block.Block[][] xzAxis = new org.bukkit.block.Block[xzAxisDepth][xzAxisDepth];
        int iXX = 0;
        for (int iX = worldSpawnX - xzAxisSmall; iX < (worldSpawnX + xzAxisLarge); iX++) {
            int iZZ = 0;
            for (int iZ = worldSpawnZ - xzAxisSmall; iZ < (worldSpawnZ + xzAxisLarge); iZ++) {
                xzAxis[iXX][iZZ] = world.getBlockAt(iX, iY, iZ);
                de.luricos.bukkit.WormholeXTreme.Worlds.utils.WXLogger.prettyLog(java.util.logging.Level.FINEST, false, (((((((((((("Y/YY: " + iY) + "/") + iYY) + " X/XX: ") + iX) + "/") + iXX) + " Z/ZZ: ") + iZ) + "/") + iZZ) + " Block: ") + xzAxis[iXX][iZZ].toString());
                iZZ++;
            }
            iXX++;
        }
        return xzAxis;
    }

    /**
     * Removes the world.
     *
     * @param world
     * 		the world
     */
    public static void removeWorld(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld world) {
        if (world != null) {
            de.luricos.bukkit.WormholeXTreme.Worlds.config.XMLConfig.deleteXmlWorldConfig(world.getWorldName());
            de.luricos.bukkit.WormholeXTreme.Worlds.world.WorldManager.worldList.remove(world.getWorldName());
        }
    }

    /**
     * Sets the world pv p.
     *
     * @param wormholeWorld
     * 		the new world pv p
     */
    public static void setWorldPvP(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld) {
        if (wormholeWorld.isWorldLoaded()) {
            wormholeWorld.getWorld().setPVP(wormholeWorld.isWorldAllowPvP());
        }
    }

    /**
     * Sets the world weather.
     *
     * @param wormholeWorld
     * 		the new world weather
     */
    public static void setWorldWeather(final de.luricos.bukkit.WormholeXTreme.Worlds.world.WormholeWorld wormholeWorld) {
        if (wormholeWorld.isWorldWeatherLock()) {
            final org.bukkit.World world = wormholeWorld.getWorld();
            switch (wormholeWorld.getWorldWeatherLockType()) {
                case CLEAR :
                    world.setStorm(false);
                    world.setThundering(false);
                    break;
                case RAIN :
                    world.setStorm(true);
                    world.setThundering(false);
                    break;
                case STORM :
                    world.setStorm(true);
                    world.setThundering(true);
                    break;
                default :
                    break;
            }
        }
    }
}