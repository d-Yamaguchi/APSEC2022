package de.xzise.xwarp;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.util.config.Configuration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

import de.xzise.xwarp.dataconnections.DataConnection;
import de.xzise.xwarp.dataconnections.HModConnection;
import de.xzise.xwarp.dataconnections.SQLiteConnection;
import de.xzise.xwarp.dataconnections.YmlConnection;
import de.xzise.xwarp.lister.GenericLister.Column;

public class PluginProperties {

    private static final ImmutableList<String> DEFAULT_VISIBILITIES = ImmutableList.of("public", "global");
    private static final ImmutableList<String> DEFAULT_COLUMNS = ImmutableList.of("owner", "world", "location");

    private DataConnection dataConnection;
    private boolean cooldownNotify;
    private boolean warmupNotify;
    private boolean useForceTo;
    private boolean showFreePriceMessage;
    private boolean cancelWarmUpOnDamage;
    private boolean cancelWarmUpOnMovement;
    private boolean createUpdates;
    private boolean caseSensitive;
    private String defaultMessage;

    private String permissionsPlugin;

    private String economyPlugin;
    private String economyBaseAccount;

    private boolean markerEnabled;
    private String markerPNG;
    private ImmutableList<String> markerVisibilities;

    private ImmutableSet<Column> defaultColumns;

    private final File dataDirectory;
    private final File configFile;
    private final Server server;

    public PluginProperties(File dataDirectory, Server server) {
        this.dataDirectory = dataDirectory;
        this.configFile = new File(this.dataDirectory, "config.yml");
        this.server = server;
        this.read();
    }

    public DataConnection getDataConnection() {
        return this.dataConnection;
    }

    public File getDataConnectionFile() {
        return new File(this.dataDirectory, this.dataConnection.getFilename());
    }

    private static String getPlugin(String value) {
        if (value.equalsIgnoreCase("none") || value.equalsIgnoreCase("null")) {
            return null;
        } else {
            return value;
        }
    }

    public String getEconomyPlugin() {
        return getPlugin(this.economyPlugin);
    }

    public String getPermissionsPlugin() {
        return getPlugin(this.permissionsPlugin);
    }

    public String getEconomyBaseAccount() {
        return this.economyBaseAccount;
    }

    public boolean isCooldownNotify() {
        return this.cooldownNotify;
    }

    public boolean isWarmupNotify() {
        return this.warmupNotify;
    }

    public boolean isForceToUsed() {
        return this.useForceTo;
    }

    public boolean isCancelWarmUpOnDamage() {
        return this.cancelWarmUpOnDamage;
    }

    public boolean isCancelWarmUpOnMovement() {
        return this.cancelWarmUpOnMovement;
    }

    public boolean showFreePriceMessage() {
        return this.showFreePriceMessage;
    }

    public boolean isCreationUpdating() {
        return this.createUpdates;
    }

    public boolean isCaseSensitive() {
        return this.caseSensitive;
    }

    public String getMarkerPNG() {
        return this.markerPNG;
    }

    public ImmutableList<String> getMarkerVisibilities() {
        return this.markerVisibilities;
    }

    public boolean isMarkerEnabled() {
        return this.markerEnabled;
    }

    public ImmutableSet<Column> getListColumns() {
        return this.defaultColumns;
    }

    public void read() {
        Configuration configuration = new Configuration(this.configFile);
        if (this.configFile.exists()) {
            configuration.load();
        } else {
            configuration.setProperty("data.connection", "sqlite");
            configuration.setProperty("economy.plugin", "");
            configuration.setProperty("economy.base-account", "");
            configuration.setProperty("permissions.plugin", "");
            configuration.setProperty("warmup.notify", true);
            configuration.setProperty("warmup.cancel.movement", false);
            configuration.setProperty("warmup.cancel.damage", true);
            configuration.setProperty("cooldown.notify", true);
            configuration.setProperty("case-sensitive", false);
            configuration.setProperty("update-if-exists", false);
            configuration.setProperty("use-force-to", false);
            configuration.setProperty("show-free-price-message", false);
            configuration.setProperty("warp.defaultmsg", "Welcome to '{NAME}'!");
            configuration.setProperty("marker.png", "marker.png");
            configuration.setProperty("marker.visibilities", DEFAULT_VISIBILITIES);
            configuration.setProperty("marker.enabled", false);
            configuration.setProperty("list.columns", DEFAULT_COLUMNS);
            if (configuration.save()) {
                XWarp.logger.info("Successfully created default configuration file.");
            } else {
                XWarp.logger.warning("Unable to create properties file.");
            }
        }

        String dataConnectionProperty = configuration.getString("data.connection");

        if ("hmod".equalsIgnoreCase(dataConnectionProperty)) {
            this.dataConnection = new HModConnection(this.server);
        } else if ("yml".equalsIgnoreCase(dataConnectionProperty)) {
            this.dataConnection = new YmlConnection();
        } else {
            if (!"sqlite".equalsIgnoreCase(dataConnectionProperty)) {
                XWarp.logger.warning("Unrecognized data-connection selected (" + dataConnectionProperty + ")");
            }
            // Per default sqlite
            this.dataConnection = new SQLiteConnection(server);
        }

        this.caseSensitive = configuration.getBoolean("case-sensitive", false);

        this.economyPlugin = configuration.getString("economy.plugin", "");
        this.economyBaseAccount = configuration.getString("economy.base-account", "");

        this.permissionsPlugin = configuration.getString("permissions.plugin", "");

        this.cooldownNotify = configuration.getBoolean("cooldown.notify", true);

        this.warmupNotify = configuration.getBoolean("warmup.notify", true);
        this.cancelWarmUpOnDamage = configuration.getBoolean("warmup.cancel.damage", true);
        this.cancelWarmUpOnMovement = configuration.getBoolean("warmup.cancel.movement", false);

        this.useForceTo = configuration.getBoolean("use-force-to", false);
        this.showFreePriceMessage = configuration.getBoolean("show-free-price-message", true);
        this.createUpdates = configuration.getBoolean("update-if-exists", false);

        this.markerPNG = configuration.getString("marker.png", "marker.png");
        this.markerVisibilities = ImmutableList.copyOf(configuration.getStringList("marker.visibilities", DEFAULT_VISIBILITIES));
        this.markerEnabled = configuration.getBoolean("marker.enabled", false);

        Builder<Column> columnsBuilder = ImmutableSet.builder();
        for (String column : configuration.getStringList("list.columns", DEFAULT_COLUMNS)) {
            if (column.equalsIgnoreCase("owner")) {
                columnsBuilder.add(Column.OWNER);
            } else if (column.equalsIgnoreCase("world")) {
                columnsBuilder.add(Column.WORLD);
            } else if (column.equalsIgnoreCase("location")) {
                columnsBuilder.add(Column.LOCATION);
            }
        }
        this.defaultColumns = columnsBuilder.build();

        this.defaultMessage = configuration.getString("warp.defaultmsg", "Welcome to '{NAME}'!");
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    public ImmutableSet<Column> getDefaultColumns() {
        return this.defaultColumns;
    }
}
