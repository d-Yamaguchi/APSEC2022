/**
 * Sends a Raw Message to the player passed.
 *
 * @param player
 * 		to send
 */
public void sendRawMessage(org.bukkit.entity.Player player, java.lang.String rawMessage) {
    try {
        java.lang.String seperator = ".";
        java.lang.Class<? extends java.lang.Object> chatSerializer = java.lang.Class.forName(((((((("net" + seperator) + "minecraft") + seperator) + "server") + seperator) + CB_RELOCATION) + seperator) + "ChatSerializer");
        java.lang.reflect.Method serialize = chatSerializer.getMethod("a", java.lang.String.class);
        java.lang.Object serializedMessage = serialize.invoke(null, rawMessage);
        org.bukkit.entity.Player _CVAR0 = player;
        java.lang.Object mcPlayer = getMCEntityFromBukkitEntity(_CVAR0);
        java.lang.Object _CVAR1 = mcPlayer;
        java.lang.String _CVAR4 = "IChatBaseComponent";
        java.lang.String _CVAR5 = ((((((("net" + seperator) + "minecraft") + seperator) + "server") + seperator) + CB_RELOCATION) + seperator) + _CVAR4;
        java.lang.Class<? extends java.lang.Object> iChatBaseComponent = java.lang.Class.forName(_CVAR5);
        java.lang.Class<? extends java.lang.Object> _CVAR2 = _CVAR1.getClass();
        java.lang.String _CVAR3 = "sendMessage";
        java.lang.Class<? extends java.lang.Object> _CVAR6 = iChatBaseComponent;
        java.lang.reflect.Method sendMessage = _CVAR2.getMethod(_CVAR3, _CVAR6);
        sendMessage.invoke(mcPlayer, serializedMessage);
    } catch (java.lang.Throwable exp) {
        player.sendMessage(rawMessage);
    }
}