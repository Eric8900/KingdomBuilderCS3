package Logic1;
import java.util.*;
import Graphics.*;

public class NextTurn {
    Player curr = GameState.players.get(GameState.currentPlayer);
    public NextTurn() {
        curr.locActionsLeft = 0;
        curr.settleActionsLeft = 0;
        GameState.deck.discardTop();
        GamePanel.currentHighlights = new boolean[20][20];
        curr.chosenCard = -1;
        GameState.currentPlayer++;
        if (GameState.currentPlayer == 4) GameState.currentPlayer = 0;
        GameState.setState(GameState.State.DRAWCARD);
        for (int i = 0; i < 4; i++) {
            GameState.players.get(i).updateRoundLocTiles();
        }
        
    }
}
