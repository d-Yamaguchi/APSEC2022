protected void onDraw(android.graphics.Canvas canvas) {
    if (this.e == null) {
        super.onDraw(canvas);
        return;
    }
    if (this.a == null) {
        b();
    }
    int height = getHeight();
    android.graphics.Canvas canvas2 = canvas;
    int _CVAR3 = getPaddingRight();
    int _CVAR5 = getPaddingBottom();
    android.graphics.Canvas _CVAR0 = canvas2;
    int _CVAR1 = ((float) (getPaddingLeft()));
    int _CVAR2 = ((float) (getPaddingTop()));
    int _CVAR4 = ((float) (getWidth() - _CVAR3));
    int _CVAR6 = ((float) (height - _CVAR5));
    <nulltype> _CVAR7 = null;
    int _CVAR8 = 31;
    int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR4, _CVAR6, _CVAR7, _CVAR8);
    super.onDraw(canvas);
    int width = this.a.getWidth();
    int width2 = getWidth() >> 1;
    width2 = (((int) (((float) (width2)) * (1.0F - this.b))) + (width2 >> 1)) - (width >> 1);
    this.d.setXfermode(this.c);
    canvas.translate(((float) (width2)), 0.0F);
    canvas.drawRect(0.0F, 0.0F, ((float) (width)), ((float) (height)), this.d);
    this.d.setXfermode(null);
    canvas.restoreToCount(saveLayer);
}