package me.desht.autohop;
public class AutoHop extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    private static java.util.Set<java.lang.Integer> passable = new java.util.HashSet<java.lang.Integer>();

    static {
        passable.add(org.bukkit.Material.AIR.getId());
        passable.add(org.bukkit.Material.WATER.getId());
        passable.add(org.bukkit.Material.STATIONARY_WATER.getId());
        passable.add(org.bukkit.Material.SAPLING.getId());
        passable.add(org.bukkit.Material.POWERED_RAIL.getId());
        passable.add(org.bukkit.Material.DETECTOR_RAIL.getId());
        passable.add(org.bukkit.Material.WEB.getId());
        passable.add(org.bukkit.Material.LONG_GRASS.getId());
        passable.add(org.bukkit.Material.DEAD_BUSH.getId());
        passable.add(org.bukkit.Material.YELLOW_FLOWER.getId());
        passable.add(org.bukkit.Material.RED_ROSE.getId());
        passable.add(org.bukkit.Material.BROWN_MUSHROOM.getId());
        passable.add(org.bukkit.Material.RED_MUSHROOM.getId());
        passable.add(org.bukkit.Material.TORCH.getId());
        passable.add(org.bukkit.Material.FIRE.getId());
        passable.add(org.bukkit.Material.REDSTONE_WIRE.getId());
        passable.add(org.bukkit.Material.CROPS.getId());
        passable.add(org.bukkit.Material.SIGN_POST.getId());
        passable.add(org.bukkit.Material.LADDER.getId());
        passable.add(org.bukkit.Material.RAILS.getId());
        passable.add(org.bukkit.Material.WALL_SIGN.getId());
        passable.add(org.bukkit.Material.LEVER.getId());
        passable.add(org.bukkit.Material.STONE_PLATE.getId());
        passable.add(org.bukkit.Material.WOOD_PLATE.getId());
        passable.add(org.bukkit.Material.REDSTONE_TORCH_OFF.getId());
        passable.add(org.bukkit.Material.REDSTONE_TORCH_ON.getId());
        passable.add(org.bukkit.Material.STONE_BUTTON.getId());
        passable.add(org.bukkit.Material.SNOW.getId());
        passable.add(org.bukkit.Material.SUGAR_CANE.getId());
        passable.add(org.bukkit.Material.PORTAL.getId());
        passable.add(org.bukkit.Material.DIODE_BLOCK_OFF.getId());
        passable.add(org.bukkit.Material.DIODE_BLOCK_ON.getId());
        passable.add(org.bukkit.Material.PUMPKIN_STEM.getId());
        passable.add(org.bukkit.Material.MELON_STEM.getId());
        passable.add(org.bukkit.Material.VINE.getId());
        passable.add(org.bukkit.Material.WATER_LILY.getId());
        passable.add(org.bukkit.Material.NETHER_WARTS.getId());
        passable.add(org.bukkit.Material.ENDER_PORTAL.getId());
        passable.add(org.bukkit.Material.TRIPWIRE.getId());
        passable.add(org.bukkit.Material.TRIPWIRE_HOOK.getId());
        // yeah, fences aren't passable, but this prevents players attempting to jump them at all
        passable.add(org.bukkit.Material.FENCE.getId());
    }

    @java.lang.Override
    public void onDisable() {
    }

    @java.lang.Override
    public void onEnable() {
        org.bukkit.plugin.PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this, this);
        try {
            me.desht.autohop.MetricsLite metrics = new me.desht.autohop.MetricsLite(this);
            metrics.start();
        } catch (java.io.IOException e) {
            this.getLogger().log(java.util.logging.Level.WARNING, "Couldn't submit metrics stats: " + e.getMessage());
        }
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        // long s0 = System.nanoTime();
        if (!event.getPlayer().hasPermission("autohop.hop"))
            return;

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
        if (yaw < 0)
            yaw += 360;

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
        if ($missing$) {
            org.bukkit.material.Stairs s = ((org.bukkit.material.Stairs) (b.getState().getData()));
            climbable = s.getAscendingDirection() == face;
            // System.out.println("see some stairs: climbable = " + climbable);
        } else if ($missing$) {
            climbable = true;
        }
        org.bukkit.Location to = event.getTo();
        org.bukkit.block.BlockFace face = null;
        // the block we're trying to move into
        org.bukkit.block.Block b = to.getBlock().getRelative(face);
        org.bukkit.Location from = event.getFrom();
        if ($missing$) {
            int id1 = b.getType();
            int id2 = b.getType();
            // ensure there's room above the block we want to jump on
            // if there's a slab on that block, we can still jump, iff we're already on a slab
            if ((me.desht.autohop.AutoHop.passable.contains(id1) || (isSlab(id1) && standingOnSlab(from))) && me.desht.autohop.AutoHop.passable.contains(id2)) {
                // is player standing on solid ground or on (including partway up) some stairs or a slab?
                if ($missing$) {
                    org.bukkit.util.Vector v = event.getPlayer().getVelocity();
                    // System.out.println("current velocity = " + v + ", y pos = " + f.getY() + "->" + t.getY());
                    v.setY(0.37);
                    event.getPlayer().setVelocity(v);
                }
            }
        }
        // System.out.println("event handler: " + (System.nanoTime() - s0));
    }

    private boolean isStairs(int id) {
        return (id == org.bukkit.Material.COBBLESTONE_STAIRS.getId()) || (id == org.bukkit.Material.WOOD_STAIRS.getId());
    }

    private boolean isSlab(int id) {
        return (id == org.bukkit.Material.STEP.getId()) || (id == org.bukkit.Material.WOOD_STEP.getId());
    }

    private boolean standingOnSlab(org.bukkit.Location l) {
        return isSlab(l.getBlock().getType()) && ((l.getY() % 1) <= 0.51);
    }
}