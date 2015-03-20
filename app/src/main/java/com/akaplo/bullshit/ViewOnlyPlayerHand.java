package com.akaplo.bullshit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ViewOnlyPlayerHand extends ActionBarActivity {


    private static final String TAG = ViewOnlyPlayerHand.class.getSimpleName();


    Game game = NameEntry.game;

    int[][] cardPictures;

    LinearLayout myGallery;

    User viewOnlyUser;


    Hand hand;


    Card[] cardArray;

    Button view_only_finish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_only_player_hand);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {


            cardPictures = game.getCardPictures();

            List<User> userList = game.getUserList();

            viewOnlyUser = userList.get(game.getViewOnlyPlayer());

            //Puts the name of the current user at the top of the screen:
            getSupportActionBar().setTitle(viewOnlyUser.getName() + "'s Hand (View Only!)");

            hand = viewOnlyUser.getPlayerHand();

            hand.sortByValue();

            cardArray = hand.toArray();


            Log.d(TAG, "VIEW ONLY: User is: " + game.getUserName());

            myGallery = (LinearLayout) findViewById(R.id.view_only_gallery);

            initPlayerView();

        }
    }


    protected void initPlayerView(){

        view_only_finish = (Button) findViewById(R.id.view_only_finish);

        Log.d(TAG, "Ready to display all cards in this user's hand");

        Log.d(TAG, "Indeces ready, about to display hand");

        view_only_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog("Done", "All done?");
            }
        });


        drawHand();


    }



    protected void drawHand(){

        for (int index = 0; index < hand.getCardCount(); index++) {
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
                createToast("You can't make any changes to your hand right now!");
            }});
        return layout;
    }

    public void createDialog(String title, String positiveMessage) {

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
                        // if this button is clicked, move the the landing to ask whether or not to call bullshit
                        Intent moveToBSLanding = new Intent(ViewOnlyPlayerHand.this, CallBullshit.class);

                        startActivity(moveToBSLanding);
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
        createToast("Hit 'Done' if you're done");
    }

    public void createToast(String msg){
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_only_player_hand, menu);
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
