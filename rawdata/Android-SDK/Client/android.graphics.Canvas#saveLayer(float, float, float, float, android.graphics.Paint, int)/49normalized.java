/* access modifiers changed from: protected */
public void onDraw(android.graphics.Canvas canvas) {
    if (!isInEditMode()) {
        android.graphics.Canvas _CVAR0 = canvas;
        float _CVAR1 = 0.0F;
        float _CVAR2 = 0.0F;
        int _CVAR3 = ((float) (getWidth()));
        int _CVAR4 = ((float) (getHeight()));
        <nulltype> _CVAR5 = null;
        int _CVAR6 = 31;
        int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
        try {
            android.graphics.Bitmap bitmap = (this.mWeakBitmap != null) ? ((android.graphics.Bitmap) (this.mWeakBitmap.get())) : null;
            if ((bitmap == null) || bitmap.isRecycled()) {
                android.graphics.drawable.Drawable drawable = getDrawable();
                if (drawable != null) {
                    try {
                        bitmap = android.graphics.Bitmap.createBitmap(getWidth(), getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
                        android.graphics.Canvas canvas2 = new android.graphics.Canvas(bitmap);
                        drawable.setBounds(0, 0, getWidth(), getHeight());
                        drawable.draw(canvas2);
                        if ((this.mMaskBitmap == null) || this.mMaskBitmap.isRecycled()) {
                            this.mMaskBitmap = createMask1();
                        }
                        this.mPaint.reset();
                        this.mPaint.setFilterBitmap(false);
                        this.mPaint.setXfermode(com.alipay.biometrics.ui.widget.CircleForegroud.SXFERMODE);
                        canvas2.drawBitmap(this.mMaskBitmap, 0.0F, 0.0F, this.mPaint);
                        this.mWeakBitmap = new java.lang.ref.WeakReference<>(bitmap);
                    } catch (java.lang.OutOfMemoryError e) {
                        com.alipay.mobile.security.bio.utils.BioLog.e(((java.lang.Throwable) (e)));
                        java.lang.System.gc();
                        canvas.restoreToCount(saveLayer);
                        return;
                    }
                }
            }
            if (bitmap != null) {
                this.mPaint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0.0F, 0.0F, this.mPaint);
                return;
            }
            canvas.restoreToCount(saveLayer);
        } catch (java.lang.Exception e2) {
        } finally {
            canvas.restoreToCount(saveLayer);
        }
    } else {
        super.onDraw(canvas);
    }
}