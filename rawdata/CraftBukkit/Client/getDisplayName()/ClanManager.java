package net.sacredlabyrinth.phaed.simpleclans.managers;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

import net.sacredlabyrinth.phaed.simpleclans.*;
import net.sacredlabyrinth.phaed.simpleclans.api.events.SimpleClansClanCreateEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author phaed
 */
public final class ClanManager {

    private SimpleClans plugin;
    private HashMap<String, Clan> clans = new HashMap<String, Clan>();
    private HashMap<String, ClanPlayer> clanPlayers = new HashMap<String, ClanPlayer>();

    /**
     *
     */
    public ClanManager(SimpleClans plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Deletes all clans and clan players in memory
     */
    public void cleanData()
    {
        clans.clear();
        clanPlayers.clear();
    }

    /**
     * Import a clan into the in-memory store
     *
     * @param clan
     */
    public void importClan(Clan clan)
    {
        this.clans.put(clan.getTag(), clan);
    }

    /**
     * Import a clan player into the in-memory store
     *
     * @param cp
     */
    public void importClanPlayer(ClanPlayer cp)
    {
        this.clanPlayers.put(cp.getCleanName(), cp);
    }

    /**
     * Create a new clan
     *
     * @param player
     * @param colorTag
     * @param name
     */
    public void createClan(Player player, String colorTag, String name)
    {
        ClanPlayer cp = getCreateClanPlayer(player.getName());

        boolean verified = !plugin.getSettingsManager().isRequireVerification() || plugin.getPermissionsManager().has(player, "simpleclans.mod.verify");

        Clan clan = new Clan(plugin, colorTag, name, verified);
        clan.addPlayerToClan(cp);
        cp.setLeader(true);

        plugin.getStorageManager().insertClan(clan);
        importClan(clan);
        plugin.getStorageManager().updateClanPlayer(cp);

        plugin.getPermissionsManager().updateClanPermissions(clan);

        plugin.getServer().getPluginManager().callEvent(new SimpleClansClanCreateEvent(cp, clan));

        if (plugin.hasSpout()) {
            plugin.getSpoutPluginManager().processPlayer(player.getName());
        }
    }

    /**
     * Returns a clan at a specific location
     *
     * @param world
     * @param x
     * @param z
     * @return
     */
    public Clan getClanAt(World world, int x, int z)
    {
        return getClanAt(new ChunkLocation(world.getName(), x, z, true));
    }

    /**
     * Returns a clan at a specific location
     *
     * @param loc
     * @return
     */
    public Clan getClanAt(Location loc)
    {
        return getClanAt(loc.getWorld(), loc.getBlockX(), loc.getBlockZ());
    }

    /**
     * Returns a clan at a specific location
     *
     * @param chunk
     * @return
     */
    public Clan getClanAt(ChunkLocation chunk)
    {
        for (Clan clans1 : plugin.getClanManager().getClans()) {
            if (clans1.isClaimed(chunk)) {
                return clans1;
            }
        }
        return null;
    }

    /**
     * Returns if the location is claimed
     *
     * @param world
     * @param x
     * @param z
     * @return
     */
    public boolean isClaimed(World world, int x, int z)
    {
        return getClanAt(world, x, z) != null;
    }

    /**
     * Resets the kdr of all players (!Warning! clears all deaths/kills)
     */
    public void resetKDRs()
    {
        for (ClanPlayer cp : getAllClanPlayers()) {
            cp.setDeaths(0);
            cp.setNeutralKills(0);
            cp.setCivilianKills(0);
            cp.setRivalKills(0);
            plugin.getStorageManager().updateClanPlayer(cp);
        }
    }

    /**
     * Resets the kdr of a player (!Warning! clears all deaths/kills)
     *
     * @param cp
     */
    public void resetKDR(ClanPlayer cp)
    {
        cp.setDeaths(0);
        cp.setNeutralKills(0);
        cp.setCivilianKills(0);
        cp.setRivalKills(0);
        plugin.getStorageManager().updateClanPlayer(cp);
    }

    /**
     * Delete a players data file
     *
     * @param cp
     */
    public void deleteClanPlayer(ClanPlayer cp)
    {
        clanPlayers.remove(cp.getCleanName());
        plugin.getStorageManager().deleteClanPlayer(cp);
    }

    /**
     * Remove a clan from memory
     *
     * @param tag
     */
    public void removeClan(String tag)
    {
        clans.remove(tag);
    }

    /**
     * Whether the tag belongs to a clan
     *
     * @param tag
     * @return
     */
    public boolean isClan(String tag)
    {
        return clans.containsKey(Helper.cleanTag(tag));

    }

    /**
     * Returns the clan the tag belongs to
     *
     * @param tag
     * @return
     */
    public Clan getClan(String tag)
    {
        return clans.get(Helper.cleanTag(tag));
    }

    /**
     * Get a player's clan
     *
     * @param playerName
     * @return null if not in a clan
     */
    public Clan getClanByPlayerName(String playerName)
    {
        ClanPlayer cp = getClanPlayer(playerName);

        if (cp != null) {
            return cp.getClan();
        }

        return null;
    }

    /**
     * @return the clans
     */
    public List<Clan> getClans()
    {
        return new ArrayList<Clan>(clans.values());
    }

    /**
     * Returns the collection of all clan players, including the disabled ones
     *
     * @return
     */
    public List<ClanPlayer> getAllClanPlayers()
    {
        return new ArrayList<ClanPlayer>(clanPlayers.values());
    }

    /**
     * Returns the collection of all online clan players, including the disabled
     * ones
     *
     * @return
     */
    public Set<ClanPlayer> getAllOnlineClanPlayers()
    {
        Set<ClanPlayer> onlinePlayers = new HashSet<ClanPlayer>();
        for (ClanPlayer clanPlayer : clanPlayers.values()) {
            if (clanPlayer.isOnline()) {
                onlinePlayers.add(clanPlayer);
            }
        }
        return onlinePlayers;
    }

    /**
     * Gets the ClanPlayer data object if a player is currently in a clan, null
     * if he's not in a clan
     *
     * @param player
     * @return
     */
    public ClanPlayer getClanPlayer(Player player)
    {
        return getClanPlayer(player.getName());
    }

    /**
     * Gets the ClanPlayer data object if a player is currently in a clan, null
     * if he's not in a clan
     *
     * @param playerName
     * @return
     */
    public ClanPlayer getClanPlayer(String playerName)
    {
        ClanPlayer cp = clanPlayers.get(playerName.toLowerCase());

        if (cp == null) {
            return null;
        }

        if (cp.getClan() == null) {
            return null;
        }

        return cp;
    }

    /**
     * Gets the ClanPlayer data object for the player, will retrieve disabled
     * clan players as well, these are players who used to be in a clan but are
     * not currently in one, their data file persists and can be accessed. their
     * clan will be null though.
     *
     * @param playerName
     * @return
     */
    public ClanPlayer getAnyClanPlayer(String playerName)
    {
        return clanPlayers.get(playerName.toLowerCase());
    }

    /**
     * Gets the ClanPlayer object for the player, creates one if not found
     *
     * @param playerName
     * @return
     */
    public ClanPlayer getCreateClanPlayer(String playerName)
    {
        if (clanPlayers.containsKey(playerName.toLowerCase())) {
            return clanPlayers.get(playerName.toLowerCase());
        }

        ClanPlayer cp = new ClanPlayer(plugin, playerName);

        plugin.getStorageManager().insertClanPlayer(cp);
        importClanPlayer(cp);

        return cp;
    }

    /**
     * Announce message to the server
     *
     * @param msg
     */
    public void serverAnnounce(String msg)
    {
        Player[] players = plugin.getServer().getOnlinePlayers();

        for (Player player : players) {
            ChatBlock.sendMessage(player, ChatColor.DARK_GRAY + "* " + ChatColor.AQUA + msg);
        }

        SimpleClans.log(ChatColor.AQUA + "[" + plugin.getLang("server.announce") + "] " + ChatColor.WHITE + msg);
    }

    /**
     * Update the players display name with his clan's tag
     *
     * @param player
     */
    public void updateDisplayName(Player player)
    {
        // do not update displayname if in compat mode

        if (plugin.getSettingsManager().isCompatMode()) {
            return;
        }

        if (player == null) {
            return;
        }

        if (plugin.getSettingsManager().isChatTags()) {
            String lastColor = plugin.getSettingsManager().isUseColorCodeFromPrefix() ? Helper.getLastColorCode(plugin.getPermissionsManager().getPrefix(player)) : ChatColor.WHITE.toString();
            String fullName = player.getName();

            ClanPlayer cp = plugin.getClanManager().getAnyClanPlayer(player.getName());

            if (plugin.getSettingsManager().ismChatIntegration() && plugin.getPermissionsManager().getMChat() != null) {
                if (cp == null) {
                    plugin.getPermissionsManager().clearSetMChatClanTag(player);
                    return;
                }

                if (cp.isTagEnabled()) {
                    Clan clan = cp.getClan();

                    if (clan != null) {
                        plugin.getPermissionsManager().addSetMChatClanTag(player, clan.getTagLabel());
                    } else {
                        plugin.getPermissionsManager().clearSetMChatClanTag(player);
                    }
                } else {
                    plugin.getPermissionsManager().clearSetMChatClanTag(player);
                }
            } else {
                if (cp == null) {
                    return;
                }

                if (cp.isTagEnabled()) {
                    Clan clan = cp.getClan();

                    if (clan != null) {
                        fullName = clan.getTagLabel() + lastColor + fullName + ChatColor.WHITE;
                    }

                    player.setDisplayName(fullName);
                } else {
                    player.setDisplayName(lastColor + fullName + ChatColor.WHITE);
                }
            }
        }
    }

    /**
     * Process a player and his clan's last seen date
     *
     * @param player
     */
    public void updateLastSeen(Player player)
    {
        ClanPlayer cp = getAnyClanPlayer(player.getName());

        if (cp != null) {

            plugin.getStorageManager().updateClanPlayer(cp);

            Clan clan = cp.getClan();

            if (clan != null) {
                plugin.getStorageManager().updateClan(clan);
            }
        }
    }

    /**
     * Bans a player
     *
     * @param playerName
     */
    public void ban(String playerName)
    {
        ClanPlayer cp = getClanPlayer(playerName);
        Clan clan = cp.getClan();

        if (clan != null) {
            if (clan.getSize() == 1) {
                clan.disband();
            } else {
                cp.setClan(null);
                cp.addPastClan(clan.getColorTag() + (cp.isLeader() ? ChatColor.DARK_RED + "*" : ""));
                cp.setLeader(false);
                cp.setJoinDate(0);
                clan.removeMember(playerName);

                plugin.getStorageManager().updateClanPlayer(cp);
                plugin.getStorageManager().updateClan(clan);
            }
        }

        plugin.getSettingsManager().addBanned(playerName);
    }

    /**
     * Get a count of rivable clans
     *
     * @return
     */
    public int getRivableClanCount()
    {
        int clanCount = 0;

        for (Clan tm : clans.values()) {
            if (!plugin.getSettingsManager().isUnrivable(tm.getTag())) {
                clanCount++;
            }
        }

        return clanCount;
    }

    /**
     * Returns a formatted string detailing the players armor
     *
     * @param inv
     * @return
     */
    public String getArmorString(PlayerInventory inv)
    {
        String out = "";

        ItemStack h = inv.getHelmet();
        Material htype;

        try {
            htype = h.getType();
        } catch (Exception ex) {
            htype = null;
        }

        if (htype == null) {
            out += ChatColor.BLACK + plugin.getLang("armor.h");
        } else if (htype.equals(Material.CHAINMAIL_HELMET)) {
            out += ChatColor.WHITE + plugin.getLang("armor.h");
        } else if (htype.equals(Material.DIAMOND_HELMET)) {
            out += ChatColor.AQUA + plugin.getLang("armor.h");
        } else if (htype.equals(Material.GOLD_HELMET)) {
            out += ChatColor.YELLOW + plugin.getLang("armor.h");
        } else if (htype.equals(Material.IRON_HELMET)) {
            out += ChatColor.GRAY + plugin.getLang("armor.h");
        } else if (htype.equals(Material.LEATHER_HELMET)) {
            out += ChatColor.GOLD + plugin.getLang("armor.h");
        } else {
            out += ChatColor.RED + plugin.getLang("armor.h");
        }

        ItemStack c = inv.getChestplate();
        Material ctype;

        try {
            ctype = c.getType();
        } catch (Exception ex) {
            ctype = null;
        }

        if (ctype == null) {
            out += ChatColor.BLACK + plugin.getLang("armor.c");
        } else if (ctype.equals(Material.CHAINMAIL_CHESTPLATE)) {
            out += ChatColor.WHITE + plugin.getLang("armor.c");
        } else if (ctype.equals(Material.DIAMOND_CHESTPLATE)) {
            out += ChatColor.AQUA + plugin.getLang("armor.c");
        } else if (ctype.equals(Material.GOLD_CHESTPLATE)) {
            out += ChatColor.YELLOW + plugin.getLang("armor.c");
        } else if (ctype.equals(Material.IRON_CHESTPLATE)) {
            out += ChatColor.GRAY + plugin.getLang("armor.c");
        } else if (ctype.equals(Material.LEATHER_CHESTPLATE)) {
            out += ChatColor.GOLD + plugin.getLang("armor.c");
        } else {
            out += ChatColor.RED + plugin.getLang("armor.c");
        }

        ItemStack l = inv.getLeggings();
        Material ltype;

        try {
            ltype = l.getType();
        } catch (Exception ex) {
            ltype = null;
        }

        if (ltype == null) {
            out += ChatColor.BLACK + plugin.getLang("armor.l");
        } else if (ltype.equals(Material.CHAINMAIL_LEGGINGS)) {
            out += ChatColor.WHITE + plugin.getLang("armor.l");
        } else if (ltype.equals(Material.DIAMOND_LEGGINGS)) {
            out += ChatColor.AQUA + plugin.getLang("armor.l");
        } else if (ltype.equals(Material.GOLD_LEGGINGS)) {
            out += ChatColor.YELLOW + plugin.getLang("armor.l");
        } else if (ltype.equals(Material.IRON_LEGGINGS)) {
            out += ChatColor.GRAY + plugin.getLang("armor.l");
        } else if (ltype.equals(Material.LEATHER_LEGGINGS)) {
            out += ChatColor.GOLD + plugin.getLang("armor.l");
        } else {
            out += ChatColor.RED + plugin.getLang("armor.l");
        }

        ItemStack b = inv.getBoots();
        Material btype;

        try {
            btype = b.getType();
        } catch (Exception ex) {
            btype = null;
        }

        if (btype == null) {
            out += ChatColor.BLACK + plugin.getLang("armor.B");
        } else if (btype.equals(Material.CHAINMAIL_BOOTS)) {
            out += ChatColor.WHITE + plugin.getLang("armor.B");
        } else if (btype.equals(Material.DIAMOND_BOOTS)) {
            out += ChatColor.AQUA + plugin.getLang("armor.B");
        } else if (btype.equals(Material.GOLD_BOOTS)) {
            out += ChatColor.YELLOW + plugin.getLang("armor.B");
        } else if (btype.equals(Material.IRON_BOOTS)) {
            out += ChatColor.GRAY + plugin.getLang("armor.B");
        } else if (btype.equals(Material.LEATHER_BOOTS)) {
            out += ChatColor.GOLD + plugin.getLang("armor.B");
        } else {
            out += ChatColor.RED + plugin.getLang("armor.B");
        }

        return out;
    }

    /**
     * Returns a formatted string detailing the players weapons
     *
     * @param inv
     * @return
     */
    public String getWeaponString(PlayerInventory inv)
    {
        String headColor = plugin.getSettingsManager().getPageHeadingsColor();

        String out = "";

        int count = getItemCount(inv.all(Material.DIAMOND_SWORD));

        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            out += ChatColor.AQUA + plugin.getLang("weapon.S") + headColor + countString;
        }

        count = getItemCount(inv.all(Material.GOLD_SWORD));

        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            out += ChatColor.YELLOW + plugin.getLang("weapon.S") + headColor + countString;
        }

