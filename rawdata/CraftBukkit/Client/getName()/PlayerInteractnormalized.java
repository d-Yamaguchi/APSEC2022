/* The event called when a player clicks a block.

USED:
When a ZAPlayer clicks a sign, to check the lines for strings that trigger a response.
 */
@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
public void PIE(org.bukkit.event.player.PlayerInteractEvent event) {
    org.bukkit.event.block.Action a = event.getAction();
    org.bukkit.event.player.PlayerInteractEvent _CVAR90 = _CVAR82;
    org.bukkit.event.player.PlayerInteractEvent _CVAR98 = _CVAR90;
    org.bukkit.event.player.PlayerInteractEvent _CVAR110 = _CVAR98;
    org.bukkit.event.player.PlayerInteractEvent _CVAR130 = _CVAR110;
    org.bukkit.event.player.PlayerInteractEvent _CVAR145 = _CVAR130;
    org.bukkit.event.player.PlayerInteractEvent _CVAR164 = _CVAR145;
    org.bukkit.event.player.PlayerInteractEvent _CVAR185 = _CVAR164;
    org.bukkit.event.player.PlayerInteractEvent _CVAR217 = _CVAR185;
    org.bukkit.entity.Player p = _CVAR217.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR172 = _CVAR164;
    org.bukkit.event.player.PlayerInteractEvent _CVAR193 = _CVAR172;
    org.bukkit.event.player.PlayerInteractEvent _CVAR221 = _CVAR193;
    org.bukkit.entity.Player p = _CVAR221.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR1 = event;
    org.bukkit.event.player.PlayerInteractEvent _CVAR11 = _CVAR1;
    org.bukkit.event.player.PlayerInteractEvent _CVAR16 = _CVAR11;
    org.bukkit.event.player.PlayerInteractEvent _CVAR20 = _CVAR16;
    org.bukkit.event.player.PlayerInteractEvent _CVAR27 = _CVAR20;
    org.bukkit.event.player.PlayerInteractEvent _CVAR35 = _CVAR27;
    org.bukkit.event.player.PlayerInteractEvent _CVAR39 = _CVAR35;
    org.bukkit.event.player.PlayerInteractEvent _CVAR47 = _CVAR39;
    org.bukkit.event.player.PlayerInteractEvent _CVAR56 = _CVAR47;
    org.bukkit.event.player.PlayerInteractEvent _CVAR60 = _CVAR56;
    org.bukkit.event.player.PlayerInteractEvent _CVAR64 = _CVAR60;
    org.bukkit.event.player.PlayerInteractEvent _CVAR68 = _CVAR64;
    org.bukkit.event.player.PlayerInteractEvent _CVAR75 = _CVAR68;
    org.bukkit.event.player.PlayerInteractEvent _CVAR82 = _CVAR75;
    org.bukkit.event.player.PlayerInteractEvent _CVAR95 = _CVAR82;
    org.bukkit.event.player.PlayerInteractEvent _CVAR102 = _CVAR95;
    org.bukkit.event.player.PlayerInteractEvent _CVAR114 = _CVAR102;
    org.bukkit.event.player.PlayerInteractEvent _CVAR134 = _CVAR114;
    org.bukkit.event.player.PlayerInteractEvent _CVAR153 = _CVAR134;
    org.bukkit.event.player.PlayerInteractEvent _CVAR177 = _CVAR153;
    org.bukkit.event.player.PlayerInteractEvent _CVAR200 = _CVAR177;
    org.bukkit.event.player.PlayerInteractEvent _CVAR225 = _CVAR200;
    org.bukkit.entity.Player p = _CVAR225.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR206 = _CVAR200;
    org.bukkit.event.player.PlayerInteractEvent _CVAR232 = _CVAR206;
    org.bukkit.entity.Player p = _CVAR232.getPlayer();
    org.bukkit.event.player.PlayerInteractEvent _CVAR7 = event;
    org.bukkit.event.player.PlayerInteractEvent _CVAR31 = _CVAR7;
    org.bukkit.event.player.PlayerInteractEvent _CVAR86 = _CVAR31;
    org.bukkit.event.player.PlayerInteractEvent _CVAR123 = _CVAR86;
    org.bukkit.event.player.PlayerInteractEvent _CVAR156 = _CVAR123;
    org.bukkit.event.player.PlayerInteractEvent _CVAR168 = _CVAR156;
    org.bukkit.event.player.PlayerInteractEvent _CVAR196 = _CVAR168;
    org.bukkit.event.player.PlayerInteractEvent _CVAR210 = _CVAR196;
    org.bukkit.event.player.PlayerInteractEvent _CVAR237 = _CVAR210;
    org.bukkit.block.Block b = _CVAR237.getClickedBlock();
    org.bukkit.event.player.PlayerInteractEvent _CVAR119 = _CVAR114;
    org.bukkit.event.player.PlayerInteractEvent _CVAR141 = _CVAR119;
    org.bukkit.event.player.PlayerInteractEvent _CVAR160 = _CVAR141;
    org.bukkit.event.player.PlayerInteractEvent _CVAR181 = _CVAR160;
    org.bukkit.event.player.PlayerInteractEvent _CVAR214 = _CVAR181;
    org.bukkit.event.player.PlayerInteractEvent _CVAR241 = _CVAR214;
    org.bukkit.entity.Player p = _CVAR241.getPlayer();
    if (b != null) {
        org.bukkit.entity.Player _CVAR2 = p;
        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR0 = com.github.event.bukkit.PlayerInteract.barrierPlayers;
        java.lang.String _CVAR3 = _CVAR2.getName();
         _CVAR4 = _CVAR0.containsKey(_CVAR3);
         _CVAR5 = (!data.isZAPlayer(p)) && _CVAR4;
         _CVAR6 = _CVAR5 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
        org.bukkit.block.Block _CVAR8 = b;
        org.bukkit.block.Block _CVAR32 = _CVAR8;
        org.bukkit.block.Block _CVAR87 = _CVAR32;
        org.bukkit.block.Block _CVAR124 = _CVAR87;
        org.bukkit.block.Block _CVAR169 = _CVAR124;
        org.bukkit.Location loc = _CVAR169.getLocation();
        if () {
            if (data.isBarrier(loc)) {
                p.sendMessage(org.bukkit.ChatColor.RED + "That is already a barrier!");
                return;
            }
            org.bukkit.entity.Player _CVAR12 = p;
            java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR10 = com.github.event.bukkit.PlayerInteract.barrierPlayers;
            java.lang.String _CVAR13 = _CVAR12.getName();
            org.bukkit.Location _CVAR9 = loc;
             _CVAR14 = _CVAR10.get(_CVAR13);
            p.sendMessage(org.bukkit.ChatColor.GRAY + "Barrier created successfully!");
            org.bukkit.entity.Player _CVAR17 = p;
            java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR15 = com.github.event.bukkit.PlayerInteract.barrierPlayers;
            java.lang.String _CVAR18 = _CVAR17.getName();
            _CVAR15.remove(_CVAR18);
        } else {
            org.bukkit.entity.Player _CVAR21 = p;
            java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR19 = com.github.event.bukkit.PlayerInteract.powerSwitchClickers;
            java.lang.String _CVAR22 = _CVAR21.getName();
             _CVAR23 = _CVAR19.containsKey(_CVAR22);
             _CVAR24 = (!data.isZAPlayer(p)) && _CVAR23;
             _CVAR25 = _CVAR24 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
            if () {
                if (data.isPowerSwitch(loc)) {
                    p.sendMessage(org.bukkit.ChatColor.RED + "That is already a power switch!");
                    return;
                }
                org.bukkit.entity.Player _CVAR28 = p;
                java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR26 = com.github.event.bukkit.PlayerInteract.powerSwitchClickers;
                java.lang.String _CVAR29 = _CVAR28.getName();
                 _CVAR30 = _CVAR26.get(_CVAR29);
                org.bukkit.Location _CVAR33 = loc;
                p.sendMessage(org.bukkit.ChatColor.GRAY + "Switch created successfully.");
                org.bukkit.entity.Player _CVAR36 = p;
                java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR34 = com.github.event.bukkit.PlayerInteract.powerSwitchClickers;
                java.lang.String _CVAR37 = _CVAR36.getName();
                _CVAR34.remove(_CVAR37);
                return;
            } else {
                org.bukkit.entity.Player _CVAR40 = p;
                java.util.HashMap<java.lang.String, java.lang.Boolean> _CVAR38 = com.github.event.bukkit.PlayerInteract.powerClickers;
                java.lang.String _CVAR41 = _CVAR40.getName();
                boolean _CVAR42 = _CVAR38.containsKey(_CVAR41);
                 _CVAR43 = (!data.isZAPlayer(p)) && _CVAR42;
                 _CVAR44 = _CVAR43 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                if () {
                    if (data.isGameObject(loc)) {
                        com.github.behavior.GameObject object = data.getGameObjectByLocation(loc);
                        org.bukkit.entity.Player _CVAR48 = p;
                        java.util.HashMap<java.lang.String, java.lang.String> _CVAR46 = com.github.event.bukkit.PlayerInteract.powerClickerGames;
                        java.lang.String _CVAR49 = _CVAR48.getName();
                        com.github.DataContainer _CVAR45 = data;
                        java.lang.String _CVAR50 = _CVAR46.get(_CVAR49);
                        boolean _CVAR51 = false;
                         _CVAR52 = _CVAR45.getGame(_CVAR50, _CVAR51);
                         _CVAR53 = object.getGame() == _CVAR52;
                         _CVAR54 = ((object instanceof com.github.behavior.Powerable) && (object.getGame() != null)) && _CVAR53;
                        if () {
                            com.github.behavior.Powerable powerObj = ((com.github.behavior.Powerable) (object));
                            org.bukkit.entity.Player _CVAR57 = p;
                            java.util.HashMap<java.lang.String, java.lang.Boolean> _CVAR55 = com.github.event.bukkit.PlayerInteract.powerClickers;
                            java.lang.String _CVAR58 = _CVAR57.getName();
                            boolean power = _CVAR55.get(_CVAR58);
                            powerObj.setRequiresPower(power);
                            p.sendMessage(((org.bukkit.ChatColor.GRAY + "Power on this object ") + (power ? "enabled" : "disabled")) + ".");
                            return;
                        }
                        p.sendMessage(org.bukkit.ChatColor.RED + "Either this object is not powerable or it does not match the game entered.");
                        return;
                    }
                    p.sendMessage(org.bukkit.ChatColor.RED + "This is not an object!");
                    org.bukkit.entity.Player _CVAR61 = p;
                    java.util.HashMap<java.lang.String, java.lang.Boolean> _CVAR59 = com.github.event.bukkit.PlayerInteract.powerClickers;
                    java.lang.String _CVAR62 = _CVAR61.getName();
                    _CVAR59.remove(_CVAR62);
                    org.bukkit.entity.Player _CVAR65 = p;
                    java.util.HashMap<java.lang.String, java.lang.String> _CVAR63 = com.github.event.bukkit.PlayerInteract.powerClickerGames;
                    java.lang.String _CVAR66 = _CVAR65.getName();
                    _CVAR63.remove(_CVAR66);
                    return;
                } else {
                    org.bukkit.entity.Player _CVAR69 = p;
                    java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR67 = com.github.event.bukkit.PlayerInteract.spawnerPlayers;
                    java.lang.String _CVAR70 = _CVAR69.getName();
                     _CVAR71 = _CVAR67.containsKey(_CVAR70);
                     _CVAR72 = (!data.isZAPlayer(p)) && _CVAR71;
                     _CVAR73 = _CVAR72 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                    if () {
                        org.bukkit.entity.Player _CVAR76 = p;
                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR74 = com.github.event.bukkit.PlayerInteract.spawnerPlayers;
                        java.lang.String _CVAR77 = _CVAR76.getName();
                         _CVAR78 = _CVAR74.get(_CVAR77);
                         _CVAR79 = data.getMobSpawner(b.getLocation()).getGame() == _CVAR78;
                         _CVAR80 = data.isMobSpawner(b.getLocation()) && _CVAR79;
                        if () {
                            p.sendMessage(org.bukkit.ChatColor.RED + "That is already a mob spawner for this game!");
                            return;
                        }
                        org.bukkit.entity.Player _CVAR83 = p;
                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR81 = com.github.event.bukkit.PlayerInteract.spawnerPlayers;
                        java.lang.String _CVAR84 = _CVAR83.getName();
                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR89 = com.github.event.bukkit.PlayerInteract.spawnerPlayers;
                        org.bukkit.entity.Player _CVAR91 = p;
                        org.bukkit.entity.Player _CVAR96 = _CVAR91;
                        java.lang.String _CVAR92 = _CVAR96.getName();
                        org.bukkit.Location _CVAR88 = loc;
                         _CVAR93 = _CVAR89.get(_CVAR92);
                         _CVAR85 = _CVAR81.get(_CVAR84);
                        com.github.aspect.MobSpawner _CVAR94 = new com.github.aspect.MobSpawner(_CVAR88, _CVAR93);
                        _CVAR85.addObject(_CVAR94);
                        p.sendMessage(org.bukkit.ChatColor.GRAY + "Spawner created successfully!");
                        org.bukkit.entity.Player _CVAR99 = p;
                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR97 = com.github.event.bukkit.PlayerInteract.spawnerPlayers;
                        java.lang.String _CVAR100 = _CVAR99.getName();
                        _CVAR97.remove(_CVAR100);
                    } else {
                        org.bukkit.entity.Player _CVAR103 = p;
                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR101 = com.github.event.bukkit.PlayerInteract.chestPlayers;
                        java.lang.String _CVAR104 = _CVAR103.getName();
                         _CVAR105 = _CVAR101.containsKey(_CVAR104);
                         _CVAR106 = (!data.isZAPlayer(p)) && _CVAR105;
                         _CVAR107 = _CVAR106 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                         _CVAR108 = _CVAR107 && (b.getType() == org.bukkit.Material.CHEST);
                        if () {
                            event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                            java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR109 = com.github.event.bukkit.PlayerInteract.chestPlayers;
                            org.bukkit.entity.Player _CVAR111 = p;
                            org.bukkit.entity.Player _CVAR120 = _CVAR111;
                            java.lang.String _CVAR112 = _CVAR120.getName();
                            java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR118 = _CVAR109;
                            java.lang.String _CVAR121 = _CVAR112;
                            com.github.aspect.Game zag = _CVAR118.get(_CVAR121);
                            if (!data.isMysteryChest(b.getLocation())) {
                                org.bukkit.entity.Player _CVAR115 = p;
                                java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR113 = com.github.event.bukkit.PlayerInteract.chestPlayers;
                                java.lang.String _CVAR116 = _CVAR115.getName();
                                <nulltype> _CVAR126 = null;
                                com.github.aspect.Game _CVAR122 = zag;
                                org.bukkit.Location _CVAR125 = loc;
                                 _CVAR127 = zag.getActiveMysteryChest() == _CVAR126;
                                 _CVAR117 = _CVAR113.get(_CVAR116);
                                com.github.aspect.MysteryChest _CVAR128 = new com.github.aspect.MysteryChest(_CVAR122, _CVAR125, _CVAR127);
                                _CVAR117.addObject(_CVAR128);
                                p.sendMessage(org.bukkit.ChatColor.GRAY + "Mystery chest created successfully!");
                            } else {
                                p.sendMessage(org.bukkit.ChatColor.RED + "That is already a mystery chest!");
                            }
                            org.bukkit.entity.Player _CVAR131 = p;
                            java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR129 = com.github.event.bukkit.PlayerInteract.chestPlayers;
                            java.lang.String _CVAR132 = _CVAR131.getName();
                            _CVAR129.remove(_CVAR132);
                        } else {
                            org.bukkit.entity.Player _CVAR135 = p;
                            java.util.ArrayList<java.lang.String> _CVAR133 = com.github.event.bukkit.PlayerInteract.removers;
                            java.lang.String _CVAR136 = _CVAR135.getName();
                            boolean _CVAR137 = _CVAR133.contains(_CVAR136);
                             _CVAR138 = (!data.isZAPlayer(p)) && _CVAR137;
                             _CVAR139 = _CVAR138 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                            if () {
                                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                                com.github.behavior.GameObject removal = null;
                                for (com.github.behavior.GameObject o : data.getObjectsOfType(com.github.behavior.GameObject.class)) {
                                    for (org.bukkit.block.Block block : o.getDefiningBlocks()) {
                                        if ((block != null) && (block.getLocation().distanceSquared(b.getLocation()) <= 1)) {
                                            // 1 squared = 1
                                            removal = o;
                                            break;
                                        }
                                    }
                                }
                                if (removal != null) {
                                    removal.remove();
                                    p.sendMessage(((org.bukkit.ChatColor.GRAY + "Removal ") + org.bukkit.ChatColor.GREEN) + "successful");
                                } else {
                                    p.sendMessage(((org.bukkit.ChatColor.GRAY + "Removal ") + org.bukkit.ChatColor.RED) + "unsuccessful");
                                }
                                org.bukkit.entity.Player _CVAR142 = p;
                                java.util.ArrayList<java.lang.String> _CVAR140 = com.github.event.bukkit.PlayerInteract.removers;
                                java.lang.String _CVAR143 = _CVAR142.getName();
                                _CVAR140.remove(_CVAR143);
                            } else {
                                org.bukkit.entity.Player _CVAR146 = p;
                                java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR144 = com.github.event.bukkit.PlayerInteract.passagePlayers;
                                java.lang.String _CVAR147 = _CVAR146.getName();
                                 _CVAR148 = _CVAR144.containsKey(_CVAR147);
                                 _CVAR149 = (!data.isZAPlayer(p)) && _CVAR148;
                                 _CVAR150 = _CVAR149 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                                if () {
                                    if (isPassage(b)) {
                                        p.sendMessage(org.bukkit.ChatColor.RED + "That is already a passage!");
                                        return;
                                    }
                                    boolean _CVAR151 = !com.github.event.bukkit.PlayerInteract.locClickers.containsKey(p.getName());
                                    if () {
                                        org.bukkit.entity.Player _CVAR154 = p;
                                        org.bukkit.block.Block _CVAR157 = b;
                                        java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR152 = com.github.event.bukkit.PlayerInteract.locClickers;
                                        java.lang.String _CVAR155 = _CVAR154.getName();
                                        org.bukkit.Location _CVAR158 = _CVAR157.getLocation();
                                        _CVAR152.put(_CVAR155, _CVAR158);
                                        p.sendMessage(org.bukkit.ChatColor.GRAY + "Click another block to select point 2.");
                                    } else {
                                        org.bukkit.entity.Player _CVAR165 = p;
                                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR163 = com.github.event.bukkit.PlayerInteract.passagePlayers;
                                        java.lang.String _CVAR166 = _CVAR165.getName();
                                        java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR159 = com.github.event.bukkit.PlayerInteract.locClickers;
                                        org.bukkit.entity.Player _CVAR161 = p;
                                        org.bukkit.entity.Player _CVAR173 = _CVAR161;
                                        java.lang.String _CVAR162 = _CVAR173.getName();
                                        java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR171 = _CVAR159;
                                        java.lang.String _CVAR174 = _CVAR162;
                                        org.bukkit.Location loc2 = _CVAR171.get(_CVAR174);
                                         _CVAR167 = _CVAR163.get(_CVAR166);
                                        org.bukkit.Location _CVAR170 = loc;
                                        org.bukkit.Location _CVAR175 = loc2;
                                        org.bukkit.entity.Player _CVAR178 = p;
                                        java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR176 = com.github.event.bukkit.PlayerInteract.locClickers;
                                        java.lang.String _CVAR179 = _CVAR178.getName();
                                        _CVAR176.remove(_CVAR179);
                                        org.bukkit.entity.Player _CVAR182 = p;
                                        java.util.HashMap<java.lang.String, com.github.aspect.Game> _CVAR180 = com.github.event.bukkit.PlayerInteract.passagePlayers;
                                        java.lang.String _CVAR183 = _CVAR182.getName();
                                        _CVAR180.remove(_CVAR183);
                                        p.sendMessage(org.bukkit.ChatColor.GRAY + "Passage created!");
                                    }
                                } else {
                                    org.bukkit.entity.Player _CVAR186 = p;
                                    java.util.HashMap<java.lang.String, java.lang.String> _CVAR184 = com.github.event.bukkit.PlayerInteract.mapDataSavePlayers;
                                    java.lang.String _CVAR187 = _CVAR186.getName();
                                    boolean _CVAR188 = _CVAR184.containsKey(_CVAR187);
                                     _CVAR189 = (!data.isZAPlayer(p)) && _CVAR188;
                                     _CVAR190 = _CVAR189 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                                    if () {
                                        boolean _CVAR191 = !com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers.containsKey(p.getName());
                                        if () {
                                            org.bukkit.entity.Player _CVAR194 = p;
                                            org.bukkit.block.Block _CVAR197 = b;
                                            java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR192 = com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers;
                                            java.lang.String _CVAR195 = _CVAR194.getName();
                                            org.bukkit.Location _CVAR198 = _CVAR197.getLocation();
                                            _CVAR192.put(_CVAR195, _CVAR198);
                                            p.sendMessage(org.bukkit.ChatColor.GRAY + "Please click the other corner of the map.");
                                        } else {
                                            org.bukkit.entity.Player _CVAR201 = p;
                                            java.util.HashMap<java.lang.String, java.lang.String> _CVAR199 = com.github.event.bukkit.PlayerInteract.mapDataSavePlayers;
                                            java.lang.String _CVAR202 = _CVAR201.getName();
                                            java.lang.String _CVAR203 = _CVAR199.get(_CVAR202);
                                            java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR205 = com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers;
                                            org.bukkit.entity.Player _CVAR207 = p;
                                            org.bukkit.entity.Player _CVAR215 = _CVAR207;
                                            java.lang.String _CVAR208 = _CVAR215.getName();
                                            org.bukkit.block.Block _CVAR211 = b;
                                            org.bukkit.Location _CVAR209 = _CVAR205.get(_CVAR208);
                                            org.bukkit.Location _CVAR212 = _CVAR211.getLocation();
                                            com.github.storage.MapDataStorage _CVAR204 = new com.github.storage.MapDataStorage(_CVAR203);
                                            com.github.utility.selection.Rectangle _CVAR213 = new com.github.utility.selection.Rectangle(_CVAR209, _CVAR212);
                                            boolean saved = _CVAR204.save(_CVAR213);
                                            org.bukkit.entity.Player _CVAR218 = p;
                                            java.util.HashMap<java.lang.String, org.bukkit.Location> _CVAR216 = com.github.event.bukkit.PlayerInteract.mapDataPoint1SaveClickers;
                                            java.lang.String _CVAR219 = _CVAR218.getName();
                                            _CVAR216.remove(_CVAR219);
                                            org.bukkit.entity.Player _CVAR222 = p;
                                            java.util.HashMap<java.lang.String, java.lang.String> _CVAR220 = com.github.event.bukkit.PlayerInteract.mapDataSavePlayers;
                                            java.lang.String _CVAR223 = _CVAR222.getName();
                                            _CVAR220.remove(_CVAR223);
                                            java.lang.String successful = (saved) ? (org.bukkit.ChatColor.GREEN + "successfully") + org.bukkit.ChatColor.RESET : (org.bukkit.ChatColor.RED + "unsuccessfully") + org.bukkit.ChatColor.RESET;
                                            p.sendMessage(((org.bukkit.ChatColor.GRAY + "Mapdata saved ") + successful) + ".");
                                        }
                                    } else {
                                        org.bukkit.entity.Player _CVAR226 = p;
                                        java.util.HashMap<java.lang.String, java.lang.String> _CVAR224 = com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers;
                                        java.lang.String _CVAR227 = _CVAR226.getName();
                                        boolean _CVAR228 = _CVAR224.containsKey(_CVAR227);
                                         _CVAR229 = (!data.isZAPlayer(p)) && _CVAR228;
                                         _CVAR230 = _CVAR229 && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK);
                                        if () {
                                            org.bukkit.entity.Player _CVAR233 = p;
                                            java.util.HashMap<java.lang.String, java.lang.String> _CVAR231 = com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers;
                                            java.lang.String _CVAR234 = _CVAR233.getName();
                                            java.lang.String _CVAR235 = _CVAR231.get(_CVAR234);
                                            org.bukkit.block.Block _CVAR238 = b;
                                             _CVAR236 = com.github.storage.MapDataStorage.getFromGame(_CVAR235);
                                            org.bukkit.Location _CVAR239 = _CVAR238.getLocation();
                                            boolean loaded = _CVAR236.load(_CVAR239);
                                            org.bukkit.entity.Player _CVAR242 = p;
                                            java.util.HashMap<java.lang.String, java.lang.String> _CVAR240 = com.github.event.bukkit.PlayerInteract.mapDataLoadPlayers;
                                            java.lang.String _CVAR243 = _CVAR242.getName();
                                            _CVAR240.remove(_CVAR243);
                                            java.lang.String successful = (loaded) ? (org.bukkit.ChatColor.GREEN + "successfully") + org.bukkit.ChatColor.RESET : (org.bukkit.ChatColor.RED + "unsuccessfully") + org.bukkit.ChatColor.RESET;
                                            p.sendMessage(((org.bukkit.ChatColor.GRAY + "Mapdata loaded ") + successful) + ".");
                                        } else if ((((b.getType() == org.bukkit.Material.SIGN) || (b.getType() == org.bukkit.Material.SIGN_POST)) || (b.getType() == org.bukkit.Material.WALL_SIGN)) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                                            org.bukkit.block.Sign s = ((org.bukkit.block.Sign) (b.getState()));
                                            if (s.getLine(0).equalsIgnoreCase(Local.BASE_STRING.getSetting())) {
                                                event.setUseInteractedBlock(org.bukkit.event.Event.Result.DENY);
                                                runLines(s, p);
                                                return;
                                            }
                                            return;
                                        } else if (data.isZAPlayer(p)) {
                                            if (!com.github.event.bukkit.BlockPlace.shouldBePlaced(p.getItemInHand().getType())) {
                                                event.setUseItemInHand(org.bukkit.event.Event.Result.DENY);
                                            }
                                            com.github.aspect.ZAPlayer zap = data.getZAPlayer(p);
                                            com.github.aspect.Game game = zap.getGame();
                                            if ((b.getType() == org.bukkit.Material.ENDER_PORTAL_FRAME) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                                            } else if ((b.getType() == org.bukkit.Material.CHEST) && (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK)) {
                                                org.bukkit.Location l = b.getLocation();
                                                if (data.isMysteryChest(l)) {
                                                    com.github.aspect.MysteryChest mc = data.getMysteryChest(l);
                                                    if ((mc != null) && mc.isActive()) {
                                                        mc.giveRandomItem(zap);
                                                    } else {
                                                        p.sendMessage(org.bukkit.ChatColor.RED + "That chest is not active!");
                                                        event.setCancelled(true);
                                                        return;
                                                    }
                                                }
                                            } else if ((b.getType() == org.bukkit.Material.WOOD_DOOR) || (b.getType() == org.bukkit.Material.IRON_DOOR)) {
                                                event.setCancelled(true);
                                            } else if ((b.getType() == org.bukkit.Material.FENCE) && (a == org.bukkit.event.block.Action.LEFT_CLICK_BLOCK)) {
                                                // through-fence damage
                                                short damage = p.getItemInHand().getDurability();
                                                zap.shoot(3, 1, damage, false, true);
                                            } else if (a == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
                                                if (com.github.utility.MiscUtil.locationMatch(b.getLocation(), game.getMainframe().getLocation(), 2)) {
                                                    // mainframe
                                                    com.github.aspect.Mainframe frame = game.getMainframe();
                                                    if (com.github.event.bukkit.PlayerInteract.mainframeLinkers.containsKey(zap)) {
                                                        com.github.threading.inherent.TeleporterLinkageTimerThread tltt = com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.get(zap);
                                                        if (tltt.canBeLinked()) {
                                                            p.sendMessage(org.bukkit.ChatColor.GREEN + "Teleporter linked!");
                                                            tltt.setLinked(true);
                                                            frame.link(com.github.event.bukkit.PlayerInteract.mainframeLinkers.get(zap));
                                                        }
                                                        com.github.event.bukkit.PlayerInteract.mainframeLinkers.remove(zap);
                                                        com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.remove(zap);
                                                    }
                                                } else if (b.getType() == org.bukkit.Material.ENDER_PORTAL_FRAME) {
                                                    // not mainframe
                                                    int time = -1;
                                                    boolean requiresLinkage = true;
                                                    org.bukkit.Location below = loc.clone().subtract(0, 1, 0);
                                                    if (below.getBlock().getState() instanceof org.bukkit.block.Sign) {
                                                        org.bukkit.block.Sign sign = ((org.bukkit.block.Sign) (below.getBlock().getState()));
                                                        try {
                                                            requiresLinkage = java.lang.Boolean.parseBoolean(sign.getLine(0));
                                                            time = java.lang.Integer.parseInt(sign.getLine(1));
                                                        } catch (java.lang.Exception e) {
                                                            // nothing
                                                        }
                                                    }
                                                    if (game.getMainframe().isLinked(b.getLocation()) || (!requiresLinkage)) {
                                                        // this teleporter linked to the mainframe...
                                                        if (!zap.isTeleporting()) {
                                                            p.sendMessage(org.bukkit.ChatColor.GRAY + "Teleportation sequence started...");
                                                            new com.github.threading.inherent.TeleportThread(zap, ((java.lang.Integer) (Setting.TELEPORT_TIME.getSetting())), true, 20);
                                                            return;
                                                        } else {
                                                            p.sendMessage(org.bukkit.ChatColor.GRAY + "You are already teleporting!");
                                                            return;
                                                        }
                                                    } else {
                                                        // this teleporter is not linked to the mainframe...
                                                        time = (time == (-1)) ? ((int) (loc.distanceSquared(game.getMainframe().getLocation()) * 4.5)) : time;// 1 second per block difference (4.5 approx sqrt 20)

                                                        com.github.event.bukkit.PlayerInteract.mainframeLinkers.put(zap, loc);
                                                        com.github.event.bukkit.PlayerInteract.mainframeLinkers_Timers.put(zap, new com.github.threading.inherent.TeleporterLinkageTimerThread(game.getMainframe(), zap, time));// difference

                                                        p.sendMessage(((org.bukkit.ChatColor.GRAY + "You now have ") + (time / 20)) + " seconds to link the teleporter to the mainframe!");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}