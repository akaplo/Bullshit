package com.akaplo.bullshit;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class NameEntry extends ActionBarActivity {

    private static final String TAG = NameEntry.class.getSimpleName();

    //Declare ints

   public static int numberOfPlayers;

    //Declare EditTexts

    List<EditText> editTextNames = new ArrayList<EditText>();

    //Declare String Arrays

    String[] nameStrings;

    //Declare Buttons

    Button play;

    Deck deck;

    //Declare programmatic layouts

    private LinearLayout editTextHolder;


    List<User> userList = new ArrayList<User>();

    public static Game game;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);

        //If the page isn't being restored:

        if(savedInstanceState == null) {

            //Get the number of players from the button on the previous page

            this.numberOfPlayers = MainActivity.numberOfPlayers;

            //Based on the number of players, instantiate the EditText array

            editTextHolder = (LinearLayout) findViewById(R.id.editTextHolder);

            for(int playerNumber = 0; playerNumber< numberOfPlayers; playerNumber++) {

                //create the edittexts

                EditText editText = new EditText(NameEntry.this);

                editText.setId(playerNumber + 1);

                editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                editText.setHint("Name of Player " + (playerNumber + 1));

                //Add this editText to the master array of all of them

                editTextNames.add(editText);

                //Show the EditText on the page

                editTextHolder.addView(editText);

            } //end for loop


            nameStrings = new String[numberOfPlayers];

            //Instantiate the button

            play = (Button) findViewById(R.id.btn_start_game);

            play.setOnClickListener(new View.OnClickListener() {
                @Override

                //When "play" is clicked, read the names of each user and instantiate their objects.

                public void onClick(View v) {

                    //Read the names written in the EditTexts

                    for(int player = 0; player < editTextNames.size(); player++) {

                        //Put the names into a String array

                        nameStrings[player] = editTextNames.get(player).getText().toString();

                    }

                    //Create a new user for each name in the array of names
                    //Then add it to the list of Users
                    for(int s = 0; s < nameStrings.length; s++){
                        userList.add(new User(s, nameStrings[s]));

                    }
                    userList.add(new User(numberOfPlayers+1, "The Middle"));


                    Log.d(TAG, "All users accounted for");

                    deck = new Deck(false);

                    deck.shuffle();

                    Log.d(TAG, "Deck created and shuffled");

                    for(User u : userList){
                        if(u.getName() != "The Middle") {
                            u.initHand(deck, numberOfPlayers);
                            u.showCurrentCard();
                        }
                    }

                    //Log.d(TAG, "All user hands have been initialized");

                    Log.d(TAG, "Number of users: " + (userList.size()-1));

                    /*Drawable*/int[][] cardPictures = bindCardPictures();

                    Log.d(TAG, "Card pictures bound");

                    game = new Game(userList, deck, numberOfPlayers, cardPictures);



                    game.play();

                    Intent playerLanding = new Intent(NameEntry.this, PlayerLanding.class);
                    startActivity(playerLanding);
                }
            });


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_name_entry, menu);
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

    public /*Drawable*/int[][] bindCardPictures(){

        int SPADES = 0;   // Codes for the 4 suits, plus Joker.
        int HEARTS = 1;
        int DIAMONDS = 2;
        int CLUBS = 3;


        int ACE = 1;      // Codes for the non-numeric cards.
        int JACK = 11;    //   Cards 2 through 10 have their
        int QUEEN = 12;   //   numerical values for their codes.
        int KING = 13;

        /*Drawable*/int[][] cardPictures = new /*Drawable*/int[14][4];

        //CLUBS:

        cardPictures[ACE][CLUBS] = /*getResources().getDrawable*/(R.drawable.ac);
        cardPictures[2][CLUBS] = /*getResources().getDrawable*/(R.drawable.twoc);
        cardPictures[3][CLUBS] = /*getResources().getDrawable*/(R.drawable.threec);
        cardPictures[4][CLUBS] = /*getResources().getDrawable*/(R.drawable.fourc);
        cardPictures[5][CLUBS] = /*getResources().getDrawable*/(R.drawable.fivec);
        cardPictures[6][CLUBS] = /*getResources().getDrawable*/(R.drawable.sixc);
        cardPictures[7][CLUBS] = /*getResources().getDrawable*/(R.drawable.sevc);
        cardPictures[8][CLUBS] = /*getResources().getDrawable*/(R.drawable.eightc);
        cardPictures[9][CLUBS] = /*getResources().getDrawable*/(R.drawable.ninec);
        cardPictures[10][CLUBS] = /*getResources().getDrawable*/(R.drawable.tenc);
        cardPictures[JACK][CLUBS] = /*getResources().getDrawable*/(R.drawable.jc);
        cardPictures[QUEEN][CLUBS] = /*getResources().getDrawable*/(R.drawable.qc);
        cardPictures[KING][CLUBS] = /*getResources().getDrawable*/(R.drawable.kc);

        //DIAMONDS:

        cardPictures[ACE][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.ad);
        cardPictures[2][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.twod);
        cardPictures[3][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.threed);
        cardPictures[4][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.fourd);
        cardPictures[5][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.fived);
        cardPictures[6][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.sixd);
        cardPictures[7][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.sevd);
        cardPictures[8][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.eightd);
        cardPictures[9][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.nined);
        cardPictures[10][DIAMONDS] = /*getResources().getDrawabl*e*/(R.drawable.tend);
        cardPictures[JACK] [DIAMONDS] = /*getResources().getDrawable*/(R.drawable.jd);
        cardPictures[QUEEN][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.qd);
        cardPictures[KING][DIAMONDS] = /*getResources().getDrawable*/(R.drawable.kd);

        //HEARTS:

        cardPictures[ACE][HEARTS] = /*getResources().getDrawable*/(R.drawable.ah);
        cardPictures[2][HEARTS] = /*getResources().getDrawable*/(R.drawable.twoh);
        cardPictures[3][HEARTS] = /*getResources().getDrawable*/(R.drawable.threeh);
        cardPictures[4][HEARTS] = /*getResources().getDrawable*/(R.drawable.fourh);
        cardPictures[5][HEARTS] = /*getResources().getDrawable*/(R.drawable.fiveh);
        cardPictures[6][HEARTS] = /*getResources().getDrawable*/(R.drawable.sixh);
        cardPictures[7][HEARTS] = /*getResources().getDrawable*/(R.drawable.sevh);
        cardPictures[8][HEARTS] = /*getResources().getDrawable*/(R.drawable.eighth);
        cardPictures[9][HEARTS] = /*getResources().getDrawable*/(R.drawable.nineh);
        cardPictures[10][HEARTS] = /*getResources().getDrawable*/(R.drawable.tenh);
        cardPictures[JACK][HEARTS] = /*getResources().getDrawable*/(R.drawable.jh);
        cardPictures[QUEEN][HEARTS] = /*getResources().getDrawable*/(R.drawable.qh);
        cardPictures[KING][HEARTS] = /*etResources().getDrawable*/(R.drawable.kh);

        //SPADES:

        cardPictures[ACE][SPADES] = /*getResources().getDrawable*/(R.drawable.as);
        cardPictures[2][SPADES] =/* getResources().getDrawable*/(R.drawable.twos);
        cardPictures[3][SPADES] =/* getResources().getDrawable*/(R.drawable.threes);
        cardPictures[4][SPADES] =/* getResources().getDrawable*/(R.drawable.fours);
        cardPictures[5][SPADES] =/* getResources().getDrawable*/(R.drawable.fives);
        cardPictures[6][SPADES] = /*getResources().getDrawable*/(R.drawable.sixs);
        cardPictures[7][SPADES] = /*getResources().getDrawable*/(R.drawable.sevs);
        cardPictures[8][SPADES] = /*getResources().getDrawable*/(R.drawable.eights);
        cardPictures[9][SPADES] = /*getResources().getDrawable*/(R.drawable.nines);
        cardPictures[10][SPADES] =/* getResources().getDrawable*/(R.drawable.tens);
        cardPictures[JACK][SPADES] =/* getResources().getDrawable*/(R.drawable.js);
        cardPictures[QUEEN][SPADES] = /*getResources().getDrawable*/(R.drawable.qs);
        cardPictures[KING][SPADES] = /*getResources().getDrawable*/(R.drawable.ks);

        return cardPictures;

    }
    }

