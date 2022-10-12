public boolean execute(org.bukkit.command.CommandSender sender, java.lang.String[] args) {
    org.bukkit.inventory.ItemStack newItem = null;
    org.bukkit.Material newMaterial;
    java.lang.Integer amount;
    java.lang.Double startBid = 0.0;
    java.lang.Integer multiAuction = 1;
    if (!sender.hasPermission("auctionhouse.use.add")) {
        org.bukkit.command.CommandSender _CVAR0 = sender;
        java.lang.String _CVAR1 = "You are not allowed to add an Auction!";
        _CVAR0.sendMessage(_CVAR1);
        return true;
    }
    if (args.length < 1) {
        org.bukkit.command.CommandSender _CVAR2 = sender;
        java.lang.String _CVAR3 = "/ah add hand [StartBid] [Length] [m:<quantity>]";
        _CVAR2.sendMessage(_CVAR3);
        org.bukkit.command.CommandSender _CVAR4 = sender;
        java.lang.String _CVAR5 = "/ah add <Item> <Amount> [StartBid] [Length]";
        _CVAR4.sendMessage(_CVAR5);
        org.bukkit.command.CommandSender _CVAR6 = sender;
        java.lang.String _CVAR7 = "/ah add <Item> <Amount> [StartBid] [Length] [m:<quantity>]";
        _CVAR6.sendMessage(_CVAR7);
        org.bukkit.command.CommandSender _CVAR8 = sender;
        java.lang.String _CVAR9 = "Length is in d|h|m|s";
        _CVAR8.sendMessage(_CVAR9);
        return true;
    }
    de.paralleluniverse.Faithcaio.AuctionHouse.Arguments arguments = new de.paralleluniverse.Faithcaio.AuctionHouse.Arguments(args);
    if (arguments.getString("m") != null) {
        multiAuction = arguments.getInt("m");
        if (multiAuction == null) {
            org.bukkit.command.CommandSender _CVAR10 = sender;
            java.lang.String _CVAR11 = "Info: MultiAuction m: must be an Number!";
            _CVAR10.sendMessage(_CVAR11);
            return true;
        }
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("MultiAuction: " + multiAuction);
        if (!sender.hasPermission("auctionhouse.use.add.multi")) {
            org.bukkit.command.CommandSender _CVAR12 = sender;
            java.lang.String _CVAR13 = "You are not allowed to add multiple Auctions!";
            _CVAR12.sendMessage(_CVAR13);
            return true;
        }
    }
    if (arguments.getString("1").equalsIgnoreCase("hand")) {
        if (!(sender instanceof org.bukkit.command.ConsoleCommandSender)) {
            newItem = ((org.bukkit.entity.Player) (sender)).getItemInHand();
            if (newItem.getType() == org.bukkit.Material.AIR) {
                org.bukkit.command.CommandSender _CVAR14 = sender;
                java.lang.String _CVAR15 = "ProTip: You can NOT sell your hands!";
                _CVAR14.sendMessage(_CVAR15);
                return true;
            }
            de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("Hand ItemDetection OK: " + newItem.toString());
            if (arguments.getString("2") != null) {
                startBid = arguments.getDouble("2");
                if (startBid == null) {
                    org.bukkit.command.CommandSender _CVAR16 = sender;
                    java.lang.String _CVAR17 = "Info: Invalid Start Bid Format!";
                    _CVAR16.sendMessage(_CVAR17);
                    return true;
                }
                de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("StartBid OK");
            } else {
                startBid = 0.0;
                de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("No StartBid Set to 0");
            }
            if (arguments.getString("3") != null) {
                java.lang.Integer length = this.convert(arguments.getString("3"));
                if (length == null) {
                    org.bukkit.command.CommandSender _CVAR18 = sender;
                    java.lang.String _CVAR19 = "Error: Invalid Length Format";
                    _CVAR18.sendMessage(_CVAR19);
                    return true;
                }
                if (length <= de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_maxLength) {
                    auctionEnd = java.lang.System.currentTimeMillis() + length;
                    de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("AuctionLentgh OK");
                } else {
                     _CVAR21 = de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_maxLength;
                    java.lang.String _CVAR22 = "dd:hh:mm:ss";
                    java.lang.String _CVAR23 = org.apache.commons.lang.time.DateFormatUtils.format(_CVAR21, _CVAR22);
                    org.bukkit.command.CommandSender _CVAR20 = sender;
                     _CVAR24 = "Info: AuctionLength too high! Max: " + _CVAR23;
                    _CVAR20.sendMessage(_CVAR24);
                    return true;
                }
            } else {
                auctionEnd = java.lang.System.currentTimeMillis() + de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_standardLength;
                de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("No Auction Length Set to default");
            }
        }
    } else {
        newMaterial = org.bukkit.Material.matchMaterial(arguments.getString("1"));
        if (newMaterial == null) {
            java.lang.String _CVAR26 = " is not a valid Item";
            org.bukkit.command.CommandSender _CVAR25 = sender;
             _CVAR27 = ("Info: " + arguments.getString("1")) + _CVAR26;
            _CVAR25.sendMessage(_CVAR27);
            return true;
        }
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("Item MaterialDetection OK: " + newMaterial.toString());
        if (newMaterial.equals(org.bukkit.Material.AIR)) {
            org.bukkit.command.CommandSender _CVAR28 = sender;
            java.lang.String _CVAR29 = "Info: AIR ist not a valid Item!";
            _CVAR28.sendMessage(_CVAR29);
            return true;
        }
        amount = arguments.getInt("2");
        if (amount == null) {
            org.bukkit.command.CommandSender _CVAR30 = sender;
            java.lang.String _CVAR31 = "Info: No Amount given";
            _CVAR30.sendMessage(_CVAR31);
            return true;
        }
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("Quantity MaterialDetection OK: " + amount);
        newItem = new org.bukkit.inventory.ItemStack(newMaterial, amount);
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("Separate ItemDetection OK: " + newItem.toString());
        if (arguments.getString("3") != null) {
            startBid = arguments.getDouble("3");
            if (startBid == null) {
                org.bukkit.command.CommandSender _CVAR32 = sender;
                java.lang.String _CVAR33 = "Info: Invalid Start Bid Format!";
                _CVAR32.sendMessage(_CVAR33);
                return true;
            }
            de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("StartBid OK");
        } else {
            startBid = 0.0;
            de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("No StartBid Set to 0");
        }
        if (arguments.getString("4") != null) {
            java.lang.Integer length = this.convert(arguments.getString("4"));
            if (length == null) {
                org.bukkit.command.CommandSender _CVAR34 = sender;
                java.lang.String _CVAR35 = "Error: Invalid Length Format";
                _CVAR34.sendMessage(_CVAR35);
                return true;
            }
            if (length <= de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_maxLength) {
                auctionEnd = java.lang.System.currentTimeMillis() + length;
                de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("AuctionLentgh OK");
            } else {
                 _CVAR37 = de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_maxLength;
                java.lang.String _CVAR38 = "dd:hh:mm:ss";
                java.lang.String _CVAR39 = org.apache.commons.lang.time.DateFormatUtils.format(_CVAR37, _CVAR38);
                org.bukkit.command.CommandSender _CVAR36 = sender;
                 _CVAR40 = "Info: AuctionLength too high! Max: " + _CVAR39;
                _CVAR36.sendMessage(_CVAR40);
                return true;
            }
        } else {
            auctionEnd = java.lang.System.currentTimeMillis() + de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_standardLength;
            de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("No Auction Length Set to default");
        }
    }
    if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
        org.bukkit.command.CommandSender _CVAR41 = sender;
        java.lang.String _CVAR42 = "Info: Creating Auction as Server...";
        _CVAR41.sendMessage(_CVAR42);
    }
    if (newItem == null) {
        org.bukkit.command.CommandSender _CVAR43 = sender;
        java.lang.String _CVAR44 = "ProTip: You are a Server. You have no hands!";
        _CVAR43.sendMessage(_CVAR44);
        return true;
    }
    org.bukkit.inventory.ItemStack removeItem = newItem.clone();
    removeItem.setAmount(removeItem.getAmount() * multiAuction);
    if (!(sender instanceof org.bukkit.command.ConsoleCommandSender)) {
        if (((org.bukkit.entity.Player) (sender)).getInventory().contains(removeItem.getType(), removeItem.getAmount())) {
            de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("Item Amount OK");
        } else if (sender.hasPermission("auctionhouse.cheatItems")) {
            org.bukkit.command.CommandSender _CVAR45 = sender;
            java.lang.String _CVAR46 = "Info: Not enough Items! Cheat Items...";
            _CVAR45.sendMessage(_CVAR46);
        } else {
            org.bukkit.command.CommandSender _CVAR47 = sender;
            java.lang.String _CVAR48 = "Info: Not enough Items";
            _CVAR47.sendMessage(_CVAR48);
            return true;
        }
    }
    for (org.bukkit.inventory.ItemStack item : de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_blacklist) {
        if (item.getType().equals(newItem.getType())) {
            org.bukkit.command.CommandSender _CVAR49 = sender;
            java.lang.String _CVAR50 = "Error: This Item is blacklisted!";
            _CVAR49.sendMessage(_CVAR50);
            return true;
        }
    }
    de.paralleluniverse.Faithcaio.AuctionHouse.Auction newAuction;
    for (int i = 0; i < multiAuction; ++i) {
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            newAuction = new de.paralleluniverse.Faithcaio.AuctionHouse.Auction(newItem, de.paralleluniverse.Faithcaio.AuctionHouse.ServerBidder.getInstance(), auctionEnd, startBid);
            de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.log("Console adds Auction");
        } else {
            newAuction = new de.paralleluniverse.Faithcaio.AuctionHouse.Auction(newItem, de.paralleluniverse.Faithcaio.AuctionHouse.Bidder.getInstance(((org.bukkit.entity.Player) (sender))), auctionEnd, startBid);// Created Auction

        }
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug(("Auction #" + (i + 1)) + " init complete");
        if (!this.RegisterAuction(newAuction, sender)) {
            org.bukkit.command.CommandSender _CVAR51 = sender;
            java.lang.String _CVAR52 = "Info: Couldn't add all Auctions!";
            _CVAR51.sendMessage(_CVAR52);
            java.lang.String _CVAR54 = ")";
            org.bukkit.command.CommandSender _CVAR53 = sender;
             _CVAR55 = ("Info: Max Auctions reached! (" + de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_maxAuctions_overall) + _CVAR54;
            _CVAR53.sendMessage(_CVAR55);
            return true;
        }
    }
    if (!(sender instanceof org.bukkit.command.ConsoleCommandSender)) {
        ((org.bukkit.entity.Player) (sender)).getInventory().removeItem(removeItem);
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("UserAuction(s) added succesfully!");
    } else {
        de.paralleluniverse.Faithcaio.AuctionHouse.AuctionHouse.debug("ServerAuction(s) added succesfully!");
    }
    long auctionEnd = 1;
    long _CVAR57 = auctionEnd;
     _CVAR58 = de.paralleluniverse.Faithcaio.AuctionHouse.Commands.AddCommand.config.auction_timeFormat;
    java.lang.String _CVAR59 = org.apache.commons.lang.time.DateFormatUtils.format(_CVAR57, _CVAR58);
    org.bukkit.command.CommandSender _CVAR56 = sender;
     _CVAR60 = (((((("AuctionHouse: Started " + multiAuction) + " Auction(s) with ") + newItem.toString()) + ". StartBid: ") + startBid) + ". Auction ends: ") + _CVAR59;
    _CVAR56.sendMessage(_CVAR60);
    return true;
}