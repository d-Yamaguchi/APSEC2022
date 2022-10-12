package com.android.camera.ui;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
public class MimojiTypeHorizonScrollView extends android.widget.HorizontalScrollView {
    private boolean mIsRTL;

    private android.graphics.Paint mPaint;

    private int mWidth;

    public MimojiTypeHorizonScrollView(android.content.Context context) {
        super(context);
        initView();
    }

    public MimojiTypeHorizonScrollView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public MimojiTypeHorizonScrollView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    /* access modifiers changed from: protected */
    public boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        android.graphics.Canvas canvas2 = canvas;
        int width = getWidth();
        int height = getHeight();
        float f2 = ((float) (height));
        int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (java.lang.Math.max(width, view.getWidth()))), f2, ((android.graphics.Paint) (null)));
        boolean drawChild = __SmPLUnsupported__(0).drawChild(canvas, view, j);
        canvas2.translate(((float) (computeHorizontalScrollOffset())), 0.0F);
        float f3 = ((float) (width));
        float f4 = f3 / 2.0F;
        float f5 = f2 / 2.0F;
        float f6 = ((float) (height - width)) / 2.0F;
        int save = canvas.save();
        canvas2.rotate(90.0F, f4, f5);
        canvas2.translate(0.0F, f6);
        float f7 = 0.0F - f6;
        float f8 = f3 + f6;
        canvas.drawRect(f7, 0.0F, f8, ((float) (this.mWidth)), this.mPaint);
        canvas2.restoreToCount(save);
        int save2 = canvas.save();
        canvas2.rotate(-90.0F, f4, f5);
        canvas2.translate(0.0F, f6);
        canvas.drawRect(f7, 0.0F, f8, ((float) (this.mWidth)), this.mPaint);
        canvas2.restoreToCount(save2);
        canvas2.restoreToCount(saveLayer);
        return drawChild;
    }

    public void initView() {
        this.mIsRTL = com.android.camera.Util.isLayoutRTL(getContext());
        this.mWidth = getResources().getDimensionPixelSize(R.dimen.mode_select_layout_edge);
        this.mPaint = new android.graphics.Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mPaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
        android.graphics.Paint paint = this.mPaint;
        android.graphics.LinearGradient linearGradient = new android.graphics.LinearGradient(0.0F, 0.0F, 0.0F, ((float) (this.mWidth)), new int[]{ android.support.v4.view.ViewCompat.MEASURED_STATE_MASK, -939524096, 0 }, new float[]{ 0.0F, 0.2F, 1.0F }, android.graphics.Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        setFocusable(false);
    }
}