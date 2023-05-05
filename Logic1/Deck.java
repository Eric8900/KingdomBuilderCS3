package Logic1;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Integer> deck;
    private ArrayList<Integer> discard;
    private ArrayList<Integer> objectiveCards;
    private ArrayList<Integer> chosenObjectiveCards;

    //Deck constructor. Instantiates instance variables
    
    public Deck(){
        deck = new ArrayList<>();
        discard = new ArrayList<>();
        objectiveCards = new ArrayList<>();
        chosenObjectiveCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) deck.add(0);
        for (int i = 0; i < 5; i++) deck.add(1);
        for (int i = 0; i < 5; i++) deck.add(3);
        for (int i = 0; i < 5; i++) deck.add(4);
        for (int i = 0; i < 5; i++) deck.add(6);
        shuffleDeck();
        for(int x = 0; x<10; x++){
            objectiveCards.add(x);
        }
        shuffleObjectiveCards();
        for (int i = 0; i < 3; i++) {
            chosenObjectiveCards.add(objectiveCards.get(i));
        }
        //testing
        chosenObjectiveCards.remove(0);
        chosenObjectiveCards.add(4);
        chosenObjectiveCards.remove(1);
        chosenObjectiveCards.add(9);
        chosenObjectiveCards.remove(0);
        chosenObjectiveCards.add(6);
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
    public int getTop() {
        if (deck.size() == 0){
            refillDeck();
        }
        return deck.get(0);
    }

    public void discardTop() {
        discard.add(deck.get(0));
        deck.remove(0);
    }

    //returns the deck arraylist
    public ArrayList<Integer> getDeck(){return deck;}

    //returns the chosen objective cards
    public ArrayList<Integer> getChosenObjectiveCards(){return chosenObjectiveCards;}

    //returns the discard deck
    public ArrayList<Integer> getDiscard(){return discard;}

    //returns the top terrain card and removes it from the deck
    public Integer getTerrainCard(){return deck.remove(0);}


    //Modifier methods

    //Adds a discarded card to the discard deck
    public void discard(int card){discard.add(card);}
}
