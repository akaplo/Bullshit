package com.akaplo.bullshit;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 2/3/15.
 */
public class Game {

   List<User> userList;

    Deck deck;

    int numberOfPlayers;

    int currentUser;

    Hand middle;

    /*Drawable*/int[][] cardPictures;

    public Game(List<User> userList, Deck d, int num, /*Drawable*/int[][] cardpics){
        this.userList = userList;
        this.deck = d;
        this. numberOfPlayers = num;
        currentUser = 0;
        middle = new Hand();
        cardPictures = cardpics;
    }

    //Only run this method once per game, as it will start everything fresh.
    public void play(){

    }

    public int getUserInt(){
        return currentUser;
    }

    public String getUserName(){
        return userList.get(currentUser).getName();
    }

    public /*Drawable*/int[][] getCardPictures(){
        return cardPictures;
    }

    public List<User> getUserList(){
        return userList;
    }
}
