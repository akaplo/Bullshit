package com.akaplo.bullshit;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    //Declare ints

    public static int numberOfPlayers;

    //Declare textviews

    TextView userNumberPrompt;


    //Declare buttons

    Button twoUsersBT;
    Button threeUsersBT;
    Button fourUsersBT;
    Button fiveUsersBT;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if the app's just starting, or if it's being restored

        if(savedInstanceState == null){

            //just starting

            //Ask for number of users

            userNumberPrompt = (TextView) findViewById(R.id.user_number);

            //Initialize buttons

            twoUsersBT = (Button) findViewById(R.id.two_players);
            threeUsersBT = (Button) findViewById(R.id.three_players);
            fourUsersBT = (Button) findViewById(R.id.four_players);
            fiveUsersBT = (Button) findViewById(R.id.five_players);

            //Add clickListeners for ALL buttons

            setButtonOnClickListeners();
        }


    }


    private void setButtonOnClickListeners(){

    //Click handler for the "2 players" button

        twoUsersBT.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                numberOfPlayers = 2;

                //Go to the name entry page

                Intent nameEntry = new Intent(MainActivity.this, NameEntry.class);
                startActivity(nameEntry);
            }
        });

    //Click handler for the "3 players" button

        threeUsersBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfPlayers = 3;

                //Go to the name entry page

                Intent nameEntry = new Intent(MainActivity.this, NameEntry.class);
                startActivity(nameEntry);
            }
        });

        //Click handler for the "4 players" button

        fourUsersBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfPlayers = 4;

                //Go to the name entry page

                Intent nameEntry = new Intent(MainActivity.this, NameEntry.class);
                startActivity(nameEntry);
            }
        });

        //Click handler for the "5 players" button

        fiveUsersBT.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOfPlayers = 5;

                //Go to the name entry page

                Intent nameEntry = new Intent(MainActivity.this, NameEntry.class);
                startActivity(nameEntry);
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
