/**
 *
 */
@java.lang.Override
public void onEnable() {
    this.server = getServer();
    this.pm = this.server.getPluginManager();
    org.bukkit.plugin.Plugin ess = pm.getPlugin("Essentials");
    if ((ess != null) && (ess instanceof com.earth2me.essentials.Essentials)) {
        this.hasEssentials = true;
        this.ess = ((com.earth2me.essentials.Essentials) (ess));
    }
    if (!setupEconomy()) {
        logError("Disabled due to no Vault dependency found!");
        setEnabled(false);
        return;
    }
    try {
        this.lots = new de.bangl.lm.LotManagerDatabase();
    } catch (java.lang.Exception e3) {
        logError(e3.getMessage());
    }
    new java.io.File(this.dataFolder).mkdir();
    loadConfig();
    try {
        this.lots.sqlClass();
    } catch (java.lang.ClassNotFoundException e) {
        logError("MySQL Class not loadable!");
        setEnabled(false);
    }
    try {
        this.lots.connect();
    } catch (java.sql.SQLException e) {
        logError("MySQL connection failed!");
        setEnabled(false);
    }
    if (this.lots.getConnection() == null) {
        logInfo("Connection failed.");
        setEnabled(false);
        return;
    }
    try {
        if (!this.lots.checkTables()) {
            logInfo("Tables not found. created.");
            this.lots.Disconnect();
            setEnabled(false);
            return;
        }
    } catch (java.lang.ClassNotFoundException e) {
        logError("Tablecheck failed!");
        setEnabled(false);
    } catch (java.sql.SQLException e) {
        logError("Tablecheck failed!");
        setEnabled(false);
    }
    try {
        this.lots.load();
    } catch (java.lang.ClassNotFoundException e) {
        logError(e.getMessage());
    } catch (java.sql.SQLException e) {
        logError(e.getMessage());
    }
    try {
        this.wg = new de.bangl.lm.WorldGuardWrapper(this.pm, this.server.getWorlds());
        if (this.wg == null) {
            logError("WorldGuardWrapper is null :O");
            setEnabled(false);
            return;
        }
        if (this.wg.getWG() == null) {
            logError("WorldGuardPlugin is null :O");
            setEnabled(false);
            return;
        }
        for (org.bukkit.World world : this.server.getWorlds()) {
            if (world == null) {
                logError("World is null :O");
                setEnabled(false);
                return;
            }
            if (this.wg.getRegionManager(world) == null) {
                org.bukkit.World _CVAR0 = world;
                java.lang.String _CVAR1 = _CVAR0.getName();
                java.lang.String _CVAR2 = "RegionManager of " + _CVAR1;
                java.lang.String _CVAR3 = _CVAR2 + " is null :O";
                logError(_CVAR3);
                setEnabled(false);
                return;
            }
        }
    } catch (java.lang.ClassNotFoundException e1) {
        logError("Wasn't able get a connection to the WorldGuard API.");
        setEnabled(false);
        return;
    }
    if (this.signsFile.exists()) {
        loadSigns();
    }
    this.pm.registerEvents(this.eventListener, this);
}