package com.anglll.beelayout;
/**
 * 讲边框线半径和图片的剪切半径设置为相同，是边框能够完全覆盖住图片半径
 */
public class BeeItemView extends android.widget.FrameLayout {
    private android.graphics.Path path = new android.graphics.Path();

    private android.graphics.Paint strokePaint;

    private android.graphics.Paint paint;

    private android.graphics.Path strokePath = new android.graphics.Path();

    private float strokeR;

    private int borderColor = android.graphics.Color.RED;

    private float borderWidth = 10.0F;

    private boolean hasBorder = false;

    private float roundCorner = 0.1F;

    private float spaceWidth = 10.0F;

    public BeeItemView(android.content.Context context) {
        super(context);
        init(context, null);
    }

    public BeeItemView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BeeItemView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        strokePaint.setColor(borderColor);
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        strokePaint.setStrokeWidth(borderWidth);
        // 边框宽度
        strokeR = ((float) ((borderWidth / 2) / java.lang.Math.sin((60 * java.lang.Math.PI) / 180)));
    }

    public void hasBorder(boolean hasBorder) {
        this.hasBorder = hasBorder;
    }

    public void setSpaceWidth(float spaceWidth) {
        this.spaceWidth = spaceWidth;
    }

    public void setRoundCorner(float roundCorner) {
        this.roundCorner = roundCorner;
    }

    private void init(android.content.Context context, android.util.AttributeSet attrs) {
        strokePaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
        strokePaint.setStrokeWidth(borderWidth);
        strokePaint.setStyle(android.graphics.Paint.Style.STROKE);
        strokePaint.setColor(borderColor);
        // 边框宽度
        strokeR = ((float) ((borderWidth / 2) / java.lang.Math.sin((60 * java.lang.Math.PI) / 180)));
        paint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
    }

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

        float r = (R - strokeR) - (spaceWidth / 2);// 边框中心半径和图片剪切半径

        float r = (R - strokeR) - (spaceWidth / 2);// 边框中心半径和图片剪切半径

        int save = canvas.saveLayer(((float) ((width / 2) - (r * java.lang.Math.sin((60 * java.lang.Math.PI) / 180)))), (height / 2) - r, ((float) ((width / 2) + (r * java.lang.Math.sin((60 * java.lang.Math.PI) / 180)))), (height / 2) + r, null);
        __SmPLUnsupported__(0).dispatchDraw(canvas);
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
        save = canvas.saveLayer(0, 0, width, height, null, android.graphics.Canvas.ALL_SAVE_FLAG);
        canvas.translate(width / 2, height / 2);
        if (hasBorder)
            canvas.drawPath(strokePath, strokePaint);

        canvas.restoreToCount(save);
    }

    @java.lang.Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
    }
}