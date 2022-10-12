@java.lang.Override
protected void dispatchDraw(android.graphics.Canvas canvas) {
    /**
     *
     *
     * @path 要显示的形状
    未设置path 设置了园角度
     */
    path.reset();
    strokePath.reset();
    int R = java.lang.Math.min(width, width) / 2;// 最大半径

    int _CVAR1 = 180;
    double _CVAR2 = (60 * java.lang.Math.PI) / _CVAR1;
    double _CVAR3 = java.lang.Math.sin(_CVAR2);
    double _CVAR4 = r * _CVAR3;
    int _CVAR6 = 2;
    float _CVAR7 = spaceWidth / _CVAR6;
    float r = (R - strokeR) - _CVAR7;// 边框中心半径和图片剪切半径

    float _CVAR8 = r;
    int _CVAR10 = 180;
    double _CVAR11 = (60 * java.lang.Math.PI) / _CVAR10;
    double _CVAR12 = java.lang.Math.sin(_CVAR11);
    double _CVAR13 = r * _CVAR12;
    float _CVAR15 = _CVAR7;
    float r = (R - strokeR) - _CVAR15;// 边框中心半径和图片剪切半径

    float _CVAR16 = r;
    android.graphics.Canvas _CVAR0 = canvas;
    double _CVAR5 = ((float) ((width / 2) - _CVAR4));
    float _CVAR9 = (height / 2) - _CVAR8;
    double _CVAR14 = ((float) ((width / 2) + _CVAR13));
    float _CVAR17 = (height / 2) + _CVAR16;
    <nulltype> _CVAR18 = null;
    int _CVAR19 = android.graphics.Canvas.ALL_SAVE_FLAG;
    // 裁剪背景
    int save = _CVAR0.saveLayer(_CVAR5, _CVAR9, _CVAR14, _CVAR17, _CVAR18, _CVAR19);
    super.dispatchDraw(canvas);
    canvas.translate(width / 2, height / 2);
    for (int i = 0; i < 6; i++) {
        if (i == 0) {
            path.moveTo(((float) (r * java.lang.Math.cos(((30 + (i * 60)) * java.lang.Math.PI) / 180))), ((float) (r * java.lang.Math.sin(((30 + (i * 60)) * java.lang.Math.PI) / 180))));
            strokePath.moveTo(((float) (r * java.lang.Math.cos(((30 + (i * 60)) * java.lang.Math.PI) / 180))), ((float) (r * java.lang.Math.sin(((30 + (i * 60)) * java.lang.Math.PI) / 180))));
        } else {
            path.lineTo(((float) (r * java.lang.Math.cos(((30 + (i * 60)) * java.lang.Math.PI) / 180))), ((float) (r * java.lang.Math.sin(((30 + (i * 60)) * java.lang.Math.PI) / 180))));
            strokePath.lineTo(((float) (r * java.lang.Math.cos(((30 + (i * 60)) * java.lang.Math.PI) / 180))), ((float) (r * java.lang.Math.sin(((30 + (i * 60)) * java.lang.Math.PI) / 180))));
        }
    }
    path.close();
    strokePath.close();
    // 圆角处理
    paint.setPathEffect(new android.graphics.CornerPathEffect(r * roundCorner));
    strokePaint.setPathEffect(new android.graphics.CornerPathEffect(r * roundCorner));
    paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
    canvas.drawPath(path, paint);
    canvas.restoreToCount(save);
    paint.setXfermode(null);
    int width = getMeasuredWidth();
    int height = getMeasuredHeight();
    android.graphics.Canvas _CVAR20 = canvas;
    int _CVAR21 = 0;
    int _CVAR22 = 0;
    int _CVAR23 = width;
    int _CVAR24 = height;
    <nulltype> _CVAR25 = null;
    int _CVAR26 = android.graphics.Canvas.ALL_SAVE_FLAG;
    int _CVAR27 = _CVAR20.saveLayer(_CVAR21, _CVAR22, _CVAR23, _CVAR24, _CVAR25, _CVAR26);
    save = _CVAR27;
    canvas.translate(width / 2, height / 2);
    if (hasBorder) {
        canvas.drawPath(strokePath, strokePaint);
    }
    canvas.restoreToCount(save);
}