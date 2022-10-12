/* Copyright (C) 2018 AlexMofer

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package am.widget.multifunctionalimageview;
/**
 * 版本兼容器
 * Created by Alex on 2018/11/21.
 */
@java.lang.SuppressWarnings("SameParameterValue")
final class Compat {
    private static final int LAYOUT_DIRECTION_LTR = 0;

    private static final am.widget.multifunctionalimageview.Compat.CompatImpl IMPL;

    static {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            IMPL = new am.widget.multifunctionalimageview.Compat.Api21CompatImpl();
        } else {
            IMPL = new am.widget.multifunctionalimageview.Compat.BaseCompatImpl();
        }
    }

    static int saveLayer(android.graphics.Canvas canvas, float left, float top, float right, float bottom, android.graphics.Paint paint) {
        return canvas.saveLayer(canvas, left, top, right, bottom);
    }

    static void addOval(android.graphics.Path path, float left, float top, float right, float bottom, android.graphics.Path.Direction dir) {
        am.widget.multifunctionalimageview.Compat.IMPL.addOval(path, left, top, right, bottom, dir);
    }

    static void addRoundRect(android.graphics.Path path, float left, float top, float right, float bottom, float rx, float ry, android.graphics.Path.Direction dir) {
        am.widget.multifunctionalimageview.Compat.IMPL.addRoundRect(path, left, top, right, bottom, rx, ry, dir);
    }

    static void apply(int gravity, int w, int h, android.graphics.Rect container, android.graphics.Rect outRect, int layoutDirection) {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            android.view.Gravity.apply(gravity, w, h, container, outRect, layoutDirection);
        } else {
            android.view.Gravity.apply(gravity, w, h, container, outRect);
        }
    }

    static int getLayoutDirection(android.view.View view) {
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            return view.getLayoutDirection();
        }
        return am.widget.multifunctionalimageview.Compat.LAYOUT_DIRECTION_LTR;
    }

    private Compat() {
        // no instance
    }

    public interface CompatImpl {
        int saveLayer(android.graphics.Canvas canvas, float left, float top, float right, float bottom, android.graphics.Paint paint);

        void addOval(android.graphics.Path path, float left, float top, float right, float bottom, android.graphics.Path.Direction dir);

        void addRoundRect(android.graphics.Path path, float left, float top, float right, float bottom, float rx, float ry, android.graphics.Path.Direction dir);
    }

    private static class BaseCompatImpl implements am.widget.multifunctionalimageview.Compat.CompatImpl {
        private static final java.util.ArrayList<android.graphics.RectF> RECT_FS = new java.util.ArrayList<>();

        private static android.graphics.RectF get() {
            synchronized(am.widget.multifunctionalimageview.Compat.BaseCompatImpl.RECT_FS) {
                if (am.widget.multifunctionalimageview.Compat.BaseCompatImpl.RECT_FS.isEmpty())
                    return new android.graphics.RectF();
                else
                    return am.widget.multifunctionalimageview.Compat.BaseCompatImpl.RECT_FS.remove(am.widget.multifunctionalimageview.Compat.BaseCompatImpl.RECT_FS.size() - 1);

            }
        }

        private static void put(android.graphics.RectF rect) {
            synchronized(am.widget.multifunctionalimageview.Compat.BaseCompatImpl.RECT_FS) {
                am.widget.multifunctionalimageview.Compat.BaseCompatImpl.RECT_FS.add(rect);
            }
        }

        @java.lang.Override
        public int saveLayer(android.graphics.Canvas canvas, float left, float top, float right, float bottom, android.graphics.Paint paint) {
            return canvas.saveLayer(left, top, right, bottom, paint, android.graphics.Canvas.ALL_SAVE_FLAG);
        }

        @java.lang.Override
        public void addOval(android.graphics.Path path, float left, float top, float right, float bottom, android.graphics.Path.Direction dir) {
            final android.graphics.RectF oval = am.widget.multifunctionalimageview.Compat.BaseCompatImpl.get();
            oval.set(left, top, right, bottom);
            path.addOval(oval, dir);
            am.widget.multifunctionalimageview.Compat.BaseCompatImpl.put(oval);
        }

        @java.lang.Override
        public void addRoundRect(android.graphics.Path path, float left, float top, float right, float bottom, float rx, float ry, android.graphics.Path.Direction dir) {
            final android.graphics.RectF rect = am.widget.multifunctionalimageview.Compat.BaseCompatImpl.get();
            rect.set(left, top, right, bottom);
            path.addRoundRect(rect, rx, ry, dir);
            am.widget.multifunctionalimageview.Compat.BaseCompatImpl.put(rect);
        }
    }

    @android.annotation.TargetApi(21)
    private static class Api21CompatImpl implements am.widget.multifunctionalimageview.Compat.CompatImpl {
        @java.lang.Override
        public int saveLayer(android.graphics.Canvas canvas, float left, float top, float right, float bottom, android.graphics.Paint paint) {
            return canvas.saveLayer(left, top, right, bottom, paint);
        }

        @java.lang.Override
        public void addOval(android.graphics.Path path, float left, float top, float right, float bottom, android.graphics.Path.Direction dir) {
            path.addOval(left, top, right, bottom, dir);
        }

        @java.lang.Override
        public void addRoundRect(android.graphics.Path path, float left, float top, float right, float bottom, float rx, float ry, android.graphics.Path.Direction dir) {
            path.addRoundRect(left, top, right, bottom, rx, ry, dir);
        }
    }
}