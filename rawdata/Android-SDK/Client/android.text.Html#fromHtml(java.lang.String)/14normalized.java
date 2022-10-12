@android.annotation.SuppressLint("DefaultLocale")
public PhrasesAdapter(teach.vietnam.asia.activity.PhrasesActivity activity, java.util.List listData, teach.vietnam.asia.sound.IAudioPlayer iAudioPlayer) {
    int i = 0;
    java.lang.String word;
    audio = new teach.vietnam.asia.sound.AudioPlayer(activity, iAudioPlayer);
    // this.iAudioPlayer = iAudioPlayer;
    teach.vietnam.asia.utils.ULog.i(teach.vietnam.asia.adapter.PhrasesAdapter.class, "PracticeAdapter locale:" + java.util.Locale.getDefault().getLanguage());
    this.activity = activity;
    this.listData = listData;
    listData2 = new java.util.ArrayList();
    listData2.addAll(listData);
    lang = activity.lang;
    layoutInflater = android.view.LayoutInflater.from(activity);
    alpha = null;
    alpha = new java.lang.String[listData.size()];
    for (java.lang.Object viet : listData) {
        java.lang.Object _CVAR0 = viet;
        java.lang.String _CVAR1 = lang;
         _CVAR2 = teach.vietnam.asia.utils.Utility.getO1(_CVAR0, _CVAR1);
        android.text.Spanned _CVAR3 = android.text.Html.fromHtml(_CVAR2);
        java.lang.String _CVAR4 = _CVAR3.toString();
        word = _CVAR4;
        alpha[i++] = word.split(" ")[0];
    }
}