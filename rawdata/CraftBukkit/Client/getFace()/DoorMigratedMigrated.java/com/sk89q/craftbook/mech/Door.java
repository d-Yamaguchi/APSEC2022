package com.sk89q.craftbook.mech;
import com.sk89q.craftbook.AbstractMechanic;
import com.sk89q.craftbook.AbstractMechanicFactory;
import com.sk89q.craftbook.InsufficientPermissionsException;
import com.sk89q.craftbook.InvalidMechanismException;
import com.sk89q.craftbook.LocalPlayer;
import com.sk89q.craftbook.MechanismsConfiguration;
import com.sk89q.craftbook.ProcessedMechanismException;
import com.sk89q.craftbook.SourcedBlockRedstoneEvent;
import com.sk89q.craftbook.bukkit.BukkitPlayer;
import com.sk89q.craftbook.bukkit.MechanismsPlugin;
import com.sk89q.craftbook.util.SignUtil;
import com.sk89q.worldedit.BlockWorldVector;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
/**
 * Door.
 *
 * @author turtle9598
 */
public class Door extends com.sk89q.craftbook.AbstractMechanic {
    public static class Factory extends com.sk89q.craftbook.AbstractMechanicFactory<com.sk89q.craftbook.mech.Door> {
        public Factory(com.sk89q.craftbook.bukkit.MechanismsPlugin plugin) {
            this.plugin = plugin;
        }

        private com.sk89q.craftbook.bukkit.MechanismsPlugin plugin;

        /**
         * Detect the mechanic at a placed sign.
         *
         * @throws ProcessedMechanismException
         * 		
         */
        @java.lang.Override
        public com.sk89q.craftbook.mech.Door detect(com.sk89q.worldedit.BlockWorldVector pt, com.sk89q.craftbook.LocalPlayer player, org.bukkit.block.Sign sign) throws com.sk89q.craftbook.InvalidMechanismException, com.sk89q.craftbook.ProcessedMechanismException {
            if (sign.getLine(1).equalsIgnoreCase("[Door Down]")) {
                if (!player.hasPermission("craftbook.mech.door")) {
                    throw new com.sk89q.craftbook.InsufficientPermissionsException();
                }
                sign.setLine(1, "[Door Down]");
                player.print("Door created.");
            } else if (sign.getLine(1).equalsIgnoreCase("[Door Up]")) {
                if (!player.hasPermission("craftbook.mech.door")) {
                    throw new com.sk89q.craftbook.InsufficientPermissionsException();
                }
                sign.setLine(1, "[Door Up]");
                player.print("Door created.");
            } else if (sign.getLine(1).equalsIgnoreCase("[Door]")) {
                if (!player.hasPermission("craftbook.mech.door")) {
                    throw new com.sk89q.craftbook.InsufficientPermissionsException();
                }
                sign.setLine(1, "[Door]");
                player.print("Door created.");
            } else {
                return null;
            }
            throw new com.sk89q.craftbook.ProcessedMechanismException();
        }

        /**
         * Explore around the trigger to find a Door; throw if things look funny.
         *
         * @param pt
         * 		the trigger (should be a signpost)
         * @return a Door if we could make a valid one, or null if this looked
        nothing like a door.
         * @throws InvalidMechanismException
         * 		if the area looked like it was intended to be a door, but
         * 		it failed.
         */
        @java.lang.Override
        public com.sk89q.craftbook.mech.Door detect(com.sk89q.worldedit.BlockWorldVector pt) throws com.sk89q.craftbook.InvalidMechanismException {
            org.bukkit.block.Block block = com.sk89q.worldedit.bukkit.BukkitUtil.toBlock(pt);
            // check if this looks at all like something we're interested in first
            if (block.getTypeId() != com.sk89q.worldedit.blocks.BlockID.SIGN_POST)
                return null;

            if ((!((org.bukkit.block.Sign) (block.getState())).getLine(1).contains("Door")) || ((org.bukkit.block.Sign) (block.getState())).getLine(1).equalsIgnoreCase("[Door]"))
                return null;

            // okay, now we can start doing exploration of surrounding blocks
            // and if something goes wrong in here then we throw fits.
            return new com.sk89q.craftbook.mech.Door(block, plugin);
        }
    }

