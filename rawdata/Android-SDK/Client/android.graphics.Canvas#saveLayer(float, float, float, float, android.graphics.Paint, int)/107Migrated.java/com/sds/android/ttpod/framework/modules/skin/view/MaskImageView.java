package com.sds.android.ttpod.framework.modules.skin.view;
public class MaskImageView extends com.sds.android.ttpod.framework.modules.skin.view.RecyclingImageView {
    private android.graphics.drawable.Drawable a;

    private final android.graphics.Xfermode b = new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN);

    public MaskImageView(android.content.Context context) {
        super(context);
    }

    public MaskImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MaskImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setMaskImageDrawable(android.graphics.drawable.Drawable drawable) {
        if ((drawable instanceof android.graphics.drawable.ShapeDrawable) || (drawable instanceof android.graphics.drawable.BitmapDrawable)) {
            this.a = drawable;
        } else {
            this.a = null;
        }
    }

    protected void onDraw(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = this.a;
        if (drawable != null) {
            android.graphics.Paint a2 = a(getDrawable());
            android.graphics.Paint a = a(drawable);
            if (!((a == null) || (a2 == null))) {
                android.graphics.Rect bounds = drawable.getBounds();
                int saveLayer = canvas.saveLayer(((float) (bounds.left)), ((float) (bounds.top)), ((float) (bounds.right)), ((float) (bounds.bottom)), a);
                android.graphics.Xfermode xfermode = a2.getXfermode();
                drawable.draw(canvas);
                a2.setXfermode(this.b);
                __SmPLUnsupported__(0).onDraw(canvas);
                canvas.restoreToCount(saveLayer);
                a2.setXfermode(xfermode);
                return;
            }
        }
        __SmPLUnsupported__(1).onDraw(canvas);
    }

    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (this.a != null) {
            int paddingTop = getPaddingTop() + i2;
            int paddingRight = i3 - getPaddingRight();
            int paddingLeft = paddingRight - (getPaddingLeft() + i);
            paddingRight = (i4 - getPaddingBottom()) - paddingTop;
            int intrinsicWidth = this.a.getIntrinsicWidth();
            int intrinsicHeight = this.a.getIntrinsicHeight();
            if ((intrinsicWidth > 0) && (intrinsicHeight > 0)) {
                float min = java.lang.Math.min(((float) (paddingLeft)) / ((float) (intrinsicWidth)), ((float) (paddingRight)) / ((float) (intrinsicHeight)));
                intrinsicWidth = ((int) (((float) (intrinsicWidth)) * min));
                paddingLeft = (paddingLeft - intrinsicWidth) >> 1;
                this.a.setBounds(paddingLeft, paddingTop, intrinsicWidth + paddingLeft, ((int) (min * ((float) (intrinsicHeight)))) + paddingTop);
            }
        }
        return frame;
    }

    private android.graphics.Paint a(android.graphics.drawable.Drawable drawable) {
        if (drawable instanceof android.graphics.drawable.ShapeDrawable) {
            return ((android.graphics.drawable.ShapeDrawable) (drawable)).getPaint();
        }
        if (drawable instanceof android.graphics.drawable.BitmapDrawable) {
            return ((android.graphics.drawable.BitmapDrawable) (drawable)).getPaint();
        }
        return null;
    }
}