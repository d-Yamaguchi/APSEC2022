/**
 * Get a SkillPlayer by his Player object
 *
 * @param player
 * 		the player
 * @return the SkillPlayer
 */
public de.craftlancer.buyskills.SkillPlayer getSkillPlayer(org.bukkit.entity.Player player) {
    org.bukkit.entity.Player _CVAR0 = player;
    java.lang.String _CVAR1 = _CVAR0.getName();
    de.craftlancer.buyskills.SkillPlayer _CVAR2 = getSkillPlayer(_CVAR1);
    return _CVAR2;
}