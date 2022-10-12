package me.botsko.prismapidemo.manual;

import me.botsko.prism.actionlibs.RecordingQueue;
import me.botsko.prism.actions.ItemStackAction;
import me.botsko.prismapidemo.PrismApiDemo;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomManualActionExample {
	
	
	/**
	 * Sometimes you need to manually build an event based off of an existing
	 * Prism action handler. For example, if you're intercepting events and 
	 * need to handle adding an action to the queue, you can build it manually.
	 * 
	 * There are two ways to do this. Both are shown below.
	 * 
	 * @param plugin
	 * @param player
	 */
	public static void trigger( PrismApiDemo plugin, Player player ){
		
		if (plugin.getPrism() != null) {

			
			/**
			 * Here we'll generate a fake "item-insert" action using a very short "factory" system
			 * that lets us pass in some args in a single line.
			 * 
			 * It's not recommended because we *may* change this API, but you may manually add actions to the queue. 
			 * 
			 * By using one of the available create methods in ActionFactory you can quickly create an instance of
			 * an action handler and pass it to the queue.
			 * 
			 */
//			Handler a = ActionFactory.createItemStack( "item-drop", new ItemStack(1), null, player.getLocation(), player.getName() );
//			RecordingQueue.addToQueue(a);
			
			
			/**
			 * The more verbose, but safer way to manually build actions is to build the handler yourself. For the item
			 * stack action, the following settings are all required.
			 */
			ItemStackAction a = new ItemStackAction();
			
			// Always required no matter the action type
			a.setLoc( player.getLocation() );
			a.setActionType("item-drop");
			a.setPlayerName(player.getName());
			
			// Required for the ItemStackAction
			a.setItem(new ItemStack(Material.STONE), 1, 0, null);
			
			// Add the recorder queue
			RecordingQueue.addToQueue(a);

	        player.sendMessage("Event triggered. Try /demo lookup");
		}
	}
}