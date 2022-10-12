private org.bukkit.block.Block getTargetedBlock(final org.bukkit.entity.Player p, final org.bukkit.event.Event e) {
    if (p == null) {
        return null;
    }
    final long time = org.bukkit.Bukkit.getWorlds().get(0).getFullTime();
    if ((ch.njol.skript.expressions.ExprTargetedBlock.last != e) || (time != ch.njol.skript.expressions.ExprTargetedBlock.blocksValidForTick)) {
        ch.njol.skript.expressions.ExprTargetedBlock.targetedBlocks.clear();
        ch.njol.skript.expressions.ExprTargetedBlock.blocksValidForTick = time;
        ch.njol.skript.expressions.ExprTargetedBlock.last = e;
    }
    if (((!actualTargetedBlock) && (getTime() <= 0)) && ch.njol.skript.expressions.ExprTargetedBlock.targetedBlocks.containsKey(p)) {
        return ch.njol.skript.expressions.ExprTargetedBlock.targetedBlocks.get(p);
    }
    // if (e instanceof PlayerInteractEvent && p == ((PlayerInteractEvent) e).getPlayer() && (((PlayerInteractEvent) e).getAction() == Action.LEFT_CLICK_BLOCK || ((PlayerInteractEvent) e).getAction() == Action.RIGHT_CLICK_BLOCK)) {
    // targetedBlocks.put(((PlayerInteractEvent) e).getPlayer(), ((PlayerInteractEvent) e).getClickedBlock());
    // return ((PlayerInteractEvent) e).getClickedBlock();
    // }
    org.bukkit.block.Block b = p.getTargetBlock(null, SkriptConfig.maxTargetBlockDistance.value());
    int _CVAR0 = 0;
    boolean _CVAR1 = b.getTypeId() == _CVAR0;
    if () {
        b = null;
    }
    ch.njol.skript.expressions.ExprTargetedBlock.targetedBlocks.put(p, b);
    return b;
}