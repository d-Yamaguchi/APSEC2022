package com.shuame.rootgenius.p115ui.view;
import android.content.p013pm.PermissionInfo;
import android.support.p015v4.view.ViewCompat;
import com.shuame.rootgenius.common.p087ui.view.C1378a;
import com.shuame.rootgenius.common.p087ui.view.C1378a.C1177a;
/* renamed from: com.shuame.rootgenius.ui.view.MobileOptimizeProgressBar */
public class MobileOptimizeProgressBar extends android.view.View implements com.shuame.rootgenius.common.p087ui.view.C1378a.C1177a {
    /* renamed from: A */
    private int f4408A = 0;

    /* renamed from: B */
    private int f4409B = 0;

    /* renamed from: C */
    private int f4410C;

    /* renamed from: D */
    private int f4411D;

    /* renamed from: E */
    private android.graphics.Rect f4412E;

    /* renamed from: F */
    private int f4413F;

    /* renamed from: G */
    private int f4414G;

    /* renamed from: H */
    private int f4415H;

    /* renamed from: I */
    private int f4416I;

    /* renamed from: J */
    private java.lang.String f4417J = "分";

    /* renamed from: K */
    private float f4418K;

    /* renamed from: L */
    private float f4419L;

    /* renamed from: a */
    private android.graphics.Paint f4420a;

    /* renamed from: b */
    private android.graphics.Shader f4421b;

    /* renamed from: c */
    private int f4422c;

    /* renamed from: d */
    private int f4423d;

    /* renamed from: e */
    private float f4424e;

    /* renamed from: f */
    private android.graphics.Bitmap f4425f;

    /* renamed from: g */
    private android.graphics.Bitmap f4426g;

    /* renamed from: h */
    private float f4427h;

    /* renamed from: i */
    private float f4428i;

    /* renamed from: j */
    private float f4429j;

    /* renamed from: k */
    private int f4430k = 0;

    /* renamed from: l */
    private int f4431l = 0;

    /* renamed from: m */
    private int f4432m = 0;

    /* renamed from: n */
    private int f4433n = android.content.p013pm.PermissionInfo.PROTECTION_MASK_FLAGS;

    /* renamed from: o */
    private float f4434o = ((float) (this.f4433n)) / 100.0F;

    /* renamed from: p */
    private float f4435p = 8.5F;

    /* renamed from: q */
    private float f4436q;

    /* renamed from: r */
    private int f4437r;

    /* renamed from: s */
    private int f4438s;

    /* renamed from: t */
    private float f4439t;

    /* renamed from: u */
    private float f4440u;

    /* renamed from: v */
    private float f4441v;

    /* renamed from: w */
    private float f4442w;

    /* renamed from: x */
    private float f4443x;

    /* renamed from: y */
    private float f4444y;

    /* renamed from: z */
    private android.graphics.LinearGradient f4445z;

    public MobileOptimizeProgressBar(android.content.Context context) {
        super(context);
    }

