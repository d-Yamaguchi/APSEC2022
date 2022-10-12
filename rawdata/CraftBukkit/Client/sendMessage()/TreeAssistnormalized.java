@org.bukkit.event.EventHandler
public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, java.lang.String commandLabel, java.lang.String[] args) {
    if (cmd.getName().equalsIgnoreCase("TreeAssist")) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("Reload")) {
                if (!sender.hasPermission("treeassist.reload")) {
                    void _CVAR1 = MSG.ERROR_PERMISSION_RELOAD;
                    org.bukkit.command.CommandSender _CVAR0 = sender;
                     _CVAR2 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR1);
                    _CVAR0.sendMessage(_CVAR2);
                    return true;
                }
                blockList.save();
                reloadConfig();
                this.loadYamls();
                reloadLists();
                void _CVAR4 = MSG.SUCCESSFUL_RELOAD;
                org.bukkit.command.CommandSender _CVAR3 = sender;
                 _CVAR5 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR4);
                _CVAR3.sendMessage(_CVAR5);
                return true;
            } else if (args[0].equalsIgnoreCase("Toggle")) {
                if (sender.hasPermission("treeassist.toggle.other") && (args.length > 1)) {
                    if (args.length > 2) {
                        if (org.bukkit.Bukkit.getWorld(args[2]) == null) {
                            void _CVAR7 = MSG.ERROR_NOTFOUND_WORLD;
                            java.lang.String _CVAR8 = args[1];
                            org.bukkit.command.CommandSender _CVAR6 = sender;
                             _CVAR9 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR7, _CVAR8);
                            _CVAR6.sendMessage(_CVAR9);
                            return true;
                        }
                        if (toggleWorld(args[2], args[1])) {
                            void _CVAR11 = MSG.SUCCESSFUL_TOGGLE_OTHER_WORLD_ON;
                            java.lang.String _CVAR12 = args[1];
                            java.lang.String _CVAR13 = args[2];
                            org.bukkit.command.CommandSender _CVAR10 = sender;
                             _CVAR14 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR11, _CVAR12, _CVAR13);
                            _CVAR10.sendMessage(_CVAR14);
                        } else {
                            void _CVAR16 = MSG.SUCCESSFUL_TOGGLE_OTHER_WORLD_OFF;
                            java.lang.String _CVAR17 = args[1];
                            java.lang.String _CVAR18 = args[2];
                            org.bukkit.command.CommandSender _CVAR15 = sender;
                             _CVAR19 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR16, _CVAR17, _CVAR18);
                            _CVAR15.sendMessage(_CVAR19);
                        }
                    }
                    if (toggleGlobal(args[1])) {
                        void _CVAR21 = MSG.SUCCESSFUL_TOGGLE_OTHER_ON;
                        java.lang.String _CVAR22 = args[1];
                        org.bukkit.command.CommandSender _CVAR20 = sender;
                         _CVAR23 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR21, _CVAR22);
                        _CVAR20.sendMessage(_CVAR23);
                    } else {
                        void _CVAR25 = MSG.SUCCESSFUL_TOGGLE_OTHER_OFF;
                        java.lang.String _CVAR26 = args[1];
                        org.bukkit.command.CommandSender _CVAR24 = sender;
                         _CVAR27 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR25, _CVAR26);
                        _CVAR24.sendMessage(_CVAR27);
                    }
                    return true;
                }
                if (!sender.hasPermission("treeassist.toggle")) {
                    void _CVAR29 = MSG.ERROR_PERMISSION_TOGGLE;
                    org.bukkit.command.CommandSender _CVAR28 = sender;
                     _CVAR30 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR29);
                    _CVAR28.sendMessage(_CVAR30);
                    return true;
                }
                if (args.length > 1) {
                    if (org.bukkit.Bukkit.getWorld(args[1]) == null) {
                        void _CVAR32 = MSG.ERROR_NOTFOUND_WORLD;
                        java.lang.String _CVAR33 = args[1];
                        org.bukkit.command.CommandSender _CVAR31 = sender;
                         _CVAR34 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR32, _CVAR33);
                        _CVAR31.sendMessage(_CVAR34);
                        return true;
                    }
                    if (toggleWorld(args[1], sender.getName())) {
                        void _CVAR36 = MSG.SUCCESSFUL_TOGGLE_YOU_WORLD_ON;
                        java.lang.String _CVAR37 = args[1];
                        org.bukkit.command.CommandSender _CVAR35 = sender;
                         _CVAR38 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR36, _CVAR37);
                        _CVAR35.sendMessage(_CVAR38);
                    } else {
                        void _CVAR40 = MSG.SUCCESSFUL_TOGGLE_YOU_WORLD_OFF;
                        java.lang.String _CVAR41 = args[1];
                        org.bukkit.command.CommandSender _CVAR39 = sender;
                         _CVAR42 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR40, _CVAR41);
                        _CVAR39.sendMessage(_CVAR42);
                    }
                }
                if (toggleGlobal(sender.getName())) {
                    void _CVAR44 = MSG.SUCCESSFUL_TOGGLE_YOU_ON;
                    org.bukkit.command.CommandSender _CVAR43 = sender;
                     _CVAR45 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR44);
                    _CVAR43.sendMessage(_CVAR45);
                } else {
                    void _CVAR47 = MSG.SUCCESSFUL_TOGGLE_YOU_OFF;
                    org.bukkit.command.CommandSender _CVAR46 = sender;
                     _CVAR48 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR47);
                    _CVAR46.sendMessage(_CVAR48);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("Global")) {
                if (!sender.hasPermission("treeassist.toggle.global")) {
                    void _CVAR50 = MSG.ERROR_PERMISSION_TOGGLE_GLOBAL;
                    org.bukkit.command.CommandSender _CVAR49 = sender;
                     _CVAR51 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR50);
                    _CVAR49.sendMessage(_CVAR51);
                    return true;
                }
                if (!this.Enabled) {
                    this.Enabled = true;
                    void _CVAR53 = MSG.SUCCESSFUL_TOGGLE_GLOBAL_ON;
                    org.bukkit.command.CommandSender _CVAR52 = sender;
                     _CVAR54 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR53);
                    _CVAR52.sendMessage(_CVAR54);
                } else {
                    this.Enabled = false;
                    void _CVAR56 = MSG.SUCCESSFUL_TOGGLE_GLOBAL_OFF;
                    org.bukkit.command.CommandSender _CVAR55 = sender;
                     _CVAR57 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR56);
                    _CVAR55.sendMessage(_CVAR57);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("Debug")) {
                if (args.length < 2) {
                    getConfig().set("Debug", "none");
                    me.itsatacoshop247.TreeAssist.core.Debugger.load(this, sender);
                } else {
                    getConfig().set("Debug", args[1]);
                    me.itsatacoshop247.TreeAssist.core.Debugger.load(this, sender);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("ProtectTool")) {
                if (!sender.hasPermission("treeassist.tool")) {
                    void _CVAR59 = MSG.ERROR_PERMISSION_TOGGLE_TOOL;
                    org.bukkit.command.CommandSender _CVAR58 = sender;
                     _CVAR60 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR59);
                    _CVAR58.sendMessage(_CVAR60);
                    return true;
                }
                if (sender instanceof org.bukkit.entity.Player) {
                    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
                    boolean found = false;
                    for (org.bukkit.inventory.ItemStack item : player.getInventory().getContents()) {
                        if (item != null) {
                            if (item.hasItemMeta()) {
                                if (listener.isProtectTool(item)) {
                                    player.getInventory().removeItem(item);
                                    void _CVAR62 = MSG.SUCCESSFUL_TOOL_OFF;
                                    org.bukkit.command.CommandSender _CVAR61 = sender;
                                     _CVAR63 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR62);
                                    _CVAR61.sendMessage(_CVAR63);
                                    found = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        player.getInventory().addItem(listener.getProtectionTool());
                        void _CVAR65 = MSG.SUCCESSFUL_TOOL_ON;
                        org.bukkit.command.CommandSender _CVAR64 = sender;
                         _CVAR66 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR65);
                        _CVAR64.sendMessage(_CVAR66);
                    }
                    return true;
                }
                void _CVAR68 = MSG.ERROR_ONLY_PLAYERS;
                org.bukkit.command.CommandSender _CVAR67 = sender;
                 _CVAR69 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR68);
                _CVAR67.sendMessage(_CVAR69);
                return true;
            } else if (args[0].equalsIgnoreCase("noreplace")) {
                listener.noReplace(sender.getName(), seconds);
                org.bukkit.configuration.file.FileConfiguration _CVAR72 = getConfig();
                java.lang.String _CVAR73 = "Sapling Replant.Command Time Delay (Seconds)";
                int _CVAR74 = 30;
                int seconds = _CVAR72.getInt(_CVAR73, _CVAR74);
                int _CVAR75 = seconds;
                void _CVAR71 = MSG.SUCCESSFUL_NOREPLACE;
                java.lang.String _CVAR76 = java.lang.String.valueOf(_CVAR75);
                org.bukkit.command.CommandSender _CVAR70 = sender;
                 _CVAR77 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR71, _CVAR76);
                _CVAR70.sendMessage(_CVAR77);
                return true;
            } else if (args[0].equalsIgnoreCase("purge")) {
                if (!sender.hasPermission("treeassist.purge")) {
                    void _CVAR79 = MSG.ERROR_PERMISSION_PURGE;
                    org.bukkit.command.CommandSender _CVAR78 = sender;
                     _CVAR80 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR79);
                    _CVAR78.sendMessage(_CVAR80);
                    return true;
                }
                if (blockList instanceof FlatFileBlockList) {
                    FlatFileBlockList bl = ((FlatFileBlockList) (blockList));
                    try {
                        java.lang.String _CVAR84 = args[1];
                        int days = java.lang.Integer.parseInt(_CVAR84);
                        me.itsatacoshop247.TreeAssist.FlatFileBlockList _CVAR83 = bl;
                        int _CVAR85 = days;
                        int done = _CVAR83.purge(_CVAR85);
                        int _CVAR86 = done;
                        void _CVAR82 = MSG.SUCCESSFUL_PURGE_DAYS;
                        java.lang.String _CVAR87 = java.lang.String.valueOf(_CVAR86);
                        java.lang.String _CVAR88 = args[1];
                        org.bukkit.command.CommandSender _CVAR81 = sender;
                         _CVAR89 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR82, _CVAR87, _CVAR88);
                        _CVAR81.sendMessage(_CVAR89);
                    } catch (java.lang.NumberFormatException e) {
                        if (args[1].equalsIgnoreCase("global")) {
                            me.itsatacoshop247.TreeAssist.FlatFileBlockList _CVAR92 = bl;
                            org.bukkit.command.CommandSender _CVAR93 = sender;
                            int done = _CVAR92.purge(_CVAR93);
                            int _CVAR94 = done;
                            void _CVAR91 = MSG.SUCCESSFUL_PURGE_GLOBAL;
                            java.lang.String _CVAR95 = java.lang.String.valueOf(_CVAR94);
                            org.bukkit.command.CommandSender _CVAR90 = sender;
                             _CVAR96 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR91, _CVAR95);
                            _CVAR90.sendMessage(_CVAR96);
                        } else {
                            me.itsatacoshop247.TreeAssist.FlatFileBlockList _CVAR99 = bl;
                            java.lang.String _CVAR100 = args[1];
                            int done = _CVAR99.purge(_CVAR100);
                            int _CVAR101 = done;
                            void _CVAR98 = MSG.SUCCESSFUL_PURGE_WORLD;
                            java.lang.String _CVAR102 = java.lang.String.valueOf(_CVAR101);
                            java.lang.String _CVAR103 = args[1];
                            org.bukkit.command.CommandSender _CVAR97 = sender;
                             _CVAR104 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR98, _CVAR102, _CVAR103);
                            _CVAR97.sendMessage(_CVAR104);
                        }
                    }
                } else {
                    void _CVAR106 = MSG.ERROR_ONLY_TREEASSIST_BLOCKLIST;
                    org.bukkit.command.CommandSender _CVAR105 = sender;
                     _CVAR107 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR106);
                    _CVAR105.sendMessage(_CVAR107);
                }
                return true;
            } else if (args[0].equalsIgnoreCase("addtool")) {
                if (!sender.hasPermission("treeassist.addtool")) {
                    void _CVAR109 = MSG.ERROR_PERMISSION_ADDTOOL;
                    org.bukkit.command.CommandSender _CVAR108 = sender;
                     _CVAR110 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR109);
                    _CVAR108.sendMessage(_CVAR110);
                    return true;
                }
                if (sender instanceof org.bukkit.entity.Player) {
                    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
                    me.itsatacoshop247.TreeAssist.core.Utils.addRequiredTool(player);
                    return true;
                }
                void _CVAR112 = MSG.ERROR_ONLY_PLAYERS;
                org.bukkit.command.CommandSender _CVAR111 = sender;
                 _CVAR113 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR112);
                _CVAR111.sendMessage(_CVAR113);
                return true;
            } else if (args[0].equalsIgnoreCase("removetool")) {
                if (!sender.hasPermission("treeassist.removetool")) {
                    void _CVAR115 = MSG.ERROR_PERMISSION_REMOVETOOL;
                    org.bukkit.command.CommandSender _CVAR114 = sender;
                     _CVAR116 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR115);
                    _CVAR114.sendMessage(_CVAR116);
                    return true;
                }
                if (sender instanceof org.bukkit.entity.Player) {
                    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
                    me.itsatacoshop247.TreeAssist.core.Utils.removeRequiredTool(player);
                    return true;
                }
                void _CVAR118 = MSG.ERROR_ONLY_PLAYERS;
                org.bukkit.command.CommandSender _CVAR117 = sender;
                 _CVAR119 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR118);
                _CVAR117.sendMessage(_CVAR119);
                return true;
            } else if (args[0].equalsIgnoreCase("addcustom")) {
                if (!sender.hasPermission("treeassist.addcustom")) {
                    void _CVAR121 = MSG.ERROR_PERMISSION_ADDCUSTOM;
                    org.bukkit.command.CommandSender _CVAR120 = sender;
                     _CVAR122 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR121);
                    _CVAR120.sendMessage(_CVAR122);
                    return true;
                }
                if (sender instanceof org.bukkit.entity.Player) {
                    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
                    me.itsatacoshop247.TreeAssist.core.Utils.addCustomGroup(player);
                    return true;
                }
                void _CVAR124 = MSG.ERROR_ONLY_PLAYERS;
                org.bukkit.command.CommandSender _CVAR123 = sender;
                 _CVAR125 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR124);
                _CVAR123.sendMessage(_CVAR125);
                return true;
            } else if (args[0].equalsIgnoreCase("removecustom")) {
                if (!sender.hasPermission("treeassist.removecustom")) {
                    void _CVAR127 = MSG.ERROR_PERMISSION_REMOVECUSTOM;
                    org.bukkit.command.CommandSender _CVAR126 = sender;
                     _CVAR128 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR127);
                    _CVAR126.sendMessage(_CVAR128);
                    return true;
                }
                if (sender instanceof org.bukkit.entity.Player) {
                    org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (sender));
                    me.itsatacoshop247.TreeAssist.core.Utils.removeCustomGroup(player);
                    return true;
                }
                void _CVAR130 = MSG.ERROR_ONLY_PLAYERS;
                org.bukkit.command.CommandSender _CVAR129 = sender;
                 _CVAR131 = me.itsatacoshop247.TreeAssist.core.Language.parse(_CVAR130);
                _CVAR129.sendMessage(_CVAR131);
                return true;
            }
        }
    }
    return false;
}