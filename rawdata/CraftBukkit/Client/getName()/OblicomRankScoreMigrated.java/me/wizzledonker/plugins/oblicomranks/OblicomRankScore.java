/* To change this template, choose Tools | Templates
and open the template in the editor.
 */
package me.wizzledonker.plugins.oblicomranks;
/**
 *
 *
 * @author Winfried
 */
public class OblicomRankScore {
    private static me.wizzledonker.plugins.oblicomranks.OblicomRanks plugin;

    private java.io.File scoreConfigFile = null;

    private org.bukkit.configuration.file.FileConfiguration scoreConfig = null;

    public OblicomRankScore(me.wizzledonker.plugins.oblicomranks.OblicomRanks instance) {
        me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin = instance;
    }

    private void reloadScoreConfig() {
        if (scoreConfigFile == null) {
            scoreConfigFile = new java.io.File(me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getDataFolder(), "scores.yml");
        }
        scoreConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(scoreConfigFile);
    }

    private org.bukkit.configuration.file.FileConfiguration getScoreConfig() {
        if (scoreConfig == null) {
            reloadScoreConfig();
        }
        return scoreConfig;
    }

    private void saveScoreConfig() {
        if ((scoreConfigFile == null) || (scoreConfig == null)) {
            return;
        }
        try {
            scoreConfig.save(scoreConfigFile);
        } catch (java.io.IOException ex) {
            me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.log("oops! Error saving score config file. Here's the details: " + ex.toString());
        }
    }

    public int getScore(java.lang.String player) {
        return getScoreConfig().getInt(player + ".score");
    }

    public void addScore(int add, org.bukkit.entity.Player player) {
        player.sendMessage(((org.bukkit.ChatColor.GREEN + "+") + add) + " Points");
        if () {
            add = getScore(store.loadAchievements(mPlayer)) + add;
        }
        setScore(add, player);
    }

    public void subtractScore(int subtract, org.bukkit.entity.Player player) {
        player.sendMessage(((org.bukkit.ChatColor.RED + "-") + subtract) + " Points");
        if (getScoreConfig().contains(player.getName())) {
            subtract = getScore(player.getName()) - subtract;
        }
        setScore(subtract, player);
    }

    public void setScore(int score, org.bukkit.entity.Player player) {
        getScoreConfig().set(player.getName() + ".score", score);
        saveScoreConfig();
        updateRank(player);
    }

    public java.lang.String determineFaction(org.bukkit.entity.Player player) {
        java.lang.String group = me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.permission.getPrimaryGroup(player);
        return me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getFaction(group);
    }

    public java.lang.String getRankInfo(org.bukkit.entity.Player player) {
        // Update the player's rank
        updateRank(player);
        // Form the information string
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(org.bukkit.ChatColor.GREEN).append(determineFaction(player)).append(" rank progress\n");
        sb.append(org.bukkit.ChatColor.RED).append(getRank(player)).append(org.bukkit.ChatColor.GOLD).append(" -");
        for (int i = 0; i < 50; ++i) {
            if (i == java.lang.Math.round(getProgressTowardsNextRank(player) * 50)) {
                sb.append(org.bukkit.ChatColor.DARK_RED);
            }
            sb.append('|');
        }
        sb.append(org.bukkit.ChatColor.GOLD).append("- ").append(org.bukkit.ChatColor.GREEN).append(me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getNextRank(determineFaction(player), getRank(player))).append("\n");
        sb.append(org.bukkit.ChatColor.AQUA).append("The next rank grants you: \n").append(org.bukkit.ChatColor.DARK_GREEN);
        if (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getNextRank(determineFaction(player), getRank(player)), "permissions") != null) {
            for (java.lang.String perm : ((java.util.List<java.lang.String>) (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getNextRank(determineFaction(player), getRank(player)), "permissions")))) {
                sb.append("- ").append(perm).append("\n");
            }
        } else {
            sb.append("- Nothing");
        }
        return sb.toString();
    }

    public void updateRank(org.bukkit.entity.Player player) {
        setRank(player, getRank(player));
    }

    public java.lang.String getRank(org.bukkit.entity.Player player) {
        int prev = 0;
        if (getScore(player.getName()) < prev) {
            return me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getLowestRank(determineFaction(player));
        }
        for (java.util.Iterator<java.lang.Integer> it = me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRanks(determineFaction(player)).keySet().iterator(); it.hasNext();) {
            int current = it.next();
            if (getScore(player.getName()) == current) {
                return me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRanks(determineFaction(player)).get(current);
            }
            if (getScore(player.getName()) < current) {
                if (getScore(player.getName()) > prev) {
                    return me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRanks(determineFaction(player)).get(prev);
                }
            } else if (((java.lang.Integer) (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getHighestRank(determineFaction(player)), "score"))) == current) {
                return me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRanks(determineFaction(player)).get(current);
            }
            prev = current;
        }
        return me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRanks(determineFaction(player)).get(prev);
    }

    public float getProgressTowardsNextRank(org.bukkit.entity.Player player) {
        // Problem is here, decimal is returning 0.0
        // Fixed and edited!
        float playerScore = ((float) (getScore(player.getName())));
        float playerRank = ((float) ((java.lang.Integer) (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(getRank(player), "score"))));
        float nextPlayerRank = ((float) ((java.lang.Integer) (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getNextRank(determineFaction(player), getRank(player)), "score"))));
        float finalArith = (playerScore - playerRank) / (nextPlayerRank - playerRank);
        return finalArith;
    }

    private void setRank(org.bukkit.entity.Player player, java.lang.String rank) {
        java.lang.String prePrefix = determinePrefix(player);
        // Set the prefix for the player
        me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.chat.setPlayerPrefix(player, ((java.lang.String) (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(rank, "prefix"))));
        // Now that the easy part is done, Do the permissions!
        for (java.lang.String perm : me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getAllRankPermissions()) {
            if (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.permission.playerHas(player, perm)) {
                me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.permission.playerRemove(player, perm);
            }
        }
        if (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(rank, "permissions") != null) {
            for (java.lang.String perm : ((java.util.List<java.lang.String>) (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.getRankVariable(rank, "permissions")))) {
                me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.permission.playerAdd(player, perm);
            }
        }
        if (prePrefix != determinePrefix(player)) {
            player.sendMessage(((org.bukkit.ChatColor.GREEN + "Your rank has changed to ") + org.bukkit.ChatColor.GOLD) + rank);
            me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.log((("Player " + player.getName()) + " was changed to ") + rank);
        }
    }

    private java.lang.String determinePrefix(org.bukkit.entity.Player player) {
        java.lang.String finalPrefix = "";
        if (me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.chat.getPlayerPrefix(player) != "") {
            finalPrefix = me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.chat.getPlayerPrefix(player);
        } else {
            finalPrefix = me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.chat.getGroupPrefix(player.getWorld(), me.wizzledonker.plugins.oblicomranks.OblicomRankScore.plugin.permission.getPrimaryGroup(player));
        }
        return finalPrefix;
    }
}