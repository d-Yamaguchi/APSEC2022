@java.lang.Override
public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
    final android.widget.LinearLayout itemView;
    if (convertView == null) {
        itemView = new android.widget.LinearLayout(context);
        java.lang.String inflater = android.content.Context.LAYOUT_INFLATER_SERVICE;
        android.view.LayoutInflater li;
        li = ((android.view.LayoutInflater) (context.getSystemService(inflater)));
        li.inflate(resource, itemView, true);
        android.widget.LinearLayout.LayoutParams linearLayoutParams = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(new android.widget.AbsListView.LayoutParams(linearLayoutParams));
        itemView.setPadding(5, 5, 5, 5);
    } else {
        itemView = ((android.widget.LinearLayout) (convertView));
    }
    android.widget.TextView textDate = ((android.widget.TextView) (itemView.findViewById(R.id.textDate)));
    android.widget.TextView textNoResult = ((android.widget.TextView) (itemView.findViewById(R.id.item_noti_comment_label)));
    android.widget.LinearLayout _CVAR0 = itemView;
    android.widget.LinearLayout _CVAR9 = _CVAR0;
    android.widget.LinearLayout _CVAR18 = _CVAR9;
    android.widget.LinearLayout _CVAR27 = _CVAR18;
    void _CVAR1 = R.id.item_noti_name_label;
    void _CVAR10 = _CVAR1;
    void _CVAR19 = _CVAR10;
    void _CVAR28 = _CVAR19;
    android.widget.LinearLayout _CVAR36 = _CVAR27;
    void _CVAR37 = _CVAR28;
    android.widget.TextView txtValNotifi = ((android.widget.TextView) (_CVAR36.findViewById(_CVAR37)));
    if (lstNotifications.size() == 0) {
        textNoResult.setVisibility(android.view.View.VISIBLE);
        textNoResult.setText(context.getResources().getString(R.string.noResult));
    } else {
        com.hitechno.sogoods.models.Notification notification = lstNotifications.get(position);
        java.lang.String database = notification.date;
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd h:m:s");
        java.text.SimpleDateFormat simpYear = new java.text.SimpleDateFormat("yyyy");
        java.text.SimpleDateFormat simpMonth = new java.text.SimpleDateFormat("MM");
        java.text.SimpleDateFormat simpDay = new java.text.SimpleDateFormat("dd");
        java.text.SimpleDateFormat simpHour = new java.text.SimpleDateFormat("h");
        java.text.SimpleDateFormat simpMinutes = new java.text.SimpleDateFormat("m");
        java.text.SimpleDateFormat simpSecond = new java.text.SimpleDateFormat("s");
        java.util.Date date = new java.util.Date();
        // String a = dateFormat.format(date);
        java.util.Date dateData;
        int yearData = 0;
        int monthData = 0;
        int dayData = 0;
        int hourData = 0;
        int minuteData = 0;
        int secondData = 0;
        try {
            dateData = dateFormat.parse(database);
            yearData = java.lang.Integer.parseInt(simpYear.format(dateData));
            monthData = java.lang.Integer.parseInt(simpMonth.format(dateData));
            dayData = java.lang.Integer.parseInt(simpDay.format(dateData));
            hourData = java.lang.Integer.parseInt(simpHour.format(dateData));
            minuteData = java.lang.Integer.parseInt(simpMinutes.format(dateData));
            secondData = java.lang.Integer.parseInt(simpSecond.format(dateData));
        } catch (java.text.ParseException e1) {
            e1.printStackTrace();
        }
        int yearNow = java.lang.Integer.parseInt(simpYear.format(date));
        int monthNow = java.lang.Integer.parseInt(simpMonth.format(date));
        int dayNow = java.lang.Integer.parseInt(simpDay.format(date));
        int hourNow = java.lang.Integer.parseInt(simpHour.format(date));
        int minuteNow = java.lang.Integer.parseInt(simpMinutes.format(date));
        int secondNow = java.lang.Integer.parseInt(simpSecond.format(date));
        java.lang.String yearAfter;
        java.lang.String monthAfter;
        java.lang.String dayAter;
        java.lang.String hourAfter;
        java.lang.String minuteAfter;
        java.lang.String secondAfter;
        java.lang.String lang = java.util.Locale.getDefault().getLanguage();
        if ((yearNow - yearData) == 0) {
            yearAfter = "";
        } else if (lang == "fr") {
            yearAfter = ((context.getResources().getString(R.string.elapsed_years) + java.lang.String.valueOf(yearNow - yearData)) + " ") + context.getResources().getString(R.string.elapsed_years_af);
        } else {
            yearAfter = (java.lang.String.valueOf(yearNow - yearData) + " ") + context.getResources().getString(R.string.elapsed_years);
        }
        if ((monthNow - monthData) == 0) {
            monthAfter = "";
        } else if (lang == "fr") {
            monthAfter = ((context.getResources().getString(R.string.elapsed_months) + java.lang.String.valueOf(monthNow - monthData)) + " ") + context.getResources().getString(R.string.elapsed_months_af);
        } else {
            monthAfter = (java.lang.String.valueOf(monthNow - monthData) + " ") + context.getResources().getString(R.string.elapsed_months);
        }
        if ((dayNow - dayData) == 0) {
            dayAter = "";
        } else if (lang == "fr") {
            dayAter = ((context.getResources().getString(R.string.elapsed_days) + java.lang.String.valueOf(dayNow - dayData)) + " ") + context.getResources().getString(R.string.elapsed_days_af);
        } else {
            dayAter = (java.lang.String.valueOf(dayNow - dayData) + " ") + context.getResources().getString(R.string.elapsed_days);
        }
        if ((hourNow - hourData) == 0) {
            hourAfter = "";
        } else if (lang == "fr") {
            hourAfter = ((context.getResources().getString(R.string.elapsed_hours) + java.lang.String.valueOf(hourNow - hourData)) + " ") + context.getResources().getString(R.string.elapsed_hours_af);
        } else {
            hourAfter = (java.lang.String.valueOf(hourNow - hourData) + " ") + context.getResources().getString(R.string.elapsed_hours);
        }
        if ((minuteNow - minuteData) == 0) {
            minuteAfter = "";
        } else if (lang == "fr") {
            minuteAfter = ((context.getResources().getString(R.string.elapsed_minutes) + java.lang.String.valueOf(minuteNow - minuteData)) + " ") + context.getResources().getString(R.string.elapsed_minutes_af);
        } else {
            minuteAfter = (java.lang.String.valueOf(minuteNow - minuteData) + " ") + context.getResources().getString(R.string.elapsed_minutes);
        }
        if ((secondNow - secondData) == 0) {
            secondAfter = "";
        } else if (lang == "fr") {
            secondAfter = ((context.getResources().getString(R.string.elapsed_seconds) + java.lang.String.valueOf(secondNow - secondData)) + " ") + context.getResources().getString(R.string.elapsed_seconds_af);
        } else {
            secondAfter = (java.lang.String.valueOf(secondNow - secondData) + " ") + context.getResources().getString(R.string.elapsed_seconds);
        }
        if (yearAfter != "") {
            textDate.setText(yearAfter);
        } else if (monthAfter != "") {
            textDate.setText(monthAfter);
        } else if (dayAter != "") {
            textDate.setText(dayAter);
        } else if (hourAfter != "") {
            textDate.setText(hourAfter);
        } else if (minuteAfter != "") {
            textDate.setText(minuteAfter);
        } else if (secondAfter != "") {
            textDate.setText(secondAfter);
        }
        if (notification.type.equals("following_add_comment")) {
            android.content.Context _CVAR3 = context;
            android.content.res.Resources _CVAR4 = _CVAR3.getResources();
            void _CVAR5 = R.string.added_new_comment;
            java.lang.String _CVAR6 = _CVAR4.getString(_CVAR5);
            java.lang.String html = (notification.username + " ") + _CVAR6;
            java.lang.String _CVAR7 = html;
            android.widget.TextView _CVAR2 = txtValNotifi;
            android.text.Spanned _CVAR8 = android.text.Html.fromHtml(_CVAR7);
            _CVAR2.setText(_CVAR8);
        } else if (notification.type.equals("new_follower")) {
            android.content.Context _CVAR12 = context;
            android.content.res.Resources _CVAR13 = _CVAR12.getResources();
            void _CVAR14 = R.string.followed_you;
            java.lang.String _CVAR15 = _CVAR13.getString(_CVAR14);
            java.lang.String html = (notification.username + " ") + _CVAR15;
            java.lang.String _CVAR16 = html;
            android.widget.TextView _CVAR11 = txtValNotifi;
            android.text.Spanned _CVAR17 = android.text.Html.fromHtml(_CVAR16);
            _CVAR11.setText(_CVAR17);
        } else if (notification.type.equals("following_add_product")) {
            android.content.Context _CVAR21 = context;
            android.content.res.Resources _CVAR22 = _CVAR21.getResources();
            void _CVAR23 = R.string.added_new_product;
            java.lang.String _CVAR24 = _CVAR22.getString(_CVAR23);
            java.lang.String html = (notification.username + " ") + _CVAR24;
            java.lang.String _CVAR25 = html;
            android.widget.TextView _CVAR20 = txtValNotifi;
            android.text.Spanned _CVAR26 = android.text.Html.fromHtml(_CVAR25);
            _CVAR20.setText(_CVAR26);
        } else if (notification.type.equals("new_comment_on_my_product")) {
            android.content.Context _CVAR30 = context;
            android.content.res.Resources _CVAR31 = _CVAR30.getResources();
            void _CVAR32 = R.string.new_comment_on_your_product;
            java.lang.String _CVAR33 = _CVAR31.getString(_CVAR32);
            java.lang.String html = (((context.getResources().getString(R.string.there_is) + " ") + notification.nbcomments) + " ") + _CVAR33;
            java.lang.String _CVAR34 = html;
            android.widget.TextView _CVAR29 = txtValNotifi;
            android.text.Spanned _CVAR35 = android.text.Html.fromHtml(_CVAR34);
            _CVAR29.setText(_CVAR35);
        } else if (notification.type.equals("following_add_picture")) {
            android.content.Context _CVAR39 = context;
            android.content.res.Resources _CVAR40 = _CVAR39.getResources();
            void _CVAR41 = R.string.added_new_picture;
            java.lang.String _CVAR42 = _CVAR40.getString(_CVAR41);
            java.lang.String html = (notification.username + " ") + _CVAR42;
            java.lang.String _CVAR43 = html;
            android.widget.TextView _CVAR38 = txtValNotifi;
            android.text.Spanned _CVAR44 = android.text.Html.fromHtml(_CVAR43);
            _CVAR38.setText(_CVAR44);
        }
    }
    return itemView;
}