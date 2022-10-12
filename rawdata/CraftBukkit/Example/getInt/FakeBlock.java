package com.huskehhh.fakeblock;

import com.huskehhh.fakeblock.objects.Config;
import com.huskehhh.fakeblock.objects.Wall;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class FakeBlock extends JavaPlugin implements Listener {

    YamlConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/FakeBlock/config.yml"));

    List<String> right = new ArrayList<String>();
    List<String> selecting = new ArrayList<String>();
    HashMap<String, String> map = new HashMap<String, String>();
    HashMap<String, Config> configObj = new HashMap<String, Config>();

    Utility api = new Utility();

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new FakeBlockListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        createConfig();
        Wall.loadWalls();
    }

    public void onDisable() {
        Wall.unloadWalls();
    }

    private void createConfig() {
        boolean exists = new File("plugins/FakeBlock/config.yml").exists();

        if (!exists) {
            new File("plugins/FakeBlock").mkdir();
            config.options().header("FakeBlock, made by Husky!");
            List<String> walls = new ArrayList<String>();
            walls.add("default");
            config.set("default.data", "1,2,3,world,1,2,3,46");
            config.set("walls.list", walls);

            try {
                config.save("plugins/FakeBlock/config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (commandLabel.equalsIgnoreCase("fakeblock") || commandLabel.equalsIgnoreCase("fb")) {

            if (sender == getServer().getConsoleSender()) {

                sender.sendMessage(ChatColor.RED + "Only players can use these commands.");

            } else {

                if (args.length > 0) {
                    Player p = (Player) sender;
                    String para = args[0];

                    if (para.equalsIgnoreCase("set")) {

                        map.put(p.getName(), args[1]);
                        selecting.add(p.getName());

                        Config conf = new Config();

                        conf.setName(args[1]);
                        conf.setId(Integer.parseInt(args[2]));

                        configObj.put(p.getName(), conf);

                        p.sendMessage(ChatColor.GREEN + "[FakeBlock] You can now select the blocks you want..");

                    }

                }

                return true;

            }
        }
        return false;
    }

    @EventHandler
    public void selection(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (e.getClickedBlock() != null) {
            Block b = e.getClickedBlock();
            if (p.hasPermission("fakeblock.admin")) {

                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

                    if (selecting.contains(p.getName()) && !right.contains(p.getName())) {
                        Location l = b.getLocation();

                        int lx = l.getBlockX();
                        int ly = l.getBlockY();
                        int lz = l.getBlockZ();

                        Config conf = configObj.get(p.getName());
                        conf.setWorldname(l.getWorld().getName());
                        conf.setX(lx);
                        conf.setY(ly);
                        conf.setZ(lz);

                        right.add(p.getName());
                        selecting.remove(p.getName());
                        p.sendMessage(ChatColor.GREEN + "[FakeBlock] Great! Now Please Right-Click and select the second point!");
                        e.setCancelled(true);

                    }
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    if (!selecting.contains(p.getName()) && right.contains(p.getName())) {

                        Location rl = b.getLocation();
                        int rx = rl.getBlockX();
                        int ry = rl.getBlockY();
                        int rz = rl.getBlockZ();

                        Config conf = configObj.get(p.getName());
                        conf.setX1(rx);
                        conf.setY1(ry);
                        conf.setZ1(rz);

                        conf.createObject();

                        configObj.remove(p.getName());

                        p.sendMessage(ChatColor.GREEN + "[FakeBlock] Great! Creating the fake wall now!");

                        right.remove(p.getName());

                        e.setCancelled(true);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            public void run() {
                                for (Player server : Bukkit.getServer().getOnlinePlayers()) {
                                    api.sendFakeBlocks(server);
                                }
                            }
                        }, (2 * 20));

                    }
                }
            }
        }
    }


}
