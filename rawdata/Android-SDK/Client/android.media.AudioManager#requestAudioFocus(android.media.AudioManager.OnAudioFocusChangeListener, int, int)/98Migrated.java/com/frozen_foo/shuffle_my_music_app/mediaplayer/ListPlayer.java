package com.frozen_foo.shuffle_my_music_app.mediaplayer;
import com.frozen_foo.shuffle_my_music_2.IndexEntry;
import com.frozen_foo.shuffle_my_music_app.shuffle.ShuffleAccess;
/**
 * Created by Frank on 18.07.2017.
 */
public class ListPlayer {
    private android.media.AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;

    private android.media.AudioDeviceCallback audioDeviceCallback;

    private java.io.File[] songs;

    private com.frozen_foo.shuffle_my_music_app.mediaplayer.ListPlayerListener listPlayerListener;

    private android.content.Context context;

    private android.media.AudioManager audioManager;

    private android.media.MediaPlayer currentPlayer;

    private int songIndex = 0;

    private int recentCurrentPosition = 0;

    public ListPlayer(android.content.Context context, com.frozen_foo.shuffle_my_music_app.mediaplayer.ListPlayerListener listPlayerListener, final int recentSongIndex, int recentCurrentPosition) {
        this.context = context;
        this.listPlayerListener = listPlayerListener;
        this.songIndex = recentSongIndex;
        this.recentCurrentPosition = recentCurrentPosition;
    }

    public boolean isPlaying() {
        return (currentPlayer != null) && currentPlayer.isPlaying();
    }

    public void start() {
        init();
        int permission = requestAudioFocus();
        if (android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED == permission) {
            currentPlayer.start();
            listPlayerListener.onStart();
        }
    }

    private int requestAudioFocus() {
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        return audioManager.requestAudioFocus(request);
    }

    public void pause() {
        init();
        currentPlayer.pause();
        listPlayerListener.onPause();
    }

    public void startSongAtIndex(int index) {
        if ((index >= 0) && (index < songs.length)) {
            songIndex = index;
            if (currentPlayer == null) {
                init();
                start();
            } else {
                currentPlayer.stop();
                currentPlayer.release();
                initCurrentPlayer();
                start();
            }
        }
    }

    public void nextSong() {
        startSongAtIndex(songIndex + 1);
    }

    public void previousSong() {
        startSongAtIndex(songIndex - 1);
    }

    public void release() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer.release();
            currentPlayer = null;
            songs = null;
            listPlayerListener.playingSongChanged(ListPlayerControllerListener.NO_SONG);
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            audioManager.unregisterAudioDeviceCallback(audioDeviceCallback);
        }
    }

    private void init() {
        if (currentPlayer == null) {
            audioManager = context.getSystemService(android.media.AudioManager.class);
            onAudioFocusChangeListener = new android.media.AudioManager.OnAudioFocusChangeListener() {
                @java.lang.Override
                public void onAudioFocusChange(final int focusChange) {
                    switch (focusChange) {
                        case android.media.AudioManager.AUDIOFOCUS_LOSS :
                            new android.os.Handler().postDelayed(new java.lang.Runnable() {
                                @java.lang.Override
                                public void run() {
                                    pause();
                                }
                            }, 30L);
                            break;
                        case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                            pause();
                            break;
                        case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                            break;
                        case android.media.AudioManager.AUDIOFOCUS_GAIN :
                            start();
                            break;
                    }
                }
            };
            pausePlayBackWhenDeviceIsRemoved();
            initCurrentPlayer();
        }
    }

    private void pausePlayBackWhenDeviceIsRemoved() {
        audioDeviceCallback = new android.media.AudioDeviceCallback() {
            @java.lang.Override
            public void onAudioDevicesRemoved(final android.media.AudioDeviceInfo[] removedDevices) {
                pause();
            }
        };
        audioManager.registerAudioDeviceCallback(audioDeviceCallback, null);
    }

    public void loadSongs() {
        java.util.List<com.frozen_foo.shuffle_my_music_2.IndexEntry> indexEntries = new com.frozen_foo.shuffle_my_music_app.shuffle.ShuffleAccess().getLocalIndex(context);
        songs = new com.frozen_foo.shuffle_my_music_app.shuffle.ShuffleAccess().resolveLocalSongs(context, indexEntries);
    }

    private void initCurrentPlayer() {
        if (!org.apache.commons.lang3.ArrayUtils.isEmpty(songs)) {
            currentPlayer = android.media.MediaPlayer.create(context, android.net.Uri.fromFile(songs[songIndex]));
            currentPlayer.setOnErrorListener(listPlayerListener);
            currentPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
                @java.lang.Override
                public void onCompletion(android.media.MediaPlayer mp) {
                    startSongAtIndex(songIndex + 1);
                }
            });
            listPlayerListener.playingSongChanged(songIndex);
            currentPlayer.seekTo(recentCurrentPosition);
        }
    }

    public void volumeMax() {
        audioManager = context.getSystemService(android.media.AudioManager.class);
        audioManager.setStreamVolume(android.media.AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(android.media.AudioManager.STREAM_MUSIC), android.media.AudioManager.FLAG_SHOW_UI);
    }

    public int getSongIndex() {
        return songIndex;
    }

    public int getCurrentPosition() {
        return currentPlayer.getCurrentPosition();
    }
}