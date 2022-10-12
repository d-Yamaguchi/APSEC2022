package de.tobiyas.racesandclasses.playermanagement.player;
public abstract class PlayerProxy implements org.bukkit.entity.Player {
    public PlayerProxy() {
    }

    /**
     * Returns the Player
     */
    public abstract org.bukkit.entity.Player getRealPlayer();

    @java.lang.Override
    public java.lang.String getName() {
        return getRealPlayer().getName();
    }

    @java.lang.Override
    public org.bukkit.inventory.PlayerInventory getInventory() {
        return getRealPlayer().getInventory();
    }

    @java.lang.Override
    public org.bukkit.inventory.Inventory getEnderChest() {
        return getRealPlayer().getEnderChest();
    }

    @java.lang.Override
    public boolean setWindowProperty(org.bukkit.inventory.InventoryView.Property prop, int value) {
        return getRealPlayer().setWindowProperty(prop, value);
    }

    @java.lang.Override
    public org.bukkit.inventory.InventoryView getOpenInventory() {
        return getRealPlayer().getOpenInventory();
    }

    @java.lang.Override
    public org.bukkit.inventory.InventoryView openInventory(org.bukkit.inventory.Inventory inventory) {
        return getRealPlayer().openInventory(inventory);
    }

    @java.lang.Override
    public org.bukkit.inventory.InventoryView openWorkbench(org.bukkit.Location location, boolean force) {
        return getRealPlayer().openWorkbench(location, force);
    }

    @java.lang.Override
    public org.bukkit.inventory.InventoryView openEnchanting(org.bukkit.Location location, boolean force) {
        return getRealPlayer().openEnchanting(location, force);
    }

    @java.lang.Override
    public void openInventory(org.bukkit.inventory.InventoryView inventory) {
        getRealPlayer().openInventory(inventory);
    }

    @java.lang.Override
    public void closeInventory() {
        getRealPlayer().closeInventory();
    }

    @java.lang.Override
    public org.bukkit.inventory.ItemStack getItemInHand() {
        return getRealPlayer().getItemInHand();
    }

    @java.lang.Override
    public void setItemInHand(org.bukkit.inventory.ItemStack item) {
        getRealPlayer().setItemInHand(item);
    }

    @java.lang.Override
    public org.bukkit.inventory.ItemStack getItemOnCursor() {
        return getRealPlayer().getItemOnCursor();
    }

    @java.lang.Override
    public void setItemOnCursor(org.bukkit.inventory.ItemStack item) {
        getRealPlayer().setItemInHand(item);
    }

    @java.lang.Override
    public boolean isSleeping() {
        return getRealPlayer().isSleeping();
    }

    @java.lang.Override
    public int getSleepTicks() {
        return getRealPlayer().getSleepTicks();
    }

    @java.lang.Override
    public org.bukkit.GameMode getGameMode() {
        return getRealPlayer().getGameMode();
    }

    @java.lang.Override
    public void setGameMode(org.bukkit.GameMode mode) {
        getRealPlayer().setGameMode(mode);
    }

    @java.lang.Override
    public boolean isBlocking() {
        return getRealPlayer().isBlocking();
    }

    @java.lang.Override
    public int getExpToLevel() {
        return getRealPlayer().getExpToLevel();
    }

    @java.lang.Override
    public double getEyeHeight() {
        return getRealPlayer().getEyeHeight();
    }

    @java.lang.Override
    public double getEyeHeight(boolean ignoreSneaking) {
        return getRealPlayer().getEyeHeight(ignoreSneaking);
    }

