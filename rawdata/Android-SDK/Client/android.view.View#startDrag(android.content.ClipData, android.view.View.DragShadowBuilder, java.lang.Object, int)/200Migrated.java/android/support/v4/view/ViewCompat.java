package android.support.v4.view;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.os.BuildCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;

    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;

    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;

    static final android.support.v4.view.ViewCompat.ViewCompatBaseImpl IMPL;

    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;

    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;

    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;

    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;

    @java.lang.Deprecated
    public static final int LAYER_TYPE_HARDWARE = 2;

    @java.lang.Deprecated
    public static final int LAYER_TYPE_NONE = 0;

    @java.lang.Deprecated
    public static final int LAYER_TYPE_SOFTWARE = 1;

    public static final int LAYOUT_DIRECTION_INHERIT = 2;

    public static final int LAYOUT_DIRECTION_LOCALE = 3;

    public static final int LAYOUT_DIRECTION_LTR = 0;

    public static final int LAYOUT_DIRECTION_RTL = 1;

    @java.lang.Deprecated
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;

    @java.lang.Deprecated
    public static final int MEASURED_SIZE_MASK = 16777215;

    @java.lang.Deprecated
    public static final int MEASURED_STATE_MASK = -16777216;

    @java.lang.Deprecated
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;

    @java.lang.Deprecated
    public static final int OVER_SCROLL_ALWAYS = 0;

    @java.lang.Deprecated
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;

    @java.lang.Deprecated
    public static final int OVER_SCROLL_NEVER = 2;

    public static final int SCROLL_AXIS_HORIZONTAL = 1;

    public static final int SCROLL_AXIS_NONE = 0;

    public static final int SCROLL_AXIS_VERTICAL = 2;

    public static final int SCROLL_INDICATOR_BOTTOM = 2;

    public static final int SCROLL_INDICATOR_END = 32;

    public static final int SCROLL_INDICATOR_LEFT = 4;

    public static final int SCROLL_INDICATOR_RIGHT = 8;

    public static final int SCROLL_INDICATOR_START = 16;

    public static final int SCROLL_INDICATOR_TOP = 1;

    private static final java.lang.String TAG = "ViewCompat";

    @android.support.annotation.RestrictTo({ android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusDirection {}

    @android.support.annotation.RestrictTo({ android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusRealDirection {}

    @android.support.annotation.RestrictTo({ android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusRelativeDirection {}

    @android.support.annotation.RestrictTo({ android.support.annotation.RestrictTo.Scope.LIBRARY_GROUP })
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {}

    static class ViewCompatBaseImpl {
        static boolean sAccessibilityDelegateCheckFailed = false;

        static java.lang.reflect.Field sAccessibilityDelegateField;

        private static java.lang.reflect.Method sChildrenDrawingOrderMethod;

        private static java.lang.reflect.Field sMinHeightField;

        private static boolean sMinHeightFieldFetched;

        private static java.lang.reflect.Field sMinWidthField;

        private static boolean sMinWidthFieldFetched;

        private static java.util.WeakHashMap<android.view.View, java.lang.String> sTransitionNameMap;

        private java.lang.reflect.Method mDispatchFinishTemporaryDetach;

        private java.lang.reflect.Method mDispatchStartTemporaryDetach;

        private boolean mTempDetachBound;

        java.util.WeakHashMap<android.view.View, android.support.v4.view.ViewPropertyAnimatorCompat> mViewPropertyAnimatorCompatMap = null;

        ViewCompatBaseImpl() {
        }

        public void setAccessibilityDelegate(android.view.View v, @android.support.annotation.Nullable
        android.support.v4.view.AccessibilityDelegateCompat delegate) {
            v.setAccessibilityDelegate(delegate == null ? null : delegate.getBridge());
        }

        public boolean hasAccessibilityDelegate(android.view.View v) {
            boolean z = true;
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateCheckFailed) {
                return false;
            }
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateField == null) {
                try {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateField = android.view.View.class.getDeclaredField("mAccessibilityDelegate");
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateField.setAccessible(true);
                } catch (java.lang.Throwable th) {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateCheckFailed = true;
                    return false;
                }
            }
            try {
                if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateField.get(v) == null) {
                    z = false;
                }
                return z;
            } catch (java.lang.Throwable th2) {
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sAccessibilityDelegateCheckFailed = true;
                return false;
            }
        }

        public void onInitializeAccessibilityNodeInfo(android.view.View v, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat info) {
            v.onInitializeAccessibilityNodeInfo(((android.view.accessibility.AccessibilityNodeInfo) (info.getInfo())));
        }

        public boolean startDragAndDrop(android.view.View v, android.content.ClipData data, android.view.View.DragShadowBuilder shadowBuilder, java.lang.Object localState, int flags) {
            return v.startDragAndDrop(data, shadowBuilder, localState, flags);
        }

        public void cancelDragAndDrop(android.view.View v) {
        }

        public void updateDragShadow(android.view.View v, android.view.View.DragShadowBuilder shadowBuilder) {
        }

        public boolean hasTransientState(android.view.View view) {
            return false;
        }

        public void setHasTransientState(android.view.View view, boolean hasTransientState) {
        }

        public void postInvalidateOnAnimation(android.view.View view) {
            view.invalidate();
        }

        public void postInvalidateOnAnimation(android.view.View view, int left, int top, int right, int bottom) {
            view.invalidate(left, top, right, bottom);
        }

        public void postOnAnimation(android.view.View view, java.lang.Runnable action) {
            view.postDelayed(action, getFrameTime());
        }

        public void postOnAnimationDelayed(android.view.View view, java.lang.Runnable action, long delayMillis) {
            view.postDelayed(action, getFrameTime() + delayMillis);
        }

        long getFrameTime() {
            return android.animation.ValueAnimator.getFrameDelay();
        }

        public int getImportantForAccessibility(android.view.View view) {
            return 0;
        }

        public void setImportantForAccessibility(android.view.View view, int mode) {
        }

        public boolean isImportantForAccessibility(android.view.View view) {
            return true;
        }

        public boolean performAccessibilityAction(android.view.View view, int action, android.os.Bundle arguments) {
            return false;
        }

        public android.support.v4.view.accessibility.AccessibilityNodeProviderCompat getAccessibilityNodeProvider(android.view.View view) {
            return null;
        }

        public int getLabelFor(android.view.View view) {
            return 0;
        }

        public void setLabelFor(android.view.View view, int id) {
        }

        public void setLayerPaint(android.view.View view, android.graphics.Paint paint) {
            view.setLayerType(view.getLayerType(), paint);
            view.invalidate();
        }

        public int getLayoutDirection(android.view.View view) {
            return 0;
        }

        public void setLayoutDirection(android.view.View view, int layoutDirection) {
        }

        public android.view.ViewParent getParentForAccessibility(android.view.View view) {
            return view.getParent();
        }

        public int getAccessibilityLiveRegion(android.view.View view) {
            return 0;
        }

        public void setAccessibilityLiveRegion(android.view.View view, int mode) {
        }

        public int getPaddingStart(android.view.View view) {
            return view.getPaddingLeft();
        }

        public int getPaddingEnd(android.view.View view) {
            return view.getPaddingRight();
        }

        public void setPaddingRelative(android.view.View view, int start, int top, int end, int bottom) {
            view.setPadding(start, top, end, bottom);
        }

        public void dispatchStartTemporaryDetach(android.view.View view) {
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchStartTemporaryDetach != null) {
                try {
                    this.mDispatchStartTemporaryDetach.invoke(view, new java.lang.Object[0]);
                    return;
                } catch (java.lang.Exception e) {
                    android.util.Log.d(android.support.v4.view.ViewCompat.TAG, "Error calling dispatchStartTemporaryDetach", e);
                    return;
                }
            }
            view.onStartTemporaryDetach();
        }

        public void dispatchFinishTemporaryDetach(android.view.View view) {
            if (!this.mTempDetachBound) {
                bindTempDetach();
            }
            if (this.mDispatchFinishTemporaryDetach != null) {
                try {
                    this.mDispatchFinishTemporaryDetach.invoke(view, new java.lang.Object[0]);
                    return;
                } catch (java.lang.Exception e) {
                    android.util.Log.d(android.support.v4.view.ViewCompat.TAG, "Error calling dispatchFinishTemporaryDetach", e);
                    return;
                }
            }
            view.onFinishTemporaryDetach();
        }

        public boolean hasOverlappingRendering(android.view.View view) {
            return true;
        }

        private void bindTempDetach() {
            try {
                this.mDispatchStartTemporaryDetach = android.view.View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new java.lang.Class[0]);
                this.mDispatchFinishTemporaryDetach = android.view.View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new java.lang.Class[0]);
            } catch (java.lang.NoSuchMethodException e) {
                android.util.Log.e(android.support.v4.view.ViewCompat.TAG, "Couldn't find method", e);
            }
            this.mTempDetachBound = true;
        }

        public int getMinimumWidth(android.view.View view) {
            if (!android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinWidthFieldFetched) {
                try {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinWidthField = android.view.View.class.getDeclaredField("mMinWidth");
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinWidthField.setAccessible(true);
                } catch (java.lang.NoSuchFieldException e) {
                }
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinWidthFieldFetched = true;
            }
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinWidthField != null) {
                try {
                    return ((java.lang.Integer) (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinWidthField.get(view))).intValue();
                } catch (java.lang.Exception e2) {
                }
            }
            return 0;
        }

        public int getMinimumHeight(android.view.View view) {
            if (!android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinHeightFieldFetched) {
                try {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinHeightField = android.view.View.class.getDeclaredField("mMinHeight");
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinHeightField.setAccessible(true);
                } catch (java.lang.NoSuchFieldException e) {
                }
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinHeightFieldFetched = true;
            }
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinHeightField != null) {
                try {
                    return ((java.lang.Integer) (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sMinHeightField.get(view))).intValue();
                } catch (java.lang.Exception e2) {
                }
            }
            return 0;
        }

        public android.support.v4.view.ViewPropertyAnimatorCompat animate(android.view.View view) {
            if (this.mViewPropertyAnimatorCompatMap == null) {
                this.mViewPropertyAnimatorCompatMap = new java.util.WeakHashMap();
            }
            android.support.v4.view.ViewPropertyAnimatorCompat vpa = ((android.support.v4.view.ViewPropertyAnimatorCompat) (this.mViewPropertyAnimatorCompatMap.get(view)));
            if (vpa != null) {
                return vpa;
            }
            vpa = new android.support.v4.view.ViewPropertyAnimatorCompat(view);
            this.mViewPropertyAnimatorCompatMap.put(view, vpa);
            return vpa;
        }

        public void setTransitionName(android.view.View view, java.lang.String transitionName) {
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sTransitionNameMap == null) {
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sTransitionNameMap = new java.util.WeakHashMap();
            }
            android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sTransitionNameMap.put(view, transitionName);
        }

        public java.lang.String getTransitionName(android.view.View view) {
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sTransitionNameMap == null) {
                return null;
            }
            return ((java.lang.String) (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sTransitionNameMap.get(view)));
        }

        public int getWindowSystemUiVisibility(android.view.View view) {
            return 0;
        }

        public void requestApplyInsets(android.view.View view) {
        }

        public void setElevation(android.view.View view, float elevation) {
        }

        public float getElevation(android.view.View view) {
            return 0.0F;
        }

        public void setTranslationZ(android.view.View view, float translationZ) {
        }

        public float getTranslationZ(android.view.View view) {
            return 0.0F;
        }

        public void setClipBounds(android.view.View view, android.graphics.Rect clipBounds) {
        }

        public android.graphics.Rect getClipBounds(android.view.View view) {
            return null;
        }

        public void setChildrenDrawingOrderEnabled(android.view.ViewGroup viewGroup, boolean enabled) {
            if (android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sChildrenDrawingOrderMethod == null) {
                try {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sChildrenDrawingOrderMethod = android.view.ViewGroup.class.getDeclaredMethod("setChildrenDrawingOrderEnabled", new java.lang.Class[]{ java.lang.Boolean.TYPE });
                } catch (java.lang.NoSuchMethodException e) {
                    android.util.Log.e(android.support.v4.view.ViewCompat.TAG, "Unable to find childrenDrawingOrderEnabled", e);
                }
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sChildrenDrawingOrderMethod.setAccessible(true);
            }
            try {
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.sChildrenDrawingOrderMethod.invoke(viewGroup, new java.lang.Object[]{ java.lang.Boolean.valueOf(enabled) });
            } catch (java.lang.IllegalAccessException e2) {
                android.util.Log.e(android.support.v4.view.ViewCompat.TAG, "Unable to invoke childrenDrawingOrderEnabled", e2);
            } catch (java.lang.IllegalArgumentException e3) {
                android.util.Log.e(android.support.v4.view.ViewCompat.TAG, "Unable to invoke childrenDrawingOrderEnabled", e3);
            } catch (java.lang.reflect.InvocationTargetException e4) {
                android.util.Log.e(android.support.v4.view.ViewCompat.TAG, "Unable to invoke childrenDrawingOrderEnabled", e4);
            }
        }

        public boolean getFitsSystemWindows(android.view.View view) {
            return false;
        }

        public void setOnApplyWindowInsetsListener(android.view.View view, android.view.View.OnApplyWindowInsetsListener listener) {
        }

        public android.support.v4.view.WindowInsetsCompat onApplyWindowInsets(android.view.View v, android.support.v4.view.WindowInsetsCompat insets) {
            return insets;
        }

        public android.support.v4.view.WindowInsetsCompat dispatchApplyWindowInsets(android.view.View v, android.support.v4.view.WindowInsetsCompat insets) {
            return insets;
        }

        public boolean isPaddingRelative(android.view.View view) {
            return false;
        }

        public void setNestedScrollingEnabled(android.view.View view, boolean enabled) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                ((android.support.v4.view.NestedScrollingChild) (view)).setNestedScrollingEnabled(enabled);
            }
        }

        public boolean isNestedScrollingEnabled(android.view.View view) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).isNestedScrollingEnabled();
            }
            return false;
        }

        public void setBackground(android.view.View view, android.graphics.drawable.Drawable background) {
            view.setBackgroundDrawable(background);
        }

        public android.content.res.ColorStateList getBackgroundTintList(android.view.View view) {
            return view instanceof android.support.v4.view.TintableBackgroundView ? ((android.support.v4.view.TintableBackgroundView) (view)).getSupportBackgroundTintList() : null;
        }

        public void setBackgroundTintList(android.view.View view, android.content.res.ColorStateList tintList) {
            if (view instanceof android.support.v4.view.TintableBackgroundView) {
                ((android.support.v4.view.TintableBackgroundView) (view)).setSupportBackgroundTintList(tintList);
            }
        }

        public void setBackgroundTintMode(android.view.View view, android.graphics.PorterDuff.Mode mode) {
            if (view instanceof android.support.v4.view.TintableBackgroundView) {
                ((android.support.v4.view.TintableBackgroundView) (view)).setSupportBackgroundTintMode(mode);
            }
        }

        public android.graphics.PorterDuff.Mode getBackgroundTintMode(android.view.View view) {
            return view instanceof android.support.v4.view.TintableBackgroundView ? ((android.support.v4.view.TintableBackgroundView) (view)).getSupportBackgroundTintMode() : null;
        }

        public boolean startNestedScroll(android.view.View view, int axes) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).startNestedScroll(axes);
            }
            return false;
        }

        public void stopNestedScroll(android.view.View view) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                ((android.support.v4.view.NestedScrollingChild) (view)).stopNestedScroll();
            }
        }

        public boolean hasNestedScrollingParent(android.view.View view) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).hasNestedScrollingParent();
            }
            return false;
        }

        public boolean dispatchNestedScroll(android.view.View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
            }
            return false;
        }

        public boolean dispatchNestedPreScroll(android.view.View view, int dx, int dy, int[] consumed, int[] offsetInWindow) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
            }
            return false;
        }

        public boolean dispatchNestedFling(android.view.View view, float velocityX, float velocityY, boolean consumed) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).dispatchNestedFling(velocityX, velocityY, consumed);
            }
            return false;
        }

        public boolean dispatchNestedPreFling(android.view.View view, float velocityX, float velocityY) {
            if (view instanceof android.support.v4.view.NestedScrollingChild) {
                return ((android.support.v4.view.NestedScrollingChild) (view)).dispatchNestedPreFling(velocityX, velocityY);
            }
            return false;
        }

        public boolean isInLayout(android.view.View view) {
            return false;
        }

        public boolean isLaidOut(android.view.View view) {
            return (view.getWidth() > 0) && (view.getHeight() > 0);
        }

        public boolean isLayoutDirectionResolved(android.view.View view) {
            return false;
        }

        public float getZ(android.view.View view) {
            return getTranslationZ(view) + getElevation(view);
        }

        public void setZ(android.view.View view, float z) {
        }

        public boolean isAttachedToWindow(android.view.View view) {
            return view.getWindowToken() != null;
        }

        public boolean hasOnClickListeners(android.view.View view) {
            return false;
        }

        public int getScrollIndicators(android.view.View view) {
            return 0;
        }

        public void setScrollIndicators(android.view.View view, int indicators) {
        }

        public void setScrollIndicators(android.view.View view, int indicators, int mask) {
        }

        public void offsetLeftAndRight(android.view.View view, int offset) {
            view.offsetLeftAndRight(offset);
            if (view.getVisibility() == 0) {
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(view);
                android.view.ViewParent parent = view.getParent();
                if (parent instanceof android.view.View) {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(((android.view.View) (parent)));
                }
            }
        }

        public void offsetTopAndBottom(android.view.View view, int offset) {
            view.offsetTopAndBottom(offset);
            if (view.getVisibility() == 0) {
                android.support.v4.view.ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(view);
                android.view.ViewParent parent = view.getParent();
                if (parent instanceof android.view.View) {
                    android.support.v4.view.ViewCompat.ViewCompatBaseImpl.tickleInvalidationFlag(((android.view.View) (parent)));
                }
            }
        }

        private static void tickleInvalidationFlag(android.view.View view) {
            float y = view.getTranslationY();
            view.setTranslationY(1.0F + y);
            view.setTranslationY(y);
        }

        public void setPointerIcon(android.view.View view, android.support.v4.view.PointerIconCompat pointerIcon) {
        }

        public android.view.Display getDisplay(android.view.View view) {
            if (isAttachedToWindow(view)) {
                return ((android.view.WindowManager) (view.getContext().getSystemService("window"))).getDefaultDisplay();
            }
            return null;
        }

        public void setTooltipText(android.view.View view, java.lang.CharSequence tooltipText) {
            android.support.v4.view.ViewCompatICS.setTooltipText(view, tooltipText);
        }
    }

    @android.annotation.TargetApi(15)
    static class ViewCompatApi15Impl extends android.support.v4.view.ViewCompat.ViewCompatBaseImpl {
        ViewCompatApi15Impl() {
        }

        public boolean hasOnClickListeners(android.view.View view) {
            return view.hasOnClickListeners();
        }
    }

    @android.annotation.TargetApi(16)
    static class ViewCompatApi16Impl extends android.support.v4.view.ViewCompat.ViewCompatApi15Impl {
        ViewCompatApi16Impl() {
        }

        public boolean hasTransientState(android.view.View view) {
            return view.hasTransientState();
        }

        public void setHasTransientState(android.view.View view, boolean hasTransientState) {
            view.setHasTransientState(hasTransientState);
        }

        public void postInvalidateOnAnimation(android.view.View view) {
            view.postInvalidateOnAnimation();
        }

        public void postInvalidateOnAnimation(android.view.View view, int left, int top, int right, int bottom) {
            view.postInvalidateOnAnimation(left, top, right, bottom);
        }

        public void postOnAnimation(android.view.View view, java.lang.Runnable action) {
            view.postOnAnimation(action);
        }

        public void postOnAnimationDelayed(android.view.View view, java.lang.Runnable action, long delayMillis) {
            view.postOnAnimationDelayed(action, delayMillis);
        }

        public int getImportantForAccessibility(android.view.View view) {
            return view.getImportantForAccessibility();
        }

        public void setImportantForAccessibility(android.view.View view, int mode) {
            if (mode == 4) {
                mode = 2;
            }
            view.setImportantForAccessibility(mode);
        }

        public boolean performAccessibilityAction(android.view.View view, int action, android.os.Bundle arguments) {
            return view.performAccessibilityAction(action, arguments);
        }

        public android.support.v4.view.accessibility.AccessibilityNodeProviderCompat getAccessibilityNodeProvider(android.view.View view) {
            android.view.accessibility.AccessibilityNodeProvider provider = view.getAccessibilityNodeProvider();
            if (provider != null) {
                return new android.support.v4.view.accessibility.AccessibilityNodeProviderCompat(provider);
            }
            return null;
        }

        public android.view.ViewParent getParentForAccessibility(android.view.View view) {
            return view.getParentForAccessibility();
        }

        public int getMinimumWidth(android.view.View view) {
            return view.getMinimumWidth();
        }

        public int getMinimumHeight(android.view.View view) {
            return view.getMinimumHeight();
        }

        public void requestApplyInsets(android.view.View view) {
            view.requestFitSystemWindows();
        }

        public boolean getFitsSystemWindows(android.view.View view) {
            return view.getFitsSystemWindows();
        }

        public boolean hasOverlappingRendering(android.view.View view) {
            return view.hasOverlappingRendering();
        }

        public void setBackground(android.view.View view, android.graphics.drawable.Drawable background) {
            view.setBackground(background);
        }
    }

    @android.annotation.TargetApi(17)
    static class ViewCompatApi17Impl extends android.support.v4.view.ViewCompat.ViewCompatApi16Impl {
        ViewCompatApi17Impl() {
        }

        public int getLabelFor(android.view.View view) {
            return view.getLabelFor();
        }

        public void setLabelFor(android.view.View view, int id) {
            view.setLabelFor(id);
        }

        public void setLayerPaint(android.view.View view, android.graphics.Paint paint) {
            view.setLayerPaint(paint);
        }

        public int getLayoutDirection(android.view.View view) {
            return view.getLayoutDirection();
        }

        public void setLayoutDirection(android.view.View view, int layoutDirection) {
            view.setLayoutDirection(layoutDirection);
        }

        public int getPaddingStart(android.view.View view) {
            return view.getPaddingStart();
        }

        public int getPaddingEnd(android.view.View view) {
            return view.getPaddingEnd();
        }

        public void setPaddingRelative(android.view.View view, int start, int top, int end, int bottom) {
            view.setPaddingRelative(start, top, end, bottom);
        }

        public int getWindowSystemUiVisibility(android.view.View view) {
            return view.getWindowSystemUiVisibility();
        }

        public boolean isPaddingRelative(android.view.View view) {
            return view.isPaddingRelative();
        }

        public android.view.Display getDisplay(android.view.View view) {
            return view.getDisplay();
        }
    }

    @android.annotation.TargetApi(18)
    static class ViewCompatApi18Impl extends android.support.v4.view.ViewCompat.ViewCompatApi17Impl {
        ViewCompatApi18Impl() {
        }

        public void setClipBounds(android.view.View view, android.graphics.Rect clipBounds) {
            view.setClipBounds(clipBounds);
        }

        public android.graphics.Rect getClipBounds(android.view.View view) {
            return view.getClipBounds();
        }

        public boolean isInLayout(android.view.View view) {
            return view.isInLayout();
        }
    }

    @android.annotation.TargetApi(19)
    static class ViewCompatApi19Impl extends android.support.v4.view.ViewCompat.ViewCompatApi18Impl {
        ViewCompatApi19Impl() {
        }

        public int getAccessibilityLiveRegion(android.view.View view) {
            return view.getAccessibilityLiveRegion();
        }

        public void setAccessibilityLiveRegion(android.view.View view, int mode) {
            view.setAccessibilityLiveRegion(mode);
        }

        public void setImportantForAccessibility(android.view.View view, int mode) {
            view.setImportantForAccessibility(mode);
        }

        public boolean isLaidOut(android.view.View view) {
            return view.isLaidOut();
        }

        public boolean isLayoutDirectionResolved(android.view.View view) {
            return view.isLayoutDirectionResolved();
        }

        public boolean isAttachedToWindow(android.view.View view) {
            return view.isAttachedToWindow();
        }
    }

    @android.annotation.TargetApi(21)
    static class ViewCompatApi21Impl extends android.support.v4.view.ViewCompat.ViewCompatApi19Impl {
        private static java.lang.ThreadLocal<android.graphics.Rect> sThreadLocalRect;

        ViewCompatApi21Impl() {
        }

        public void setTransitionName(android.view.View view, java.lang.String transitionName) {
            view.setTransitionName(transitionName);
        }

        public java.lang.String getTransitionName(android.view.View view) {
            return view.getTransitionName();
        }

        public void requestApplyInsets(android.view.View view) {
            view.requestApplyInsets();
        }

        public void setElevation(android.view.View view, float elevation) {
            view.setElevation(elevation);
        }

        public float getElevation(android.view.View view) {
            return view.getElevation();
        }

        public void setTranslationZ(android.view.View view, float translationZ) {
            view.setTranslationZ(translationZ);
        }

        public float getTranslationZ(android.view.View view) {
            return view.getTranslationZ();
        }

        public void setOnApplyWindowInsetsListener(android.view.View view, final android.view.View.OnApplyWindowInsetsListener listener) {
            if (listener == null) {
                view.setOnApplyWindowInsetsListener(null);
            } else {
                view.setOnApplyWindowInsetsListener(new android.view.View.OnApplyWindowInsetsListener() {
                    public android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets insets) {
                        return ((android.view.WindowInsets) (android.support.v4.view.WindowInsetsCompat.unwrap(listener.onApplyWindowInsets(view, android.support.v4.view.WindowInsetsCompat.wrap(insets)))));
                    }
                });
            }
        }

        public void setNestedScrollingEnabled(android.view.View view, boolean enabled) {
            view.setNestedScrollingEnabled(enabled);
        }

        public boolean isNestedScrollingEnabled(android.view.View view) {
            return view.isNestedScrollingEnabled();
        }

        public boolean startNestedScroll(android.view.View view, int axes) {
            return view.startNestedScroll(axes);
        }

        public void stopNestedScroll(android.view.View view) {
            view.stopNestedScroll();
        }

        public boolean hasNestedScrollingParent(android.view.View view) {
            return view.hasNestedScrollingParent();
        }

        public boolean dispatchNestedScroll(android.view.View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
            return view.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
        }

        public boolean dispatchNestedPreScroll(android.view.View view, int dx, int dy, int[] consumed, int[] offsetInWindow) {
            return view.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
        }

        public boolean dispatchNestedFling(android.view.View view, float velocityX, float velocityY, boolean consumed) {
            return view.dispatchNestedFling(velocityX, velocityY, consumed);
        }

        public boolean dispatchNestedPreFling(android.view.View view, float velocityX, float velocityY) {
            return view.dispatchNestedPreFling(velocityX, velocityY);
        }

        public boolean isImportantForAccessibility(android.view.View view) {
            return view.isImportantForAccessibility();
        }

        public android.content.res.ColorStateList getBackgroundTintList(android.view.View view) {
            return view.getBackgroundTintList();
        }

        public void setBackgroundTintList(android.view.View view, android.content.res.ColorStateList tintList) {
            view.setBackgroundTintList(tintList);
            if (android.os.Build.VERSION.SDK_INT == 21) {
                android.graphics.drawable.Drawable background = view.getBackground();
                boolean hasTint = ((view.getBackgroundTintList() == null) || (view.getBackgroundTintMode() == null)) ? false : true;
                if ((background != null) && hasTint) {
                    if (background.isStateful()) {
                        background.setState(view.getDrawableState());
                    }
                    view.setBackground(background);
                }
            }
        }

        public void setBackgroundTintMode(android.view.View view, android.graphics.PorterDuff.Mode mode) {
            view.setBackgroundTintMode(mode);
            if (android.os.Build.VERSION.SDK_INT == 21) {
                android.graphics.drawable.Drawable background = view.getBackground();
                boolean hasTint = ((view.getBackgroundTintList() == null) || (view.getBackgroundTintMode() == null)) ? false : true;
                if ((background != null) && hasTint) {
                    if (background.isStateful()) {
                        background.setState(view.getDrawableState());
                    }
                    view.setBackground(background);
                }
            }
        }

        public android.graphics.PorterDuff.Mode getBackgroundTintMode(android.view.View view) {
            return view.getBackgroundTintMode();
        }

        public android.support.v4.view.WindowInsetsCompat onApplyWindowInsets(android.view.View v, android.support.v4.view.WindowInsetsCompat insets) {
            android.view.WindowInsets unwrapped = ((android.view.WindowInsets) (android.support.v4.view.WindowInsetsCompat.unwrap(insets)));
            android.view.WindowInsets result = v.onApplyWindowInsets(unwrapped);
            if (result != unwrapped) {
                unwrapped = new android.view.WindowInsets(result);
            }
            return android.support.v4.view.WindowInsetsCompat.wrap(unwrapped);
        }

        public android.support.v4.view.WindowInsetsCompat dispatchApplyWindowInsets(android.view.View v, android.support.v4.view.WindowInsetsCompat insets) {
            android.view.WindowInsets unwrapped = ((android.view.WindowInsets) (android.support.v4.view.WindowInsetsCompat.unwrap(insets)));
            android.view.WindowInsets result = v.dispatchApplyWindowInsets(unwrapped);
            if (result != unwrapped) {
                unwrapped = new android.view.WindowInsets(result);
            }
            return android.support.v4.view.WindowInsetsCompat.wrap(unwrapped);
        }

        public float getZ(android.view.View view) {
            return view.getZ();
        }

        public void setZ(android.view.View view, float z) {
            view.setZ(z);
        }

        public void offsetLeftAndRight(android.view.View view, int offset) {
            android.graphics.Rect parentRect = android.support.v4.view.ViewCompat.ViewCompatApi21Impl.getEmptyTempRect();
            boolean needInvalidateWorkaround = false;
            android.view.ViewParent parent = view.getParent();
            if (parent instanceof android.view.View) {
                android.view.View p = ((android.view.View) (parent));
                parentRect.set(p.getLeft(), p.getTop(), p.getRight(), p.getBottom());
                needInvalidateWorkaround = !parentRect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
            super.offsetLeftAndRight(view, offset);
            if (needInvalidateWorkaround && parentRect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                ((android.view.View) (parent)).invalidate(parentRect);
            }
        }

        public void offsetTopAndBottom(android.view.View view, int offset) {
            android.graphics.Rect parentRect = android.support.v4.view.ViewCompat.ViewCompatApi21Impl.getEmptyTempRect();
            boolean needInvalidateWorkaround = false;
            android.view.ViewParent parent = view.getParent();
            if (parent instanceof android.view.View) {
                android.view.View p = ((android.view.View) (parent));
                parentRect.set(p.getLeft(), p.getTop(), p.getRight(), p.getBottom());
                needInvalidateWorkaround = !parentRect.intersects(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
            super.offsetTopAndBottom(view, offset);
            if (needInvalidateWorkaround && parentRect.intersect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom())) {
                ((android.view.View) (parent)).invalidate(parentRect);
            }
        }

        private static android.graphics.Rect getEmptyTempRect() {
            if (android.support.v4.view.ViewCompat.ViewCompatApi21Impl.sThreadLocalRect == null) {
                android.support.v4.view.ViewCompat.ViewCompatApi21Impl.sThreadLocalRect = new java.lang.ThreadLocal();
            }
            android.graphics.Rect rect = ((android.graphics.Rect) (android.support.v4.view.ViewCompat.ViewCompatApi21Impl.sThreadLocalRect.get()));
            if (rect == null) {
                rect = new android.graphics.Rect();
                android.support.v4.view.ViewCompat.ViewCompatApi21Impl.sThreadLocalRect.set(rect);
            }
            rect.setEmpty();
            return rect;
        }
    }

    @android.annotation.TargetApi(23)
    static class ViewCompatApi23Impl extends android.support.v4.view.ViewCompat.ViewCompatApi21Impl {
        ViewCompatApi23Impl() {
        }

        public void setScrollIndicators(android.view.View view, int indicators) {
            view.setScrollIndicators(indicators);
        }

        public void setScrollIndicators(android.view.View view, int indicators, int mask) {
            view.setScrollIndicators(indicators, mask);
        }

        public int getScrollIndicators(android.view.View view) {
            return view.getScrollIndicators();
        }

        public void offsetLeftAndRight(android.view.View view, int offset) {
            view.offsetLeftAndRight(offset);
        }

        public void offsetTopAndBottom(android.view.View view, int offset) {
            view.offsetTopAndBottom(offset);
        }
    }

    @android.annotation.TargetApi(24)
    static class ViewCompatApi24Impl extends android.support.v4.view.ViewCompat.ViewCompatApi23Impl {
        ViewCompatApi24Impl() {
        }

        public void dispatchStartTemporaryDetach(android.view.View view) {
            view.dispatchStartTemporaryDetach();
        }

        public void dispatchFinishTemporaryDetach(android.view.View view) {
            view.dispatchFinishTemporaryDetach();
        }

        public void setPointerIcon(android.view.View view, android.support.v4.view.PointerIconCompat pointerIconCompat) {
            view.setPointerIcon(((android.view.PointerIcon) (pointerIconCompat != null ? pointerIconCompat.getPointerIcon() : null)));
        }

        public boolean startDragAndDrop(android.view.View view, android.content.ClipData data, android.view.View.DragShadowBuilder shadowBuilder, java.lang.Object localState, int flags) {
            return view.startDragAndDrop(data, shadowBuilder, localState, flags);
        }

        public void cancelDragAndDrop(android.view.View view) {
            view.cancelDragAndDrop();
        }

        public void updateDragShadow(android.view.View view, android.view.View.DragShadowBuilder shadowBuilder) {
            view.updateDragShadow(shadowBuilder);
        }
    }

    @android.annotation.TargetApi(26)
    static class ViewCompatApi26Impl extends android.support.v4.view.ViewCompat.ViewCompatApi24Impl {
        ViewCompatApi26Impl() {
        }

        public void setTooltipText(android.view.View view, java.lang.CharSequence tooltipText) {
            view.setTooltipText(tooltipText);
        }
    }

    static {
        int version = android.os.Build.VERSION.SDK_INT;
        if (android.support.v4.os.BuildCompat.isAtLeastO()) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi26Impl();
        } else if (version >= 24) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi24Impl();
        } else if (version >= 23) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi23Impl();
        } else if (version >= 21) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi21Impl();
        } else if (version >= 19) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi19Impl();
        } else if (version >= 18) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi18Impl();
        } else if (version >= 17) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi17Impl();
        } else if (version >= 16) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi16Impl();
        } else if (version >= 15) {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatApi15Impl();
        } else {
            IMPL = new android.support.v4.view.ViewCompat.ViewCompatBaseImpl();
        }
    }

    @java.lang.Deprecated
    public static boolean canScrollHorizontally(android.view.View view, int direction) {
        return view.canScrollHorizontally(direction);
    }

    @java.lang.Deprecated
    public static boolean canScrollVertically(android.view.View view, int direction) {
        return view.canScrollVertically(direction);
    }

    @java.lang.Deprecated
    public static int getOverScrollMode(android.view.View v) {
        return v.getOverScrollMode();
    }

    @java.lang.Deprecated
    public static void setOverScrollMode(android.view.View v, int overScrollMode) {
        v.setOverScrollMode(overScrollMode);
    }

    @java.lang.Deprecated
    public static void onPopulateAccessibilityEvent(android.view.View v, android.view.accessibility.AccessibilityEvent event) {
        v.onPopulateAccessibilityEvent(event);
    }

    @java.lang.Deprecated
    public static void onInitializeAccessibilityEvent(android.view.View v, android.view.accessibility.AccessibilityEvent event) {
        v.onInitializeAccessibilityEvent(event);
    }

    public static void onInitializeAccessibilityNodeInfo(android.view.View v, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat info) {
        android.support.v4.view.ViewCompat.IMPL.onInitializeAccessibilityNodeInfo(v, info);
    }

    public static void setAccessibilityDelegate(android.view.View v, android.support.v4.view.AccessibilityDelegateCompat delegate) {
        android.support.v4.view.ViewCompat.IMPL.setAccessibilityDelegate(v, delegate);
    }

    public static boolean hasAccessibilityDelegate(android.view.View v) {
        return android.support.v4.view.ViewCompat.IMPL.hasAccessibilityDelegate(v);
    }

    public static boolean hasTransientState(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.hasTransientState(view);
    }

    public static void setHasTransientState(android.view.View view, boolean hasTransientState) {
        android.support.v4.view.ViewCompat.IMPL.setHasTransientState(view, hasTransientState);
    }

    public static void postInvalidateOnAnimation(android.view.View view) {
        android.support.v4.view.ViewCompat.IMPL.postInvalidateOnAnimation(view);
    }

    public static void postInvalidateOnAnimation(android.view.View view, int left, int top, int right, int bottom) {
        android.support.v4.view.ViewCompat.IMPL.postInvalidateOnAnimation(view, left, top, right, bottom);
    }

    public static void postOnAnimation(android.view.View view, java.lang.Runnable action) {
        android.support.v4.view.ViewCompat.IMPL.postOnAnimation(view, action);
    }

    public static void postOnAnimationDelayed(android.view.View view, java.lang.Runnable action, long delayMillis) {
        android.support.v4.view.ViewCompat.IMPL.postOnAnimationDelayed(view, action, delayMillis);
    }

    public static int getImportantForAccessibility(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getImportantForAccessibility(view);
    }

    public static void setImportantForAccessibility(android.view.View view, int mode) {
        android.support.v4.view.ViewCompat.IMPL.setImportantForAccessibility(view, mode);
    }

    public static boolean isImportantForAccessibility(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isImportantForAccessibility(view);
    }

    public static boolean performAccessibilityAction(android.view.View view, int action, android.os.Bundle arguments) {
        return android.support.v4.view.ViewCompat.IMPL.performAccessibilityAction(view, action, arguments);
    }

    public static android.support.v4.view.accessibility.AccessibilityNodeProviderCompat getAccessibilityNodeProvider(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getAccessibilityNodeProvider(view);
    }

    @java.lang.Deprecated
    public static float getAlpha(android.view.View view) {
        return view.getAlpha();
    }

    @java.lang.Deprecated
    public static void setLayerType(android.view.View view, int layerType, android.graphics.Paint paint) {
        view.setLayerType(layerType, paint);
    }

    @java.lang.Deprecated
    public static int getLayerType(android.view.View view) {
        return view.getLayerType();
    }

    public static int getLabelFor(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getLabelFor(view);
    }

    public static void setLabelFor(android.view.View view, @android.support.annotation.IdRes
    int labeledId) {
        android.support.v4.view.ViewCompat.IMPL.setLabelFor(view, labeledId);
    }

    public static void setLayerPaint(android.view.View view, android.graphics.Paint paint) {
        android.support.v4.view.ViewCompat.IMPL.setLayerPaint(view, paint);
    }

    public static int getLayoutDirection(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getLayoutDirection(view);
    }

    public static void setLayoutDirection(android.view.View view, int layoutDirection) {
        android.support.v4.view.ViewCompat.IMPL.setLayoutDirection(view, layoutDirection);
    }

    public static android.view.ViewParent getParentForAccessibility(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getParentForAccessibility(view);
    }

    @java.lang.Deprecated
    public static boolean isOpaque(android.view.View view) {
        return view.isOpaque();
    }

    @java.lang.Deprecated
    public static int resolveSizeAndState(int size, int measureSpec, int childMeasuredState) {
        return android.view.View.resolveSizeAndState(size, measureSpec, childMeasuredState);
    }

    @java.lang.Deprecated
    public static int getMeasuredWidthAndState(android.view.View view) {
        return view.getMeasuredWidthAndState();
    }

    @java.lang.Deprecated
    public static int getMeasuredHeightAndState(android.view.View view) {
        return view.getMeasuredHeightAndState();
    }

    @java.lang.Deprecated
    public static int getMeasuredState(android.view.View view) {
        return view.getMeasuredState();
    }

    @java.lang.Deprecated
    public static int combineMeasuredStates(int curState, int newState) {
        return android.view.View.combineMeasuredStates(curState, newState);
    }

    public static int getAccessibilityLiveRegion(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getAccessibilityLiveRegion(view);
    }

    public static void setAccessibilityLiveRegion(android.view.View view, int mode) {
        android.support.v4.view.ViewCompat.IMPL.setAccessibilityLiveRegion(view, mode);
    }

    public static int getPaddingStart(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getPaddingStart(view);
    }

    public static int getPaddingEnd(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getPaddingEnd(view);
    }

    public static void setPaddingRelative(android.view.View view, int start, int top, int end, int bottom) {
        android.support.v4.view.ViewCompat.IMPL.setPaddingRelative(view, start, top, end, bottom);
    }

    public static void dispatchStartTemporaryDetach(android.view.View view) {
        android.support.v4.view.ViewCompat.IMPL.dispatchStartTemporaryDetach(view);
    }

    public static void dispatchFinishTemporaryDetach(android.view.View view) {
        android.support.v4.view.ViewCompat.IMPL.dispatchFinishTemporaryDetach(view);
    }

    @java.lang.Deprecated
    public static float getTranslationX(android.view.View view) {
        return view.getTranslationX();
    }

    @java.lang.Deprecated
    public static float getTranslationY(android.view.View view) {
        return view.getTranslationY();
    }

    @android.support.annotation.Nullable
    @java.lang.Deprecated
    public static android.graphics.Matrix getMatrix(android.view.View view) {
        return view.getMatrix();
    }

    public static int getMinimumWidth(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getMinimumWidth(view);
    }

    public static int getMinimumHeight(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getMinimumHeight(view);
    }

    public static android.support.v4.view.ViewPropertyAnimatorCompat animate(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.animate(view);
    }

    @java.lang.Deprecated
    public static void setTranslationX(android.view.View view, float value) {
        view.setTranslationX(value);
    }

    @java.lang.Deprecated
    public static void setTranslationY(android.view.View view, float value) {
        view.setTranslationY(value);
    }

    @java.lang.Deprecated
    public static void setAlpha(android.view.View view, @android.support.annotation.FloatRange(from = 0.0, to = 1.0)
    float value) {
        view.setAlpha(value);
    }

    @java.lang.Deprecated
    public static void setX(android.view.View view, float value) {
        view.setX(value);
    }

    @java.lang.Deprecated
    public static void setY(android.view.View view, float value) {
        view.setY(value);
    }

    @java.lang.Deprecated
    public static void setRotation(android.view.View view, float value) {
        view.setRotation(value);
    }

    @java.lang.Deprecated
    public static void setRotationX(android.view.View view, float value) {
        view.setRotationX(value);
    }

    @java.lang.Deprecated
    public static void setRotationY(android.view.View view, float value) {
        view.setRotationY(value);
    }

    @java.lang.Deprecated
    public static void setScaleX(android.view.View view, float value) {
        view.setScaleX(value);
    }

    @java.lang.Deprecated
    public static void setScaleY(android.view.View view, float value) {
        view.setScaleY(value);
    }

    @java.lang.Deprecated
    public static float getPivotX(android.view.View view) {
        return view.getPivotX();
    }

    @java.lang.Deprecated
    public static void setPivotX(android.view.View view, float value) {
        view.setPivotX(value);
    }

    @java.lang.Deprecated
    public static float getPivotY(android.view.View view) {
        return view.getPivotY();
    }

    @java.lang.Deprecated
    public static void setPivotY(android.view.View view, float value) {
        view.setPivotY(value);
    }

    @java.lang.Deprecated
    public static float getRotation(android.view.View view) {
        return view.getRotation();
    }

    @java.lang.Deprecated
    public static float getRotationX(android.view.View view) {
        return view.getRotationX();
    }

    @java.lang.Deprecated
    public static float getRotationY(android.view.View view) {
        return view.getRotationY();
    }

    @java.lang.Deprecated
    public static float getScaleX(android.view.View view) {
        return view.getScaleX();
    }

    @java.lang.Deprecated
    public static float getScaleY(android.view.View view) {
        return view.getScaleY();
    }

    @java.lang.Deprecated
    public static float getX(android.view.View view) {
        return view.getX();
    }

    @java.lang.Deprecated
    public static float getY(android.view.View view) {
        return view.getY();
    }

    public static void setElevation(android.view.View view, float elevation) {
        android.support.v4.view.ViewCompat.IMPL.setElevation(view, elevation);
    }

    public static float getElevation(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getElevation(view);
    }

    public static void setTranslationZ(android.view.View view, float translationZ) {
        android.support.v4.view.ViewCompat.IMPL.setTranslationZ(view, translationZ);
    }

    public static float getTranslationZ(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getTranslationZ(view);
    }

    public static void setTransitionName(android.view.View view, java.lang.String transitionName) {
        android.support.v4.view.ViewCompat.IMPL.setTransitionName(view, transitionName);
    }

    public static java.lang.String getTransitionName(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getTransitionName(view);
    }

    public static int getWindowSystemUiVisibility(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getWindowSystemUiVisibility(view);
    }

    public static void requestApplyInsets(android.view.View view) {
        android.support.v4.view.ViewCompat.IMPL.requestApplyInsets(view);
    }

    public static void setChildrenDrawingOrderEnabled(android.view.ViewGroup viewGroup, boolean enabled) {
        android.support.v4.view.ViewCompat.IMPL.setChildrenDrawingOrderEnabled(viewGroup, enabled);
    }

    public static boolean getFitsSystemWindows(android.view.View v) {
        return android.support.v4.view.ViewCompat.IMPL.getFitsSystemWindows(v);
    }

    @java.lang.Deprecated
    public static void setFitsSystemWindows(android.view.View view, boolean fitSystemWindows) {
        view.setFitsSystemWindows(fitSystemWindows);
    }

    @java.lang.Deprecated
    public static void jumpDrawablesToCurrentState(android.view.View v) {
        v.jumpDrawablesToCurrentState();
    }

    public static void setOnApplyWindowInsetsListener(android.view.View v, android.view.View.OnApplyWindowInsetsListener listener) {
        android.support.v4.view.ViewCompat.IMPL.setOnApplyWindowInsetsListener(v, listener);
    }

    public static android.support.v4.view.WindowInsetsCompat onApplyWindowInsets(android.view.View view, android.support.v4.view.WindowInsetsCompat insets) {
        return android.support.v4.view.ViewCompat.IMPL.onApplyWindowInsets(view, insets);
    }

    public static android.support.v4.view.WindowInsetsCompat dispatchApplyWindowInsets(android.view.View view, android.support.v4.view.WindowInsetsCompat insets) {
        return android.support.v4.view.ViewCompat.IMPL.dispatchApplyWindowInsets(view, insets);
    }

    @java.lang.Deprecated
    public static void setSaveFromParentEnabled(android.view.View v, boolean enabled) {
        v.setSaveFromParentEnabled(enabled);
    }

    @java.lang.Deprecated
    public static void setActivated(android.view.View view, boolean activated) {
        view.setActivated(activated);
    }

    public static boolean hasOverlappingRendering(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.hasOverlappingRendering(view);
    }

    public static boolean isPaddingRelative(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isPaddingRelative(view);
    }

    public static void setBackground(android.view.View view, android.graphics.drawable.Drawable background) {
        android.support.v4.view.ViewCompat.IMPL.setBackground(view, background);
    }

    public static android.content.res.ColorStateList getBackgroundTintList(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getBackgroundTintList(view);
    }

    public static void setBackgroundTintList(android.view.View view, android.content.res.ColorStateList tintList) {
        android.support.v4.view.ViewCompat.IMPL.setBackgroundTintList(view, tintList);
    }

    public static android.graphics.PorterDuff.Mode getBackgroundTintMode(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getBackgroundTintMode(view);
    }

    public static void setBackgroundTintMode(android.view.View view, android.graphics.PorterDuff.Mode mode) {
        android.support.v4.view.ViewCompat.IMPL.setBackgroundTintMode(view, mode);
    }

    public static void setNestedScrollingEnabled(android.view.View view, boolean enabled) {
        android.support.v4.view.ViewCompat.IMPL.setNestedScrollingEnabled(view, enabled);
    }

    public static boolean isNestedScrollingEnabled(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isNestedScrollingEnabled(view);
    }

    public static boolean startNestedScroll(android.view.View view, int axes) {
        return android.support.v4.view.ViewCompat.IMPL.startNestedScroll(view, axes);
    }

    public static void stopNestedScroll(android.view.View view) {
        android.support.v4.view.ViewCompat.IMPL.stopNestedScroll(view);
    }

    public static boolean hasNestedScrollingParent(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.hasNestedScrollingParent(view);
    }

    public static boolean dispatchNestedScroll(android.view.View view, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return android.support.v4.view.ViewCompat.IMPL.dispatchNestedScroll(view, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    public static boolean dispatchNestedPreScroll(android.view.View view, int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return android.support.v4.view.ViewCompat.IMPL.dispatchNestedPreScroll(view, dx, dy, consumed, offsetInWindow);
    }

    public static boolean dispatchNestedFling(android.view.View view, float velocityX, float velocityY, boolean consumed) {
        return android.support.v4.view.ViewCompat.IMPL.dispatchNestedFling(view, velocityX, velocityY, consumed);
    }

    public static boolean dispatchNestedPreFling(android.view.View view, float velocityX, float velocityY) {
        return android.support.v4.view.ViewCompat.IMPL.dispatchNestedPreFling(view, velocityX, velocityY);
    }

    public static boolean isInLayout(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isInLayout(view);
    }

    public static boolean isLaidOut(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isLaidOut(view);
    }

    public static boolean isLayoutDirectionResolved(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isLayoutDirectionResolved(view);
    }

    public static float getZ(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getZ(view);
    }

    public static void setZ(android.view.View view, float z) {
        android.support.v4.view.ViewCompat.IMPL.setZ(view, z);
    }

    public static void offsetTopAndBottom(android.view.View view, int offset) {
        android.support.v4.view.ViewCompat.IMPL.offsetTopAndBottom(view, offset);
    }

    public static void offsetLeftAndRight(android.view.View view, int offset) {
        android.support.v4.view.ViewCompat.IMPL.offsetLeftAndRight(view, offset);
    }

    public static void setClipBounds(android.view.View view, android.graphics.Rect clipBounds) {
        android.support.v4.view.ViewCompat.IMPL.setClipBounds(view, clipBounds);
    }

    public static android.graphics.Rect getClipBounds(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getClipBounds(view);
    }

    public static boolean isAttachedToWindow(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.isAttachedToWindow(view);
    }

    public static boolean hasOnClickListeners(android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.hasOnClickListeners(view);
    }

    public static void setScrollIndicators(@android.support.annotation.NonNull
    android.view.View view, int indicators) {
        android.support.v4.view.ViewCompat.IMPL.setScrollIndicators(view, indicators);
    }

    public static void setScrollIndicators(@android.support.annotation.NonNull
    android.view.View view, int indicators, int mask) {
        android.support.v4.view.ViewCompat.IMPL.setScrollIndicators(view, indicators, mask);
    }

    public static int getScrollIndicators(@android.support.annotation.NonNull
    android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getScrollIndicators(view);
    }

    public static void setPointerIcon(@android.support.annotation.NonNull
    android.view.View view, android.support.v4.view.PointerIconCompat pointerIcon) {
        android.support.v4.view.ViewCompat.IMPL.setPointerIcon(view, pointerIcon);
    }

    public static android.view.Display getDisplay(@android.support.annotation.NonNull
    android.view.View view) {
        return android.support.v4.view.ViewCompat.IMPL.getDisplay(view);
    }

    public static void setTooltipText(@android.support.annotation.NonNull
    android.view.View view, @android.support.annotation.Nullable
    java.lang.CharSequence tooltipText) {
        android.support.v4.view.ViewCompat.IMPL.setTooltipText(view, tooltipText);
    }

    public static boolean startDragAndDrop(android.view.View v, android.content.ClipData data, android.view.View.DragShadowBuilder shadowBuilder, java.lang.Object localState, int flags) {
        return android.support.v4.view.ViewCompat.IMPL.startDragAndDrop(v, data, shadowBuilder, localState, flags);
    }

    public static void cancelDragAndDrop(android.view.View v) {
        android.support.v4.view.ViewCompat.IMPL.cancelDragAndDrop(v);
    }

    public static void updateDragShadow(android.view.View v, android.view.View.DragShadowBuilder shadowBuilder) {
        android.support.v4.view.ViewCompat.IMPL.updateDragShadow(v, shadowBuilder);
    }

    protected ViewCompat() {
    }
}