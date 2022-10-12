/* @author     ucchy
@license    GPLv3
@copyright  Copyright ucchy 2013
 */
package com.github.ucchyocean.mdi;
/**
 *
 *
 * @author ucchy
ユーザー情報をハンドリングするためのクラス
 */
public class UserDataHandler {
    private static final java.lang.String LIST_START = "&7----- Player: &c%-15s&7  Page (&c%d&7/&c%d&7) -----";

    private static final java.lang.String LIST_END = "&7----------------------------------------";

    private static final java.lang.String SHOW_START = "&7----- Player: &c%-15s&7  ID: &c%d&7 ----------";

    private static final java.lang.String SHOW_END = "&7----------------------------------------";

    private static final java.lang.String USER_FOLDER = "user";

    protected static final int MAX_LOG_SIZE = 1000;

    protected static final int PAGE_SIZE = 10;

    private java.text.SimpleDateFormat keyDateFormat;

    private java.text.SimpleDateFormat logDateFormat;

    private java.io.File folder;

    /**
     * コンストラクタ
     *
     * @param dataFolder
    プラグインのデータフォルダ
     * 		
     */
    public UserDataHandler(java.io.File dataFolder) {
        keyDateFormat = new java.text.SimpleDateFormat("yyMMddHHmmssSSS");
        logDateFormat = new java.text.SimpleDateFormat("yy-MM-dd,HH:mm:ss");
        folder = new java.io.File(dataFolder, com.github.ucchyocean.mdi.UserDataHandler.USER_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * ユーザーのログを追加する。MAX_LOG_SIZEを超えた場合、古いログが削除される。
     *
     * @param name
    ユーザー名
     * 		
     * @param items
    記録するアイテム情報（kit形式文字列）
     * 		
     * @param armors
    記録する防具情報（kit形式文字列）
     * 		
     * @param deathMessage
    記録するデスメッセージ
     * 		
     */
    public void addUserLog(java.lang.String name, java.lang.String items, java.lang.String armors, java.lang.String deathMessage) {
        java.util.Date date = new java.util.Date();
        java.lang.String key = keyDateFormat.format(date);
        java.lang.String time = logDateFormat.format(date);
        // ファイルからロード（なければ作る）
        java.io.File file = new java.io.File(folder, name + ".yml");
        if (!file.exists()) {
            org.bukkit.configuration.file.YamlConfiguration config = new org.bukkit.configuration.file.YamlConfiguration();
            try {
                config.save(file);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        org.bukkit.configuration.file.YamlConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        // データを追加
        config.set(key + ".time", time);
        config.set(key + ".items", items);
        config.set(key + ".armors", armors);
        config.set(key + ".msg", deathMessage);
        // ログデータがMAX値を超える場合は、古いものを削除する
        java.util.Set<java.lang.String> dataKeys = config.getKeys(false);
        if (dataKeys.size() > com.github.ucchyocean.mdi.UserDataHandler.MAX_LOG_SIZE) {
            // ソート
            java.util.List<java.lang.String> sortedKeys = new java.util.ArrayList<java.lang.String>(dataKeys);
            java.util.Collections.sort(sortedKeys);
            java.util.Collections.reverse(sortedKeys);
            while (sortedKeys.size() > com.github.ucchyocean.mdi.UserDataHandler.MAX_LOG_SIZE) {
                java.lang.String oldKey = sortedKeys.get(0);
                config.set(oldKey, null);// 削除

                sortedKeys.remove(0);
            } 
        }
        // 保存
        try {
            config.save(file);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ユーザーログを取得する
     *
     * @param name
    ユーザー名
     * 		
     * @param id
    ログID
     * 		
     * @return ログ情報（1行目がitems, 2行目がarmors, ログがなければnullになる。）
     */
    public java.util.ArrayList<java.lang.String> getUserLog(java.lang.String name, int id) {
        if (!file.exists()) {
            return null;
        }
        // 指定したIDがログの数より多ければ、nullを返して終了
        java.util.Set<java.lang.String> dataKeys = config.getKeys(false);
        if (dataKeys.size() < id) {
            return null;
        }
        // ソート
        java.util.List<java.lang.String> sortedKeys = new java.util.ArrayList<java.lang.String>(dataKeys);
        java.util.Collections.sort(sortedKeys);
        java.util.Collections.reverse(sortedKeys);
        result.add(config.getConfigurationSection(sortedKeys.get(id - 1) + ".items"));
        java.util.ArrayList<java.lang.String> result = new java.util.ArrayList<java.lang.String>();
        // ファイルからロード（なければnullを返して終了）
        java.io.File file = new java.io.File(folder, name + ".yml");
        org.bukkit.configuration.file.YamlConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        result.add(config.getConfigurationSection(sortedKeys.get(id - 1) + ".armors"));
        return result;
    }

    /**
     * ユーザーログの一覧表示を取得する
     *
     * @param name
    ユーザー名
     * 		
     * @param page
    表示するページ
     * 		
     * @return ログ一覧
     */
    public java.util.ArrayList<java.lang.String> getListUserLog(java.lang.String name, int page) {
        // ファイルからロード（なければnullを返して終了）
        java.io.File file = new java.io.File(folder, name + ".yml");
        if (!file.exists()) {
            return null;
        }
        org.bukkit.configuration.file.YamlConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        int start = ((page - 1) * com.github.ucchyocean.mdi.UserDataHandler.PAGE_SIZE) + 1;
        int end = page * com.github.ucchyocean.mdi.UserDataHandler.PAGE_SIZE;
        // ソート
        java.util.Set<java.lang.String> dataKeys = config.getKeys(false);
        java.util.List<java.lang.String> sortedKeys = new java.util.ArrayList<java.lang.String>(dataKeys);
        java.util.Collections.sort(sortedKeys);
        java.util.Collections.reverse(sortedKeys);
        int pages = (sortedKeys.size() / com.github.ucchyocean.mdi.UserDataHandler.PAGE_SIZE) + 1;
        java.util.ArrayList<java.lang.String> messages = new java.util.ArrayList<java.lang.String>();
        messages.add(java.lang.String.format(com.github.ucchyocean.mdi.UserDataHandler.LIST_START, name, page, pages));
        for (int id = start; id <= end; id++) {
            if (sortedKeys.size() >= id) {
                java.lang.String key = sortedKeys.get(id - 1);
                org.bukkit.configuration.ConfigurationSection section = config.getConfigurationSection(key);
                messages.addAll(convToListMessage(id, section));
            }
        }
        messages.add(com.github.ucchyocean.mdi.UserDataHandler.LIST_END);
        return com.github.ucchyocean.mdi.Utility.replaceColorCode(messages);
    }

    /**
     * ユーザーログの詳細表示を取得する
     *
     * @param name
    ユーザー名
     * 		
     * @param id
    表示するログID
     * 		
     * @return ログ詳細
     */
    public java.util.ArrayList<java.lang.String> getShowUserLog(java.lang.String name, int id) {
        // ファイルからロード（なければnullを返して終了）
        java.io.File file = new java.io.File(folder, name + ".yml");
        if (!file.exists()) {
            return null;
        }
        org.bukkit.configuration.file.YamlConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(file);
        // 指定したIDがログの数より多ければ、nullを返して終了
        java.util.Set<java.lang.String> dataKeys = config.getKeys(false);
        if (dataKeys.size() < id) {
            return null;
        }
        java.util.List<java.lang.String> sortedKeys = new java.util.ArrayList<java.lang.String>(dataKeys);
        java.util.Collections.sort(sortedKeys);
        java.util.Collections.reverse(sortedKeys);
        java.util.ArrayList<java.lang.String> messages = new java.util.ArrayList<java.lang.String>();
        messages.add(java.lang.String.format(com.github.ucchyocean.mdi.UserDataHandler.SHOW_START, name, id));
        java.lang.String key = sortedKeys.get(id - 1);
        org.bukkit.configuration.ConfigurationSection section = config.getConfigurationSection(key);
        messages.addAll(convToShowMessage(section));
        messages.add(com.github.ucchyocean.mdi.UserDataHandler.SHOW_END);
        return com.github.ucchyocean.mdi.Utility.replaceColorCode(messages);
    }

    /**
     * 指定したコンフィグセクションを、一覧形式に変換して返す
     *
     * @param id
    ログID（メッセージの内容に使われる）
     * 		
     * @param section
    セクション
     * 		
     * @return 一覧形式
     */
    private java.util.ArrayList<java.lang.String> convToListMessage(int id, org.bukkit.configuration.ConfigurationSection section) {
        java.lang.String time = config.getConfigurationSection("time");
        java.lang.String items = me.johni0702.ButtonControl.ButtonControl.config.getConfigurationSection("items");
        java.lang.String armors = me.johni0702.ButtonControl.ButtonControl.config.getConfigurationSection("armors");
        java.lang.String msg = c.getConfigurationSection("msg");
        java.util.ArrayList<java.lang.String> result = new java.util.ArrayList<java.lang.String>();
        result.add(java.lang.String.format("&7| ID:&c%d&7 %s  items:&c%d&7, armors:&c%d&7", id, time, getItemCount(items), getItemCount(armors)));
        result.add("&7|   &c" + msg);
        return result;
    }

    /**
     * 指定したコンフィグセクションを、詳細形式に変換して返す
     *
     * @param section
    セクション
     * 		
     * @return 詳細形式
     */
    private java.util.ArrayList<java.lang.String> convToShowMessage(org.bukkit.configuration.ConfigurationSection section) {
        java.lang.String time = section.getString("time", "");
        java.lang.String msg = section.getString("msg", "");
        java.lang.String items_org = section.getString("items", "");
        java.lang.String[] items = items_org.split(",");
        java.lang.String[] armors = section.getString("armors", "").split(",");
        java.lang.System.out.println(("debug : <" + section.getString("items", "")) + ">");
        java.lang.System.out.println("debug : " + items.length);
        for (java.lang.String i : items) {
            java.lang.System.out.println(("       <" + i) + ">");
        }
        java.util.ArrayList<java.lang.String> result = new java.util.ArrayList<java.lang.String>();
        result.add("&7| &c" + time);
        result.add("&7|   &c" + msg);
        result.add("&7| items: ");
        if (items_org.length() > 0) {
            for (java.lang.String item : items) {
                try {
                    result.addAll(DeathInv.khandler.getDescFromItemInfo(item));
                } catch (java.lang.Exception e) {
                    result.add(e.getMessage());
                }
            }
        }
        result.add("&7| armors: ");
        for (java.lang.String armor : armors) {
            try {
                result.addAll(DeathInv.khandler.getDescFromItemInfo(armor));
            } catch (java.lang.Exception e) {
                result.add(e.getMessage());
            }
        }
        return result;
    }

    private int getItemCount(java.lang.String info) {
        if ((info == null) || (info.length() == 0)) {
            return 0;
        }
        int count = 0;
        java.lang.String[] items = info.split(",");
        for (java.lang.String i : items) {
            if (!i.equals("0")) {
                count++;
            }
        }
        return count;
    }

    /**
     * 最大のページ数
     *
     * @return 最大のページ数
     */
    public int getMaxOfPage() {
        return com.github.ucchyocean.mdi.UserDataHandler.MAX_LOG_SIZE / com.github.ucchyocean.mdi.UserDataHandler.PAGE_SIZE;
    }
}