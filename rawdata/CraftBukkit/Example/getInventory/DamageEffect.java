package com.github.customentitylibrary.entities;

import com.github.customentitylibrary.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DamageEffect
{
	public static final String POTION = "potion";
	public static final String FIRE = "fire";
	public static final String KNOCKBACK = "knockback";
	public static final String FORCE_EQUIP = "forceequip";
	public static final String TELEPORT = "teleport";
	public static final String TELEPORT_RELATIVE = "teleportrelative";
	public static final String DAMAGE_HUNGER = "damagehunger";
	public static final String COMMAND = "command";
	
	private String name;
	private String[] args;
		
	public DamageEffect(String name, String[] args)
	{
		this.name = name.toLowerCase();
		this.args = args;
	}
	
	public void dealEffect(LivingEntity target, LivingEntity source)
	{
		Location sourceLoc = source.getLocation();
		Location targetLoc = target.getLocation();
		if(name.equals(POTION))
		{
			PotionEffectType potionType = PotionEffectType.getByName(args[0].toUpperCase().replaceAll(" ", "_"));
			if(potionType != null)
				target.addPotionEffect(new PotionEffect(potionType, Utils.parseInt(args[1], 100), Utils.parseInt(args[2], 1)));
		} else if(name.equals(FIRE))
		{
			target.setFireTicks(Utils.parseInt(args[0], 100));
		} else if(name.equals(KNOCKBACK))
		{
			Utils.knockBack(target, sourceLoc, Utils.parseDouble(args[0], 1.0), Utils.parseDouble(args[1], 1.0));
		} else if(name.equals(FORCE_EQUIP))
		{
			ItemStack item = new ItemStack(Material.getMaterial(args[0]));
			if(item != null)
			{
				switch(Utils.parseInt(args[1], 0))
				{
				case 0:
					target.getEquipment().setBoots(item);
					break;
				case 1:
					target.getEquipment().setLeggings(item);
					break;
				case 2:
					target.getEquipment().setChestplate(item);
					break;
				case 3:
					target.getEquipment().setHelmet(item);
					break;
				default:
					target.getEquipment().setBoots(item);
				}
			}
		} else if(name.equals(TELEPORT))
		{
			World world = Bukkit.getWorld(args[0]);
			if(world == null)
				world = target.getWorld();
			target.teleport(new Location(world, Utils.parseDouble(args[1], targetLoc.getX()), Utils.parseDouble(args[2], targetLoc.getY()), 
					Utils.parseDouble(args[3], targetLoc.getZ())));
		} else if(name.equals(TELEPORT_RELATIVE))
		{
			double prevX = targetLoc.getX();
			double prevY = targetLoc.getY();
			double prevZ = targetLoc.getZ();
			target.teleport(new Location(targetLoc.getWorld(), prevX + Utils.parseDouble(args[0], 0), prevY + Utils.parseDouble(args[1], 0), 
					prevZ + Utils.parseDouble(args[2], 0)));
		} else if(name.equals(DAMAGE_HUNGER))
		{
			if(!(target instanceof Player))
				return;
			Player player = (Player) target;
			player.setFoodLevel(player.getFoodLevel() - Utils.parseInt(args[0], 1));
			player.setSaturation(player.getSaturation() - Utils.parseInt(args[0], 1));
		} else if(name.equals(COMMAND))
		{
			if(target instanceof Player)
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args.toString().replaceAll("[|]", "").replaceAll(",", "")
						.replaceAll("%PLAYER%", ((Player) target).getName()));
			else
				Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args.toString().replaceAll("[|]", "").replaceAll(",", ""));
		}
	}
}
