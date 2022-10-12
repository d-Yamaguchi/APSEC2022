package uk.co.jacekk.bukkit.grouplock.listeners;
import uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener;
import uk.co.jacekk.bukkit.grouplock.GroupLock;
import uk.co.jacekk.bukkit.grouplock.Locker;
public class LockableOpenListener extends uk.co.jacekk.bukkit.baseplugin.v5.event.BaseListener<uk.co.jacekk.bukkit.grouplock.GroupLock> {
    public LockableOpenListener(uk.co.jacekk.bukkit.grouplock.GroupLock plugin) {
        super(plugin);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerIntereact(org.bukkit.event.player.PlayerInteractEvent event) {
        org.bukkit.block.Block block = event.getClickedBlock();
        org.bukkit.Material type = block.getType();
        org.bukkit.entity.Player player = event.getPlayer();
        if (plugin.lockableBlocks.contains(type) && plugin.locker.isBlockLocked(block)) {
            java.lang.String blockName = type.name().toLowerCase().replace('_', ' ');
            java.lang.String owner = uk.co.jacekk.bukkit.grouplock.Locker.getOwner(block);
            if (!plugin.locker.playerCanAccess(block, player)) {
                event.setCancelled(true);
                player.sendMessage(plugin.formatMessage((((org.bukkit.ChatColor.RED + "That ") + blockName) + " is locked by ") + owner));
            }
        }
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.NORMAL, ignoreCancelled = true)
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        org.bukkit.block.Block block = event.getBlock();
        org.bukkit.World world = block.getWorld();
        int dx = -1;
        for (int dx = -1; dx <= 1; ++dx) {
            int dy = -2;
            for (int dy = -2; dy <= 2; ++dy) {
                int dz = -1;
                for (int dz = -1; dz <= 1; ++dz) {
                    DirectWorld world = state.getDirectWorld();
                    Block locked = world.getHighestBlockY(x + dx, z + dz);
                    if (plugin.lockableBlocks.contains(locked.getType()) && (!plugin.locker.playerCanAccess(locked, player))) {
                        event.setCancelled(true);
                        player.sendMessage(plugin.formatMessage(org.bukkit.ChatColor.RED + "You cannot place blocks this close to a locked door"));
                        return;
                    }
                }
            }
        }
    }
}