package io.github.totemo.doppelganger;
import com.amoebaman.kitmaster.utilities.CommandController;
// ----------------------------------------------------------------------------
/**
 * Plugin class.
 */
public class Doppelganger extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
    // --------------------------------------------------------------------------
    /**
     * Return the configuration.
     *
     * @return the configuration.
     */
    public io.github.totemo.doppelganger.Configuration getConfiguration() {
        return _configuration;
    }

    // --------------------------------------------------------------------------
    /**
     * Return the {@link CreatureFactory}.
     *
     * @return the {@link CreatureFactory}.
     */
    public io.github.totemo.doppelganger.CreatureFactory getCreatureFactory() {
        return _creatureFactory;
    }

    // --------------------------------------------------------------------------
    @java.lang.Override
    public void onEnable() {
        // Saves only if config.yml doesn't exist.
        saveDefaultConfig();
        _configuration.load();
        getServer().getPluginManager().registerEvents(this, this);
        // The Plugin.getLogger() (used by help) is null at Doppelganger
        // construction time.
        if (_commands == null) {
            _commands = new io.github.totemo.doppelganger.Commands(this);
        }
        com.amoebaman.kitmaster.utilities.CommandController.registerCommands(this, _commands);
    }

    // --------------------------------------------------------------------------
    /**
     * Event handler for placing blocks.message
     *
     * Checks that a named item is stacked on a configured shape made of blocks of
     * the requisite material.
     *
     * @param event
     * 		the event.
     */
    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        // Ignore named hoes tilling soil by checking if the item is a block.
        if ((!placedItem.hasItemMeta()) || (!placedItem.getType().isBlock())) {
            return;
        }
        org.bukkit.inventory.meta.ItemMeta meta = placedItem.getItemMeta();
        org.bukkit.inventory.ItemStack placedItem = event.getItemInHand();
        if (meta.hasDisplayName()) {
            java.lang.String doppelgangerName = meta.getDisplayName();
            java.util.regex.Matcher nameMatcher = _namePattern.matcher(doppelgangerName);
            if ((_configuration.warnOnInvalidName() && (!_configuration.isArbitraryNameAllowed())) && (!nameMatcher.matches())) {
                event.getPlayer().sendMessage(((org.bukkit.ChatColor.DARK_RED + "\"") + doppelgangerName) + "\" is not a valid player name.");
            }// if name is allowed
             else {
                org.bukkit.World world = event.getPlayer().getWorld();
                // Is a specific shape required to spawn a doppelganger of this name?
                java.util.ArrayList<io.github.totemo.doppelganger.CreatureShape> shapes = _creatureFactory.getPlayerShapes(doppelgangerName);
                org.bukkit.Location loc = event.getBlock().getLocation();
                if (shapes != null) {
                    // Search the shapes associated with the specific player name.
                    io.github.totemo.doppelganger.CreatureShape shape = null;
                    for (io.github.totemo.doppelganger.CreatureShape tryShape : shapes) {
                        Block nameSign = getGateNameBlockHolder().getRelative(getGateFacing());
                        if () {
                            if (tryShape.hasBorder(loc)) {
                                shape = tryShape;
                            } else {
                                event.getPlayer().sendMessage(org.bukkit.ChatColor.YELLOW + "You need a one block gap horizontally around the shape.");
                            }
                            __SmPLUnsupported__(0);
                        }
                    }// for

                    if (shape == null) {
                        event.getPlayer().sendMessage(((org.bukkit.ChatColor.YELLOW + "That's not how you summon ") + doppelgangerName) + ".");
                    } else {
                        doDoppelganger(doppelgangerName, _creatureFactory.getPlayerCreature(doppelgangerName), shape, event);
                    }
                } else {
                    // Generic case where the doppelganger name doesn't matter.
                    // Check whether there is a complete creature under the trigger block.
                    io.github.totemo.doppelganger.CreatureShape shape = _creatureFactory.getCreatureShape(loc, event.getPlayer().getItemInHand());
                    if (shape != null) {
                        if (shape.hasBorder(loc)) {
                            java.lang.String creatureType = shape.chooseCreatureType();
                            if (_creatureFactory.isValidCreatureType(creatureType)) {
                                doDoppelganger(doppelgangerName, creatureType, shape, event);
                            } else {
                                getLogger().warning(java.lang.String.format(java.util.Locale.US, "Player %s tried to spawn a doppelganger named %s at (%g,%g,%g) in %s of invalid type %s by building a %s.", event.getPlayer().getName(), doppelgangerName, loc.getX(), loc.getY(), loc.getZ(), world.getName(), creatureType, shape.getName()));
                            }
                        } else {
                            event.getPlayer().sendMessage(org.bukkit.ChatColor.YELLOW + "You need a one block gap horizontally around the shape.");
                        }
                    }
                }
            }// if name is allowed

        }
    }// onBlockPlace


    // --------------------------------------------------------------------------
    /**
     * Vanilla Minecraft doesn't always drop equipment when the drop chance is 1.0
     * (or more). Try to work around that by moving the equipment into the drops
     * if it is not already there.
     *
     * In Bukkit versions prior to 1.7.9, the equipment was not part of the drops
     * list in the EntityDeathEvent. Bukkit fixed that issue with the API, but had
     * to retain vanilla's handling of drop probabilities, which is still faulty.
     *
     * This handler will process any entity death, but naturally spawned monsters
     * probably won't have a (near) 1.0 drop chance for the their equipment, and
     * in any case the relocation of the equipment to drops should be benign.
     */
    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        final float NEAR_UNITY = 0.999F;
        boolean forcedDrops = false;
        if (event.getEntity() instanceof org.bukkit.entity.Creature) {
            org.bukkit.inventory.EntityEquipment equipment = event.getEntity().getEquipment();
            java.util.List<org.bukkit.inventory.ItemStack> drops = event.getDrops();
            if (equipment.getHelmetDropChance() > NEAR_UNITY) {
                forcedDrops = true;
                org.bukkit.inventory.ItemStack helmet = equipment.getHelmet();
                if ((helmet != null) && (!drops.contains(helmet))) {
                    drops.add(helmet);
                    equipment.setHelmet(null);
                }
            }
            if (equipment.getChestplateDropChance() > NEAR_UNITY) {
                forcedDrops = true;
                org.bukkit.inventory.ItemStack chestplate = equipment.getChestplate();
                if ((chestplate != null) && (!drops.contains(chestplate))) {
                    drops.add(chestplate);
                    equipment.setChestplate(null);
                }
            }
            if (equipment.getLeggingsDropChance() > NEAR_UNITY) {
                forcedDrops = true;
                org.bukkit.inventory.ItemStack leggings = equipment.getLeggings();
                if ((leggings != null) && (!drops.contains(leggings))) {
                    drops.add(leggings);
                    equipment.setLeggings(null);
                }
            }
            if (equipment.getBootsDropChance() > NEAR_UNITY) {
                forcedDrops = true;
                org.bukkit.inventory.ItemStack boots = equipment.getBoots();
                if ((boots != null) && (!drops.contains(boots))) {
                    drops.add(boots);
                    equipment.setBoots(null);
                }
            }
            if (equipment.getItemInHandDropChance() > NEAR_UNITY) {
                forcedDrops = true;
                org.bukkit.inventory.ItemStack itemInHand = equipment.getItemInHand();
                if ((itemInHand != null) && (!drops.contains(itemInHand))) {
                    drops.add(itemInHand);
                    equipment.setItemInHand(null);
                }
            }
        }
        // If a unity drop chance was specified, it's probably a Doppelganger.
        // Also require a custom name, since 'special' mobs that pick up items
        // will always drop them too.
        // Log the drops for verification purposes.
        if (forcedDrops && (event.getEntity().getCustomName() != null)) {
            org.bukkit.Location loc = event.getEntity().getLocation();
            java.lang.StringBuilder drops = new java.lang.StringBuilder();
            drops.append("At (").append(loc.getBlockX()).append(',');
            drops.append(loc.getBlockY()).append(',');
            drops.append(loc.getBlockZ()).append(") drops:");
            for (org.bukkit.inventory.ItemStack item : event.getDrops()) {
                drops.append(' ');
                drops.append(item);
            }
            getLogger().info(drops.toString());
        }
    }// onEntityDeath


    // --------------------------------------------------------------------------
    /**
     * Spawn a doppelganger of the specified type and name.
     *
     * @param creatureType
     * 		the type of creature to spawn.
     * @param name
     * 		the name to show on the name tag. This is also the name of the
     * 		player whose head will be worn, unless the creature type includes
     * 		a specific mask override. If the name is null or the empty string,
     * 		the name defaults to the default name set in the creature type.
     * 		If, after the default name is taken into account, the name is
     * 		still null or empty, no name tag will be set and no player head
     * 		will be worn.
     * @param loc
     * 		the Location on the ground where the creature will spawn.
     * @return the spawned LivingEntity, or null if it could not be spawned.
     */
    public org.bukkit.entity.LivingEntity spawnDoppelganger(java.lang.String creatureType, java.lang.String name, org.bukkit.Location loc) {
        return _creatureFactory.spawnCreature(creatureType, loc, name, this);
    }

    // --------------------------------------------------------------------------
    /**
     * Cancel the original block placement, vaporise the golem blocks and spawn a
     * named LivingEntity of the specified type.
     *
     * @param doppelgangerName
     * 		the name of spawned creature.
     * @param creatureType
     * 		the name of the type of creature to spawn.
     * @param shape
     * 		the shape of the golem blocks.
     * @param event
     * 		the BlockPlaceEvent that triggered this.
     */
    protected void doDoppelganger(java.lang.String doppelgangerName, java.lang.String creatureType, io.github.totemo.doppelganger.CreatureShape shape, org.bukkit.event.block.BlockPlaceEvent event) {
        org.bukkit.Location loc = event.getBlock().getLocation();
        org.bukkit.inventory.ItemStack placedItem = event.getItemInHand();
        getLogger().info(java.lang.String.format(java.util.Locale.US, "Player %s spawned a %s named %s at (%g,%g,%g) in %s by building a %s.", event.getPlayer().getName(), creatureType, doppelgangerName, loc.getX(), loc.getY(), loc.getZ(), loc.getWorld().getName(), shape.getName()));
        // Cancel placement of the trigger.
        event.setCancelled(true);
        // Remove one trigger item from the item stack.
        if (placedItem.getAmount() > 1) {
            placedItem.setAmount(placedItem.getAmount() - 1);
            event.getPlayer().setItemInHand(placedItem);
        } else {
            event.getPlayer().setItemInHand(null);
        }
        // Vaporise the shape blocks.
        shape.vaporise(loc);
        // Add 0.5 to X and Z so the creature is not on the block boundary.
        org.bukkit.Location groundLocation = loc.clone();
        groundLocation.add(0.5, shape.getGroundOffset(), 0.5);
        // TODO: allow a customisable offset above the computed ground position.
        // The doppelganger mob.
        org.bukkit.entity.LivingEntity doppelganger = spawnDoppelganger(creatureType, doppelgangerName, groundLocation);
        if (doppelganger == null) {
            // If the creature type is invalid, it is a configuration error. The shape
            // items are already lost. Since Configuration.isValidCreatureType() was
            // called prior to entering doDoppelganger(), this shouldn't happen.
            getLogger().severe("Could not spawn " + creatureType);
        } else if (doppelganger instanceof org.bukkit.entity.Creature) {
            // If we can, make the doppelganger the players *problem*.
            ((org.bukkit.entity.Creature) (doppelganger)).setTarget(event.getPlayer());
        }
    }// doDoppelganger


    // --------------------------------------------------------------------------
    /**
     * Pattern describing allowable doppelganger names.
     */
    protected java.util.regex.Pattern _namePattern = java.util.regex.Pattern.compile("^\\w+$");

    /**
     * Handles creation of creatures.
     */
    protected io.github.totemo.doppelganger.CreatureFactory _creatureFactory = new io.github.totemo.doppelganger.CreatureFactory();

    /**
     * Configuration management.
     */
    protected io.github.totemo.doppelganger.Configuration _configuration = new io.github.totemo.doppelganger.Configuration(this, _creatureFactory);

    /**
     * Handles the command line.
     */
    protected io.github.totemo.doppelganger.Commands _commands;
}