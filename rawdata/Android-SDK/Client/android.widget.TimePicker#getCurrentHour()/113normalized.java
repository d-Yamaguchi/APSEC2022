@java.lang.Override
public void onClick(android.view.View v) {
    jp.ikisaki.www.BasicModel.setDate((("&date=" + (datePicker.getMonth() + 1)) + ",") + datePicker.getDayOfMonth());
    android.widget.TimePicker _CVAR1 = timePicker;
    java.lang.Integer _CVAR2 = _CVAR1.getCurrentHour();
    java.lang.String _CVAR3 = ((((datePicker.getMonth() + 1) + "月") + datePicker.getDayOfMonth()) + "日") + _CVAR2;
    java.lang.String _CVAR4 = _CVAR3 + "時";
    java.lang.String _CVAR5 = _CVAR4 + timePicker.getCurrentMinute();
    java.lang.String setting = _CVAR5 + "分に";
    if (timePicker.getCurrentMinute() == 0) {
        android.widget.TimePicker _CVAR7 = timePicker;
        java.lang.Integer _CVAR8 = _CVAR7.getCurrentHour();
        java.lang.String _CVAR9 = ((((datePicker.getMonth() + 1) + "月") + datePicker.getDayOfMonth()) + "日") + _CVAR8;
        java.lang.String _CVAR10 = _CVAR9 + "時";
        java.lang.String _CVAR11 = _CVAR10 + "00";
        java.lang.String _CVAR12 = _CVAR11 + "分に";
        setting = _CVAR12;
    }
    if (jp.ikisaki.www.BasicModel.getForwardOrBackward() == "&dir=backward") {
        setting += "到着";
    } else {
        setting += "出発";
    }
    jp.ikisaki.www.BasicModel.setSetting(setting);
    android.widget.TimePicker _CVAR14 = timePicker;
    java.lang.Integer _CVAR15 = _CVAR14.getCurrentHour();
    java.lang.String _CVAR16 = "&hour=" + _CVAR15;
    java.lang.String _CVAR17 = _CVAR16 + "&min=";
    java.lang.String _CVAR18 = _CVAR17 + timePicker.getCurrentMinute();
    jp.ikisaki.www.BasicModel.setTime(_CVAR18);
    jp.ikisaki.www.BasicModel.setMonth(datePicker.getMonth() + 1);
    jp.ikisaki.www.BasicModel.setDay(datePicker.getDayOfMonth());
    void _CVAR0 = R.id.time_and_route_tab3_timePicker;
    void _CVAR6 = _CVAR0;
    void _CVAR13 = _CVAR6;
    void _CVAR19 = _CVAR13;
    final android.widget.TimePicker timePicker = ((android.widget.TimePicker) (findViewById(_CVAR19)));
    android.widget.TimePicker _CVAR20 = timePicker;
    java.lang.Integer _CVAR21 = _CVAR20.getCurrentHour();
    jp.ikisaki.www.BasicModel.setHour(_CVAR21);
    jp.ikisaki.www.BasicModel.setMinute(timePicker.getCurrentMinute());
    jp.ikisaki.www.BasicModel.setYear(datePicker.getYear());
    jp.ikisaki.www.BasicModel.setTabNumber(2);
    android.content.Intent intent = new android.content.Intent(this, jp.ikisaki.www.RouteSearchActivity.class);
    intent.putExtra("keyword", "");
    startActivity(intent);
}