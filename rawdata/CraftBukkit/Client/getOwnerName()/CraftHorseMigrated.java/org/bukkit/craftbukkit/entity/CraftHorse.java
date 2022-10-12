package org.bukkit.craftbukkit.entity;
import net.minecraft.server.EntityHorse;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.inventory.CraftInventoryHorse;
public class CraftHorse extends org.bukkit.craftbukkit.entity.CraftAnimals implements org.bukkit.entity.Horse {
    public CraftHorse(org.bukkit.craftbukkit.CraftServer server, net.minecraft.server.EntityHorse entity) {
        super(server, entity);
    }

    @java.lang.Override
    public net.minecraft.server.EntityHorse getHandle() {
        return ((net.minecraft.server.EntityHorse) (entity));
    }

    public org.bukkit.entity.Horse.Variant getVariant() {
        return org.bukkit.entity.Horse.Variant.values()[getHandle().getType()];
    }

    public void setVariant(org.bukkit.entity.Horse.Variant variant) {
        org.apache.commons.lang.Validate.notNull(variant, "Variant cannot be null");
        getHandle().setType(variant.ordinal());
    }

    public org.bukkit.entity.Horse.Color getColor() {
        return org.bukkit.entity.Horse.Color.values()[getHandle().getVariant() & 0xff];
    }

    public void setColor(org.bukkit.entity.Horse.Color color) {
        org.apache.commons.lang.Validate.notNull(color, "Color cannot be null");
        getHandle().setVariant((color.ordinal() & 0xff) | (getStyle().ordinal() << 8));
    }

    public org.bukkit.entity.Horse.Style getStyle() {
        return org.bukkit.entity.Horse.Style.values()[getHandle().getVariant() >>> 8];
    }

    public void setStyle(org.bukkit.entity.Horse.Style style) {
        org.apache.commons.lang.Validate.notNull(style, "Style cannot be null");
        getHandle().setVariant((getColor().ordinal() & 0xff) | (style.ordinal() << 8));
    }

    public boolean isCarryingChest() {
        return getHandle().hasChest();
    }

    public void setCarryingChest(boolean chest) {
        if (chest == isCarryingChest())
            return;

        getHandle().setHasChest(chest);
        getHandle().loadChest();
    }

    public int getDomestication() {
        return getHandle().getTemper();
    }

    public void setDomestication(int value) {
        org.apache.commons.lang.Validate.isTrue(value >= 0, "Domestication cannot be less than zero");
        org.apache.commons.lang.Validate.isTrue(value <= getMaxDomestication(), "Domestication cannot be greater than the max domestication");
        getHandle().setTemper(value);
    }

    public int getMaxDomestication() {
        return getHandle().getMaxDomestication();
    }

    public void setMaxDomestication(int value) {
        org.apache.commons.lang.Validate.isTrue(value > 0, "Max domestication cannot be zero or less");
        getHandle().maxDomestication = value;
    }

    public double getJumpStrength() {
        return getHandle().getJumpStrength();
    }

    public void setJumpStrength(double strength) {
        org.apache.commons.lang.Validate.isTrue(strength >= 0, "Jump strength cannot be less than zero");
        getHandle().getAttributeInstance(EntityHorse.attributeJumpStrength).setValue(strength);
    }

    @java.lang.Override
    public boolean isTamed() {
        return getHandle().isTame();
    }

    @java.lang.Override
    public void setTamed(boolean tamed) {
        getHandle().setTame(tamed);
    }

    @java.lang.Override
    public org.bukkit.entity.AnimalTamer getOwner() {
        if ()
            return null;

        return getServer().getOfflinePlayer(getHandle().getOwnerUUID());
    }

    @java.lang.Override
    public void setOwner(org.bukkit.entity.AnimalTamer owner) {
        if ((owner != null) && (!"".equals(owner.getName()))) {
            setTamed(true);
            getHandle().setPathEntity(null);
            setOwnerName(owner.getName());
        } else {
            setTamed(false);
            setOwnerName("");
        }
    }

    public java.lang.String getOwnerName() {
        return getHandle().getOwnerName();
    }

    public void setOwnerName(java.lang.String name) {
        getHandle().setOwnerName(name);
    }

    public org.bukkit.inventory.HorseInventory getInventory() {
        return new org.bukkit.craftbukkit.inventory.CraftInventoryHorse(getHandle().inventoryChest);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((("CraftHorse{variant=" + getVariant()) + ", owner=") + getOwner()) + '}';
    }

    public org.bukkit.entity.EntityType getType() {
        return org.bukkit.entity.EntityType.HORSE;
    }
}