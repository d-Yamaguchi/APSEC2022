package at.pavlov.cannons.listener;
import at.pavlov.cannons.CalcAngle;
import at.pavlov.cannons.CannonManager;
import at.pavlov.cannons.Cannons;
import at.pavlov.cannons.FireCannon;
import at.pavlov.cannons.cannon.Cannon;
import at.pavlov.cannons.cannon.CannonDesign;
import at.pavlov.cannons.config.Config;
import at.pavlov.cannons.config.MessageEnum;
import at.pavlov.cannons.config.UserMessages;
import at.pavlov.cannons.event.CannonFireEvent;
import at.pavlov.cannons.event.CannonRedstoneEvent;
import at.pavlov.cannons.event.CannonUseEvent;
import at.pavlov.cannons.event.InteractAction;
import at.pavlov.cannons.projectile.Projectile;
import at.pavlov.cannons.utils.CannonsUtil;
public class PlayerListener implements org.bukkit.event.Listener {
    private at.pavlov.cannons.config.Config config;

    private at.pavlov.cannons.config.UserMessages userMessages;

    private at.pavlov.cannons.Cannons plugin;

    private at.pavlov.cannons.CannonManager cannonManager;

    private at.pavlov.cannons.FireCannon fireCannon;

    private at.pavlov.cannons.CalcAngle calcAngle;

    public PlayerListener(at.pavlov.cannons.Cannons plugin) {
        this.plugin = plugin;
        this.config = this.plugin.getmyConfig();
        this.userMessages = this.plugin.getmyConfig().getUserMessages();
        this.cannonManager = this.plugin.getCannonManager();
        this.fireCannon = this.plugin.getFireCannon();
        this.calcAngle = this.plugin.getCalcAngle();
    }

