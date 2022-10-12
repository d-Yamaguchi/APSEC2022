package org.bukkit.craftbukkit.block;
import net.minecraft.server.BiomeBase;
import net.minecraft.server.BlockRedstoneWire;
import net.minecraft.server.Direction;
import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.TileEntitySkull;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
public class CraftBlock implements org.bukkit.block.Block {
    private final org.bukkit.craftbukkit.CraftChunk chunk;

    private final int x;

    private final int y;

    private final int z;

    private static final org.bukkit.block.Biome[] BIOME_MAPPING;

    private static final net.minecraft.server.BiomeBase[] BIOMEBASE_MAPPING;

    public CraftBlock(org.bukkit.craftbukkit.CraftChunk chunk, int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunk = chunk;
    }

    public org.bukkit.World getWorld() {
        return chunk.getWorld();
    }

    public org.bukkit.Location getLocation() {
        return new org.bukkit.Location(getWorld(), x, y, z);
    }

    public org.bukkit.Location getLocation(org.bukkit.Location loc) {
        if (loc != null) {
            loc.setWorld(getWorld());
            loc.setX(x);
            loc.setY(y);
            loc.setZ(z);
            loc.setYaw(0);
            loc.setPitch(0);
        }
        return loc;
    }

    public org.bukkit.util.BlockVector getVector() {
        return new org.bukkit.util.BlockVector(x, y, z);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public org.bukkit.Chunk getChunk() {
        return chunk;
    }

    public void setData(final byte data) {
        chunk.getHandle().world.setData(x, y, z, data, 3);
    }

    public void setData(final byte data, boolean applyPhysics) {
        if (applyPhysics) {
            chunk.getHandle().world.setData(x, y, z, data, 3);
        } else {
            chunk.getHandle().world.setData(x, y, z, data, 2);
        }
    }

    public byte getData() {
        return ((byte) (chunk.getHandle().getData(this.x & 0xf, this.y & 0xff, this.z & 0xf)));
    }

    public void setType(final org.bukkit.Material type) {
        setTypeId(type.getId());
    }

    public boolean setTypeId(final int type) {
        return chunk.getHandle().world.setTypeIdAndData(x, y, z, type, getData(), 3);
    }

    public boolean setTypeId(final int type, final boolean applyPhysics) {
        if (applyPhysics) {
            return setTypeId(type);
        } else {
            return chunk.getHandle().world.setTypeIdAndData(x, y, z, type, getData(), 2);
        }
    }

    public boolean setTypeIdAndData(final int type, final byte data, final boolean applyPhysics) {
        if (applyPhysics) {
            return chunk.getHandle().world.setTypeIdAndData(x, y, z, type, data, 3);
        } else {
            boolean success = chunk.getHandle().world.setTypeIdAndData(x, y, z, type, data, 2);
            if (success) {
                chunk.getHandle().world.notify(x, y, z);
            }
            return success;
        }
    }

    public org.bukkit.Material getType() {
        Block nameSign = getGateNameBlockHolder().getRelative(getGateFacing());
        return org.bukkit.Material.getMaterial(nameSign.getType());
    }

    public int getTypeId() {
        return chunk.getHandle().getTypeId(this.x & 0xf, this.y & 0xff, this.z & 0xf);
    }

    public byte getLightLevel() {
        return ((byte) (chunk.getHandle().world.getLightLevel(this.x, this.y, this.z)));
    }

    public byte getLightFromSky() {
        return ((byte) (chunk.getHandle().getBrightness(EnumSkyBlock.SKY, this.x & 0xf, this.y & 0xff, this.z & 0xf)));
    }

    public byte getLightFromBlocks() {
        return ((byte) (chunk.getHandle().getBrightness(EnumSkyBlock.BLOCK, this.x & 0xf, this.y & 0xff, this.z & 0xf)));
    }

    public org.bukkit.block.Block getFace(final org.bukkit.block.BlockFace face) {
        return getRelative(face, 1);
    }

    public org.bukkit.block.Block getFace(final org.bukkit.block.BlockFace face, final int distance) {
        return getRelative(face, distance);
    }

    public org.bukkit.block.Block getRelative(final int modX, final int modY, final int modZ) {
        return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
    }

    public org.bukkit.block.Block getRelative(org.bukkit.block.BlockFace face) {
        return getRelative(face, 1);
    }

    public org.bukkit.block.Block getRelative(org.bukkit.block.BlockFace face, int distance) {
        return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
    }

    public org.bukkit.block.BlockFace getFace(final org.bukkit.block.Block block) {
        org.bukkit.block.BlockFace[] values = org.bukkit.block.BlockFace.values();
        for (org.bukkit.block.BlockFace face : values) {
            if ((((this.getX() + face.getModX()) == block.getX()) && ((this.getY() + face.getModY()) == block.getY())) && ((this.getZ() + face.getModZ()) == block.getZ())) {
                return face;
            }
        }
        return null;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return (((((((((((("CraftBlock{" + "chunk=") + chunk) + ",x=") + x) + ",y=") + y) + ",z=") + z) + ",type=") + getType()) + ",data=") + getData()) + '}';
    }

    /**
     * Notch uses a 0-5 to mean DOWN, UP, NORTH, SOUTH, WEST, EAST
     * in that order all over. This method is convenience to convert for us.
     *
     * @return BlockFace the BlockFace represented by this number
     */
    public static org.bukkit.block.BlockFace notchToBlockFace(int notch) {
        switch (notch) {
            case 0 :
                return org.bukkit.block.BlockFace.DOWN;
            case 1 :
                return org.bukkit.block.BlockFace.UP;
            case 2 :
                return org.bukkit.block.BlockFace.NORTH;
            case 3 :
                return org.bukkit.block.BlockFace.SOUTH;
            case 4 :
                return org.bukkit.block.BlockFace.WEST;
            case 5 :
                return org.bukkit.block.BlockFace.EAST;
            default :
                return org.bukkit.block.BlockFace.SELF;
        }
    }

    public static int blockFaceToNotch(org.bukkit.block.BlockFace face) {
        switch (face) {
            case DOWN :
                return 0;
            case UP :
                return 1;
            case NORTH :
                return 2;
            case SOUTH :
                return 3;
            case WEST :
                return 4;
            case EAST :
                return 5;
            default :
                return 7;// Good as anything here, but technically invalid

        }
    }

    public org.bukkit.block.BlockState getState() {
        org.bukkit.Material material = getType();
        switch (material) {
            case SIGN :
            case SIGN_POST :
            case WALL_SIGN :
                return new org.bukkit.craftbukkit.block.CraftSign(this);
            case CHEST :
            case TRAPPED_CHEST :
                return new org.bukkit.craftbukkit.block.CraftChest(this);
            case BURNING_FURNACE :
            case FURNACE :
                return new org.bukkit.craftbukkit.block.CraftFurnace(this);
            case DISPENSER :
                return new org.bukkit.craftbukkit.block.CraftDispenser(this);
            case DROPPER :
                return new org.bukkit.craftbukkit.block.CraftDropper(this);
            case HOPPER :
                return new org.bukkit.craftbukkit.block.CraftHopper(this);
            case MOB_SPAWNER :
                return new org.bukkit.craftbukkit.block.CraftCreatureSpawner(this);
            case NOTE_BLOCK :
                return new org.bukkit.craftbukkit.block.CraftNoteBlock(this);
            case JUKEBOX :
                return new org.bukkit.craftbukkit.block.CraftJukebox(this);
            case BREWING_STAND :
                return new org.bukkit.craftbukkit.block.CraftBrewingStand(this);
            case SKULL :
                return new org.bukkit.craftbukkit.block.CraftSkull(this);
            case COMMAND :
                return new org.bukkit.craftbukkit.block.CraftCommandBlock(this);
            case BEACON :
                return new org.bukkit.craftbukkit.block.CraftBeacon(this);
            default :
                return new org.bukkit.craftbukkit.block.CraftBlockState(this);
        }
    }

    public org.bukkit.block.Biome getBiome() {
        return getWorld().getBiome(x, z);
    }

    public void setBiome(org.bukkit.block.Biome bio) {
        getWorld().setBiome(x, z, bio);
    }

    public static org.bukkit.block.Biome biomeBaseToBiome(net.minecraft.server.BiomeBase base) {
        if (base == null) {
            return null;
        }
        return org.bukkit.craftbukkit.block.CraftBlock.BIOME_MAPPING[base.id];
    }

    public static net.minecraft.server.BiomeBase biomeToBiomeBase(org.bukkit.block.Biome bio) {
        if (bio == null) {
            return null;
        }
        return org.bukkit.craftbukkit.block.CraftBlock.BIOMEBASE_MAPPING[bio.ordinal()];
    }

    public double getTemperature() {
        return getWorld().getTemperature(x, z);
    }

    public double getHumidity() {
        return getWorld().getHumidity(x, z);
    }

    public boolean isBlockPowered() {
        return chunk.getHandle().world.getBlockPower(x, y, z) > 0;
    }

    public boolean isBlockIndirectlyPowered() {
        return chunk.getHandle().world.isBlockIndirectlyPowered(x, y, z);
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if (o == this)
            return true;

        if (!(o instanceof org.bukkit.craftbukkit.block.CraftBlock))
            return false;

        org.bukkit.craftbukkit.block.CraftBlock other = ((org.bukkit.craftbukkit.block.CraftBlock) (o));
        return (((this.x == other.x) && (this.y == other.y)) && (this.z == other.z)) && this.getWorld().equals(other.getWorld());
    }

    @java.lang.Override
    public int hashCode() {
        return (((this.y << 24) ^ this.x) ^ this.z) ^ this.getWorld().hashCode();
    }

    public boolean isBlockFacePowered(org.bukkit.block.BlockFace face) {
        return chunk.getHandle().world.isBlockFacePowered(x, y, z, org.bukkit.craftbukkit.block.CraftBlock.blockFaceToNotch(face));
    }

    public boolean isBlockFaceIndirectlyPowered(org.bukkit.block.BlockFace face) {
        int power = chunk.getHandle().world.getBlockFacePower(x, y, z, org.bukkit.craftbukkit.block.CraftBlock.blockFaceToNotch(face));
        org.bukkit.block.Block relative = getRelative(face);
        if (relative.getType() == org.bukkit.Material.REDSTONE_WIRE) {
            return java.lang.Math.max(power, relative.getData()) > 0;
        }
        return power > 0;
    }

    public int getBlockPower(org.bukkit.block.BlockFace face) {
        int power = 0;
        net.minecraft.server.BlockRedstoneWire wire = org.bukkit.block.Block.REDSTONE_WIRE;
        org.bukkit.World world = chunk.getHandle().world;
        if (((face == org.bukkit.block.BlockFace.DOWN) || (face == org.bukkit.block.BlockFace.SELF)) && world.isBlockFacePowered(x, y - 1, z, 0))
            power = wire.getPower(world, x, y - 1, z, power);

        if (((face == org.bukkit.block.BlockFace.UP) || (face == org.bukkit.block.BlockFace.SELF)) && world.isBlockFacePowered(x, y + 1, z, 1))
            power = wire.getPower(world, x, y + 1, z, power);

        if (((face == org.bukkit.block.BlockFace.EAST) || (face == org.bukkit.block.BlockFace.SELF)) && world.isBlockFacePowered(x + 1, y, z, 2))
            power = wire.getPower(world, x + 1, y, z, power);

        if (((face == org.bukkit.block.BlockFace.WEST) || (face == org.bukkit.block.BlockFace.SELF)) && world.isBlockFacePowered(x - 1, y, z, 3))
            power = wire.getPower(world, x - 1, y, z, power);

        if (((face == org.bukkit.block.BlockFace.NORTH) || (face == org.bukkit.block.BlockFace.SELF)) && world.isBlockFacePowered(x, y, z - 1, 4))
            power = wire.getPower(world, x, y, z - 1, power);

        if (((face == org.bukkit.block.BlockFace.SOUTH) || (face == org.bukkit.block.BlockFace.SELF)) && world.isBlockFacePowered(x, y, z + 1, 5))
            power = wire.getPower(world, x, y, z - 1, power);

        return power > 0 ? power : face == org.bukkit.block.BlockFace.SELF ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face) ? 15 : 0;
    }

