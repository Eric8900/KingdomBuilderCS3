package Logic1;

public class DrawCard {
    public DrawCard() {
        Player curr = GameState.players.get(GameState.currentPlayer);
        //give curr player the top of the deck
        GameState.players.get(GameState.currentPlayer).chosenCard = GameState.deck.getTop();
        // give curr player 3 (settlements) + locationTiles size actions left for their turn
        GameState.players.get(GameState.currentPlayer).actionsLeft = 3 + curr.locationTiles.size();
        
    }
}
