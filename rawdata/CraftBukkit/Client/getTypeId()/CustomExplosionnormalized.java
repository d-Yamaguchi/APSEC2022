public void doBlocks() {
    for (int i = com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.blocks.size() - 1; i >= 0; i--) {
        com.bergerkiller.bukkit.common.bases.IntVector3 cpos = com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.blocks.get(i);
        blockList.add(this.world.getBlockAt(cpos.x, cpos.y, cpos.z));
    }
    com.bergerkiller.bukkit.common.utils.CommonUtil.callEvent(event);
    com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.blocks.clear();
    if (event.isCancelled()) {
        this.wasCanceled = true;
        return;
    } else if (NoLaggTNT.plugin.getTNTHandler().createExplosion(event)) {
        // handled by ourself
        return;
    }
    // CraftBukkit end
    int x;
    int y;
    int z;
    int type;
    org.bukkit.Location _CVAR1 = pos;
    // CraftBukkit start
    org.bukkit.Location location = _CVAR1.clone();
    java.util.List<com.bergerkiller.bukkit.common.bases.IntVector3> _CVAR3 = com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.blocks;
     _CVAR4 = _CVAR3.size();
    // generate org.bukkit Block array
    java.util.List<org.bukkit.block.Block> blockList = new java.util.ArrayList<org.bukkit.block.Block>(_CVAR4);
    org.bukkit.entity.Entity _CVAR0 = this.source;
    org.bukkit.Location _CVAR2 = location;
    java.util.List<org.bukkit.block.Block> _CVAR5 = blockList;
    float _CVAR6 = 0.3F;
    org.bukkit.event.entity.EntityExplodeEvent event = new org.bukkit.event.entity.EntityExplodeEvent(_CVAR0, _CVAR2, _CVAR5, _CVAR6);
    org.bukkit.event.entity.EntityExplodeEvent _CVAR7 = event;
    java.util.List<org.bukkit.block.Block> blocks = _CVAR7.blockList();
    int _CVAR9 = 1;
    int i = blocks.size() - _CVAR9;
    for (int i = blocks.size() - _CVAR9; i >= 0; --i) {
        x = block.getX();
        y = block.getY();
        z = block.getZ();
        java.util.List<org.bukkit.block.Block> _CVAR8 = blocks;
        int _CVAR10 = i;
        org.bukkit.block.Block block = _CVAR8.get(_CVAR10);
        org.bukkit.block.Block _CVAR11 = block;
        int _CVAR12 = _CVAR11.getTypeId();
        type = _CVAR12;
        double d0 = ((double) (((float) (x)) + worldRandom.nextFloat()));
        double d1 = ((double) (((float) (y)) + worldRandom.nextFloat()));
        double d2 = ((double) (((float) (z)) + worldRandom.nextFloat()));
        double d3 = d0 - this.pos.getX();
        double d4 = d1 - this.pos.getY();
        double d5 = d2 - this.pos.getZ();
        double d6 = java.lang.Math.sqrt(((d3 * d3) + (d4 * d4)) + (d5 * d5));
        d3 /= d6;
        d4 /= d6;
        d5 /= d6;
        double d7 = 0.5 / ((d6 / ((double) (this.size))) + 0.1);
        d7 *= ((double) ((worldRandom.nextFloat() * worldRandom.nextFloat()) + 0.3F));
        d3 *= d7;
        d4 *= d7;
        d5 *= d7;
        // CraftBukkit - stop explosions from putting out fire
        if ((type > 0) && (type != org.bukkit.Material.FIRE.getId())) {
            final com.bergerkiller.bukkit.common.wrappers.BlockInfo info = com.bergerkiller.bukkit.common.wrappers.BlockInfo.get(block);
            info.destroy(block, event.getYield());
            info.ignite(block);
        }
        if (this.fire) {
            int typeBelow = this.world.getBlockTypeIdAt(x, y - 1, z);
            if (((type == 0) && MaterialUtil.ISSOLID.get(typeBelow)) && (this.h.nextInt(3) == 0)) {
                block.setType(org.bukkit.Material.FIRE);
            }
        }
    }
}