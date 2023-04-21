package Logic1;
import java.util.*;
public class Player {
    public int num;
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
    public Player(int n){
        num = n;
        tilesLeft = 40;
        locationTiles = new ArrayList<Integer>(4);
        for (int i = 0; i < 4; i++) locationTiles.add(0);
        chosenCard = -1;
        score = 0;
    
    }
    public int getNumLocationTiles() {
        int c =0;
        for (int i = 0; i < 4; i++) {
            c += locationTiles.get(i);
        }
        return c;
    }
}
