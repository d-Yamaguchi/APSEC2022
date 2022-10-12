@java.lang.Override
public boolean openConnection() {
    try {
        org.bukkit.configuration.file.FileConfiguration _CVAR0 = config;
        java.lang.String _CVAR1 = "MySQL.host";
        java.lang.String _CVAR2 = "localhost";
        // get config info
        java.lang.String host = _CVAR0.getString(_CVAR1, _CVAR2);
        java.lang.String port = java.lang.String.valueOf(config.getInt("MySQL.port", 3306));
        org.bukkit.configuration.file.FileConfiguration _CVAR3 = config;
        java.lang.String _CVAR4 = "MySQL.database";
        java.lang.String _CVAR5 = "loginsecurity";
        java.lang.String databse = _CVAR3.getString(_CVAR4, _CVAR5);
        org.bukkit.configuration.file.FileConfiguration _CVAR6 = config;
        java.lang.String _CVAR7 = "MySQL.username";
        java.lang.String _CVAR8 = "root";
        java.lang.String username = _CVAR6.getString(_CVAR7, _CVAR8);
        org.bukkit.configuration.file.FileConfiguration _CVAR9 = config;
        java.lang.String _CVAR10 = "MySQL.password";
        java.lang.String _CVAR11 = "";
        java.lang.String pass = _CVAR9.getString(_CVAR10, _CVAR11);
        // open connection
        con = java.sql.DriverManager.getConnection(((((((((("jdbc:mysql://" + host) + ":") + port) + "/") + databse) + "?") + "user=") + username) + "&password=") + pass);
        statement = con.createStatement();
        // set the timeout
        statement.setQueryTimeout(30);
        return true;
    } catch (java.sql.SQLException e) {
        log.warning("[LoginSecurity] Could not open MySQL connection: " + e.getMessage());
        return false;
    }
}