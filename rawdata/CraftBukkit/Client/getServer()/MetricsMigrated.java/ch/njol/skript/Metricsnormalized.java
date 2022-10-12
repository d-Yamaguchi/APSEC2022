/**
 * Generic method that posts a plugin to the metrics website
 */
void postPlugin(final boolean isPing) throws java.io.IOException {
    // Server software specific section
    final org.bukkit.plugin.PluginDescriptionFile description = plugin.getDescription();
    final java.lang.String pluginName = description.getName();
    final boolean onlineMode = plugin.realIp.get(pluginName).getOnlineMode();// TRUE if online mode is enabled

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
    } else if (response.equals("1") || response.contains("This is your first update this hour")) {
        synchronized(graphs) {
            final java.util.Iterator<ch.njol.skript.Metrics.Graph> iter = graphs.iterator();
            __SmPLUnsupported__(1);
        }
    }
}