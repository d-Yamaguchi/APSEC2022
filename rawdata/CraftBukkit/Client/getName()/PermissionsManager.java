package net.sacredlabyrinth.phaed.simpleclans.managers;

import com.miraclem4n.mchat.api.API;
import in.mDev.MiracleM4n.mChatSuite.mChatSuite;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.sacredlabyrinth.Phaed.PreciousStones.FieldFlag;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;
import net.sacredlabyrinth.Phaed.PreciousStones.ResultsFilter;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.ChunkVec;
import net.sacredlabyrinth.Phaed.PreciousStones.vectors.Field;
import net.sacredlabyrinth.phaed.simpleclans.ChunkLocation;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author phaed
 */
public final class PermissionsManager {

    /**
     *
     */
    private SimpleClans plugin;
    private PreciousStones ps;
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;
    private mChatSuite mchat = null;

    /**
     *
     */
    public PermissionsManager(SimpleClans plugin)
    {
        this.plugin = plugin;
        detectPreciousStones();
        detectMChat();

        try {
            Class.forName("net.milkbowl.vault.permission.Permission");

            setupChat();
            setupEconomy();
            setupPermissions();
        } catch (ClassNotFoundException e) {
            //SimpleClans.log("[PreciousStones] Vault.jar not found. No economy support.");
            //no need to spam everyone who doesnt use vault
        }
    }

    public mChatSuite getMChat()
    {
        return mchat;
    }

    /**
     * Whether exonomy plugin exists and is enabled
     *
     * @return
     */
    public boolean hasEconomy()
    {
        if (economy != null && economy.isEnabled()) {
            return true;
        }
        return false;
    }

    /**
     * Adds all pemrissions for a clan
     *
     * @param clan
     */
    public void updateClanPermissions(Clan clan)
    {
        if (plugin.getSettingsManager().isPermissionsEnabled()) {
            for (ClanPlayer cp : clan.getMembers()) {
                setupPlayerPermissions(cp);
            }
        }
    }

    public void updatePlayerPermissions(ClanPlayer cp)
    {
        cp.removePermissionAttachment();
        setupPlayerPermissions(cp);
    }

    /**
     * Adds permissions for a player
     *
     * @param cp
     */
    public void setupPlayerPermissions(ClanPlayer cp)
    {
        if (plugin.getSettingsManager().isPermissionsEnabled()) {
            if (cp != null) {
                Clan clan = cp.getClan();
                if (clan != null) {
                    String tag = clan.getTag();
                    Set<String> clanPermissions = plugin.getSettingsManager().getClanPermissions(tag);

                    if (clanPermissions == null) {
                        return;
                    }

                    //setup permission set
                    cp.setupPermissionAttachment();

                    //Adds all permisisons from his clan
                    for (String perm : clanPermissions) {
                        cp.addPermission(perm);
                    }

                    if (cp.isLeader()) {
                        Set<String> defaultleaderpermissions = plugin.getSettingsManager().getDefaultLeaderPermissions(tag);

                        for (String perm : defaultleaderpermissions) {
                            cp.addPermission(perm);
                        }
                    }

                    if (cp.isTrusted()) {
                        Set<String> defaultTrustedPermissions = plugin.getSettingsManager().getDefaultTrustedPermissions(tag);

                        for (String perm : defaultTrustedPermissions) {
                            cp.addPermission(perm);
                        }
                    } else if (!cp.isTrusted()) {

                        Set<String> defaultUnTrustedPermissions = plugin.getSettingsManager().getDefaultUnTrustedPermissions(tag);

                        for (String perm : defaultUnTrustedPermissions) {
                            cp.addPermission(perm);
                        }
                    }

                    if (plugin.getSettingsManager().isAutoGroupGroupName()) {
                        cp.addPermission("group." + tag);
                    }

                    cp.recalculatePermissions();
                }
            }
        }
    }

    /**
     * Removes permissions for a clan (when it gets disbanded for example)
     *
     * @param clan
     */
    public void removeClanPermissions(Clan clan)
    {
        for (ClanPlayer cp : clan.getMembers()) {
            removeClanPlayerPermissions(cp);
        }
    }

    /**
     * Removes permissions for a player (when he gets kicked for example)
     *
     * @param cp
     */
    public void removeClanPlayerPermissions(ClanPlayer cp)
    {
        if (cp != null) {
            Clan clan = cp.getClan();
            if (clan != null) {
                cp.removePermissionAttachment();
            }
        }
    }

    /**
     * Charge a player some money
     *
     * @param player
     * @param money
     * @return
     */
    public boolean playerChargeMoney(Player player, double money)
    {
        return economy.withdrawPlayer(player.getName(), money).transactionSuccess();
    }

    /**
     * Grants a player some money
     *
     * @param player
     * @param money
     * @return
     */
    public boolean playerGrantMoney(Player player, double money)
    {
        return economy.depositPlayer(player.getName(), money).transactionSuccess();
    }

    /**
     * Grants a player some money
     *
     * @param player
     * @param money
     * @return
     */
    public boolean playerGrantMoney(String player, double money)
    {
        return economy.depositPlayer(player, money).transactionSuccess();
    }

    /**
     * Check if a user has the money
     *
     * @param player
     * @param money
     * @return whether he has the money
     */
    public boolean playerHasMoney(Player player, double money)
    {
        return economy.has(player.getName(), money);
    }

