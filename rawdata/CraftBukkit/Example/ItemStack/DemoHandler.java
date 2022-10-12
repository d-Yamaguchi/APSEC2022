package me.botsko.prismapidemo.customevent;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.botsko.prism.actionlibs.QueryParameters;
import me.botsko.prism.actions.GenericAction;
import me.botsko.prism.appliers.ChangeResult;
import me.botsko.prism.appliers.ChangeResultType;

public class DemoHandler extends GenericAction {
	
	
	/**
	 * 
	 */
	public String getNiceName(){
		return "my custom handler";
	}
	
	
	/**
	 * When Prism knows you've registered a custom handler, not only can you control the
	 * displayed information, but you can also provide custom logic for how to rollback
	 * or restore the actions.
	 * 
	 * Prism gives you the player doing the rollback/restore, the query parameters
	 * used, and whether or not it's a preview.
	 * 
	 * The handler will already have information about the specific event. For example,
	 * block/item events would have getBlockId(), getBlockSubId ready for you to use.
	 * 
	 * Take a look at the GenericAction api docs for all of the information available.
	 * 
	 * You must then return a change result with whether it was APPLIED, SKIPPED, or DEFERRED
	 *
	 */
	@Override
	public ChangeResult applyRollback( Player player, QueryParameters parameters, boolean is_preview ){
		// This message is here for demo only. You should rely on the applier callback to send messages.
		player.sendMessage("Custom rollback handler drops a stick at your feet. ;) ");
		// We'll pretend "dropping a stick at your feet" is our custom rollback.
		player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.STICK, 1));
		return new ChangeResult( ChangeResultType.APPLIED );
	}
	
	
	/**
	 * 
	 */
	@Override
	public ChangeResult applyRestore( Player player, QueryParameters parameters, boolean is_preview ){
		return null;
	}
}
