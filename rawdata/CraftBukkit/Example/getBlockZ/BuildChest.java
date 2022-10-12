package com.norcode.bukkit.buildinabox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.norcode.bukkit.schematica.Clipboard;
import com.norcode.bukkit.schematica.ClipboardBlock;
import com.norcode.bukkit.schematica.MaterialID;
import net.minecraft.server.v1_5_R3.NBTTagCompound;
import net.minecraft.server.v1_5_R3.Packet61WorldEvent;
import org.bukkit.block.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Directional;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BlockVector;

public class BuildChest {
    BuildInABox plugin;
    private boolean previewing = false;
    private boolean building = false;
    private BuildingPlan plan;
    private LockingTask lockingTask = null;
    private BuildManager.BuildTask buildTask = null;
    private ChestData data;
    private long lastClicked = -1;
    private Action lastClickType = null;

    private static HashSet<Integer> silentBlocks = new HashSet<Integer>();
    static {
        silentBlocks.add(Material.GLASS.getId());
        silentBlocks.add(Material.THIN_GLASS.getId());
        silentBlocks.add(Material.REDSTONE_LAMP_OFF.getId());
        silentBlocks.add(Material.REDSTONE_LAMP_ON.getId());
        silentBlocks.add(Material.GLOWSTONE.getId());
        silentBlocks.add(Material.ICE.getId());
    }

    public BuildChest(ChestData data) {
        this.plugin = BuildInABox.getInstance();
        this.data = data;
        this.plan = BuildInABox.getInstance().getDataStore()
                .getBuildingPlan(data.getPlanName());
    }

    public ChestData getData() {
        return data;
    }
    public int getId() {
        return data.getId();
    }

    public boolean isLocking() {
        return lockingTask != null;
    }

    public LockingTask getLockingTask() {
        return lockingTask;
    }

    public boolean isLocked() {
        return data.getLockedBy() != null;
    }

    public String getLockedBy() {
        return data.getLockedBy();
    }

    public Location getLocation() {
        return data.getLocation();
    }

    public BuildingPlan getPlan() {
        return plan;
    }

    public void endPreview(final Player player) {
        if (!previewing) return;
        final World world = player.getWorld();
        Clipboard clipboard = plan.getRotatedClipboard(getDirectional().getFacing());
        Block b = getBlock();
        BlockVector o = clipboard.getOffset();
        clipboard.setOrigin(new BlockVector(b.getX() + o.getBlockX(), b.getY() + o.getBlockY(), b.getZ() + o.getBlockZ()));
        buildTask = new BuildManager.BuildTask(clipboard, clipboard.getPasteQueue(false, null), 150) {
            @Override
            public void processBlock(BlockVector clipboardPoint) {
                Location loc = clipboard.getWorldLocationFor(clipboardPoint, world);
                player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
            }

            @Override
            public void onComplete() {
                previewing = false;
                getBlock().removeMetadata("buildInABox", plugin);
                getBlock().setTypeIdAndData(0, (byte) 0, false);
                getBlock().getWorld().dropItem(getBlock().getLocation().add(0.5,0.5,0.5), data.toItemStack());
                data.setLocation(null);
                data.setLastActivity(System.currentTimeMillis());
                plugin.getDataStore().saveChest(data);
                buildTask = null;
            }
        };
        plugin.getBuildManager().scheduleTask(buildTask);
    }

    public boolean isPreviewing() {
        return previewing;
    }

    public Directional getDirectional() {
        return (Directional) Material.getMaterial(plugin.cfg.getChestBlockId()).getNewData(getBlock().getData());
    }

    public Block getBlock() {
        if (data.getLocation() != null) {
            try {
                return data.getLocation().getBlock();
            } catch (NullPointerException ex) {
                return null;
            }
        }
        return null;
    }

