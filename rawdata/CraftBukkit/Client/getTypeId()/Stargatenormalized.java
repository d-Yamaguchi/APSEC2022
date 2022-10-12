/**
 * This method sets the iris state and toggles the iris lever.
 * Smart enough to know if the gate is active and set the proper
 * material in its interior.
 *
 * @param irisactive
 * 		true for iris on, false for off.
 */
private void setIrisState(final boolean irisactive) {
    setGateIrisActive(irisactive);
    fillGateInterior(isGateIrisActive() ? isGateCustom() ? getGateCustomIrisMaterial().getId() : getGateShape() != null ? getGateShape().getShapeIrisMaterial().getId() : 1 : isGateActive() ? isGateCustom() ? getGateCustomPortalMaterial().getId() : getGateShape() != null ? getGateShape().getShapePortalMaterial().getId() : 9 : 0);
    int _CVAR0 = 69;
    boolean _CVAR1 = getGateIrisLeverBlock().getTypeId() == _CVAR0;
    boolean _CVAR2 = (getGateIrisLeverBlock() != null) && _CVAR1;
    if () {
        getGateIrisLeverBlock().setData(de.luricos.bukkit.WormholeXTreme.Wormhole.utils.WorldUtils.getLeverToggleByte(getGateIrisLeverBlock().getData(), isGateIrisActive()));
    }
}