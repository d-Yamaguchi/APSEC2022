package org.bukkit.craftbukkit.entity;
import net.minecraft.server.EntityTameableAnimal;
import org.bukkit.craftbukkit.CraftServer;
public class CraftTameableAnimal extends org.bukkit.craftbukkit.entity.CraftAnimals implements org.bukkit.entity.Tameable , org.bukkit.entity.Creature {
    public CraftTameableAnimal(org.bukkit.craftbukkit.CraftServer server, net.minecraft.server.EntityTameableAnimal entity) {
        super(server, entity);
    }

    @java.lang.Override
    public net.minecraft.server.EntityTameableAnimal getHandle() {
        return ((net.minecraft.server.EntityTameableAnimal) (super.getHandle()));
    }

    public org.bukkit.entity.AnimalTamer getOwner() {
        if ("".equals(getOwnerName()))
            return null;

        org.bukkit.entity.AnimalTamer owner = getServer().getPlayerExact(getOwnerName());
        if (owner == null) {
            owner = getServer().getOfflinePlayer(getOwnerName());
        }
        return owner;
    }

    public java.lang.String getOwnerName() {
        return getHandle().getOwnerName();
    }

    public boolean isTamed() {
        return getHandle().isTamed();
    }

    public void setOwner(org.bukkit.entity.AnimalTamer tamer) {
        if (tamer != null) {
            setTamed(true);
            getHandle().setPathEntity(null);
            setOwnerUUID(tamer.getUniqueId());
        } else {
            setTamed(false);
            getHandle().setOwnerUUID("");
        }
    }

    public void setOwnerName(java.lang.String ownerName) {
        getHandle().setOwnerName(ownerName == null ? "" : ownerName);
    }

    public void setTamed(boolean tame) {
        getHandle().setTamed(tame);
        if (!tame) {
            setOwnerName("");
        }
    }

    public boolean isSitting() {
        return getHandle().isSitting();
    }

    public void setSitting(boolean sitting) {
        getHandle().getGoalSit().setSitting(sitting);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((((getClass().getSimpleName() + "{owner=") + getOwner()) + ",tamed=") + isTamed()) + "}";
    }
}