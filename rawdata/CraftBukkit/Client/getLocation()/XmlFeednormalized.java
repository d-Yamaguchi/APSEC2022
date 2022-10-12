public XmlFeed() {
    long lastContactMainThread = java.lang.System.currentTimeMillis() - com.gmail.timaaarrreee.mineload.MineloadPlugin.getHeartbeatTime();
    long startTime = java.lang.System.currentTimeMillis();
    // 
    javax.xml.parsers.DocumentBuilderFactory docFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    try {
        docBuilder = docFactory.newDocumentBuilder();
    } catch (javax.xml.parsers.ParserConfigurationException ex) {
    }
    org.w3c.dom.Element rootElement = doc.createElement("server");
    doc.appendChild(rootElement);
    org.w3c.dom.Element motd = doc.createElement("motd");
    motd.appendChild(doc.createTextNode(data.getMotd()));
    rootElement.appendChild(motd);
    org.w3c.dom.Element playercount = doc.createElement("playercount");
    playercount.appendChild(doc.createTextNode(java.lang.String.valueOf(data.getPlayerCount())));
    rootElement.appendChild(playercount);
    org.w3c.dom.Element maxplayers = doc.createElement("maxplayers");
    maxplayers.appendChild(doc.createTextNode(java.lang.String.valueOf(data.getMaxPlayers())));
    rootElement.appendChild(maxplayers);
    org.w3c.dom.Element totalplayers = doc.createElement("totalplayers");
    totalplayers.appendChild(doc.createTextNode(java.lang.String.valueOf(data.getTotalPlayers())));
    rootElement.appendChild(totalplayers);
    org.w3c.dom.Element memoryused = doc.createElement("memoryused");
    memoryused.appendChild(doc.createTextNode(java.lang.String.valueOf(data.getMemoryUsed())));
    rootElement.appendChild(memoryused);
    org.w3c.dom.Element maxmemory = doc.createElement("maxmemory");
    maxmemory.appendChild(doc.createTextNode(java.lang.String.valueOf(data.getMaxMemory())));
    rootElement.appendChild(maxmemory);
    org.w3c.dom.Element jvmversion = doc.createElement("jvmversion");
    jvmversion.appendChild(doc.createTextNode(java.lang.System.getProperty("java.version")));
    rootElement.appendChild(jvmversion);
    org.w3c.dom.Element osname = doc.createElement("osname");
    osname.appendChild(doc.createTextNode(java.lang.System.getProperty("os.name")));
    rootElement.appendChild(osname);
    org.w3c.dom.Element osversion = doc.createElement("osversion");
    osversion.appendChild(doc.createTextNode(java.lang.System.getProperty("os.version")));
    rootElement.appendChild(osversion);
    org.w3c.dom.Element cwd = doc.createElement("cwd");
    cwd.appendChild(doc.createTextNode(java.lang.System.getProperty("user.dir")));
    rootElement.appendChild(cwd);
    org.w3c.dom.Element bukkitversion = doc.createElement("bukkitversion");
    bukkitversion.appendChild(doc.createTextNode(org.bukkit.Bukkit.getBukkitVersion()));
    rootElement.appendChild(bukkitversion);
    // display plugins
    org.w3c.dom.Element plugins = doc.createElement("plugins");
    for (java.util.Iterator<org.bukkit.plugin.Plugin> it = data.getPlugins().iterator(); it.hasNext();) {
        org.bukkit.plugin.Plugin eachPlugin = it.next();
        org.w3c.dom.Element plugin = doc.createElement("plugin");
        plugin.appendChild(doc.createTextNode(eachPlugin.getName()));
        plugin.setAttribute("enabled", java.lang.String.valueOf(eachPlugin.isEnabled()));
        plugins.appendChild(plugin);
        java.io.InputStream is = eachPlugin.getResource("plugin.yml");
        org.yaml.snakeyaml.Yaml yaml = new org.yaml.snakeyaml.Yaml();
        java.util.Map<java.lang.String, java.lang.Object> config = ((java.util.Map<java.lang.String, java.lang.Object>) (yaml.load(is)));
        plugin.setAttribute("version", config.get("version").toString());
        if (config.containsKey("author")) {
            plugin.setAttribute("author", config.get("author").toString());
        }
        if (config.containsKey("authors")) {
            plugin.setAttribute("author", config.get("authors").toString().replace("[", "").replace("]", ""));
        }
        if (config.containsKey("description")) {
            plugin.setAttribute("description", config.get("description").toString());
        }
        if (config.containsKey("website")) {
            plugin.setAttribute("website", config.get("website").toString());
        }
    }
    rootElement.appendChild(plugins);
    org.w3c.dom.Element players = doc.createElement("players");
    javax.xml.parsers.DocumentBuilder docBuilder = null;
    javax.xml.parsers.DocumentBuilder _CVAR0 = docBuilder;
    // root elements
    org.w3c.dom.Document doc = _CVAR0.newDocument();
    java.util.Iterator<org.bukkit.entity.Player> it = _CVAR6.iterator();
    java.util.Iterator<org.bukkit.entity.Player> it = _CVAR17.iterator();
    com.gmail.timaaarrreee.mineload.DataCollector data = com.gmail.timaaarrreee.mineload.MineloadPlugin.getData();
    com.gmail.timaaarrreee.mineload.DataCollector _CVAR5 = data;
    com.gmail.timaaarrreee.mineload.DataCollector _CVAR16 = _CVAR5;
    com.gmail.timaaarrreee.mineload.DataCollector _CVAR23 = _CVAR16;
     _CVAR6 = _CVAR23.getPlayers();
     _CVAR17 = _CVAR6;
     _CVAR24 = _CVAR17;
    java.util.Iterator<org.bukkit.entity.Player> it = _CVAR24.iterator();
    for (java.util.Iterator<org.bukkit.entity.Player> it = _CVAR24.iterator(); it.hasNext();) {
        player.setAttribute("world", eachPlayer.getWorld().getName());
        player.setAttribute("ip", eachPlayer.getAddress().getAddress().getHostAddress());
        org.w3c.dom.Document _CVAR1 = doc;
        java.lang.String _CVAR2 = "player";
        org.w3c.dom.Element player = _CVAR1.createElement(_CVAR2);
        org.bukkit.entity.Player _CVAR8 = eachPlayer;
        org.bukkit.Location _CVAR9 = _CVAR8.getLocation();
        int _CVAR10 = _CVAR9.getBlockX();
        java.lang.String _CVAR11 = java.lang.String.valueOf(_CVAR10);
        java.lang.String _CVAR12 = _CVAR11 + ",";
        org.bukkit.entity.Player _CVAR19 = eachPlayer;
        org.bukkit.Location _CVAR20 = _CVAR19.getLocation();
        int _CVAR21 = _CVAR20.getBlockY();
        java.lang.String _CVAR22 = java.lang.String.valueOf(_CVAR21);
        java.lang.String _CVAR13 = _CVAR12 + _CVAR22;
        java.lang.String _CVAR14 = _CVAR13 + ",";
        org.w3c.dom.Element _CVAR3 = player;
        java.lang.String _CVAR4 = "xyz";
        java.util.Iterator<org.bukkit.entity.Player> _CVAR7 = it;
        java.util.Iterator<org.bukkit.entity.Player> _CVAR18 = _CVAR7;
        java.util.Iterator<org.bukkit.entity.Player> _CVAR25 = _CVAR18;
        org.bukkit.entity.Player eachPlayer = _CVAR25.next();
        org.bukkit.entity.Player _CVAR26 = eachPlayer;
        org.bukkit.Location _CVAR27 = _CVAR26.getLocation();
        int _CVAR28 = _CVAR27.getBlockZ();
        java.lang.String _CVAR29 = java.lang.String.valueOf(_CVAR28);
        java.lang.String _CVAR15 = _CVAR14 + _CVAR29;
        _CVAR3.setAttribute(_CVAR4, _CVAR15);
        player.setAttribute("inhand", eachPlayer.getItemInHand().getType().toString());
        player.setAttribute("allowedflight", java.lang.String.valueOf(eachPlayer.getAllowFlight()));
        player.setAttribute("op", java.lang.String.valueOf(eachPlayer.isOp()));
        player.setAttribute("gamemode", eachPlayer.getGameMode().toString());
        player.setAttribute("health", java.lang.String.valueOf(eachPlayer.getHealth()));
        player.setAttribute("name", eachPlayer.getName());
        player.setAttribute("displayname", eachPlayer.getDisplayName().replaceAll("§", "&amp;"));
        org.w3c.dom.Element inventory = doc.createElement("inventory");
        org.bukkit.inventory.PlayerInventory inven = eachPlayer.getInventory();
        for (int i = 0; i < inven.getSize(); i++) {
            if (inven.getItem(i) != null) {
                org.bukkit.inventory.ItemStack is = inven.getItem(i);
                org.w3c.dom.Element item = doc.createElement("item");
                item.setAttribute("slot", java.lang.String.valueOf(i));
                item.setAttribute("id", java.lang.String.valueOf(is.getTypeId()));
                item.setAttribute("name", is.getType().toString());
                item.setAttribute("amount", java.lang.String.valueOf(is.getAmount()));
                inventory.appendChild(item);
            }
        }
        player.appendChild(inventory);
        players.appendChild(player);
    }
    rootElement.appendChild(players);
    org.w3c.dom.Element worlds = doc.createElement("worlds");
    for (java.util.Iterator<org.bukkit.World> it = data.getWorlds().iterator(); it.hasNext();) {
        org.bukkit.World eachWorld = it.next();
        org.w3c.dom.Element world = doc.createElement("world");
        world.appendChild(doc.createTextNode(eachWorld.getName()));
        world.setAttribute("players", java.lang.String.valueOf(eachWorld.getPlayers().size()));
        world.setAttribute("time", java.lang.String.valueOf(eachWorld.getTime()));
        world.setAttribute("type", eachWorld.getWorldType().toString());
        world.setAttribute("difficulty", eachWorld.getDifficulty().toString());
        world.setAttribute("seed", java.lang.String.valueOf(eachWorld.getSeed()));
        world.setAttribute("entities", java.lang.String.valueOf(eachWorld.getEntities().size()));
        world.setAttribute("moblimit", java.lang.String.valueOf(eachWorld.getMonsterSpawnLimit()));
        worlds.appendChild(world);
    }
    rootElement.appendChild(worlds);
    org.w3c.dom.Element tps = doc.createElement("tps");
    tps.appendChild(doc.createTextNode(java.lang.String.valueOf(data.getTPS())));
    rootElement.appendChild(tps);
    org.w3c.dom.Element heartbeat = doc.createElement("heartbeat");
    heartbeat.appendChild(doc.createTextNode(java.lang.String.valueOf(lastContactMainThread)));
    heartbeat.setAttribute("ticktime", java.lang.String.valueOf(com.gmail.timaaarrreee.mineload.MineloadPlugin.getTickTime()));
    rootElement.appendChild(heartbeat);
    double timeTaken = java.lang.System.currentTimeMillis() - startTime;
    org.w3c.dom.Element time = doc.createElement("generated");
    time.appendChild(doc.createTextNode(java.lang.String.valueOf(timeTaken)));
    rootElement.appendChild(time);
    try {
        javax.xml.transform.TransformerFactory transfac = javax.xml.transform.TransformerFactory.newInstance();
        javax.xml.transform.Transformer trans = transfac.newTransformer();
        // create string from xml tree
        java.io.StringWriter sw = new java.io.StringWriter();
        javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(sw);
        javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
        trans.transform(source, result);
        java.lang.String xmlString = sw.toString();
        xmlData = xmlString;
    } catch (java.lang.Exception e) {
        e.printStackTrace();
    }
}