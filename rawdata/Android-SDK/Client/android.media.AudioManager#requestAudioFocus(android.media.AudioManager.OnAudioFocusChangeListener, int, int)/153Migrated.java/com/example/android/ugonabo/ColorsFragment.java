package com.example.android.ugonabo;
import android.support.v4.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends android.support.v4.app.Fragment {
    android.media.MediaPlayer mp;

    private android.media.AudioManager audioManager;

    public ColorsFragment() {
        // Required empty public constructor
    }

    android.media.AudioManager.OnAudioFocusChangeListener MonAudioFocusChange = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                // When we lose focus for a little amount of time(Shortly)
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                // When we regain focus and can resume playback
                mp.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                // When we permanently lose audio focus
                releaseMediaPlayer();
            }
        }
    };

    private android.media.MediaPlayer.OnCompletionListener completionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @java.lang.Override
        public void onCompletion(android.media.MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
            mp = null;
            audioManager.abandonAudioFocus(MonAudioFocusChange);
        }
    }

    @java.lang.Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.word_list, container, false);
        // setContentView(R.layout.word_list);
        audioManager = ((android.media.AudioManager) (getActivity().getSystemService(android.content.Context.AUDIO_SERVICE)));
        final java.util.ArrayList<com.example.android.ugonabo.Word> words = new java.util.ArrayList<com.example.android.ugonabo.Word>();
        words.add(new com.example.android.ugonabo.Word("Black", "oji", R.drawable.color_black, R.raw.color_black));
        words.add(new com.example.android.ugonabo.Word("Blue", "amaloji", R.drawable.color_black, R.raw.color_black));
        words.add(new com.example.android.ugonabo.Word("Brown", "uri", R.drawable.color_brown, R.raw.color_brown));
        words.add(new com.example.android.ugonabo.Word("Green", "ndu-ndu", R.drawable.color_green, R.raw.color_green));
        words.add(new com.example.android.ugonabo.Word("Gray", "ntu-ntu", R.drawable.color_gray, R.raw.color_gray));
        words.add(new com.example.android.ugonabo.Word("Yellow", "edo", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new com.example.android.ugonabo.Word("Pink", "uhie ocha", R.drawable.color_red, R.raw.color_red));
        words.add(new com.example.android.ugonabo.Word("Red", "mme mme", R.drawable.color_red, R.raw.color_red));
        words.add(new com.example.android.ugonabo.Word("White", "ocha", R.drawable.color_white, R.raw.color_white));
        words.add(new com.example.android.ugonabo.Word("Purple", "ododo", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        com.example.android.ugonabo.WordAdapter itemsAdapter = new com.example.android.ugonabo.WordAdapter(getActivity(), words, R.color.category_colors);
        android.widget.ListView listView = ((android.widget.ListView) (rootView.findViewById(R.id.root_view)));
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @java.lang.Override
            public void onItemClick(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                com.example.android.ugonabo.Word currentWord = words.get(position);
                int sd = currentWord.getSoundResourceId();
                releaseMediaPlayer();
                AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
                int result = audioManager.requestAudioFocus(request);
                if (result == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // When we recieve audio focus
                    mp = android.media.MediaPlayer.create(getActivity(), sd);
                    mp.start();
                    mp.setOnCompletionListener(completionListener);
                }
            }
        });
        return rootView;
    }
}