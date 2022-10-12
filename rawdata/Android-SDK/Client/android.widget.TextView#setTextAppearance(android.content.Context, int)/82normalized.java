public TextGroup(android.content.Context paramContext) {
    super();
    a = new android.widget.TextView(paramContext);
    b = new android.widget.TextView(paramContext);
    c = new android.widget.TextView(paramContext);
    d = new android.widget.TextView(paramContext);
    if (com.cbs.app.adapter.CustomAdapterViewGroup.a(this)) {
        android.widget.TextView _CVAR0 = a;
        android.content.Context _CVAR1 = paramContext;
        int _CVAR2 = 2131427544;
        _CVAR0.setTextAppearance(_CVAR1, _CVAR2);
        android.widget.TextView _CVAR3 = b;
        android.content.Context _CVAR4 = paramContext;
        int _CVAR5 = 2131427545;
        _CVAR3.setTextAppearance(_CVAR4, _CVAR5);
        android.widget.TextView _CVAR6 = c;
        android.content.Context _CVAR7 = paramContext;
        int _CVAR8 = 2131427545;
        _CVAR6.setTextAppearance(_CVAR7, _CVAR8);
        android.widget.TextView _CVAR9 = d;
        android.content.Context _CVAR10 = paramContext;
        int _CVAR11 = 2131427545;
        _CVAR9.setTextAppearance(_CVAR10, _CVAR11);
    }
    for (; ;) {
        a.setMaxLines(2);
        a.setEllipsize(TextUtils.TruncateAt.END);
        b.setMaxLines(2);
        b.setEllipsize(TextUtils.TruncateAt.END);
        addView(a);
        addView(b);
        addView(c);
        addView(d);
        return;
        android.widget.TextView _CVAR12 = a;
        android.content.Context _CVAR13 = paramContext;
        int _CVAR14 = 2131427542;
        _CVAR12.setTextAppearance(_CVAR13, _CVAR14);
        android.widget.TextView _CVAR15 = b;
        android.content.Context _CVAR16 = paramContext;
        int _CVAR17 = 2131427546;
        _CVAR15.setTextAppearance(_CVAR16, _CVAR17);
        android.widget.TextView _CVAR18 = c;
        android.content.Context _CVAR19 = paramContext;
        int _CVAR20 = 2131427546;
        _CVAR18.setTextAppearance(_CVAR19, _CVAR20);
        android.widget.TextView _CVAR21 = d;
        android.content.Context _CVAR22 = paramContext;
        int _CVAR23 = 2131427546;
        _CVAR21.setTextAppearance(_CVAR22, _CVAR23);
        d.setTextColor(com.cbs.app.adapter.CustomAdapterViewGroup.b(this));
    }
}