/* Copyright 2013 Kevin Seiden. All rights reserved.

This works is licensed under the Creative Commons Attribution-NonCommercial 3.0

You are Free to:
to Share: to copy, distribute and transmit the work
to Remix: to adapt the work

Under the following conditions:
Attribution: You must attribute the work in the manner specified by the author (but not in any way that suggests that they endorse you or your use of the work).
Non-commercial: You may not use this work for commercial purposes.

With the understanding that:
Waiver: Any of the above conditions can be waived if you get permission from the copyright holder.
Public Domain: Where the work or any of its elements is in the public domain under applicable law, that status is in no way affected by the license.
Other Rights: In no way are any of the following rights affected by the license:
Your fair dealing or fair use rights, or other applicable copyright exceptions and limitations;
The author's moral rights;
Rights other persons may have either in the work itself or in how the work is used, such as publicity or privacy rights.

Notice: For any reuse or distribution, you must make clear to others the license terms of this work. The best way to do this is with a link to this web page.
http://creativecommons.org/licenses/by-nc/3.0/
 */
package io.github.alshain01.flags;
import com.bekvon.bukkit.residence.Residence;
import io.github.alshain01.flags.area.*;
import io.github.alshain01.flags.economy.EconomyPurchaseType;
import io.github.alshain01.flags.sector.Sector;
import java.util.*;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
/**
 * Class for managing YAML Database Storage
 */
final class DataStoreYaml implements DataStore {
    private static final java.lang.String DATA_FILE = "data.yml";

    private static final java.lang.String WILDERNESS_FILE = "wilderness.yml";

    private static final java.lang.String DEFAULT_FILE = "default.yml";

    private static final java.lang.String BUNDLE_FILE = "bundle.yml";

    private static final java.lang.String PRICE_FILE = "price.yml";

    private static final java.lang.String SECTOR_FILE = "sector.yml";

    private static final java.lang.String DATABASE_VERSION_PATH = "Default.Database.Version";

    private static final java.lang.String BUNDLE_PATH = "Bundle";

    private static final java.lang.String TRUST_PATH = "Trust";

    private static final java.lang.String VALUE_PATH = "Value";

    private static final java.lang.String MESSAGE_PATH = "Message";

    private static final java.lang.String INHERIT_PATH = "InheritParent";

    private static final java.lang.String PRICE_PATH = "Price";

    private static final java.lang.String SECTOR_PATH = "Sectors";

    private static final java.lang.String DATA_FOOTER = "Data";

    private final java.io.File dataFolder;

    private final org.bukkit.plugin.Plugin plugin;

    private final int saveInterval;

    // Auto-Save manager
    private org.bukkit.scheduler.BukkitTask as;

    private org.bukkit.configuration.file.YamlConfiguration data;

    private org.bukkit.configuration.file.YamlConfiguration def;

    private org.bukkit.configuration.file.YamlConfiguration wilderness;

    private org.bukkit.configuration.file.YamlConfiguration bundle;

    private org.bukkit.configuration.file.YamlConfiguration price;

