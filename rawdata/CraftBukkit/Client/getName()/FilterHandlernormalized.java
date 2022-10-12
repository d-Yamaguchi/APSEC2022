public java.lang.String execute(final org.bukkit.entity.Player player, final java.lang.String chatMessage) {
    if (!player.hasPermission("prospam.nocheck")) {
        org.bukkit.entity.Player _CVAR0 = player;
        java.lang.String _CVAR1 = _CVAR0.getName();
        final de.rob1n.prospam.chatter.Chatter chatter = de.rob1n.prospam.chatter.ChatterHandler.getChatter(_CVAR1);
        java.lang.String previouslyFilteredMessage = chatMessage;
        java.lang.String filteredMessage = chatMessage;
        final java.util.List<java.lang.String> filters_triggered = new java.util.ArrayList<java.lang.String>();
        try {
            if (settings.filter_enabled_chars) {
                filteredMessage = filterChars.execute(chatter, filteredMessage);
                // check if this filter got triggert
                if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage))) {
                    chatter.increaseSpamCountChars();
                    filters_triggered.add("Chars");
                    if (settings.trigger_enabled_chars) {
                        trigger.execute(chatter, chatter.getSpamCountChars(), settings.trigger_chars);
                    }
                }
                previouslyFilteredMessage = filteredMessage;
                if (filteredMessage == null) {
                    return null;
                }
            }
            if (settings.filter_enabled_caps) {
                filteredMessage = filterCaps.execute(chatter, filteredMessage);
                // check if this filter got triggert
                if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage))) {
                    chatter.increaseSpamCountCaps();
                    filters_triggered.add("Caps");
                    if (settings.trigger_enabled_caps) {
                        trigger.execute(chatter, chatter.getSpamCountCaps(), settings.trigger_caps);
                    }
                }
                previouslyFilteredMessage = filteredMessage;
                if (filteredMessage == null) {
                    return null;
                }
            }
            if (settings.filter_enabled_urls) {
                filteredMessage = filterUrls.execute(chatter, filteredMessage);
                // check if this filter got triggert
                if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage))) {
                    chatter.increaseSpamCountUrls();
                    filters_triggered.add("URLs");
                    if (settings.trigger_enabled_urls) {
                        trigger.execute(chatter, chatter.getSpamCountUrls(), settings.trigger_urls);
                    }
                }
                previouslyFilteredMessage = filteredMessage;
                if (filteredMessage == null) {
                    return null;
                }
            }
            if (settings.filter_enabled_flood) {
                filteredMessage = filterFlood.execute(chatter, filteredMessage);
                if (filteredMessage == null) {
                    chatter.increaseSpamCountFlood();
                    filters_triggered.add("Flood");
                    org.bukkit.entity.Player _CVAR2 = player;
                    java.lang.String _CVAR3 = _CVAR2.getName();
                    java.util.List<java.lang.String> _CVAR4 = filters_triggered;
                    java.lang.String _CVAR5 = chatMessage;
                    informSpam(_CVAR3, _CVAR4, _CVAR5);
                    if (settings.trigger_enabled_flood) {
                        trigger.execute(chatter, chatter.getSpamCountFlood(), settings.trigger_flood);
                    }
                    if ((strings.filter_lines_locked != null) && (!strings.filter_lines_locked.isEmpty())) {
                        player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', strings.filter_lines_locked));
                    }
                    return null;
                }
            }
            if (settings.filter_enabled_similar) {
                filteredMessage = filterSimilar.execute(chatter, filteredMessage);
                if (filteredMessage == null) {
                    chatter.increaseSpamCountSimilar();
                    filters_triggered.add("Similar");
                    org.bukkit.entity.Player _CVAR6 = player;
                    java.lang.String _CVAR7 = _CVAR6.getName();
                    java.util.List<java.lang.String> _CVAR8 = filters_triggered;
                    java.lang.String _CVAR9 = chatMessage;
                    informSpam(_CVAR7, _CVAR8, _CVAR9);
                    if (settings.trigger_enabled_similar) {
                        trigger.execute(chatter, chatter.getSpamCountSimilar(), settings.trigger_similar);
                    }
                    if ((strings.filter_lines_similar != null) && (!strings.filter_lines_similar.isEmpty())) {
                        player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', strings.filter_lines_similar));
                    }
                    return null;
                }
            }
            if (settings.filter_enabled_blacklist) {
                filteredMessage = filterBlacklist.execute(chatter, filteredMessage);
                // check if this filter got triggert
                if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage))) {
                    chatter.increaseSpamCountBlacklist();
                    filters_triggered.add("Blacklist");
                    if (settings.trigger_enabled_blacklist) {
                        trigger.execute(chatter, chatter.getSpamCountBlacklist(), settings.trigger_blacklist);
                    }
                }
                if (filteredMessage == null) {
                    org.bukkit.entity.Player _CVAR10 = player;
                    java.lang.String _CVAR11 = _CVAR10.getName();
                    java.util.List<java.lang.String> _CVAR12 = filters_triggered;
                    java.lang.String _CVAR13 = chatMessage;
                    informSpam(_CVAR11, _CVAR12, _CVAR13);
                    if ((strings.blacklist_lines_ignored != null) && (!strings.blacklist_lines_ignored.isEmpty())) {
                        player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', strings.blacklist_lines_ignored));
                    }
                    return null;
                }
            }
            if (filters_triggered.size() > 0) {
                org.bukkit.entity.Player _CVAR14 = player;
                java.lang.String _CVAR15 = _CVAR14.getName();
                java.util.List<java.lang.String> _CVAR16 = filters_triggered;
                java.lang.String _CVAR17 = chatMessage;
                informSpam(_CVAR15, _CVAR16, _CVAR17);
            }
            return filteredMessage;
        } catch (java.lang.IllegalArgumentException e) {
            plugin.getLogger().severe(e.getMessage());
        } catch (java.lang.NullPointerException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }
    return chatMessage;
}