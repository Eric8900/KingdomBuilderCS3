package Logic1;
import java.util.*;
public class Player {
    public int chosenCard;
    public ArrayList<Integer> locationTiles;
    public int actionsLeft;
    public int uniqueRows;
    public int[] freqRows;
    public boolean[] seenRow;
    public int score;
    public int tilesLeft;
    public int maxRowCount;
    public int sectorMin;//like one below but for least
    public int sectorMax;//gives the amount of settelements in the sector with the most of player's owm
    public Player(){
        tilesLeft = 40;
        locationTiles = new ArrayList<Integer>(4);
        locationTiles.add(0);
        locationTiles.add(0);
        locationTiles.add(0);
        locationTiles.add(0);
        chosenCard = 1;
        score = 0;
    
    }

}
