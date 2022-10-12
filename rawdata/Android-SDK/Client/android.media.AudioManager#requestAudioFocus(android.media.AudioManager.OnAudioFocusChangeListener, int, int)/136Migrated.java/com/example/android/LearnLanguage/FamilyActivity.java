package com.example.android.LearnLanguage;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
public class FamilyActivity extends android.support.v7.app.AppCompatActivity {
    private java.util.ArrayList<com.example.android.LearnLanguage.Words> arrayList;

    private android.media.MediaPlayer mediaPlayer;

    private android.media.MediaPlayer.OnCompletionListener onCompletionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @java.lang.Override
        public void onCompletion(android.media.MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private android.media.AudioManager audioManager;

    private android.media.AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                mediaPlayer.release();
            }
        }
    };

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
        arrayList = new java.util.ArrayList<>();
        android.widget.ListView listView = ((android.widget.ListView) (findViewById(R.id.list_view)));
        com.example.android.LearnLanguage.CustomAdapter customAdapter = new com.example.android.LearnLanguage.CustomAdapter(this, populateArrayListFamily());
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @java.lang.Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                releaseMediaPlayer();
                AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
                int requestAudioFocus = audioManager.requestAudioFocus(request);
                if (requestAudioFocus == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = android.media.MediaPlayer.create(FamilyActivity.this, arrayList.get(position).getSpellSounds());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
    }

    private java.util.ArrayList<com.example.android.LearnLanguage.Words> populateArrayListFamily() {
        com.example.android.LearnLanguage.RawData data = new com.example.android.LearnLanguage.RawData();
        java.lang.String[] arabicWords = data.getFamilyArray_ar();
        java.lang.String[] englishWords = data.getFamilyArray_en();
        int[] pictureRep = data.getFamilyImageArray();
        int[] soundsRep = data.getFamilySoundsArray();
        for (int i = 0; i < data.getFamilyCount(); i++) {
            arrayList.add(new com.example.android.LearnLanguage.Words(englishWords[i], arabicWords[i], pictureRep[i], soundsRep[i]));
        }
        return arrayList;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @java.lang.Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @java.lang.Override
    public void onBackPressed() {
        super.onBackPressed();
        releaseMediaPlayer();
    }
}