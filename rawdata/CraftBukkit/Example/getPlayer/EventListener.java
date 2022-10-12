package nl.lolmewn.sortal;

import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

/**
 *
 * @author Sybren
 */
public class EventListener implements Listener {

    private Main plugin;

    private Main getPlugin() {
        return this.plugin;
    }

    private Localisation getLocalisation() {
        return this.getPlugin().getSettings().getLocalisation();
    }

    public EventListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        for (int i = 0; i < event.getLines().length; i++) {
            if (this.getPlugin().getSettings().isDebug()) {
                this.getPlugin().getLogger().log(Level.INFO, String.format("[Debug] Checking line "
                        + "%s of the sign", i));
            }
            if (event.getLine(i).toLowerCase().contains("[sortal]")
                    || event.getLine(i).toLowerCase().contains(this.getPlugin().getSettings().getSignContains())) {
                if (!event.getPlayer().hasPermission("sortal.placesign")) {
                    event.getPlayer().sendMessage(this.getLocalisation().getNoPerms());
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerHitSign(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Block b = event.getClickedBlock();
            Player p = event.getPlayer();
            if (b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST)
                    || b.getType().equals(Material.WALL_SIGN)) {
                //It's a sign
                Sign s = (Sign) b.getState();
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    if (sortalSign(s, p)) {
                        event.setCancelled(true);
                    }
                    return;
                }
                int found = -1;
                for (int i = 0; i < s.getLines().length; i++) {
                    if (s.getLine(i).toLowerCase().contains("[sortal]")
                            || s.getLine(i).toLowerCase().contains(this.getPlugin().getSettings().getSignContains())) {
                        //It's a sortal sign
                        found = i;
                        break;
                    }
                }
                if (found == -1) {
                    if (!this.getPlugin().getWarpManager().hasSignInfo(s.getLocation())) {
                        return; //nvm it's not a sortal sign of any kind.
                    }
                    if (!p.hasPermission("sortal.warp")) {
                        p.sendMessage(this.getLocalisation().getNoPerms());
                        event.setCancelled(true);
                        return;
                    }
                    SignInfo sign = this.getPlugin().getWarpManager().getSign(s.getLocation());
                    if (sign.hasWarp()) {
                        if(this.getPlugin().getSettings().isPerWarpPerm()){
                            if(!p.hasPermission("sortal.warp." + sign.getWarp())){
                                p.sendMessage(this.getLocalisation().getNoPerms());
                                event.setCancelled(true);
                                return;
                            }
                        }
                        Warp w = this.getPlugin().getWarpManager().getWarp(sign.getWarp());
                        if(sign.hasPrice()){
                            if (!this.getPlugin().pay(p, sign.getPrice())) {
                                event.setCancelled(true);
                                return;
                            }
                        }else if(w.hasPrice()){
                            if(!this.getPlugin().pay(p, this.getPlugin().getWarpManager().getWarp(sign.getWarp()).getPrice())){
                                event.setCancelled(true);
                                return;
                            }
                        }else{
                            if(!this.getPlugin().pay(p, this.getPlugin().getSettings().getWarpUsePrice())){
                                event.setCancelled(true);
                                return;
                            }
                        }
                        Location loc = w.getLocation();
                        if(loc.getYaw() == 0 && loc.getPitch()==0){
                            loc.setYaw(p.getLocation().getYaw());
                            loc.setPitch(p.getLocation().getPitch());
                        }
                        p.teleport(w.getLocation(), TeleportCause.PLUGIN);
                        p.sendMessage(this.getLocalisation().getPlayerTeleported(w.getName()));
                        event.setCancelled(true); //Cancel, don't place block.
                        return;
                    }
                    p.sendMessage(this.getLocalisation().getErrorInSign()); //Sign does have a price but no warp -> weird.
                    event.setCancelled(true);
                    return; //have to return, otherwise it'll check the next lines
                }
                if (!p.hasPermission("sortal.warp")) {
                    p.sendMessage(this.getLocalisation().getNoPerms());
                    event.setCancelled(true);
                    return;
                }
                String nextLine = s.getLine(found + 1);
                if (nextLine == null || nextLine.equals("")) {
                    //Well, that didn't really work out well..
                    p.sendMessage(this.getLocalisation().getErrorInSign());
                    event.setCancelled(true);
                    return;
                }
                if (nextLine.contains("w:")) {
                    //It's a warp
                    String warp = nextLine.split(":")[1];
                    if(this.getPlugin().getSettings().isPerWarpPerm()){
                            if(!p.hasPermission("sortal.warp." + warp)){
                                p.sendMessage(this.getLocalisation().getNoPerms());
                                event.setCancelled(true);
                                return;
                            }
                        }
                    if (!this.getPlugin().getWarpManager().hasWarp(warp)) {
                        p.sendMessage(this.getLocalisation().getWarpNotFound(warp));
                        event.setCancelled(true);
                        return;
                    }
                    Warp w = this.getPlugin().getWarpManager().getWarp(warp);
                    int price = w.getPrice();
                    if(this.getPlugin().getWarpManager().hasSignInfo(s.getLocation())){
                        price = this.getPlugin().getWarpManager().getSign(s.getLocation()).getPrice();
                    }
                    if (!this.getPlugin().pay(p, price)) {
                        event.setCancelled(true);
                        return;
                    }
                    p.teleport(w.getLocation(), TeleportCause.PLUGIN);
                    p.sendMessage(this.getLocalisation().getPlayerTeleported(warp));
                    event.setCancelled(true);
                    return;
                }
                if (nextLine.contains(",")) {
                    String[] split = nextLine.split(",");
                    World w;
                    int add = 0;
                    if (split.length == 3) {
                        w = p.getWorld();
                    } else {
                        w = this.getPlugin().getServer().getWorld(split[0]);
                        if (w == null) {
                            p.sendMessage(this.getLocalisation().getErrorInSign());
                            event.setCancelled(true);
                            return;
                        }
                        add = 1;
                    }
                    int price = this.getPlugin().getSettings().getWarpUsePrice();
                    if(this.getPlugin().getWarpManager().hasSignInfo(s.getLocation())){
                        price = this.getPlugin().getWarpManager().getSign(s.getLocation()).getPrice();
                    }
                    if(!this.getPlugin().pay(p, price)){
                        event.setCancelled(true);
                        return;
                    }
                    int x = Integer.parseInt(split[0 + add]), y = Integer.parseInt(split[1 + add]),
                            z = Integer.parseInt(split[2 + add]);
                    Location dest = new Location(w, x, y, z, p.getLocation().getYaw(), p.getLocation().getPitch());
                    p.teleport(dest, TeleportCause.PLUGIN);
                    p.sendMessage(this.getLocalisation().getPlayerTeleported(
                            dest.getBlockX() + ", " + dest.getBlockY() + ", " + dest.getBlockZ()));
                    event.setCancelled(true);
                    return;
                }
                p.sendMessage(this.getLocalisation().getErrorInSign());
                event.setCancelled(true);
            }
        }
    }

