@java.lang.Override
public void onClick(android.view.View v) {
    if (me.shyboy.mengma.Common.SignConfig.isNetworkConnected(this) == false) {
        android.widget.Toast.makeText(this, "凑 ~ ~ 没联网", android.widget.Toast.LENGTH_SHORT).show();
        return;
    }
    java.lang.String description = et_decription.getText().toString();
    if (description.length() == 0) {
        android.widget.Toast.makeText(this, "描述内容不能为空.", android.widget.Toast.LENGTH_SHORT).show();
        return;
    }
    android.widget.TimePicker _CVAR0 = tp_start;
    java.lang.Integer _CVAR1 = _CVAR0.getCurrentMinute();
    java.lang.String _CVAR2 = reFormatTime(_CVAR1);
    java.lang.String _CVAR3 = (reFormatTime(tp_start.getCurrentHour()) + ":") + _CVAR2;
    java.lang.String start_at = _CVAR3 + ":00";
    android.widget.TimePicker _CVAR4 = tp_end;
    java.lang.Integer _CVAR5 = _CVAR4.getCurrentMinute();
    java.lang.String _CVAR6 = reFormatTime(_CVAR5);
    java.lang.String _CVAR7 = (reFormatTime(tp_end.getCurrentHour()) + ":") + _CVAR6;
    java.lang.String end_at = _CVAR7 + ":00";
    me.shyboy.mengma.Common.User user = new me.shyboy.mengma.database.UserHelper(this).getUser();
    me.shyboy.mengma.Common.Sign sign = new me.shyboy.mengma.Common.Sign(user.getSno(), start_at, end_at, description, user.getAccess_token());
    new me.shyboy.mengma.methods.OkHttpUtil(this).newSign(sign);
}