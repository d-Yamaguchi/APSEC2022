package com.example.android.miwok;
import android.support.v7.app.AppCompatActivity;
public class ColorsActivity extends android.support.v7.app.AppCompatActivity {
    android.media.MediaPlayer mediaPlayer;

    java.util.ArrayList<com.example.android.miwok.Word> words = new java.util.ArrayList<com.example.android.miwok.Word>();

    android.media.MediaPlayer.OnCompletionListener onCompletionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @java.lang.Override
        public void onCompletion(android.media.MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int i) {
            if (i == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            } else if (i == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                mediaPlayer.stop();
                releaseMediaPlayer();
            } else if (i == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                mediaPlayer.pause();
            } else if (i == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
            }
        }
    };

    android.media.AudioManager audioManager;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        words.add(new com.example.android.miwok.Word("red", "laal", R.drawable.color_red, R.raw.red));
        words.add(new com.example.android.miwok.Word("green", "shobuch", R.drawable.color_green, R.raw.green));
        words.add(new com.example.android.miwok.Word("brown", "badami", R.drawable.color_brown, R.raw.brown));
        words.add(new com.example.android.miwok.Word("gray", "dhushara", R.drawable.color_gray, R.raw.gray));
        words.add(new com.example.android.miwok.Word("black", "kalo", R.drawable.color_black, R.raw.black));
        words.add(new com.example.android.miwok.Word("white", "shadha", R.drawable.color_white, R.raw.white));
        words.add(new com.example.android.miwok.Word("yellow", "halud", R.drawable.color_mustard_yellow, R.raw.yellow));
        // int audioFocus = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,  AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        // onAudioFocusChangeListener.onAudioFocusChange(audioFocus);
        com.example.android.miwok.WordAdapter adapter = new com.example.android.miwok.WordAdapter(this, words, R.color.category_colors);
        android.widget.ListView listView = ((android.widget.ListView) (findViewById(R.id.list)));
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @java.lang.Override
            public void onItemClick(android.widget.AdapterView<?> a, android.view.View v, int position, long id) {
                releaseMediaPlayer();
                audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
                AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
                int audioFocus = audioManager.requestAudioFocus(request);
                if (audioFocus == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    mediaPlayer = android.media.MediaPlayer.create(ColorsActivity.this, words.get(position).getAudioResourceId());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        listView.setAdapter(adapter);
    }

    @java.lang.Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}