package com.example.android.learnitalian;
import android.support.v4.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends android.support.v4.app.Fragment {
    private android.media.MediaPlayer mediaPlayer;

    private android.media.AudioManager audioManager;

    private android.media.MediaPlayer.OnCompletionListener mCompletionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @java.lang.Override
        public void onCompletion(android.media.MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                // Gain audio focus
                mediaPlayer.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of the audio focus
                releaseMediaPlayer();
            } else if ((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                // Temporary loss of the audio focus, we should start the audio from the beginning since we want to hear the whole pronunciation
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.word_list, container, false);
        final java.util.ArrayList<com.example.android.learnitalian.Word> myColors = new java.util.ArrayList<>();
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.red), "red", R.drawable.color_red, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.blue), "blue", R.drawable.color_gray, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.green), "green", R.drawable.color_green, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.yellow), "yellow", R.drawable.color_dusty_yellow, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.orange), "orange", R.drawable.color_mustard_yellow, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.brown), "brown", R.drawable.color_brown, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.gray), "grey", R.drawable.color_gray, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.purple), "purple", R.drawable.color_gray, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.pink), "pink", R.drawable.color_red, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.white), "white", R.drawable.color_white, R.raw.number_one));
        myColors.add(new com.example.android.learnitalian.Word(getString(R.string.black), "black", R.drawable.color_black, R.raw.number_one));
        com.example.android.learnitalian.WordAdapter itemsAdapter = new com.example.android.learnitalian.WordAdapter(getActivity(), myColors);
        android.widget.ListView listView = rootView.findViewById(R.id.list);
        listView.setBackgroundColor(getResources().getColor(R.color.category_colors));
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @java.lang.Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                releaseMediaPlayer();
                // initialize audioManager
                audioManager = ((android.media.AudioManager) (getActivity().getSystemService(android.content.Context.AUDIO_SERVICE)));
                AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
                int audioFocusReturn = audioManager.requestAudioFocus(request);
                if (audioFocusReturn == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = android.media.MediaPlayer.create(getActivity(), myColors.get(position).getmAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @java.lang.Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }
}