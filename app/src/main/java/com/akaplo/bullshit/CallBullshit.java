package com.akaplo.bullshit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CallBullshit extends ActionBarActivity {

    //TODO:Implement the CallBullshit.xml ability for bullshit to be called by a specific user

    final String TAG = "CALLBS";

    TextView callBSEditText;

    Button callBS;
    Button dontCallBS;

    Game game = NameEntry.game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_bullshit);

        callBSEditText = (TextView) findViewById(R.id.callBSTV);

        callBS = (Button) findViewById(R.id.callBS);
        dontCallBS = (Button) findViewById(R.id.dontCallBS);

        if(savedInstanceState == null){

            callBSEditText.setText(game.getUserName() + " has finished turn.  Call bullshit?");

            callBS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Prepare to show the Middle's hand onscreen
                       game.setUserWhoCalledBullshit();
                    Intent toMiddleHand= new Intent(CallBullshit.this, MiddleHand.class);
                    startActivity(toMiddleHand);
                }
            });



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
