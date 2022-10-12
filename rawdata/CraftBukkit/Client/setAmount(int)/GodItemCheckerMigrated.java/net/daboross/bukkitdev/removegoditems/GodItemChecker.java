/* Copyright (C) 2013 daboross

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.removegoditems;
/**
 *
 *
 * @author daboross
 */
public class GodItemChecker {
    private final net.daboross.bukkitdev.removegoditems.RemoveGodItemsPlugin plugin;

    public GodItemChecker(net.daboross.bukkitdev.removegoditems.RemoveGodItemsPlugin plugin) {
        this.plugin = plugin;
    }

    public void removeGodEnchants(org.bukkit.entity.HumanEntity player) {
        org.bukkit.inventory.Inventory inv = player.getInventory();
        org.bukkit.Location loc = player.getLocation();
        java.lang.String name = player.getName();
        for (org.bukkit.inventory.ItemStack it : player.getInventory().getArmorContents()) {
            removeGodEnchants(it, inv, loc, name);
        }
        for (org.bukkit.inventory.ItemStack it : player.getInventory().getContents()) {
            removeGodEnchants(it, inv, loc, name);
        }
    }

    public void removeGodEnchants(org.bukkit.inventory.ItemStack itemStack, org.bukkit.entity.HumanEntity p) {
        removeGodEnchants(itemStack, p.getInventory(), p.getLocation(), p.getName());
    }

    public void removeGodEnchants(org.bukkit.inventory.ItemStack itemStack, org.bukkit.inventory.Inventory inventory, org.bukkit.Location location, java.lang.String name) {
        if ((itemStack != null) && (itemStack.getType() != org.bukkit.Material.AIR)) {
            for (java.util.Map.Entry<org.bukkit.enchantments.Enchantment, java.lang.Integer> entry : itemStack.getEnchantments().entrySet()) {
                org.bukkit.enchantments.Enchantment e = entry.getKey();
                if ((entry.getValue() > e.getMaxLevel()) || (!e.canEnchantItem(itemStack))) {
                    java.lang.String message;
                    if (e.canEnchantItem(itemStack)) {
                        message = java.lang.String.format("Changed level of enchantment %s from %s to %s on item %s in inventory of %s", e.getName(), entry.getValue(), e.getMaxLevel(), itemStack.getType().toString(), name);
                        itemStack.addEnchantment(e, e.getMaxLevel());
                    } else {
                        message = java.lang.String.format("Removed enchantment %s level %s on item %s in inventory of %s", e.getName(), entry.getValue(), itemStack.getType().toString(), name);
                        itemStack.removeEnchantment(e);
                    }
                    plugin.getLogger().log(java.util.logging.Level.INFO, message);
                }
            }
            checkOverstack(itemStack, inventory, location, name);
        }
    }

    public void checkOverstack(org.bukkit.inventory.ItemStack itemStack, org.bukkit.inventory.Inventory inventory, org.bukkit.Location location, java.lang.String name) {
        int amount = itemStack.getAmount();
        int maxAmount = itemStack.getType().getMaxStackSize();
        if (amount > maxAmount) {
            int numStacks = amount / maxAmount;
            plugin.getLogger().log(java.util.logging.Level.INFO, "Unstacked item {0} of size {1} to size {2} with {3} extra stacks in inventory of {4} size", __SmPLUnsupported__(0));
            int left = amount % maxAmount;
            itemStack.getAmount();
            for (int i = 0; i < numStacks; i++) {
                org.bukkit.inventory.ItemStack newStack = itemStack.clone();
                newStack.setAmount(maxAmount);
                int slot = inventory.firstEmpty();
                if (slot < 0) {
                    location.getWorld().dropItemNaturally(location, newStack);
                } else {
                    inventory.setItem(slot, newStack);
                }
            }
        }
    }

    public void runFullCheckNextSecond(org.bukkit.entity.Player p) {
        org.bukkit.Bukkit.getScheduler().runTaskLater(plugin, new net.daboross.bukkitdev.removegoditems.GodItemChecker.GodItemFixRunnable(p), 20);
    }

    public void removeGodEnchantsNextTick(org.bukkit.entity.HumanEntity p, java.lang.Iterable<java.lang.Integer> slots) {
        org.bukkit.Bukkit.getScheduler().runTask(plugin, new net.daboross.bukkitdev.removegoditems.GodItemChecker.VarriedCheckRunnable(p, slots));
    }

    public class GodItemFixRunnable implements java.lang.Runnable {
        private final org.bukkit.entity.HumanEntity p;

        public GodItemFixRunnable(org.bukkit.entity.HumanEntity p) {
            this.p = p;
        }

        @java.lang.Override
        public void run() {
            removeGodEnchants(p);
        }
    }

    public class VarriedCheckRunnable implements java.lang.Runnable {
        private final org.bukkit.entity.HumanEntity p;

        private final java.lang.Iterable<java.lang.Integer> items;

        public VarriedCheckRunnable(org.bukkit.entity.HumanEntity p, java.lang.Iterable<java.lang.Integer> items) {
            this.p = p;
            this.items = items;
        }

        @java.lang.Override
        public void run() {
            org.bukkit.inventory.Inventory inv = p.getInventory();
            org.bukkit.Location loc = p.getLocation();
            java.lang.String name = p.getName();
            for (java.lang.Integer i : items) {
                removeGodEnchants(inv.getItem(i), inv, loc, name);
            }
        }
    }
}