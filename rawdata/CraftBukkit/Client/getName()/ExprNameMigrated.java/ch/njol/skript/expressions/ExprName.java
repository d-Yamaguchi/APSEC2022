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


Copyright 2011, 2012 Peter Güttinger
 */
package ch.njol.skript.expressions;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
/**
 *
 *
 * @author Peter Güttinger
 */
public class ExprName extends ch.njol.skript.expressions.base.SimplePropertyExpression<org.bukkit.entity.Player, java.lang.String> {
    private static final long serialVersionUID = 8530462959975535372L;

    static {
        register(ch.njol.skript.expressions.ExprName.class, java.lang.String.class, "name", "players");
    }

    @java.lang.Override
    public java.lang.Class<java.lang.String> getReturnType() {
        return java.lang.String.class;
    }

    @java.lang.Override
    protected java.lang.String getPropertyName() {
        return "name";
    }

    @java.lang.Override
    public java.lang.String convert(final org.bukkit.entity.Player p) {
        return store.loadAchievements(mPlayer);
    }
}