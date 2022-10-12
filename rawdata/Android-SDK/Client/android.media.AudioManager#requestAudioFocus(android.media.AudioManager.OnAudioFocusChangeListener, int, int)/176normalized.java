public void onClickMusic(android.view.View view) {
    // метод срабатывает при нажатии кнопки Music.
    // создаём медиаплеер (для мелодии).
    mpMusic = new android.media.MediaPlayer();
    // try {
    // даём медиаплееру файл с музыкой.
    // mpMusic.setDataSource("mnt/sdcard/Music/music.mp3");
    // mpMusic.setDataSource("P1281_AudioFocus/app/src/main/res/raw/music.mp3");
    /* мы ходим считать музыку из SD-карты.
    //получаем доступ к SD-карте, отыскиваем там файл с музыкой.
    //получам адрес файла на SD-карте.
    //прописываем адрес файла в методе mpMusic.setDataSource().

    //проверяем доступность SD.
    if (!Environment.getExternalStorageState().equals(
    Environment.MEDIA_MOUNTED)) {
    Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
    return;
    }
    //получаем путь к SD.
    File sdPath = Environment.getExternalStorageDirectory();
    //добавляем свой каталог к пути.
    sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
    //формируем объект File, который содержит путь к файлу.
    File sdFile = new File(sdPath, FILENAME_SD);


    Log.d(LOG_TAG, "адрес .mp3-файла: " + String.valueOf(sdFile));
    //даём медиаплееру файл с музыкой.
    mpMusic.setDataSource(String.valueOf(sdFile));
     */
    // mpMusic.setDataSource("sdcard/MyFiles/music.mp3");
    // mpMusic.setDataSource("P1281_AudioFocus/app/src/main/res/raw/music.mp3");
    // создаем MediaPlayer, который будет воспроизводить музыку из папки raw.
    mpMusic = android.media.MediaPlayer.create(this, R.raw.music);
    // mpMusic.prepare();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // устанавливаем Activity, как получателя уведомления об окончании воспроизведения.
    mpMusic.setOnCompletionListener(this);
    // Далее идет работа с фокусом.
    // afListenerMusic – это слушатель (реализующий интерфейс OnAudioFocusChangeListener),
    // который будет получать сообщения о потере/восстановлении фокуса.
    // Он является экземпляром класса AFListener, который мы рассмотрим чуть дальше.
    afListenerMusic = new com.a5androidintern2.p1281_audiofocus.MainActivity.AFListener(mpMusic, "Music");
    android.media.AudioManager _CVAR0 = audioManager;
    com.a5androidintern2.p1281_audiofocus.MainActivity.AFListener _CVAR1 = afListenerMusic;
    int _CVAR2 = android.media.AudioManager.STREAM_MUSIC;
    int _CVAR3 = android.media.AudioManager.AUDIOFOCUS_GAIN;
    // Фокус запрашивается с помощью метода requestAudioFocus.
    // На вход необходимо передать:
    // - слушателя, который будет получать сообщения о фокусе
    // - тип потока
    // - тип фокуса
    int requestResult = _CVAR0.requestAudioFocus(_CVAR1, _CVAR2, _CVAR3);
    // Метод requestAudioFocus возвращает статус:
    // AUDIOFOCUS_REQUEST_FAILED = 0 – фокус не получен
    // AUDIOFOCUS_REQUEST_GRANTED = 1 – фокус получен
    android.util.Log.d(com.a5androidintern2.p1281_audiofocus.MainActivity.LOG_TAG, "Music request focus, result: " + requestResult);
    // После того, как получили фокус, стартуем воспроизведение.
    mpMusic.start();
}