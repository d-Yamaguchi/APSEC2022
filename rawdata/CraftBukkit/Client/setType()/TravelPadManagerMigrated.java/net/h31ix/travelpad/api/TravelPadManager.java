package net.h31ix.travelpad.api;
import net.h31ix.travelpad.LangManager;
import net.h31ix.travelpad.event.TravelPadCreateEvent;
import net.h31ix.travelpad.event.TravelPadDeleteEvent;
import net.h31ix.travelpad.event.TravelPadExpireEvent;
import net.h31ix.travelpad.event.TravelPadNameEvent;
public class TravelPadManager {
    private java.util.List<net.h31ix.travelpad.api.Pad> padList;

    private java.util.List<net.h31ix.travelpad.api.UnnamedPad> unvList;

    public net.h31ix.travelpad.api.Configuration config = new net.h31ix.travelpad.api.Configuration();

    final org.bukkit.Server server = org.bukkit.Bukkit.getServer();

    final org.bukkit.plugin.Plugin plugin;

    public net.h31ix.travelpad.LangManager l = new net.h31ix.travelpad.LangManager();

    public TravelPadManager(org.bukkit.plugin.Plugin plugin) {
        this.plugin = plugin;
        padList = config.getPads();
        unvList = config.getUnnamedPads();
    }

    /**
     * Update the list of pads
     */
    public void update() {
        padList = config.getPads();
        unvList = config.getUnnamedPads();
    }

