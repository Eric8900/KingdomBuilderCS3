package Logic1;
import java.util.*;

import Graphics.GamePanel;

public class PlayLocationTile {
    int curr = GameState.currentPlayer;
    int locTile = GamePanel.locTiles[GameState.players.get(curr).selectedAction];
    public PlayLocationTile() {
        //oracle
        if (locTile == 7) {
            highlightOracle();
        }
        //farm
        if (locTile == 8) {
            highlightFarm();    
        }
        //oasis
        if (locTile == 15) {
            highlightOasis();
        }
        //tower
        if (locTile == 9) {
            highlightTower();
        }
        //tavern
        if (locTile == 10) {
            highlightTavern();
        }
        //----------------------
        //barn
        if (locTile == 11) {
            
        }
        //harbor
        if (locTile == 12) {
            
        }
        //paddock
        if (locTile == 13) {
            
        }
    }
    public void highlightOracle() {
        boolean[][] highlighted = new boolean[20][20];
        ArrayList<Pair> placed = GameState.board.getPlacedForPlayer(curr, GameState.players.get(curr).chosenCard);
        GameHex[][] board = GameState.board.GameMatrix;
        boolean ok = false;
        if (placed != null) {
            int c = 0;
            for (int i = 0; i < placed.size(); i++) {
                GameHex a = GameState.board.GameMatrix[placed.get(i).first][placed.get(i).second];
                for (int j = 0; j < a.neighbors.length; j++) {
                    if (a.neighbors[j] != null && a.neighbors[j].terr == GameState.players.get(curr).chosenCard && a.neighbors[j].player == -1) {
                        highlighted[a.neighbors[j].pos.first][a.neighbors[j].pos.second] = true;
                        c++;
                    }
                }
            }
            if (c == 0) ok = true;
        }
        else {
            ok = true;
        }
        if (ok) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (board[i][j].terr == GameState.players.get(curr).chosenCard && board[i][j].player == -1) {
                        highlighted[i][j] = true;
                    }
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
    public void highlightFarm() {
        boolean[][] highlighted = new boolean[20][20];
        ArrayList<Pair> placed = GameState.board.getPlacedForPlayer(curr, 6);
        GameHex[][] board = GameState.board.GameMatrix;
        boolean ok = false;
        if (placed != null) {
            int c = 0;
            for (int i = 0; i < placed.size(); i++) {
                GameHex a = GameState.board.GameMatrix[placed.get(i).first][placed.get(i).second];
                for (int j = 0; j < a.neighbors.length; j++) {
                    if (a.neighbors[j] != null && a.neighbors[j].terr == 6 && a.neighbors[j].player == -1) {
                        highlighted[a.neighbors[j].pos.first][a.neighbors[j].pos.second] = true;
                        c++;
                    }
                }
            }
            if (c == 0) ok = true;
        }
        else {
            ok = true;
        }
        if (ok) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (board[i][j].terr == 6 && board[i][j].player == -1) {
                        highlighted[i][j] = true;
                    }
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
    public void highlightOasis() {
        boolean[][] highlighted = new boolean[20][20];
        ArrayList<Pair> placed = GameState.board.getPlacedForPlayer(curr, 0);
        GameHex[][] board = GameState.board.GameMatrix;
        boolean ok = false;
        if (placed != null) {
            int c = 0;
            for (int i = 0; i < placed.size(); i++) {
                GameHex a = GameState.board.GameMatrix[placed.get(i).first][placed.get(i).second];
                for (int j = 0; j < a.neighbors.length; j++) {
                    if (a.neighbors[j] != null && a.neighbors[j].terr == 0 && a.neighbors[j].player == -1) {
                        highlighted[a.neighbors[j].pos.first][a.neighbors[j].pos.second] = true;
                        c++;
                    }
                }
            }
            if (c == 0) ok = true;
        }
        else {
            ok = true;
        }
        if (ok) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (board[i][j].terr == 0 && board[i][j].player == -1) {
                        highlighted[i][j] = true;
                    }
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
    public void highlightTower() {
        boolean[][] highlighted = new boolean[20][20];
        GameHex[][] board = GameState.board.GameMatrix;
        //edge is i == 0 || i == 19 || j == 0 || j == 19 
        int c = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (i <= 1 || i >= 18 || j <= 1 || j >= 18 && !board[i][j].isLocationTile && board[i][j].player == curr) {
                    for (int a = 0; a < board[i][j].neighbors.length; a++) {
                        if (board[i][j].neighbors[a] == null) continue;
                        Pair p = board[i][j].neighbors[a].pos;
                        if ((p.first == 0 || p.first == 19 || p.second == 0 || p.second == 19) && board[i][j].neighbors[a].player == -1 && board[i][j].terr != 2 && board[i][j].terr != 5) {
                            c++;
                            highlighted[p.first][p.second] = true;
                        }
                    }
                }
            }
        }

        if (c > 0) {
            GamePanel.currentHighlights = highlighted;
            return;
        }

        for (int i = 0; i < 20; i+=19) {
            for (int j = 0; j < 20; j++) {
                //(i, j) (j, i)
                if (board[i][j].player == -1 && board[i][j].terr != 2 && board[i][j].terr != 5) {
                    highlighted[i][j] = true;
                }
                if (board[j][i].player == -1 && board[j][i].terr != 2 && board[j][i].terr != 5) {
                    highlighted[j][i] = true;
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
    public void highlightTavern() {
        boolean[][] highlighted = new boolean[20][20];
        GameHex[][] board = GameState.board.GameMatrix;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (board[i][j].player == curr) {
                    // check num adj on all 6 sides up to 3; hard coding goes crazy..............................
                    int c = 1;
                    GameHex head = board[i][j];
                    //ENTERING DANGER ZONE: YOU ARE WARNED; The area below is know to cause brain damage
                    //tl
                    for (int a = 0; a < 2; a++) {
                        if (head.topLeft != null && head.topLeft.player == curr) {
                            c++;
                            head = head.topLeft;
                        }
                    }
                    if (c == 3) {
                        if (head.topLeft != null && head.topLeft.terr != 2 && head.topLeft.terr != 5 && head.topLeft.terr <= 6 && head.topLeft.player == -1) {
                            highlighted[head.topLeft.pos.first][head.topLeft.pos.second] = true;
                        }
                    }
                    head = board[i][j];
                    c = 1;
                    //tr
                    for (int a = 0; a < 2; a++) {
                        if (head.topRight != null && head.topRight.player == curr) {
                            c++;
                            head = head.topRight;
                        }
                    }
                    if (c == 3) {
                        if (head.topRight != null && head.topRight.terr != 2 && head.topRight.terr != 5 && head.topRight.terr <= 6 && head.topRight.player == -1) {
                            highlighted[head.topRight.pos.first][head.topRight.pos.second] = true;
                        }
                    }
                    head = board[i][j];
                    c = 1;
                    //l
                    for (int a = 0; a < 2; a++) {
                        if (head.left != null && head.left.player == curr) {
                            c++;
                            head = head.left;
                        }
                    }
                    if (c == 3) {
                        if (head.left != null && head.left.terr != 2 && head.left.terr != 5 && head.left.terr <= 6 && head.left.player == -1) {
                            highlighted[head.left.pos.first][head.left.pos.second] = true;
                        }
                    }
                    head = board[i][j];
                    c = 1;
                    //r
                    for (int a = 0; a < 2; a++) {
                        if (head.right != null && head.right.player == curr) { 
                            c++;
                            head = head.right;
                        }
                    }
                    if (c == 3) {
                        if (head.right != null && head.right.terr != 2 && head.right.terr != 5 && head.right.terr <= 6 && head.right.player == -1) {
                            highlighted[head.right.pos.first][head.right.pos.second] = true;
                        }
                    }
                    head = board[i][j];
                    c = 1;
                    //bl
                    for (int a = 0; a < 2; a++) {
                        if (head.botLeft != null && head.botLeft.player == curr) {
                            c++;
                            head = head.botLeft;
                        }
                    }
                    if (c == 3) {
                        if (head.botLeft != null && head.botLeft.terr != 2 && head.botLeft.terr != 5 && head.botLeft.terr <= 6 && head.botLeft.player == -1) {
                            highlighted[head.botLeft.pos.first][head.botLeft.pos.second] = true;
                        }
                    }
                    head = board[i][j];
                    c = 1;
                    //br
                    for (int a = 0; a < 2; a++) {
                        if (head.botRight != null && head.botRight.player == curr) {
                            c++;
                            head = head.botRight;
                        }
                    }
                    if (c == 3) {
                        if (head.botRight != null && head.botRight.terr != 2 && head.botRight.terr != 5 && head.botRight.terr <= 6 && head.botRight.player == -1) {
                            highlighted[head.botRight.pos.first][head.botRight.pos.second] = true;
                        }
                    }
                    //EXITING DANGER ZONE
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
}
