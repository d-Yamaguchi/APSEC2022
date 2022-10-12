package com.a5androidintern2.p1281_audiofocus;
public class MainActivity extends android.app.Activity implements android.media.MediaPlayer.OnCompletionListener {
    static final java.lang.String LOG_TAG = "myLogs";

    final java.lang.String DIR_SD = "MyFiles";

    final java.lang.String FILENAME_SD = "music.mp3";

    android.media.AudioManager audioManager;

    com.a5androidintern2.p1281_audiofocus.MainActivity.AFListener afListenerMusic;

    com.a5androidintern2.p1281_audiofocus.MainActivity.AFListener afListenerSound;

    android.media.MediaPlayer mpMusic;

    android.media.MediaPlayer mpSound;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // получаем AudioManager. Именно через него мы будем запрашивать фокус.
        audioManager = ((android.media.AudioManager) (getSystemService(android.content.Context.AUDIO_SERVICE)));
    }

    public void onClickMusic(android.view.View view) {
        // метод срабатывает при нажатии кнопки Music.
        // создаём медиаплеер (для мелодии).
        mpMusic = new android.media.MediaPlayer();
        // try {
        // даём медиаплееру файл с музыкой.
        // mpMusic.setDataSource("mnt/sdcard/Music/music.mp3");
        // mpMusic.setDataSource("P1281_AudioFocus/app/src/main/res/raw/music.mp3");
        /* //мы ходим считать музыку из SD-карты.
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
        AudioFocusRequest request = audioFocusRequestOreo.getAudioFocusRequest();
        int requestResult = audioManager.requestAudioFocus(request);
        // Метод requestAudioFocus возвращает статус:
        // AUDIOFOCUS_REQUEST_FAILED = 0 – фокус не получен
        // AUDIOFOCUS_REQUEST_GRANTED = 1 – фокус получен
        android.util.Log.d(com.a5androidintern2.p1281_audiofocus.MainActivity.LOG_TAG, "Music request focus, result: " + requestResult);
        // После того, как получили фокус, стартуем воспроизведение.
        mpMusic.start();
    }

    public void onClickSound(android.view.View view) {
        // метод срабатывает при нажатии на любую из 3-х кнопок.
        // Здесь мы определяем, какая из 3-х кнопок была нажата.
        // в переменную durationHint пишем тип аудио-фокуса, который будем запрашивать.
        int durationHint = android.media.AudioManager.AUDIOFOCUS_GAIN;
        switch (view.getId()) {
            case R.id.btnPlaySoundG :
                durationHint = android.media.AudioManager.AUDIOFOCUS_GAIN;
                break;
            case R.id.btnPlaySoundGT :
                durationHint = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
                break;
            case R.id.btnPlaySoundGTD :
                durationHint = android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK;
                break;
        }
        // создаем MediaPlayer, который будет воспроизводить наш звук взрыва из папки raw.
        mpSound = android.media.MediaPlayer.create(this, R.raw.explosion);
        // Присваиваем этому MediaPlayer-у слушателя окончания воспроизведения.
        mpSound.setOnCompletionListener(this);
        // Запрашиваем фокус с типом, который определили выше.
        afListenerSound = new com.a5androidintern2.p1281_audiofocus.MainActivity.AFListener(mpSound, "Sound");
        int requestResult = audioManager.requestAudioFocus(afListenerSound, android.media.AudioManager.STREAM_MUSIC, durationHint);
        android.util.Log.d(com.a5androidintern2.p1281_audiofocus.MainActivity.LOG_TAG, "Sound request focus, result: " + requestResult);
        // Стартуем воспроизведение.
        mpSound.start();
    }

    @java.lang.Override
    public void onCompletion(android.media.MediaPlayer mp) {
        // метод срабатывает по окончании воспроизведения.
        // определяем, какой именно MediaPlayer закончил играть
        // и методом abandonAudioFocus сообщаем системе,
        // что больше не претендуем на аудио-фокус.
        if (mp == mpMusic) {
            android.util.Log.d(com.a5androidintern2.p1281_audiofocus.MainActivity.LOG_TAG, "Music: abandon focus");
            audioManager.abandonAudioFocus(afListenerMusic);
        } else if (mp == mpSound) {
            android.util.Log.d(com.a5androidintern2.p1281_audiofocus.MainActivity.LOG_TAG, "Sound: abandon focus");
            audioManager.abandonAudioFocus(afListenerSound);
        }
    }

    @java.lang.Override
    protected void onDestroy() {
        // освобождаем ресурсы и отпускаем фокус.
        super.onDestroy();
        if (mpMusic != null)
            mpMusic.release();

        if (mpSound != null)
            mpSound.release();

        if (afListenerMusic != null)
            audioManager.abandonAudioFocus(afListenerMusic);

        if (afListenerSound != null)
            audioManager.abandonAudioFocus(afListenerSound);

    }

    class AFListener implements android.media.AudioManager.OnAudioFocusChangeListener {
        // класс является получателем сообщений о потере/восстановлении фокуса.
        java.lang.String label = "";

        android.media.MediaPlayer mp;

        public AFListener(android.media.MediaPlayer mp, java.lang.String label) {
            this.label = label;
            this.mp = mp;
        }

        @java.lang.Override
        public void onAudioFocusChange(int focusChange) {
            // Метод onAudioFocusChange получает на вход статус фокуса этого приложения.
            // При потерях фокуса AUDIOFOCUS_LOSS и AUDIOFOCUS_LOSS_TRANSIENT ставим паузу.
            // А при AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK – просто уменьшаем громкость.
            // При получении же фокуса (AUDIOFOCUS_GAIN)  возобновляем воспроизведение,
            // если оно было приостановлено, и ставим громкость на максимум.
            java.lang.String event = "";
            switch (focusChange) {
                case android.media.AudioManager.AUDIOFOCUS_LOSS :
                    event = "AUDIOFOCUS_LOSS";
                    mp.pause();
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT :
                    event = "AUDIOFOCUS_LOSS_TRANSIENT";
                    mp.pause();
                    break;
                case android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK :
                    event = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
                    mp.setVolume(0.5F, 0.5F);
                    break;
                case android.media.AudioManager.AUDIOFOCUS_GAIN :
                    event = "AUDIOFOCUS_GAIN";
                    if (!mp.isPlaying())
                        mp.start();

                    mp.setVolume(1.0F, 1.0F);
                    break;
            }
            android.util.Log.d(com.a5androidintern2.p1281_audiofocus.MainActivity.LOG_TAG, (label + " onAudioFocusChange: ") + event);
        }
    }
}