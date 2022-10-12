/* access modifiers changed from: protected */
public void dispatchDraw(android.graphics.Canvas canvas) {
    android.graphics.Canvas _CVAR0 = canvas;
    float _CVAR1 = 0.0F;
    float _CVAR2 = 0.0F;
    int _CVAR3 = ((float) (getWidth()));
    int _CVAR4 = ((float) (getHeight()));
    <nulltype> _CVAR5 = ((android.graphics.Paint) (null));
    int _CVAR6 = 31;
    int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
    android.graphics.Path path = new android.graphics.Path();
    android.graphics.RectF rectF = new android.graphics.RectF(((float) (getPaddingLeft())), ((float) (getPaddingTop())), ((float) (getWidth() - getPaddingRight())), ((float) (getHeight() - getPaddingBottom())));
    int i = this.mRadius;
    path.addRoundRect(rectF, ((float) (i)), ((float) (i)), android.graphics.Path.Direction.CW);
    canvas.clipPath(path, android.graphics.Region.Op.INTERSECT);
    super.dispatchDraw(canvas);
    int i2 = this.mRadius;
    canvas.drawRoundRect(rectF, ((float) (i2)), ((float) (i2)), this.mPaint);
    canvas.restoreToCount(saveLayer);
}