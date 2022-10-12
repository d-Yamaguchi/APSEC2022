/* Copyright 2013 eccentric_nz. */
package me.eccentric_nz.nonspecificodyssey;
/**
 *
 *
 * @author eccentric_nz
 */
public class NonSpecificOdysseyListener implements org.bukkit.event.Listener {
    private final me.eccentric_nz.nonspecificodyssey.NonSpecificOdyssey plugin;

    java.util.List<java.lang.String> travellers = new java.util.ArrayList<java.lang.String>();

    java.util.List<java.lang.String> hasClicked = new java.util.ArrayList<java.lang.String>();

    java.lang.String firstline;

    public NonSpecificOdysseyListener(me.eccentric_nz.nonspecificodyssey.NonSpecificOdyssey plugin) {
        this.plugin = plugin;
        this.firstline = plugin.getConfig().getString("firstline");
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
    public void onPlayerSuffocate(org.bukkit.event.entity.EntityDamageByBlockEvent event) {
        org.bukkit.entity.Entity e = event.getEntity();
        if ((e instanceof org.bukkit.entity.Player) && event.getCause().equals(org.bukkit.event.entity.EntityDamageEvent.DamageCause.SUFFOCATION)) {
            org.bukkit.entity.Player p = ((org.bukkit.entity.Player) (e));
            java.lang.String name = store.loadAchievements(mPlayer);
            if (travellers.contains(name)) {
                org.bukkit.Location l = p.getLocation();
                double y = l.getWorld().getHighestBlockYAt(l);
                l.setY(y);
                p.teleport(l);
                travellers.remove(name);
            }
        }
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL)
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        org.bukkit.block.Block b = event.getClickedBlock();
        if ((b != null) && (b.getType().equals(org.bukkit.Material.SIGN_POST) || b.getType().equals(org.bukkit.Material.WALL_SIGN))) {
            org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (b.getState()));
            java.lang.String nsoline = org.bukkit.ChatColor.stripColor(sign.getLine(0));
            if (nsoline.equalsIgnoreCase(("[" + firstline) + "]")) {
                final org.bukkit.entity.Player p = event.getPlayer();
                if (p.hasPermission("nonspecificodyssey.sign")) {
                    final java.lang.String name = p.getName();
                    if (!hasClicked.contains(name)) {
                        hasClicked.add(name);
                        final org.bukkit.World w = b.getWorld();
                        if (p.isSneaking() && p.isOp()) {
                            b.setType(org.bukkit.Material.AIR);
                            w.dropItemNaturally(b.getLocation(), new org.bukkit.inventory.ItemStack(org.bukkit.Material.SIGN, 1));
                            hasClicked.remove(name);
                        } else {
                            // check the other lines
                            java.lang.String world_line = org.bukkit.ChatColor.stripColor(sign.getLine(2));
                            org.bukkit.World the_world;
                            if (plugin.getServer().getWorld(world_line) != null) {
                                the_world = plugin.getServer().getWorld(world_line);
                            } else {
                                the_world = w;
                            }
                            org.bukkit.Location the_location;
                            org.bukkit.block.Biome biome = checkBiomeLine(sign.getLine(3).toUpperCase());
                            if (biome != null) {
                                the_location = plugin.getCommando().searchBiome(p, biome, the_world);
                            } else {
                                the_location = plugin.getCommando().randomOverworldLocation(the_world);
                            }
                            final org.bukkit.Location random = the_location;
                            if (random != null) {
                                p.sendMessage(((((org.bukkit.ChatColor.GOLD + "[") + plugin.getPluginName()) + "] ") + org.bukkit.ChatColor.RESET) + "Standby for random teleport...");
                                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new java.lang.Runnable() {
                                    @java.lang.Override
                                    public void run() {
                                        plugin.getCommando().movePlayer(p, random, w);
                                    }
                                }, 40L);
                            } else {
                                p.sendMessage(((((org.bukkit.ChatColor.GOLD + "[") + plugin.getPluginName()) + "] ") + org.bukkit.ChatColor.RESET) + "Location finding timed out, most likely the biome couldn't be found!");
                            }
                            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new java.lang.Runnable() {
                                @java.lang.Override
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

    @org.bukkit.event.EventHandler
    public void onSignChange(org.bukkit.event.block.SignChangeEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        java.lang.String nsoline = org.bukkit.ChatColor.stripColor(event.getLine(0));
        if (nsoline.equalsIgnoreCase(("[" + firstline) + "]") && (!player.hasPermission("nonspecificodyssey.admin"))) {
            event.setLine(0, "");
            player.sendMessage(((((org.bukkit.ChatColor.GOLD + "[") + plugin.getPluginName()) + "] ") + org.bukkit.ChatColor.RESET) + "You do not have permission to create teleport signs!");
        }
    }

    public org.bukkit.block.Biome checkBiomeLine(java.lang.String str) {
        int start = (str.startsWith(java.lang.String.valueOf(org.bukkit.ChatColor.COLOR_CHAR))) ? 2 : 0;
        int end = (str.startsWith(java.lang.String.valueOf(org.bukkit.ChatColor.COLOR_CHAR))) ? 12 : 15;
        for (org.bukkit.block.Biome b : org.bukkit.block.Biome.values()) {
            if (b.toString().length() > 15) {
                if (b.toString().substring(0, end).equals(str.substring(start))) {
                    return b;
                }
            }
        }
        try {
            return org.bukkit.block.Biome.valueOf(str);
        } catch (java.lang.IllegalArgumentException e) {
            return null;
        }
    }
}