    public int getBlockPower() {
        return getBlockPower(org.bukkit.block.BlockFace.SELF);
    }

    public boolean isEmpty() {
        return getType() == org.bukkit.Material.AIR;
    }

    public boolean isLiquid() {
        return (((getType() == org.bukkit.Material.WATER) || (getType() == org.bukkit.Material.STATIONARY_WATER)) || (getType() == org.bukkit.Material.LAVA)) || (getType() == org.bukkit.Material.STATIONARY_LAVA);
    }

    public org.bukkit.block.PistonMoveReaction getPistonMoveReaction() {
        return org.bukkit.block.PistonMoveReaction.getById(org.bukkit.block.Block.byId[this.getTypeId()].material.getPushReaction());
    }

    private boolean itemCausesDrops(org.bukkit.inventory.ItemStack item) {
        org.bukkit.block.Block block = org.bukkit.block.Block.byId[this.getTypeId()];
        org.bukkit.craftbukkit.block.Item itemType = (item != null) ? net.minecraft.server.Item.byId[item.getTypeId()] : null;
        return (block != null) && (block.material.isAlwaysDestroyable() || ((itemType != null) && itemType.canDestroySpecialBlock(block)));
    }

    public boolean breakNaturally() {
        // Order matters here, need to drop before setting to air so skulls can get their data
        org.bukkit.block.Block block = org.bukkit.block.Block.byId[this.getTypeId()];
        byte data = getData();
        boolean result = false;
        if (block != null) {
            block.dropNaturally(chunk.getHandle().world, x, y, z, data, 1.0F, 0);
            result = true;
        }
        setTypeId(org.bukkit.Material.AIR.getId());
        return result;
    }

