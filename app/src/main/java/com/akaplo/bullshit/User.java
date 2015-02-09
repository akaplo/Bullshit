package com.akaplo.bullshit;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2/2/15.
 */
public class User {

    public static final String TAG = User.class.getSimpleName();

    public final int DECK_SIZE = 52;

    int playerNumber;
    String name;

    Hand playerHand;

    int currentCard;

    List<Card> cardList = new ArrayList<Card>();



    public User(int pnum, String name){
        this.playerNumber = pnum;
        this.name = name;
        currentCard = 0;
        playerHand = new Hand();
    }



    //Goes through the players hand (using Cards.java methods),
    //Prints pictures of each card in the hand to the screen.
    public void showCards(){


    }




  //Begin getters and setters

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //End getters and setters


    public void initHand(Deck deck, int numberOfPlayers) {
        int numberOfCards = DECK_SIZE / numberOfPlayers;





        for(int i = 0; i < numberOfCards; i++){

                playerHand.addCard(deck.dealCard());

        }

        Log.d(TAG, "Hand size: " + playerHand.getCardCount());

/*
        String acard = deck.dealCard().toString();




*/
    }


    public Hand getPlayerHand(){
        return playerHand;
    }




    public void showCurrentCard(){

        for(int j = 0; j < playerHand.getCardCount(); j++) {

            String acard = playerHand.getCard(j).toString();
            Log.d(TAG, acard);
        }

        Log.d(TAG, "END END END END");
        Log.d(TAG, "END END END END");
        Log.d(TAG, "END END END END");
        Log.d(TAG, "END END END END");
        Log.d(TAG, "END END END END");

    }



}
