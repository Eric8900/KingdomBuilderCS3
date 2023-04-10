package Logic1;
import java.util.*;
public class Player {
    public int chosenCard;
    public ArrayList<Integer> locationTiles;
    public int actionsLeft;
    public int uniqueRows;
    public int[] freqRows;
    public boolean[] seenRow;

    public int tilesLeft;
    public Player(){
        tilesLeft = 40;
        locationTiles = new ArrayList<Integer>(4);
        locationTiles.add(0);
        locationTiles.add(0);
        locationTiles.add(0);
        locationTiles.add(0);
    
    }

}
