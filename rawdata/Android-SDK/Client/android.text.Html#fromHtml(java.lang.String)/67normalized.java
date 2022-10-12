@java.lang.Override
protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
        case com.stacktips.speechtotext.MainActivity.REQ_CODE_SPEECH_INPUT :
            {
                if ((resultCode == RESULT_OK) && (null != data)) {
                    java.util.ArrayList<java.lang.String> result = data.getStringArrayListExtra(android.speech.RecognizerIntent.EXTRA_RESULTS);
                    // mVoiceInputTv.setText(result.get(0));
                    if ((result != null) && (result.size() > 0)) {
                        java.lang.String _CVAR1 = "</p>";
                        java.lang.String _CVAR2 = ("<p style=\"color:blue;\">User : " + result.get(0)) + _CVAR1;
                        android.widget.TextView _CVAR0 = mVoiceInputTv;
                        android.text.Spanned _CVAR3 = android.text.Html.fromHtml(_CVAR2);
                        _CVAR0.append(_CVAR3);
                        // If user says hello, Ask for User's name & show the Greeting Text with users name.
                        if (result.get(0).equalsIgnoreCase("hello")) {
                            tts.speak("What is your name", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR5 = "<p style=\"color:red;\">Speaker : What is your name ?</p>";
                            android.widget.TextView _CVAR4 = mVoiceInputTv;
                            android.text.Spanned _CVAR6 = android.text.Html.fromHtml(_CVAR5);
                            _CVAR4.append(_CVAR6);
                        } else if (result.get(0).contains("name")) {
                            // Set the Greeting by indexing
                            java.lang.String name = result.get(0).substring(result.get(0).lastIndexOf(' ') + 1);
                            // Setting into Editor
                            editor.putString("name", name).apply();
                            tts.speak("Hello, " + name, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR8 = "</p>";
                            java.lang.String _CVAR9 = ("<p style=\"color:red;\">Speaker : Hello, " + name) + _CVAR8;
                            android.widget.TextView _CVAR7 = mVoiceInputTv;
                            android.text.Spanned _CVAR10 = android.text.Html.fromHtml(_CVAR9);
                            _CVAR7.append(_CVAR10);
                        } else if (result.get(0).contains("not feeling good")) {
                            tts.speak("I can understand. Please tell your symptoms in short", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR12 = "<p style=\"color:red;\">Speaker : I can understand. Please tell your symptoms in short</p>";
                            android.widget.TextView _CVAR11 = mVoiceInputTv;
                            android.text.Spanned _CVAR13 = android.text.Html.fromHtml(_CVAR12);
                            _CVAR11.append(_CVAR13);
                        } else if (result.get(0).contains("thank you")) {
                            tts.speak(("Thank you too, " + preferences.getString("name", "")) + " Take care.", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR15 = " Take care.</p>";
                            java.lang.String _CVAR16 = ("<p style=\"color:red;\">Speaker : Thank you too, " + preferences.getString("name", "")) + _CVAR15;
                            android.widget.TextView _CVAR14 = mVoiceInputTv;
                            android.text.Spanned _CVAR17 = android.text.Html.fromHtml(_CVAR16);
                            _CVAR14.append(_CVAR17);
                        } else if (result.get(0).contains("what time")) {
                            // Speaking the Time for the User
                            java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("HH:mm");// dd/MM/yyyy

                            java.util.Date now = new java.util.Date();
                            java.lang.String[] strDate = sdfDate.format(now).split(":");
                            if (strDate[1].contains("00")) {
                                strDate[1] = "o'clock";
                            }
                            tts.speak("The time is : " + sdfDate.format(now), android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR19 = "</p>";
                            java.lang.String _CVAR20 = ("<p style=\"color:red;\">Speaker : The time is : " + sdfDate.format(now)) + _CVAR19;
                            android.widget.TextView _CVAR18 = mVoiceInputTv;
                            android.text.Spanned _CVAR21 = android.text.Html.fromHtml(_CVAR20);
                            _CVAR18.append(_CVAR21);
                        } else if (result.get(0).contains("medicine")) {
                            tts.speak("I think you have fever. Please take this medicine.", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR23 = "<p style=\"color:red;\">Speaker : I think you have fever. Please take this medicine.</p>";
                            android.widget.TextView _CVAR22 = mVoiceInputTv;
                            android.text.Spanned _CVAR24 = android.text.Html.fromHtml(_CVAR23);
                            _CVAR22.append(_CVAR24);
                        } else {
                            tts.speak("Sorry, I cant help you with that", android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                            java.lang.String _CVAR26 = "<p style=\"color:red;\">Speaker : Sorry, I cant help you with that</p>";
                            android.widget.TextView _CVAR25 = mVoiceInputTv;
                            android.text.Spanned _CVAR27 = android.text.Html.fromHtml(_CVAR26);
                            _CVAR25.append(_CVAR27);
                        }
                    }
                }
                break;
            }
    }
}