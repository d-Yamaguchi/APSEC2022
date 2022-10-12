/* This file is part of EchoPet.

EchoPet is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

EchoPet is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with EchoPet.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dsh105.echopet.api.pet;
import com.dsh105.dshutils.util.StringUtil;
import com.dsh105.echopet.compat.api.entity.*;
import com.dsh105.echopet.compat.api.event.PetTeleportEvent;
import com.dsh105.echopet.compat.api.plugin.EchoPet;
import com.dsh105.echopet.compat.api.util.*;
import com.dsh105.echopet.compat.api.util.reflection.SafeMethod;
import com.dsh105.echopet.compat.api.util.wrapper.WrapperPacketWorldParticles;
public abstract class Pet implements IPet {
    private com.dsh105.echopet.api.pet.IEntityPet entityPet;

    private com.dsh105.echopet.api.pet.PetType petType;

    private java.lang.String owner;

    private com.dsh105.echopet.api.pet.Pet rider;

    private java.lang.String name;

    private java.util.ArrayList<PetData> petData = new java.util.ArrayList<PetData>();

    private boolean isRider = false;

    public boolean ownerIsMounting = false;

    private boolean ownerRiding = false;

    private boolean isHat = false;

    public Pet(java.lang.String owner, IEntityPet entityPet) {
        this.owner = owner;
        this.setPetType();
        this.entityPet = entityPet;
        this.setPetName(this.getPetType().getDefaultName(this.getNameOfOwner()));
    }

    public Pet(org.bukkit.entity.Player owner) {
        if (owner != null) {
            this.owner = owner.getName();
            this.setPetType();
            this.entityPet = com.dsh105.echopet.compat.api.plugin.EchoPet.getPlugin().getSpawnUtil().spawn(this, owner);
            if (this.entityPet != null) {
                this.setPetName(this.getPetType().getDefaultName(this.getNameOfOwner()));
                this.teleportToOwner();
            }
        }
    }

    protected void setPetType() {
        EntityPetType entityPetType = this.getClass().getAnnotation(com.dsh105.echopet.api.pet.EntityPetType.class);
        if (entityPetType != null) {
            this.petType = entityPetType.petType();
        }
    }

    @java.lang.Override
    public com.dsh105.echopet.api.pet.IEntityPet getEntityPet() {
        return this.entityPet;
    }

    @java.lang.Override
    public com.dsh105.echopet.api.pet.ICraftPet getCraftPet() {
        return this.getEntityPet() == null ? null : this.getEntityPet().getBukkitEntity();
    }

    @java.lang.Override
    public org.bukkit.Location getLocation() {
        return this.getCraftPet().getLocation();
    }

    @java.lang.Override
    public org.bukkit.entity.Player getOwner() {
        if (this.owner == null) {
            return null;
        }
        return ServerUtil.findPlayerExact(owner);
    }

    @java.lang.Override
    public java.lang.String getNameOfOwner() {
        return this.owner;
    }

    @java.lang.Override
    public com.dsh105.echopet.api.pet.PetType getPetType() {
        return this.petType;
    }

    @java.lang.Override
    public boolean isRider() {
        return this.isRider;
    }

    protected void setRider() {
        this.isRider = true;
    }

    @java.lang.Override
    public boolean isOwnerInMountingProcess() {
        return ownerIsMounting;
    }

    @java.lang.Override
    public com.dsh105.echopet.api.pet.Pet getRider() {
        return this.rider;
    }

    @java.lang.Override
    public java.lang.String getPetName() {
        return name;
    }

    @java.lang.Override
    public java.lang.String getPetNameWithoutColours() {
        return com.dsh105.dshutils.util.StringUtil.replaceColoursWithString(this.getPetName());
    }

    @java.lang.Override
    public boolean setPetName(java.lang.String name) {
        return this.setPetName(name, true);
    }

    @java.lang.Override
    public boolean setPetName(java.lang.String name, boolean sendFailMessage) {
        if (PetNames.allow(name, this)) {
            this.name = org.bukkit.ChatColor.translateAlternateColorCodes('&', name);
            if (com.dsh105.echopet.compat.api.plugin.EchoPet.getPlugin().getMainConfig().getBoolean("stripDiacriticsFromNames", true)) {
                this.name = StringSimplifier.stripDiacritics(this.name);
            }
            if ((this.name == null) || this.name.equalsIgnoreCase("")) {
                this.name = this.petType.getDefaultName(this.owner);
            }
            if (this.getCraftPet() != null) {
                this.getCraftPet().setCustomName(this.name);
                this.getCraftPet().setCustomNameVisible(com.dsh105.echopet.compat.api.plugin.EchoPet.getConfig().getBoolean(("pets." + this.getPetType().toString().toLowerCase().replace("_", " ")) + ".tagVisible", true));
            }
            return true;
        } else {
            if (sendFailMessage) {
                if (this.getOwner() != null) {
                    Lang.sendTo(this.getOwner(), Lang.NAME_NOT_ALLOWED.toString().replace("%name%", name));
                }
            }
            return false;
        }
    }

    @java.lang.Override
    public java.util.ArrayList<PetData> getPetData() {
        return this.petData;
    }

    @java.lang.Override
    public void removeRider() {
        if (rider != null) {
            rider.removePet(true);
            this.rider = null;
        }
    }

    @java.lang.Override
    public void removePet(boolean makeSound) {
        if (this.getCraftPet() != null) {
            ParticleUtil.show(WrapperPacketWorldParticles.ParticleType.CLOUD, this.getLocation());
            ParticleUtil.show(WrapperPacketWorldParticles.ParticleType.LAVA_SPARK, this.getLocation());
        }
        removeRider();
        if (this.getEntityPet() != null) {
            this.getEntityPet().remove(makeSound);
        }
    }

    @java.lang.Override
    public void teleportToOwner() {
        if ((this.getOwner() == null) || (this.getOwner().getLocation() == null)) {
            this.removePet(false);
            return;
        }
        this.teleport(this.getOwner().getLocation());
    }

    @java.lang.Override
    public void teleport(org.bukkit.Location to) {
        if ((this.getEntityPet() == null) || this.getEntityPet().isDead()) {
            com.dsh105.echopet.compat.api.plugin.EchoPet.getManager().saveFileData("autosave", this);
            com.dsh105.echopet.compat.api.plugin.EchoPet.getSqlManager().saveToDatabase(this, false);
            com.dsh105.echopet.compat.api.plugin.EchoPet.getManager().removePet(this, false);
            com.dsh105.echopet.compat.api.plugin.EchoPet.getManager().createPetFromFile("autosave", this.getOwner());
            return;
        }
        com.dsh105.echopet.compat.api.event.PetTeleportEvent teleportEvent = new com.dsh105.echopet.compat.api.event.PetTeleportEvent(this, this.getLocation(), to);
        com.dsh105.echopet.compat.api.plugin.EchoPet.getPlugin().getServer().getPluginManager().callEvent(teleportEvent);
        if (teleportEvent.isCancelled()) {
            return;
        }
        org.bukkit.Location l = teleportEvent.getTo();
        if (l.getWorld() == this.getLocation().getWorld()) {
            if (this.getRider() != null) {
                this.getRider().getCraftPet().eject();
                this.getRider().getCraftPet().teleport(l);
            }
            this.getCraftPet().teleport(l);
            if (this.getRider() != null) {
                this.getCraftPet().setPassenger(this.getRider().getCraftPet());
            }
        }
    }

    @java.lang.Override
    public boolean isOwnerRiding() {
        return this.ownerRiding;
    }

    @java.lang.Override
    public boolean isHat() {
        return this.isHat;
    }

    @java.lang.Override
    public void ownerRidePet(boolean flag) {
        this.ownerIsMounting = true;
        if (this.ownerRiding == flag) {
            return;
        }
        if (this.isHat) {
            this.setAsHat(false);
        }
        // Ew...This stuff is UGLY :c
        if (!flag) {
            new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(PlayerUtil.playerToEntityPlayer(this.getOwner()), new java.lang.Object[]{ null });
            // ((CraftPlayer) this.getOwner()).getHandle().mount(null);
            if (this.getEntityPet() instanceof IEntityNoClipPet) {
                ((IEntityNoClipPet) (this.getEntityPet())).noClip(true);
            }
            ownerIsMounting = false;
        } else {
            if (this.getRider() != null) {
                this.getRider().removePet(false);
            }
            new org.bukkit.scheduler.BukkitRunnable() {
                @java.lang.Override
                public void run() {
                    new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(PlayerUtil.playerToEntityPlayer(getOwner()), getEntityPet());
                    // ((CraftPlayer) getOwner()).getHandle().mount(getEntityPet());
                    ownerIsMounting = false;
                    if (getEntityPet() instanceof IEntityNoClipPet) {
                        ((IEntityNoClipPet) (getEntityPet())).noClip(false);
                    }
                }
            }.runTaskLater(com.dsh105.echopet.compat.api.plugin.EchoPet.getPlugin(), 5L);
        }
        this.teleportToOwner();
        this.getEntityPet().resizeBoundingBox(flag);
        this.ownerRiding = flag;
        ParticleUtil.show(WrapperPacketWorldParticles.ParticleType.PORTAL, this.getLocation());
        org.bukkit.Location l = this.getLocation().clone();
        l.setY(l.getY() - 1.0);
        ParticleUtil.showWithData(WrapperPacketWorldParticles.ParticleType.BLOCK_DUST, this.getLocation(), l.getBlock().getTypeId(), 0);
    }

    @java.lang.Override
    public void setAsHat(boolean flag) {
        if (this.isHat == flag) {
            return;
        }
        if (this.ownerRiding) {
            this.ownerRidePet(false);
        }
        this.teleportToOwner();
        // Ew...This stuff is UGLY :c
        // Entity craftPet = ((Entity) this.getCraftPet().getHandle());
        if (!flag) {
            if (this.getRider() != null) {
                // Entity rider = ((Entity) this.getRider().getCraftPet().getHandle());
                // rider.mount(null);
                new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getRider().getEntityPet(), new java.lang.Object[]{ null });
                // craftPet.mount(null);
                new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getEntityPet(), new java.lang.Object[]{ null });
                // rider.mount(craftPet);
                new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getRider().getEntityPet(), this.getEntityPet());
            } else {
                // craftPet.mount(null);
                new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getEntityPet(), new java.lang.Object[]{ null });
            }
        } else if (this.getRider() != null) {
            // Entity rider = ((Entity) this.getRider().getCraftPet().getHandle());
            // rider.mount(null);
            new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getRider().getEntityPet(), new java.lang.Object[]{ null });
            // craftPet.mount(((CraftPlayer) this.getOwner()).getHandle());
            new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getEntityPet(), PlayerUtil.playerToEntityPlayer(this.getOwner()));
            // this.getCraftPet().setPassenger(this.getRider().getCraftPet());
            new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getRider().getEntityPet(), this.getEntityPet());
        } else {
            // craftPet.mount(((CraftPlayer) this.getOwner()).getHandle());
            new com.dsh105.echopet.compat.api.util.reflection.SafeMethod(ReflectionUtil.getNMSClass("Entity"), "mount", ReflectionUtil.getNMSClass("Entity")).invoke(this.getEntityPet(), PlayerUtil.playerToEntityPlayer(this.getOwner()));
        }
        this.getEntityPet().resizeBoundingBox(flag);
        this.isHat = flag;
        ParticleUtil.show(WrapperPacketWorldParticles.ParticleType.PORTAL, this.getLocation());
        org.bukkit.Location l = this.getLocation().clone();
        l.setY(l.getY() - 1.0);
        ParticleUtil.showWithData(WrapperPacketWorldParticles.ParticleType.PORTAL, this.getLocation(), l.getBlock().getTypeId(), 0);
    }

    @java.lang.Override
    public com.dsh105.echopet.api.pet.Pet createRider(final PetType pt, boolean sendFailMessage) {
        if (pt == PetType.HUMAN) {
            if (sendFailMessage) {
                Lang.sendTo(this.getOwner(), Lang.RIDERS_DISABLED.toString().replace("%type%", com.dsh105.dshutils.util.StringUtil.capitalise(this.getPetType().toString())));
            }
            return null;
        }
        if (!com.dsh105.echopet.compat.api.plugin.EchoPet.getOptions().allowRidersFor(this.getPetType())) {
            if (sendFailMessage) {
                Lang.sendTo(this.getOwner(), Lang.RIDERS_DISABLED.toString().replace("%type%", com.dsh105.dshutils.util.StringUtil.capitalise(this.getPetType().toString())));
            }
            return null;
        }
        if (this.isOwnerRiding()) {
            this.ownerRidePet(false);
        }
        if (this.rider != null) {
            this.removeRider();
        }
        IPet newRider = pt.getNewPetInstance(this.getOwner());
        if (newRider != null) {
            this.rider = ((com.dsh105.echopet.api.pet.Pet) (newRider));
            this.rider.setRider();
            new org.bukkit.scheduler.BukkitRunnable() {
                @java.lang.Override
                public void run() {
                    getCraftPet().setPassenger(Pet.this.rider.getCraftPet());
                    com.dsh105.echopet.compat.api.plugin.EchoPet.getSqlManager().saveToDatabase(Pet.this.rider, true);
                }
            }.runTaskLater(com.dsh105.echopet.compat.api.plugin.EchoPet.getPlugin(), 5L);
        }
        return this.rider;
    }
}