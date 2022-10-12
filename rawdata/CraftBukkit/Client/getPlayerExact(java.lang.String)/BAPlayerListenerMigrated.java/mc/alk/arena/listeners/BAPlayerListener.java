package mc.alk.arena.listeners;
import mc.alk.arena.BattleArena;
import mc.alk.arena.Defaults;
import mc.alk.arena.Permissions;
import mc.alk.arena.controllers.BattleArenaController;
import mc.alk.arena.controllers.HeroesController;
import mc.alk.arena.controllers.PlayerController;
import mc.alk.arena.controllers.PlayerStoreController;
import mc.alk.arena.controllers.TeleportController;
import mc.alk.arena.controllers.WorldGuardController;
import mc.alk.arena.objects.ArenaPlayer;
import mc.alk.arena.objects.regions.WorldGuardRegion;
import mc.alk.arena.util.InventoryUtil;
import mc.alk.arena.util.InventoryUtil.PInv;
import mc.alk.arena.util.Log;
import mc.alk.arena.util.MessageUtil;
import mc.alk.arena.util.PermissionsUtil;
import mc.alk.arena.util.ServerUtil;
import mc.alk.arena.util.Util;
import mc.alk.virtualPlayer.VirtualPlayers;
/**
 *
 *
 * @author alkarin
 */
public class BAPlayerListener implements org.bukkit.event.Listener {
    public static java.util.HashSet<java.lang.String> die = new java.util.HashSet<java.lang.String>();

    public static java.util.HashSet<java.lang.String> clearInventory = new java.util.HashSet<java.lang.String>();

    public static java.util.HashMap<java.lang.String, java.lang.Integer> clearWool = new java.util.HashMap<java.lang.String, java.lang.Integer>();

    public static java.util.HashMap<java.lang.String, org.bukkit.Location> tp = new java.util.HashMap<java.lang.String, org.bukkit.Location>();

    public static java.util.HashMap<java.lang.String, java.lang.Integer> expRestore = new java.util.HashMap<java.lang.String, java.lang.Integer>();

    public static java.util.HashMap<java.lang.String, java.lang.Integer> healthRestore = new java.util.HashMap<java.lang.String, java.lang.Integer>();

    public static java.util.HashMap<java.lang.String, java.lang.Integer> hungerRestore = new java.util.HashMap<java.lang.String, java.lang.Integer>();

    public static java.util.HashMap<java.lang.String, java.lang.Integer> magicRestore = new java.util.HashMap<java.lang.String, java.lang.Integer>();

    public static java.util.HashMap<java.lang.String, mc.alk.arena.util.InventoryUtil.PInv> itemRestore = new java.util.HashMap<java.lang.String, mc.alk.arena.util.InventoryUtil.PInv>();

    public static java.util.HashMap<java.lang.String, mc.alk.arena.util.InventoryUtil.PInv> matchItemRestore = new java.util.HashMap<java.lang.String, mc.alk.arena.util.InventoryUtil.PInv>();

    public static java.util.HashMap<java.lang.String, java.util.List<org.bukkit.inventory.ItemStack>> itemRemove = new java.util.HashMap<java.lang.String, java.util.List<org.bukkit.inventory.ItemStack>>();

    public static java.util.HashMap<java.lang.String, org.bukkit.GameMode> gamemodeRestore = new java.util.HashMap<java.lang.String, org.bukkit.GameMode>();

    public static java.util.HashMap<java.lang.String, java.lang.String> messagesOnRespawn = new java.util.HashMap<java.lang.String, java.lang.String>();

    mc.alk.arena.controllers.BattleArenaController bac;

    public BAPlayerListener(mc.alk.arena.controllers.BattleArenaController bac) {
        mc.alk.arena.listeners.BAPlayerListener.die.clear();
        mc.alk.arena.listeners.BAPlayerListener.clearInventory.clear();
        mc.alk.arena.listeners.BAPlayerListener.clearWool.clear();
        mc.alk.arena.listeners.BAPlayerListener.tp.clear();
        mc.alk.arena.listeners.BAPlayerListener.expRestore.clear();
        mc.alk.arena.listeners.BAPlayerListener.itemRestore.clear();
        mc.alk.arena.listeners.BAPlayerListener.matchItemRestore.clear();
        mc.alk.arena.listeners.BAPlayerListener.gamemodeRestore.clear();
        mc.alk.arena.listeners.BAPlayerListener.messagesOnRespawn.clear();
        this.bac = bac;
    }

