package net.bukkitplugins.ItemXchange.Utils;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UItem
{
	public static ItemStack modifyLore(ItemStack is, int loreLine, String lore)
	{
		if(is == null)
			return null;
		
		ItemMeta im = is.getItemMeta();
		
		List<String> loreList = im.getLore();
		
		loreList.set(loreLine, lore);
		
		im.setLore(loreList);
		
		is.setItemMeta(im);
		
		return is;
	}
	
	public static void repairItem(Player player, Material id, short duribility)
	{
		Inventory inv = player.getInventory();
		
		for(int i = 0; i < inv.getSize(); i++)
		{
			ItemStack is = inv.getItem(i);
			
			if(is == null)
				continue;
			
			if(is.getType() == id)
			{
				is.setDurability(duribility);
				
				inv.setItem(i, is);
			}
		}
	}
	
	public static boolean hasCustomItem(Player player, String itemName, boolean inHand)
	{
		Inventory inv = player.getInventory();
		
		if(inHand)
		{
			ItemStack is = player.getItemInHand();
			
			return hasCustomItem(is, itemName);
		}
		
		boolean isValid = false;
		for(int i = 0; i < inv.getSize(); i++)
		{
			ItemStack is = inv.getItem(i);
			
			if(is == null)
				continue;
			
			ItemMeta im = is.getItemMeta();
			
			if(im.getDisplayName() == itemName)
				isValid = true;
		}
		
		return isValid;
	}
	
	public static boolean hasCustomItem(ItemStack is, String itemName)
	{
		if(is == null)
			return false;
		
		if(!is.hasItemMeta())
			return false;
		
		if(!is.getItemMeta().hasDisplayName())
			return false;
		
		return is.getItemMeta().getDisplayName().equals(itemName);
	}
	
	@SuppressWarnings("incomplete-switch")
	public static ItemStack smeltable(ItemStack is)
	{
		Random rand = new Random();
		
		switch(is.getType())
		{
		case COAL_ORE:
			return new ItemStack(Material.COAL, 1);
		case IRON_ORE:
			return new ItemStack(Material.IRON_INGOT, 1);
		case GOLD_ORE:
			return new ItemStack(Material.GOLD_INGOT, 1);
		case LAPIS_ORE:
			return new ItemStack(Material.INK_SACK, rand.nextInt(4) + 1, (short)4);
		case EMERALD_ORE:
			return new ItemStack(Material.EMERALD, 1);
		case REDSTONE_ORE:
			return new ItemStack(Material.REDSTONE, rand.nextInt(4) + 1);
		}
		
		return null;
	}
	
	//Custom Items
	//Cloud In A Bottle
	public static boolean isValidCloudInABottle(ItemStack is)
	{
		if(is == null)
			return false;
		
		return is.getType() == Material.GLASS_BOTTLE && is.getDurability() == (short)1;
	}
	
	public static boolean isCloudInABottleEnabled(ItemStack is)
	{
		return is.containsEnchantment(Enchantment.PROTECTION_FALL);
	}
	
	
	public static boolean isValidFlowerOfTheEarth(ItemStack is)
	{
		if(is == null)
			return false;
		
		return is.getType() == Material.RED_ROSE;
	}
	
	public static boolean isFlowerOfTheEarthEnabled(ItemStack is)
	{
		if(is == null)
			return false;
		
		if(!is.hasItemMeta())
			return false;
		
		if(!is.getItemMeta().hasLore())
			return false;
		
		String[] lore = is.getItemMeta().getLore().get(1).split(": ");
		
		return (ChatColor.stripColor(lore[1]).equals("Enabled"));
	}
}