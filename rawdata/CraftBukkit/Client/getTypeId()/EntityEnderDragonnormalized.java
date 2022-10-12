private boolean a(net.minecraft.server.AxisAlignedBB axisalignedbb) {
    int i = net.minecraft.server.MathHelper.floor(axisalignedbb.a);
    int j = net.minecraft.server.MathHelper.floor(axisalignedbb.b);
    int k = net.minecraft.server.MathHelper.floor(axisalignedbb.c);
    int l = net.minecraft.server.MathHelper.floor(axisalignedbb.d);
    int i1 = net.minecraft.server.MathHelper.floor(axisalignedbb.e);
    int j1 = net.minecraft.server.MathHelper.floor(axisalignedbb.f);
    boolean flag = false;
    boolean flag1 = false;
    // CraftBukkit start - Create a list to hold all the destroyed blocks
    java.util.List<org.bukkit.block.Block> destroyedBlocks = new java.util.ArrayList<org.bukkit.block.Block>();
    net.minecraft.server.CraftWorld craftWorld = this.world.getWorld();
    // CraftBukkit end
    for (int k1 = i; k1 <= l; ++k1) {
        for (int l1 = j; l1 <= i1; ++l1) {
            for (int i2 = k; i2 <= j1; ++i2) {
                int j2 = this.world.getTypeId(k1, l1, i2);
                if (j2 != 0) {
                    if ((((j2 != Block.OBSIDIAN.id) && (j2 != Block.WHITESTONE.id)) && (j2 != Block.BEDROCK.id)) && this.world.getGameRules().getBoolean("mobGriefing")) {
                        // CraftBukkit start - Add blocks to list rather than destroying them
                        // flag1 = this.world.setAir(k1, l1, i2) || flag1;
                        flag1 = true;
                        destroyedBlocks.add(craftWorld.getBlockAt(k1, l1, i2));
                        // CraftBukkit end
                    } else {
                        flag = true;
                    }
                }
            }
        }
    }
    if (flag1) {
        // CraftBukkit start - Set off an EntityExplodeEvent for the dragon exploding all these blocks
        org.bukkit.entity.Entity bukkitEntity = this.getBukkitEntity();
        org.bukkit.event.entity.EntityExplodeEvent event = new org.bukkit.event.entity.EntityExplodeEvent(bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0.0F);
        org.bukkit.Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            // This flag literally means 'Dragon hit something hard' (Obsidian, White Stone or Bedrock) and will cause the dragon to slow down.
            // We should consider adding an event extension for it, or perhaps returning true if the event is cancelled.
            return flag;
        } else if (event.getYield() == 0.0F) {
            // Yield zero ==> no drops
            for (org.bukkit.block.Block block : event.blockList()) {
                this.world.setAir(block.getX(), block.getY(), block.getZ());
            }
        } else {
            for (org.bukkit.block.Block block : event.blockList()) {
                org.bukkit.block.Block _CVAR0 = block;
                int blockId = _CVAR0.getTypeId();
                if (blockId == 0) {
                    continue;
                }
                int blockX = block.getX();
                int blockY = block.getY();
                int blockZ = block.getZ();
                if (Block.byId[blockId].a(explosionSource)) {
                    Block.byId[blockId].dropNaturally(this.world, blockX, blockY, blockZ, block.getData(), event.getYield(), 0);
                }
                Block.byId[blockId].wasExploded(world, blockX, blockY, blockZ, explosionSource);
                this.world.setAir(blockX, blockY, blockZ);
            }
        }
        // CraftBukkit end
        double d0 = axisalignedbb.a + ((axisalignedbb.d - axisalignedbb.a) * ((double) (this.random.nextFloat())));
        double d1 = axisalignedbb.b + ((axisalignedbb.e - axisalignedbb.b) * ((double) (this.random.nextFloat())));
        double d2 = axisalignedbb.c + ((axisalignedbb.f - axisalignedbb.c) * ((double) (this.random.nextFloat())));
        this.world.addParticle("largeexplode", d0, d1, d2, 0.0, 0.0, 0.0);
    }
    return flag;
}