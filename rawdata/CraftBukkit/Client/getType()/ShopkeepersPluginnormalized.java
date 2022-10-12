@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.lang.String[] args) {
    if (((args.length > 0) && args[0].equalsIgnoreCase("reload")) && sender.hasPermission("shopkeeper.reload")) {
        // reload command
        reload();
        sender.sendMessage(org.bukkit.ChatColor.GREEN + "Shopkeepers plugin reloaded!");
        return true;
    } else if (((args.length == 1) && args[0].equalsIgnoreCase("debug")) && sender.isOp()) {
        // toggle debug command
        debug = !debug;
        sender.sendMessage((org.bukkit.ChatColor.GREEN + "Debug mode ") + (debug ? "enabled" : "disabled"));
        return true;
    } else if (((args.length == 1) && args[0].equals("check")) && sender.isOp()) {
        for (Shopkeeper shopkeeper : activeShopkeepers.values()) {
            if (shopkeeper.isActive()) {
                org.bukkit.Location loc = shopkeeper.getActualLocation();
                sender.sendMessage(((("Shopkeeper at " + shopkeeper.getPositionString()) + ": active (") + (loc != null ? loc.toString() : "maybe not?!?")) + ")");
            } else {
                sender.sendMessage(("Shopkeeper at " + shopkeeper.getPositionString()) + ": INACTIVE!");
            }
        }
        return true;
    } else if (sender instanceof org.bukkit.entity.Player) {
        org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
        @java.lang.SuppressWarnings("deprecation")
        org.bukkit.block.Block block = player.getTargetBlock(null, 10);// TODO: fix this when API becomes available

        // transfer ownership
        if (((args.length == 2) && args[0].equalsIgnoreCase("transfer")) && player.hasPermission("shopkeeper.transfer")) {
            org.bukkit.entity.Player newOwner = org.bukkit.Bukkit.getPlayer(args[1]);
            if (newOwner == null) {
                this.sendMessage(player, Settings.msgUnknownPlayer);
                return true;
            }
            org.bukkit.Material _CVAR0 = org.bukkit.Material.CHEST;
            boolean _CVAR1 = block.getType() != _CVAR0;
            if () {
                this.sendMessage(player, Settings.msgMustTargetChest);
                return true;
            }
            java.util.List<PlayerShopkeeper> shopkeepers = getShopkeeperOwnersOfChest(block);
            if (shopkeepers.size() == 0) {
                this.sendMessage(player, Settings.msgUnusedChest);
                return true;
            }
            if ((!player.isOp()) && (!player.hasPermission("shopkeeper.bypass"))) {
                for (PlayerShopkeeper shopkeeper : shopkeepers) {
                    if (!shopkeeper.isOwner(player)) {
                        this.sendMessage(player, Settings.msgNotOwner);
                        return true;
                    }
                }
            }
            for (PlayerShopkeeper shopkeeper : shopkeepers) {
                shopkeeper.setOwner(newOwner);
            }
            save();
            this.sendMessage(player, Settings.msgOwnerSet.replace("{owner}", newOwner.getName()));
            return true;
        }
        // set for hire
        if (((args.length == 1) && args[0].equalsIgnoreCase("setforhire")) && player.hasPermission("shopkeeper.setforhire")) {
            org.bukkit.Material _CVAR2 = org.bukkit.Material.CHEST;
            boolean _CVAR3 = block.getType() != _CVAR2;
            if () {
                this.sendMessage(player, Settings.msgMustTargetChest);
                return true;
            }
            java.util.List<PlayerShopkeeper> shopkeepers = getShopkeeperOwnersOfChest(block);
            if (shopkeepers.size() == 0) {
                this.sendMessage(player, Settings.msgUnusedChest);
                return true;
            }
            if ((!player.isOp()) && (!player.hasPermission("shopkeeper.bypass"))) {
                for (PlayerShopkeeper shopkeeper : shopkeepers) {
                    if (!shopkeeper.isOwner(player)) {
                        this.sendMessage(player, Settings.msgNotOwner);
                        return true;
                    }
                }
            }
            org.bukkit.inventory.ItemStack hireCost = player.getItemInHand();
            if (((hireCost == null) || (hireCost.getType() == org.bukkit.Material.AIR)) || (hireCost.getAmount() == 0)) {
                this.sendMessage(player, Settings.msgMustHoldHireItem);
                return true;
            }
            for (PlayerShopkeeper shopkeeper : shopkeepers) {
                shopkeeper.setForHire(true, hireCost.clone());
            }
            save();
            this.sendMessage(player, Settings.msgSetForHire);
            return true;
        }
        // open remote shop
        if (((args.length >= 2) && args[0].equalsIgnoreCase("remote")) && player.hasPermission("shopkeeper.remote")) {
            java.lang.String shopName = args[1];
            for (int i = 2; i < args.length; i++) {
                shopName += " " + args[i];
            }
            boolean opened = false;
            for (java.util.List<Shopkeeper> list : allShopkeepersByChunk.values()) {
                for (Shopkeeper shopkeeper : list) {
                    if (((shopkeeper instanceof AdminShopkeeper) && (shopkeeper.getName() != null)) && org.bukkit.ChatColor.stripColor(shopkeeper.getName()).equalsIgnoreCase(shopName)) {
                        openTradeWindow(shopkeeper, player);
                        opened = true;
                        break;
                    }
                }
                if (opened) {
                    break;
                }
            }
            if (!opened) {
                this.sendMessage(player, Settings.msgUnknownShopkeeper);
            }
            return true;
        }
        org.bukkit.Material _CVAR4 = org.bukkit.Material.AIR;
        boolean _CVAR5 = block.getType() != _CVAR4;
        boolean _CVAR6 = (block != null) && _CVAR5;
        // get the spawn location for the shopkeeper
        if () {
            org.bukkit.Material _CVAR7 = org.bukkit.Material.CHEST;
            boolean _CVAR8 = block.getType() == _CVAR7;
             _CVAR9 = Settings.createPlayerShopWithCommand && _CVAR8;
            if () {
                // check if already a chest
                if (isChestProtected(null, block)) {
                    return true;
                }
                // check for recently placed
                if (Settings.requireChestRecentlyPlaced) {
                    java.util.List<java.lang.String> list = com.nisovin.shopkeepers.ShopkeepersPlugin.plugin.recentlyPlacedChests.get(player.getName());
                    if ((list == null) || (!list.contains((((((block.getWorld().getName() + ",") + block.getX()) + ",") + block.getY()) + ",") + block.getZ()))) {
                        sendMessage(player, Settings.msgChestNotPlaced);
                        return true;
                    }
                }
                // check for permission
                if (Settings.simulateRightClickOnCommand) {
                    org.bukkit.event.player.PlayerInteractEvent event = new org.bukkit.event.player.PlayerInteractEvent(player, org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK, new org.bukkit.inventory.ItemStack(org.bukkit.Material.AIR), block, org.bukkit.block.BlockFace.UP);
                    org.bukkit.Bukkit.getPluginManager().callEvent(event);
                    if (event.isCancelled()) {
                        return true;
                    }
                }
                // create the player shopkeeper
                ShopkeeperType shopType = ShopkeeperType.next(player, null);
                ShopObjectType shopObjType = ShopObjectType.next(player, null);
                if ((args != null) && (args.length > 0)) {
                    if (args.length >= 1) {
                        if (args[0].toLowerCase().startsWith("norm") || args[0].toLowerCase().startsWith("sell")) {
                            shopType = ShopkeeperType.PLAYER_NORMAL;
                        } else if (args[0].toLowerCase().startsWith("book")) {
                            shopType = ShopkeeperType.PLAYER_BOOK;
                        } else if (args[0].toLowerCase().startsWith("buy")) {
                            shopType = ShopkeeperType.PLAYER_BUY;
                        } else if (args[0].toLowerCase().startsWith("trad")) {
                            shopType = ShopkeeperType.PLAYER_TRADE;
                        } else if (args[0].toLowerCase().equals("villager") && Settings.enableVillagerShops) {
                            shopObjType = ShopObjectType.VILLAGER;
                        } else if (args[0].toLowerCase().equals("sign") && Settings.enableSignShops) {
                            shopObjType = ShopObjectType.SIGN;
                        } else if (args[0].toLowerCase().equals("witch") && Settings.enableWitchShops) {
                            shopObjType = ShopObjectType.WITCH;
                        } else if (args[0].toLowerCase().equals("creeper") && Settings.enableCreeperShops) {
                            shopObjType = ShopObjectType.CREEPER;
                        }
                    }
                    if (args.length >= 2) {
                        if (args[1].equalsIgnoreCase("villager") && Settings.enableVillagerShops) {
                            shopObjType = ShopObjectType.VILLAGER;
                        } else if (args[1].equalsIgnoreCase("sign") && Settings.enableSignShops) {
                            shopObjType = ShopObjectType.SIGN;
                        } else if (args[1].equalsIgnoreCase("witch") && Settings.enableWitchShops) {
                            shopObjType = ShopObjectType.WITCH;
                        } else if (args[1].equalsIgnoreCase("creeper") && Settings.enableCreeperShops) {
                            shopObjType = ShopObjectType.CREEPER;
                        }
                    }
                    if ((shopType != null) && (!shopType.hasPermission(player))) {
                        shopType = null;
                    }
                    if ((shopObjType != null) && (!shopObjType.hasPermission(player))) {
                        shopObjType = null;
                    }
                }
                if (shopType != null) {
                    Shopkeeper shopkeeper = createNewPlayerShopkeeper(player, block, block.getLocation().add(0, 1.5, 0), shopType, shopObjType);
                    if (shopkeeper != null) {
                        sendCreatedMessage(player, shopType);
                    }
                }
            } else if (player.hasPermission("shopkeeper.admin")) {
                // create the admin shopkeeper
                ShopObjectType shopObjType = ShopObjectType.VILLAGER;
                org.bukkit.Location loc = block.getLocation().add(0, 1.5, 0);
                if (args.length > 0) {
                    if (args[0].equals("sign") && Settings.enableSignShops) {
                        shopObjType = ShopObjectType.SIGN;
                        loc = block.getLocation();
                    } else if (args[0].equals("witch") && Settings.enableWitchShops) {
                        shopObjType = ShopObjectType.WITCH;
                    } else if (args[0].equals("creeper") && Settings.enableCreeperShops) {
                        shopObjType = ShopObjectType.CREEPER;
                    }
                }
                Shopkeeper shopkeeper = createNewAdminShopkeeper(loc, shopObjType);
                if (shopkeeper != null) {
                    sendMessage(player, Settings.msgAdminShopCreated);
                    // run event
                    org.bukkit.Bukkit.getPluginManager().callEvent(new ShopkeeperCreatedEvent(player, shopkeeper));
                }
            }
        } else {
            sendMessage(player, Settings.msgShopCreateFail);
        }
        return true;
    } else {
        sender.sendMessage("You must be a player to create a shopkeeper.");
        sender.sendMessage("Use 'shopkeeper reload' to reload the plugin.");
        return true;
    }
}