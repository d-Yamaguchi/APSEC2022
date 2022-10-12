package com.example.macx.music_app.activities;
import android.support.v7.app.AppCompatActivity;
import com.example.macx.music_app.R;
/**
 * Created by MacX on 2018-04-04.
 */
public class PlayerActivity extends android.support.v7.app.AppCompatActivity {
    private android.media.MediaPlayer mediaPlayer;

    private android.widget.ImageView playPause;

    private android.media.AudioManager audioManager;

    // it is a parameter for methods {@link requestAudioFocus} and {@link abandonAudioFocus}
    android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                try {
                    mediaPlayer.pause();
                } catch (java.lang.Exception e) {
                    // do nothing
                }
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.play_screen);
        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
        android.util.Log.i(com.example.macx.music_app.activities.PlayerActivity.class.getName(), "RUN TEST");
        java.lang.String title = getIntent().getStringExtra("TITLE");
        java.lang.String author = getIntent().getStringExtra("AUTHOR");
        int rawId = getIntent().getIntExtra("RAWID", 0);
        int imgId = getIntent().getIntExtra("IMGID", 0);
        android.util.Log.i(com.example.macx.music_app.activities.PlayerActivity.class.getName(), "TEST:" + title);
        android.widget.TextView titleTextView = findViewById(R.id.title_text);
        titleTextView.setText(title);
        android.widget.TextView authorTextView = findViewById(R.id.author_text);
        authorTextView.setText(author);
        android.widget.ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageResource(imgId);
        mediaPlayer = android.media.MediaPlayer.create(this, rawId);
        android.widget.ImageView rewindBack = findViewById(R.id.rewind_backward);
        android.widget.ImageView rewindForward = findViewById(R.id.rewind_forward);
        playPause = findViewById(R.id.play_pause);
        rewindBack.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                mediaPlayer.stop();
                try {
                    mediaPlayer.prepare();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
                playPause.setImageResource(R.drawable.play);
            }
        });
        rewindForward.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                releaseMediaPlayer();
                android.content.Intent backToPlaylist = new android.content.Intent(PlayerActivity.this, com.example.macx.music_app.activities.ListActivity.class);
                startActivity(backToPlaylist);
            }
        });
        playPause.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View v) {
                if ((mediaPlayer != null) && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    playPause.setImageResource(R.drawable.play);
                } else {
                    AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
                    int result = audioManager.requestAudioFocus(request);
                    if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        mediaPlayer.start();
                    }
                    playPause.setImageResource(R.drawable.pause);
                }
            }
        });
        mediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @java.lang.Override
            public void onCompletion(android.media.MediaPlayer mp) {
                mediaPlayer.release();
                android.content.Intent backToPlaylist = new android.content.Intent(PlayerActivity.this, com.example.macx.music_app.activities.ListActivity.class);
                startActivity(backToPlaylist);
            }
        });
    }

    @java.lang.Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * We need to check if media player is not null before we call release() method on it
     * otherwise this will cause NullPointerException
     */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}