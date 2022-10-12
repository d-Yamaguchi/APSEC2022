/**
 * breaks blocks that are on the trajectory of the projectile. The projectile is stopped by impenetratable blocks (obsidian)
 *
 * @param cannonball
 * 		
 * @return 
 */
private org.bukkit.Location blockBreaker(at.pavlov.cannons.projectile.FlyingProjectile cannonball) {
    at.pavlov.cannons.projectile.Projectile projectile = cannonball.getProjectile();
    // has this projectile the super breaker property and makes block damage
    java.lang.Boolean superbreaker = projectile.hasProperty(ProjectileProperties.SUPERBREAKER);
    java.lang.Boolean doesBlockDamage = projectile.getPenetrationDamage();
    // list of destroy blocks
    java.util.LinkedList<org.bukkit.block.Block> blocklist = new java.util.LinkedList<org.bukkit.block.Block>();
    org.bukkit.util.Vector vel = snowball.getVelocity();
    at.pavlov.cannons.projectile.FlyingProjectile _CVAR0 = cannonball;
    org.bukkit.entity.Snowball snowball = _CVAR0.getSnowball();
    org.bukkit.entity.Snowball _CVAR1 = snowball;
    org.bukkit.Location snowballLoc = _CVAR1.getLocation();
    org.bukkit.World world = snowball.getWorld();
    int penetration = ((int) ((cannonball.getProjectile().getPenetration() * vel.length()) / projectile.getVelocity()));
    org.bukkit.Location impactLoc = snowballLoc.clone();
    // the cannonball will only break blocks if it has penetration.
    if (cannonball.getProjectile().getPenetration() > 0) {
        org.bukkit.util.BlockIterator iter = new org.bukkit.util.BlockIterator(world, snowballLoc.toVector(), vel.normalize(), 0, penetration + 1);
        int i = 0;
        while (iter.hasNext() && (i <= (penetration + 1))) {
            i++;
            org.bukkit.block.Block next = iter.next();
            // Break block on ray
            if (i <= penetration) {
                // if block can be destroyed the the iterator will check the next block. Else the projectile will explode
                if (breakBlock(next, blocklist, superbreaker, doesBlockDamage) == false) {
                    // found undestroyable block - set impactloc
                    impactLoc = next.getLocation();
                    break;
                }
            } else // set impact location
            {
                impactLoc = next.getLocation();
            }
        } 
    }
    if (superbreaker) {
        // small explosion on impact
        org.bukkit.block.Block block = impactLoc.getBlock();
        breakBlock(block, blocklist, superbreaker, doesBlockDamage);
        breakBlock(block.getRelative(org.bukkit.block.BlockFace.UP), blocklist, superbreaker, doesBlockDamage);
        breakBlock(block.getRelative(org.bukkit.block.BlockFace.DOWN), blocklist, superbreaker, doesBlockDamage);
        breakBlock(block.getRelative(org.bukkit.block.BlockFace.SOUTH), blocklist, superbreaker, doesBlockDamage);
        breakBlock(block.getRelative(org.bukkit.block.BlockFace.WEST), blocklist, superbreaker, doesBlockDamage);
        breakBlock(block.getRelative(org.bukkit.block.BlockFace.EAST), blocklist, superbreaker, doesBlockDamage);
        breakBlock(block.getRelative(org.bukkit.block.BlockFace.NORTH), blocklist, superbreaker, doesBlockDamage);
    }
    // no eventhandling if the list is empty
    if (blocklist.size() > 0) {
        // create bukkit event
        org.bukkit.event.entity.EntityExplodeEvent event = new org.bukkit.event.entity.EntityExplodeEvent(cannonball.getSnowball(), impactLoc, blocklist, 1.0F);
        // handle with bukkit
        plugin.getServer().getPluginManager().callEvent(event);
        // if not canceled
        if ((!event.isCancelled()) && (plugin.BlockBreakPluginLoaded() == false)) {
            // break water, lava, obsidian if cannon projectile
            for (int i = 0; i < event.blockList().size(); i++) {
                org.bukkit.block.Block block = event.blockList().get(i);
                if (event.getEntity() != null) {
                    block = event.blockList().get(i);
                    // break the block, no matter what it is
                    BlockBreak(block, event.getYield());
                }
            }
        }
    }
    return impactLoc;
}