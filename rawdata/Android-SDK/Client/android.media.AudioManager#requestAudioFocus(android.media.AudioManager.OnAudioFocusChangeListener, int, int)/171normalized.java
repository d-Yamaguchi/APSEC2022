@java.lang.Override
protected void onCreate(android.os.Bundle savedInstanceState) {
    if (!getIntent().hasExtra("songobj")) {
        super.onCreate(savedInstanceState);
        return;
    }
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_now_playing);
    audioManager = ((android.media.AudioManager) (this.getSystemService(android.content.Context.AUDIO_SERVICE)));
    final com.example.android.musicplayerapp.Song song = ((com.example.android.musicplayerapp.Song) (getIntent().getSerializableExtra("songobj")));
    final int songid = song.getSongid();
    android.media.AudioManager _CVAR0 = audioManager;
    android.media.AudioManager.OnAudioFocusChangeListener _CVAR1 = onAudioFocusChangeListener;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    int result = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
        mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), songid);
    }
    seekBar = ((android.widget.SeekBar) (findViewById(R.id.seekbar)));
    playpausebutton = ((android.widget.Button) (findViewById(R.id.playpausebutton)));
    currentsongtext = ((android.widget.TextView) (findViewById(R.id.currentsong)));
    new com.example.android.musicplayerapp.NowPlayingActivity.PlaymediaTask().execute();
    android.widget.Button _CVAR4 = playpausebutton;
    android.view.View.OnClickListener _CVAR5 = new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                }
            } else {
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN);
                if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), songid);
                }
                new com.example.android.musicplayerapp.NowPlayingActivity.PlaymediaTask().execute();
            }
        }
    };
    _CVAR4.setOnClickListener(_CVAR5);
    android.widget.Button getmoreinfobutton = ((android.widget.Button) (findViewById(R.id.getmoreinfo)));
    getmoreinfobutton.setOnClickListener(new android.view.View.OnClickListener() {
        @java.lang.Override
        public void onClick(android.view.View v) {
            android.content.Intent intent = new android.content.Intent(getApplicationContext(), com.example.android.musicplayerapp.GetMoreInfoActivity.class);
            intent.putExtra("songobject", ((com.example.android.musicplayerapp.Song) (getIntent().getSerializableExtra("songobj"))));
            startActivity(intent);
        }
    });
}