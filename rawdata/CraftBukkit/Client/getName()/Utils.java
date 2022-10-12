package de.JeterLP.ChatManager;

import de.JeterLP.ChatManager.Plugins.Factions;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author TheJeterLP
 */
public class Utils {

        private final Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
        private final Pattern chatMagicPattern = Pattern.compile("(?i)&([K])");
        private final Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
        private final Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
        private final Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
        private final Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
        private final Pattern chatResetPattern = Pattern.compile("(?i)&([R])");
        private final String permissionChatColor = "chatex.chat.color";
        private final String permissionChatMagic = "chatex.chat.magic";
        private final String permissionChatBold = "chatex.chat.bold";
        private final String permissionChatStrikethrough = "chatex.chat.strikethrough";
        private final String permissionChatUnderline = "chatex.chat.underline";
        private final String permissionChatItalic = "chatex.chat.italic";
        private final String permissionChatReset = "chatex.chat.reset";

        public String translateColorCodes(String string, Player p) {
                if (string == null) {
                        return "";
                }
                String newstring = string;
                if (p.hasPermission(this.permissionChatColor)) newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
                if (p.hasPermission(this.permissionChatMagic)) newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1");
                if (p.hasPermission(this.permissionChatBold)) newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
                if (p.hasPermission(this.permissionChatStrikethrough)) newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
                if (p.hasPermission(this.permissionChatUnderline)) newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
                if (p.hasPermission(this.permissionChatItalic)) newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
                if (p.hasPermission(this.permissionChatReset)) newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
                return newstring;
        }

        public String replaceColors(String message) {
                return message.replaceAll("&((?i)[0-9a-fk-or])", "§$1");
        }

        public List<Player> getLocalRecipients(Player sender) {
                Location playerLocation = sender.getLocation();
                List<Player> recipients = new LinkedList<Player>();
                double squaredDistance = Math.pow(Config.RANGE.getDouble(), 2);
                for (Player recipient : Bukkit.getServer().getOnlinePlayers()) {
                        if (!recipient.getWorld().equals(sender.getWorld())) {
                                continue;
                        }
                        if (playerLocation.distanceSquared(recipient.getLocation()) > squaredDistance) {
                                continue;
                        }
                        recipients.add(recipient);
                }
                return recipients;
        }

        public String replacePlayerPlaceholders(Player player, String format) {
                String result = format;
                result = result.replace("%prefix", this.replaceColors(ChatManager.getManager().getPrefix(
                                player,
                                player.getWorld().getName(),
                                Config.MULTIPREFIXES.getBoolean(),
                                Config.PREPENDPREFIX.getBoolean())));
                result = result.replace("%suffix", this.replaceColors(ChatManager.getManager().getSuffix(
                                player,
                                player.getWorld().getName(),
                                Config.MULTISUFFIXES.getBoolean(),
                                Config.PREPENDSUFFIX.getBoolean())));
                result = result.replace("%world", this.replaceColors(player.getWorld().getName()));
                result = result.replace("%player", player.getName());
                result = result.replace("%group", this.replaceColors(ChatManager.getManager().getGroupNames(player, player.getWorld().getName())[0]));
                result = this.replaceColors(replaceTime(result));
                result = this.replaceColors(replaceFaction(result, player));
                return result;
        }

        private String replaceTime(String message) {
                Calendar calendar = Calendar.getInstance();

                if (message.contains("%h")) {
                        final String hour = String.valueOf(calendar.get(Calendar.HOUR));
                        message = message.replace("%h", hour);
                }

                if (message.contains("%H")) {
                        final String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
                        message = message.replace("%H", hour);
                }

                if (message.contains("%i")) {
                        final String minute = String.valueOf(calendar.get(Calendar.MINUTE));
                        message = message.replace("%i", minute);
                }

                if (message.contains("%s")) {
                        final String second = String.valueOf(calendar.get(Calendar.SECOND));
                        message = message.replace("%s", second);
                }

                if (message.contains("%a")) {
                        message = message.replace("%a", (calendar.get(Calendar.AM_PM) == 0) ? "am" : "pm");
                }

                if (message.contains("%A")) {
                        message = message.replace("%A", (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
                }

                if (message.contains("%m")) {
                        final String month = String.valueOf(calendar.get(Calendar.MONTH));
                        message = message.replace("%m", month);
                }

                if (message.contains("%M")) {
                        String month = "";
                        final int monat = calendar.get(Calendar.MONTH) + 1;
                        if (monat == 1) {
                                month = "January";
                        } else if (monat == 2) {
                                month = "February";
                        } else if (monat == 3) {
                                month = "March";
                        } else if (monat == 4) {
                                month = "April";
                        } else if (monat == 5) {
                                month = "May";
                        } else if (monat == 6) {
                                month = "June";
                        } else if (monat == 7) {
                                month = "July";
                        } else if (monat == 8) {
                                month = "August";
                        } else if (monat == 9) {
                                month = "September";
                        } else if (monat == 10) {
                                month = "October";
                        } else if (monat == 11) {
                                month = "November";
                        } else if (monat == 12) {
                                month = "December";
                        }
                        message = message.replace("%M", month);
                }

                if (message.contains("%y")) {
                        final String year = String.valueOf(calendar.get(Calendar.YEAR));
                        message = message.replace("%m", year);
                }

                if (message.contains("%Y")) {
                        int year = calendar.get(Calendar.YEAR);
                        String year_new = String.valueOf(year);
                        year_new = year_new.replace("19", "").replace("20", "");
                        message = message.replace("%Y", year_new);
                }

                if (message.contains("%d")) {
                        final String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
                        message = message.replace("%d", day);
                }

                if (message.contains("%D")) {
                        final String day = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
                        message = message.replace("%D", day);
                }

                message = this.replaceColors(message);
                return message;
        }

        private String replaceFaction(String format, Player player) {
                String result = format;
                if (ChatManager.getHook().checkFactions()) {
                        result = result.replace("%faction", this.replaceColors(Factions.getFactionName(player)));
                } else {
                        result = result.replace("%faction", "");
                }
                result = this.replaceColors(result);
                return result;
        }
}
