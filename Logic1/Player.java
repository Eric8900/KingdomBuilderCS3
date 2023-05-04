package Logic1;
import java.util.*;
public class Player implements Comparable<Player> {
    public int num;
    public int chosenCard;
    public int[] locationTiles;
    public int[] roundLocTiles = new int[4];
    public int settleActionsLeft = 0;
    public int locActionsLeft = 0;
    public int score;
    public int tilesLeft;
    // -1 = settlements, 0, 1, 2, 3 for the location tile
    public int selectedAction = -1;
    public boolean settlementLock = false;
    public int[] sectorCount = new int[4];
    public int[] getScore = new int[11];//getScore[i] returns the amount of points the player got from score i, 10 gives u the amount u got from castle ehx adj
    public Player(int n){
        num = n;
        tilesLeft = 20;
        locationTiles = new int[4];
        chosenCard = -1;
        score = 0;
    }
    public int getNumLocationTiles() {
        int c = 0;
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
    public int compareTo(Player other){
        return other.score - this.score;
    }
}
