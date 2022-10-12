package com.example.android.musicplayerapp;
import android.support.v7.app.AppCompatActivity;
/* public void getmoreinfo(View view){
Intent intent=new Intent(this,GetMoreInfoActivity.class);
intent.putExtra("songobject",(Song)getIntent().getSerializableExtra("songobj"));
startActivity(intent);
}
 */
public class NowPlayingActivity extends android.support.v7.app.AppCompatActivity {
    private android.media.MediaPlayer mediaPlayer;

    private android.media.MediaPlayer.OnCompletionListener onCompletionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @java.lang.Override
        public void onCompletion(android.media.MediaPlayer mp) {
            releasemediaplayer();
        }
    };

    private android.media.AudioManager audioManager;

    private android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause();
            }
            if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                releasemediaplayer();
            }
            if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
                mediaPlayer.seekTo(0);
            }
        }
    };

    private android.widget.SeekBar seekBar;

    private android.widget.Button playpausebutton;

    private android.widget.TextView currentsongtext;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        if (!getIntent().hasExtra("songobj")) {
            __SmPLUnsupported__(0).onCreate(savedInstanceState);
            return;
        }
        __SmPLUnsupported__(1).onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);
        audioManager = ((android.media.AudioManager) (this.getSystemService(android.content.Context.AUDIO_SERVICE)));
        final com.example.android.musicplayerapp.Song song = ((com.example.android.musicplayerapp.Song) (getIntent().getSerializableExtra("songobj")));
        final int songid = song.getSongid();
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int result = audioManager.requestAudioFocus(request);
        if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mediaPlayer = android.media.MediaPlayer.create(getApplicationContext(), songid);
        }
        seekBar = ((android.widget.SeekBar) (findViewById(R.id.seekbar)));
        playpausebutton = ((android.widget.Button) (findViewById(R.id.playpausebutton)));
        currentsongtext = ((android.widget.TextView) (findViewById(R.id.currentsong)));
        new com.example.android.musicplayerapp.NowPlayingActivity.PlaymediaTask().execute();
        playpausebutton.setOnClickListener(__SmPLUnsupported__(2));
        android.widget.Button getmoreinfobutton = ((android.widget.Button) (findViewById(R.id.getmoreinfo)));
        getmoreinfobutton.setOnClickListener(__SmPLUnsupported__(3));
    }

    private void releasemediaplayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }

    private class PlaymediaTask extends android.os.AsyncTask<java.lang.Void, java.lang.Integer, java.lang.Void> {
        @java.lang.Override
        protected void onPreExecute() {
            seekBar.setMax(mediaPlayer.getDuration());
            com.example.android.musicplayerapp.Song song = ((com.example.android.musicplayerapp.Song) (getIntent().getSerializableExtra("songobj")));
            currentsongtext.setText(song.getSongtitle());
        }

        @java.lang.Override
        protected java.lang.Void doInBackground(java.lang.Void... params) {
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            int currentposition;
            while (mediaPlayer != null) {
                try {
                    currentposition = mediaPlayer.getCurrentPosition();
                    publishProgress(currentposition);
                    // Thread.sleep(10);
                } catch (java.lang.Exception e) {
                }
            } 
            return null;
        }

        @java.lang.Override
        protected void onProgressUpdate(java.lang.Integer... values) {
            seekBar.setProgress(values[0]);
            // Toast.makeText(getApplicationContext(),""+values[0],Toast.LENGTH_SHORT).show();
        }
    }
}