package com.bergerkiller.bukkit.mw;
import com.bergerkiller.bukkit.common.utils.MaterialUtil;
public class Util {
    private static final int STATW_ID = org.bukkit.Material.STATIONARY_WATER.getId();

    public static boolean isSolid(org.bukkit.block.Block b, org.bukkit.block.BlockFace direction) {
        int maxwidth = 10;
        while ((maxwidth--) >= 0) {
            int id = b.getTypeId();
            if (com.bergerkiller.bukkit.common.utils.MaterialUtil.isType(id, org.bukkit.Material.WATER, org.bukkit.Material.STATIONARY_WATER)) {
                b = b.getRelative(direction);
            } else {
                return id != 0;
            }
        } 
        return false;
    }

    private static boolean isObsidianPortal(org.bukkit.block.Block main, org.bukkit.block.BlockFace direction) {
        for (int counter = 0; counter < 20; counter++) {
            org.bukkit.Material type = main.getType();
            if (type == org.bukkit.Material.PORTAL) {
                main = main.getRelative(direction);
            } else if (type == org.bukkit.Material.OBSIDIAN) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Attempts to find the type of Portal that is near a specific Block
     *
     * @param world
     * 		to look in
     * @param x
     * 		- coordinate to look nearby
     * @param y
     * 		- coordinate to look nearby
     * @param z
     * 		- coordinate to look nearby
     * @return Portal material, or NULL if no Portal is found
     */
    public static org.bukkit.Material findPortalMaterial(org.bukkit.World world, int x, int y, int z) {
        // Check self
        org.bukkit.Material mat = com.bergerkiller.bukkit.mw.Util.findPortalMaterialSingle(world, x, y, z);
        if (mat == null) {
            // Check in a 3x3x3 cube area
            int dx;
            int dy;
            int dz;
            for (dx = -1; dx <= 1; dx++) {
                for (dy = -1; dy <= 1; dy++) {
                    for (dz = -1; dz <= 1; dz++) {
                        mat = com.bergerkiller.bukkit.mw.Util.findPortalMaterialSingle(world, x + dx, y + dy, z + dz);
                        if (mat != null) {
                            return mat;
                        }
                    }
                }
            }
        }
        return mat;
    }

    private static org.bukkit.Material findPortalMaterialSingle(org.bukkit.World world, int x, int y, int z) {
        int typeId = WorldUtil.getBlockType(world, x, y, z);
        if (typeId == com.bergerkiller.bukkit.mw.Util.STATW_ID) {
            if (com.bergerkiller.bukkit.mw.Util.isWaterPortal(world.getBlockAt(x, y, z))) {
                return org.bukkit.Material.STATIONARY_WATER;
            }
        } else if (typeId == org.bukkit.Material.PORTAL.getId()) {
            if (com.bergerkiller.bukkit.mw.Util.isNetherPortal(world.getBlockAt(x, y, z))) {
                return org.bukkit.Material.PORTAL;
            }
        } else if (typeId == org.bukkit.Material.ENDER_PORTAL.getId()) {
            if (com.bergerkiller.bukkit.mw.Util.isEndPortal(world.getBlockAt(x, y, z))) {
                return org.bukkit.Material.ENDER_PORTAL;
            }
        }
        return null;
    }

    /**
     * Checks if a given block is part of a valid water portal, plugin settings are applied
     *
     * @param main
     * 		portal block
     * @return True if it is a water Portal, False if not
     */
    public static boolean isWaterPortal(org.bukkit.block.Block main) {
        if ((!MyWorlds.useWaterTeleport) || (main.getTypeId() != com.bergerkiller.bukkit.mw.Util.STATW_ID)) {
            return false;
        }
        if ((main.getRelative(org.bukkit.block.BlockFace.UP).getTypeId() == com.bergerkiller.bukkit.mw.Util.STATW_ID) || (main.getRelative(org.bukkit.block.BlockFace.DOWN).getTypeId() == com.bergerkiller.bukkit.mw.Util.STATW_ID)) {
            boolean allow = false;
            if ((main.getRelative(org.bukkit.block.BlockFace.NORTH).getType() == org.bukkit.Material.AIR) || (main.getRelative(org.bukkit.block.BlockFace.SOUTH).getType() == org.bukkit.Material.AIR)) {
                if (com.bergerkiller.bukkit.mw.Util.isSolid(main, org.bukkit.block.BlockFace.WEST) && com.bergerkiller.bukkit.mw.Util.isSolid(main, org.bukkit.block.BlockFace.EAST)) {
                    allow = true;
                }
            } else if ((main.getRelative(org.bukkit.block.BlockFace.EAST).getType() == org.bukkit.Material.AIR) || (main.getRelative(org.bukkit.block.BlockFace.WEST).getType() == org.bukkit.Material.AIR)) {
                if (com.bergerkiller.bukkit.mw.Util.isSolid(main, org.bukkit.block.BlockFace.NORTH) && com.bergerkiller.bukkit.mw.Util.isSolid(main, org.bukkit.block.BlockFace.SOUTH)) {
                    allow = true;
                }
            }
            return allow;
        }
        return false;
    }

    /**
     * Checks if a given block is part of a valid ender portal, plugin settings are applied
     *
     * @param main
     * 		portal block
     * @return True if it is an end Portal, False if not
     */
    public static boolean isEndPortal(org.bukkit.block.Block main) {
        return main.getType() == org.bukkit.Material.ENDER_PORTAL;
    }

    /**
     * Checks if a given block is part of a valid nether portal, plugin settings are applied
     *
     * @param main
     * 		portal block
     * @param overrideMainType
     * 		- True to override the main block type checking
     * @return True if it is a nether Portal, False if not
     */
    public static boolean isNetherPortal(org.bukkit.block.Block main) {
        if (!MyWorlds.onlyObsidianPortals) {
            // Simple check
            return main.getType() == org.bukkit.Material.PORTAL;
        }
        // Obsidian portal check
        if (main.getType() != org.bukkit.Material.PORTAL) {
            return false;
        }
        if (com.bergerkiller.bukkit.mw.Util.isObsidianPortal(main, org.bukkit.block.BlockFace.UP) && com.bergerkiller.bukkit.mw.Util.isObsidianPortal(main, org.bukkit.block.BlockFace.DOWN)) {
            if (com.bergerkiller.bukkit.mw.Util.isObsidianPortal(main, org.bukkit.block.BlockFace.NORTH) && com.bergerkiller.bukkit.mw.Util.isObsidianPortal(main, org.bukkit.block.BlockFace.SOUTH)) {
                return true;
            }
            if (com.bergerkiller.bukkit.mw.Util.isObsidianPortal(main, org.bukkit.block.BlockFace.EAST) && com.bergerkiller.bukkit.mw.Util.isObsidianPortal(main, org.bukkit.block.BlockFace.WEST)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds the spawn offset to a given Location
     *
     * @param location
     * 		to add to, can be null
     * @return Location with the spawn offset
     */
    public static org.bukkit.Location spawnOffset(org.bukkit.Location location) {
        if (location == null) {
            return null;
        }
        return location.clone().add(0.5, 2, 0.5);
    }

    /**
     * Gets the Location from a Position
     *
     * @param position
     * 		to convert
     * @return the Location, or null on failure
     */
    public static org.bukkit.Location getLocation(com.bergerkiller.bukkit.mw.Position position) {
        if (position != null) {
            org.bukkit.Location loc = position.toLocation();
            if (loc.getWorld() != null) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Removes all unallowed characters from a portal name.
     * These are characters that would cause internal loading/saving issues otherwise.
     *
     * @param name
     * 		to filter
     * @return filtered name
     */
    public static java.lang.String filterPortalName(java.lang.String name) {
        if (name == null) {
            return null;
        } else {
            return name.replace("\"", "").replace("'", "");
        }
    }

    /**
     * Gets the amount of bytes of data stored on disk by a specific file or folder
     *
     * @param file
     * 		to get the size of
     * @return File/folder size in bytes
     */
    public static long getFileSize(java.io.File file) {
        if (!file.exists()) {
            return 0L;
        } else if (file.isDirectory()) {
            long size = 0;
            for (java.io.File subfile : file.listFiles()) {
                size += com.bergerkiller.bukkit.mw.Util.getFileSize(subfile);
            }
            return size;
        } else {
            return file.length();
        }
    }
}