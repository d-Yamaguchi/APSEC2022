protected void onDraw(android.graphics.Canvas canvas) {
    if (!isInEditMode()) {
        android.graphics.Canvas canvas2;
        android.graphics.Paint paint;
        android.graphics.Canvas _CVAR0 = canvas;
        float _CVAR1 = 0.0F;
        float _CVAR2 = 0.0F;
        int _CVAR3 = ((float) (this.f4422c));
        int _CVAR4 = ((float) (this.f4423d));
        <nulltype> _CVAR5 = null;
        int _CVAR6 = 31;
        int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
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
            this.f4421b = new android.graphics.SweepGradient(((float) (this.f4437r)), ((float) (this.f4438s)), new int[]{ android.graphics.Color.argb(255, 251, 109, 0), android.graphics.Color.argb(255, 255, 216, 0), android.graphics.Color.argb(255, 31, 191, 105), android.graphics.Color.argb(255, 32, 193, 106) }, new float[]{ 0.07F, 0.23F, 0.47F, 0.7F });
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
            this.f4445z = new android.graphics.LinearGradient(((float) (this.f4437r)), ((float) (this.f4438s)), ((float) (this.f4437r)), f3, new int[]{ android.graphics.Color.argb(255, 247, 107, 28), android.graphics.Color.argb(255, 252, 200, 0) }, new float[]{ 0.3F, 0.7F }, android.graphics.Shader.TileMode.CLAMP);
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