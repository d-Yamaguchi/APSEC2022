/* renamed from: c */
private void m27893c(android.graphics.Canvas canvas) {
    if (android.os.Build.VERSION.SDK_INT >= 21) {
        this.f28331c = canvas.saveLayer(0.0F, 0.0F, ((float) (canvas.getWidth())), ((float) (canvas.getHeight())), null);
        return;
    }
    android.graphics.Canvas _CVAR3 = canvas;
    android.graphics.Canvas _CVAR5 = canvas;
    android.graphics.Canvas _CVAR0 = canvas;
    float _CVAR1 = 0.0F;
    float _CVAR2 = 0.0F;
    int _CVAR4 = ((float) (_CVAR3.getWidth()));
    int _CVAR6 = ((float) (_CVAR5.getHeight()));
    <nulltype> _CVAR7 = null;
    int _CVAR8 = 31;
    int _CVAR9 = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR4, _CVAR6, _CVAR7, _CVAR8);
    this.f28331c = _CVAR9;
}