    // ########### PlayerMove #######################################
    @org.bukkit.event.EventHandler
    public void PlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        // only active if the player is in aiming mode
        if (calcAngle.distanceCheck(event.getPlayer(), calcAngle.getCannonInAimingMode(event.getPlayer())) == false) {
            userMessages.displayMessage(event.getPlayer(), MessageEnum.AimingModeTooFarAway);
            at.pavlov.cannons.config.MessageEnum message = calcAngle.disableAimingMode(event.getPlayer());
            userMessages.displayMessage(event.getPlayer(), message);
        }
    }

    // ########### BlockFromTo #######################################
    @org.bukkit.event.EventHandler
    public void BlockFromTo(org.bukkit.event.block.BlockFromToEvent event) {
        org.bukkit.block.Block block = event.getToBlock();
        if ((block.getType() == org.bukkit.Material.STONE_BUTTON) || (block.getType() == org.bukkit.Material.TORCH)) {
            if (cannonManager.getCannon(block.getLocation(), null) != null) {
                event.setCancelled(true);
            }
        }
    }

    // ########### BlockPistonRetract #######################################
    @org.bukkit.event.EventHandler
    public void BlockPistonRetract(org.bukkit.event.block.BlockPistonRetractEvent event) {
        // when piston is sticky and has a cannon block attached delete the
        // cannon
        if (event.isSticky() == true) {
            org.bukkit.Location loc = event.getBlock().getRelative(event.getDirection(), 2).getLocation();
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(loc, null);
            if (cannon != null) {
                cannonManager.removeCannon(cannon);
            }
        }
    }

    // ########### BlockPistonExtend #######################################
    @org.bukkit.event.EventHandler
    public void BlockPistonExtend(org.bukkit.event.block.BlockPistonExtendEvent event) {
        // when the moved block is a cannonblock
        java.util.Iterator<org.bukkit.block.Block> iter = event.getBlocks().iterator();
        while (iter.hasNext()) {
            // if moved block is cannonBlock delete cannon
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(iter.next().getLocation(), null);
            if (cannon != null) {
                cannonManager.removeCannon(cannon);
            }
        } 
    }

    // ########### BlockBurn #######################################
    @org.bukkit.event.EventHandler
    public void BlockBurn(org.bukkit.event.block.BlockBurnEvent event) {
        // the cannon will not burn down
        if (cannonManager.getCannon(event.getBlock().getLocation(), null) != null) {
            event.setCancelled(true);
        }
    }

    /**
     * if one block of the cannon is destroyed, it is removed from the list of cannons
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler
    public void BlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
        // if deleted block is cannonBlock delete cannon
        at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(event.getBlock().getLocation(), null);
        if (cannon != null) {
            cannonManager.removeCannon(cannon);
        }
    }

    /**
     * cancels the event if the player click a cannon with water
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler
    public void PlayerBucketEmpty(org.bukkit.event.player.PlayerBucketEmptyEvent event) {
        // if player loads a lava/water bucket in the cannon
        org.bukkit.Location blockLoc = event.getBlockClicked().getLocation();
        at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(blockLoc, event.getPlayer().getName());
        // check if it is a cannon
        if (cannon != null) {
            // data =-1 means no data check, all buckets are allowed
            at.pavlov.cannons.projectile.Projectile projectile = plugin.getProjectile(cannon, event.getBucket().getId(), -1);
            if (projectile != null)
                event.setCancelled(true);

        }
    }

    /**
     * Create a cannon if the building process is finished Deletes a projectile
     * if loaded Checks for redstone torches if built
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler
    public void BlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        org.bukkit.Location blockLoc = block.getLocation();
        // setup a new cannon
        cannonManager.getCannon(blockLoc, event.getPlayer().getName());
        org.bukkit.block.Block block = event.getBlockPlaced();
        org.bukkit.block.Block block = event.getBlockPlaced();
        // delete placed projectile or gunpowder if clicked against the barrel
        if (event.getBlockAgainst() != null) {
            org.bukkit.Location barrel = event.getBlockAgainst().getLocation();
            // check if block is cannonblock
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(barrel, event.getPlayer().getName(), true);
            if (cannon != null) {
                // delete projectile
                at.pavlov.cannons.projectile.Projectile projectile = plugin.getProjectile(cannon, block.getType(), block.getData());
                if ((projectile != null) && cannon.getCannonDesign().canLoad(projectile)) {
                    // check if the placed block is not part of the cannon
                    if (!cannon.isCannonBlock(event.getBlock())) {
                        event.setCancelled(true);
                    }
                }
                // delete gunpowder block
                if (cannon.getCannonDesign().getGunpowderType().equalsFuzzy(event.getBlock())) {
                    // check if the placed block is not part of the cannon
                    if (!cannon.isCannonBlock(event.getBlock())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
        // Place redstonetorch under to the cannon
        if ((event.getBlockPlaced().getType() == org.bukkit.Material.REDSTONE_TORCH_ON) || (event.getBlockPlaced().getType() == org.bukkit.Material.REDSTONE_TORCH_OFF)) {
            // check cannon
            org.bukkit.Location loc = event.getBlock().getRelative(org.bukkit.block.BlockFace.UP).getLocation();
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(loc, event.getPlayer().getName(), true);
            if (cannon != null) {
                // check permissions
                if (event.getPlayer().hasPermission(cannon.getCannonDesign().getPermissionRedstone()) == false) {
                    // check if the placed block is in the redstone torch interface
                    if (cannon.isRedstoneTorchInterface(event.getBlock().getLocation())) {
                        userMessages.displayMessage(event.getPlayer(), MessageEnum.PermissionErrorRedstone);
                        event.setCancelled(true);
                    }
                }
            }
        }
        // Place redstone wire next to the button
        if (event.getBlockPlaced().getType() == org.bukkit.Material.REDSTONE_WIRE) {
            // check cannon
            for (org.bukkit.block.Block b : at.pavlov.cannons.utils.CannonsUtil.HorizontalSurroundingBlocks(event.getBlock())) {
                org.bukkit.Location loc = b.getLocation();
                at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(loc, event.getPlayer().getName(), true);
                if (cannon != null) {
                    // check permissions
                    if (event.getPlayer().hasPermission(cannon.getCannonDesign().getPermissionRedstone()) == false) {
                        // check if the placed block is in the redstone wire interface
                        if (cannon.isRedstoneWireInterface(event.getBlock().getLocation())) {
                            userMessages.displayMessage(event.getPlayer(), MessageEnum.PermissionErrorRedstone);
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
        // cancel igniting of the cannon
        if (event.getBlock().getType() == org.bukkit.Material.FIRE) {
            // check cannon
            org.bukkit.Location loc = event.getBlockAgainst().getLocation();
            if (cannonManager.getCannon(loc, event.getPlayer().getName(), true) != null) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * handles redstone events (torch, wire, repeater, button)
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler
    public void RedstoneEvent(org.bukkit.event.block.BlockRedstoneEvent event) {
        org.bukkit.block.Block block = event.getBlock();
        if (block == null)
            return;

        plugin.logDebug((("Redstone event was fired by " + block.getTypeId()) + ":") + block.getData());
        // ##########  redstone torch fire
        // off because it turn form off to on
        if (block.getType() == org.bukkit.Material.REDSTONE_TORCH_ON) {
            // go one block up and check this is a cannon
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(block.getRelative(org.bukkit.block.BlockFace.UP).getLocation(), null);
            if (cannon != null) {
                // there is cannon next to the torch - check if the torch is
                // place right
                plugin.logDebug("redstone torch");
                if (cannon.isRedstoneTorchInterface(block.getLocation())) {
                    at.pavlov.cannons.event.CannonRedstoneEvent redEvent = new at.pavlov.cannons.event.CannonRedstoneEvent(cannon);
                    org.bukkit.Bukkit.getServer().getPluginManager().callEvent(redEvent);
                    if (redEvent.isCancelled())
                        return;

                    at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, null, cannon.getCannonDesign().isAutoreloadRedstone());
                    plugin.logDebug("fire cannon returned: " + message.getString());
                }
            }
        }
        // ##########  redstone wire fire
        if (block.getType() == org.bukkit.Material.REDSTONE_WIRE) {
            // block is powered
            if (block.getData() == 0) {
                // check all block next to this if there is a cannon
                for (org.bukkit.block.Block b : at.pavlov.cannons.utils.CannonsUtil.HorizontalSurroundingBlocks(block)) {
                    at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(b.getLocation(), null);
                    if (cannon != null) {
                        // there is cannon next to the wire - check if the wire
                        // is place right
                        plugin.logDebug("redstone wire ");
                        if (cannon.isRedstoneWireInterface(block.getLocation())) {
                            at.pavlov.cannons.event.CannonRedstoneEvent redEvent = new at.pavlov.cannons.event.CannonRedstoneEvent(cannon);
                            org.bukkit.Bukkit.getServer().getPluginManager().callEvent(redEvent);
                            if (redEvent.isCancelled())
                                return;

                            at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, null, cannon.getCannonDesign().isAutoreloadRedstone());
                            plugin.logDebug("fire cannon returned: " + message.getString());
                        }
                    }
                }
            }
        }
        // ##########  redstone repeater and comparator fire
        if ((block.getType() == org.bukkit.Material.DIODE_BLOCK_OFF) || (block.getTypeId() == 149)) {
            // check all block next to this if there is a cannon
            for (org.bukkit.block.Block b : at.pavlov.cannons.utils.CannonsUtil.HorizontalSurroundingBlocks(block)) {
                at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(b.getLocation(), null);
                if (cannon != null) {
                    // there is cannon next to the wire - check if the wire
                    // is place right
                    plugin.logDebug("redstone repeater ");
                    if (cannon.isRedstoneRepeaterInterface(block.getLocation())) {
                        at.pavlov.cannons.event.CannonRedstoneEvent redEvent = new at.pavlov.cannons.event.CannonRedstoneEvent(cannon);
                        org.bukkit.Bukkit.getServer().getPluginManager().callEvent(redEvent);
                        if (redEvent.isCancelled())
                            return;

                        at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, null, cannon.getCannonDesign().isAutoreloadRedstone());
                        plugin.logDebug("fire cannon returned: " + message.getString());
                    }
                }
            }
        }
        // ##########  fire with Button
        at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(event.getBlock().getLocation(), null);
        if (cannon != null) {
            // check if the button is a loading firing interface of the cannon
            if (cannon.isRestoneTrigger(event.getBlock().getLocation())) {
                // get the user of the cannon
                org.bukkit.entity.Player player = null;
                if (cannon.getLastUser() != null)
                    player = org.bukkit.Bukkit.getPlayer(cannon.getLastUser());

                // reset user
                cannon.setLastUser("");
                if (player == null)
                    return;

                plugin.logDebug("Redfire with button by " + player.getName());
                // register event with bukkit
                at.pavlov.cannons.event.CannonUseEvent useEvent = new at.pavlov.cannons.event.CannonUseEvent(cannon, player, at.pavlov.cannons.event.InteractAction.fireButton);
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(useEvent);
                if (useEvent.isCancelled())
                    return;

                // execute event
                boolean autoreload = player.isSneaking() && player.hasPermission(cannon.getCannonDesign().getPermissionAutoreload());
                at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, player, autoreload);
                userMessages.displayMessage(player, message, cannon);
            }
        }
    }

    /**
     * Handles event if player interacts with the cannon
     *
     * @param event
     * 		
     */
    @org.bukkit.event.EventHandler
    public void PlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) || (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR)) {
            org.bukkit.block.Block clickedBlock;
            if (event.getClickedBlock() == null) {
                // no clicked block - get block player is looking at
                clickedBlock = event.getPlayer().getTargetBlock(null, 4);
            } else {
                clickedBlock = event.getClickedBlock();
            }
            org.bukkit.entity.Player player = event.getPlayer();
            org.bukkit.Location barrel = clickedBlock.getLocation();
            // find cannon or add it to the list
            at.pavlov.cannons.cannon.Cannon cannon = cannonManager.getCannon(barrel, player.getName(), true);
            // no cannon found - maybe the player has click into the air to stop aiming
            if (cannon == null) {
                // all other actions will stop aiming mode
                if (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) {
                    at.pavlov.cannons.config.MessageEnum message = calcAngle.disableAimingMode(event.getPlayer());
                    userMessages.displayMessage(player, message);
                }
                return;
            }
            // get cannon design
            at.pavlov.cannons.cannon.CannonDesign design = cannon.getCannonDesign();
            // prevent eggs and snowball from firing when loaded into the gun
            org.bukkit.Material ItemInHand = player.getItemInHand().getType();
            if (((ItemInHand == org.bukkit.Material.EGG) || (ItemInHand == org.bukkit.Material.SNOW_BALL)) || (ItemInHand == org.bukkit.Material.MONSTER_EGG)) {
                event.setCancelled(true);
            }
            plugin.logDebug("player interact event fired");
            // ############ set angle ################################
            if ((config.getToolAdjust().equalsFuzzy(player.getItemInHand()) || config.getToolAutoaim().equalsFuzzy(player.getItemInHand())) && cannon.isLoadingBlock(clickedBlock.getLocation())) {
                plugin.logDebug("change cannon angle");
                // fire event
                at.pavlov.cannons.event.CannonUseEvent useEvent = new at.pavlov.cannons.event.CannonUseEvent(cannon, player, at.pavlov.cannons.event.InteractAction.adjust);
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(useEvent);
                if (useEvent.isCancelled())
                    return;

                at.pavlov.cannons.config.MessageEnum message = calcAngle.ChangeAngle(cannon, event.getAction(), event.getBlockFace(), player);
                userMessages.displayMessage(player, message, cannon);
                // update Signs
                cannon.updateCannonSigns();
                return;
            }
            // ########## Load Projectile ######################
            at.pavlov.cannons.projectile.Projectile projectile = plugin.getProjectile(cannon, event.getItem());
            if (cannon.isLoadingBlock(clickedBlock.getLocation()) && (projectile != null)) {
                plugin.logDebug("load projectile");
                // fire event
                at.pavlov.cannons.event.CannonUseEvent useEvent = new at.pavlov.cannons.event.CannonUseEvent(cannon, player, at.pavlov.cannons.event.InteractAction.loadProjectile);
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(useEvent);
                if (useEvent.isCancelled())
                    return;

                // load projectile
                at.pavlov.cannons.config.MessageEnum message = cannon.loadProjectile(projectile, player);
                // display message
                userMessages.displayMessage(player, message, cannon);
            }
            // ########## Barrel clicked with gunpowder
            if (cannon.isLoadingBlock(clickedBlock.getLocation()) && design.getGunpowderType().equalsFuzzy(event.getItem())) {
                plugin.logDebug("load gunpowder");
                // fire event
                at.pavlov.cannons.event.CannonUseEvent useEvent = new at.pavlov.cannons.event.CannonUseEvent(cannon, player, at.pavlov.cannons.event.InteractAction.loadGunpowder);
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(useEvent);
                if (useEvent.isCancelled())
                    return;

                // load gunpowder
                at.pavlov.cannons.config.MessageEnum message = cannon.loadGunpowder(player);
                // display message
                userMessages.displayMessage(player, message, cannon);
            }
            // ############ Torch clicked ############################
            if (cannon.isRightClickTrigger(clickedBlock.getLocation())) {
                plugin.logDebug("fire torch");
                // fire event
                at.pavlov.cannons.event.CannonUseEvent useEvent = new at.pavlov.cannons.event.CannonUseEvent(cannon, player, at.pavlov.cannons.event.InteractAction.fireTorch);
                org.bukkit.Bukkit.getServer().getPluginManager().callEvent(useEvent);
                if (useEvent.isCancelled())
                    return;

                boolean autoreload = player.isSneaking() && player.hasPermission(design.getPermissionAutoreload());
                at.pavlov.cannons.config.MessageEnum message = fireCannon.prepareFire(cannon, player, autoreload);
                // display message
                userMessages.displayMessage(player, message, cannon);
                return;
            }
            // ############ Button clicked ############################
            if (cannon.isRestoneTrigger(clickedBlock.getLocation())) {
                plugin.logDebug("interact event: fire button");
                cannon.setLastUser(player.getName());
                return;
            }
        }
        return;
    }
}