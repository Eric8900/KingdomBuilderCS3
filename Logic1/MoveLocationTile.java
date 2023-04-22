package Logic1;
import Graphics.*;
import java.util.*;

public class MoveLocationTile {
    int curr = GameState.currentPlayer;
    int locTile = GamePanel.locTiles[GameState.players.get(curr).selectedAction];
    GameHex[][] board = GameBoard.GameMatrix;
    GameHex c;
    boolean[][] highlighted = new boolean[20][20];
    public MoveLocationTile(GameHex chosen) {
        if (chosen == null) return;
        c = chosen;
        //barn
        if (locTile == 11) {
            highlightBarn();
        }
        //harbor
        if (locTile == 12) {
            highlightHarbor();            
        }
        //paddock
        if (locTile == 13) {
            highlightPaddock();
        }
    }
    public void highlightBarn() {
        ArrayList<Pair> placed = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (GameBoard.GameMatrix[i][j].player == curr && GameBoard.GameMatrix[i][j].terr == GameState.players.get(curr).chosenCard && !onCurr(i, j)) {
                    placed.add(new Pair(i, j));
                }
            }
        }
        boolean ok = false;
        if (placed.size() > 0) {
            int cnt = 0;
            for (int i = 0; i < placed.size(); i++) {
                GameHex a = GameState.board.GameMatrix[placed.get(i).first][placed.get(i).second];
                for (int j = 0; j < a.neighbors.length; j++) {
                    if (a.neighbors[j] != null && a.neighbors[j].terr == GameState.players.get(curr).chosenCard && a.neighbors[j].player == -1 && !onCurr(a.neighbors[j].pos.first, a.neighbors[j].pos.second)) {
                        highlighted[a.neighbors[j].pos.first][a.neighbors[j].pos.second] = true;
                        cnt++;
                    }
                }
            }
            if (cnt == 0) ok = true;
        }
        else {
            ok = true;
        }
        if (ok) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (board[i][j].terr == GameState.players.get(curr).chosenCard && board[i][j].player == -1 && !onCurr(i,j)) {
                        highlighted[i][j] = true;
                    }
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
    public void highlightHarbor() {
        ArrayList<Pair> placed = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (GameBoard.GameMatrix[i][j].player == curr && !onCurr(i,j)) {
                    placed.add(new Pair(i, j));
                }
            }
        }
        boolean ok = false;
        if (placed.size() > 0) {
            int cnt = 0;
            for (int i = 0; i < placed.size(); i++) {
                GameHex a = GameState.board.GameMatrix[placed.get(i).first][placed.get(i).second];
                for (int j = 0; j < a.neighbors.length; j++) {
                    if (a.neighbors[j] != null && a.neighbors[j].terr == 5 && a.neighbors[j].player == -1 && !onCurr(a.neighbors[j].pos.first, a.neighbors[j].pos.second)) {
                        highlighted[a.neighbors[j].pos.first][a.neighbors[j].pos.second] = true;
                        cnt++;
                    }
                }
            }
            if (cnt == 0) ok = true;
        }
        else {
            ok = true;
        }
        if (ok) {
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (board[i][j].terr == 5 && board[i][j].player == -1 && !onCurr(i,j)) {
                        highlighted[i][j] = true;
                    }
                }
            }
        }
        GamePanel.currentHighlights = highlighted;
    }
    public void highlightPaddock() {
        int cnt = 2;
        GameHex head = c;
        //ENTERING DANGER ZONE: YOU ARE WARNED; The area below is know to cause brain damage
        //tl
        for (int a = 0; a < 1; a++) {
            if (head.topLeft != null) {
                cnt++;
                head = head.topLeft;
            }
        }
        if (cnt == 3) {
            if (head.topLeft != null && head.topLeft.terr != 2 && head.topLeft.terr != 5 && head.topLeft.terr <= 6 && head.topLeft.player == -1) {
                highlighted[head.topLeft.pos.first][head.topLeft.pos.second] = true;
            }
        }
        head = c;
        cnt = 2;
        //tr
        for (int a = 0; a < 1; a++) {
            if (head.topRight != null) {
                cnt++;
                head = head.topRight;
            }
        }
        if (cnt == 3) {
            if (head.topRight != null && head.topRight.terr != 2 && head.topRight.terr != 5 && head.topRight.terr <= 6 && head.topRight.player == -1) {
                highlighted[head.topRight.pos.first][head.topRight.pos.second] = true;
            }
        }
        head = c;
        cnt = 2;
        //l
        for (int a = 0; a < 1; a++) {
            if (head.left != null) {
                cnt++;
                head = head.left;
            }
        }
        if (cnt == 3) {
            if (head.left != null && head.left.terr != 2 && head.left.terr != 5 && head.left.terr <= 6 && head.left.player == -1) {
                highlighted[head.left.pos.first][head.left.pos.second] = true;
            }
        }
        head = c;
        cnt = 2;
        //r
        for (int a = 0; a < 1; a++) {
            if (head.right != null) { 
                cnt++;
                head = head.right;
            }
        }
        if (cnt == 3) {
            if (head.right != null && head.right.terr != 2 && head.right.terr != 5 && head.right.terr <= 6 && head.right.player == -1) {
                highlighted[head.right.pos.first][head.right.pos.second] = true;
            }
        }
        head = c;
        cnt = 2;
        //bl
        for (int a = 0; a < 1; a++) {
            if (head.botLeft != null) {
                cnt++;
                head = head.botLeft;
            }
        }
        if (cnt == 3) {
            if (head.botLeft != null && head.botLeft.terr != 2 && head.botLeft.terr != 5 && head.botLeft.terr <= 6 && head.botLeft.player == -1) {
                highlighted[head.botLeft.pos.first][head.botLeft.pos.second] = true;
            }
        }
        head = c;
        cnt = 2;
        //br
        for (int a = 0; a < 1; a++) {
            if (head.botRight != null) {
                cnt++;
                head = head.botRight;
            }
        }
        if (cnt == 3) {
            if (head.botRight != null && head.botRight.terr != 2 && head.botRight.terr != 5 && head.botRight.terr <= 6 && head.botRight.player == -1) {
                highlighted[head.botRight.pos.first][head.botRight.pos.second] = true;
            }
        }
        //EXITING DANGER ZONE
        GamePanel.currentHighlights = highlighted;
    }
    public boolean onCurr(int i, int j) {
        return c.pos.first == i && c.pos.second == j;
    }
}
