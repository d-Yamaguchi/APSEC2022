package net.elusive92.bukkit.wolvesonmeds;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
public class WolvesOnMeds extends org.bukkit.plugin.java.JavaPlugin {
    /**
     * Stores the configuration handler.
     */
    private net.elusive92.bukkit.wolvesonmeds.WOMConfiguration config = new net.elusive92.bukkit.wolvesonmeds.WOMConfiguration(this);

    /**
     * Stores the entity listener.
     */
    private net.elusive92.bukkit.wolvesonmeds.WOMEntityListener entityListener = new net.elusive92.bukkit.wolvesonmeds.WOMEntityListener(this);

    /**
     * Caches all tamed wolves on the server to allow for very fast access when
     * being used by the scheduler. The set needs to be synchronized to ensure
     * that nothing is modified while the scheduler is using it.
     */
    private java.util.Set<org.bukkit.entity.Wolf> recoveringWolves = java.util.Collections.synchronizedSet(new java.util.HashSet<org.bukkit.entity.Wolf>());

    /**
     * Stores the time (in ticks) until a wolf starts to recover. Attacking a
     * wolf will reset the delay to the configured value to avoid instant
     * healing after combat.
     */
    private java.util.Map<org.bukkit.entity.Wolf, java.lang.Integer> recoveryDelays = java.util.Collections.synchronizedMap(new java.util.HashMap<org.bukkit.entity.Wolf, java.lang.Integer>());

    /**
     * Stores the maximum health a wolf can be healed to.
     */
    private static int maxHealth;

    /**
     * Stores the minimum health a wolf needs for automatic recovery.
     */
    private static int minHealth;

    /**
     * Stores the number of ticks needed to heal a wolf from 0-100% health.
     */
    private long recoveryDurationTicks;

    /**
     * Stores wether to dispatch wolf health instantly.
     */
    private boolean recoverInstantly = false;

    /**
     * Stores the interval (in ticks) at which the wolves get healed.
     */
    private long healIntervalTicks;

    /**
     * Initializes the plugin.
     */
    public void onEnable() {
        // Load the configuration every time the plugin is enabled. This needs
        // to be done first to prevent missing properties from eating our souls.
        config.load();
        // Determine the minimum and maximum health. A health value needs to be
        // in the range from 1 to 20.
        net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth = java.lang.Math.min(java.lang.Math.max(config.getInt("heal.max-health"), 1), 20);
        net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.minHealth = java.lang.Math.min(java.lang.Math.max(config.getInt("heal.min-health"), 1), 20);
        // Convert seconds to ticks.
        recoveryDurationTicks = config.getInt("heal.duration") * 20;
        // Very low values will be almost equal to instant healing, so we use
        // that to save CPU cycles.
        recoverInstantly = recoveryDurationTicks <= 20;// 1 second

        // Register event listeners.
        registerEvent(Type.CREATURE_SPAWN, entityListener);
        registerEvent(Type.ENTITY_DAMAGE, entityListener);
        registerEvent(Type.ENTITY_DEATH, entityListener);
        registerEvent(Type.ENTITY_TAME, entityListener);
        // Find all wounded tamed wolves that are currently on the server.
        for (org.bukkit.World world : getServer().getWorlds()) {
            for (org.bukkit.entity.Entity entity : world.getEntities()) {
                if (entity instanceof org.bukkit.entity.Wolf) {
                    dispatch(((org.bukkit.entity.Wolf) (entity)));
                }
            }
        }
        // We need to do some additional setup for timed recovery.
        if (!recoverInstantly) {
            healIntervalTicks = recoveryDurationTicks / net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth;
            // Try to schedule our healing task.
            int taskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, new net.elusive92.bukkit.wolvesonmeds.WOMHealTask(this), 0L, healIntervalTicks);
            // Use instant recovery if the task could not be scheduled. It is
            // better than nothing.
            if (taskId == (-1)) {
                recoverInstantly = true;
                log("Failed to schedule wolf healing task. Falling back to instant recovery.");
            }
        }
        java.lang.System.out.println(this + " is now enabled!");
    }

    /**
     * Shuts down the plugin.
     */
    public void onDisable() {
        java.lang.System.out.println(this + " is now disabled!");
    }

    /**
     * Decides what to do with the wolf. If he can recover, he is either healed
     * instantly or queued for timed healing.
     *
     * @param wolf
     * 		
     * @param health
     * 		to check against to determine if the wolf is wounded
     */
    /* package */
    void dispatch(org.bukkit.entity.Wolf wolf, int health) {
        if (wolf.isTamed() && (health < net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth)) {
            if (!recoverInstantly) {
                recoveringWolves.add(wolf);
                debug(("Wolf " + wolf.getUniqueId()) + " was scheduled for timed recovery.");
            } else {
                HeroesUtil.setHealthP(player, health);
                recoveringWolves.remove(wolf);
                debug(("Wolf " + wolf.getUniqueId()) + " was healed instantly.");
            }
        } else {
            recoveringWolves.remove(wolf);
        }
    }

    /**
     * Shortcut that automatically inserts the living entity health to check
     * against.
     *
     * @param wolf
     * 		
     */
    /* package */
    void dispatch(org.bukkit.entity.Wolf wolf) {
        dispatch(wolf, wolf.getHealth());
    }

    /**
     * Actually heals the wounded wolves. This method is thread safe.
     */
    /* package */
    void heal() {
        // Do not do anything if there are no wolves to be healed.
        if (recoveringWolves.isEmpty()) {
            return;
        }
        // We need to use an iterator to allow for removing elements while
        // iterating.
        java.util.Iterator<org.bukkit.entity.Wolf> itr = recoveringWolves.iterator();
        while (itr.hasNext()) {
            org.bukkit.entity.Wolf wolf = itr.next();
            // Calculate the target health for the wolf.
            int newHealth = wolf.getHealth() + 1;
            // Did we reach the maximum health?
            if (newHealth < net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth) {
                wolf.setHealth(newHealth);
            } else {
                wolf.setHealth(net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth);
                // We need to make sure that the wolf is removed from the list
                // of wounded tamed wolves after it has been healed.
                itr.remove();
            }
            debug(((((("Wolf " + wolf.getUniqueId()) + " was healed to ") + wolf.getHealth()) + "/") + net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth) + ".");
        } 
    }

    /**
     * Shortcut to regitering event listeners.
     *
     * @param type
     * 		
     * @param listener
     * 		
     */
    private void registerEvent(org.bukkit.event.Event.Type type, org.bukkit.event.Listener listener) {
        getServer().getPluginManager().registerEvent(type, listener, Priority.High, this);
    }

    /**
     * Reports an arbitrary string to the server console and automatically
     * prepend the plugin name to ease message source identification.
     *
     * @param message
     * 		
     */
    /* package */
    void log(java.lang.String message) {
        java.lang.System.out.println((getDescription().getName() + ": ") + message);
    }

    /**
     * Sends a debug message to the server console.
     *
     * @param message
     * 		
     */
    /* package */
    void debug(java.lang.String message) {
        if (config.getBoolean("debug")) {
            log("DEBUG: " + message);
        }
    }

    /**
     * Provides additional information to the full plugin name.
     *
     * @return enhanced description
     */
    @java.lang.Override
    public java.lang.String toString() {
        return super.toString() + " by Elusive92";
    }
}