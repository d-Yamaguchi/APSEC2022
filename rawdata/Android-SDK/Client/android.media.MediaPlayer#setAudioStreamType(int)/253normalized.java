// private final IBinder iBinder = new Mu();
@android.support.annotation.RequiresApi(api = android.os.Build.VERSION_CODES.LOLLIPOP)
@java.lang.Override
public void onCreate() {
    super.onCreate();
    android.media.AudioAttributes.Builder builder = new android.media.AudioAttributes.Builder();
    builder.setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC);
    mediaPlayer = new android.media.MediaPlayer();
    android.media.MediaPlayer _CVAR0 = mediaPlayer;
    int _CVAR1 = android.media.AudioManager.STREAM_MUSIC;
    _CVAR0.setAudioStreamType(_CVAR1);
    // mediaPlayer.setAudioAttributes(builder.build());
    // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    // mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.FULL_WAKE_LOCK);
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.setOnErrorListener(this);
    mediaPlayer.setOnPreparedListener(this);
}