    public MobileOptimizeProgressBar(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MobileOptimizeProgressBar(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private android.graphics.RectF getArcRect() {
        float f = this.f4429j * 2.0F;
        return new android.graphics.RectF(this.f4427h, this.f4428i, this.f4427h + f, f + this.f4428i);
    }

    /* renamed from: a */
    public final void mo6801a(float f) {
        this.f4424e = f;
        this.f4430k = java.lang.Math.round(((float) (this.f4431l)) + (((float) (this.f4432m - this.f4431l)) * this.f4424e));
        this.f4408A = java.lang.Math.round(((float) (this.f4410C)) + (((float) (this.f4411D - this.f4410C)) * this.f4424e));
        invalidate();
    }

    /* renamed from: a */
    public final void mo7531a(int i) {
        if ((i == 0 ? null : 1) != null) {
            clearAnimation();
            this.f4410C = this.f4408A;
            this.f4411D = i;
            int round = java.lang.Math.round((((float) (i)) * 100.0F) / ((float) (this.f4409B)));
            this.f4431l = this.f4430k;
            this.f4432m = round;
            long abs = ((long) ((java.lang.Math.abs(((float) (round - this.f4430k))) * this.f4434o) * this.f4435p));
            android.view.animation.Animation c1378a = new com.shuame.rootgenius.common.p087ui.view.C1378a(this);
            c1378a.setDuration(abs);
            c1378a.setInterpolator(new android.view.animation.LinearInterpolator());
            startAnimation(c1378a);
        }
    }

    protected void onDraw(android.graphics.Canvas canvas) {
        if (!isInEditMode()) {
            android.graphics.Canvas canvas2;
            android.graphics.Paint paint;
            int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (this.f4422c)), ((float) (this.f4423d)), null);
            this.f4420a.setColor(-1);
            if (this.f4425f == null) {
                this.f4425f = android.graphics.Bitmap.createBitmap(this.f4422c, this.f4423d, android.graphics.Bitmap.Config.ARGB_8888);
                canvas2 = new android.graphics.Canvas(this.f4425f);
                paint = new android.graphics.Paint();
                paint.setAntiAlias(true);
                paint.setColor(-1);
                paint.setStyle(android.graphics.Paint.Style.STROKE);
                paint.setStrokeCap(android.graphics.Paint.Cap.ROUND);
                paint.setStrokeWidth(this.f4436q);
                canvas2.rotate(150.0F, ((float) (this.f4437r)), ((float) (this.f4438s)));
                canvas2.drawArc(getArcRect(), 6.0F, ((float) (this.f4433n - 12)), false, paint);
            }
            canvas.drawBitmap(this.f4425f, 0.0F, 0.0F, this.f4420a);
            if (this.f4421b == null) {
                this.f4421b = new android.graphics.SweepGradient(((float) (this.f4437r)), ((float) (this.f4438s)), __SmPLUnsupported__(0), __SmPLUnsupported__(1));
            }
            float f = ((float) (this.f4430k)) * this.f4434o;
            this.f4420a.setColor(-1);
            this.f4426g = android.graphics.Bitmap.createBitmap(this.f4422c, this.f4423d, android.graphics.Bitmap.Config.ARGB_8888);
            canvas2 = new android.graphics.Canvas(this.f4426g);
            paint = new android.graphics.Paint();
            paint.setAntiAlias(true);
            paint.setStyle(android.graphics.Paint.Style.STROKE);
            paint.setStrokeWidth(this.f4436q + 10.0F);
            paint.setShader(this.f4421b);
            canvas2.rotate(150.0F, ((float) (this.f4437r)), ((float) (this.f4438s)));
            canvas2.drawArc(getArcRect(), 0.0F, f, false, paint);
            this.f4420a.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(this.f4426g, 0.0F, 0.0F, this.f4420a);
            this.f4420a.setXfermode(null);
            canvas.restoreToCount(saveLayer);
            this.f4420a.setStyle(android.graphics.Paint.Style.FILL);
            this.f4420a.setStrokeWidth(1.0F);
            canvas.drawCircle(((float) (this.f4437r)), ((float) (this.f4438s)), this.f4439t, this.f4420a);
            this.f4420a.setStyle(android.graphics.Paint.Style.STROKE);
            this.f4420a.setStrokeWidth(this.f4442w);
            this.f4420a.setColor(android.graphics.Color.argb(78, 31, 191, 105));
            canvas.drawCircle(((float) (this.f4437r)), ((float) (this.f4438s)), this.f4440u - (this.f4442w / 2.0F), this.f4420a);
            this.f4420a.setStrokeWidth(this.f4443x);
            this.f4420a.setColor(android.graphics.Color.argb(255, 247, 107, 28));
            canvas.drawCircle(((float) (this.f4437r)), ((float) (this.f4438s)), this.f4441v, this.f4420a);
            canvas.save();
            float f2 = ((float) (this.f4430k)) * this.f4434o;
            if (f2 <= 2.0F) {
                f2 = 2.0F;
            } else if (f2 >= (((float) (this.f4433n)) - 2.0F)) {
                f2 = ((float) (this.f4433n)) - 2.0F;
            }
            canvas.rotate((f2 - 150.0F) + 30.0F, ((float) (this.f4437r)), ((float) (this.f4438s)));
            this.f4420a.setStyle(android.graphics.Paint.Style.STROKE);
            this.f4420a.setStrokeWidth(this.f4444y);
            if (this.f4445z == null) {
                float f3 = 0.0F;
                this.f4445z = new android.graphics.LinearGradient(((float) (this.f4437r)), ((float) (this.f4438s)), ((float) (this.f4437r)), f3, __SmPLUnsupported__(2), __SmPLUnsupported__(3), android.graphics.Shader.TileMode.CLAMP);
            }
            this.f4420a.setColor(-1);
            this.f4420a.setShader(this.f4445z);
            canvas.drawLine(((float) (this.f4437r)), ((float) (this.f4438s)) - this.f4441v, ((float) (this.f4437r)), 0.0F, this.f4420a);
            this.f4420a.setShader(null);
            canvas.restore();
            this.f4420a.reset();
            this.f4420a.setAntiAlias(true);
            this.f4420a.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.f4420a.setTextSize(this.f4418K);
            this.f4420a.getTextBounds(java.lang.String.valueOf(this.f4408A), 0, java.lang.String.valueOf(this.f4408A).length(), this.f4412E);
            if (this.f4412E.right > this.f4413F) {
                this.f4413F = this.f4412E.right;
            }
            f2 = ((float) (this.f4437r - (this.f4413F / 2)));
            float f4 = ((float) (this.f4423d)) * 0.86F;
            canvas.drawText(this.f4408A, f2, f4, this.f4420a);
            this.f4420a.setTextSize(this.f4419L);
            canvas.drawText(this.f4417J, (f2 + ((float) (this.f4413F))) + (((float) (this.f4423d)) * 0.02F), (f4 + ((float) (this.f4414G))) - ((float) (this.f4416I)), this.f4420a);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        android.graphics.Paint.FontMetricsInt fontMetricsInt;
        super.onLayout(z, i, i2, i3, i4);
        if (this.f4420a == null) {
            this.f4420a = new android.graphics.Paint();
            this.f4420a.setAntiAlias(true);
            this.f4412E = new android.graphics.Rect();
        }
        this.f4422c = getWidth();
        this.f4423d = getHeight();
        this.f4437r = this.f4422c / 2;
        this.f4438s = this.f4423d / 2;
        this.f4436q = ((float) (java.lang.Math.floor(((double) (((float) (this.f4423d)) * 0.08F)))));
        this.f4429j = ((float) (this.f4438s)) * 0.85F;
        this.f4427h = (((float) (this.f4422c)) - (this.f4429j * 2.0F)) / 2.0F;
        this.f4428i = (((float) (this.f4423d)) - (this.f4429j * 2.0F)) / 2.0F;
        this.f4439t = this.f4429j * 0.35F;
        this.f4440u = this.f4439t * 0.918F;
        this.f4441v = this.f4439t * 0.14F;
        this.f4442w = this.f4440u * 0.15F;
        this.f4443x = this.f4442w * 0.916F;
        this.f4444y = this.f4443x / 2.0F;
        if (this.f4414G == 0) {
            this.f4418K = ((float) (this.f4423d)) * 0.188F;
            this.f4420a.setTextSize(this.f4418K);
            fontMetricsInt = this.f4420a.getFontMetricsInt();
            this.f4414G = fontMetricsInt.descent + fontMetricsInt.ascent;
        }
        if (this.f4415H == 0) {
            this.f4419L = this.f4418K * 0.4F;
            this.f4420a.setTextSize(this.f4419L);
            android.graphics.Rect rect = new android.graphics.Rect();
            this.f4420a.getTextBounds(this.f4417J, 0, 1, rect);
            this.f4415H = rect.right;
            fontMetricsInt = this.f4420a.getFontMetricsInt();
            this.f4416I = fontMetricsInt.descent + fontMetricsInt.ascent;
        }
    }

    public void setTotalScore(int i) {
        this.f4409B = i;
    }
}