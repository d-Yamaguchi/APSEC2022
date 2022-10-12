/* The event called when a player clicks a block.

USED:
When a ZAPlayer clicks a sign, to check the lines for strings that trigger a response.
 */
@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
public void PIE(org.bukkit.event.player.PlayerInteractEvent event) {
    org.bukkit.block.Block b = event.getClickedBlock();
    org.bukkit.event.block.Action a = event.getAction();
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.event.player.PlayerInteractEvent _CVAR4 = _CVAR0;
    org.bukkit.event.player.PlayerInteractEvent _CVAR8 = _CVAR4;
    org.bukkit.event.player.PlayerInteractEvent _CVAR12 = _CVAR8;
    org.bukkit.event.player.PlayerInteractEvent _CVAR16 = _CVAR12;
    org.bukkit.event.player.PlayerInteractEvent _CVAR20 = _CVAR16;
    org.bukkit.event.player.PlayerInteractEvent _CVAR24 = _CVAR20;
    org.bukkit.event.player.PlayerInteractEvent _CVAR28 = _CVAR24;
    org.bukkit.event.player.PlayerInteractEvent _CVAR32 = _CVAR28;
    org.bukkit.event.player.PlayerInteractEvent _CVAR36 = _CVAR32;
    org.bukkit.event.player.PlayerInteractEvent _CVAR40 = _CVAR36;
    org.bukkit.event.player.PlayerInteractEvent _CVAR44 = _CVAR40;
    org.bukkit.event.player.PlayerInteractEvent _CVAR48 = _CVAR44;
    org.bukkit.event.player.PlayerInteractEvent _CVAR52 = _CVAR48;
    org.bukkit.event.player.PlayerInteractEvent _CVAR56 = _CVAR52;
    org.bukkit.event.player.PlayerInteractEvent _CVAR60 = _CVAR56;
    org.bukkit.event.player.PlayerInteractEvent _CVAR64 = _CVAR60;
    org.bukkit.event.player.PlayerInteractEvent _CVAR68 = _CVAR64;
    org.bukkit.event.player.PlayerInteractEvent _CVAR72 = _CVAR68;
    org.bukkit.event.player.PlayerInteractEvent _CVAR76 = _CVAR72;
    org.bukkit.event.player.PlayerInteractEvent _CVAR80 = _CVAR76;
    org.bukkit.event.player.PlayerInteractEvent _CVAR84 = _CVAR80;
    org.bukkit.event.player.PlayerInteractEvent _CVAR88 = _CVAR84;
    org.bukkit.event.player.PlayerInteractEvent _CVAR92 = _CVAR88;
    org.bukkit.entity.Player p = _CVAR92.getPlayer();
    if (b != null) {
        org.bukkit.Location loc = b.getLocation();
        if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.barrierPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isBarrier(loc)) {
                java.lang.String _CVAR2 = "That is already a barrier!";
                org.bukkit.entity.Player _CVAR1 = p;
                java.lang.String _CVAR3 = org.bukkit.ChatColor.RED + _CVAR2;
                _CVAR1.sendMessage(_CVAR3);
                return;
            }
            new com.github.aspect.Barrier(loc, com.github.event.bukkit.PlayerInteract.barrierPlayers.get(p.getName()));
            java.lang.String _CVAR6 = "Barrier created successfully!";
            org.bukkit.entity.Player _CVAR5 = p;
            java.lang.String _CVAR7 = org.bukkit.ChatColor.GRAY + _CVAR6;
            _CVAR5.sendMessage(_CVAR7);
            com.github.event.bukkit.PlayerInteract.barrierPlayers.remove(p.getName());
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.powerSwitchClickers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isPowerSwitch(loc)) {
                java.lang.String _CVAR10 = "That is already a power switch!";
                org.bukkit.entity.Player _CVAR9 = p;
                java.lang.String _CVAR11 = org.bukkit.ChatColor.RED + _CVAR10;
                _CVAR9.sendMessage(_CVAR11);
                return;
            }
            new com.github.aspect.PowerSwitch(com.github.event.bukkit.PlayerInteract.powerSwitchClickers.get(p.getName()), loc);
            java.lang.String _CVAR14 = "Switch created successfully.";
            org.bukkit.entity.Player _CVAR13 = p;
            java.lang.String _CVAR15 = org.bukkit.ChatColor.GRAY + _CVAR14;
            _CVAR13.sendMessage(_CVAR15);
            com.github.event.bukkit.PlayerInteract.powerSwitchClickers.remove(p.getName());
            return;
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.powerClickers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isGameObject(loc)) {
                com.github.behavior.GameObject object = data.getGameObjectByLocation(loc);
                if (((object instanceof com.github.behavior.Powerable) && (object.getGame() != null)) && (object.getGame() == data.getGame(com.github.event.bukkit.PlayerInteract.powerClickerGames.get(p.getName()), false))) {
                    com.github.behavior.Powerable powerObj = ((com.github.behavior.Powerable) (object));
                    boolean power = com.github.event.bukkit.PlayerInteract.powerClickers.get(p.getName());
                    powerObj.setRequiresPower(power);
                    java.lang.String _CVAR18 = ".";
                    org.bukkit.entity.Player _CVAR17 = p;
                    java.lang.String _CVAR19 = ((org.bukkit.ChatColor.GRAY + "Power on this object ") + (power ? "enabled" : "disabled")) + _CVAR18;
                    _CVAR17.sendMessage(_CVAR19);
                    return;
                }
                java.lang.String _CVAR22 = "Either this object is not powerable or it does not match the game entered.";
                org.bukkit.entity.Player _CVAR21 = p;
                java.lang.String _CVAR23 = org.bukkit.ChatColor.RED + _CVAR22;
                _CVAR21.sendMessage(_CVAR23);
                return;
            }
            java.lang.String _CVAR26 = "This is not an object!";
            org.bukkit.entity.Player _CVAR25 = p;
            java.lang.String _CVAR27 = org.bukkit.ChatColor.RED + _CVAR26;
            _CVAR25.sendMessage(_CVAR27);
            com.github.event.bukkit.PlayerInteract.powerClickers.remove(p.getName());
            com.github.event.bukkit.PlayerInteract.powerClickerGames.remove(p.getName());
            return;
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.spawnerPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (data.isMobSpawner(b.getLocation()) && (data.getMobSpawner(b.getLocation()).getGame() == com.github.event.bukkit.PlayerInteract.spawnerPlayers.get(p.getName()))) {
                java.lang.String _CVAR30 = "That is already a mob spawner for this game!";
                org.bukkit.entity.Player _CVAR29 = p;
                java.lang.String _CVAR31 = org.bukkit.ChatColor.RED + _CVAR30;
                _CVAR29.sendMessage(_CVAR31);
                return;
            }
            com.github.event.bukkit.PlayerInteract.spawnerPlayers.get(p.getName()).addObject(new com.github.aspect.MobSpawner(loc, com.github.event.bukkit.PlayerInteract.spawnerPlayers.get(p.getName())));
            java.lang.String _CVAR34 = "Spawner created successfully!";
            org.bukkit.entity.Player _CVAR33 = p;
            java.lang.String _CVAR35 = org.bukkit.ChatColor.GRAY + _CVAR34;
            _CVAR33.sendMessage(_CVAR35);
            com.github.event.bukkit.PlayerInteract.spawnerPlayers.remove(p.getName());
        } else if ((((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.chestPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) && (b.getType() == org.bukkit.Material.CHEST)) {
            event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
            com.github.aspect.Game zag = com.github.event.bukkit.PlayerInteract.chestPlayers.get(p.getName());
            if (!data.isMysteryChest(b.getLocation())) {
                com.github.event.bukkit.PlayerInteract.chestPlayers.get(p.getName()).addObject(new com.github.aspect.MysteryChest(zag, loc, zag.getActiveMysteryChest() == null));
                java.lang.String _CVAR38 = "Mystery chest created successfully!";
                org.bukkit.entity.Player _CVAR37 = p;
                java.lang.String _CVAR39 = org.bukkit.ChatColor.GRAY + _CVAR38;
                _CVAR37.sendMessage(_CVAR39);
            } else {
                java.lang.String _CVAR42 = "That is already a mystery chest!";
                org.bukkit.entity.Player _CVAR41 = p;
                java.lang.String _CVAR43 = org.bukkit.ChatColor.RED + _CVAR42;
                _CVAR41.sendMessage(_CVAR43);
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
                java.lang.String _CVAR46 = "successful";
                org.bukkit.entity.Player _CVAR45 = p;
                java.lang.String _CVAR47 = ((org.bukkit.ChatColor.GRAY + "Removal ") + org.bukkit.ChatColor.GREEN) + _CVAR46;
                _CVAR45.sendMessage(_CVAR47);
            } else {
                java.lang.String _CVAR50 = "unsuccessful";
                org.bukkit.entity.Player _CVAR49 = p;
                java.lang.String _CVAR51 = ((org.bukkit.ChatColor.GRAY + "Removal ") + org.bukkit.ChatColor.RED) + _CVAR50;
                _CVAR49.sendMessage(_CVAR51);
            }
            com.github.event.bukkit.PlayerInteract.removers.remove(p.getName());
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.passagePlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (isPassage(b)) {
                java.lang.String _CVAR54 = "That is already a passage!";
                org.bukkit.entity.Player _CVAR53 = p;
                java.lang.String _CVAR55 = org.bukkit.ChatColor.RED + _CVAR54;
                _CVAR53.sendMessage(_CVAR55);
                return;
            }
            if (!com.github.event.bukkit.PlayerInteract.locClickers.containsKey(p.getName())) {
                com.github.event.bukkit.PlayerInteract.locClickers.put(p.getName(), b.getLocation());
                java.lang.String _CVAR58 = "Click another block to select point 2.";
                org.bukkit.entity.Player _CVAR57 = p;
                java.lang.String _CVAR59 = org.bukkit.ChatColor.GRAY + _CVAR58;
                _CVAR57.sendMessage(_CVAR59);
            } else {
                org.bukkit.Location loc2 = com.github.event.bukkit.PlayerInteract.locClickers.get(p.getName());
                new com.github.aspect.Passage(com.github.event.bukkit.PlayerInteract.passagePlayers.get(p.getName()), loc, loc2);
                com.github.event.bukkit.PlayerInteract.locClickers.remove(p.getName());
                com.github.event.bukkit.PlayerInteract.passagePlayers.remove(p.getName());
                java.lang.String _CVAR62 = "Passage created!";
                org.bukkit.entity.Player _CVAR61 = p;
                java.lang.String _CVAR63 = org.bukkit.ChatColor.GRAY + _CVAR62;
                _CVAR61.sendMessage(_CVAR63);
            }
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.mapDataSavePlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            if (!com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.containsKey(p.getName())) {
                com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.put(p.getName(), b.getLocation());
                java.lang.String _CVAR66 = "Please click the other corner of the map.";
                org.bukkit.entity.Player _CVAR65 = p;
                java.lang.String _CVAR67 = org.bukkit.ChatColor.GRAY + _CVAR66;
                _CVAR65.sendMessage(_CVAR67);
            } else {
                boolean saved = new com.github.storage.MapDataStorage(com.github.event.bukkit.PlayerInteract.mapDataSavePlayers.get(p.getName())).save(new com.github.utility.selection.Rectangle(com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.get(p.getName()), b.getLocation()));
                com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.remove(p.getName());
                com.github.event.bukkit.PlayerInteract.mapDataSavePlayers.remove(p.getName());
                java.lang.String successful = (saved) ? (org.bukkit.ChatColor.GREEN + "successfully") + org.bukkit.ChatColor.RESET : (org.bukkit.ChatColor.RED + "unsuccessfully") + org.bukkit.ChatColor.RESET;
                java.lang.String _CVAR70 = ".";
                org.bukkit.entity.Player _CVAR69 = p;
                java.lang.String _CVAR71 = ((org.bukkit.ChatColor.GRAY + "Mapdata saved ") + successful) + _CVAR70;
                _CVAR69.sendMessage(_CVAR71);
            }
        } else if (((!data.isZAPlayer(p)) && com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers.containsKey(p.getName())) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            boolean loaded = com.github.storage.MapDataStorage.getFromGame(com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers.get(p.getName())).load(b.getLocation());
            com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers.remove(p.getName());
            java.lang.String successful = (loaded) ? (org.bukkit.ChatColor.GREEN + "successfully") + org.bukkit.ChatColor.RESET : (org.bukkit.ChatColor.RED + "unsuccessfully") + org.bukkit.ChatColor.RESET;
            java.lang.String _CVAR74 = ".";
            org.bukkit.entity.Player _CVAR73 = p;
            java.lang.String _CVAR75 = ((org.bukkit.ChatColor.GRAY + "Mapdata loaded ") + successful) + _CVAR74;
            _CVAR73.sendMessage(_CVAR75);
        } else if ((((b.getType() == org.bukkit.Material.SIGN) || (b.getType() == org.bukkit.Material.SIGN_POST)) || (b.getType() == org.bukkit.Material.WALL_SIGN)) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
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
            if ((b.getType() == org.bukkit.Material.ENDER_PORTAL_FRAME) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
            } else if ((b.getType() == org.bukkit.Material.CHEST) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                org.bukkit.Location l = b.getLocation();
                if (data.isMysteryChest(l)) {
                    com.github.aspect.MysteryChest mc = data.getMysteryChest(l);
                    if ((mc != null) && mc.isActive()) {
                        mc.giveRandomItem(zap);
                    } else {
                        java.lang.String _CVAR78 = "That chest is not active!";
                        org.bukkit.entity.Player _CVAR77 = p;
                        java.lang.String _CVAR79 = org.bukkit.ChatColor.RED + _CVAR78;
                        _CVAR77.sendMessage(_CVAR79);
                        event.setCancelled(true);
                        return;
                    }
                }
            } else if ((b.getType() == org.bukkit.Material.WOOD_DOOR) || (b.getType() == org.bukkit.Material.IRON_DOOR)) {
                event.setCancelled(true);
            } else if ((b.getType() == org.bukkit.Material.FENCE) && (a == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
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
                            java.lang.String _CVAR82 = "Teleporter linked!";
                            org.bukkit.entity.Player _CVAR81 = p;
                            java.lang.String _CVAR83 = org.bukkit.ChatColor.GREEN + _CVAR82;
                            _CVAR81.sendMessage(_CVAR83);
                            tltt.setLinked(true);
                            frame.link(com.github.event.bukkit.PlayerInteract.mainframeLinkers.get(zap));
                        }
                        com.github.event.bukkit.PlayerInteract.mainframeLinkers.remove(zap);
                        com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.remove(zap);
                    }
                } else if (b.getType() == org.bukkit.Material.ENDER_PORTAL_FRAME) {
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
                            java.lang.String _CVAR86 = "Teleportation sequence started...";
                            org.bukkit.entity.Player _CVAR85 = p;
                            java.lang.String _CVAR87 = org.bukkit.ChatColor.GRAY + _CVAR86;
                            _CVAR85.sendMessage(_CVAR87);
                            new com.github.threading.inherent.TeleportThread(zap, ((java.lang.Integer) (Setting.TELEPORT_TIME.getSetting())), true, 20);
                            return;
                        } else {
                            java.lang.String _CVAR90 = "You are already teleporting!";
                            org.bukkit.entity.Player _CVAR89 = p;
                            java.lang.String _CVAR91 = org.bukkit.ChatColor.GRAY + _CVAR90;
                            _CVAR89.sendMessage(_CVAR91);
                            return;
                        }
                    } else {
                        // this teleporter is not linked to the mainframe...
                        time = (time == (-1)) ? ((int) (loc.distanceSquared(game.getMainframe().getLocation()) * 4.5)) : time;// 1 second per block difference (4.5 approx sqrt 20)

                        com.github.event.bukkit.PlayerInteract.mainframeLinkers.put(zap, loc);
                        com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.put(zap, new com.github.threading.inherent.TeleporterLinkageTimerThread(game.getMainframe(), zap, time));// difference

                        java.lang.String _CVAR94 = " seconds to link the teleporter to the mainframe!";
                        org.bukkit.entity.Player _CVAR93 = p;
                        java.lang.String _CVAR95 = ((org.bukkit.ChatColor.GRAY + "You now have ") + (time / 20)) + _CVAR94;
                        _CVAR93.sendMessage(_CVAR95);
                    }
                }
            }
        }
    }
}