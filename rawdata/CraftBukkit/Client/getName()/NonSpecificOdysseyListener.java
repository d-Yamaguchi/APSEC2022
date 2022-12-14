/*
 *  Copyright 2013 eccentric_nz.
 */
package me.eccentric_nz.nonspecificodyssey;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author eccentric_nz
 */
public class NonSpecificOdysseyListener implements Listener {

    private final NonSpecificOdyssey plugin;
    List<String> travellers = new ArrayList<String>();
    List<String> hasClicked = new ArrayList<String>();
    String firstline;

    public NonSpecificOdysseyListener(NonSpecificOdyssey plugin) {
        this.plugin = plugin;
        this.firstline = plugin.getConfig().getString("firstline");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerSuffocate(EntityDamageByBlockEvent event) {
        Entity e = event.getEntity();
        if (e instanceof Player && event.getCause().equals(DamageCause.SUFFOCATION)) {
            Player p = (Player) e;
            String name = p.getName();
            if (travellers.contains(name)) {
                Location l = p.getLocation();
                double y = l.getWorld().getHighestBlockYAt(l);
                l.setY(y);
                p.teleport(l);
                travellers.remove(name);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block b = event.getClickedBlock();
        if (b != null && (b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN))) {
            Sign sign = (Sign) b.getState();
            String nsoline = ChatColor.stripColor(sign.getLine(0));
            if (nsoline.equalsIgnoreCase("[" + firstline + "]")) {
                final Player p = event.getPlayer();
                if (p.hasPermission("nonspecificodyssey.sign")) {
                    final String name = p.getName();
                    if (!hasClicked.contains(name)) {
                        hasClicked.add(name);
                        final World w = b.getWorld();
                        if (p.isSneaking() && p.isOp()) {
                            b.setType(Material.AIR);
                            w.dropItemNaturally(b.getLocation(), new ItemStack(Material.SIGN, 1));
                            hasClicked.remove(name);
                        } else {
                            // check the other lines
                            String world_line = ChatColor.stripColor(sign.getLine(2));
                            World the_world;
                            if (plugin.getServer().getWorld(world_line) != null) {
                                the_world = plugin.getServer().getWorld(world_line);
                            } else {
                                the_world = w;
                            }
                            Location the_location;
                            Biome biome = checkBiomeLine(sign.getLine(3).toUpperCase());
                            if (biome != null) {
                                the_location = plugin.getCommando().searchBiome(p, biome, the_world);
                            } else {
                                the_location = plugin.getCommando().randomOverworldLocation(the_world);
                            }
                            final Location random = the_location;
                            if (random != null) {
                                p.sendMessage(ChatColor.GOLD + "[" + plugin.getPluginName() + "] " + ChatColor.RESET + "Standby for random teleport...");
                                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                    @Override
                                    public void run() {
                                        plugin.getCommando().movePlayer(p, random, w);
                                    }
                                }, 40L);
                            } else {
                                p.sendMessage(ChatColor.GOLD + "[" + plugin.getPluginName() + "] " + ChatColor.RESET + "Location finding timed out, most likely the biome couldn't be found!");
                            }
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    hasClicked.remove(name);
                                }
                            }, 80L);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        String nsoline = ChatColor.stripColor(event.getLine(0));
        if (nsoline.equalsIgnoreCase("[" + firstline + "]") && !player.hasPermission("nonspecificodyssey.admin")) {
            event.setLine(0, "");
            player.sendMessage(ChatColor.GOLD + "[" + plugin.getPluginName() + "] " + ChatColor.RESET + "You do not have permission to create teleport signs!");
        }
    }

    public Biome checkBiomeLine(String str) {
        int start = (str.startsWith(String.valueOf(ChatColor.COLOR_CHAR))) ? 2 : 0;
        int end = (str.startsWith(String.valueOf(ChatColor.COLOR_CHAR))) ? 12 : 15;
        for (Biome b : Biome.values()) {
            if (b.toString().length() > 15) {
                if (b.toString().substring(0, end).equals(str.substring(start))) {
                    return b;
                }
            }
        }
        try {
            return Biome.valueOf(str);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
