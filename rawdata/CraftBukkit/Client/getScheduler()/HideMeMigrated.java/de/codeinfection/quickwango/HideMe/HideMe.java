package de.codeinfection.quickwango.HideMe;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import de.codeinfection.quickwango.HideMe.commands.HideCommand;
import de.codeinfection.quickwango.HideMe.commands.ListhiddensCommand;
import de.codeinfection.quickwango.HideMe.commands.SeehiddensCommand;
import de.codeinfection.quickwango.HideMe.commands.UnhideCommand;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet20NamedEntitySpawn;
import net.minecraft.server.Packet29DestroyEntity;
import net.minecraft.server.ServerConfigurationManager;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.util.config.Configuration;
public class HideMe extends org.bukkit.plugin.java.JavaPlugin {
    protected static final java.util.logging.Logger log = java.util.logging.Logger.getLogger("Minecraft");

    public static boolean debugMode = true;

    protected final java.util.ArrayList<org.bukkit.entity.Player> hiddenPlayers = new java.util.ArrayList<org.bukkit.entity.Player>();

    public final java.util.ArrayList<org.bukkit.entity.Player> canSeeHiddens = new java.util.ArrayList<org.bukkit.entity.Player>();

    protected static com.nijiko.permissions.PermissionHandler permissionHandler = null;

    public org.bukkit.Server server;

    public org.bukkit.craftbukkit.CraftServer cserver;

    public net.minecraft.server.ServerConfigurationManager mojangServer;

    protected org.bukkit.plugin.PluginManager pm;

    protected org.bukkit.util.config.Configuration config;

    protected java.io.File dataFolder;

    protected org.bukkit.scheduler.BukkitScheduler scheduler;

    protected de.codeinfection.quickwango.HideMe.Hider hider;

    protected final int hideInterval = 10;

    protected int hiderTaskId;

    public HideMe() {
        this.hiderTaskId = -1;
    }

