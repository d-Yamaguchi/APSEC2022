/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars.api.location;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;

/**
 *
 * @author daboross
 */
@SerializableAs("SkyLocationAccurate")
public class SkyPlayerLocation implements ConfigurationSerializable {

    public final double x;
    public final double y;
    public final double z;
    public final String world;

    public SkyPlayerLocation(double x, double y, double z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world.toLowerCase();
    }

    public SkyPlayerLocation(Block block) {
        this(block.getX(), block.getY(), block.getZ(), block.getWorld().getName());
    }

    public SkyPlayerLocation(Location location) {
        this(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getWorld().getName());
    }

    public SkyPlayerLocation(Entity entity) {
        this(entity.getLocation());
    }

    public SkyPlayerLocation add(double x, double y, double z) {
        return new SkyPlayerLocation(this.x + x, this.y + y, this.z + z, world);
    }

    public SkyPlayerLocation add(SkyBlockLocation location) {
        return new SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public SkyPlayerLocation add(SkyPlayerLocation location) {
        return new SkyPlayerLocation(this.x + location.x, this.y + location.y, this.z + location.z, world);
    }

    public SkyBlockLocation round() {
        return new SkyBlockLocation((int) x, (int) y, (int) z, world);
    }

    public Location toLocation() {
        World bukkitWorld = Bukkit.getWorld(world);
        if (bukkitWorld == null) {
            Bukkit.getLogger().log(Level.WARNING, "[SkyWars] World ''{0}'' not found!", world);
            return null;
        }
        return new Location(bukkitWorld, x, y, z);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("xpos", x);
        map.put("ypos", y);
        map.put("zpos", z);
        map.put("world", world);
        return map;
    }

    public static SkyPlayerLocation deserialize(Map<String, Object> map) {
        Object worldO = map.get("world");
        Double x = get(map.get("xpos")),
                y = get(map.get("ypos")),
                z = get(map.get("zpos"));
        if (x == null || y == null || z == null || worldO == null
                || !(x instanceof Double)
                || !(y instanceof Double)
                || !(z instanceof Double)) {
            return null;
        }
        String world = worldO.toString();
        return new SkyPlayerLocation(x, y, z, world);
    }

    private static Double get(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof Integer) {
            return Double.valueOf(((Integer) o).doubleValue());
        }
        if (o instanceof Double) {
            return (Double) o;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SkyBlockLocation)) {
            return false;
        }
        SkyBlockLocation l = (SkyBlockLocation) obj;
        return l.x == x && l.y == y && l.z == z && l.world.equals(world);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        hash = 97 * hash + (this.world != null ? this.world.hashCode() : 0);
        return hash;
    }
}
