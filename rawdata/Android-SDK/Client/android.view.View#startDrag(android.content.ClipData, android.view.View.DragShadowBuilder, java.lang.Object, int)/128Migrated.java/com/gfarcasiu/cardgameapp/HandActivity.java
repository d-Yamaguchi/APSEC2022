package com.gfarcasiu.cardgameapp;
import com.gfarcasiu.client.Client;
import com.gfarcasiu.client.MultiServer;
import com.gfarcasiu.game.Game;
import com.gfarcasiu.game.Player;
import com.gfarcasiu.game.PlayingCard;
import com.gfarcasiu.utilities.HelperFunctions;
public class HandActivity extends android.app.Activity {
    private static java.lang.String uniqueId;

    private boolean isServer;

    private android.view.View viewBeingDragged;// TODO think of a better name...


    private java.util.HashMap<android.view.View, com.gfarcasiu.game.PlayingCard> cardMap = new java.util.HashMap<>();// TODO there must be a better way


    private long commandTimeStamp = 0;

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_hand);
        // Initialize game and player settings
        isServer = getIntent().getExtras().getBoolean("isServer");
        android.util.Log.i("Debug", ("<Device is sever: " + isServer) + "/>");
        if (getIntent().getExtras().getBoolean("isNewGame")) {
            // Add current player to game
            com.gfarcasiu.cardgameapp.HandActivity.uniqueId = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            com.gfarcasiu.game.Player currentPlayer = new com.gfarcasiu.game.Player(com.gfarcasiu.cardgameapp.HandActivity.uniqueId, 52);
            try {
                executeAction(com.gfarcasiu.game.Game.class.getMethod("addPlayer", com.gfarcasiu.game.Player.class), currentPlayer);
            } catch (java.lang.NoSuchMethodException e) {
                android.util.Log.e("Error", "<Player could not be added/>");
                e.printStackTrace();
                this.onStop();
                return;
            }
        } else {
            // Populate cards
            com.gfarcasiu.game.PlayingCard[] cards = com.gfarcasiu.utilities.HelperFunctions.getGame(isServer).getPlayer(com.gfarcasiu.cardgameapp.HandActivity.uniqueId).getCards();
            for (com.gfarcasiu.game.PlayingCard card : cards)
                displayCard(card);

        }
        // Set listeners
        com.gfarcasiu.cardgameapp.HandActivity.MyDragEventListener dragEventListener = new com.gfarcasiu.cardgameapp.HandActivity.MyDragEventListener();
        findViewById(R.id.bottom).setOnDragListener(dragEventListener);
        findViewById(R.id.middle).setOnDragListener(dragEventListener);
        findViewById(R.id.top).setOnDragListener(dragEventListener);
        findViewById(R.id.deck_button).setOnDragListener(dragEventListener);
        findViewById(R.id.trash_button).setOnDragListener(dragEventListener);
        findViewById(R.id.table_button).setOnDragListener(dragEventListener);
    }

    public void toTable(android.view.View view) {
        this.onStop();// Perhaps not necessary

        android.content.Intent intent = new android.content.Intent(this, com.gfarcasiu.cardgameapp.TableActivity.class);
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("isServer", isServer);
        intent.putExtra("uniqueId", com.gfarcasiu.cardgameapp.HandActivity.uniqueId);
        startActivity(intent);
    }

    public void drawCard(android.view.View view) {
        com.gfarcasiu.game.PlayingCard playingCard = com.gfarcasiu.game.Game.getRandomCard(com.gfarcasiu.utilities.HelperFunctions.getGame(isServer).getDeck());
        try {
            executeAction(com.gfarcasiu.game.Game.class.getMethod("deckToPlayer", com.gfarcasiu.game.PlayingCard.class, java.lang.String.class), playingCard, com.gfarcasiu.cardgameapp.HandActivity.uniqueId);
        } catch (java.lang.NoSuchMethodException e) {
            android.util.Log.e("Error", "<Couldn't execute draw card/>");
            e.printStackTrace();
        }
        displayCard(playingCard);
    }

    private final class MyTouchListener implements android.view.View.OnTouchListener {
        @java.lang.Override
        public boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent) {
            if (motionEvent.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                android.content.ClipData data = android.content.ClipData.newPlainText("", "");
                android.view.View.DragShadowBuilder shadowBuilder = new android.view.View.DragShadowBuilder(view);
                view.startDragAndDrop(data, shadowBuilder, view.getParent(), 0);
                ((android.widget.LinearLayout) (view.getParent())).removeView(view);
                cardMap.put(view, com.gfarcasiu.utilities.HelperFunctions.getPlayingCardFromImageName(((java.lang.String) (view.getTag()))));
                viewBeingDragged = view;
                return true;
            } else {
                return false;
            }
        }
    }

    private final class MyDragEventListener implements android.view.View.OnDragListener {
        @java.lang.Override
        public boolean onDrag(android.view.View v, android.view.DragEvent event) {
            final int action = event.getAction();
            if (action == android.view.DragEvent.ACTION_DROP) {
                android.util.Log.i("Debug", ("<Card info: " + cardMap.get(viewBeingDragged)) + "/>");
                final int containerId = v.getId();
                com.gfarcasiu.game.PlayingCard card = cardMap.get(viewBeingDragged);
                try {
                    switch (containerId) {
                        case R.id.table_button :
                            android.util.Log.i("Debug", "<Table button dragged/>");
                            executeAction(com.gfarcasiu.game.Game.class.getMethod("playerToVisible", com.gfarcasiu.game.PlayingCard.class, java.lang.String.class), card, com.gfarcasiu.cardgameapp.HandActivity.uniqueId);
                            break;
                        case R.id.trash_button :
                            android.util.Log.i("Debug", "<Trash button dragged/>");
                            executeAction(com.gfarcasiu.game.Game.class.getMethod("playerToTrash", com.gfarcasiu.game.PlayingCard.class, java.lang.String.class), card, com.gfarcasiu.cardgameapp.HandActivity.uniqueId);
                            break;
                        case R.id.deck_button :
                            android.util.Log.i("Debug", "<Deck button dragged/>");
                            executeAction(com.gfarcasiu.game.Game.class.getMethod("playerToDeck", com.gfarcasiu.game.PlayingCard.class, java.lang.String.class), card, com.gfarcasiu.cardgameapp.HandActivity.uniqueId);
                            break;
                    }
                } catch (java.lang.NoSuchMethodException e) {
                    // THIS SHOULD NEVER HAPPEN
                    e.printStackTrace();
                }
                if (((containerId != R.id.table_button) && (containerId != R.id.trash_button)) && (containerId != R.id.deck_button)) {
                    ((android.widget.LinearLayout) (findViewById(R.id.middle))).addView(viewBeingDragged);
                } else {
                    cardMap.remove(viewBeingDragged);
                }
                viewBeingDragged = null;
            }
            return true;
        }
    }

    // HELPER METHOD
    private void executeAction(final java.lang.reflect.Method method, final java.lang.Object... args) {
        // Wait to execute calls if they occur to quckly
        if ((java.lang.System.currentTimeMillis() - commandTimeStamp) > (0.25 * java.lang.Math.pow(10, 9))) {
            if (!isServer)
                com.gfarcasiu.client.Client.getInstance().executeAction(method, args);
            else
                com.gfarcasiu.client.MultiServer.executeAction(method, args);

            commandTimeStamp = java.lang.System.currentTimeMillis();
        } else {
            new java.lang.Thread() {
                public void run() {
                    try {
                        java.lang.Thread.sleep(200);
                    } catch (java.lang.InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isServer)
                        com.gfarcasiu.client.Client.getInstance().executeAction(method, args);
                    else
                        com.gfarcasiu.client.MultiServer.executeAction(method, args);

                }
            }.start();
            commandTimeStamp = java.lang.System.currentTimeMillis() + 200;
        }
    }

    private void displayCard(com.gfarcasiu.game.PlayingCard card) {
        java.lang.String imageName = com.gfarcasiu.utilities.HelperFunctions.getImageNameFromPlayingCard(card);
        android.content.Context context = getApplicationContext();
        android.content.res.Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(imageName, "drawable", context.getPackageName());
        android.graphics.drawable.Drawable drawable = context.getResources().getDrawable(resourceId);
        android.widget.ImageView cardView = new android.widget.ImageView(getApplicationContext());
        int width = ((int) (android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics())));
        int height = ((int) (android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, 115, getResources().getDisplayMetrics())));
        int margin = ((int) (android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())));
        // TODO margins are not applying
        android.view.ViewGroup.MarginLayoutParams layoutParams = new android.view.ViewGroup.MarginLayoutParams(width, height);
        layoutParams.setMargins(margin, 0, margin, 0);
        cardView.setLayoutParams(layoutParams);
        cardView.setBackground(drawable);
        cardView.setLayoutParams(layoutParams);
        cardView.setTag(imageName);
        cardView.setOnTouchListener(new com.gfarcasiu.cardgameapp.HandActivity.MyTouchListener());
        ((android.widget.LinearLayout) (findViewById(R.id.middle))).addView(cardView);
        cardMap.put(cardView, card);
    }
}