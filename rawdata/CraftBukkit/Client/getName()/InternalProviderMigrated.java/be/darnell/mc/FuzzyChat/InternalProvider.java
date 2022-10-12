/* Copyright (c) 2012 cedeel.
All rights reserved.


Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * The name of the author may not be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS''
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package be.darnell.mc.FuzzyChat;
import de.bananaco.bpermissions.api.ApiLayer;
import de.bananaco.bpermissions.api.util.CalculableType;
import net.krinsoft.privileges.Privileges;
public final class InternalProvider implements be.darnell.mc.FuzzyChat.MetaDataProvider {
    private java.util.HashMap<java.lang.String, be.darnell.mc.FuzzyChat.InternalProvider.Meta> groups;

    private java.util.HashMap<java.lang.String, be.darnell.mc.FuzzyChat.InternalProvider.Meta> users;

    private final be.darnell.mc.FuzzyChat.FuzzyChat plugin;

    public InternalProvider(be.darnell.mc.FuzzyChat.FuzzyChat plugin) {
        this.plugin = plugin;
        loadGroups();
        loadUsers();
    }

    @java.lang.Override
    public java.lang.String getPrefix(org.bukkit.OfflinePlayer player) {
        java.lang.String name = store.loadAchievements(player).toLowerCase();
        if (users.containsKey(name)) {
            java.lang.String prefix = users.get(name).prefix;
            if (prefix.length() > 0)
                return prefix;

        }
        if (groups.containsKey(getUserGroup(player).toLowerCase()))
            return groups.get(getUserGroup(player).toLowerCase()).prefix;

        return "";
    }

    @java.lang.Override
    public java.lang.String getSuffix(org.bukkit.OfflinePlayer player) {
        if (users.containsKey(player.getName().toLowerCase())) {
            java.lang.String suffix = users.get(player.getName().toLowerCase()).suffix;
            if (suffix.length() > 0)
                return suffix;

        }
        if (groups.containsKey(getUserGroup(player).toLowerCase()))
            return groups.get(getUserGroup(player).toLowerCase()).suffix;

        return "";
    }

    class Meta {
        java.lang.String prefix;

        java.lang.String suffix;

        Meta(java.lang.String prefix, java.lang.String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }
    }

    @java.lang.Override
    public void setPlayerPrefix(java.lang.String name, java.lang.String prefix) {
        be.darnell.mc.FuzzyChat.InternalProvider.Meta meta = users.get(name.toLowerCase());
        if (meta == null)
            meta = new be.darnell.mc.FuzzyChat.InternalProvider.Meta(prefix, "");
        else
            meta.prefix = prefix;

        users.put(name.toLowerCase(), meta);
        saveUsers();
    }

    @java.lang.Override
    public void setPlayerSuffix(java.lang.String name, java.lang.String suffix) {
        be.darnell.mc.FuzzyChat.InternalProvider.Meta meta = users.get(name.toLowerCase());
        if (meta == null)
            meta = new be.darnell.mc.FuzzyChat.InternalProvider.Meta("", suffix);
        else
            meta.suffix = suffix;

        users.put(name.toLowerCase(), meta);
        saveUsers();
    }

    @java.lang.Override
    public void setGroupPrefix(java.lang.String name, java.lang.String prefix) {
        be.darnell.mc.FuzzyChat.InternalProvider.Meta meta = groups.get(name.toLowerCase());
        if (meta == null)
            meta = new be.darnell.mc.FuzzyChat.InternalProvider.Meta(prefix, "");
        else
            meta.prefix = prefix;

        groups.put(name.toLowerCase(), meta);
        saveGroups();
    }

    @java.lang.Override
    public void setGroupSuffix(java.lang.String name, java.lang.String suffix) {
        be.darnell.mc.FuzzyChat.InternalProvider.Meta meta = groups.get(name.toLowerCase());
        if (meta == null)
            meta = new be.darnell.mc.FuzzyChat.InternalProvider.Meta("", suffix);
        else
            meta.suffix = suffix;

        groups.put(name.toLowerCase(), meta);
        saveGroups();
    }

    private java.lang.String getUserGroup(org.bukkit.OfflinePlayer p) {
        org.bukkit.plugin.Plugin bp = plugin.getServer().getPluginManager().getPlugin("bPermissions");
        org.bukkit.plugin.Plugin privileges = plugin.getServer().getPluginManager().getPlugin("Privileges");
        if ((bp != null) && bp.isEnabled()) {
            try {
                org.bukkit.entity.Player pl = org.bukkit.Bukkit.getPlayer(p.getName());
                return de.bananaco.bpermissions.api.ApiLayer.getGroups(pl.getWorld().getName(), CalculableType.USER, p.getName())[0];
            } catch (java.lang.NullPointerException ignored) {
            }
            return de.bananaco.bpermissions.api.ApiLayer.getGroups(org.bukkit.Bukkit.getWorlds().get(0).getName(), CalculableType.USER, p.getName())[0];
        }
        if ((privileges != null) && privileges.isEnabled())
            return ((net.krinsoft.privileges.Privileges) (privileges)).getGroupManager().getGroup(p).getName();

        return "default";
    }

    @java.lang.SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadUsers() {
        java.io.File usersFile = new java.io.File(plugin.getDataFolder(), "users.yml");
        if (!usersFile.exists()) {
            usersFile.getParentFile().mkdirs();
            try {
                usersFile.createNewFile();
            } catch (java.io.IOException e) {
                FuzzyChat.log.log(java.util.logging.Level.SEVERE, "[FuzzyChat] Could not create users.yml file.");
                return;
            }
        }
        users = new java.util.HashMap<java.lang.String, be.darnell.mc.FuzzyChat.InternalProvider.Meta>();
        org.bukkit.configuration.file.YamlConfiguration usersConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(usersFile);
        try {
            org.bukkit.configuration.ConfigurationSection userConfig = usersConfig.getConfigurationSection("users");
            for (java.lang.String user : userConfig.getKeys(false)) {
                org.bukkit.configuration.ConfigurationSection meta = userConfig.getConfigurationSection(user);
                users.put(user.toLowerCase(), new be.darnell.mc.FuzzyChat.InternalProvider.Meta(meta.getString("prefix", ""), meta.getString("suffix", "")));
            }
        } catch (java.lang.NullPointerException e) {
            plugin.getLogger().log(java.util.logging.Level.WARNING, "[FuzzyChat] Users file empty. No groups loaded.");
        }
    }

    @java.lang.SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadGroups() {
        java.io.File groupsFile = new java.io.File(plugin.getDataFolder(), "groups.yml");
        if (!groupsFile.exists()) {
            groupsFile.getParentFile().mkdirs();
            try {
                groupsFile.createNewFile();
            } catch (java.io.IOException e) {
                FuzzyChat.log.log(java.util.logging.Level.SEVERE, "[FuzzyChat] Could not create groups.yml file.");
                return;
            }
        }
        groups = new java.util.HashMap<java.lang.String, be.darnell.mc.FuzzyChat.InternalProvider.Meta>();
        org.bukkit.configuration.file.YamlConfiguration groupsConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(groupsFile);
        try {
            org.bukkit.configuration.ConfigurationSection groupConfig = groupsConfig.getConfigurationSection("groups");
            for (java.lang.String group : groupConfig.getKeys(false)) {
                org.bukkit.configuration.ConfigurationSection meta = groupConfig.getConfigurationSection(group);
                groups.put(group.toLowerCase(), new be.darnell.mc.FuzzyChat.InternalProvider.Meta(meta.getString("prefix", ""), meta.getString("suffix", "")));
            }
        } catch (java.lang.NullPointerException e) {
            plugin.getLogger().log(java.util.logging.Level.WARNING, "[FuzzyChat] Groups file empty. No groups loaded.");
        }
    }

    private void saveUsers() {
        java.io.File usersFile = new java.io.File(plugin.getDataFolder(), "users.yml");
        org.bukkit.configuration.file.YamlConfiguration fc = new org.bukkit.configuration.file.YamlConfiguration();
        org.bukkit.configuration.ConfigurationSection userSection = fc.createSection("users");
        for (java.util.Map.Entry<java.lang.String, be.darnell.mc.FuzzyChat.InternalProvider.Meta> entry : users.entrySet()) {
            org.bukkit.configuration.ConfigurationSection cs = userSection.createSection(entry.getKey());
            cs.set("prefix", entry.getValue().prefix);
            cs.set("suffix", entry.getValue().suffix);
        }
        try {
            fc.save(usersFile);
        } catch (java.io.IOException e) {
            FuzzyChat.log.log(java.util.logging.Level.SEVERE, "[FuzzyChat] Failed to write to users.yml. Changes not saved!");
        }
    }

    private void saveGroups() {
        java.io.File groupsFile = new java.io.File(plugin.getDataFolder(), "groups.yml");
        org.bukkit.configuration.file.YamlConfiguration fc = new org.bukkit.configuration.file.YamlConfiguration();
        org.bukkit.configuration.ConfigurationSection groupSection = fc.createSection("groups");
        for (java.util.Map.Entry<java.lang.String, be.darnell.mc.FuzzyChat.InternalProvider.Meta> entry : groups.entrySet()) {
            org.bukkit.configuration.ConfigurationSection cs = groupSection.createSection(entry.getKey());
            cs.set("prefix", entry.getValue().prefix);
            cs.set("suffix", entry.getValue().suffix);
        }
        try {
            fc.save(groupsFile);
        } catch (java.io.IOException e) {
            FuzzyChat.log.log(java.util.logging.Level.SEVERE, "[FuzzyChat] Failed to write to groups.yml. Changes not saved!");
        }
    }
}