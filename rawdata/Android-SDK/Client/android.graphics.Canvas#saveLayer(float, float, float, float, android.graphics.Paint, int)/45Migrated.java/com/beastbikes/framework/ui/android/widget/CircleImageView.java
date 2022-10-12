package com.beastbikes.framework.ui.android.widget;
import com.beastbikes.framework.ui.android.C2824R;
public class CircleImageView extends android.widget.ImageView {
    private final int borderColor;

    private int borderType;

    private int borderWidth;

    private final android.graphics.Paint edgePaint;

    private android.graphics.Bitmap mask;

    private final android.graphics.Paint maskPaint;

    private final android.graphics.Paint nonePaint;

    public CircleImageView(android.content.Context context) {
        this(context, null);
        this.borderWidth = 0;
    }

    public CircleImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C2824R.styleable.CircularImageView);
        this.borderColor = obtainStyledAttributes.getColor(C2824R.styleable.CircularImageView_borderColor, 0);
        this.borderWidth = ((int) (obtainStyledAttributes.getDimension(C2824R.styleable.CircularImageView_imageBorderWidth, 6.0F)));
        this.borderType = obtainStyledAttributes.getInt(C2824R.styleable.CircularImageView_borderType, 1);
        obtainStyledAttributes.recycle();
        this.nonePaint = new android.graphics.Paint();
        this.nonePaint.setAntiAlias(true);
        this.edgePaint = new android.graphics.Paint();
        this.edgePaint.setStyle(android.graphics.Paint.Style.STROKE);
        this.edgePaint.setAntiAlias(true);
        this.edgePaint.setColor(this.borderColor);
        this.edgePaint.setStrokeWidth(((float) (this.borderWidth)));
        this.maskPaint = new android.graphics.Paint();
        this.maskPaint.setAntiAlias(true);
        this.maskPaint.setFilterBitmap(false);
        this.maskPaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
    }

    protected void onDraw(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = getDrawable();
        if ((drawable != null) && (!(drawable instanceof android.graphics.drawable.NinePatchDrawable))) {
            int width = getWidth();
            int height = getHeight();
            int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (width)), ((float) (height)), this.nonePaint);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            if ((this.mask == null) || this.mask.isRecycled()) {
                this.mask = createOvalBitmap(width, height);
            }
            canvas.drawBitmap(this.mask, 0.0F, 0.0F, this.maskPaint);
            canvas.restoreToCount(saveLayer);
            drawBorder(canvas, width, height);
        }
    }

    private void drawBorder(android.graphics.Canvas canvas, int i, int i2) {
        if (this.borderWidth > 0) {
            canvas.drawCircle(((float) (i >> 1)), ((float) (i2 >> 1)), ((float) ((i - this.borderWidth) >> 1)), this.edgePaint);
        }
    }

    public android.graphics.Bitmap createOvalBitmap(int i, int i2) {
        android.graphics.RectF rectF;
        android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(i, i2, android.graphics.Bitmap.Config.ARGB_8888);
        android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
        int i3 = (this.borderWidth > 1) ? this.borderWidth >> 1 : 1;
        if (this.borderType == 1) {
            rectF = new android.graphics.RectF(((float) (i3 * 2)), ((float) (i3 * 2)), ((float) (i - (i3 * 2))), ((float) (i2 - (i3 * 2))));
        } else {
            rectF = new android.graphics.RectF(0.0F, 0.0F, ((float) (i)), ((float) (i2)));
        }
        canvas.drawOval(rectF, this.nonePaint);
        return createBitmap;
    }
}