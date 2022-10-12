@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.entity.Player target, org.bukkit.command.Command command, java.lang.String label, java.lang.String[] args) {
    org.bukkit.entity.Player _CVAR0 = target;
    // Check the player is holding the item
    org.bukkit.inventory.ItemStack held = _CVAR0.getItemInHand();
    if ((held == null) || (held.getTypeId() == 0)) {
        sender.sendMessage(plugin.translate(sender, "error-no-item-in-hand", "You must be holding a menu item"));
        return true;
    }
    // Get or create the lore
    org.bukkit.inventory.meta.ItemMeta meta = held.getItemMeta();
    java.util.List<java.lang.String> loreStrings;
    if (meta.hasLore()) {
        loreStrings = meta.getLore();
    } else {
        loreStrings = new java.util.ArrayList<>();
    }
    if (args.length < 2) {
        return false;
    }
    java.lang.String indexString = args[0];
    int index = getIndex(indexString, loreStrings.size(), sender);
    if (index == (-1)) {
        return true;
    }
    // Expecting one or more parameters that make up the command or comment to add
    java.lang.StringBuilder sb = new java.lang.StringBuilder(args[1]);
    for (int i = 2; i < args.length; i++) {
        sb.append(" ").append(args[i]);
    }
    java.lang.String commandString = sb.toString();
    // Replace the given line in the lore
    if (!commandString.startsWith("/")) {
        // Support for colour codes in non-commands
        commandString = commandString.replace('&', org.bukkit.ChatColor.COLOR_CHAR);
    }
    if (index == 0) {
        // Handle first-line special case
        java.lang.String firstLine = loreStrings.get(0);
        int lastPartIndex = firstLine.lastIndexOf("\r") + 1;
        java.lang.String displacedText = firstLine.substring(lastPartIndex);
        loreStrings.set(0, firstLine.substring(0, lastPartIndex) + commandString);
        loreStrings.add(1, displacedText);
    } else {
        loreStrings.add(index, commandString);
    }
    sender.sendMessage(plugin.translate(sender, "script-inserted", "{0} was inserted before line {1} in the command list of this menu item", commandString, index));
    // Update the item
    meta.setLore(loreStrings);
    held.setItemMeta(meta);
    return true;
}