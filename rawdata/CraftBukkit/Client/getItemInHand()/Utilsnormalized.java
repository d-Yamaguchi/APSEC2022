public static void useFuel(org.bukkit.entity.Player player, boolean mustBeHolding, double factor) {
    int coalToGiveBack;
    org.bukkit.inventory.ItemStack myitem;
    if (mustBeHolding) {
        org.bukkit.entity.Player _CVAR0 = player;
        org.bukkit.inventory.PlayerInventory _CVAR1 = _CVAR0.getInventory();
        org.bukkit.inventory.ItemStack _CVAR2 = _CVAR1.getItemInHand();
        myitem = _CVAR2;
    } else {
        myitem = player.getInventory().getItem(player.getInventory().first(org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL"))));
    }
    coalToGiveBack = myitem.getAmount() - 1;
    if (coalToGiveBack > 0) {
        player.getInventory().remove(myitem);
        if (!mustBeHolding) {
            player.getInventory().addItem(new org.bukkit.inventory.ItemStack(org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL")), 1));
        } else {
            player.setItemInHand(new org.bukkit.inventory.ItemStack(org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL")), 1));
        }
    }
    org.bukkit.inventory.ItemStack item;
    if (mustBeHolding) {
        item = player.getItemInHand();
    } else {
        item = player.getInventory().getItem(player.getInventory().first(org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL"))));
    }
    short fuelUsage;
    if (!(((item != null) && item.hasItemMeta()) && item.getItemMeta().hasLore())) {
        // Unused fuel
        fuelUsage = 100;
    } else {
        if (mustBeHolding) {
            item = player.getItemInHand();
        } else {
            item = player.getInventory().getItem(net.jselby.ej.Utils.findCoal(player, false));
        }
        java.lang.String fuel = item.getItemMeta().getLore().get(0).substring(2, item.getItemMeta().getLore().get(0).indexOf("%"));
        fuelUsage = java.lang.Short.parseShort(fuel);
    }
    fuelUsage -= (((double) (100)) / ((double) (10))) / factor;
    java.util.List<java.lang.String> newLore = new java.util.ArrayList<java.lang.String>();
    newLore.add(((org.bukkit.ChatColor.RESET + "") + fuelUsage) + "% left");
    int coal = net.jselby.ej.Utils.findCoal(player, true);
    if (mustBeHolding) {
        org.bukkit.entity.Player _CVAR3 = player;
        org.bukkit.inventory.PlayerInventory _CVAR4 = _CVAR3.getInventory();
        org.bukkit.inventory.ItemStack _CVAR5 = _CVAR4.getItemInHand();
        item = _CVAR5;
    } else {
        item = player.getInventory().getItem(net.jselby.ej.Utils.findCoal(player, true));
    }
    org.bukkit.inventory.meta.ItemMeta itemMeta = item.getItemMeta();
    itemMeta.setDisplayName(((((((org.bukkit.ChatColor.RESET + "") + org.bukkit.ChatColor.DARK_RED) + "Burning ") + org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL")).name().toLowerCase()) + " - ") + fuelUsage) + "% left");
    itemMeta.setLore(newLore);
    item.setItemMeta(itemMeta);
    if (fuelUsage < 1) {
        if (mustBeHolding) {
            player.getInventory().setItemInHand(new org.bukkit.inventory.ItemStack(org.bukkit.Material.AIR, 1));
        } else {
            player.getInventory().clear(coal);
        }
        net.jselby.ej.Utils.shuffleCoal(player, mustBeHolding);
    } else if (!mustBeHolding) {
        player.getInventory().setItem(coal, item);
    } else {
        player.getInventory().setItemInHand(item);
    }
    if (coalToGiveBack > 0) {
        player.getInventory().addItem(new org.bukkit.inventory.ItemStack(org.bukkit.Material.getMaterial(EasyJetpack.getInstance().getConfig().getString("fuel.material", "COAL")), coalToGiveBack));
    }
}