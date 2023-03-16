package Logic1;

import java.awt.image.BufferedImage;
import java.util.*;

public class GameBoard {
    //this class will be the graph
    public static GameHex[][] GameMatrix;

    public GameBoard(int[][] init, BufferedImage board, int startX, int startY) {
        GameMatrix = new GameHex[init.length][init[0].length];
        for (int i = 0; i < init.length; i++) {
            for (int j = 0; j < init[i].length; j++) {
                GameMatrix[i][j] = new GameHex(-1, -1, init[i][j], null, null, null, null, null, null);
            }
        }
        //6 pointers
        for(int i = 0; i<GameMatrix.length;i++){
            for(int j = 0; j<GameMatrix[i].length; j++){
                addNeighbors(GameMatrix[i][j], i, j);
            }
        }
        setCoords(board,startX, startY);
    }

    /*monkey idea for init:
     * init a 2d array of every game hex with an i and j
     * each hex will have an i and j
     * every time we add a neighbor, we brute force through the 2d array and find the i and j
     *
     *
     * OR JUST USE THEIR INDEX IN THE ARRAY? POSSIBLY?
     */
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

    public void setCoords(BufferedImage board, int startX, int startY){
        double width = 2 * board.getWidth();//total width of the rectangular board
        double hexSideLength = width/30;//sidelength of the hexagon is 1/30th of the width (this has been verified)
        int space_x = 2 * (int)(hexSideLength * Math.sin(Math.toRadians(60)));//horiztonal gap between the centers of two adj hexagons
        int space_y = 3 * (int)hexSideLength/2;//vertical gape between the centers of two adj hexagons (one on top and one below)
        int first_x = startX + space_x;
        int first_y = startY + space_x/2 + (int)hexSideLength/2;
        //first_x and first_y represent the x y coordinates of the first hexagon
        GameMatrix[0][0].x = first_x;
        GameMatrix[0][0].y = first_y;
        for(int i = 0; i<GameMatrix.length; i++){
            for(int j = 0; j<GameMatrix[i].length; j++){
               GameMatrix[i][j].x = first_x + j * space_x;
               GameMatrix[i][j].y = first_y + i * space_y;
               if(i % 2 == 1){
                   GameMatrix[i][j].x += space_x/2;
               }
            }
        }
    }

    public boolean inBounds(int i, int j) {
        return i >= 0 && i < GameMatrix.length && j >= 0 && j < GameMatrix[i].length;
    }
}
