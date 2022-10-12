package p163g.p174d.p178b.p185g0.p186v5;
import p686n.p687a.Timber;
/* renamed from: g.d.b.g0.v5.e */
/* compiled from: AudioFocusFlow */
public class C5176e {
    /* renamed from: a */
    protected android.media.AudioManager f12501a;

    /* renamed from: b */
    protected android.media.AudioManager.OnAudioFocusChangeListener f12502b;

    C5176e(android.media.AudioManager audioManager, android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener) {
        this.f12501a = audioManager;
        this.f12502b = onAudioFocusChangeListener;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo16679a(java.lang.Object obj) {
        boolean z = true;
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int requestAudioFocus = this.f12501a.requestAudioFocus(request);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Audio Focus Granted ");
        if (requestAudioFocus != 1) {
            z = false;
        }
        sb.append(z);
        p686n.p687a.Timber.m44529c(sb.toString(), __SmPLUnsupported__(0));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public void mo16680b(java.lang.Object obj) {
        mo16678a();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo16678a() {
        this.f12501a.abandonAudioFocus(this.f12502b);
    }
}