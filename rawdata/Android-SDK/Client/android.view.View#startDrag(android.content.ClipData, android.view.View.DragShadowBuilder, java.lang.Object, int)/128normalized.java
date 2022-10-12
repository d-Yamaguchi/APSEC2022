@java.lang.Override
public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
    if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {
        java.lang.String _CVAR1 = "";
        java.lang.String _CVAR2 = "";
        android.content.ClipData data = android.content.ClipData.newPlainText(_CVAR1, _CVAR2);
        android.view.View _CVAR4 = view;
        android.view.View.DragShadowBuilder shadowBuilder = new android.view.View.DragShadowBuilder(_CVAR4);
        android.view.View _CVAR6 = view;
        android.view.View _CVAR0 = view;
        android.content.ClipData _CVAR3 = data;
        android.view.View.DragShadowBuilder _CVAR5 = shadowBuilder;
        android.view.ViewParent _CVAR7 = _CVAR6.getParent();
        int _CVAR8 = 0;
        _CVAR0.startDrag(_CVAR3, _CVAR5, _CVAR7, _CVAR8);
        ((android.widget.LinearLayout) (view.getParent())).removeView(view);
        cardMap.put(view, com.gfarcasiu.utilities.HelperFunctions.getPlayingCardFromImageName(((java.lang.String) (view.getTag()))));
        viewBeingDragged = view;
        return true;
    } else {
        return false;
    }
}