    /**
     * Why priority.HIGHEST: if an exception happens after we have already set their respawn location,
     * they relog in at a separate time and will not get teleported to the correct place.
     * As a workaround, try to handle this event last.
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        final org.bukkit.entity.Player p = event.getPlayer();
        final java.lang.String name = p.getName();
        if (mc.alk.arena.listeners.BAPlayerListener.clearInventory.remove(name)) {
            mc.alk.arena.util.Log.warn("[BattleArena] clearing inventory for quitting during a match " + p.getName());
            for (org.bukkit.inventory.ItemStack is : p.getInventory().getContents()) {
                if ((is == null) || (is.getType() == org.bukkit.Material.AIR))
                    continue;

                // FileLogger.log("d  itemstack="+ InventoryUtil.getItemString(is));
            }
            for (org.bukkit.inventory.ItemStack is : p.getInventory().getArmorContents()) {
                if ((is == null) || (is.getType() == org.bukkit.Material.AIR))
                    continue;

                // FileLogger.log("d aitemstack="+ InventoryUtil.getItemString(is));
            }
            mc.alk.arena.util.InventoryUtil.clearInventory(p);
        }
        playerReturned(p, null);
    }

    /**
     * Why priority highest, some other plugins try to force respawn the player in spawn(or some other loc)
     * problem is if they have come from the creative world, their game mode gets reset to creative
     * but the other plugin force spawns them at spawn... so they now have creative in an area they shouldnt
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onPlayerRespawn(org.bukkit.event.player.PlayerRespawnEvent event) {
        org.bukkit.entity.Player p = event.getPlayer();
        playerReturned(p, event);
    }

    private void playerReturned(final org.bukkit.entity.Player p, org.bukkit.event.player.PlayerRespawnEvent event) {
        final java.lang.String name = _CVAR0.getName();
        final java.lang.String msg = mc.alk.arena.listeners.BAPlayerListener.messagesOnRespawn.remove(p.getName());
        if (msg != null) {
            mc.alk.arena.util.MessageUtil.sendMessage(p, msg);
        }
        if (mc.alk.arena.listeners.BAPlayerListener.die.remove(name)) {
            mc.alk.arena.util.MessageUtil.sendMessage(p, "&eYou have been killed by the Arena for not being online");
            p.setHealth(0);
            return;
        }
        // / Teleport players, or set respawn point
        if (mc.alk.arena.listeners.BAPlayerListener.tp.containsKey(name)) {
            final org.bukkit.Location loc = mc.alk.arena.listeners.BAPlayerListener.tp.get(name);
            if (loc != null) {
                if (event == null) {
                    org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                        @java.lang.Override
                        public void run() {
                            org.bukkit.entity.Player pl = mc.alk.arena.util.ServerUtil.findPlayerExact(name);
                            if (pl != null) {
                                mc.alk.arena.controllers.TeleportController.teleport(p, mc.alk.arena.listeners.BAPlayerListener.tp.remove(name));
                            } else {
                                mc.alk.arena.util.Util.printStackTrace();
                            }
                        }
                    });
                } else {
                    mc.alk.arena.util.PermissionsUtil.givePlayerInventoryPerms(p);
                    event.setRespawnLocation(mc.alk.arena.listeners.BAPlayerListener.tp.remove(name));
                    // / Set a timed event to check to make sure the player actually arrived
                    // / Then do a teleport if needed
                    // / This can happen on servers where plugin conflicts prevent the respawn (somehow!!!)
                    if (mc.alk.arena.controllers.HeroesController.enabled()) {
                        org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                            @java.lang.Override
                            public void run() {
                                org.bukkit.entity.Player pl = mc.alk.arena.util.ServerUtil.findPlayerExact(name);
                                if (pl != null) {
                                    if ((pl.getLocation().getWorld().getUID() != loc.getWorld().getUID()) || (pl.getLocation().distanceSquared(loc) > 100)) {
                                        mc.alk.arena.controllers.TeleportController.teleport(p, loc);
                                    }
                                } else {
                                    mc.alk.arena.util.Util.printStackTrace();
                                }
                            }
                        }, 2L);
                    }
                }
            } else {
                // / this is bad, how did they get a null tp loc
                mc.alk.arena.util.Log.err(name + " respawn loc =null");
            }
        }
        // / Do these after teleports
        // / Restore game mode
        if (mc.alk.arena.listeners.BAPlayerListener.gamemodeRestore.containsKey(name)) {
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                    mc.alk.arena.controllers.PlayerStoreController.setGameMode(p, mc.alk.arena.listeners.BAPlayerListener.gamemodeRestore.remove(name));
                }
            });
        }
        // / Exp restore
        if (mc.alk.arena.listeners.BAPlayerListener.expRestore.containsKey(p.getName())) {
            final int exp = mc.alk.arena.listeners.BAPlayerListener.expRestore.remove(p.getName());
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    final java.lang.String name = p.getName();
                    Player pl = ServerUtil.findPlayerExact(name);
                    if (pl != null) {
                        pl.giveExp(exp);
                    }
                }
            });
        }
        // / Health restore
        if (mc.alk.arena.listeners.BAPlayerListener.healthRestore.containsKey(p.getName())) {
            final int val = mc.alk.arena.listeners.BAPlayerListener.healthRestore.remove(p.getName());
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayerExact(name);
                    if (pl != null) {
                        mc.alk.arena.BattleArena.toArenaPlayer(pl).setHealth(val);
                    }
                }
            });
        }
        // / Hunger restore
        if (mc.alk.arena.listeners.BAPlayerListener.hungerRestore.containsKey(p.getName())) {
            final int val = mc.alk.arena.listeners.BAPlayerListener.hungerRestore.remove(p.getName());
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayerExact(name);
                    if (pl != null) {
                        mc.alk.arena.BattleArena.toArenaPlayer(pl).setFoodLevel(val);
                    }
                }
            });
        }
        // / Magic restore
        if (mc.alk.arena.listeners.BAPlayerListener.magicRestore.containsKey(p.getName())) {
            final int val = mc.alk.arena.listeners.BAPlayerListener.magicRestore.remove(p.getName());
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayerExact(name);
                    if (pl != null) {
                        mc.alk.arena.controllers.HeroesController.setMagicLevel(pl, val);
                    }
                }
            });
        }
        // / Restore Items
        if (mc.alk.arena.listeners.BAPlayerListener.itemRestore.containsKey(p.getName())) {
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    org.bukkit.entity.Player pl;
                    if (mc.alk.arena.Defaults.DEBUG_VIRTUAL) {
                        pl = mc.alk.virtualPlayer.VirtualPlayers.getPlayer(name);
                    } else {
                        pl = org.bukkit.Bukkit.getPlayer(name);
                    }
                    // System.out.println("### restoring items to " + name +"   pl = " + pl);
                    if (pl != null) {
                        mc.alk.arena.util.InventoryUtil.PInv pinv = mc.alk.arena.listeners.BAPlayerListener.itemRestore.remove(pl.getName());
                        mc.alk.arena.objects.ArenaPlayer ap = mc.alk.arena.controllers.PlayerController.toArenaPlayer(pl);
                        mc.alk.arena.controllers.PlayerStoreController.setInventory(ap, pinv);
                    }
                }
            });
        }
        // / Restore Match Items
        if (mc.alk.arena.listeners.BAPlayerListener.matchItemRestore.containsKey(p.getName())) {
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    org.bukkit.entity.Player pl;
                    if (mc.alk.arena.Defaults.DEBUG_VIRTUAL) {
                        pl = mc.alk.virtualPlayer.VirtualPlayers.getPlayer(name);
                    } else {
                        pl = org.bukkit.Bukkit.getPlayer(name);
                    }
                    if (pl != null) {
                        mc.alk.arena.util.InventoryUtil.PInv pinv = mc.alk.arena.listeners.BAPlayerListener.matchItemRestore.remove(pl.getName());
                        mc.alk.arena.objects.ArenaPlayer ap = mc.alk.arena.controllers.PlayerController.toArenaPlayer(pl);
                        mc.alk.arena.controllers.PlayerStoreController.setInventory(ap, pinv);
                    }
                }
            });
        }
        // / Remove Items
        if (mc.alk.arena.listeners.BAPlayerListener.itemRemove.containsKey(p.getName())) {
            org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(mc.alk.arena.BattleArena.getSelf(), new java.lang.Runnable() {
                public void run() {
                    org.bukkit.entity.Player pl;
                    if (mc.alk.arena.Defaults.DEBUG_VIRTUAL) {
                        pl = mc.alk.virtualPlayer.VirtualPlayers.getPlayer(name);
                    } else {
                        pl = org.bukkit.Bukkit.getPlayer(name);
                    }
                    if (pl != null) {
                        java.util.List<org.bukkit.inventory.ItemStack> items = mc.alk.arena.listeners.BAPlayerListener.itemRemove.remove(pl.getName());
                        mc.alk.arena.controllers.PlayerStoreController.removeItems(mc.alk.arena.BattleArena.toArenaPlayer(pl), items);
                    }
                }
            });
        }
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onPlayerTeleport(org.bukkit.event.player.PlayerTeleportEvent event) {
        if (((event.isCancelled() || (!mc.alk.arena.controllers.WorldGuardController.hasWorldGuard())) || (mc.alk.arena.controllers.WorldGuardController.regionCount() == 0)) || event.getPlayer().hasPermission(Permissions.TELEPORT_BYPASS_PERM))
            return;

        mc.alk.arena.objects.regions.WorldGuardRegion region = mc.alk.arena.controllers.WorldGuardController.getContainingRegion(event.getTo());
        if ((region != null) && (!mc.alk.arena.controllers.WorldGuardController.hasPlayer(event.getPlayer().getName(), region))) {
            mc.alk.arena.util.MessageUtil.sendMessage(event.getPlayer(), "&cYou can't enter the arena through teleports");
            event.setCancelled(true);
            return;
        }
    }

    public static void killOnReenter(java.lang.String playerName) {
        mc.alk.arena.listeners.BAPlayerListener.die.add(playerName);
    }

    public static void teleportOnReenter(java.lang.String playerName, org.bukkit.Location loc) {
        mc.alk.arena.listeners.BAPlayerListener.tp.put(playerName, loc);
    }

    public static void addMessageOnReenter(java.lang.String playerName, java.lang.String string) {
        mc.alk.arena.listeners.BAPlayerListener.messagesOnRespawn.put(playerName, string);
    }

    public static void restoreExpOnReenter(java.lang.String playerName, java.lang.Integer val) {
        if (mc.alk.arena.listeners.BAPlayerListener.expRestore.containsKey(playerName)) {
            val += mc.alk.arena.listeners.BAPlayerListener.expRestore.get(playerName);
        }
        mc.alk.arena.listeners.BAPlayerListener.expRestore.put(playerName, val);
    }

    public static void restoreItemsOnReenter(java.lang.String playerName, mc.alk.arena.util.InventoryUtil.PInv pinv) {
        mc.alk.arena.listeners.BAPlayerListener.itemRestore.put(playerName, pinv);
    }

    public static void restoreMatchItemsOnReenter(java.lang.String playerName, mc.alk.arena.util.InventoryUtil.PInv pinv) {
        mc.alk.arena.listeners.BAPlayerListener.matchItemRestore.put(playerName, pinv);
    }

    public static void removeMatchItems(java.lang.String playerName) {
        mc.alk.arena.listeners.BAPlayerListener.matchItemRestore.remove(playerName);
    }

    public static void clearWoolOnReenter(java.lang.String playerName, int color) {
        if ((playerName == null) || (color == (-1)))
            return;

        mc.alk.arena.listeners.BAPlayerListener.clearWool.put(playerName, color);
    }

    public static void restoreGameModeOnEnter(java.lang.String playerName, org.bukkit.GameMode gamemode) {
        mc.alk.arena.listeners.BAPlayerListener.gamemodeRestore.put(playerName, gamemode);
    }

    public static void removeItemOnEnter(mc.alk.arena.objects.ArenaPlayer p, org.bukkit.inventory.ItemStack is) {
        java.util.List<org.bukkit.inventory.ItemStack> items = mc.alk.arena.listeners.BAPlayerListener.itemRemove.get(p.getName());
        if (items == null) {
            items = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
            mc.alk.arena.listeners.BAPlayerListener.itemRemove.put(p.getName(), items);
        }
        items.add(is);
    }

    public static void removeItemsOnEnter(mc.alk.arena.objects.ArenaPlayer p, java.util.List<org.bukkit.inventory.ItemStack> itemsToRemove) {
        java.util.List<org.bukkit.inventory.ItemStack> items = mc.alk.arena.listeners.BAPlayerListener.itemRemove.get(p.getName());
        if (items == null) {
            items = new java.util.ArrayList<org.bukkit.inventory.ItemStack>();
            mc.alk.arena.listeners.BAPlayerListener.itemRemove.put(p.getName(), items);
        }
        items.addAll(itemsToRemove);
    }

    public static void restoreHealthOnReenter(java.lang.String playerName, java.lang.Integer val) {
        mc.alk.arena.listeners.BAPlayerListener.healthRestore.put(playerName, val);
    }

    public static void restoreHungerOnReenter(java.lang.String playerName, java.lang.Integer val) {
        mc.alk.arena.listeners.BAPlayerListener.hungerRestore.put(playerName, val);
    }

    public static void restoreMagicOnReenter(java.lang.String playerName, java.lang.Integer val) {
        mc.alk.arena.listeners.BAPlayerListener.magicRestore.put(playerName, val);
    }
}