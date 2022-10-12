package com.hackhalo2.creative;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class PixlPlayer extends PlayerListener {
    public class helper {
	private final int[] supported = new int[] { 4, 6, 17, 18, 19, 31, 33, 35, 43, 44, 48, 53, 67, 85, 98, 108, 109, 114}; //Supported Block ID's
	public helper() { }

	public boolean Block(Block b) {
	    for (int i=0; i < supported.length; i++) {
		if(ID(b) == supported[i]) { return true; }
	    }
	    return false;
	}

	public Material Type(Block b) { return b.getType(); }

	public int ID(Block b) { return b.getTypeId(); }
    }

    private final Pixl plugin;
    private final helper a = new helper();

    public PixlPlayer(Pixl p) { this.plugin = p; }

    @Override
    public void onPlayerInteract(PlayerInteractEvent e) {
	if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    if(e.getPlayer().getItemInHand().getType() == Material.AIR) { //make sure the item in hand is air
		if(plugin.checkPermissions(e.getPlayer(), "pixl.toggle", false) && plugin.isToggled(e.getPlayer()) && a.Block(e.getClickedBlock())) {
		    pixlArt(e.getClickedBlock(), e.getPlayer());
		}
	    }
	}
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent e) {
	//TODO load user settings upon login
	//TODO message user that Pixl is enabled/disabled on login
	if(plugin.isToggled(e.getPlayer())) { plugin.setToggle(e.getPlayer(), false); }
	if(plugin.breakMode(e.getPlayer())) { plugin.setBreak(e.getPlayer(), false); }
    }

    public void pixlArt(Block b, Player p) {
	//Very hackish detection
	BlockBreakEvent event1 = new BlockBreakEvent(b, p);
	plugin.getServer().getPluginManager().callEvent(event1);
	if(event1.isCancelled()) {
	    return;
	} else {
	    //switch statement later on?
	    Block previousBlock = b;
	    if(a.Type(b) == Material.LOG || a.Type(b) == Material.LEAVES) {
		if(b.getData() < (byte)(2)) { b.setData((byte)(b.getData()+1)); } //add one
		else { b.setData((byte)(0)); } //reset it
	    } else if(a.Type(b) == Material.WOOL) {
		if(plugin.isSet(p) == null) {
		    if(b.getData() < (byte)(15)) { b.setData((byte)(b.getData()+1)); } //add one
		    else { b.setData((byte)(0)); } //reset it
		} else { b.setData(plugin.isSet(p).byteValue()); } //should set the byte value...
	    } else if(a.Type(b) == Material.STEP || a.Type(b) == Material.DOUBLE_STEP) {
		if(b.getData() < (byte)(5)) { b.setData((byte)(b.getData()+1)); } //add one
		else { b.setData((byte)(0)); } //reset it
	    } else if(a.Type(b) == Material.WOOD_STAIRS || a.Type(b) == Material.COBBLESTONE_STAIRS ||
		    a.Type(b) == Material.BRICK_STAIRS || a.Type(b) == Material.SMOOTH_STAIRS || a.ID(b) == 114) {
		if(b.getData() == (byte)(0)) { //Ascending south
		    b.setData((byte)(2)); //Ascending west
		} else if(b.getData() == (byte)(1)) { //Ascending north
		    b.setData((byte)(3)); //Ascending east
		} else if(b.getData() == (byte)(2)) { //Ascending west
		    b.setData((byte)(1)); //Ascending north
		} else if(b.getData() == (byte)(3)) { //Ascending east
		    b.setData((byte)(0)); //Ascending south
		}
	    } else if(a.Type(b) == Material.SMOOTH_BRICK) {
		if(b.getData() < (byte)(2)) { b.setData((byte)(b.getData()+1)); } //add one
		else { b.setData((byte)(0)); } //reset it
	    } else if(a.Type(b) == Material.SPONGE || a.Type(b) == Material.FENCE) { //Turn Sponges into fences and back again!
		if(a.Type(b) == Material.SPONGE) { b.setType(Material.FENCE); }
		else if(a.Type(b) == Material.FENCE) { b.setType(Material.SPONGE); }
	    } else if(a.Type(b) == Material.COBBLESTONE || a.Type(b) == Material.MOSSY_COBBLESTONE) { //Turn Cobble into mossy and back again!
		if(a.Type(b) == Material.COBBLESTONE) { b.setType(Material.MOSSY_COBBLESTONE); }
		else if(a.Type(b) == Material.MOSSY_COBBLESTONE) { b.setType(Material.COBBLESTONE); }
	    } else if(a.Type(b) == Material.LONG_GRASS || a.Type(b) == Material.SAPLING) {
		if(b.getData() < (byte)(2)) { b.setData((byte)(b.getData()+1)); } //add one
		else { b.setData((byte)(0)); } //reset it
	    }
	    plugin.logBlockPlace(b, previousBlock.getState(), previousBlock, p.getItemInHand(), p, true);
	}
    }
}
