package uk.co.gorbb.qwicktree.chop;
import uk.co.gorbb.qwicktree.QwickTree;
import uk.co.gorbb.qwicktree.config.Config;
import uk.co.gorbb.qwicktree.tree.TreeInfo;
import uk.co.gorbb.qwicktree.tree.info.TreeType;
import uk.co.gorbb.qwicktree.util.HouseIgnore;
import uk.co.gorbb.qwicktree.util.Logging;
import uk.co.gorbb.qwicktree.util.Message;
import uk.co.gorbb.qwicktree.util.Permission;
import uk.co.gorbb.qwicktree.util.debug.Debugger;
public class ChopAction {
    private org.bukkit.entity.Player player;

    private uk.co.gorbb.qwicktree.tree.TreeInfo tree;

    private java.util.Stack<org.bukkit.block.Block> logsToSearch;

    private java.util.List<org.bukkit.Location> baseBlocks;

    private org.bukkit.Location dropLocation;

    private java.util.List<org.bukkit.block.Block> logs;

    private java.util.List<org.bukkit.block.Block> leaves;

    private java.util.List<org.bukkit.block.Block> vines;

    private java.util.Random rnd;

    private boolean ignoreHouseBlocks;

    private uk.co.gorbb.qwicktree.util.debug.Debugger debugger;

    public ChopAction(org.bukkit.entity.Player player, uk.co.gorbb.qwicktree.tree.TreeInfo tree, org.bukkit.block.Block baseBlock) {
        this.player = player;
        this.tree = tree;
        logsToSearch = new java.util.Stack<org.bukkit.block.Block>();
        logs = new java.util.LinkedList<org.bukkit.block.Block>();
        leaves = new java.util.LinkedList<org.bukkit.block.Block>();
        baseBlocks = new java.util.LinkedList<org.bukkit.Location>();
        vines = new java.util.LinkedList<org.bukkit.block.Block>();
        rnd = new java.util.Random();
        ignoreHouseBlocks = false;
        debugger = uk.co.gorbb.qwicktree.util.debug.Debugger.get(player);
        logsToSearch.add(baseBlock);
        dropLocation = baseBlock.getLocation();
    }

    public void go() {
        debugger.addStage("CA.go");// 1

        // Check
        if (!check())
            return;

        debugger.addStage("CA.go");// 2

        // Damage
        if (!damage())
            return;

        debugger.addStage("CA.go");// 3

        // Chop
        chop();
        debugger.addStage("CA.go");// 4

        // Replant
        replant();
        debugger.addStage("CA.go");// 5

    }

    private boolean check() {
        debugger.addStage("CA.check");// 1

        // Log search
        if (!logSearch())
            return false;

        debugger.addStage("CA.check");// 2

        // Leaf/other search
        if (!leafSearch())
            return false;

        debugger.addStage("CA.check");// 3

        // Size check
        if (!checkSize())
            return false;

        debugger.addStage("CA.check");// 4

        return true;
    }

    private boolean damage() {
        return doDamage();
    }

    private void chop() {
        uk.co.gorbb.qwicktree.QwickTree.get().addTreeChop(tree.getType());
        // Break all of the leaves and vines
        debugger.addStage("CA.chop");// 1

        for (org.bukkit.block.Block leaf : leaves)
            breakBlock(leaf);

        debugger.addStage("CA.chop");// 2

        for (org.bukkit.block.Block vine : vines)
            breakBlock(vine);

        debugger.addStage("CA.chop");// 3

        // Check if autoCollect
        if (((player.getGameMode() == org.bukkit.GameMode.CREATIVE) && uk.co.gorbb.qwicktree.config.Config.get().doCreativeAutoCollect()) || tree.doAutoCollect()) {
            for (org.bukkit.block.Block log : logs)
                breakBlock(log);

            dropToInventory();
        } else if (uk.co.gorbb.qwicktree.config.Config.get().doGroupDrops()) {
            for (org.bukkit.block.Block log : logs)
                breakBlock(log);

            dropToGroup();
        } else // Check normal
        {
            for (org.bukkit.block.Block log : logs)
                breakBlockNaturally(log);

            dropToWorld();
        }
        debugger.addStage("CA.chop");// 4

    }

