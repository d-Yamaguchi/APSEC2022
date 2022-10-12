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
            org.bukkit.entity.Wolf _CVAR0 = wolf;
            int _CVAR1 = net.elusive92.bukkit.wolvesonmeds.WolvesOnMeds.maxHealth;
            _CVAR0.setHealth(_CVAR1);
            recoveringWolves.remove(wolf);
            debug(("Wolf " + wolf.getUniqueId()) + " was healed instantly.");
        }
    } else {
        recoveringWolves.remove(wolf);
    }
}