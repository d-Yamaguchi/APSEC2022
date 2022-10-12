package net.worldoftomorrow.noitem;
import net.worldoftomorrow.noitem.config.ConfigManager;
import net.worldoftomorrow.noitem.interfaces.IAction;
import net.worldoftomorrow.noitem.interfaces.IActionFactory;
import net.worldoftomorrow.noitem.interfaces.INoItemPlayer;
public class ActionFactory implements org.bukkit.event.Listener , net.worldoftomorrow.noitem.interfaces.IActionFactory {
    private java.util.HashMap<java.lang.String, net.worldoftomorrow.noitem.interfaces.INoItemPlayer> players = new java.util.HashMap<java.lang.String, net.worldoftomorrow.noitem.interfaces.INoItemPlayer>();

    private java.util.ArrayList<net.worldoftomorrow.noitem.interfaces.INoItemPlayer> toUpdate = new java.util.ArrayList<net.worldoftomorrow.noitem.interfaces.INoItemPlayer>();

    // This method is to keep track of players that join
    @org.bukkit.event.EventHandler
    public void registerPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        players.put(event.getPlayer().getName(), new net.worldoftomorrow.noitem.NoItemPlayer(event.getPlayer()));
    }

    // This method is to keep track of players that quit
    @org.bukkit.event.EventHandler
    public void registerPlayerQuit(org.bukkit.event.player.PlayerQuitEvent event) {
        players.remove(event.getPlayer().getName());
    }

    @java.lang.SuppressWarnings("deprecation")
    @org.bukkit.event.EventHandler
    public void blockBreakEvent(org.bukkit.event.block.BlockBreakEvent event) {
        if (event.isCancelled())
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        if (player == null)
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.BREAK, getBlockName(event.getBlock()));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.BREAK, getBlockName(event.getBlock()), java.lang.String.valueOf(event.getBlock().getData()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    @java.lang.SuppressWarnings("deprecation")
    @org.bukkit.event.EventHandler
    public void blockPlaceEvent(org.bukkit.event.block.BlockPlaceEvent event) {
        if (event.isCancelled())
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        if (player == null)
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.PLACE, getBlockName(event.getBlock()));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.PLACE, getBlockName(event.getBlock()), java.lang.String.valueOf(event.getBlock().getData()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    @org.bukkit.event.EventHandler
    public void playerOpenInventoryEvent(org.bukkit.event.inventory.InventoryOpenEvent event) {
        if (event.isCancelled())
            return;

        net.worldoftomorrow.noitem.Block costBlock = getCostBlock();
        net.worldoftomorrow.noitem.InventoryType invType = costBlock.getX();
        // These do not need to be checked for opening
        if ((invType == org.bukkit.event.inventory.InventoryType.PLAYER) || (invType == org.bukkit.event.inventory.InventoryType.CREATIVE))
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        // Apparently this might happen sometimes?
        if (player == null)
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.OPEN, invType.toString().replaceAll("_", "").toLowerCase());
        if (!player.canDoAction(action)) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    // Crafting should only check for the item name with data since it makes a difference when crafting
    @org.bukkit.event.EventHandler
    public void onPlayerCraftItem(org.bukkit.event.inventory.CraftItemEvent event) {
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
        if (player == null)
            return;

        org.bukkit.inventory.ItemStack result = event.getRecipe().getResult();
        short dur = result.getDurability();
        // Only check both permissions if the durability is 0
        if (dur != 0) {
            net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.CRAFT, getItemName(result), java.lang.String.valueOf(result.getDurability()));
            if (!player.canDoAction(actionWithData)) {
                event.setCancelled(true);
                player.notifyPlayer(actionWithData);
                this.toUpdate.add(player);
            }
        } else {
            net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.CRAFT, getItemName(result), java.lang.String.valueOf(result.getDurability()));
            net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.CRAFT, getItemName(result));
            if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
                event.setCancelled(true);
                player.notifyPlayer(action);
                this.toUpdate.add(player);
            }
        }
    }

    // Update the players inventory if they are marked for it.
    @java.lang.SuppressWarnings("deprecation")
    @org.bukkit.event.EventHandler
    public void updateInvOnClose(org.bukkit.event.inventory.InventoryCloseEvent event) {
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        if (player == null)
            return;

        if (toUpdate.contains(player)) {
            player.getPlayer().updateInventory();
            toUpdate.remove(player);
        }
    }

    // Reference - org/bukkit/craftbukkit/inventory/CraftInventoryFurnace.java#L22
    // Cooking also only checks with a second value
    @org.bukkit.event.EventHandler
    public void onPlayerCook(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.isCancelled())
            return;

        Block costBlock = getCostBlock();
        InventoryType invType = costBlock.getX();
        if (invType != org.bukkit.event.inventory.InventoryType.FURNACE)
            return;

        org.bukkit.inventory.ItemStack itemToCheck;
        org.bukkit.inventory.InventoryView inventoryView = event.getView();
        org.bukkit.inventory.ItemStack onCursor = event.getCursor();
        org.bukkit.inventory.ItemStack currentIng = inventoryView.getItem(0);
        // -- Begin logic to determine what to check -- //
        // If the clicked slot type is fuel and it is empty
        if ((event.getSlotType() == org.bukkit.event.inventory.InventoryType.SlotType.FUEL) && isAir(event.getCurrentItem())) {
            // If the cursor or the ingredient is empty, return.
            if (isAir(onCursor) || isAir(currentIng))
                return;

            itemToCheck = currentIng;
        } else {
            int clickedSlot = event.getRawSlot();
            // If click was in the ingredient slot
            if (isSlotTopInventory(clickedSlot, inventoryView) && (clickedSlot == 0)) {
                // If there is not an ingredient and there is a cursor item
                if (isAir(currentIng) && (!isAir(onCursor))) {
                    itemToCheck = onCursor;
                } else
                    return;

            } else {
                // Anything not a click on the fuel slot, or ingredient slot.
                // Shift clicking is the last possibility.
                if (!event.isShiftClick())
                    return;

                org.bukkit.inventory.ItemStack clickedItem = event.getCurrentItem();
                // If the clicked slot is empty, return.
                if (isAir(clickedItem))
                    return;

                itemToCheck = clickedItem;
            }
        }
        // -- End logic to determine what to check -- //
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
        if (player == null)
            return;

        short dur = itemToCheck.getDurability();
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.COOK, getItemName(itemToCheck), java.lang.String.valueOf(dur));
        if (dur != 0) {
            if (!player.canDoAction(actionWithData)) {
                event.setCancelled(true);
                player.notifyPlayer(actionWithData);
            }
        } else {
            net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.COOK, getItemName(itemToCheck));
            if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
                event.setCancelled(true);
                player.notifyPlayer(action);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onItemPickup(org.bukkit.event.player.PlayerPickupItemEvent event) {
        if (event.isCancelled())
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        org.bukkit.entity.Item item = event.getItem();
        org.bukkit.inventory.ItemStack stack = item.getItemStack();
        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.PICKUP, getItemName(stack));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.PICKUP, getItemName(stack), java.lang.String.valueOf(stack.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            // Set the pickup delay to the same time as the message delay to prevent event spam
            item.setPickupDelay(java.lang.Integer.valueOf(net.worldoftomorrow.noitem.config.ConfigManager.getInstance().getValue("notify.timeout")));
            player.notifyPlayer(action);
        }
    }

    @org.bukkit.event.EventHandler
    public void onItemDrop(org.bukkit.event.player.PlayerDropItemEvent event) {
        if (event.isCancelled())
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        // Okay seriously, why should this ever happen? Apparently it does though.
        // I should probably find out why this would ever be null..
        if (player == null)
            return;

        org.bukkit.entity.Item item = event.getItemDrop();
        org.bukkit.inventory.ItemStack stack = item.getItemStack();
        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.DROP, getItemName(stack));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.DROP, getItemName(stack), java.lang.String.valueOf(stack.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    // Click drop (&drag?) into slot
    @org.bukkit.event.EventHandler
    public void onPlayerHoldItemClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.isCancelled())
            return;

        org.bukkit.inventory.Inventory inventory = event.getInventory();
        if (inventory.getType() != org.bukkit.event.inventory.InventoryType.CRAFTING)
            return;

        if (event.getSlotType() != org.bukkit.event.inventory.InventoryType.SlotType.QUICKBAR)
            return;

        org.bukkit.inventory.ItemStack toCheck = event.getCursor();
        if (isAir(toCheck))
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
        if (player == null)
            return;

        if (event.getSlot() != player.getPlayer().getInventory().getHeldItemSlot())
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    // Scroll onto slot
    @org.bukkit.event.EventHandler
    public void onPlayerHoldItemSwitch(org.bukkit.event.player.PlayerItemHeldEvent event) {
        if (event.isCancelled())
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        org.bukkit.inventory.ItemStack toCheck = player.getPlayer().getInventory().getItem(event.getNewSlot());
        if (isAir(toCheck))
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    // Press number key onto slot
    @org.bukkit.event.EventHandler
    public void onPlayerHoldItemKeyPress(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.isCancelled())
            return;

        if (event.getClick() != org.bukkit.event.inventory.ClickType.NUMBER_KEY)
            return;

        // confirm this is the correct item to check
        org.bukkit.inventory.ItemStack toCheck = event.getCurrentItem();
        if (isAir(toCheck))
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
        if (player == null)
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    // Shift click
    @org.bukkit.event.EventHandler
    public void onPlayerHoldItemShiftClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.isCancelled())
            return;

        if (!event.isShiftClick())
            return;

        org.bukkit.inventory.ItemStack toCheck = event.getCurrentItem();
        if (isAir(toCheck))
            return;

        if (event.getSlot() < 9)
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getWhoClicked());
        int heldSlot = player.getPlayer().getInventory().getHeldItemSlot();
        // if the first empty quickbar slot is not the held slot, return.
        if (firstEmptyQuickbarSlot(player.getPlayer()) != heldSlot)
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    // Pickup into slot
    @org.bukkit.event.EventHandler
    public void onPlayerPickupItemHold(org.bukkit.event.player.PlayerPickupItemEvent event) {
        if (event.isCancelled())
            return;

        int firstEmpty = firstEmptyQuickbarSlot(event.getPlayer());
        // If the quickbar is not full (-1) or the first empty slot is not the held slot, return.
        if ((firstEmpty == (-1)) || (firstEmpty != event.getPlayer().getInventory().getHeldItemSlot()))
            return;

        // At this point, the item being picked up SHOULD be going to the held item slot, so lets check it.
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        org.bukkit.inventory.ItemStack toCheck = event.getItem().getItemStack();
        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.HOLD, getItemName(toCheck), java.lang.String.valueOf(toCheck.getDurability()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            player.notifyPlayer(action);
            // Should I also set the pickup time for the item?
        }
    }

    // Handle a player interacting with an object
    @java.lang.SuppressWarnings("deprecation")
    @org.bukkit.event.EventHandler
    public void onPlayerInteractObjectEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.isCancelled())
            return;

        // Shouldn't need to do anything here
        if (event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_AIR)
            return;

        // interacting should only be right click, #amiright?
        if (event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)
            return;

        org.bukkit.block.Block toCheck = event.getClickedBlock();
        if (isAir(toCheck))
            return;

        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        if (player == null)
            return;

        // If the item is not on the intractable item list
        // This is to prevent blanket permissions from stopping blocks being broken
        if (!net.worldoftomorrow.noitem.ItemChecks.isInteractable(toCheck))
            return;

        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.INTERACT_OBJECT, getBlockName(toCheck));
        net.worldoftomorrow.noitem.interfaces.IAction actionWithData = new net.worldoftomorrow.noitem.Action(ActionType.INTERACT_OBJECT, getBlockName(toCheck), java.lang.String.valueOf(toCheck.getData()));
        if ((!player.canDoAction(action)) || (!player.canDoAction(actionWithData))) {
            event.setCancelled(true);
            event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
            event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
            player.notifyPlayer(action);
        }
    }

    // Handle a player interacting with an entity
    @org.bukkit.event.EventHandler
    public void onPlayerInteractEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        org.bukkit.entity.Entity clicked = event.getRightClicked();
        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.INTERACT_ENTITY, getEntityName(clicked));
        if (!player.canDoAction(action)) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerAttackEntity(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        // if(event.getCause() != DamageCause.ENTITY_ATTACK) return;
        org.bukkit.entity.Entity attacker = event.getDamager();
        if (!(attacker instanceof org.bukkit.entity.Player))
            return;

        org.bukkit.entity.Entity attacked = event.getEntity();
        // If it is not a living entity
        // Forget this for now, that way minecarts and such are included
        // if(!entityType.isAlive()) return;
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(((org.bukkit.entity.HumanEntity) (attacker)));
        net.worldoftomorrow.noitem.interfaces.IAction action = new net.worldoftomorrow.noitem.Action(ActionType.ATTACK, getEntityName(attacked));
        if (!player.canDoAction(action)) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerUseEvent(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.isCancelled())
            return;

        org.bukkit.inventory.ItemStack toCheck = event.getPlayer().getItemInHand();
        net.worldoftomorrow.noitem.interfaces.IAction action;
        if ((event.getAction() == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK) && net.worldoftomorrow.noitem.ItemChecks.isUsableTool(toCheck)) {
            org.bukkit.block.Block clicked = event.getClickedBlock();
            // If shears are used on anything but leaves with a left click block, return.
            org.bukkit.Material material = clicked.getType();
            // If it is a shearable item
            if (toCheck.getType().equals(org.bukkit.Material.SHEARS) && (!((((material.equals(org.bukkit.Material.LEAVES) || material.equals(org.bukkit.Material.LEAVES_2)) || material.equals(org.bukkit.Material.DEAD_BUSH)) || material.equals(org.bukkit.Material.LONG_GRASS)) || material.equals(org.bukkit.Material.DOUBLE_PLANT)))) {
                return;
            }
            action = new net.worldoftomorrow.noitem.Action(ActionType.USE, getItemName(toCheck));
            // Use tool or something on block
        } else if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && net.worldoftomorrow.noitem.ItemChecks.isUsableItem(toCheck)) {
            // Shears do nothing when a block is right clicked
            if (toCheck.getType().equals(org.bukkit.Material.SHEARS))
                return;

            // F+S, Spawn Eggs, Water + Lava Bucket
            action = new net.worldoftomorrow.noitem.Action(ActionType.USE, getItemName(toCheck));
        } else if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR) && net.worldoftomorrow.noitem.ItemChecks.isUsableItem(toCheck)) {
            // If they right clicked air with a fishing rod, set that as the action
            // otherwise return, because there is nothing else to check.
            if (toCheck.getType().equals(org.bukkit.Material.FISHING_ROD)) {
                action = new net.worldoftomorrow.noitem.Action(ActionType.USE, getItemName(toCheck));
            } else {
                return;
            }
        } else {
            return;
        }
        net.worldoftomorrow.noitem.interfaces.INoItemPlayer player = getPlayer(event.getPlayer());
        if (player == null)
            return;

        if (!player.canDoAction(action)) {
            event.setCancelled(true);
            player.notifyPlayer(action);
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerUseOnEntityEvent(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        if (event.isCancelled())
            return;

    }

    // Forget this for now, this is no clean way to do it atm.
    /* @EventHandler
    public void onBrewerClick(InventoryClickEvent event) {
    InventoryType invType = event.getInventory().getType();
    if(invType != InventoryType.BREWING) return;
    BrewerInventory brewInv = (BrewerInventory) event.getInventory();
    ItemStack ingredient = brewInv.getIngredient();
    // 0 - 2 look to be items, 3 is ingredient
    // net/minecraft/server/TileEntityBrewingStand.java#L13
    ItemStack[] items = new ItemStack[] {
    brewInv.getItem(0),
    brewInv.getItem(1),
    brewInv.getItem(2)
    };
    }
     */
    // Utility Method
    private java.lang.String getBlockName(org.bukkit.block.Block b) {
        return b.getType().toString().replace("_", "").toLowerCase();
    }

    private java.lang.String getItemName(org.bukkit.inventory.ItemStack stack) {
        return stack.getType().toString().replace("_", "").toLowerCase();
    }

    private java.lang.String getEntityName(org.bukkit.entity.Entity entity) {
        return entity.getType().toString().toLowerCase().replaceAll("_", "");
    }

    private boolean isSlotTopInventory(int rawSlot, org.bukkit.inventory.InventoryView view) {
        return view.convertSlot(rawSlot) == rawSlot;
    }

    private boolean isAir(org.bukkit.inventory.ItemStack stack) {
        if (stack == null)
            return true;

        return stack.getType().equals(org.bukkit.Material.AIR);
    }

    private boolean isAir(org.bukkit.block.Block block) {
        if (block == null)
            return true;

        return block.getType().equals(org.bukkit.Material.AIR);
    }

    // Returns -1 if there is no empty quickbar slot
    private int firstEmptyQuickbarSlot(org.bukkit.entity.Player player) {
        org.bukkit.inventory.ItemStack itemInSlot;
        for (int i = 0; i < 9; i++) {
            itemInSlot = player.getInventory().getItem(i);
            if (isAir(itemInSlot))
                return i;

        }
        return -1;
    }

    public net.worldoftomorrow.noitem.interfaces.INoItemPlayer getPlayer(org.bukkit.entity.HumanEntity player) {
        return players.get(player.getName());
    }
}