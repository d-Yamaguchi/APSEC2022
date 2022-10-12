package net.miscjunk.fancyshop;
public class FancyShopCommandExecutor implements org.bukkit.command.CommandExecutor {
    enum PendingCommand {

        CREATE,
        REMOVE,
        ADMIN_ON,
        ADMIN_OFF;}

    private net.miscjunk.fancyshop.FancyShop plugin;

    boolean flagsInstalled = false;

    java.util.Map<java.lang.String, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand> pending;

    java.util.Map<java.lang.String, org.bukkit.scheduler.BukkitTask> tasks;

    public FancyShopCommandExecutor(net.miscjunk.fancyshop.FancyShop plugin) {
        this.plugin = plugin;
        this.pending = new java.util.HashMap<java.lang.String, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand>();
        this.tasks = new java.util.HashMap<java.lang.String, org.bukkit.scheduler.BukkitTask>();
        flagsInstalled = org.bukkit.Bukkit.getServer().getPluginManager().isPluginEnabled("Flags");
        if (flagsInstalled) {
            org.bukkit.Bukkit.getLogger().info("Found Flags, enabling region support");
            io.github.alshain01.flags.Registrar flagsRegistrar = io.github.alshain01.flags.Flags.getRegistrar();
            io.github.alshain01.flags.Flag flag = flagsRegistrar.register("FancyShop", "Allow creating shops", false, "FancyShop", "Entering shops area", "Leaving shops area");
        } else {
            org.bukkit.Bukkit.getLogger().info("Flags is not installed, disabling region support");
        }
    }

    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
        if (!(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("Must be a player.");
            return true;
        }
        org.bukkit.entity.Player p = ((org.bukkit.entity.Player) (sender));
        if (args.length < 1) {
            printUsage(sender);
        } else if (args[0].equals("create")) {
            create(p, cmd, label, args);
        } else if (args[0].equals("remove")) {
            remove(p, cmd, label, args);
        } else if (args[0].equals("setadmin")) {
            setAdmin(p, cmd, label, args);
        } else if (args[0].equals("currency")) {
            currency(p, cmd, label, args);
        } else {
            printUsage(sender);
        }
        return true;
    }

    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        if ()
            return;

