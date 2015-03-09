package com.akaplo.bullshit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;


public class MiddleHand extends ActionBarActivity {


    private static final String TAG = MiddleHand.class.getSimpleName();

    List<User> userList = NameEntry.game.getUserList();

    //TODO:Implement the CallBullshit.xml ability for bullshit to be called by a specific user
    //That logic would extend to the following assignment:

    //User calledBS =


    Game game = NameEntry.game;

    int[][] cardPictures;

    LinearLayout myGallery;

    User currentUser;

    Hand hand;


    Card[] cardArray;

    Button done;

    User whoCalledBullshit = game.getUserWhoCalledBullshit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle_hand);

        if (savedInstanceState == null) {

            cardPictures = game.getCardPictures();


            currentUser = game.getMiddleUser();


            hand = currentUser.getPlayerHand();

            hand.sortByValue();

            cardArray = hand.toArray();


            Log.d(TAG, "Current user is: " + game.getUserName());

            myGallery = (LinearLayout) findViewById(R.id.middleGallery);

           initMiddleView();
        }
    }






    protected void initMiddleView(){
        done = (Button) findViewById(R.id.done_middleHand);
        done.setText("Were you bullshitting?");


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog("Was it BS?", "Yes :(");
            }
        });


        drawHand();


    }

public void drawHand() {

    int middleCards = game.getNumberOfCardsSentThisTurn();


    Log.d(TAG, "The middle has "+ hand.getCardCount() + " cards");

    Log.d(TAG, "Game thinks "+ middleCards + " cards were sent this turn");


        for (int index = 0; index < hand.getCardCount(); index++) {
            if(middleCards > 0) {
                int suit = hand.getCard(index).getSuit();
                int val = hand.getCard(index).getValue();
                myGallery.addView(insertPhoto(cardPictures[val][suit], index));
            }
                middleCards--;

        }
}

    View insertPhoto(int path, int cardIndex){



        Bitmap bm = BitmapFactory.decodeResource(this.getResources(), path);

        LinearLayout layout = new LinearLayout(getApplicationContext());

        layout.setLayoutParams(new ViewGroup.LayoutParams(750, 850));

        layout.setGravity(Gravity.BOTTOM);

        ImageView imageView = new ImageView(getApplicationContext());

        imageView.setLayoutParams(new ViewGroup.LayoutParams(720, 820));

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        imageView.setImageBitmap(bm);

        layout.addView(imageView);

        return layout;
    }


    public void createDialog(String title, String positiveMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle(title);
        Log.d(TAG, "Game thinks the previous user was " + game.getUserBeforeBullshit());
        // set dialog message
        alertDialogBuilder
                .setMessage(positiveMessage)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        game.successfulBullshitCall();
                        nextTurn();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If the player was telling the truth,
                        // put the cards into the hand of the player who called bullshit
                            dialog.cancel();
                            game.failedBullshitCall();
                            createToast("Congratulations!");
                            nextTurn();
                        }

                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void nextTurn(){
        game.nextTurn();
        Intent nextTurn = new Intent(MiddleHand.this, PlayerLanding.class);
        startActivity(nextTurn);
    }

    public void createToast(String msg){
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){

        createToast("There's no going back now!");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_middle_hand, menu);
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