    @java.lang.Override
    public org.bukkit.Location getEyeLocation() {
        return getRealPlayer().getEyeLocation();
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public java.util.List<org.bukkit.block.Block> getLineOfSight(java.util.HashSet<java.lang.Byte> transparent, int maxDistance) {
        return getRealPlayer().getLineOfSight(transparent, maxDistance);
    }

    @java.lang.Override
    public java.util.List<org.bukkit.block.Block> getLineOfSight(java.util.Set<org.bukkit.Material> transparent, int maxDistance) {
        return getRealPlayer().getLineOfSight(transparent, maxDistance);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public org.bukkit.block.Block getTargetBlock(java.util.HashSet<java.lang.Byte> transparent, int maxDistance) {
        return getRealPlayer().getTargetBlock(transparent, maxDistance);
    }

    @java.lang.Override
    public org.bukkit.block.Block getTargetBlock(java.util.Set<org.bukkit.Material> transparent, int maxDistance) {
        return getRealPlayer().getTargetBlock(transparent, maxDistance);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public java.util.List<org.bukkit.block.Block> getLastTwoTargetBlocks(java.util.HashSet<java.lang.Byte> transparent, int maxDistance) {
        return getRealPlayer().getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @java.lang.Override
    public java.util.List<org.bukkit.block.Block> getLastTwoTargetBlocks(java.util.Set<org.bukkit.Material> transparent, int maxDistance) {
        return getRealPlayer().getLastTwoTargetBlocks(transparent, maxDistance);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public org.bukkit.entity.Egg throwEgg() {
        return getRealPlayer().throwEgg();
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public org.bukkit.entity.Snowball throwSnowball() {
        return getRealPlayer().launchProjectile(org.bukkit.entity.Egg.class);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public org.bukkit.entity.Arrow shootArrow() {
        return getRealPlayer().shootArrow();
    }

    @java.lang.Override
    public int getRemainingAir() {
        return getRealPlayer().getRemainingAir();
    }

    @java.lang.Override
    public void setRemainingAir(int ticks) {
        getRealPlayer().setRemainingAir(ticks);
    }

    @java.lang.Override
    public int getMaximumAir() {
        return getRealPlayer().getMaximumAir();
    }

    @java.lang.Override
    public void setMaximumAir(int ticks) {
        getRealPlayer().setMaximumAir(ticks);
    }

    @java.lang.Override
    public int getMaximumNoDamageTicks() {
        return getRealPlayer().getMaximumNoDamageTicks();
    }

    @java.lang.Override
    public void setMaximumNoDamageTicks(int ticks) {
        getRealPlayer().setMaximumNoDamageTicks(ticks);
    }

    @java.lang.Override
    public double getLastDamage() {
        return getRealPlayer().getLastDamage();
    }

    @java.lang.Override
    public int _INVALID_getLastDamage() {
        return ((int) (getRealPlayer().getLastDamage()));
    }

    @java.lang.Override
    public void setLastDamage(double damage) {
        getRealPlayer().setLastDamage(damage);
    }

    @java.lang.Override
    public void _INVALID_setLastDamage(int damage) {
        getRealPlayer().setLastDamage(damage);
    }

    @java.lang.Override
    public int getNoDamageTicks() {
        return getRealPlayer().getNoDamageTicks();
    }

    @java.lang.Override
    public void setNoDamageTicks(int ticks) {
        getRealPlayer().setNoDamageTicks(ticks);
    }

    @java.lang.Override
    public org.bukkit.entity.Player getKiller() {
        return getRealPlayer().getKiller();
    }

    @java.lang.Override
    public boolean addPotionEffect(org.bukkit.potion.PotionEffect effect) {
        return getRealPlayer().addPotionEffect(effect);
    }

    @java.lang.Override
    public boolean addPotionEffect(org.bukkit.potion.PotionEffect effect, boolean force) {
        return getRealPlayer().addPotionEffect(effect, force);
    }

    @java.lang.Override
    public boolean addPotionEffects(java.util.Collection<org.bukkit.potion.PotionEffect> effects) {
        return getRealPlayer().addPotionEffects(effects);
    }

    @java.lang.Override
    public boolean hasPotionEffect(org.bukkit.potion.PotionEffectType type) {
        return getRealPlayer().hasPotionEffect(type);
    }

    @java.lang.Override
    public void removePotionEffect(org.bukkit.potion.PotionEffectType type) {
        getRealPlayer().removePotionEffect(type);
    }

    @java.lang.Override
    public java.util.Collection<org.bukkit.potion.PotionEffect> getActivePotionEffects() {
        return getRealPlayer().getActivePotionEffects();
    }

    @java.lang.Override
    public boolean hasLineOfSight(org.bukkit.entity.Entity other) {
        return getRealPlayer().hasLineOfSight(other);
    }

    @java.lang.Override
    public boolean getRemoveWhenFarAway() {
        return getRealPlayer().getRemoveWhenFarAway();
    }

    @java.lang.Override
    public void setRemoveWhenFarAway(boolean remove) {
        getRealPlayer().setRemoveWhenFarAway(remove);
    }

    @java.lang.Override
    public org.bukkit.inventory.EntityEquipment getEquipment() {
        return getRealPlayer().getEquipment();
    }

    @java.lang.Override
    public void setCanPickupItems(boolean pickup) {
        getRealPlayer().setCanPickupItems(pickup);
    }

    @java.lang.Override
    public boolean getCanPickupItems() {
        return getRealPlayer().getCanPickupItems();
    }

    @java.lang.Override
    public boolean isLeashed() {
        return getRealPlayer().isLeashed();
    }

    @java.lang.Override
    public org.bukkit.entity.Entity getLeashHolder() throws java.lang.IllegalStateException {
        return getRealPlayer().getLeashHolder();
    }

    @java.lang.Override
    public boolean setLeashHolder(org.bukkit.entity.Entity holder) {
        return getRealPlayer().setLeashHolder(holder);
    }

    @java.lang.Override
    public org.bukkit.Location getLocation() {
        return getRealPlayer().getLocation();
    }

    @java.lang.Override
    public org.bukkit.Location getLocation(org.bukkit.Location loc) {
        return getRealPlayer().getLocation(loc);
    }

    @java.lang.Override
    public void setVelocity(org.bukkit.util.Vector velocity) {
        getRealPlayer().setVelocity(velocity);
    }

    @java.lang.Override
    public org.bukkit.util.Vector getVelocity() {
        return getRealPlayer().getVelocity();
    }

    @java.lang.Override
    public org.bukkit.World getWorld() {
        return getRealPlayer().getWorld();
    }

    @java.lang.Override
    public boolean teleport(org.bukkit.Location location) {
        return getRealPlayer().teleport(location);
    }

    @java.lang.Override
    public boolean teleport(org.bukkit.Location location, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause cause) {
        return getRealPlayer().teleport(location, cause);
    }

    @java.lang.Override
    public boolean teleport(org.bukkit.entity.Entity destination) {
        return getRealPlayer().teleport(destination);
    }

    @java.lang.Override
    public boolean teleport(org.bukkit.entity.Entity destination, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause cause) {
        return getRealPlayer().teleport(destination, cause);
    }

    @java.lang.Override
    public java.util.List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
        return getRealPlayer().getNearbyEntities(x, y, z);
    }

    @java.lang.Override
    public int getEntityId() {
        return getRealPlayer().getEntityId();
    }

    @java.lang.Override
    public int getFireTicks() {
        return getRealPlayer().getFireTicks();
    }

    @java.lang.Override
    public int getMaxFireTicks() {
        return getRealPlayer().getMaxFireTicks();
    }

    @java.lang.Override
    public void setFireTicks(int ticks) {
        getRealPlayer().setFireTicks(ticks);
    }

    @java.lang.Override
    public void remove() {
        getRealPlayer().remove();
    }

    @java.lang.Override
    public boolean isDead() {
        return getRealPlayer().isDead();
    }

    @java.lang.Override
    public boolean isValid() {
        return getRealPlayer().isValid();
    }

    @java.lang.Override
    public org.bukkit.Server getServer() {
        return getRealPlayer().getServer();
    }

    @java.lang.Override
    public org.bukkit.entity.Entity getPassenger() {
        return getRealPlayer().getPassenger();
    }

    @java.lang.Override
    public boolean setPassenger(org.bukkit.entity.Entity passenger) {
        return getRealPlayer().setPassenger(passenger);
    }

    @java.lang.Override
    public boolean isEmpty() {
        return getRealPlayer().isEmpty();
    }

    @java.lang.Override
    public boolean eject() {
        return getRealPlayer().eject();
    }

    @java.lang.Override
    public float getFallDistance() {
        return getRealPlayer().getFallDistance();
    }

    @java.lang.Override
    public void setFallDistance(float distance) {
        getRealPlayer().setFallDistance(distance);
    }

    @java.lang.Override
    public void setLastDamageCause(org.bukkit.event.entity.EntityDamageEvent event) {
        getRealPlayer().setLastDamageCause(event);
    }

    @java.lang.Override
    public org.bukkit.event.entity.EntityDamageEvent getLastDamageCause() {
        return getRealPlayer().getLastDamageCause();
    }

    @java.lang.Override
    public java.util.UUID getUniqueId() {
        return getRealPlayer().getUniqueId();
    }

    @java.lang.Override
    public int getTicksLived() {
        return getRealPlayer().getTicksLived();
    }

    @java.lang.Override
    public void setTicksLived(int value) {
        getRealPlayer().setTicksLived(value);
    }

    @java.lang.Override
    public void playEffect(org.bukkit.EntityEffect type) {
        getRealPlayer().playEffect(type);
    }

    @java.lang.Override
    public org.bukkit.entity.EntityType getType() {
        return getRealPlayer().getType();
    }

    @java.lang.Override
    public boolean isInsideVehicle() {
        return getRealPlayer().isInsideVehicle();
    }

    @java.lang.Override
    public boolean leaveVehicle() {
        return getRealPlayer().leaveVehicle();
    }

    @java.lang.Override
    public org.bukkit.entity.Entity getVehicle() {
        return getRealPlayer().getVehicle();
    }

    @java.lang.Override
    public void setCustomName(java.lang.String name) {
        getRealPlayer().setCustomName(name);
    }

    @java.lang.Override
    public java.lang.String getCustomName() {
        return getRealPlayer().getCustomName();
    }

    @java.lang.Override
    public void setCustomNameVisible(boolean flag) {
        getRealPlayer().setCustomNameVisible(flag);
    }

    @java.lang.Override
    public boolean isCustomNameVisible() {
        return getRealPlayer().isCustomNameVisible();
    }

    @java.lang.Override
    public void setMetadata(java.lang.String metadataKey, org.bukkit.metadata.MetadataValue newMetadataValue) {
        getRealPlayer().setMetadata(metadataKey, newMetadataValue);
    }

    @java.lang.Override
    public java.util.List<org.bukkit.metadata.MetadataValue> getMetadata(java.lang.String metadataKey) {
        return getRealPlayer().getMetadata(metadataKey);
    }

    @java.lang.Override
    public boolean hasMetadata(java.lang.String metadataKey) {
        return getRealPlayer().hasMetadata(metadataKey);
    }

    @java.lang.Override
    public void removeMetadata(java.lang.String metadataKey, org.bukkit.plugin.Plugin owningPlugin) {
        getRealPlayer().removeMetadata(metadataKey, owningPlugin);
    }

    @java.lang.Override
    public void sendMessage(java.lang.String message) {
        getRealPlayer().sendMessage(message);
    }

    @java.lang.Override
    public void sendMessage(java.lang.String[] messages) {
        getRealPlayer().sendMessage(messages);
    }

    @java.lang.Override
    public boolean isPermissionSet(java.lang.String name) {
        return getRealPlayer().isPermissionSet(name);
    }

    @java.lang.Override
    public boolean isPermissionSet(org.bukkit.permissions.Permission perm) {
        return getRealPlayer().isPermissionSet(perm);
    }

    @java.lang.Override
    public boolean hasPermission(java.lang.String name) {
        return getRealPlayer().hasPermission(name);
    }

    @java.lang.Override
    public boolean hasPermission(org.bukkit.permissions.Permission perm) {
        return getRealPlayer().hasPermission(perm);
    }

    @java.lang.Override
    public org.bukkit.permissions.PermissionAttachment addAttachment(org.bukkit.plugin.Plugin plugin, java.lang.String name, boolean value) {
        return getRealPlayer().addAttachment(plugin, name, value);
    }

    @java.lang.Override
    public org.bukkit.permissions.PermissionAttachment addAttachment(org.bukkit.plugin.Plugin plugin) {
        return getRealPlayer().addAttachment(plugin);
    }

    @java.lang.Override
    public org.bukkit.permissions.PermissionAttachment addAttachment(org.bukkit.plugin.Plugin plugin, java.lang.String name, boolean value, int ticks) {
        return getRealPlayer().addAttachment(plugin, name, value, ticks);
    }

    @java.lang.Override
    public org.bukkit.permissions.PermissionAttachment addAttachment(org.bukkit.plugin.Plugin plugin, int ticks) {
        return getRealPlayer().addAttachment(plugin, ticks);
    }

    @java.lang.Override
    public void removeAttachment(org.bukkit.permissions.PermissionAttachment attachment) {
        getRealPlayer().removeAttachment(attachment);
    }

    @java.lang.Override
    public void recalculatePermissions() {
        getRealPlayer().recalculatePermissions();
    }

    @java.lang.Override
    public java.util.Set<org.bukkit.permissions.PermissionAttachmentInfo> getEffectivePermissions() {
        return getRealPlayer().getEffectivePermissions();
    }

    @java.lang.Override
    public boolean isOp() {
        return getRealPlayer().isOp();
    }

    @java.lang.Override
    public void setOp(boolean value) {
        getRealPlayer().setOp(value);
    }

    @java.lang.Override
    public void damage(double amount) {
        getRealPlayer().damage(amount);
    }

    @java.lang.Override
    public void _INVALID_damage(int amount) {
        getRealPlayer().damage(amount);
    }

    @java.lang.Override
    public void damage(double amount, org.bukkit.entity.Entity source) {
        getRealPlayer().damage(amount, source);
    }

    @java.lang.Override
    public void _INVALID_damage(int amount, org.bukkit.entity.Entity source) {
        getRealPlayer().damage(amount, source);
    }

    @java.lang.Override
    public double getHealth() {
        return getRealPlayer().getHealth();
    }

    @java.lang.Override
    public int _INVALID_getHealth() {
        return ((int) (getRealPlayer().getHealth()));
    }

    @java.lang.Override
    public void setHealth(double health) {
        getRealPlayer().setHealth(health);
    }

    @java.lang.Override
    public void _INVALID_setHealth(int health) {
        getRealPlayer().setHealth(health);
    }

    @java.lang.Override
    public double getMaxHealth() {
        return getRealPlayer().getMaxHealth();
    }

    @java.lang.Override
    public int _INVALID_getMaxHealth() {
        return ((int) (getRealPlayer().getMaxHealth()));
    }

    @java.lang.Override
    public void setMaxHealth(double health) {
        getRealPlayer().setMaxHealth(health);
    }

    @java.lang.Override
    public void _INVALID_setMaxHealth(int health) {
        getRealPlayer().setMaxHealth(health);
    }

    @java.lang.Override
    public void resetMaxHealth() {
        getRealPlayer().resetMaxHealth();
    }

    @java.lang.Override
    public <T extends org.bukkit.entity.Projectile> T launchProjectile(java.lang.Class<? extends T> projectile) {
        return getRealPlayer().launchProjectile(projectile);
    }

    @java.lang.Override
    public <T extends org.bukkit.entity.Projectile> T launchProjectile(java.lang.Class<? extends T> projectile, org.bukkit.util.Vector velocity) {
        return getRealPlayer().launchProjectile(projectile, velocity);
    }

    @java.lang.Override
    public boolean isConversing() {
        return getRealPlayer().isConversing();
    }

    @java.lang.Override
    public void acceptConversationInput(java.lang.String input) {
        getRealPlayer().acceptConversationInput(input);
    }

    @java.lang.Override
    public boolean beginConversation(org.bukkit.conversations.Conversation conversation) {
        return getRealPlayer().beginConversation(conversation);
    }

    @java.lang.Override
    public void abandonConversation(org.bukkit.conversations.Conversation conversation) {
        getRealPlayer().abandonConversation(conversation);
    }

    @java.lang.Override
    public void abandonConversation(org.bukkit.conversations.Conversation conversation, org.bukkit.conversations.ConversationAbandonedEvent details) {
        getRealPlayer().abandonConversation(conversation, details);
    }

    @java.lang.Override
    public boolean isOnline() {
        return getRealPlayer().isOnline();
    }

    @java.lang.Override
    public boolean isBanned() {
        return getRealPlayer().isBanned();
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void setBanned(boolean banned) {
        getRealPlayer().setBanned(banned);
    }

    @java.lang.Override
    public boolean isWhitelisted() {
        return getRealPlayer().isWhitelisted();
    }

    @java.lang.Override
    public void setWhitelisted(boolean value) {
        getRealPlayer().setWhitelisted(value);
    }

    @java.lang.Override
    public org.bukkit.entity.Player getPlayer() {
        return getRealPlayer().getPlayer();
    }

    @java.lang.Override
    public long getFirstPlayed() {
        return getRealPlayer().getFirstPlayed();
    }

    @java.lang.Override
    public long getLastPlayed() {
        return getRealPlayer().getLastPlayed();
    }

    @java.lang.Override
    public boolean hasPlayedBefore() {
        return getRealPlayer().hasPlayedBefore();
    }

    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.Object> serialize() {
        return getRealPlayer().serialize();
    }

    @java.lang.Override
    public void sendPluginMessage(org.bukkit.plugin.Plugin source, java.lang.String channel, byte[] message) {
        getRealPlayer().sendPluginMessage(source, channel, message);
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> getListeningPluginChannels() {
        return getRealPlayer().getListeningPluginChannels();
    }

    @java.lang.Override
    public java.lang.String getDisplayName() {
        return getRealPlayer().getDisplayName();
    }

    @java.lang.Override
    public void setDisplayName(java.lang.String name) {
        getRealPlayer().setDisplayName(name);
    }

    @java.lang.Override
    public java.lang.String getPlayerListName() {
        return getRealPlayer().getPlayerListName();
    }

    @java.lang.Override
    public void setPlayerListName(java.lang.String name) {
        getRealPlayer().setPlayerListName(name);
    }

    @java.lang.Override
    public void setCompassTarget(org.bukkit.Location loc) {
        getRealPlayer().setCompassTarget(loc);
    }

    @java.lang.Override
    public org.bukkit.Location getCompassTarget() {
        return getRealPlayer().getCompassTarget();
    }

    @java.lang.Override
    public java.net.InetSocketAddress getAddress() {
        return getRealPlayer().getAddress();
    }

    @java.lang.Override
    public void sendRawMessage(java.lang.String message) {
        getRealPlayer().sendRawMessage(message);
    }

    @java.lang.Override
    public void kickPlayer(java.lang.String message) {
        getRealPlayer().kickPlayer(message);
    }

    @java.lang.Override
    public void chat(java.lang.String msg) {
        getRealPlayer().chat(msg);
    }

    @java.lang.Override
    public boolean performCommand(java.lang.String command) {
        return getRealPlayer().performCommand(command);
    }

    @java.lang.Override
    public boolean isSneaking() {
        return getRealPlayer().isSneaking();
    }

    @java.lang.Override
    public void setSneaking(boolean sneak) {
        getRealPlayer().setSneaking(sneak);
    }

    @java.lang.Override
    public boolean isSprinting() {
        return getRealPlayer().isSprinting();
    }

    @java.lang.Override
    public void setSprinting(boolean sprinting) {
        getRealPlayer().setSprinting(sprinting);
    }

    @java.lang.Override
    public void saveData() {
        getRealPlayer().saveData();
    }

    @java.lang.Override
    public void loadData() {
        getRealPlayer().loadData();
    }

    @java.lang.Override
    public void setSleepingIgnored(boolean isSleeping) {
        getRealPlayer().setSleepingIgnored(isSleeping);
    }

    @java.lang.Override
    public boolean isSleepingIgnored() {
        return getRealPlayer().isSleepingIgnored();
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void playNote(org.bukkit.Location loc, byte instrument, byte note) {
        getRealPlayer().playNote(loc, instrument, note);
    }

    @java.lang.Override
    public void playNote(org.bukkit.Location loc, org.bukkit.Instrument instrument, org.bukkit.Note note) {
        getRealPlayer().playNote(loc, instrument, note);
    }

    @java.lang.Override
    public void playSound(org.bukkit.Location location, org.bukkit.Sound sound, float volume, float pitch) {
        getRealPlayer().playSound(location, sound, volume, pitch);
    }

    @java.lang.Override
    public void playSound(org.bukkit.Location location, java.lang.String sound, float volume, float pitch) {
        getRealPlayer().playSound(location, sound, volume, pitch);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void playEffect(org.bukkit.Location loc, org.bukkit.Effect effect, int data) {
        getRealPlayer().playEffect(loc, effect, data);
    }

    @java.lang.Override
    public <T> void playEffect(org.bukkit.Location loc, org.bukkit.Effect effect, T data) {
        getRealPlayer().playEffect(loc, effect, data);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void sendBlockChange(org.bukkit.Location loc, org.bukkit.Material material, byte data) {
        getRealPlayer().sendBlockChange(loc, material, data);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public boolean sendChunkChange(org.bukkit.Location loc, int sx, int sy, int sz, byte[] data) {
        return getRealPlayer().sendChunkChange(loc, sx, sy, sz, data);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void sendBlockChange(org.bukkit.Location loc, int material, byte data) {
        getRealPlayer().sendBlockChange(loc, material, data);
    }

    @java.lang.Override
    public void sendSignChange(org.bukkit.Location loc, java.lang.String[] lines) throws java.lang.IllegalArgumentException {
        getRealPlayer().sendSignChange(loc, lines);
    }

    @java.lang.Override
    public void sendMap(org.bukkit.map.MapView map) {
        getRealPlayer().sendMap(map);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void updateInventory() {
        getRealPlayer().updateInventory();
    }

    @java.lang.Override
    public void awardAchievement(org.bukkit.Achievement achievement) {
        getRealPlayer().awardAchievement(achievement);
    }

    @java.lang.Override
    public void removeAchievement(org.bukkit.Achievement achievement) {
        getRealPlayer().removeAchievement(achievement);
    }

    @java.lang.Override
    public boolean hasAchievement(org.bukkit.Achievement achievement) {
        return getRealPlayer().hasAchievement(achievement);
    }

    @java.lang.Override
    public void incrementStatistic(org.bukkit.Statistic statistic) throws java.lang.IllegalArgumentException {
        getRealPlayer().incrementStatistic(statistic);
    }

    @java.lang.Override
    public void decrementStatistic(org.bukkit.Statistic statistic) throws java.lang.IllegalArgumentException {
        getRealPlayer().decrementStatistic(statistic);
    }

    @java.lang.Override
    public void incrementStatistic(org.bukkit.Statistic statistic, int amount) throws java.lang.IllegalArgumentException {
        getRealPlayer().incrementStatistic(statistic, amount);
    }

    @java.lang.Override
    public void decrementStatistic(org.bukkit.Statistic statistic, int amount) throws java.lang.IllegalArgumentException {
        getRealPlayer().decrementStatistic(statistic, amount);
    }

    @java.lang.Override
    public void setStatistic(org.bukkit.Statistic statistic, int newValue) throws java.lang.IllegalArgumentException {
        getRealPlayer().setStatistic(statistic, newValue);
    }

    @java.lang.Override
    public int getStatistic(org.bukkit.Statistic statistic) throws java.lang.IllegalArgumentException {
        return getRealPlayer().getStatistic(statistic);
    }

    @java.lang.Override
    public void incrementStatistic(org.bukkit.Statistic statistic, org.bukkit.Material material) throws java.lang.IllegalArgumentException {
        getRealPlayer().incrementStatistic(statistic, material);
    }

    @java.lang.Override
    public void decrementStatistic(org.bukkit.Statistic statistic, org.bukkit.Material material) throws java.lang.IllegalArgumentException {
        getRealPlayer().decrementStatistic(statistic, material);
    }

    @java.lang.Override
    public int getStatistic(org.bukkit.Statistic statistic, org.bukkit.Material material) throws java.lang.IllegalArgumentException {
        return getRealPlayer().getStatistic(statistic, material);
    }

    @java.lang.Override
    public void incrementStatistic(org.bukkit.Statistic statistic, org.bukkit.Material material, int amount) throws java.lang.IllegalArgumentException {
        getRealPlayer().incrementStatistic(statistic, material, amount);
    }

    @java.lang.Override
    public void decrementStatistic(org.bukkit.Statistic statistic, org.bukkit.Material material, int amount) throws java.lang.IllegalArgumentException {
        getRealPlayer().decrementStatistic(statistic, material, amount);
    }

    @java.lang.Override
    public void setStatistic(org.bukkit.Statistic statistic, org.bukkit.Material material, int newValue) throws java.lang.IllegalArgumentException {
        getRealPlayer().setStatistic(statistic, material, newValue);
    }

    @java.lang.Override
    public void incrementStatistic(org.bukkit.Statistic statistic, org.bukkit.entity.EntityType entityType) throws java.lang.IllegalArgumentException {
        getRealPlayer().incrementStatistic(statistic, entityType);
    }

    @java.lang.Override
    public void decrementStatistic(org.bukkit.Statistic statistic, org.bukkit.entity.EntityType entityType) throws java.lang.IllegalArgumentException {
        getRealPlayer().decrementStatistic(statistic, entityType);
    }

    @java.lang.Override
    public int getStatistic(org.bukkit.Statistic statistic, org.bukkit.entity.EntityType entityType) throws java.lang.IllegalArgumentException {
        return getRealPlayer().getStatistic(statistic, entityType);
    }

    @java.lang.Override
    public void incrementStatistic(org.bukkit.Statistic statistic, org.bukkit.entity.EntityType entityType, int amount) throws java.lang.IllegalArgumentException {
        getRealPlayer().incrementStatistic(statistic, entityType, amount);
    }

    @java.lang.Override
    public void decrementStatistic(org.bukkit.Statistic statistic, org.bukkit.entity.EntityType entityType, int amount) {
        getRealPlayer().decrementStatistic(statistic, entityType, amount);
    }

    @java.lang.Override
    public void setStatistic(org.bukkit.Statistic statistic, org.bukkit.entity.EntityType entityType, int newValue) {
        getRealPlayer().setStatistic(statistic, entityType, newValue);
    }

    @java.lang.Override
    public void setPlayerTime(long time, boolean relative) {
        getRealPlayer().setPlayerTime(time, relative);
    }

    @java.lang.Override
    public long getPlayerTime() {
        return getRealPlayer().getPlayerTime();
    }

    @java.lang.Override
    public long getPlayerTimeOffset() {
        return getRealPlayer().getPlayerTimeOffset();
    }

    @java.lang.Override
    public boolean isPlayerTimeRelative() {
        return getRealPlayer().isPlayerTimeRelative();
    }

    @java.lang.Override
    public void resetPlayerTime() {
        getRealPlayer().resetPlayerTime();
    }

    @java.lang.Override
    public void setPlayerWeather(org.bukkit.WeatherType type) {
        getRealPlayer().setPlayerWeather(type);
    }

    @java.lang.Override
    public org.bukkit.WeatherType getPlayerWeather() {
        return getRealPlayer().getPlayerWeather();
    }

    @java.lang.Override
    public void resetPlayerWeather() {
        getRealPlayer().resetPlayerWeather();
    }

    @java.lang.Override
    public void giveExp(int amount) {
        getRealPlayer().giveExp(amount);
    }

    @java.lang.Override
    public void giveExpLevels(int amount) {
        getRealPlayer().giveExpLevels(amount);
    }

    @java.lang.Override
    public float getExp() {
        return getRealPlayer().getExp();
    }

    @java.lang.Override
    public void setExp(float exp) {
        getRealPlayer().setExp(exp);
    }

    @java.lang.Override
    public int getLevel() {
        return getRealPlayer().getLevel();
    }

    @java.lang.Override
    public void setLevel(int level) {
        getRealPlayer().setLevel(level);
    }

    @java.lang.Override
    public int getTotalExperience() {
        return getRealPlayer().getTotalExperience();
    }

    @java.lang.Override
    public void setTotalExperience(int exp) {
        getRealPlayer().setTotalExperience(exp);
    }

    @java.lang.Override
    public float getExhaustion() {
        return getRealPlayer().getExhaustion();
    }

    @java.lang.Override
    public void setExhaustion(float value) {
        getRealPlayer().setExhaustion(value);
    }

    @java.lang.Override
    public float getSaturation() {
        return getRealPlayer().getSaturation();
    }

    @java.lang.Override
    public void setSaturation(float value) {
        getRealPlayer().setSaturation(value);
    }

    @java.lang.Override
    public int getFoodLevel() {
        return getRealPlayer().getFoodLevel();
    }

    @java.lang.Override
    public void setFoodLevel(int value) {
        getRealPlayer().setFoodLevel(value);
    }

    @java.lang.Override
    public org.bukkit.Location getBedSpawnLocation() {
        return getRealPlayer().getBedSpawnLocation();
    }

    @java.lang.Override
    public void setBedSpawnLocation(org.bukkit.Location location) {
        getRealPlayer().setBedSpawnLocation(location);
    }

    @java.lang.Override
    public void setBedSpawnLocation(org.bukkit.Location location, boolean force) {
        getRealPlayer().setBedSpawnLocation(location, force);
    }

    @java.lang.Override
    public boolean getAllowFlight() {
        return getRealPlayer().getAllowFlight();
    }

    @java.lang.Override
    public void setAllowFlight(boolean flight) {
        getRealPlayer().setAllowFlight(flight);
    }

    @java.lang.Override
    public void hidePlayer(org.bukkit.entity.Player player) {
        getRealPlayer().hidePlayer(player);
    }

    @java.lang.Override
    public void showPlayer(org.bukkit.entity.Player player) {
        getRealPlayer().showPlayer(player);
    }

    @java.lang.Override
    public boolean canSee(org.bukkit.entity.Player player) {
        return getRealPlayer().canSee(player);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public boolean isOnGround() {
        return getRealPlayer().isOnGround();
    }

    @java.lang.Override
    public boolean isFlying() {
        return getRealPlayer().isFlying();
    }

    @java.lang.Override
    public void setFlying(boolean value) {
        getRealPlayer().setFlying(value);
    }

    @java.lang.Override
    public void setFlySpeed(float value) throws java.lang.IllegalArgumentException {
        getRealPlayer().setFlySpeed(value);
    }

    @java.lang.Override
    public void setWalkSpeed(float value) throws java.lang.IllegalArgumentException {
        getRealPlayer().setWalkSpeed(value);
    }

    @java.lang.Override
    public float getFlySpeed() {
        return getRealPlayer().getFlySpeed();
    }

    @java.lang.Override
    public float getWalkSpeed() {
        return getRealPlayer().getWalkSpeed();
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void setTexturePack(java.lang.String url) {
        getRealPlayer().setTexturePack(url);
    }

    @java.lang.Override
    public void setResourcePack(java.lang.String url) {
        getRealPlayer().setResourcePack(url);
    }

    @java.lang.Override
    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return getRealPlayer().getScoreboard();
    }

    @java.lang.Override
    public void setScoreboard(org.bukkit.scoreboard.Scoreboard scoreboard) throws java.lang.IllegalArgumentException, java.lang.IllegalStateException {
        getRealPlayer().setScoreboard(scoreboard);
    }

    @java.lang.Override
    public boolean isHealthScaled() {
        return getRealPlayer().isHealthScaled();
    }

    @java.lang.Override
    public void setHealthScaled(boolean scale) {
        getRealPlayer().setHealthScaled(scale);
    }

    @java.lang.Override
    public void setHealthScale(double scale) throws java.lang.IllegalArgumentException {
        getRealPlayer().setHealthScale(scale);
    }

    @java.lang.Override
    public double getHealthScale() {
        return getRealPlayer().getHealthScale();
    }

    @java.lang.Override
    public org.bukkit.entity.Entity getSpectatorTarget() {
        return getRealPlayer().getSpectatorTarget();
    }

    @java.lang.Override
    public void setSpectatorTarget(org.bukkit.entity.Entity entity) {
        getRealPlayer().setSpectatorTarget(entity);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void sendTitle(java.lang.String title, java.lang.String subtitle) {
        getRealPlayer().sendTitle(title, subtitle);
    }

    @java.lang.SuppressWarnings("deprecation")
    @java.lang.Override
    public void resetTitle() {
        getRealPlayer().resetTitle();
    }
}