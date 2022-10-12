package com.hongliang.demo.view;
import android.support.annotation.Nullable;
import com.hongliang.demo.util.UIHelper;
public class CanvasView extends android.view.View {
    private android.graphics.Paint mPaint;

    private int mViewWidth;

    private int mViewHeight;// 控件宽高


    private float curValue = 0.0F;

    private int OFFSET = 100;// 偏移量


    private android.content.Context mContext;

    public CanvasView(android.content.Context context) {
        this(context, null);
    }

    public CanvasView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasView(android.content.Context context, @android.support.annotation.Nullable
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
        initAnimator();
    }

    @java.lang.Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        /* 获取控件宽高 */
        mViewWidth = w;
        mViewHeight = h;
    }

    private void init() {
        mPaint = new android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG | android.graphics.Paint.FILTER_BITMAP_FLAG);
        mPaint.setColor(android.graphics.Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(android.graphics.Paint.Style.FILL);
        float size = com.hongliang.demo.util.UIHelper.dp2px(mContext, 15);
        mPaint.setTextSize(size);
    }

    @java.lang.Override
    protected void onDraw(android.graphics.Canvas canvas) {
        __SmPLUnsupported__(0).onDraw(canvas);
        // 设置一个BLUE的背景颜色
        canvas.drawColor(android.graphics.Color.BLUE);
        int saveCount = canvas.saveLayer(OFFSET, OFFSET, mViewWidth - OFFSET, mViewHeight - OFFSET, mPaint);
        // 设置画布的颜色为GREEN
        canvas.drawColor(android.graphics.Color.GREEN);
        // 再创建一个画布
        int saveCount1 = canvas.saveLayer(OFFSET * 2, OFFSET * 2, mViewWidth - (OFFSET * 2), mViewHeight - (OFFSET * 2), mPaint, android.graphics.Canvas.ALL_SAVE_FLAG);
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

    private void initAnimator() {
        android.animation.ValueAnimator animator = android.animation.ValueAnimator.ofFloat(0, 90);
        animator.setDuration(1000);
        animator.setStartDelay(2000);
        animator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
            @java.lang.Override
            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
                curValue = ((float) (animation.getAnimatedValue()));
                invalidate();
            }
        });
        animator.start();
    }
}