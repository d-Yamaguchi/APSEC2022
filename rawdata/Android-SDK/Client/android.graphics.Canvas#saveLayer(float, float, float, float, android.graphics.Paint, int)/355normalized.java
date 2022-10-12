protected void onDraw(android.graphics.Canvas canvas) {
    if (!isInEditMode()) {
        android.graphics.Canvas _CVAR0 = canvas;
        float _CVAR1 = 0.0F;
        float _CVAR2 = 0.0F;
        int _CVAR3 = ((float) (this.f4454c));
        int _CVAR4 = ((float) (this.f4455d));
        <nulltype> _CVAR5 = null;
        int _CVAR6 = 31;
        int saveLayer = _CVAR0.saveLayer(_CVAR1, _CVAR2, _CVAR3, _CVAR4, _CVAR5, _CVAR6);
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