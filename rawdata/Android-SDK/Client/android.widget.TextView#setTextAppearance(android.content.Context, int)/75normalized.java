public DataDisplayControl(android.content.Context context, android.util.AttributeSet attrs) {
    super(context, attrs);
    mContext = context;
    setLayoutParams(new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
    setOrientation(android.widget.LinearLayout.HORIZONTAL);
    vh = new com.hzgzh.naturegasheat.DataDisplayControl.ViewHolder();
    vh.name = new android.widget.TextView(context);
    vh.symbol = new android.widget.TextView(context);
    android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    vh.name.setLayoutParams(new android.widget.LinearLayout.LayoutParams(0, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, ((float) (1.7))));
    vh.symbol.setLayoutParams(lp);
    android.widget.TextView _CVAR0 = vh.name;
    android.content.Context _CVAR1 = context;
    int _CVAR2 = android.R.style.TextAppearance_Small;
    _CVAR0.setTextAppearance(_CVAR1, _CVAR2);
    android.widget.TextView _CVAR3 = vh.symbol;
    android.content.Context _CVAR4 = context;
    int _CVAR5 = android.R.style.TextAppearance_Small;
    _CVAR3.setTextAppearance(_CVAR4, _CVAR5);
    android.content.res.TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Custom);
    int indexCount = a.getIndexCount();
    for (int i = 0; i < indexCount; i++) {
        int attr = a.getIndex(i);
        switch (attr) {
            case R.styleable.Custom_type :
                // ��ȡmyText����ֵ
                java.lang.String myType = a.getString(attr);
                // �õ�ֵ��Ϳ��԰�����Ҫ��ʹ���ˣ��������Ǹ�Button���ð�ť��ʾ����
                if (myType.equals("text")) {
                    vh.value = new android.widget.TextView(mContext);
                    vh.value.setLayoutParams(lp);
                    addView(vh.name);
                    addView(vh.symbol);
                    addView(vh.value);
                    android.widget.TextView _CVAR6 = vh.value;
                    android.content.Context _CVAR7 = context;
                    int _CVAR8 = android.R.style.TextAppearance_Small;
                    _CVAR6.setTextAppearance(_CVAR7, _CVAR8);
                }
                if (myType.equals("edit")) {
                    vh.evalue = new android.widget.EditText(mContext);
                    vh.evalue.setLayoutParams(lp);
                    addView(vh.name);
                    addView(vh.symbol);
                    addView(vh.evalue);
                    android.widget.EditText _CVAR9 = vh.evalue;
                    android.content.Context _CVAR10 = context;
                    int _CVAR11 = android.R.style.TextAppearance_Small;
                    _CVAR9.setTextAppearance(_CVAR10, _CVAR11);
                }
                if (myType.equals("spinner")) {
                    vh.svalue = new android.widget.Spinner(mContext);
                    vh.svalue.setLayoutParams(lp);
                    addView(vh.name);
                    addView(vh.symbol);
                    addView(vh.svalue);
                }
        }
    }
    a.recycle();
    vh.unit = new android.widget.TextView(mContext);
    vh.unit.setLayoutParams(lp);
    addView(vh.unit);
    android.widget.TextView _CVAR12 = vh.unit;
    android.content.Context _CVAR13 = context;
    int _CVAR14 = android.R.style.TextAppearance_Small;
    _CVAR12.setTextAppearance(_CVAR13, _CVAR14);
}