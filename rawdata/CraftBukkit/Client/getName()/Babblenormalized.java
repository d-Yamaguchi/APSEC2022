@java.lang.Override
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, java.lang.String label, java.lang.String[] args) {
    org.bukkit.command.Command _CVAR0 = command;
    java.lang.String _CVAR1 = _CVAR0.getName();
    java.lang.String _CVAR2 = "babble";
    boolean _CVAR3 = _CVAR1.equalsIgnoreCase(_CVAR2);
    if () {
        if (args.length > 0) {
            if ("add".equals(args[0])) {
                if ((args.length != 3) && (args.length != 2)) {
                    return false;
                }
                addPlayerToList(sender, args);
                return true;
            } else if ("remove".equals(args[0])) {
                if (args.length != 2) {
                    return false;
                }
                removePlayerFromList(sender, args);
                return true;
            } else if ("list".equals(args[0])) {
                if (args.length != 1) {
                    return false;
                }
                sendBabblersToPlayer(sender);
                return true;
            } else if ("patterns".equals(args[0])) {
                if (args.length != 1) {
                    return false;
                }
                sendAvailablePatternsToPlayer(sender);
                return true;
            } else if ("reset".equals(args[0])) {
                if (args.length != 1) {
                    return false;
                }
                clearList(sender);
                return true;
            } else if ("mute".equals(args[0])) {
                if (args.length != 2) {
                    return false;
                }
                java.lang.String[] newArgs = new java.lang.String[]{ "", args[1], args[0] };
                addPlayerToList(sender, newArgs);
                return true;
            }
        } else {
            return false;
        }
    }
    return false;
}