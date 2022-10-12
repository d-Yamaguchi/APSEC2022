package com.google.android.material.textfield;
/* renamed from: com.google.android.material.textfield.a */
class C10825a extends android.graphics.drawable.GradientDrawable {
    /* renamed from: a */
    private final android.graphics.Paint f28329a = new android.graphics.Paint(1);

    /* renamed from: b */
    private final android.graphics.RectF f28330b;

    /* renamed from: c */
    private int f28331c;

    C10825a() {
        m27892c();
        this.f28330b = new android.graphics.RectF();
    }

    /* renamed from: c */
    private void m27892c() {
        this.f28329a.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
        this.f28329a.setColor(-1);
        this.f28329a.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public boolean mo31409a() {
        return !this.f28330b.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public void mo31410b() {
        mo31407a(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void draw(android.graphics.Canvas canvas) {
        m27891b(canvas);
        super.draw(canvas);
        canvas.drawRect(this.f28330b, this.f28329a);
        m27889a(canvas);
    }

    /* renamed from: b */
    private void m27891b(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable.Callback callback = getCallback();
        if (m27890a(callback)) {
            ((android.view.View) (callback)).setLayerType(2, null);
        } else {
            m27893c(canvas);
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo31407a(float f, float f2, float f3, float f4) {
        android.graphics.RectF rectF = this.f28330b;
        if ((((f != rectF.left) || (f2 != rectF.top)) || (f3 != rectF.right)) || (f4 != rectF.bottom)) {
            this.f28330b.set(f, f2, f3, f4);
            invalidateSelf();
        }
    }

    /* renamed from: c */
    private void m27893c(android.graphics.Canvas canvas) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            this.f28331c = canvas.saveLayer(0.0F, 0.0F, ((float) (canvas.getWidth())), ((float) (canvas.getHeight())), null);
            return;
        }
        this.f28331c = canvas.saveLayer(0.0F, 0.0F, ((float) (canvas.getWidth())), ((float) (canvas.getHeight())), null);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo31408a(android.graphics.RectF rectF) {
        mo31407a(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* renamed from: a */
    private void m27889a(android.graphics.Canvas canvas) {
        if (!m27890a(getCallback())) {
            canvas.restoreToCount(this.f28331c);
        }
    }

    /* renamed from: a */
    private boolean m27890a(android.graphics.drawable.Drawable.Callback callback) {
        return callback instanceof android.view.View;
    }
}