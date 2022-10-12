package me.eccentric_nz.plugins.profession;

import java.io.*;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Profession extends JavaPlugin implements Listener {

    public PluginDescriptionFile pdfFile;
    public FileConfiguration config = null;
    private File myconfigfile = null;
    private File materialfile = null;
    private Material f;
    private Material b;
    private Material l;
    private Material s;
    private Material p;
    private ProfessionSetExecutor professionSetExecutor;
    private ProfessionWorldExecutor professionWorldExecutor;
    private ProfessionConsumeExecutor professionConsumeExecutor;
    private HashMap npm_hm;
    private HashMap nms_hm;
    private HashMap ssm_hm;
    private HashMap pna_hm;
    private String world;
    private Boolean worldcheck;
    private World current_world;
    private boolean secretary = false;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        pdfFile = getDescription();
        Constants.MY_PLUGIN_NAME = "[" + pdfFile.getName() + "]";

        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }
        } catch (Exception e) {
            System.out.println("Profession 1.3 could not create directory!");
            System.out.println("Profession 1.3 requires you to manually make the Profession/ directory!");
        }

        getDataFolder().setWritable(true);
        getDataFolder().setExecutable(true);

        if (config == null) {
            loadConfig();
        }

        professionSetExecutor = new ProfessionSetExecutor(this);
        getCommand("setprof").setExecutor(professionSetExecutor);

        professionWorldExecutor = new ProfessionWorldExecutor(this);
        getCommand("worldprof").setExecutor(professionWorldExecutor);

        professionConsumeExecutor = new ProfessionConsumeExecutor(this);
        getCommand("consumeprof").setExecutor(professionConsumeExecutor);
    }

    @Override
    public void onDisable() {
        saveCustomConfig();
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        // get player
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        EntityType entityType = entity.getType();
        ItemStack stack = player.getItemInHand();
        Material material = stack.getType();

        if (entityType.equals(EntityType.VILLAGER)) {

            if (player.hasPermission("profession.change")) {

                // check if the config setting for this world allows profession changing
                world = player.getWorld().getName();
                worldcheck = config.getBoolean("worlds." + world);
                if (worldcheck == true) {

                    Villager.Profession profession = Villager.Profession.FARMER;
                    ChatColor colour = ChatColor.RESET;
                    Villager villager = (Villager) entity;

                    if (material == f || material == b || material == l || material == s || material == p) {

                        if (material == f) {
                            profession = Villager.Profession.FARMER;
                            colour = ChatColor.YELLOW;
                        }
                        if (material == l) {
                            profession = Villager.Profession.LIBRARIAN;
                            colour = ChatColor.BLUE;
                        }
                        if (material == s) {
                            profession = Villager.Profession.BLACKSMITH;
                            colour = ChatColor.GRAY;
                        }
                        if (material == b) {
                            profession = Villager.Profession.BUTCHER;
                            colour = ChatColor.RED;
                        }
                        if (material == p) {
                            profession = Villager.Profession.PRIEST;
                            colour = ChatColor.DARK_RED;
                        }

                        villager.setProfession(profession);

                        ssm_hm = Constants.ssm(colour, profession);
                        String ssm = (String) ssm_hm.get(Constants.LANGUAGE);
                        player.sendMessage(ssm);

                        // should the material be used up?
                        if (Constants.CONSUME == true) {
                            int a = player.getInventory().getItemInHand().getAmount();
                            int a2 = new Integer(a - 1);
                            if (a2 > 0) {
                                player.getInventory().getItemInHand().setAmount(a2);
                            } else {
                                player.getInventory().removeItem(new ItemStack(material, 1));
                            }
                        }
                        event.setCancelled(true);
                    } else {
                        if (getServer().getPluginManager().getPlugin("Secretary") != null && (material.equals(Material.FEATHER) || material.equals(Material.INK_SACK) || material.equals(Material.PAPER))) {
                            if (secretary == false) {
                                System.out.println(Constants.SECRETARY_ENABLED);
                                secretary = true;
                            }
                        } else {
                            player.sendMessage(Constants.NO_MATS_MESSAGE);
                        }
                    }
                } else {
                    pna_hm = Constants.pna(world);
                    String pna = (String) pna_hm.get(Constants.LANGUAGE);
                    player.sendMessage(pna);
                }
            } else {
                npm_hm = Constants.npm();
                String npm = (String) npm_hm.get(Constants.LANGUAGE);
                player.sendMessage(npm);
            }
        }
    }

    public FileConfiguration loadConfig() {
        try {
            //reloadCustomConfig();
            myconfigfile = new File(getDataFolder(), Constants.CONFIG_FILE_NAME);
            if (!myconfigfile.exists()) {
                // load the default values into file
                copy(getResource(Constants.CONFIG_FILE_NAME), myconfigfile);
            }
            materialfile = new File(getDataFolder(), Constants.MAT_FILE_NAME);
            if (!materialfile.exists()) {
                // load the default values into file
                copy(getResource(Constants.MAT_FILE_NAME), materialfile);
            }

            config = YamlConfiguration.loadConfiguration(myconfigfile);

            // read the material values we need and convert them to ENUM
            String f_str = config.getString("farmer_material");
            String b_str = config.getString("butcher_material");
            String l_str = config.getString("librarian_material");
            String s_str = config.getString("smith_material");
            String p_str = config.getString("priest_material");

            // convert strings to Material ENUMs
            f = Material.getMaterial(f_str);
            b = Material.getMaterial(b_str);
            l = Material.getMaterial(l_str);
            s = Material.getMaterial(s_str);
            p = Material.getMaterial(p_str);

            if (config.getString("lang") == null) {
                System.out.println("Profession is trying to update the config file...");
                // we need to overwrite the config file and set the values back from above
                copy(getResource(Constants.CONFIG_FILE_NAME), myconfigfile);
                config.set("farmer_material", f_str);
                config.set("butcher_material", b_str);
                config.set("librarian_material", l_str);
                config.set("smith_material", s_str);
                config.set("priest_material", p_str);
                // find the current world name and add it to the config
                current_world = getServer().getWorlds().get(0);
                config.set("worlds." + current_world.getName(), true);
                config.set("lang", "EN");
                config.set("consume", false);
                // save the config
                saveCustomConfig();
                System.out.println("Profession is saving the new config and reloading... please check the config file and edit it as necessary using the appropriate commands.");
                // reload the config
                config = YamlConfiguration.loadConfiguration(myconfigfile);
            }
            // read the language value
            Constants.LANGUAGE = config.getString("lang");
            // read the consume value
            Constants.CONSUME = config.getBoolean("consume");

            nms_hm = Constants.nms();
            String nms = (String) nms_hm.get(Constants.LANGUAGE);

            Constants.NO_MATS_MESSAGE = nms + ChatColor.YELLOW + f_str + " -> FARMER\n" + ChatColor.RED + b_str + " -> BUTCHER\n" + ChatColor.BLUE + l_str + " -> LIBRARIAN\n" + ChatColor.GRAY + s_str + " -> BLACKSMITH\n" + ChatColor.DARK_RED + p_str + " -> PRIEST";

        } catch (Exception e) {
            System.err.println(Constants.MY_PLUGIN_NAME + " failed to retrieve configuration from directory. Using defaults.");
        }
        return config;
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            System.err.println(Constants.MY_PLUGIN_NAME + " could not save the config file.");
        }
    }

    public void saveCustomConfig() {
        if (config == null || myconfigfile == null) {
            return;
        }
        try {
            config.save(myconfigfile);
        } catch (IOException ex) {
            System.err.println(Constants.MY_PLUGIN_NAME + " Could not save config to " + myconfigfile + " - " + ex);
        }
    }
}