    /* Constructor */
    DataStoreYaml(org.bukkit.plugin.Plugin plugin, int autoSaveInterval) {
        this.dataFolder = plugin.getDataFolder();
        this.plugin = plugin;
        this.saveInterval = autoSaveInterval;
        // Upgrade sequence from older versions
        java.io.File worldFile = new java.io.File(dataFolder, "world.yml");
        java.io.File wildFile = new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.WILDERNESS_FILE);
        if (worldFile.exists() && (!wildFile.exists())) {
            if (!worldFile.renameTo(wildFile)) {
                Logger.error("Failed to rename world.yml to wilderness.yml");
            }
        }
        reload();
    }

    private class AutoSave extends org.bukkit.scheduler.BukkitRunnable {
        @java.lang.Override
        public void run() {
            save();
        }
    }

    /* Interface Methods */
    @java.lang.Override
    public void create(org.bukkit.plugin.java.JavaPlugin plugin) {
        // Don't change the version here, not needed (will change in update)
        if (notExists(plugin)) {
            writeVersion(new DataStoreVersion(1, 0, 0));
        }
    }

    @java.lang.Override
    public void reload() {
        wilderness = Material.getMaterial(io.github.alshain01.flags.DataStoreYaml.WILDERNESS_FILE);
        def = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.DEFAULT_FILE));
        data = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.DATA_FILE));
        if (!bundleFile.exists()) {
            plugin.saveResource(io.github.alshain01.flags.DataStoreYaml.BUNDLE_FILE, false);
        }
        // Check to see if the file exists and if not, write the defaults
        java.io.File bundleFile = new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.BUNDLE_FILE);
        bundle = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(bundleFile);
        if (!priceFile.exists()) {
            plugin.saveResource(io.github.alshain01.flags.DataStoreYaml.PRICE_FILE, false);
        }
        // Check to see if the file exists and if not, write the defaults
        java.io.File priceFile = new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.PRICE_FILE);
        price = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(priceFile);
        // Remove old auto-saves
        if (as != null) {
            as.cancel();
            as = null;
        }
        // Set up autosave
        if (saveInterval > 0) {
            as = new io.github.alshain01.flags.DataStoreYaml.AutoSave().runTaskTimer(plugin, saveInterval * 1200, saveInterval * 1200);
        }
    }

    public void save() {
        try {
            wilderness.save(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.WILDERNESS_FILE));
            def.save(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.DEFAULT_FILE));
            data.save(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.DATA_FILE));
            bundle.save(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.BUNDLE_FILE));
            price.save(new java.io.File(dataFolder, io.github.alshain01.flags.DataStoreYaml.PRICE_FILE));
        } catch (java.io.IOException ex) {
            Logger.error("Faled to write to data files. " + ex.getMessage());
        }
    }

    @java.lang.Override
    public void close() {
        save();
    }

    @java.lang.Override
    public io.github.alshain01.flags.DataStoreVersion readVersion() {
        final org.bukkit.configuration.file.YamlConfiguration versionConfig = getYml(io.github.alshain01.flags.DataStoreYaml.DATABASE_VERSION_PATH);
        if (!versionConfig.isSet(io.github.alshain01.flags.DataStoreYaml.DATABASE_VERSION_PATH)) {
            return new DataStoreVersion(0, 0, 0);
        }
        final java.lang.String[] ver = versionConfig.getString(io.github.alshain01.flags.DataStoreYaml.DATABASE_VERSION_PATH).split("\\.");
        return new DataStoreVersion(java.lang.Integer.valueOf(ver[0]), java.lang.Integer.valueOf(ver[1]), java.lang.Integer.valueOf(ver[2]));
    }

    @java.lang.Override
    public io.github.alshain01.flags.DataStoreType getType() {
        return DataStoreType.YAML;
    }

    @java.lang.Override
    public void update(org.bukkit.plugin.java.JavaPlugin plugin) {
        final DataStoreVersion ver = readVersion();
        if (((ver.getMajor() == 1) && (ver.getMinor() == 4)) && (ver.getBuild() == 2)) {
            return;
        }
        if (((ver.getMajor() <= 1) && (ver.getMinor() <= 4)) && (ver.getBuild() <= 2)) {
            if (((ver.getMajor() <= 1) && (ver.getMinor() <= 2)) && (ver.getBuild() < 2)) {
                org.bukkit.configuration.file.YamlConfiguration dataConfig = getYml("data");
                org.bukkit.configuration.ConfigurationSection cSec;
                CuboidType system = CuboidType.getActive();
                if (dataConfig.isConfigurationSection(CuboidType.getActive().toString() + io.github.alshain01.flags.DataStoreYaml.DATA_FOOTER)) {
                    if ((system == CuboidType.GRIEF_PREVENTION) || (CuboidType.getActive() == CuboidType.RESIDENCE)) {
                        cSec = dataConfig.getConfigurationSection(CuboidType.getActive().toString() + io.github.alshain01.flags.DataStoreYaml.DATA_FOOTER);
                        final java.util.Set<java.lang.String> keys = cSec.getKeys(true);
                        for (final java.lang.String k : keys) {
                            if (((k.contains(io.github.alshain01.flags.DataStoreYaml.VALUE_PATH) || k.contains(io.github.alshain01.flags.DataStoreYaml.MESSAGE_PATH)) || k.contains(io.github.alshain01.flags.DataStoreYaml.TRUST_PATH)) || k.contains(io.github.alshain01.flags.DataStoreYaml.INHERIT_PATH)) {
                                final java.lang.String id = k.split("\\.")[0];
                                java.lang.String world;
                                if (CuboidType.getActive() == CuboidType.GRIEF_PREVENTION) {
                                    world = GriefPrevention.instance.dataStore.getClaim(java.lang.Long.valueOf(id)).getGreaterBoundaryCorner().getWorld().getName();
                                } else {
                                    world = com.bekvon.bukkit.residence.Residence.getResidenceManager().getByName(id).getWorld();
                                }
                                cSec.set((world + ".") + k, cSec.get(k));
                            }
                        }
                        // Remove the old
                        for (final java.lang.String k : keys) {
                            if ((k.split("\\.").length == 1) && (org.bukkit.Bukkit.getWorld(k.split("\\.")[0]) == null)) {
                                cSec.set(k, null);
                            }
                        }
                    }
                    // Convert values to boolean instead of string
                    final java.lang.String[] fileArray = new java.lang.String[]{ "data", "default", "world" };
                    for (final java.lang.String s : fileArray) {
                        dataConfig = getYml(s);
                        java.util.Set<java.lang.String> keys = dataConfig.getKeys(true);
                        for (final java.lang.String k : keys) {
                            if (k.contains("Value") || k.contains("InheritParent")) {
                                dataConfig.set(k, java.lang.Boolean.valueOf(dataConfig.getString(k)));
                            }
                        }
                    }
                    // Remove "Data" from the root heading.
                    dataConfig = getYml("data");
                    cSec = getYml("data").getConfigurationSection(CuboidType.getActive().toString() + "Data");
                    org.bukkit.configuration.ConfigurationSection newCSec = getYml("data").createSection(CuboidType.getActive().toString());
                    java.util.Set<java.lang.String> keys = cSec.getKeys(true);
                    for (final java.lang.String k : keys) {
                        if (((k.contains(io.github.alshain01.flags.DataStoreYaml.VALUE_PATH) || k.contains(io.github.alshain01.flags.DataStoreYaml.MESSAGE_PATH)) || k.contains(io.github.alshain01.flags.DataStoreYaml.TRUST_PATH)) || k.contains(io.github.alshain01.flags.DataStoreYaml.INHERIT_PATH)) {
                            newCSec.set(k, cSec.get(k));
                            cSec.set(k, null);
                        }
                    }
                    dataConfig.set(CuboidType.getActive().toString() + "Data", null);
                }
                writeVersion(new DataStoreVersion(1, 2, 2));
            }
            // Upgrade to 1.4.2
            org.bukkit.configuration.file.YamlConfiguration cYml = getYml("wilderness");
            if (cYml.isConfigurationSection("World")) {
                org.bukkit.configuration.ConfigurationSection cSec = cYml.getConfigurationSection("World");
                org.bukkit.configuration.ConfigurationSection newCSec = cYml.createSection("Wilderness");
                for (final java.lang.String k : cSec.getKeys(true)) {
                    if (((k.contains(io.github.alshain01.flags.DataStoreYaml.VALUE_PATH) || k.contains(io.github.alshain01.flags.DataStoreYaml.MESSAGE_PATH)) || k.contains(io.github.alshain01.flags.DataStoreYaml.TRUST_PATH)) || k.contains(io.github.alshain01.flags.DataStoreYaml.INHERIT_PATH)) {
                        newCSec.set(k, cSec.get(k));
                    }
                }
            }
            cYml.set("World", null);
            writeVersion(new DataStoreVersion(1, 4, 2));
        }
    }

    @java.lang.Override
    public final java.util.Set<java.lang.String> readBundles() {
        if (bundle.isConfigurationSection(io.github.alshain01.flags.DataStoreYaml.BUNDLE_PATH)) {
            return bundle.getConfigurationSection(io.github.alshain01.flags.DataStoreYaml.BUNDLE_PATH).getKeys(false);
        }
        return new java.util.HashSet<java.lang.String>();
    }

    @java.lang.Override
    public final java.util.Set<Flag> readBundle(java.lang.String bundleName) {
        final java.util.HashSet<Flag> flags = new java.util.HashSet<Flag>();
        if (!bundle.isConfigurationSection(io.github.alshain01.flags.DataStoreYaml.BUNDLE_PATH)) {
            return flags;
        }
        final java.util.List<?> list = bundle.getConfigurationSection(io.github.alshain01.flags.DataStoreYaml.BUNDLE_PATH).getList(bundleName, new java.util.ArrayList<java.lang.String>());
        for (final java.lang.Object o : list) {
            if (Flags.getRegistrar().isFlag(((java.lang.String) (o)))) {
                flags.add(Flags.getRegistrar().getFlag(((java.lang.String) (o))));
            }
        }
        return flags;
    }

    @java.lang.Override
    public final void writeBundle(java.lang.String name, java.util.Set<Flag> flags) {
        org.bukkit.configuration.ConfigurationSection bundleConfig = getCreatedSection(bundle, io.github.alshain01.flags.DataStoreYaml.BUNDLE_PATH);
        if ((flags == null) || (flags.size() == 0)) {
            // Delete the bundle
            bundleConfig.set(name, null);
            return;
        }
        final java.util.List<java.lang.String> list = new java.util.ArrayList<java.lang.String>();
        for (final Flag f : flags) {
            list.add(f.getName());
        }
        bundleConfig.set(name, list);
    }

    @java.lang.Override
    public java.lang.Boolean readFlag(Area area, Flag flag) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        if (!getYml(path).isConfigurationSection(path)) {
            return null;
        }
        final org.bukkit.configuration.ConfigurationSection flagConfig = getYml(path).getConfigurationSection(path);
        return flagConfig.isSet(io.github.alshain01.flags.DataStoreYaml.VALUE_PATH) ? flagConfig.getBoolean(io.github.alshain01.flags.DataStoreYaml.VALUE_PATH) : null;
    }

    @java.lang.Override
    public void writeFlag(Area area, Flag flag, java.lang.Boolean value) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        final org.bukkit.configuration.ConfigurationSection flagConfig = getCreatedSection(getYml(path), path);
        flagConfig.set(io.github.alshain01.flags.DataStoreYaml.VALUE_PATH, value);
    }

    @java.lang.Override
    public java.lang.String readMessage(Area area, Flag flag) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        if (!getYml(path).isConfigurationSection(path)) {
            return null;
        }
        return getYml(path).getConfigurationSection(path).getString(io.github.alshain01.flags.DataStoreYaml.MESSAGE_PATH);
    }

    @java.lang.Override
    public void writeMessage(Area area, Flag flag, java.lang.String message) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        final org.bukkit.configuration.ConfigurationSection dataConfig = getCreatedSection(getYml(path), path);
        dataConfig.set(io.github.alshain01.flags.DataStoreYaml.MESSAGE_PATH, message);
    }

    @java.lang.Override
    public double readPrice(Flag flag, io.github.alshain01.flags.economy.EconomyPurchaseType type) {
        final java.lang.String path = (io.github.alshain01.flags.DataStoreYaml.PRICE_PATH + ".") + type.toString();
        if (!price.isConfigurationSection(path)) {
            return 0;
        }
        final org.bukkit.configuration.ConfigurationSection priceConfig = price.getConfigurationSection(path);
        return priceConfig.isSet(flag.getName()) ? priceConfig.getDouble(flag.getName()) : 0;
    }

    @java.lang.Override
    public void writePrice(Flag flag, io.github.alshain01.flags.economy.EconomyPurchaseType type, double newPrice) {
        final java.lang.String path = (io.github.alshain01.flags.DataStoreYaml.PRICE_PATH + ".") + type.toString();
        final org.bukkit.configuration.ConfigurationSection priceConfig = getCreatedSection(price, path);
        priceConfig.set(flag.getName(), newPrice);
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> readTrust(Area area, Flag flag) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        final java.util.Set<java.lang.String> stringData = new java.util.HashSet<java.lang.String>();
        if (!getYml(path).isConfigurationSection(path)) {
            return stringData;
        }
        final java.util.List<?> setData = getYml(path).getConfigurationSection(path).getList(io.github.alshain01.flags.DataStoreYaml.TRUST_PATH, new java.util.ArrayList<java.lang.String>());
        for (final java.lang.Object o : setData) {
            stringData.add(((java.lang.String) (o)));
        }
        return stringData;
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> readPlayerTrust(Area area, Flag flag) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        final java.util.Set<java.lang.String> stringData = new java.util.HashSet<java.lang.String>();
        if (!getYml(path).isConfigurationSection(path)) {
            return stringData;
        }
        final java.util.List<?> setData = getYml(path).getConfigurationSection(path).getList(io.github.alshain01.flags.DataStoreYaml.TRUST_PATH, new java.util.ArrayList<java.lang.String>());
        for (final java.lang.Object o : setData) {
            if (!((java.lang.String) (o)).contains(".")) {
                stringData.add(((java.lang.String) (o)));
            }
        }
        return stringData;
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> readPermissionTrust(Area area, Flag flag) {
        final java.lang.String path = (getAreaPath(area) + ".") + flag.getName();
        final java.util.Set<java.lang.String> stringData = new java.util.HashSet<java.lang.String>();
        if (!getYml(path).isConfigurationSection(path)) {
            return stringData;
        }
        final java.util.List<?> setData = getYml(path).getConfigurationSection(path).getList(io.github.alshain01.flags.DataStoreYaml.TRUST_PATH, new java.util.ArrayList<java.lang.String>());
        for (final java.lang.Object o : setData) {
            if (((java.lang.String) (o)).contains(".")) {
                stringData.add(((java.lang.String) (o)));
            }
        }
        return stringData;
    }

    @java.lang.Override
    public void writeTrust(Area area, Flag flag, java.util.Set<java.lang.String> players) {
        final java.lang.String path = ((getAreaPath(area) + ".") + flag.getName()) + ".Trust";
        final org.bukkit.configuration.ConfigurationSection trustConfig = getCreatedSection(getYml(path), path);
        final java.util.List<java.lang.String> list = new java.util.ArrayList<java.lang.String>();
        for (final java.lang.String s : players) {
            list.add(s);
        }
        trustConfig.set(path, list);
    }

    @java.lang.Override
    public boolean readInheritance(Area area) {
        if ((!(area instanceof Subdivision)) || (!((Subdivision) (area)).isSubdivision())) {
            return true;
        }
        final java.lang.String path = getAreaPath(area);
        if (!getYml(path).isConfigurationSection(path)) {
            return true;
        }
        org.bukkit.configuration.ConfigurationSection inheritConfig = getYml(path).getConfigurationSection(path);
        return (!inheritConfig.isSet(io.github.alshain01.flags.DataStoreYaml.INHERIT_PATH)) || inheritConfig.getBoolean(io.github.alshain01.flags.DataStoreYaml.INHERIT_PATH);
    }

    @java.lang.Override
    public void writeInheritance(Area area, boolean value) {
        if ((!(area instanceof Subdivision)) || (!((Subdivision) (area)).isSubdivision())) {
            return;
        }
        final java.lang.String path = getAreaPath(area);
        org.bukkit.configuration.ConfigurationSection inheritConfig = getCreatedSection(getYml(path), path);
        inheritConfig.set(path, value);
    }

    @java.lang.Override
    public java.util.Map<java.util.UUID, io.github.alshain01.flags.sector.Sector> readSectors() {
        java.util.Map<java.util.UUID, io.github.alshain01.flags.sector.Sector> sectors = new java.util.HashMap<java.util.UUID, io.github.alshain01.flags.sector.Sector>();
        java.io.File file = new java.io.File(plugin.getDataFolder(), io.github.alshain01.flags.DataStoreYaml.SECTOR_FILE);
        if (!file.exists()) {
            return sectors;
        }
        org.bukkit.configuration.file.YamlConfiguration sector = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        if (!sector.isConfigurationSection(io.github.alshain01.flags.DataStoreYaml.SECTOR_PATH)) {
            return sectors;
        }
        org.bukkit.configuration.ConfigurationSection sectorConfig = sector.getConfigurationSection(io.github.alshain01.flags.DataStoreYaml.SECTOR_PATH);
        for (java.lang.String s : sectorConfig.getKeys(false)) {
            java.util.UUID sID = java.util.UUID.fromString(s);
            sectors.put(sID, new io.github.alshain01.flags.sector.Sector(sID, sectorConfig.getConfigurationSection(s).getValues(false)));
        }
        return sectors;
    }

    @java.lang.Override
    public void writeSectors(java.util.Collection<io.github.alshain01.flags.sector.Sector> sectors) {
        org.bukkit.configuration.file.YamlConfiguration sector = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(new java.io.File(plugin.getDataFolder(), io.github.alshain01.flags.DataStoreYaml.SECTOR_FILE));
        org.bukkit.configuration.ConfigurationSection sectorConfig = getCreatedSection(sector, io.github.alshain01.flags.DataStoreYaml.SECTOR_PATH);
        for (io.github.alshain01.flags.sector.Sector s : sectors) {
            sectorConfig.set(s.getID().toString(), s.serialize());
        }
    }

    @java.lang.Override
    public void remove(Area area) {
        java.lang.String path = getAreaPath(area);
        getYml(path).set(path, null);
    }

    /* Used in SQL Import/Export */
    // Future Use
    @java.lang.SuppressWarnings("unused")
    protected java.util.Set<java.lang.String> getAreaIDs() {
        org.bukkit.configuration.ConfigurationSection cSec = getYml("data").getConfigurationSection(CuboidType.getActive().toString() + "Data");
        if (cSec == null) {
            return new java.util.HashSet<java.lang.String>();
        }
        return cSec.getKeys(false);
    }

    // Future Use
    @java.lang.SuppressWarnings("unused")
    protected java.util.Set<java.lang.String> getAreaSubIDs(java.lang.String id) {
        org.bukkit.configuration.ConfigurationSection cSec = getYml("data").getConfigurationSection((CuboidType.getActive().toString() + "Data.") + id);
        java.util.Set<java.lang.String> subIDs = new java.util.HashSet<java.lang.String>();
        if (cSec == null) {
            return subIDs;
        }
        for (java.lang.String i : cSec.getKeys(true)) {
            java.lang.String[] nodes = i.split("\\.");
            if (nodes.length == 4) {
                // All keys for the parent should have 3 nodes.
                subIDs.add(nodes[0]);
            }
        }
        return subIDs;
    }

    java.util.Set<java.lang.String> readKeys() {
        return data.getKeys(true);
    }

    java.lang.Boolean getBoolean(java.lang.String dataLocation) {
        if (data.isBoolean(dataLocation)) {
            return data.getBoolean(dataLocation);
        }
        return null;
    }

    java.lang.String getString(java.lang.String dataLocation) {
        return data.getString(dataLocation);
    }

    java.util.List<?> getList(java.lang.String dataLocation) {
        return data.getList(dataLocation);
    }

    /* Private */
    private void writeVersion(DataStoreVersion version) {
        final org.bukkit.configuration.file.YamlConfiguration cYml = getYml(io.github.alshain01.flags.DataStoreYaml.DATABASE_VERSION_PATH);
        cYml.set(io.github.alshain01.flags.DataStoreYaml.DATABASE_VERSION_PATH, (((version.getMajor() + ".") + version.getMinor()) + ".") + version.getBuild());
    }

    private boolean notExists(org.bukkit.plugin.java.JavaPlugin plugin) {
        final java.io.File fileObject = new java.io.File(plugin.getDataFolder(), "default.yml");
        return !fileObject.exists();
    }

    private java.lang.String getAreaPath(Area area) {
        java.lang.String path = (area.getCuboidType().toString() + ".") + area.getWorld().getName();
        if (!((area instanceof Wilderness) || (area instanceof Default))) {
            path += "." + area.getSystemID();
        }
        if ((area instanceof Subdivision) && (!readInheritance(area))) {
            path += "." + ((Subdivision) (area)).getSystemSubID();
        }
        return path;
    }

    private org.bukkit.configuration.ConfigurationSection getCreatedSection(org.bukkit.configuration.file.YamlConfiguration config, java.lang.String path) {
        if (config.isConfigurationSection(path)) {
            return config.getConfigurationSection(path);
        } else {
            return config.createSection(path);
        }
    }

    private org.bukkit.configuration.file.YamlConfiguration getYml(java.lang.String path) {
        final java.lang.String[] pathList = path.split("\\.");
        if (pathList[0].equalsIgnoreCase("wilderness")) {
            return wilderness;
        } else if (pathList[0].equalsIgnoreCase("default")) {
            return def;
        } else {
            return data;
        }
    }
}