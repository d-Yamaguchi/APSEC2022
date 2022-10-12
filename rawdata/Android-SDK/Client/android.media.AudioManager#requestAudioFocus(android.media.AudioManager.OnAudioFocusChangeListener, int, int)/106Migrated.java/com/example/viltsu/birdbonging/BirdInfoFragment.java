package com.example.viltsu.birdbonging;
import android.support.v4.app.Fragment;
/**
 * A simple {@link Fragment} subclass.
 */
public class BirdInfoFragment extends android.support.v4.app.Fragment {
    private android.media.MediaPlayer mp;

    private android.media.MediaPlayer.OnCompletionListener completionListener = new android.media.MediaPlayer.OnCompletionListener() {
        @java.lang.Override
        public void onCompletion(android.media.MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private android.media.AudioManager mAudioManager;

    // uusi metodi statejen käsittelyyn. Takaa estottoman audio toiston.
    private android.media.AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) || (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)) {
                // audio focus lähti hetkeksi, tulee takas. Mitä teet? Pause ja alkuun, soitto jatkuu focuksen tultua takasin.
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_GAIN) {
                // focus tulee takas niin soitto alkaa.
                mp.start();
            } else if (focusChange == android.media.AudioManager.AUDIOFOCUS_LOSS) {
                // vapauttaa resurssit taas.
                releaseMediaPlayer();
            }
        }
    };

    public BirdInfoFragment() {
        // Required empty public constructor
    }

    @java.lang.Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {
        android.view.View rootView = inflater.inflate(R.layout.activity_bird_info, container, false);
        mAudioManager = ((android.media.AudioManager) (getActivity().getSystemService(android.content.Context.AUDIO_SERVICE)));
        android.widget.ImageView playpenguin = rootView.findViewById(R.id.penguinsound);
        playpenguin.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(android.view.View arg0) {
                releaseMediaPlayer();
                AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
                int audiostate = mAudioManager.requestAudioFocus(request);
                // jos saadaan toisto-oikeus:
                if (audiostate == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // mediaplayer luodaan ja mp3 ladataan
                    mp = android.media.MediaPlayer.create(getActivity(), R.raw.shortpenguin);
                    // toisto mp3
                    mp.start();
                    // kun soitto loppu, aja oma releaseMediaPlayer metodi
                    mp.setOnCompletionListener(completionListener);
                }
            }
        });
        android.widget.ImageView playduck = rootView.findViewById(R.id.ducksound);
        playduck.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View view) {
                int audiostate = mAudioManager.requestAudioFocus(audioFocusChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                // jos saadaan toisto-oikeus:
                if (audiostate == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // mediaplayer luodaan ja mp3 ladataan
                    mp = android.media.MediaPlayer.create(getActivity(), R.raw.shortduck);
                    // toisto mp3
                    mp.start();
                    // kun soitto loppu, aja oma releaseMediaPlayer metodi
                    mp.setOnCompletionListener(completionListener);
                }
            }
        });
        android.widget.ImageView playmallord = rootView.findViewById(R.id.mallordsound);
        playmallord.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(android.view.View view) {
                int audiostate = mAudioManager.requestAudioFocus(audioFocusChangeListener, android.media.AudioManager.STREAM_MUSIC, android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (audiostate == android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = android.media.MediaPlayer.create(getActivity(), R.raw.shortmallord);
                    mp.start();
                    mp.setOnCompletionListener(completionListener);
                }
            }
        });
        return rootView;
    }

    // applikaatiosta poistuttaessa tehdään tämä jotta musiikki loppuu:
    @java.lang.Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    // vapautetaan mediaplayer
    public void releaseMediaPlayer() {
        if (mp != null) {
            mp.release();
            mp = null;
            mAudioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }
}