package com.akaplo.bullshit;


import android.util.Log;

import java.util.List;


public class Game {

    private static final String TAG = Game.class.getSimpleName();

    private Card[] cardsSent= new Card[26];
    int numOfCards = 0;

    List<User> userList;

    Deck deck;

    int numberOfPlayers;

    int currentUser;

    User middleUser;

    Hand middleHand;

    int[][] cardPictures;

    int userWhoCalledBullshit;

    //Holds the user who was playing immediately before a bullshit was called.
    int lastUser;

    /**
     * *************************************************
     * When the one and only Game object is instantiated in NameEntry,
     * numberOfPlayers passed to this class is the ACTUAL
     * NUMBER OF PLAYERS.  MIDDLE MUST BE ACCESSED THROUGH
     * (numberOfPlayers+1)
     * **************************************************
     */
    public Game(List<User> userList, Deck d, int numOfPlayers, int[][] cardpics) {
        this.userList = userList;
        this.deck = d;
        this.numberOfPlayers = numOfPlayers;
        currentUser = 0;
        middleUser = new User(numOfPlayers+1, "The Middle");
        middleHand = middleUser.getPlayerHand();
        cardPictures = cardpics;
    }

    //Only run this method once per game, as it will start everything fresh.
    public void play() {

    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public Hand getUserHand(int userIndex) {
        return userList.get(userIndex).getPlayerHand();
    }

    public int getUserInt() {
        return currentUser;
    }

    public String getUserName() {
        return userList.get(currentUser).getName();
    }

    public void setUser() {
        if ((currentUser + 1) >= numberOfPlayers) currentUser = 0;
        else this.currentUser++;
    }

    public Hand getMiddleHand(){
        return middleHand;
    }

    public int[][] getCardPictures() {
        return cardPictures;
    }

    public void nextTurn() {
        setUser();

        for (int j = 0; j < numOfCards; j++) {
            cardsSent[j] = null;
        }
        numOfCards = 0;
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getCurrentUser(){
        return userList.get(currentUser);
    }

    public void successfulBullshitCall() {
        User badLier = userList.get(getUserBeforeBullshit());
        Log.d(TAG, "The user whose deck the cards go back to: " + badLier.getName());
        for (int j = 0; j < middleHand.getCardCount(); j++) {
            badLier.getPlayerHand().addCard(middleHand.getCard(j));
            middleHand.removeCard(j);
        }
    }

    public void putOneCardBack() {
            if(numOfCards > 0) {
                getUserHand(currentUser).addCard(cardsSent[numOfCards - 1]);
                middleHand.removeCard(cardsSent[numOfCards - 1]);
                numOfCards--;
            }
        else throw new IllegalStateException("Attempted to put a card back, but there's none to put back");
        }


    public void saveUserBeforeBullshit() {
        lastUser = getUserInt();
    }

    public int getUserBeforeBullshit() {
        return lastUser;
    }

    public Hand getHandBeforeBullshit() {
        return userList.get(lastUser).getPlayerHand();
    }


    public void moveCardToMiddle(Card c){
        middleHand.addCard(c);
        userList.get(currentUser).getPlayerHand().removeCard(c);
        numOfCards++;
    }




    public Card[] getCardsSentThisTurn() {
        Card[] cardsSent = new Card[middleHand.getCardCount()];
        int middleLast = middleHand.getCardCount()-1;
        for(int index = 0; index < numOfCards; index++){
            cardsSent[index] = middleHand.getCard(middleLast);
            middleLast--;
        }

        return cardsSent;
    }

    public int getNumberOfCardsSentThisTurn() {
        return numOfCards;
    }


    public User getMiddleUser() {
        return middleUser;
    }



    public void setUserWhoCalledBullshit(){
        int whoCalledBullshit;
        if ((currentUser + 1) >= numberOfPlayers) whoCalledBullshit= 0;
        else whoCalledBullshit = currentUser+1;

        userWhoCalledBullshit = whoCalledBullshit;
    }

    public User getUserWhoCalledBullshit(){
        return userList.get(userWhoCalledBullshit);
    }

    public void failedBullshitCall(){
        User badGuesser = getUserWhoCalledBullshit();
        for (int j = 0; j < middleHand.getCardCount(); j++) {
            badGuesser.getPlayerHand().addCard(middleHand.getCard(j));
            middleHand.removeCard(j);
        }
    }

}

