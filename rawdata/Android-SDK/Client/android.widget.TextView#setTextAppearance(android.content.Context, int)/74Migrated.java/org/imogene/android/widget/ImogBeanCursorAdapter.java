package org.imogene.android.widget;
import org.imogene.android.database.ImogBeanCursor;
import org.imogene.android.template.R;
public class ImogBeanCursorAdapter extends android.widget.CursorAdapter {
    private final android.graphics.drawable.Drawable mColor;

    private final int mLayoutId;

    public ImogBeanCursorAdapter(android.content.Context context, android.database.Cursor c, android.graphics.drawable.Drawable color, int layoutId) {
        super(context, c);
        mColor = color;
        mLayoutId = layoutId;
    }

    public ImogBeanCursorAdapter(android.content.Context context, android.database.Cursor c, android.graphics.drawable.Drawable color, boolean multiple) {
        this(context, c, color, multiple ? R.layout.imog__entity_row_multiple : R.layout.imog__entity_row);
    }

    public java.lang.String getItemStringId(int position) {
        org.imogene.android.database.ImogBeanCursor<?> c = ((org.imogene.android.database.ImogBeanCursor<?>) (getItem(position)));
        if (c != null) {
            return c.getId();
        }
        return null;
    }

    @java.lang.Override
    public long getItemId(int position) {
        // Good old trick, we need to return something for ListView.CHOICE_MODE_MULTIPLE to work in ListAdapter
        return position;
    }

    @java.lang.Override
    public void bindView(android.view.View view, android.content.Context context, android.database.Cursor cursor) {
        org.imogene.android.database.ImogBeanCursor<?> c = ((org.imogene.android.database.ImogBeanCursor<?>) (cursor));
        view.findViewById(android.R.id.background).setBackgroundDrawable(mColor);
        main.setText(null);
        secondary.setText(null);
        android.widget.TextView main = ((android.widget.TextView) (view.findViewById(android.R.id.text1)));
        android.widget.TextView secondary = ((android.widget.TextView) (view.findViewById(android.R.id.text2)));
        if (c.getFlagRead()) {
            view.setBackgroundResource(android.R.drawable.list_selector_background);
            TextView textView = new android.widget.TextView(context);
            textView.setTextAppearance(mLayoutId);
            main.setTypeface(android.graphics.Typeface.DEFAULT);
            secondary.setTextAppearance(context, android.R.style.TextAppearance_Small);
            secondary.setTypeface(android.graphics.Typeface.DEFAULT);
        } else {
            view.setBackgroundResource(R.drawable.imog__list_selector_background_inverse);
            main.setTextAppearance(context, android.R.style.TextAppearance_Medium_Inverse);
            main.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
            secondary.setTextAppearance(context, android.R.style.TextAppearance_Small_Inverse);
            secondary.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        }
        android.widget.ImageView icon = ((android.widget.ImageView) (view.findViewById(android.R.id.icon)));
        if (icon != null) {
            icon.setImageResource(android.R.drawable.stat_notify_sync);
            icon.setVisibility(c.getFlagSynchronized() ? android.view.View.GONE : android.view.View.VISIBLE);
        }
        main.setText(c.getMainDisplay(context));
        java.lang.String sec = c.getSecondaryDisplay(context);
        if (android.text.TextUtils.isEmpty(sec.trim())) {
            secondary.setVisibility(android.view.View.GONE);
        } else {
            secondary.setVisibility(android.view.View.VISIBLE);
            secondary.setText(sec);
        }
    }

    @java.lang.Override
    public android.view.View newView(android.content.Context context, android.database.Cursor cursor, android.view.ViewGroup parent) {
        android.view.LayoutInflater inflater = android.view.LayoutInflater.from(context);
        return inflater.inflate(mLayoutId, parent, false);
    }
}