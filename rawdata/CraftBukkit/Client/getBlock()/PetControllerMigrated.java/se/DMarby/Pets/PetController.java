package se.DMarby.Pets;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.event.entity.*;
import se.DMarby.Pets.pet.EntityEndermanPet;
import se.DMarby.Pets.pet.EntityHorsePet;
import se.DMarby.Pets.pet.EntityIronGolemPet;
public class PetController implements org.bukkit.event.Listener {
    private final java.util.Map<java.lang.String, se.DMarby.Pets.PetController.PlayerData> playerData = com.google.common.collect.Maps.newHashMap();

    private final org.bukkit.plugin.Plugin plugin;

    // private long timePeriod = TimeUnit.MILLISECONDS.convert(1,
    // TimeUnit.DAYS);
    public PetController(org.bukkit.plugin.Plugin plugin) {
        this.plugin = plugin;
        // if (!plugin.getConfig().isSet("player.level-up-period"))
        // plugin.getConfig().set("player.level-up-period", timePeriod);
        // timePeriod = plugin.getConfig().getLong("player.level-up-period");
    }

    /* public long getAliveTime(Player player) {
    PlayerData data = playerData.get(player.getName());
    if (data == null)
    return 0;
    return data.getAliveTime();
    }
     */
    /* private long getRemainder(long time) {
    return (timePeriod - (time % timePeriod)) * 20;
    }
     */
    private se.DMarby.Pets.PetEntity getPet(org.bukkit.entity.Entity entity) {
        return entity instanceof PetEntity ? ((PetEntity) (entity)) : null;
    }

    public boolean isActive(org.bukkit.entity.Player player) {
        return !playerData.containsKey(player.getName()) ? false : playerData.get(player.getName()).petActive;
    }

    public java.lang.String getType(org.bukkit.entity.Player player) {
        return !playerData.containsKey(player.getName()) ? null : playerData.get(player.getName()).type;
    }

    public java.lang.String getName(org.bukkit.entity.Player player) {
        return !playerData.containsKey(player.getName()) ? null : playerData.get(player.getName()).name;
    }

    public java.lang.String getItem(org.bukkit.entity.Player player) {
        return !playerData.containsKey(player.getName()) ? null : playerData.get(player.getName()).item;
    }

