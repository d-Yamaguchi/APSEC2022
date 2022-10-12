@com.mtihc.minecraft.treasurechest.v8.util.commands.Command(aliases = { "random", "setrandom", "r" }, args = "[amount]", desc = "Make a treasure randomized.", help = { "The argument is the amount of item-stacks that will be included in the treasure at random." })
public void random(org.bukkit.command.CommandSender sender, java.lang.String[] args) throws com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException {
    if (!(sender instanceof org.bukkit.entity.Player)) {
        sender.sendMessage("Command must be executed by a player, in game.");
        return;
    }
    if (!sender.hasPermission(Permission.RANDOM.getNode())) {
        throw new com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException("You don't have permission to make a treasure randomized.");
    }
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    org.bukkit.block.Block block = com.mtihc.minecraft.treasurechest.v8.core.TreasureManager.getTargetedContainerBlock(player);
    if (block == null) {
        throw new com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException("You're not looking at a container block.");
    }
    org.bukkit.Location loc = com.mtihc.minecraft.treasurechest.v8.core.TreasureManager.getLocation(((org.bukkit.inventory.InventoryHolder) (block.getState())));
    com.mtihc.minecraft.treasurechest.v8.core.ITreasureChest tchest = manager.getTreasure(loc);
    if (tchest == null) {
        throw new com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException("You're not looking at a treasure.");
    }
    int randomness;
    try {
        randomness = java.lang.Integer.parseInt(args[0]);
        if (randomness < 1) {
            com.mtihc.minecraft.treasurechest.v8.plugin.TreasureChestCommand.sendRandomCommandIllegalArgumentMessage(sender);
            return;
        }
    } catch (java.lang.NullPointerException e) {
        randomness = 0;
    } catch (java.lang.IndexOutOfBoundsException e) {
        randomness = 0;
    } catch (java.lang.Exception e) {
        com.mtihc.minecraft.treasurechest.v8.plugin.TreasureChestCommand.sendRandomCommandIllegalArgumentMessage(sender);
        return;
    }
    org.bukkit.inventory.ItemStack[] contents = tchest.getContainer().getContents();
    int total = 0;
    for (org.bukkit.inventory.ItemStack item : contents) {
        int _CVAR0 = 0;
        boolean _CVAR1 = item.getTypeId() == _CVAR0;
        boolean _CVAR2 = (item == null) || _CVAR1;
        if () {
            continue;
        }
        total++;
    }
    if (randomness >= total) {
        sender.sendMessage(org.bukkit.ChatColor.RED + "Unable to make a random treasure.");
        if (total <= 1) {
            throw new com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException(("This treasure contains " + total) + " items.");
        } else {
            throw new com.mtihc.minecraft.treasurechest.v8.util.commands.CommandException(("Expected a number from 1 to " + (total - 1)) + ", including.");
        }
    }
    tchest.setAmountOfRandomlyChosenStacks(randomness);
    if (randomness > 0) {
        sender.sendMessage(org.bukkit.ChatColor.GOLD + "This treasure is random!");
    } else {
        sender.sendMessage(org.bukkit.ChatColor.YELLOW + "This treasure is no longer random.");
    }
    manager.setTreasure(tchest);
    return;
}