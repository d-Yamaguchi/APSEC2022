package com.sadmean.mc.SpawnerAdjuster;
public class AdjusterPlayerListener implements org.bukkit.event.Listener {
    public static com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster plugin;

    public AdjusterPlayerListener(com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster instance) {
        com.sadmean.mc.SpawnerAdjuster.AdjusterPlayerListener.plugin = instance;
    }

    @org.bukkit.event.EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        if ((event.getClickedBlock() == null) || (!SpawnerAdjuster.usePlayerListener))
            return;

        if (event.getClickedBlock().getType() == org.bukkit.Material.MOB_SPAWNER) {
            if (com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(event.getPlayer(), "SpawnerAdjuster.ChangeSpawnType")) {
                if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) || (event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_AIR)) {
                    java.lang.String name = spawner.getSpawnedType().getName();
                    if (SpawnerAdjuster.opsChangeSpawnTypeOnly) {
                        if (event.getPlayer().isOp()) {
                            com.sadmean.mc.SpawnerAdjuster.AdjusterPlayerListener.setSpawnType(spawner, event.getPlayer());
                        }
                    } else {
                        com.sadmean.mc.SpawnerAdjuster.AdjusterPlayerListener.setSpawnType(spawner, event.getPlayer());
                    }
                    org.bukkit.block.CreatureSpawner spawner = ((org.bukkit.block.CreatureSpawner) (event.getClickedBlock().getState()));
                    java.lang.String newName = spawner.getSpawnedType().getName();
                    event.getPlayer().sendMessage(((((((SpawnerAdjuster.chatPrefix + "Spawner was: ") + org.bukkit.ChatColor.GREEN) + name) + org.bukkit.ChatColor.GRAY) + " is now: ") + org.bukkit.ChatColor.GREEN) + newName);
                    event.setCancelled(true);// maybe prevent block placement?

                    spawner.setDelay(200);
                }
            }
        }
    }

    public static void setSpawnType(org.bukkit.block.CreatureSpawner spawner, org.bukkit.entity.Player player) {
        int i = -1;
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.CAVE_SPIDER) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.CaveSpider")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 0;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.SPIDER) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Spider")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 1;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.CREEPER) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Creeper")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 1;
            i = 2;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.SILVERFISH) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Silverfish")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 3;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.SKELETON) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Skeleton")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 4;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.ZOMBIE) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Zombie")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 5;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.SLIME) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostile.Slime")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 6;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.ENDERMAN) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Hostle.Enderman")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 7;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.ENDER_DRAGON) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Boss.EnderDragon")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 8;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.MAGMA_CUBE) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.MagmaCube")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 9;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.BLAZE) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.Blaze")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 10;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.GHAST) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.Ghast")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 11;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.PIG_ZOMBIE) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Nether.PigZombie")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 12;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.PIG) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Pig")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 13;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.COW) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Cow")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 14;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.SHEEP) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Sheep")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 15;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.WOLF) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Wolf")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 16;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.CHICKEN) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Chicken")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 17;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.MUSHROOM_COW) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Mooshroom")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 18;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.SNOWMAN) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Utility.SnowGolem")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 19;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.VILLAGER) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Villager")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 20;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.GIANT) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Unused.Giant")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 21;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.OCELOT) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Neutral.Ocelot")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

            i = 22;
        }
        if (spawner.getCreatureType() == org.bukkit.entity.CreatureType.IRON_GOLEM) {
            if ((!com.sadmean.mc.SpawnerAdjuster.SpawnerAdjuster.permCheck(player, "SpawnerAdjuster.SetMobs.Utility.IronGolem")) && SpawnerAdjuster.mustHaveValidPermissionsToAlterSpawner)
                return;

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
            if (i >= 24)
                i = 0;

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
}