@java.lang.Override
protected void onDraw(android.graphics.Canvas canvas) {
    // super.onDraw(canvas);
    android.graphics.drawable.BitmapDrawable drawable = ((android.graphics.drawable.BitmapDrawable) (getDrawable()));
    if (drawable != null) {
        android.graphics.Bitmap bitmap = drawable.getBitmap();
        android.graphics.Canvas _CVAR0 = canvas;
        int _CVAR1 = 0;
        int _CVAR2 = 0;
        int _CVAR3 = getWidth();
        int _CVAR4 = getHeight();
        <nulltype> _CVAR5 = null;
        int _CVAR6 = android.graphics.Canvas.ALL_SAVE_FLAG;
        int sc = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
        // 绘制底层圆形
        int r = java.lang.Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, r, paint);
        // 三角形
        // Path path = new Path();
        // path.moveTo(getWidth()/2,0);
        // path.lineTo(0,getHeight()/2);
        // path.lineTo(getWidth(),getHeight()/2);
        // path.close();
        // canvas.drawPath(path,paint);
        // 椭圆
        // RectF rectF = new RectF(getWidth()/4,getHeight()/4,getWidth()/2,getHeight()/2);
        // canvas.drawOval(rectF,paint);
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        float f_x = (getWidth() / 2) - (bitmap.getWidth() / 2);
        float f_y = (getHeight() / 2) - (bitmap.getHeight() / 2);
        // 绘制原图
        canvas.drawBitmap(bitmap, f_x, f_y, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}