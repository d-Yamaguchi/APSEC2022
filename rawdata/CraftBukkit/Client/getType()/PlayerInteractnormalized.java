/* The event called when a player clicks a block.

USED:
When a ZAPlayer clicks a sign, to check the lines for strings that trigger a response.
 */
@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
public void PIE(org.bukkit.event.player.PlayerInteractEvent event) {
    org.bukkit.block.Block b = event.getClickedBlock();
    org.bukkit.entity.Player p = event.getPlayer();
    org.bukkit.event.block.Action a = event.getAction();
    if (b != null) {
        org.bukkit.Location loc = b.getLocation();
        if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.barrierPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isBarrier(loc)) {
                p.sendMessage(org.bukkit.ChatColor.RED + "That is already a barrier!");
                return;
            }
            new com.github.aspect.Barrier(loc, com.github.event.bukkit.PlayerInteract.barrierPlayers.get(p.getName()));
            p.sendMessage(org.bukkit.ChatColor.GRAY + "Barrier created successfully!");
            com.github.event.bukkit.PlayerInteract.barrierPlayers.remove(p.getName());
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.powerSwitchClickers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isPowerSwitch(loc)) {
                p.sendMessage(org.bukkit.ChatColor.RED + "That is already a power switch!");
                return;
            }
            new com.github.aspect.PowerSwitch(com.github.event.bukkit.PlayerInteract.powerSwitchClickers.get(p.getName()), loc);
            p.sendMessage(org.bukkit.ChatColor.GRAY + "Switch created successfully.");
            com.github.event.bukkit.PlayerInteract.powerSwitchClickers.remove(p.getName());
            return;
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.powerClickers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isGameObject(loc)) {
                com.github.behavior.GameObject object = data.getGameObjectByLocation(loc);
                if (((object instanceof com.github.behavior.Powerable) && (object.getGame() != null)) && (object.getGame() == data.getGame(com.github.event.bukkit.PlayerInteract.powerClickerGames.get(p.getName()), false))) {
                    com.github.behavior.Powerable powerObj = ((com.github.behavior.Powerable) (object));
                    boolean power = com.github.event.bukkit.PlayerInteract.powerClickers.get(p.getName());
                    powerObj.setRequiresPower(power);
                    p.sendMessage(((org.bukkit.ChatColor.GRAY + "Power on this object ") + (power ? "enabled" : "disabled")) + ".");
                    return;
                }
                p.sendMessage(org.bukkit.ChatColor.RED + "Either this object is not powerable or it does not match the game entered.");
                return;
            }
            p.sendMessage(org.bukkit.ChatColor.RED + "This is not an object!");
            com.github.event.bukkit.PlayerInteract.powerClickers.remove(p.getName());
            com.github.event.bukkit.PlayerInteract.powerClickerGames.remove(p.getName());
            return;
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.spawnerPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isMobSpawner(b.getLocation()) && (data.getMobSpawner(b.getLocation()).getGame() == com.github.event.bukkit.PlayerInteract.spawnerPlayers.get(p.getName()))) {
                p.sendMessage(org.bukkit.ChatColor.RED + "That is already a mob spawner for this game!");
                return;
            }
            com.github.event.bukkit.PlayerInteract.spawnerPlayers.get(p.getName()).addObject(new com.github.aspect.MobSpawner(loc, com.github.event.bukkit.PlayerInteract.spawnerPlayers.get(p.getName())));
            p.sendMessage(org.bukkit.ChatColor.GRAY + "Spawner created successfully!");
            com.github.event.bukkit.PlayerInteract.spawnerPlayers.remove(p.getName());
        } else {
            org.bukkit.Material _CVAR0 = org.bukkit.Material.CHEST;
            boolean _CVAR1 = b.getType() == _CVAR0;
             _CVAR2 = (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.chestPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) && _CVAR1;
            if () {
                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                com.github.aspect.Game zag = com.github.event.bukkit.PlayerInteract.chestPlayers.get(p.getName());
                if (!data.isMysteryChest(b.getLocation())) {
                    com.github.event.bukkit.PlayerInteract.chestPlayers.get(p.getName()).addObject(new com.github.aspect.MysteryChest(zag, loc, zag.getActiveMysteryChest() == null));
                    p.sendMessage(org.bukkit.ChatColor.GRAY + "Mystery chest created successfully!");
                } else {
                    p.sendMessage(org.bukkit.ChatColor.RED + "That is already a mystery chest!");
                }
                com.github.event.bukkit.PlayerInteract.chestPlayers.remove(p.getName());
            } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.removers.contains(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                com.github.behavior.GameObject removal = null;
                for (com.github.behavior.GameObject o : data.getObjectsOfType(com.github.behavior.GameObject.class)) {
                    for (org.bukkit.block.Block block : o.getDefiningBlocks()) {
                        if ((block != null) && (block.getLocation().distanceSquared(b.getLocation()) <= 1)) {
                            // 1 squared = 1
                            removal = o;
                            break;
                        }
                    }
                }
                if (removal != null) {
                    removal.remove();
                    p.sendMessage(((org.bukkit.ChatColor.GRAY + "Removal ") + org.bukkit.ChatColor.GREEN) + "successful");
                } else {
                    p.sendMessage(((org.bukkit.ChatColor.GRAY + "Removal ") + org.bukkit.ChatColor.RED) + "unsuccessful");
                }
                com.github.event.bukkit.PlayerInteract.removers.remove(p.getName());
            } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.passagePlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                if (isPassage(b)) {
                    p.sendMessage(org.bukkit.ChatColor.RED + "That is already a passage!");
                    return;
                }
                if (!com.github.event.bukkit.PlayerInteract.locClickers.containsKey(p.getName())) {
                    com.github.event.bukkit.PlayerInteract.locClickers.put(p.getName(), b.getLocation());
                    p.sendMessage(org.bukkit.ChatColor.GRAY + "Click another block to select point 2.");
                } else {
                    org.bukkit.Location loc2 = com.github.event.bukkit.PlayerInteract.locClickers.get(p.getName());
                    new com.github.aspect.Passage(com.github.event.bukkit.PlayerInteract.passagePlayers.get(p.getName()), loc, loc2);
                    com.github.event.bukkit.PlayerInteract.locClickers.remove(p.getName());
                    com.github.event.bukkit.PlayerInteract.passagePlayers.remove(p.getName());
                    p.sendMessage(org.bukkit.ChatColor.GRAY + "Passage created!");
                }
            } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.mapDataSavePlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                if (!com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.containsKey(p.getName())) {
                    com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.put(p.getName(), b.getLocation());
                    p.sendMessage(org.bukkit.ChatColor.GRAY + "Please click the other corner of the map.");
                } else {
                    boolean saved = new com.github.storage.MapDataStorage(com.github.event.bukkit.PlayerInteract.mapDataSavePlayers.get(p.getName())).save(new com.github.utility.selection.Rectangle(com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.get(p.getName()), b.getLocation()));
                    com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.remove(p.getName());
                    com.github.event.bukkit.PlayerInteract.mapDataSavePlayers.remove(p.getName());
                    java.lang.String successful = (saved) ? (org.bukkit.ChatColor.GREEN + "successfully") + org.bukkit.ChatColor.RESET : (org.bukkit.ChatColor.RED + "unsuccessfully") + org.bukkit.ChatColor.RESET;
                    p.sendMessage(((org.bukkit.ChatColor.GRAY + "Mapdata saved ") + successful) + ".");
                }
            } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                boolean loaded = com.github.storage.MapDataStorage.getFromGame(com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers.get(p.getName())).load(b.getLocation());
                com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers.remove(p.getName());
                java.lang.String successful = (loaded) ? (org.bukkit.ChatColor.GREEN + "successfully") + org.bukkit.ChatColor.RESET : (org.bukkit.ChatColor.RED + "unsuccessfully") + org.bukkit.ChatColor.RESET;
                p.sendMessage(((org.bukkit.ChatColor.GRAY + "Mapdata loaded ") + successful) + ".");
            } else {
                org.bukkit.Material _CVAR3 = org.bukkit.Material.SIGN;
                boolean _CVAR4 = b.getType() == _CVAR3;
                org.bukkit.Material _CVAR8 = org.bukkit.Material.SIGN_POST;
                boolean _CVAR9 = b.getType() == _CVAR8;
                boolean _CVAR5 = _CVAR4 || _CVAR9;
                org.bukkit.Material _CVAR10 = org.bukkit.Material.WALL_SIGN;
                boolean _CVAR11 = b.getType() == _CVAR10;
                boolean _CVAR6 = _CVAR5 || _CVAR11;
                boolean _CVAR7 = _CVAR6 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                if () {
                    org.bukkit.block.Sign s = ((org.bukkit.block.Sign) (b.getState()));
                    if (s.getLine(0).equalsIgnoreCase(Local.BASE_STRING.getSetting())) {
                        event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                        runLines(s, p);
                        return;
                    }
                    return;
                } else if (data.isZAPlayer(p)) {
                    if (!com.github.event.bukkit.BlockPlace.shouldBePlaced(p.getItemInHand().getType())) {
                        event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
                    }
                    com.github.aspect.ZAPlayer zap = data.getZAPlayer(p);
                    com.github.aspect.Game game = zap.getGame();
                    org.bukkit.Material _CVAR12 = org.bukkit.Material.ENDER_PORTAL_FRAME;
                    boolean _CVAR13 = b.getType() == _CVAR12;
                    boolean _CVAR14 = _CVAR13 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                    if () {
                    } else {
                        org.bukkit.Material _CVAR15 = org.bukkit.Material.CHEST;
                        boolean _CVAR16 = b.getType() == _CVAR15;
                        boolean _CVAR17 = _CVAR16 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                        if () {
                            org.bukkit.Location l = b.getLocation();
                            if (data.isMysteryChest(l)) {
                                com.github.aspect.MysteryChest mc = data.getMysteryChest(l);
                                if ((mc != null) && mc.isActive()) {
                                    mc.giveRandomItem(zap);
                                } else {
                                    p.sendMessage(org.bukkit.ChatColor.RED + "That chest is not active!");
                                    event.setCancelled(true);
                                    return;
                                }
                            }
                        } else {
                            org.bukkit.Material _CVAR18 = org.bukkit.Material.WOOD_DOOR;
                            boolean _CVAR19 = b.getType() == _CVAR18;
                            org.bukkit.Material _CVAR21 = org.bukkit.Material.IRON_DOOR;
                            boolean _CVAR22 = b.getType() == _CVAR21;
                            boolean _CVAR20 = _CVAR19 || _CVAR22;
                            if () {
                                event.setCancelled(true);
                            } else {
                                org.bukkit.Material _CVAR23 = org.bukkit.Material.FENCE;
                                boolean _CVAR24 = b.getType() == _CVAR23;
                                boolean _CVAR25 = _CVAR24 && (a == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK);
                                if () {
                                    // through-fence damage
                                    short damage = p.getItemInHand().getDurability();
                                    zap.shoot(3, 1, damage, false, true);
                                } else if (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
                                    if (com.github.utility.MiscUtil.locationMatch(b.getLocation(), game.getMainframe().getLocation(), 2)) {
                                        // mainframe
                                        com.github.aspect.Mainframe frame = game.getMainframe();
                                        if (com.github.event.bukkit.PlayerInteract.mainframeLinkers.containsKey(zap)) {
                                            com.github.threading.inherent.TeleporterLinkageTimerThread tltt = com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.get(zap);
                                            if (tltt.canBeLinked()) {
                                                p.sendMessage(org.bukkit.ChatColor.GREEN + "Teleporter linked!");
                                                tltt.setLinked(true);
                                                frame.link(com.github.event.bukkit.PlayerInteract.mainframeLinkers.get(zap));
                                            }
                                            com.github.event.bukkit.PlayerInteract.mainframeLinkers.remove(zap);
                                            com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.remove(zap);
                                        }
                                    } else {
                                        org.bukkit.Material _CVAR26 = org.bukkit.Material.ENDER_PORTAL_FRAME;
                                        boolean _CVAR27 = b.getType() == _CVAR26;
                                        if () {
                                            // not mainframe
                                            int time = -1;
                                            boolean requiresLinkage = true;
                                            org.bukkit.Location below = loc.clone().subtract(0, 1, 0);
                                            if (below.getBlock().getState() instanceof org.bukkit.block.Sign) {
                                                org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (below.getBlock().getState()));
                                                try {
                                                    requiresLinkage = java.lang.Boolean.parseBoolean(sign.getLine(0));
                                                    time = java.lang.Integer.parseInt(sign.getLine(1));
                                                } catch (java.lang.Exception e) {
                                                    // nothing
                                                }
                                            }
                                            if (game.getMainframe().isLinked(b.getLocation()) || (!requiresLinkage)) {
                                                // this teleporter linked to the mainframe...
                                                if (!zap.isTeleporting()) {
                                                    p.sendMessage(org.bukkit.ChatColor.GRAY + "Teleportation sequence started...");
                                                    new com.github.threading.inherent.TeleportThread(zap, ((java.lang.Integer) (Setting.TELEPORT_TIME.getSetting())), true, 20);
                                                    return;
                                                } else {
                                                    p.sendMessage(org.bukkit.ChatColor.GRAY + "You are already teleporting!");
                                                    return;
                                                }
                                            } else {
                                                // this teleporter is not linked to the mainframe...
                                                time = (time == (-1)) ? ((int) (loc.distanceSquared(game.getMainframe().getLocation()) * 4.5)) : time;// 1 second per block difference (4.5 approx sqrt 20)

                                                com.github.event.bukkit.PlayerInteract.mainframeLinkers.put(zap, loc);
                                                com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.put(zap, new com.github.threading.inherent.TeleporterLinkageTimerThread(game.getMainframe(), zap, time));// difference

                                                p.sendMessage(((org.bukkit.ChatColor.GRAY + "You now have ") + (time / 20)) + " seconds to link the teleporter to the mainframe!");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}