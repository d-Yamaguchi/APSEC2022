package me.repeat.ruFix;
import java.io.*;
import java.util.logging.*;
import me.repeat.ruFix.Listeners.PlayerDeath;
public class ruFix extends org.bukkit.plugin.java.JavaPlugin {
    private final me.repeat.ruFix.Listeners.PlayerListener PlayerListener = new me.repeat.ruFix.Listeners.PlayerListener(this);

    private final me.repeat.ruFix.Listeners.ServerListener ServerListener = new me.repeat.ruFix.Listeners.ServerListener(this);

    public static boolean ruFixDebug = false;

    public static java.lang.String ruFixConsole = "UTF-8";

    public static java.lang.String ruFixLogFile = "UTF-8";

    public static boolean parseConsole = true;

    public static boolean parseLogFile = true;

    public static java.io.File directory;

    public static char[] fromGame = new char[]{  };

    public static char[] toGame = new char[]{  };

    public static java.lang.String prefix = null;

    private org.bukkit.configuration.file.FileConfiguration config = null;

    private java.io.File configFile = null;

    @java.lang.Override
    public void onEnable() {
        me.repeat.ruFix.ruFix.directory = this.getDataFolder();
        org.bukkit.plugin.PluginDescriptionFile pdfFile = this.getDescription();
        me.repeat.ruFix.ruFix.prefix = ("[" + pdfFile.getName()) + "]";
        java.util.logging.Logger.getLogger("Minecraft").info(((me.repeat.ruFix.ruFix.prefix + " version ") + pdfFile.getVersion()) + " is enabled!");
        readConfig();
        try {
            ruFixHandler.handlerChange();
        } catch (java.security.NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        org.bukkit.plugin.PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(PlayerListener, this);
        pm.registerEvents(ServerListener, this);
        pm.registerEvents(new me.repeat.ruFix.Listeners.PlayerDeath(this), this);
        readTables();
    }

    @java.lang.Override
    public void onDisable() {
        java.util.logging.Logger.getLogger("Minecraft").info(me.repeat.ruFix.ruFix.prefix + " is disabled!");
    }

    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
        return readCommand(sender, commandLabel, args);
    }

    public boolean readCommand(org.bukkit.command.CommandSender sender, java.lang.String command, java.lang.String[] args) {
        if (command.equalsIgnoreCase("rufixreload") && (sender instanceof org.bukkit.command.ConsoleCommandSender)) {
            this.reloadConfig();
            readConfig();
            readTables();
            if (me.repeat.ruFix.ruFix.ruFixDebug)
                sender.sendMessage("ParseConsole is now " + me.repeat.ruFix.ruFix.parseConsole);

            return true;
        }
        return false;
    }

    public void saveConfig() {
        if ((config == null) || (configFile == null)) {
            return;
        }
        try {
            config.save(configFile);
        } catch (java.io.IOException ex) {
            java.util.logging.Logger.getLogger(org.bukkit.plugin.java.JavaPlugin.class.getName()).log(java.util.logging.Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    private void placeFiles() {
        boolean configExists = false;
        boolean langTableExists = false;
        if (me.repeat.ruFix.ruFix.directory.exists()) {
            for (java.lang.String f : me.repeat.ruFix.ruFix.directory.list()) {
                if (f.equalsIgnoreCase("config.yml"))
                    configExists = true;

                if (f.equalsIgnoreCase("ru.tbl"))
                    langTableExists = true;

            }
        } else {
            me.repeat.ruFix.ruFix.directory.mkdir();
        }
        if (!configExists) {
            java.util.logging.Logger.getLogger("Minecraft").info(me.repeat.ruFix.ruFix.prefix + ": WRITING DEFAULT CONFIG");
            saveDefaultConfig();
        }
        if (!langTableExists) {
            boolean writeFile = true;
            try {
                java.io.InputStream defaultStream = null;
                java.io.File conf = null;
                java.util.logging.Logger.getLogger("Minecraft").info(me.repeat.ruFix.ruFix.prefix + ": WRITING DEFAULT LANGUAGE TABLE (RU)");
                defaultStream = this.getClass().getResourceAsStream("/ru.tbl");
                conf = new java.io.File((me.repeat.ruFix.ruFix.directory + java.io.File.separator) + "ru.tbl");
                if (langTableExists)
                    writeFile = false;

                if (writeFile) {
                    me.repeat.ruFix.ruFix.directory.mkdir();
                    conf.createNewFile();
                    java.io.OutputStream confStream = new java.io.FileOutputStream(conf);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = defaultStream.read(buf)) > 0) {
                        confStream.write(buf, 0, len);
                    } 
                    defaultStream.close();
                    confStream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                e.printStackTrace();
            } catch (java.lang.NullPointerException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readConfig() {
        placeFiles();
        java.io.File configFile = new java.io.File(me.repeat.ruFix.ruFix.directory, "config.yml");
        config = Material.getMaterial("config.yml");
        saveConfig();
        me.repeat.ruFix.ruFix.ruFixDebug = config.getBoolean("Debug", false);
        me.repeat.ruFix.ruFix.parseConsole = config.getBoolean("ParseConsole", true);
        me.repeat.ruFix.ruFix.parseLogFile = config.getBoolean("ParseLogFile", true);
        me.repeat.ruFix.ruFix.ruFixConsole = config.getString("Console", "UTF-8");
        me.repeat.ruFix.ruFix.ruFixLogFile = config.getString("LogFile", "UTF-8");
    }

    private void readTables() {
        java.io.File configFile = new java.io.File(me.repeat.ruFix.ruFix.directory, "config.yml");
        config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
        java.util.List<java.lang.String> fixTables = config.getStringList("Tables");
        if (fixTables != null) {
            for (java.lang.String fixTable : fixTables) {
                java.io.File file = new java.io.File(me.repeat.ruFix.ruFix.directory, fixTable + ".tbl");
                java.io.FileReader input;
                try {
                    input = new java.io.FileReader(file);
                    java.io.BufferedReader bufRead = new java.io.BufferedReader(input);
                    // read remark
                    bufRead.readLine();
                    me.repeat.ruFix.ruFix.fromGame = bufRead.readLine().toCharArray();
                    me.repeat.ruFix.ruFix.toGame = bufRead.readLine().toCharArray();
                } catch (java.io.FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (java.io.IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static java.lang.String fixUseTable(java.lang.String msg) {
        for (int n = 0; n < msg.length(); n++) {
            char t = msg.charAt(n);
            int idx = java.util.Arrays.binarySearch(me.repeat.ruFix.ruFix.fromGame, t);
            if (idx > (-1)) {
                msg = msg.replace(t, me.repeat.ruFix.ruFix.toGame[idx]);
            }
        }
        return msg;
    }
}