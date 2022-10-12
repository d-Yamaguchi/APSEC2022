/**
 * Check stargate.
 *
 * @param buttonBlock
 * 		the button_block
 * @param facing
 * 		the facing
 * @param shape
 * 		the shape
 * @param create
 * 		the create
 * @return the stargate
 */
private static de.luricos.bukkit.WormholeXTreme.Wormhole.model.Stargate checkStargate(final org.bukkit.block.Block buttonBlock, final org.bukkit.block.BlockFace facing, final de.luricos.bukkit.WormholeXTreme.Wormhole.model.StargateShape shape, final boolean create) {
    org.bukkit.block.BlockFace _CVAR1 = facing;
    final org.bukkit.block.BlockFace opposite = de.luricos.bukkit.WormholeXTreme.Wormhole.utils.WorldUtils.getInverseDirection(_CVAR1);
    org.bukkit.block.Block _CVAR0 = buttonBlock;
    org.bukkit.block.BlockFace _CVAR2 = opposite;
    final org.bukkit.block.Block holdingBlock = _CVAR0.getFace(_CVAR2);
    if (de.luricos.bukkit.WormholeXTreme.Wormhole.logic.StargateHelper.isStargateMaterial(holdingBlock, shape)) {
        // Probably a stargate, lets start checking!
        final de.luricos.bukkit.WormholeXTreme.Wormhole.model.Stargate tempGate = new de.luricos.bukkit.WormholeXTreme.Wormhole.model.Stargate();
        tempGate.setGateWorld(buttonBlock.getWorld());
        tempGate.setGateName("");
        tempGate.setGateDialLeverBlock(buttonBlock);
        tempGate.setGateFacing(facing);
        tempGate.getGateStructureBlocks().add(buttonBlock.getLocation());
        tempGate.setGateShape(shape);
        if (!de.luricos.bukkit.WormholeXTreme.Wormhole.logic.StargateHelper.isStargateMaterial(holdingBlock.getRelative(org.bukkit.block.BlockFace.DOWN), tempGate.getGateShape())) {
            return null;
        }
        final org.bukkit.block.Block possibleSignHolder = holdingBlock.getRelative(de.luricos.bukkit.WormholeXTreme.Wormhole.utils.WorldUtils.getPerpendicularRightDirection(opposite));
        if (de.luricos.bukkit.WormholeXTreme.Wormhole.logic.StargateHelper.isStargateMaterial(possibleSignHolder, tempGate.getGateShape())) {
            // This might be a public gate with activation method of sign instead of name.
            final org.bukkit.block.Block signBlock = possibleSignHolder.getRelative(tempGate.getGateFacing());
            // If the sign block is messed up just return the gate.
            if ((!de.luricos.bukkit.WormholeXTreme.Wormhole.logic.StargateHelper.tryCreateGateSign(signBlock, tempGate)) && tempGate.isGateSignPowered()) {
                return tempGate;
            }
        }
        final int[] facingVector = new int[]{ 0, 0, 0 };
        final org.bukkit.World w = buttonBlock.getWorld();
        // Now we start calculaing the values for the blocks that need to be the stargate material.
        if (facing == org.bukkit.block.BlockFace.NORTH) {
            facingVector[0] = 1;
        } else if (facing == org.bukkit.block.BlockFace.SOUTH) {
            facingVector[0] = -1;
        } else if (facing == org.bukkit.block.BlockFace.EAST) {
            facingVector[2] = 1;
        } else if (facing == org.bukkit.block.BlockFace.WEST) {
            facingVector[2] = -1;
        } else if (facing == org.bukkit.block.BlockFace.UP) {
            facingVector[1] = -1;
        } else if (facing == org.bukkit.block.BlockFace.DOWN) {
            facingVector[1] = 1;
        }
        final int[] directionVector = new int[]{ 0, 0, 0 };
        final int[] startingPosition = new int[]{ 0, 0, 0 };
        // Calculate the cross product
        directionVector[0] = (facingVector[1] * shape.getShapeReferenceVector()[2]) - (facingVector[2] * shape.getShapeReferenceVector()[1]);
        directionVector[1] = (facingVector[2] * shape.getShapeReferenceVector()[0]) - (facingVector[0] * shape.getShapeReferenceVector()[2]);
        directionVector[2] = (facingVector[0] * shape.getShapeReferenceVector()[1]) - (facingVector[1] * shape.getShapeReferenceVector()[0]);
        // This is the 0,0,0 the block at the ground against the far side of the stargate
        startingPosition[0] = (buttonBlock.getX() + (facingVector[0] * shape.getShapeToGateCorner()[2])) + (directionVector[0] * shape.getShapeToGateCorner()[0]);
        startingPosition[1] = buttonBlock.getY() + shape.getShapeToGateCorner()[1];
        startingPosition[2] = (buttonBlock.getZ() + (facingVector[2] * shape.getShapeToGateCorner()[2])) + (directionVector[2] * shape.getShapeToGateCorner()[0]);
        for (int i = 0; i < shape.getShapeStructurePositions().length; i++) {
            final int[] bVect = shape.getShapeStructurePositions()[i];
            final int[] blockLocation = new int[]{ (bVect[2] * directionVector[0]) * (-1), bVect[1], (bVect[2] * directionVector[2]) * (-1) };
            final org.bukkit.block.Block maybeBlock = w.getBlockAt(blockLocation[0] + startingPosition[0], blockLocation[1] + startingPosition[1], blockLocation[2] + startingPosition[2]);
            if (create) {
                maybeBlock.setType(tempGate.getGateShape().getShapeStructureMaterial());
            }
            if (de.luricos.bukkit.WormholeXTreme.Wormhole.logic.StargateHelper.isStargateMaterial(maybeBlock, tempGate.getGateShape())) {
                tempGate.getGateStructureBlocks().add(maybeBlock.getLocation());
                for (final int lightPosition : shape.getShapeLightPositions()) {
                    if (lightPosition == i) {
                        while (tempGate.getGateLightBlocks().size() < 2) {
                            tempGate.getGateLightBlocks().add(new java.util.ArrayList<org.bukkit.Location>());
                        } 
                        // In 2d gate all lights go in first iteration!
                        tempGate.getGateLightBlocks().get(1).add(maybeBlock.getLocation());
                    }
                }
            } else {
                if (tempGate.getGateNetwork() != null) {
                    tempGate.getGateNetwork().getNetworkGateList().remove(tempGate);
                    if (tempGate.isGateSignPowered()) {
                        tempGate.getGateNetwork().getNetworkSignGateList().remove(tempGate);
                    }
                }
                return null;
            }
        }
        // Set the name sign location.
        if (shape.getShapeSignPosition().length > 0) {
            final int[] signLocationArray = new int[]{ (shape.getShapeSignPosition()[2] * directionVector[0]) * (-1), shape.getShapeSignPosition()[1], (shape.getShapeSignPosition()[2] * directionVector[2]) * (-1) };
            final org.bukkit.block.Block nameBlock = w.getBlockAt(signLocationArray[0] + startingPosition[0], signLocationArray[1] + startingPosition[1], signLocationArray[2] + startingPosition[2]);
            tempGate.setGateNameBlockHolder(nameBlock);
        }
        // Now set teleport in location
        final int[] teleportLocArray = new int[]{ (shape.getShapeEnterPosition()[2] * directionVector[0]) * (-1), shape.getShapeEnterPosition()[1], (shape.getShapeEnterPosition()[2] * directionVector[2]) * (-1) };
        final org.bukkit.block.Block teleBlock = w.getBlockAt(teleportLocArray[0] + startingPosition[0], teleportLocArray[1] + startingPosition[1], teleportLocArray[2] + startingPosition[2]);
        // First go forward one
        org.bukkit.block.Block bLoc = teleBlock.getRelative(facing);
        // Now go up until we hit air or water.
        while ((bLoc.getTypeId() != 0) && (bLoc.getTypeId() != 8)) {
            bLoc = bLoc.getRelative(org.bukkit.block.BlockFace.UP);
        } 
        final org.bukkit.Location teleLoc = bLoc.getLocation();
        // Make sure the guy faces the right way out of the portal.
        teleLoc.setYaw(de.luricos.bukkit.WormholeXTreme.Wormhole.utils.WorldUtils.getDegreesFromBlockFace(facing));
        teleLoc.setPitch(0);
        // Put him in the middle of the block instead of a corner.
        // Players are 1.65 blocks tall, so we go up .66 more up :-p
        teleLoc.setX(teleLoc.getX() + 0.5);
        teleLoc.setY(teleLoc.getY() + 0.66);
        teleLoc.setZ(teleLoc.getZ() + 0.5);
        tempGate.setGatePlayerTeleportLocation(teleLoc);
        for (final int[] bVect : shape.getShapePortalPositions()) {
            final int[] blockLocation = new int[]{ (bVect[2] * directionVector[0]) * (-1), bVect[1], (bVect[2] * directionVector[2]) * (-1) };
            final org.bukkit.block.Block maybeBlock = w.getBlockAt(blockLocation[0] + startingPosition[0], blockLocation[1] + startingPosition[1], blockLocation[2] + startingPosition[2]);
            if (maybeBlock.getTypeId() == 0) {
                tempGate.getGatePortalBlocks().add(maybeBlock.getLocation());
            } else {
                if (tempGate.getGateNetwork() != null) {
                    tempGate.getGateNetwork().getNetworkGateList().remove(tempGate);
                }
                return null;
            }
        }
        de.luricos.bukkit.WormholeXTreme.Wormhole.logic.StargateHelper.setupSignGateNetwork(tempGate);
        return tempGate;
    }
    return null;
}