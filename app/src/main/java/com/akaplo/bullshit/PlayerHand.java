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

            done = (Button) findViewById(R.id.done);

            Log.d(TAG, "Ready to display all cards in this user's hand");

            Log.d(TAG, "Indeces ready, about to display hand");



            myGallery = (LinearLayout) findViewById(R.id.mygallery);


            drawHand();

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDoneDialog();
                }
            });

        }
    }


        public void drawHand(){

            for(int index = 0; index < hand.getCardCount(); index++) {
                int suit = hand.getCard(index).getSuit();
                int val = hand.getCard(index).getValue();
                myGallery.addView(insertPhoto(cardPictures[val][suit], index));
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

    public void createDoneDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Finish Turn");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you done?")
                .setCancelable(false)
                .setPositiveButton("Yes!",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, move the the next player's landing page
                        game.setUser(currentUser.getPlayerNumber()+1);
                        Intent nextPlayer = new Intent(PlayerHand.this, CallBullshit.class);
                        Log.d(TAG, "The user has just been updated to " + game.getUserInt() + " " + game.getUserName());
                        startActivity(nextPlayer);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    @Override
    public void onBackPressed(){

        if(middleHand.getCardCount() == 0)
            createErrorToast();
        else {
            Card c = middleHand.removeLast();
            hand.addCard(c);
            refresh();
        }

    }

    public void createErrorToast(){
        Toast.makeText(getApplicationContext(), "No more moves to undo!",
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

                        justRemovedCard = hand.removeCard(cardArray[cardIndex]);
                        Log.d(TAG, "The card just removed is: " + justRemovedCard.toString());
                        middleHand.addCard(justRemovedCard);


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



}
