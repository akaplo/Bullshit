package com.akaplo.bullshit;

import android.util.Log;

/**
 * Created by Aaron on 2/3/15.
 */
public class DeckofCards {

        public static final String TAG = DeckofCards.class.getSimpleName();

        public static final int NCARDS = 52;

        private Cards[] deckOfCards;         // Contains all 52 cards
        private int currentCard;            // deal THIS card in deck


   public DeckofCards()
        {
            deckOfCards = new Cards[ NCARDS ];

            int i = 0;

            for ( int suit = Cards.SPADE; suit <= Cards.DIAMOND; suit++ )
                for ( int rank = 1; rank <= 13; rank++ )
                    deckOfCards[i++] = new Cards(suit, rank);

            currentCard = 0;
        }

        /* ---------------------------------
          shuffle(n): shuffle the deck
          --------------------------------- */
        public void shuffle(int n)
        {
            int i, j, k;

            for ( k = 0; k < n; k++ )
            {
                i = (int) ( NCARDS * Math.random() );  // Pick 2 random cards
                j = (int) ( NCARDS * Math.random() );  // in the deck

   	     /* ---------------------------------
   		swap these randomly picked cards
   		--------------------------------- */
                Cards tmp = deckOfCards[i];
                deckOfCards[i] = deckOfCards[j];
                deckOfCards[j] = tmp;;
            }

            currentCard = 0;   // Reset current card to deal
        }

        public boolean cardsLeft(){
            if ( currentCard < NCARDS ) return true;
            else return false;
        }

        /* -------------------------------------------
          deal(): deal deckOfCards[currentCard] out
          ------------------------------------------- */
        public Cards deal()
        {
            if ( currentCard < NCARDS )
            {
                return ( deckOfCards[ currentCard++ ] );
            }
            else
            {
                Log.d(TAG, "Out of cards error");
                return ( null );  // Error;
            }
        }

        public String toString()
        {
            String s = "";
            int k;

            k = 0;
            for ( int i = 0; i < 4; i++ )
            {
                for ( int j = 1; j <= 13; j++ )
                    s += (deckOfCards[k++] + " ");

                s += "\n";
            }
            return ( s );
        }
    }

