public static android.os.Bundle writeActionAndGetExtras(android.app.Notification.Builder builder, android.support.v4.app.NotificationCompatBase.Action action) {
    NotificationCompatBase.Action _CVAR1 = action;
    NotificationCompatBase.Action _CVAR3 = action;
    NotificationCompatBase.Action _CVAR5 = action;
    android.app.Notification.Builder _CVAR0 = builder;
     _CVAR2 = _CVAR1.getIcon();
     _CVAR4 = _CVAR3.getTitle();
     _CVAR6 = _CVAR5.getActionIntent();
    _CVAR0.addAction(_CVAR2, _CVAR4, _CVAR6);
    builder = new android.os.Bundle(action.getExtras());
    if (action.getRemoteInputs() != null) {
        builder.putParcelableArray("android.support.remoteInputs", android.support.v4.app.RemoteInputCompatJellybean.toBundleArray(action.getRemoteInputs()));
    }
    return builder;
}