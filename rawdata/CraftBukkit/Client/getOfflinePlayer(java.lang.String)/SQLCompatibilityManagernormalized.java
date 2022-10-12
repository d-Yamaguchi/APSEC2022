@java.lang.SuppressWarnings("deprecation")
private void migrateUuids(java.sql.Connection con) throws java.lang.Exception {
    java.util.HashMap<java.lang.String, java.lang.Integer> usernameUidMap = new java.util.HashMap<java.lang.String, java.lang.Integer>(256);
    java.sql.ResultSet usernameResults = con.createStatement().executeQuery("SELECT uid, username FROM PlayerOld");
    try {
        while (usernameResults.next()) {
            int uid = usernameResults.getInt("uid");
            java.lang.String username = usernameResults.getString("username");
            usernameUidMap.put(username, uid);
        } 
    } finally {
        com.overmc.overpermissions.MySQLManager.attemptClose(usernameResults);
    }
    java.util.Map<java.lang.String, java.util.UUID> uuidMap;// Convert usernames to UUIDs

    if (org.bukkit.Bukkit.getOnlineMode()) {
        com.overmc.overpermissions.uuid.UUIDFetcher fetcher = new com.overmc.overpermissions.uuid.UUIDFetcher(new java.util.ArrayList<java.lang.String>(usernameUidMap.keySet()));
        uuidMap = fetcher.call();
    } else {
        uuidMap = new java.util.HashMap<java.lang.String, java.util.UUID>(usernameUidMap.size());
        for (java.util.Map.Entry<java.lang.String, java.lang.Integer> e : usernameUidMap.entrySet()) {
            java.util.Map.Entry<java.lang.String, java.lang.Integer> _CVAR1 = e;
            java.util.Map.Entry<java.lang.String, java.lang.Integer> _CVAR3 = e;
            java.lang.String _CVAR4 = _CVAR3.getKey();
            org.bukkit.OfflinePlayer _CVAR5 = org.bukkit.Bukkit.getOfflinePlayer(_CVAR4);
            java.util.Map<java.lang.String, java.util.UUID> _CVAR0 = uuidMap;
            java.lang.String _CVAR2 = _CVAR1.getKey();
            java.util.UUID _CVAR6 = _CVAR5.getUniqueId();
            _CVAR0.put(_CVAR2, _CVAR6);// Offline conversion is considerably less elegant.

        }
    }
    for (java.util.Map.Entry<java.lang.String, java.lang.Integer> playerData : usernameUidMap.entrySet()) {
        // Iterate over usernames.
        if (uuidMap.containsKey(playerData.getKey().toLowerCase())) {
            // We've got a match!
            java.util.UUID uuid = uuidMap.get(playerData.getKey().toLowerCase());
            java.sql.PreparedStatement pst = null;
            try {
                pst = con.prepareStatement("INSERT INTO Player(uid, last_seen_username, lower_uid, upper_uid) VALUES (?, ?, ?, ?)");
                pst.setInt(1, playerData.getValue());
                pst.setString(2, playerData.getKey());
                pst.setLong(3, uuid.getLeastSignificantBits());
                pst.setLong(4, uuid.getMostSignificantBits());
                pst.executeUpdate();
                executeStatement(con, ("DELETE FROM PlayerOld WHERE uid=\"" + playerData.getValue()) + "\"");// I know... sloppy, but no sqlI here.

            } finally {
                com.overmc.overpermissions.MySQLManager.attemptClose(pst);
            }
        } else {
            plugin.getLogger().warning(("Failed to transfer username \"" + playerData.getKey()) + ".\" UUID doesn\'t exist!");
        }
    }
    java.sql.Statement tableEmptyStatement = null;
    java.sql.ResultSet tableEmpty = null;
    try {
        tableEmptyStatement = con.createStatement();
        tableEmpty = tableEmptyStatement.executeQuery("SELECT COUNT(*) FROM PlayerOld");
        if (tableEmpty.next() && (tableEmpty.getInt(1) == 0)) {
            plugin.getLogger().info("Migration: Deleting empty old player table...");
            executeStatement(con, "DROP TABLE PlayerOld");
        } else {
            plugin.getLogger().warning("Migration: There were some usernames that failed to transfer. Old player table data not deleted.");
        }
    } finally {
        com.overmc.overpermissions.MySQLManager.attemptClose(tableEmpty);
        com.overmc.overpermissions.MySQLManager.attemptClose(tableEmptyStatement);
    }
}