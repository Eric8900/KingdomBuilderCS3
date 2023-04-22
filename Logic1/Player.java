package Logic1;
import java.util.*;
public class Player {
    public int num;
    public int chosenCard;
    public int[] locationTiles;
    public int[] roundLocTiles = new int[4];
    public int settleActionsLeft = 0;
    public int locActionsLeft = 0;
    public int uniqueRows;
    public int[] freqRows;
    public boolean[] seenRow;
    public int score;
    public int tilesLeft;
    public int maxRowCount;
    // -1 = settlements, 0, 1, 2, 3 for the location tile
    public int selectedAction = -1;
    public boolean settlementLock = false;
    public int sectorMin;//like one below but for least
    public int sectorMax;//gives the amount of settelements in the sector with the most of player's owm
    public Player(int n){
        num = n;
        tilesLeft = 40;
        locationTiles = new int[4];
        chosenCard = -1;
        score = 0;
    
    }
    public int getNumLocationTiles() {
        int c =0;
        for (int i = 0; i < 4; i++) {
            c += locationTiles[i];
        }
        return c;
    }
    public void updateRoundLocTiles() {
        for (int i = 0; i < 4; i++) {
            roundLocTiles[i] = locationTiles[i];
        }
    }
}
