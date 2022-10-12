/* Copyright 2011-2013 Tyler Blair. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and contributors and should not be interpreted as representing official policies,
either expressed or implied, of anybody else.
 */
package ch.njol.skript;
import ch.njol.skript.util.Task;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
// Copied to have this source available as mcstats' Maven repo does neither provide the source nor any Javadocs
// I also made a few changes to support older versions of Bukkit
@org.eclipse.jdt.annotation.NonNullByDefault(false)
public class Metrics {
    /**
     * The current revision number
     */
    private static final int REVISION = 7;

    /**
     * The base url of the metrics domain
     */
    private static final java.lang.String BASE_URL = "http://report.mcstats.org";

    /**
     * The url used to report a server's status
     */
    private static final java.lang.String REPORT_URL = "/plugin/%s";

    /**
     * Interval of time to ping (in minutes)
     */
    private static final int PING_INTERVAL = 15;

    /**
     * The plugin this metrics submits for
     */
    @org.eclipse.jdt.annotation.NonNull
    private final org.bukkit.plugin.Plugin plugin;

    /**
     * All of the custom graphs to submit to metrics
     */
    final java.util.Set<ch.njol.skript.Metrics.Graph> graphs = java.util.Collections.synchronizedSet(new java.util.HashSet<ch.njol.skript.Metrics.Graph>());

    /**
     * The plugin configuration file
     */
    private final org.bukkit.configuration.file.YamlConfiguration configuration;

    /**
     * The plugin configuration file
     */
    private final java.io.File configurationFile;

    /**
     * Unique server id
     */
    private final java.lang.String guid;

    /**
     * Debug mode
     */
    final boolean debug;

    /**
     * Lock for synchronization
     */
    final java.lang.Object optOutLock = new java.lang.Object();

    /**
     * The scheduled task
     */
    @org.eclipse.jdt.annotation.Nullable
    ch.njol.skript.util.Task task = null;

