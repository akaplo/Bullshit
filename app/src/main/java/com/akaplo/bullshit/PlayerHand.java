package com.akaplo.bullshit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;



import java.util.List;


public class PlayerHand extends ActionBarActivity {


    private static final String TAG = NameEntry.class.getSimpleName();

    List<User> userList = NameEntry.game.getUserList();

    Game game = NameEntry.game;

    int[][] cardPictures;

    LinearLayout myGallery;

    User currentUser;

    Hand hand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_hand);

        if (savedInstanceState == null) {


            cardPictures = game.getCardPictures();

            currentUser = userList.get(game.getUserInt());
            hand = currentUser.getPlayerHand();

            Log.d(TAG, "Ready to display all cards in this user's hand");

            Log.d(TAG, "Indeces ready, about to display hand");

            myGallery = (LinearLayout) findViewById(R.id.mygallery);


            for(int index = 0; index < hand.getCardCount(); index++) {
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
                    Log.d(TAG, "On click listener set, ready for cards to be sent to the middle!");
                    createDialog(currCardIndex);
                   }});
            return layout;
        }


    public void createDialog(int cardIndex) {

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
                        Log.d(TAG, "Someone wants to send a card to the middle!");
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