    /**
     * Returns the players money
     *
     * @param player
     * @return the players money
     */
    public double playerGetMoney(Player player)
    {
        return economy.getBalance(player.getName());
    }

    /**
     * Check if a player has permissions
     *
     * @param player the player
     * @param perm   the permission
     * @return whether he has the permission
     */
    public boolean has(Player player, String perm)
    {
        if (player == null) {
            return false;
        }

        if (permission != null) {
            return permission.has(player, perm);
        } else {
            return player.hasPermission(perm);
        }
    }

    /**
     * Check if a commandsender has permissions
     *
     * @param sender the player
     * @param perm   the permission
     * @return whether he has the permission
     */
    public boolean has(CommandSender sender, String perm)
    {
        if (sender == null) {
            return false;
        }

        if (permission != null) {
            return permission.has(sender, perm);
        } else {
            return sender.hasPermission(perm);
        }
    }

    /**
     * Sets the mChat clan tag
     *
     * @param player
     * @param value
     */
    public void addSetMChatClanTag(Player player, String value)
    {
        if (mchat != null) {
            API.addPlayerVar(player.getName(), "clan", value);
        }
    }

    /**
     * Clears the mChat clan tag
     *
     * @param player
     */
    public void clearSetMChatClanTag(Player player)
    {
        if (mchat != null) {
            API.addPlayerVar(player.getName(), "clan", "");
        }
    }

    public boolean isAreaAlreadyProtected(ChunkLocation chunk)
    {
        if (ps != null) {
            List<Field> fields = ps.getForceFieldManager().getSourceFieldsInChunk(new ChunkVec(chunk.getNormalX(), chunk.getNormalZ(), chunk.getWorld()), FieldFlag.ALL, new ResultsFilter[0]);
            if (fields != null) {
                if (!fields.isEmpty()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Whether a player is allowed in the area
     *
     * @param player
     * @param location
     * @return
     */
    public boolean teleportAllowed(Player player, Location location)
    {
        if (ps != null) {
            List<Field> fields = ps.getForceFieldManager().getSourceFields(location, FieldFlag.PREVENT_TELEPORT);

            if (fields != null && !fields.isEmpty()) {
                for (Field field : fields) {
                    boolean allowed = ps.getForceFieldManager().isApplyToAllowed(field, player.getName());

                    if (!allowed || field.hasFlag(FieldFlag.APPLY_TO_ALL)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void detectPreciousStones()
    {
        Plugin plug = plugin.getServer().getPluginManager().getPlugin("PreciousStones");

        if (plug != null) {
            ps = ((PreciousStones) plug);
        }
    }

    private void detectMChat()
    {
        Plugin test = plugin.getServer().getPluginManager().getPlugin("mChatSuite");

        if (test != null) {
            mchat = (mChatSuite) test;
        }
    }

    private Boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    private boolean setupChat()
    {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        if (rsp != null) {
            chat = rsp.getProvider();
        }
        return chat != null;
    }

    private Boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    public String getPrefix(Player p)
    {
        String out;

        try {
            out = chat.getPlayerPrefix(p);

            if (out == null) {
                return "";
            }

            return out;
        } catch (Exception e) {
            SimpleClans.debug(Level.SEVERE, "Failed to get prefix! (Problem with Vault/mChatSuite!)");
            return "";
        }

//        try {
//            if (chat != null) {
//                out = chat.getPlayerPrefix(p);
//            }
//        } catch (Exception ex) {
//            // yea vault kinda sucks like that
//        }
//
//        if (permission != null && chat != null) {
//            //try {
//            String world = p.getWorld().getName();
//            String name = p.getName();
//            String prefix = chat.getPlayerPrefix(name, world);
//            if (prefix == null || prefix.isEmpty()) {
//                String group = permission.getPrimaryGroup(world, name);
//                prefix = chat.getGroupPrefix(world, group);
//                if (prefix == null) {
//                    prefix = "";
//                }
//            }
//
//            out = prefix.replace("&", "\u00a7").replace(String.valueOf((char) 194), "");
//            //} catch (Exception e) {
//
//            //}
//        }

//        return out;
    }

    public String getSuffix(Player p)
    {
        String out;

        try {
            out = chat.getPlayerSuffix(p);

            if (out == null) {
                return "";
            }

            return out;
        } catch (Exception e) {
            SimpleClans.debug(Level.SEVERE, "Failed to get suffix! (Problem with Vault/mChatSuite!)");
            return "";
        }

//        try {
//            if (chat != null) {
//                return chat.getPlayerSuffix(p);
//            }
//        } catch (Exception ex) {
//            // yea vault kinda sucks like that
//        }
//
//        if (permission != null && chat != null) {
////            try {
//            String world = p.getWorld().getName();
//            String name = p.getName();
//            String suffix = chat.getPlayerSuffix(world, name);
//            if (suffix == null || suffix.isEmpty()) {
//                String group = permission.getPrimaryGroup(world, name);
//                suffix = chat.getPlayerSuffix(world, group);
//                if (suffix == null) {
//                    suffix = "";
//                }
//            }
//            return suffix.replace("&", "\u00a7").replace(String.valueOf((char) 194), "");
////            } catch (Exception e) {
////                System.out.println(e.getMessage());
////                return "";
////            }
//        }
//        return "";
    }
}
