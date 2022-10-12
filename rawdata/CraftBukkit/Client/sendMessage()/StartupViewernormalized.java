/**
 * Made to print a report on the server info
 *
 * @since 1.0
 * @version 1.0
 */
@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String label, java.lang.String[] args) {
    if (cmd.getName().equalsIgnoreCase("startup")) {
        if (args.length == 1) {
            java.lang.management.RuntimeMXBean RuntimemxBean = java.lang.management.ManagementFactory.getRuntimeMXBean();
            java.util.List<java.lang.String> arguments = RuntimemxBean.getInputArguments();
            for (java.lang.String arg : arguments) {
                sb.append(arg).append(" ");
            }
            java.lang.String _CVAR1 = "Arguments: ";
            java.lang.StringBuilder sb = new java.lang.StringBuilder(_CVAR1);
            if (args[0].equalsIgnoreCase("print") && sender.hasPermission("startupviewer.print")) {
                java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-YY--HH-mm-ss");
                java.util.Date date = new java.util.Date();
                java.lang.String filename = (((this.getDataFolder() + java.io.File.separator) + "startup--") + dateFormat.format(date)) + ".txt";
                if (!this.getDataFolder().exists()) {
                    this.getDataFolder().mkdir();
                }
                java.io.File file = new java.io.File(filename);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                } catch (java.io.IOException ex) {
                    java.util.logging.Logger.getLogger(com.rogue.startupviewer.StartupViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                try {
                    java.io.BufferedWriter out = new java.io.BufferedWriter(new java.io.FileWriter(filename));
                    out.write(sb.toString());
                    out.close();
                } catch (java.io.IOException ex) {
                    java.util.logging.Logger.getLogger(com.rogue.startupviewer.StartupViewer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("view") && sender.hasPermission("startupviewer.view")) {
                java.lang.StringBuilder _CVAR2 = sb;
                org.bukkit.command.CommandSender _CVAR0 = sender;
                java.lang.String _CVAR3 = _CVAR2.toString();
                _CVAR0.sendMessage(_CVAR3);
                return true;
            }
        } else if (args.length == 0) {
            java.lang.String _CVAR5 = "&f, made by &e1Rogue";
            java.lang.String _CVAR6 = ("[&9StartupViewer&f] v&a" + this.getDescription().getVersion()) + _CVAR5;
            org.bukkit.command.CommandSender _CVAR4 = sender;
            java.lang.String _CVAR7 = com.rogue.startupviewer.StartupViewer._(_CVAR6);
            _CVAR4.sendMessage(_CVAR7);
            return true;
        }
        return false;
    }
    return false;
}