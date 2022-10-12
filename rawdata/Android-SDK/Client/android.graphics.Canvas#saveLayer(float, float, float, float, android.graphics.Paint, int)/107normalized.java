protected void onDraw(android.graphics.Canvas canvas) {
    android.graphics.drawable.Drawable drawable = this.a;
    if (drawable != null) {
        android.graphics.Paint a2 = a(getDrawable());
        android.graphics.drawable.Drawable _CVAR5 = drawable;
        android.graphics.Paint a = a(_CVAR5);
        if (!((a == null) || (a2 == null))) {
            android.graphics.Rect bounds = drawable.getBounds();
            android.graphics.Canvas _CVAR0 = canvas;
            int _CVAR1 = ((float) (bounds.left));
            int _CVAR2 = ((float) (bounds.top));
            int _CVAR3 = ((float) (bounds.right));
            int _CVAR4 = ((float) (bounds.bottom));
            android.graphics.Paint _CVAR6 = a;
            int _CVAR7 = 31;
            int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR6, _CVAR7);
            android.graphics.Xfermode xfermode = a2.getXfermode();
            drawable.draw(canvas);
            a2.setXfermode(this.b);
            super.onDraw(canvas);
            canvas.restoreToCount(saveLayer);
            a2.setXfermode(xfermode);
            return;
        }
    }
    super.onDraw(canvas);
}