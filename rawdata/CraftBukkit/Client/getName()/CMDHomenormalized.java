@java.lang.Override
public void onExecute(java.lang.String[] args, org.bukkit.command.CommandSender sender) {
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    if (args.length < 1) {
        org.bukkit.command.CommandSender _CVAR0 = sender;
        java.lang.String _CVAR1 = _CVAR0.getName();
        com.dre.managerxl.MPlayer mPlayer = com.dre.managerxl.MPlayer.getOrCreate(_CVAR1);
        if (mPlayer.getHome() != null) {
            player.teleport(mPlayer.getHome());
        } else {
            P.p.msg(player, P.p.getLanguageReader().get("Error_CmdHome_NoHome"));
        }
    } else if (P.p.getPermissionHandler().has(sender, "mxl.cmd.player.homeother")) {
        com.dre.managerxl.MPlayer oPlayer = com.dre.managerxl.MPlayer.get(args[0]);
        if (oPlayer != null) {
            if (oPlayer.getHome() != null) {
                player.teleport(oPlayer.getHome());
            } else {
                P.p.msg(player, P.p.getLanguageReader().get("Error_CmdHome_NoHome2", args[0]));
            }
        } else {
            P.p.msg(player, P.p.getLanguageReader().get("Error_PlayerNotExist", args[0]));
        }
    }
}