    private boolean sortalSign(Sign s, Player player) {
        //checks whether
        if (this.getPlugin().setcost.containsKey(player.getName())) {
            if (this.getPlugin().getWarpManager().hasSignInfo(s.getLocation())) {
                //It's a registered sign
                SignInfo sign = this.getPlugin().getWarpManager().getSign(s.getLocation());
                sign.setPrice(this.getPlugin().setcost.remove(player.getName()));
                player.sendMessage("Price set to " + sign.getPrice() + " for this sign!");
                return true;
            }
            for(String line : s.getLines()){
                if(line.toLowerCase().contains("[sortal]") || line.contains(this.getPlugin().getSettings().getSignContains())){
                    this.getPlugin().getWarpManager().addSign(s.getLocation()).setPrice(this.getPlugin().setcost.remove(player.getName()));
                    player.sendMessage("Price set to " + this.getPlugin().getWarpManager().getSign(s.getLocation()).getPrice() + " for this sign!");
                    return true;
                }
            }
            player.sendMessage("This is not a valid sortal sign!");
            return true;
        }
        if (this.getPlugin().register.containsKey(player.getName())) {
            if (this.getPlugin().getWarpManager().hasSignInfo(s.getLocation())) {
                //It's a registered sign
                SignInfo sign = this.getPlugin().getWarpManager().getSign(s.getLocation());
                sign.setWarp(this.getPlugin().register.remove(player.getName()));
                player.sendMessage("Sign is now pointing to " + sign.getWarp());
                return true;
            }
            SignInfo sign = this.getPlugin().getWarpManager().addSign(s.getLocation());
            String warp = this.getPlugin().register.remove(player.getName());
            sign.setWarp(warp);
            player.sendMessage("Sign is now pointing to " + warp);
            return true;
        }
        if (this.getPlugin().unregister.contains(player.getName())){
            if (!this.getPlugin().getWarpManager().hasSignInfo(s.getLocation())){
                player.sendMessage("This sign isn't registered, please hit a registered sign to unregister!");
                return true;
            }
            SignInfo sign = this.getPlugin().getWarpManager().getSign(s.getLocation());
            if(!sign.hasWarp()){
                player.sendMessage("This sign isn't pointing to a warp!");
                return true;
            }
            if(!sign.hasPrice()){
                //Sign doesn't have a price and warp gets removed, remove whole sign info
                if(this.getPlugin().getSettings().useMySQL()){
                    sign.delete(this.getPlugin().getMySQL(), this.getPlugin().getSignTable());
                }else{
                    sign.delete(this.getPlugin().getWarpManager().signFile);
                }
                player.sendMessage("Unregistered sign! No data left for sign, removing..");
                this.getPlugin().unregister.remove(player.getName());
                return true;
            }
            sign.setWarp(null);
            this.getPlugin().unregister.remove(player.getName());
            player.sendMessage("Sign unregistered!");
            return true;
        }
        if(this.getPlugin().setuses.containsKey(player.getName())){
            
        }
        return false;
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block b = event.getBlock();
        if(b.getType().equals(Material.SIGN) || b.getType().equals(Material.SIGN_POST)
                || b.getType().equals(Material.WALL_SIGN)){
            Sign s = (Sign)b.getState();
            for(String line : s.getLines()){
                if(line.toLowerCase().contains("[sortal]") || line.toLowerCase().contains(this.getPlugin().getSettings().getSignContains())){
                    if(!event.getPlayer().hasPermission("sortal.breaksign")){
                        event.getPlayer().sendMessage(this.getLocalisation().getNoPerms());
                        event.setCancelled(true);
                        return;
                    }
                }
            }
            //no [Sortal] or whatever on sign, maybe registered?
            if(this.getPlugin().getWarpManager().hasSignInfo(b.getLocation())){
                if(!event.getPlayer().hasPermission("sortal.breaksign")){
                    event.getPlayer().sendMessage(this.getLocalisation().getNoPerms());
                    event.setCancelled(true);
                }
            }
        }
    }
}
