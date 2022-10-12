@java.lang.Override
public void onClick(android.view.View v) {
    jp.ikisaki.www.BasicModel.setDate((("&date=" + (datePicker.getMonth() + 1)) + ",") + datePicker.getDayOfMonth());
    android.widget.TimePicker _CVAR1 = timePicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentMinute();
    java.lang.String _CVAR3 = ((((((datePicker.getMonth() + 1) + "月") + datePicker.getDayOfMonth()) + "日") + timePicker.getCurrentHour()) + "時") + _CVAR2;
    java.lang.String setting = _CVAR3 + "分に";
    int _CVAR4 = 0;
    boolean _CVAR5 = timePicker.getCurrentMinute() == _CVAR4;
    if () {
        setting = (((((((datePicker.getMonth() + 1) + "月") + datePicker.getDayOfMonth()) + "日") + timePicker.getCurrentHour()) + "時") + "00") + "分に";
    }
    if (jp.ikisaki.www.BasicModel.getForwardOrBackward() == "&dir=backward") {
        setting += "到着";
    } else {
        setting += "出発";
    }
    jp.ikisaki.www.BasicModel.setSetting(setting);
    android.widget.TimePicker _CVAR7 = timePicker;
    java.lang.Integer _CVAR8 = _CVAR7.getCurrentMinute();
    java.lang.String _CVAR9 = (("&hour=" + timePicker.getCurrentHour()) + "&min=") + _CVAR8;
    jp.ikisaki.www.BasicModel.setTime(_CVAR9);
    jp.ikisaki.www.BasicModel.setMonth(datePicker.getMonth() + 1);
    jp.ikisaki.www.BasicModel.setDay(datePicker.getDayOfMonth());
    jp.ikisaki.www.BasicModel.setHour(timePicker.getCurrentHour());
    void _CVAR0 = R.id.time_and_route_tab3_timePicker;
    void _CVAR6 = _CVAR0;
    void _CVAR10 = _CVAR6;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (findViewById(_CVAR10)));
    android.widget.TimePicker _CVAR11 = timePicker;
    java.lang.Integer _CVAR12 = _CVAR11.getCurrentMinute();
    jp.ikisaki.www.BasicModel.setMinute(_CVAR12);
    jp.ikisaki.www.BasicModel.setYear(datePicker.getYear());
    jp.ikisaki.www.BasicModel.setTabNumber(2);
    android.content.Intent intent = new android.content.Intent(this, jp.ikisaki.www.RouteSearchActivity.class);
    intent.putExtra("keyword", "");
    startActivity(intent);
}