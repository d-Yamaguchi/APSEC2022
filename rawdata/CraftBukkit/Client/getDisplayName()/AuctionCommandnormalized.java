public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] a) {
    java.util.LinkedList<java.lang.String> args = new java.util.LinkedList<java.lang.String>(java.util.Arrays.asList(a));
    if (!(sender instanceof org.bukkit.entity.Player)) {
        sender.sendMessage("Only players may auction");
        return true;
    }
    if (args.size() == 0) {
        couk.rob4001.iAuction.Messaging.playerMessage(player, "help");
        return true;
    }
    java.lang.String main = args.removeFirst();
    if (main.equalsIgnoreCase("start") || main.equalsIgnoreCase("s")) {
        couk.rob4001.util.chat.ChatManager.addListener(player);
        if (!player.hasPermission("auction.start")) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "error.perm");
            return true;
        }
        if (iAuction.getInstance().hasAuction(player)) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.hasauction");
            return true;
        }
        org.bukkit.entity.Player _CVAR1 = player;
         _CVAR0 = iAuction.getInstance().lots;
        java.lang.String _CVAR2 = _CVAR1.getDisplayName();
        boolean _CVAR3 = _CVAR0.containsKey(_CVAR2);
        if () {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.collection");
            return true;
        }
        if (args.size() != 2) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.wrongargs");
            return true;
        }
        int price;
        int time;
        try {
            price = java.lang.Integer.parseInt(args.getFirst());
            args.removeFirst();
            time = java.lang.Integer.parseInt(args.getFirst());
        } catch (java.lang.NumberFormatException e) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.wrongargs");
            return true;
        }
        if (time < iAuction.getInstance().getConfig().getInt("start.timelow")) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.timelow");
            return true;
        }
        if (time > iAuction.getInstance().getConfig().getInt("start.timehigh")) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.timehigh");
            return true;
        }
        if ((price < iAuction.getInstance().getConfig().getInt("start.pricelow")) && (!((-1) == iAuction.getInstance().getConfig().getInt("start.pricelow")))) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.pricelow");
            return true;
        }
        if ((price > iAuction.getInstance().getConfig().getInt("start.pricehigh")) && (!((-1) == iAuction.getInstance().getConfig().getInt("start.pricehigh")))) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "start.pricehigh");
            return true;
        }
        ((org.bukkit.entity.Player) (sender)).openInventory(new couk.rob4001.iAuction.gui.BasicNewInterface(new couk.rob4001.iAuction.gui.BasicNewLayout(iAuction.getInstance().getConfig().getInt("start.rows")), price, time).getInventory());
        return true;
    }
    if (main.equalsIgnoreCase("list") || main.equalsIgnoreCase("l")) {
        couk.rob4001.util.chat.ChatManager.addListener(player);
        java.util.Iterator<couk.rob4001.iAuction.Auction> it = iAuction.auctionQueue.iterator();
        java.lang.Integer i = 0;
        couk.rob4001.iAuction.Messaging.playerMessage(player, "auction.listtitle");
        while (it.hasNext()) {
            i++;
            couk.rob4001.iAuction.Auction au = it.next();
            couk.rob4001.iAuction.Messaging.playerMessage(player, "auction.info", i.toString(), au.getOwner().getDisplayName(), au.getTime(), au.getBid());
        } 
        return true;
    }
    if (main.equalsIgnoreCase("help") || main.equalsIgnoreCase("h")) {
        couk.rob4001.iAuction.Messaging.playerMessage(player, "help");
        return true;
    }
    if (main.equalsIgnoreCase("listen")) {
        if (!player.hasPermission("auction.listen")) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "error.perm");
            return true;
        }
        org.bukkit.entity.Player _CVAR5 = player;
         _CVAR4 = iAuction.getInstance().listeners;
        java.lang.String _CVAR6 = _CVAR5.getDisplayName();
        _CVAR4.add(_CVAR6);
        couk.rob4001.util.chat.ChatManager.addListener(player);
        couk.rob4001.iAuction.Messaging.playerMessage(player, "listen.on");
        return true;
    }
    if (main.equalsIgnoreCase("mute")) {
        if (!player.hasPermission("auction.mute")) {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "error.perm");
            return true;
        }
        org.bukkit.entity.Player _CVAR8 = player;
         _CVAR7 = iAuction.getInstance().listeners;
        java.lang.String _CVAR9 = _CVAR8.getDisplayName();
        boolean _CVAR10 = _CVAR7.contains(_CVAR9);
        if () {
            org.bukkit.entity.Player _CVAR12 = player;
             _CVAR11 = iAuction.getInstance().listeners;
            java.lang.String _CVAR13 = _CVAR12.getDisplayName();
            _CVAR11.remove(_CVAR13);
        }
        couk.rob4001.util.chat.ChatManager.removeListener(player);
        couk.rob4001.iAuction.Messaging.playerMessage(player, "listen.off");
        return true;
    }
    if (main.equalsIgnoreCase("collect")) {
        org.bukkit.entity.Player _CVAR15 = player;
         _CVAR14 = iAuction.getInstance().lots;
        java.lang.String _CVAR16 = _CVAR15.getDisplayName();
        boolean _CVAR17 = _CVAR14.containsKey(_CVAR16);
        if () {
            couk.rob4001.util.InventoryUtil.setupCollect(player).open();
        } else {
            couk.rob4001.iAuction.Messaging.playerMessage(player, "error.nocollect");
        }
        return true;
    }
    // TODO: Add Custom Command support ??
    couk.rob4001.iAuction.Auction auc = iAuction.getCurrent();
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    if (auc != null) {
        if (main.equalsIgnoreCase("bid")) {
            if (!player.hasPermission("auction.bid")) {
                couk.rob4001.iAuction.Messaging.playerMessage(player, "error.perm");
                return true;
            }
            org.bukkit.entity.Player _CVAR19 = player;
             _CVAR18 = iAuction.getInstance().lots;
            java.lang.String _CVAR20 = _CVAR19.getDisplayName();
            boolean _CVAR21 = _CVAR18.containsKey(_CVAR20);
            if () {
                couk.rob4001.iAuction.Messaging.playerMessage(player, "bidding.collection");
                return true;
            }
            if (args.size() > 1) {
                auc.bid(player, args);
            } else {
                auc.bid(player, args);
            }
            couk.rob4001.util.chat.ChatManager.addListener(player);
            return true;
        }
        if (main.equalsIgnoreCase("info")) {
            ((org.bukkit.entity.Player) (sender)).openInventory(new couk.rob4001.iAuction.gui.BasicInfoInterface(auc.getInventory()).getInventory());
            couk.rob4001.util.chat.ChatManager.addListener(player);
            return true;
        }
        if (main.equalsIgnoreCase("end") || main.equalsIgnoreCase("e")) {
            couk.rob4001.util.chat.ChatManager.addListener(player);
            auc.end(player, args);
            return true;
        }
        if (main.equalsIgnoreCase("cancel") || main.equalsIgnoreCase("c")) {
            couk.rob4001.util.chat.ChatManager.addListener(player);
            auc.cancel(player, args);
            return true;
        }
    } else {
        couk.rob4001.iAuction.Messaging.playerMessage(player, "error.invalidcommand");
    }
    return true;
}