    public Metrics(final org.bukkit.plugin.Plugin plugin) {
        if (plugin == null) {
            throw new java.lang.IllegalArgumentException("Plugin cannot be null");
        }
        this.plugin = plugin;
        // load the config
        configurationFile = getConfigFile();
        configuration = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configurationFile);
        // add some defaults
        configuration.addDefault("opt-out", false);
        configuration.addDefault("guid", java.util.UUID.randomUUID().toString());
        configuration.addDefault("debug", false);
        // Do we need to create the file?
        if (configuration.get("guid", null) == null) {
            configuration.options().header("http://mcstats.org").copyDefaults(true);
            try {
                configuration.save(configurationFile);
            } catch (final java.io.IOException e) {
            }
        }
        // Load the guid then
        guid = configuration.getString("guid");
        debug = configuration.getBoolean("debug", false);
    }

    /**
     * Construct and create a Graph that can be used to separate specific plotters to their own graphs on the metrics
     * website. Plotters can be added to the graph object returned.
     *
     * @param name
     * 		The name of the graph
     * @return Graph object created. Will never return NULL under normal circumstances unless bad parameters are given
     */
    public ch.njol.skript.Metrics.Graph createGraph(final java.lang.String name) {
        if (name == null) {
            throw new java.lang.IllegalArgumentException("Graph name cannot be null");
        }
        // Construct the graph object
        final ch.njol.skript.Metrics.Graph graph = new ch.njol.skript.Metrics.Graph(name);
        // Now we can add our graph
        graphs.add(graph);
        // and return back
        return graph;
    }

    /**
     * Add a Graph object to BukkitMetrics that represents data for the plugin that should be sent to the backend
     *
     * @param graph
     * 		The name of the graph
     */
    public void addGraph(final ch.njol.skript.Metrics.Graph graph) {
        if (graph == null) {
            throw new java.lang.IllegalArgumentException("Graph cannot be null");
        }
        graphs.add(graph);
    }

    /**
     * Start measuring statistics. This will immediately create an async repeating task as the plugin and send the
     * initial data to the metrics backend, and then after that it will post in increments of PING_INTERVAL * 1200
     * ticks.
     *
     * @return True if statistics measuring is running, otherwise false.
     */
    public boolean start() {
        synchronized(optOutLock) {
            // Did we opt out?
            if (isOptOut()) {
                return false;
            }
            // Is metrics already running?
            if (task != null) {
                return true;
            }
            // Begin hitting the server with glorious data
            task = new ch.njol.skript.util.Task(plugin, 0, ch.njol.skript.Metrics.PING_INTERVAL * 1200, true) {
                private boolean firstPost = true;

                @java.lang.Override
                public void run() {
                    try {
                        // This has to be synchronized or it can collide with the disable method.
                        synchronized(optOutLock) {
                            // Disable Task, if it is running and the server owner decided to opt-out
                            if (isOptOut() && (task != null)) {
                                task.cancel();
                                task = null;
                                // Tell all plotters to stop gathering information.
                                for (final ch.njol.skript.Metrics.Graph graph : graphs) {
                                    graph.onOptOut();
                                }
                            }
                        }
                        // We use the inverse of firstPost because if it is the first time we are posting,
                        // it is not a interval ping, so it evaluates to FALSE
                        // Each time thereafter it will evaluate to TRUE, i.e PING!
                        postPlugin(!firstPost);
                        // After the first post we set firstPost to false
                        // Each post thereafter will be a ping
                        firstPost = false;
                    } catch (final java.io.IOException e) {
                        if (debug) {
                            org.bukkit.Bukkit.getLogger().log(java.util.logging.Level.INFO, "[Metrics] " + e.getMessage());
                        }
                    }
                }
            };
            return true;
        }
    }

    /**
     * Has the server owner denied plugin metrics?
     *
     * @return true if metrics should be opted out of it
     */
    public boolean isOptOut() {
        synchronized(optOutLock) {
            try {
                // Reload the metrics file
                configuration.load(getConfigFile());
            } catch (final java.io.IOException ex) {
                if (debug) {
                    org.bukkit.Bukkit.getLogger().log(java.util.logging.Level.INFO, "[Metrics] " + ex.getMessage());
                }
                return true;
            } catch (final org.bukkit.configuration.InvalidConfigurationException ex) {
                if (debug) {
                    org.bukkit.Bukkit.getLogger().log(java.util.logging.Level.INFO, "[Metrics] " + ex.getMessage());
                }
                return true;
            }
            return configuration.getBoolean("opt-out", false);
        }
    }

    /**
     * Enables metrics for the server by setting "opt-out" to false in the config file and starting the metrics task.
     *
     * @throws java.io.IOException
     * 		
     */
    public void enable() throws java.io.IOException {
        // This has to be synchronized or it can collide with the check in the task.
        synchronized(optOutLock) {
            // Check if the server owner has already set opt-out, if not, set it.
            if (isOptOut()) {
                configuration.set("opt-out", false);
                configuration.save(configurationFile);
            }
            // Enable Task, if it is not running
            if (task == null) {
                start();
            }
        }
    }

    /**
     * Disables metrics for the server by setting "opt-out" to true in the config file and canceling the metrics task.
     *
     * @throws java.io.IOException
     * 		
     */
    public void disable() throws java.io.IOException {
        // This has to be synchronized or it can collide with the check in the task.
        synchronized(optOutLock) {
            // Check if the server owner has already set opt-out, if not, set it.
            if (!isOptOut()) {
                configuration.set("opt-out", true);
                configuration.save(configurationFile);
            }
            // Disable Task, if it is running
            if (task != null) {
                task.cancel();
                task = null;
            }
        }
    }

    /**
     * Gets the File object of the config file that should be used to store data such as the GUID and opt-out status
     *
     * @return the File object for the config file
     */
    public java.io.File getConfigFile() {
        // I believe the easiest way to get the base folder (e.g craftbukkit set via -P) for plugins to use
        // is to abuse the plugin object we already have
        // plugin.getDataFolder() => base/plugins/PluginA/
        // pluginsFolder => base/plugins/
        // The base is not necessarily relative to the startup directory.
        final java.io.File pluginsFolder = plugin.getDataFolder().getParentFile();
        // return => base/plugins/PluginMetrics/config.yml
        return new java.io.File(new java.io.File(pluginsFolder, "PluginMetrics"), "config.yml");
    }

    /**
     * Generic method that posts a plugin to the metrics website
     */
    void postPlugin(final boolean isPing) throws java.io.IOException {
        // Server software specific section
        final org.bukkit.plugin.PluginDescriptionFile description = plugin.getDescription();
        final java.lang.String pluginName = description.getName();
        final boolean onlineMode = ChannelsPlugin.get().getOnlineMode();// TRUE if online mode is enabled

        final java.lang.String pluginVersion = description.getVersion();
        final java.lang.String serverVersion = org.bukkit.Bukkit.getVersion();
        final int playersOnline = org.bukkit.Bukkit.getServer().getOnlinePlayers().length;
        // END server software specific section -- all code below does not use any code outside of this class / Java
        // Construct the post data
        final java.lang.StringBuilder json = new java.lang.StringBuilder(1024);
        json.append('{');
        // The plugin's description file containg all of the plugin data such as name, version, author, etc
        ch.njol.skript.Metrics.appendJSONPair(json, "guid", guid);
        ch.njol.skript.Metrics.appendJSONPair(json, "plugin_version", pluginVersion);
        ch.njol.skript.Metrics.appendJSONPair(json, "server_version", serverVersion);
        ch.njol.skript.Metrics.appendJSONPair(json, "players_online", java.lang.Integer.toString(playersOnline));
        // New data as of R6
        final java.lang.String osname = java.lang.System.getProperty("os.name");
        java.lang.String osarch = java.lang.System.getProperty("os.arch");
        final java.lang.String osversion = java.lang.System.getProperty("os.version");
        final java.lang.String java_version = java.lang.System.getProperty("java.version");
        final int coreCount = java.lang.Runtime.getRuntime().availableProcessors();
        // normalize os arch .. amd64 -> x86_64
        if (osarch.equals("amd64")) {
            osarch = "x86_64";
        }
        ch.njol.skript.Metrics.appendJSONPair(json, "osname", osname);
        ch.njol.skript.Metrics.appendJSONPair(json, "osarch", osarch);
        ch.njol.skript.Metrics.appendJSONPair(json, "osversion", osversion);
        ch.njol.skript.Metrics.appendJSONPair(json, "cores", java.lang.Integer.toString(coreCount));
        ch.njol.skript.Metrics.appendJSONPair(json, "auth_mode", onlineMode ? "1" : "0");
        ch.njol.skript.Metrics.appendJSONPair(json, "java_version", java_version);
        // If we're pinging, append it
        if (isPing) {
            ch.njol.skript.Metrics.appendJSONPair(json, "ping", "1");
        }
        if (graphs.size() > 0) {
            synchronized(graphs) {
                json.append(',');
                json.append('"');
                json.append("graphs");
                json.append('"');
                json.append(':');
                json.append('{');
                boolean firstGraph = true;
                final java.util.Iterator<ch.njol.skript.Metrics.Graph> iter = graphs.iterator();
                __SmPLUnsupported__(0);
                json.append('}');
            }
        }
        // close json
        json.append('}');
        // Create the url
        final java.net.URL url = new java.net.URL(ch.njol.skript.Metrics.BASE_URL + java.lang.String.format(ch.njol.skript.Metrics.REPORT_URL, ch.njol.skript.Metrics.urlEncode(pluginName)));
        // Connect to the website
        java.net.URLConnection connection;
        // Mineshafter creates a socks proxy, so we can safely bypass it
        // It does not reroute POST requests so we need to go around it
        if (ch.njol.skript.Metrics.isMineshafterPresent()) {
            connection = url.openConnection(java.net.Proxy.NO_PROXY);
        } else {
            connection = url.openConnection();
        }
        final byte[] uncompressed = json.toString().getBytes();
        final byte[] compressed = ch.njol.skript.Metrics.gzip(json.toString());
        // Headers
        connection.addRequestProperty("User-Agent", "MCStats/" + ch.njol.skript.Metrics.REVISION);
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("Content-Encoding", "gzip");
        connection.addRequestProperty("Content-Length", java.lang.Integer.toString(compressed.length));
        connection.addRequestProperty("Accept", "application/json");
        connection.addRequestProperty("Connection", "close");
        connection.setDoOutput(true);
        if (debug) {
            java.lang.System.out.println((((("[Metrics] Prepared request for " + pluginName) + " uncompressed=") + uncompressed.length) + " compressed=") + compressed.length);
        }
        // Write the data
        final java.io.OutputStream os = connection.getOutputStream();
        os.write(compressed);
        os.flush();
        // Now read the response
        final java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
        java.lang.String response = reader.readLine();
        // close resources
        os.close();
        reader.close();
        if (((response == null) || response.startsWith("ERR")) || response.startsWith("7")) {
            if (response == null) {
                response = "null";
            } else if (response.startsWith("7")) {
                response = response.substring(response.startsWith("7,") ? 2 : 1);
            }
            throw new java.io.IOException(response);
        } else // Is this the first update this hour?
        if (response.equals("1") || response.contains("This is your first update this hour")) {
            synchronized(graphs) {
                final java.util.Iterator<ch.njol.skript.Metrics.Graph> iter = graphs.iterator();
                __SmPLUnsupported__(1);
            }
        }
    }

    /**
     * GZip compress a string of bytes
     *
     * @param input
     * 		
     * @return Compressed data
     */
    public static byte[] gzip(final java.lang.String input) {
        final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.util.zip.GZIPOutputStream gzos = null;
        try {
            gzos = new java.util.zip.GZIPOutputStream(baos);
            gzos.write(input.getBytes("UTF-8"));
        } catch (final java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (gzos != null)
                try {
                    gzos.close();
                } catch (final java.io.IOException ignore) {
                }

        }
        return baos.toByteArray();
    }

    /**
     * Check if mineshafter is present. If it is, we need to bypass it to send POST requests
     *
     * @return true if mineshafter is installed on the server
     */
    private static boolean isMineshafterPresent() {
        try {
            java.lang.Class.forName("mineshafter.MineServer");
            return true;
        } catch (final java.lang.Exception e) {
            return false;
        }
    }

    /**
     * Appends a json encoded key/value pair to the given string builder.
     *
     * @param json
     * 		
     * @param key
     * 		
     * @param value
     * 		
     */
    private static void appendJSONPair(final java.lang.StringBuilder json, final java.lang.String key, final java.lang.String value) {
        boolean isValueNumeric = false;
        try {
            if (value.equals("0") || (!value.endsWith("0"))) {
                java.lang.Double.parseDouble(value);
                isValueNumeric = true;
            }
        } catch (final java.lang.NumberFormatException e) {
            isValueNumeric = false;
        }
        if (json.charAt(json.length() - 1) != '{') {
            json.append(',');
        }
        json.append(ch.njol.skript.Metrics.escapeJSON(key));
        json.append(':');
        if (isValueNumeric) {
            json.append(value);
        } else {
            json.append(ch.njol.skript.Metrics.escapeJSON(value));
        }
    }

    /**
     * Escape a string to create a valid JSON string
     *
     * @param text
     * 		
     * @return Escaped String
     */
    private static java.lang.String escapeJSON(final java.lang.String text) {
        final java.lang.StringBuilder builder = new java.lang.StringBuilder();
        builder.append('"');
        for (int index = 0; index < text.length(); index++) {
            final char chr = text.charAt(index);
            switch (chr) {
                case '"' :
                case '\\' :
                    builder.append('\\');
                    builder.append(chr);
                    break;
                case '\b' :
                    builder.append("\\b");
                    break;
                case '\t' :
                    builder.append("\\t");
                    break;
                case '\n' :
                    builder.append("\\n");
                    break;
                case '\r' :
                    builder.append("\\r");
                    break;
                default :
                    if (chr < ' ') {
                        final java.lang.String t = "000" + java.lang.Integer.toHexString(chr);
                        builder.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        builder.append(chr);
                    }
                    break;
            }
        }
        builder.append('"');
        return builder.toString();
    }

    /**
     * Encode text as UTF-8
     *
     * @param text
     * 		the text to encode
     * @return the encoded text, as UTF-8
     */
    private static java.lang.String urlEncode(final java.lang.String text) throws java.io.UnsupportedEncodingException {
        return java.net.URLEncoder.encode(text, "UTF-8");
    }

    /**
     * Represents a custom graph on the website
     */
    public static class Graph {
        /**
         * The graph's name, alphanumeric and spaces only :) If it does not comply to the above when submitted, it is
         * rejected
         */
        private final java.lang.String name;

        /**
         * The set of plotters that are contained within this graph
         */
        private final java.util.Set<ch.njol.skript.Metrics.Plotter> plotters = new java.util.LinkedHashSet<ch.njol.skript.Metrics.Plotter>();

        Graph(final java.lang.String name) {
            this.name = name;
        }

        /**
         * Gets the graph's name
         *
         * @return the Graph's name
         */
        public java.lang.String getName() {
            return name;
        }

        /**
         * Add a plotter to the graph, which will be used to plot entries
         *
         * @param plotter
         * 		the plotter to add to the graph
         */
        public void addPlotter(final ch.njol.skript.Metrics.Plotter plotter) {
            plotters.add(plotter);
        }

        /**
         * Remove a plotter from the graph
         *
         * @param plotter
         * 		the plotter to remove from the graph
         */
        public void removePlotter(final ch.njol.skript.Metrics.Plotter plotter) {
            plotters.remove(plotter);
        }

        /**
         * Gets an <b>unmodifiable</b> set of the plotter objects in the graph
         *
         * @return an unmodifiable {@link java.util.Set} of the plotter objects
         */
        public java.util.Set<ch.njol.skript.Metrics.Plotter> getPlotters() {
            return java.util.Collections.unmodifiableSet(plotters);
        }

        @java.lang.Override
        public int hashCode() {
            return name.hashCode();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object object) {
            if (!(object instanceof ch.njol.skript.Metrics.Graph)) {
                return false;
            }
            final ch.njol.skript.Metrics.Graph graph = ((ch.njol.skript.Metrics.Graph) (object));
            return graph.name.equals(name);
        }

        /**
         * Called when the server owner decides to opt-out of BukkitMetrics while the server is running.
         */
        protected void onOptOut() {
        }
    }

    /**
     * Interface used to collect custom data for a plugin
     */
    public static abstract class Plotter {
        /**
         * The plot's name
         */
        private final java.lang.String name;

        /**
         * Construct a plotter with the default plot name
         */
        public Plotter() {
            this("Default");
        }

        /**
         * Construct a plotter with a specific plot name
         *
         * @param name
         * 		the name of the plotter to use, which will show up on the website
         */
        public Plotter(final java.lang.String name) {
            this.name = name;
        }

        /**
         * Get the current value for the plotted point. Since this function defers to an external function it may or may
         * not return immediately thus cannot be guaranteed to be thread friendly or safe. This function can be called
         * from any thread so care should be taken when accessing resources that need to be synchronized.
         *
         * @return the current value for the point to be plotted.
         */
        public abstract int getValue();

        /**
         * Get the column name for the plotted point
         *
         * @return the plotted point's column name
         */
        public java.lang.String getColumnName() {
            return name;
        }

        /**
         * Called after the website graphs have been updated
         */
        public void reset() {
        }

        @java.lang.Override
        public int hashCode() {
            return getColumnName().hashCode();
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object object) {
            if (!(object instanceof ch.njol.skript.Metrics.Plotter)) {
                return false;
            }
            final ch.njol.skript.Metrics.Plotter plotter = ((ch.njol.skript.Metrics.Plotter) (object));
            return plotter.name.equals(name) && (plotter.getValue() == getValue());
        }
    }
}