package com.android.camera.ui;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
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

    /* access modifiers changed from: protected */
    public boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
        android.graphics.Canvas canvas2 = canvas;
        int width = getWidth();
        int height = getHeight();
        float f2 = ((float) (height));
        int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (java.lang.Math.max(width, view.getWidth()))), f2, ((android.graphics.Paint) (null)));
        boolean drawChild = __SmPLUnsupported__(0).drawChild(canvas, view, j);
        canvas2.translate(this.mIsRTL ? ((float) (java.lang.Math.max(0, view.getWidth() - width))) : 0.0F, 0.0F);
        float f3 = ((float) (width));
        float f4 = f3 / 2.0F;
        float f5 = f2 / 2.0F;
        float f6 = ((float) (height - width)) / 2.0F;
        int save = canvas.save();
        canvas2.rotate(90.0F, f4, f5);
        canvas2.translate(0.0F, f6);
        float f7 = 0.0F - f6;
        float f8 = f3 + f6;
        canvas.drawRect(f7, 0.0F, f8, ((float) (this.mEdgeWidth)), this.mEdgePaint);
        canvas2.restoreToCount(save);
        int save2 = canvas.save();
        canvas2.rotate(-90.0F, f4, f5);
        canvas2.translate(0.0F, f6);
        canvas.drawRect(f7, 0.0F, f8, ((float) (this.mEdgeWidth)), this.mEdgePaint);
        canvas2.restoreToCount(save2);
        canvas2.restoreToCount(saveLayer);
        return drawChild;
    }

    public void initView() {
        this.mIsRTL = com.android.camera.Util.isLayoutRTL(getContext());
        this.mEdgeWidth = getResources().getDimensionPixelSize(R.dimen.mode_select_layout_edge);
        this.mEdgePaint = new android.graphics.Paint();
        this.mEdgePaint.setAntiAlias(true);
        this.mEdgePaint.setStyle(android.graphics.Paint.Style.FILL);
        this.mEdgePaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
        android.graphics.Paint paint = this.mEdgePaint;
        android.graphics.LinearGradient linearGradient = new android.graphics.LinearGradient(0.0F, 0.0F, 0.0F, ((float) (this.mEdgeWidth)), new int[]{ android.support.v4.view.ViewCompat.MEASURED_STATE_MASK, -1728053248, 0 }, new float[]{ 0.0F, 0.3F, 2.0F }, android.graphics.Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        setFocusable(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0010, code lost:
    if (r0 != 3) goto L_0x006a;
     */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (this.mDownX == (-1.0F)) {
                        this.mDownX = motionEvent.getX();
                    }
                    float x = motionEvent.getX() - this.mDownX;
                    com.android.camera.protocol.ModeProtocol.ModeChangeController modeChangeController = ((com.android.camera.protocol.ModeProtocol.ModeChangeController) (com.android.camera.protocol.ModeCoordinatorImpl.getInstance().getAttachProtocol(179)));
                    if ((!this.mScrolled) && (modeChangeController != null)) {
                        if ((x > ((float) (com.android.camera.Util.dpToPixel(17.0F)))) && modeChangeController.canSwipeChangeMode()) {
                            modeChangeController.changeModeByGravity(3, 0);
                            this.mScrolled = true;
                        } else if ((x < ((float) (-com.android.camera.Util.dpToPixel(17.0F)))) && modeChangeController.canSwipeChangeMode()) {
                            modeChangeController.changeModeByGravity(5, 0);
                            this.mScrolled = true;
                        }
                    }
                    return true;
                }
            }
            this.mDownX = -1.0F;
            this.mScrolled = false;
            return false;
        }
        this.mDownX = motionEvent.getX();
        this.mScrolled = false;
        return true;
    }
}