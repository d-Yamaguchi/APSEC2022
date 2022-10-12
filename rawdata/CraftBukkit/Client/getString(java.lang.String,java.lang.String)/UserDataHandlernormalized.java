/**
 * ユーザーログを取得する
 *
 * @param name
 * 		ユーザー名
 * @param id
 * 		ログID
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
    java.lang.String _CVAR6 = ".items";
    org.bukkit.configuration.file.YamlConfiguration _CVAR5 = config;
    java.lang.String _CVAR7 = sortedKeys.get(id - 1) + _CVAR6;
    java.lang.String _CVAR8 = "";
    java.util.ArrayList<java.lang.String> _CVAR0 = result;
    java.lang.String _CVAR9 = _CVAR5.getString(_CVAR7, _CVAR8);
    _CVAR0.add(_CVAR9);
    java.util.ArrayList<java.lang.String> result = new java.util.ArrayList<java.lang.String>();
    java.io.File _CVAR1 = folder;
    java.lang.String _CVAR2 = ".yml";
    java.lang.String _CVAR12 = _CVAR2;
    java.lang.String _CVAR3 = name + _CVAR12;
    java.io.File _CVAR11 = _CVAR1;
    java.lang.String _CVAR13 = _CVAR3;
    // ファイルからロード（なければnullを返して終了）
    java.io.File file = new java.io.File(_CVAR11, _CVAR13);
    java.io.File _CVAR4 = file;
    java.io.File _CVAR14 = _CVAR4;
    org.bukkit.configuration.file.YamlConfiguration config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(_CVAR14);
    java.lang.String _CVAR16 = ".armors";
    org.bukkit.configuration.file.YamlConfiguration _CVAR15 = config;
    java.lang.String _CVAR17 = sortedKeys.get(id - 1) + _CVAR16;
    java.lang.String _CVAR18 = "";
    java.util.ArrayList<java.lang.String> _CVAR10 = result;
    java.lang.String _CVAR19 = _CVAR15.getString(_CVAR17, _CVAR18);
    _CVAR10.add(_CVAR19);
    return result;
}