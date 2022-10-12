@java.lang.Override
public boolean execute(org.bukkit.plugin.Plugin plugin, org.bukkit.command.CommandSender player, java.lang.String[] args) throws me.desht.chesscraft.exceptions.ChessException {
    notFromConsole(player);
    if (args.length >= 1) {
         _CVAR0 = me.desht.chesscraft.chess.ChessGameManager.getManager();
        java.lang.String _CVAR1 = args[0];
        org.bukkit.command.CommandSender _CVAR3 = player;
         _CVAR2 = _CVAR0.getGame(_CVAR1);
        java.lang.String _CVAR4 = _CVAR3.getName();
        _CVAR2.start(_CVAR4);
    } else {
        org.bukkit.command.CommandSender _CVAR6 = player;
         _CVAR5 = me.desht.chesscraft.chess.ChessGameManager.getManager();
        java.lang.String _CVAR7 = _CVAR6.getName();
        boolean _CVAR8 = true;
         _CVAR9 = _CVAR5.getCurrentGame(_CVAR7, _CVAR8);
        org.bukkit.command.CommandSender _CVAR10 = player;
        org.bukkit.command.CommandSender _CVAR12 = _CVAR10;
        java.lang.String _CVAR11 = _CVAR12.getName();
        _CVAR9.start(_CVAR11);
    }
    return true;
}