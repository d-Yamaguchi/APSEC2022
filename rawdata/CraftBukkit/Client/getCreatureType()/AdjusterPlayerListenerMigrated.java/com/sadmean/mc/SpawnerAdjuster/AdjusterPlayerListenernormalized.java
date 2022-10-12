public static void setSpawnType(org.bukkit.block.CreatureSpawner spawner, org.bukkit.entity.Player player) {
    int i = -1;
    org.bukkit.entity.CreatureType _CVAR0 = org.bukkit.entity.CreatureType.CAVE_SPIDER;
    boolean _CVAR1 = spawner.getCreatureType() == _CVAR0;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.CaveSpider")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 0;
    }
    org.bukkit.entity.CreatureType _CVAR2 = org.bukkit.entity.CreatureType.SPIDER;
    boolean _CVAR3 = spawner.getCreatureType() == _CVAR2;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Spider")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 1;
    }
    org.bukkit.entity.CreatureType _CVAR4 = org.bukkit.entity.CreatureType.CREEPER;
    boolean _CVAR5 = spawner.getCreatureType() == _CVAR4;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Creeper")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 1;
        i = 2;
    }
    org.bukkit.entity.CreatureType _CVAR6 = org.bukkit.entity.CreatureType.SILVERFISH;
    boolean _CVAR7 = spawner.getCreatureType() == _CVAR6;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Silverfish")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 3;
    }
    org.bukkit.entity.CreatureType _CVAR8 = org.bukkit.entity.CreatureType.SKELETON;
    boolean _CVAR9 = spawner.getCreatureType() == _CVAR8;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Skeleton")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 4;
    }
    org.bukkit.entity.CreatureType _CVAR10 = org.bukkit.entity.CreatureType.ZOMBIE;
    boolean _CVAR11 = spawner.getCreatureType() == _CVAR10;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Zombie")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 5;
    }
    org.bukkit.entity.CreatureType _CVAR12 = org.bukkit.entity.CreatureType.SLIME;
    boolean _CVAR13 = spawner.getCreatureType() == _CVAR12;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Slime")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 6;
    }
    org.bukkit.entity.CreatureType _CVAR14 = org.bukkit.entity.CreatureType.ENDERMAN;
    boolean _CVAR15 = spawner.getCreatureType() == _CVAR14;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Enderman")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 7;
    }
    org.bukkit.entity.CreatureType _CVAR16 = org.bukkit.entity.CreatureType.ENDER_DRAGON;
    boolean _CVAR17 = spawner.getCreatureType() == _CVAR16;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Boss.EnderDragon")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 8;
    }
    org.bukkit.entity.CreatureType _CVAR18 = org.bukkit.entity.CreatureType.MAGMA_CUBE;
    boolean _CVAR19 = spawner.getCreatureType() == _CVAR18;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.MagmaCube")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 9;
    }
    org.bukkit.entity.CreatureType _CVAR20 = org.bukkit.entity.CreatureType.BLAZE;
    boolean _CVAR21 = spawner.getCreatureType() == _CVAR20;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.Blaze")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 10;
    }
    org.bukkit.entity.CreatureType _CVAR22 = org.bukkit.entity.CreatureType.GHAST;
    boolean _CVAR23 = spawner.getCreatureType() == _CVAR22;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.Ghast")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 11;
    }
    org.bukkit.entity.CreatureType _CVAR24 = org.bukkit.entity.CreatureType.PIG_ZOMBIE;
    boolean _CVAR25 = spawner.getCreatureType() == _CVAR24;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.PigZombie")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 12;
    }
    org.bukkit.entity.CreatureType _CVAR26 = org.bukkit.entity.CreatureType.PIG;
    boolean _CVAR27 = spawner.getCreatureType() == _CVAR26;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Pig")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 13;
    }
    org.bukkit.entity.CreatureType _CVAR28 = org.bukkit.entity.CreatureType.COW;
    boolean _CVAR29 = spawner.getCreatureType() == _CVAR28;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Cow")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 14;
    }
    org.bukkit.entity.CreatureType _CVAR30 = org.bukkit.entity.CreatureType.SHEEP;
    boolean _CVAR31 = spawner.getCreatureType() == _CVAR30;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Sheep")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 15;
    }
    org.bukkit.entity.CreatureType _CVAR32 = org.bukkit.entity.CreatureType.WOLF;
    boolean _CVAR33 = spawner.getCreatureType() == _CVAR32;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Wolf")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 16;
    }
    org.bukkit.entity.CreatureType _CVAR34 = org.bukkit.entity.CreatureType.CHICKEN;
    boolean _CVAR35 = spawner.getCreatureType() == _CVAR34;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Chicken")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 17;
    }
    org.bukkit.entity.CreatureType _CVAR36 = org.bukkit.entity.CreatureType.MUSHROOM_COW;
    boolean _CVAR37 = spawner.getCreatureType() == _CVAR36;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Mooshroom")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 18;
    }
    org.bukkit.entity.CreatureType _CVAR38 = org.bukkit.entity.CreatureType.SNOWMAN;
    boolean _CVAR39 = spawner.getCreatureType() == _CVAR38;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Utility.SnowGolem")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 19;
    }
    org.bukkit.entity.CreatureType _CVAR40 = org.bukkit.entity.CreatureType.VILLAGER;
    boolean _CVAR41 = spawner.getCreatureType() == _CVAR40;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Villager")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 20;
    }
    org.bukkit.entity.CreatureType _CVAR42 = org.bukkit.entity.CreatureType.GIANT;
    boolean _CVAR43 = spawner.getCreatureType() == _CVAR42;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Unused.Giant")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 21;
    }
     _CVAR44 = org.bukkit.entity.CreatureType.OCELOT;
     _CVAR45 = spawner.getCreatureType() == _CVAR44;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Ocelot")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 22;
    }
     _CVAR46 = org.bukkit.entity.CreatureType.IRON_GOLEM;
     _CVAR47 = spawner.getCreatureType() == _CVAR46;
    if () {
        if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Utility.IronGolem")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner) {
            return;
        }
        i = 23;
    }
    // if i is still -1, then we have an unknown mob type. We should not play with that spawner
    if (i == (-1)) {
        com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.log_It("warning", "unkown mob type error");
        return;
    }
    int initalvalue = i;
    // i--;
    int b = 0;
    while ((i != initalvalue) || (b == 0)) {
        if (i >= 24) {
            i = 0;
        }
        if (((i == 0) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Spider")) && SpawnerAdjuster.allowSpider) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.SPIDER);
            return;
        }
        if (((i == 1) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Creeper")) && SpawnerAdjuster.allowCreeper) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.CREEPER);
            return;
        }
        if (((i == 2) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Silverfish")) && SpawnerAdjuster.allowSilverfish) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.SILVERFISH);
            return;
        }
        if (((i == 3) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Skeleton")) && SpawnerAdjuster.allowSkeleton) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.SKELETON);
            return;
        }
        if (((i == 4) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Zombie")) && SpawnerAdjuster.allowZombie) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.ZOMBIE);
            return;
        }
        if (((i == 5) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Slime")) && SpawnerAdjuster.allowSlime) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.SLIME);
            return;
        }
        if (((i == 6) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Enderman")) && SpawnerAdjuster.allowEnderman) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.ENDERMAN);
            return;
        }
        if (((i == 7) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Boss.EnderDragon")) && SpawnerAdjuster.allowEnderDragon) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.ENDER_DRAGON);
            return;
        }
        if (((i == 8) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.MagmaCube")) && SpawnerAdjuster.allowMagmaCube) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.MAGMA_CUBE);
            return;
        }
        if (((i == 9) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.Blaze")) && SpawnerAdjuster.allowBlaze) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.BLAZE);
            return;
        }
        if (((i == 10) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.Ghast")) && SpawnerAdjuster.allowGhast) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.GHAST);
            return;
        }
        if (((i == 11) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.PigZombie")) && SpawnerAdjuster.allowPigZombie) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.PIG_ZOMBIE);
            return;
        }
        if (((i == 12) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Pig")) && SpawnerAdjuster.allowPig) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.PIG);
            return;
        }
        if (((i == 13) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Cow")) && SpawnerAdjuster.allowCow) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.COW);
            return;
        }
        if (((i == 14) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Sheep")) && SpawnerAdjuster.allowSheep) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.SHEEP);
            return;
        }
        if (((i == 15) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Wolf")) && SpawnerAdjuster.allowWolf) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.WOLF);
            return;
        }
        if (((i == 16) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Chicken")) && SpawnerAdjuster.allowChicken) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.CHICKEN);
            return;
        }
        if (((i == 17) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Mooshroom")) && SpawnerAdjuster.allowMooshroom) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.MUSHROOM_COW);
            return;
        }
        if (((i == 18) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Utility.SnowGolem")) && SpawnerAdjuster.allowSnowGolem) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.SNOWMAN);
            return;
        }
        if (((i == 19) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Villager")) && SpawnerAdjuster.allowVillager) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.VILLAGER);
            return;
        }
        if (((i == 20) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Unused.Giant")) && SpawnerAdjuster.allowGiant) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.GIANT);
            return;
        }
        if (((i == 21) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Ocelot")) && SpawnerAdjuster.allowOcelot) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.Ocelot);
            return;
        }
        if (((i == 22) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.IronGolem")) && SpawnerAdjuster.allowIronGolem) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.Iron_Golem);
            return;
        }
        if (((i == 23) && com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.CaveSpider")) && SpawnerAdjuster.allowCaveSpider) {
            spawner.setCreatureType(org.bukkit.entity.CreatureType.CAVE_SPIDER);
            return;
        }
        b++;
        i++;
        if (b > 30) {
            // infinite loop protection.
            return;
        }
    } 
}