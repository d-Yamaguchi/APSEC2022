@java.lang.Override
public void onCreatureSpawn(org.bukkit.event.entity.CreatureSpawnEvent event) {
    if (event.isCancelled()) {
        return;
    }
    org.bukkit.Location loc = event.getLocation();
    boolean did_it = false;
    org.bukkit.entity.CreatureType ct = event.getCreatureType();
    com.mikeprimm.bukkit.AngryWolves.AngryWolves.BaseConfig cfg = null;
    /* If monster spawn */
    if ((((ct.equals(org.bukkit.entity.CreatureType.ZOMBIE) || ct.equals(org.bukkit.entity.CreatureType.CREEPER)) || ct.equals(org.bukkit.entity.CreatureType.SPIDER)) || ct.equals(org.bukkit.entity.CreatureType.SKELETON)) || ct.equals(org.bukkit.entity.CreatureType.PIG_ZOMBIE)) {
        /* Find configuration for our location */
        cfg = plugin.findByLocation(loc);
        // plugin.log.info("mob: " + cfg);
        int rate = cfg.getMobToWolfRate(ct);
        if (plugin.verbose) {
            AngryWolves.log.info((("mobrate(" + ct) + ")=") + rate);
        }
        /* If so, percentage is relative to population of monsters (percent * 10% is chance we grab */
        if ((rate > 0) && (rnd.nextInt(1000) < rate)) {
            boolean ignore_terrain = cfg.getMobToWolfTerrainIgnore();/* See if we're ignoring terrain */

            org.bukkit.block.Block b = loc.getBlock();
            org.bukkit.block.Biome bio = b.getBiome();
            if (plugin.verbose) {
                AngryWolves.log.info((("biome=" + bio) + ", ignore=") + ignore_terrain);
            }
            /* See if hellhound - only hellhounds substitute in Nether, use hellhound_rate elsewhere */
            boolean do_hellhound = bio.equals(org.bukkit.block.Biome.HELL) || (rnd.nextInt(100) <= cfg.getHellhoundRate());
            /* If valid biome for wolf (or hellhound) */
            if ((((ignore_terrain || bio.equals(org.bukkit.block.Biome.FOREST)) || bio.equals(org.bukkit.block.Biome.TAIGA)) || bio.equals(org.bukkit.block.Biome.SEASONAL_FOREST)) || bio.equals(org.bukkit.block.Biome.HELL)) {
                /* If hellhound in hell, we're good */
                if (bio.equals(org.bukkit.block.Biome.HELL)) {
                } else if (!ignore_terrain) {
                    while ((b != null) && b.getType().equals(org.bukkit.Material.AIR)) {
                        b = b.getFace(org.bukkit.block.BlockFace.DOWN);
                    } 
                    /* Quit if we're not over soil */
                    if ((b == null) || (!b.getType().equals(org.bukkit.Material.GRASS))) {
                        if (plugin.verbose) {
                            AngryWolves.log.info("material=" + b.getType());
                        }
                        return;
                    }
                }
                event.setCancelled(true);
                com.mikeprimm.bukkit.AngryWolves.AngryWolvesEntityListener.DoSpawn ds = new com.mikeprimm.bukkit.AngryWolves.AngryWolvesEntityListener.DoSpawn();
                ds.loc = loc;
                ds.is_hellhound = do_hellhound;
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ds);
                did_it = true;
                if (plugin.verbose) {
                    AngryWolves.log.info(("Mapped " + ct) + " spawn to angry wolf");
                }
            }
        }
    } else if (ct.equals(org.bukkit.entity.CreatureType.WOLF)) {
        org.bukkit.entity.Wolf w = ((org.bukkit.entity.Wolf) (event.getEntity()));
        /* If not angry and not tame */
        if (((w.isAngry() == false) && (plugin.isTame(w) == false)) && (!plugin.isNormalSpawn())) {
            cfg = plugin.findByLocation(loc);
            // plugin.log.info("wolf: " + cfg);
            int rate = cfg.getSpawnAngerRate();
            int fmrate = cfg.getSpawnAngerRateMoon();
            /* If higher rate during full moon, check if we're having one */
            if ((fmrate > rate) && plugin.isFullMoon(loc.getWorld())) {
                rate = fmrate;
            }
            if ((rate > 0) && (rnd.nextInt(100) <= rate)) {
                w.setAngry(true);
                /* See if it is a hellhound! */
                rate = cfg.getHellhoundRate();
                if ((rate > 0) && (rnd.nextInt(100) <= rate)) {
                    com.mikeprimm.bukkit.AngryWolves.AngryWolvesEntityListener.hellhound_ids.add(w.getEntityId());
                    w.setFireTicks(com.mikeprimm.bukkit.AngryWolves.AngryWolvesEntityListener.HELLHOUND_FIRETICKS);
                    if (plugin.verbose) {
                        AngryWolves.log.info("Made a spawned wolf into a hellhound");
                    }
                } else if (plugin.verbose) {
                    AngryWolves.log.info("Made a spawned wolf angry");
                }
                did_it = true;
            }
        }
    }
    if (did_it) {
        /* And get our spawn message */
        java.lang.String sm = cfg.getSpawnMsg();
        double radius = ((double) (cfg.getSpawnMsgRadius()));
        if ((sm != null) && (sm.length() > 0)) {
            /* See if too soon (avoid spamming these messages) */
            java.lang.Long last = msg_ts_by_world.get(loc.getWorld().getName());
            if ((last == null) || ((last.longValue() + com.mikeprimm.bukkit.AngryWolves.AngryWolvesEntityListener.SPAM_TIMER) < java.lang.System.currentTimeMillis())) {
                msg_ts_by_world.put(loc.getWorld().getName(), java.lang.Long.valueOf(java.lang.System.currentTimeMillis()));
                java.util.List<org.bukkit.entity.Player> pl = loc.getWorld().getPlayers();
                for (org.bukkit.entity.Player p : pl) {
                    if (radius > 0.0) {
                        /* If radius limit, see if player is close enough */
                        org.bukkit.Location ploc = p.getLocation();
                        double dx = ploc.getX() - loc.getX();
                        double dy = ploc.getY() - loc.getY();
                        double dz = ploc.getZ() - loc.getZ();
                        if ((((dx * dx) + (dy * dy)) + (dz * dz)) <= (radius * radius)) {
                            p.sendMessage(sm);
                        }
                    } else {
                        p.sendMessage(sm);
                    }
                }
            }
        }
    }
}