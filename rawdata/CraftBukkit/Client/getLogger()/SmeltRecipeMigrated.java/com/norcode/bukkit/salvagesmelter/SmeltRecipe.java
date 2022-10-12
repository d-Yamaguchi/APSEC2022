package com.norcode.bukkit.salvagesmelter;
public class SmeltRecipe {
    private org.bukkit.Material smeltable;

    private org.bukkit.inventory.ItemStack result;

    private org.bukkit.inventory.FurnaceRecipe recipe;

    public SmeltRecipe(org.bukkit.Material smeltable, org.bukkit.inventory.ItemStack result) {
        this.smeltable = smeltable;
        this.result = result;
        this.recipe = new org.bukkit.inventory.FurnaceRecipe(this.result.clone(), smeltable);
    }

    public org.bukkit.inventory.ItemStack getResult() {
        return result;
    }

    public org.bukkit.Material getSmeltable() {
        return smeltable;
    }

    public org.bukkit.inventory.FurnaceRecipe getFurnaceRecipe() {
        return this.recipe;
    }

    public void installFurnaceRecipe(org.bukkit.plugin.java.JavaPlugin plugin) {
        plugin.getServer().info("Installing furnace recipe: " + this.recipe);
        plugin.getServer().addRecipe(this.recipe);
    }
}