    /**
     *
     *
     * @param trigger
     * 		if you didn't already check if this is a signpost with appropriate
     * 		text, you're going on Santa's naughty list.
     * @param plugin
     * 		
     * @throws InvalidMechanismException
     * 		
     */
    private Door(org.bukkit.block.Block trigger, com.sk89q.craftbook.bukkit.MechanismsPlugin plugin) throws com.sk89q.craftbook.InvalidMechanismException {
        super();
        // check and set some properties
        if (!com.sk89q.craftbook.util.SignUtil.isCardinal(trigger))
            throw new com.sk89q.craftbook.mech.Door.InvalidDirectionException();

        this.trigger = trigger;
        this.plugin = plugin;
        this.settings = plugin.getLocalConfiguration().doorSettings;
        org.bukkit.Material mat;
        findBase : {
            if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Up]")) {
                proximalBaseCenter = trigger.getRelative(org.bukkit.block.BlockFace.UP);
            } else if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Down]")) {
                proximalBaseCenter = trigger.getRelative(org.bukkit.block.BlockFace.DOWN);
            }
            mat = proximalBaseCenter.getType();
            if (settings.canUseBlock(mat)) {
                if ((proximalBaseCenter.getRelative(com.sk89q.craftbook.util.SignUtil.getLeft(trigger)).getType() == mat) && (proximalBaseCenter.getRelative(com.sk89q.craftbook.util.SignUtil.getRight(trigger)).getType() == mat))
                    break findBase;

                throw new com.sk89q.craftbook.mech.Door.InvalidConstructionException("Blocks adjacent to the door block must be of the same type.");
            } else {
                throw new com.sk89q.craftbook.mech.Door.UnacceptableMaterialException();
            }
        }
        // Find the other side
        if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Up]")) {
            otherSide = trigger.getRelative(org.bukkit.block.BlockFace.UP);
        } else if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Down]")) {
            otherSide = trigger.getRelative(org.bukkit.block.BlockFace.DOWN);
        }
        for (int i = 0; i <= settings.maxLength; i++) {
            // about the loop index:
            // i = 0 is the first block after the proximal base
            // since we're allowed to have settings.maxLength toggle blocks,
            // i = settings.maxLength is actually the farthest place we're
            // allowed to find the distal signpost
            if (otherSide.getType() == org.bukkit.Material.SIGN_POST) {
                java.lang.String otherSignText = ((org.bukkit.block.Sign) (otherSide.getState())).getLines()[1];
                if ("[Door Down]".equalsIgnoreCase(otherSignText))
                    break;

                if ("[Door Up]".equalsIgnoreCase(otherSignText))
                    break;

                if ("[Door]".equalsIgnoreCase(otherSignText))
                    break;

            }
            if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Up]")) {
                otherSide = otherSide.getRelative(org.bukkit.block.BlockFace.UP);
            } else if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Down]")) {
                otherSide = otherSide.getRelative(org.bukkit.block.BlockFace.DOWN);
            }
        }
        if (otherSide.getType() != org.bukkit.Material.SIGN_POST)
            throw new com.sk89q.craftbook.mech.Door.InvalidConstructionException("Door sign required on other side (or it was too far away).");

        // Check the other side's base blocks for matching type
        org.bukkit.block.Block distalBaseCenter = null;
        if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Up]")) {
            distalBaseCenter = otherSide.getRelative(org.bukkit.block.BlockFace.DOWN);
        } else if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Down]")) {
            distalBaseCenter = otherSide.getRelative(org.bukkit.block.BlockFace.UP);
        }
        if (((distalBaseCenter.getType() != mat) || (distalBaseCenter.getRelative(com.sk89q.craftbook.util.SignUtil.getLeft(trigger)).getType() != mat)) || (distalBaseCenter.getRelative(com.sk89q.craftbook.util.SignUtil.getRight(trigger)).getType() != mat))
            throw new com.sk89q.craftbook.mech.Door.InvalidConstructionException("The other side must be made with the same blocks.");

        // Select the togglable region
        toggle = new com.sk89q.worldedit.regions.CuboidRegion(com.sk89q.worldedit.bukkit.BukkitUtil.toVector(proximalBaseCenter), com.sk89q.worldedit.bukkit.BukkitUtil.toVector(distalBaseCenter));
        toggle.expand(com.sk89q.worldedit.bukkit.BukkitUtil.toVector(com.sk89q.craftbook.util.SignUtil.getLeft(trigger)));
        toggle.expand(com.sk89q.worldedit.bukkit.BukkitUtil.toVector(com.sk89q.craftbook.util.SignUtil.getRight(trigger)));
        toggle.contract(com.sk89q.worldedit.bukkit.BukkitUtil.toVector(org.bukkit.block.BlockFace.UP));
        toggle.contract(com.sk89q.worldedit.bukkit.BukkitUtil.toVector(org.bukkit.block.BlockFace.DOWN));
        // Onward to victory!
    }

    @java.lang.Override
    public void onRightClick(org.bukkit.event.player.PlayerInteractEvent event) {
        if (!plugin.getLocalConfiguration().doorSettings.enable)
            return;

        if (!com.sk89q.worldedit.bukkit.BukkitUtil.toWorldVector(event.getClickedBlock()).equals(com.sk89q.worldedit.bukkit.BukkitUtil.toWorldVector(trigger)))
            return;

        com.sk89q.craftbook.bukkit.BukkitPlayer player = new com.sk89q.craftbook.bukkit.BukkitPlayer(plugin, event.getPlayer());
        if (!player.hasPermission("craftbook.mech.door.use")) {
            player.printError("You don't have permission to use doors.");
            return;
        }
        flipState();
    }

    @java.lang.Override
    public void onBlockRedstoneChange(com.sk89q.craftbook.SourcedBlockRedstoneEvent event) {
        if (!plugin.getLocalConfiguration().doorSettings.enableRedstone)
            return;

        if (!com.sk89q.worldedit.bukkit.BukkitUtil.toWorldVector(event.getBlock()).equals(com.sk89q.worldedit.bukkit.BukkitUtil.toWorldVector(trigger)))
            return;

        if (event.getNewCurrent() == event.getOldCurrent())
            return;

        if (event.getNewCurrent() == 0) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new com.sk89q.craftbook.mech.Door.ToggleRegionOpen(), 2);
        } else {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new com.sk89q.craftbook.mech.Door.ToggleRegionClosed(), 2);
        }
    }

    private void flipState() {
        // this is kinda funky, but we only check one position
        // to see if the door is open and/or closable.
        // efficiency choice :/
        org.bukkit.block.Block hinge = null;
        if (((org.bukkit.block.Sign) (trigger.getState())).getLine(1).equalsIgnoreCase("[Door Up]")) {
            hinge = proximalBaseCenter.getRelative(org.bukkit.block.BlockFace.UP);
        } else {
            hinge = proximalBaseCenter.getRelative(org.bukkit.block.BlockFace.DOWN);
        }
        // aaand we also only check if it's something we can
        // smosh or not when deciding if we're open or closed.
        // there are no errors reported upon weird blocks like
        // obsidian in the middle of a wooden door, just weird
        // results.
        if (com.sk89q.craftbook.mech.Door.canPassThrough(hinge.getTypeId())) {
            new com.sk89q.craftbook.mech.Door.ToggleRegionClosed().run();
        } else {
            new com.sk89q.craftbook.mech.Door.ToggleRegionOpen().run();
        }
    }

    private class ToggleRegionOpen implements java.lang.Runnable {
        public void run() {
            for (com.sk89q.worldedit.BlockVector bv : toggle) {
                // this package specification is something that needs to be fixed in the overall scheme
                org.bukkit.block.Block b = trigger.getWorld().getBlockAt(bv.getBlockX(), bv.getBlockY(), bv.getBlockZ());
                if ((b.getType() == getDoorMaterial()) || com.sk89q.craftbook.mech.Door.canPassThrough(b.getTypeId()))
                    b.setType(org.bukkit.Material.AIR);

            }
        }
    }

    private class ToggleRegionClosed implements java.lang.Runnable {
        public void run() {
            for (com.sk89q.worldedit.BlockVector bv : toggle) {
                // this package specification is something that needs to be fixed in the overall scheme
                org.bukkit.block.Block b = trigger.getWorld().getBlockAt(bv.getBlockX(), bv.getBlockY(), bv.getBlockZ());
                if (com.sk89q.craftbook.mech.Door.canPassThrough(b.getTypeId()))
                    b.setType(getDoorMaterial());

            }
        }
    }

    @java.lang.Override
    public void unload() {
        /* we're not persistent */
    }

    @java.lang.Override
    public boolean isActive() {
        /* we're not persistent */
        return false;
    }

    private org.bukkit.Material getDoorMaterial() {
        return proximalBaseCenter.getType();
    }

    private com.sk89q.craftbook.bukkit.MechanismsPlugin plugin;

    private MechanismsConfiguration.DoorSettings settings;

    /**
     * The signpost we came from.
     */
    private org.bukkit.block.Block trigger;

    /**
     * The block that determines door type.
     */
    private org.bukkit.block.Block proximalBaseCenter;

    /**
     * The signpost on the other end.
     */
    private org.bukkit.block.Block otherSide;

    /**
     * The rectangle that we toggle.
     */
    private com.sk89q.worldedit.regions.CuboidRegion toggle;

    // we don't store anything about the blocks on the ends because
    // we never poke them; just check that they're sane when we're building
    // the door.  if this were a PersistentMechanic, those six blocks
    // would be considered defining blocks, though.
    /**
     *
     *
     * @return whether the door can pass through this BlockType (and displace it
    if needed).
     */
    private static boolean canPassThrough(int t) {
        if (((((((t != com.sk89q.worldedit.blocks.BlockID.WATER) && (t != com.sk89q.worldedit.blocks.BlockID.STATIONARY_WATER)) && (t != com.sk89q.worldedit.blocks.BlockID.LAVA)) && (t != com.sk89q.worldedit.blocks.BlockID.STATIONARY_LAVA)) && (t != com.sk89q.worldedit.blocks.BlockID.FENCE)) && (t != com.sk89q.worldedit.blocks.BlockID.SNOW)) && (t != 0)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Thrown when the sign is an invalid direction.
     */
    private static class InvalidDirectionException extends com.sk89q.craftbook.InvalidMechanismException {
        private static final long serialVersionUID = -3183606604247616362L;
    }

    /**
     * Thrown when the door type is unacceptable.
     */
    private static class UnacceptableMaterialException extends com.sk89q.craftbook.InvalidMechanismException {
        private static final long serialVersionUID = 8340723004466483212L;
    }

    /**
     * Thrown when the door type is not constructed correctly.
     */
    private static class InvalidConstructionException extends com.sk89q.craftbook.InvalidMechanismException {
        private static final long serialVersionUID = 4943494589521864491L;

        /**
         * Construct the object.
         *
         * @param msg
         * 		
         */
        public InvalidConstructionException(java.lang.String msg) {
            super(msg);
        }
    }
}