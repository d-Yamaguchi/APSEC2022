/* Copyright (c) 2013 cedeel.
All rights reserved.


Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * The name of the author may not be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS''
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package be.darnell.timetracker;
public class TimeTracker extends org.bukkit.plugin.java.JavaPlugin {
    protected java.util.concurrent.ConcurrentMap<java.lang.String, java.lang.Long> players;

    // File storage
    private org.bukkit.configuration.file.YamlConfiguration Data = null;

    private java.io.File DataFile = null;

    private static final java.lang.String DATAFILENAME = "Data.yml";

    // Join message
    private java.lang.String joinMsg;

    private java.lang.String colour;

    // Date formatting
    private boolean alwaysDate = false;

    private int daysBeforeDate = 30;

    /**
     * Construct a human readable string of a duration
     *
     * @param start
     * 		Beginning time in seconds.
     * @param end
     * 		End time in seconds
     * @return A human readable string of a duration
     */
    protected static java.lang.String humanTime(long start, long end) {
        if (start != (-1L)) {
            final long finalTime = (end - start) / 1000L;
            final long MINUTE = 60L;
            final long HOUR = 60 * MINUTE;
            final long DAY = 24 * HOUR;
            if (finalTime >= DAY) {
                java.lang.String s = (finalTime >= (2 * DAY)) ? "days" : "day";
                return ((finalTime / DAY) + " ") + s;
            } else if (finalTime >= HOUR) {
                java.lang.String s = (finalTime >= (2 * HOUR)) ? "hours" : "hour";
                return ((finalTime / HOUR) + " ") + s;
            } else if (finalTime >= MINUTE) {
                java.lang.String s = (finalTime >= (2 * MINUTE)) ? "minutes" : "minute";
                return ((finalTime / MINUTE) + " ") + s;
            } else {
                java.lang.String s = (finalTime >= 2) ? "seconds" : "second";
                return (finalTime + " ") + s;
            }
        }
        return null;
    }

    private static java.lang.String dateTime(long start) {
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        java.util.Date date = new java.util.Date(start);
        return df.format(date);
    }

    protected java.lang.String sinceString(long start, long end) {
        long daysSince = (end - start) / 86400000L;
        if ((daysSince > daysBeforeDate) || alwaysDate)
            return ((be.darnell.timetracker.TimeTracker.dateTime(start) + " (") + be.darnell.timetracker.TimeTracker.humanTime(start, end)) + " ago)";
        else
            return be.darnell.timetracker.TimeTracker.humanTime(start, end) + " ago";

    }

    @java.lang.Override
    public void onDisable() {
        org.bukkit.Bukkit.getScheduler().cancelTasks(this);
        for (java.lang.String p : players.keySet()) {
            removePlayer(p);
        }
        saveData();
        this.getLogger().info("Disabled successfully");
    }

    @java.lang.Override
    public void onEnable() {
        org.bukkit.plugin.PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new be.darnell.timetracker.TimeTrackerPlayerListener(this), this);
        saveDefaultConfig();
        saveData();
        players = new java.util.concurrent.ConcurrentHashMap<java.lang.String, java.lang.Long>();
        this.getCommand("seen").setExecutor(new be.darnell.timetracker.SeenCommand(this));
        this.getCommand("playtime").setExecutor(new be.darnell.timetracker.PlaytimeCommand(this));
        joinMsg = getConfig().getString("JoinMessage");
        for (org.bukkit.entity.Player p : getServer().getOnlinePlayers()) {
            addPlayerAsync(store.loadAchievements(mPlayer));
        }
        colour = org.bukkit.ChatColor.translateAlternateColorCodes('&', getConfig().getString("MessageColour", "&e"));
        alwaysDate = getConfig().getBoolean("AlwaysDate", false);
        daysBeforeDate = getConfig().getInt("DaysBeforeDate", 30);
        this.getLogger().info("All good. Loaded successfully");
    }

    /**
     * Get the time in milliseconds of the first join of a given player
     *
     * @param name
     * 		The player to get the data from
     * @return The time in milliseconds of the first join
     */
    public long getFirstSeen(java.lang.String name) {
        return getData().getLong(name.toLowerCase() + ".first", -1L);
    }

    /**
     * Get the time in milliseconds of the last join of a given player
     *
     * @param name
     * 		The player to get the data from
     * @return The time in milliseconds of the last join
     */
    public long getLastSeen(java.lang.String name) {
        return getData().getLong(name.toLowerCase() + ".last", -1L);
    }

    /**
     * Get the time a player has spent on the server
     *
     * @param name
     * 		The player to get the data from
     * @return The time, in milliseconds, the player has spent on the server
     */
    public long getPlayTime(java.lang.String name) {
        return getData().getLong(name.toLowerCase() + ".playtime", -1L);
    }

    /**
     * Add a specific amount of time to the player's file
     *
     * @param name
     * 		The player to add the time to
     * @param value
     * 		The amount of time, in milliseconds, to add
     */
    protected void addPlayTime(java.lang.String name, long value) {
        getData().set(name.toLowerCase() + ".playtime", java.lang.Long.valueOf(getPlayTime(name) + value));
        saveData();
    }

    /**
     * Set the time a player was first seen
     *
     * @param name
     * 		The name of the player
     * @param value
     * 		The time to be set, in milliseconds
     */
    protected void setFirstSeen(java.lang.String name, long value) {
        getData().set(name.toLowerCase() + ".first", java.lang.Long.valueOf(value));
        saveData();
    }

    /**
     * Set the time a player was last seen
     *
     * @param name
     * 		The name of the player
     * @param value
     * 		The time to be set, in milliseconds
     */
    protected void setLastSeen(java.lang.String name, long value) {
        getData().set(name.toLowerCase() + ".last", java.lang.Long.valueOf(value));
        saveData();
    }

    /**
     * Reload the data file
     */
    private void reloadData() {
        if (DataFile == null)
            DataFile = new java.io.File(getDataFolder(), be.darnell.timetracker.TimeTracker.DATAFILENAME);

        Data = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(DataFile);
        java.io.InputStream defData = this.getResource(be.darnell.timetracker.TimeTracker.DATAFILENAME);
        if (defData != null) {
            org.bukkit.configuration.file.YamlConfiguration defConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(defData);
            Data.setDefaults(defConfig);
        }
    }

    /**
     * Remove a player from the list in memory
     *
     * @param name
     * 		The player to remove
     */
    protected void removePlayerAsync(final java.lang.String name) {
        org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(this, new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                removePlayer(name);
            }
        });
    }

    private void removePlayer(java.lang.String name) {
        if (players.containsKey(name)) {
            long time = new java.util.Date().getTime();
            setLastSeen(name, time);
            addPlayTime(name, time - players.get(name));
            players.remove(name);
        }
    }

    /**
     * Add a player to the list in memory
     *
     * @param name
     * 		The player to add
     */
    protected void addPlayerAsync(final java.lang.String name) {
        org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(this, new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                if (!players.containsKey(name)) {
                    long last = getLastSeen(name);
                    long first = getFirstSeen(name);
                    long ex = new java.util.Date().getTime();
                    players.put(name, ex);
                    if ((last == (-1L)) || (first == (-1L))) {
                        setFirstSeen(name, ex);
                        getServer().broadcastMessage(colour + joinMsg.replace("%p", name));
                    }
                }
            }
        });
    }

    /**
     * Get the data from file
     *
     * @return The player data
     */
    private org.bukkit.configuration.file.YamlConfiguration getData() {
        if (Data == null)
            this.reloadData();

        return Data;
    }

    private void saveData() {
        if ((Data == null) || (DataFile == null))
            return;

        try {
            getData().save(DataFile);
        } catch (java.io.IOException ex) {
            this.getLogger().log(java.util.logging.Level.SEVERE, "Could not save config to " + DataFile, ex);
        }
    }
}