package com.android.camera.ui;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.ModeChangeController;
public class EdgeHorizonScrollView extends android.widget.HorizontalScrollView {
    private float mDownX = -1.0F;

    private android.graphics.Paint mEdgePaint;

    private int mEdgeWidth;

    private boolean mIsRTL;

    private boolean mScrolled = false;

    public EdgeHorizonScrollView(android.content.Context context) {
        super(context);
        initView();
    }

    public EdgeHorizonScrollView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public EdgeHorizonScrollView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    public void initView() {
        this.mIsRTL = com.android.camera.Util.isLayoutRTL(getContext());
        this.mEdgeWidth = getResources().getDimensionPixelSize(R.dimen.mode_select_layout_edge);
        this.mEdgePaint = new android.graphics.Paint();
        this.mEdgePaint.setAntiAlias(true);
        this.mEdgePaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mEdgePaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
        this.mEdgePaint.setShader(new android.graphics.LinearGradient(0.0F, 0.0F, 0.0F, ((float) (this.mEdgeWidth)), new int[]{ android.support.v4.view.ViewCompat.MEASURED_STATE_MASK, android.support.v4.view.ViewCompat.MEASURED_STATE_MASK, 0 }, new float[]{ 0.0F, 0.2F, 1.0F }, android.graphics.Shader.TileMode.CLAMP));
        setFocusable(false);
    }

    protected boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        android.graphics.Canvas canvas2 = canvas;
        int width = getWidth();
        int height = getHeight();
        float f = ((float) (height));
        int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (java.lang.Math.max(width, view.getWidth()))), f, null);
        boolean drawChild = __SmPLUnsupported__(0).drawChild(canvas, view, j);
        canvas2.translate(this.mIsRTL ? ((float) (java.lang.Math.max(0, view.getWidth() - width))) : 0.0F, 0.0F);
        float f2 = ((float) (width));
        float f3 = f2 / 2.0F;
        f /= 2.0F;
        float f4 = ((float) (height - width)) / 2.0F;
        height = canvas.save();
        canvas2.rotate(90.0F, f3, f);
        canvas2.translate(0.0F, f4);
        float f5 = 0.0F - f4;
        float f6 = f2 + f4;
        canvas2.drawRect(f5, 0.0F, f6, ((float) (this.mEdgeWidth)), this.mEdgePaint);
        canvas2.restoreToCount(height);
        int save = canvas.save();
        canvas2.rotate(-90.0F, f3, f);
        canvas2.translate(0.0F, f4);
        canvas2.drawRect(f5, 0.0F, f6, ((float) (this.mEdgeWidth)), this.mEdgePaint);
        canvas2.restoreToCount(save);
        canvas2.restoreToCount(saveLayer);
        return drawChild;
    }

    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0 :
                this.mDownX = motionEvent.getX();
                this.mScrolled = false;
                return true;
            case 1 :
            case 3 :
                this.mDownX = -1.0F;
                this.mScrolled = false;
                break;
            case 2 :
                if (this.mDownX == (-1.0F)) {
                    this.mDownX = motionEvent.getX();
                }
                float x = motionEvent.getX() - this.mDownX;
                com.android.camera.protocol.ModeProtocol.ModeChangeController modeChangeController = ((com.android.camera.protocol.ModeProtocol.ModeChangeController) (com.android.camera.protocol.ModeCoordinatorImpl.getInstance().getAttachProtocol(179)));
                if (!this.mScrolled) {
                    if (x > ((float) (com.android.camera.Util.dpToPixel(17.0F)))) {
                        modeChangeController.selectMode(3, 0);
                        this.mScrolled = true;
                    } else if (x < ((float) (-com.android.camera.Util.dpToPixel(17.0F)))) {
                        modeChangeController.selectMode(5, 0);
                        this.mScrolled = true;
                    }
                }
                return true;
        }
        return false;
    }
}