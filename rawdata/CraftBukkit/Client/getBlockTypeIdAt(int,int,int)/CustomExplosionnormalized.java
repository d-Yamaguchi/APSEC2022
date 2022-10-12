public void prepare() {
    int xoff = pos.getBlockX();
    int yoff = pos.getBlockY();
    int zoff = pos.getBlockZ();
    com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.root.sourcedamage = (com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.factor * this.size) * (0.7F + (worldRandom.nextFloat() * 0.6F));
    // Recursively operate on all blocks
    int i = 0;
    float damageFactor;
    int x;
    int y;
    int z;
    boolean hasDamage = true;
    while (hasDamage && (i < com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionLayers.size())) {
        hasDamage = false;
        for (com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionSlot slot : com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionLayers.get(i).slotArray) {
            // generate the info for this block
            if (slot.block.initialized.set()) {
                x = slot.block.pos.x + xoff;
                y = slot.block.pos.y + yoff;
                z = slot.block.pos.z + zoff;
                slot.block.type = world.getBlockTypeIdAt(x, y, z);
                if (slot.block.type > 0) {
                    slot.block.damagefactor = (com.bergerkiller.bukkit.common.utils.MaterialUtil.getDamageResilience(slot.block.type, source) + 0.3F) * 0.3F;
                    slot.block.damagefactor *= (2.0F + worldRandom.nextFloat()) / 3.0F;
                } else {
                    slot.block.destroyed.set();// prevent air getting destroyed by marking it as destroyed already

                    slot.block.damagefactor = 0;
                }
            }
            // subtract damage factor
            damageFactor = slot.sourcedamage - slot.block.damagefactor;
            slot.sourcedamage = 0;
            if (damageFactor <= 0) {
                continue;
            }
            // mark the block for destroying
            if (slot.block.destroyed.set()) {
                com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.blocks.add(slot.block.pos.add(xoff, yoff, zoff));
            }
            // one block layer further...
            if ((damageFactor -= 0.225F) <= 0.0F) {
                continue;
            }
            // force a new layer if needed
            if (slot.next == null) {
                // create a new layer
                com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionLayer nextLayer = new com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionLayer(com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionLayers.size());
                // convert to array
                for (com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionSlot slot2 : com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionLayers.get(com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionLayers.size() - 1).slots.values()) {
                    slot2.next = slot2.nextSet.toArray(new com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionSlot[0]);
                    slot2.nextSet = null;
                }
                com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionLayers.add(nextLayer);
            }
            // set source damage of next slots
            for (com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionSlot slot2 : slot.next) {
                if (damageFactor > slot2.sourcedamage) {
                    slot2.sourcedamage = damageFactor;
                }
            }
            hasDamage = true;
        }
        i++;
    } 
    // Restore blocks to old state
    for (com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.ExplosionBlock block : com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.explosionBlocks) {
        block.destroyed.clear();
        block.initialized.clear();
    }
    // =====================generate entities===================
    double tmpsize = ((double) (this.size)) * 2.0;
    double xmin = (this.pos.getX() - tmpsize) - 1.0;
    double ymin = (this.pos.getY() - tmpsize) - 1.0;
    double zmin = (this.pos.getZ() - tmpsize) - 1.0;
    double xmax = (this.pos.getX() + tmpsize) + 1.0;
    double ymax = (this.pos.getY() + tmpsize) + 1.0;
    double zmax = (this.pos.getZ() + tmpsize) + 1.0;
    java.util.List<org.bukkit.entity.Entity> list = com.bergerkiller.bukkit.common.utils.WorldUtil.getEntities(this.world, this.source, xmin, ymin, zmin, xmax, ymax, zmax);
    // ==========================================================
    // Vec3D vec3d = Vec3D.a(this.posX, this.posY, this.posZ);
    double tmpX;
    double tmpY;
    double tmpZ;
    for (org.bukkit.entity.Entity bukkitEntity : list) {
        if ((bukkitEntity == null) || bukkitEntity.isDead()) {
            continue;
        }
        final com.bergerkiller.bukkit.common.entity.CommonEntity<?> entity = com.bergerkiller.bukkit.common.entity.CommonEntity.get(bukkitEntity);
        tmpX = entity.loc.getX() - this.pos.getX();
        tmpY = entity.loc.getY() - this.pos.getY();
        tmpZ = entity.loc.getZ() - this.pos.getZ();
        double length = com.bergerkiller.bukkit.common.utils.MathUtil.lengthSquared(tmpX, tmpY, tmpZ);
        if (length >= (tmpsize * tmpsize)) {
            continue;
        }
        length = java.lang.Math.sqrt(length);
        // normalize
        tmpX /= length;
        tmpY /= length;
        tmpZ /= length;
        double distanceFactor = length / tmpsize;
        if (com.bergerkiller.bukkit.nolagg.tnt.CustomExplosion.useQuickDamageMode) {
            // damage factor
            if (bukkitEntity instanceof org.bukkit.entity.Item) {
                damageFactor = 1.0F;
            } else if (bukkitEntity instanceof org.bukkit.entity.TNTPrimed) {
                damageFactor = 8.0F / 9.0F;
            } else {
                damageFactor = com.bergerkiller.bukkit.common.utils.WorldUtil.getExplosionDamageFactor(pos, bukkitEntity);
            }
        } else {
            damageFactor = com.bergerkiller.bukkit.common.utils.WorldUtil.getExplosionDamageFactor(pos, bukkitEntity);
        }
        double force = (1.0 - distanceFactor) * ((double) (damageFactor));
        double damageDone = (((force * (force + 1.0)) * 4.0) * tmpsize) + 1.0;
        // Send a damage event to Bukkit and deal the damage if not cancelled
        final org.bukkit.event.entity.EntityDamageEvent event;
        double damage;
        if (Common.MC_VERSION.equals("1.5.2")) {
            if (source == null) {
                event = new org.bukkit.event.entity.EntityDamageByBlockEvent(null, bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, ((int) (damageDone)));
            } else if (source instanceof org.bukkit.entity.TNTPrimed) {
                event = new org.bukkit.event.entity.EntityDamageByEntityEvent(source, bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, ((int) (damageDone)));
            } else {
                event = new org.bukkit.event.entity.EntityDamageByEntityEvent(source, bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, ((int) (damageDone)));
            }
            if (com.bergerkiller.bukkit.common.utils.CommonUtil.callEvent(event).isCancelled()) {
                return;
            }
            damage = new com.bergerkiller.bukkit.common.reflection.SafeMethod<java.lang.Integer>(event, "getDamage").invoke(event);
        } else {
            if (source == null) {
                event = new org.bukkit.event.entity.EntityDamageByBlockEvent(null, bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
            } else if (source instanceof org.bukkit.entity.TNTPrimed) {
                event = new org.bukkit.event.entity.EntityDamageByEntityEvent(source, bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damageDone);
            } else {
                event = new org.bukkit.event.entity.EntityDamageByEntityEvent(source, bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, damageDone);
            }
            if (com.bergerkiller.bukkit.common.utils.CommonUtil.callEvent(event).isCancelled()) {
                return;
            }
            damage = event.getDamage();
        }
        if (!com.bergerkiller.bukkit.common.utils.CommonUtil.callEvent(event).isCancelled()) {
            com.bergerkiller.bukkit.common.utils.EntityUtil.damage(bukkitEntity, org.bukkit.event.entity.EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, damage);
            entity.vel.add(tmpX * force, tmpY * force, tmpZ * force);
        }
    }
}