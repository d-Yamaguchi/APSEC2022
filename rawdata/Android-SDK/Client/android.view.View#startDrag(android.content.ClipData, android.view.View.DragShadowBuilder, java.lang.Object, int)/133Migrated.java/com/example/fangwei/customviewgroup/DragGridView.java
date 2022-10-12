package com.example.fangwei.customviewgroup;
/**
 * 拖拽控件
 * Created by fangwei on 15/11/2.
 */
public class DragGridView extends android.view.ViewGroup {
    boolean isFirst = true;

    boolean isAnim = false;

    android.content.Context mcontext;

    java.util.HashMap<java.lang.String, android.graphics.drawable.Drawable> cache = new java.util.HashMap<java.lang.String, android.graphics.drawable.Drawable>();

    boolean isEnter = false;

    android.animation.AnimatorSet resultSet;

    android.widget.ImageView dragView;

    android.widget.ImageView changView;

    int dragposition = -1;

    int mdownx = 100;

    private android.widget.Scroller mScroller;

    int mdowny = 100;

    int xcount = 5;

    int ycount = 8;

    int marginx = 20;

    int marginy = 10;

    int xinter = 10;

    int yinter = 20;

    public DragGridView(android.content.Context context) {
        super(context);
        mcontext = context;
        mScroller = new android.widget.Scroller(context);
        init();
    }