    /**
     * Create a new, unnamed pad
     *
     * @param location
     * 		Location of the center of the pad
     * @param player
     * 		Player who should own this pad
     */
    public void createPad(final org.bukkit.Location location, org.bukkit.entity.Player player) {
        update();
        final net.h31ix.travelpad.api.UnnamedPad pad = new net.h31ix.travelpad.api.UnnamedPad(location, player);
        net.h31ix.travelpad.event.TravelPadCreateEvent e = new net.h31ix.travelpad.event.TravelPadCreateEvent(pad);
        plugin.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            config.addUnv(pad);
            final org.bukkit.entity.Player owner = pad.getOwner();
            server.getScheduler().scheduleSyncDelayedTask(plugin, __SmPLUnsupported__(0), 600L);
            DirectWorld world = state.getDirectWorld();
            int x = chunkX;
            int z = chunkZ;
            int y = world.getHighestBlockY(x, z);
            world.setRawTypeIdAndData(x, y, z, org.bukkit.Material.LONG_GRASS.getId(), 1);
            block.getRelative(org.bukkit.block.BlockFace.WEST).setType(org.bukkit.Material.STEP);
            block.getRelative(org.bukkit.block.BlockFace.NORTH).setType(org.bukkit.Material.STEP);
            block.getRelative(org.bukkit.block.BlockFace.SOUTH).setType(org.bukkit.Material.STEP);
            final org.bukkit.block.Block block = location.getBlock();
            block.getRelative(org.bukkit.block.BlockFace.UP).setType(org.bukkit.Material.WATER);
            server.getScheduler().scheduleSyncDelayedTask(plugin, __SmPLUnsupported__(1), 10L);
            owner.sendMessage(org.bukkit.ChatColor.GREEN + l.create_approve_1());
            owner.sendMessage(org.bukkit.ChatColor.GREEN + l.create_approve_2());
            update();
        }
    }

    /**
     * Remove an Unnamed Pad
     *
     * @param pad
     * 		UnnamedPad to be deleted
     */
    public void deleteUnnamedPad(net.h31ix.travelpad.api.UnnamedPad pad) {
        net.h31ix.travelpad.event.TravelPadExpireEvent e = new net.h31ix.travelpad.event.TravelPadExpireEvent(pad);
        plugin.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            update();
            org.bukkit.Location location = pad.getLocation();
            config.removePad(pad);
            pad.getOwner().sendMessage(org.bukkit.ChatColor.RED + l.pad_expire());
            deleteBlocks(location);
            update();
        }
    }

    /**
     * Clean up all the blocks around a pad after it has been deleted
     *
     * @param location
     * 		Location of pad to be destroyed
     */
    public void deleteBlocks(org.bukkit.Location location) {
        org.bukkit.World world = location.getWorld();
        org.bukkit.block.Block block = world.getBlockAt(location);
        block.setType(org.bukkit.Material.AIR);
        block.getRelative(org.bukkit.block.BlockFace.EAST).setType(org.bukkit.Material.AIR);
        block.getRelative(org.bukkit.block.BlockFace.SOUTH).setType(org.bukkit.Material.AIR);
        block.getRelative(org.bukkit.block.BlockFace.NORTH).setType(org.bukkit.Material.AIR);
        block.getRelative(org.bukkit.block.BlockFace.WEST).setType(org.bukkit.Material.AIR);
        org.bukkit.inventory.ItemStack i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.OBSIDIAN, 1);
        org.bukkit.inventory.ItemStack e = new org.bukkit.inventory.ItemStack(org.bukkit.Material.BRICK, 4);
        world.dropItemNaturally(block.getLocation(), i);
        world.dropItemNaturally(block.getLocation(), e);
    }

    /**
     * Change an unnamed pad into a named, operational pad
     *
     * @param pad
     * 		Unnamed pad to be changed
     * @param name
     * 		The name of the pad
     */
    public void switchPad(net.h31ix.travelpad.api.UnnamedPad pad, java.lang.String name) {
        net.h31ix.travelpad.event.TravelPadNameEvent e = new net.h31ix.travelpad.event.TravelPadNameEvent(pad, name);
        plugin.getServer().getPluginManager().callEvent(e);
        if (!e.isCancelled()) {
            config.removePad(pad);
            config.addPad(new net.h31ix.travelpad.api.Pad(pad.getLocation(), pad.getOwner().getName(), e.getName()));
            update();
        }
    }

    /**
     * Remove a Pad
     *
     * @param pad
     * 		Pad to be deleted
     */
    public void deletePad(net.h31ix.travelpad.api.Pad pad) {
        net.h31ix.travelpad.event.TravelPadDeleteEvent d = new net.h31ix.travelpad.event.TravelPadDeleteEvent(pad);
        plugin.getServer().getPluginManager().callEvent(d);
        if (!d.isCancelled()) {
            update();
            config.removePad(pad);
            org.bukkit.entity.Player player = org.bukkit.Bukkit.getPlayer(pad.getOwner());
            if (player != null) {
                player.sendMessage((((org.bukkit.ChatColor.RED + l.delete_approve()) + " ") + org.bukkit.ChatColor.WHITE) + pad.getName());
            }
            org.bukkit.Location location = pad.getLocation();
            org.bukkit.World world = location.getWorld();
            org.bukkit.block.Block block = world.getBlockAt(location);
            block.setType(org.bukkit.Material.AIR);
            block.getRelative(org.bukkit.block.BlockFace.EAST).setType(org.bukkit.Material.AIR);
            block.getRelative(org.bukkit.block.BlockFace.SOUTH).setType(org.bukkit.Material.AIR);
            block.getRelative(org.bukkit.block.BlockFace.NORTH).setType(org.bukkit.Material.AIR);
            block.getRelative(org.bukkit.block.BlockFace.WEST).setType(org.bukkit.Material.AIR);
            org.bukkit.inventory.ItemStack i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.OBSIDIAN, 1);
            org.bukkit.inventory.ItemStack e = new org.bukkit.inventory.ItemStack(org.bukkit.Material.BRICK, 4);
            world.dropItemNaturally(block.getLocation(), i);
            world.dropItemNaturally(block.getLocation(), e);
            update();
        }
    }

    /**
     * Check if a name is already in use
     *
     * @param name
     * 		Name to be checked
     */
    public boolean nameIsValid(java.lang.String name) {
        update();
        for (net.h31ix.travelpad.api.Pad pad : padList) {
            if (pad.getName().equalsIgnoreCase(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get a pad by it's name
     *
     * @param name
     * 		Pad's name to be found
     * @return Pad if found, null if no pad by that name
     */
    public net.h31ix.travelpad.api.Pad getPad(java.lang.String name) {
        update();
        for (net.h31ix.travelpad.api.Pad pad : padList) {
            if (pad.getName().equalsIgnoreCase(name)) {
                return pad;
            }
        }
        return null;
    }

    /**
     * Get a pad by it's location
     *
     * @param location
     * 		Pad's location to be found
     * @return Pad if found, null if no pad at that location
     */
    public net.h31ix.travelpad.api.Pad getPadAt(org.bukkit.Location location) {
        update();
        for (net.h31ix.travelpad.api.Pad pad : padList) {
            int x = ((int) (pad.getLocation().getX()));
            int y = ((int) (pad.getLocation().getY()));
            int z = ((int) (pad.getLocation().getZ()));
            int xx = ((int) (location.getX()));
            int yy = ((int) (location.getY()));
            int zz = ((int) (location.getZ()));
            if (((x == xx) && (y == yy)) && (z == zz)) {
                return pad;
            }
        }
        return null;
    }

    /**
     * Get an Unnamed Pad by it's location
     *
     * @param location
     * 		Unnamed Pad's location to be found
     * @return Unnamed Pad if found, null if no pad by that name
     */
    public net.h31ix.travelpad.api.UnnamedPad getUnnamedPadAt(org.bukkit.Location location) {
        update();
        for (net.h31ix.travelpad.api.UnnamedPad pad : unvList) {
            int x = ((int) (pad.getLocation().getX()));
            int y = ((int) (pad.getLocation().getY()));
            int z = ((int) (pad.getLocation().getZ()));
            int xx = ((int) (location.getX()));
            int yy = ((int) (location.getY()));
            int zz = ((int) (location.getZ()));
            if (((x == xx) && (y == yy)) && (z == zz)) {
                return pad;
            }
        }
        return null;
    }

    /**
     * Get all the pads that a player owns
     *
     * @param player
     * 		Player to search for
     * @return Set of pads that the player owns, null if they have none.
     */
    public java.util.List<net.h31ix.travelpad.api.Pad> getPadsFrom(org.bukkit.entity.Player player) {
        update();
        java.util.List<net.h31ix.travelpad.api.Pad> list = new java.util.ArrayList<net.h31ix.travelpad.api.Pad>();
        for (net.h31ix.travelpad.api.Pad pad : padList) {
            if (pad.getOwner().equalsIgnoreCase(player.getName())) {
                list.add(pad);
            }
        }
        return list;
    }

    /**
     * Get all the unnamed pads that a player owns
     *
     * @param player
     * 		Player to search for
     * @return Set of unnamed pads that the player owns, null if they have none.
     */
    public java.util.List<net.h31ix.travelpad.api.UnnamedPad> getUnnamedPadsFrom(org.bukkit.entity.Player player) {
        update();
        java.util.List<net.h31ix.travelpad.api.UnnamedPad> list = new java.util.ArrayList<net.h31ix.travelpad.api.UnnamedPad>();
        for (net.h31ix.travelpad.api.UnnamedPad pad : unvList) {
            if (pad.getOwner() == player) {
                list.add(pad);
            }
        }
        return list;
    }

    /**
     * Get all registered pads
     *
     * @return Set of pads that exists
     */
    public java.util.List<net.h31ix.travelpad.api.Pad> getPads() {
        update();
        return padList;
    }

    /**
     * Get all unregistered pads
     *
     * @return Set of pads that are awaiting naming
     */
    public java.util.List<net.h31ix.travelpad.api.UnnamedPad> getUnnamedPads() {
        update();
        return unvList;
    }
}