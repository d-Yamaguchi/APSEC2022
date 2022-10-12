public TwoLinesCheckView(android.content.Context context) {
    super(context);
    this.setOrientation(android.widget.LinearLayout.HORIZONTAL);
    this.setPadding(6, 6, 6, 6);
    _context = context;
    _relatives = new java.util.ArrayList<android.view.View>();
    _textLayer = new android.widget.LinearLayout(_context);
    _title = new android.widget.TextView(_context);
    _value = new android.widget.TextView(_context);
    _box = new android.widget.CheckBox(_context);
    android.widget.TextView _CVAR0 = _title;
    android.content.Context _CVAR1 = _context;
    int _CVAR2 = android.R.style.TextAppearance_Large;
    _CVAR0.setTextAppearance(_CVAR1, _CVAR2);
    android.widget.TextView _CVAR3 = _value;
    android.content.Context _CVAR4 = _context;
    int _CVAR5 = android.R.style.TextAppearance_Small;
    _CVAR3.setTextAppearance(_CVAR4, _CVAR5);
    _textLayer.setOrientation(android.widget.LinearLayout.VERTICAL);
    _title.setGravity(android.view.Gravity.CENTER_VERTICAL);
    android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT);
    _textLayer.addView(_title, params);
    params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT);
    _textLayer.addView(_value, params);
    params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT, 1);
    this.addView(_textLayer, params);
    _box.setGravity(android.view.Gravity.BOTTOM);
    _box.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
        @java.lang.Override
        public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
            setChecked(isChecked);
        }
    });
    params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT, 0);
    this.addView(_box, params);
}