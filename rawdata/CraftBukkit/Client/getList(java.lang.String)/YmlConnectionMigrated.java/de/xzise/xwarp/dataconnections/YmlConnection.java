package de.xzise.xwarp.dataconnections;
import de.xzise.bukkit.util.MemorySectionFromMap;
import de.xzise.bukkit.util.callback.Callback;
import de.xzise.metainterfaces.FixedLocation;
import de.xzise.metainterfaces.LocationWrapper;
import de.xzise.xwarp.DefaultWarpObject;
import de.xzise.xwarp.DefaultWarpObject.EditorPermissionEntry;
import de.xzise.xwarp.Warp;
import de.xzise.xwarp.Warp.Visibility;
import de.xzise.xwarp.WarpObject;
import de.xzise.xwarp.WarpProtectionArea;
import de.xzise.xwarp.WorldWrapper;
import de.xzise.xwarp.XWarp;
import de.xzise.xwarp.editors.Editor;
import de.xzise.xwarp.editors.EditorPermissions;
import de.xzise.xwarp.editors.EditorPermissions.Type;
import de.xzise.xwarp.editors.WarpPermissions;
import de.xzise.xwarp.editors.WarpProtectionAreaPermissions;
public class YmlConnection implements de.xzise.xwarp.dataconnections.WarpProtectionConnection {
    // @formatter:off
    /* xwarp:
      version: 0
      protectionareas:
        - name: 'foo'
          owner: 'xZise'
          creator: 'xZise'
          editors:
            - name: 'somebody'
              type: 'player'
              permissions:
                - 'overwrite'
          world: 'world'
          corners:
            - x: 0.0
              y: 1.0
              z: 0.0
            - x: 10.0
              y: 3.0
              z: 10.0
      warps:
        - name: 'foo'
          owner: 'xZise'
          creator: 'xZise'
          editors:
            - name: 'somebody'
              type: 'player'
              permissions:
                - 'location'
                - 'invite'
          x: 0.0
          y: 65.0
          z: -0.5
          yaw: 0.0
          pitch: 0.0
          world: 'world'
          visibility: 'public'
          listed: 'true'
          price: 0.0 # < 0 → def
          cooldown: 0 # < 0 → def
          warmup: 0 # < 0 → def
          welcome: 'Welcome!'
     */
    // @formatter:on
    private static final de.xzise.bukkit.util.callback.Callback<de.xzise.xwarp.Warp, org.bukkit.configuration.MemorySection> NODE_TO_WARP = new de.xzise.bukkit.util.callback.Callback<de.xzise.xwarp.Warp, org.bukkit.configuration.MemorySection>() {
        @java.lang.Override
        public de.xzise.xwarp.Warp call(org.bukkit.configuration.MemorySection parameter) {
            return de.xzise.xwarp.dataconnections.YmlConnection.getWarp(parameter);
        }
    };

    private static final de.xzise.bukkit.util.callback.Callback<de.xzise.xwarp.WarpProtectionArea, org.bukkit.configuration.MemorySection> NODE_TO_WPA = new de.xzise.bukkit.util.callback.Callback<de.xzise.xwarp.WarpProtectionArea, org.bukkit.configuration.MemorySection>() {
        @java.lang.Override
        public de.xzise.xwarp.WarpProtectionArea call(org.bukkit.configuration.MemorySection parameter) {
            return de.xzise.xwarp.dataconnections.YmlConnection.getWarpProtectionArea(parameter);
        }
    };

    private static final java.lang.String WARP_PATH = "xwarp.warps";

    private static final java.lang.String WPA_PATH = "xwarp.protectionareas";

    private org.bukkit.configuration.file.YamlConfiguration config;

    private java.io.File file;

    @java.lang.Override
    public boolean load(java.io.File file) {
        this.file = file;
        if (!file.exists()) {
            this.clear();
        }
        this.config = new org.bukkit.configuration.file.YamlConfiguration();
        try {
            this.config.load(file);
        } catch (java.io.FileNotFoundException e) {
            XWarp.logger.severe("Unable to load warps.yml as it doesn't exist.", e);
        } catch (java.io.IOException e) {
            XWarp.logger.severe("Unable to load warps.yml.", e);
        } catch (org.bukkit.configuration.InvalidConfigurationException e) {
            XWarp.logger.severe("Unable to load warps.yml because the configuration seems to be invalid!", e);
        }
        return file.canWrite();
    }

