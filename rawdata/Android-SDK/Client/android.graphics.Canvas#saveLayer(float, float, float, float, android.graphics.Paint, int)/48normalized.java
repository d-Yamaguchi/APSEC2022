public void drawElement(android.graphics.Canvas canvas) {
    if (!frameControl(this.mCurFrame)) {
        android.graphics.Canvas _CVAR3 = canvas;
        android.graphics.Canvas _CVAR5 = canvas;
        android.graphics.Canvas _CVAR0 = canvas;
        float _CVAR1 = 0.0F;
        float _CVAR2 = 0.0F;
        int _CVAR4 = ((float) (_CVAR3.getWidth()));
        int _CVAR6 = ((float) (_CVAR5.getHeight()));
        <nulltype> _CVAR7 = null;
        int _CVAR8 = 31;
        int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR4, _CVAR6, _CVAR7, _CVAR8);
        this.mPaint.setAlpha(this.alpha);
        int i = this.mAnimScene.getSceneParameter().getPoint().y;
        this.matrix.setScale(this.scaleX, 1.0F, ((float) (this.mRunBitmap.getWidth())), ((float) (this.mRunBitmap.getHeight())));
        this.matrix.postTranslate(((float) (this.transXFrom)), ((float) (this.mRunBitmapTop + i)));
        canvas.drawBitmap(this.mRunBitmap, this.matrix, this.mPaint);
        this.mPaintShade.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
        canvas.drawBitmap(this.mRunShadeBitmap, 0.0F, ((float) (i + this.mRunBitmapTop)), this.mPaintShade);
        this.mPaintShade.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }
}