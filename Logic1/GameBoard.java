package Logic1;

import java.awt.image.BufferedImage;
import java.util.*;

public class GameBoard {
    //this class will be the graph
    public static GameHex[][] GameMatrix;

    public GameBoard(int[][] init, Pair[][] centers) {
        GameMatrix = new GameHex[init.length][init[0].length];
        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[i].length; j++) {
                GameMatrix[i][j] = new GameHex(centers[i][j].first, centers[i][j].second, init[i][j], null, null, null, null, null, null, i, j);
            }
        }
        //6 pointers
        for(int i = 0; i<GameMatrix.length;i++){
            for(int j = 0; j<GameMatrix[i].length; j++){
                addNeighbors(GameMatrix[i][j], i, j);
                GameMatrix[i][j].updateNeighbors();
                GameMatrix[i][j].id = 20 * i + j;
            }
        }
    }
    public void addNeighbors(GameHex node, int i, int j) {
        /*
        even = (i,j), (i - 1, j), (i + 1, j), (i, j + 1), (i, j - 1), (i + 1, j - 1), (i - 1, j - 1)
        odd = (i,j), (i - 1, j), (i + 1, j), (i, j + 1), (i, j - 1), (i + 1, j + 1), (i - 1, j + 1)
         */
           //neighbors regardless even or odd
            if (inBounds(i, j - 1)) {
                node.left = GameMatrix[i][j - 1];
            }
            if (inBounds(i, j + 1)) {
                node.right = GameMatrix[i][j + 1];
            }

            //even neighbors
            if(i % 2 == 0) {
                if (inBounds(i - 1, j - 1)) {
                    node.topLeft = GameMatrix[i - 1][j - 1];
                }
                if (inBounds(i + 1, j - 1)) {
                    node.botLeft = GameMatrix[i + 1][j - 1];
                }
                if (inBounds(i + 1, j)) {
                    node.botRight = GameMatrix[i + 1][j];
                }
                if (inBounds(i - 1, j)) {
                    node.topRight = GameMatrix[i - 1][j];
                }
            }else {
                //odd neighbors
                if (inBounds(i - 1, j)) {
                   node.topLeft = GameMatrix[i - 1][j];
                }
                if(inBounds(i - 1, j + 1)){
                    node.topRight = GameMatrix[i - 1][j + 1];
                }
                if(inBounds(i + 1, j)){
                    node.botLeft = GameMatrix[i + 1][j];
                }
                if(inBounds(i + 1, j + 1)){
                    node.botRight = GameMatrix[i + 1][j + 1];
                }
            }
    }
    public static ArrayList<Pair> getPlacedForPlayer(int p, int terr) {
        ArrayList<Pair> placed = new ArrayList<>();
        int c = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (GameMatrix[i][j].player == p && (GameMatrix[i][j].terr == terr || terr == -1)) {
                    c++;
                    placed.add(new Pair(i, j));
                }
            }
        }
        if (c == 0) {
            return null;
        }
        else {
            return placed;
        }
    }

    public boolean inBounds(int i, int j) {
        return i >= 0 && i < GameMatrix.length && j >= 0 && j < GameMatrix[i].length;
    }
}
