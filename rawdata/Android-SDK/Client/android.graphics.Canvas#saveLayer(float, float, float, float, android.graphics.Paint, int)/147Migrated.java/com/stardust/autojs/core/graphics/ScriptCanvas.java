package com.stardust.autojs.core.graphics;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.stardust.autojs.core.image.ImageWrapper;
import com.stardust.autojs.runtime.ScriptRuntime;
/**
 * Created by Stardust on 2018/3/22.
 */
public class ScriptCanvas {
    private android.graphics.Canvas mCanvas;

    private android.graphics.Bitmap mBitmap;

    public ScriptCanvas(int width, int height) {
        this(android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888));
    }

    public ScriptCanvas(@androidx.annotation.NonNull
    android.graphics.Bitmap bitmap) {
        mCanvas = new android.graphics.Canvas(bitmap);
        mBitmap = bitmap;
    }

    public ScriptCanvas(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image) {
        this(image.getBitmap().copy(image.getBitmap().getConfig(), true));
    }

    public ScriptCanvas() {
    }

    public android.graphics.Canvas getAndroidCanvas() {
        return mCanvas;
    }

    void setCanvas(android.graphics.Canvas canvas) {
        mCanvas = canvas;
    }

    public com.stardust.autojs.core.image.ImageWrapper toImage() {
        return com.stardust.autojs.core.image.ImageWrapper.ofBitmap(mBitmap.copy(mBitmap.getConfig(), true));
    }

    public boolean isHardwareAccelerated() {
        return mCanvas.isHardwareAccelerated();
    }

    public void setBitmap(@androidx.annotation.Nullable
    android.graphics.Bitmap bitmap) {
        mCanvas.setBitmap(bitmap);
    }

    public boolean isOpaque() {
        return mCanvas.isOpaque();
    }

    public int getWidth() {
        return mCanvas.getWidth();
    }

    public int getHeight() {
        return mCanvas.getHeight();
    }

    public int getDensity() {
        return mCanvas.getDensity();
    }

    public void setDensity(int density) {
        mCanvas.setDensity(density);
    }

    public int getMaximumBitmapWidth() {
        return mCanvas.getMaximumBitmapWidth();
    }

    public int getMaximumBitmapHeight() {
        return mCanvas.getMaximumBitmapHeight();
    }

    public int save() {
        return mCanvas.save();
    }

    public int saveLayer(@androidx.annotation.Nullable
    android.graphics.RectF bounds, @androidx.annotation.Nullable
    android.graphics.Paint paint, int saveFlags) {
        return mCanvas.saveLayer(bounds, paint, saveFlags);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public int saveLayer(@androidx.annotation.Nullable
    android.graphics.RectF bounds, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.LOLLIPOP);
        return mCanvas.saveLayer(bounds, paint);
    }

    public int saveLayer(float left, float top, float right, float bottom, @androidx.annotation.Nullable
    android.graphics.Paint paint, int saveFlags) {
        return mCanvas.saveLayer(left, top, right, bottom, paint);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public int saveLayer(float left, float top, float right, float bottom, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.LOLLIPOP);
        return mCanvas.saveLayer(left, top, right, bottom, paint);
    }

    public int saveLayerAlpha(@androidx.annotation.Nullable
    android.graphics.RectF bounds, int alpha, int saveFlags) {
        return mCanvas.saveLayerAlpha(bounds, alpha, saveFlags);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public int saveLayerAlpha(@androidx.annotation.Nullable
    android.graphics.RectF bounds, int alpha) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.LOLLIPOP);
        return mCanvas.saveLayerAlpha(bounds, alpha);
    }

    public int saveLayerAlpha(float left, float top, float right, float bottom, int alpha, int saveFlags) {
        return mCanvas.saveLayerAlpha(left, top, right, bottom, alpha, saveFlags);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public int saveLayerAlpha(float left, float top, float right, float bottom, int alpha) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.LOLLIPOP);
        return mCanvas.saveLayerAlpha(left, top, right, bottom, alpha);
    }

    public void restore() {
        mCanvas.restore();
    }

    public int getSaveCount() {
        return mCanvas.getSaveCount();
    }

    public void restoreToCount(int saveCount) {
        mCanvas.restoreToCount(saveCount);
    }

    public void translate(float dx, float dy) {
        mCanvas.translate(dx, dy);
    }

    public void scale(float sx, float sy) {
        mCanvas.scale(sx, sy);
    }

    public void scale(float sx, float sy, float px, float py) {
        mCanvas.scale(sx, sy, px, py);
    }

    public void rotate(float degrees) {
        mCanvas.rotate(degrees);
    }

    public void rotate(float degrees, float px, float py) {
        mCanvas.rotate(degrees, px, py);
    }

    public void skew(float sx, float sy) {
        mCanvas.skew(sx, sy);
    }

    public void concat(@androidx.annotation.Nullable
    android.graphics.Matrix matrix) {
        mCanvas.concat(matrix);
    }

    public void setMatrix(@androidx.annotation.Nullable
    android.graphics.Matrix matrix) {
        mCanvas.setMatrix(matrix);
    }

    @java.lang.Deprecated
    public void getMatrix(@androidx.annotation.NonNull
    android.graphics.Matrix ctm) {
        mCanvas.getMatrix(ctm);
    }

    @java.lang.Deprecated
    public android.graphics.Matrix getMatrix() {
        return mCanvas.getMatrix();
    }

    public boolean clipRect(@androidx.annotation.NonNull
    android.graphics.RectF rect, @androidx.annotation.NonNull
    android.graphics.Region.Op op) {
        return mCanvas.clipRect(rect, op);
    }

    public boolean clipRect(@androidx.annotation.NonNull
    android.graphics.Rect rect, @androidx.annotation.NonNull
    android.graphics.Region.Op op) {
        return mCanvas.clipRect(rect, op);
    }

    public boolean clipRect(@androidx.annotation.NonNull
    android.graphics.RectF rect) {
        return mCanvas.clipRect(rect);
    }

    public boolean clipRect(@androidx.annotation.NonNull
    android.graphics.Rect rect) {
        return mCanvas.clipRect(rect);
    }

    public boolean clipRect(float left, float top, float right, float bottom, @androidx.annotation.NonNull
    android.graphics.Region.Op op) {
        return mCanvas.clipRect(left, top, right, bottom, op);
    }

    public boolean clipRect(float left, float top, float right, float bottom) {
        return mCanvas.clipRect(left, top, right, bottom);
    }

    public boolean clipRect(int left, int top, int right, int bottom) {
        return mCanvas.clipRect(left, top, right, bottom);
    }

    public boolean clipPath(@androidx.annotation.NonNull
    android.graphics.Path path, @androidx.annotation.NonNull
    android.graphics.Region.Op op) {
        return mCanvas.clipPath(path, op);
    }

    public boolean clipPath(@androidx.annotation.NonNull
    android.graphics.Path path) {
        return mCanvas.clipPath(path);
    }

    public android.graphics.DrawFilter getDrawFilter() {
        return mCanvas.getDrawFilter();
    }

    public void setDrawFilter(@androidx.annotation.Nullable
    android.graphics.DrawFilter filter) {
        mCanvas.setDrawFilter(filter);
    }

    public boolean quickReject(@androidx.annotation.NonNull
    android.graphics.RectF rect, @androidx.annotation.NonNull
    android.graphics.Canvas.EdgeType type) {
        return mCanvas.quickReject(rect, type);
    }

    public boolean quickReject(@androidx.annotation.NonNull
    android.graphics.Path path, @androidx.annotation.NonNull
    android.graphics.Canvas.EdgeType type) {
        return mCanvas.quickReject(path, type);
    }

    public boolean quickReject(float left, float top, float right, float bottom, @androidx.annotation.NonNull
    android.graphics.Canvas.EdgeType type) {
        return mCanvas.quickReject(left, top, right, bottom, type);
    }

    public boolean getClipBounds(@androidx.annotation.Nullable
    android.graphics.Rect bounds) {
        return mCanvas.getClipBounds(bounds);
    }

    public android.graphics.Rect getClipBounds() {
        return mCanvas.getClipBounds();
    }

    public void drawRGB(int r, int g, int b) {
        mCanvas.drawRGB(r, g, b);
    }

    public void drawARGB(int a, int r, int g, int b) {
        mCanvas.drawARGB(a, r, g, b);
    }

    public void drawColor(int color) {
        mCanvas.drawColor(color);
    }

    public void drawColor(int color, @androidx.annotation.NonNull
    android.graphics.PorterDuff.Mode mode) {
        mCanvas.drawColor(color, mode);
    }

    public void drawPaint(@androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPaint(paint);
    }

    public void drawPoints(float[] pts, int offset, int count, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPoints(pts, offset, count, paint);
    }

    public void drawPoints(@androidx.annotation.NonNull
    float[] pts, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPoints(pts, paint);
    }

    public void drawPoint(float x, float y, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPoint(x, y, paint);
    }

    public void drawLine(float startX, float startY, float stopX, float stopY, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    public void drawLines(@androidx.annotation.NonNull
    float[] pts, int offset, int count, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawLines(pts, offset, count, paint);
    }

    public void drawLines(@androidx.annotation.NonNull
    float[] pts, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawLines(pts, paint);
    }

    public void drawRect(@androidx.annotation.NonNull
    android.graphics.RectF rect, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawRect(rect, paint);
    }

    public void drawRect(@androidx.annotation.NonNull
    android.graphics.Rect r, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawRect(r, paint);
    }

    public void drawRect(float left, float top, float right, float bottom, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawRect(left, top, right, bottom, paint);
    }

    public void drawOval(@androidx.annotation.NonNull
    android.graphics.RectF oval, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawOval(oval, paint);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public void drawOval(float left, float top, float right, float bottom, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawOval(left, top, right, bottom, paint);
    }

    public void drawCircle(float cx, float cy, float radius, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawCircle(cx, cy, radius, paint);
    }

    public void drawArc(@androidx.annotation.NonNull
    android.graphics.RectF oval, float startAngle, float sweepAngle, boolean useCenter, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.LOLLIPOP);
        mCanvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, useCenter, paint);
    }

    public void drawRoundRect(@androidx.annotation.NonNull
    android.graphics.RectF rect, float rx, float ry, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawRoundRect(rect, rx, ry, paint);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.LOLLIPOP);
        mCanvas.drawRoundRect(left, top, right, bottom, rx, ry, paint);
    }

    public void drawPath(@androidx.annotation.NonNull
    android.graphics.Path path, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPath(path, paint);
    }

    public void drawBitmap(@androidx.annotation.NonNull
    android.graphics.Bitmap bitmap, float left, float top, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(bitmap, left, top, paint);
    }

    public void drawBitmap(@androidx.annotation.NonNull
    android.graphics.Bitmap bitmap, @androidx.annotation.Nullable
    android.graphics.Rect src, @androidx.annotation.NonNull
    android.graphics.RectF dst, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(bitmap, src, dst, paint);
    }

    public void drawBitmap(@androidx.annotation.NonNull
    android.graphics.Bitmap bitmap, @androidx.annotation.Nullable
    android.graphics.Rect src, @androidx.annotation.NonNull
    android.graphics.Rect dst, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(bitmap, src, dst, paint);
    }

    @java.lang.Deprecated
    public void drawBitmap(@androidx.annotation.NonNull
    int[] colors, int offset, int stride, float x, float y, int width, int height, boolean hasAlpha, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    @java.lang.Deprecated
    public void drawBitmap(@androidx.annotation.NonNull
    int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    public void drawBitmap(@androidx.annotation.NonNull
    android.graphics.Bitmap bitmap, @androidx.annotation.NonNull
    android.graphics.Matrix matrix, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(bitmap, matrix, paint);
    }

    public void drawBitmapMesh(@androidx.annotation.NonNull
    android.graphics.Bitmap bitmap, int meshWidth, int meshHeight, @androidx.annotation.NonNull
    float[] verts, int vertOffset, @androidx.annotation.Nullable
    int[] colors, int colorOffset, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors, colorOffset, paint);
    }

    public void drawVertices(@androidx.annotation.NonNull
    android.graphics.Canvas.VertexMode mode, int vertexCount, @androidx.annotation.NonNull
    float[] verts, int vertOffset, @androidx.annotation.Nullable
    float[] texs, int texOffset, @androidx.annotation.Nullable
    int[] colors, int colorOffset, @androidx.annotation.Nullable
    short[] indices, int indexOffset, int indexCount, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors, colorOffset, indices, indexOffset, indexCount, paint);
    }

    public void drawText(@androidx.annotation.NonNull
    char[] text, int index, int count, float x, float y, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawText(text, index, count, x, y, paint);
    }

    public void drawText(@androidx.annotation.NonNull
    java.lang.String text, float x, float y, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawText(text, x, y, paint);
    }

    public void drawText(@androidx.annotation.NonNull
    java.lang.String text, int start, int end, float x, float y, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawText(text, start, end, x, y, paint);
    }

    public void drawText(@androidx.annotation.NonNull
    java.lang.CharSequence text, int start, int end, float x, float y, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawText(text, start, end, x, y, paint);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
    public void drawTextRun(@androidx.annotation.NonNull
    char[] text, int index, int count, int contextIndex, int contextCount, float x, float y, boolean isRtl, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.M);
        mCanvas.drawTextRun(text, index, count, contextIndex, contextCount, x, y, isRtl, paint);
    }

    @androidx.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.M)
    public void drawTextRun(@androidx.annotation.NonNull
    java.lang.CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        com.stardust.autojs.runtime.ScriptRuntime.requiresApi(android.os.Build.VERSION_CODES.M);
        mCanvas.drawTextRun(text, start, end, contextStart, contextEnd, x, y, isRtl, paint);
    }

    @java.lang.Deprecated
    public void drawPosText(@androidx.annotation.NonNull
    char[] text, int index, int count, @androidx.annotation.NonNull
    float[] pos, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPosText(text, index, count, pos, paint);
    }

    @java.lang.Deprecated
    public void drawPosText(@androidx.annotation.NonNull
    java.lang.String text, @androidx.annotation.NonNull
    float[] pos, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawPosText(text, pos, paint);
    }

    public void drawTextOnPath(@androidx.annotation.NonNull
    char[] text, int index, int count, @androidx.annotation.NonNull
    android.graphics.Path path, float hOffset, float vOffset, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawTextOnPath(text, index, count, path, hOffset, vOffset, paint);
    }

    public void drawTextOnPath(@androidx.annotation.NonNull
    java.lang.String text, @androidx.annotation.NonNull
    android.graphics.Path path, float hOffset, float vOffset, @androidx.annotation.NonNull
    android.graphics.Paint paint) {
        mCanvas.drawTextOnPath(text, path, hOffset, vOffset, paint);
    }

    public void drawPicture(@androidx.annotation.NonNull
    android.graphics.Picture picture) {
        mCanvas.drawPicture(picture);
    }

    public void drawPicture(@androidx.annotation.NonNull
    android.graphics.Picture picture, @androidx.annotation.NonNull
    android.graphics.RectF dst) {
        mCanvas.drawPicture(picture, dst);
    }

    public void drawPicture(@androidx.annotation.NonNull
    android.graphics.Picture picture, @androidx.annotation.NonNull
    android.graphics.Rect dst) {
        mCanvas.drawPicture(picture, dst);
    }

    public void drawImage(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image, float left, float top, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(image.getBitmap(), left, top, paint);
    }

    public void drawImage(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image, float left, float top, float width, float height, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(image.getBitmap(), null, new android.graphics.RectF(left, top, left + width, top + height), paint);
    }

    public void drawImage(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image, int sx, int sy, int swidth, int sheight, float left, float top, float width, float height, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(image.getBitmap(), new android.graphics.Rect(sx, sy, sx + swidth, sy + sheight), new android.graphics.RectF(left, top, left + width, top + height), paint);
    }

    public void drawImage(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image, @androidx.annotation.Nullable
    android.graphics.Rect src, @androidx.annotation.NonNull
    android.graphics.RectF dst, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(image.getBitmap(), src, dst, paint);
    }

    public void drawImage(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image, @androidx.annotation.Nullable
    android.graphics.Rect src, @androidx.annotation.NonNull
    android.graphics.Rect dst, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(image.getBitmap(), src, dst, paint);
    }

    public void drawImage(@androidx.annotation.NonNull
    com.stardust.autojs.core.image.ImageWrapper image, @androidx.annotation.NonNull
    android.graphics.Matrix matrix, @androidx.annotation.Nullable
    android.graphics.Paint paint) {
        mCanvas.drawBitmap(image.getBitmap(), matrix, paint);
    }
}