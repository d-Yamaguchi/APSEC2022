@java.lang.Override
public void onUpdate(android.content.Context context, android.appwidget.AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    android.content.Intent intent;
    in.grat.esd2.MyWidgetProvider.dbhelper = in.grat.esd2.MyWidgetProvider.getDBHelper(context);
    java.util.Calendar calendar = java.util.Calendar.getInstance();
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
    java.text.SimpleDateFormat displayFormat = new java.text.SimpleDateFormat("EEEE, MMMM d");
    java.lang.String today = dateFormat.format(calendar.getTime());
    java.lang.String todayDisplay = displayFormat.format(calendar.getTime());
    for (int widgetId : appWidgetIds) {
        android.content.SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        java.lang.String lang = preferences.getString(in.grat.esd2.MyWidgetProvider.LANG_PREFIX_KEY + widgetId, "e");
        intent = new android.content.Intent(context.getApplicationContext(), in.grat.esd2.ESDailyActivity.class);
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(android.net.Uri.withAppendedPath(android.net.Uri.parse("esdLang://widget/id/"), java.lang.String.valueOf(widgetId)));
        intent.putExtra("lang", lang);
        java.lang.String theme = preferences.getString(in.grat.esd2.MyWidgetProvider.THEME_PREFIX_KEY + widgetId, "e");
        int layout = (theme.equalsIgnoreCase("normal")) ? R.layout.widget_layout : R.layout.widget_layout_dark;
        intent.putExtra("theme", theme);
        int fontSize = preferences.getInt(in.grat.esd2.MyWidgetProvider.FONT_SIZE_PREFIX_KEY + widgetId, 14);
        in.grat.esd2.DBHelper.VerseParts vp = in.grat.esd2.MyWidgetProvider.dbhelper.getVerseParts(today, "document", lang);
         _CVAR0 = vp.date;
        android.text.Spanned dateDisplay = android.text.Html.fromHtml(_CVAR0);
        todayDisplay = (dateDisplay != null) ? dateDisplay.toString().trim() : todayDisplay;
         _CVAR1 = vp.verse;
        android.text.Spanned _CVAR2 = android.text.Html.fromHtml(_CVAR1);
         _CVAR3 = _CVAR2.toString();
        java.lang.StringBuilder text = new java.lang.StringBuilder(_CVAR3);
        android.widget.RemoteViews remoteViews = new android.widget.RemoteViews(context.getPackageName(), layout);
        remoteViews.setTextViewText(R.id.tvTodaysDate, todayDisplay);
        remoteViews.setTextViewText(R.id.tvTodaysText, text);
        remoteViews.setFloat(R.id.tvTodaysDate, "setTextSize", ((float) (fontSize + 2)));
        remoteViews.setFloat(R.id.tvTodaysText, "setTextSize", ((float) (fontSize)));
        remoteViews.setOnClickPendingIntent(R.id.tvTodaysText, android.app.PendingIntent.getActivity(context, widgetId, intent, 0));
        appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
}