/* private void checkNearAndReplace(Block block, Material require, Material replacement, Double chance) {
for (BlockFace face : BlockFace.values()) {
Block relativeBlock = block.getRelative(face);
if (relativeBlock !=null && relativeBlock.getType() == require) {
// note: can't modify in async so store in a map that a sync thread will check
// or trigger an event
if (rolldice(chance)) {
triggerEvent(block, replacement); 
}
}
}
}

//world level conditions: world, time, weather
//block level conditions: biome, region, height, lightlevel

private void triggerEvent(Block block, Material replacement) {
Bukkit.getServer().broadcastMessage("Working!");		
}
 */
private void SyncProcess() {
    long beforeLoading = java.lang.System.currentTimeMillis();
    com.gmail.zariust.mcplugins.othergrowth.Log.high("Starting sync replacements...");
    if (OtherGrowthConfig.globalScanLoadedChunks) {
        gatherLoadedChunks();
    } else {
        gatherChunks();
    }
    long afterLoading = java.lang.System.currentTimeMillis();
    com.gmail.zariust.mcplugins.othergrowth.Log.high(("After loading chunks... (elapsed: " + (afterLoading - beforeLoading)) + ")");
    com.gmail.zariust.mcplugins.othergrowth.MatchResult result = OtherGrowth.results.poll();
    int count = 0;
    while (result != null) {
        org.bukkit.ChunkSnapshot chunkSnapshot = result.getChunkSnapshot();
        org.bukkit.Location loc = result.getLocation().clone();
        org.bukkit.World world = org.bukkit.Bukkit.getServer().getWorld(chunkSnapshot.getWorldName());
        org.bukkit.block.Block block = world.getChunkAt(chunkSnapshot.getX(), chunkSnapshot.getZ()).getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        com.gmail.zariust.mcplugins.othergrowth.Recipe recipe = result.getRecipe();
        com.gmail.zariust.mcplugins.othergrowth.Log.highest("Got a result: " + block.getType().toString());
        boolean neededMatch = false;
        neededMatch = checkNeeded(block, recipe, neededMatch);
        // match regions
        // if (!isInRegion(world, block.getLocation().clone(), recipe.regions)) {
        // neededMatch = false;
        // }
        if (neededMatch) {
            count++;
            com.gmail.zariust.mcplugins.othergrowth.Log.highest("Replacing with: " + recipe.replacementMat);
            block.setType(recipe.replacementMat);
        } else {
            com.gmail.zariust.mcplugins.othergrowth.Log.highest("Match failed.");
        }
        // Block block = world.getBlock(x,y,z);
        // recipe = recipeSet.get(block.getType());
        // if (recipe != null) {
        // if (block.getBiome().matches(recipe.biome)) {
        // checkNearAndReplace(block, recipe.require, recipe.replacement, recipe.chance);
        // }
        // }
        // }
        // }
        result = OtherGrowth.results.poll();
    } 
    long finishedTime = java.lang.System.currentTimeMillis();
    com.gmail.zariust.mcplugins.othergrowth.Log.high(((("Sync complete, replaced (" + count) + " blocks) (elapsed: ") + (finishedTime - beforeLoading)) + ")");
}