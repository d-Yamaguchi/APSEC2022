@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.util.LinkedList<java.lang.String> args) {
    if (!bedBlock.getType().equals(org.bukkit.Material.BED_BLOCK)) {
        sender.sendMessage(plugin.getMsg("must-target-bed"));
        return true;
    }
    if (args.size() == 0) {
        homeName = "home";
    } else {
        homeName = args.peek().toLowerCase();
    }
    if (homeName.toLowerCase().equals("list") || (!plugin.getValidNamePattern().matcher(homeName).matches())) {
        player.sendMessage(plugin.getMsg("invalid-name", homeName));
        return true;
    }
    com.norcode.bukkit.telewarp.commands.home.SetHomeCommand _CVAR0 = plugin;
    org.bukkit.entity.Player _CVAR2 = player;
     _CVAR1 = _CVAR0.getHomeManager();
    java.lang.String _CVAR3 = _CVAR2.getName();
    java.lang.String _CVAR4 = homeName;
    com.norcode.bukkit.telewarp.persistence.home.Home home = _CVAR1.getHome(_CVAR3, _CVAR4);
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    java.lang.String homeName = null;
    org.bukkit.entity.Player _CVAR21 = player;
    <nulltype> _CVAR22 = null;
    int _CVAR23 = 4;
    org.bukkit.block.Block bedBlock = _CVAR21.getTargetBlock(_CVAR22, _CVAR23);
    org.bukkit.block.Block _CVAR24 = bedBlock;
    org.bukkit.Location l = _CVAR24.getLocation();
    org.bukkit.block.Block _CVAR28 = _CVAR24;
    org.bukkit.Location l = _CVAR28.getLocation();
    org.bukkit.block.Block _CVAR31 = _CVAR28;
    org.bukkit.Location l = _CVAR31.getLocation();
    org.bukkit.block.Block _CVAR34 = _CVAR31;
    org.bukkit.Location l = _CVAR34.getLocation();
    org.bukkit.block.Block _CVAR37 = _CVAR34;
    org.bukkit.Location l = _CVAR37.getLocation();
    org.bukkit.block.Block _CVAR40 = _CVAR37;
    org.bukkit.Location l = _CVAR40.getLocation();
    if (home != null) {
        home.setWorld(l.getWorld().getName());
        home.setX(l.getX());
        home.setY(l.getY());
        home.setZ(l.getZ());
        home.setYaw(l.getYaw());
        home.setPitch(l.getPitch());
    } else {
        com.norcode.bukkit.telewarp.commands.home.SetHomeCommand _CVAR5 = plugin;
        org.bukkit.entity.Player _CVAR7 = player;
         _CVAR6 = _CVAR5.getHomeManager();
        java.lang.String _CVAR8 = _CVAR7.getName();
        // check totals
        java.util.Map<java.lang.String, com.norcode.bukkit.telewarp.persistence.home.Home> playerHomes = _CVAR6.getHomesFor(_CVAR8);
        org.bukkit.entity.Player _CVAR10 = player;
        com.norcode.bukkit.telewarp.commands.home.SetHomeCommand _CVAR9 = plugin;
        java.lang.String _CVAR11 = _CVAR10.getName();
         _CVAR12 = _CVAR9.getPlayerMaxHomes(_CVAR11);
         _CVAR13 = playerHomes.size() > _CVAR12;
         _CVAR14 = (playerHomes != null) && _CVAR13;
        if () {
            sender.sendMessage(plugin.getMsg("too-many-homes"));
            return true;
        }
        com.norcode.bukkit.telewarp.commands.home.SetHomeCommand _CVAR15 = plugin;
        org.bukkit.entity.Player _CVAR17 = player;
        java.lang.String _CVAR19 = homeName;
        org.bukkit.Location _CVAR25 = l;
        org.bukkit.World _CVAR26 = _CVAR25.getWorld();
        org.bukkit.Location _CVAR29 = l;
        org.bukkit.Location _CVAR32 = l;
        org.bukkit.Location _CVAR35 = l;
        org.bukkit.Location _CVAR38 = l;
        org.bukkit.Location _CVAR41 = l;
         _CVAR16 = _CVAR15.getHomeManager();
        java.lang.String _CVAR18 = _CVAR17.getName();
        java.lang.String _CVAR20 = _CVAR19.toLowerCase();
        java.lang.String _CVAR27 = _CVAR26.getName();
        double _CVAR30 = _CVAR29.getX();
        double _CVAR33 = _CVAR32.getY();
        double _CVAR36 = _CVAR35.getZ();
        float _CVAR39 = _CVAR38.getYaw();
        float _CVAR42 = _CVAR41.getPitch();
         _CVAR43 = _CVAR16.createHome(_CVAR18, _CVAR20, _CVAR27, _CVAR30, _CVAR33, _CVAR36, _CVAR39, _CVAR42);
        home = _CVAR43;
    }
    home.setPlugin(plugin);
    plugin.getHomeManager().saveHome(home);
    player.sendMessage(plugin.getMsg("home-set", home.getName()));
    return true;
}