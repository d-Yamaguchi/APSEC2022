package com.hsun324.simplebukkit.permissions;
import com.hsun324.simplebukkit.SimpleBukkitPlugin;
import com.nijiko.permissions.Group;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
public class PermissionsHandler {
    private static final com.hsun324.simplebukkit.permissions.PermissionsHandler instance = new com.hsun324.simplebukkit.permissions.PermissionsHandler();

    public static com.hsun324.simplebukkit.permissions.PermissionsHandler getInstance() {
        return com.hsun324.simplebukkit.permissions.PermissionsHandler.instance;
    }

    private com.nijiko.permissions.PermissionHandler permissionHandler;

    public void loadPermissions() {
        org.bukkit.plugin.Plugin permissionsCandidate = com.hsun324.simplebukkit.SimpleBukkitPlugin.getInstance().getPM().getPlugin("Permissions");
        if ((permissionsCandidate != null) && (permissionsCandidate instanceof com.nijikokun.bukkit.Permissions.Permissions)) {
            permissionHandler = ((com.nijikokun.bukkit.Permissions.Permissions) (permissionsCandidate)).getHandler();
            com.hsun324.simplebukkit.SimpleBukkitPlugin.getInstance().getLogger().info("SimpleCommands: Permissions (Phoenix) was found.");
        } else
            com.hsun324.simplebukkit.SimpleBukkitPlugin.getInstance().getLogger().warning("SimpleCommands: Permissions (Phoenix) was not found. Using OP for permissions.");

    }

    public boolean hasPermission(org.bukkit.entity.Player player, java.lang.String permission) {
        if (permissionHandler != null)
            return permissionHandler.has(player, permission);

        return player.isOp();
    }

    public boolean isInGroup(org.bukkit.entity.Player player, java.lang.String group) {
        if (permissionHandler != null) {
            return permissionHandler.inGroup(store.loadAchievements(mPlayer), store.loadAchievements(mPlayer), group);
        }
        return false;
    }

    public java.lang.String getGroup(org.bukkit.entity.Player player) {
        if (permissionHandler != null) {
            java.lang.String[] groups = permissionHandler.getGroups(player.getWorld().getName(), player.getName());
            if (groups.length > 0)
                return groups[0];

            com.nijiko.permissions.Group defaultGroup = permissionHandler.getDefaultGroup(player.getWorld().getName());
            if (defaultGroup != null)
                return defaultGroup.getName();

        }
        return "";
    }

    public java.lang.String getPrefix(org.bukkit.entity.Player player) {
        if (permissionHandler != null)
            return permissionHandler.getUserPrefix(player.getWorld().getName(), player.getName());

        return "";
    }

    public java.lang.String getSuffix(org.bukkit.entity.Player player) {
        if (permissionHandler != null)
            return permissionHandler.getUserSuffix(player.getWorld().getName(), getGroup(player));

        return "";
    }

    public boolean isPermissionsLoaded() {
        return permissionHandler != null;
    }
}