    private void replant() {
        // Check if the tree should be replanted, or not..
        if (((player.getGameMode() == org.bukkit.GameMode.CREATIVE) && (!uk.co.gorbb.qwicktree.config.Config.get().doCreativeReplant())) || (!tree.doReplant()))
            return;

        uk.co.gorbb.qwicktree.chop.TreeReplanter replanter = new uk.co.gorbb.qwicktree.chop.TreeReplanter(tree, baseBlocks);
        org.bukkit.Bukkit.getScheduler().scheduleSyncDelayedTask(uk.co.gorbb.qwicktree.QwickTree.get(), replanter);
    }

    /* ### CHECK ### */
    private boolean logSearch() {
        while (!logsToSearch.isEmpty()) {
            // Get the next block to search around
            org.bukkit.block.Block current = logsToSearch.pop();
            // Process it...
            if (!processCurrentLog(current))
                return false;

            // Then search around it.
            if (!searchCurrentLog(current))
                return false;

        } 
        return true;
    }

    private boolean processCurrentLog(org.bukkit.block.Block block) {
        // Don't add it if it's already in the list
        if (logs.contains(block))
            return true;

        // Add it to the list
        logs.add(block);
        // Check against max tree size
        if (logs.size() > tree.getLogMax())
            return false;

        // If it's a standing block, then add it to base blocks too, but only if there's space
        if (tree.isValidStandingBlock(block.getRelative(org.bukkit.block.BlockFace.DOWN)) && (baseBlocks.size() < 4))
            baseBlocks.add(block.getLocation());

        return true;
    }

    private boolean searchCurrentLog(org.bukkit.block.Block current) {
        int yStart = (tree.getAnyBlock()) ? -1 : 0;
        for (int x = -1; x <= 1; x++)
            for (int z = -1; z <= 1; z++)
                for (int y = yStart; y <= 1; y++) {
                    org.bukkit.block.Block block = current.getRelative(x, y, z);
                    if (!houseBlockSearch(block))
                        return false;

                    // ...or is the current one...
                    if ((((!tree.isValidLog(block))// If it's not a valid log...
                     || logsToSearch.contains(block))// ...or is already set to search around...
                     || logs.contains(block))// ...or has already been searched around...
                     || current.equals(block))
                        continue;

                    // ...then skip to the next loop.
                    logsToSearch.push(block);
                }


        return true;
    }

    private boolean houseBlockSearch(org.bukkit.block.Block log) {
        for (int x = -2; x <= 2; x++)
            for (int z = -2; z <= 2; z++)
                for (int y = -1; y <= 1; y++) {
                    org.bukkit.block.Block current = log.getRelative(x, y, z);
                    // Check for house block
                    if ((!ignoreHouseBlocks) && uk.co.gorbb.qwicktree.config.Config.get().isHouseBlock(current))
                        if (uk.co.gorbb.qwicktree.util.HouseIgnore.get().ignoreHouseBlocks(player))
                            ignoreHouseBlocks = true;
                        else {
                            Message.NOTIFY.send(Permission.NOTIFY, player.getName(), formatLocation(current));
                            return false;
                        }

                }


        return true;
    }

    private boolean leafSearch() {
        int leafReach = getLeafReach();
        // For each log
        for (org.bukkit.block.Block log : logs)
            for (int x = -leafReach; x <= leafReach; x++)
                for (int z = -leafReach; z <= leafReach; z++)
                    for (int y = 0; y <= leafReach; y++) {
                        org.bukkit.block.Block current = log.getRelative(x, y, z);
                        if (!processCurrentLeaf(current))
                            return false;

                    }



        return true;
    }

