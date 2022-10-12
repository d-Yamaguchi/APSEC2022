@org.bukkit.event.EventHandler
public void onClick(org.bukkit.event.player.PlayerInteractEvent event) {
    org.bukkit.event.player.PlayerInteractEvent _CVAR0 = event;
    org.bukkit.entity.Player player = _CVAR0.getPlayer();
    org.bukkit.entity.Player _CVAR1 = player;
    org.bukkit.inventory.ItemStack itemInHand = _CVAR1.getItemInHand();
    org.bukkit.inventory.ItemStack _CVAR2 = itemInHand;
    org.bukkit.inventory.meta.ItemMeta inHandMeta = _CVAR2.getItemMeta();
    org.bukkit.inventory.meta.ItemMeta _CVAR3 = inHandMeta;
    org.bukkit.ChatColor _CVAR5 = org.bukkit.ChatColor.RESET;
    java.lang.String _CVAR4 = _CVAR3.getDisplayName();
     _CVAR6 = (org.bukkit.ChatColor.WHITE + Settings.MailItemName) + _CVAR5;
    boolean _CVAR7 = _CVAR4.equals(_CVAR6);
     _CVAR8 = ((((itemInHand != null) && (inHandMeta != null)) && (itemInHand.getType() == org.bukkit.Material.getMaterial(Settings.MailItemID))) && (itemInHand.getDurability() == Settings.MailItemDV)) && _CVAR7;
     _CVAR9 = _CVAR8 && player.hasPermission(Permissions.SEND_ITEM_PERM);
    if () {
        new com.github.derwisch.paperMail.PaperMailGUI(player, true).Show();
    }
    org.bukkit.block.Block clickedBlock = event.getClickedBlock();
    if ((clickedBlock != null) && (clickedBlock.getState() instanceof org.bukkit.block.Sign)) {
        org.bukkit.block.Sign s = ((org.bukkit.block.Sign) (event.getClickedBlock().getState()));
        if (s.getLine(1).toLowerCase().contains("[mailbox]")) {
            if (event.getAction().equals(org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                new com.github.derwisch.paperMail.PaperMailGUI(player).Show();
            } else if (event.getAction().equals(org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
                com.github.derwisch.paperMail.Inbox inbox = null;
                try {
                    inbox = com.github.derwisch.paperMail.Inbox.GetInbox(player.getName());
                } catch (java.io.IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (org.bukkit.configuration.InvalidConfigurationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                inbox.openInbox();
                event.setCancelled(true);
            }
        }
    }
}