    public void onEnable() {
        this.server = this.getServer();
        this.cserver = ((org.bukkit.craftbukkit.CraftServer) (this.server));
        this.mojangServer = this.cserver.getHandle();
        this.pm = this.server.getPluginManager();
        this.config = this.getConfiguration();
        this.dataFolder = this.getDataFolder();
        this.scheduler = Utils.getInstance();
        this.hider = new de.codeinfection.quickwango.HideMe.Hider(this, mojangServer, cserver);
        this.dataFolder.mkdirs();
        // Create default config if it doesn't exist.
        if (!new java.io.File(this.dataFolder, "config.yml").exists()) {
            this.defaultConfig();
        }
        this.loadConfig();
        com.nijikokun.bukkit.Permissions.Permissions permissions = ((com.nijikokun.bukkit.Permissions.Permissions) (this.pm.getPlugin("Permissions")));
        if (permissions != null) {
            de.codeinfection.quickwango.HideMe.HideMe.permissionHandler = permissions.getHandler();
        }
        this.getCommand("hide").setExecutor(new de.codeinfection.quickwango.HideMe.commands.HideCommand(this));
        this.getCommand("unhide").setExecutor(new de.codeinfection.quickwango.HideMe.commands.UnhideCommand(this));
        this.getCommand("seehiddens").setExecutor(new de.codeinfection.quickwango.HideMe.commands.SeehiddensCommand(this));
        this.getCommand("listhiddens").setExecutor(new de.codeinfection.quickwango.HideMe.commands.ListhiddensCommand(this));
        de.codeinfection.quickwango.HideMe.HideMePlayerListener playerListener = new de.codeinfection.quickwango.HideMe.HideMePlayerListener(this);
        this.pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Highest, this);
        this.pm.registerEvent(Type.PLAYER_QUIT, playerListener, Priority.Highest, this);
        this.pm.registerEvent(Type.PLAYER_PICKUP_ITEM, playerListener, Priority.Highest, this);
        this.pm.registerEvent(Type.PLAYER_CHAT, playerListener, Priority.Lowest, this);
        this.activateHider();
        java.lang.System.out.println(((this.getDescription().getName() + " (v") + this.getDescription().getVersion()) + ") enabled");
    }

    public void onDisable() {
        this.scheduler.cancelTasks(this);
        java.lang.System.out.println(this.getDescription().getName() + " Disabled");
    }

    private void loadConfig() {
        this.config.load();
    }

    private void defaultConfig() {
        this.config.save();
    }

    protected boolean activateHider() {
        if ((this.hiderTaskId < 0) && (this.hiddenPlayers.size() > 0)) {
            this.hiderTaskId = this.scheduler.scheduleAsyncRepeatingTask(this, this.hider, 0, hideInterval);
            de.codeinfection.quickwango.HideMe.HideMe.log("Activated the HiderTask");
            return this.hiderTaskId > (-1);
        } else {
            return false;
        }
    }

    public boolean deactivateHider() {
        if (this.hiderTaskId > (-1)) {
            this.scheduler.cancelTask(this.hiderTaskId);
            this.hiderTaskId = -1;
            de.codeinfection.quickwango.HideMe.HideMe.log("Deactivated the HiderTask");
            return true;
        } else {
            return false;
        }
    }

    public static void log(java.lang.String msg) {
        de.codeinfection.quickwango.HideMe.HideMe.log.log(java.util.logging.Level.INFO, "[HideMe] " + msg);
    }

    public static void error(java.lang.String msg) {
        de.codeinfection.quickwango.HideMe.HideMe.log.log(java.util.logging.Level.SEVERE, "[HideMe] " + msg);
    }

    public static void error(java.lang.String msg, java.lang.Throwable t) {
        de.codeinfection.quickwango.HideMe.HideMe.log.log(java.util.logging.Level.SEVERE, "[HideMe] " + msg, t);
    }

    public static void debug(java.lang.String msg) {
        if (de.codeinfection.quickwango.HideMe.HideMe.debugMode) {
            de.codeinfection.quickwango.HideMe.HideMe.log("[debug] " + msg);
        }
    }

    public static boolean hasPermission(org.bukkit.entity.Player player, java.lang.String permission) {
        return player.isOp() || ((de.codeinfection.quickwango.HideMe.HideMe.permissionHandler != null) && de.codeinfection.quickwango.HideMe.HideMe.permissionHandler.has(player, permission));
    }

    public void addPlayerEntity(java.lang.String toHide, java.lang.String hideFrom) {
        org.bukkit.entity.Player playerToHide = this.server.getPlayer(toHide);
        org.bukkit.entity.Player hideFromPlayer = this.server.getPlayer(hideFrom);
        if ((playerToHide == null) || (hideFromPlayer == null)) {
            return;
        }
        this.addPlayerEntity(playerToHide, hideFromPlayer);
    }

    public void addPlayerEntity(org.bukkit.entity.Player toHide, org.bukkit.entity.Player hideFrom) {
        this.addPlayerEntity(((org.bukkit.craftbukkit.entity.CraftPlayer) (toHide)), ((org.bukkit.craftbukkit.entity.CraftPlayer) (hideFrom)));
    }

    public void addPlayerEntity(org.bukkit.craftbukkit.entity.CraftPlayer toHide, org.bukkit.craftbukkit.entity.CraftPlayer hideFrom) {
        this.addPlayerEntity(toHide.getHandle(), hideFrom.getHandle());
    }

    public void addPlayerEntity(net.minecraft.server.EntityPlayer toHide, net.minecraft.server.EntityPlayer hideFrom) {
        hideFrom.netServerHandler.sendPacket(new net.minecraft.server.Packet20NamedEntitySpawn(toHide));
    }

    public void removePlayerEntity(java.lang.String toHide, java.lang.String hideFrom) {
        org.bukkit.entity.Player playerToHide = server.getPlayer(toHide);
        org.bukkit.entity.Player hideFromPlayer = server.getPlayer(hideFrom);
        if ((playerToHide == null) || (hideFromPlayer == null)) {
            return;
        }
        this.removePlayerEntity(playerToHide, hideFromPlayer);
    }

    public void removePlayerEntity(org.bukkit.entity.Player toHide, org.bukkit.entity.Player hideFrom) {
        this.removePlayerEntity(((org.bukkit.craftbukkit.entity.CraftPlayer) (toHide)), ((org.bukkit.craftbukkit.entity.CraftPlayer) (hideFrom)));
    }

    public void removePlayerEntity(org.bukkit.craftbukkit.entity.CraftPlayer toHide, org.bukkit.craftbukkit.entity.CraftPlayer hideFrom) {
        this.removePlayerEntity(toHide.getHandle(), hideFrom.getHandle());
    }

    public void removePlayerEntity(net.minecraft.server.EntityPlayer toHide, net.minecraft.server.EntityPlayer hideFrom) {
        hideFrom.netServerHandler.sendPacket(new net.minecraft.server.Packet29DestroyEntity(toHide.id));
    }

    public void hide(org.bukkit.entity.Player player) {
        net.minecraft.server.EntityPlayer playerEntity = ((org.bukkit.craftbukkit.entity.CraftPlayer) (player)).getHandle();
        this.mojangServer.players.remove(playerEntity);
        /* try
        {
        for (EntityPlayer current : (List<EntityPlayer>)mojangServer.players)
        {
        if (!this.canSeeHiddens.contains(current.netServerHandler.getPlayer()))
        {
        removePlayerEntity(playerEntity, current);
        }
        }

        for (Player current : this.hiddenPlayers)
        {
        if (!canSeeHiddens.contains(current))
        {
        if (current != player)
        {
        removePlayerEntity(player, current);
        }
        }
        }
        }
        catch (Exception e)
        {
        // Readd the player on failure
        mojangServer.players.add(playerEntity);
        return false;
        }
         */
        de.codeinfection.quickwango.HideMe.FakePlayerQuitEvent playerQuit = new de.codeinfection.quickwango.HideMe.FakePlayerQuitEvent(player, (org.bukkit.ChatColor.YELLOW + player.getName()) + " left the game.");
        this.server.getPluginManager().callEvent(playerQuit);
        this.server.broadcastMessage(java.lang.String.valueOf(playerQuit.getQuitMessage()));
        this.addHidden(player);
    }

    public void unhide(org.bukkit.entity.Player player) {
        net.minecraft.server.EntityPlayer playerEntity = ((org.bukkit.craftbukkit.entity.CraftPlayer) (player)).getHandle();
        de.codeinfection.quickwango.HideMe.FakePlayerJoinEvent playerJoin = new de.codeinfection.quickwango.HideMe.FakePlayerJoinEvent(player, (org.bukkit.ChatColor.YELLOW + player.getName()) + " joined the game.");
        this.server.getPluginManager().callEvent(playerJoin);
        this.server.broadcastMessage(java.lang.String.valueOf(playerJoin.getJoinMessage()));
        this.removeHidden(player);
        for (net.minecraft.server.EntityPlayer current : ((java.util.List<net.minecraft.server.EntityPlayer>) (mojangServer.players))) {
            current.netServerHandler.sendPacket(new net.minecraft.server.Packet20NamedEntitySpawn(playerEntity));
        }
        for (org.bukkit.entity.Player current : this.hiddenPlayers) {
            if ((current != player) && (!this.canSeeHiddens.contains(current))) {
                this.addPlayerEntity(playerEntity, ((org.bukkit.craftbukkit.entity.CraftPlayer) (current)).getHandle());
            }
        }
        this.mojangServer.players.add(playerEntity);
    }

    public boolean isHidden(org.bukkit.entity.Player player) {
        return this.hiddenPlayers.contains(player);
    }

    public void addHidden(org.bukkit.entity.Player player) {
        this.hiddenPlayers.add(player);
        this.activateHider();
    }

    public void removeHidden(org.bukkit.entity.Player player) {
        this.hiddenPlayers.remove(player);
        if (this.countHiddens() < 1) {
            this.deactivateHider();
        }
    }

    public java.util.List<org.bukkit.entity.Player> getHiddens() {
        return this.hiddenPlayers;
    }

    public int countHiddens() {
        return this.hiddenPlayers.size();
    }
}