    private boolean processCurrentLeaf(org.bukkit.block.Block current) {
        // Check for vines
        if ((current.getType() == org.bukkit.Material.VINE) && (vines.size() < uk.co.gorbb.qwicktree.config.Config.get().getMaxVines()))
            vines.add(current);

        // Check for leaves
        // ...or it's within ground reach...
        if (((!tree.isValidLeaf(current))// If it's not a valid leaf...
         || leaves.contains(current))// ...or the leaf has already been found...
         || groundInReach(current))
            return true;

        // ...then get outta here!
        leaves.add(current);
        return true;
    }

    private boolean checkSize() {
        debugger.setStage("Log Size", logs.size());
        debugger.setStage("Leaf Size", leaves.size());
        if (logs.size() < tree.getLogMin())
            return false;

        if (logs.size() > tree.getLogMax())
            return false;

        if (leaves.size() < tree.getLeafMin())
            return false;

        return true;
    }

    /* ### DAMAGE ### */
    private boolean doDamage() {
        // If player is creative and shouldn't do damage, then return
        if ((player.getGameMode() == org.bukkit.GameMode.CREATIVE) && (!uk.co.gorbb.qwicktree.config.Config.get().doCreativeDamage()))
            return true;

        // Work out base damage
        int damageAmt;
        __SmPLUnsupported__(0);
        // Work out unbreaking
        damageAmt = calculateUnbreaking(damageAmt);
        uk.co.gorbb.qwicktree.chop.ItemStack item = player.getInventory();
        short newDurability = ((short) (item.getDurability() + damageAmt));// Figure out the new durability of the item

        if (newDurability > item.getType().getMaxDurability())
            return false;

        // If the item cannot take this much damage, then return
        // Apply damage
        item.setDurability(newDurability);
        return true;
    }

    private int calculateUnbreaking(int damageAmt) {
        int unbreakingLevel = player.getInventory().getEnchantmentLevel(org.bukkit.enchantments.Enchantment.DURABILITY);
        // If the item doesn't have unbreaking, or the damage amount is already nothing (or less?!), then don't do anything
        if ((unbreakingLevel == 0) || (damageAmt <= 0))
            return damageAmt;

        int newDamageAmt = 0;
        double chance = 0.5;
        if (unbreakingLevel == 2)
            chance = 0.6666;
        else if (unbreakingLevel == 3)
            chance = 0.75;

        // Since unbreaking is applied for EACH point of damage, I'll do the same.
        for (int point = 0; point < damageAmt; point++)
            if (rnd.nextDouble() >= chance)
                newDamageAmt++;


        return newDamageAmt;
    }

    /* ### CHOP ### */
    private void dropToInventory() {
        org.bukkit.inventory.ItemStack[] items = combineItems();
        java.util.HashMap<java.lang.Integer, org.bukkit.inventory.ItemStack> returned = player.getInventory().addItem(items);
        if (returned == null)
            return;

        org.bukkit.inventory.ItemStack[] returnedItems = returned.values().toArray(new org.bukkit.inventory.ItemStack[returned.size()]);
        dropAt(dropLocation, returnedItems);
    }

    private void dropToGroup() {
        dropAt(dropLocation, combineItems());
    }

    private void dropToWorld() {
        java.util.HashMap<org.bukkit.Location, org.bukkit.inventory.ItemStack> drops = processDrops();
        for (org.bukkit.Location location : drops.keySet())
            dropAt(location, drops.get(location));

    }

    private org.bukkit.inventory.ItemStack[] combineItems() {
        java.util.Collection<org.bukkit.inventory.ItemStack> drops = processDrops().values();
        java.util.HashMap<org.bukkit.Material, java.lang.Integer> combinedDrops = new java.util.HashMap<org.bukkit.Material, java.lang.Integer>();
        // First get how many of each item we should have
        for (org.bukkit.inventory.ItemStack drop : drops) {
            int qty = drop.getAmount();
            if (combinedDrops.containsKey(drop.getType()))
                qty += combinedDrops.get(drop.getType());

            combinedDrops.put(drop.getType(), qty);
        }
        // Add the logs, since everything here is being combined...
        combinedDrops.put(org.bukkit.Material.LOG, logs.size());
        // And then the vines if there are any.
        if (vines.size() > 0)
            combinedDrops.put(org.bukkit.Material.VINE, vines.size());

        // Then create ItemStacks of this
        java.util.List<org.bukkit.inventory.ItemStack> combinedList = new java.util.ArrayList<org.bukkit.inventory.ItemStack>(combinedDrops.size());
        for (org.bukkit.Material material : combinedDrops.keySet())
            combinedList.add(tree.processItem(material, combinedDrops.get(material)));

        return combinedList.toArray(new org.bukkit.inventory.ItemStack[combinedList.size()]);
    }