    public boolean breakNaturally(org.bukkit.inventory.ItemStack item) {
        if (itemCausesDrops(item)) {
            return breakNaturally();
        } else {
            return setTypeId(org.bukkit.Material.AIR.getId());
        }
    }

    public java.util.Collection<org.bukkit.inventory.ItemStack> getDrops() {
        java.util.List<org.bukkit.inventory.ItemStack> drops = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
        org.bukkit.block.Block block = org.bukkit.block.Block.byId[this.getTypeId()];
        if (block != null) {
            byte data = getData();
            // based on nms.Block.dropNaturally
            int count = block.getDropCount(0, chunk.getHandle().world.random);
            for (int i = 0; i < count; ++i) {
                int item = block.getDropType(data, chunk.getHandle().world.random, 0);
                if (item > 0) {
                    // Skulls are special, their data is based on the tile entity
                    if (net.minecraft.server.Block.SKULL.id == this.getTypeId()) {
                        org.bukkit.inventory.ItemStack nmsStack = new org.bukkit.inventory.ItemStack(item, 1, block.getDropData(chunk.getHandle().world, x, y, z));
                        net.minecraft.server.TileEntitySkull tileentityskull = ((net.minecraft.server.TileEntitySkull) (chunk.getHandle().world.getTileEntity(x, y, z)));
                        if (((tileentityskull.getSkullType() == 3) && (tileentityskull.getExtraType() != null)) && (tileentityskull.getExtraType().length() > 0)) {
                            nmsStack.setTag(new net.minecraft.server.NBTTagCompound());
                            nmsStack.getTag().setString("SkullOwner", tileentityskull.getExtraType());
                        }
                        drops.add(org.bukkit.craftbukkit.inventory.CraftItemStack.asBukkitCopy(nmsStack));
                    } else {
                        drops.add(new org.bukkit.inventory.ItemStack(item, 1, ((short) (block.getDropData(data)))));
                    }
                }
            }
        }
        return drops;
    }

