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
package ch.njol.skript.classes.data;
import ch.njol.skript.Aliases;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumParser;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleLiteral;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.ItemType;
import ch.njol.skript.util.PotionEffectUtils;
import ch.njol.skript.util.StringMode;
import ch.njol.skript.util.Utils;
import ch.njol.util.StringUtils;
/**
 *
 *
 * @author Peter Güttinger
 */
public class BukkitClasses {
    public BukkitClasses() {
    }

    static {
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.entity.Entity>(org.bukkit.entity.Entity.class, "entity", "entity").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.entity.Entity>(org.bukkit.entity.Entity.class)).parser(new ch.njol.skript.classes.Parser<org.bukkit.entity.Entity>() {
            @java.lang.Override
            public org.bukkit.entity.Entity parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return false;
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.entity.Entity e) {
                World w = b.getWorld();
                return "entity:" + w.getName();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "entity:[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.entity.Entity e) {
                return ch.njol.skript.entity.EntityData.toString(e);
            }
        }).changer(DefaultChangers.entityChanger));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.entity.LivingEntity>(org.bukkit.entity.LivingEntity.class, "livingentity", "living entity").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.entity.LivingEntity>(org.bukkit.entity.LivingEntity.class)).changer(DefaultChangers.entityChanger));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.entity.Projectile>(org.bukkit.entity.Projectile.class, "projectile", "projectile").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.entity.Projectile>(org.bukkit.entity.Projectile.class)).changer(DefaultChangers.nonLivingEntityChanger));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.block.Block>(org.bukkit.block.Block.class, "block", "block").user("block").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.block.Block>(org.bukkit.block.Block.class)).parser(new ch.njol.skript.classes.Parser<org.bukkit.block.Block>() {
            @java.lang.Override
            public org.bukkit.block.Block parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return false;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.block.Block b) {
                return ch.njol.skript.util.ItemType.toString(new org.bukkit.inventory.ItemStack(b.getTypeId(), 1, b.getState().getRawData()));
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.block.Block b) {
                return (((((b.getWorld().getName() + ":") + b.getX()) + ",") + b.getY()) + ",") + b.getZ();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "\\S:-?\\d+,-?\\d+,-?\\d+";
            }

            @java.lang.Override
            public java.lang.String getDebugMessage(final org.bukkit.block.Block b) {
                return ((((((((toString(b) + " block (") + b.getWorld().getName()) + ":") + b.getX()) + ",") + b.getY()) + ",") + b.getZ()) + ")";
            }
        }).changer(DefaultChangers.blockChanger).serializer(new ch.njol.skript.classes.Serializer<org.bukkit.block.Block>() {
            @java.lang.Override
            public java.lang.String serialize(final org.bukkit.block.Block b) {
                return (((((b.getWorld().getName() + ":") + b.getX()) + ",") + b.getY()) + ",") + b.getZ();
            }

            @java.lang.Override
            public org.bukkit.block.Block deserialize(final java.lang.String s) {
                final java.lang.String[] split = s.split("[:,]");
                if (split.length != 4)
                    return null;

                final org.bukkit.World w = org.bukkit.Bukkit.getWorld(split[0]);
                if (w == null) {
                    return null;
                }
                try {
                    final int[] l = new int[3];
                    for (int i = 0; i < 3; i++)
                        l[i] = java.lang.Integer.parseInt(split[i + 1]);

                    return w.getBlockAt(l[0], l[1], l[2]);
                } catch (final java.lang.NumberFormatException e) {
                    return null;
                }
            }
        }));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.Location>(org.bukkit.Location.class, "location", "location").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.Location>(org.bukkit.Location.class)).parser(new ch.njol.skript.classes.Parser<org.bukkit.Location>() {
            @java.lang.Override
            public org.bukkit.Location parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return false;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.Location l) {
                return (((("x: " + ch.njol.util.StringUtils.toString(l.getX(), Skript.NUMBERACCURACY)) + ", y: ") + ch.njol.util.StringUtils.toString(l.getY(), Skript.NUMBERACCURACY)) + ", z: ") + ch.njol.util.StringUtils.toString(l.getZ(), Skript.NUMBERACCURACY);
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.Location l) {
                return (((((l.getWorld().getName() + ":") + l.getX()) + ",") + l.getY()) + ",") + l.getZ();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "\\S:-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?";
            }

            @java.lang.Override
            public java.lang.String getDebugMessage(final org.bukkit.Location l) {
                return ((((((((((("(" + l.getWorld().getName()) + ":") + l.getX()) + ",") + l.getY()) + ",") + l.getZ()) + "|yaw=") + l.getYaw()) + "/pitch=") + l.getPitch()) + ")";
            }
        }).serializer(new ch.njol.skript.classes.Serializer<org.bukkit.Location>() {
            @java.lang.Override
            public java.lang.String serialize(final org.bukkit.Location l) {
                return (((((((((l.getWorld().getName() + ":") + l.getX()) + ",") + l.getY()) + ",") + l.getZ()) + "|") + l.getYaw()) + "/") + l.getPitch();
            }

            @java.lang.Override
            public org.bukkit.Location deserialize(final java.lang.String s) {
                final java.lang.String[] split = s.split("[:,|/]");
                if (split.length != 6)
                    return null;

                final org.bukkit.World w = org.bukkit.Bukkit.getWorld(split[0]);
                if (w == null)
                    return null;

                try {
                    final double[] l = new double[5];
                    for (int i = 0; i < 5; i++)
                        l[i] = java.lang.Double.parseDouble(split[i + 1]);

                    return new org.bukkit.Location(w, l[0], l[1], l[2], ((float) (l[3])), ((float) (l[4])));
                } catch (final java.lang.NumberFormatException e) {
                    return null;
                }
            }
        }));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.World>(org.bukkit.World.class, "world", "world").user("worlds?").after("string").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.World>(org.bukkit.World.class)).parser(new ch.njol.skript.classes.Parser<org.bukkit.World>() {
            @java.lang.Override
            public org.bukkit.World parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                if (context == ch.njol.skript.lang.ParseContext.COMMAND)
                    return org.bukkit.Bukkit.getWorld(s);

                if (!s.matches("\".+\""))
                    return null;

                return org.bukkit.Bukkit.getWorld(s.substring(1, s.length() - 1));
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.World w) {
                return w.getName();
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.World o) {
                return o.getName();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "\\S+";
            }
        }).serializer(new ch.njol.skript.classes.Serializer<org.bukkit.World>() {
            @java.lang.Override
            public java.lang.String serialize(final org.bukkit.World w) {
                return w.getName();
            }

            @java.lang.Override
            public org.bukkit.World deserialize(final java.lang.String s) {
                return org.bukkit.Bukkit.getWorld(s);
            }
        }));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.inventory.Inventory>(org.bukkit.inventory.Inventory.class, "inventory", "inventory").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.inventory.Inventory>(org.bukkit.inventory.Inventory.class)).parser(new ch.njol.skript.classes.Parser<org.bukkit.inventory.Inventory>() {
            @java.lang.Override
            public org.bukkit.inventory.Inventory parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return false;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.inventory.Inventory i) {
                return "inventory of " + ch.njol.skript.registrations.Classes.toString(i.getHolder());
            }

            @java.lang.Override
            public java.lang.String getDebugMessage(final org.bukkit.inventory.Inventory i) {
                return "inventory of " + ch.njol.skript.registrations.Classes.getDebugMessage(i.getHolder());
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.inventory.Inventory o) {
                return "inventory of " + ch.njol.skript.registrations.Classes.toString(o.getHolder(), StringMode.VARIABLE_NAME, false);
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "inventory of .+";
            }
        }).changer(DefaultChangers.inventoryChanger));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.entity.Player>(org.bukkit.entity.Player.class, "player", "player").user("players?").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.entity.Player>(org.bukkit.entity.Player.class)).after("string").parser(new ch.njol.skript.classes.Parser<org.bukkit.entity.Player>() {
            @java.lang.Override
            public org.bukkit.entity.Player parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                if (context == ch.njol.skript.lang.ParseContext.COMMAND) {
                    final java.util.List<org.bukkit.entity.Player> ps = org.bukkit.Bukkit.matchPlayer(s);
                    if (ps.size() == 1)
                        return ps.get(0);

                    if (ps.size() == 0)
                        ch.njol.skript.Skript.error(("There is no player online whose name starts with '" + s) + "'");
                    else
                        ch.njol.skript.Skript.error(("There are several players online whose names start with '" + s) + "'");

                    return null;
                }
                // if (s.matches("\"\\S+\""))
                // return Bukkit.getPlayerExact(s.substring(1, s.length() - 1));
                assert false;
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return context == ch.njol.skript.lang.ParseContext.COMMAND;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.entity.Player p) {
                return p.getDisplayName() + org.bukkit.ChatColor.RESET;
            }

            @java.lang.Override
            public java.lang.String toCommandString(final org.bukkit.entity.Player p) {
                return p.getName();
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.entity.Player p) {
                return p.getName();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "\\S+";
            }

            @java.lang.Override
            public java.lang.String getDebugMessage(final org.bukkit.entity.Player p) {
                return (p.getName() + " ") + ch.njol.skript.registrations.Classes.getDebugMessage(p.getLocation());
            }
        }).changer(DefaultChangers.playerChanger).serializeAs(org.bukkit.OfflinePlayer.class));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.OfflinePlayer>(org.bukkit.OfflinePlayer.class, "offlineplayer", "player").user("offline ?players?").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.OfflinePlayer>(org.bukkit.OfflinePlayer.class)).after("string").parser(new ch.njol.skript.classes.Parser<org.bukkit.OfflinePlayer>() {
            @java.lang.Override
            public org.bukkit.OfflinePlayer parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                if (context == ch.njol.skript.lang.ParseContext.COMMAND) {
                    if (!s.matches("\\S+"))
                        return null;

                    return org.bukkit.Bukkit.getOfflinePlayer(s);
                }
                // if (s.matches("\"\\S+\""))
                // return Bukkit.getOfflinePlayer(s.substring(1, s.length() - 1));
                assert false;
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return context == ch.njol.skript.lang.ParseContext.COMMAND;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.OfflinePlayer p) {
                if (p.isOnline())
                    return p.getPlayer().getDisplayName() + org.bukkit.ChatColor.RESET;

                return p.getName();
            }

            @java.lang.Override
            public java.lang.String toCommandString(final org.bukkit.OfflinePlayer p) {
                return p.getName();
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.OfflinePlayer p) {
                return p.getName();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "\\S+";
            }

            @java.lang.Override
            public java.lang.String getDebugMessage(final org.bukkit.OfflinePlayer p) {
                if (p.isOnline())
                    return ch.njol.skript.registrations.Classes.getDebugMessage(p.getPlayer());

                return p.getName();
            }
        }).serializer(new ch.njol.skript.classes.Serializer<org.bukkit.OfflinePlayer>() {
            @java.lang.Override
            public java.lang.String serialize(final org.bukkit.OfflinePlayer p) {
                return p.getName();
            }

            @java.lang.Override
            public org.bukkit.OfflinePlayer deserialize(final java.lang.String s) {
                return org.bukkit.Bukkit.getOfflinePlayer(s);
            }
        }));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.command.CommandSender>(org.bukkit.command.CommandSender.class, "commandsender", "player/console").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.command.CommandSender>(org.bukkit.command.CommandSender.class)).parser(new ch.njol.skript.classes.Parser<org.bukkit.command.CommandSender>() {
            @java.lang.Override
            public org.bukkit.command.CommandSender parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return null;
            }

            @java.lang.Override
            public boolean canParse(final ch.njol.skript.lang.ParseContext context) {
                return false;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.command.CommandSender s) {
                return s.getName();
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.command.CommandSender o) {
                return o.getName();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "\\S+";
            }
        }));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.block.BlockFace>(org.bukkit.block.BlockFace.class, "blockface", "direction").user("directions?").parser(new ch.njol.skript.classes.Parser<org.bukkit.block.BlockFace>() {
            @java.lang.Override
            public org.bukkit.block.BlockFace parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return ch.njol.skript.util.Utils.getBlockFace(s, true);
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.block.BlockFace o) {
                return o.toString().toLowerCase().replace('_', ' ');
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.block.BlockFace o) {
                return o.toString().toLowerCase(java.util.Locale.ENGLISH).replace('_', ' ');
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "[a-z ]+";
            }
        }).serializer(new ch.njol.skript.classes.EnumSerializer<org.bukkit.block.BlockFace>(org.bukkit.block.BlockFace.class)));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.inventory.InventoryHolder>(org.bukkit.inventory.InventoryHolder.class, "inventoryholder", "inventory holder").defaultExpression(new ch.njol.skript.expressions.base.EventValueExpression<org.bukkit.inventory.InventoryHolder>(org.bukkit.inventory.InventoryHolder.class)));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.GameMode>(org.bukkit.GameMode.class, "gamemode", "game mode").user("game ?modes?").defaultExpression(new ch.njol.skript.lang.util.SimpleLiteral<org.bukkit.GameMode>(org.bukkit.GameMode.SURVIVAL, true)).parser(new ch.njol.skript.classes.Parser<org.bukkit.GameMode>() {
            @java.lang.Override
            public org.bukkit.GameMode parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                try {
                    return org.bukkit.GameMode.valueOf(s.toUpperCase());
                } catch (final java.lang.IllegalArgumentException e) {
                    return null;
                }
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.GameMode m) {
                return m.toString().toLowerCase();
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.GameMode o) {
                return o.toString().toLowerCase(java.util.Locale.ENGLISH);
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "[a-z]+";
            }
        }).serializer(new ch.njol.skript.classes.EnumSerializer<org.bukkit.GameMode>(org.bukkit.GameMode.class)));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.inventory.ItemStack>(org.bukkit.inventory.ItemStack.class, "itemstack", "material").user("item", "material").after("number").parser(new ch.njol.skript.classes.Parser<org.bukkit.inventory.ItemStack>() {
            @java.lang.Override
            public org.bukkit.inventory.ItemStack parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                ch.njol.skript.util.ItemType t = ch.njol.skript.Aliases.parseItemType(s);
                if (t == null)
                    return null;

                t = t.getItem();
                if (t.numTypes() != 1) {
                    ch.njol.skript.Skript.error(("'" + s) + "' represents multiple materials");
                    return null;
                }
                if (!t.getTypes().get(0).hasDataRange())
                    return t.getRandom();

                if (t.getTypes().get(0).dataMin > 0) {
                    ch.njol.skript.Skript.error(("'" + s) + "' represents multiple materials");
                    return null;
                }
                final org.bukkit.inventory.ItemStack i = t.getRandom();
                i.setDurability(((short) (0)));
                return i;
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.inventory.ItemStack i) {
                return ch.njol.skript.util.ItemType.toString(i);
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.inventory.ItemStack i) {
                final java.lang.StringBuilder b = new java.lang.StringBuilder("item:");
                b.append(i.getType().name());
                b.append(":" + i.getDurability());
                b.append("*" + i.getAmount());
                for (final java.util.Map.Entry<org.bukkit.enchantments.Enchantment, java.lang.Integer> e : i.getEnchantments().entrySet()) {
                    b.append("#" + e.getKey().getId());
                    b.append(":" + e.getValue());
                }
                return b.toString();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return "item:.+";
            }
        }).serializer(new ch.njol.skript.classes.Serializer<org.bukkit.inventory.ItemStack>() {
            @java.lang.Override
            public java.lang.String serialize(final org.bukkit.inventory.ItemStack i) {
                final java.lang.StringBuilder b = new java.lang.StringBuilder();
                b.append(i.getTypeId());
                b.append(":" + i.getDurability());
                b.append("*" + i.getAmount());
                for (final java.util.Map.Entry<org.bukkit.enchantments.Enchantment, java.lang.Integer> e : i.getEnchantments().entrySet()) {
                    b.append("#" + e.getKey().getId());
                    b.append(":" + e.getValue());
                }
                return b.toString();
            }

            @java.lang.Override
            public org.bukkit.inventory.ItemStack deserialize(final java.lang.String s) {
                final java.lang.String[] split = s.split("[:*#]");
                if ((split.length < 3) || ((split.length % 2) != 1))
                    return null;

                int typeId = -1;
                try {
                    typeId = org.bukkit.Material.valueOf(split[0]).getId();
                } catch (final java.lang.IllegalArgumentException e) {
                }
                try {
                    final org.bukkit.inventory.ItemStack is = new org.bukkit.inventory.ItemStack(typeId == (-1) ? java.lang.Integer.parseInt(split[0]) : typeId, java.lang.Integer.parseInt(split[2]), java.lang.Short.parseShort(split[1]));
                    for (int i = 3; i < split.length; i += 2) {
                        final org.bukkit.enchantments.Enchantment ench = org.bukkit.enchantments.Enchantment.getById(java.lang.Integer.parseInt(split[i]));
                        if (ench == null)
                            return null;

                        is.addUnsafeEnchantment(ench, java.lang.Integer.parseInt(split[i + 1]));
                    }
                    return is;
                } catch (final java.lang.NumberFormatException e) {
                    return null;
                } catch (final java.lang.IllegalArgumentException e) {
                    return null;
                }
            }
        }));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.block.Biome>(org.bukkit.block.Biome.class, "biome", "biome").user("biomes?").parser(new ch.njol.skript.classes.EnumParser<org.bukkit.block.Biome>(org.bukkit.block.Biome.class)).serializer(new ch.njol.skript.classes.EnumSerializer<org.bukkit.block.Biome>(org.bukkit.block.Biome.class)));
        ch.njol.skript.registrations.Classes.registerClass(new ch.njol.skript.classes.ClassInfo<org.bukkit.potion.PotionEffectType>(org.bukkit.potion.PotionEffectType.class, "potioneffecttype", "potion").user("potions?( ?effects?)?( ?types?)?").parser(new ch.njol.skript.classes.Parser<org.bukkit.potion.PotionEffectType>() {
            @java.lang.Override
            public org.bukkit.potion.PotionEffectType parse(final java.lang.String s, final ch.njol.skript.lang.ParseContext context) {
                return ch.njol.skript.util.PotionEffectUtils.parse(s);
            }

            @java.lang.Override
            public java.lang.String toString(final org.bukkit.potion.PotionEffectType o) {
                return ch.njol.skript.util.PotionEffectUtils.toString(o);
            }

            @java.lang.Override
            public java.lang.String toVariableNameString(final org.bukkit.potion.PotionEffectType o) {
                return o.getName();
            }

            @java.lang.Override
            public java.lang.String getVariableNamePattern() {
                return ".+";
            }
        }).serializer(new ch.njol.skript.classes.Serializer<org.bukkit.potion.PotionEffectType>() {
            @java.lang.Override
            public java.lang.String serialize(final org.bukkit.potion.PotionEffectType o) {
                return o.getName();
            }

            @java.lang.Override
            public org.bukkit.potion.PotionEffectType deserialize(final java.lang.String s) {
                return org.bukkit.potion.PotionEffectType.getByName(s);
            }
        }));
    }
}