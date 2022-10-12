@org.bukkit.event.EventHandler
public void onBlockBreak(org.bukkit.event.block.BlockBreakEvent event) {
    org.bukkit.entity.Player player = event.getPlayer();
    org.bukkit.event.block.BlockBreakEvent _CVAR0 = event;
    org.bukkit.block.Block _CVAR1 = _CVAR0.getBlock();
    org.bukkit.Material _CVAR2 = _CVAR1.getType();
    boolean _CVAR3 = _CVAR2 == org.bukkit.Material.STONE;
    org.bukkit.event.block.BlockBreakEvent _CVAR8 = event;
    org.bukkit.block.Block _CVAR9 = _CVAR8.getBlock();
    org.bukkit.Material _CVAR10 = _CVAR9.getType();
    boolean _CVAR11 = _CVAR10 == org.bukkit.Material.COBBLESTONE;
    boolean _CVAR4 = _CVAR3 || _CVAR11;
     _CVAR5 = _CVAR4 && com.acyrid.SunSteel.utils.SSMechanics.hasSSPick(player);
     _CVAR6 = _CVAR5 && com.acyrid.SunSteel.utils.SSPermissions.allowedPick(player);
     _CVAR7 = _CVAR6 && com.acyrid.SunSteel.utils.SSMechanics.smeltChance();
    if () {
        smeltBlock(event, 1);
    } else {
        org.bukkit.event.block.BlockBreakEvent _CVAR12 = event;
        org.bukkit.block.Block _CVAR13 = _CVAR12.getBlock();
        org.bukkit.Material _CVAR14 = _CVAR13.getType();
        boolean _CVAR15 = _CVAR14 == org.bukkit.Material.SAND;
         _CVAR16 = _CVAR15 && com.acyrid.SunSteel.utils.SSMechanics.hasSSShovel(player);
         _CVAR17 = _CVAR16 && com.acyrid.SunSteel.utils.SSPermissions.allowedPick(player);
         _CVAR18 = _CVAR17 && com.acyrid.SunSteel.utils.SSMechanics.smeltChance();
        if () {
            smeltBlock(event, 20);
        } else {
            org.bukkit.event.block.BlockBreakEvent _CVAR19 = event;
            org.bukkit.block.Block _CVAR20 = _CVAR19.getBlock();
            org.bukkit.Material _CVAR21 = _CVAR20.getType();
            boolean _CVAR22 = _CVAR21 == org.bukkit.Material.LOG;
             _CVAR23 = _CVAR22 && com.acyrid.SunSteel.utils.SSMechanics.hasSSAxe(player);
             _CVAR24 = _CVAR23 && com.acyrid.SunSteel.utils.SSPermissions.allowedAxe(player);
             _CVAR25 = _CVAR24 && com.acyrid.SunSteel.utils.SSMechanics.smeltChance();
            if () {
                smeltBlock(event, 263);
            } else {
                org.bukkit.event.block.BlockBreakEvent _CVAR26 = event;
                org.bukkit.block.Block _CVAR27 = _CVAR26.getBlock();
                org.bukkit.Material _CVAR28 = _CVAR27.getType();
                boolean _CVAR29 = _CVAR28 == org.bukkit.Material.IRON_ORE;
                 _CVAR30 = _CVAR29 && com.acyrid.SunSteel.utils.SSMechanics.hasSSPick(player);
                 _CVAR31 = _CVAR30 && com.acyrid.SunSteel.utils.SSPermissions.allowedPick(player);
                 _CVAR32 = _CVAR31 && com.acyrid.SunSteel.utils.SSMechanics.smeltChance();
                if () {
                    smeltBlock(event, 265);
                } else {
                    org.bukkit.event.block.BlockBreakEvent _CVAR33 = event;
                    org.bukkit.block.Block _CVAR34 = _CVAR33.getBlock();
                    org.bukkit.Material _CVAR35 = _CVAR34.getType();
                    boolean _CVAR36 = _CVAR35 == org.bukkit.Material.GOLD_ORE;
                     _CVAR37 = _CVAR36 && com.acyrid.SunSteel.utils.SSMechanics.hasSSPick(player);
                     _CVAR38 = _CVAR37 && com.acyrid.SunSteel.utils.SSPermissions.allowedPick(player);
                     _CVAR39 = _CVAR38 && com.acyrid.SunSteel.utils.SSMechanics.smeltChance();
                    if () {
                        smeltBlock(event, 266);
                    } else {
                        org.bukkit.event.block.BlockBreakEvent _CVAR40 = event;
                        org.bukkit.block.Block _CVAR41 = _CVAR40.getBlock();
                        org.bukkit.Material _CVAR42 = _CVAR41.getType();
                        boolean _CVAR43 = _CVAR42 == org.bukkit.Material.CLAY;
                         _CVAR44 = _CVAR43 && com.acyrid.SunSteel.utils.SSMechanics.hasSSShovel(player);
                         _CVAR45 = _CVAR44 && com.acyrid.SunSteel.utils.SSPermissions.allowedShovel(player);
                         _CVAR46 = _CVAR45 && com.acyrid.SunSteel.utils.SSMechanics.smeltChance();
                        if () {
                            smeltBlock(event, 336);
                        }
                    }
                }
            }
        }
    }
}