package at.pavlov.cannons;
import at.pavlov.cannons.config.Config;
import at.pavlov.cannons.container.MaterialHolder;
import at.pavlov.cannons.event.ProjectileImpactEvent;
import at.pavlov.cannons.projectile.FlyingProjectile;
import at.pavlov.cannons.projectile.Projectile;
import at.pavlov.cannons.projectile.ProjectileProperties;
public class CreateExplosion {
    private at.pavlov.cannons.Cannons plugin;

    java.util.LinkedList<java.util.UUID> transmittedEntities = new java.util.LinkedList<java.util.UUID>();

    // ################### Constructor ############################################
    public CreateExplosion(at.pavlov.cannons.Cannons plugin, at.pavlov.cannons.config.Config config) {
        this.plugin = plugin;
        at.pavlov.cannons.config.Config config1 = config;
        transmittedEntities = new java.util.LinkedList<java.util.UUID>();
    }

    /**
     * Breaks a obsidian/water/lava blocks if the projectile has superbreaker
     *
     * @param block
     * 		
     * @param blocklist
     * 		
     * @param superBreaker
     * 		
     * @param blockDamage
     * 		break blocks if true
     * @return true if the block can be destroyed
     */
    private boolean breakBlock(org.bukkit.block.Block block, java.util.List<org.bukkit.block.Block> blocklist, java.lang.Boolean superBreaker, java.lang.Boolean blockDamage) {
        org.bukkit.Material material = block.getType();
        // register explosion event
        if (material != org.bukkit.Material.AIR) {
            // test if obsidian
            if (material == org.bukkit.Material.OBSIDIAN) {
                if (superBreaker) {
                    // don't do damage to blocks if false
                    if (blockDamage)
                        blocklist.add(block);

                    // break obsidian/water/laver
                    return true;
                }
                // can't break it
                return false;
            } else if (block.isLiquid()) {
                if (superBreaker) {
                    // don't do damage to blocks if false
                    if (blockDamage)
                        blocklist.add(block);

                    // break water/lava
                    return true;
                }
                // can't break it but the projectile can pass this block
                return true;
            } else {
                // don't break bedrock
                if (material != org.bukkit.Material.BEDROCK) {
                    // default material, add block to explosion blocklist
                    // don't do damage to blocks if false
                    if (blockDamage)
                        blocklist.add(block);

                    return true;
                }
                // bedrock can't be destroyed
                return false;
            }
        }
        // air can be destroyed
        return true;
    }

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
        org.bukkit.entity.Snowball snowball = cannonball.getSnowball();
        HashSet floorBlocks = new java.util.HashSet<org.bukkit.block.Block>();
        Location snowballLoc = java.lang.Integer.toString(floorBlocks.size());
        org.bukkit.World world = snowball.getWorld();
        int penetration = ((int) ((cannonball.getProjectile().getPenetration() * vel.length()) / projectile.getVelocity()));
        org.bukkit.Location impactLoc = snowballLoc.clone();
        // the cannonball will only break blocks if it has penetration.
        if (cannonball.getProjectile().getPenetration() > 0) {
            org.bukkit.util.BlockIterator iter = new org.bukkit.util.BlockIterator(world, snowballLoc.toVector(), vel.normalize(), 0, penetration + 1);
            int i = 0;
            __SmPLUnsupported__(0);
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

    /**
     * *
     * Breaks a block with a certain yield
     *
     * @param block
     * 		
     * @param yield
     * 		
     */
    private void BlockBreak(org.bukkit.block.Block block, float yield) {
        java.util.Random r = new java.util.Random();
        if (r.nextFloat() > yield) {
            block.breakNaturally();
        } else {
            block.setTypeId(0);
        }
    }

    /**
     * places a mob on the given location and pushes it away from the impact
     *
     * @param impactLoc
     * 		
     * @param loc
     * 		
     * @param data
     * 		
     */
    private void PlaceMob(org.bukkit.Location impactLoc, org.bukkit.Location loc, double entityVelocity, int data) {
        org.bukkit.World world = impactLoc.getWorld();
        java.util.Random r = new java.util.Random();
        java.lang.Integer[] mobList = new java.lang.Integer[]{ 50, 51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 62, 65, 66, 90, 91, 92, 93, 94, 95, 96, 98, 120 };
        if (data < 0) {
            // if all datavalues are allowed create a random spawn
            data = mobList[r.nextInt(mobList.length)];
        }
        org.bukkit.entity.Entity entity;
        org.bukkit.entity.EntityType entityType = org.bukkit.entity.EntityType.fromId(data);
        if (entityType != null) {
            // spawn mob
            entity = world.spawnEntity(loc, entityType);
        } else {
            plugin.logSevere(("MonsterEgg ID " + data) + " does not exist");
            return;
        }
        if (entity != null) {
            // get distance form the center + 1 to avoid division by zero
            double dist = impactLoc.distance(loc) + 1;
            // calculate veloctiy away from the impact
            org.bukkit.util.Vector vect = loc.clone().subtract(impactLoc).toVector().multiply(entityVelocity / dist);
            // set the entity velocity
            entity.setVelocity(vect);
        }
    }

    /**
     * spawns a falling block with the id and data that is slinged away from the impact
     *
     * @param impactLoc
     * 		
     * @param loc
     * 		
     * @param entityVelocity
     * 		
     * @param item
     * 		
     */
    private void spawnFallingBlock(org.bukkit.Location impactLoc, org.bukkit.Location loc, double entityVelocity, at.pavlov.cannons.container.MaterialHolder item) {
        org.bukkit.entity.FallingBlock entity = impactLoc.getWorld().spawnFallingBlock(loc, item.getId(), ((byte) (item.getData())));
        // give the blocks some velocity
        if (entity != null) {
            // get distance form the center + 1, to avoid division by zero
            double dist = impactLoc.distance(loc) + 1;
            // calculate veloctiy away from the impact
            org.bukkit.util.Vector vect = loc.clone().subtract(impactLoc).toVector().multiply(entityVelocity / dist);
            // set the entity velocity
            entity.setVelocity(vect);
            // set some other properties
            entity.setDropItem(false);
        } else {
            plugin.logSevere(((("Item id:" + item.getId()) + " data:") + item.getData()) + " can't be spawned as falling block.");
        }
    }

    /**
     * performs the entity placing on the given location
     *
     * @param impactLoc
     * 		
     * @param loc
     * 		
     * @param cannonball
     * 		
     */
    private void makeBlockPlace(org.bukkit.Location impactLoc, org.bukkit.Location loc, at.pavlov.cannons.projectile.FlyingProjectile cannonball) {
        at.pavlov.cannons.projectile.Projectile projectile = cannonball.getProjectile();
        if (canPlaceBlock(loc.getBlock())) {
            if (checkLineOfSight(impactLoc, loc) == 0) {
                if (projectile == null) {
                    plugin.logSevere("no projectile data in flyingprojectile for makeBlockPlace");
                    return;
                }
                for (at.pavlov.cannons.container.MaterialHolder placeBlock : projectile.getBlockPlaceList()) {
                    // check if Material is a mob egg
                    if (placeBlock.equals(org.bukkit.Material.MONSTER_EGG)) {
                        // else place mob
                        PlaceMob(impactLoc, loc, projectile.getBlockPlaceVelocity(), placeBlock.getData());
                    } else {
                        spawnFallingBlock(impactLoc, loc, projectile.getBlockPlaceVelocity(), placeBlock);
                    }
                }
            }
        }
    }

    /**
     * performs the block spawning for the given projectile
     *
     * @param impactLoc
     * 		
     * @param cannonball
     * 		
     */
    private void spreadBlocks(org.bukkit.Location impactLoc, at.pavlov.cannons.projectile.FlyingProjectile cannonball) {
        at.pavlov.cannons.projectile.Projectile projectile = cannonball.getProjectile();
        if (projectile.doesBlockPlace() == true) {
            java.util.Random r = new java.util.Random();
            org.bukkit.Location loc;
            double spread = projectile.getBlockPlaceRadius();
            // add some randomness to the amount of spawned blocks
            int maxPlacement = ((int) (projectile.getBlockPlaceAmount() * (1 + r.nextGaussian())));
            // iterate blocks around to get a good place
            int placedBlocks = 0;
            int iterations1 = 0;
            do {
                iterations1++;
                loc = impactLoc.clone();
                // get new position
                loc.setX(loc.getX() + ((r.nextGaussian() * spread) / 2));
                loc.setZ(loc.getZ() + ((r.nextGaussian() * spread) / 2));
                // check a entity can spawn on this block
                if (canPlaceBlock(loc.getBlock())) {
                    placedBlocks++;
                    // place the block
                    makeBlockPlace(impactLoc, loc, cannonball);
                }
            } while ((iterations1 < maxPlacement) && (placedBlocks < maxPlacement) );
        }
    }

    /**
     * returns true if an entity can be place on this block
     *
     * @param block
     * 		
     * @return 
     */
    private boolean canPlaceBlock(org.bukkit.block.Block block) {
        return ((block.getType() == org.bukkit.Material.AIR) || (block.getType() == org.bukkit.Material.FIRE)) || block.isLiquid();
    }

    // ####################################  checkLineOfSight ##############################
    private int checkLineOfSight(org.bukkit.Location impact, org.bukkit.Location target) {
        int blockingBlocks = 0;
        // vector pointing from impact to target
        org.bukkit.util.Vector vect = target.toVector().clone().subtract(impact.toVector());
        int length = ((int) (java.lang.Math.ceil(vect.length())));
        vect.normalize();
        org.bukkit.Location impactClone = impact.clone();
        for (int i = 2; i <= length; i++) {
            // check if line of sight is blocked
            if (impactClone.add(vect).getBlock().getType() != org.bukkit.Material.AIR) {
                blockingBlocks++;
            }
        }
        return blockingBlocks;
    }

    // ####################################  doPlayerDamage ##############################
    private void applyPotionEffect(org.bukkit.Location impactLoc, org.bukkit.entity.Entity next, at.pavlov.cannons.projectile.FlyingProjectile cannonball) {
        at.pavlov.cannons.projectile.Projectile projectile = cannonball.getProjectile();
        double dist = impactLoc.distanceSquared(next.getLocation());
        // if the entity is too far away, return
        if (dist > projectile.getPotionRange())
            return;

        // duration of the potion effect
        double duration = projectile.getPotionDuration() * 20;
        if (next instanceof org.bukkit.entity.LivingEntity) {
            // calc damage
            org.bukkit.entity.LivingEntity living = ((org.bukkit.entity.LivingEntity) (next));
            // check line of sight and reduce damage if the way is blocked
            int blockingBlocks = checkLineOfSight(impactLoc, living.getEyeLocation());
            duration = duration / (blockingBlocks + 1);
            // critical
            java.util.Random r = new java.util.Random();
            int crit = r.nextInt(10);
            if (crit == 0)
                duration *= 3;

            // apply potion effect if the duration is not small then 1 tick
            if (duration >= 1) {
                int intDuration = ((int) (java.lang.Math.floor(duration)));
                for (org.bukkit.potion.PotionEffectType potionEffect : projectile.getPotionsEffectList()) {
                    // apply to entity
                    potionEffect.createEffect(intDuration, projectile.getPotionAmplifier()).apply(living);
                }
            }
        }
    }

    // ####################################  CREATE_EXPLOSION ##############################
    public void detonate(at.pavlov.cannons.projectile.FlyingProjectile cannonball) {
        at.pavlov.cannons.projectile.Projectile projectile = cannonball.getProjectile().clone();
        org.bukkit.entity.Snowball snowball = cannonball.getSnowball();
        org.bukkit.entity.LivingEntity shooter = snowball.getShooter();
        org.bukkit.entity.Player player = null;
        if (shooter instanceof org.bukkit.entity.Player)
            player = ((org.bukkit.entity.Player) (shooter));

        // blocks from the impact of the projectile to the location of the explosion
        org.bukkit.Location impactLoc = blockBreaker(cannonball);
        // get world
        org.bukkit.World world = impactLoc.getWorld();
        // teleport snowball to impact
        snowball.teleport(impactLoc);
        float explosion_power = ((float) (projectile.getExplosionPower()));
        // find living entities
        java.util.List<org.bukkit.entity.Entity> entity;
        // fire impact event
        at.pavlov.cannons.event.ProjectileImpactEvent impactEvent = new at.pavlov.cannons.event.ProjectileImpactEvent(projectile, impactLoc);
        org.bukkit.Bukkit.getServer().getPluginManager().callEvent(impactEvent);
        // if canceled then exit
        if (impactEvent.isCancelled()) {
            // event canceled, make a save fake explosion
            world.createExplosion(impactLoc.getX(), impactLoc.getY(), impactLoc.getZ(), 0);
            return;
        }
        // explosion event
        boolean incendiary = projectile.hasProperty(ProjectileProperties.INCENDIARY);
        boolean blockDamage = projectile.getExplosionDamage();
        boolean canceled = world.createExplosion(impactLoc.getX(), impactLoc.getY(), impactLoc.getZ(), explosion_power, incendiary, blockDamage);
        // send a message about the impact
        plugin.displayImpactMessage(player, impactLoc, canceled);
        if (canceled == true) {
            // place blocks around the impact like webs, lava, water
            spreadBlocks(impactLoc, cannonball);
            // do potion effects
            int effectRange = ((int) (projectile.getPotionRange())) / 2;
            entity = snowball.getNearbyEntities(effectRange, effectRange, effectRange);
            java.util.Iterator<org.bukkit.entity.Entity> it = entity.iterator();
            while (it.hasNext()) {
                org.bukkit.entity.Entity next = it.next();
                applyPotionEffect(impactLoc, next, cannonball);
            } 
            // teleport to impact
            if (cannonball.getProjectile().hasProperty(ProjectileProperties.TELEPORT) == true) {
                // teleport shooter to impact
                if (shooter != null)
                    shooter.teleport(impactLoc);

            }
            // check which entities are affected by the event
            java.util.List<org.bukkit.entity.Entity> EntitiesAfterExplosion = snowball.getNearbyEntities(effectRange, effectRange, effectRange);
            transmittingEntities(EntitiesAfterExplosion, snowball.getShooter());// place blocks around the impact like webs, lava, water

            spreadBlocks(impactLoc, cannonball);
        }
    }

    // ####################################  transmittingEntities  ##############################
    private void transmittingEntities(java.util.List<org.bukkit.entity.Entity> after, org.bukkit.entity.Entity shooter) {
        // exit now
        shooter = null;
        // check if there is a shooter, redstone cannons are not counted
        if (shooter == null)
            return;

        if (!(shooter instanceof org.bukkit.entity.Player))
            return;

        // return if the list before is empty
        if (after.size() == 0)
            return;

        // calculate distance form the shooter location to the first monster
        double distance = 0.0;
        // check which entities have been killed
        java.util.List<org.bukkit.entity.LivingEntity> killedEntities = new java.util.LinkedList<org.bukkit.entity.LivingEntity>();
        java.util.Iterator<org.bukkit.entity.Entity> iter = after.iterator();
        while (iter.hasNext()) {
            org.bukkit.entity.Entity entity = iter.next();
            if (entity instanceof org.bukkit.entity.LivingEntity) {
                // killed by the explosion
                if (entity.isDead() == true) {
                    org.bukkit.entity.LivingEntity LivEntity = ((org.bukkit.entity.LivingEntity) (entity));
                    // check if the entity has not been transmitted
                    if (hasBeenTransmitted(LivEntity.getUniqueId()) == false) {
                        // calculate distance form the shooter location to the first monster
                        distance = shooter.getLocation().distance(LivEntity.getLocation());
                        killedEntities.add(LivEntity);
                        transmittedEntities.add(LivEntity.getUniqueId());
                    }
                }
            }
        } 
        // list should not be empty
        if (killedEntities.size() > 0) {
            try {
                // handler.updateGunnerReputation((Player) shooter, killedEntities, distance);
            } catch (java.lang.Exception e) {
                plugin.logSevere("Error adding reputation to player");
            }
        }
    }

    // ############### hasBeenTransmitted ########################
    private boolean hasBeenTransmitted(java.util.UUID id) {
        java.util.ListIterator<java.util.UUID> iter = transmittedEntities.listIterator(transmittedEntities.size());
        while (iter.hasPrevious()) {
            if (iter.previous() == id)
                return true;

        } 
        // has not been transmitted
        return false;
    }

    // ############### deleteTransmittedEntities ##################
    public void deleteTransmittedEntities() {
        transmittedEntities = new java.util.LinkedList<java.util.UUID>();
    }
}