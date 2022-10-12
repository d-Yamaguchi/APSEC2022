package cn.v6.sixrooms.surfaceanim.giftframe.giftscene;
import cn.v6.sixrooms.surfaceanim.AnimScene;
import cn.v6.sixrooms.surfaceanim.R;
import cn.v6.sixrooms.surfaceanim.util.AnimSceneResManager;
public abstract class GiftSceneElementRun extends cn.v6.sixrooms.surfaceanim.giftframe.giftscene.GiftSceneElement {
    protected int alpha = 255;

    protected int alphaFrame;

    protected android.graphics.Bitmap mBg;

    protected cn.v6.sixrooms.surfaceanim.giftframe.giftscene.GiftScene mGiftScene;

    protected android.graphics.Paint mPaint;

    protected android.graphics.Paint mPaintShade;

    protected android.graphics.Bitmap mRunBitmap;

    protected int mRunBitmapLeft;

    protected int mRunBitmapTop;

    protected android.graphics.Bitmap mRunShadeBitmap;

    protected android.graphics.Matrix matrix;

    protected float scaleX = 1.0F;

    protected float scaleXDest;

    protected float scaleXFrame;

    protected int transXFrame;

    protected int transXFrom;

    public abstract android.graphics.Bitmap elementBg();

    public abstract android.graphics.Bitmap elementRun();

    public GiftSceneElementRun(cn.v6.sixrooms.surfaceanim.AnimScene animScene) {
        super(animScene);
        this.mGiftScene = ((cn.v6.sixrooms.surfaceanim.giftframe.giftscene.GiftScene) (animScene));
        this.mPaint = new android.graphics.Paint();
        this.mPaintShade = new android.graphics.Paint();
        this.matrix = new android.graphics.Matrix();
        this.mRunBitmap = elementRun();
        this.mRunShadeBitmap = getBitmap(R.drawable.run_shade);
        this.mBg = elementBg();
        this.mRunBitmapLeft = cn.v6.sixrooms.surfaceanim.util.AnimSceneResManager.getInstance().getResources().getDimensionPixelSize(R.dimen.gift_run_margin_left);
        this.mRunBitmapTop = (this.mBg.getHeight() / 2) - (this.mRunBitmap.getHeight() / 2);
        this.transXFrom = -this.mRunBitmap.getWidth();
        this.transXFrame = (this.mRunBitmapLeft - this.transXFrom) / 13;
        this.alphaFrame = this.alpha / 8;
        this.scaleXDest = 0.48349056F;
        this.scaleXFrame = (1.0F - this.scaleXDest) / 8.0F;
    }

    public void drawElement(android.graphics.Canvas canvas) {
        if (!frameControl(this.mCurFrame)) {
            int saveLayer = canvas.saveLayer(0.0F, 0.0F, ((float) (canvas.getWidth())), ((float) (canvas.getHeight())), null);
            this.mPaint.setAlpha(this.alpha);
            int i = this.mAnimScene.getSceneParameter().getPoint().y;
            this.matrix.setScale(this.scaleX, 1.0F, ((float) (this.mRunBitmap.getWidth())), ((float) (this.mRunBitmap.getHeight())));
            this.matrix.postTranslate(((float) (this.transXFrom)), ((float) (this.mRunBitmapTop + i)));
            canvas.drawBitmap(this.mRunBitmap, this.matrix, this.mPaint);
            this.mPaintShade.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
            canvas.drawBitmap(this.mRunShadeBitmap, 0.0F, ((float) (i + this.mRunBitmapTop)), this.mPaintShade);
            this.mPaintShade.setXfermode(null);
            canvas.restoreToCount(saveLayer);
        }
    }
}