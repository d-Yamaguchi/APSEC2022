public org.bukkit.block.PistonMoveReaction getPistonMoveReaction() {
     _CVAR0 = org.bukkit.block.Block.byId[this.getTypeId()].material;
     _CVAR1 = _CVAR0.getPushReaction();
    org.bukkit.block.PistonMoveReaction _CVAR2 = org.bukkit.block.PistonMoveReaction.getById(_CVAR1);
    return _CVAR2;
}