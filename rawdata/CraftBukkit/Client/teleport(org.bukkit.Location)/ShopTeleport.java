package no.nytt1.shopteleport;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopTeleport extends JavaPlugin implements Listener {
	public final Logger logger = Logger.getLogger("Minecraft");
	public static ShopTeleport plugin;

	private List<Player> cantDoCommand = new ArrayList<Player>();

	Countdown d = new Countdown();

	protected UpdateChecker updateChecker;
	
	

	@Override
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		this.logger.info(pdfFile.getName() + " Has Been Disabled!");
	}

	@Override
	public void onEnable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		getServer().getPluginManager().registerEvents(this, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		if(getConfig().getString("config.check-for-update").equalsIgnoreCase("true")) {
			this.logger.info("We are now checking for a new version!");
		this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/bukkit-plugins/shopteleport/files.rss");
		this.updateChecker.updateNeeded();
		}
		if(getConfig().getString("config.check-for-update").equalsIgnoreCase("true")) {
			if (this.updateChecker.updateNeeded()){
				this.logger.info("Newest version available: " + this.updateChecker.getVersion());
				this.logger.info("Download it Here: " + this.updateChecker.getLink());
			} else {
				this.logger.info("Nope, no new version here!");
			}
			}
		
		 //Register Commands
		//this.getCommand("setshop").setExecutor(new Setshop());
		this.getCommand("setshop").setExecutor(new no.nytt1.shopteleport.commands.Setshop(this));
		
		this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " Has Been Enabled!");
		
	}


	@EventHandler
    public void onplayerjoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
        if(player.hasPermission("shopteleport.admin") || player.isOp()) {
        	if(getConfig().getString("config.check-for-update").equalsIgnoreCase("true")) {
    			if (this.updateChecker.updateNeeded()){
    				player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "ShopTeleport" + ChatColor.WHITE + "] " + ChatColor.GREEN + "A New Version is Available!");
    				player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "ShopTeleport" + ChatColor.WHITE + "] " + ChatColor.GREEN + "Get Version " + this.updateChecker.getVersion() + " Here: " + this.updateChecker.getLink());
    			}
    			}
        }
    }

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String NoPermission = Messages.colours(getConfig().getString("messages.no-permission"));
		String TpedToYourShop = Messages.colours(getConfig().getString("messages.tped-to-your-shop"));
		String Delay = Messages.colours(getConfig().getString("messages.delay"));
		String Cooldown = Messages.colours(getConfig().getString("messages.cooldown"));
		String NoShop = Messages.colours(getConfig().getString("messages.no-shop"));
		String NoTp = Messages.colours(getConfig().getString("messages.no-tp"));
		String Tped = Messages.colours(getConfig().getString("messages.tped"));
		String DoesntExsist = Messages.colours(getConfig().getString("messages.doesnt-exsist"));
		String ShopDeleted = Messages.colours(getConfig().getString("messages.shop-deleted"));
		String NoShopDeleted = Messages.colours(getConfig().getString("messages.no-shop-deleted"));
		
		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("shop")){
			if (args.length == 0) {
				String playername1 = player.getName().toLowerCase();
				if (StringUtils.isNotBlank(getConfig().getString("shops." + playername1))) {
					if(player.hasPermission("shopteleport.admin") || player.hasPermission("shopteleport.nolimit")) {
						int x = getConfig().getInt("shops." + playername1 + ".x");
				        int y = getConfig().getInt("shops." + playername1 + ".y");
				        int z = getConfig().getInt("shops." + playername1 + ".z");
				        int yaw = getConfig().getInt("shops." + playername1 + ".yaw");
				        int pitch = getConfig().getInt("shops." + playername1 + ".pitch");
				        Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
				        loc.setPitch(pitch);
				        loc.setYaw(yaw);
				        player.teleport(loc);
							player.sendMessage(TpedToYourShop);
						return true;
					}else{
					if (getConfig().getBoolean("config.delay.self")==true) {
						if (!cantDoCommand.contains(player)) {
							try {
								int delaytime = getConfig().getInt("config.delay.time");
								int delaytime1 = delaytime * 1000;
								player.sendMessage(Delay);
								Thread.sleep(delaytime1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							int x = getConfig().getInt("shops." + playername1 + ".x");
					        int y = getConfig().getInt("shops." + playername1 + ".y");
					        int z = getConfig().getInt("shops." + playername1 + ".z");
					        int yaw = getConfig().getInt("shops." + playername1 + ".yaw");
					        int pitch = getConfig().getInt("shops." + playername1 + ".pitch");
					        Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
					        loc.setPitch(pitch);
					        loc.setYaw(yaw);
					        player.teleport(loc);
							player.sendMessage(TpedToYourShop);
							cantDoCommand.add(player);
							d.setList(cantDoCommand);
							d.setPlayer(player);
							new Thread(d).start();
							return true;
						}else{
							player.sendMessage(Cooldown);
							return true;
						}
					}else{
						int x = getConfig().getInt("shops." + playername1 + ".x");
				        int y = getConfig().getInt("shops." + playername1 + ".y");
				        int z = getConfig().getInt("shops." + playername1 + ".z");
				        int yaw = getConfig().getInt("shops." + playername1 + ".yaw");
				        int pitch = getConfig().getInt("shops." + playername1 + ".pitch");
				        Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
				        loc.setPitch(pitch);
				        loc.setYaw(yaw);
				        player.teleport(loc);
						player.sendMessage(TpedToYourShop);
						cantDoCommand.add(player);
						d.setList(cantDoCommand);
						d.setPlayer(player);
						new Thread(d).start();
						return true;
					}
					}
				}else{
					
					player.sendMessage(NoShop);
					return true;
				}
			}else{
				if (args[0].equalsIgnoreCase("help") || args[0].equals("?")){

						if(player.hasPermission("shopteleport.admin") || player.isOp()) {
						   player.sendMessage(ChatColor.WHITE + "----" + ChatColor.GREEN + " ShopTeleport Help " + ChatColor.RED + "(Admin) " + ChatColor.WHITE + "----");
						}else{
							player.sendMessage(ChatColor.WHITE + "----" + ChatColor.GREEN + " ShopTeleport Help " + ChatColor.WHITE + "----");
						}
						   player.sendMessage(ChatColor.AQUA + "/shop help" + ChatColor.WHITE + "|" + ChatColor.AQUA + "?" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Shows this help page!");
						   player.sendMessage(ChatColor.AQUA + "/setshop" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Set your shop's warp position.");
						   player.sendMessage(ChatColor.AQUA + "/shop <name>" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Teleports you to your/someone's shop.");
						   player.sendMessage(ChatColor.AQUA + "/delshop" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Deletes your shop.");
						   if(player.hasPermission("shopteleport.admin") || player.isOp()) {
							   player.sendMessage(ChatColor.BLUE + "/shop reload" + ChatColor.WHITE + " - " + ChatColor.DARK_GREEN + "Reloads Config Files!");
							   player.sendMessage(ChatColor.BLUE + "/shop set" + ChatColor.WHITE + " - " + ChatColor.DARK_GREEN + "More Information on Set command!");
							}
							   return true;
						}else if (args[0].equalsIgnoreCase("reload")){
					if(player.hasPermission("shopteleport.admin") || player.isOp()) {
						reloadConfig();
						PluginDescriptionFile pdfFile = this.getDescription();
						player.sendMessage(ChatColor.GREEN + "Reloaded " + pdfFile.getName() + "!");
						return true;
						}else{
							player.sendMessage(NoPermission);
							return true;
						}
				}else if (args[0].equalsIgnoreCase("set")){
					if(player.hasPermission("shopteleport.admin") || player.isOp()) {
						if (args.length == 1 || args.length == 2 || args.length > 3){
							player.sendMessage(ChatColor.WHITE + "----" + ChatColor.GREEN + " ShopTeleport Help " + ChatColor.RED + "SET " + ChatColor.WHITE + "----");
							player.sendMessage(ChatColor.AQUA + "/shop set cooldown " + ChatColor.BLUE + "true" + ChatColor.WHITE + "|" + ChatColor.BLUE + "false" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Enables/Disables cooldown");
							player.sendMessage(ChatColor.AQUA + "/shop set cooldown-time " + ChatColor.BLUE + "number" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Sets the cooldown time");
							player.sendMessage(ChatColor.AQUA + "/shop set delay-self " + ChatColor.BLUE + "true" + ChatColor.WHITE + "|" + ChatColor.BLUE + "false" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Enables/Disables delay for own shop");
							player.sendMessage(ChatColor.AQUA + "/shop set delay-others " + ChatColor.BLUE + "true" + ChatColor.WHITE + "|" + ChatColor.BLUE + "false" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Enables/Disables delay for others shops");
							player.sendMessage(ChatColor.AQUA + "/shop set delay-time " + ChatColor.BLUE + "number" + ChatColor.WHITE + " - " + ChatColor.GREEN + "Sets the delay time");
						}else{
							if (args[1].equalsIgnoreCase("cooldown")) {
								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									if (args[2].equalsIgnoreCase("true")) {
										getConfig().set("config.cooldown", true);
										saveConfig();
										player.sendMessage(ChatColor.DARK_GREEN + "Cooldown is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + "!");
									}else{
										getConfig().set("config.cooldown", false);
										saveConfig();
										player.sendMessage(ChatColor.DARK_GREEN + "Cooldown is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + "!");
									}
									reloadConfig();
								}else{
									player.sendMessage(ChatColor.RED + "Usage: /shop set cooldown true" + ChatColor.WHITE + "|" + ChatColor.RED + "false");
									return true;
								}
							}else if (args[1].equalsIgnoreCase("cooldown-time")) {
								if (Pattern.matches("[a-zA-Z]+", args[2])){
									player.sendMessage(ChatColor.RED + "Usage: /shop set cooldown-time " + ChatColor.WHITE + "number");
									return true;
								}else{
									int i = Integer.parseInt(args[2]);
									getConfig().set("config.cooldown-time", i);
									saveConfig();
									player.sendMessage(ChatColor.DARK_GREEN + "Cooldown-time is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + " seconds!");
									reloadConfig();
								}
							}else if (args[1].equalsIgnoreCase("delay-self")){
								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									if (args[2].equalsIgnoreCase("true")) {
										getConfig().set("config.delay.self", true);
										saveConfig();
										player.sendMessage(ChatColor.DARK_GREEN + "Delay-Self is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + "!");
									}else{
										getConfig().set("config.delay.self", false);
										saveConfig();
										player.sendMessage(ChatColor.DARK_GREEN + "Delay-Self is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + "!");
									}
									reloadConfig();
								}else{
									player.sendMessage(ChatColor.RED + "Usage: /shop set delay-self true" + ChatColor.WHITE + "|" + ChatColor.RED + "false");
									return true;
								}
							}else if (args[1].equalsIgnoreCase("delay-others")){
								if (args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("false")) {
									if (args[2].equalsIgnoreCase("true")) {
										getConfig().set("config.delay.others", true);
										saveConfig();
										player.sendMessage(ChatColor.DARK_GREEN + "Delay-Others is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + "!");
									}else{
										getConfig().set("config.delay.others", false);
										saveConfig();
										player.sendMessage(ChatColor.DARK_GREEN + "Delay-Others is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + "!");
									}
									reloadConfig();
								}else{
									player.sendMessage(ChatColor.RED + "Usage: /shop set delay-others true" + ChatColor.WHITE + "|" + ChatColor.RED + "false");
									return true;
								}
							}else if (args[1].equalsIgnoreCase("delay-time")){
								if (Pattern.matches("[a-zA-Z]+", args[2])){
									player.sendMessage(ChatColor.RED + "Usage: /shop set delay-time " + ChatColor.WHITE + "number");
									return true;
								}else{
								int i = Integer.parseInt(args[2]);
								getConfig().set("config.delay.time", i);
								saveConfig();
								player.sendMessage(ChatColor.DARK_GREEN + "Delay-Time is now set to " + ChatColor.GREEN + args[2] + ChatColor.DARK_GREEN + " seconds!");
								reloadConfig();
								}
							}
						}
					}else{
						player.sendMessage(NoPermission);
						return true;
					}
						   return true;
					}else if (args[0].equals("reload")){
				if(player.hasPermission("shopteleport.admin")) {
					reloadConfig();
					PluginDescriptionFile pdfFile = this.getDescription();
					player.sendMessage(ChatColor.GREEN + "Reloaded " + pdfFile.getName() + "!");
					return true;
					}else{
						player.sendMessage(NoPermission);
						return true;
					}
			}else{
					String playername = args[0].toLowerCase();
					String playername1 = player.getName().toLowerCase();
					if (StringUtils.isNotBlank(getConfig().getString("shops." + playername))) {
						if(playername.equals(playername1)) {
							if(player.hasPermission("shopteleport.admin") || player.hasPermission("shopteleport.nolimit")) {
								int x = getConfig().getInt("shops." + playername1 + ".x");
						        int y = getConfig().getInt("shops." + playername1 + ".y");
						        int z = getConfig().getInt("shops." + playername1 + ".z");
						        int yaw = getConfig().getInt("shops." + playername1 + ".yaw");
						        int pitch = getConfig().getInt("shops." + playername1 + ".pitch");
						        Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
						        loc.setPitch(pitch);
						        loc.setYaw(yaw);
						        player.teleport(loc);
								player.sendMessage(TpedToYourShop);
								return true;
							}else{
							if (getConfig().getBoolean("config.delay.self")==true) {
								if (!cantDoCommand.contains(player)) {
									try {
										int delaytime = getConfig().getInt("config.delay.time");
										int delaytime1 = delaytime * 1000;
										player.sendMessage(Delay);
										Thread.sleep(delaytime1);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								int x = getConfig().getInt("shops." + playername1 + ".x");
				        		int y = getConfig().getInt("shops." + playername1 + ".y");
				        		int z = getConfig().getInt("shops." + playername1 + ".z");
				        		int yaw = getConfig().getInt("shops." + playername1 + ".yaw");
						        int pitch = getConfig().getInt("shops." + playername1 + ".pitch");
				        		Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
				        		loc.setPitch(pitch);
						        loc.setYaw(yaw);

						        if(isSafe(loc)==true){
				        		player.teleport(loc);
								player.sendMessage(Tped);
								cantDoCommand.add(player);
								d.setList(cantDoCommand);
								d.setPlayer(player);
								new Thread(d).start();
								}else if (isSafe(loc)==false){
		                        	player.sendMessage(NoTp);
		                        }
								return true;
							}else{
								player.sendMessage(Cooldown);
								return true;
							}
							}
							}
						}else{
							if (getConfig().getBoolean("config.delay.others")==true) {
								if (!cantDoCommand.contains(player)) {
									try {
										int delaytime = getConfig().getInt("config.delay.time");
										int delaytime1 = delaytime * 1000;
										player.sendMessage(Delay);
										Thread.sleep(delaytime1);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								int x = getConfig().getInt("shops." + playername + ".x");
				        		int y = getConfig().getInt("shops." + playername + ".y");
				        		int z = getConfig().getInt("shops." + playername + ".z");
				        		int yaw = getConfig().getInt("shops." + playername + ".yaw");
						        int pitch = getConfig().getInt("shops." + playername + ".pitch");
				        		Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername + ".world")), x, y, z); //defines new location
				        		loc.setPitch(pitch);
						        loc.setYaw(yaw);
						        if(isSafe(loc)==true){
						        	Location loce = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
					        		player.teleport(loce);
								player.sendMessage(Tped);
								cantDoCommand.add(player);
								d.setList(cantDoCommand);
								d.setPlayer(player);
								new Thread(d).start();
						        }else if (isSafe(loc)==false){
		                        	player.sendMessage(NoTp);
		                        }
								return true;
								}else{
									player.sendMessage(Cooldown);
									return true;
								}
							}else{
								int x = getConfig().getInt("shops." + playername + ".x");
				        		int y = getConfig().getInt("shops." + playername + ".y");
				        		int z = getConfig().getInt("shops." + playername + ".z");
				        		int yaw = getConfig().getInt("shops." + playername + ".yaw");
						        int pitch = getConfig().getInt("shops." + playername + ".pitch");
				        		Location loc = new Location(getServer().getWorld(getConfig().getString("shops." + playername + ".world")), x, y, z); //defines new location
				        		loc.setPitch(pitch);
						        loc.setYaw(yaw);
						        if(isSafe(loc)==true){
						        	Location loce = new Location(getServer().getWorld(getConfig().getString("shops." + playername1 + ".world")), x, y, z); //defines new location
					        		player.teleport(loce);
								player.sendMessage(Tped);
								cantDoCommand.add(player);
								d.setList(cantDoCommand);
								d.setPlayer(player);
								new Thread(d).start();
							}else if (isSafe(loc)==false){
	                        	player.sendMessage(NoTp);
	                        }
								return true;
							}
							}
						}else{
						player.sendMessage(DoesntExsist);
						}
					}
				}
			return true;
		}else if (cmd.getName().equalsIgnoreCase("delshop")){
			String playername = player.getName().toLowerCase();
			if (StringUtils.isNotBlank(getConfig().getString("shops." + playername))) {
				if (player.hasPermission("shopteleport.setshop")){
				getConfig().set("shops." + playername, null);
				saveConfig();

				player.sendMessage(ShopDeleted);
				}else{
					player.sendMessage(NoPermission);
				}
			}else{
				player.sendMessage(NoShopDeleted);
			}
			return true;
		}
		return false;



	}

	public boolean isSafe(Location loc){
	    World world = loc.getWorld();
	    double y = loc.getY();
	    //Check for suffocation
	    loc.setY(y+1);
	    Block block1 = world.getBlockAt(loc);
	    loc.setY(y+2);
	    Block block2 = world.getBlockAt(loc);
	    if(!(block1.getTypeId()==0||block2.getTypeId()==0)){
	        return false;//not safe, suffocated
	    }
	    //Check for lava/void
	    for(double i=128;i>-1;i--){
	        loc.setY(i);
	        Block block = world.getBlockAt(loc);
	        if(block.getTypeId()!=0){
	            if(block.getType()==Material.LAVA){
	                return false;//not safe, lava above or below you
	            }else{
	                if(!(block.getType()==Material.TORCH||block.getType()==Material.REDSTONE_TORCH_ON||block.getType()==Material.REDSTONE_TORCH_OFF||block.getType()==Material.PAINTING)){
	                    if(i<y){
	                        loc.setY(-1);//set y to negitive 1 to end loop, we hit solid ground.
	                    }
	                    //Check for painful fall
	                    if((y-i)>10){
	                        return false;//would fall down at least 11 blocks = painful landing...
	                }else{
	                	return true;
	                }
	            }
	            }
	        }else{
	            if(i==0){
	                if(block.getTypeId()==0){
	                    return false;//not safe, void
	                }else{
	                	return true;
	                }
	            }
	        }
	    }
	    return true;
	}

	public class Countdown implements Runnable {

		public Player player1 = null;
		public List<Player> cantDoCommand1 = new ArrayList<Player>();

		public void setPlayer(Player player) {
			player1 = player;
		}

		public void setList(List<Player> list) {
			cantDoCommand1 = list;
		}

		public List<Player> getList() {
			return cantDoCommand1;
		}

		public void run() {
			try {
				int counttime = getConfig().getInt("config.cooldown-time");
				int counttime1 = counttime * 1000;
				Thread.sleep(counttime1);
				cantDoCommand1.remove(player1);
			} catch (Exception ignored) {
			}
		}
	}
}