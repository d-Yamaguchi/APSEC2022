/**
 * Returns whether the {@link org.bukkit.entity.Player} that interacted was the Pet's owner
 *
 * @return true if it is the owner
 */
public boolean isPlayerOwner() {
    org.bukkit.entity.Player _CVAR0 = this.player;
    com.dsh105.echopet.compat.api.entity.IPet _CVAR2 = this.pet;
    java.lang.String _CVAR1 = _CVAR0.getName();
     _CVAR3 = _CVAR2.getNameOfOwner();
    boolean _CVAR4 = _CVAR1.equals(_CVAR3);
    return _CVAR4;
}