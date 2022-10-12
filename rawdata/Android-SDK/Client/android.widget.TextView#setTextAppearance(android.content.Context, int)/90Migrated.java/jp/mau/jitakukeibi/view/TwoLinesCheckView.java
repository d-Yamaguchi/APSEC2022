package jp.mau.jitakukeibi.view;
public class TwoLinesCheckView extends android.widget.LinearLayout {
    /**
     * ViewGroup.LayoutParams.WRAP_CONTENT
     */
    private static final int WRAP_CONTENT = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * ViewGroup.LayoutParams.MATCH_PARENT
     */
    @android.annotation.SuppressLint("InlinedApi")
    private static final int MATCH_PARENT = android.view.ViewGroup.LayoutParams.MATCH_PARENT;

    private android.content.Context _context;

    private android.widget.LinearLayout _textLayer;

    private android.widget.TextView _title;

    private android.widget.TextView _value;

    private android.widget.CheckBox _box;

    private java.util.ArrayList<android.view.View> _relatives;

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
        _title.setTextAppearance(WRAP_CONTENT);
        _value.setTextAppearance(_context, android.R.style.TextAppearance_Small);
        _textLayer.setOrientation(android.widget.LinearLayout.VERTICAL);
        _title.setGravity(android.view.Gravity.CENTER_VERTICAL);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT);
        _textLayer.addView(_title, params);
        params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT);
        _textLayer.addView(_value, params);
        params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.MATCH_PARENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT, 1);
        this.addView(_textLayer, params);
        _box.setGravity(android.view.Gravity.BOTTOM);
        _box.setOnCheckedChangeListener(__SmPLUnsupported__(0));
        params = new android.widget.LinearLayout.LayoutParams(jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT, jp.mau.jitakukeibi.view.TwoLinesCheckView.WRAP_CONTENT, 0);
        this.addView(_box, params);
    }

    /**
     * チェックする/はずす
     */
    public void setChecked(boolean checked) {
        _box.setChecked(checked);
        _textLayer.setEnabled(checked);
        _value.setTextAppearance(_context, checked ? android.R.style.TextAppearance_Small : android.R.style.TextAppearance_Small_Inverse);
        // 関連付けられたViewのEnableを変更
        for (int i = 0; i < _relatives.size(); i++) {
            _relatives.get(i).setEnabled(checked);
        }
    }

    /**
     * チェックボックスの状態を取得する
     *
     * @return チェックされているか
     */
    public boolean isChecked() {
        return _box.isChecked();
    }

    /**
     * タイトルを設定する
     *
     * @param str
     * 		タイトル文字列
     */
    public void setTitle(java.lang.String str) {
        _title.setText(str);
    }

    /**
     * タイトルを設定する
     *
     * @param id
     * 		タイトルリソースID
     */
    public void setTitle(int id) {
        _title.setText(_context.getString(id));
    }

    /**
     * 値の文字列を設定する
     *
     * @param str
     * 		値文字列
     */
    public void setValue(java.lang.String str) {
        _value.setText(str);
    }

    /**
     * 文字列部分をクリックした時の挙動を設定する
     */
    public void setOnClickListener(android.view.View.OnClickListener listener) {
        _textLayer.setOnClickListener(listener);
    }

    /**
     * チェックに他のViewも関連付ける
     */
    public void setRelatives(android.view.View view) {
        _relatives.add(view);
    }

    /**
     * 利用可否を変更する
     */
    public void setEnabled(boolean enabled) {
        _textLayer.setEnabled(enabled);
        _box.setEnabled(enabled);
        if (enabled) {
            _value.setTextAppearance(_context, _box.isChecked() ? android.R.style.TextAppearance_Small : android.R.style.TextAppearance_Small_Inverse);
            _title.setTextAppearance(_context, android.R.style.TextAppearance_Large);
        } else {
            _value.setTextAppearance(_context, android.R.style.TextAppearance_Small_Inverse);
            _title.setTextAppearance(_context, android.R.style.TextAppearance_Large_Inverse);
        }
    }
}