package Logic1;
import java.util.*;
import Graphics.*;
import Logic1.GameState.State;

public class NextTurn {
    Player curr = GameState.players.get(GameState.currentPlayer);
    int cur;
    public NextTurn() {
        cur = GameState.currentPlayer;
        curr.locActionsLeft = 0;
        curr.settleActionsLeft = 0;
        GameState.deck.discard(curr.chosenCard);
        GamePanel.currentHighlights = new boolean[20][20];
        GameState.tempChosenGameHex = null;
        curr.chosenCard = -1;
        GameState.setState(State.DRAWCARD);
        GameState.update();
        GameState.currentPlayer++;
        if (GameState.currentPlayer == 4) GameState.currentPlayer = 0;
        // GameState.setState(State.DRAWCARD);
        // if (GameState.players.get(GameState.currentPlayer).chosenCard > -1) {
        //     GameState.setState(State.PLAYSETTLEMENTS);
        //     GameState.update();
        // }
        for (int i = 0; i < 4; i++) {
            GameState.players.get(i).updateRoundLocTiles();
        }
        if (cur == 3) {
            for (int i = 0; i < 4; i++) {
                if (GameState.players.get(i).tilesLeft < 1) {
                    GameState.setState(State.ENDGAME);
                    GameState.update();
                    break;
                }
            }
        }
        if (GameState.currentState != State.ENDGAME) {
            GameState.setState(State.PLAYSETTLEMENTS);
            GameState.update();
        }
    }
}
