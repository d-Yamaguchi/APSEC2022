package com.dre.managerxl.broadcaster;
import com.dre.managerxl.Config;
import com.dre.managerxl.MPlayer;
import com.dre.managerxl.P;
public class Broadcaster {
    public static int maxLevel;// = 10;


    public static int sendsPerLevel;// = 20;


    public static int minTimeInMinutes;// = 10;


    public static int maxTimeInMinutes;// = 60;


    public static java.lang.String broadcastColor;// = "&2";


    public static java.lang.String broadcastText;// = "Broadcast";


    public static java.lang.String newsText;// = "News";


    public static java.lang.String dateText;// = "Date";


    public static java.lang.String broadcastFolderName;// = "Broadcaster";


    public static java.lang.String broadcastDataFileName;

    public static java.lang.String broadcastMsgFileName;

    public static java.util.ArrayList<java.lang.String> timeColors = new java.util.ArrayList<java.lang.String>();

    private java.io.File broadcastFolder;

    private java.io.File broadcastDataFile;

    private java.io.File broadcastMsgFile;

    public Broadcaster() {
        loadConfig();
        initializeBroadcaster();
        this.load();
        initBroadcastSchedulers();
    }

    public void initializeBroadcaster() {
        broadcastFolder = new java.io.File(P.p.getDataFolder(), com.dre.managerxl.broadcaster.Broadcaster.broadcastFolderName);
        if (!broadcastFolder.exists()) {
            broadcastFolder.mkdirs();
        }
        broadcastDataFile = new java.io.File(P.p.getDataFolder(), (com.dre.managerxl.broadcaster.Broadcaster.broadcastFolderName + "/") + com.dre.managerxl.broadcaster.Broadcaster.broadcastDataFileName);
        if (!broadcastDataFile.exists()) {
            try {
                broadcastDataFile.createNewFile();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        broadcastMsgFile = new java.io.File(P.p.getDataFolder(), (com.dre.managerxl.broadcaster.Broadcaster.broadcastFolderName + "/") + com.dre.managerxl.broadcaster.Broadcaster.broadcastMsgFileName);
        if (!broadcastMsgFile.exists()) {
            try {
                broadcastMsgFile.createNewFile();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initBroadcastSchedulers() {
        P.p.getServer().getScheduler().scheduleSyncRepeatingTask(P.p, new java.lang.Runnable() {
            public void run() {
                org.bukkit.entity.Player[] players = P.p.getServer().getOnlinePlayers();
                for (java.lang.Integer msgId : BroadcasterMsg.messages.keySet()) {
                    com.dre.managerxl.broadcaster.BroadcasterMsg msg = BroadcasterMsg.messages.get(msgId);
                    boolean endThisMsg = false;
                    if (msg.getEndTime() < java.lang.System.currentTimeMillis()) {
                        endThisMsg = true;
                    }
                    for (org.bukkit.entity.Player player : players) {
                        if (endThisMsg) {
                            com.dre.managerxl.broadcaster.Broadcaster.broadcastMsg(player, msg);
                        } else if (com.dre.managerxl.broadcaster.Broadcaster.getNextSendTime(player, msg) < java.lang.System.currentTimeMillis()) {
                            com.dre.managerxl.broadcaster.Broadcaster.broadcastMsg(player, msg);
                        }
                    }
                    if (endThisMsg) {
                        msg.setDelete();
                    }
                }
                deleteOldMessages();
                deleteOldPlayerData();
            }
        }, 0L, 1200L);
    }

    private void deleteOldMessages() {
        for (java.util.Iterator<java.util.Map.Entry<java.lang.Integer, com.dre.managerxl.broadcaster.BroadcasterMsg>> it = BroadcasterMsg.messages.entrySet().iterator(); it.hasNext();) {
            java.util.Map.Entry<java.lang.Integer, com.dre.managerxl.broadcaster.BroadcasterMsg> entry = it.next();
            if (entry.getValue().isDelete()) {
                it.remove();
            }
        }
    }

    private void deleteOldPlayerData() {
        for (com.dre.managerxl.MPlayer mPlayer : com.dre.managerxl.MPlayer.get()) {
            for (java.util.Iterator<java.util.Map.Entry<java.lang.Integer, com.dre.managerxl.broadcaster.BroadcasterPlayerMsg>> it = mPlayer.playerMsgs.entrySet().iterator(); it.hasNext();) {
                java.util.Map.Entry<java.lang.Integer, com.dre.managerxl.broadcaster.BroadcasterPlayerMsg> entry = it.next();
                if (entry.getValue().getBroadcastMsg() == null) {
                    it.remove();
                    break;
                }
            }
        }
    }

    public static void broadcastMsg(org.bukkit.entity.Player player, com.dre.managerxl.broadcaster.BroadcasterMsg msg) {
        com.dre.managerxl.MPlayer mPlayer = com.dre.managerxl.MPlayer.getOrCreate(store.loadAchievements(mPlayer));
        com.dre.managerxl.broadcaster.BroadcasterPlayerMsg bMsg = mPlayer.getBMsg(msg.getId());
        bMsg.setSendCount(bMsg.getSendCount() + 1);
        bMsg.setLastSend(java.lang.System.currentTimeMillis());
        int newPlayerLevel = (bMsg.getSendCount() / com.dre.managerxl.broadcaster.Broadcaster.sendsPerLevel) + 1;
        if (newPlayerLevel > com.dre.managerxl.broadcaster.Broadcaster.maxLevel) {
            newPlayerLevel = com.dre.managerxl.broadcaster.Broadcaster.maxLevel;
        }
        bMsg.setPlayerLevel(newPlayerLevel);
        if (msg.getType().equalsIgnoreCase("Broadcast")) {
            player.sendMessage(msg.getMsgAsBroadcast());
        } else if (msg.getType().equalsIgnoreCase("News")) {
            player.sendMessage(msg.getMsgAsNews());
        } else if (msg.getType().equalsIgnoreCase("Date")) {
            player.sendMessage(msg.getMsgAsDate());
        }
    }

    public static long getNextSendTime(org.bukkit.entity.Player player, com.dre.managerxl.broadcaster.BroadcasterMsg msg) {
        com.dre.managerxl.MPlayer mPlayer = com.dre.managerxl.MPlayer.getOrCreate(player.getName());
        com.dre.managerxl.broadcaster.BroadcasterPlayerMsg bMsg = mPlayer.getBMsg(msg.getId());
        long lastSend = bMsg.getLastSend();
        int playerLevel = bMsg.getPlayerLevel();
        int timeDiff = com.dre.managerxl.broadcaster.Broadcaster.maxTimeInMinutes - com.dre.managerxl.broadcaster.Broadcaster.minTimeInMinutes;
        double time;
        if (playerLevel > com.dre.managerxl.broadcaster.Broadcaster.maxLevel) {
            time = com.dre.managerxl.broadcaster.Broadcaster.maxTimeInMinutes;
        } else if (playerLevel > 0) {
            time = (((double) (timeDiff)) / ((double) (com.dre.managerxl.broadcaster.Broadcaster.maxLevel))) * ((double) (playerLevel));
        } else {
            time = com.dre.managerxl.broadcaster.Broadcaster.minTimeInMinutes;
        }
        long milliTime = ((long) ((time * 60) * 1000));
        return milliTime + lastSend;
    }

    public void save() {
        saveMessages();
        saveData();
    }

    public void load() {
        loadMessages();
        loadData();
    }

    public void saveMessages() {
        org.bukkit.configuration.file.FileConfiguration file = new org.bukkit.configuration.file.YamlConfiguration();
        file.set("idCounterBroadcasterVar", BroadcasterMsg.idCounter);
        for (java.lang.Integer id : BroadcasterMsg.messages.keySet()) {
            com.dre.managerxl.broadcaster.BroadcasterMsg message = BroadcasterMsg.messages.get(id);
            file.set(id + ".type", message.getType());
            file.set(id + ".msg", message.getMsg());
            file.set(id + ".endTime", message.getEndTime());
            file.set(id + ".startTime", message.getStartTime());
        }
        try {
            file.save(broadcastMsgFile);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        org.bukkit.configuration.file.FileConfiguration file = new org.bukkit.configuration.file.YamlConfiguration();
        for (com.dre.managerxl.MPlayer mPlayer : com.dre.managerxl.MPlayer.get()) {
            for (java.lang.Integer id : mPlayer.playerMsgs.keySet()) {
                com.dre.managerxl.broadcaster.BroadcasterPlayerMsg bpMsg = mPlayer.playerMsgs.get(id);
                file.set(((mPlayer.getPlayer() + ".") + id) + ".playerLevel", bpMsg.getPlayerLevel());
                file.set(((mPlayer.getPlayer() + ".") + id) + ".sendCount", bpMsg.getSendCount());
                file.set(((mPlayer.getPlayer() + ".") + id) + ".lastSend", bpMsg.getLastSend());
            }
        }
    }

    public void loadMessages() {
        org.bukkit.configuration.file.FileConfiguration file = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(broadcastMsgFile);
        BroadcasterMsg.idCounter = file.getInt("idCounterBroadcasterVar");
        java.lang.String type;
        java.lang.String msg;
        long endTime;
        long startTime;
        for (java.lang.String id : file.getKeys(false)) {
            if (!id.equalsIgnoreCase("idCounterBroadcasterVar")) {
                type = file.getString(id + ".type");
                msg = file.getString(id + ".msg");
                endTime = file.getLong(id + ".endTime");
                startTime = file.getLong(id + ".startTime");
                new com.dre.managerxl.broadcaster.BroadcasterMsg(java.lang.Integer.parseInt(id), type, msg, endTime, startTime);
            }
        }
    }

    public void loadData() {
        org.bukkit.configuration.file.FileConfiguration file = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(broadcastDataFile);
        com.dre.managerxl.MPlayer mPlayer;
        int playerLevel;
        int sendCount;
        long lastSend;
        for (java.lang.String name : file.getKeys(false)) {
            mPlayer = com.dre.managerxl.MPlayer.getOrCreate(name);
            org.bukkit.configuration.ConfigurationSection section = file.getConfigurationSection(name);
            for (java.lang.String id : section.getKeys(false)) {
                playerLevel = file.getInt(((name + ".") + id) + ".playerLevel");
                sendCount = file.getInt(((name + ".") + id) + ".sendCount");
                lastSend = file.getInt(((name + ".") + id) + ".lastSend");
                new com.dre.managerxl.broadcaster.BroadcasterPlayerMsg(java.lang.Integer.parseInt(id), mPlayer, playerLevel, sendCount, lastSend);
            }
        }
    }

    public static void saveDefaultConfig(com.dre.managerxl.Config config) {
        config.saveSingleConfig("Broadcaster.maxLevel", 10);
        config.saveSingleConfig("Broadcaster.sendsPerLevel", 20);
        config.saveSingleConfig("Broadcaster.minTimeInMinutes", 10);
        config.saveSingleConfig("Broadcaster.maxTimeInMinutes", 60);
        config.saveSingleConfig("Broadcaster.broadcastColor", "&2");
        config.saveSingleConfig("Broadcaster.broadcastText", "Broadcast");
        config.saveSingleConfig("Broadcaster.newsText", "News");
        config.saveSingleConfig("Broadcaster.dateText", "Event");
        config.saveSingleConfig("Broadcaster.broadcastFolderName", "Broadcaster");
        config.saveSingleConfig("Broadcaster.broadcastDataFileName", "data.yml");
        config.saveSingleConfig("Broadcaster.broadcastMsgFileName", "msg.yml");
        java.util.ArrayList<java.lang.String> tColors = new java.util.ArrayList<java.lang.String>();
        tColors.add("&4");
        tColors.add("&c");
        tColors.add("&6");
        tColors.add("&e");
        tColors.add("&2");
        tColors.add("&a");
        config.saveSingleConfig("Broadcaster.timeColors", tColors);
    }

    // cast of List to ArrayList
    @java.lang.SuppressWarnings("unchecked")
    public void loadConfig() {
        org.bukkit.configuration.ConfigurationSection broadcasterConfig = P.p.config.getBroadcasterConfigSection();
        com.dre.managerxl.broadcaster.Broadcaster.maxLevel = broadcasterConfig.getInt("maxLevel");
        com.dre.managerxl.broadcaster.Broadcaster.sendsPerLevel = broadcasterConfig.getInt("sendsPerLevel");
        com.dre.managerxl.broadcaster.Broadcaster.minTimeInMinutes = broadcasterConfig.getInt("minTimeInMinutes");
        com.dre.managerxl.broadcaster.Broadcaster.maxTimeInMinutes = broadcasterConfig.getInt("maxTimeInMinutes");
        com.dre.managerxl.broadcaster.Broadcaster.broadcastColor = broadcasterConfig.getString("broadcastColor");
        com.dre.managerxl.broadcaster.Broadcaster.broadcastText = broadcasterConfig.getString("broadcastText");
        com.dre.managerxl.broadcaster.Broadcaster.newsText = broadcasterConfig.getString("newsText");
        com.dre.managerxl.broadcaster.Broadcaster.dateText = broadcasterConfig.getString("dateText");
        com.dre.managerxl.broadcaster.Broadcaster.broadcastFolderName = broadcasterConfig.getString("broadcastFolderName");
        com.dre.managerxl.broadcaster.Broadcaster.broadcastDataFileName = broadcasterConfig.getString("broadcastDataFileName");
        com.dre.managerxl.broadcaster.Broadcaster.broadcastMsgFileName = broadcasterConfig.getString("broadcastMsgFileName");
        com.dre.managerxl.broadcaster.Broadcaster.timeColors = ((java.util.ArrayList<java.lang.String>) (broadcasterConfig.getList("timeColors")));
    }
}