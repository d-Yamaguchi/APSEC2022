@org.bukkit.event.EventHandler(ignoreCancelled = true)
public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
    // long s0 = System.nanoTime();
    if (!event.getPlayer().hasPermission("autohop.hop")) {
        return;
    }
    // delta X and Z - which way the player is going
    double dx = to.getX() - from.getX();
    double dz = to.getZ() - from.getZ();
    // extrapolation of next X and Z the player will get to
    double nextX = to.getX() + dx;
    double nextZ = to.getZ() + dz;
    // X and Z position within a block - a player pushing against a wall will
    // have X or Z either < ~0.3 or > ~0.7 due to player entity bounding box size
    double tx = nextX - java.lang.Math.floor(nextX);
    double tz = nextZ - java.lang.Math.floor(nextZ);
    // System.out.println("yaw = " + t.getYaw() + " dx = " + dx + " dz = " + dz + " nextX = " + nextX + " tx = " + tx + " nextZ = " + nextZ + " tz = " + tz);
    float yaw = to.getYaw() % 360;
    if (yaw < 0) {
        yaw += 360;
    }
    if ((((yaw >= 45) && (yaw < 135)) && (dx <= 0.0)) && (tx < 0.3001)) {
        face = org.bukkit.block.BlockFace.NORTH;
    } else if ((((yaw >= 135) && (yaw < 225)) && (dz <= 0.0)) && (tz < 0.3001)) {
        face = org.bukkit.block.BlockFace.EAST;
    } else if ((((yaw >= 225) && (yaw < 315)) && (dx >= 0.0)) && (tx > 0.6999)) {
        face = org.bukkit.block.BlockFace.SOUTH;
    } else if ((((yaw >= 315) || (yaw < 45)) && (dz >= 0.0)) && (tz > 0.6999)) {
        face = org.bukkit.block.BlockFace.WEST;
    } else {
        return;
    }
    // System.out.println("check block " + face + " type = " + b.getType());
    boolean climbable = false;
    org.bukkit.block.Block _CVAR4 = b;
    int _CVAR5 = _CVAR4.getTypeId();
    boolean _CVAR6 = isStairs(_CVAR5);
    if () {
        org.bukkit.material.Stairs s = ((org.bukkit.material.Stairs) (b.getState().getData()));
        climbable = s.getAscendingDirection() == face;
        // System.out.println("see some stairs: climbable = " + climbable);
    } else {
        org.bukkit.block.Block _CVAR11 = b;
        int _CVAR12 = _CVAR11.getTypeId();
        boolean _CVAR13 = isSlab(_CVAR12);
        if () {
            climbable = true;
        }
    }
    boolean _CVAR14 = !me.desht.autohop.AutoHop.passable.contains(b.getTypeId());
    boolean _CVAR15 = (!climbable) && _CVAR14;
    org.bukkit.event.player.PlayerMoveEvent _CVAR0 = event;
    org.bukkit.event.player.PlayerMoveEvent _CVAR7 = _CVAR0;
    org.bukkit.event.player.PlayerMoveEvent _CVAR16 = _CVAR7;
    org.bukkit.event.player.PlayerMoveEvent _CVAR23 = _CVAR16;
    org.bukkit.Location to = _CVAR23.getTo();
    org.bukkit.Location _CVAR1 = to;
    org.bukkit.Location _CVAR8 = _CVAR1;
    org.bukkit.Location _CVAR17 = _CVAR8;
    org.bukkit.Location _CVAR24 = _CVAR17;
    org.bukkit.block.Block _CVAR2 = _CVAR24.getBlock();
    org.bukkit.block.Block _CVAR9 = _CVAR2;
    org.bukkit.block.Block _CVAR18 = _CVAR9;
    org.bukkit.block.BlockFace face = null;
    org.bukkit.block.BlockFace _CVAR3 = face;
    org.bukkit.block.BlockFace _CVAR10 = _CVAR3;
    org.bukkit.block.BlockFace _CVAR19 = _CVAR10;
    org.bukkit.block.Block _CVAR25 = _CVAR18;
    org.bukkit.block.BlockFace _CVAR26 = _CVAR19;
    // the block we're trying to move into
    org.bukkit.block.Block b = _CVAR25.getRelative(_CVAR26);
    org.bukkit.event.player.PlayerMoveEvent _CVAR35 = event;
    org.bukkit.Location from = _CVAR35.getFrom();
    if () {
        org.bukkit.block.Block _CVAR20 = b;
        org.bukkit.block.BlockFace _CVAR21 = org.bukkit.block.BlockFace.UP;
        org.bukkit.block.Block _CVAR22 = _CVAR20.getRelative(_CVAR21);
        // trying to move into a non-passable or climbable block
        // see if we're able to jump onto it
        int id1 = _CVAR22.getTypeId();
        org.bukkit.block.Block _CVAR27 = b;
        org.bukkit.block.BlockFace _CVAR28 = org.bukkit.block.BlockFace.UP;
        int _CVAR29 = 2;
        org.bukkit.block.Block _CVAR30 = _CVAR27.getRelative(_CVAR28, _CVAR29);
        int id2 = _CVAR30.getTypeId();
        // ensure there's room above the block we want to jump on
        // if there's a slab on that block, we can still jump, iff we're already on a slab
        if ((me.desht.autohop.AutoHop.passable.contains(id1) || (isSlab(id1) && standingOnSlab(from))) && me.desht.autohop.AutoHop.passable.contains(id2)) {
            boolean _CVAR31 = !me.desht.autohop.AutoHop.passable.contains(from.getBlock().getRelative(org.bukkit.block.BlockFace.DOWN).getTypeId());
            boolean _CVAR32 = ((from.getY() % 1) < 1.0E-4) && _CVAR31;
            org.bukkit.Location _CVAR36 = from;
            org.bukkit.block.Block _CVAR37 = _CVAR36.getBlock();
            int _CVAR38 = _CVAR37.getTypeId();
            boolean _CVAR39 = isStairs(_CVAR38);
            boolean _CVAR33 = _CVAR32 || _CVAR39;
            boolean _CVAR34 = _CVAR33 || standingOnSlab(from);
            // is player standing on solid ground or on (including partway up) some stairs or a slab?
            if () {
                org.bukkit.util.Vector v = event.getPlayer().getVelocity();
                // System.out.println("current velocity = " + v + ", y pos = " + f.getY() + "->" + t.getY());
                v.setY(0.37);
                event.getPlayer().setVelocity(v);
            }
        }
    }
    // System.out.println("event handler: " + (System.nanoTime() - s0));
}