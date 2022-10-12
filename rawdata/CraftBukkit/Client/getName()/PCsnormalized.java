public PCs(final me.sablednah.legendquest.Main p) {
    this.lq = p;
    for (final org.bukkit.entity.Player player : lq.getServer().getOnlinePlayers()) {
        org.bukkit.entity.Player _CVAR0 = player;
        final java.lang.String pName = _CVAR0.getName();
        final me.sablednah.legendquest.playercharacters.PC pc = getPC(player);
        addPlayer(pName, pc);
    }
}