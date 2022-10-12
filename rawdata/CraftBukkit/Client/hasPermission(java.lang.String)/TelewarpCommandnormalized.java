@java.lang.Override
public java.util.List<java.lang.String> onTabComplete(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String alias, java.util.LinkedList<java.lang.String> args) {
    java.util.List<java.lang.String> results = new java.util.ArrayList<java.lang.String>();
    if (args.peekLast().toLowerCase().startsWith("reload")) {
        org.bukkit.command.CommandSender _CVAR0 = sender;
        java.lang.String _CVAR1 = "telewarp.admin";
        boolean _CVAR2 = _CVAR0.hasPermission(_CVAR1);
        if () {
            results.add("reload");
        }
    }
    return results;
}