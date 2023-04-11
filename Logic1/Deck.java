package Logic1;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Integer> deck;
    public ArrayList<Integer> discard;
    public ArrayList<Integer> objectiveCards;
    public ArrayList<Integer> chosenObjectiveCards;

    //Deck constructor. Instantiates instance variables
    
    public Deck(){
        deck = new ArrayList<>();
        discard = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        chosenObjectiveCards = new ArrayList<>();
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                deck.add(i);
            }
        }
        shuffleDeck();
        for(int x = 0; x<10; x++){
            objectiveCards.add(x);
        }
        shuffleObjectiveCards();
        for (int i = 0; i < 3; i++) {
            chosenObjectiveCards.add(objectiveCards.get(i));
        }
    }

    //Shuffles the deck
    public void shuffleDeck(){Collections.shuffle(deck);}

    //Shuffles the objective cards
    public void shuffleObjectiveCards(){Collections.shuffle(objectiveCards);}

    //Refills the deck with discarded cards
    public void refillDeck(){
        deck.addAll(discard);
        discard.clear();
        shuffleDeck();
    }


    //Accessor methods

    //returns the deck arraylist
    public ArrayList<Integer> getDeck(){return deck;}

    //returns the objective cards
    public ArrayList<Integer> getObjectiveCards(){return objectiveCards;}

    //returns the discard deck
    public ArrayList<Integer> getDiscard(){return discard;}

    //returns the top terrain card and removes it from the deck
    public Integer getTerrainCard(){return deck.remove(0);}


    //Modifier methods

    //Adds a discarded card to the discard deck
    public void discard(int card){discard.add(card);}
}