    public DragGridView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
        mScroller = new android.widget.Scroller(context);
        init();
    }

    public DragGridView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
        mScroller = new android.widget.Scroller(context);
        init();
    }

    private void init() {
    }

    @java.lang.Override
    public boolean dispatchTouchEvent(android.view.MotionEvent ev) {
        if (ev.getAction() == android.view.MotionEvent.ACTION_OUTSIDE) {
            finishUI();
        } else if (ev.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            mdownx = ((int) (ev.getX()));
            mdowny = ((int) (ev.getY()));
        }
        return super.dispatchTouchEvent(ev);
    }

    @java.lang.Override
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @java.lang.Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @java.lang.Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((android.view.View) (getParent())).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @java.lang.Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int itemwidth = (width - marginx) / xcount;
        itemwidth = itemwidth - xinter;
        int itemHeight = itemwidth;
        int lineWith = marginx / 2;
        int lineheight = marginy;
        for (int j = 0; j < ycount; j++) {
            for (int i = 0; i < xcount; i++) {
                android.widget.ImageView iv;
                if (isFirst == true) {
                    iv = new android.widget.ImageView(mcontext);
                    iv.setTag((j * xcount) + i);
                    addView(iv);
                } else {
                    iv = ((android.widget.ImageView) (getChildAt((j * xcount) + i)));
                    iv.setVisibility(android.view.View.VISIBLE);
                }
                if (((j * xcount) + i) == dragposition) {
                    iv.setVisibility(android.view.View.INVISIBLE);
                    changView = iv;
                }
                if (i == 1) {
                    if (cache.containsKey(java.lang.String.valueOf(i))) {
                        iv.setBackground(cache.get(java.lang.String.valueOf(i)));
                    } else {
                        android.graphics.drawable.Drawable drawable = mcontext.getResources().getDrawable(R.drawable.test);
                        iv.setBackground(drawable);
                        cache.put(java.lang.String.valueOf(i), drawable);
                    }
                } else if (i == 2) {
                    if (cache.containsKey(java.lang.String.valueOf(i))) {
                        iv.setBackground(cache.get(java.lang.String.valueOf(i)));
                    } else {
                        android.graphics.drawable.Drawable drawable = mcontext.getResources().getDrawable(R.drawable.test1);
                        iv.setBackground(drawable);
                        cache.put(java.lang.String.valueOf(i), drawable);
                    }
                }
                if (i == 3) {
                    if (cache.containsKey(java.lang.String.valueOf(i))) {
                        iv.setBackground(cache.get(java.lang.String.valueOf(i)));
                    } else {
                        android.graphics.drawable.Drawable drawable = mcontext.getResources().getDrawable(R.drawable.test2);
                        iv.setBackground(drawable);
                        cache.put(java.lang.String.valueOf(i), drawable);
                    }
                }
                if (i == 4) {
                    if (cache.containsKey(java.lang.String.valueOf(i))) {
                        iv.setBackground(cache.get(java.lang.String.valueOf(i)));
                    } else {
                        android.graphics.drawable.Drawable drawable = mcontext.getResources().getDrawable(R.drawable.test3);
                        iv.setBackground(drawable);
                        cache.put(java.lang.String.valueOf(i), drawable);
                    }
                }
                if (i == 5) {
                    if (cache.containsKey(java.lang.String.valueOf(i))) {
                        iv.setBackground(cache.get(java.lang.String.valueOf(i)));
                    } else {
                        android.graphics.drawable.Drawable drawable = mcontext.getResources().getDrawable(R.drawable.test4);
                        iv.setBackground(drawable);
                        cache.put(java.lang.String.valueOf(i), drawable);
                    }
                }
                if (i == 6) {
                    if (cache.containsKey(java.lang.String.valueOf(i))) {
                        iv.setBackground(cache.get(java.lang.String.valueOf(i)));
                    } else {
                        android.graphics.drawable.Drawable drawable = mcontext.getResources().getDrawable(R.drawable.test5);
                        iv.setBackground(drawable);
                        cache.put(java.lang.String.valueOf(i), drawable);
                    }
                } else {
                    iv.setBackground(mcontext.getResources().getDrawable(R.drawable.test6));
                }
                iv.layout(l + lineWith, t + lineheight, (l + lineWith) + itemwidth, (t + lineheight) + itemHeight);
                iv.setLeft(l + lineWith);
                iv.setRight((l + lineWith) + itemwidth);
                iv.setMaxHeight(itemHeight);
                iv.setMaxWidth(itemwidth);
                iv.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
                iv.setOnLongClickListener(mlongListener);
                lineWith = (lineWith + itemwidth) + xinter;
            }
            lineheight = (lineheight + yinter) + itemHeight;
            lineWith = marginx / 2;
        }
        isFirst = false;
    }

    @java.lang.Override
    public boolean onInterceptHoverEvent(android.view.MotionEvent event) {
        return super.onInterceptHoverEvent(event);
    }

    @java.lang.Override
    public boolean onInterceptTouchEvent(android.view.MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @java.lang.Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        return super.onTouchEvent(event);
    }

    android.view.View.OnLongClickListener mlongListener = new android.view.View.OnLongClickListener() {
        @java.lang.Override
        public boolean onLongClick(android.view.View v) {
            if (isAnim == true) {
                return false;
            }
            dragposition = pointToPosition(v.getLeft() + (v.getWidth() / 2), v.getTop() + (v.getHeight() / 2));
            dragView = ((android.widget.ImageView) (v));
            isEnter = false;
            mdownx = v.getLeft();
            mdowny = v.getTop();
            android.content.ClipData data = android.content.ClipData.newPlainText("dot", "Dot : " + v.toString());
            final android.view.View.DragShadowBuilder builder = new android.view.View.DragShadowBuilder(v);
            v.startDragAndDrop(data, builder, v, 0);
            v.setOnDragListener(__SmPLUnsupported__(0));
            return true;
        }
    };

    public int pointToPosition(int x, int y) {
        android.graphics.Rect frame = new android.graphics.Rect();
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final android.view.View child = getChildAt(i);
            if (child.getVisibility() == android.view.View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }
            }
        }
        return android.widget.AdapterView.INVALID_POSITION;
    }

    @java.lang.Override
    protected boolean drawChild(android.graphics.Canvas canvas, android.view.View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    @java.lang.Override
    public boolean dispatchDragEvent(android.view.DragEvent event) {
        // 截获拖拽事件的坐标，通过发消息通知，控件更新动画
        // Log.e("dragcc",mdownx+"|"+mdowny);
        if (isEnter) {
            updateUI();
        }
        mdowny = ((int) (event.getY()));
        mdownx = ((int) (event.getX()));
        isEnter = true;
        if ((event.getAction() == android.view.DragEvent.ACTION_DRAG_ENDED) || (event.getAction() == android.view.DragEvent.ACTION_DRAG_EXITED)) {
            finishUI();
            return true;
        }
        return super.dispatchDragEvent(event);
    }

    public void updateUI() {
        if (isAnim == true) {
            return;
        }
        final int tempPosit = pointToPosition(mdownx, mdowny);
        if (tempPosit == (-1)) {
            return;
        }
        isAnim = true;
        boolean isForward = tempPosit > dragposition;
        java.util.List<android.animation.Animator> resultList = new java.util.LinkedList<android.animation.Animator>();
        android.util.Log.e("ccc", (dragposition + "|") + tempPosit);
        if (isForward) {
            for (int i = dragposition; i < tempPosit; i++) {
                android.animation.ObjectAnimator objx = null;
                android.animation.ObjectAnimator objy = null;
                android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
                int temp1 = i;
                int temp2 = i + 1;
                changView = ((android.widget.ImageView) (getChildAt(temp2)));
                int y1 = ((int) (java.lang.Math.ceil(((float) (temp1 + 1.0F)) / ((float) (xcount)))));
                int y2 = ((int) (java.lang.Math.ceil(((float) (temp2 + 1.0F)) / ((float) (xcount)))));
                int x1 = temp1 % xcount;
                int x2 = temp2 % xcount;
                if (y1 != y2) {
                    if (y2 > y1) {
                        objy = android.animation.ObjectAnimator.ofFloat(changView, "translationY", (-(y2 - y1)) * (changView.getHeight() + yinter));
                    } else {
                        objy = android.animation.ObjectAnimator.ofFloat(changView, "translationY", (y1 - y2) * (changView.getHeight() + yinter));
                    }
                }
                if (x2 > x1) {
                    objx = android.animation.ObjectAnimator.ofFloat(changView, "translationX", (-(changView.getWidth() + xinter)) * java.lang.Math.abs(x1 - x2));
                } else {
                    objx = android.animation.ObjectAnimator.ofFloat(changView, "translationX", (changView.getWidth() + xinter) * java.lang.Math.abs(x2 - x1));
                }
                isAnim = true;
                if (objy != null) {
                    animatorSet.playTogether(objx, objy);
                } else {
                    animatorSet.playTogether(objx);
                }
                resultList.add(animatorSet);
            }
        } else {
            for (int i = dragposition; i > tempPosit; i--) {
                android.animation.ObjectAnimator objx = null;
                android.animation.ObjectAnimator objy = null;
                android.animation.ObjectAnimator objx1 = null;
                android.animation.ObjectAnimator objy1 = null;
                android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
                int temp1 = i;
                int temp2 = i - 1;
                changView = ((android.widget.ImageView) (getChildAt(temp2)));
                android.view.View dragView = getChildAt(temp1);
                int y1 = ((int) (java.lang.Math.ceil(((float) (temp1 + 1.0F)) / ((float) (xcount)))));
                int y2 = ((int) (java.lang.Math.ceil(((float) (temp2 + 1.0F)) / ((float) (xcount)))));
                int x1 = temp1 % xcount;
                int x2 = temp2 % xcount;
                if (y1 != y2) {
                    if (y2 > y1) {
                        objy = android.animation.ObjectAnimator.ofFloat(changView, "translationY", (-(y2 - y1)) * (changView.getHeight() + yinter));
                        objy1 = android.animation.ObjectAnimator.ofFloat(changView, "translationY", (y2 - y1) * (changView.getHeight() + yinter));
                    } else {
                        objy = android.animation.ObjectAnimator.ofFloat(changView, "translationY", (y1 - y2) * (changView.getHeight() + yinter));
                        objy1 = android.animation.ObjectAnimator.ofFloat(changView, "translationY", (-(y2 - y1)) * (changView.getHeight() + yinter));
                    }
                }
                if (x2 > x1) {
                    objx = android.animation.ObjectAnimator.ofFloat(changView, "translationX", (-(changView.getWidth() + xinter)) * java.lang.Math.abs(x1 - x2));
                } else {
                    objx = android.animation.ObjectAnimator.ofFloat(changView, "translationX", (changView.getWidth() + xinter) * java.lang.Math.abs(x2 - x1));
                }
                if (objy != null) {
                    animatorSet.playTogether(objy, objx);
                } else {
                    animatorSet.playTogether(objx);
                }
                resultList.add(animatorSet);
            }
        }
        resultSet = new android.animation.AnimatorSet();
        resultSet.playTogether(resultList);
        resultSet.setDuration(200);
        resultSet.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
        resultSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @java.lang.Override
            public void onAnimationStart(android.animation.Animator animation) {
                super.onAnimationStart(animation);
            }

            @java.lang.Override
            public void onAnimationEnd(android.animation.Animator animation) {
                invalidate();
                dragposition = tempPosit;
                isFirst = true;
                removeAllViews();
                requestLayout();
                // invalidate();
                isAnim = false;
            }
        });
        resultSet.start();
    }

    public void finishUI() {
        if (dragposition >= 0) {
            android.animation.ObjectAnimator anim = android.animation.ObjectAnimator.ofFloat(getChildAt(dragposition), "alpha", 0.0F, 1.0F);
            anim.setDuration(100);
            anim.setInterpolator(new android.view.animation.AccelerateInterpolator());
            if ((resultSet != null) && resultSet.isRunning()) {
                anim.setStartDelay(200);
            }
            anim.start();
            anim.addListener(new android.animation.Animator.AnimatorListener() {
                @java.lang.Override
                public void onAnimationStart(android.animation.Animator animation) {
                }

                @java.lang.Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    android.view.View view = getChildAt(dragposition);
                    if (view != null) {
                        view.setVisibility(android.view.View.VISIBLE);
                    }
                    if (dragView != null) {
                        dragView.setVisibility(android.view.View.VISIBLE);
                    }
                    int count = getChildCount();
                    for (int i = 0; i < count; i++) {
                        getChildAt(i).setVisibility(android.view.View.VISIBLE);
                    }
                    if (changView != null) {
                        changView.setVisibility(android.view.View.VISIBLE);
                    }
                }

                @java.lang.Override
                public void onAnimationCancel(android.animation.Animator animation) {
                }

                @java.lang.Override
                public void onAnimationRepeat(android.animation.Animator animation) {
                }
            });
        }
        isEnter = false;
    }
}