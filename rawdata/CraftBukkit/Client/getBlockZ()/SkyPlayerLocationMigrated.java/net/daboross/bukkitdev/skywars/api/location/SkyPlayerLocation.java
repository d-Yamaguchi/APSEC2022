/* Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars.api.location;
/**
 *
 *
 * @author daboross
 */
@org.bukkit.configuration.serialization.SerializableAs("SkyLocationAccurate")
public class SkyPlayerLocation implements org.bukkit.configuration.serialization.ConfigurationSerializable {
    public final double x;

    public final double y;

    public final double z;

    public final java.lang.String world;

    public SkyPlayerLocation(double x, double y, double z, java.lang.String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world.toLowerCase();
    }

    public SkyPlayerLocation(org.bukkit.block.Block block) {
        this(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
    }

    public SkyPlayerLocation(org.bukkit.Location location) {
        this(location.getBlockX(), location.getBlockY(), getBlock(), location.getWorld().getName());
    }

    public SkyPlayerLocation(org.bukkit.entity.Entity entity) {
        this(entity.getLocation());
    }

    public net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation add(double x, double y, double z) {
        return new net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation(this.x + x, this.y + y, this.z + z, world);
    }

    public net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation add(net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation location) {
        return new net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation add(net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation location) {
        return new net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation round() {
        return new net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation(((int) (x)), ((int) (y)), ((int) (z)), world);
    }

    public org.bukkit.Location toLocation() {
        org.bukkit.World bukkitWorld = org.bukkit.Bukkit.getWorld(world);
        if (bukkitWorld == null) {
            org.bukkit.Bukkit.getLogger().log(java.util.logging.Level.WARNING, "[SkyWars] World ''{0}'' not found!", world);
            return null;
        }
        return new org.bukkit.Location(bukkitWorld, x, y, z);
    }

    @java.lang.Override
    public java.util.Map<java.lang.String, java.lang.Object> serialize() {
        java.util.Map<java.lang.String, java.lang.Object> map = new java.util.HashMap<java.lang.String, java.lang.Object>();
        map.put("xpos", x);
        map.put("ypos", y);
        map.put("zpos", z);
        map.put("world", world);
        return map;
    }

    public static net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation deserialize(java.util.Map<java.lang.String, java.lang.Object> map) {
        java.lang.Object worldO = map.get("world");
        java.lang.Double x = net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation.get(map.get("xpos"));
        java.lang.Double y = net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation.get(map.get("ypos"));
        java.lang.Double z = net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation.get(map.get("zpos"));
        if (((((((x == null) || (y == null)) || (z == null)) || (worldO == null)) || (!(x instanceof java.lang.Double))) || (!(y instanceof java.lang.Double))) || (!(z instanceof java.lang.Double))) {
            return null;
        }
        java.lang.String world = worldO.toString();
        return new net.daboross.bukkitdev.skywars.api.location.SkyPlayerLocation(x, y, z, world);
    }

    private static java.lang.Double get(java.lang.Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof java.lang.Integer) {
            return java.lang.Double.valueOf(((java.lang.Integer) (o)).doubleValue());
        }
        if (o instanceof java.lang.Double) {
            return ((java.lang.Double) (o));
        }
        return null;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation)) {
            return false;
        }
        net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation l = ((net.daboross.bukkitdev.skywars.api.location.SkyBlockLocation) (obj));
        return (((l.x == x) && (l.y == y)) && (l.z == z)) && l.world.equals(world);
    }

    @java.lang.Override
    public int hashCode() {
        int hash = 3;
        hash = (97 * hash) + ((int) (java.lang.Double.doubleToLongBits(this.x) ^ (java.lang.Double.doubleToLongBits(this.x) >>> 32)));
        hash = (97 * hash) + ((int) (java.lang.Double.doubleToLongBits(this.y) ^ (java.lang.Double.doubleToLongBits(this.y) >>> 32)));
        hash = (97 * hash) + ((int) (java.lang.Double.doubleToLongBits(this.z) ^ (java.lang.Double.doubleToLongBits(this.z) >>> 32)));
        hash = (97 * hash) + (this.world != null ? this.world.hashCode() : 0);
        return hash;
    }
}