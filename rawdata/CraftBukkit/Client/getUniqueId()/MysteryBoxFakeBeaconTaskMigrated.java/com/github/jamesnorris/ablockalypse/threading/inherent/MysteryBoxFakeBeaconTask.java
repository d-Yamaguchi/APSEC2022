package com.github.jamesnorris.ablockalypse.threading.inherent;
import com.github.jamesnorris.ablockalypse.aspect.block.MysteryBox;
import com.github.jamesnorris.ablockalypse.aspect.intelligent.Game;
import com.github.jamesnorris.ablockalypse.enumerated.Setting;
import com.github.jamesnorris.ablockalypse.event.bukkit.EntityExplode;
import com.github.jamesnorris.ablockalypse.threading.RepeatingTask;
import com.github.jamesnorris.ablockalypse.utility.BukkitUtility;
public class MysteryBoxFakeBeaconTask extends com.github.jamesnorris.ablockalypse.threading.RepeatingTask {
    private java.util.ArrayList<org.bukkit.Location> fireLocations = new java.util.ArrayList<org.bukkit.Location>();

    private java.util.Random rand = new java.util.Random();

    private com.github.jamesnorris.ablockalypse.aspect.block.MysteryBox active;

    private com.github.jamesnorris.ablockalypse.aspect.intelligent.Game game;

    public MysteryBoxFakeBeaconTask(com.github.jamesnorris.ablockalypse.aspect.intelligent.Game game, int interval, boolean autorun) {
        super(interval, autorun);
        this.game = game;
    }

    @java.lang.Override
    public void run() {
        if (((java.lang.Boolean) (Setting.BEACONS.getSetting()))) {
            if ((game == null) || (game.getFakeBeaconThread() != this)) {
                cancel();
                return;
            }
            if (game.getActiveMysteryChest() == null) {
                java.util.List<com.github.jamesnorris.ablockalypse.aspect.block.MysteryBox> chests = game.getObjectsOfType(com.github.jamesnorris.ablockalypse.aspect.block.MysteryBox.class);
                if (chests.size() > 0) {
                    game.setActiveMysteryChest(chests.get(rand.nextInt(chests.size())));
                }
            }
            if (game.hasStarted() && (game.getActiveMysteryChest() != null)) {
                if ((active == null) || (!com.github.jamesnorris.ablockalypse.utility.BukkitUtility.locationMatch(game.getActiveMysteryChest().getLocation(), active.getLocation()))) {
                    active = game.getActiveMysteryChest();
                    fireLocations = getFiringLocations(active.getLocation());
                }
                for (org.bukkit.Location l : fireLocations) {
                    org.bukkit.FireworkEffect.Builder effect = org.bukkit.FireworkEffect.builder().trail(true).flicker(false).withColor(org.bukkit.Color.BLUE).with(org.bukkit.FireworkEffect.Type.BURST);
                    org.bukkit.entity.Firework work = l.getWorld().spawn(l, org.bukkit.entity.Firework.class);
                    com.github.jamesnorris.ablockalypse.event.bukkit.EntityExplode.preventExplosion(l.getWorld(), true);
                    org.bukkit.inventory.meta.FireworkMeta meta = work.getFireworkMeta();
                    meta.addEffect(effect.build());
                    meta.setPower(5);
                    work.setFireworkMeta(meta);
                }
            }
        }
    }

    private java.util.ArrayList<org.bukkit.Location> getFiringLocations(org.bukkit.Location loc) {
        java.util.ArrayList<org.bukkit.Location> locations = new java.util.ArrayList<org.bukkit.Location>();
        locations.add(loc.clone().add(0, 1, 0));
        // World world = loc.getWorld();
        // for (int y = loc.getBlockY(); y < world.getHighestBlockAt(loc).getLocation().clone().add(0, 1, 0).getBlockY(); y++) {
        // Location newloc = world.getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getLocation();
        // if (newloc.getBlock().isEmpty() && !newloc.clone().subtract(0, 1, 0).getBlock().isEmpty()) {
        // locations.add(newloc);
        // }
        // }
        return locations;
    }
}