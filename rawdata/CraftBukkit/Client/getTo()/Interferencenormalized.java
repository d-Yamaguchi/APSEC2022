public void update(org.bukkit.entity.Player player, org.bukkit.event.player.PlayerMoveEvent event) {
    // Check that the player actually has a compas..
    int index = player.getInventory().first(org.bukkit.Material.COMPASS);
    if (index == (-1)) {
        // No compass.. nothing to see here, move on.
        return;
    }
    org.bukkit.event.player.PlayerMoveEvent _CVAR0 = event;
    org.bukkit.Location _CVAR1 = _CVAR0.getTo();
    org.bukkit.Location _CVAR3 = lastPosition;
    org.bukkit.World _CVAR2 = _CVAR1.getWorld();
    org.bukkit.World _CVAR4 = _CVAR3.getWorld();
    boolean _CVAR5 = _CVAR2.equals(_CVAR4);
    boolean _CVAR6 = (lastPosition != null) && _CVAR5;
    org.bukkit.event.player.PlayerMoveEvent _CVAR8 = event;
    org.bukkit.Location _CVAR9 = _CVAR8.getTo();
    org.bukkit.Location _CVAR10 = lastPosition;
    double _CVAR11 = _CVAR9.distanceSquared(_CVAR10);
    boolean _CVAR12 = _CVAR11 < 2;
    boolean _CVAR7 = _CVAR6 && _CVAR12;
    // First check that we aren't spamming this - so check every 2 blocks worth of movement
    if () {
        return;
    }
    org.bukkit.event.player.PlayerMoveEvent _CVAR13 = event;
    org.bukkit.Location _CVAR14 = _CVAR13.getTo();
    lastPosition = _CVAR14;
    int distance = za.dats.bukkit.memorystone.Config.getCompassToUnmemorizedStoneDistance();
    distance = distance * distance;// Just to make things slightly quicker

    za.dats.bukkit.memorystone.MemoryStone closestStone = null;
    double closestDistance = 0;
    for (za.dats.bukkit.memorystone.MemoryStone stone : za.dats.bukkit.memorystone.MemoryStonePlugin.getInstance().getMemoryStoneManager().getStones()) {
        if (stone.getSign() == null) {
            continue;
        }
        if (!stone.getSign().getWorld().getName().equals(player.getWorld().getName())) {
            continue;
        }
        if (za.dats.bukkit.memorystone.MemoryStonePlugin.getInstance().getCompassManager().isMemorized(player, stone)) {
            continue;
        }
        org.bukkit.event.player.PlayerMoveEvent _CVAR15 = event;
        za.dats.bukkit.memorystone.MemoryStone _CVAR17 = stone;
         _CVAR18 = _CVAR17.getSign();
         _CVAR19 = _CVAR18.getBlock();
        org.bukkit.Location _CVAR16 = _CVAR15.getTo();
         _CVAR20 = _CVAR19.getLocation();
        double currentDistance = _CVAR16.distanceSquared(_CVAR20);
        if (player.getLocation().distanceSquared(stone.getSign().getBlock().getLocation()) < distance) {
            if ((closestStone == null) || (closestDistance > currentDistance)) {
                closestStone = stone;
                closestDistance = currentDistance;
            }
        }
    }
    // Point compass
    if (closestStone != null) {
        // Should only set old location if we aren't moving from stone to stone.
        if (this.stone == null) {
            oldLocation = player.getCompassTarget();
        }
        this.stone = closestStone;
        player.setCompassTarget(this.stone.getSign().getBlock().getLocation());
        if (!active) {
            // Interference message was not sent yet - send it
            player.sendMessage(za.dats.bukkit.memorystone.Config.getColorLang("compassinterference"));
            active = true;
        }
    } else if (oldLocation != null) {
        // Unset compass
        if (active) {
            player.sendMessage(za.dats.bukkit.memorystone.Config.getColorLang("compasslostinterference"));
        }
        active = false;
        this.stone = null;
        player.setCompassTarget(oldLocation);
        oldLocation = null;
    }
}