@java.lang.Override
protected void onDraw(android.graphics.Canvas canvas) {
    super.onDraw(canvas);
    // 设置一个BLUE的背景颜色
    canvas.drawColor(android.graphics.Color.BLUE);
    int _CVAR3 = OFFSET;
    int _CVAR5 = OFFSET;
    android.graphics.Canvas _CVAR0 = canvas;
    int _CVAR1 = OFFSET;
    int _CVAR2 = OFFSET;
    int _CVAR4 = mViewWidth - _CVAR3;
    int _CVAR6 = mViewHeight - _CVAR5;
    android.graphics.Paint _CVAR7 = mPaint;
    int _CVAR8 = android.graphics.Canvas.ALL_SAVE_FLAG;
    // 创建一个画布，
    int saveCount = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR4, _CVAR6, _CVAR7, _CVAR8);
    // 设置画布的颜色为GREEN
    canvas.drawColor(android.graphics.Color.GREEN);
    int _CVAR10 = 2;
    int _CVAR12 = 2;
    int _CVAR14 = 2;
    int _CVAR15 = OFFSET * _CVAR14;
    int _CVAR17 = 2;
    int _CVAR18 = OFFSET * _CVAR17;
    android.graphics.Canvas _CVAR9 = canvas;
    int _CVAR11 = OFFSET * _CVAR10;
    int _CVAR13 = OFFSET * _CVAR12;
    int _CVAR16 = mViewWidth - _CVAR15;
    int _CVAR19 = mViewHeight - _CVAR18;
    android.graphics.Paint _CVAR20 = mPaint;
    int _CVAR21 = android.graphics.Canvas.ALL_SAVE_FLAG;
    // 再创建一个画布
    int saveCount1 = _CVAR9.saveLayer(_CVAR11, _CVAR13, _CVAR16, _CVAR19, _CVAR20, _CVAR21);
    // 设置画布的颜色为BLACK
    canvas.drawColor(android.graphics.Color.BLACK);
    // 在BLACK画布上画一个圆， 注意这个圆不能显示完全，因为画布的大小有限制，左边点的位置还是相对于屏幕坐上角
    canvas.drawCircle(300, 300, 300, mPaint);
    // 创建一个新画笔
    android.graphics.Paint mPaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG | android.graphics.Paint.FILTER_BITMAP_FLAG);
    mPaint.setColor(android.graphics.Color.WHITE);
    mPaint.setStrokeWidth(10);
    mPaint.setStyle(android.graphics.Paint.Style.FILL);
    // 还是在黑色画布上画一个矩形
    canvas.drawRect(new android.graphics.Rect(0, 0, 350, 350), mPaint);
    // 切换到黑色画布之前，就是绿色画布
    canvas.restoreToCount(saveCount1);
    mPaint.setColor(android.graphics.Color.CYAN);
    // 在绿色画布上再画一个矩形
    canvas.drawRect(new android.graphics.Rect(0, 0, 300, 300), mPaint);
    android.widget.Toast.makeText(mContext, " canvas.getSaveCount()" + canvas.getSaveCount(), android.widget.Toast.LENGTH_LONG).show();
}