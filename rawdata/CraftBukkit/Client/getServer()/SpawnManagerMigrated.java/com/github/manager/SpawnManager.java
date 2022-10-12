package com.github.manager;
import com.github.aspect.Barrier;
import com.github.aspect.Game;
import com.github.aspect.MobSpawner;
import com.github.enumerated.GameEntityType;
import com.github.enumerated.Setting;
import com.github.event.GameMobSpawnEvent;
import com.github.threading.inherent.MobSpawningThread;
import com.github.utility.MiscUtil;
import com.github.utility.Pathfinder;
public class SpawnManager {
    /**
     * Checks if all of the mobs of a game are spawned in to the game for this round.
     * This does not check if they are all alive, just if they have spawned.
     *
     * @param game
     * 		The game to check the mobs for
     * @return Whether or not all mobs have been spawned for this round
     */
    public static boolean allSpawnedIn(com.github.aspect.Game game) {
        return game.getMobCountSpawnedInThisRound() >= com.github.manager.SpawnManager.getCurrentSpawnAmount(game);
    }

    /**
     * Gets the barrier closest to the location.
     *
     * @param loc
     * 		The location to check for
     * @return The closest barrier
     */
    public static com.github.aspect.Barrier getClosestBarrier(com.github.aspect.Game game, org.bukkit.Location loc) {
        double distanceSquared = java.lang.Double.MAX_VALUE;
        com.github.aspect.Barrier lp = null;// low priority

        com.github.aspect.Barrier hp = null;// high priority

        for (com.github.aspect.Barrier gb : game.getObjectsOfType(com.github.aspect.Barrier.class)) {
            org.bukkit.Location l = gb.getCenter();
            double current = loc.distanceSquared(l);
            if (current < distanceSquared) {
                distanceSquared = current;
                lp = gb;
                if (com.github.manager.SpawnManager.pathIsClear(loc, l)) {
                    hp = gb;
                }
            }
        }
        return hp != null ? hp : lp;
    }

    /**
     * Gets the barrier closest to the player.
     *
     * @param p
     * 		The player to check for
     * @return The closest barrier
     */
    public static com.github.aspect.Barrier getClosestBarrier(com.github.aspect.Game game, org.bukkit.entity.Player p) {
        return com.github.manager.SpawnManager.getClosestBarrier(game, p.getLocation());
    }

    /**
     * Gets the closest spawner to the location.
     *
     * @param loc
     * 		The location to check for
     * @return The closest spawner
     */
    public static com.github.aspect.MobSpawner getClosestSpawner(com.github.aspect.Game game, org.bukkit.Location loc) {
        double distanceSquared = java.lang.Double.MAX_VALUE;
        com.github.aspect.MobSpawner lp = null;// low priority

        com.github.aspect.MobSpawner hp = null;// high priority

        for (com.github.aspect.MobSpawner l1 : game.getObjectsOfType(com.github.aspect.MobSpawner.class)) {
            org.bukkit.Location l = l1.getBukkitLocation();
            double current = loc.distanceSquared(l);
            if (current < distanceSquared) {
                distanceSquared = current;
                lp = l1;
                if (com.github.manager.SpawnManager.pathIsClear(loc, l)) {
                    hp = l1;
                }
            }
        }
        return hp != null ? hp : lp;
    }

    /**
     * Gets the closest spawner to the player.
     *
     * @param p
     * 		The player to check for
     * @return The closest spawner
     */
    public static com.github.aspect.MobSpawner getClosestSpawner(com.github.aspect.Game game, org.bukkit.entity.Player p) {
        return com.github.manager.SpawnManager.getClosestSpawner(game, p.getLocation());
    }

    /**
     * Gets the amount of spawns in the current level of the game.
     *
     * @return The amount of spawns in the current level
     */
    public static int getCurrentSpawnAmount(com.github.aspect.Game game) {
        return com.github.manager.SpawnManager.getSpawnAmount(game.getLevel(), game.getPlayers().size(), game.isWolfRound());
    }

    /**
     * Gets the amount of spawns in the given level, with the given amount of players.
     *
     * @param level
     * 		The level you are looking for
     * @param playeramt
     * 		The amount of players you are looking for
     * @param wolfround
     * 		Whether or not the game is a wolf round or not
     * @return The amount of spawns in this hypathetical level
     */
    public static int getSpawnAmount(int level, int playeramt, boolean wolfround) {
        /* The mob spawn amount equation for the entire game */
        return ((int) ((wolfround ? 1 / 3 : 1) * (java.lang.Math.sqrt(10 * level) + (java.lang.Math.sqrt(level) * playeramt))));
    }

    /**
     * Checks that the blocks from the one location to the next in the distance are empty.
     *
     * @param start
     * 		The starting location
     * @param end
     * 		The ending location
     * @param distance
     * 		The distance from the start to end
     * @return Whether or not all blocks from start to end are empty
     */
    public static boolean pathIsClear(org.bukkit.Location start, org.bukkit.Location end) {
        return com.github.utility.Pathfinder.pathReaches(com.github.utility.Pathfinder.calculate(start, end), end, 2);
    }

    /**
     * Gamespawns a mob at the specified location.
     *
     * @param loc
     * 		The location to spawn the mob at
     * @param exactLocation
     * 		Whether or not to spawn right next to the target, or to find a closeby location.
     */
    public static void spawn(com.github.aspect.Game game, org.bukkit.Location loc, boolean exactLocation) {
        com.github.manager.SpawnManager.spawn(game, exactLocation ? loc : com.github.utility.MiscUtil.getNearbyLocation(loc, 4, 7, 0, 0, 4, 7), game.isWolfRound() ? org.bukkit.entity.EntityType.WOLF : org.bukkit.entity.EntityType.ZOMBIE);
    }

    /**
     * Spawns a wave of mobs around random living players in this game.
     * If barriers are present and accessible, spawns the mobs at the barriers.
     * If mob spawners are set in the game and are accessible, spawns the mobs at a mob spawner.
     * This will only spawn mobs if the game has a mob count of 0.
     */
    public static void spawnWave(com.github.aspect.Game game) {
        com.github.manager.SpawnManager.spawnWave(game, com.github.manager.SpawnManager.getCurrentSpawnAmount(game));
    }

    public static void spawnWave(com.github.aspect.Game game, int amt) {
        if ((game.getRemainingPlayers() < 1) && (game.getMobCount() <= 0)) {
            game.end(true);
            return;
        }
        if (((java.lang.Boolean) (Setting.DEBUG.getSetting()))) {
            java.lang.System.out.println((("[Ablockalypse] [DEBUG] Amount of zombies in this wave: (" + game.getName()) + ") ") + amt);
        }
        for (int i = 1; i <= amt; i++) {
            new com.github.threading.inherent.MobSpawningThread(game, i * 80);
        }
    }

    protected static void spawn(com.github.aspect.Game game, org.bukkit.Location l, org.bukkit.entity.EntityType et) {
        org.bukkit.entity.Entity e = l.getWorld().spawnEntity(l, et);
        com.github.enumerated.GameEntityType type = com.github.enumerated.GameEntityType.translate(et);
        com.github.event.GameMobSpawnEvent gmse = new com.github.event.GameMobSpawnEvent(e, game, type);
        ChannelsPlugin.get().getPluginManager().callEvent(gmse);
        game.setMobCountSpawnedInThisRound(game.getMobCountSpawnedInThisRound() + 1);
        if (!gmse.isCancelled()) {
            type.instantiate(e, game);
        } else {
            e.remove();
        }
    }
}