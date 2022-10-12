/* Access modifiers changed, original: protected */
public void onDraw(android.graphics.Canvas canvas) {
    com.tencent.matrix.trace.core.AppMethodBeat.i(136205);
    android.graphics.Paint paint = new android.graphics.Paint();
    paint.setAntiAlias(true);
    paint.setColor(-1);
    if (this.rect == null) {
        this.rect = new android.graphics.Rect(0, 0, getWidth(), getHeight());
    }
    if (this.kPM == null) {
        this.kPM = new android.graphics.RectF(this.rect);
    }
    if (!((this.bitmap == null) || (this.kPN == null))) {
        int saveLayer;
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (getWidth())), ((float) (getHeight())), null);
        } else {
            android.graphics.Canvas _CVAR0 = canvas;
            float _CVAR1 = 0.0F;
            float _CVAR2 = 0.0F;
             _CVAR3 = ((float) (getWidth()));
             _CVAR4 = ((float) (getHeight()));
            <nulltype> _CVAR5 = null;
            int _CVAR6 = 31;
            int _CVAR7 = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
            saveLayer = _CVAR7;
        }
        canvas.drawBitmap(this.kPN, this.rect, this.rect, paint);
        paint.setXfermode(com.tencent.mm.plugin.downloader_app.ui.AppIconView.kPO);
        canvas.drawBitmap(this.bitmap, null, this.rect, paint);
        canvas.restoreToCount(saveLayer);
        paint.setXfermode(null);
    }
    paint.setStyle(android.graphics.Paint.Style.STROKE);
    paint.setStrokeWidth(1.0F);
    paint.setColor(getResources().getColor(R.color.ge));
    canvas.drawRoundRect(this.kPM, 32.0F, 32.0F, paint);
    com.tencent.matrix.trace.core.AppMethodBeat.o(136205);
}