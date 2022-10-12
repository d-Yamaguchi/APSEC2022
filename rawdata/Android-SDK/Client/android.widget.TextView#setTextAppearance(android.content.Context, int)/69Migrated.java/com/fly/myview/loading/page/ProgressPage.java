package com.fly.myview.loading.page;
import android.support.annotation.StyleRes;
/**
 * 包    名 : com.fly.myview.loading.callback
 * 作    者 : FLY
 * 创建时间 : 2019/7/2
 * 描述: 加载页面
 */
public class ProgressPage extends com.fly.myview.loading.page.Page {
    private java.lang.String title;

    private java.lang.String subTitle;

    private int subTitleStyleRes = -1;

    private int titleStyleRes = -1;

    private ProgressPage(com.fly.myview.loading.page.ProgressPage.Builder builder) {
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

        public com.fly.myview.loading.page.ProgressPage.Builder setTitle(java.lang.String title) {
            return setTitle(title, -1);
        }

        public com.fly.myview.loading.page.ProgressPage.Builder setTitle(java.lang.String title, @android.support.annotation.StyleRes
        int titleStyleRes) {
            this.title = title;
            this.titleStyleRes = titleStyleRes;
            return this;
        }

        public com.fly.myview.loading.page.ProgressPage.Builder setSubTitle(java.lang.String subTitle) {
            return setSubTitle(subTitle, -1);
        }

        public com.fly.myview.loading.page.ProgressPage.Builder setSubTitle(java.lang.String subTitle, @android.support.annotation.StyleRes
        int subTitleStyleRes) {
            this.subTitle = subTitle;
            this.subTitleStyleRes = subTitleStyleRes;
            return this;
        }

        public com.fly.myview.loading.page.ProgressPage.Builder setAboveSuccess(boolean aboveable) {
            this.aboveable = aboveable;
            return this;
        }

        public com.fly.myview.loading.page.ProgressPage build() {
            return new com.fly.myview.loading.page.ProgressPage(this);
        }
    }
}