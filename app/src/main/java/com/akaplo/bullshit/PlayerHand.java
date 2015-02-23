package com.akaplo.bullshit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


import java.util.List;


public class PlayerHand extends ActionBarActivity {


    private static final String TAG = NameEntry.class.getSimpleName();

    List<User> userList = NameEntry.game.getUserList();

    Game game = NameEntry.game;

    int[][] cardPictures;

    LinearLayout myGallery;

    User currentUser;

    User middle;

    Hand hand;

    Hand middleHand;

    Card[] cardArray;

    Button done;

    Card justRemovedCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_hand);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {


            cardPictures = game.getCardPictures();

            currentUser = userList.get(game.getUserInt());

            //a convoluted way of accessing the last element in the list (User: "middle")
            middle = userList.get(game.getNumberOfPlayers());

            middleHand = middle.getPlayerHand();

            hand = currentUser.getPlayerHand();

            hand.sortByValue();

            cardArray = hand.toArray();


            Log.d(TAG, "Current user is: " + game.getUserName());

            myGallery = (LinearLayout) findViewById(R.id.mygallery);

            if(!game.playerIsMiddle()) initPlayerView();

            if(game.playerIsMiddle()) initMiddleView();





        }
    }


        protected void initPlayerView(){
            done = (Button) findViewById(R.id.done);

            Log.d(TAG, "Ready to display all cards in this user's hand");

            Log.d(TAG, "Indeces ready, about to display hand");
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialog("Done", "Are you done?", false);
                }
            });


            drawHand(false);


        }

        protected void initMiddleView(){
            done = (Button) findViewById(R.id.done);
            done.setText("Were you bullshitting?");

            Log.d(TAG, "Ready to display all cards in the middle's hand");



            Log.d(TAG, "Indeces ready, about to display hand");
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialog("Was it BS?", "Yes :(", true);
                }
            });


            drawHand(true);


        }

        protected void drawHand(boolean middleView){

            if(middleView) {

               Card[] middleCards = game.getCardsSentThisTurn();
                int ct = game.getNumberOfCardsSentThisTurn();

                for (int index = 0; index < ct; index++) {
                    int suit = middleCards[index].getSuit();
                    int val = middleCards[index].getValue();
                    myGallery.addView(insertPhoto(cardPictures[val][suit], index));
                }
            }
            else {

                for (int index = 0; index < hand.getCardCount(); index++) {
                    int suit = hand.getCard(index).getSuit();
                    int val = hand.getCard(index).getValue();
                    myGallery.addView(insertPhoto(cardPictures[val][suit], index));
                }
            }
        }


        View insertPhoto(int path, int cardIndex){



            Bitmap bm = BitmapFactory.decodeResource(this.getResources(), path);

            LinearLayout layout = new LinearLayout(getApplicationContext());

            layout.setLayoutParams(new LayoutParams(750, 850));

            layout.setGravity(Gravity.BOTTOM);

            ImageView imageView = new ImageView(getApplicationContext());

            imageView.setLayoutParams(new LayoutParams(720, 820));

            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            imageView.setImageBitmap(bm);

            layout.addView(imageView);


            final int currCardIndex = cardIndex;




            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "On click listener set, ready for cards to be sent to the middle! Curr card:" + currCardIndex);
                    createMiddleDialog(currCardIndex);
                   }});
            return layout;
        }

    public void createDialog(String title, String positiveMessage, final boolean isBS) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(positiveMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (isBS) {
                            game.bullshitTrue();
                            game.setUser(/*game.getUserBeforeBullshit()*/);
                            game.nextTurn();

                            nextTurn();

                        } else {//game.setUserBeforeBullshit();
                            // if this button is clicked, move the the landing to ask whether or not to call bullshit


                            Intent moveToBSLanding = new Intent(PlayerHand.this, CallBullshit.class);
                            Log.d(TAG, "The user has just been updated to " + game.getUserInt() + " " + game.getUserName());
                            startActivity(moveToBSLanding);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        createToast("Congratulations!");
                        game.nextTurn();
                        nextTurn();


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onBackPressed(){

        if(hand.getSentThisTurn() == 0)
            createToast("No more moves to undo!");
        else {
            Card c = middleHand.removeLast();
            hand.addCard(c);
            hand.downSentThisTurn();
            refresh();
        }

    }

    public void createToast(String msg){
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();
    }

    public void createMiddleDialog(final int cardIndex) {
        //aaron is a poop, he poops a lot therefore he is a poop
        //ding dong aaron has a dong
        //i like his dong
        //it makes me happy
        //as well does his rice crispy treats
        //cutie patootie writing code, making a game with an inappropriate name
        //that's my aaron
        //being smart, going to college and learning a lot
        //i like to rhyme
        //doodle doodle do, i love you
        //its the day after valentine's but i love you even more
        //i like sleeping next to you
        //and waking up and kissing you
        //you're weird though, you're always hot and i'm always cold
        //you are my space heater
        //i need a more romantic way to say that but i don't care because it's very accurate moooo
        //this is a great comment and i think it will make you happy when you're done pooping



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Send this card to Middle?");

        // set dialog message
        alertDialogBuilder
                .setMessage(hand.getCard(cardIndex).toString())
                .setCancelable(false)
                .setPositiveButton("Yes!",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, send the card to the "middle" hand
                        Log.d(TAG, "Before removing, the hand has " + hand.getCardCount() + " cards");

                        moveToMiddle(cardIndex);



                        refresh();

                       // drawHand();

                       // findViewById(android.R.id.content).getRootView().requestLayout();


                        Log.d(TAG, "After removing, the hand has " + hand.getCardCount() + " cards");



                        Log.d(TAG, "The middle's hand consists of: " + middleHand.getCardCount() + " cards");
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void refresh(){
        Intent refresh = new Intent(PlayerHand.this, PlayerHand.class);
        //this.finishActivity(0);
        startActivity(refresh);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_hand, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void moveToMiddle(int cardIndex){
        justRemovedCard = hand.removeCard(cardArray[cardIndex]);
        Log.d(TAG, "The card just removed is: " + justRemovedCard.toString());
        middleHand.addCard(justRemovedCard);
        hand.upSentThisTurn();
        game.addCardSentThisTurn(justRemovedCard);
    }

    public void nextTurn(){
        Intent nextTurn = new Intent(PlayerHand.this, PlayerLanding.class);
        startActivity(nextTurn);
    }


}
