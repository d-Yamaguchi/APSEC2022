@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_info);
    init();
    android.content.Intent intent = getIntent();
    int id = intent.getExtras().getInt("id");
    items = com.example.a85625.seoultour.DB.selectCommonInfo(getBaseContext(), id);
    com.example.a85625.seoultour.Info_item item = items.get(0);
    systemLang = getResources().getConfiguration().locale.getDefault().getLanguage();// 시스템설정 언어 가져오기

    if (systemLang.equals("en")) {
        title_tv.setTextSize(25);
    }
    title_tv.setText(item.title);
    if (item.firstImage.equals("null")) {
        com.bumptech.glide.Glide.with(this).load(R.drawable.no_image).into(image);
    } else {
        com.bumptech.glide.Glide.with(this).load(item.firstImage).into(image);
    }
    if (item.zipcode.equals("null")) {
        if (item.addr1.equals("null")) {
            zipcode_tv.setText("(NO DATA)");
        } else {
            zipcode_tv.setText(item.addr1);
        }
    } else {
        zipcode_tv.setText(((item.addr1 + " (") + item.zipcode) + ")");
    }
    if (item.tel.equals("null")) {
        tel_tv.setText("(NO DATA)");
    } else {
        tel_tv.setText(item.tel);
    }
    if (item.telname.equals("null")) {
        telname_tv.setText("(NO DATA)");
    } else {
        telname_tv.setText(item.telname);
    }
    if (item.homepage.equals("null")) {
        java.lang.String _CVAR1 = "(NO DATA)";
        android.widget.TextView _CVAR0 = homepage_tv;
        android.text.Spanned _CVAR2 = android.text.Html.fromHtml(_CVAR1);
        _CVAR0.setText(_CVAR2);
    } else {
        java.lang.String _CVAR4 = item.homepage;
        android.widget.TextView _CVAR3 = homepage_tv;
        android.text.Spanned _CVAR5 = android.text.Html.fromHtml(_CVAR4);
        _CVAR3.setText(_CVAR5);
    }
    homepage_tv.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    if (item.overview.equals("null")) {
        overview_tv.setText("(NO DATA)");
    } else {
        java.lang.String _CVAR7 = item.overview;
        android.widget.TextView _CVAR6 = overview_tv;
        android.text.Spanned _CVAR8 = android.text.Html.fromHtml(_CVAR7);
        _CVAR6.setText(_CVAR8);
    }
}