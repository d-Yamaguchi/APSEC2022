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
    android.view.View _CVAR2 = v;
    java.lang.String _CVAR3 = _CVAR2.toString();
    java.lang.String _CVAR1 = "dot";
    java.lang.String _CVAR4 = "Dot : " + _CVAR3;
    android.content.ClipData data = android.content.ClipData.newPlainText(_CVAR1, _CVAR4);
    android.view.View _CVAR6 = v;
    final android.view.View.DragShadowBuilder builder = new android.view.View.DragShadowBuilder(_CVAR6);
    android.view.View _CVAR0 = v;
    android.content.ClipData _CVAR5 = data;
    android.view.View.DragShadowBuilder _CVAR7 = builder;
    android.view.View _CVAR8 = v;
    int _CVAR9 = 0;
    _CVAR0.startDrag(_CVAR5, _CVAR7, _CVAR8, _CVAR9);
    v.setOnDragListener(new android.view.View.OnDragListener() {
        @java.lang.Override
        public boolean onDrag(android.view.View v, android.view.DragEvent event) {
            switch (event.getAction()) {
                case android.view.DragEvent.ACTION_DRAG_STARTED :
                    dragView.setVisibility(android.view.View.INVISIBLE);
                    isEnter = true;
                    break;
                case android.view.DragEvent.ACTION_DRAG_EXITED :
                    dragView.setVisibility(android.view.View.VISIBLE);
                    break;
            }
            return true;
        }
    });
    return true;
}