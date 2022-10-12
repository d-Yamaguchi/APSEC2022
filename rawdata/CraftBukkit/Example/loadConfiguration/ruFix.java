package me.repeat.ruFix;

import me.repeat.ruFix.Listeners.PlayerDeath;
import me.repeat.ruFix.Listeners.PlayerListener;
import me.repeat.ruFix.Listeners.ServerListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ruFix extends JavaPlugin {

    public static boolean advancedMode = false;

    public static boolean ruFixDebug = false;
    public static String ruFixConsole = "UTF-8";
    public static String ruFixLogFile = "UTF-8";

    public static boolean parseConsole = true;
    public static boolean parseLogFile = true;

    public static boolean fixDeathMessages = true;

    public static char[] fromGame = {};
    public static char[] toGame = {};

    private FileConfiguration config = null;
    private File configFile = null;

    public static ruFix plugin = null;

    @Override
    public void onEnable() {

        plugin = this;

        getLogger().info("Version " + this.getDescription().getVersion() + " is enabled!");

        readConfig();

        try {
            ruFixHandler.handlerChange();
        } catch (Exception e) {
            e.printStackTrace();
        }

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new ServerListener(this), this);
        if (fixDeathMessages)
            pm.registerEvents(new PlayerDeath(this), this);

        readTables();
    }

    @Override
    public void onDisable() {
        getLogger().info("RuFix is disabled!");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return readCommand(sender, commandLabel, args);
    }

    public boolean readCommand(CommandSender sender, String command, String[] args) {
        if ((command.equalsIgnoreCase("rufixreload"))) {
            readConfig();
            readTables();
            sender.sendMessage("RuFix " + this.getDescription().getVersion() + " Reloaded!");
            return true;
        }
        return false;
    }

    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            config.save(configFile);
        } catch (IOException ex) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    private void placeFiles() {
        boolean langTableExists = false;
        File conf = new File(getDataFolder() + File.separator + "ru.tbl");

        if (conf.exists()) {
            langTableExists = true;
        }

        if (!langTableExists) {
            try {
                getLogger().info("WRITING DEFAULT LANGUAGE TABLE (RU)");
                InputStream defaultStream = getResource("ru.tbl");
                conf.createNewFile();
                OutputStream confStream = new FileOutputStream(conf);
                byte buf[] = new byte[1024];
                int len;
                while ((len = defaultStream.read(buf)) > 0) {
                    confStream.write(buf, 0, len);
                }
                defaultStream.close();
                confStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readConfig() {
        reloadConfig();
        this.saveDefaultConfig();
        config = getConfig();
        advancedMode = config.getBoolean("Advanced", false);
        if (advancedMode) {
            ruFixDebug = config.getBoolean("Debug", false);
            parseConsole = config.getBoolean("ParseConsole", true);
            parseLogFile = config.getBoolean("ParseLogFile", true);
            ruFixConsole = config.getString("Console", "UTF-8");
            ruFixLogFile = config.getString("LogFile", "UTF-8");
        } else {
            if (config.getString("OS", "Linux").equals("Windows")) {
                ruFixConsole = "CP866";
            }
        }
        fixDeathMessages = config.getBoolean("DeathMessages", true);
        saveConfig();
        placeFiles();
    }

    private void readTables() {

        List<String> fixTables = config.getStringList("Tables");

        if (fixTables != null) {
            for (String fixTable : fixTables) {
                File file = new File(getDataFolder(), fixTable + ".tbl");
                FileReader input;
                try {
                    input = new FileReader(file);
                    BufferedReader bufRead = new BufferedReader(input);

                    // read remark
                    bufRead.readLine();

                    fromGame = bufRead.readLine().toCharArray();
                    toGame = bufRead.readLine().toCharArray();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String fixUseTable(String msg) {
        for (int n = 0; n < msg.length(); n++) {
            char t = msg.charAt(n);
            int idx = Arrays.binarySearch(ruFix.fromGame, t);
            if (idx > -1) {
                msg = msg.replace(t, ruFix.toGame[idx]);
            }
        }
        return msg;
    }
}
