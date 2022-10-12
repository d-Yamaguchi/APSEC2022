package net.loadingchunks.plugins.AuthenticItem.AuthenticItem;

import net.minecraft.server.v1_4_6.NBTTagCompound;
import org.bukkit.craftbukkit.v1_4_6.inventory.CraftItemStack;

public class AItem {
	
	CraftItemStack itemstack;
	AuthenticItem plugin;

	public AItem(CraftItemStack item)
	{
		NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
		
		if(tag == null)
			CraftItemStack.asNMSCopy(item).setTag(new NBTTagCompound());
		
		this.itemstack = item;
	}
	
	public void setPlugin(AuthenticItem plugin)
	{
		this.plugin = plugin;
	}

	public String getAuthentic()
	{
		if(CraftItemStack.asNMSCopy(this.itemstack).getTag().hasKey("authentic_type"))
			return CraftItemStack.asNMSCopy(this.itemstack).getTag().getString("authentic_type");
		else
			return null;
	}
	
	public void setAuthentic(String type)
	{
		CraftItemStack.asNMSCopy(this.itemstack).getTag().setString("authentic_type", type);
		CraftItemStack.asNMSCopy(this.itemstack).getTag().setInt("RepairCost", 999);
	}
	
	public net.minecraft.server.v1_4_6.ItemStack getStack()
	{
		return CraftItemStack.asNMSCopy(this.itemstack);
	}
	
	private NBTTagCompound getDisplay()
	{
		return CraftItemStack.asNMSCopy(this.itemstack).getTag().getCompound("display");
	}
	
	public String getDisplayName()
	{
		if(getDisplay() == null)
		{
			return null;
		}
		String name = getDisplay().getString("Name");
		
		if(name.equals(""))
		{
			return null;
		}
		else
			return name;
	}
	
	public void setDisplayName(String name)
	{
		if(getDisplay() == null)
		{
			CraftItemStack.asNMSCopy(this.itemstack).getTag().setCompound("display", new NBTTagCompound());
		}
		
		NBTTagCompound display = getDisplay();

		display.setString("Name", name);
	}
}
