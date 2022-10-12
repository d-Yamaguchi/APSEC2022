package top.horsttop.appcore.load.callback;
import android.support.annotation.StyleRes;
/**
 * Created by horsttop on 2016/04/10.
 */
public class ProgressCallback extends top.horsttop.appcore.load.callback.Callback {
    private java.lang.String title;

    private java.lang.String subTitle;

    private int subTitleStyleRes = -1;

    private int titleStyleRes = -1;

    private ProgressCallback(top.horsttop.appcore.load.callback.ProgressCallback.Builder builder) {
        this.title = builder.title;
        this.subTitle = builder.subTitle;
        this.subTitleStyleRes = builder.subTitleStyleRes;
        this.titleStyleRes = builder.titleStyleRes;
        setSuccessVisible(builder.aboveable);
    }

    @java.lang.Override
    protected int onCreateView() {
        return 0;
    }

    @java.lang.Override
    protected android.view.View onBuildView(android.content.Context context) {
        return new android.widget.LinearLayout(context);
    }

    @java.lang.Override
    protected void onViewCreate(android.content.Context context, android.view.View view) {
        android.widget.LinearLayout.LayoutParams lp = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = android.view.Gravity.CENTER;
        android.widget.LinearLayout ll = ((android.widget.LinearLayout) (view));
        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
        ll.setGravity(android.view.Gravity.CENTER);
        android.widget.ProgressBar progressBar = new android.widget.ProgressBar(context);
        ll.addView(progressBar, lp);
        if (!android.text.TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
            android.widget.TextView tvTitle = new android.widget.TextView(context);
            if (titleStyleRes == (-1)) {
                TextView textView = new android.widget.TextView(context);
                textView.setTextAppearance(subTitleStyleRes);
            } else {
                tvTitle.setTextAppearance(context, titleStyleRes);
            }
            ll.addView(tvTitle, lp);
        }
        if (!android.text.TextUtils.isEmpty(subTitle)) {
            tvSubtitle.setText(subTitle);
            android.widget.TextView tvSubtitle = new android.widget.TextView(context);
            if (subTitleStyleRes == (-1)) {
                tvSubtitle.setTextAppearance(context, android.R.style.TextAppearance_Medium);
            } else {
                tvSubtitle.setTextAppearance(context, subTitleStyleRes);
            }
            ll.addView(tvSubtitle, lp);
        }
    }

    public static class Builder {
        private java.lang.String title;

        private java.lang.String subTitle;

        private int subTitleStyleRes = -1;

        private int titleStyleRes = -1;

        private boolean aboveable;

        public top.horsttop.appcore.load.callback.ProgressCallback.Builder setTitle(java.lang.String title) {
            return setTitle(title, -1);
        }

        public top.horsttop.appcore.load.callback.ProgressCallback.Builder setTitle(java.lang.String title, @android.support.annotation.StyleRes
        int titleStyleRes) {
            this.title = title;
            this.titleStyleRes = titleStyleRes;
            return this;
        }

        public top.horsttop.appcore.load.callback.ProgressCallback.Builder setSubTitle(java.lang.String subTitle) {
            return setSubTitle(subTitle, -1);
        }

        public top.horsttop.appcore.load.callback.ProgressCallback.Builder setSubTitle(java.lang.String subTitle, @android.support.annotation.StyleRes
        int subTitleStyleRes) {
            this.subTitle = subTitle;
            this.subTitleStyleRes = subTitleStyleRes;
            return this;
        }

        public top.horsttop.appcore.load.callback.ProgressCallback.Builder setAboveSuccess(boolean aboveable) {
            this.aboveable = aboveable;
            return this;
        }

        public top.horsttop.appcore.load.callback.ProgressCallback build() {
            return new top.horsttop.appcore.load.callback.ProgressCallback(this);
        }
    }
}