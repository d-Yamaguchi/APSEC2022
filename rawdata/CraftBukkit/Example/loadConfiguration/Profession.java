package me.eccentric_nz.plugins.profession;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Profession extends JavaPlugin implements Listener {

    private Material f;
    private Material b;
    private Material l;
    private Material s;
    private Material p;
    private Material z;
    private final String pluginName = "[Profession]";
    private String language;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ProfessionConfiguration pc = new ProfessionConfiguration(this);
        pc.checkConfig();
        loadMaterials();
        getServer().getPluginManager().registerEvents(new ProfessionListener(this), this);
        getCommand("setprof").setExecutor(new ProfessionSetExecutor(this));
        getCommand("worldprof").setExecutor(new ProfessionWorldExecutor(this));
        getCommand("consumeprof").setExecutor(new ProfessionConsumeExecutor(this));
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public void loadMaterials() {
        // read the material values we need and convert them to ENUM
        String f_str = getConfig().getString("farmer_material");
        String b_str = getConfig().getString("butcher_material");
        String l_str = getConfig().getString("librarian_material");
        String s_str = getConfig().getString("smith_material");
        String p_str = getConfig().getString("priest_material");
        String z_str = getConfig().getString("zombie_material");

        // convert strings to Material ENUMs
        f = Material.getMaterial(f_str);
        b = Material.getMaterial(b_str);
        l = Material.getMaterial(l_str);
        s = Material.getMaterial(s_str);
        p = Material.getMaterial(p_str);
        z = Material.getMaterial(z_str);

        // read the language value
        language = getConfig().getString("lang");

        Constants.NO_MATS_MESSAGE = Constants.nms().get(language) + ChatColor.YELLOW + f_str + " -> FARMER\n" + ChatColor.RED + b_str + " -> BUTCHER\n" + ChatColor.BLUE + l_str + " -> LIBRARIAN\n" + ChatColor.GRAY + s_str + " -> BLACKSMITH\n" + ChatColor.DARK_RED + p_str + " -> PRIEST\n" + ChatColor.DARK_GREEN + z_str + " -> ZOMBIE VILLAGER";
    }

    public Material getF() {
        return f;
    }

    public Material getB() {
        return b;
    }

    public Material getL() {
        return l;
    }

    public Material getS() {
        return s;
    }

    public Material getP() {
        return p;
    }

    public Material getZ() {
        return z;
    }

    public String getPluginName() {
        return pluginName;
    }

    public String getLanguage() {
        return language;
    }
}
