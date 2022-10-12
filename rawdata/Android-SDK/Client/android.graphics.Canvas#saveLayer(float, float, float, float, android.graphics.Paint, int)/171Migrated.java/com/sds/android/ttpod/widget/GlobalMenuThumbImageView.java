package com.sds.android.ttpod.widget;
import com.sds.android.sdk.lib.util.b;
import com.sds.android.ttpod.R;
import com.sds.android.ttpod.b.v;
import com.sds.android.ttpod.framework.modules.theme.ThemeElement;
public class GlobalMenuThumbImageView extends android.widget.ImageView {
    private android.graphics.Bitmap a;

    private float b;

    private android.graphics.PorterDuffXfermode c = new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT);

    private android.graphics.Paint d = new android.graphics.Paint(1);

    private android.graphics.drawable.Drawable e;

    public GlobalMenuThumbImageView(android.content.Context context) {
        super(context);
    }

    public GlobalMenuThumbImageView(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GlobalMenuThumbImageView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.a = null;
    }

    public void setThumbDrawable(android.graphics.drawable.Drawable drawable) {
        this.e = drawable;
        this.a = null;
        invalidate();
    }

    private void b() {
        int intrinsicHeight = this.e.getIntrinsicHeight();
        int height = getHeight();
        if ((intrinsicHeight == height) && (this.e instanceof android.graphics.drawable.BitmapDrawable)) {
            this.a = ((android.graphics.drawable.BitmapDrawable) (this.e)).getBitmap();
        } else {
            float f = (((float) (height)) * 1.0F) / ((float) (intrinsicHeight));
            this.a = b.a(this.e, ((int) (((float) (this.e.getIntrinsicWidth())) * f)), ((int) (((float) (intrinsicHeight)) * f)));
        }
        this.d.setShader(new android.graphics.BitmapShader(this.a, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP));
    }

    protected void onDraw(android.graphics.Canvas canvas) {
        if (this.e == null) {
            __SmPLUnsupported__(0).onDraw(canvas);
            return;
        }
        if (this.a == null) {
            b();
        }
        int height = getHeight();
        android.graphics.Canvas canvas2 = canvas;
        int saveLayer = canvas.saveLayer(((float) (getPaddingLeft())), ((float) (getPaddingTop())), ((float) (getWidth() - getPaddingRight())), ((float) (height - getPaddingBottom())), null);
        __SmPLUnsupported__(1).onDraw(canvas);
        int width = this.a.getWidth();
        int width2 = getWidth() >> 1;
        width2 = (((int) (((float) (width2)) * (1.0F - this.b))) + (width2 >> 1)) - (width >> 1);
        this.d.setXfermode(this.c);
        canvas.translate(((float) (width2)), 0.0F);
        canvas.drawRect(0.0F, 0.0F, ((float) (width)), ((float) (height)), this.d);
        this.d.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    public void setThumbOffset(float f) {
        this.b = f;
        invalidate();
    }

    public void a() {
        setImageDrawable(v.a(ThemeElement.SETTING_MENU_INDICATOR_BACKGROUND_IMAGE, ((int) (R.drawable.img_menu_indicator_background))));
        setThumbDrawable(getResources().getDrawable(R.drawable.img_menu_indicator_thumb));
    }
}