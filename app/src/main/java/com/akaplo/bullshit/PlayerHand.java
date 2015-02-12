package com.akaplo.bullshit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;


import android.os.Environment;

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

    HorizontalScrollView cardView;

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

         //   cardView = (HorizontalScrollView) findViewById(R.id.linearImage);

            cardPictures = game.getCardPictures();

            currentUser = userList.get(game.getUserInt());
            hand = currentUser.getPlayerHand();

            Log.d(TAG, "Ready to display all cards in this user's hand");
/*
            for(int i = 0; i < hand.getCardCount(); i++) {
                int suit = hand.getCard(i).getSuit();
                int val = hand.getCard(i).getValue();

                ImageView image = new ImageView(PlayerHand.this);

                Log.d(TAG, "Indeces ready, about to display hand");

               //Normal line:
               image.setImageDrawable(cardPictures[val][suit]);

                //Test line:
                //image.setImageDrawable(cardPictures[1][3]);

                cardView.addView(image);

                Log.d(TAG, "Hand should be up!");

            }

        */
            Log.d(TAG, "Indeces ready, about to display hand");
            myGallery = (LinearLayout) findViewById(R.id.mygallery);

      /*      String targetPath/*ExternalStorageDirectoryPath = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath();
                    Uri.parse("android.resource://com.akaplo.bullshit/" + R.drawable.ac).getPath();

            String targetPath = ExternalStorageDirectoryPath + "/test/";

            Toast.makeText(getApplicationContext(), targetPath, Toast.LENGTH_LONG).show();
            File targetDirector = new File(targetPath);
            Log.d(TAG, "Indeces ready, about to display hand" + targetPath);
            File[] files = targetDirector.listFiles();
            for (File file : files) {*/
               // myGallery.addView(insertPhoto(/*file.getAbsolutePath()*/"moo"));
       //     }

            for(int index = 0; index < hand.getCardCount(); index++) {
                int suit = hand.getCard(index).getSuit();
                int val = hand.getCard(index).getValue();
                myGallery.addView(insertPhoto(cardPictures[val][suit], val, suit, index));
            }

        }
    }

        View insertPhoto(int path, int val, int suit, int cardIndex){
           // Bitmap bm = decodeSampledBitmapFromUri(path, 220, 220);

            Bitmap bm = BitmapFactory.decodeResource(this.getResources(),
                   /*R.drawable.ac);*/ path);

            LinearLayout layout = new LinearLayout(getApplicationContext());
            layout.setLayoutParams(new LayoutParams(750, 850));
            layout.setGravity(Gravity.BOTTOM);

            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setLayoutParams(new LayoutParams(720, 820));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setImageBitmap(bm);

            layout.addView(imageView);

            final int value = val;
            final int suitNum = suit;
            final int currCardIndex = cardIndex;
            imageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "On click listener set, ready for cards to be sent to the middle!");
                    createDialog(value, suitNum, currCardIndex);
                    /*Toast.makeText(this,
                            "Clicked - " + itemList.get(i),
                            Toast.LENGTH_LONG).show();  */
                    //ToMiddleDialog middle = new ToMiddleDialog();


                }});
            return layout;
        }


    public void createDialog(int val, int suit, int cardIndex) {
        // Use the Builder class for convenient dialog construction
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.middle)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog

                    }
                });
        // Create the AlertDialog object and return it
         builder.create();

    */


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



    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
        Bitmap bm = null;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(path, options);

        return bm;
    }

    public int calculateInSampleSize(

            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float)height / (float)reqHeight);
            } else {
                inSampleSize = Math.round((float)width / (float)reqWidth);
            }
        }

        return inSampleSize;
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
