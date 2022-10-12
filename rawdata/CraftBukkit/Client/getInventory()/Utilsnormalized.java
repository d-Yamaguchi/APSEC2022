public static void useFuel(org.bukkit.entity.Player player, boolean mustBeHolding, double factor) {
    int coalToGiveBack;
    org.bukkit.inventory.ItemStack myitem;
    if (mustBeHolding) {
        org.bukkit.entity.Player _CVAR0 = player;
        org.bukkit.inventory.PlayerInventory _CVAR1 = _CVAR0.getInventory();
        org.bukkit.inventory.ItemStack _CVAR2 = _CVAR1.getItemInHand();
        myitem = _CVAR2;
    } else {
        org.bukkit.entity.Player _CVAR3 = player;
         _CVAR7 = EasyJetpack.getInstance();
         _CVAR8 = _CVAR7.getConfig();
        java.lang.String _CVAR9 = "fuel.material";
        java.lang.String _CVAR10 = "COAL";
         _CVAR11 = _CVAR8.getString(_CVAR9, _CVAR10);
        org.bukkit.entity.Player _CVAR5 = player;
        org.bukkit.entity.Player _CVAR15 = _CVAR5;
        org.bukkit.inventory.PlayerInventory _CVAR6 = _CVAR15.getInventory();
        org.bukkit.Material _CVAR12 = org.bukkit.Material.getMaterial(_CVAR11);
        org.bukkit.inventory.PlayerInventory _CVAR4 = _CVAR3.getInventory();
        int _CVAR13 = _CVAR6.first(_CVAR12);
        org.bukkit.inventory.ItemStack _CVAR14 = _CVAR4.getItem(_CVAR13);
        myitem = _CVAR14;
    }
    coalToGiveBack = myitem.getAmount() - 1;
    if (coalToGiveBack > 0) {
        org.bukkit.entity.Player _CVAR16 = player;
        org.bukkit.inventory.PlayerInventory _CVAR17 = _CVAR16.getInventory();
        org.bukkit.inventory.ItemStack _CVAR18 = myitem;
        _CVAR17.remove(_CVAR18);
        if (!mustBeHolding) {
            org.bukkit.entity.Player _CVAR19 = player;
             _CVAR21 = EasyJetpack.getInstance();
             _CVAR22 = _CVAR21.getConfig();
            java.lang.String _CVAR23 = "fuel.material";
            java.lang.String _CVAR24 = "COAL";
             _CVAR25 = _CVAR22.getString(_CVAR23, _CVAR24);
            org.bukkit.Material _CVAR26 = org.bukkit.Material.getMaterial(_CVAR25);
            int _CVAR27 = 1;
            org.bukkit.inventory.PlayerInventory _CVAR20 = _CVAR19.getInventory();
            org.bukkit.inventory.ItemStack _CVAR28 = new org.bukkit.inventory.ItemStack(_CVAR26, _CVAR27);
            _CVAR20.addItem(_CVAR28);
        } else {
            player.setItemInHand(new org.bukkit.inventory.ItemStack(org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL")), 1));
        }
    }
    org.bukkit.inventory.ItemStack item;
    if (mustBeHolding) {
        item = player.getItemInHand();
    } else {
        org.bukkit.entity.Player _CVAR29 = player;
         _CVAR33 = EasyJetpack.getInstance();
         _CVAR34 = _CVAR33.getConfig();
        java.lang.String _CVAR35 = "fuel.material";
        java.lang.String _CVAR36 = "COAL";
         _CVAR37 = _CVAR34.getString(_CVAR35, _CVAR36);
        org.bukkit.entity.Player _CVAR31 = player;
        org.bukkit.entity.Player _CVAR41 = _CVAR31;
        org.bukkit.inventory.PlayerInventory _CVAR32 = _CVAR41.getInventory();
        org.bukkit.Material _CVAR38 = org.bukkit.Material.getMaterial(_CVAR37);
        org.bukkit.inventory.PlayerInventory _CVAR30 = _CVAR29.getInventory();
        int _CVAR39 = _CVAR32.first(_CVAR38);
        org.bukkit.inventory.ItemStack _CVAR40 = _CVAR30.getItem(_CVAR39);
        item = _CVAR40;
    }
    short fuelUsage;
    if (!(((item != null) && item.hasItemMeta()) && item.getItemMeta().hasLore())) {
        // Unused fuel
        fuelUsage = 100;
    } else {
        if (mustBeHolding) {
            item = player.getItemInHand();
        } else {
            org.bukkit.entity.Player _CVAR42 = player;
            org.bukkit.entity.Player _CVAR44 = player;
            boolean _CVAR45 = false;
            org.bukkit.inventory.PlayerInventory _CVAR43 = _CVAR42.getInventory();
            int _CVAR46 = net.jselby.ej.Utils.findCoal(_CVAR44, _CVAR45);
            org.bukkit.inventory.ItemStack _CVAR47 = _CVAR43.getItem(_CVAR46);
            item = _CVAR47;
        }
        java.lang.String fuel = item.getItemMeta().getLore().get(0).substring(2, item.getItemMeta().getLore().get(0).indexOf("%"));
        fuelUsage = java.lang.Short.parseShort(fuel);
    }
    fuelUsage -= (((double) (100)) / ((double) (10))) / factor;
    java.util.List<java.lang.String> newLore = new java.util.ArrayList<java.lang.String>();
    newLore.add(((org.bukkit.ChatColor.RESET + "") + fuelUsage) + "% left");
    if (mustBeHolding) {
        org.bukkit.entity.Player _CVAR48 = player;
        org.bukkit.inventory.PlayerInventory _CVAR49 = _CVAR48.getInventory();
        org.bukkit.inventory.ItemStack _CVAR50 = _CVAR49.getItemInHand();
        item = _CVAR50;
    } else {
        org.bukkit.entity.Player _CVAR51 = player;
        org.bukkit.entity.Player _CVAR53 = player;
        boolean _CVAR54 = true;
        org.bukkit.inventory.PlayerInventory _CVAR52 = _CVAR51.getInventory();
        int _CVAR55 = net.jselby.ej.Utils.findCoal(_CVAR53, _CVAR54);
        org.bukkit.inventory.ItemStack _CVAR56 = _CVAR52.getItem(_CVAR55);
        item = _CVAR56;
    }
    org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
    itemMeta.setDisplayName(((((((org.bukkit.ChatColor.RESET + "") + org.bukkit.ChatColor.DARK_RED) + "Burning ") + org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL")).name().toLowerCase()) + " - ") + fuelUsage) + "% left");
    itemMeta.setLore(newLore);
    item.setItemMeta(itemMeta);
    org.bukkit.entity.Player _CVAR64 = player;
    boolean _CVAR65 = true;
    org.bukkit.entity.Player _CVAR69 = _CVAR64;
    boolean _CVAR70 = _CVAR65;
    int coal = net.jselby.ej.Utils.findCoal(_CVAR69, _CVAR70);
    if (fuelUsage < 1) {
        if (mustBeHolding) {
            org.bukkit.entity.Player _CVAR57 = player;
            org.bukkit.Material _CVAR59 = org.bukkit.Material.AIR;
            int _CVAR60 = 1;
            org.bukkit.inventory.PlayerInventory _CVAR58 = _CVAR57.getInventory();
            org.bukkit.inventory.ItemStack _CVAR61 = new org.bukkit.inventory.ItemStack(_CVAR59, _CVAR60);
            _CVAR58.setItemInHand(_CVAR61);
        } else {
            org.bukkit.entity.Player _CVAR62 = player;
            org.bukkit.inventory.PlayerInventory _CVAR63 = _CVAR62.getInventory();
            int _CVAR66 = coal;
            _CVAR63.clear(_CVAR66);
        }
        net.jselby.ej.Utils.shuffleCoal(player, mustBeHolding);
    } else if (!mustBeHolding) {
        org.bukkit.entity.Player _CVAR67 = player;
        org.bukkit.inventory.PlayerInventory _CVAR68 = _CVAR67.getInventory();
        int _CVAR71 = coal;
        org.bukkit.inventory.ItemStack _CVAR72 = item;
        _CVAR68.setItem(_CVAR71, _CVAR72);
    } else {
        org.bukkit.entity.Player _CVAR73 = player;
        org.bukkit.inventory.PlayerInventory _CVAR74 = _CVAR73.getInventory();
        org.bukkit.inventory.ItemStack _CVAR75 = item;
        _CVAR74.setItemInHand(_CVAR75);
    }
    if (coalToGiveBack > 0) {
        org.bukkit.entity.Player _CVAR76 = player;
         _CVAR78 = EasyJetpack.getInstance();
         _CVAR79 = _CVAR78.getConfig();
        java.lang.String _CVAR80 = "fuel.material";
        java.lang.String _CVAR81 = "COAL";
         _CVAR82 = _CVAR79.getString(_CVAR80, _CVAR81);
        org.bukkit.Material _CVAR83 = org.bukkit.Material.getMaterial(_CVAR82);
        int _CVAR84 = coalToGiveBack;
        org.bukkit.inventory.PlayerInventory _CVAR77 = _CVAR76.getInventory();
        org.bukkit.inventory.ItemStack _CVAR85 = new org.bukkit.inventory.ItemStack(_CVAR83, _CVAR84);
        _CVAR77.addItem(_CVAR85);
    }
}