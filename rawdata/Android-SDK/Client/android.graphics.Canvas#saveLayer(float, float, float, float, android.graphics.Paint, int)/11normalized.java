/* access modifiers changed from: protected */
public boolean drawChild(android.graphics.Canvas canvas, android.view.View view, long j) {
    android.graphics.Canvas canvas2 = canvas;
    int width = getWidth();
    android.view.View _CVAR4 = view;
    int _CVAR3 = width;
    int _CVAR5 = _CVAR4.getWidth();
    int height = getHeight();
    float f2 = ((float) (height));
    android.graphics.Canvas _CVAR0 = canvas;
    float _CVAR1 = 0.0F;
    float _CVAR2 = 0.0F;
    int _CVAR6 = ((float) (java.lang.Math.max(_CVAR3, _CVAR5)));
    float _CVAR7 = f2;
    <nulltype> _CVAR8 = ((android.graphics.Paint) (null));
    int _CVAR9 = 31;
    int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR6, _CVAR7, _CVAR8, _CVAR9);
    boolean drawChild = super.drawChild(canvas, view, j);
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