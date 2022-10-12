@java.lang.Override
public void onMessageReceived(com.google.firebase.messaging.RemoteMessage remoteMessage) {
    // ...
    java.util.Random R = new java.util.Random();
    int random = R.nextInt(24);
    java.util.Map<java.lang.String, java.lang.String> data = remoteMessage.getData();
    java.lang.String testing = data.get("body");
    android.util.Log.d("GOTMESSAGE", "Message data payload: " + testing);
    // TODO(developer): Handle FCM messages here.
    if (random < 10) {
        sendNotification(java.lang.String.valueOf(random));
    } else if ((random > 9) && (random < 20)) {
        mediaPlayer.setOnErrorListener(new android.media.MediaPlayer.OnErrorListener() {
            public boolean onError(android.media.MediaPlayer mp, int what, int extra) {
                // TODO notify error to user or play next song
                return true;
            }
        });
        mediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            public void onCompletion(android.media.MediaPlayer mp) {
                // TODO Notify to user the completion of song or play next song
            }
        });
        android.media.MediaPlayer mediaPlayer = new android.media.MediaPlayer();
        if (random == 10) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a0);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a0);
                android.media.MediaPlayer _CVAR0 = mediaPlayer;
                int _CVAR1 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR0.setAudioStreamType(_CVAR1);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 11) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a1);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a1);
                android.media.MediaPlayer _CVAR2 = mediaPlayer;
                int _CVAR3 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR2.setAudioStreamType(_CVAR3);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 12) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a2);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a2);
                android.media.MediaPlayer _CVAR4 = mediaPlayer;
                int _CVAR5 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR4.setAudioStreamType(_CVAR5);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 13) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a3);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a3);
                android.media.MediaPlayer _CVAR6 = mediaPlayer;
                int _CVAR7 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR6.setAudioStreamType(_CVAR7);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 14) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a4);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a4);
                android.media.MediaPlayer _CVAR8 = mediaPlayer;
                int _CVAR9 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR8.setAudioStreamType(_CVAR9);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 15) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a5);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a5);
                android.media.MediaPlayer _CVAR10 = mediaPlayer;
                int _CVAR11 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR10.setAudioStreamType(_CVAR11);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 16) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a6);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a6);
                android.media.MediaPlayer _CVAR12 = mediaPlayer;
                int _CVAR13 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR12.setAudioStreamType(_CVAR13);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 17) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a7);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a7);
                android.media.MediaPlayer _CVAR14 = mediaPlayer;
                int _CVAR15 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR14.setAudioStreamType(_CVAR15);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 18) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a8);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a8);
                android.media.MediaPlayer _CVAR16 = mediaPlayer;
                int _CVAR17 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR16.setAudioStreamType(_CVAR17);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else if (random == 19) {
            java.io.InputStream ins = getResources().openRawResource(com.example.franklin.smartreminder.R.raw.a9);
            try {
                mediaPlayer = android.media.MediaPlayer.create(this, com.example.franklin.smartreminder.R.raw.a9);
                android.media.MediaPlayer _CVAR18 = mediaPlayer;
                int _CVAR19 = android.media.AudioManager.STREAM_MUSIC;
                _CVAR18.setAudioStreamType(_CVAR19);
                mediaPlayer.prepare();
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    } else if (random > 19) {
        if (random == 20) {
            android.os.Vibrator v = ((android.os.Vibrator) (getSystemService(android.content.Context.VIBRATOR_SERVICE)));
            // Vibrate for 500 milliseconds
            long[] timing = new long[]{ 0, 200, 200, 200 };
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                v.vibrate(android.os.VibrationEffect.createWaveform(timing, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // deprecated in API 26
                long[] pattern = new long[]{ 0, 200, 200, 200 };
                v.vibrate(pattern, -1);
            }
        } else if (random == 21) {
            android.os.Vibrator v = ((android.os.Vibrator) (getSystemService(android.content.Context.VIBRATOR_SERVICE)));
            // Vibrate for 500 milliseconds
            long[] timing = new long[]{ 0, 200, 600, 200 };
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                v.vibrate(android.os.VibrationEffect.createWaveform(timing, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // deprecated in API 26
                long[] pattern = new long[]{ 0, 200, 600, 200 };
                v.vibrate(pattern, -1);
            }
        } else if (random == 22) {
            android.os.Vibrator v = ((android.os.Vibrator) (getSystemService(android.content.Context.VIBRATOR_SERVICE)));
            // Vibrate for 500 milliseconds
            long[] timing = new long[]{ 0, 600, 200, 600 };
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                v.vibrate(android.os.VibrationEffect.createWaveform(timing, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // deprecated in API 26
                long[] pattern = new long[]{ 0, 600, 200, 600 };
                v.vibrate(pattern, -1);
            }
        } else if (random == 23) {
            android.os.Vibrator v = ((android.os.Vibrator) (getSystemService(android.content.Context.VIBRATOR_SERVICE)));
            // Vibrate for 500 milliseconds
            long[] timing = new long[]{ 0, 600, 600, 600 };
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                v.vibrate(android.os.VibrationEffect.createWaveform(timing, android.os.VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // deprecated in API 26
                long[] pattern = new long[]{ 0, 600, 600, 600 };
                v.vibrate(pattern, -1);
            }
        }
    }
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    android.util.Log.d(android.content.ContentValues.TAG, "From: " + remoteMessage.getFrom());
    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
        android.util.Log.d("GOTMESSAGE", "Message data payload: " + testing);
        /* Check if data needs to be processed by long running job */
        if (true) {
            // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
        } else {
            // Handle message within 10 seconds
        }
    }
    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
        android.util.Log.d(android.content.ContentValues.TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }
    // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.
}