    public void preview(final Player player) {
        final World world = player.getWorld();
        final long previewDuration = (plugin.cfg.getPreviewDuration() * 20)/1000; // millis to ticks.
        final boolean checkBuildPermissions = plugin.cfg.isBuildPermissionCheckEnabled();

        Clipboard clipboard = plan.getRotatedClipboard(getDirectional().getFacing());
        Block b = getBlock();
        BlockVector o = clipboard.getOffset();
        clipboard.setOrigin(new BlockVector(b.getX() + o.getBlockX(), b.getY() + o.getBlockY(), b.getZ() + o.getBlockZ()));
        previewing = true;
        buildTask = new BuildManager.BuildTask(clipboard, clipboard.getPasteQueue(false, null), 250) {
            boolean cancelled = false;
            @Override
            public void processBlock(BlockVector clipboardPoint) {
                ClipboardBlock block = clipboard.getBlock(clipboardPoint);
                Location loc = clipboard.getWorldLocationFor(clipboardPoint, world);
                if (checkBuildPermissions) {
                    BlockCanBuildEvent canBuildEvent = new BlockCanBuildEvent(loc.getBlock(), block.getType(), true);
                    plugin.getServer().getPluginManager().callEvent(canBuildEvent);
                    if (!canBuildEvent.isBuildable()) {
                        cancelled = true;
                        return;
                    }
                    if (player.isOnline()) {
                        BuildInABox.getInstance().exemptPlayer(player);
                        FakeBlockPlaceEvent event = new FakeBlockPlaceEvent(loc, player);
                        plugin.getServer().getPluginManager().callEvent(event);
                        BuildInABox.getInstance().unexemptPlayer(player);
                        if (event.wasCancelled() || !event.canBuild()) {
                            cancelled = true;
                            return;
                        }
                    }
                }
                player.sendBlockChange(loc, block.getType(), block.getData());

            }

            @Override
            public void onComplete() {
                if (cancelled) {
                    player.sendMessage(BuildInABox.getErrorMsg("building-wont-fit",
                            plan.getDisplayName()));
                } else {
                    player.sendMessage(getDescription());
                }
                buildTask = null;
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    public void run() {
                        endPreview(player);
                    }
                }, cancelled ? 1 : previewDuration);
            }
        };
        plugin.getBuildManager().scheduleTask(buildTask);
    }

    public Set<Chunk> protectBlocks(Clipboard clipboard) {
        HashSet<Chunk> loadedChunks = new HashSet<Chunk>();
        Block b = getBlock();
        if (clipboard == null) {
            BlockFace dir = getDirectional().getFacing();
            clipboard = plan.getRotatedClipboard(dir);
        }
        BlockVector offset = clipboard.getOffset().clone().toBlockVector();
        clipboard.setOrigin(new BlockVector(b.getX() + offset.getBlockX(), b.getY() + offset.getBlockY(), b.getZ() + offset.getBlockZ()));
        Location loc;
        for (int x=0;x<clipboard.getSize().getBlockX();x++) {
            for (int y = 0;y<clipboard.getSize().getBlockY();y++) {
                for (int z=0;z<clipboard.getSize().getBlockZ();z++) {
                    if (clipboard.getBlock(x,y,z).getType() > 0) {
                        loc = clipboard.getWorldLocationFor(new BlockVector(x,y,z), b.getWorld());
                        if (!loc.getChunk().isLoaded()) {
                            loadedChunks.add(loc.getChunk());
                        }
                        loc.getChunk().load();
                        getBlock().getWorld().getBlockAt(loc).setMetadata("biab-block", new FixedMetadataValue(plugin, this));
                    }
                }
            }
        }
        return loadedChunks;
    }

    public void build(final Player player) {
        final World world = player.getWorld();
        Clipboard clipboard = plan.getRotatedClipboard(getDirectional().getFacing());
        Block b = getBlock();
        BlockVector offset = clipboard.getOffset();
        clipboard.setOrigin(new BlockVector(b.getX() + offset.getBlockX(), b.getY() + offset.getBlockY(), b.getZ() + offset.getBlockZ()));
        buildTask = new BuildManager.BuildTask(clipboard, clipboard.getPasteQueue(false, null), 250) {
            @Override
            public void processBlock(BlockVector clipboardPoint) {
                Location loc = clipboard.getWorldLocationFor(clipboardPoint, world);
                player.sendBlockChange(loc, loc.getBlock().getType(), loc.getBlock().getData());
            }
            @Override
            public void onComplete() {
                previewing = false;
                buildTask = null;
                startBuild(player);

            }
        };
        plugin.getBuildManager().scheduleTask(buildTask);
    }

    private void startBuild(final Player player) {
        double cost = plugin.cfg.getBuildCost();
        if (cost > 0 && BuildInABox.hasEconomy()) {
            if (!BuildInABox.getEconomy().withdrawPlayer(player.getName(), cost).transactionSuccess()) {
                player.sendMessage(BuildInABox.getErrorMsg("insufficient-funds", BuildInABox.getEconomy().format(cost)));
                return;
            }
        }
        previewing = false;
        building = true;
        player.sendMessage(BuildInABox.getNormalMsg("building", plan.getDisplayName()));
        final World world = player.getWorld();
        data.setLocation(getLocation());
        data.setLastActivity(System.currentTimeMillis());
        plugin.getDataStore().saveChest(data);
        final List<Player> nearby = new ArrayList<Player>();
        for (Player p: world.getPlayers()) {
            if (p.getLocation().distance(getLocation()) < 16) {
                nearby.add(p);
            }
        }
        Clipboard clipboard = plan.getRotatedClipboard(BlockFace.NORTH); // NORTH = no rotation
        // this is to assign the saved tileEntity data into the clipboard before copying. our location data is stored
        // in the NORTH orientation.
        if (data.getTileEntities() != null) {
            NBTTagCompound tag;
            for (Entry<BlockVector, NBTTagCompound> entry: data.getTileEntities().entrySet()) {
                tag = (NBTTagCompound) entry.getValue().clone();
                tag.setInt("x", entry.getKey().getBlockX());
                tag.setInt("y", entry.getKey().getBlockY());
                tag.setInt("z", entry.getKey().getBlockZ());
                clipboard.getBlock(entry.getKey()).setTag(tag);
            }
        }

        clipboard.rotate2D(BuildInABox.getRotationDegrees(BlockFace.NORTH, getDirectional().getFacing()));
        Block b = getBlock();
        BlockVector off = clipboard.getOffset();
        clipboard.setOrigin(new BlockVector(b.getX() + off.getBlockX(), b.getY() + off.getBlockY(), b.getZ() + off.getBlockZ()));
        List<BlockVector> vectorQueue = clipboard.getPasteQueue(plugin.cfg.isBuildAnimationShuffled(), null);
        final BIABConfig.AnimationStyle animationStyle = plugin.cfg.getBuildAnimationStyle();
        buildTask = new BuildManager.BuildTask(clipboard, vectorQueue, plugin.cfg.getBuildBlocksPerTick()) {

            private void saveReplacedBlock(BlockVector c, Location wc) {
                // save blocks below ground level for restoration.
                plugin.debug("Saving replaced block @ " + c);
                getData().getReplacedBlocks().put(c,
                        new ClipboardBlock(wc.getBlock().getTypeId(), wc.getBlock().getData()));
            }

            @Override
            public void processBlock(BlockVector clipboardPoint) {
                Location loc = clipboard.getWorldLocationFor(clipboardPoint, world);
                if (loc.equals(getBlock().getLocation())) return; // DONT REPLACE THE ENDERCHEST
                ClipboardBlock cb = clipboard.getBlock(clipboardPoint);
                if (clipboardPoint.getY() < -clipboard.getOffset().getBlockY()) {
                    saveReplacedBlock(clipboardPoint, loc);
                }
                if (animationStyle != BIABConfig.AnimationStyle.NONE && !silentBlocks.contains(cb.getType())) {
                    playBuildAnimation(loc, cb.getType(), cb.getData(), nearby, animationStyle);
                }
                this.clipboard.copyBlockToWorld(clipboard.getBlock(clipboardPoint), loc);
                if (plugin.cfg.isBuildingProtectionEnabled() && plugin.cfg.isPickupEnabled()) {
                    loc.getBlock().setMetadata("biab-block", new FixedMetadataValue(plugin, BuildChest.this));
                }
            }

            @Override
            public void onComplete() {
                building = false;
                buildTask = null;
                data.clearTileEntities();
                if (!plugin.cfg.isPickupEnabled()) {
                    plugin.getDataStore().deleteChest(data.getId());
                    getBlock().removeMetadata("buildInABox", plugin);
                }
                player.sendMessage(BuildInABox.getSuccessMsg("build-complete"));
                int fireworksLevel = plugin.cfg.getBuildFireworks();
                if (fireworksLevel > 0) {
                    //TODO: launchFireworks(fireworksLevel);
                }
                building = false;
            }
        };
        plugin.getBuildManager().scheduleTask(buildTask);
    }

    private void playBuildAnimation(Location loc, int type, byte data, List<Player> nearby, BIABConfig.AnimationStyle style) {
        Packet61WorldEvent packet = null;
        switch (style) {
        case BREAK:
            packet = new Packet61WorldEvent(2001, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getBlock().getTypeId(), false);
            break;
        case SMOKE:
            packet = new Packet61WorldEvent(2000, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), 4, false);
            break;
        }
        for (Player p: nearby) {
            if (p.isOnline()) {
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        }

    }

    public void unlock(Player player) {
        long total = plugin.cfg.getUnlockTime();
        if (data.getLockedBy().equals(player.getName())) {
            total = plugin.cfg.getLockTime();
        }
        double cost = plugin.cfg.getUnlockCost();
        if (cost > 0 && BuildInABox.hasEconomy()) {
            if (!BuildInABox.getEconomy().withdrawPlayer(player.getName(), cost).transactionSuccess()) {
                player.sendMessage(BuildInABox.getErrorMsg("insufficient-funds", BuildInABox.getEconomy().format(cost)));
                return;
            }
        }
        lockingTask = new UnlockingTask(player.getName(), total);
        lockingTask.run();
    }

    public void lock(Player player) {
        long total = plugin.cfg.getLockTime();
        double cost = plugin.cfg.getLockCost();
        if (cost > 0 && BuildInABox.hasEconomy()) {
            if (!BuildInABox.getEconomy().withdrawPlayer(player.getName(), cost).transactionSuccess()) {
                player.sendMessage(BuildInABox.getErrorMsg("insufficient-funds", BuildInABox.getEconomy().format(cost)));
                return;
            }
        }
        lockingTask = new LockingTask(player.getName(), total);
        lockingTask.run();
    }

    public void pickup(final Player player) {
        double cost = plugin.cfg.getPickupCost();
        if (cost > 0 && BuildInABox.hasEconomy()) {
            if (!BuildInABox.getEconomy().withdrawPlayer(player.getName(), cost).transactionSuccess()) {
                player.sendMessage(BuildInABox.getErrorMsg("insufficient-funds", BuildInABox.getEconomy().format(cost)));
                return;
            }
        }

        final List<Player> nearby = new ArrayList<Player>();
        for (Player p : player.getWorld().getPlayers()) {
            nearby.add(p);
        }
        if (isLocked()) {
            player.sendMessage(BuildInABox.getErrorMsg("building-is-locked",
                    getPlan().getDisplayName(), getLockedBy()));
        }

        player.sendMessage(BuildInABox.getNormalMsg("removing", this.getPlan().getDisplayName()));
        building = true;
        data.clearTileEntities();
        Block b = getBlock();
        final World world = player.getWorld();
        final BIABConfig.AnimationStyle animationStyle = plugin.cfg.getPickupAnimationStyle();
        Clipboard clipboard = plan.getRotatedClipboard(getDirectional().getFacing());
        clipboard.setOrigin(new BlockVector(b.getX() + clipboard.getOffset().getBlockX(), b.getY() + clipboard.getOffset().getBlockY(), b.getZ() + clipboard.getOffset().getBlockZ()));
        buildTask = new BuildManager.BuildTask(clipboard, clipboard.getCutQueue(plugin.cfg.isPickupAnimationShuffled(), null), plugin.cfg.getPickupBlocksPerTick()) {

            @Override
            public void processBlock(BlockVector clipboardPoint) {
                Location loc = clipboard.getWorldLocationFor(clipboardPoint, world);
                if (loc.equals(getBlock().getLocation())) {
                    return;
                }

                BlockState state = loc.getBlock().getState();
                if (MaterialID.isTileEntityBlock(state.getTypeId())) {
                    clipboard.copyBlockFromWorld(loc.getBlock(), clipboardPoint);
                }

                if (state instanceof InventoryHolder) {
                    InventoryHolder holder = (InventoryHolder) state;
                    Inventory inv = holder.getInventory();
                    if (holder instanceof Chest) {
                        inv = ((Chest) holder).getBlockInventory();
                    }
                    inv.clear();
                } else if (state instanceof Jukebox) {
                    ((Jukebox) state).setPlaying(null);
                }

                if (!animationStyle.equals(BIABConfig.AnimationStyle.NONE)) {
                    playBuildAnimation(loc, loc.getBlock().getTypeId(), loc.getBlock().getData(), nearby, animationStyle);
                }
                if (clipboardPoint.getBlockY() < -clipboard.getOffset().getBlockY()) {
                    if (data.getReplacedBlocks().containsKey(clipboardPoint)) {
                        ClipboardBlock replacement = data.getReplacedBlocks().get(clipboardPoint);
                        if (replacement != null) {
                            loc.getBlock().setTypeIdAndData(replacement.getType(), replacement.getData(), false);
                        }
                    } else {
                        loc.getBlock().setTypeIdAndData(0,(byte) 0, false);
                    }
                } else {
                    loc.getBlock().setTypeIdAndData(0, (byte) 0, false);
                }
                loc.getBlock().removeMetadata("biab-block", plugin);
                loc.getBlock().removeMetadata("buildInABox", plugin);
            }

            @Override
            public void onComplete() {
                ClipboardBlock cb;
                clipboard.rotate2D(BuildInABox.getRotationDegrees(getDirectional().getFacing(), BlockFace.NORTH));
                for (int x=0;x<clipboard.getSize().getBlockX();x++) {
                    for (int z=0;z<clipboard.getSize().getBlockZ();z++) {
                        for (int y=0;y<clipboard.getSize().getBlockY();y++) {
                            if (x == -clipboard.getOffset().getBlockX() && y == -clipboard.getOffset().getBlockY() && z == -clipboard.getOffset().getBlockZ()) {
                                continue;
                            }
                            cb = clipboard.getBlock(x,y,z);
                            if (cb.hasTag()) {
                                data.setTileEntity(new BlockVector(x,y,z), (NBTTagCompound) cb.getTag().clone());
                            }
                        }
                    }
                }

                getBlock().setType(Material.AIR);
                getBlock().removeMetadata("buildInABox", plugin);
                getBlock().removeMetadata("biab-block", plugin);
                data.getLocation().getWorld().dropItem(new Location(data.getLocation().getWorld(), data.getLocation().getX() + 0.5, data.getLocation().getY() + 0.5, data.getLocation().getZ() + 0.5), data.toItemStack());
                data.setLocation(null);
                data.setLastActivity(System.currentTimeMillis());
                data.setReplacedBlocks(null);
                plugin.getDataStore().saveChest(data);
                int fireworksLevel = plugin.getConfig().getInt("pickup-animation.fireworks", 0);
                if (fireworksLevel > 0) {
                    //TODO: launchFireworks(fireworksLevel);
                }
                building = false;
                player.sendMessage(BuildInABox.getSuccessMsg("removal-complete"));
                buildTask = null;
            }
        };
        plugin.getBuildManager().scheduleTask(buildTask);
    }

    public class LockingTask implements Runnable {
        public boolean cancelled = false;
        public String lockingPlayer;
        long totalTime;
        long startTime;

        public LockingTask(String playerName, long totalTimeSeconds) {
            this.startTime = System.currentTimeMillis();
            this.totalTime = totalTimeSeconds * 1000;
            this.lockingPlayer = playerName;
        }

        protected String getCancelMessage() {
            return BuildInABox.getErrorMsg("lock-cancelled-self");
        }

        protected String getSuccessMessage() {
            return BuildInABox.getSuccessMsg("lock-success-self", getPlan()
                    .getName());
        }

        protected String getProgressMessage(int percentage) {
            return BuildInABox.getNormalMsg("lock-progress", getPlan()
                    .getName(), percentage);
        }

        protected String getLockedBy() {
            return lockingPlayer;
        }

        public void cancel() {
            Player player = plugin.getServer().getPlayer(lockingPlayer);
            cancelled = true;
            if (player.isOnline()) {
                player.sendMessage(getCancelMessage());
                data.setLockedBy(getLockedBy() == null ? lockingPlayer : null);
                data.setLastActivity(System.currentTimeMillis());
                lockingTask = null;
            }
        }

        public void run() {
            if (cancelled)
                return;
            Player player = plugin.getServer().getPlayer(lockingPlayer);
            if (!player.isOnline()) {
                cancel();
            } else {
                // check distance from chest;
                try {
                    double distance = player.getLocation().distance(
                            data.getLocation());
                    if (distance > plugin.getConfig().getDouble(
                            "max-locking-distance", 5)) {
                        cancel();
                        return;
                    }
                } catch (IllegalArgumentException ex) {
                    // Cross-world distance check
                    cancel();
                    return;
                }
                long elapsed = System.currentTimeMillis() - startTime;
                if (elapsed > totalTime)
                    elapsed = totalTime;
                int pct = (int) Math
                        .floor((elapsed / (double) totalTime) * 100);
                if (pct < 100) {
                    player.sendMessage(getProgressMessage(pct));
                    plugin.getServer().getScheduler()
                            .runTaskLater(plugin, this, 20);
                } else {
                    data.setLockedBy(getLockedBy());
                    data.setLastActivity(System.currentTimeMillis());
                    plugin.getDataStore().saveChest(data);
                    lockingTask = null;
                    player.sendMessage(getSuccessMessage());
                }
            }
        }
    }

    public class UnlockingTask extends LockingTask {
        public UnlockingTask(String playerName, long totalTime) {
            super(playerName, totalTime);
        }

        @Override
        public String getCancelMessage() {
            return BuildInABox.getErrorMsg("unlock-cancelled-self");
        }

        @Override
        public String getSuccessMessage() {
            return BuildInABox.getSuccessMsg("unlock-success-self", getPlan()
                    .getName());
        }

        @Override
        public String getProgressMessage(int percentage) {
            return BuildInABox.getNormalMsg("unlock-progress", getPlan()
                    .getName(), percentage);
        }

        @Override
        public String getLockedBy() {
            return null;
        }
    }

    public String[] getDescription() {
        List<String> desc = new ArrayList<String>(2);
        String header = ChatColor.GOLD + getPlan().getName();
        if (previewing || plugin.getConfig().getBoolean("allow-locking", true)) {
            header += " - "
                    + (previewing ? ChatColor.GREEN
                            + BuildInABox.getMsg("preview")
                            : (isLocked() ? ChatColor.RED
                                    + BuildInABox.getMsg("locked")
                                    + ChatColor.WHITE + " [" + ChatColor.GOLD
                                    + data.getLockedBy() + ChatColor.WHITE
                                    + "]" : ChatColor.GREEN
                                    + BuildInABox.getMsg("unlocked")));
        }
        desc.add(header);
        if (previewing) {
            desc.add(ChatColor.GOLD
                    + BuildInABox.getMsg("left-click-to-cancel")
                    + ChatColor.WHITE + " | " + ChatColor.GOLD
                    + BuildInABox.getMsg("right-click-to-confirm"));
        } else if (isLocked()) {
            desc.add(ChatColor.GOLD
                    + BuildInABox.getMsg("right-click-twice-to-unlock"));
        } else {
            String instructions = ChatColor.GOLD
                    + BuildInABox.getMsg("left-click-twice-to-pickup");
            if (plugin.getConfig().getBoolean("allow-locking", true)) {
                instructions += ChatColor.WHITE + " | " + ChatColor.GOLD
                        + BuildInABox.getMsg("right-click-twice-to-lock");
            }
            desc.add(instructions);
        }
        String[] sa = new String[desc.size()];
        return desc.toArray(sa);
    }

    public void updateActivity() {
        data.setLastActivity(System.currentTimeMillis());
        plugin.getDataStore().saveChest(data);
    }

    public boolean isBuilding() {
        return building;
    }

    public long getLastClicked() {
        return lastClicked;
    }

    public Action getLastClickType() {
        return lastClickType;
    }

    public void setLastClicked(long lastClicked) {
        this.lastClicked = lastClicked;
    }

    public void setLastClickType(Action lastClickType) {
        this.lastClickType = lastClickType;
    }

    public void unprotect() {
        Clipboard clipboard = getPlan().getRotatedClipboard(this.getDirectional().getFacing());
        Location loc;
        BlockVector offset = clipboard.getOffset();
        BlockVector origin = new BlockVector(getBlock().getX(), getBlock().getY(),
                getBlock().getZ());
        for (int x = 0; x < clipboard.getSize().getBlockX(); x++) {
            for (int y = 0; y < clipboard.getSize().getBlockY(); y++) {
                for (int z = 0; z < clipboard.getSize().getBlockZ(); z++) {
                    if (clipboard.getBlock(x, y, z).getType() > 0) {
                        BlockVector v = (BlockVector) origin.clone().add(offset);
                        loc = new Location(getBlock().getWorld(), v.getBlockX() + x, v.getBlockY() + y, v.getBlockZ() + z);
                        getBlock().getWorld().getBlockAt(loc).removeMetadata("biab-block", plugin);
                        getBlock().getWorld().getBlockAt(loc).removeMetadata("buildInABox", plugin);
                    }
                }
            }
        }
        getBlock().breakNaturally();
    }
}