public Elevator(net.croxis.plugins.lift.Lift plugin, org.bukkit.block.Block block) {
    long startTime = java.lang.System.currentTimeMillis();
    this.plugin = plugin;
    // ID the iron base block.
    if (plugin.debug) {
        java.lang.System.out.println("Starting elevator gen");
    }
    // int yd = 2;
    int yscan = block.getY() - 1;
    if (plugin.useV10) {
        org.bukkit.World currentWorld = block.getWorld();
        while (yscan >= plugin.v10verlap_API.getMinY(currentWorld)) {
            if (yscan == plugin.v10verlap_API.getMinY(currentWorld)) {
                currentWorld = plugin.v10verlap_API.getLowerWorld(block.getWorld());
                // Gone too far with no base abort!
                if (currentWorld == null) {
                    return;
                }
                yscan = plugin.v10verlap_API.getMaxY(currentWorld);
                org.bukkit.block.Block checkBlock = currentWorld.getBlockAt(block.getX(), yscan, block.getZ());
                if (isValidBlock(checkBlock)) {
                    // Do nothing keep going
                } else if (isBaseBlock(checkBlock)) {
                    scanFloorBlocks(checkBlock);
                    break;
                } else {
                    // Something is obstructing the elevator so stop
                    return;
                }
                yscan--;
            }
        } 
    } else {
        while (yscan >= 0) {
            // Gone too far with no base abort!
            if (yscan == 0) {
                return;
            }
            org.bukkit.block.Block checkBlock = block.getWorld().getBlockAt(block.getX(), yscan, block.getZ());
            if (isValidBlock(checkBlock)) {
                // Do nothing keep going
            } else if (isBaseBlock(checkBlock)) {
                scanFloorBlocks(checkBlock);
                break;
            } else {
                // Something is obstructing the elevator so stop
                return;
            }
            yscan--;
        } 
    }
    // Count all blocks up from base and make sure no obstructions to top floor
    // Identify floors
    if (plugin.debug) {
        java.lang.System.out.println("Base size: " + java.lang.Integer.toString(floorBlocks.size()));
    }
    for (org.bukkit.block.Block b : floorBlocks) {
        int x = b.getX();
        int z = b.getZ();
        int y1 = b.getY();
        yscan = b.getY();
        org.bukkit.World currentWorld = b.getWorld();
        if (plugin.useV10) {
            while (true) {
                yscan++;
                if (yscan > plugin.v10verlap_API.getMaxY(currentWorld)) {
                    if (plugin.v10verlap_API.getUpperWorld(currentWorld) == null) {
                        break;
                    }
                    currentWorld = plugin.v10verlap_API.getUpperWorld(currentWorld);
                    org.bukkit.block.Block testBlock = currentWorld.getBlockAt(x, yscan, z);
                    if (!isValidBlock(testBlock)) {
                        break;
                    }
                    if (testBlock.getType() == org.bukkit.Material.STONE_BUTTON) {
                        if (plugin.checkGlass) {
                            if (!scanGlassAtY(currentWorld, testBlock.getY() - 2)) {
                                break;
                            }
                        }
                        net.croxis.plugins.lift.Floor floor = new net.croxis.plugins.lift.Floor();
                        floor.setY(yscan);
                        floor.setWorld(currentWorld);
                        if (testBlock.getRelative(org.bukkit.block.BlockFace.DOWN).getType() == org.bukkit.Material.WALL_SIGN) {
                            floor.setName(((org.bukkit.block.Sign) (testBlock.getRelative(org.bukkit.block.BlockFace.DOWN).getState())).getLine(1));
                        }
                        if (testBlock.getRelative(org.bukkit.block.BlockFace.UP).getType() == org.bukkit.Material.WALL_SIGN) {
                            if (worldFloorMap.containsKey(currentWorld)) {
                                worldFloorMap.get(currentWorld).put(yscan, floor);
                            } else {
                                java.util.TreeMap<java.lang.Integer, net.croxis.plugins.lift.Floor> map = new java.util.TreeMap<java.lang.Integer, net.croxis.plugins.lift.Floor>();
                                map.put(y1, floor);
                                worldFloorMap.put(currentWorld, map);
                            }
                        }
                        if (plugin.debug) {
                            java.lang.System.out.println("Floor added: " + b.getLocation());
                        }
                    }
                }
            } 
        } else {
            while (true) {
                y1 = y1 + 1;
                org.bukkit.block.Block testBlock = b.getWorld().getBlockAt(x, y1, z);
                if (!isValidBlock(testBlock)) {
                    break;
                }
                if (testBlock.getType() == org.bukkit.Material.STONE_BUTTON) {
                    if (plugin.checkGlass) {
                        if (!scanGlassAtY(currentWorld, testBlock.getY() - 2)) {
                            break;
                        }
                    }
                    net.croxis.plugins.lift.Floor floor = new net.croxis.plugins.lift.Floor();
                    floor.setY(y1);
                    if (testBlock.getRelative(org.bukkit.block.BlockFace.DOWN).getType() == org.bukkit.Material.WALL_SIGN) {
                        floor.setName(((org.bukkit.block.Sign) (testBlock.getRelative(org.bukkit.block.BlockFace.DOWN).getState())).getLine(1));
                    }
                    if (testBlock.getRelative(org.bukkit.block.BlockFace.UP).getType() == org.bukkit.Material.WALL_SIGN) {
                        floormap.put(y1, floor);
                    }
                    if (plugin.debug) {
                        java.lang.System.out.println("Floor added: " + b.getLocation());
                    }
                }
            } 
        }
    }
    // Count all floors and order them
    if (plugin.useV10) {
        int floorNumber = 1;
        // First order the worlds from bottom to top in array/list
        // Then cycle through worlds in order to build floor order
        java.util.ArrayList<org.bukkit.World> worlds = new java.util.ArrayList<org.bukkit.World>();
        org.bukkit.World currentWorld = block.getWorld();
        worlds.add(currentWorld);
        while (true) {
            currentWorld = plugin.v10verlap_API.getLowerWorld(currentWorld);
            if (currentWorld == null) {
                break;
            }
            if (worlds.contains(currentWorld)) {
                break;
            }
            worlds.add(0, currentWorld);
        } 
        currentWorld = block.getWorld();
        while (true) {
            currentWorld = plugin.v10verlap_API.getUpperWorld(currentWorld);
            if (currentWorld == null) {
                break;
            }
            if (worlds.contains(currentWorld)) {
                break;
            }
            worlds.add(currentWorld);
        } 
        for (org.bukkit.World world : worlds) {
            if (worldFloorMap.containsKey(world)) {
                for (net.croxis.plugins.lift.Floor floor : worldFloorMap.get(world).values()) {
                    floor.setFloor(floorNumber);
                    floormap2.put(floorNumber, floor);
                    floorNumber++;
                }
            }
        }
    } else {
        int floorNumber = 1;
        for (net.croxis.plugins.lift.Floor floor : floormap.values()) {
            floor.setFloor(floorNumber);
            floormap2.put(floorNumber, floor);
            floorNumber = floorNumber + 1;
        }
    }
    // Elevator is constructed, pass off to check signs for floor destination, collect all people and move them
    if (plugin.debug) {
        java.lang.System.out.println(("Elevator gen took: " + (java.lang.System.currentTimeMillis() - startTime)) + " ms.");
    }
}