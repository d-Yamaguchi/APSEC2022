@java.lang.Override
public boolean onEditorAction(android.widget.TextView tv, int action, android.view.KeyEvent keyEvent) {
    if (action == android.view.inputmethod.EditorInfo.IME_ACTION_DONE) {
        java.lang.String _CVAR1 = "</p>";
        java.lang.String _CVAR2 = ("<p><b>You:</b> " + inputText) + _CVAR1;
        android.widget.TextView _CVAR0 = conversation;
        android.text.Spanned _CVAR3 = android.text.Html.fromHtml(_CVAR2);
        _CVAR0.append(_CVAR3);
        userInput.setText("");
        void _CVAR5 = R.string.workspace;
        android.widget.EditText _CVAR8 = userInput;
        android.text.Editable _CVAR9 = _CVAR8.getText();
        final java.lang.String inputText = _CVAR9.toString();
        com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest.Builder _CVAR7 = new com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest.Builder();
        java.lang.String _CVAR10 = inputText;
         _CVAR11 = _CVAR7.inputText(_CVAR10);
        com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest request = _CVAR11.build();
        com.ibm.watson.developer_cloud.conversation.v1.ConversationService _CVAR4 = myConversationService;
         _CVAR6 = getString(_CVAR5);
        com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest _CVAR12 = request;
         _CVAR13 = _CVAR4.message(_CVAR6, _CVAR12);
        com.ibm.watson.developer_cloud.http.ServiceCallback<com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse> _CVAR14 = new com.ibm.watson.developer_cloud.http.ServiceCallback<com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse>() {
            @java.lang.Override
            public void onResponse(com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse response) {
                final java.lang.String outputText = response.getText().get(0);
                tts.speak(outputText, android.speech.tts.TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                runOnUiThread(new java.lang.Runnable() {
                    @java.lang.Override
                    public void run() {
                        conversation.append(android.text.Html.fromHtml(("<p><b> Zara :</b> " + outputText) + "</p>"));
                    }
                });
                if (response.getIntents().get(0).getIntent().endsWith("RequestQuote")) {
                    java.lang.String quotesURL = "https://api.forismatic.com/api/1.0/" + "?method=getQuote&format=text&lang=en";
                    com.github.kittinunf.fuel.Fuel.get(quotesURL).responseString(new com.github.kittinunf.fuel.core.Handler<java.lang.String>() {
                        @java.lang.Override
                        public void success(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, java.lang.String quote) {
                            android.util.Log.d("Success", "To ho gaya, ab kya");
                            tts.speak(quote, android.speech.tts.TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                            conversation.append(android.text.Html.fromHtml(("<p><b>Zara :</b> " + quote) + "</p>"));
                        }

                        @java.lang.Override
                        public void failure(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, com.github.kittinunf.fuel.core.FuelError fuelError) {
                        }
                    });
                } else if (response.getIntents().get(0).getIntent().endsWith("PNR")) {
                    if ((com.android.parii.travcom.chatBot.x % 2) == 0) {
                        java.lang.String m = "Please Enter 10 DIGIT PNR";
                        tts.speak(m, android.speech.tts.TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                        conversation.append(android.text.Html.fromHtml(("<p><b>Bot:</b> " + m) + "</p>"));
                        com.android.parii.travcom.chatBot.x++;
                    } else {
                        com.android.parii.travcom.chatBot.x++;
                        /* String m = "CNF/B2/44/DF";
                        tts.speak(m, TextToSpeech.QUEUE_ADD, null, "DEFAULT");

                        conversation.append(
                        Html.fromHtml("<p><b>Bot:</b> " +
                        m + "</p>")
                        );
                         */
                        android.util.Log.d("me", "" + inputText);
                        java.lang.String l = inputText;
                        java.lang.String requestURL = ("https://api.railwayapi.com/v2/pnr-status/pnr/" + l) + "/apikey/bmbsdt3g07/";
                        android.util.Log.d("me :", "" + requestURL);
                        java.net.URL url = createURL(requestURL);
                        java.lang.String jsonResponse = "";
                        try {
                            android.util.Log.d("me :", "" + requestURL);
                            jsonResponse = makeHttprequest(url);
                            android.util.Log.d("me :", "" + jsonResponse);
                            android.widget.Toast.makeText(chatBot.this, "" + jsonResponse, android.widget.Toast.LENGTH_LONG).show();
                            org.json.JSONObject jsonObject = new org.json.JSONObject(jsonResponse);
                            android.util.Log.d("jsonobject", "check parse 1" + jsonObject);
                            org.json.JSONArray jsonArray = jsonObject.optJSONArray("passengers");
                            android.util.Log.d("jsonArray", "check parse 2" + jsonArray);
                            org.json.JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                            android.util.Log.d("jsonobject 1 ", "check parse 3" + jsonObject1);
                            pop = jsonObject1.optString("current_status");
                            android.util.Log.d("String", "check parse 4" + pop);
                            tts.speak(pop, android.speech.tts.TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                            conversation.append(android.text.Html.fromHtml(("<p><b>Bot:</b> " + pop) + "</p>"));
                        } catch (java.io.IOException e) {
                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @java.lang.Override
            public void onFailure(java.lang.Exception e) {
                android.util.Log.d("Fail", "Ho gaya hai Parii.....debug :( ");
            }
        };
        _CVAR13.enqueue(_CVAR14);
    }
    return false;
}