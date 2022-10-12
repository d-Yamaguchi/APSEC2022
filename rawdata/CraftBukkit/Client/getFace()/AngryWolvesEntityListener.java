
package com.mikeprimm.bukkit.AngryWolves;

import org.bukkit.entity.CreatureType;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.Material;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
/**
 * Entity listener - listen for spawns of wolves
 * @author MikePrimm
 */
public class AngryWolvesEntityListener extends EntityListener {
    private final AngryWolves plugin;
    private final Random rnd = new Random(System.currentTimeMillis());
    private Map<String, Long> msg_ts_by_world = new HashMap<String, Long>();
    private static final long SPAM_TIMER = 60000;
    private static Set<Integer> hellhound_ids = new HashSet<Integer>();
    private static final int HELLHOUND_FIRETICKS = 60*20;	/* Do 60 seconds at a time */
    
    public AngryWolvesEntityListener(final AngryWolves plugin) {
        this.plugin = plugin;
    }

    private static class DoSpawn implements Runnable {
    	Location loc;
    	Player tgt;
    	boolean is_hellhound;
    	public void run() {
    		Wolf w = (Wolf)loc.getWorld().spawnCreature(loc, CreatureType.WOLF);
    		if(w != null) {
    			w.setAngry(true);
    			if(tgt != null)
    				w.setTarget(tgt);
    			if(is_hellhound) {
    				hellhound_ids.add(Integer.valueOf(w.getEntityId()));	/* Add to table */
    				w.setFireTicks(HELLHOUND_FIRETICKS);	/* Set it on fire */
    			}
    		}
    	}
    }
    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event) {
    	if(event.isCancelled())
    		return;
    	Location loc = event.getLocation();
    	boolean did_it = false;
    	CreatureType ct = event.getCreatureType();
    	AngryWolves.BaseConfig cfg = null;
    	/* If monster spawn */
    	if(ct.equals(CreatureType.ZOMBIE) || ct.equals(CreatureType.CREEPER) ||
    		ct.equals(CreatureType.SPIDER) || ct.equals(CreatureType.SKELETON) ||
    		ct.equals(CreatureType.PIG_ZOMBIE)) {
    		/* Find configuration for our location */
    		cfg = plugin.findByLocation(loc);
    		//plugin.log.info("mob: " + cfg);
    		int rate = cfg.getMobToWolfRate(ct);
    		if(plugin.verbose) AngryWolves.log.info("mobrate(" + ct + ")=" + rate);
    		/* If so, percentage is relative to population of monsters (percent * 10% is chance we grab */
    		if((rate > 0) && (rnd.nextInt(1000) < rate)) {
    			boolean ignore_terrain = cfg.getMobToWolfTerrainIgnore();	/* See if we're ignoring terrain */
        		Block b = loc.getBlock();
        		Biome bio = b.getBiome();
        		if(plugin.verbose) AngryWolves.log.info("biome=" + bio + ", ignore=" + ignore_terrain);
        		/* See if hellhound - only hellhounds substitute in Nether, use hellhound_rate elsewhere */
        		boolean do_hellhound = (bio.equals(Biome.HELL) || (rnd.nextInt(100) <= cfg.getHellhoundRate()));
        		/* If valid biome for wolf (or hellhound) */
        		if(ignore_terrain || bio.equals(Biome.FOREST) || bio.equals(Biome.TAIGA) || bio.equals(Biome.SEASONAL_FOREST) ||
        				bio.equals(Biome.HELL)) {
    				/* If hellhound in hell, we're good */
    				if(bio.equals(Biome.HELL)) {
    					
    				}
    				else if(!ignore_terrain) {
        				while((b != null) && (b.getType().equals(Material.AIR))) {
        					b = b.getFace(BlockFace.DOWN);
        				}
        				/* Quit if we're not over soil */
        				if((b == null) || (!b.getType().equals(Material.GRASS))) {
        					if(plugin.verbose) AngryWolves.log.info("material=" + b.getType()); 
        					return;
        				}
        			}
    				event.setCancelled(true);
    				DoSpawn ds = new DoSpawn();
    				ds.loc = loc;
    				ds.is_hellhound = do_hellhound;
    				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ds);
    				did_it = true;
    				if(plugin.verbose) AngryWolves.log.info("Mapped " + ct + " spawn to angry wolf");
    			}
    		}
    	}
    	else if(ct.equals(CreatureType.WOLF)) {
    		Wolf w = (Wolf)event.getEntity();
    		/* If not angry and not tame  */
    		if((w.isAngry() == false) && (plugin.isTame(w) == false) && (!plugin.isNormalSpawn())) {
        		cfg = plugin.findByLocation(loc);
        		//plugin.log.info("wolf: " + cfg);
    			int rate = cfg.getSpawnAngerRate();
    			int fmrate = cfg.getSpawnAngerRateMoon();
    			/* If higher rate during full moon, check if we're having one */
    			if((fmrate > rate) && plugin.isFullMoon(loc.getWorld())) {	
    				rate = fmrate;
    			}
    			if((rate > 0) && (rnd.nextInt(100) <= rate)) {
    				w.setAngry(true);
    				/* See if it is a hellhound! */
    				rate = cfg.getHellhoundRate();
    				if((rate > 0) && (rnd.nextInt(100) <= rate)) {
    					hellhound_ids.add(w.getEntityId());
    					w.setFireTicks(HELLHOUND_FIRETICKS);
        				if(plugin.verbose) AngryWolves.log.info("Made a spawned wolf into a hellhound");
    				}
    				else {
    					if(plugin.verbose) AngryWolves.log.info("Made a spawned wolf angry");
    				}
    				did_it = true;
    			}
    		}
    	}
    	if(did_it) {
    		/* And get our spawn message */
    		String sm = cfg.getSpawnMsg();
    		double radius = (double)cfg.getSpawnMsgRadius();
			if((sm != null) && (sm.length() > 0)) {
				/* See if too soon (avoid spamming these messages) */
				Long last = msg_ts_by_world.get(loc.getWorld().getName());
				if((last == null) || ((last.longValue() + SPAM_TIMER) < System.currentTimeMillis())) {
					msg_ts_by_world.put(loc.getWorld().getName(), Long.valueOf(System.currentTimeMillis()));
					List<Player> pl = loc.getWorld().getPlayers();
					for(Player p : pl) {
						if(radius > 0.0) {	/* If radius limit, see if player is close enough */
							Location ploc = p.getLocation();
							double dx = ploc.getX() - loc.getX();
							double dy = ploc.getY() - loc.getY();
							double dz = ploc.getZ() - loc.getZ();
							if(((dx*dx) + (dy*dy) + (dz*dz)) <= (radius*radius)) {
								p.sendMessage(sm);
							}
						}
						else
							p.sendMessage(sm);
					}
				}
  			}
    	}
    }
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
    	if(event.isCancelled())
    		return;
		Entity e = event.getEntity();
    	/* If fire damage, see if it is a hellhound */
    	if(hellhound_ids.contains(e.getEntityId())) {
    		e.setFireTicks(HELLHOUND_FIRETICKS);
        	DamageCause dc = event.getCause();
        	if((dc == DamageCause.FIRE_TICK) || (dc == DamageCause.FIRE) || (dc == DamageCause.LAVA)) {
    			event.setCancelled(true);	/* Cancel it - we're fireproof! */
    			return;
    		}
    	}
    	if(!(event instanceof EntityDamageByEntityEvent))
    		return;
    	EntityDamageByEntityEvent evt = (EntityDamageByEntityEvent)event;
    	Entity damager = evt.getDamager();
    	if(damager instanceof Player) {
    		Player p = (Player)damager;
    		/* See if its a sheep */
    		if(!(e instanceof Sheep))
    			return;
    		Sheep s = (Sheep)e;
    		Location loc = s.getLocation();
    		AngryWolves.BaseConfig cfg = plugin.findByLocation(loc);
    		//plugin.log.info("sheep: " + cfg);
    		int rate = cfg.getWolfInSheepRate();
    	
    		/* Use hashcode - random enough, and makes it so that something damaged
    		 * once will trigger, or never will, even if damaged again */
    		if(new Random(e.hashCode()).nextInt(1000) >= rate)
    			return;
    		String msg = cfg.getWolfInSheepMsg();
    		if((msg != null) && (msg.length() > 0))
    			p.sendMessage(cfg.getWolfInSheepMsg());
    		evt.setCancelled(true);	/* Cancel event */
    		e.remove();	/* Remove the sheep */
    	
    		DoSpawn ds = new DoSpawn();
    		ds.loc = loc;
    		ds.tgt = p;
    		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ds);
    		if(plugin.verbose) AngryWolves.log.info("Made attacked sheep into angry wolf"); 
    	}
    	else if(damager instanceof Wolf) {
    		if(!(e instanceof Player)) {	/* Not a player - don't worry */
    			return;
    		}
    		/* If we don't do wolf-friends here, skip it (check based on player's location) */
    		AngryWolves.BaseConfig cfg = plugin.findByLocation(e.getLocation());
    		//plugin.log.info("wolffriend: " + cfg);
    		if(cfg.getWolfFriendActive() == false) {
    			return;
    		}
    		if(AngryWolvesPermissions.permission((Player)e, AngryWolves.WOLF_FRIEND_PERM)) {
    			event.setCancelled(true);	/* Cancel it */
    			((Wolf)damager).setTarget(null);	/* Target someone else */
    			if(plugin.verbose) AngryWolves.log.info("Cancelled attack on wolf-friend"); 
    		}
    	}
    }
    @Override
    public void onEntityTarget(EntityTargetEvent event) {
    	if(event.isCancelled())
    		return;
    	Entity e = event.getEntity();
    	if(!(e instanceof Wolf))	/* Don't care about non-wolves */
    		return;
    	if(hellhound_ids.contains(e.getEntityId()))
    		e.setFireTicks(HELLHOUND_FIRETICKS);
    	Entity t = event.getTarget();
    	if(!(t instanceof Player)) 	/* Don't worry about non-player targets */
    		return;
    	Player p = (Player)t;
    	/* If we don't do wolf-friends here, skip it (check based on player's location) */
    	AngryWolves.BaseConfig cfg = plugin.findByLocation(p.getLocation());
		if(cfg.getWolfFriendActive() == false) {
			return;
		}
    	if(AngryWolvesPermissions.permission(p, AngryWolves.WOLF_FRIEND_PERM)) {	/* If player is a wolf-friend */
    		event.setCancelled(true);	/* Cancel it - not a valid target */
    		if(plugin.verbose) AngryWolves.log.info("Cancelled target on wolf friend");
    	}
    }
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        Entity e = event.getEntity();
        if(!(e instanceof Wolf))    /* Don't care about non-wolves */
            return;
        AngryWolves.BaseConfig cfg = plugin.findByLocation(e.getLocation());    /* Get our configuration for location */
        if(cfg.getWolfLootRate() > rnd.nextInt(100)) {
            List<Integer> loot = cfg.getWolfLoot();
            int sz = loot.size();
            if(sz > 0) {
                int id = loot.get(rnd.nextInt(sz));
                e.getWorld().dropItemNaturally(e.getLocation(), new ItemStack(id, 1));
            }
        }
        /* Forget the dead hellhound */
        hellhound_ids.remove(e.getEntityId());
    }    
}
