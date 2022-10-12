public java.lang.Boolean run(org.bukkit.command.CommandSender sender, java.lang.String[] args) {
    java.lang.Integer level = 0;
    try {
         _CVAR0 = us.corenetwork.challenges.IO.getConnection();
        java.lang.String _CVAR1 = "SELECT Level FROM weekly_levels WHERE Level > (SELECT IFNULL(MAX(Level), 0) FROM weekly_completed WHERE Player = ? AND WeekID = ? AND State < 2) AND WeekID = ? ORDER BY Level ASC LIMIT 1";
        java.sql.PreparedStatement statement = _CVAR0.prepareStatement(_CVAR1);
        org.bukkit.entity.Player _CVAR4 = player;
        java.util.UUID _CVAR5 = _CVAR4.getUniqueId();
        java.sql.PreparedStatement _CVAR2 = statement;
        int _CVAR3 = 1;
        java.lang.String _CVAR6 = _CVAR5.toString();
        _CVAR2.setString(_CVAR3, _CVAR6);
        statement.setInt(2, us.corenetwork.challenges.WeekUtil.getCurrentWeek());
        statement.setInt(3, us.corenetwork.challenges.WeekUtil.getCurrentWeek());
        java.sql.ResultSet set = statement.executeQuery();
        if (set.next()) {
            level = set.getInt("Level");
        } else {
            us.corenetwork.challenges.Util.Message(us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_CH_ALL_COMPELETED), sender);
            statement.close();
            return true;
        }
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
    java.sql.ResultSet set = null;
    try {
        statement = us.corenetwork.challenges.IO.getConnection().prepareStatement("SELECT weekly_levels.Level, weekly_completed.State FROM weekly_levels LEFT JOIN weekly_completed ON weekly_levels.WeekID = weekly_completed.WeekID AND weekly_levels.Level  <= weekly_completed.level AND weekly_completed.player = ? AND weekly_completed.state < 2 WHERE weekly_levels.level = ? AND weekly_levels.weekID = ? LIMIT 1");
        org.bukkit.entity.Player _CVAR9 = player;
        java.util.UUID _CVAR10 = _CVAR9.getUniqueId();
        java.sql.PreparedStatement _CVAR7 = statement;
        int _CVAR8 = 1;
        java.lang.String _CVAR11 = _CVAR10.toString();
        _CVAR7.setString(_CVAR8, _CVAR11);
        statement.setInt(2, level);
        statement.setInt(3, us.corenetwork.challenges.WeekUtil.getCurrentWeek());
        set = statement.executeQuery();
        if (!set.next()) {
            us.corenetwork.challenges.Util.Message(us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_INVALID_LEVEL).replace("<Level>", java.lang.Integer.toString(level)), sender);
            return true;
        }
        set.getInt("State");
        if (!set.wasNull()) {
            us.corenetwork.challenges.Util.Message(us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_ALREADY_COMPLETED), sender);
            return true;
        }
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            statement.close();
            set.close();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
    int maxlevel = 0;
    try {
        statement = us.corenetwork.challenges.IO.getConnection().prepareStatement("SELECT MAX(Level) AS MaxLevel FROM weekly_levels WHERE WeekID = ? LIMIT 1");
        statement.setInt(1, us.corenetwork.challenges.WeekUtil.getCurrentWeek());
        set = statement.executeQuery();
        set.next();
        maxlevel = set.getInt(1);
        statement.close();
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
    if (maxlevel == level) {
        us.corenetwork.challenges.Util.Message(us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_CHALLENGE_SENT_MAX), sender);
    } else {
        us.corenetwork.challenges.Util.Message(us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_CHALLENGE_SENT), sender);
    }
    int id = 0;
    java.sql.PreparedStatement statement = null;
    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
    try {
        delStatement.setInt(1, us.corenetwork.challenges.WeekUtil.getCurrentWeek());
         _CVAR12 = us.corenetwork.challenges.IO.getConnection();
        java.lang.String _CVAR13 = "DELETE FROM weekly_completed WHERE WeekID = ? AND Player = ? AND Level <= ? AND State >= 2";
        // Delete old submission first
        java.sql.PreparedStatement delStatement = _CVAR12.prepareStatement(_CVAR13);
        org.bukkit.entity.Player _CVAR16 = player;
        java.util.UUID _CVAR17 = _CVAR16.getUniqueId();
        java.sql.PreparedStatement _CVAR14 = delStatement;
        int _CVAR15 = 2;
        java.lang.String _CVAR18 = _CVAR17.toString();
        _CVAR14.setString(_CVAR15, _CVAR18);
        delStatement.setInt(3, level);
        delStatement.executeUpdate();
        delStatement.close();
        statement = us.corenetwork.challenges.IO.getConnection().prepareStatement("INSERT INTO weekly_completed (WeekID, Level, Player, State, X, Y, Z, World, lastUpdate) VALUES (?,?,?,0,?,?,?,?,?)");
        statement.setInt(1, us.corenetwork.challenges.WeekUtil.getCurrentWeek());
        statement.setInt(2, level);
        org.bukkit.entity.Player _CVAR21 = player;
        java.util.UUID _CVAR22 = _CVAR21.getUniqueId();
        java.sql.PreparedStatement _CVAR19 = statement;
        int _CVAR20 = 3;
        java.lang.String _CVAR23 = _CVAR22.toString();
        _CVAR19.setString(_CVAR20, _CVAR23);
        statement.setInt(4, player.getLocation().getBlockX());
        statement.setInt(5, player.getLocation().getBlockY());
        statement.setInt(6, player.getLocation().getBlockZ());
        statement.setString(7, player.getLocation().getWorld().getName());
        statement.setInt(8, ((int) (java.lang.System.currentTimeMillis() / 1000)));
        statement.executeUpdate();
        set = statement.getGeneratedKeys();
        set.next();
        id = set.getInt(1);
        us.corenetwork.challenges.IO.getConnection().commit();
        statement.close();
    } catch (java.sql.SQLException e) {
        e.printStackTrace();
    }
    java.lang.String message = us.corenetwork.challenges.Settings.getString(Setting.MESSAGE_MOD_SUBMISSION_NOTICE);
    message = message.replace("<Player>", player.getName());
    message = message.replace("<Level>", java.lang.Integer.toString(level));
    message = message.replace("<ID>", java.lang.Integer.toString(id));
    us.corenetwork.challenges.Util.MessagePermissions(message, "challenges.notify");
    return true;
}