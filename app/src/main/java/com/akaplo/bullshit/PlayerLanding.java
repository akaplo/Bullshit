package com.akaplo.bullshit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PlayerLanding extends ActionBarActivity {

    private static final String TAG = NameEntry.class.getSimpleName();

    TextView landingName;
    TextView landingTurn;
    TextView landingTap;

    RelativeLayout pageLayout;

    String turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_landing);

        landingName = (TextView) findViewById(R.id.player_landing_name);
        landingTurn = (TextView) findViewById(R.id.your_turn);
        landingTap = (TextView) findViewById(R.id.tap_to_continue);

        pageLayout = (RelativeLayout) findViewById(R.id.landing_layout);

        turn = NameEntry.game.getUserName();

        landingName.setText(turn);

        Log.d(TAG, "Everything on landing set, ready for tap.");

        pageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPlayerHand = new Intent(PlayerLanding.this, PlayerHand.class);
                startActivity(toPlayerHand);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_landing, menu);
        
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
