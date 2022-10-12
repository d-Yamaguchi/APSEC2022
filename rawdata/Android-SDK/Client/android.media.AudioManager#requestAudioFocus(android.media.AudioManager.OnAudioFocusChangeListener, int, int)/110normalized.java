/* access modifiers changed from: 0000 */
/* renamed from: a */
public void mo16679a(java.lang.Object obj) {
    boolean z = true;
    android.media.AudioManager _CVAR0 = this.f12501a;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = this.f12502b;
    int _CVAR2 = 3;
    int _CVAR3 = 1;
    int requestAudioFocus = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    java.lang.StringBuilder sb = new java.lang.StringBuilder();
    sb.append("Audio Focus Granted ");
    if (requestAudioFocus != 1) {
        z = false;
    }
    sb.append(z);
    p686n.p687a.Timber.m44529c(sb.toString(), new java.lang.Object[0]);
}