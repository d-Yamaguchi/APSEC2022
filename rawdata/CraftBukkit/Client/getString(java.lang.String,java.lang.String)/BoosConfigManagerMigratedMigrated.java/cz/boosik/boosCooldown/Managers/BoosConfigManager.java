package cz.boosik.boosCooldown.Managers;
import cz.boosik.boosCooldown.BoosCoolDown;
import util.BoosChat;
public class BoosConfigManager {
    private static org.bukkit.configuration.file.YamlConfiguration conf;

    private static org.bukkit.configuration.file.YamlConfiguration confusers;

    private static java.io.File confFile;

    private static java.io.File confusersFile;

    @java.lang.SuppressWarnings("static-access")
    public BoosConfigManager(cz.boosik.boosCooldown.BoosCoolDown boosCoolDown) {
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confFile = new java.io.File(boosCoolDown.getDataFolder(), "config.yml");
        if (cz.boosik.boosCooldown.Managers.BoosConfigManager.confFile.exists()) {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.conf = new org.bukkit.configuration.file.YamlConfiguration();
            cz.boosik.boosCooldown.Managers.BoosConfigManager.load();
        } else {
            this.confFile = new java.io.File(boosCoolDown.getDataFolder(), "config.yml");
            this.conf = new org.bukkit.configuration.file.YamlConfiguration();
        }
        if (cz.boosik.boosCooldown.Managers.BoosConfigManager.confFile.exists()) {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.load();
        }
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confusersFile = new java.io.File(boosCoolDown.getDataFolder(), "users.yml");
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers = new org.bukkit.configuration.file.YamlConfiguration();
        if (cz.boosik.boosCooldown.Managers.BoosConfigManager.confusersFile.exists()) {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.loadConfusers();
        } else {
            try {
                cz.boosik.boosCooldown.Managers.BoosConfigManager.confusersFile.createNewFile();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Could not save storage file!");
            }
        }
    }

    public static void clear() {
        org.bukkit.configuration.ConfigurationSection userSection = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.getConfigurationSection("users");
        if (userSection == null) {
            return;
        }
        for (java.lang.String user : userSection.getKeys(false)) {
            org.bukkit.configuration.ConfigurationSection cooldown = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.getConfigurationSection(("users." + user) + ".cooldown");
            if (cooldown != null) {
                for (java.lang.String key : cooldown.getKeys(false)) {
                    cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set((("users." + user) + ".cooldown.") + key, null);
                }
            }
            cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set(("users." + user) + ".cooldown", null);
            org.bukkit.configuration.ConfigurationSection warmup = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.getConfigurationSection(("users." + user) + ".warmup");
            if (warmup != null) {
                for (java.lang.String key : warmup.getKeys(false)) {
                    cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set((("users." + user) + ".warmup.") + key, null);
                }
            }
            cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set(("users." + user) + ".warmup", null);
            cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set("users." + user, null);
        }
        cz.boosik.boosCooldown.Managers.BoosConfigManager.saveConfusers();
        cz.boosik.boosCooldown.Managers.BoosConfigManager.loadConfusers();
    }

    public static void clearSomething(java.lang.String co, java.util.UUID uuid) {
        org.bukkit.configuration.ConfigurationSection userSection = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.getConfigurationSection((("users." + uuid) + ".") + co);
        if (userSection == null) {
            return;
        }
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set((("users." + uuid) + ".") + co, null);
        cz.boosik.boosCooldown.Managers.BoosConfigManager.saveConfusers();
        cz.boosik.boosCooldown.Managers.BoosConfigManager.loadConfusers();
    }

    public static void clearSomething(java.lang.String co, java.util.UUID uuid, java.lang.String command) {
        int pre2 = command.toLowerCase().hashCode();
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set((((("users." + uuid) + ".") + co) + ".") + pre2, 0);
        cz.boosik.boosCooldown.Managers.BoosConfigManager.saveConfusers();
        cz.boosik.boosCooldown.Managers.BoosConfigManager.loadConfusers();
    }

