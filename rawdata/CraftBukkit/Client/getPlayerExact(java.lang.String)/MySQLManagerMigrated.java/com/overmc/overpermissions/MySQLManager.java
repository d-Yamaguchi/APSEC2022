package com.overmc.overpermissions;
public final class MySQLManager implements com.overmc.overpermissions.SQLManager {
    private final com.overmc.overpermissions.OverPermissions plugin;

    protected java.sql.Connection con = null;

    private final java.lang.String dbUrl;

    private final java.lang.String dbName;

    private final java.lang.String dbUsername;

    private final java.lang.String dbPassword;

    public java.sql.Connection getConnection() throws java.sql.SQLException {
        if ((con == null) || con.isClosed()) {
            con = java.sql.DriverManager.getConnection(dbUrl + dbName, dbUsername, dbPassword);
        }
        return con;
    }

    public MySQLManager(com.overmc.overpermissions.OverPermissions plugin, java.lang.String dbUrl, java.lang.String dbName, java.lang.String dbUsername, java.lang.String dbPassword) throws java.lang.Throwable {
        this.plugin = plugin;
        this.dbUrl = dbUrl;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        initDatabase();
    }

    private void initDatabase() throws java.lang.Throwable {
        java.lang.Class.forName("com.mysql.jdbc.Driver");
        try {
            con = java.sql.DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException e) {
            throw new com.overmc.overpermissions.StartException(com.overmc.overpermissions.Messages.format(Messages.ERROR_SQL_NOT_CONNECTED, dbUrl));
        } catch (java.sql.SQLException e) {
            if (e.getMessage().startsWith("Unable to open a test connection")) {
                throw new com.overmc.overpermissions.StartException(com.overmc.overpermissions.Messages.format(Messages.ERROR_SQL_NOT_CONNECTED, dbUrl));
            }
        }
        java.sql.Statement st = null;
        try {
            st = con.createStatement();
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            st.close();
            st = con.createStatement();
            st.executeUpdate("USE " + dbName);
            st.close();
            st = con.createStatement();
            st.addBatch("CREATE TABLE IF NOT EXISTS Player" + (((((("(" + "uid int AUTO_INCREMENT PRIMARY KEY,") + "last_seen_username varchar(16),") + "lower_uid BIGINT NOT NULL,") + "upper_uid BIGINT NOT NULL,") + "INDEX username (lower_uid, upper_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS World" + (((("(" + "uid int AUTO_INCREMENT PRIMARY KEY,") + "name varchar(16) NOT NULL,") + "INDEX name (name ASC)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Permission" + (((("(" + "uid int AUTO_INCREMENT PRIMARY KEY,") + "permission varchar(50) NOT NULL,") + "INDEX permission (permission ASC)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Permission" + (((((((("(" + "world_uid int NOT NULL,") + "permission_uid int NOT NULL,") + "player_uid int NOT NULL,") + "FOREIGN KEY(world_uid) REFERENCES World(uid),") + "FOREIGN KEY(permission_uid) REFERENCES Permission(uid),") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "PRIMARY KEY(world_uid, permission_uid, player_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Permission_Timeout" + ((((((((("(" + "world_uid int NOT NULL,") + "permission_uid int NOT NULL,") + "player_uid int NOT NULL,") + "timeout bigint NOT NULL,") + "FOREIGN KEY(world_uid) REFERENCES World(uid),") + "FOREIGN KEY(permission_uid) REFERENCES Permission(uid),") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "PRIMARY KEY(world_uid, permission_uid, player_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Global_Permission" + (((((("(" + "permission_uid int NOT NULL,") + "player_uid int NOT NULL,") + "FOREIGN KEY(permission_uid) REFERENCES Permission(uid),") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "PRIMARY KEY(permission_uid, player_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Global_Permission_Timeout" + ((((((("(" + "permission_uid int NOT NULL,") + "player_uid int NOT NULL,") + "timeout bigint NOT NULL,") + "FOREIGN KEY(permission_uid) REFERENCES Permission(uid),") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "PRIMARY KEY(permission_uid, player_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Meta" + (((((((("(" + "world_uid int NOT NULL,") + "player_uid int NOT NULL,") + "meta_key varchar(50) NOT NULL,") + "meta_value varchar(50) NOT NULL,") + "FOREIGN KEY(world_uid) REFERENCES World(uid),") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "PRIMARY KEY(world_uid, player_uid, meta_key)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Global_Meta" + (((((("(" + "player_uid int NOT NULL,") + "meta_key varchar(50) NOT NULL,") + "meta_value varchar(50) NOT NULL,") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "PRIMARY KEY(player_uid, meta_key)") + ")"));
            // Groups and their data
            st.addBatch("CREATE TABLE IF NOT EXISTS Permission_Group" + ((((("(" + "uid int AUTO_INCREMENT PRIMARY KEY,") + "priority int NOT NULL,") + "world_uid int,") + "name varchar(50) NOT NULL") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Group_Parent" + (((((("(" + "group_uid int NOT NULL,") + "parent_uid int NOT NULL,") + "FOREIGN KEY(group_uid) REFERENCES Permission_Group(uid),") + "FOREIGN KEY(parent_uid) REFERENCES Permission_Group(uid),") + "PRIMARY KEY(group_uid, parent_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Group_Permission" + ((((("(" + "permission_uid int NOT NULL,") + "group_uid int NOT NULL,") + "FOREIGN KEY(group_uid) REFERENCES Permission_Group(uid),") + "PRIMARY KEY(permission_uid, group_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Group_Permission_Timeout" + ((((((("(" + "permission_uid int NOT NULL,") + "group_uid int NOT NULL,") + "timeout bigint NOT NULL,") + "FOREIGN KEY(permission_uid) REFERENCES Permission(uid),") + "FOREIGN KEY(group_uid) REFERENCES Permission_Group(uid),") + "PRIMARY KEY(permission_uid, group_uid)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Group_Meta" + (((((("(" + "group_uid int NOT NULL,") + "meta_key varchar(50) NOT NULL,") + "meta_value varchar(50) NOT NULL,") + "FOREIGN KEY(group_uid) REFERENCES Permission_Group(uid),") + "PRIMARY KEY(group_uid, meta_key)") + ")"));
            st.addBatch("CREATE TABLE IF NOT EXISTS Player_Group" + (((((("(" + "group_uid int NOT NULL,") + "player_uid int NOT NULL,") + "FOREIGN KEY(player_uid) REFERENCES Player(uid),") + "FOREIGN KEY(group_uid) REFERENCES Permission_Group(uid),") + "PRIMARY KEY(group_uid, player_uid)") + ")"));
            st.executeBatch();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(st);
        }
    }

    @java.lang.Override
    public java.util.ArrayList<java.lang.String> getPlayerPermissions(int playerId, int worldId) {
        java.util.ArrayList<java.lang.String> ret = new java.util.ArrayList<java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT permission_uid FROM Player_Permission WHERE player_uid=? AND world_uid=?");
            pst.setInt(1, playerId);
            pst.setInt(2, worldId);
            rs = pst.executeQuery();
            while (rs.next()) {
                java.lang.String perm = getPermissionValue(rs.getInt("permission_uid"));
                if (perm.length() != 0) {
                    ret.add(perm);
                }
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.ArrayList<java.lang.String> getGlobalPlayerPermissions(int playerId) {
        java.util.ArrayList<java.lang.String> ret = new java.util.ArrayList<java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT permission_uid FROM Player_Global_Permission WHERE player_uid=?");
            pst.setInt(1, playerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                java.lang.String perm = getPermissionValue(rs.getInt("permission_uid"));
                if (perm.length() != 0) {
                    ret.add(perm);
                }
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.List<com.overmc.overpermissions.Group> getPlayerEffectiveGroups(int playerId, int worldId) {
        java.util.List<com.overmc.overpermissions.Group> effectiveGroups = new java.util.ArrayList<com.overmc.overpermissions.Group>();
        org.bukkit.World world = getWorld(worldId);
        for (java.lang.Integer i : getPlayerGroups(playerId)) {
            com.overmc.overpermissions.Group parent = plugin.getGroupManager().getGroup(i);
            if (parent == null) {
                plugin.getLogger().warning((("Invalid group found while checking player id (" + playerId) + ")'s effective groups. Group id: ") + i);
                continue;
            }
            for (com.overmc.overpermissions.Group group : parent.getAllParents()) {
                if (((group.getWorld() == null) || group.getWorld().equals(world)) && (!effectiveGroups.contains(group))) {
                    effectiveGroups.add(group);
                }
            }
        }
        java.util.Collections.sort(effectiveGroups);
        return effectiveGroups;
    }

    @java.lang.Override
    public boolean checkPlayerPermission(int playerId, int worldId, java.lang.String permission) {
        if (permission.startsWith("+") || permission.startsWith("-")) {
            permission = permission.substring(1);
        }
        boolean value = false;
        boolean negSet = false;
        boolean posSet = false;
        for (com.overmc.overpermissions.Group g : getPlayerEffectiveGroups(playerId, worldId)) {
            if ((!posSet) && g.hasNode("+" + permission)) {
                posSet = true;
                break;
            }
            if ((!negSet) && g.hasNode("-" + permission)) {
                negSet = true;
            }
            if (g.hasPermission(permission)) {
                value = g.getPermission(permission);
            }
        }
        java.util.ArrayList<java.lang.String> worldPermissions = getGlobalPlayerPermissions(playerId);
        if (worldId >= 0) {
            worldPermissions.addAll(getPlayerPermissions(playerId, worldId));
        }
        if (!posSet) {
            if (checkPlayerPermissionExists(playerId, worldId, "+" + permission)) {
                posSet = true;
            } else if ((!negSet) && checkPlayerPermissionExists(playerId, worldId, "-" + permission)) {
                negSet = true;
            } else if (checkPlayerPermissionExists(playerId, worldId, "-" + permission)) {
                value = true;
            }
        }
        if (checkPlayerPermissionExists(playerId, worldId, permission)) {
            if ((checkPlayerPermissionExists(playerId, worldId, "-" + permission) && (!checkPlayerPermissionExists(playerId, worldId, "+" + permission))) && (!posSet)) {
                value = false;
            } else {
                value = true;
            }
        }
        if (negSet) {
            value = false;
        }
        if (posSet) {
            value = true;
        }
        return value;
    }

    @java.lang.Override
    public java.util.List<java.lang.String> getPlayerNodeInfo(int playerId, int worldId, java.lang.String node) {
        java.util.ArrayList<java.lang.String> ret = new java.util.ArrayList<java.lang.String>();
        if (playerId < 0) {
            return ret;
        }
        boolean negSet = false;
        boolean posSet = false;
        java.util.ArrayList<java.lang.String> tempArray = new java.util.ArrayList<java.lang.String>();
        for (com.overmc.overpermissions.Group group : getPlayerEffectiveGroups(playerId, worldId)) {
            for (java.lang.String nodei : group.getNodes()) {
                java.lang.String basenode = (nodei.startsWith("-") || nodei.startsWith("+")) ? nodei.substring(1) : nodei;
                if (basenode.equalsIgnoreCase(node)) {
                    if (nodei.startsWith("+") && (!posSet)) {
                        tempArray.add(org.bukkit.ChatColor.GREEN + nodei);
                        posSet = true;
                    } else if ((nodei.startsWith("-") && (!negSet)) && (!posSet)) {
                        tempArray.add(org.bukkit.ChatColor.RED + nodei);
                        negSet = true;
                    } else if ((!negSet) && (!posSet)) {
                        tempArray.add(org.bukkit.ChatColor.GREEN + nodei);
                    }
                }
            }
        }
        if (tempArray.size() != 0) {
            ret.add(com.overmc.overpermissions.Messages.format(Messages.GROUPS_WITH_NODE));
            for (java.lang.String s : tempArray) {
                ret.add(com.overmc.overpermissions.Messages.format(Messages.GROUP_NODE_VALUE, s));
            }
        }
        tempArray.clear();
        java.util.ArrayList<java.lang.String> effectiveNodes = plugin.getSQLManager().getGlobalPlayerPermissions(playerId);
        effectiveNodes.addAll(plugin.getSQLManager().getPlayerPermissions(playerId, worldId));
        for (java.lang.String nodei : effectiveNodes) {
            java.lang.String nodeival = (nodei.startsWith("-") || nodei.startsWith("+")) ? nodei.substring(1) : nodei;
            if (nodeival.equalsIgnoreCase(node)) {
                if (nodei.startsWith("+") && (!posSet)) {
                    tempArray.add(org.bukkit.ChatColor.GREEN + nodei);
                    posSet = true;
                } else if ((nodei.startsWith("-") && (!negSet)) && (!posSet)) {
                    tempArray.add(org.bukkit.ChatColor.RED + nodei);
                    negSet = true;
                } else if ((!negSet) && (!posSet)) {
                    tempArray.add(org.bukkit.ChatColor.GREEN + nodei);
                }
            }
        }
        if (tempArray.size() != 0) {
            ret.add(com.overmc.overpermissions.Messages.format(Messages.PLAYER_NODES));
            for (java.lang.String s : tempArray) {
                ret.add(com.overmc.overpermissions.Messages.format(Messages.PLAYER_NODE_VALUE, s));
            }
        }
        tempArray.clear();
        for (com.overmc.overpermissions.TimedPlayerPermission t : getPlayerPermissionTimeouts(playerId)) {
            if (((t.worldId == (-1)) || (t.worldId == worldId)) && (t.executeTime < (java.lang.System.currentTimeMillis() / 1000))) {
                java.lang.String nodeval = (t.node.startsWith("-") || t.node.startsWith("+")) ? t.node.substring(1) : t.node;
                if (nodeval.equalsIgnoreCase(node)) {
                    if (t.node.startsWith("+") && (!posSet)) {
                        tempArray.add(org.bukkit.ChatColor.GREEN + t.node);
                        posSet = true;
                    } else if ((t.node.startsWith("-") && (!negSet)) && (!posSet)) {
                        tempArray.add(org.bukkit.ChatColor.RED + t.node);
                        negSet = true;
                    } else if ((!negSet) && (!posSet)) {
                        tempArray.add(org.bukkit.ChatColor.GREEN + t.node);
                    }
                }
            }
        }
        if (tempArray.size() != 0) {
            ret.add(com.overmc.overpermissions.Messages.format(Messages.PLAYER_TEMP_NODES));
            for (java.lang.String s : tempArray) {
                ret.add(com.overmc.overpermissions.Messages.format(Messages.PLAYER_TEMP_NODE_VALUE, s));
            }
        }
        return ret;
    }

    @java.lang.Override
    public java.util.Collection<java.lang.String> getTotalPlayerNodes(int playerId, int worldId) {
        java.util.HashSet<java.lang.String> ret = new java.util.HashSet<java.lang.String>();
        if (playerId < 0) {
            return ret;
        }
        for (com.overmc.overpermissions.Group g : getPlayerEffectiveGroups(playerId, worldId)) {
            ret.addAll(g.getNodes());
        }
        ret.addAll(getGlobalPlayerPermissions(playerId));
        if (worldId >= 0) {
            ret.addAll(getPlayerPermissions(playerId, worldId));
        }
        return ret;
    }

    @java.lang.Override
    public boolean checkPlayerPermissionExists(int playerId, int worldId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            pst = getConnection().prepareStatement("SELECT EXISTS(SELECT 1 FROM Player_Permission WHERE player_uid=? AND world_uid=? AND permission_uid=? LIMIT 1)");
            pst.setInt(1, playerId);
            pst.setInt(2, worldId);
            pst.setInt(3, permissionId);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public java.util.HashMap<java.lang.String, java.lang.String> getPlayerMeta(int playerId, int worldId) {
        java.util.HashMap<java.lang.String, java.lang.String> ret = new java.util.HashMap<java.lang.String, java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT meta_key, meta_value FROM Player_Meta WHERE player_uid=? AND world_uid=?");
            pst.setInt(1, playerId);
            pst.setInt(2, worldId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString("meta_key"), rs.getString("meta_value"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.HashMap<java.lang.String, java.lang.String> getGlobalPlayerMeta(int playerId) {
        java.util.HashMap<java.lang.String, java.lang.String> ret = new java.util.HashMap<java.lang.String, java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT meta_key, meta_value FROM Player_Global_Meta WHERE player_uid=?");
            pst.setInt(1, playerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString("meta_key"), rs.getString("meta_value"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.lang.String getPlayerMetaValue(int playerId, int worldId, java.lang.String key) {
        if (playerId < 0) {
            return null;
        }
        if (worldId < 0) {
            return null;
        }
        java.lang.String value = null;
        java.util.List<com.overmc.overpermissions.Group> effectiveGroups = new java.util.ArrayList<com.overmc.overpermissions.Group>();
        for (java.lang.Integer i : getPlayerGroups(playerId)) {
            for (com.overmc.overpermissions.Group group : plugin.getGroupManager().getGroup(i).getAllParents()) {
                if ((group.getWorld() == null) || ((group.getWorldId() == worldId) && (!effectiveGroups.contains(group)))) {
                    effectiveGroups.add(group);
                }
            }
        }
        java.util.Collections.sort(effectiveGroups);
        for (com.overmc.overpermissions.Group g : effectiveGroups) {
            if (g.hasMeta(key)) {
                value = g.getMeta(key);
            }
        }
        java.lang.String playerValue = getGlobalPlayerMeta(playerId).get(key);
        if (playerValue != null) {
            value = playerValue;
        } else {
            playerValue = getPlayerMeta(playerId, worldId).get(key);
        }
        if (playerValue != null) {
            value = playerValue;
        }
        return value;
    }

    @java.lang.Override
    public boolean addPlayerPermission(int playerId, int worldId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            int permId = getPermissionValue(permission, true);
            pst = getConnection().prepareStatement("INSERT IGNORE INTO Player_Permission(permission_uid, player_uid, world_uid) VALUES (?, ?, ?)");
            pst.setInt(1, permId);
            pst.setInt(2, playerId);
            pst.setInt(3, worldId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean addGlobalPlayerPermission(int playerId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            int permId = getPermissionValue(permission, true);
            pst = getConnection().prepareStatement("INSERT IGNORE INTO Player_Global_Permission(permission_uid, player_uid) VALUES (?, ?)");
            pst.setInt(1, permId);
            pst.setInt(2, playerId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean addPlayerPermissionTimeout(int playerId, int worldId, java.lang.String permission, long timeInSeconds) {
        java.sql.PreparedStatement pst = null;
        try {
            int permId = getPermissionValue(permission, false);
            if (permId < 0) {
                return false;
            }
            long time = (java.lang.System.currentTimeMillis() / 1000L) + timeInSeconds;
            pst = getConnection().prepareStatement("INSERT INTO Player_Permission_Timeout(timeout, permission_uid, player_uid, world_uid) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE timeout=?");
            pst.setLong(1, time);
            pst.setInt(2, permId);
            pst.setInt(3, playerId);
            pst.setInt(4, worldId);
            pst.setLong(5, time);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean addGlobalPlayerPermissionTimeout(int playerId, java.lang.String permission, long timeInSeconds) {
        java.sql.PreparedStatement pst = null;
        try {
            int permId = getPermissionValue(permission, false);
            if (permId < 0) {
                return false;
            }
            long time = (java.lang.System.currentTimeMillis() / 1000L) + timeInSeconds;
            pst = getConnection().prepareStatement("INSERT INTO Player_Global_Permission_Timeout(timeout, permission_uid, player_uid) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE timeout=?");
            pst.setLong(1, time);
            pst.setInt(2, permId);
            pst.setInt(3, playerId);
            pst.setLong(4, time);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removePlayerPermissionTimeout(int playerId, int worldId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            if (permissionId <= 0) {
                return false;
            }
            pst = getConnection().prepareStatement("DELETE FROM Player_Permission_Timeout WHERE permission_uid=? AND player_uid=? AND world_uid=?");
            pst.setInt(1, permissionId);
            pst.setInt(2, playerId);
            if (worldId < 0) {
                pst.setNull(3, java.sql.Types.INTEGER);
            } else {
                pst.setInt(3, worldId);
            }
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removeGlobalPlayerPermissionTimeout(int playerId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            if (permissionId <= 0) {
                return false;
            }
            pst = getConnection().prepareStatement("DELETE FROM Player_Global_Permission_Timeout WHERE permission_uid=? AND player_uid=?");
            pst.setInt(1, permissionId);
            pst.setInt(2, playerId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public java.util.ArrayList<com.overmc.overpermissions.TimedPlayerPermission> getPlayerPermissionTimeouts(int playerId) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        java.util.ArrayList<com.overmc.overpermissions.TimedPlayerPermission> ret = new java.util.ArrayList<com.overmc.overpermissions.TimedPlayerPermission>();
        try {
            pst = getConnection().prepareStatement("SELECT world_uid, permission_uid, timeout FROM Player_Permission_Timeout WHERE player_uid=?");
            pst.setInt(1, playerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(new com.overmc.overpermissions.TimedPlayerPermission(rs.getInt("world_uid"), playerId, getPermissionValue(rs.getInt("permission_uid")), rs.getLong("timeout")));
            } 
            rs.close();
            pst.close();
            pst = getConnection().prepareStatement("SELECT permission_uid,timeout FROM Player_Global_Permission_Timeout WHERE player_uid=?");
            pst.setInt(1, playerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(new com.overmc.overpermissions.TimedPlayerPermission(-1, playerId, getPermissionValue(rs.getInt("permission_uid")), rs.getLong("timeout")));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public boolean removePlayerPermission(int playerId, int worldId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            if (permissionId <= 0) {
                return false;
            }
            pst = getConnection().prepareStatement("DELETE FROM Player_Permission WHERE permission_uid=? AND player_uid=? AND world_uid=?");
            pst.setInt(1, permissionId);
            pst.setInt(2, playerId);
            pst.setInt(3, worldId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removeGlobalPlayerPermission(int playerId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            if (permissionId <= 0) {
                return false;
            }
            pst = getConnection().prepareStatement("DELETE FROM Player_Global_Permission WHERE permission_uid=? AND player_uid=?");
            pst.setInt(1, permissionId);
            pst.setInt(2, playerId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public void setPlayerMeta(int playerId, int world, java.lang.String key, java.lang.String value) {
        key = key.toLowerCase();
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("INSERT INTO Player_Meta(world_uid, player_uid, meta_key, meta_value) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE meta_value=?");
            pst.setInt(1, world);
            pst.setInt(2, playerId);
            pst.setString(3, key);
            pst.setString(4, value);
            pst.setString(5, value);
            pst.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
    }

    @java.lang.Override
    public void setGlobalPlayerMeta(int playerId, java.lang.String key, java.lang.String value) {
        key = key.toLowerCase();
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("INSERT INTO Player_Global_Meta(player_uid, meta_key, meta_value) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE meta_value=?");
            pst.setInt(1, playerId);
            pst.setString(2, key);
            pst.setString(3, value);
            pst.setString(4, value);
            pst.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
    }

    @java.lang.Override
    public boolean delPlayerMeta(int playerId, int world, java.lang.String key) {
        key = key.toLowerCase();
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("DELETE FROM Player_Meta WHERE player_uid=? AND meta_key=? AND world_uid = ?");
            pst.setInt(1, playerId);
            pst.setString(2, key);
            pst.setInt(3, world);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean delGlobalPlayerMeta(int playerId, java.lang.String key) {
        key = key.toLowerCase();
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("DELETE FROM Player_Global_Meta WHERE player_uid=? AND meta_key=?");
            pst.setInt(1, playerId);
            pst.setString(2, key);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public java.util.ArrayList<java.lang.Integer> getPlayerGroups(int playerId) {
        java.util.ArrayList<java.lang.Integer> ret = new java.util.ArrayList<java.lang.Integer>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            if (playerId < 0) {
                return ret;
            }
            pst = getConnection().prepareStatement("SELECT group_uid FROM Player_Group WHERE player_uid=?");
            pst.setInt(1, playerId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("group_uid"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        if (ret.size() == 0) {
            ret.add(plugin.getDefaultGroupId());
        }
        return ret;
    }

    @java.lang.Override
    public void setPlayerGroup(int playerId, int group) {
        java.sql.PreparedStatement pst = null;
        try {
            if (playerId < 0) {
                return;
            }
            pst = getConnection().prepareStatement("DELETE FROM Player_Group WHERE player_uid=?");
            pst.setInt(1, playerId);
            pst.executeUpdate();
            pst.close();
            pst = getConnection().prepareStatement("INSERT INTO Player_Group(player_uid, group_uid) VALUES (?, ?)");
            pst.setInt(1, playerId);
            pst.setInt(2, group);
            pst.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
    }

    @java.lang.Override
    public boolean addPlayerGroup(int playerId, int groupId) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            if (playerId < 0) {
                return false;
            }
            pst = getConnection().prepareStatement("INSERT IGNORE INTO Player_Group(player_uid, group_uid) VALUES (?, ?)");
            pst.setInt(1, playerId);
            pst.setInt(2, groupId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean isPlayerInGroup(int playerId, int groupId) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            if (playerId < 0) {
                return false;
            }
            pst = getConnection().prepareStatement("SELECT EXISTS(SELECT 1 FROM Player_Group WHERE player_uid=? AND group_uid=? LIMIT 1)");
            pst.setInt(1, playerId);
            pst.setInt(2, groupId);
            rs = pst.executeQuery();
            return rs.next() && rs.getBoolean(1);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removePlayerGroup(int playerId, int groupId) {
        java.sql.PreparedStatement pst = null;
        try {
            if (playerId < 0) {
                return false;
            }
            pst = getConnection().prepareStatement("DELETE FROM Player_Group WHERE player_uid=? AND group_uid=?");
            pst.setInt(1, playerId);
            pst.setInt(2, groupId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean setGroup(java.lang.String groupName, int priority, int world) {
        if (world < 0) {
            world = -1;
        }
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("UPDATE Permission_Group SET priority=?, world_uid=? WHERE name=?");
            pst.setInt(1, priority);
            pst.setInt(2, world);
            pst.setString(3, groupName);
            if (pst.executeUpdate() == 0) {
                pst.close();
                pst = getConnection().prepareStatement("INSERT INTO Permission_Group(priority, world_uid, name) VALUES (?, ?, ?)");
                pst.setInt(1, priority);
                if (world < 0) {
                    world = -1;
                }
                pst.setInt(2, world);
                pst.setString(3, groupName);
                pst.executeUpdate();
                return true;
            }
            return true;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean deleteGroup(int groupId) {
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("DELETE FROM Group_Permission_Timeout WHERE group_uid=?");
            pst.setInt(1, groupId);
            pst.executeUpdate();
            pst.close();
            pst = getConnection().prepareStatement("DELETE FROM Group_Permission WHERE group_uid=?");
            pst.setInt(1, groupId);
            pst.executeUpdate();
            pst.close();
            pst = getConnection().prepareStatement("DELETE FROM Group_Meta WHERE group_uid=?");
            pst.setInt(1, groupId);
            pst.executeUpdate();
            pst.close();
            pst = getConnection().prepareStatement("DELETE FROM Player_Group WHERE group_uid=?");
            pst.setInt(1, groupId);
            pst.executeUpdate();
            pst.close();
            pst = getConnection().prepareStatement("DELETE FROM Group_Parent WHERE group_uid=? OR parent_uid=?");
            pst.setInt(1, groupId);
            pst.setInt(2, groupId);
            pst.executeUpdate();
            pst.close();
            pst = getConnection().prepareStatement("DELETE FROM Permission_Group WHERE uid=?");
            pst.setInt(1, groupId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public java.util.ArrayList<com.overmc.overpermissions.Group> getGroups() {
        java.util.ArrayList<com.overmc.overpermissions.Group> ret = new java.util.ArrayList<com.overmc.overpermissions.Group>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT world_uid, uid, priority, name FROM Permission_Group");
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(new com.overmc.overpermissions.Group(plugin, getWorld(rs.getInt("world_uid")), rs.getString("name"), rs.getInt("priority"), rs.getInt("uid")));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.ArrayList<java.lang.Integer> getGroupParents(int group) {
        java.util.ArrayList<java.lang.Integer> ret = new java.util.ArrayList<java.lang.Integer>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT parent_uid FROM Group_Parent WHERE group_uid=?");
            pst.setInt(1, group);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("parent_uid"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.ArrayList<java.lang.Integer> getGroupChildren(int group) {
        java.util.ArrayList<java.lang.Integer> ret = new java.util.ArrayList<java.lang.Integer>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT group_uid FROM Group_Parent WHERE parent_uid=?");
            pst.setInt(1, group);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("group_uid"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public boolean addGroupParent(int group, int parent) {
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("INSERT IGNORE INTO Group_Parent(group_uid, parent_uid) VALUES (?, ?)");
            pst.setInt(1, group);
            pst.setInt(2, parent);
            if (pst.executeUpdate() > 0) {
                return true;
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removeGroupParent(int group, int parent) {
        java.sql.PreparedStatement pst = null;
        try {
            pst = getConnection().prepareStatement("DELETE FROM Group_Parent WHERE group_uid=? AND parent_uid=?");
            pst.setInt(1, group);
            pst.setInt(2, parent);
            if (pst.executeUpdate() > 0) {
                return true;
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removeGroupPermission(int groupId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            pst = getConnection().prepareStatement("DELETE FROM Group_Permission WHERE group_uid=? AND permission_uid=?");
            pst.setInt(1, groupId);
            pst.setInt(2, permissionId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean removeGroupPermissionTimeout(int groupId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        try {
            int permissionId = getPermissionValue(permission, false);
            pst = getConnection().prepareStatement("DELETE FROM Group_Permission_Timeout WHERE group_uid=? AND permission_uid=?");
            pst.setInt(1, groupId);
            pst.setInt(2, permissionId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean addGroupPermission(int groupId, java.lang.String permission) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            int permuid = getPermissionValue(permission, true);
            pst = getConnection().prepareStatement("INSERT IGNORE INTO Group_Permission(permission_uid, group_uid) VALUES (?, ?)");
            pst.setInt(1, permuid);
            pst.setInt(2, groupId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public boolean addGroupPermissionTimeout(int groupId, java.lang.String permission, long timeout) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            int permuid = getPermissionValue(permission, true);
            pst = getConnection().prepareStatement("INSERT IGNORE INTO Group_Permission_Timeout(permission_uid, group_uid, timeout) VALUES (?, ?, ?)");
            pst.setInt(1, permuid);
            pst.setInt(2, groupId);
            pst.setLong(3, timeout);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public void setGroupMeta(int groupId, java.lang.String key, java.lang.String value) {
        java.sql.PreparedStatement pst = null;
        key = key.toLowerCase();
        try {
            pst = getConnection().prepareStatement("INSERT INTO Group_Meta(group_uid, meta_key, meta_value) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE meta_value=?");
            pst.setInt(1, groupId);
            pst.setString(2, key);
            pst.setString(3, value);
            pst.setString(4, value);
            pst.executeUpdate();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
    }

    @java.lang.Override
    public boolean removeGroupMeta(int groupId, java.lang.String key) {
        java.sql.PreparedStatement pst = null;
        key = key.toLowerCase();
        try {
            pst = getConnection().prepareStatement("DELETE FROM Group_Meta WHERE meta_key=? AND group_uid=?");
            pst.setString(1, key);
            pst.setInt(2, groupId);
            return pst.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return false;
    }

    @java.lang.Override
    public int getGroupId(java.lang.String group) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT uid FROM Permission_Group WHERE name=?");
            pst.setString(1, group.toLowerCase());
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("uid");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return -1;
    }

    @java.lang.Override
    public int getGroupPriority(int groupId) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT priority FROM Permission_Group WHERE uid=?");
            pst.setInt(1, groupId);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("priority");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return 0;
    }

    @java.lang.Override
    public java.util.ArrayList<java.lang.String> getGroupPermissions(int groupId) {
        java.util.ArrayList<java.lang.String> ret = new java.util.ArrayList<java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT permission_uid FROM Group_Permission WHERE group_uid=?");
            pst.setInt(1, groupId);
            rs = pst.executeQuery();
            while (rs.next()) {
                java.lang.String perm = getPermissionValue(rs.getInt("permission_uid"));
                if (perm.length() != 0) {
                    ret.add(perm);
                }
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.ArrayList<com.overmc.overpermissions.TimedGroupPermission> getGroupPermissionTimeouts(int groupId) {
        java.util.ArrayList<com.overmc.overpermissions.TimedGroupPermission> ret = new java.util.ArrayList<com.overmc.overpermissions.TimedGroupPermission>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT permission_uid, timeout FROM Group_Permission_Timeout WHERE group_uid=?");
            pst.setInt(1, groupId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(new com.overmc.overpermissions.TimedGroupPermission(groupId, getPermissionValue(rs.getInt("permission_uid")), rs.getLong("timeout")));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.util.HashMap<java.lang.String, java.lang.String> getGroupMeta(int groupId) {
        java.util.HashMap<java.lang.String, java.lang.String> ret = new java.util.HashMap<java.lang.String, java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT meta_key, meta_value FROM Group_Meta WHERE group_uid=?");
            pst.setInt(1, groupId);
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString("meta_key"), rs.getString("meta_value"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret;
    }

    @java.lang.Override
    public java.lang.String getGroup(int groupId) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT name FROM Permission_Group WHERE uid=?");
            pst.setInt(1, groupId);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return "";
    }

    @java.lang.Override
    public int getPermissionValue(java.lang.String permission, boolean insertIfNotExists) {
        permission = permission.toLowerCase();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT uid FROM Permission WHERE permission = ?");
            pst.setString(1, permission);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("uid");
            }
            if (insertIfNotExists) {
                rs.close();
                pst.close();
                pst = getConnection().prepareStatement("INSERT INTO Permission(permission) VALUES (?)", java.sql.PreparedStatement.RETURN_GENERATED_KEYS);
                pst.setString(1, permission);
                pst.executeUpdate();
                rs = pst.getGeneratedKeys();
                if ((rs != null) && rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return -1;
    }

    @java.lang.Override
    public int getPermissionValue(java.lang.String permission) {
        return getPermissionValue(permission, false);
    }

    @java.lang.Override
    public java.lang.String getPermissionValue(int permissionId) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT permission FROM Permission WHERE uid=?");
            pst.setInt(1, permissionId);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("permission");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return "";
    }

    @java.lang.Override
    public java.lang.String[] getWorlds() {
        java.util.ArrayList<java.lang.String> ret = new java.util.ArrayList<java.lang.String>();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT name FROM World");
            rs = pst.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("name"));
            } 
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return ret.toArray(new java.lang.String[ret.size()]);
    }

    @java.lang.Override
    public org.bukkit.World getWorld(int id) {
        if (id <= 0) {
            return null;
        }
        return org.bukkit.Bukkit.getWorld(getWorldName(id));
    }

    @java.lang.Override
    public java.lang.String getWorldName(int id) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT name FROM World WHERE uid=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return null;
    }

    @java.lang.Override
    public int getWorldId(java.lang.String worldname, boolean makeNew) {
        if (worldname == null) {
            return -1;
        }
        worldname = worldname.toLowerCase();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        int uid = -1;
        try {
            pst = getConnection().prepareStatement("SELECT uid FROM World WHERE name=?");
            pst.setString(1, worldname);
            rs = pst.executeQuery();
            if (rs.next()) {
                uid = rs.getInt("uid");
            } else if (makeNew) {
                rs.close();
                pst.close();
                pst = getConnection().prepareStatement("INSERT INTO World(name) VALUES (?)", java.sql.Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, worldname);
                pst.executeUpdate();
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    uid = rs.getInt(1);
                } else {
                    throw new java.lang.RuntimeException("GENERATED_KEY was empty! This should never happen!");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return uid;
    }

    @java.lang.Override
    public int getWorldId(java.lang.String worldname) {
        return getWorldId(worldname, false);
    }

    @java.lang.Override
    public int getWorldId(org.bukkit.World world) {
        if (world == null) {
            return -1;
        }
        return getWorldId(world.getName(), true);
    }

    @java.lang.Override
    public int getPlayerId(java.util.UUID playerId) {
        return getPlayerId(playerId, false);
    }

    @java.lang.Override
    public int getPlayerId(java.util.UUID playerId, boolean makeNew) {
        if (playerId == null) {
            if (makeNew) {
                throw new java.lang.IllegalArgumentException("playerId: " + playerId);
            } else {
                return -1;
            }
        }
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        int uid = -1;
        try {
            pst = getConnection().prepareStatement("SELECT uid FROM Player WHERE lower_uid=? AND upper_uid=?");
            pst.setLong(1, playerId.getLeastSignificantBits());
            pst.setLong(2, playerId.getMostSignificantBits());
            rs = pst.executeQuery();
            if (rs.next()) {
                uid = rs.getInt("uid");
            } else if (makeNew) {
                rs.close();
                pst.close();
                pst = getConnection().prepareStatement("INSERT INTO Player(lower_uid, upper_uid) VALUES (?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
                pst.setLong(1, playerId.getLeastSignificantBits());
                pst.setLong(2, playerId.getMostSignificantBits());
                pst.executeUpdate();
                rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    uid = rs.getInt(1);
                } else {
                    throw new java.lang.AssertionError("GENERATED_KEY was empty! This should never happen!");
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return uid;
    }

    /**
     * Gets a UID from a given username. Deprecated because the username may change between logins, use {@link #getPlayerId(UUID)}
     */
    @java.lang.Override
    @java.lang.Deprecated
    public int getPlayerId(java.lang.String username) {
        if (((username == null) || (username.length() == 0)) || (username.length() > 16)) {
            throw new java.lang.IllegalArgumentException("username");
        }
        username = username.toLowerCase();
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        int uid = -1;
        try {
            pst = getConnection().prepareStatement("SELECT uid FROM Player WHERE last_seen_username=?");
            pst.setString(1, username);
            rs = pst.executeQuery();
            if (rs.next()) {
                uid = rs.getInt("uid");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return uid;
    }

    @java.lang.Override
    public org.bukkit.entity.Player getPlayer(int id) {
        return ServerUtil.findPlayerExact(getLastSeenPlayerName(id));
    }

    @java.lang.Override
    public java.lang.String getLastSeenPlayerName(int id) {
        java.sql.PreparedStatement pst = null;
        java.sql.ResultSet rs = null;
        try {
            pst = getConnection().prepareStatement("SELECT last_seen_username FROM Player WHERE uid=?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(rs);
            com.overmc.overpermissions.MySQLManager.attemptClose(pst);
        }
        return null;
    }

    @java.lang.Override
    public void deleteDatabase() {
        java.sql.Statement st = null;
        try {
            boolean autoCommit = getConnection().getAutoCommit();
            if (autoCommit) {
                getConnection().setAutoCommit(false);
            }
            st = getConnection().createStatement();
            st.executeUpdate("DELETE FROM Player_Global_Permission");
            st.executeUpdate("DELETE FROM Player_Permission");
            st.executeUpdate("DELETE FROM Player_Permission_Timeout");
            st.executeUpdate("DELETE FROM Player_Global_Permission_Timeout)");
            st.executeUpdate("DELETE FROM Player_Meta");
            st.executeUpdate("DELETE FROM Player_Global_Meta");
            st.executeUpdate("DELETE FROM Player_Group");
            st.executeUpdate("DELETE FROM Group_Meta");
            st.executeUpdate("DELETE FROM Group_Permission_Timeout");
            st.executeUpdate("DELETE FROM Group_Parent");
            st.executeUpdate("DELETE FROM Group_Permission");
            st.executeUpdate("DELETE FROM Permission_Group");
            st.executeUpdate("DELETE FROM Permission");
            getConnection().commit();
            getConnection().setAutoCommit(autoCommit);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        } finally {
            com.overmc.overpermissions.MySQLManager.attemptClose(st);
        }
    }

    public static void attemptClose(java.sql.PreparedStatement pst) {
        try {
            if (pst != null) {
                pst.close();
            }
        } catch (java.lang.Throwable t) {
        }
    }

    public static void attemptClose(java.sql.Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (java.lang.Throwable t) {
        }
    }

    public static void attemptClose(java.sql.ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (java.lang.Throwable t) {
        }
    }
}