        count = getItemCount(inv.all(Material.IRON_SWORD));

        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            out += ChatColor.WHITE + plugin.getLang("weapon.S") + headColor + countString;
        }

        count = getItemCount(inv.all(Material.STONE_SWORD));

        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            out += ChatColor.GRAY + plugin.getLang("weapon.S") + headColor + countString;
        }

        count = getItemCount(inv.all(Material.WOOD_SWORD));

        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            out += ChatColor.GOLD + plugin.getLang("weapon.S") + headColor + countString;
        }

        count = getItemCount(inv.all(Material.BOW));

        if (count > 0) {
            String countString = count > 1 ? count + "" : "";
            out += ChatColor.GOLD + plugin.getLang("weapon.B") + headColor + countString;
        }

        count = getItemCount(inv.all(Material.ARROW));

        if (count > 0) {
            out += ChatColor.GOLD + plugin.getLang("weapon.A") + headColor + count;
        }

        if (out.length() == 0) {
            out = ChatColor.BLACK + "None";
        }

        return out;
    }

    private int getItemCount(HashMap<Integer, ? extends ItemStack> all)
    {
        int count = 0;

        for (ItemStack is : all.values()) {
            count += is.getAmount();
        }

        return count;
    }

    /**
     * Returns a formatted string detailing the players food
     *
     * @param inv
     * @return
     */
    public String getFoodString(PlayerInventory inv)
    {
        double out = 0;

        int count = getItemCount(inv.all(320)); // cooked porkchop

        if (count > 0) {
            out += count * 4;
        }

        count = getItemCount(inv.all(Material.COOKED_FISH));

        if (count > 0) {
            out += count * 3;
        }

        count = getItemCount(inv.all(Material.COOKIE));

        if (count > 0) {
            out += count * 1;
        }

        count = getItemCount(inv.all(Material.CAKE));

        if (count > 0) {
            out += count * 6;
        }

        count = getItemCount(inv.all(Material.CAKE_BLOCK));

        if (count > 0) {
            out += count * 9;
        }

        count = getItemCount(inv.all(Material.MUSHROOM_SOUP));

        if (count > 0) {
            out += count * 4;
        }

        count = getItemCount(inv.all(Material.BREAD));

        if (count > 0) {
            out += count * 3;
        }

        count = getItemCount(inv.all(Material.APPLE));

        if (count > 0) {
            out += count * 2;
        }

        count = getItemCount(inv.all(Material.GOLDEN_APPLE));

        if (count > 0) {
            out += count * 5;
        }

        count = getItemCount(inv.all(Material.RAW_BEEF));

        if (count > 0) {
            out += count * 2;
        }

        count = getItemCount(inv.all(364));  // steak

        if (count > 0) {
            out += count * 4;
        }

        count = getItemCount(inv.all(319)); // raw porkchop

        if (count > 0) {
            out += count * 2;
        }

        count = getItemCount(inv.all(Material.RAW_CHICKEN));

        if (count > 0) {
            out += count * 1;
        }

        count = getItemCount(inv.all(Material.COOKED_CHICKEN));

        if (count > 0) {
            out += count * 3;
        }

        count = getItemCount(inv.all(Material.ROTTEN_FLESH));

        if (count > 0) {
            out += count * 2;
        }

        count = getItemCount(inv.all(360));  // melon slice

        if (count > 0) {
            out += count * 2;
        }

        if (out == 0) {
            return ChatColor.BLACK + plugin.getLang("none");
        } else {
            return new DecimalFormat("#.#").format(out) + "" + ChatColor.GOLD + "h";
        }
    }

    /**
     * Returns a formatted string detailing the players health
     *
     * @param health
     * @return
     */
    public String getHealthString(int health)
    {
        String out = "";

        if (health >= 16) {
            out += ChatColor.GREEN;
        } else if (health >= 8) {
            out += ChatColor.GOLD;
        } else {
            out += ChatColor.RED;
        }

        for (int i = 0; i < health; i++) {
            out += '|';
        }

        return out;
    }

    /**
     * Returns a formatted string detailing the players hunger
     *
     * @param health
     * @return
     */
    public String getHungerString(int health)
    {
        String out = "";

        if (health >= 16) {
            out += ChatColor.GREEN;
        } else if (health >= 8) {
            out += ChatColor.GOLD;
        } else {
            out += ChatColor.RED;
        }

        for (int i = 0; i < health; i++) {
            out += '|';
        }

        return out;
    }

    /**
     * Sort clans by KDR
     *
     * @param clans
     * @return
     */
    public void sortClansByKDR(List<Clan> clans)
    {
        Collections.sort(clans, new Comparator<Clan>() {

            @Override
            public int compare(Clan c1, Clan c2)
            {
                Float o1 = c1.getTotalKDR();
                Float o2 = c2.getTotalKDR();

                return o2.compareTo(o1);
            }
        });
    }

    /**
     * Sort clan players by Health. (Goes up from 0 health)
     *
     * @param players
     * @return
     */
    public void sortClanPlayersByHealth(List<ClanPlayer> players)
    {
        Collections.sort(players, new Comparator<ClanPlayer>() {

            @Override
            public int compare(ClanPlayer c1, ClanPlayer c2)
            {
                Player p = c1.toPlayer();
                Player p2 = c2.toPlayer();
                if (p == null || p2 == null) {
                    return -1;
                }

                Integer h = p.getHealth();
                Integer h2 = p2.getHealth();
                return h.compareTo(h2);
            }
        });
    }

    /**
     * Sort clan players by KDR
     *
     * @param cps
     * @return
     */
    public void sortClanPlayersByKDR(List<ClanPlayer> cps)
    {
        Collections.sort(cps, new Comparator<ClanPlayer>() {

            @Override
            public int compare(ClanPlayer c1, ClanPlayer c2)
            {
                Float o1 = c1.getKDR();
                Float o2 = c2.getKDR();

                return o2.compareTo(o1);
            }
        });
    }

    /**
     * Sort clan players by last seen days
     *
     * @param cps
     * @return
     */
    public void sortClanPlayersByLastSeen(List<ClanPlayer> cps)
    {
        Collections.sort(cps, new Comparator<ClanPlayer>() {

            @Override
            public int compare(ClanPlayer c1, ClanPlayer c2)
            {
                Double o1 = c1.getLastSeenDays();
                Double o2 = c2.getLastSeenDays();

                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Purchase clan creation
     *
     * @param player
     * @return
     */
    public boolean purchaseCreation(Player player)
    {
        if (!plugin.getSettingsManager().isePurchaseCreation()) {
            return true;
        }

        double price = plugin.getSettingsManager().getCreationPrice();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Purchase invite
     *
     * @param player
     * @return
     */
    public boolean purchaseInvite(Player player)
    {
        if (!plugin.getSettingsManager().isePurchaseInvite()) {
            return true;
        }

        double price = plugin.getSettingsManager().getInvitePrice();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Purchase Rally-point Teleport
     *
     * @param player
     * @return
     */
    public boolean purchaseRallyPointTeleport(Player player)
    {
        if (!plugin.getSettingsManager().isRallyTeleportPurchase()) {
            return true;
        }

        double price = plugin.getSettingsManager().getRallyTeleportPrice();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Purchase Rally-point Teleport Set
     *
     * @param player
     * @return
     */
    public boolean purchaseRallyPointTeleportSet(Player player)
    {
        if (!plugin.getSettingsManager().isRallyTeleportSetPurchase()) {
            return true;
        }

        double price = plugin.getSettingsManager().getRallyTeleportSetPrice();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Purchase Home Teleport
     *
     * @param player
     * @return
     */
    public boolean purchaseHomeTeleport(Player player)
    {
        if (!plugin.getSettingsManager().isePurchaseHomeTeleport()) {
            return true;
        }

        double price = plugin.getSettingsManager().getHomeTeleportPrice();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Purchase Home Teleport Set
     *
     * @param player
     * @return
     */
    public boolean purchaseHomeTeleportSet(Player player)
    {
        if (!plugin.getSettingsManager().isePurchaseHomeTeleportSet()) {
            return true;
        }

        double price = plugin.getSettingsManager().getHomeTeleportPriceSet();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Purchase clan verification
     *
     * @param player
     * @return
     */
    public boolean purchaseVerification(Player player)
    {
        if (!plugin.getSettingsManager().isePurchaseVerification()) {
            return true;
        }

        double price = plugin.getSettingsManager().getVerificationPrice();

        if (plugin.getPermissionsManager().hasEconomy()) {
            if (plugin.getPermissionsManager().playerHasMoney(player, price)) {
                plugin.getPermissionsManager().playerChargeMoney(player, price);
                player.sendMessage(ChatColor.RED + MessageFormat.format(plugin.getLang("account.has.been.debited"), price));
            } else {
                player.sendMessage(ChatColor.RED + plugin.getLang("not.sufficient.money"));
                return false;
            }
        }

        return true;
    }

    /**
     * Processes a clan chat command
     *
     * @param player
     * @param msg
     */
    public void processClanChat(Player player, String tag, String msg)
    {
        Clan clan = plugin.getClanManager().getClan(tag);

        if (!clan.isMember(player)) {
            return;
        }

        processClanChat(player, msg);
    }

    /**
     * Processes a clan chat command
     *
     * @param msg
     */
    public void processClanChat(Player player, String msg)
    {
        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player.getName());

        if (cp == null) {
            return;
        }

        String[] split = msg.split(" ");

        if (split.length == 0) {
            return;
        }

        String command = split[0];

        if (command.equals(plugin.getLang("on"))) {
            cp.setClanChat(true);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have enabled clan chat");
        } else if (command.equals(plugin.getLang("off"))) {
            cp.setClanChat(false);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have disabled clan chat");
        } else if (command.equals(plugin.getLang("join"))) {
            cp.setChannel(ClanPlayer.Channel.CLAN);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have joined clan chat");
        } else if (command.equals(plugin.getLang("leave"))) {
            cp.setChannel(ClanPlayer.Channel.NONE);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have left clan chat");
        } else {
            String code = ChatColor.RED.toString() + ChatColor.WHITE + ChatColor.RED + ChatColor.BLACK;
            String tag;

            if (cp.getRank() != null && !cp.getRank().isEmpty()) {
                tag = plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketLeft() + plugin.getSettingsManager().getClanChatRankColor() + cp.getRank() + plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketRight() + " ";
            } else {
                tag = plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketLeft() + plugin.getSettingsManager().getTagDefaultColor() + cp.getClan().getColorTag() + plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketRight() + " ";
            }

            String message = code + Helper.parseColors(tag) + plugin.getSettingsManager().getClanChatNameColor() + plugin.getSettingsManager().getClanChatPlayerBracketLeft() + player.getDisplayName() + plugin.getSettingsManager().getClanChatPlayerBracketRight() + " " + plugin.getSettingsManager().getClanChatMessageColor() + msg;
            String eyeMessage = code + plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketLeft() + plugin.getSettingsManager().getTagDefaultColor() + cp.getClan().getColorTag() + plugin.getSettingsManager().getClanChatBracketColor() + plugin.getSettingsManager().getClanChatTagBracketRight() + " " + plugin.getSettingsManager().getClanChatNameColor() + plugin.getSettingsManager().getClanChatPlayerBracketLeft() + player.getDisplayName() + plugin.getSettingsManager().getClanChatPlayerBracketRight() + " " + plugin.getSettingsManager().getClanChatMessageColor() + msg;

            plugin.getServer().getConsoleSender().sendMessage(eyeMessage);

            List<ClanPlayer> cps = cp.getClan().getMembers();

            for (ClanPlayer cpp : cps) {
                Player member = plugin.getServer().getPlayer(cpp.getName());

                ChatBlock.sendMessage(member, message);
            }

            sendToAllSeeing(eyeMessage, cps);
        }
    }

    public void sendToAllSeeing(String msg, List<ClanPlayer> cps)
    {
        Player[] players = plugin.getServer().getOnlinePlayers();

        for (Player player : players) {
            if (plugin.getPermissionsManager().has(player, "simpleclans.admin.all-seeing-eye")) {
                if (plugin.getClanManager().getAnyClanPlayer(player.getName()) != null) {
                    boolean alreadySent = false;

                    for (ClanPlayer cpp : cps) {
                        if (cpp.getName().equalsIgnoreCase(player.getName())) {
                            alreadySent = true;
                        }
                    }

                    if (!alreadySent) {
                        ChatBlock.sendMessage(player, ChatColor.DARK_GRAY + Helper.stripColors(msg));
                    }
                }
            }
        }
    }

    /**
     * Processes a ally chat command
     *
     * @param msg
     */
    public void processAllyChat(Player player, String msg)
    {
        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player);

        if (cp == null) {
            return;
        }

        String[] split = msg.split(" ");

        if (split.length == 0) {
            return;
        }

        String command = split[0];

        if (command.equals(plugin.getLang("on"))) {
            cp.setAllyChat(true);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have enabled ally chat");
        } else if (command.equals(plugin.getLang("off"))) {
            cp.setAllyChat(false);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have disabled ally chat");
        } else if (command.equals(plugin.getLang("join"))) {
            cp.setChannel(ClanPlayer.Channel.ALLY);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have joined ally chat");
        } else if (command.equals(plugin.getLang("leave"))) {
            cp.setChannel(ClanPlayer.Channel.NONE);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have left ally chat");
        } else {
            String code = "" + ChatColor.AQUA + ChatColor.WHITE + ChatColor.AQUA + ChatColor.BLACK;
            String message = code + plugin.getSettingsManager().getAllyChatBracketColor() + plugin.getSettingsManager().getAllyChatTagBracketLeft() + plugin.getSettingsManager().getAllyChatTagColor() + plugin.getSettingsManager().getCommandAlly() + plugin.getSettingsManager().getAllyChatBracketColor() + plugin.getSettingsManager().getAllyChatTagBracketRight() + " " + plugin.getSettingsManager().getAllyChatNameColor() + plugin.getSettingsManager().getAllyChatPlayerBracketLeft() + player.getDisplayName() + plugin.getSettingsManager().getAllyChatPlayerBracketRight() + " " + plugin.getSettingsManager().getAllyChatMessageColor() + msg;
            SimpleClans.log(message);

            Player self = plugin.getServer().getPlayer(player.getName());
            ChatBlock.sendMessage(self, message);

            Set<ClanPlayer> allies = cp.getClan().getAllAllyMembers();
            allies.addAll(cp.getClan().getMembers());

            for (ClanPlayer ally : allies) {
                if (player.getName().equalsIgnoreCase(ally.getName())) {
                    continue;
                }

                Player member = plugin.getServer().getPlayer(ally.getName());
                ChatBlock.sendMessage(member, message);
            }
        }
    }

    /**
     * Processes a global chat command
     *
     * @param msg
     */
    public boolean processGlobalChat(Player player, String msg)
    {
        ClanPlayer cp = plugin.getClanManager().getClanPlayer(player.getName());

        if (cp == null) {
            return false;
        }

        String[] split = msg.split(" ");

        if (split.length == 0) {
            return false;
        }

        String command = split[0];

        if (command.equals(plugin.getLang("on"))) {
            cp.setGlobalChat(true);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have enabled global chat");
        } else if (command.equals(plugin.getLang("off"))) {
            cp.setGlobalChat(false);
            plugin.getStorageManager().updateClanPlayer(cp);
            ChatBlock.sendMessage(player, ChatColor.AQUA + "You have disabled global chat");
        } else {
            return true;
        }

        return false;
    }
}
