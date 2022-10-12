protected void onDraw(android.graphics.Canvas canvas) {
    android.graphics.drawable.Drawable drawable = getDrawable();
    if ((drawable != null) && (!(drawable instanceof android.graphics.drawable.NinePatchDrawable))) {
        int width = getWidth();
        int height = getHeight();
        android.graphics.Canvas _CVAR0 = canvas;
        float _CVAR1 = 0.0F;
        float _CVAR2 = 0.0F;
        int _CVAR3 = ((float) (width));
        int _CVAR4 = ((float) (height));
        android.graphics.Paint _CVAR5 = this.nonePaint;
        int _CVAR6 = 31;
        int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
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