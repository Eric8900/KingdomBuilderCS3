package Logic1;
import Graphics.*;
import java.util.*;

public class PlayMoveLocationTile {
    int curr = GameState.currentPlayer;
    int locTile = GamePanel.locTiles[GameState.players.get(curr).selectedAction];
    public PlayMoveLocationTile() {
        boolean[][] highlighted = new boolean[20][20];
        ArrayList<Pair> placed = GameBoard.getPlacedForPlayer(curr, -1);
            if (placed != null) {
                for (int i = 0; i < placed.size(); i++) {
                    highlighted[placed.get(i).first][placed.get(i).second] = true;
                }
            }
        GamePanel.currentHighlights = highlighted;
    }
}
