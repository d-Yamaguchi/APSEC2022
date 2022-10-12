@java.lang.Override
public void run() {
    org.bukkit.entity.Player _CVAR0 = player;
    final java.lang.String pName = _CVAR0.getName();
    final me.sablednah.legendquest.playercharacters.PC pc = lq.players.getPC(pName);
    pc.setXP(xp);
    lq.players.savePlayer(pc);
}