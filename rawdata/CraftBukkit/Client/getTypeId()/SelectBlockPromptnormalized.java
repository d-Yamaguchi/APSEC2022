@java.lang.Override
protected boolean isInputValid(org.bukkit.conversations.ConversationContext context, java.lang.String input) {
    if (input.startsWith("/")) {
        org.bukkit.Bukkit.dispatchCommand(((org.bukkit.command.CommandSender) (context.getForWhom())), input.substring(1));
        return false;
    } else if (input.equalsIgnoreCase("CANCEL")) {
        return true;
    } else if (input.equalsIgnoreCase("OK")) {
        org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (context.getForWhom()));
        org.bukkit.block.Block block = player.getTargetBlock(invisible, 8);
        int _CVAR0 = 0;
        boolean _CVAR1 = block.getTypeId() == _CVAR0;
        boolean _CVAR2 = (block == null) || _CVAR1;
        if () {
            player.sendRawMessage(org.bukkit.ChatColor.RED + "You're not looking at a block.");
            return false;
        }
        if ((getWorldName() != null) && (!block.getWorld().getName().equals(getWorldName()))) {
            context.getForWhom().sendRawMessage(((((((org.bukkit.ChatColor.RED + "You're in a different world. Go to ") + org.bukkit.ChatColor.WHITE) + "\"") + getWorldName()) + "\"") + org.bukkit.ChatColor.RED) + ".");
            return false;
        }
        if ((min != null) && (max != null)) {
            if ((((((block.getX() < min.getBlockX()) || (block.getX() > max.getBlockX())) || (block.getY() < min.getBlockY())) || (block.getY() > max.getBlockY())) || (block.getZ() < min.getBlockZ())) || (block.getZ() > max.getBlockZ())) {
                context.getForWhom().sendRawMessage(((((((((((((((((org.bukkit.ChatColor.RED + "Location is outside of the region ") + org.bukkit.ChatColor.WHITE) + "( ") + min.getBlockX()) + ",") + min.getBlockY()) + ",") + min.getBlockZ()) + " ") + max.getBlockX()) + ",") + max.getBlockY()) + ",") + max.getBlockZ()) + " )") + org.bukkit.ChatColor.GOLD) + ".");
                return false;
            }
        }
        if (type != null) {
            if (block.getType() != type) {
                context.getForWhom().sendRawMessage(((((((org.bukkit.ChatColor.RED + "You're not looking at ") + org.bukkit.ChatColor.WHITE) + "\"") + type.name().toLowerCase().replace("_", " ")) + "\"") + org.bukkit.ChatColor.RED) + ".");
                return false;
            }
        }
        context.setSessionData("block", block);
        return true;
    } else {
        return false;
    }
}