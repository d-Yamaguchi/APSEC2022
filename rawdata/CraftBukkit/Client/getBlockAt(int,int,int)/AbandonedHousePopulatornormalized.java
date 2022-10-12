private void buildHouse(org.bukkit.World world, java.util.Random rand, int realX, int realZ, int door) {
    int houseX = rand.nextInt(4) + 4;
    int houseZ = rand.nextInt(4) + 4;
    int realY = world.getHighestBlockYAt(realX, realZ) - 1;
    int midX;
    if ((houseX % 2) == 0) {
        midX = houseX / 2;
    } else {
        midX = (houseX - 1) / 2;
    }
    int midZ;
    if ((houseZ % 2) == 0) {
        midZ = houseZ / 2;
    } else {
        midZ = (houseZ - 1) / 2;
    }
    boolean chest = false;
    for (int i = 0; i <= houseX; i++) {
        int o = 0;
        for (int o = 0; o <= houseZ; o++) {
            org.bukkit.World _CVAR3 = world;
            int _CVAR4 = realX;
            int _CVAR5 = realZ;
            int top = _CVAR3.getHighestBlockYAt(_CVAR4, _CVAR5);
            int p = top;
            org.bukkit.World _CVAR10 = world;
            int _CVAR11 = realX;
            int _CVAR12 = realZ;
            org.bukkit.block.Block _CVAR13 = _CVAR10.getHighestBlockAt(_CVAR11, _CVAR12);
            org.bukkit.Material m = _CVAR13.getType();
            for (int p = top; p < realY; p++) {
                int _CVAR1 = i;
                int _CVAR7 = o;
                org.bukkit.World _CVAR0 = world;
                int _CVAR2 = realX + _CVAR1;
                int _CVAR6 = p;
                int _CVAR8 = realZ + _CVAR7;
                org.bukkit.block.Block _CVAR9 = _CVAR0.getBlockAt(_CVAR2, _CVAR6, _CVAR8);
                org.bukkit.Material _CVAR14 = m;
                _CVAR9.setType(_CVAR14);
            }
        }
    }
    int i = 0;
    for (int i = 0; i <= houseX; i++) {
        int o = 0;
        for (int o = 0; o <= houseZ; o++) {
            int p = 0;
            for (int p = 0; p < 5; p++) {
                if (rand.nextInt(10) == 0) {
                    continue;
                }
                boolean isWall = (((i == 0) || (o == 0)) || (i == houseX)) || (o == houseZ);
                boolean isCorner = ((((i == 0) && (o == 0)) || ((i == 0) && (o == houseZ))) || ((i == houseX) && (o == 0))) || ((i == houseX) && (o == houseZ));
                boolean isDoorSpot = (((((door == 0) && (i == midX)) && (o == 0)) || (((door == 1) && (i == midX)) && (o == houseZ))) || (((door == 2) && (i == 0)) && (o == midZ))) || (((door == 3) && (i == houseX)) && (o == 0));
                if (p == 0) {
                    if (isWall && (!isDoorSpot)) {
                        int _CVAR16 = i;
                        int _CVAR18 = p;
                        int _CVAR20 = o;
                        org.bukkit.World _CVAR15 = world;
                        int _CVAR17 = realX + _CVAR16;
                        int _CVAR19 = realY + _CVAR18;
                        int _CVAR21 = realZ + _CVAR20;
                        org.bukkit.block.Block _CVAR22 = _CVAR15.getBlockAt(_CVAR17, _CVAR19, _CVAR21);
                        org.bukkit.Material _CVAR23 = org.bukkit.Material.COBBLESTONE;
                        _CVAR22.setType(_CVAR23);
                    } else if (rand.nextBoolean()) {
                        int _CVAR25 = i;
                        int _CVAR27 = p;
                        int _CVAR29 = o;
                        org.bukkit.World _CVAR24 = world;
                        int _CVAR26 = realX + _CVAR25;
                        int _CVAR28 = realY + _CVAR27;
                        int _CVAR30 = realZ + _CVAR29;
                        org.bukkit.block.Block _CVAR31 = _CVAR24.getBlockAt(_CVAR26, _CVAR28, _CVAR30);
                        org.bukkit.Material _CVAR32 = org.bukkit.Material.WOOD;
                        _CVAR31.setType(_CVAR32);
                    } else // noinspection deprecation,deprecation
                    {
                        int _CVAR34 = i;
                        int _CVAR36 = p;
                        int _CVAR38 = o;
                        org.bukkit.World _CVAR33 = world;
                        int _CVAR35 = realX + _CVAR34;
                        int _CVAR37 = realY + _CVAR36;
                        int _CVAR39 = realZ + _CVAR38;
                        org.bukkit.Material _CVAR41 = org.bukkit.Material.WOOD;
                        org.bukkit.block.Block _CVAR40 = _CVAR33.getBlockAt(_CVAR35, _CVAR37, _CVAR39);
                        int _CVAR42 = _CVAR41.getId();
                        int _CVAR43 = ((byte) (3));
                        boolean _CVAR44 = false;
                        _CVAR40.setTypeIdAndData(_CVAR42, _CVAR43, _CVAR44);
                    }
                } else if (((p == 1) && isWall) && (!isDoorSpot)) {
                    int _CVAR46 = i;
                    int _CVAR48 = p;
                    int _CVAR50 = o;
                    org.bukkit.World _CVAR45 = world;
                    int _CVAR47 = realX + _CVAR46;
                    int _CVAR49 = realY + _CVAR48;
                    int _CVAR51 = realZ + _CVAR50;
                    org.bukkit.block.Block _CVAR52 = _CVAR45.getBlockAt(_CVAR47, _CVAR49, _CVAR51);
                    org.bukkit.Material _CVAR53 = org.bukkit.Material.COBBLESTONE;
                    _CVAR52.setType(_CVAR53);
                } else if (((p == 2) && isWall) && (!isDoorSpot)) {
                    if (isCorner) {
                        int _CVAR55 = i;
                        int _CVAR57 = p;
                        int _CVAR59 = o;
                        org.bukkit.World _CVAR54 = world;
                        int _CVAR56 = realX + _CVAR55;
                        int _CVAR58 = realY + _CVAR57;
                        int _CVAR60 = realZ + _CVAR59;
                        org.bukkit.block.Block _CVAR61 = _CVAR54.getBlockAt(_CVAR56, _CVAR58, _CVAR60);
                        org.bukkit.Material _CVAR62 = org.bukkit.Material.COBBLESTONE;
                        _CVAR61.setType(_CVAR62);
                    } else if (rand.nextBoolean()) {
                        int _CVAR64 = i;
                        int _CVAR66 = p;
                        int _CVAR68 = o;
                        org.bukkit.World _CVAR63 = world;
                        int _CVAR65 = realX + _CVAR64;
                        int _CVAR67 = realY + _CVAR66;
                        int _CVAR69 = realZ + _CVAR68;
                        org.bukkit.block.Block _CVAR70 = _CVAR63.getBlockAt(_CVAR65, _CVAR67, _CVAR69);
                        org.bukkit.Material _CVAR71 = org.bukkit.Material.WOOD;
                        _CVAR70.setType(_CVAR71);
                    } else // noinspection deprecation,deprecation
                    {
                        int _CVAR73 = i;
                        int _CVAR75 = p;
                        int _CVAR77 = o;
                        org.bukkit.World _CVAR72 = world;
                        int _CVAR74 = realX + _CVAR73;
                        int _CVAR76 = realY + _CVAR75;
                        int _CVAR78 = realZ + _CVAR77;
                        org.bukkit.Material _CVAR80 = org.bukkit.Material.WOOD;
                        org.bukkit.block.Block _CVAR79 = _CVAR72.getBlockAt(_CVAR74, _CVAR76, _CVAR78);
                        int _CVAR81 = _CVAR80.getId();
                        int _CVAR82 = ((byte) (3));
                        boolean _CVAR83 = false;
                        _CVAR79.setTypeIdAndData(_CVAR81, _CVAR82, _CVAR83);
                    }
                } else if ((p == 3) && isWall) {
                    if (isCorner) {
                        int _CVAR85 = i;
                        int _CVAR87 = p;
                        int _CVAR89 = o;
                        org.bukkit.World _CVAR84 = world;
                        int _CVAR86 = realX + _CVAR85;
                        int _CVAR88 = realY + _CVAR87;
                        int _CVAR90 = realZ + _CVAR89;
                        org.bukkit.block.Block _CVAR91 = _CVAR84.getBlockAt(_CVAR86, _CVAR88, _CVAR90);
                        org.bukkit.Material _CVAR92 = org.bukkit.Material.COBBLESTONE;
                        _CVAR91.setType(_CVAR92);
                    } else if ((((o < ((houseZ / 2) - 2)) && (o > 1)) && ((i == 0) || (i == houseX))) && (!isDoorSpot)) {
                        if (rand.nextBoolean()) {
                            int _CVAR94 = i;
                            int _CVAR96 = p;
                            int _CVAR98 = o;
                            org.bukkit.World _CVAR93 = world;
                            int _CVAR95 = realX + _CVAR94;
                            int _CVAR97 = realY + _CVAR96;
                            int _CVAR99 = realZ + _CVAR98;
                            org.bukkit.block.Block _CVAR100 = _CVAR93.getBlockAt(_CVAR95, _CVAR97, _CVAR99);
                            org.bukkit.Material _CVAR101 = org.bukkit.Material.GLASS;
                            _CVAR100.setType(_CVAR101);
                        } else {
                            int _CVAR103 = i;
                            int _CVAR105 = p;
                            int _CVAR107 = o;
                            org.bukkit.World _CVAR102 = world;
                            int _CVAR104 = realX + _CVAR103;
                            int _CVAR106 = realY + _CVAR105;
                            int _CVAR108 = realZ + _CVAR107;
                            org.bukkit.block.Block _CVAR109 = _CVAR102.getBlockAt(_CVAR104, _CVAR106, _CVAR108);
                            org.bukkit.Material _CVAR110 = org.bukkit.Material.AIR;
                            _CVAR109.setType(_CVAR110);
                        }
                    } else if (rand.nextBoolean()) {
                        int _CVAR112 = i;
                        int _CVAR114 = p;
                        int _CVAR116 = o;
                        org.bukkit.World _CVAR111 = world;
                        int _CVAR113 = realX + _CVAR112;
                        int _CVAR115 = realY + _CVAR114;
                        int _CVAR117 = realZ + _CVAR116;
                        org.bukkit.block.Block _CVAR118 = _CVAR111.getBlockAt(_CVAR113, _CVAR115, _CVAR117);
                        org.bukkit.Material _CVAR119 = org.bukkit.Material.WOOD;
                        _CVAR118.setType(_CVAR119);
                    } else // noinspection deprecation,deprecation
                    {
                        int _CVAR121 = i;
                        int _CVAR123 = p;
                        int _CVAR125 = o;
                        org.bukkit.World _CVAR120 = world;
                        int _CVAR122 = realX + _CVAR121;
                        int _CVAR124 = realY + _CVAR123;
                        int _CVAR126 = realZ + _CVAR125;
                        org.bukkit.Material _CVAR128 = org.bukkit.Material.WOOD;
                        org.bukkit.block.Block _CVAR127 = _CVAR120.getBlockAt(_CVAR122, _CVAR124, _CVAR126);
                        int _CVAR129 = _CVAR128.getId();
                        int _CVAR130 = ((byte) (3));
                        boolean _CVAR131 = false;
                        _CVAR127.setTypeIdAndData(_CVAR129, _CVAR130, _CVAR131);
                    }
                } else if (p == 4) {
                    if (i == 0) {
                        int _CVAR133 = i;
                        int _CVAR135 = p;
                        int _CVAR137 = o;
                        org.bukkit.World _CVAR132 = world;
                        int _CVAR134 = realX + _CVAR133;
                        int _CVAR136 = realY + _CVAR135;
                        int _CVAR138 = realZ + _CVAR137;
                        org.bukkit.Material _CVAR140 = org.bukkit.Material.COBBLESTONE_STAIRS;
                        org.bukkit.block.Block _CVAR139 = _CVAR132.getBlockAt(_CVAR134, _CVAR136, _CVAR138);
                        int _CVAR141 = _CVAR140.getId();
                        int _CVAR142 = ((byte) (0));
                        boolean _CVAR143 = false;
                        // noinspection deprecation,deprecation
                        _CVAR139.setTypeIdAndData(_CVAR141, _CVAR142, _CVAR143);
                    } else if (i == houseX) {
                        int _CVAR145 = i;
                        int _CVAR147 = p;
                        int _CVAR149 = o;
                        org.bukkit.World _CVAR144 = world;
                        int _CVAR146 = realX + _CVAR145;
                        int _CVAR148 = realY + _CVAR147;
                        int _CVAR150 = realZ + _CVAR149;
                        org.bukkit.Material _CVAR152 = org.bukkit.Material.COBBLESTONE_STAIRS;
                        org.bukkit.block.Block _CVAR151 = _CVAR144.getBlockAt(_CVAR146, _CVAR148, _CVAR150);
                        int _CVAR153 = _CVAR152.getId();
                        int _CVAR154 = ((byte) (1));
                        boolean _CVAR155 = false;
                        // noinspection deprecation,deprecation
                        _CVAR151.setTypeIdAndData(_CVAR153, _CVAR154, _CVAR155);
                    } else if (o == 0) {
                        int _CVAR157 = i;
                        int _CVAR159 = p;
                        int _CVAR161 = o;
                        org.bukkit.World _CVAR156 = world;
                        int _CVAR158 = realX + _CVAR157;
                        int _CVAR160 = realY + _CVAR159;
                        int _CVAR162 = realZ + _CVAR161;
                        org.bukkit.Material _CVAR164 = org.bukkit.Material.COBBLESTONE_STAIRS;
                        org.bukkit.block.Block _CVAR163 = _CVAR156.getBlockAt(_CVAR158, _CVAR160, _CVAR162);
                        int _CVAR165 = _CVAR164.getId();
                        int _CVAR166 = ((byte) (2));
                        boolean _CVAR167 = false;
                        // noinspection deprecation,deprecation
                        _CVAR163.setTypeIdAndData(_CVAR165, _CVAR166, _CVAR167);
                    } else if (o == houseZ) {
                        int _CVAR169 = i;
                        int _CVAR171 = p;
                        int _CVAR173 = o;
                        org.bukkit.World _CVAR168 = world;
                        int _CVAR170 = realX + _CVAR169;
                        int _CVAR172 = realY + _CVAR171;
                        int _CVAR174 = realZ + _CVAR173;
                        org.bukkit.Material _CVAR176 = org.bukkit.Material.COBBLESTONE_STAIRS;
                        org.bukkit.block.Block _CVAR175 = _CVAR168.getBlockAt(_CVAR170, _CVAR172, _CVAR174);
                        int _CVAR177 = _CVAR176.getId();
                        int _CVAR178 = ((byte) (3));
                        boolean _CVAR179 = false;
                        // noinspection deprecation,deprecation
                        _CVAR175.setTypeIdAndData(_CVAR177, _CVAR178, _CVAR179);
                    } else if (rand.nextBoolean()) {
                        int _CVAR181 = i;
                        int _CVAR183 = p;
                        int _CVAR185 = o;
                        org.bukkit.World _CVAR180 = world;
                        int _CVAR182 = realX + _CVAR181;
                        int _CVAR184 = realY + _CVAR183;
                        int _CVAR186 = realZ + _CVAR185;
                        org.bukkit.block.Block _CVAR187 = _CVAR180.getBlockAt(_CVAR182, _CVAR184, _CVAR186);
                        org.bukkit.Material _CVAR188 = org.bukkit.Material.WOOD;
                        _CVAR187.setType(_CVAR188);
                    } else // noinspection deprecation,deprecation
                    {
                        int _CVAR190 = i;
                        int _CVAR192 = p;
                        int _CVAR194 = o;
                        org.bukkit.World _CVAR189 = world;
                        int _CVAR191 = realX + _CVAR190;
                        int _CVAR193 = realY + _CVAR192;
                        int _CVAR195 = realZ + _CVAR194;
                        org.bukkit.Material _CVAR197 = org.bukkit.Material.WOOD;
                        org.bukkit.block.Block _CVAR196 = _CVAR189.getBlockAt(_CVAR191, _CVAR193, _CVAR195);
                        int _CVAR198 = _CVAR197.getId();
                        int _CVAR199 = ((byte) (3));
                        boolean _CVAR200 = false;
                        _CVAR196.setTypeIdAndData(_CVAR198, _CVAR199, _CVAR200);
                    }
                } else if ((p == 1) && ((((i == 1) || (o == 1)) || (i == (houseX - 1))) || (o == (houseZ - 1)))) {
                    if ((!chest) && (rand.nextInt((((houseX - 1) / 2) * (houseZ - 1)) / 2) == 0)) {
                        int _CVAR202 = i;
                        int _CVAR204 = p;
                        int _CVAR206 = o;
                        org.bukkit.World _CVAR201 = world;
                        int _CVAR203 = realX + _CVAR202;
                        int _CVAR205 = realY + _CVAR204;
                        int _CVAR207 = realZ + _CVAR206;
                        org.bukkit.block.Block _CVAR208 = _CVAR201.getBlockAt(_CVAR203, _CVAR205, _CVAR207);
                        org.bukkit.Material _CVAR209 = org.bukkit.Material.CHEST;
                        _CVAR208.setType(_CVAR209);
                        int _CVAR211 = i;
                        int _CVAR213 = p;
                        int _CVAR215 = o;
                        org.bukkit.World _CVAR210 = world;
                        int _CVAR212 = realX + _CVAR211;
                        int _CVAR214 = realY + _CVAR213;
                        int _CVAR216 = realZ + _CVAR215;
                        org.bukkit.block.Block _CVAR217 = _CVAR210.getBlockAt(_CVAR212, _CVAR214, _CVAR216);
                        org.bukkit.block.Chest c = ((org.bukkit.block.Chest) (_CVAR217.getState()));
                        org.bukkit.inventory.Inventory inv = c.getBlockInventory();
                        for (java.util.Map.Entry<net.cyberninjapiggy.apocalyptic.generator.AbandonedHousePopulator.LootTableEntry, java.lang.Integer> e : lootTable.entrySet()) {
                            if (rand.nextInt(e.getValue()) == 0) {
                                org.bukkit.inventory.ItemStack stack = null;
                                if (e.getKey().isSpecialItem()) {
                                    switch (e.getKey().getSpecialItem()) {
                                        case HAZMAT_HOOD :
                                            stack = new org.bukkit.inventory.ItemStack(plugin.getHazmatHood());
                                            break;
                                        case HAZMAT_SUIT :
                                            stack = new org.bukkit.inventory.ItemStack(plugin.getHazmatSuit());
                                            break;
                                        case HAZMAT_PANTS :
                                            stack = new org.bukkit.inventory.ItemStack(plugin.getHazmatPants());
                                            break;
                                        case HAZMAT_BOOTS :
                                            stack = new org.bukkit.inventory.ItemStack(plugin.getHazmatBoots());
                                            break;
                                    }
                                } else {
                                    stack = new org.bukkit.inventory.ItemStack(e.getKey().getMaterial());
                                }
                                plugin.getLogger().info("" + (e.getKey().getMax() - e.getKey().getMin()));
                                stack.setAmount(rand.nextInt((e.getKey().getMax() - e.getKey().getMin()) + 1) + e.getKey().getMin());
                                inv.setItem(rand.nextInt(inv.getSize() - 1), stack);
                            }
                        }
                        chest = true;
                    } else {
                        int _CVAR219 = i;
                        int _CVAR221 = p;
                        int _CVAR223 = o;
                        org.bukkit.World _CVAR218 = world;
                        int _CVAR220 = realX + _CVAR219;
                        int _CVAR222 = realY + _CVAR221;
                        int _CVAR224 = realZ + _CVAR223;
                        org.bukkit.block.Block _CVAR225 = _CVAR218.getBlockAt(_CVAR220, _CVAR222, _CVAR224);
                        org.bukkit.Material _CVAR226 = org.bukkit.Material.AIR;
                        _CVAR225.setType(_CVAR226);
                    }
                } else {
                    int _CVAR228 = i;
                    int _CVAR230 = p;
                    int _CVAR232 = o;
                    org.bukkit.World _CVAR227 = world;
                    int _CVAR229 = realX + _CVAR228;
                    int _CVAR231 = realY + _CVAR230;
                    int _CVAR233 = realZ + _CVAR232;
                    org.bukkit.block.Block _CVAR234 = _CVAR227.getBlockAt(_CVAR229, _CVAR231, _CVAR233);
                    org.bukkit.Material _CVAR235 = org.bukkit.Material.AIR;
                    _CVAR234.setType(_CVAR235);
                }
            }
        }
    }
}