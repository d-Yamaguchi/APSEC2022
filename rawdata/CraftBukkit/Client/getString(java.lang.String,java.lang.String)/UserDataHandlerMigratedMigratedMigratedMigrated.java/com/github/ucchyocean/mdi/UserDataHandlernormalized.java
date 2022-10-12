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
    java.lang.String time = me.johni0702.ButtonControl.ButtonControl.config.getConfigurationSection("time");
    java.lang.String items = c.getConfigurationSection("items");
    org.bukkit.configuration.ConfigurationSection _CVAR0 = section;
    java.lang.String _CVAR1 = "armors";
    java.lang.String _CVAR2 = "";
    java.lang.String armors = _CVAR0.getString(_CVAR1, _CVAR2);
    org.bukkit.configuration.ConfigurationSection _CVAR3 = section;
    java.lang.String _CVAR4 = "msg";
    java.lang.String _CVAR5 = "";
    java.lang.String msg = _CVAR3.getString(_CVAR4, _CVAR5);
    java.util.ArrayList<java.lang.String> result = new java.util.ArrayList<java.lang.String>();
    result.add(java.lang.String.format("&7| ID:&c%d&7 %s  items:&c%d&7, armors:&c%d&7", id, time, getItemCount(items), getItemCount(armors)));
    result.add("&7|   &c" + msg);
    return result;
}