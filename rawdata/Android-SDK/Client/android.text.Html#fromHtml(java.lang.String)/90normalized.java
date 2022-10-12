protected void onCreate(android.os.Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_feature_list);
    if (savedInstanceState == null) {
        getSupportFragmentManager().beginTransaction().add(R.id.container, new com.dark.droid.features.FeatureList.PlaceholderFragment()).commit();
    }
    t.setScroller(new android.widget.Scroller(this));
    t.setVerticalScrollBarEnabled(true);
    t.setMovementMethod(new android.text.method.ScrollingMovementMethod());
    com.dark.droid.features.Feature[] features = getFeatures();
    for (com.dark.droid.features.Feature feature : features) {
        text += ("<p>" + feature.msg) + "</p>";
    }
    void _CVAR0 = R.id.editText;
    android.widget.TextView t = ((android.widget.TextView) (findViewById(_CVAR0)));
    java.lang.String text = "<h1><b><u>All</u></b><h1>";
    java.lang.String _CVAR2 = text;
    android.widget.TextView _CVAR1 = t;
    android.text.Spanned _CVAR3 = android.text.Html.fromHtml(_CVAR2);
    _CVAR1.setText(_CVAR3);
}