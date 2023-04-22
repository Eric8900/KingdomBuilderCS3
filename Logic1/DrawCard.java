package Logic1;

public class DrawCard {
    public DrawCard() {
        Player curr = GameState.players.get(GameState.currentPlayer);
        //give curr player the top of the deck
        curr.chosenCard = GameState.deck.getTop();
        // give curr player 3 (settlements) + locationTiles size actions left for their turn
        curr.settleActionsLeft = 3;
        curr.locActionsLeft = curr.getNumLocationTiles();
    }
}
