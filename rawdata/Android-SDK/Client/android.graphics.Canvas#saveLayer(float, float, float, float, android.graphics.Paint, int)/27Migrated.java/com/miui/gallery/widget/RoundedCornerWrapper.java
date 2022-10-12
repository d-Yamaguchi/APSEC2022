package com.miui.gallery.widget;
import com.miui.gallery.R;
public class RoundedCornerWrapper extends android.widget.LinearLayout {
    private android.graphics.Paint mPaint;

    private int mRadius;

    public RoundedCornerWrapper(android.content.Context context) {
        this(context, ((android.util.AttributeSet) (null)));
    }

    public RoundedCornerWrapper(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundedCornerWrapper(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundedCornerWrapper, i, 0);
        this.mRadius = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        obtainStyledAttributes.recycle();
        ensurePaint();
    }

    private void ensurePaint() {
        if (this.mPaint == null) {
            this.mPaint = new android.graphics.Paint();
            this.mPaint.setAntiAlias(true);
            this.mPaint.setDither(true);
            this.mPaint.setColor(getResources().getColor(R.color.image_stroke_color_non_transparent));
            this.mPaint.setStrokeWidth(1.0F);
            this.mPaint.setStyle(android.graphics.Paint.Style.STROKE);
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(android.graphics.Canvas canvas) {
        int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (getWidth())), ((float) (getHeight())), ((android.graphics.Paint) (null)));
        android.graphics.Path path = new android.graphics.Path();
        android.graphics.RectF rectF = new android.graphics.RectF(((float) (getPaddingLeft())), ((float) (getPaddingTop())), ((float) (getWidth() - getPaddingRight())), ((float) (getHeight() - getPaddingBottom())));
        int i = this.mRadius;
        path.addRoundRect(rectF, ((float) (i)), ((float) (i)), android.graphics.Path.Direction.CW);
        canvas.clipPath(path, android.graphics.Region.Op.INTERSECT);
        __SmPLUnsupported__(0).dispatchDraw(canvas);
        int i2 = this.mRadius;
        canvas.drawRoundRect(rectF, ((float) (i2)), ((float) (i2)), this.mPaint);
        canvas.restoreToCount(saveLayer);
    }
}