    static java.lang.String getAlias(java.lang.String message) {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("commands.aliases." + message);
    }

    public static java.util.Set<java.lang.String> getAliases() {
        java.util.Set<java.lang.String> aliases = null;
        org.bukkit.configuration.ConfigurationSection aliasesSection = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getConfigurationSection("commands.aliases");
        if (aliasesSection != null) {
            aliases = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getConfigurationSection("commands.aliases").getKeys(false);
        }
        return aliases;
    }

    public static boolean getBlockInteractDuringWarmup() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.block_interact_during_warmup", false);
    }

    public static java.lang.String getCancelWarmupByGameModeChangeMessage() {
        return c.getConfigurationSection("options.messages.warmup_cancelled_by_gamemode_change");
    }

    public static boolean getCancelWarmUpOnDamage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.cancel_warmup_on_damage", false);
    }

    public static boolean getCancelWarmUpOnGameModeChange() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.cancel_warmup_on_gamemode_change", false);
    }

    public static boolean getCancelWarmupOnMove() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.cancel_warmup_on_move", false);
    }

    public static boolean getCancelWarmupOnSneak() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.cancel_warmup_on_sneak", false);
    }

    public static java.lang.String getCancelWarmupOnSneakMessage() {
        return c.getConfigurationSection("options.messages.warmup_cancelled_by_sneak");
    }

    public static boolean getCancelWarmupOnSprint() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.cancel_warmup_on_sprint", false);
    }

    public static java.lang.String getCancelWarmupOnSprintMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.warmup_cancelled_by_sprint", "&6Warm-ups have been cancelled due to sprinting.&f");
    }

    public static java.lang.String getCannotCreateSignMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.cannot_create_sign", "&6You are not allowed to create this kind of signs!&f");
    }

    public static java.lang.String getCannotUseSignMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.cannot_use_sign", "&6You are not allowed to use this sign!&f");
    }

    public static boolean getCleanCooldownsOnDeath() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.clear_cooldowns_on_death", false);
    }

    public static boolean getCleanUsesOnDeath() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.clear_uses_on_death", false);
    }

    public static boolean getClearOnRestart() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.clear_on_restart", false);
    }

    static java.lang.String getCommandBlockedMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.limit_achieved", "&6You cannot use this command anymore!&f");
    }

    private static java.lang.String getCommandGroup(org.bukkit.entity.Player player) {
        java.lang.String cmdGroup = "default";
        java.util.Set<java.lang.String> groups = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroups();
        if (groups != null) {
            for (java.lang.String group : groups) {
                if (player.hasPermission("booscooldowns." + group)) {
                    cmdGroup = group;
                }
            }
        }
        return cmdGroup;
    }

    private static java.util.Set<java.lang.String> getCommandGroups() {
        org.bukkit.configuration.ConfigurationSection groupsSection = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getConfigurationSection("commands.groups");
        java.util.Set<java.lang.String> groups = null;
        if (groupsSection != null) {
            groups = groupsSection.getKeys(false);
        }
        return groups;
    }

    public static boolean getCommandLogging() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.command_logging", false);
    }

    public static java.util.Set<java.lang.String> getCommands(org.bukkit.entity.Player player) {
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        java.util.Set<java.lang.String> commands = null;
        org.bukkit.configuration.ConfigurationSection commandsSection = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getConfigurationSection("commands.groups." + group);
        if (commandsSection != null) {
            commands = commandsSection.getKeys(false);
        }
        return commands;
    }

    public static org.bukkit.configuration.file.YamlConfiguration getConfusers() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers;
    }

    public static int getCoolDown(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int coolDown;
        java.lang.String coolDownString = "";
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        coolDownString = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommand) + ".cooldown", "0");
        coolDown = cz.boosik.boosCooldown.Managers.BoosConfigManager.parseTime(coolDownString);
        return coolDown;
    }

    public static boolean getCooldownEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.cooldowns_enabled", true);
    }

    static java.lang.String getCoolDownMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.cooling_down", "&6Wait&e &seconds& seconds&6 before you can use command&e &command& &6again.&f");
    }

    static java.util.Set<java.lang.String> getCooldowns(org.bukkit.entity.Player player) {
        java.lang.String cool = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getConfigurationSection("commands.groups." + cool).getKeys(false);
    }

    public static java.lang.String getInsufficientFundsMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.insufficient_funds", "&6You have insufficient funds!&e &command& &6costs &e%s &6but you only have &e%s");
    }

    public static java.lang.String getInteractBlockedMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.interact_blocked_during_warmup", "&6You can't do this when command is warming-up!&f");
    }

    public static java.util.List<java.lang.String> getItemCostLore(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getStringList(((("commands.groups." + cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player)) + ".") + regexCommand) + ".itemcost.lore");
    }

    public static java.util.List<java.lang.String> getItemCostEnchants(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getStringList(((("commands.groups." + cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player)) + ".") + regexCommand) + ".itemcost.enchants");
    }

    public static java.lang.String getItemCostName(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player)) + ".") + regexCommand) + ".itemcost.name", "");
    }

    public static java.lang.String getItemCostItem(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player)) + ".") + regexCommand) + ".itemcost.item", "");
    }

    public static int getItemCostCount(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getInt(((("commands.groups." + cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player)) + ".") + regexCommand) + ".itemcost.count", 0);
    }

    public static int getLimit(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int limit;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        limit = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getInt(((("commands.groups." + group) + ".") + regexCommand) + ".limit", -1);
        return limit;
    }

    public static boolean getLimitEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.limits_enabled", true);
    }

    static java.lang.String getLimitListMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.limit_list", "&6Limit for command &e&command&&6 is &e&limit&&6. You can still use it &e&times&&6 times.&f");
    }

    static boolean getLimitsEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.limits_enabled", true);
    }

    static java.util.Set<java.lang.String> getAllPlayers() {
        org.bukkit.configuration.ConfigurationSection users = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.getConfigurationSection("users");
        return users.getKeys(false);
    }

    static java.util.List<java.lang.String> getSharedCooldowns(java.lang.String pre, org.bukkit.entity.Player player) {
        java.util.List<java.lang.String> sharedCooldowns;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        sharedCooldowns = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getStringList(((("commands.groups." + group) + ".") + pre) + ".shared_cooldown");
        return sharedCooldowns;
    }

    public static java.util.List<java.lang.String> getSharedLimits(java.lang.String pre, org.bukkit.entity.Player player) {
        java.util.List<java.lang.String> sharedLimits;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        sharedLimits = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getStringList(((("commands.groups." + group) + ".") + pre) + ".shared_limit");
        return sharedLimits;
    }

    public static java.lang.String getMessage(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        java.lang.String message = "";
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        message = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommand) + ".message", "");
        return message;
    }

    static java.lang.String getPaidErrorMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.paid_error", "An error has occured: %s");
    }

    static java.lang.String getPaidForCommandMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.paid_for_command", "Price of &command& was %s and you now have %s");
    }

    static java.lang.String getPotionEffect(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        java.lang.String effect = "";
        java.lang.String temp;
        java.lang.String[] command;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        temp = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommand) + ".potion", "");
        command = temp.split(",");
        if (command.length == 2) {
            effect = command[0];
        }
        return effect;
    }

    static int getPotionEffectStrength(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int effect = 0;
        java.lang.String temp;
        java.lang.String[] command;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        temp = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommand) + ".potion", "");
        command = temp.split(",");
        if (command.length == 2) {
            effect = java.lang.Integer.valueOf(command[1]);
        }
        return effect;
    }

    public static double getPrice(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        double price;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        price = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getDouble(((("commands.groups." + group) + ".") + regexCommand) + ".price", 0.0);
        return price;
    }

    public static boolean getPriceEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.prices_enabled", true);
    }

    public static int getSaveInterval() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getInt("options.options.save_interval_in_minutes", 15);
    }

    public static boolean getSignCommands() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.command_signs", false);
    }

    public static boolean getStartCooldownsOnDeath() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.start_cooldowns_on_death", false);
    }

    static java.lang.String getUnitHoursMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.units.hours", "hours");
    }

    static java.lang.String getUnitMinutesMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.units.minutes", "minutes");
    }

    static java.lang.String getUnitSecondsMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.units.seconds", "seconds");
    }

    public static int getWarmUp(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int warmUp;
        java.lang.String warmUpString = "";
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        warmUpString = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommand) + ".warmup", "0");
        warmUp = cz.boosik.boosCooldown.Managers.BoosConfigManager.parseTime(warmUpString);
        return warmUp;
    }

    static java.lang.String getWarmUpAlreadyStartedMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.warmup_already_started", "&6Warm-Up process for&e &command& &6has already started.&f");
    }

    public static java.lang.String getWarmUpCancelledByDamageMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.warmup_cancelled_by_damage", "&6Warm-ups have been cancelled due to receiving damage.&f");
    }

    public static java.lang.String getWarmUpCancelledByMoveMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.warmup_cancelled_by_move", "&6Warm-ups have been cancelled due to moving.&f");
    }

    public static boolean getWarmupEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.warmups_enabled", true);
    }

    static java.lang.String getWarmUpMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.warming_up", "&6Wait&e &seconds& seconds&6 before command&e &command& &6has warmed up.&f");
    }

    public static void load() {
        try {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.load(cz.boosik.boosCooldown.Managers.BoosConfigManager.confFile);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Configuration file not found!");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Could not read configuration file!");
        } catch (org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Configuration file is invalid!");
        }
    }

    public static void loadConfusers() {
        try {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.load(cz.boosik.boosCooldown.Managers.BoosConfigManager.confusersFile);
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Storage file not found!");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Could not read storage file!");
        } catch (org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Storage file is invalid!");
        }
    }

    public static void reload() {
        cz.boosik.boosCooldown.Managers.BoosConfigManager.conf = new org.bukkit.configuration.file.YamlConfiguration();
        cz.boosik.boosCooldown.Managers.BoosConfigManager.load();
    }

    public static void saveConfusers() {
        try {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.confFile.createNewFile();
            cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.save(cz.boosik.boosCooldown.Managers.BoosConfigManager.confusersFile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Could not save storage file!");
        }
    }

    public static void setAddToConfigFile(java.lang.String group, java.lang.String command, java.lang.String what, java.lang.String value) {
        group = group.toLowerCase();
        command = command.toLowerCase();
        int value2;
        try {
            value2 = java.lang.Integer.parseInt(value);
            cz.boosik.boosCooldown.Managers.BoosConfigManager.reload();
            cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.set((((("commands.groups." + group) + ".") + command) + ".") + what, value2);
        } catch (java.lang.NumberFormatException e1) {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.reload();
            cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.set((((("commands.groups." + group) + ".") + command) + ".") + what, value);
        }
        try {
            cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.save(cz.boosik.boosCooldown.Managers.BoosConfigManager.confFile);
        } catch (java.io.IOException e) {
            cz.boosik.boosCooldown.BoosCoolDown.getLog().severe("[boosCooldowns] Could not save configuration file!");
        }
        cz.boosik.boosCooldown.Managers.BoosConfigManager.reload();
    }

    public static void toggleConfirmations(org.bukkit.entity.Player player) {
        java.lang.Boolean def = cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.getBoolean(("users." + player.getUniqueId()) + ".confirmations", cz.boosik.boosCooldown.Managers.BoosConfigManager.getConfirmCommandEnabled(player));
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set(("users." + player.getUniqueId()) + ".confirmations", !def);
        if (def) {
            util.BoosChat.sendMessageToPlayer(player, "&6[boosCooldowns]&e " + cz.boosik.boosCooldown.Managers.BoosConfigManager.getConfirmToggleMessageFalse());
        } else {
            util.BoosChat.sendMessageToPlayer(player, "&6[boosCooldowns]&e " + cz.boosik.boosCooldown.Managers.BoosConfigManager.getConfirmToggleMessageTrue());
        }
        cz.boosik.boosCooldown.Managers.BoosConfigManager.saveConfusers();
        cz.boosik.boosCooldown.Managers.BoosConfigManager.loadConfusers();
    }

    public static java.lang.Boolean getConfirmationsPlayer(org.bukkit.entity.Player player) {
        return ((java.lang.Boolean) (cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.get(("users." + player.getUniqueId()) + ".confirmations", null)));
    }

    public static boolean getAutoSave() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.auto_save_enabled_CAN_CAUSE_BIG_LAGS", false);
    }

    static java.lang.String getPaidItemsForCommandMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.paid_items_for_command", "&6Price of&e &command& &6was &e%s");
    }

    public static java.lang.String getInsufficientItemsMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.insufficient_items", "&6You have not enough items!&e &command& &6needs &e%s");
    }

    public static boolean getItemCostEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.item_cost_enabled", true);
    }

    public static boolean getPlayerPointsEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.player_points_prices_enabled", true);
    }

    public static int getPlayerPointsPrice(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int price;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        price = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getInt(((("commands.groups." + group) + ".") + regexCommand) + ".playerpoints", 0);
        return price;
    }

    static java.lang.String getPaidXPForCommandMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.paid_xp_for_command", "&6Price of&e &command& &6was &e%s");
    }

    public static int getXpPrice(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int price;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        price = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getInt(((("commands.groups." + group) + ".") + regexCommand) + ".xpcost", 0);
        return price;
    }

    public static int getXpRequirement(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        int price;
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        price = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getInt(((("commands.groups." + group) + ".") + regexCommand) + ".xprequirement", 0);
        return price;
    }

    public static boolean getXpPriceEnabled() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.xp_cost_enabled", true);
    }

    public static java.lang.String getInsufficientXpMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.insufficient_xp", "&6You have not enough XP!&e &command& &6needs &e%s");
    }

    public static java.lang.String getInsufficientXpRequirementMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.insufficient_xp_requirement", "&6Your level is too low to use this!&e &command& &6needs &e%s");
    }

    public static java.lang.String getInsufficientPlayerPointsMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.insufficient_player_points", "'&6You have not enough PlayerPoints!&e &command& &6needs &e%s'");
    }

    public static java.lang.String getInvalidCommandSyntaxMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.invalid_command_syntax", "&6You are not allowed to use command syntax /<pluginname>:<command>!");
    }

    static long getLimitResetDelay(java.lang.String regexCommand, org.bukkit.entity.Player player) {
        long limitreset;
        java.lang.String limitResetString = "";
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        limitResetString = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommand) + ".limit_reset_delay", "0");
        limitreset = cz.boosik.boosCooldown.Managers.BoosConfigManager.parseTime(limitResetString);
        return limitreset;
    }

    static java.lang.String getLimitResetMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.limit_reset", "&6Wait&e &seconds& &unit&&6 before your limit for command&e &command& &6is reset.&f");
    }

    static void clearSomething2(java.lang.String co, java.lang.String uuid, int hashedCommand) {
        cz.boosik.boosCooldown.Managers.BoosConfigManager.confusers.set((((("users." + uuid) + ".") + co) + ".") + hashedCommand, 0);
    }

    public static long getLimitResetDelayGlobal(java.lang.String command) {
        long delay = 0;
        java.lang.String delayString = "";
        delayString = cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(("global." + command) + ".limit_reset_delay", "0");
        delay = cz.boosik.boosCooldown.Managers.BoosConfigManager.parseTime(delayString);
        return delay;
    }

    static java.util.Set<java.lang.String> getLimitResetCommandsGlobal() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getConfigurationSection("global").getKeys(false);
    }

    private static int parseTime(java.lang.String time) {
        java.lang.String[] timeString = time.split(" ", 2);
        if (timeString[0].equals("cancel")) {
            return -65535;
        }
        int timeNumber = java.lang.Integer.valueOf(timeString[0]);
        int timeMultiplier = 1;
        if (timeString.length > 1) {
            java.lang.String timeUnit = timeString[1];
            switch (timeUnit) {
                case "minute" :
                case "minutes" :
                    timeMultiplier = 60;
                    break;
                case "hour" :
                case "hours" :
                    timeMultiplier = 60 * 60;
                    break;
                case "day" :
                case "days" :
                    timeMultiplier = (60 * 60) * 24;
                    break;
                case "week" :
                case "weeks" :
                    timeMultiplier = ((60 * 60) * 24) * 7;
                    break;
                case "month" :
                case "months" :
                    timeMultiplier = ((60 * 60) * 24) * 30;
                    break;
                default :
                    timeMultiplier = 1;
                    break;
            }
        }
        return timeNumber * timeMultiplier;
    }

    public static java.lang.String getLimitResetNowMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.limit_reset_now", "&6Reseting limits for command&e &command& &6now.&f");
    }

    public static java.lang.String getPermission(org.bukkit.entity.Player player, java.lang.String regexCommad) {
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommad) + ".permission");
    }

    public static java.lang.String getPermissionMessage(org.bukkit.entity.Player player, java.lang.String regexCommad) {
        java.lang.String group = cz.boosik.boosCooldown.Managers.BoosConfigManager.getCommandGroup(player);
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString(((("commands.groups." + group) + ".") + regexCommad) + ".denied_message");
    }

    public static java.lang.String getCancelCommandMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_cancel_command_execution", "No");
    }

    public static java.lang.String getConfirmCommandMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_confirm_command_execution", "Yes");
    }

    public static java.lang.String getCancelCommandHint() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_cancel_command_execution_hint", "Click to cancel");
    }

    public static java.lang.String getConfirmCommandHint() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_confirm_command_execution_hint", "Click to confirm");
    }

    public static java.lang.String getConfirmToggleMessageTrue() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_toggle_enable", "Confirmation messages are now enabled for you!");
    }

    public static java.lang.String getConfirmToggleMessageFalse() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_toggle_disable", "Confirmation messages are now disabled for you!");
    }

    public static java.lang.String getItsPriceMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_price_of_command", "&6its price is&e &price& &6and you now have &e&balance&");
    }

    public static java.lang.String getQuestionMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_message", "&6Would you like to use command&e &command& &6?");
    }

    public static java.lang.String getItsItemCostMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_item_price_of_command", "&6its price is&e &itemprice& &itemname&");
    }

    public static java.lang.String getItsLimitMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_limit_of_command", "&6it is limited to&e &limit& &6uses and you can still use it&e &uses& &6times");
    }

    public static java.lang.String getItsXpPriceMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_xp_price_of_command", "&6its price is&e &xpprice& experience levels");
    }

    public static java.lang.String getItsPlayerPointsPriceMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_player_points_price_of_command", "&6its price is&e &ppprice& PlayerPoints &6and you now have &e&ppbalance& PlayerPoints");
    }

    public static java.lang.String getCommandCanceledMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.confirmation_command_cancelled", "&6Execution of command&e &command& &6was cancelled");
    }

    public static boolean getSyntaxBlocker() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.syntax_blocker_enabled", true);
    }

    public static boolean getConfirmCommandEnabled(org.bukkit.entity.Player player) {
        java.lang.Boolean playersChoice = cz.boosik.boosCooldown.Managers.BoosConfigManager.getConfirmationsPlayer(player);
        if (playersChoice != null) {
            return playersChoice;
        } else {
            return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getBoolean("options.options.command_confirmation", true);
        }
    }

    public static java.lang.String getPlayerPointsForCommandMessage() {
        return cz.boosik.boosCooldown.Managers.BoosConfigManager.conf.getString("options.messages.paid_player_points_for_command", "Price of &command& was %s PlayerPoints and you now have %s" + " PlayerPoints");
    }
}