package com.shuame.rootgenius.p115ui.view;
import com.shuame.rootgenius.common.p087ui.view.C1378a;
import com.shuame.rootgenius.common.p087ui.view.C1378a.C1177a;
import com.shuame.rootgenius.hook.C1450R;
/* renamed from: com.shuame.rootgenius.ui.view.OptimizeCircleProgressBar */
public class OptimizeCircleProgressBar extends android.view.View implements com.shuame.rootgenius.common.p087ui.view.C1378a.C1177a {
    /* renamed from: a */
    private float f4452a;

    /* renamed from: b */
    private android.graphics.Paint f4453b;

    /* renamed from: c */
    private int f4454c;

    /* renamed from: d */
    private int f4455d;

    /* renamed from: e */
    private int f4456e;

    /* renamed from: f */
    private int f4457f;

    /* renamed from: g */
    private float f4458g;

    /* renamed from: h */
    private float f4459h;

    /* renamed from: i */
    private float f4460i;

    /* renamed from: j */
    private float f4461j;

    /* renamed from: k */
    private float f4462k;

    /* renamed from: l */
    private float f4463l;

    /* renamed from: m */
    private android.graphics.Bitmap f4464m;

    /* renamed from: n */
    private android.graphics.Bitmap f4465n;

    /* renamed from: o */
    private int f4466o;

    /* renamed from: p */
    private int f4467p;

    /* renamed from: q */
    private int f4468q;

    /* renamed from: r */
    private int f4469r;

    /* renamed from: s */
    private float f4470s;

    /* renamed from: t */
    private float f4471t;

    /* renamed from: u */
    private android.graphics.Canvas f4472u;

    /* renamed from: v */
    private android.graphics.Paint f4473v;

    /* renamed from: w */
    private android.graphics.Shader f4474w;

    /* renamed from: x */
    private android.graphics.Bitmap f4475x;

    /* renamed from: y */
    private android.graphics.RectF f4476y;

    /* renamed from: z */
    private boolean f4477z;

    public OptimizeCircleProgressBar(android.content.Context context) {
        this(context, null);
    }