        __SmPLUnsupported__(0);
    }

    private void remove(org.bukkit.entity.Player player, org.bukkit.inventory.Inventory inv) {
        if (net.miscjunk.fancyshop.Shop.isShop(inv)) {
            net.miscjunk.fancyshop.Shop shop = net.miscjunk.fancyshop.Shop.fromInventory(inv);
            if ((!shop.getOwner().equals(player.getUniqueId())) && (!player.hasPermission("fancyshop.remove"))) {
                net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("remove.owner"));
            } else {
                net.miscjunk.fancyshop.ShopRepository.remove(shop);
                net.miscjunk.fancyshop.Shop.removeShop(shop.getLocation());
                net.miscjunk.fancyshop.Chat.s(player, net.miscjunk.fancyshop.I18n.s("remove.confirm"));
            }
        } else {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("remove.no-shop"));
        }
        clearPending(player);
    }

    private void create(org.bukkit.entity.Player player, org.bukkit.inventory.Inventory inv) {
        if (net.miscjunk.fancyshop.Shop.isShop(inv)) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("create.exists"));
        } else if (!regionAllows(player, inv)) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("create.region"));
        } else {
            net.miscjunk.fancyshop.Shop shop = net.miscjunk.fancyshop.Shop.fromInventory(inv, player.getUniqueId());
            net.miscjunk.fancyshop.ShopRepository.store(shop);
            net.miscjunk.fancyshop.Chat.s(player, net.miscjunk.fancyshop.I18n.s("create.confirm"));
            net.miscjunk.fancyshop.Chat.i(player, net.miscjunk.fancyshop.I18n.s("create.confirm2"));
            net.miscjunk.fancyshop.Chat.i(player, net.miscjunk.fancyshop.I18n.s("create.confirm3"));
            shop.edit(player);
        }
        clearPending(player);
    }

    private void setAdmin(org.bukkit.entity.Player player, org.bukkit.inventory.Inventory inv, boolean admin) {
        if (!net.miscjunk.fancyshop.Shop.isShop(inv)) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("setadmin.no-shop"));
        } else {
            net.miscjunk.fancyshop.Shop shop = net.miscjunk.fancyshop.Shop.fromInventory(inv);
            shop.setAdmin(admin);
            net.miscjunk.fancyshop.ShopRepository.store(shop);
            if (admin) {
                net.miscjunk.fancyshop.Chat.s(player, "setadmin.confirm-true");
            } else {
                net.miscjunk.fancyshop.Chat.s(player, "setadmin.confirm-false");
            }
        }
        clearPending(player);
    }

    private void remove(org.bukkit.entity.Player player, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
        if (!player.hasPermission("fancyshop.create")) {
            // not typo - can't remove if we can't create
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("remove.permission"));
            return;
        } else if (args.length > 1) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("remove.usage"));
            return;
        }
        net.miscjunk.fancyshop.Chat.i(player, net.miscjunk.fancyshop.I18n.s("remove.prompt"));
        setPending(player, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand.REMOVE);
    }

    private void create(org.bukkit.entity.Player player, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
        if (!player.hasPermission("fancyshop.create")) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("create.permission"));
            return;
        } else if (args.length > 1) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("create.usage"));
            return;
        }
        net.miscjunk.fancyshop.Chat.i(player, net.miscjunk.fancyshop.I18n.s("create.prompt"));
        setPending(player, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand.CREATE);
    }

    private void setAdmin(org.bukkit.entity.Player player, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
        if (!player.hasPermission("fancyshop.setadmin")) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("setadmin.permission"));
            return;
        }
        if ((args.length == 1) || ((args.length == 2) && args[1].equals("true"))) {
            net.miscjunk.fancyshop.Chat.i(player, net.miscjunk.fancyshop.I18n.s("setadmin.prompt-true"));
            setPending(player, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand.ADMIN_ON);
        } else if ((args.length == 2) && args[1].equals("false")) {
            net.miscjunk.fancyshop.Chat.i(player, net.miscjunk.fancyshop.I18n.s("setadmin.prompt-false"));
            setPending(player, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand.ADMIN_OFF);
        } else {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("setadmin.usage"));
        }
    }

    private void currency(org.bukkit.entity.Player player, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
        if (!player.hasPermission("fancyshop.currency")) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("currency.permission"));
            return;
        }
        if (args.length != 2) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("currency.usage"));
            return;
        }
        java.lang.String name = args[1].trim();
        if (net.miscjunk.fancyshop.CurrencyManager.getInstance().isCustomCurrency(name)) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("currency.exists"));
            return;
        }
        if ((player.getItemInHand() == null) || (player.getItemInHand().getAmount() == 0)) {
            net.miscjunk.fancyshop.Chat.e(player, net.miscjunk.fancyshop.I18n.s("currency.empty"));
            return;
        }
        net.miscjunk.fancyshop.CurrencyManager.getInstance().addCustomCurrency(name, player.getItemInHand());
        net.miscjunk.fancyshop.Chat.s(player, net.miscjunk.fancyshop.I18n.s("currency.confirm", name));
    }

    private boolean regionAllows(org.bukkit.entity.Player player, org.bukkit.inventory.Inventory inv) {
        if (!flagsInstalled)
            return true;

        if (player.hasPermission("fancyshop.create.anywhere"))
            return true;

        io.github.alshain01.flags.Registrar flagsRegistrar = io.github.alshain01.flags.Flags.getRegistrar();
        io.github.alshain01.flags.Flag flag = flagsRegistrar.getFlag("FancyShop");
        org.bukkit.inventory.InventoryHolder h = inv.getHolder();
        org.bukkit.Location l;
        io.github.alshain01.flags.area.Area area;
        if (h instanceof org.bukkit.block.BlockState) {
            l = ((org.bukkit.block.BlockState) (h)).getLocation();
            area = io.github.alshain01.flags.CuboidType.getActive().getAreaAt(l);
        } else if (h instanceof org.bukkit.block.DoubleChest) {
            l = ((org.bukkit.block.DoubleChest) (h)).getLocation();
            area = io.github.alshain01.flags.CuboidType.getActive().getAreaAt(l);
        } else {
            area = io.github.alshain01.flags.CuboidType.DEFAULT.getAreaAt(player.getLocation());
        }
        java.util.Set<java.lang.String> trusted = area.getPlayerTrustList(flag);
        if (trusted.contains(player.getName().toLowerCase())) {
            return true;
        } else {
            for (java.lang.String s : area.getPermissionTrustList(flag)) {
                if (player.hasPermission(s))
                    return true;

            }
            return trusted.isEmpty() && area.getValue(flag, false);
        }
    }

    private void setPending(org.bukkit.entity.Player player, net.miscjunk.fancyshop.FancyShopCommandExecutor.PendingCommand cmd) {
        final java.lang.String name = player.getName();
        if (tasks.containsKey(name)) {
            org.bukkit.scheduler.BukkitTask task = tasks.get(name);
            if (task != null)
                task.cancel();

        }
        pending.put(name, cmd);
        tasks.put(name, new org.bukkit.scheduler.BukkitRunnable() {
            public void run() {
                pending.remove(name);
            }
        }.runTaskLater(plugin, 60 * 20));
    }

    private void clearPending(org.bukkit.entity.Player player) {
        final java.lang.String name = player.getName();
        if (tasks.containsKey(name)) {
            org.bukkit.scheduler.BukkitTask task = tasks.get(name);
            if (task != null)
                task.cancel();

            tasks.remove(name);
        }
        if (pending.containsKey(name))
            pending.remove(name);

    }

    public boolean hasPending(org.bukkit.entity.Player player) {
        return pending.containsKey(player.getName());
    }

    public void printUsage(org.bukkit.command.CommandSender sender) {
        net.miscjunk.fancyshop.Chat.i(sender, net.miscjunk.fancyshop.I18n.s("usage.main"));
        if ((sender instanceof org.bukkit.entity.Player) && ((org.bukkit.entity.Player) (sender)).hasPermission("fancyshop.setadmin")) {
            net.miscjunk.fancyshop.Chat.i(sender, net.miscjunk.fancyshop.I18n.s("usage.setadmin"));
        }
        if ((sender instanceof org.bukkit.entity.Player) && ((org.bukkit.entity.Player) (sender)).hasPermission("fancyshop.currency")) {
            net.miscjunk.fancyshop.Chat.i(sender, net.miscjunk.fancyshop.I18n.s("usage.currency"));
        }
    }
}