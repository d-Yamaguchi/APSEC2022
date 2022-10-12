// public class MyCircleImageView extends ImageView {
// 
// private Paint mPaint;
// 
// public MyCircleImageView(Context context, AttributeSet attrs) {
// super(context, attrs);
// initPaint();
// 
// }
// 
// private void initPaint() {
// mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
// }
// 
// 
// @Override
// protected void onDraw(Canvas canvas) {
// BitmapDrawable drawable = (BitmapDrawable) getDrawable();
// if (drawable != null) {
// Bitmap bitmap = drawable.getBitmap();
// drawTargetBitmap(canvas, bitmap);
// }
// }
// 
// 
// private void drawTargetBitmap(Canvas canvas, Bitmap bitmap) {
// final int sc = canvas.saveLayer(0, 0, getWidth(),getHeight(), null, Canvas.ALL_SAVE_FLAG);
// //先绘制dst层
// final float x = getWidth() / 2;
// final float y = getHeight() / 2;
// final float radius = Math.min(getWidth(), getHeight()) / 2;
// canvas.drawCircle(x, y, radius, mPaint);
// //设置混合模式
// mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
// //绘制src层
// final float f_x = getWidth()  / 2 -bitmap.getWidth() / 2;
// final float f_y = getHeight() / 2 -bitmap.getHeight() / 2;
// canvas.drawBitmap(bitmap, f_x,f_y, mPaint);
// // 还原混合模式
// mPaint.setXfermode(null);
// // 还原画布
// canvas.restoreToCount(sc);
// }
// }
package com.example.lenovo.mytestcode.widget;
import android.support.annotation.Nullable;
/**
 * Created by ccg on 2017/8/11.
 */
public class MyCircleImageView extends android.widget.ImageView {
    private android.graphics.Paint paint;

    public MyCircleImageView(android.content.Context context) {
        this(context, null);
    }

    public MyCircleImageView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleImageView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
    }

    @java.lang.Override
    protected void onDraw(android.graphics.Canvas canvas) {
        // super.onDraw(canvas);
        android.graphics.drawable.BitmapDrawable drawable = ((android.graphics.drawable.BitmapDrawable) (getDrawable()));
        if (drawable != null) {
            android.graphics.Bitmap bitmap = drawable.getBitmap();
            int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
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
}