@org.junit.Test
public void shouldStartPlaying() throws java.lang.Exception {
    // assume
    final java.lang.String mediaPath = "test.mp3";
    // given
    final java.io.File mp3File = new java.io.File(android.os.Environment.getExternalStorageDirectory(), mediaPath);
    mp3File.createNewFile();
    mp3File.deleteOnExit();
    final de.vanmar.android.ilikepodcasts.library.bo.Item item = ItemBuilder.anItem().withMediaPath(mediaPath).build();
    final int length = 123456;
    BDDMockito.willReturn(length).given(mediaPlayer).getDuration();
    java.lang.Class _CVAR1 = android.media.AudioManager.OnAudioFocusChangeListener.class;
    int _CVAR3 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR5 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    android.media.AudioManager _CVAR0 = audioManager;
     _CVAR2 = Matchers.any(_CVAR1);
     _CVAR4 = Matchers.eq(_CVAR3);
     _CVAR6 = Matchers.eq(_CVAR5);
    int _CVAR7 = _CVAR0.requestAudioFocus(_CVAR2, _CVAR4, _CVAR6);
     _CVAR8 = BDDMockito.given(_CVAR7);
    int _CVAR9 = android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    _CVAR8.willReturn(_CVAR9);
    // when
    binder.play(item);
    // then
    Mockito.verify(mediaPlayer).setDataSource(Matchers.any(java.io.FileDescriptor.class));
    Mockito.verify(mediaPlayer).prepare();
    Mockito.verify(mediaPlayer).seekTo(item.getPosition());
    Mockito.verify(mediaPlayer).start();
    Mockito.verify(callback).playStarted(item, length);
}