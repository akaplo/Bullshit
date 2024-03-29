package com.akaplo.bullshit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class CallBullshit extends ActionBarActivity {



    final String TAG = "CALLBS";

    TextView callBSEditText;

    Button callBS;
    Button dontCallBS;

    Button playerViewHand;
    Button playerCallBS;


    Game game = NameEntry.game;

    List<User> userList = game.getUserList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_bullshit);

        callBSEditText = (TextView) findViewById(R.id.callBSTV);

        makeButtons(game.getNumberOfPlayers());

        game.printAllHands();

        dontCallBS = (Button) findViewById(R.id.dontCallBS);

        if(savedInstanceState == null){

            callBSEditText.setText(game.getUserName() + " has finished turn.  Call bullshit?");

            dontCallBS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    game.nextTurn();
                    Intent next = new Intent(CallBullshit.this, PlayerLanding.class);
                    startActivity(next);
                }
            });

        }
    }

    public void makeButtons(int players){
        RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.callBS_Relative);

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.call_button_layout);

        for(int player = 0; player < players; player++) {
            if (player != game.getUserBeforeBullshit()) {
                final int userNum = player;
                String handButtonText = userList.get(player).getName() + ": View Hand";
                String callButtonText = userList.get(player).getName() + ": Call bullshit";


                playerViewHand = new Button(this);
                playerViewHand.setText(handButtonText);

                playerCallBS = new Button(this);
                playerCallBS.setText(callButtonText);

                linLayout.addView(playerViewHand);
                linLayout.addView(playerCallBS);


                //row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));


                playerViewHand.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Log.d(TAG, "About to view " + userList.get(userNum).getName() + "'s hand:");

                        game.setViewOnlyPlayer(userNum);

                        Intent toViewOnly = new Intent(CallBullshit.this, ViewOnlyPlayerHand.class);
                        startActivity(toViewOnly);

                    }
                });


                playerCallBS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.d(TAG, "card sent to player " + userNum + "'s hand");

                        //Prepare to show the Middle's hand onscreen
                        game.setUserWhoCalledBullshit(userNum);
                        Intent toMiddleHand = new Intent(CallBullshit.this, MiddleHand.class);
                        startActivity(toMiddleHand);
                    } // end onClick
                });
            } // end "if"
        } //end for

    } //end method

    @Override
    public void onBackPressed(){
        createToast("Can't go back, nuh uh!");

    }

    public void createToast(String msg){
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call_bullshit, menu);
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