    public java.util.Collection<org.bukkit.inventory.ItemStack> getDrops(org.bukkit.inventory.ItemStack item) {
        if (itemCausesDrops(item)) {
            return getDrops();
        } else {
            return java.util.Collections.emptyList();
        }
    }

    /* Build biome index based lookup table for BiomeBase to Biome mapping */
    static {
        BIOME_MAPPING = new org.bukkit.block.Biome[BiomeBase.biomes.length];
        BIOMEBASE_MAPPING = new net.minecraft.server.BiomeBase[org.bukkit.block.Biome.values().length];
        BIOME_MAPPING[BiomeBase.SWAMPLAND.id] = org.bukkit.block.Biome.SWAMPLAND;
        BIOME_MAPPING[BiomeBase.FOREST.id] = org.bukkit.block.Biome.FOREST;
        BIOME_MAPPING[BiomeBase.TAIGA.id] = org.bukkit.block.Biome.TAIGA;
        BIOME_MAPPING[BiomeBase.DESERT.id] = org.bukkit.block.Biome.DESERT;
        BIOME_MAPPING[BiomeBase.PLAINS.id] = org.bukkit.block.Biome.PLAINS;
        BIOME_MAPPING[BiomeBase.HELL.id] = org.bukkit.block.Biome.HELL;
        BIOME_MAPPING[BiomeBase.SKY.id] = org.bukkit.block.Biome.SKY;
        BIOME_MAPPING[BiomeBase.RIVER.id] = org.bukkit.block.Biome.RIVER;
        BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id] = org.bukkit.block.Biome.EXTREME_HILLS;
        BIOME_MAPPING[BiomeBase.OCEAN.id] = org.bukkit.block.Biome.OCEAN;
        BIOME_MAPPING[BiomeBase.FROZEN_OCEAN.id] = org.bukkit.block.Biome.FROZEN_OCEAN;
        BIOME_MAPPING[BiomeBase.FROZEN_RIVER.id] = org.bukkit.block.Biome.FROZEN_RIVER;
        BIOME_MAPPING[BiomeBase.ICE_PLAINS.id] = org.bukkit.block.Biome.ICE_PLAINS;
        BIOME_MAPPING[BiomeBase.ICE_MOUNTAINS.id] = org.bukkit.block.Biome.ICE_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.MUSHROOM_ISLAND.id] = org.bukkit.block.Biome.MUSHROOM_ISLAND;
        BIOME_MAPPING[BiomeBase.MUSHROOM_SHORE.id] = org.bukkit.block.Biome.MUSHROOM_SHORE;
        BIOME_MAPPING[BiomeBase.BEACH.id] = org.bukkit.block.Biome.BEACH;
        BIOME_MAPPING[BiomeBase.DESERT_HILLS.id] = org.bukkit.block.Biome.DESERT_HILLS;
        BIOME_MAPPING[BiomeBase.FOREST_HILLS.id] = org.bukkit.block.Biome.FOREST_HILLS;
        BIOME_MAPPING[BiomeBase.TAIGA_HILLS.id] = org.bukkit.block.Biome.TAIGA_HILLS;
        BIOME_MAPPING[BiomeBase.SMALL_MOUNTAINS.id] = org.bukkit.block.Biome.SMALL_MOUNTAINS;
        BIOME_MAPPING[BiomeBase.JUNGLE.id] = org.bukkit.block.Biome.JUNGLE;
        BIOME_MAPPING[BiomeBase.JUNGLE_HILLS.id] = org.bukkit.block.Biome.JUNGLE_HILLS;
        /* Sanity check - we should have a record for each record in the BiomeBase.a table */
        /* Helps avoid missed biomes when we upgrade bukkit to new code with new biomes */
        for (int i = 0; i < org.bukkit.craftbukkit.block.CraftBlock.BIOME_MAPPING.length; i++) {
            if ((net.minecraft.server.BiomeBase.biomes[i] != null) && (BIOME_MAPPING[i] == null)) {
                throw new java.lang.IllegalArgumentException(("Missing Biome mapping for BiomeBase[" + i) + "]");
            }
            if (BIOME_MAPPING[i] != null) {
                /* Build reverse mapping for setBiome */
                BIOMEBASE_MAPPING[BIOME_MAPPING[i].ordinal()] = net.minecraft.server.BiomeBase.biomes[i];
            }
        }
    }

    public void setMetadata(java.lang.String metadataKey, org.bukkit.metadata.MetadataValue newMetadataValue) {
        chunk.getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    public java.util.List<org.bukkit.metadata.MetadataValue> getMetadata(java.lang.String metadataKey) {
        return chunk.getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
    }

    public boolean hasMetadata(java.lang.String metadataKey) {
        return chunk.getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
    }

    public void removeMetadata(java.lang.String metadataKey, org.bukkit.plugin.Plugin owningPlugin) {
        chunk.getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }
}