    private java.util.HashMap<org.bukkit.Location, org.bukkit.inventory.ItemStack> processDrops() {
        java.util.HashMap<org.bukkit.Location, org.bukkit.inventory.ItemStack> drops = new java.util.HashMap<org.bukkit.Location, org.bukkit.inventory.ItemStack>();
        // For each leaf...
        for (org.bukkit.block.Block leaf : leaves) {
            // Get a random material to use.
            org.bukkit.Material drop = getRandomDrop();
            if (drop == null)
                continue;

            drops.put(leaf.getLocation(), tree.processItem(drop, 1));
        }
        return drops;
    }

    private org.bukkit.Material getRandomDrop() {
        java.util.HashMap<java.lang.Double, org.bukkit.Material> dropChances = tree.getDrops();
        double number = rnd.nextDouble();
        org.bukkit.Material selected = null;
        for (double dropChance : dropChances.keySet()) {
            selected = dropChances.get(dropChance);
            if (number <= dropChance)
                break;

        }
        return selected;
    }

    private void dropAt(org.bukkit.Location location, org.bukkit.inventory.ItemStack... items) {
        for (org.bukkit.inventory.ItemStack item : items)
            location.getWorld().dropItemNaturally(location, item);

    }

    /* ### OTHER ### */
    private int getLeafReach() {
        int baseLeafReach = tree.getLeafReach();
        uk.co.gorbb.qwicktree.tree.info.TreeType type = tree.getType();
        int size = logs.size();
        org.bukkit.block.Biome biome = logs.get(0).getBiome();
        if (type == uk.co.gorbb.qwicktree.tree.info.TreeType)
            if ((biome == org.bukkit.block.Biome.SWAMPLAND) || (biome == org.bukkit.block.Biome.SWAMPLAND_MOUNTAINS))
                baseLeafReach += 1;
            else if (size >= 15)
                baseLeafReach += 1;


        // Large oak elsewhere, increase by 1
        if ((type == uk.co.gorbb.qwicktree.tree.info.TreeType.PINE) && (size >= 20))
            baseLeafReach += 1;

        // Large pine, increase by 1
        if ((type == uk.co.gorbb.qwicktree.tree.info.TreeType.JUNGLE) && (size >= 20))
            baseLeafReach += 2;

        // Large jungle, increase by 1
        return baseLeafReach;
    }

    private java.lang.String formatLocation(org.bukkit.block.Block block) {
        org.bukkit.Location location = block.getLocation();
        return (((((location.getWorld().getName() + ", ") + location.getBlockX()) + ", ") + location.getBlockY()) + ", ") + location.getBlockZ();
    }

    private boolean groundInReach(org.bukkit.block.Block block) {
        int groundReach = tree.getLeafGroundOffset();
        for (int distance = 1; distance <= groundReach; distance++) {
            org.bukkit.block.Block newBlock = block.getRelative(org.bukkit.block.BlockFace.DOWN, distance);
            if (tree.isValidStandingBlock(newBlock))
                return true;

        }
        return false;
    }

    private void breakBlock(org.bukkit.block.Block block) {
        uk.co.gorbb.qwicktree.util.Logging.logBreak(player, block);
        block.setType(org.bukkit.Material.AIR);
    }

    private void breakBlockNaturally(org.bukkit.block.Block block) {
        uk.co.gorbb.qwicktree.util.Logging.logBreak(player, block);
        block.breakNaturally();
    }
}