// ####################################  doPlayerDamage ##############################
private void applyPotionEffect(org.bukkit.Location impactLoc, org.bukkit.entity.Entity next, at.pavlov.cannons.projectile.FlyingProjectile cannonball) {
    at.pavlov.cannons.projectile.Projectile projectile = cannonball.getProjectile();
    org.bukkit.entity.Entity _CVAR1 = next;
    org.bukkit.Location _CVAR0 = impactLoc;
    org.bukkit.Location _CVAR2 = _CVAR1.getLocation();
    double dist = _CVAR0.distanceSquared(_CVAR2);
    // if the entity is too far away, return
    if (dist > projectile.getPotionRange()) {
        return;
    }
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
        if (crit == 0) {
            duration *= 3;
        }
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