    // public void loadPlayer(Player player, long aliveTime, boolean enabled, String type) {
    public void loadPlayer(org.bukkit.entity.Player player, boolean enabled, java.lang.String type, java.lang.String name, java.lang.String item) {
        // PlayerData data = new PlayerData(player, aliveTime, enabled, type);
        se.DMarby.Pets.PetController.PlayerData data = new se.DMarby.Pets.PetController.PlayerData(player, enabled, type, name, item);
        playerData.put(player.getName(), data);
        /* scheduleTask(player, aliveTime);
        if (data.type != null) {
        data.spawn(data.type);
        }
         */
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onPlayerInteractEntity(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        PetEntity pet = getPet(event.getRightClicked());
        if ((pet == null) || (!(pet instanceof se.DMarby.Pets.pet.EntityHorsePet.BukkitHorsePet))) {
            return;
        }
        if (!event.getPlayer().equals(pet.getOwner())) {
            event.setCancelled(true);
        }
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onEntityInteract(org.bukkit.event.entity.EntityInteractEvent event) {
        PetEntity pet = getPet(event.getEntity());
        if (pet == null) {
            return;
        }
        if () {
            return;
        }
        event.setCancelled(true);
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onEntityChangeBlock(org.bukkit.event.entity.EntityChangeBlockEvent event) {
        PetEntity pet = getPet(event.getEntity());
        if (pet == null) {
            return;
        }
        event.setCancelled(true);
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onEntityFormBlock(org.bukkit.event.block.EntityBlockFormEvent event) {
        PetEntity pet = getPet(event.getEntity());
        if (pet == null) {
            return;
        }
        event.setCancelled(true);
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onEntityDamage(org.bukkit.event.entity.EntityDamageEvent event) {
        PetEntity pet = getPet(event.getEntity());
        if (pet == null) {
            if (event instanceof org.bukkit.event.entity.EntityDamageByEntityEvent) {
                PetEntity damager = getPet(((org.bukkit.event.entity.EntityDamageByEntityEvent) (event)).getDamager());
                if (damager != null) {
                    event.setCancelled(true);
                }
            }
            return;
        }
        event.setCancelled(true);
        if (event.getCause() == null) {
            return;
        }
        switch (event.getCause()) {
            case DROWNING :
            case LAVA :
                event.getEntity().teleport(pet.getOwner());
                break;
            case FIRE :
            case FIRE_TICK :
                event.getEntity().setFireTicks(0);
            default :
                break;
        }
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(org.bukkit.event.player.PlayerTeleportEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        if (isActive(player)) {
            se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
            if (data == null) {
                return;
            }
            org.bukkit.entity.Entity pet = data.pet;
            if (pet == null) {
                return;
            }
            pet.teleport(player.getLocation());
        }
    }

    @org.bukkit.event.EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        org.bukkit.entity.Player player = event.getEntity();
        removePet(player, false);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerRespawn(org.bukkit.event.player.PlayerRespawnEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        if (isActive(player)) {
            se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
            if (data == null) {
                return;
            }
            org.bukkit.entity.Entity pet = data.pet;
            if (pet != null) {
                pet.teleport(player.getLocation());
                return;
            }
            data.spawn(data.type, data.name, data.item);
        }
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onExplosionPrime(org.bukkit.event.entity.ExplosionPrimeEvent event) {
        PetEntity pet = getPet(event.getEntity());
        if (pet == null) {
            return;
        }
        ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity) (event.getEntity())).getHandle().getDataWatcher().watch(16, java.lang.Byte.valueOf(((byte) (-1))));
        ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity) (event.getEntity())).getHandle().getDataWatcher().watch(18, java.lang.Byte.valueOf(((byte) (0))));
        event.setCancelled(true);
    }

    @org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGHEST)
    public void onPlayerDamage(org.bukkit.event.entity.EntityDamageByEntityEvent event) {
        if (Util.removeInFight) {
            if ((event.getEntity() instanceof org.bukkit.entity.Player) && ((event.getDamager() instanceof org.bukkit.entity.Player) || (event.getDamager() instanceof org.bukkit.entity.Projectile))) {
                if (event.getDamager() instanceof org.bukkit.entity.Player) {
                    org.bukkit.entity.Player a = ((org.bukkit.entity.Player) (event.getEntity()));
                    org.bukkit.entity.Player b = ((org.bukkit.entity.Player) (event.getDamager()));
                    removePet(a, false);
                    removePet(b, false);
                } else {
                    org.bukkit.projectiles.ProjectileSource shooter = ((org.bukkit.entity.Projectile) (event.getDamager())).getShooter();
                    if (shooter instanceof org.bukkit.entity.Player) {
                        org.bukkit.entity.Player a = ((org.bukkit.entity.Player) (event.getEntity()));
                        org.bukkit.entity.Player b = ((org.bukkit.entity.Player) (shooter));
                        removePet(a, false);
                        removePet(b, false);
                    }
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onVehicleEnter(org.bukkit.event.vehicle.VehicleEnterEvent event) {
        if (Util.removeInBoat) {
            if ((event.getVehicle() instanceof org.bukkit.entity.Boat) && (event.getEntered() instanceof org.bukkit.entity.Player)) {
                removePet(((org.bukkit.entity.Player) (event.getEntered())), false);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onVehicleExit(org.bukkit.event.vehicle.VehicleExitEvent event) {
        if (Util.removeInBoat) {
            if ((event.getVehicle() instanceof org.bukkit.entity.Boat) && (event.getExited() instanceof org.bukkit.entity.Player)) {
                org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (event.getExited()));
                if (isActive(player)) {
                    se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
                    if (data == null) {
                        return;
                    }
                    org.bukkit.entity.Entity pet = data.pet;
                    if (pet != null) {
                        pet.teleport(player.getLocation());
                        return;
                    }
                    data.spawn(data.type, data.name, data.item);
                }
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onVehicleDestroy(org.bukkit.event.vehicle.VehicleDestroyEvent event) {
        if (Util.removeInBoat) {
            if ((event.getVehicle() instanceof org.bukkit.entity.Boat) && (event.getVehicle().getPassenger() instanceof org.bukkit.entity.Player)) {
                org.bukkit.entity.Player player = ((org.bukkit.entity.Player) (event.getVehicle().getPassenger()));
                if (isActive(player)) {
                    se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
                    if (data == null) {
                        return;
                    }
                    org.bukkit.entity.Entity pet = data.pet;
                    if (pet != null) {
                        pet.teleport(player.getLocation());
                        return;
                    }
                    data.spawn(data.type, data.name, data.item);
                }
            }
        }
    }

    public void removePet(org.bukkit.entity.Player player, boolean both) {
        se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
        if (data == null) {
            return;
        }
        // data.removePetAndTask(both);
        data.removeThePet();
        if (both) {
            playerData.remove(player.getName());
        }
    }

    /* private void scheduleTask(Player player, long aliveTime) {
    long ticks = getRemainder(aliveTime);
    new CreationTask(player).schedule(ticks);
    }
     */
    public void togglePet(org.bukkit.entity.Player player, java.lang.String type) {
        se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
        if (data == null) {
            return;
        }
        data.toggle(type);
    }

    public void togglePet(org.bukkit.entity.Player player) {
        se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
        if (data == null) {
            return;
        }
        data.toggle();
    }

    public void setName(org.bukkit.entity.Player player, java.lang.String name) {
        se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
        if (data == null) {
            return;
        }
        data.setName(name);
    }

    public void setItem(org.bukkit.entity.Player player, java.lang.String item) {
        se.DMarby.Pets.PetController.PlayerData data = playerData.get(player.getName());
        if (data == null) {
            return;
        }
        data.setItem(item);
    }

    /* private class CreationTask implements Runnable {
    private final Player player;
    private final long startTime = System.currentTimeMillis();
    private int taskId;

    CreationTask(Player player) {
    this.player = player;
    }

    void cancel() {
    Bukkit.getScheduler().cancelTask(taskId);
    }

    long getRunDuration() {
    return System.currentTimeMillis() - startTime;
    }

    @Override
    public void run() {
    PlayerData data = playerData.get(player.getName());
    data.aliveTime = data.aliveTime + getRunDuration();
    data.upgradeSlime();
    scheduleTask(player, 0);
    }

    void schedule(long ticks) {
    taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, ticks);
    playerData.get(player.getName()).task = this;
    }
    }
     */
    /* public void upgradeSlime() {
    if (!petActive)
    return;
    if (pet == null)
    spawnAtLevel(Math.max(getLevel(), 1));
    getPet(pet).upgrade();
    }
     */
    private class PlayerData {
        // long aliveTime;
        org.bukkit.entity.Entity pet;

        boolean petActive;

        org.bukkit.entity.Player player;

        java.lang.String type;

        java.lang.String name;

        java.lang.String item;

        // CreationTask task;
        // public PlayerData(Player player, long aliveTime, boolean enabled, String type) {
        public PlayerData(org.bukkit.entity.Player player, boolean enabled, java.lang.String type, java.lang.String name, java.lang.String item) {
            // this.aliveTime = aliveTime;
            this.player = player;
            this.type = type;
            this.name = name;
            this.item = item;
            petActive = enabled;
            if (petActive && (type != null)) {
                // long days = aliveTime / timePeriod;
                // if (days > 0)
                // spawnAtLevel((int) days);
                spawn(type, name, item);
            }
        }

        private void spawn(java.lang.String type, java.lang.String name, java.lang.String item) {
            org.bukkit.entity.Entity entity = Util.spawnPet(player, type);
            pet = entity;
            if (pet instanceof org.bukkit.entity.LivingEntity) {
                setName(name);
                java.lang.String timestamp = new java.text.SimpleDateFormat("MMdd").format(java.util.Calendar.getInstance().getTime());
                if ((!timestamp.equalsIgnoreCase("1225")) && (!timestamp.equalsIgnoreCase("1224"))) {
                    setItem(item);
                }
            }
        }

        /* private void spawnAtLevel(int level) {
        //  if (level <= 0)
        //      return;
        Entity entity = Util.spawnPet(player);
        getPet(entity).setLevel(1);
        pet = entity;
        }
         */
        /* public long getAliveTime() {
        return aliveTime + (task == null ? 0 : task.getRunDuration());
        }

        private int getLevel() {
        return (int) (getAliveTime() / timePeriod);
        }
         */
        void removeThePet() {
            if (pet != null) {
                pet.remove();
                pet = null;
            }
        }

        /* void removePetAndTask(boolean both) {
        if (pet != null) {
        pet.remove();
        pet = null;
        }

        if (both && task != null) {
        task.cancel();
        task = null;
        }
        }
         */
        public void toggle(java.lang.String type) {
            // petActive = !petActive;
            petActive = true;
            this.type = type;
            if (pet != null) {
                removePet(player, false);
            }
            spawn(type, name, item);
            player.sendMessage(((org.bukkit.ChatColor.GREEN + type.substring(0, 1).toUpperCase()) + type.substring(1)) + " spawned.");
        }

        public void toggle() {
            petActive = !petActive;
            if (pet == null) {
                if (this.type == null) {
                    return;
                }
                spawn(this.type, name, item);
                player.sendMessage(((org.bukkit.ChatColor.GREEN + type.substring(0, 1).toUpperCase()) + type.substring(1)) + " spawned.");
            } else {
                removePet(player, false);
                player.sendMessage(org.bukkit.ChatColor.GREEN + "Pet toggled off.");
            }
        }

        public void setName(java.lang.String name) {
            if (pet == null) {
                return;
            }
            if (name != null) {
                this.name = name;
                if (pet instanceof org.bukkit.entity.LivingEntity) {
                    ((org.bukkit.entity.LivingEntity) (pet)).setCustomName(name);
                    ((org.bukkit.entity.LivingEntity) (pet)).setCustomNameVisible(true);
                }
            } else {
                this.name = null;
                if (pet instanceof org.bukkit.entity.LivingEntity) {
                    ((org.bukkit.entity.LivingEntity) (pet)).setCustomName(null);
                    ((org.bukkit.entity.LivingEntity) (pet)).setCustomNameVisible(false);
                }
            }
        }

        public void setItem(java.lang.String item) {
            if (pet == null) {
                return;
            }
            if (item != null) {
                org.bukkit.Material mat;
                if (Util.isInt(item)) {
                    mat = org.bukkit.Material.getMaterial(java.lang.Integer.parseInt(item));
                } else {
                    mat = org.bukkit.Material.getMaterial(item.toUpperCase());
                }
                if (mat != null) {
                    this.item = item;
                    net.minecraft.server.v1_7_R4.Entity craftPet = ((org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity) (pet)).getHandle();
                    if (craftPet instanceof se.DMarby.Pets.pet.EntityIronGolemPet) {
                        if (mat.equals(org.bukkit.Material.RED_ROSE)) {
                            ((se.DMarby.Pets.pet.EntityIronGolemPet) (craftPet)).setCarryFlower(true);
                        } else if (((se.DMarby.Pets.pet.EntityIronGolemPet) (craftPet)).getCarryFlower()) {
                            removeThePet();
                            spawn(this.type, this.name, this.item);
                        }
                        return;
                    }
                    if (craftPet instanceof se.DMarby.Pets.pet.EntityEndermanPet) {
                        org.bukkit.material.MaterialData materialData = new org.bukkit.material.MaterialData(mat);
                        ((se.DMarby.Pets.pet.EntityEndermanPet) (craftPet)).setCarried(org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers.getBlock(materialData.getItemTypeId()));
                        ((se.DMarby.Pets.pet.EntityEndermanPet) (craftPet)).setCarriedData(materialData.getData());
                        return;
                    }
                    if (pet instanceof org.bukkit.entity.LivingEntity) {
                        ((org.bukkit.entity.LivingEntity) (pet)).getEquipment().setItemInHand(new org.bukkit.inventory.ItemStack(mat));
                        ((org.bukkit.entity.LivingEntity) (pet)).getEquipment().setItemInHandDropChance(0);
                    }
                    return;
                }
            }
            this.item = null;
            if (pet instanceof org.bukkit.entity.LivingEntity) {
                ((org.bukkit.entity.LivingEntity) (pet)).getEquipment().clear();
            }
        }
    }
}