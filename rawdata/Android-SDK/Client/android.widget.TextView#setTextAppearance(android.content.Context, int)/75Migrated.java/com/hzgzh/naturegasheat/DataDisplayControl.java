package com.hzgzh.naturegasheat;
public class DataDisplayControl extends android.widget.LinearLayout {
    android.content.Context mContext;

    com.hzgzh.naturegasheat.DataDisplayControl.ViewHolder vh = null;

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
        TextView textView = new android.widget.TextView(context);
        int resid = android.R.style.TextAppearance_Large;
        textView.setTextAppearance(resid);
        vh.symbol.setTextAppearance(context, android.R.style.TextAppearance_Small);
        android.content.res.TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Custom);
        int indexCount = a.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = a.getIndex(i);
            __SmPLUnsupported__(0);
        }
        a.recycle();
        vh.unit = new android.widget.TextView(mContext);
        vh.unit.setLayoutParams(lp);
        addView(vh.unit);
        vh.unit.setTextAppearance(context, android.R.style.TextAppearance_Small);
    }

    public java.lang.String getName() {
        return vh.name.getText().toString();
    }

    public void setName(java.lang.String name) {
        vh.name.setText(name);
    }

    public java.lang.String getSymbol() {
        return vh.symbol.getText().toString();
    }

    public void setSymbol(java.lang.String symbol) {
        vh.symbol.setText(symbol);
    }

    public java.lang.String getTextValue() {
        return vh.value.getText().toString();
    }

    public void setTextValue(java.lang.String value) {
        vh.value.setText(value);
    }

    public void setUnit(java.lang.String unit) {
        vh.unit.setText(unit);
    }

    public java.lang.String getEditValue() {
        if (vh.evalue != null)
            return vh.evalue.getText().toString();

        return null;
    }

    public void setEditValue(java.lang.String value) {
        vh.evalue.setText(value);
    }

    public java.lang.String getSpinnerValue() {
        return ((java.lang.String) (vh.svalue.getSelectedItem()));
    }

    public void setSpinnerValue(java.lang.String[] str) {
        if (vh.svalue != null) {
            android.widget.ArrayAdapter<java.lang.String> adapter = new android.widget.ArrayAdapter<java.lang.String>(mContext, android.R.layout.simple_spinner_item, str);
            vh.svalue.setAdapter(adapter);
        }
    }

    class ViewHolder {
        public android.widget.TextView name;

        public android.widget.TextView symbol;

        public android.widget.TextView value;

        public android.widget.TextView unit;

        public android.widget.EditText evalue;

        public android.widget.Spinner svalue;
    }
}