package com.akaplo.bullshit;


import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Game {

    private static final String TAG = Game.class.getSimpleName();

    private List<Card> cardsSentList = new ArrayList<>();
    private Card[] cardsSentArray = new Card[26];

    int numOfCards = 0;

    List<User> userList;

    Deck deck;

    int numberOfPlayers;

    int currentUser;

    User middleUser;

    Hand middleHand;

    int[][] cardPictures;

    int userWhoCalledBullshit;

    private Card cardJustRemoved;

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


        cardsSentList.clear();

        /* Deprecated code: this was used then cardsSend was an array
        for (int j = 0; j < numOfCards; j++) {
            cardsSentArray[j] = null;
        }
        */

        numOfCards = 0;
    }

    public List<User> getUserList() {
        return userList;
    }

    public User getCurrentUser(){
        return userList.get(currentUser);
    }

    public void putOneCardBack() {
            if(numOfCards > 0) {

                userList.get(currentUser).getPlayerHand().addCard(cardJustRemoved);
                middleHand.removeCard(cardJustRemoved);

            //Previous logic for removing the last card sent to the middle during a turn
                //getUserHand(currentUser).addCard(cardsSent[numOfCards - 1]);
                //middleHand.removeCard(cardsSent[numOfCards - 1]);

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
        cardJustRemoved = c;
        cardsSentList.add(c);
        //cardsSentArray[numOfCards] = c;
        numOfCards++;
        Log.d(TAG, numOfCards + " cards have been sent this turn");
    }

    public List<Card> getcardsSentLastTurnList(){
        return cardsSentList;
    }

    public Card[] getcardsSentLastTurnArray(){
        return cardsSentArray;
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


    public void printAllHands(){
        for(int player = 0; player < getNumberOfPlayers(); player++) {
            Card[] playersCards = userList.get(player).getPlayerHand().toArray();
            Log.d(TAG, userList.get(player).getName() + "'s hand: ");
            for(int j = 0; j <  userList.get(player).getPlayerHand().getCardCount(); j++) {
                Log.d(TAG, playersCards[j] + ", ");
            }
        }
        Card[] middlesCards = getMiddleHand().toArray();
        Log.d(TAG, "Middle's hand: ");
        for(int j = 0; j < getMiddleHand().getCardCount(); j++){
            Log.d(TAG, middlesCards[j] + ", ");
        }
    }

    public void setUserWhoCalledBullshit(){
        int whoCalledBullshit;
        if ((currentUser + 1) >= numberOfPlayers) whoCalledBullshit= 0;
        else whoCalledBullshit = currentUser+1;

        userWhoCalledBullshit = whoCalledBullshit;
    }

    public void setUserWhoCalledBullshit(int userNum){
        userWhoCalledBullshit = userNum;
    }

    public User getUserWhoCalledBullshit(){
        return userList.get(userWhoCalledBullshit);
    }

    /*
    Puts all the middle's cards into the hand of the player who called Bullshit and was wrong.

    So, say C puts in 1 Ace and A calls Bullshit.  If C wasn't lying, this method gets called
        and the entire middleHand goes into A's hand.
    */
    public void failedBullshitCall(){
        Hand badGuesserHand = getUserWhoCalledBullshit().getPlayerHand();

        List<Card> middleList = middleHand.toList();

        for(ListIterator<Card> iter = middleList.listIterator(); iter.hasNext(); ){
            Card element = iter.next();

            badGuesserHand.addCard(element);
        }

        middleHand.clear();

        Log.d(TAG, "Middle now has " + middleHand.getCardCount() + " cards.");

        Log.d(TAG, "C now has " + badGuesserHand.getCardCount() + " cards");

        /*  DEPRECATED

        middleHand = getMiddleHand();

        ArrayList<Card> middleCards = getMiddleHand().getHand();

      //  Log.d(TAG, "The middle has " + middleHand.getCardCount() + "total cards before removing them.");
      //  Log.d(TAG, "These cards are: ");

        for(Card c : middleCards){
            badGuesserHand.addCard(c);
            middleHand.removeCard(c);
        }

        if(middleHand.getCardCount() != 0) throw new IllegalStateException("'failedBullshitCall': loop to empty middle's hand doesn't work");

        */

        /* Deprecated code to remove all cards from middle and put into user's hand who called bullshit
        for (int j = 0; j <= middleHand.getCardCount(); j++) {
            Log.d(TAG, middleHand.getCard(j).toString());
            badGuesserHand.getPlayerHand().addCard(middleHand.getCard(j));
            middleHand.removeCard(j);
        }

        */

    }

    public void successfulBullshitCall() {
        Hand badLierHand = userList.get(getUserBeforeBullshit()).getPlayerHand();

        Log.d(TAG, "Before anything, c has " + badLierHand.getCardCount() + " cards");

        List<Card> middleList = middleHand.toList();
        //Log.d(TAG, "The user whose deck the cards go back to: " + badLier.());

        for (ListIterator<Card> iter = middleList.listIterator(); iter.hasNext(); ) {
            Card element = iter.next();

            badLierHand.addCard(element);
            // 1 - can call methods of element
            // 2 - can use iter.remove() to remove the current element from the list
            // 3 - can use iter.add(...) to insert a new element into the list
            //     between element and iter->next()
            // 4 - can use iter.set(...) to replace the current element

            // ...
        }

        middleHand.clear();

        Log.d(TAG, "Middle now has " + middleHand.getCardCount() + " cards.");

        Log.d(TAG, "C now has " + badLierHand.getCardCount() + " cards");

        /*

        middleHand = getMiddleHand();


        ArrayList<Card> middleCards = getMiddleHand().getHand();

        Iterator e = middleCards.iterator();
        int index = 0;
        Log.d(TAG, middleCards.size() + "");



        while(e.hasNext()){
            badLierHand.addCard(middleHand.getCard(index));
            index++;
            e.next();
        }
        badLierHand.addCard(middleHand.getCard(0));
        middleHand.clear();

        if(middleHand.getCardCount() != 0) throw new IllegalStateException("'successfulBullshitCall': Code to empty middle's hand doesn't work");


*/
        /*Deprecated code for putting all the middle's cards into the hand of the user who was bullshitting
        for (int j = 0; j <= middleHand.getCardCount(); j++) {
            badLier.getPlayerHand().addCard(middleHand.getCard(j));
            middleHand.removeCard(j);
        }
        */
    }

    public void dealAllCards(){

        int numOfPlayers = this.numberOfPlayers;

        //Shuffle the deck 3 times before dealing
        int shuffle = 0;
        while(shuffle < 3){
            deck.shuffle();
            shuffle++;
        }

        int tempUser = 0;

        //Deal out all 52 cards, rotating throughout the list of users
        //AKA Deal 1 card to user 1, deal the next card to user 2, etc.
        for(int j = 0; j < 52; j++){
            userList.get(tempUser).getPlayerHand().addCard(deck.dealCard());
            if ((tempUser + 1) >= numOfPlayers) tempUser = 0;
            else tempUser++;
        }

        for(User u : userList) u.printAllCards();


    }

}

