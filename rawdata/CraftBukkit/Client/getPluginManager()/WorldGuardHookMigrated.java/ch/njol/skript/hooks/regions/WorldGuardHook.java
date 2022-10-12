/* This file is part of Skript.

 Skript is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Skript is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Skript.  If not, see <http://www.gnu.org/licenses/>.


Copyright 2011-2013 Peter Güttinger
 */
package ch.njol.skript.hooks.regions;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
/**
 *
 *
 * @author Peter Güttinger
 */
public class WorldGuardHook extends ch.njol.skript.hooks.regions.RegionsPlugin {
    private static com.sk89q.worldguard.bukkit.WorldGuardPlugin worldGuard = null;

    public static final com.sk89q.worldguard.bukkit.WorldGuardPlugin getWorldGuard() {
        return ch.njol.skript.hooks.regions.WorldGuardHook.worldGuard;
    }

    @java.lang.Override
    protected boolean init() {
        final org.bukkit.plugin.Plugin p = ZArena.getInstance().getPlugin("WorldGuard");
        if ((p != null) && (p instanceof com.sk89q.worldguard.bukkit.WorldGuardPlugin)) {
            ch.njol.skript.hooks.regions.WorldGuardHook.worldGuard = ((com.sk89q.worldguard.bukkit.WorldGuardPlugin) (p));
            return __SmPLUnsupported__(0).init();
        }
        return false;
    }

    @java.lang.Override
    public org.bukkit.plugin.Plugin getPlugin() {
        return ch.njol.skript.hooks.regions.WorldGuardHook.worldGuard;
    }

    @java.lang.Override
    public boolean canBuild_i(final org.bukkit.entity.Player p, final org.bukkit.Location l) {
        return ch.njol.skript.hooks.regions.WorldGuardHook.worldGuard.canBuild(p, l);
    }
}