    @java.lang.Override
    public boolean create(java.io.File file) {
        if (this.load(file)) {
            this.clear();
            return true;
        } else {
            return false;
        }
    }

    @java.lang.Override
    public void free() {
        // TODO Auto-generated method stub
    }

    @java.lang.Override
    public java.lang.String getFilename() {
        return "warps.yml";
    }

    @java.lang.Override
    public void clear() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter(this.file);
            try {
                writer.write("xwarp:\n");
                writer.write("  version: 0\n");
                writer.write("  protectionareas: []\n");
                writer.write("  warps: []\n");
            } finally {
                writer.close();
            }
        } catch (java.io.IOException e) {
            XWarp.logger.severe("Unable to write the file", e);
        }
    }

    public <T extends de.xzise.xwarp.WarpObject<?>> de.xzise.xwarp.dataconnections.IdentificationInterface<T> createIdentification(T warp) {
        return de.xzise.xwarp.dataconnections.NameIdentification.create(warp);
    }

    @java.lang.Override
    public de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.Warp> createWarpIdentification(de.xzise.xwarp.Warp warp) {
        return de.xzise.xwarp.dataconnections.NameIdentification.create(warp);
    }

    public static de.xzise.xwarp.Warp getWarp(org.bukkit.configuration.MemorySection node) {
        java.lang.String name = node.getString("name");
        java.lang.String owner = node.getString("owner");
        java.lang.String creator = node.getString("creator");
        java.util.List<? extends org.bukkit.configuration.ConfigurationSection> editorNodes = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(node, "editors");
        java.util.Map<de.xzise.xwarp.editors.EditorPermissions.Type, java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<de.xzise.xwarp.editors.WarpPermissions>>> allPermissions = de.xzise.xwarp.dataconnections.YmlConnection.getEditorPermissions(editorNodes, WarpPermissions.STRING_MAP, de.xzise.xwarp.editors.WarpPermissions.class);
        // Location:
        java.lang.Double x = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(node, "x");
        java.lang.Double y = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(node, "y");
        java.lang.Double z = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(node, "z");
        java.lang.Float yaw = de.xzise.xwarp.dataconnections.YmlConnection.getFloat(node, "yaw");
        java.lang.Float pitch = de.xzise.xwarp.dataconnections.YmlConnection.getFloat(node, "pitch");
        java.lang.String world = node.getString("world");
        org.bukkit.World worldObject = org.bukkit.Bukkit.getServer().getWorld(world);
        de.xzise.xwarp.Warp.Visibility visibility = de.xzise.xwarp.Warp.Visibility.parseString(node.getString("visibility"));
        boolean listed = node.getBoolean("listed", true);
        double price = node.getDouble("price", -1);
        int cooldown = node.getInt("cooldown", -1);
        int warmup = node.getInt("warmup", -1);
        java.lang.String welcomeMessage = node.getString("welcome");
        de.xzise.xwarp.Warp warp = new de.xzise.xwarp.Warp(name, creator, owner, new de.xzise.metainterfaces.LocationWrapper(new de.xzise.metainterfaces.FixedLocation(worldObject, x, y, z, yaw, pitch), world));
        warp.setWelcomeMessage(welcomeMessage);
        for (java.util.Map.Entry<de.xzise.xwarp.editors.EditorPermissions.Type, java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<de.xzise.xwarp.editors.WarpPermissions>>> typeEntry : allPermissions.entrySet()) {
            for (java.util.Map.Entry<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<de.xzise.xwarp.editors.WarpPermissions>> editorEntry : typeEntry.getValue().entrySet()) {
                warp.getEditorPermissions(editorEntry.getKey(), true, typeEntry.getKey()).putAll(editorEntry.getValue());
            }
        }
        warp.setVisibility(visibility);
        warp.setListed(listed);
        warp.setPrice(price);
        warp.setCoolDown(cooldown);
        warp.setWarmUp(warmup);
        return warp;
    }

    @java.lang.Override
    public java.util.List<de.xzise.xwarp.Warp> getWarps() {
        return getList(de.xzise.xwarp.dataconnections.YmlConnection.WARP_PATH, de.xzise.xwarp.dataconnections.YmlConnection.NODE_TO_WARP);
    }

    public static java.lang.Double getDouble(org.bukkit.configuration.ConfigurationSection node, java.lang.String path) {
        if (node.isDouble(path)) {
            return node.getDouble(path);
        } else {
            return null;
        }
    }

    public static java.lang.Float getFloat(org.bukkit.configuration.ConfigurationSection node, java.lang.String path) {
        java.lang.Double d = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(node, path);
        if (d != null) {
            return d.floatValue();
        } else {
            return null;
        }
    }

    private static <T extends java.lang.Enum<T> & de.xzise.xwarp.editors.Editor> java.util.Map<java.lang.String, java.lang.Object> warpObjectToMap(de.xzise.xwarp.DefaultWarpObject<T> object) {
        java.util.Map<java.lang.String, java.lang.Object> map = com.google.common.collect.Maps.newHashMap();
        map.put("name", object.getName());
        map.put("owner", object.getOwner());
        map.put("creator", object.getCreator());
        map.put("world", object.getWorld());
        java.util.Collection<de.xzise.xwarp.DefaultWarpObject.EditorPermissionEntry<T>> editorPermissionEntries = object.getEditorPermissionsList();
        java.util.List<java.util.Map<java.lang.String, java.lang.Object>> editorPermissionMaps = com.google.common.collect.Lists.newArrayListWithCapacity(editorPermissionEntries.size());
        for (de.xzise.xwarp.DefaultWarpObject.EditorPermissionEntry<T> entry : editorPermissionEntries) {
            editorPermissionMaps.add(de.xzise.xwarp.dataconnections.YmlConnection.editorPermissionToMap(entry.editorPermissions, entry.name, entry.type));
        }
        map.put("editors", editorPermissionMaps);
        return map;
    }

    private static <T extends java.lang.Enum<T> & de.xzise.xwarp.editors.Editor> java.util.List<java.lang.String> getPermissionsList(de.xzise.xwarp.editors.EditorPermissions<T> editorPermissions) {
        com.google.common.collect.ImmutableSet<T> perms = editorPermissions.getByValue(true);
        java.util.List<java.lang.String> permNames = com.google.common.collect.Lists.newArrayListWithCapacity(perms.size());
        for (T p : perms) {
            permNames.add(p.getName());
        }
        return permNames;
    }

    private static <T extends java.lang.Enum<T> & de.xzise.xwarp.editors.Editor> java.util.Map<java.lang.String, java.lang.Object> editorPermissionToMap(de.xzise.xwarp.editors.EditorPermissions<T> perm, java.lang.String name, de.xzise.xwarp.editors.EditorPermissions.Type type) {
        java.util.Map<java.lang.String, java.lang.Object> map = com.google.common.collect.Maps.newHashMap();
        map.put("name", name);
        map.put("type", type.name);
        map.put("permissions", de.xzise.xwarp.dataconnections.YmlConnection.getPermissionsList(perm));
        return map;
    }

    @java.lang.Override
    public void addWarp(de.xzise.xwarp.Warp... warps) {
        List rawNodes = this.config.getMapList(de.xzise.xwarp.dataconnections.YmlConnection.WARP_PATH);
        for (de.xzise.xwarp.Warp warp : warps) {
            java.util.Map<java.lang.String, java.lang.Object> warpMap = de.xzise.xwarp.dataconnections.YmlConnection.warpObjectToMap(warp);
            de.xzise.xwarp.dataconnections.YmlConnection.fixedLocToMap(warpMap, warp.getLocation(), false, true);
            warpMap.put("visibility", warp.getVisibility().name);
            warpMap.put("listed", warp.isListed());
            warpMap.put("price", warp.getPrice());
            warpMap.put("cooldown", warp.getCoolDown());
            warpMap.put("warmup", warp.getWarmUp());
            java.lang.String rawMessage = warp.getRawWelcomeMessage();
            if (rawMessage != null) {
                warpMap.put("welcome", rawMessage);
            }
            rawNodes.add(warpMap);
        }
        this.save();
    }

    private static java.util.Map<java.lang.String, java.lang.Object> nodeToMap(org.bukkit.configuration.MemorySection node) {
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<java.lang.String, java.lang.Object>();
        for (java.lang.String key : node.getKeys(false)) {
            map.put(key, node.get(key));
        }
        return map;
    }

    public static boolean bool(java.lang.Boolean b, boolean nullIsTrue) {
        return b == null ? nullIsTrue : b;
    }

    public static class WarpObjectCallback<T extends de.xzise.xwarp.WarpObject<?>> implements de.xzise.bukkit.util.callback.Callback<java.lang.Boolean, org.bukkit.configuration.MemorySection> {
        public final de.xzise.xwarp.dataconnections.IdentificationInterface<T> id;

        public final de.xzise.bukkit.util.callback.Callback<T, org.bukkit.configuration.MemorySection> warpObjectGetter;

        public WarpObjectCallback(de.xzise.xwarp.dataconnections.IdentificationInterface<T> id, de.xzise.bukkit.util.callback.Callback<T, org.bukkit.configuration.MemorySection> warpObjectGetter) {
            this.id = id;
            this.warpObjectGetter = warpObjectGetter;
        }

        public static <T extends de.xzise.xwarp.WarpObject<?>> de.xzise.xwarp.dataconnections.YmlConnection.WarpObjectCallback<T> create(de.xzise.xwarp.dataconnections.IdentificationInterface<T> id, de.xzise.bukkit.util.callback.Callback<T, org.bukkit.configuration.MemorySection> warpObjectGetter) {
            return new de.xzise.xwarp.dataconnections.YmlConnection.WarpObjectCallback<T>(id, warpObjectGetter);
        }

        @java.lang.Override
        public java.lang.Boolean call(org.bukkit.configuration.MemorySection parameter) {
            return !this.id.isIdentificated(this.warpObjectGetter.call(parameter));
        }
    }

    public static void removeFromList(org.bukkit.configuration.MemorySection node, java.lang.String key, de.xzise.bukkit.util.callback.Callback<java.lang.Boolean, org.bukkit.configuration.MemorySection> callback) {
        java.util.List<? extends org.bukkit.configuration.MemorySection> nodes = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(node, key);
        java.lang.System.out.println((("Node size: " + nodes.size()) + " @") + key);
        java.util.List<java.util.Map<java.lang.String, java.lang.Object>> mapList = com.google.common.collect.Lists.newArrayListWithCapacity(java.lang.Math.max(nodes.size() - 1, 0));
        for (org.bukkit.configuration.MemorySection singleNode : nodes) {
            java.util.Map<java.lang.String, java.lang.Object> a = de.xzise.xwarp.dataconnections.YmlConnection.nodeToMap(singleNode);
            if (de.xzise.xwarp.dataconnections.YmlConnection.bool(callback.call(singleNode), false)) {
                mapList.add(a);
                java.lang.System.out.println((("Added node #" + mapList.size()) + " a.keySet() =>") + a.keySet());
            } else {
                java.lang.System.out.println("Skiped node w/ a.keySet() =>" + a.keySet());
            }
        }
        node.set(key, mapList);
    }

    @java.lang.Override
    public void deleteWarp(de.xzise.xwarp.Warp warp) {
        de.xzise.xwarp.dataconnections.YmlConnection.removeFromList(this.config, de.xzise.xwarp.dataconnections.YmlConnection.WARP_PATH, de.xzise.xwarp.dataconnections.YmlConnection.WarpObjectCallback.create(de.xzise.xwarp.dataconnections.NameIdentification.create(warp), de.xzise.xwarp.dataconnections.YmlConnection.NODE_TO_WARP));
        this.save();
    }

    private <T extends de.xzise.xwarp.WarpObject<?>> org.bukkit.configuration.MemorySection getNode(de.xzise.xwarp.dataconnections.IdentificationInterface<T> id, java.lang.String path, de.xzise.bukkit.util.callback.Callback<T, org.bukkit.configuration.MemorySection> nodeToWarpObject) {
        java.util.List<? extends org.bukkit.configuration.MemorySection> sections = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(this.config, path);
        for (org.bukkit.configuration.MemorySection section : sections) {
            T w = nodeToWarpObject.call(section);
            if (id.isIdentificated(w))
                return section;

        }
        return null;
    }

    private void save() {
        try {
            this.config.save(this.file);
        } catch (java.io.IOException e) {
            XWarp.logger.severe("Unable to save warps.yml file!", e);
        }
    }

    private org.bukkit.configuration.MemorySection getWarpNode(de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.Warp> id) {
        return getNode(id, de.xzise.xwarp.dataconnections.YmlConnection.WARP_PATH, de.xzise.xwarp.dataconnections.YmlConnection.NODE_TO_WARP);
    }

    private org.bukkit.configuration.MemorySection getWarpProtectionAreaNode(de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.WarpProtectionArea> id) {
        return getNode(id, de.xzise.xwarp.dataconnections.YmlConnection.WPA_PATH, de.xzise.xwarp.dataconnections.YmlConnection.NODE_TO_WPA);
    }

    private void updateField(org.bukkit.configuration.ConfigurationSection node, java.lang.String path, java.lang.Object value) {
        node.set(path, value);
        this.save();
    }

    private void updateWarpField(de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.Warp> id, java.lang.String path, java.lang.Object value) {
        this.updateField(this.getWarpNode(id), path, value);
    }

    private void updateWPAField(de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.WarpProtectionArea> id, java.lang.String path, java.lang.Object value) {
        this.updateField(this.getWarpProtectionAreaNode(id), path, value);
    }

    private void updateWPAField(de.xzise.xwarp.WarpProtectionArea area, java.lang.String path, java.lang.Object value) {
        this.updateWPAField(de.xzise.xwarp.dataconnections.NameIdentification.create(area), path, value);
    }

    private void updateWarpField(de.xzise.xwarp.Warp warp, java.lang.String path, java.lang.Object value) {
        this.updateWarpField(de.xzise.xwarp.dataconnections.NameIdentification.create(warp), path, value);
    }

    @java.lang.Override
    public void updateCreator(de.xzise.xwarp.Warp warp) {
        this.updateWarpField(warp, "creator", warp.getCreator());
    }

    @java.lang.Override
    public void updateOwner(de.xzise.xwarp.Warp warp, de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.Warp> identification) {
        this.updateWarpField(identification, "owner", warp.getOwner());
    }

    @java.lang.Override
    public void updateName(de.xzise.xwarp.Warp warp, de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.Warp> identification) {
        this.updateWarpField(identification, "name", warp.getName());
    }

    @java.lang.Override
    public void updateMessage(de.xzise.xwarp.Warp warp) {
        this.updateWarpField(warp, "creator", warp.getRawWelcomeMessage());
    }

    @java.lang.Override
    public void updateVisibility(de.xzise.xwarp.Warp warp) {
        this.updateWarpField(warp, "creator", warp.getVisibility().name);
    }

    @java.lang.Override
    public void updateLocation(de.xzise.xwarp.Warp warp) {
        org.bukkit.configuration.ConfigurationSection node = getWarpNode(de.xzise.xwarp.dataconnections.NameIdentification.create(warp));
        de.xzise.metainterfaces.LocationWrapper locWrap = warp.getLocationWrapper();
        java.lang.String world = locWrap.getWorld();
        de.xzise.metainterfaces.FixedLocation loc = locWrap.getLocation();
        node.set("x", loc.x);
        node.set("y", loc.y);
        node.set("z", loc.z);
        node.set("yaw", loc.yaw);
        node.set("pitch", loc.pitch);
        node.set("world", world);
        this.save();
    }

    private <T extends java.lang.Enum<T> & de.xzise.xwarp.editors.Editor> void updateEditor(org.bukkit.configuration.MemorySection warpObjectNode, de.xzise.xwarp.DefaultWarpObject<T> warpObject, final java.lang.String name, final de.xzise.xwarp.editors.EditorPermissions.Type type) {
        final de.xzise.bukkit.util.callback.Callback<java.lang.Boolean, org.bukkit.configuration.MemorySection> editorCheck = new de.xzise.bukkit.util.callback.Callback<java.lang.Boolean, org.bukkit.configuration.MemorySection>() {
            @java.lang.Override
            public java.lang.Boolean call(org.bukkit.configuration.MemorySection parameter) {
                java.lang.String nodeName = parameter.getString("name");
                java.lang.String nodeType = parameter.getString("type");
                return name.equalsIgnoreCase(nodeName) && type.equals(EditorPermissions.Type.parseName(nodeType));
            }
        };
        de.xzise.xwarp.editors.EditorPermissions<T> perms = warpObject.getEditorPermissions(name, false, type);
        if (perms == null) {
            de.xzise.xwarp.dataconnections.YmlConnection.removeFromList(warpObjectNode, "editors", editorCheck);
        } else {
            boolean found = false;
            java.util.List<? extends org.bukkit.configuration.MemorySection> editorNodes = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(warpObjectNode, "editors");
            java.util.List<java.util.Map<java.lang.String, java.lang.Object>> rawEditorNodes = com.google.common.collect.Lists.newArrayListWithExpectedSize(editorNodes.size());
            for (org.bukkit.configuration.MemorySection editorNode : editorNodes) {
                if (editorCheck.call(editorNode)) {
                    if (found) {
                        XWarp.logger.severe(((("Found at least two editor entries for name '" + name) + "' and type '") + type) + "'!");
                    } else {
                        // Got it!
                        editorNode.set("permissions", de.xzise.xwarp.dataconnections.YmlConnection.getPermissionsList(perms));
                        found = true;
                    }
                }
                rawEditorNodes.add(de.xzise.xwarp.dataconnections.YmlConnection.nodeToMap(editorNode));
            }
            // Add entry
            if (!found) {
                rawEditorNodes.add(de.xzise.xwarp.dataconnections.YmlConnection.editorPermissionToMap(perms, name, type));
            }
            warpObjectNode.set("editors", rawEditorNodes);
        }
        this.save();
    }

    @java.lang.Override
    public void updateEditor(de.xzise.xwarp.Warp warp, final java.lang.String name, final de.xzise.xwarp.editors.EditorPermissions.Type type) {
        org.bukkit.configuration.MemorySection warpNode = getWarpNode(de.xzise.xwarp.dataconnections.NameIdentification.create(warp));
        this.updateEditor(warpNode, warp, name, type);
    }

    @java.lang.Override
    public void updatePrice(de.xzise.xwarp.Warp warp) {
        this.updateWarpField(warp, "creator", warp.getPrice());
    }

    private static <T extends java.lang.Enum<T> & de.xzise.xwarp.editors.Editor> java.util.Map<de.xzise.xwarp.editors.EditorPermissions.Type, java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<T>>> getEditorPermissions(java.util.List<? extends org.bukkit.configuration.ConfigurationSection> editorNodes, java.util.Map<java.lang.String, T> names, java.lang.Class<T> clazz) {
        java.util.Map<de.xzise.xwarp.editors.EditorPermissions.Type, java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<T>>> editorPermissions = com.google.common.collect.Maps.newEnumMap(de.xzise.xwarp.editors.EditorPermissions.Type.class);
        for (org.bukkit.configuration.ConfigurationSection editorNode : editorNodes) {
            java.lang.String editorName = editorNode.getString("name");
            java.lang.String editorType = editorNode.getString("type");
            de.xzise.xwarp.editors.EditorPermissions<T> permissions = de.xzise.xwarp.editors.EditorPermissions.create(clazz);
            java.util.List<java.lang.String> editorPermissionPermssions = editorNode.getStringList("permissions");
            if (editorPermissionPermssions != null) {
                for (java.lang.String editorPermission : editorPermissionPermssions) {
                    T perms = names.get(editorPermission.toLowerCase());
                    if (perms == null) {
                        // Unknown permission
                    } else {
                        permissions.put(perms, true);
                    }
                }
            }
            de.xzise.xwarp.editors.EditorPermissions.Type type = de.xzise.xwarp.editors.EditorPermissions.Type.parseName(editorType);
            if (type != null) {
                java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<T>> editor = editorPermissions.get(type);
                if (editor == null) {
                    editor = com.google.common.collect.Maps.newHashMap();
                    editorPermissions.put(type, editor);
                }
                editor.put(editorName.toLowerCase(), permissions);
            }
        }
        return editorPermissions;
    }

    public static de.xzise.xwarp.WarpProtectionArea getWarpProtectionArea(final org.bukkit.configuration.MemorySection node) {
        java.lang.String name = node.getString("name");
        java.lang.String owner = node.getString("owner");
        java.lang.String creator = node.getString("creator");
        de.xzise.xwarp.WorldWrapper worldObject = new de.xzise.xwarp.WorldWrapper(node.getString("world"));
        java.util.List<? extends org.bukkit.configuration.ConfigurationSection> editorNodes = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(node, "editors");
        java.util.Map<de.xzise.xwarp.editors.EditorPermissions.Type, java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<de.xzise.xwarp.editors.WarpProtectionAreaPermissions>>> editorPermissions = de.xzise.xwarp.dataconnections.YmlConnection.getEditorPermissions(editorNodes, WarpProtectionAreaPermissions.STRING_MAP, de.xzise.xwarp.editors.WarpProtectionAreaPermissions.class);
        // Corners:
        java.util.List<? extends org.bukkit.configuration.ConfigurationSection> cornerNodes = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(node, "corners");
        de.xzise.metainterfaces.FixedLocation[] corners = new de.xzise.metainterfaces.FixedLocation[cornerNodes.size()];
        if (corners.length < 2) {
            // At least two corners!
        } else if (corners.length != 2) {
            // As long as only cuboids are possible
        } else {
            int i = 0;
            for (org.bukkit.configuration.ConfigurationSection cornerNode : cornerNodes) {
                java.lang.Double x = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(cornerNode, "x");
                java.lang.Double y = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(cornerNode, "y");
                java.lang.Double z = de.xzise.xwarp.dataconnections.YmlConnection.getDouble(cornerNode, "z");
                if (((x != null) && (y != null)) && (z != null)) {
                    corners[i++] = new de.xzise.metainterfaces.FixedLocation(x, y, z);
                } else {
                    // Invalid corner
                }
            }
        }
        de.xzise.xwarp.WarpProtectionArea warpProtectionArea = new de.xzise.xwarp.WarpProtectionArea(worldObject, corners[0], corners[1], name, owner, creator);
        for (java.util.Map.Entry<de.xzise.xwarp.editors.EditorPermissions.Type, java.util.Map<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<de.xzise.xwarp.editors.WarpProtectionAreaPermissions>>> allPermissionsEntry : editorPermissions.entrySet()) {
            for (java.util.Map.Entry<java.lang.String, de.xzise.xwarp.editors.EditorPermissions<de.xzise.xwarp.editors.WarpProtectionAreaPermissions>> editorEntry : allPermissionsEntry.getValue().entrySet()) {
                warpProtectionArea.getEditorPermissions(editorEntry.getKey(), true, allPermissionsEntry.getKey()).putAll(editorEntry.getValue());
            }
        }
        return warpProtectionArea;
    }

    private <T> java.util.List<T> getList(java.lang.String path, de.xzise.bukkit.util.callback.Callback<T, org.bukkit.configuration.MemorySection> converter) {
        java.util.List<? extends org.bukkit.configuration.MemorySection> sections = de.xzise.bukkit.util.MemorySectionFromMap.getSectionList(this.config, path);
        java.util.ArrayList<T> result = new java.util.ArrayList<T>(sections.size());
        for (org.bukkit.configuration.MemorySection section : sections) {
            T t = converter.call(section);
            if (t != null)
                result.add(t);

        }
        result.trimToSize();
        return result;
    }

    @java.lang.Override
    public java.util.List<de.xzise.xwarp.WarpProtectionArea> getProtectionAreas() {
        return getList(de.xzise.xwarp.dataconnections.YmlConnection.WPA_PATH, de.xzise.xwarp.dataconnections.YmlConnection.NODE_TO_WPA);
    }

    private static void fixedLocToMap(java.util.Map<java.lang.String, java.lang.Object> locMap, de.xzise.metainterfaces.FixedLocation location, boolean world, boolean direction) {
        locMap.put("x", location.x);
        locMap.put("y", location.y);
        locMap.put("z", location.z);
        if (direction) {
            locMap.put("yaw", location.yaw);
            locMap.put("pitch", location.pitch);
        }
        if (world) {
            locMap.put("world", location.world.getName());
        }
    }

    private static java.util.Map<java.lang.String, java.lang.Object> fixedLocToMap(de.xzise.metainterfaces.FixedLocation location, boolean world, boolean direction) {
        java.util.Map<java.lang.String, java.lang.Object> locMap = com.google.common.collect.Maps.newHashMap();
        de.xzise.xwarp.dataconnections.YmlConnection.fixedLocToMap(locMap, location, world, direction);
        return locMap;
    }

    @java.lang.Override
    public void addProtectionArea(de.xzise.xwarp.WarpProtectionArea... areas) {
        java.util.List<java.lang.Object> rawNodes = this.config.getList(de.xzise.xwarp.dataconnections.YmlConnection.WPA_PATH);
        for (de.xzise.xwarp.WarpProtectionArea wpa : areas) {
            java.util.Map<java.lang.String, java.lang.Object> wpaMap = de.xzise.xwarp.dataconnections.YmlConnection.warpObjectToMap(wpa);
            java.util.List<java.util.Map<java.lang.String, java.lang.Object>> corners = com.google.common.collect.Lists.newArrayListWithCapacity(2);
            corners.add(de.xzise.xwarp.dataconnections.YmlConnection.fixedLocToMap(wpa.getCorner(0), false, false));
            corners.add(de.xzise.xwarp.dataconnections.YmlConnection.fixedLocToMap(wpa.getCorner(1), false, false));
            wpaMap.put("corners", corners);
            rawNodes.add(wpaMap);
        }
        this.save();
    }

    @java.lang.Override
    public void deleteProtectionArea(de.xzise.xwarp.WarpProtectionArea area) {
        de.xzise.xwarp.dataconnections.YmlConnection.removeFromList(this.config, de.xzise.xwarp.dataconnections.YmlConnection.WPA_PATH, de.xzise.xwarp.dataconnections.YmlConnection.WarpObjectCallback.create(de.xzise.xwarp.dataconnections.NameIdentification.create(area), de.xzise.xwarp.dataconnections.YmlConnection.NODE_TO_WPA));
        this.save();
    }

    @java.lang.Override
    public void updateEditor(de.xzise.xwarp.WarpProtectionArea warp, java.lang.String name, de.xzise.xwarp.editors.EditorPermissions.Type type) {
        org.bukkit.configuration.MemorySection warpNode = getWarpProtectionAreaNode(de.xzise.xwarp.dataconnections.NameIdentification.create(warp));
        this.updateEditor(warpNode, warp, name, type);
    }

    @java.lang.Override
    public void updateCreator(de.xzise.xwarp.WarpProtectionArea area) {
        this.updateWPAField(de.xzise.xwarp.dataconnections.NameIdentification.create(area), "creator", area.getCreator());
    }

    @java.lang.Override
    public void updateOwner(de.xzise.xwarp.WarpProtectionArea warp, de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.WarpProtectionArea> identification) {
        this.updateWPAField(identification, "owner", warp.getOwner());
    }

    @java.lang.Override
    public void updateName(de.xzise.xwarp.WarpProtectionArea warp, de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.WarpProtectionArea> identification) {
        this.updateWPAField(identification, "name", warp.getName());
    }

    @java.lang.Override
    public void updateWorld(de.xzise.xwarp.WarpProtectionArea area) {
        this.updateWPAField(area, "world", area.getWorld());
    }

    @java.lang.Override
    public de.xzise.xwarp.dataconnections.IdentificationInterface<de.xzise.xwarp.WarpProtectionArea> createWarpProtectionAreaIdentification(de.xzise.xwarp.WarpProtectionArea area) {
        return de.xzise.xwarp.dataconnections.NameIdentification.create(area);
    }

    @java.lang.Override
    public void updateCoolDown(de.xzise.xwarp.Warp warp) {
        this.updateWarpField(warp, "cooldown", warp.getCoolDown());
    }

    @java.lang.Override
    public void updateWarmUp(de.xzise.xwarp.Warp warp) {
        this.updateWarpField(warp, "warmup", warp.getWarmUp());
    }
}