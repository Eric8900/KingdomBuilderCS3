package Logic1;
import java.util.*;

import Graphics.GamePanel;

public class PlaySettlements {
    int curr = GameState.currentPlayer;
    public PlaySettlements() {
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
}
