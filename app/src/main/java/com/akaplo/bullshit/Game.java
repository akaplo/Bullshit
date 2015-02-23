package com.akaplo.bullshit;


import java.util.List;


public class Game {

    private Card[] cardsSentThisTurn = new Card[26];
    int cardsThisTurn = 0;

   List<User> userList;

    Deck deck;

    int numberOfPlayers;

    int currentUser;

    Hand middle;

    int[][] cardPictures;

    //Holds the user who was playing immediately before a bullshit was called.
    int lastUser;

    /****************************************************
    When the one and only Game object is instantiated in NameEntry,
    numberOfPlayers passed to this class is the ACTUAL
    NUMBER OF PLAYERS.  MIDDLE MUST BE ACCESSED THROUGH
     (numberOfPlayers+1)
    ****************************************************/
    public Game(List<User> userList, Deck d, int numOfPlayers, int[][] cardpics){
        this.userList = userList;
        this.deck = d;
        this. numberOfPlayers = numOfPlayers;
        currentUser = 0;
        middle = new Hand();
        cardPictures = cardpics;
    }

    //Only run this method once per game, as it will start everything fresh.
    public void play(){

    }

    public int getNumberOfPlayers(){ return numberOfPlayers; }

    public Hand getUserHand(int userIndex){
        return userList.get(userIndex).getPlayerHand();
    }

    public int getUserInt(){
        return currentUser;
    }

    public String getUserName(){
        return userList.get(currentUser).getName();
    }

    public void setUser(){
        if((currentUser+1) >= numberOfPlayers) currentUser = 0;
        else this.currentUser++;
    }

    public void setUserMiddle(){
       currentUser = numberOfPlayers;
    }

    public boolean playerIsMiddle(){
        if(currentUser == (numberOfPlayers)) return true;
        else return false;
    }

    public int[][] getCardPictures(){
        return cardPictures;
    }

    public void nextTurn(){
        setUser();
        cardsThisTurn = 0;
        for(int j = 0; j < cardsThisTurn; j++){
            cardsSentThisTurn[j] = null;
        }
    }

    public List<User> getUserList(){
        return userList;
    }

    public void bullshitTrue() {
        for(int j = 0; j < cardsThisTurn; j++){
            getHandBeforeBullshit().addCard(cardsSentThisTurn[j]);
            middle.removeCard(cardsSentThisTurn[j]);
        }
    }
    public void setUserBeforeBullshit(){
        lastUser = getUserInt();
    }

    public Hand getHandBeforeBullshit(){
        return userList.get(getUserBeforeBullshit()).getPlayerHand();
    }

    public int getUserBeforeBullshit(){
        if(lastUser+1 != (numberOfPlayers+1))
        return lastUser+1;
        else return 0;
    }

    public void addCardSentThisTurn(Card c){
        cardsSentThisTurn[cardsThisTurn] = c;
        cardsThisTurn++;
    }

    public Card[] getCardsSentThisTurn(){
        return cardsSentThisTurn;
    }

    public int getNumberOfCardsSentThisTurn(){
        return cardsThisTurn;
    }

}

