@org.bukkit.event.EventHandler
public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.entity.Player player = _CVAR0.getPlayer();
    org.bukkit.entity.Player _CVAR1 = player;
    java.lang.String playerName = _CVAR1.getName();
    me.desht.dhutils.responsehandler.ResponseHandler resp = me.desht.chesscraft.ChessCraft.getInstance().responseHandler;
    // a left or right-click (even air, where the event is cancelled) cancels any pending player invite response
    if (resp.isExpecting(playerName, me.desht.chesscraft.expector.ExpectInvitePlayer.class)) {
        resp.cancelAction(playerName, me.desht.chesscraft.expector.ExpectInvitePlayer.class);
        MiscUtil.alertMessage(player, me.desht.chesscraft.Messages.getString("ChessPlayerListener.playerInviteCancelled"));
        event.setCancelled(true);
        return;
    }
    if (event.isCancelled()) {
        return;
    }
    try {
        org.bukkit.block.Block b = event.getClickedBlock();
        if (b == null) {
            return;
        }
        if (resp.isExpecting(playerName, me.desht.chesscraft.expector.ExpectBoardCreation.class)) {
            me.desht.chesscraft.expector.ExpectBoardCreation a = resp.getAction(playerName, me.desht.chesscraft.expector.ExpectBoardCreation.class);
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK :
                    a.setLocation(b.getLocation());
                    a.handleAction();
                    break;
                case RIGHT_CLICK_BLOCK :
                    MiscUtil.alertMessage(player, me.desht.chesscraft.Messages.getString("ChessPlayerListener.boardCreationCancelled"));// $NON-NLS-1$

                    a.cancelAction();
                    break;
                default :
                    break;
            }
            event.setCancelled(true);
        } else {
            me.desht.chesscraft.chess.BoardView bv = me.desht.chesscraft.chess.BoardViewManager.getManager().partOfChessBoard(b.getLocation(), 0);
            if ((bv != null) && bv.getControlPanel().isSignButton(b.getLocation())) {
                bv.getControlPanel().signClicked(event);
                event.setCancelled(true);
            }
        }
    } catch (me.desht.chesscraft.exceptions.ChessException e) {
        MiscUtil.errorMessage(player, e.getMessage());
        if (resp.isExpecting(playerName, me.desht.chesscraft.expector.ExpectBoardCreation.class)) {
            resp.cancelAction(playerName, me.desht.chesscraft.expector.ExpectBoardCreation.class);
            MiscUtil.errorMessage(player, me.desht.chesscraft.Messages.getString("ChessPlayerListener.boardCreationCancelled"));// $NON-NLS-1$

        }
    } catch (DHUtilsException e) {
        MiscUtil.errorMessage(player, e.getMessage());
    }
}