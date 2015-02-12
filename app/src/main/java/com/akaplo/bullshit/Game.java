package com.akaplo.bullshit;


import java.util.List;


public class Game {

   List<User> userList;

    Deck deck;

    int numberOfPlayers;

    int currentUser;

    Hand middle;

    int[][] cardPictures;

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

    public int getUserInt(){
        return currentUser;
    }

    public String getUserName(){
        return userList.get(currentUser).getName();
    }

    public void setUser(int userNumber){
        if(userNumber >= numberOfPlayers) currentUser = 0;
        else this.currentUser = userNumber;
    }

    public int[][] getCardPictures(){
        return cardPictures;
    }

    public List<User> getUserList(){
        return userList;
    }
}