    public OptimizeCircleProgressBar(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public OptimizeCircleProgressBar(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f4466o = 0;
        this.f4467p = 0;
        this.f4468q = 0;
        this.f4469r = 360;
        this.f4470s = ((float) (this.f4469r)) / 100.0F;
        this.f4471t = 1.5F;
        this.f4477z = false;
    }

    private android.graphics.RectF getArcRect() {
        float f = this.f4460i * 2.0F;
        return new android.graphics.RectF(this.f4462k, this.f4463l, this.f4462k + f, f + this.f4463l);
    }

    private android.graphics.RectF getWholeRect() {
        return new android.graphics.RectF(0.0F, 0.0F, ((float) (this.f4454c)), ((float) (this.f4455d)));
    }

    /* renamed from: a */
    public final void mo6801a(float f) {
        this.f4452a = f;
        this.f4466o = ((int) (((float) (this.f4467p)) + (((float) (this.f4468q - this.f4467p)) * this.f4452a)));
        invalidate();
    }

    protected void onDraw(android.graphics.Canvas canvas) {
        if (!isInEditMode()) {
            int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (this.f4454c)), ((float) (this.f4455d)), null);
            if (this.f4474w == null) {
                this.f4474w = new android.graphics.LinearGradient(((float) (this.f4456e)), 0.0F, ((float) (this.f4456e)), ((float) (this.f4455d)), android.graphics.Color.parseColor("#00C85D"), android.graphics.Color.parseColor("#00ACD1"), android.graphics.Shader.TileMode.CLAMP);
            }
            if (this.f4464m == null) {
                this.f4464m = android.graphics.Bitmap.createBitmap(this.f4454c, this.f4455d, android.graphics.Bitmap.Config.ARGB_8888);
                this.f4472u = new android.graphics.Canvas(this.f4464m);
                this.f4473v = new android.graphics.Paint();
                this.f4473v.setAntiAlias(true);
                this.f4473v.setStyle(android.graphics.Paint.Style.FILL);
                this.f4473v.setStrokeWidth(this.f4461j);
                this.f4473v.setShader(this.f4474w);
            }
            if (!this.f4477z) {
                this.f4472u.rotate(5.0F, ((float) (this.f4456e)), ((float) (this.f4457f)));
            }
            this.f4472u.drawCircle(((float) (this.f4456e)), ((float) (this.f4457f)), ((float) (this.f4455d / 2)), this.f4473v);
            this.f4453b.setColor(-1);
            canvas.drawBitmap(this.f4464m, 0.0F, 0.0F, this.f4453b);
            if (this.f4465n == null) {
                this.f4465n = android.graphics.Bitmap.createBitmap(this.f4454c, this.f4455d, android.graphics.Bitmap.Config.ARGB_8888);
            }
            android.graphics.Canvas canvas2 = new android.graphics.Canvas(this.f4465n);
            android.graphics.Paint paint = new android.graphics.Paint();
            paint.setAntiAlias(true);
            paint.setStyle(android.graphics.Paint.Style.FILL);
            paint.setStrokeWidth(this.f4461j);
            paint.setColor(-1);
            float f = ((float) (this.f4466o)) * this.f4470s;
            canvas2.drawColor(-1, android.graphics.PorterDuff.Mode.CLEAR);
            canvas2.drawArc(getArcRect(), -90.0F, f, true, paint);
            this.f4453b.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(this.f4465n, 0.0F, 0.0F, this.f4453b);
            this.f4453b.setXfermode(null);
            canvas.restoreToCount(saveLayer);
            if (java.lang.Math.abs(this.f4468q - this.f4467p) != 100) {
                this.f4453b.setStyle(android.graphics.Paint.Style.STROKE);
                this.f4453b.setColor(android.graphics.Color.parseColor("#00ACD1"));
                this.f4453b.setStrokeWidth(this.f4459h);
                canvas.drawCircle(((float) (this.f4456e)), ((float) (this.f4457f)), this.f4458g, this.f4453b);
            }
            if (this.f4477z) {
                if (this.f4475x == null) {
                    this.f4475x = android.graphics.BitmapFactory.decodeResource(getResources(), C1450R.drawable.optimize_item_pausemask);
                    this.f4475x = android.graphics.Bitmap.createScaledBitmap(this.f4475x, this.f4454c, this.f4455d, true);
                }
                this.f4453b.setAntiAlias(true);
                canvas.drawBitmap(this.f4475x, null, this.f4476y, this.f4453b);
            }
            invalidate();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.f4453b == null) {
            this.f4453b = new android.graphics.Paint();
            this.f4453b.setAntiAlias(true);
        }
        this.f4454c = getWidth();
        this.f4455d = getHeight();
        this.f4456e = this.f4454c / 2;
        this.f4457f = this.f4455d / 2;
        this.f4459h = ((float) (java.lang.Math.floor(((double) (((float) (this.f4455d)) * 0.02F)))));
        this.f4458g = ((float) (this.f4457f)) * 0.98F;
        this.f4461j = 0.0F;
        this.f4460i = ((float) (this.f4457f)) - (this.f4461j / 2.0F);
        this.f4462k = (((float) (this.f4454c)) - (this.f4460i * 2.0F)) / 2.0F;
        this.f4463l = (((float) (this.f4455d)) - (this.f4460i * 2.0F)) / 2.0F;
        if (this.f4476y == null) {
            this.f4476y = getWholeRect();
        }
    }

    public void setPaused(boolean z) {
        this.f4477z = z;
    }

    public void setProgress(int i) {
        java.lang.Object obj = ((i < 0) || (i > 100)) ? null : 1;
        if (obj != null) {
            clearAnimation();
            android.view.animation.Animation c1378a = new com.shuame.rootgenius.common.p087ui.view.C1378a(this);
            this.f4467p = this.f4466o;
            this.f4468q = i;
            float abs = java.lang.Math.abs(((float) (i - this.f4466o)));
            if (abs == 100.0F) {
                return;
            }
            if (abs > 90.0F) {
                this.f4466o = i;
                return;
            }
            if (abs > 60.0F) {
                abs = ((90.0F - abs) * 1.67F) + 10.0F;
            }
            c1378a.setDuration(((long) ((abs * this.f4470s) * this.f4471t)));
            c1378a.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
            startAnimation(c1378a);
        }
    }
}