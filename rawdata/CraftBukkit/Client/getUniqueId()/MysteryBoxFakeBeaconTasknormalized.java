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
                org.bukkit.Location _CVAR0 = l;
                org.bukkit.World _CVAR1 = _CVAR0.getWorld();
                org.bukkit.Location _CVAR2 = l;
                java.lang.Class _CVAR3 = org.bukkit.entity.Firework.class;
                org.bukkit.entity.Firework work = _CVAR1.spawn(_CVAR2, _CVAR3);
                org.bukkit.entity.Firework _CVAR4 = work;
                java.util.UUID _CVAR5 = _CVAR4.getUniqueId();
                boolean _CVAR6 = true;
                com.github.jamesnorris.ablockalypse.event.bukkit.EntityExplode.preventExplosion(_CVAR5, _CVAR6);
                org.bukkit.inventory.meta.FireworkMeta meta = work.getFireworkMeta();
                meta.addEffect(effect.build());
                meta.setPower(5);
                work.setFireworkMeta(meta);
            }
        }
    }
}