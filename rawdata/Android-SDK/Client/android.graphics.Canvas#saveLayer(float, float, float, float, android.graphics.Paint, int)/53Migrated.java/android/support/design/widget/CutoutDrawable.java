package android.support.design.widget;
import android.support.annotation.NonNull;
class CutoutDrawable extends android.graphics.drawable.GradientDrawable {
    private final android.graphics.RectF cutoutBounds;

    private final android.graphics.Paint cutoutPaint = new android.graphics.Paint(1);

    private int savedLayer;

    CutoutDrawable() {
        setPaintStyles();
        this.cutoutBounds = new android.graphics.RectF();
    }

    private void setPaintStyles() {
        this.cutoutPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        this.cutoutPaint.setColor(-1);
        this.cutoutPaint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
    }

    boolean hasCutout() {
        return this.cutoutBounds.isEmpty() ^ 1;
    }

    void setCutout(float f, float f2, float f3, float f4) {
        if ((((f != this.cutoutBounds.left) || (f2 != this.cutoutBounds.top)) || (f3 != this.cutoutBounds.right)) || (f4 != this.cutoutBounds.bottom)) {
            this.cutoutBounds.set(f, f2, f3, f4);
            invalidateSelf();
        }
    }

    void setCutout(android.graphics.RectF rectF) {
        setCutout(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    void removeCutout() {
        setCutout(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void draw(@android.support.annotation.NonNull
    android.graphics.Canvas canvas) {
        preDraw(canvas);
        super.draw(canvas);
        canvas.drawRect(this.cutoutBounds, this.cutoutPaint);
        postDraw(canvas);
    }

    private void preDraw(@android.support.annotation.NonNull
    android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable.Callback callback = getCallback();
        if (useHardwareLayer(callback)) {
            ((android.view.View) (callback)).setLayerType(2, null);
        } else {
            saveCanvasLayer(canvas);
        }
    }

    private void saveCanvasLayer(@android.support.annotation.NonNull
    android.graphics.Canvas canvas) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            this.savedLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (canvas.getWidth())), ((float) (canvas.getHeight())), null);
            return;
        }
        this.savedLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (canvas.getWidth())), ((float) (canvas.getHeight())), null);
    }

    private void postDraw(@android.support.annotation.NonNull
    android.graphics.Canvas canvas) {
        if (!useHardwareLayer(getCallback())) {
            canvas.restoreToCount(this.savedLayer);
        }
    }

    private boolean useHardwareLayer(android.graphics.drawable.Drawable.Callback callback) {
        return callback instanceof android.view.View;
    }
}